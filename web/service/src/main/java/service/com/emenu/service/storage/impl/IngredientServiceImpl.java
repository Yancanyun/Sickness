package com.emenu.service.storage.impl;

import com.emenu.common.dto.storage.ItemAndIngredientSearchDto;
import com.emenu.common.dto.storage.StorageItemDto;
import com.emenu.common.dto.storage.StorageSupplierDto;
import com.emenu.common.entity.dish.Unit;
import com.emenu.common.entity.storage.Ingredient;
import com.emenu.common.enums.ExcelExportTemplateEnums;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.exception.PartyException;
import com.emenu.common.utils.EntityUtil;
import com.emenu.common.utils.StringUtils;
import com.emenu.mapper.storage.IngredientMapper;
import com.emenu.service.dish.UnitService;
import com.emenu.service.other.SerialNumService;
import com.emenu.service.storage.IngredientService;
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
import java.io.InputStream;
import java.io.OutputStream;
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
    public void updateIngredient(Ingredient ingredient) throws SSException {
        try {
            if (this.checkBeforeSave(ingredient)) {
                ingredient.setMaxStorageQuantity(ingredient.getMaxStorageQuantity().multiply(ingredient.getStorageToCostCardRatio()));
                ingredient.setMinStorageQuantity(ingredient.getMinStorageQuantity().multiply(ingredient.getStorageToCostCardRatio()));
                ingredientMapper.updateIngredient(ingredient);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.IngredientUpdateFailed, e);
        }
    }

    @Override
    public Ingredient queryById(int id) throws SSException {
        if (Assert.lessOrEqualZero(id)){
            return null;
        }
        Ingredient ingredient;
        try {
            ingredient = commonDao.queryById(Ingredient.class,id);
            if (Assert.isNull(ingredient)){
                return null;
            }
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
        int pageNo = searchDto.getPageNo() <= 0 ? 0 : searchDto.getPageNo()-1;
        int offset = pageNo * searchDto.getPageSize();
        searchDto.setOffset(offset);
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
            List<Ingredient> ingredientList=ingredientMapper.listBySearchDto(searchDto);
            for (Ingredient ingredient :ingredientList) {
                EntityUtil.setNullFieldDefault(ingredient);
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
            int row=2;
            for(Ingredient ingredient :ingredientList){
                //单元格居中格式
                WritableCellFormat cellFormat=new WritableCellFormat();
                cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
                cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                cellFormat.setWrap(true);
                //编号
                Label labelSupplierName = new Label(0, row, ingredient.getAssistantCode());
                labelSupplierName.setCellFormat(cellFormat);
                sheet.addCell(labelSupplierName);
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
          /*    //库存预警上限
                Label labelTotalMoney = new Label(6, row,ingredient.getTotalQuantityStr());
                labelTotalMoney.setCellFormat(cellFormat);
                sheet.addCell(labelTotalMoney);
             //库存预警下限
                Label labelTotalMoney = new Label(6, row,ingredient.getTableName());
                labelTotalMoney.setCellFormat(cellFormat);
                sheet.addCell(labelTotalMoney);
             //均价
                Label labelTotalMoney = new Label(6, row,ingredient.get);
                labelTotalMoney.setCellFormat(cellFormat);
                sheet.addCell(labelTotalMoney);
              //数量
                Label labelTotalMoney = new Label(6, row,ingredient.getCostCardUnitName());
                labelTotalMoney.setCellFormat(cellFormat);
                sheet.addCell(labelTotalMoney);

                //金额
                Label labelTotalMoney = new Label(6, row,ingredient.getCostCardUnitName());
                labelTotalMoney.setCellFormat(cellFormat);
                sheet.addCell(labelTotalMoney);

                //总数量
                Label labelTotalMoney = new Label(6, row,ingredient.getCostCardUnitName());
                labelTotalMoney.setCellFormat(cellFormat);
                sheet.addCell(labelTotalMoney);

                //总金额
                Label labelTotalMoney = new Label(6, row,ingredient.getCostCardUnitName());
                labelTotalMoney.setCellFormat(cellFormat);
                sheet.addCell(labelTotalMoney);
*/
                row++;

            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private boolean checkBeforeSave(Ingredient ingredient) throws SSException {
        if (Assert.isNull(ingredient)) {
            return false;
        }

        Assert.isNotNull(ingredient.getTagId(), EmenuException.IngredientTagIdIsNotNull);
        Assert.isNotNull(ingredient.getName(), EmenuException.IngredientNameIsNotNull);
        checkIngredientNameIsExist(ingredient.getName());
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
}
