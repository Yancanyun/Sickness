package com.emenu.service.storage.impl;

import com.emenu.common.dto.dish.CostCardDto;
import com.emenu.common.dto.dish.CostCardItemDto;
import com.emenu.common.dto.storage.ItemAndIngredientSearchDto;
import com.emenu.common.dto.storage.StorageItemDto;
import com.emenu.common.dto.storage.StorageReportDto;
import com.emenu.common.dto.storage.StorageSupplierDto;
import com.emenu.common.entity.dish.CostCard;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.dish.Unit;
import com.emenu.common.entity.storage.*;
import com.emenu.common.enums.ExcelExportTemplateEnums;
import com.emenu.common.enums.other.ConstantEnum;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.enums.storage.StorageReportTypeEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.exception.PartyException;
import com.emenu.common.utils.EntityUtil;
import com.emenu.common.utils.StringUtils;
import com.emenu.mapper.storage.IngredientMapper;
import com.emenu.service.dish.CostCardService;
import com.emenu.service.dish.UnitService;
import com.emenu.service.other.ConstantService;
import com.emenu.service.other.SerialNumService;
import com.emenu.service.storage.IngredientService;
import com.emenu.service.storage.StorageItemService;
import com.emenu.service.storage.StorageReportItemService;
import com.emenu.service.storage.StorageReportService;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
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
 * IngredientServiceImpl
 *
 * @author xiaozl
 * @date: 2016/5/14
 */
@Service("ingredientService")
public class IngredientServiceImpl implements IngredientService {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private IngredientMapper ingredientMapper;

    @Autowired
    private UnitService unitService;

    @Autowired
    private SerialNumService serialNumService;

    @Autowired
    private CostCardService costCardService;

    @Autowired
    private StorageItemService storageItemService;

    @Autowired
    private StorageReportService storageReportService;

    @Autowired
    private ConstantService constantService;

    private final static int DEFAULT_PAGE_SIZE = 10;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public Ingredient newIngredient(Ingredient ingredient) throws SSException {
        if (!checkBeforeSave(ingredient)) {
            return null;
        }
        // 设置物品编号和助记码
        try {
            // 原配料编号
            String serialNumber = serialNumService.generateSerialNum(SerialNumTemplateEnums.IngredientNum);
            ingredient.setIngredientNumber(serialNumber);
            ingredient.setMaxStorageQuantity(ingredient.getMaxStorageQuantity().multiply(ingredient.getStorageToCostCardRatio()));
            ingredient.setMinStorageQuantity(ingredient.getMinStorageQuantity().multiply(ingredient.getStorageToCostCardRatio()));
            // 助记码
            if (Assert.isNull(ingredient.getAssistantCode())
                    || ingredient.getAssistantCode().equals("")){
                String assistantCode = StringUtils.str2Pinyin(ingredient.getName(),"headChar");
                ingredient.setAssistantCode(assistantCode);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        try {
            return commonDao.insert(ingredient);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.IngredientInserFailed, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateIngredient(Ingredient ingredient,int isUpdated) throws SSException {
        try {
            // isUpdated 0，代表 可以修改，1代表不可以修改
            if (isUpdated == 0) {
                if (this.checkBeforeSave(ingredient)) {
                    Ingredient ingredient1 = queryById(ingredient.getId());
//                    ingredient.setMaxStorageQuantity(ingredient.getMaxStorageQuantity());
//                    ingredient.setMinStorageQuantity(ingredient.getMinStorageQuantity());
                    Assert.isNotNull(ingredient1,EmenuException.IngredientIsNotNull);
                    if (ingredient1.getName().equals(ingredient.getName())){
                        ingredientMapper.updateIngredient(ingredient);
                    } else {
                        if (checkIngredientNameIsExist(ingredient.getName())){
                            throw SSException.get(EmenuException.IngredientIsExist);
                        } else {
                            ingredientMapper.updateIngredient(ingredient);
                        }
                    }
                }
            } else {
                Assert.isNotNull(ingredient,EmenuException.IngredientIsNotNull);
                Assert.isNotNull(ingredient.getAssistantCode(),EmenuException.AssistantCode);
                if (Assert.isNull(ingredient.getId())
                        || Assert.lessOrEqualZero(ingredient.getId())){
                    throw SSException.get(EmenuException.IngredientIdError);
                }
                Assert.isNotNull(ingredient.getMaxStorageQuantity(), EmenuException.IngredientMaxStorageQuantityIsNotNull);
                Assert.isNotNull(ingredient.getMinStorageQuantity(), EmenuException.IngredientMinStorageQuantityIsNotNull);
                Ingredient ingredient1 = queryById(ingredient.getId());
                ingredient1.setMaxStorageQuantity(ingredient.getMaxStorageQuantity().multiply(ingredient.getStorageToCostCardRatio()));
                ingredient1.setMinStorageQuantity(ingredient.getMinStorageQuantity().multiply(ingredient.getStorageToCostCardRatio()));
                ingredientMapper.updateIngredientCodeAndLimitById(ingredient1);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.IngredientUpdateFailed, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateIngredientStatusById(int id, int status) throws SSException {
        try {
            if (Assert.isNull(id)
                    || Assert.lessOrEqualZero(id)
                    || Assert.isNull(status)
                    || Assert.lessOrEqualZero(status)){
                throw SSException.get(EmenuException.IngredientUpdateFailed);
            }
            ingredientMapper.updateIngredientStatusById(id,status);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }


    @Override
    public Ingredient queryById(int id) throws SSException {
        if (Assert.lessOrEqualZero(id)){
            return null;
        }
        Ingredient ingredient = null;
        try {
            ingredient = commonDao.queryById(Ingredient.class,id);
            if (Assert.isNull(ingredient)){
                return null;
            }
            Tag tag = commonDao.queryById(Tag.class,ingredient.getTagId());
            if (Assert.isNull(tag)){
                return null;
            }
            ingredient.setTagName(tag.getName());
            List<Unit> unitList = unitService.listAll();
            Map<Integer, String> unitMap = new HashMap<Integer, String>();
            for (Unit unit : unitList) {
                unitMap.put(unit.getId(), unit.getName());
            }
            unitMap.put(0, "");
            ingredient.setOrderUnitName(unitMap.get(ingredient.getOrderUnitId()));
            ingredient.setStorageUnitName(unitMap.get(ingredient.getStorageUnitId()));
            ingredient.setCostCardUnitName(unitMap.get(ingredient.getCostCardUnitId()));
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return ingredient;
    }

    @Override
    public List<Ingredient> listBySearchDto(ItemAndIngredientSearchDto searchDto) throws SSException {
        List<Ingredient> ingredientList = Collections.emptyList();
        int pageNo = 0;
        int offset = 0;
        if (Assert.isNotNull(searchDto)){
            if (Assert.isNotNull(searchDto.getPageNo())) {
                pageNo = searchDto.getPageNo() <= 0 ? 0 : searchDto.getPageNo() - 1;
            }
            if (Assert.isNotNull(searchDto.getPageSize())) {
                if (Assert.lessOrEqualZero(searchDto.getPageSize())){
                    searchDto.setPageSize(DEFAULT_PAGE_SIZE);
                }
                offset = pageNo * searchDto.getPageSize();
                searchDto.setOffset(offset);
            }
        }
        try {
            ingredientList = ingredientMapper.listBySearchDto(searchDto);
            //设置单位名称
            setUnitName(ingredientList);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return ingredientList;
    }

    @Override
    public List<Ingredient> listByKeyword(String keyword) throws SSException {

        List<Ingredient> ingredientList = Collections.emptyList();
        if (Assert.isNull(keyword)){
            throw SSException.get(EmenuException.KeywordError);
        }
        try {
            ingredientList = ingredientMapper.listByKeyword(keyword);
            setUnitName(ingredientList);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return ingredientList;
    }

    @Override
    public List<Ingredient> listAll() throws SSException {
        List<Ingredient> ingredientList = Collections.emptyList();
         try {
             ingredientList = ingredientMapper.listAll();
             //设置单位名称
             setUnitName(ingredientList);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return ingredientList;
    }

    @Override
    public int countBySearchDto(ItemAndIngredientSearchDto searchDto) throws SSException {
        Integer count = 0;
        try {
            count = ingredientMapper.countBySearchDto(searchDto);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
        return count == null ? 0 : count;
    }

    public boolean checkIngredientNameIsExist(String name) throws SSException{
        int count = 0;
        try {
            count = ingredientMapper.coutByName(name);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return count > 0 ? true:false;
    }

    @Override
    public void exportExcel(ItemAndIngredientSearchDto searchDto,  HttpServletResponse response) throws SSException {
        OutputStream os = null;
        try {
            //从数据库中获取数据
            List<Ingredient> ingredientList=ingredientMapper.listDetailsBySearchDto(searchDto);

            for (Ingredient ingredient :ingredientList) {
                EntityUtil.setNullFieldDefault(ingredient);
                setQuantityFormat(ingredient);
            }
            // 设置输出流
            // 设置excel文件名和sheetName
            String filename = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            filename = ExcelExportTemplateEnums.AdminStorageList.getName()+sdf.format(new Date());
            response.setContentType("application/msexcel");
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(filename.getBytes("gbk"), "ISO8859-1") + ".xls");
            os = response.getOutputStream();
            InputStream tplStream = IOUtil.getFileAsStream(ExcelExportTemplateEnums.AdminStorageList.getFilePath());
            Workbook tplWorkBook = Workbook.getWorkbook(tplStream);
            WritableWorkbook outBook = Workbook.createWorkbook(os, tplWorkBook);
            //获取sheet 往sheet中写入数据
            WritableSheet sheet = outBook.getSheet(0);
            int row=3;

            for(Ingredient ingredient :ingredientList){
                //单元格居中格式
                WritableCellFormat cellFormat=new WritableCellFormat();
                cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
                cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                cellFormat.setWrap(true);
                //编号
                Label lableId= new Label(0, row, ingredient.getId()+"");
                lableId.setCellFormat(cellFormat);
                sheet.addCell(lableId);
               //名称
                Label labelName = new Label(1, row,ingredient.getName());
                labelName.setCellFormat(cellFormat);
                sheet.addCell(labelName);
              //原配料编号
                Label IngredientNumber = new Label(2, row,ingredient.getIngredientNumber());
                IngredientNumber.setCellFormat(cellFormat);
                sheet.addCell(IngredientNumber);
            //助记码
                Label AssistantCode = new Label(3, row,ingredient.getAssistantCode());
                AssistantCode.setCellFormat(cellFormat);
                sheet.addCell(AssistantCode);

             //所属分类
                Label TagName = new Label(4, row,ingredient.getTagName());
                TagName.setCellFormat(cellFormat);
                sheet.addCell(TagName);
             //订货单位
                Label OrderUnitName = new Label(5, row,ingredient.getOrderUnitName());
                OrderUnitName.setCellFormat(cellFormat);
                sheet.addCell(OrderUnitName);
             //订货单位与库存换算关系
                Label OrderToStorageRatio = new Label(6, row,ingredient.getOrderToStorageRatio().toString());
                OrderToStorageRatio.setCellFormat(cellFormat);
                sheet.addCell(OrderToStorageRatio);
            //库存单位
                Label StorageUnitName = new Label(7, row,ingredient.getStorageUnitName());
                StorageUnitName.setCellFormat(cellFormat);
                sheet.addCell(StorageUnitName);
            //库存单位与成本卡换算关系
                Label StorageToCostCardRatio = new Label(8, row,ingredient.getStorageToCostCardRatio().toString());
                StorageToCostCardRatio.setCellFormat(cellFormat);
                sheet.addCell(StorageToCostCardRatio);
            //成本卡单位
                Label CostCardUnitName = new Label(9, row,ingredient.getCostCardUnitName());
                CostCardUnitName.setCellFormat(cellFormat);
                sheet.addCell(CostCardUnitName);

             //库存预警上限
                Label MaxStorageQuantity = new Label(10, row,ingredient.getMaxStorageQuantityStr());
                MaxStorageQuantity.setCellFormat(cellFormat);
                sheet.addCell(MaxStorageQuantity);
             //库存预警下限
                Label MinStorageQuantity = new Label(11, row,ingredient.getMinStorageQuantityStr());
                MinStorageQuantity.setCellFormat(cellFormat);
                sheet.addCell(MinStorageQuantity);
             //均价
                Label AveragePrice = new Label(12, row,ingredient.getAveragePrice().toString());
                AveragePrice.setCellFormat(cellFormat);
                sheet.addCell(AveragePrice);
              //数量
                Label RealQuantity = new Label(13, row,ingredient.getRealQuantityStr());
                RealQuantity.setCellFormat(cellFormat);
                sheet.addCell(RealQuantity);

                //金额
                Label RealMoney = new Label(14, row,ingredient.getRealMoney().toString());
                RealMoney.setCellFormat(cellFormat);
                sheet.addCell(RealMoney);

                //总数量
                Label TotalQuantity = new Label(15, row,ingredient.getTotalQuantityStr());
                TotalQuantity.setCellFormat(cellFormat);
                sheet.addCell(TotalQuantity);

                //总金额
                Label labelTotalMoney = new Label(16, row,ingredient.getTotalMoney().toString());
                labelTotalMoney.setCellFormat(cellFormat);
                sheet.addCell(labelTotalMoney);

                row++;

            }


            outBook.write();
            outBook.close();
            tplWorkBook.close();
            tplStream.close();
            os.close();
        } catch (Exception e) {
            System.out.println();
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
        }finally {
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


    private boolean checkBeforeSave(Ingredient ingredient) throws SSException {
        if (Assert.isNull(ingredient)) {
            return false;
        }
        Ingredient ingredientOld = null;
        Assert.isNotNull(ingredient.getTagId(), EmenuException.IngredientTagIdIsNotNull);
        Assert.isNotNull(ingredient.getName(), EmenuException.IngredientNameIsNotNull);
        if(ingredient.getId() != null){
            ingredientOld = this.queryById(ingredient.getId());
        }

        if (ingredientOld == null){
            if (checkIngredientNameIsExist(ingredient.getName())){
                throw SSException.get(EmenuException.IngredientIsExist);
            }
        }else {
            if (checkIngredientNameIsExist(ingredient.getName())
                    && !(ingredient.getName().equals(ingredientOld.getName()))){
                throw SSException.get(EmenuException.IngredientIsExist);
            }
        }

        Assert.isNotNull(ingredient.getOrderUnitId(), EmenuException.IngredientOrderUnitIdIsNotNull);
        Assert.isNotNull(ingredient.getStorageUnitId(), EmenuException.IngredientStorageUnitIdIsNotNull);
        Assert.isNotNull(ingredient.getCostCardUnitId(), EmenuException.IngredientCostCardUnitIdIsNotNull);
        Assert.isNotNull(ingredient.getOrderToStorageRatio(), EmenuException.IngredientOrderToStorageRatioIsNotNull);
        Assert.isNotNull(ingredient.getStorageToCostCardRatio(), EmenuException.IngredientStorageToCostCardRatioIsNotNull);
        Assert.isNotNull(ingredient.getMaxStorageQuantity(), EmenuException.IngredientMaxStorageQuantityIsNotNull);
        Assert.isNotNull(ingredient.getMinStorageQuantity(), EmenuException.IngredientMinStorageQuantityIsNotNull);

        return true;
    }

    private void setUnitName(List<Ingredient> ingredientList) throws SSException {
        List<Unit> unitList = unitService.listAll();
        Map<Integer, String> unitMap = new HashMap<Integer, String>();
        for (Unit unit : unitList) {
            unitMap.put(unit.getId(), unit.getName());
        }
        unitMap.put(0, "");
        for (Ingredient ingredient : ingredientList) {
            ingredient.setOrderUnitName(unitMap.get(ingredient.getOrderUnitId()));
            ingredient.setStorageUnitName(unitMap.get(ingredient.getStorageUnitId()));
            ingredient.setCostCardUnitName(unitMap.get(ingredient.getCostCardUnitId()));
        }
    }

    public void setQuantityFormat(Ingredient ingredient) throws SSException{
        // 是否启用四舍五入
        int roundingMode = Integer.parseInt(constantService.queryValueByKey(ConstantEnum.RoundingMode.getKey()));

        // 将数量和单位拼接成string，并将成本卡单位表示的数量转换为库存单位表示
        BigDecimal maxStorageQuantity = new BigDecimal(0);
        if (roundingMode == 1) {
            maxStorageQuantity = ingredient.getMaxStorageQuantity().divide(ingredient.getStorageToCostCardRatio(), 2, BigDecimal.ROUND_HALF_EVEN);
        }
        if (roundingMode == 0) {
            maxStorageQuantity = ingredient.getMaxStorageQuantity().divide(ingredient.getStorageToCostCardRatio(), 2, BigDecimal.ROUND_DOWN);
        }
        String maxStorageQuantityStr = maxStorageQuantity.toString() + ingredient.getStorageUnitName();
        ingredient.setMaxStorageQuantityStr(maxStorageQuantityStr);

        // 最小库存
        BigDecimal minStorageQuantity = new BigDecimal(0);
        if (roundingMode == 1) {
            minStorageQuantity = ingredient.getMinStorageQuantity().divide(ingredient.getStorageToCostCardRatio(), 2, BigDecimal.ROUND_HALF_EVEN);
        }
        if (roundingMode == 0) {
            minStorageQuantity = ingredient.getMinStorageQuantity().divide(ingredient.getStorageToCostCardRatio(), 2, BigDecimal.ROUND_DOWN);
        }
        String minStorageQuantityStr = minStorageQuantity.toString() + ingredient.getStorageUnitName();
        ingredient.setMinStorageQuantityStr(minStorageQuantityStr);

        // 实际数量
        BigDecimal realQuantity = new BigDecimal(0);
        if (roundingMode == 1) {
            realQuantity = ingredient.getRealQuantity().divide(ingredient.getStorageToCostCardRatio(), 2, BigDecimal.ROUND_HALF_EVEN);
        }
        if (roundingMode == 0) {
            realQuantity = ingredient.getRealQuantity().divide(ingredient.getStorageToCostCardRatio(), 2, BigDecimal.ROUND_DOWN);
        }
        String realQuantityStr = realQuantity.toString() + ingredient.getStorageUnitName();
        ingredient.setRealQuantityStr(realQuantityStr);

        // 总数量

        BigDecimal totalStockInQuantityStr = new BigDecimal(0);
        if (roundingMode == 1) {
            totalStockInQuantityStr = ingredient.getTotalQuantity().divide(ingredient.getStorageToCostCardRatio(), 2, BigDecimal.ROUND_HALF_EVEN);
        }
        if (roundingMode == 0) {
            totalStockInQuantityStr = ingredient.getTotalQuantity().divide(ingredient.getStorageToCostCardRatio(), 2, BigDecimal.ROUND_DOWN);
        }
        String totalQuantityStr = totalStockInQuantityStr.toString() + ingredient.getStorageUnitName();
        ingredient.setTotalQuantityStr(totalQuantityStr);
    }

    public void setQuantityFormat(List<Ingredient> ingredientList) throws SSException{
        // 是否启用四舍五入
        int roundingMode = Integer.parseInt(constantService.queryValueByKey(ConstantEnum.RoundingMode.getKey()));

        for (Ingredient ingredient : ingredientList) {
            // 将数量和单位拼接成string，并将成本卡单位表示的数量转换为库存单位表示
            BigDecimal maxStorageQuantity = new BigDecimal(0);
            if (roundingMode == 1) {
                maxStorageQuantity = ingredient.getMaxStorageQuantity().divide(ingredient.getStorageToCostCardRatio(), BigDecimal.ROUND_HALF_EVEN);
            }
            if (roundingMode == 0) {
                maxStorageQuantity = ingredient.getMaxStorageQuantity().divide(ingredient.getStorageToCostCardRatio(), BigDecimal.ROUND_DOWN);
            }
            String maxStorageQuantityStr = maxStorageQuantity.toString() + ingredient.getStorageUnitName();
            ingredient.setMaxStorageQuantityStr(maxStorageQuantityStr);

            // 最小库存
            BigDecimal minStorageQuantity = new BigDecimal(0);
            if (roundingMode == 1) {
                minStorageQuantity = ingredient.getMinStorageQuantity().divide(ingredient.getStorageToCostCardRatio(), BigDecimal.ROUND_HALF_EVEN);
            }
            if (roundingMode == 0) {
                minStorageQuantity = ingredient.getMinStorageQuantity().divide(ingredient.getStorageToCostCardRatio(), BigDecimal.ROUND_DOWN);
            }
            String minStorageQuantityStr = minStorageQuantity.toString() + ingredient.getStorageUnitName();
            ingredient.setMinStorageQuantityStr(minStorageQuantityStr);

            // 总数量
            BigDecimal totalStockInQuantityStr = new BigDecimal(0);
            if (roundingMode == 1) {
                totalStockInQuantityStr = ingredient.getTotalQuantity().divide(ingredient.getTotalQuantity(), BigDecimal.ROUND_HALF_EVEN);
            }
            if (roundingMode == 0) {
                totalStockInQuantityStr = ingredient.getTotalQuantity().divide(ingredient.getTotalQuantity(), BigDecimal.ROUND_DOWN);
            }
            String totalQuantityStr = totalStockInQuantityStr.toString() + ingredient.getStorageUnitName();
            ingredient.setTotalQuantityStr(totalQuantityStr);

            //数量
            BigDecimal realStockInQuantityStr = new BigDecimal(0);
            if (roundingMode == 1) {
                realStockInQuantityStr = ingredient.getRealQuantity().divide(ingredient.getStorageToCostCardRatio(), BigDecimal.ROUND_HALF_EVEN);
            }
            if (roundingMode == 0) {
                realStockInQuantityStr = ingredient.getRealQuantity().divide(ingredient.getStorageToCostCardRatio(), BigDecimal.ROUND_DOWN);
            }
            String realQuantityStr = realStockInQuantityStr.toString() + ingredient.getStorageUnitName();
            ingredient.setRealQuantityStr(realQuantityStr);
        }
    }

    @Override
    public boolean checkIsCanUpdate(int id) throws SSException {
        // 遍历所有成卡和单据,查询该原配料是被添加
        try {
            List<CostCardDto> costCardDtoList = costCardService.listAllCostCardDto();
            // map中的key为原配料的id，value为原配料存在状态0或者空代表没有，1代表有
            Map<Integer,Integer> checkMap = new HashMap<Integer, Integer>();
            List<StorageItem> storageItemList = storageItemService.listAll();
            for (StorageItem storageItem : storageItemList){
                if (storageItem.getIngredientId().equals(id)){
                    return false;
                }
            }
            for (CostCardDto costCardDto : costCardDtoList){
                List<CostCardItemDto> costCardItemDtoList = costCardDto.getCostCardItemDtos();
                for (CostCardItemDto costCardItemDto : costCardItemDtoList){
                    checkMap.put(costCardItemDto.getIngredientId(),1);
                }
            }
            List<StorageReportDto> storageReportDtoList = storageReportService.listReportDto();
            for (StorageReportDto storageReportDto : storageReportDtoList){
                if (storageReportDto.getStorageReport().getType() == StorageReportTypeEnum.StockInReport.getId()){
                    for (StorageReportItem storageReportItem : storageReportDto.getStorageReportItemList()){
                        StorageItem storageItem = storageItemService.queryById(storageReportItem.getItemId());
                        Ingredient ingredient = queryById(storageItem.getIngredientId());
                        checkMap.put(ingredient.getId(),1);
                    }
                } else {
                    for (StorageReportIngredient storageReportIngredient : storageReportDto.getStorageReportIngredientList()){
                        checkMap.put(storageReportIngredient.getIngredientId(),1);
                    }
                }
            }
            if (Assert.isNull(id)
                    ||Assert.lessOrEqualZero(id)){
                return false;
            }
            if (Assert.isNull(checkMap.get(id))
                    || checkMap.get(id) != 1){
                return true;
            }
            return false;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }
}
