package com.emenu.service.storage.impl;

import com.emenu.common.dto.dish.CostCardDto;
import com.emenu.common.dto.dish.CostCardItemDto;
import com.emenu.common.dto.storage.ItemAndIngredientSearchDto;
import com.emenu.common.dto.storage.StorageItemSearchDto;
import com.emenu.common.dto.storage.StorageReportDto;
import com.emenu.common.entity.dish.Unit;
import com.emenu.common.entity.storage.Ingredient;
import com.emenu.common.entity.storage.StorageItem;
import com.emenu.common.entity.storage.StorageReportIngredient;
import com.emenu.common.entity.storage.StorageReportItem;
import com.emenu.common.enums.other.ConstantEnum;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.enums.ExcelExportTemplateEnums;
import com.emenu.common.enums.storage.StorageItemStatusEnums;
import com.emenu.common.enums.storage.StorageReportTypeEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.StringUtils;
import com.emenu.common.utils.EntityUtil;
import com.emenu.mapper.storage.StorageItemMapper;
import com.emenu.service.dish.UnitService;
import com.emenu.service.other.ConstantService;
import com.emenu.service.other.SerialNumService;
import com.emenu.service.storage.IngredientService;
import com.emenu.service.storage.StorageItemService;
import com.emenu.service.storage.StorageReportService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.common.util.IOUtil;
import com.pandawork.core.framework.dao.CommonDao;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 库存物品Service实现
 *
 * @author: zhangteng
 * @time: 2015/11/12 8:49
 **/
@Service("storageItemService")
public class StorageItemServiceImpl implements StorageItemService {

    @Autowired
    private StorageItemMapper storageItemMapper;

    @Autowired
    private UnitService unitService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private SerialNumService serialNumService;

    @Autowired
    private StorageReportService storageReportService;

    @Autowired
    private ConstantService constantService;

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Override
    public List<StorageItem> listBySearchDto(ItemAndIngredientSearchDto searchDto) throws SSException {
        List<StorageItem> list = Collections.emptyList();

        int pageNo = searchDto.getPageNo() <= 0 ? 0 : searchDto.getPageNo() - 1;
        if (Assert.isNull(searchDto.getPageSize())
                || Assert.lessOrEqualZero(searchDto.getPageSize())){
            searchDto.setPageSize(10);
        }
        int offset = pageNo * searchDto.getPageSize();
        searchDto.setOffset(offset);
        try {
            list = storageItemMapper.listBySearchDto(searchDto);
            // 设置单位名
            setUnitName(list);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StorageItemQueryFailed, e);
        }
        return list;
    }

    @Override
    public void exportExcelBySearchDto(ItemAndIngredientSearchDto searchDto, HttpServletResponse response) throws SSException {
        OutputStream os = null;
        //从数据库中获取数据
        try {
            List<StorageItem> storageItemlist= listBySearchDto(searchDto);

            /*for (StorageItem storageItem :storageItemlist) {
                EntityUtil.setNullFieldDefault(storageItem);
                setUnitName(storageItem);
                setQuantityFormat(storageItem);
            }*/
            // 设置输出流
            // 设置excel文件名和sheetName
            String filename = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            filename = ExcelExportTemplateEnums.AdminStorageItemList.getName()+sdf.format(new Date());
            response.setContentType("application/msexcel");
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(filename.getBytes("gbk"), "ISO8859-1") + ".xls");
            os = response.getOutputStream();
            InputStream tplStream = IOUtil.getFileAsStream(ExcelExportTemplateEnums.AdminStorageItemList.getFilePath());
            Workbook tplWorkBook = Workbook.getWorkbook(tplStream);
            WritableWorkbook outBook = Workbook.createWorkbook(os, tplWorkBook);
            WritableSheet sheet = outBook.getSheet(0);
            int row=3;

            for(StorageItem storageItem :storageItemlist){
                //单元格居中格式
                WritableCellFormat cellFormat=new WritableCellFormat();
                cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
                cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                cellFormat.setWrap(true);
                // 序号
                Label labelNumber = new Label(0,row,String.valueOf(row - 2));
                sheet.addCell(labelNumber);
                // 名称
                Label lableName= new Label(1, row, storageItem.getName());
                sheet.addCell(lableName);
                // 物品编号
                Label labelItemNumber = new Label(2,row,storageItem.getItemNumber());
                sheet.addCell(labelItemNumber);
                // 助记码
                Label labelAssistantCode = new Label(3, row,storageItem.getAssistantCode());
                sheet.addCell(labelAssistantCode);
                // 原配料
                Label labelingredientName = new Label(4, row,storageItem.getIngredientName());
                sheet.addCell(labelingredientName);
                // 分类
                Label labelTagName = new Label(5, row,storageItem.getTagName());
                sheet.addCell(labelTagName);
                // 供货商
                Label labelSupplierName = new Label(6, row,storageItem.getSupplierName());
                sheet.addCell(labelSupplierName);
                // 入库总数量
                String totalStockIn = storageItem.getTotalStockInQuantity().toString();
                if(storageItem.getCountUnitId()!=null){
                    totalStockIn = totalStockIn.concat(unitService.queryById(storageItem.getCountUnitId()).getName());
                }
                Label labelTotalStockIn = new Label(7, row,totalStockIn);
                sheet.addCell(labelTotalStockIn);
                // 入库总金额
                Label labelTotalStockInMoney = new Label(8, row,storageItem.getTotalStockInMoney().toString());
                sheet.addCell(labelTotalStockInMoney);
                // 最新入库价格
                Label labelLastStockInPrice = new Label(9, row,storageItem.getLastStockInPrice().toString());
                sheet.addCell(labelLastStockInPrice);
                // 上限
                String maxStorageQuantityStr = storageItem.getMaxStorageQuantity().toString() + storageItem.getStorageUnitName();
                Label labelMaxStorage = new Label(10,row,maxStorageQuantityStr);
                sheet.addCell(labelMaxStorage);
                // 下限
                String minStorageQuantityStr = storageItem.getMinStorageQuantity().toString() + storageItem.getStorageUnitName();
                Label labelMinStorage = new Label(11,row,minStorageQuantityStr);
                sheet.addCell(labelMinStorage);
                // 出库方式
                String stockOutType = ((storageItem.getStockOutType() == 1)?"加权平均":"手动");
                Label labelStockOutType = new Label(12,row,stockOutType);
                sheet.addCell(labelStockOutType);
                row++;

            }
            outBook.write();
            outBook.close();
            tplWorkBook.close();
            tplStream.close();
            os.close();

        } catch (Exception e) {
            LogClerk.errLog.error(e);
            response.setContentType("text/html");
            response.setHeader("Content-Type", "text/html");
            response.setHeader("Content-disposition", "");
            response.setCharacterEncoding("UTF-8");
            try {
                String eMsg = "系统内部异常，请联系管理员！";
                eMsg= java.net.URLEncoder.encode(eMsg.toString(),"UTF-8");
                response.sendRedirect("/admin/storage/settlement/supplier?eMsg="+eMsg);
                os.close();
            } catch (IOException e1) {
                LogClerk.errLog.error(e1.getMessage());
            }
            throw SSException.get(EmenuException.ExportStorageSettlementCheckFailed, e);
        }finally{
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    LogClerk.errLog.error(e);
                    throw SSException.get(EmenuException.ExportStorageSettlementCheckFailed, e);
                }
            }
        }
    }

    @Override
    public int countBySearchDto(ItemAndIngredientSearchDto searchDto) throws SSException {
        Integer count = 0;
        try {
            count = storageItemMapper.countBySearchDto(searchDto);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
        return count == null ? 0 : count;
    }

    @Override
    public List<StorageItem> listAll() throws SSException {
        List<StorageItem> list = Collections.emptyList();
        try {
            list = storageItemMapper.listAll();
            setUnitName(list);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StorageItemQueryFailed, e);
        }
        return list;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void newStorageItem(StorageItem storageItem) throws SSException {
        try {
            if (!checkBeforeSave(storageItem)) {
                return ;
            }
            // 设置编号和助记码
            // 助记码
            if (Assert.isNull(storageItem.getAssistantCode())
                    || storageItem.getAssistantCode().equals("")){
                String assistantCode = StringUtils.str2Pinyin(storageItem.getName(),"headChar");
                storageItem.setAssistantCode(assistantCode);
            }
            Ingredient ingredient = ingredientService.queryById(storageItem.getIngredientId());
            storageItem.setCostCardUnitId(ingredient.getCostCardUnitId());
            storageItem.setItemNumber(serialNumService.generateSerialNum(SerialNumTemplateEnums.StorageItemNum));

            commonDao.insert(storageItem);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StorageItemInsertFailed, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateStorageItem(StorageItem storageItem,Integer isUpdated) throws SSException {
        try {
            if (isUpdated == 0){
                if (checkBeforeSave(storageItem)){
                    Assert.isNotNull(storageItem.getId(), EmenuException.StorageItemIdNotNull);
                    StorageItem storageItem1 = storageItemMapper.queryById(storageItem.getId());
                    Assert.isNotNull(storageItem1,EmenuException.StorageItemIsNotNull);
                    if (storageItem.getName().equals(storageItem1.getName())
                            && storageItem.getTagId().equals(storageItem1.getTagId())
                            && storageItem.getSupplierPartyId().equals(storageItem1.getSupplierPartyId())){

                            storageItemMapper.updateStorageItemById(storageItem);

                    } else {
                        if (checkStorageItemIsExist(storageItem)){
                            throw SSException.get(EmenuException.StorageItemIsExist);
                        } else {
                            storageItemMapper.updateStorageItemById(storageItem);
                        }
                    }
                }
            } else {
                Assert.isNotNull(storageItem.getId(), EmenuException.StorageItemIdNotNull);
                Assert.isNotNull(storageItem,EmenuException.StockOutTypeError);
                if (storageItem.getStockOutType() < 1 || storageItem.getStockOutType()>2){
                    throw SSException.get(EmenuException.StorageItemNameNotNull);
                }
                Assert.isNotNull(storageItem.getMaxStorageQuantity(), EmenuException.StorageItemMaxMinQuantity);
                Assert.isNotNull(storageItem.getMinStorageQuantity(), EmenuException.StorageItemMaxMinQuantity);
                storageItemMapper.updateStorageItemByIsUpdated(storageItem);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StorageItemUpdateFailed, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateById(StorageItem storageItem) throws SSException {
        try {
            if(Assert.isNull(storageItem)){
                throw SSException.get(EmenuException.StorageItemIdNotNull);
            }
            if (Assert.isNull(storageItem.getId())&&Assert.lessOrEqualZero(storageItem.getId())){
                throw SSException.get(EmenuException.StorageItemIdNotNull);
            }
            commonDao.update(storageItem);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StorageItemUpdateFailed, e);
        }
    }

    @Override
    public boolean checkStorageItemIsExist(StorageItem storageItem) throws SSException {
        Integer count = 0;
        try {
            Assert.isNotNull(storageItem.getName(),EmenuException.StorageItemNameNotNull);
            Assert.isNotNull(storageItem.getTagId(),EmenuException.StorageItemTagNotNull);
            Assert.lessOrEqualZero(storageItem.getTagId(),EmenuException.StorageItemTagIdError);
            Assert.isNotNull(storageItem.getSupplierPartyId(),EmenuException.StorageItemSupplierNotNull);
            Assert.lessOrEqualZero(storageItem.getSupplierPartyId(),EmenuException.StorageItemSupplierPartyIdError);
            count = storageItemMapper.countByNameAndTagIdAndsupplierPartyId(storageItem.getName(),storageItem.getTagId(),storageItem.getSupplierPartyId());
            if (Assert.isNull(count)
                    || count == 0){
                return false;
            } else {
                return count > 0;
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }

    @Override
    public void updateUnit(StorageItem storageItem) throws SSException {
        try {
            Assert.isNotNull(storageItem.getOrderUnitId(), EmenuException.StorageItemUnitNotNull);
            Assert.isNotNull(storageItem.getStorageUnitId(), EmenuException.StorageItemUnitNotNull);
            Assert.isNotNull(storageItem.getCostCardUnitId(), EmenuException.StorageItemUnitNotNull);
            Assert.isNotNull(storageItem.getOrderToStorageRatio(), EmenuException.StorageItemUnitRatioNotNull);
            Assert.isNotNull(storageItem.getStorageToCostCardRatio(), EmenuException.StorageItemUnitRatioNotNull);

            commonDao.update(storageItem);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }

    @Override
    public void delById(int id) throws SSException {
        if (Assert.isNull(id)
                ||Assert.lessOrEqualZero(id)) {
            throw SSException.get(EmenuException.StorageItemIdNotNull);
        }
        try {
            // TODO: 2015/11/12 查询是否有成本卡使用物品,查询结算的库存物品是否还有剩余
            // 删除即修改使用状态 Deleted(2, "删除")
            storageItemMapper.updateStatusById(id, StorageItemStatusEnums.Deleted.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StorageItemDeleteFailed, e);
        }
    }

    @Override
    public boolean checkIsCanDelById(int id) throws SSException {
        // 遍历所有单据,查询该库存物品是被添加
        try {
            // map中的key为库存物品的id，value为库存物品存在状态0或者空代表没有，1代表有
            Map<Integer,Integer> checkMap = new HashMap<Integer, Integer>();
            List<StorageReportDto> storageReportDtoList = storageReportService.listReportDto();
            for (StorageReportDto storageReportDto : storageReportDtoList){
                if (storageReportDto.getStorageReport().getType() == StorageReportTypeEnum.StockInReport.getId()){
                    for (StorageReportItem storageReportItem : storageReportDto.getStorageReportItemList()){
                        checkMap.put(storageReportItem.getItemId(),1);
                    }
                }
            }
            Integer jd = checkMap.get(id);
            if (Assert.isNull(id)
                    || Assert.lessOrEqualZero(id)){
                return true;
            } else {
                if (Assert.isNull(checkMap.get(id))){
                    return true;
                }
                if (checkMap.get(id) == 1){
                    return false;
                } else {
                    return true;
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }

    @Override
    public int countByTagId(int tagId) throws SSException {
        Integer count = 0;
        if (Assert.lessOrEqualZero(tagId)) {
            return count;
        }
        try {
            count = storageItemMapper.countByTagId(tagId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StorageItemQueryFailed, e);
        }
        return count == null ? 0 : count;
    }

    @Override
    public StorageItem queryById(int id) throws SSException {
        if (Assert.lessOrEqualZero(id)) {
            return null;
        }
        try {
            StorageItem storageItem = storageItemMapper.queryById(id);
            if (Assert.isNull(storageItem)){
                return storageItem;
            }
            setUnitName(storageItem);
            setQuantityFormat(storageItem);
            return storageItem;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StorageItemQueryFailed, e);
        }
    }

    @Override
    public List<StorageItem> listByKeyword(String keyword) throws SSException {
        List<StorageItem> list = Collections.emptyList();
        if (Assert.isNull(keyword)) {
            return list;
        }
        try {
            list = storageItemMapper.listByKeyword(keyword);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
        return list;
    }

    @Override
    public List<Integer> listIdsByIngredientId(Integer ingredientId) throws SSException {
        List<Integer> itemIdList = Collections.emptyList();
        if (Assert.isNull(ingredientId)
                || Assert.lessOrEqualZero(ingredientId)){
            return itemIdList;
        }
        try {
            itemIdList = storageItemMapper.listIdsByIngredientId(ingredientId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
        return itemIdList;
    }

    private boolean checkBeforeSave(StorageItem storageItem) throws SSException {
        Assert.isNotNull(storageItem,EmenuException.StorageItemIsNotNull);
        Assert.isNotNull(storageItem,EmenuException.StockOutTypeError);
        if (storageItem.getStockOutType()<1 || storageItem.getStockOutType()>2){
            throw SSException.get(EmenuException.StorageItemNameNotNull);
        }
        if (Assert.isNotNull(storageItem.getName())){
            String name1 = storageItem.getName().replaceAll(" ","");
            if (name1.equals("")){
                throw SSException.get(EmenuException.StorageItemNameNotNull);
            }
        }
        Assert.isNotNull(storageItem.getName(), EmenuException.StorageItemNameNotNull);
        Assert.isNotNull(storageItem.getIngredientId(), EmenuException.StorageItemIngredientIdNotNull);
        Assert.isNotNull(storageItem.getSupplierPartyId(), EmenuException.StorageItemSupplierNotNull);
        Assert.isNotNull(storageItem.getTagId(), EmenuException.StorageItemTagNotNull);
        Assert.isNotNull(storageItem.getOrderUnitId(), EmenuException.StorageItemUnitNotNull);
        Assert.isNotNull(storageItem.getStorageUnitId(), EmenuException.StorageItemUnitNotNull);
        Assert.isNotNull(storageItem.getOrderToStorageRatio(), EmenuException.StorageItemUnitRatioNotNull);
        Assert.isNotNull(storageItem.getStorageToCostCardRatio(), EmenuException.StorageItemUnitRatioNotNull);
        Assert.isNotNull(storageItem.getMaxStorageQuantity(), EmenuException.StorageItemMaxMinQuantity);
        Assert.isNotNull(storageItem.getMinStorageQuantity(), EmenuException.StorageItemMaxMinQuantity);

        return true;
    }

    private void setUnitName(List<StorageItem> storageItemList) throws SSException {
        List<Unit> unitList = unitService.listAll();
        Map<Integer, String> unitMap = new HashMap<Integer, String>();
        for (Unit unit : unitList) {
            unitMap.put(unit.getId(), unit.getName());
        }
        unitMap.put(0, "");
        for (StorageItem storageItem : storageItemList) {
            storageItem.setOrderUnitName(unitMap.get(storageItem.getOrderUnitId()));
            storageItem.setStorageUnitName(unitMap.get(storageItem.getStorageUnitId()));
            storageItem.setCostCardUnitName(unitMap.get(storageItem.getCostCardUnitId()));
            storageItem.setCountUnitName(unitMap.get(storageItem.getCountUnitId()));
        }
    }

    private void setUnitName(StorageItem storageItem) throws SSException {
        List<Unit> unitList = unitService.listAll();
        Map<Integer, String> unitMap = new HashMap<Integer, String>();
        for (Unit unit : unitList) {
            unitMap.put(unit.getId(), unit.getName());
        }
        unitMap.put(0, "");
        storageItem.setOrderUnitName(unitMap.get(storageItem.getOrderUnitId()));
        storageItem.setStorageUnitName(unitMap.get(storageItem.getStorageUnitId()));
        storageItem.setCostCardUnitName(unitMap.get(storageItem.getCostCardUnitId()));
        storageItem.setCountUnitName(unitMap.get(storageItem.getCountUnitId()));
    }

    public void setQuantityFormat(List<StorageItem> storageItemList) throws SSException{
        // 是否启用四舍五入
        int roundingMode = Integer.parseInt(constantService.queryValueByKey(ConstantEnum.RoundingMode.getKey()));

        for (StorageItem storageItem : storageItemList) {
            // 将数量和单位拼接成string，并将成本卡单位表示的数量转换为库存单位表示
            BigDecimal maxStorageQuantity = new BigDecimal(0);
            if (roundingMode == 1) {
                maxStorageQuantity = storageItem.getMaxStorageQuantity().divide(storageItem.getStorageToCostCardRatio(), 2, BigDecimal.ROUND_HALF_EVEN);
            }
            if (roundingMode == 0) {
                maxStorageQuantity = storageItem.getMaxStorageQuantity().divide(storageItem.getStorageToCostCardRatio(), 2, BigDecimal.ROUND_DOWN);
            }
            String maxStorageQuantityStr = maxStorageQuantity.toString() + storageItem.getStorageUnitName();
            storageItem.setMaxStorageQuantityStr(maxStorageQuantityStr);

            // 最小库存
            BigDecimal minStorageQuantity = new BigDecimal(0);
            if (roundingMode == 1) {
                minStorageQuantity = storageItem.getMinStorageQuantity().divide(storageItem.getStorageToCostCardRatio(), 2, BigDecimal.ROUND_HALF_EVEN);
            }
            if (roundingMode == 0) {
                minStorageQuantity = storageItem.getMinStorageQuantity().divide(storageItem.getStorageToCostCardRatio(), 2, BigDecimal.ROUND_DOWN);
            }
            String minStorageQuantityStr = minStorageQuantity.toString() + storageItem.getStorageUnitName();
            storageItem.setMinStorageQuantityStr(minStorageQuantityStr);

            // 总数量
            BigDecimal totalStockInQuantityStr = new BigDecimal(0);
            if (roundingMode == 1) {
                totalStockInQuantityStr = storageItem.getTotalStockInQuantity().divide(storageItem.getTotalStockInQuantity(), 2, BigDecimal.ROUND_HALF_EVEN);
            }
            if (roundingMode == 0) {
                totalStockInQuantityStr = storageItem.getTotalStockInQuantity().divide(storageItem.getTotalStockInQuantity(), 2, BigDecimal.ROUND_DOWN);
            }
            String totalQuantityStr = totalStockInQuantityStr.toString() + storageItem.getStorageUnitName();
            storageItem.setTotalStockInQuantityStr(totalQuantityStr);
        }
    }

    public void setQuantityFormat(StorageItem storageItem) throws SSException{
        // 是否启用四舍五入
        int roundingMode = Integer.parseInt(constantService.queryValueByKey(ConstantEnum.RoundingMode.getKey()));

        // 将数量和单位拼接成string，并将成本卡单位表示的数量转换为库存单位表示
        BigDecimal maxStorageQuantity = new BigDecimal(0);
//        if (roundingMode == 1) {
//            maxStorageQuantity = storageItem.getMaxStorageQuantity().divide(storageItem.getStorageToCostCardRatio(), 2, BigDecimal.ROUND_HALF_EVEN);
//        }
//        if (roundingMode == 0) {
//            maxStorageQuantity = storageItem.getMaxStorageQuantity().divide(storageItem.getStorageToCostCardRatio(), 2, BigDecimal.ROUND_DOWN);
//        }
        if (roundingMode == 1) {
            maxStorageQuantity = storageItem.getMaxStorageQuantity();
        }
        if (roundingMode == 0) {
            maxStorageQuantity = storageItem.getMaxStorageQuantity();
        }
        String maxStorageQuantityStr = maxStorageQuantity.toString() + storageItem.getStorageUnitName();
        storageItem.setMaxStorageQuantityStr(maxStorageQuantityStr);

        // 最小库存
        BigDecimal minStorageQuantity = new BigDecimal(0);
//        if (roundingMode == 1) {
//            minStorageQuantity = storageItem.getMinStorageQuantity().divide(storageItem.getStorageToCostCardRatio(), 2, BigDecimal.ROUND_HALF_EVEN);
//        }
//        if (roundingMode == 0) {
//            minStorageQuantity = storageItem.getMinStorageQuantity().divide(storageItem.getStorageToCostCardRatio(), 2, BigDecimal.ROUND_DOWN);
//        }
        if (roundingMode == 1) {
            minStorageQuantity = storageItem.getMinStorageQuantity();
        }
        if (roundingMode == 0) {
            minStorageQuantity = storageItem.getMinStorageQuantity();
        }
        String minStorageQuantityStr = minStorageQuantity.toString() + storageItem.getStorageUnitName();
        storageItem.setMinStorageQuantityStr(minStorageQuantityStr);

        // 总数量
        BigDecimal totalStockInQuantityStr = new BigDecimal(0);
        if (roundingMode == 1) {
            totalStockInQuantityStr = storageItem.getTotalStockInQuantity().divide(storageItem.getStorageToCostCardRatio(), 2, BigDecimal.ROUND_HALF_EVEN);
        }
        if (roundingMode == 0) {
            totalStockInQuantityStr = storageItem.getTotalStockInQuantity().divide(storageItem.getStorageToCostCardRatio(), 2, BigDecimal.ROUND_DOWN);
        }
        String totalQuantityStr = totalStockInQuantityStr.toString() + storageItem.getStorageUnitName();
        storageItem.setTotalStockInQuantityStr(totalQuantityStr);
    }

    @Override
    public StorageItem queryByKeyword(String keyword) throws SSException {
        StorageItem storageItem = null;
        if (Assert.isNull(keyword)){
            throw SSException.get(EmenuException.KeywordError);
        }
        try {
            storageItem = storageItemMapper.queryByKeyword(keyword);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StorageItemQueryFailed, e);
        }
        return storageItem;
    }
}
