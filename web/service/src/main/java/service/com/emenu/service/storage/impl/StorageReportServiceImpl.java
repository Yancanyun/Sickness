package com.emenu.service.storage.impl;

import com.emenu.common.dto.storage.*;
import com.emenu.common.entity.dish.Unit;
import com.emenu.common.entity.party.group.employee.Employee;
import com.emenu.common.entity.storage.*;
import com.emenu.common.enums.ExcelExportTemplateEnums;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.enums.storage.StorageReportStatusEnum;
import com.emenu.common.enums.storage.StorageReportTypeEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.exception.PartyException;
import com.emenu.common.utils.DateUtils;
import com.emenu.common.utils.EntityUtil;
import com.emenu.mapper.storage.StorageReportMapper;
import com.emenu.service.dish.UnitService;
import com.emenu.service.other.SerialNumService;
import com.emenu.service.party.group.employee.EmployeeService;
import com.emenu.service.storage.*;
import com.mchange.util.AssertException;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.common.util.IOUtil;
import com.pandawork.core.framework.dao.CommonDao;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.apache.commons.collections.bag.SynchronizedSortedBag;
import org.apache.xpath.SourceTree;
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
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * 单据操作service实现
 * @author xiaozl
 * @date 2015/11/11
 * @time 14:42
 */
@Service("storageReportService")
public class StorageReportServiceImpl implements StorageReportService {

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private StorageReportItemService storageReportItemService;

    @Autowired
    private StorageReportMapper storageReportMapper;

    @Autowired
    private StorageItemService storageItemService;

    @Autowired
    private StorageDepotService storageDepotService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SerialNumService serialNumService;

    @Autowired
    private StorageReportIngredientService storageReportIngredientService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired
    private UnitService unitService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void newReportDto(StorageReportDto reportDto) throws SSException {
        // 新的新增单据
        try {
           if (Assert.isNull(reportDto.getStorageReport().getType()) ||
                   Assert.lessOrEqualZero(reportDto.getStorageReport().getType())){
               throw SSException.get(EmenuException.ReportTypeError);
           }
           // 获取单据编号
           String serialNumber = "";
           switch (reportDto.getStorageReport().getType()){
               case 1:
                   serialNumber = serialNumService.generateSerialNum(SerialNumTemplateEnums.StockInSerialNum);
                   reportDto.getStorageReport().setSerialNumber(serialNumber);
                   break;
               case 2:
                   serialNumber = serialNumService.generateSerialNum(SerialNumTemplateEnums.StockOutSerialNum);
                   reportDto.getStorageReport().setSerialNumber(serialNumber);
                   break;
               case 3:
                   serialNumber = serialNumService.generateSerialNum(SerialNumTemplateEnums.IncomeOnSerialNum);
                   reportDto.getStorageReport().setSerialNumber(serialNumber);
                   break;
               case 4:
                   serialNumber = serialNumService.generateSerialNum(SerialNumTemplateEnums.LossOnSerialNum);
                   reportDto.getStorageReport().setSerialNumber(serialNumber);
                   break;
               default:  throw SSException.get(EmenuException.ReportTypeError);
           }

           if(Assert.isNull(reportDto.getStorageReport())){
               throw SSException.get(EmenuException.ReportIsNotNull);
           }

           if (reportDto.getStorageReport().getType() == StorageReportTypeEnum.StockInReport.getId()){
               if(Assert.isEmpty(reportDto.getStorageReportItemList()) ||Assert.lessOrEqualZero(reportDto.getStorageReportItemList().size())){
                   throw SSException.get(EmenuException.ReportItemListIsNotNull);
               }
               BigDecimal reportMoney = new BigDecimal("0.00");
               for (StorageReportItem storageReportItem : reportDto.getStorageReportItemList()){
                   reportMoney = reportMoney.add(storageReportItem.getCount());
               }
               reportDto.getStorageReport().setMoney(reportMoney);
           }
           StorageReport storageReport = this.newReport(reportDto.getStorageReport());
           if (reportDto.getStorageReport().getType() == StorageReportTypeEnum.StockInReport.getId()){
               if (Assert.isNull(reportDto.getStorageReportItemList())
                       || reportDto.getStorageReportItemList().size() == 0){
                   throw SSException.get(EmenuException.StorageReportDeatilIsNotNull);
               }
               for (StorageReportItem reportItem : reportDto.getStorageReportItemList()) {
                   reportItem.setReportId(storageReport.getId());
                   storageReportItemService.newReportItem(reportItem);
               }
           } else {
               if (Assert.isNull(reportDto.getStorageReportIngredientList())
                       || reportDto.getStorageReportIngredientList().size() == 0){
                   throw SSException.get(EmenuException.StorageReportDeatilIsNotNull);
               }
               for (StorageReportIngredient reportIngredient : reportDto.getStorageReportIngredientList()) {
                   reportIngredient.setReportId(storageReport.getId());
                   storageReportIngredientService.newReportIngredient(reportIngredient);
               }
           }

       } catch (Exception e) {
           LogClerk.errLog.error(e);
           throw SSException.get(EmenuException.InsertReportFail, e);
       }
    }

    @Override
    public List<StorageReport> listAll() throws SSException{
        // 新
        List<StorageReport> reportList = Collections.emptyList();
        try {
            reportList = storageReportMapper.listAll();
            // 设置经手人、操作人、审核人名字
            for (StorageReport storageReport : reportList){
                setReportRelatedName(storageReport);
            }
            return reportList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListReportFail, e);
        }
    }

    @Override
    public List<StorageReport> listReportBySerachDto(ReportSearchDto reportSearchDto) throws SSException {
        // 新
        List<StorageReport> reportList = Collections.emptyList();
        // 分页设置
        int pageNo = 0;
        int offset = 0;
        try {
            if (Assert.isNotNull(reportSearchDto)){
                if (Assert.isNotNull(reportSearchDto.getPageNo())) {
                    pageNo = reportSearchDto.getPageNo() <= 0 ? 0 : reportSearchDto.getPageNo() - 1;
                }
                if (Assert.isNotNull(reportSearchDto.getPageSize())) {
                    offset = pageNo * reportSearchDto.getPageSize();
                    reportSearchDto.setOffset(offset);
                }
                if (reportSearchDto.getStartTime() != null){
                    reportSearchDto.getStartTime().setHours(0);
                    reportSearchDto.getStartTime().setMinutes(0);
                    reportSearchDto.getStartTime().setSeconds(0);
                }
                if (reportSearchDto.getEndTime() != null) {
                    reportSearchDto.getEndTime().setHours(23);
                    reportSearchDto.getEndTime().setMinutes(59);
                    reportSearchDto.getEndTime().setSeconds(59);
                }
                reportList = storageReportMapper.listReportBySerachDto(reportSearchDto);
                // 设置经手人、操作人、审核人名字
                for (StorageReport storageReport : reportList){
                    setReportRelatedName(storageReport);
                }
            } else {
                throw  SSException.get(EmenuException.ReportSearchDtoError);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }

        return reportList;
    }

    @Override
    public List<StorageReportDto> listReportDtoBySerachDto(ReportSearchDto reportSearchDto) throws SSException {
        // 新
        List<StorageReport> reportList = Collections.emptyList();
        List<StorageReportDto> reportDtoList = new ArrayList<StorageReportDto>();
        try {
            reportList = listReportBySerachDto(reportSearchDto);
            if (Assert.isNull(reportList)
                    || Assert.lessOrEqualZero(reportList.size())){
                return reportDtoList;
            } else {
                for (StorageReport report : reportList){
                    if (report.getType() == StorageReportTypeEnum.StockInReport.getId()){
                        // 入库单物品详情
                        List<StorageReportItem> storageReportItemList = storageReportItemService.listByReportId(report.getId());
                        for (StorageReportItem storageReportItem : storageReportItemList){
                            StorageItem storageItem = storageItemService.queryById(storageReportItem.getItemId());
                            storageReportItem.setCostCardUnitName(storageItem.getCostCardUnitName());
                            storageReportItem.setOrderUnitName(storageItem.getOrderUnitName());
                            storageReportItem.setItemNumber(storageItem.getItemNumber());
                            storageReportItem.setItemName(storageItem.getName());
                            // 获取成卡单位数量
                            storageReportItem.setOrderQuantity(storageReportItem.getQuantity());
                            storageReportItem.setCostCardQuantity(storageReportItem.getQuantity().multiply(storageItem.getOrderToStorageRatio()).multiply(storageItem.getStorageToCostCardRatio()));
                        }
                        StorageReportDto storageReportDto = new StorageReportDto();
                        storageReportDto.setStorageReport(report);
                        storageReportDto.setStorageReportItemList(storageReportItemList);
                        reportDtoList.add(storageReportDto);
                    } else {
                        // 其他单据原配料详情
                        List<StorageReportIngredient> storageReportIngredientList = storageReportIngredientService.listByReportId(report.getId());
                        for (StorageReportIngredient storageReportIngredient : storageReportIngredientList){
                            Ingredient ingredient = ingredientService.queryById(storageReportIngredient.getIngredientId());
                            storageReportIngredient.setCostCardUnitName(ingredient.getCostCardUnitName());
                            storageReportIngredient.setStorageUnitName(ingredient.getOrderUnitName());
                            //库存单位数量
                            storageReportIngredient.setStorageQuantity(storageReportIngredient.getQuantity().divide(ingredient.getStorageToCostCardRatio(),2, BigDecimal.ROUND_HALF_EVEN));
                            //成卡单位数量
                            storageReportIngredient.setCostCardQuantity(storageReportIngredient.getQuantity());
                            storageReportIngredient.setIngredientName(ingredient.getName());
                            storageReportIngredient.setIngredientNumber(ingredient.getIngredientNumber());
                            // 获取成卡单位数量
                            storageReportIngredient.setStorageQuantity(storageReportIngredient.getQuantity().divide(ingredient.getStorageToCostCardRatio(),2, BigDecimal.ROUND_HALF_EVEN));
                        }
                        StorageReportDto storageReportDto = new StorageReportDto();
                        storageReportDto.setStorageReport(report);
                        storageReportDto.setStorageReportIngredientList(storageReportIngredientList);
                        reportDtoList.add(storageReportDto);
                    }
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return reportDtoList;
    }

    @Override
    public List<StorageReportDto> listReportDtoByTime(Date startTime, Date endTime) throws SSException {
        // 新
        List<StorageReport> reportList = Collections.emptyList();
        List<StorageReportDto> reportDtoList = new ArrayList<StorageReportDto>();
        try {
            reportList = listStorageReportByTime(startTime,endTime);
            if (Assert.isNull(reportList)
                    || Assert.lessOrEqualZero(reportList.size())){
                return reportDtoList;
            } else {
                for (StorageReport report : reportList){
                    if (report.getType() == StorageReportTypeEnum.StockInReport.getId()){
                        // 入库单物品详情
                        List<StorageReportItem> storageReportItemList = storageReportItemService.listByReportId(report.getId());
                        for (StorageReportItem storageReportItem : storageReportItemList){
                            StorageItem storageItem = storageItemService.queryById(storageReportItem.getItemId());
                            storageReportItem.setCostCardUnitName(storageItem.getCostCardUnitName());
                            storageReportItem.setOrderUnitName(storageItem.getOrderUnitName());
                            // 获取成卡单位数量
                            storageReportItem.setCostCardQuantity(storageReportItem.getQuantity().multiply(storageItem.getOrderToStorageRatio()).multiply(storageItem.getStorageToCostCardRatio()));
                        }
                        StorageReportDto storageReportDto = new StorageReportDto();
                        storageReportDto.setStorageReport(report);
                        storageReportDto.setStorageReportItemList(storageReportItemList);
                        reportDtoList.add(storageReportDto);
                    } else {
                        // 其他单据原配料详情
                        List<StorageReportIngredient> storageReportIngredientList = storageReportIngredientService.listByReportId(report.getId());
                        for (StorageReportIngredient storageReportIngredient : storageReportIngredientList){
                            Ingredient ingredient = ingredientService.queryById(storageReportIngredient.getIngredientId());
                            storageReportIngredient.setCostCardUnitName(ingredient.getCostCardUnitName());
                            storageReportIngredient.setStorageUnitName(ingredient.getOrderUnitName());
                            // 获取成卡单位数量
                            storageReportIngredient.setStorageQuantity(storageReportIngredient.getQuantity().divide(ingredient.getStorageToCostCardRatio(),2, BigDecimal.ROUND_HALF_EVEN));
                        }
                        StorageReportDto storageReportDto = new StorageReportDto();
                        storageReportDto.setStorageReport(report);
                        storageReportDto.setStorageReportIngredientList(storageReportIngredientList);
                        reportDtoList.add(storageReportDto);
                    }
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return reportDtoList;
    }

    @Override
    public List<StorageReportDto> listReportDtoByTimeAndIsAudited(Date startTime, Date endTime, int isAudited) throws SSException {
        // 新
        List<StorageReport> reportList = Collections.emptyList();
        List<StorageReportDto> reportDtoList = new ArrayList<StorageReportDto>();
        try {
            reportList = listReportByTimeAndIsAudited(startTime,endTime,isAudited);
            if (Assert.isNull(reportList)
                    || Assert.lessOrEqualZero(reportList.size())){
                return reportDtoList;
            } else {
                for (StorageReport report : reportList){
                    if (report.getType() == StorageReportTypeEnum.StockInReport.getId()){
                        // 入库单物品详情
                        List<StorageReportItem> storageReportItemList = storageReportItemService.listByReportId(report.getId());
                        for (StorageReportItem storageReportItem : storageReportItemList){
                            StorageItem storageItem = storageItemService.queryById(storageReportItem.getItemId());
                            storageReportItem.setCostCardUnitName(storageItem.getCostCardUnitName());
                            storageReportItem.setOrderUnitName(storageItem.getOrderUnitName());
                            // 获取成卡单位数量
                            storageReportItem.setCostCardQuantity(storageReportItem.getQuantity().multiply(storageItem.getOrderToStorageRatio()).multiply(storageItem.getStorageToCostCardRatio()));
                        }
                        StorageReportDto storageReportDto = new StorageReportDto();
                        storageReportDto.setStorageReport(report);
                        storageReportDto.setStorageReportItemList(storageReportItemList);
                        reportDtoList.add(storageReportDto);
                    } else {
                        // 其他单据原配料详情
                        List<StorageReportIngredient> storageReportIngredientList = storageReportIngredientService.listByReportId(report.getId());
                        for (StorageReportIngredient storageReportIngredient : storageReportIngredientList){
                            Ingredient ingredient = ingredientService.queryById(storageReportIngredient.getIngredientId());
                            storageReportIngredient.setCostCardUnitName(ingredient.getCostCardUnitName());
                            storageReportIngredient.setStorageUnitName(ingredient.getOrderUnitName());
                            // 获取成卡单位数量
                            storageReportIngredient.setStorageQuantity(storageReportIngredient.getQuantity().divide(ingredient.getStorageToCostCardRatio(),2, BigDecimal.ROUND_HALF_EVEN));
                        }
                        StorageReportDto storageReportDto = new StorageReportDto();
                        storageReportDto.setStorageReport(report);
                        storageReportDto.setStorageReportIngredientList(storageReportIngredientList);
                        reportDtoList.add(storageReportDto);
                    }
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return reportDtoList;
    }

    @Override
    public List<StorageReport> listReportByTimeAndIsAudited(Date startTime, Date endTime, int isAudited) throws SSException {
        // 新
        List<StorageReport> reportList = Collections.emptyList();
        try {
            if (Assert.isNull(endTime)){
                throw SSException.get(EmenuException.TimeIsNotNUll);
            }
            if (Assert.lessZero(isAudited)){
                throw SSException.get(EmenuException.IsAuditedIllegal);
            }
            reportList = storageReportMapper.listReportByTimeAndIsAudited(startTime,endTime,isAudited);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryReportFail, e);
        }
        return reportList;
    }

    @Override
    public List<StorageReportDto> listReportDtoByTimeAndIsAudited1(Date startTime, Date endTime, int isAudited) throws SSException {
        // 新
        List<StorageReport> reportList = Collections.emptyList();
        List<StorageReportDto> reportDtoList = new ArrayList<StorageReportDto>();
        try {
            reportList = listReportByTimeAndIsAudited1(startTime,endTime,isAudited);
            if (Assert.isNull(reportList)
                    || Assert.lessOrEqualZero(reportList.size())){
                return reportDtoList;
            } else {
                for (StorageReport report : reportList){
                    if (report.getType() == StorageReportTypeEnum.StockInReport.getId()){
                        // 入库单物品详情
                        List<StorageReportItem> storageReportItemList = storageReportItemService.listByReportId(report.getId());
                        for (StorageReportItem storageReportItem : storageReportItemList){
                            StorageItem storageItem = storageItemService.queryById(storageReportItem.getItemId());
                            storageReportItem.setCostCardUnitName(storageItem.getCostCardUnitName());
                            storageReportItem.setOrderUnitName(storageItem.getOrderUnitName());
                            // 获取成卡单位数量
                            storageReportItem.setCostCardQuantity(storageReportItem.getQuantity().multiply(storageItem.getOrderToStorageRatio()).multiply(storageItem.getStorageToCostCardRatio()));
                        }
                        StorageReportDto storageReportDto = new StorageReportDto();
                        storageReportDto.setStorageReport(report);
                        storageReportDto.setStorageReportItemList(storageReportItemList);
                        reportDtoList.add(storageReportDto);
                    } else {
                        // 其他单据原配料详情
                        List<StorageReportIngredient> storageReportIngredientList = storageReportIngredientService.listByReportId(report.getId());
                        for (StorageReportIngredient storageReportIngredient : storageReportIngredientList){
                            Ingredient ingredient = ingredientService.queryById(storageReportIngredient.getIngredientId());
                            storageReportIngredient.setCostCardUnitName(ingredient.getCostCardUnitName());
                            storageReportIngredient.setStorageUnitName(ingredient.getOrderUnitName());
                            // 获取成卡单位数量
                            storageReportIngredient.setStorageQuantity(storageReportIngredient.getQuantity().divide(ingredient.getStorageToCostCardRatio(),2, BigDecimal.ROUND_HALF_EVEN));
                        }
                        StorageReportDto storageReportDto = new StorageReportDto();
                        storageReportDto.setStorageReport(report);
                        storageReportDto.setStorageReportIngredientList(storageReportIngredientList);
                        reportDtoList.add(storageReportDto);
                    }
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return reportDtoList;
    }


    public List<StorageReport> listReportByTimeAndIsAudited1(Date startTime,Date endTime,int isAudited) throws SSException{
        // 新
        List<StorageReport> reportList = Collections.emptyList();
        try {
            if (Assert.isNull(endTime)){
                throw SSException.get(EmenuException.TimeIsNotNUll);
            }
            if (Assert.lessZero(isAudited)){
                throw SSException.get(EmenuException.IsAuditedIllegal);
            }
            reportList = storageReportMapper.listReportByTimeAndIsAudited1(startTime,endTime,isAudited);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryReportFail, e);
        }
        return reportList;
    }

    @Override
    public List<StorageReport> listStorageReportByTime(Date startTime, Date endTime) throws SSException {
        // 新
        List<StorageReport> reportList = Collections.emptyList();
        try {
            if (Assert.isNull(endTime)){
                throw SSException.get(EmenuException.TimeIsNotNUll);
            }
            reportList = storageReportMapper.listStorageReportByTime(startTime,endTime);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryReportFail, e);
        }
        return reportList;
    }

    @Override
    public StorageReportDto queryReportDtoById(int id) throws SSException {
        // 新
        try {
            if (Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.ReportIdError);
            }
            StorageReport report = queryById(id);
            if (Assert.isNull(report)){
                throw SSException.get(EmenuException.QueryReportFail);
            }
            StorageReportDto reportDto = new StorageReportDto();
            List<StorageReportItem> reportItemList = Collections.emptyList();
            List<StorageReportIngredient> reportIngredientList = Collections.emptyList();
            // 如果是入库单添加单据物品详情
            if (report.getType() == StorageReportTypeEnum.StockInReport.getId()){
                reportItemList = storageReportItemService.listByReportId(report.getId());
                for (StorageReportItem reportItem : reportItemList){
                    // 设置物品名称
                    StorageItem item = storageItemService.queryById(reportItem.getItemId());
                    if (Assert.isNull(item)){
                        throw SSException.get(EmenuException.StorageItemNotExist);
                    }
                    reportItem.setItemName(item.getName());
                    // 设置订货单位
                    Unit orderUnit = unitService.queryById(item.getStorageUnitId());
                    reportItem.setOrderUnitName(orderUnit.getName());
                    // 设置成卡单位
                    Unit costCardUnit = unitService.queryById(item.getCostCardUnitId());
                    reportItem.setCostCardUnitName(costCardUnit.getName());
                    // 设置成卡单位数量
                    BigDecimal costCardUnitQuantity =  reportItem.getQuantity().multiply(item.getOrderToStorageRatio()).multiply(item.getStorageToCostCardRatio());
                    reportItem.setCostCardQuantity(costCardUnitQuantity);
                }
            } else {
                reportIngredientList = storageReportIngredientService.listByReportId(report.getId());
                // 设置单据原配料信息
                for (StorageReportIngredient reportIngredient : reportIngredientList){
                    // 获取原配料
                    Ingredient ingredient = ingredientService.queryById(reportIngredient.getIngredientId());
                    if (Assert.isNull(ingredient)){
                        throw SSException.get(EmenuException.IngredientNotExist);
                    }
                    reportIngredient.setIngredientName(ingredient.getName());
                    // 设置成卡单位
                    Unit costCardUnit = unitService.queryById(ingredient.getCostCardUnitId());
                    reportIngredient.setCostCardUnitName(costCardUnit.getName());
                    // 设置库存单位
                    Unit storageUnit = unitService.queryById(ingredient.getStorageUnitId());
                    reportIngredient.setStorageUnitName(storageUnit.getName());
                    // 设置库存单位数量
                    BigDecimal storageQuantity = reportIngredient.getQuantity().divide(ingredient.getStorageToCostCardRatio(),2, BigDecimal.ROUND_HALF_EVEN);
                    reportIngredient.setStorageQuantity(storageQuantity);
                }
            }
            reportDto.setStorageReportItemList(reportItemList);
            reportDto.setStorageReport(report);
            return reportDto;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryReportDtoFail, e);
        }
    }



    @Override
    public StorageReport queryById(int id) throws SSException {
        // 新
        StorageReport report = null;
        try {
            if (Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.ReportIdError);
            }
            report = commonDao.queryById(StorageReport.class,id);
            if (Assert.isNotNull(report)){
                setReportRelatedName(report);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryReportFail, e);
        }
        return report;
    }

    @Override
    public boolean delReportDtoById(int id) throws SSException {
        // 新
        try {
            StorageReport report = queryById(id);
            if (report.getIsAudited() == 1){
                return false;
            }
            if (Assert.isNull(report)){
                throw SSException.get(EmenuException.ReportIsNotExist);
            }
            delById(id);
            if (report.getType() == StorageReportTypeEnum.StockInReport.getId()){
                // 入库单删除库存物品
                storageReportItemService.delByReportId(id);
            } else {
                // 其他单据删除库存原配料
                storageReportIngredientService.delByReportId(id);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelReportOrItemFail, e);
        }
        return true;
    }

    @Override
    public boolean updateIsAudited(int reportId, int isAudited) throws SSException {
        // 新
        try {
            if (Assert.lessOrEqualZero(reportId) ||
                    Assert.lessOrEqualZero(isAudited)){
                throw SSException.get(EmenuException.ReportIdOrStatusIdError);
            }
            storageReportMapper.updateIsAudited(reportId,isAudited);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
        return true;
    }

    @Override
    public boolean updateIsSettlemented(int reportId, int isSettlemented) throws SSException {
        // 新
        try {
            if (Assert.lessOrEqualZero(reportId) ||
                    Assert.lessOrEqualZero(isSettlemented)){
                throw SSException.get(EmenuException.ReportIdOrStatusIdError);
            }
            storageReportMapper.updateIsSettlemented(reportId,isSettlemented);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
        return true;
    }

    @Override
    public List<StorageReportDto> listReportDto() throws SSException {
        List<StorageReportDto> reportDtoList = new ArrayList();
        List<StorageReport> reportList = Collections.emptyList();
        try {
            //获取所有单据信息
            reportList = listAll();
            for (StorageReport report : reportList){
                {
                    if (report.getType() == StorageReportTypeEnum.StockInReport.getId()){
                        // 入库单物品详情
                        List<StorageReportItem> storageReportItemList = storageReportItemService.listByReportId(report.getId());
                        for (StorageReportItem storageReportItem : storageReportItemList){
                            StorageItem storageItem = storageItemService.queryById(storageReportItem.getItemId());
                            storageReportItem.setCostCardUnitName(storageItem.getCostCardUnitName());
                            storageReportItem.setOrderUnitName(storageItem.getOrderUnitName());
                            storageReportItem.setItemNumber(storageItem.getItemNumber());
                            storageReportItem.setItemName(storageItem.getName());
                            // 获取成卡单位数量
                            storageReportItem.setOrderQuantity(storageReportItem.getQuantity());
                            storageReportItem.setCostCardQuantity(storageReportItem.getQuantity().multiply(storageItem.getOrderToStorageRatio()).multiply(storageItem.getStorageToCostCardRatio()));
                        }
                        StorageReportDto storageReportDto = new StorageReportDto();
                        storageReportDto.setStorageReport(report);
                        storageReportDto.setStorageReportItemList(storageReportItemList);
                        reportDtoList.add(storageReportDto);
                    } else {
                        // 其他单据原配料详情
                        List<StorageReportIngredient> storageReportIngredientList = storageReportIngredientService.listByReportId(report.getId());
                        for (StorageReportIngredient storageReportIngredient : storageReportIngredientList){
                            Ingredient ingredient = ingredientService.queryById(storageReportIngredient.getIngredientId());
                            storageReportIngredient.setCostCardUnitName(ingredient.getCostCardUnitName());
                            storageReportIngredient.setStorageUnitName(ingredient.getOrderUnitName());
                            storageReportIngredient.setStorageQuantity(storageReportIngredient.getQuantity());
                            storageReportIngredient.setCostCardQuantity(storageReportIngredient.getQuantity().divide(ingredient.getStorageToCostCardRatio(),2, BigDecimal.ROUND_HALF_EVEN));
                            storageReportIngredient.setIngredientName(ingredient.getName());
                            storageReportIngredient.setIngredientNumber(ingredient.getIngredientNumber());
                            // 获取成卡单位数量
                            storageReportIngredient.setStorageQuantity(storageReportIngredient.getQuantity().divide(ingredient.getStorageToCostCardRatio(),2, BigDecimal.ROUND_HALF_EVEN));
                        }
                        StorageReportDto storageReportDto = new StorageReportDto();
                        storageReportDto.setStorageReport(report);
                        storageReportDto.setStorageReportIngredientList(storageReportIngredientList);
                        reportDtoList.add(storageReportDto);
                    }
                }
            }
            return reportDtoList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }

    @Override
    public List<StorageReportDto> listUnsettleAndAuditedStorageReportByEndTime(Date endTime) throws SSException {
        List<StorageReportDto> reportDtoList = Collections.emptyList();
        List<StorageReport> reportList = Collections.emptyList();
        try {
            if (Assert.isNull(endTime)){
                throw SSException.get(EmenuException.TimeIsNotNUll);
            }
            //获取单据信息
            reportList = storageReportMapper.listUnsettleAndAuditedStorageReportByEndTime(endTime);
            if (Assert.isEmpty(reportList)
                    || Assert.lessOrEqualZero(reportList.size())){
                return reportDtoList;
            }
            reportDtoList = new ArrayList<StorageReportDto>();
            for (StorageReport report : reportList){
                StorageReportDto reportDto = new StorageReportDto();
                if (report.getType() == StorageReportTypeEnum.StockInReport.getId()){

                    List<StorageReportItem> reportItemList = Collections.emptyList();
                    //根据单据id获取单据详情信息
                    reportItemList = storageReportItemService.listByReportId(report.getId());
                    //数据存入reportDto
                    reportDto.setStorageReport(report);
                    reportDto.setStorageReportItemList(reportItemList);
                } else {
                    List<StorageReportIngredient> reportIngredientList = Collections.emptyList();
                    //根据单据id获取单据详情信息
                    reportIngredientList = storageReportIngredientService.listByReportId(report.getId());
                    //数据存入reportDto
                    reportDto.setStorageReport(report);
                    reportDto.setStorageReportIngredientList(reportIngredientList);
                }
                reportDtoList.add(reportDto);
            }
            return reportDtoList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListReportFail, e);
        }
    }

    @Override
    public List<StorageReportDto> listReportDtoByPage(int page, int pageSize) throws SSException {
        page = page <= 0 ? 0 : page - 1;
        int offset = page * pageSize;
        List<StorageReportDto> reportDtoList = Collections.emptyList();
        List<StorageReport> reportList = Collections.emptyList();
        if(Assert.lessZero(offset)){
            return reportDtoList;
        }
        reportDtoList = new ArrayList<StorageReportDto>();
        try {
            reportList = storageReportMapper.listByPage(offset, pageSize);
            for (StorageReport report : reportList){
                StorageReportDto reportDto = new StorageReportDto();
                List<StorageReportItem> reportItemList = Collections.emptyList();
                //根据单据id获取单据详情信息
                reportItemList = storageReportItemService.listByReportId(report.getId());
                //数据存入reportDto
                reportDto.setStorageReport(report);
                reportDto.setStorageReportItemList(reportItemList);

                reportDtoList.add(reportDto);
            }
            return reportDtoList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }

    @Override
    public List<StorageReportDto> listReportDtoByCondition(StorageReport report,
                                                           int page,
                                                           int pageSize,
                                                           Date startTime,
                                                           Date endTime) throws SSException{

        page = page <= 0 ? 0 : page - 1;
        int offset = page * pageSize;

        if (endTime != null) {
            endTime.setHours(23);
            endTime.setMinutes(59);
            endTime.setSeconds(59);
        }
        List<StorageReportDto> reportDtoList = Collections.emptyList();
        List<StorageReport> reportList = Collections.emptyList();
        if(Assert.lessZero(offset)){
            return reportDtoList;
        }
        reportDtoList = new ArrayList<StorageReportDto>();
        try {
            if (Assert.isNull(report)){
                throw SSException.get(EmenuException.ReportIsNotNull);
            }
            reportList = storageReportMapper.listReportByCondition(report, offset, pageSize, startTime, endTime);

            for (StorageReport storageReport : reportList) {
                StorageReportDto reportDto = new StorageReportDto();
                List<StorageReportItem> reportItemList = new ArrayList();
                //根据单据id获取单据详情信息
                reportItemList = storageReportItemService.listByReportId(storageReport.getId());
                //数据存入reportDto
                reportDto.setStorageReport(storageReport);
                reportDto.setStorageReportItemList(reportItemList);
                reportDtoList.add(reportDto);
            }
            return reportDtoList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListReportFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateById(StorageReport report) throws SSException {
        try {
            if(Assert.isNull(report)){
                throw SSException.get(EmenuException.ReportIsNotNull);
              }
            if (Assert.isNull(report.getId())&&Assert.lessOrEqualZero(report.getId())){
                throw SSException.get(EmenuException.ReportIdError);
            }
            commonDao.update(report);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateStorageReportFail, e);
        }
    }

    @Override
    public void updateIsSettlementedById(int id, StorageReportStatusEnum storageReportStatusEnum) throws SSException {
        try {
            storageReportMapper.updateIsSettlementedById(id, storageReportStatusEnum.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateStorageReportFail, e);
        }
    }



    @Override
    public List<StorageReportDto> listReportDtoByCondition(Date startTime,
                                                           Date endTime,
                                                           List<Integer> depotIdList,
                                                           List<Integer> tagIdList) throws SSException {

        if (endTime != null) {
            endTime.setHours(23);
            endTime.setMinutes(59);
            endTime.setSeconds(59);
        }
        List<StorageReportDto> storageReportDtoList =  Collections.emptyList();
        List<StorageReport> storageReportList = Collections.emptyList();
        try {
            //根据tagId获取分类下的物品
            ItemAndIngredientSearchDto searchDto = new ItemAndIngredientSearchDto();
            searchDto.setTagIdList(tagIdList);

            List<StorageItem> storageItemList = Collections.emptyList();
            storageItemList = storageItemService.listBySearchDto(searchDto);

            if(Assert.isEmpty(storageItemList)){
                return storageReportDtoList;
            }
            storageReportDtoList = new ArrayList();

            //获取原料idList
            List<Integer> itemIdList = new ArrayList<Integer>();
            for(StorageItem storageItem : storageItemList){
                itemIdList.add(storageItem.getId());
            }
            storageReportList = storageReportMapper.listByDepotIdList(startTime, endTime, depotIdList);
            for (StorageReport storageReport : storageReportList) {
                StorageReportDto storageReportDto = new StorageReportDto();
                List<StorageReportItem> storageReportItemList = new ArrayList();
                //根据单据id获取单据详情信息
                storageReportItemList = storageReportItemService.listByReportIdAndItemIdList(storageReport.getId(), itemIdList);
                //数据存入reportDto
                storageReportDto.setStorageReport(storageReport);
                storageReportDto.setStorageReportItemList(storageReportItemList);

                storageReportDtoList.add(storageReportDto);
            }
            return storageReportDtoList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListReportFail, e);
        }
    }

    @Override
    public List<StorageReportDto> listReportDtoByCondition1(StorageReport report, Integer page, Integer pageSize, List<Integer> depotIdList, Date startTime, Date endTime) throws SSException {

        Integer offset = null;
        if (Assert.isNotNull(page) && Assert.isNotNull(pageSize)) {
            page = page <= 0 ? 0 : page - 1;
            offset = page * pageSize;
        }

        if (endTime != null) {
            endTime.setHours(23);
            endTime.setMinutes(59);
            endTime.setSeconds(59);
        }
        List<StorageReportDto> reportDtoList = Collections.emptyList();
        List<StorageReport> reportList = Collections.emptyList();
        if (Assert.isNotNull(offset)&&Assert.lessZero(offset)){
            return reportDtoList;
        }
        reportDtoList = new ArrayList<StorageReportDto>();
        try {
            if (Assert.isNull(report)){
                throw SSException.get(EmenuException.ReportIsNotNull);
            }
            reportList = storageReportMapper.listReportByCondition1(report, offset, pageSize, depotIdList, startTime, endTime);
            for (StorageReport storageReport : reportList) {
                StorageReportDto reportDto = new StorageReportDto();
                List<StorageReportItemDto> reportItemDtoList = new ArrayList();
                //根据单据id获取单据详情信息

                reportItemDtoList = storageReportItemService.listDtoByReportId(storageReport.getId());
                //数据存入reportDto
                reportDto.setStorageReport(storageReport);
                // reportDto.setStorageReportItemDtoList(reportItemDtoList);
                reportDtoList.add(reportDto);
            }
            return reportDtoList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListReportFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateReportDto(StorageReportDto reportDto) throws SSException {
        try {
            if (Assert.isNull(reportDto)){
                throw SSException.get(EmenuException.ReportDtoIsNotNull);
            }
            if (Assert.isNull(reportDto.getStorageReport())){
                throw SSException.get(EmenuException.ReportIsNotNull);
            }
            // 考虑单据类型,单据详情不能为空，大小不能为0
            if (reportDto.getStorageReport().getType() == StorageReportTypeEnum.StockInReport.getId()){
                if (Assert.isEmpty(reportDto.getStorageReportItemList())
                        || Assert.lessOrEqualZero(reportDto.getStorageReportItemList().size())){
                    throw SSException.get(EmenuException.StorageReportDeatilIsNotNull);
                }
            } else {
                if (Assert.isNull(reportDto.getStorageReportIngredientList())
                        || Assert.lessOrEqualZero(reportDto.getStorageReportIngredientList().size())){
                    throw SSException.get(EmenuException.StorageReportDeatilIsNotNull);
                }
            }

            //更新后的单据
            StorageReport reportNew = reportDto.getStorageReport();
            //数据库中原始未修改的单据
            StorageReportDto reportDtoOld = queryReportDtoById(reportNew.getId());
            if (reportNew.getType() == StorageReportTypeEnum.StockInReport.getId()){
                BigDecimal reportMoney = new BigDecimal("0.00");
                for (StorageReportItem storageReportItem : reportDto.getStorageReportItemList()){
                    reportMoney = reportMoney.add(storageReportItem.getCount());
                }
                reportNew.setMoney(reportMoney);
            }

            //更新单据
            updateById(reportNew);
            if (reportNew.getType() == StorageReportTypeEnum.StockInReport.getId()) {
                List<StorageReportItem> reportItemListNew = reportDto.getStorageReportItemList();
                List<StorageReportItem> reportItemListOld = reportDtoOld.getStorageReportItemList();
                //判断更新后的单据是否存在数据
                if (reportItemListNew.size() > 0) {
                    //判断以前的单据是否存在数据，若不存在，则直接到下一个if处添加物品
                    if (reportItemListOld.size() > 0) {
                        //判断新单据和老单据中每条物品id是否一致，若一致，则修改老单据的相应物品
                        for (StorageReportItem itemOld : reportItemListOld) {
                            for (StorageReportItem itemNew : reportItemListNew) {
                                if (!Assert.isNull(itemNew.getId()) && itemOld.getId().equals(itemNew.getId())) {
                                    itemNew.setReportId(reportNew.getId());
                                    storageReportItemService.updateById(itemNew);
                                    //删除新单据中已修改的物品，最后剩下的物品为老单据中没有的，即需要被添加的物品
                                    reportItemListNew.remove(itemNew);
                                    //删除老单据中已修改的物品，最后剩下的物品为新单据中没有的，即需要被删掉的物品
                                    reportItemListOld.remove(itemOld);
                                }
                            }
                        }
                        //删除物品
                        if (reportItemListOld.size() > 0) {
                            for (StorageReportItem itemOld : reportItemListOld) {
                                storageReportItemService.delById(itemOld.getId());
                            }
                        }
                    }
                    //添加物品
                    if (reportItemListNew.size() > 0) {
                        for (StorageReportItem itemNew : reportItemListNew) {
                            itemNew.setReportId(reportNew.getId());
                            storageReportItemService.newReportItem(itemNew);
                        }
                    }
                }
            } else {
                List<StorageReportIngredient> reportIngredientListNew = reportDto.getStorageReportIngredientList();
                List<StorageReportIngredient> reportIngredientListOld = reportDtoOld.getStorageReportIngredientList();
                //判断更新后的单据是否存在数据
                if (reportIngredientListNew.size() > 0) {
                    //判断以前的单据是否存在数据，若不存在，则直接到下一个if处添加物品
                    if (reportIngredientListOld.size() > 0) {
                        //判断新单据和老单据中每条物品id是否一致，若一致，则修改老单据的相应物品
                        for (StorageReportIngredient reportIngredientOld : reportIngredientListOld) {
                            for (StorageReportIngredient reportIngredientNew : reportIngredientListNew) {
                                if (!Assert.isNull(reportIngredientNew.getId()) && reportIngredientOld.getId().equals(reportIngredientNew.getId())) {
                                    reportIngredientNew.setReportId(reportNew.getId());
                                    storageReportIngredientService.updateById(reportIngredientNew);
                                    //删除新单据中已修改的物品，最后剩下的物品为老单据中没有的，即需要被添加的物品
                                    reportIngredientListNew.remove(reportIngredientNew);
                                    //删除老单据中已修改的物品，最后剩下的物品为新单据中没有的，即需要被删掉的物品
                                    reportIngredientListOld.remove(reportIngredientOld);
                                }
                            }
                        }
                        //删除物品
                        if (reportIngredientListOld.size() > 0) {
                            for (StorageReportIngredient reportIngredientOld : reportIngredientListOld) {
                                storageReportIngredientService.delById(reportIngredientOld.getId());
                            }
                        }
                    }
                    //添加物品
                    if (reportIngredientListNew.size() > 0) {
                        for (StorageReportIngredient reportIngredientNew : reportIngredientListNew) {
                            reportIngredientNew.setReportId(reportNew.getId());
                            storageReportIngredientService.newReportIngredient(reportIngredientNew);
                        }
                    }
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateStorageReportFail, e);
        }
    }

    @Override
    public int count() throws SSException {
        Integer count = 0;
        try {
            count = storageReportMapper.count();
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CountReportFail, e);
        }
        return count == null ? 0 : count;
    }

    @Override
    public int countByReportSerachDto(ReportSearchDto reportSearchDto) throws SSException {
        // 新
//        List<StorageReport> reportList = Collections.emptyList();
//
//        try {
//            if (reportSearchDto.getEndTime() != null) {
//                reportSearchDto.getEndTime().setHours(23);
//                reportSearchDto.getEndTime().setMinutes(59);
//                reportSearchDto.getEndTime().setSeconds(59);
//            }
//            reportList = listReportBySerachDto(reportSearchDto);
//        } catch (Exception e) {
//            LogClerk.errLog.error(e);
//            throw SSException.get(PartyException.SystemException, e);
//        }
        Integer count = 0;
        try {
            if (reportSearchDto.getStartTime() != null){
                reportSearchDto.getStartTime().setHours(0);
                reportSearchDto.getStartTime().setMinutes(0);
                reportSearchDto.getStartTime().setSeconds(0);
            }
            if (reportSearchDto.getEndTime() != null) {
                reportSearchDto.getEndTime().setHours(23);
                reportSearchDto.getEndTime().setMinutes(59);
                reportSearchDto.getEndTime().setSeconds(59);
            }
            count = storageReportMapper.countByReportSerachDto(reportSearchDto);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CountReportFail, e);
        }
        return count == null ? 0 : count;
    }


    /** -------------------------------------------私有方法------------------------------------------------------------- */

    /**
     * 设置经手人、操作人、审核人名字
     * @param storageReport
     * @throws SSException
     */
    private void setReportRelatedName(StorageReport storageReport) throws SSException{
        // 设置经手人、操作人、审核人名字
        Employee createdEmployee = employeeService.queryByPartyId(storageReport.getCreatedPartyId());
        if (Assert.isNull(createdEmployee)){
            throw SSException.get(EmenuException.SystemException);
        }
        storageReport.setCreatedName(createdEmployee.getName());

        Employee auditEmployee = employeeService.queryByPartyId(storageReport.getAuditPartyId());
        if (Assert.isNull(auditEmployee)){
            storageReport.setAuditName("");
        } else {
            storageReport.setAuditName(auditEmployee.getName());
        }
        Employee handlerEmployee = employeeService.queryByPartyId(storageReport.getHandlerPartyId());
        if (Assert.isNull(handlerEmployee)){
            throw SSException.get(EmenuException.SystemException);
        } else {
            storageReport.setHandlerName(handlerEmployee.getName());
        }
    }

    /**
     * 添加单据
     * @param storageReport
     * @return
     * @throws SSException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    private StorageReport newReport(StorageReport storageReport) throws SSException {

        try {
            if(Assert.isNull(storageReport)){
                throw SSException.get(EmenuException.ReportIsNotNull);
            }
            //关键字段不为空的情况下添加单据
            if(checkStorageReportBeforeSave(storageReport)){
                return commonDao.insert(storageReport);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertReportFail, e);
        }

        return null;
    }

    /**
     * 新
     * 检查StorageReport中关键属性是否合法 新
     * @param storageReport
     * @return
     * @throws SSException
     */
    private boolean checkStorageReportBeforeSave(StorageReport storageReport) throws SSException{

        if (Assert.isNull(storageReport)) {
            return false;
        }
        // 操作人
        if (Assert.isNull(storageReport.getCreatedPartyId()) || Assert.lessOrEqualZero(storageReport.getCreatedPartyId())){
            throw SSException.get(EmenuException.CreatedPartyIdError);
        }
        // 经手人
        if (Assert.isNull(storageReport.getHandlerPartyId()) || Assert.lessOrEqualZero(storageReport.getHandlerPartyId())){
            throw SSException.get(EmenuException.HandlerPartyId);
        }
        // 单据类型
        if (Assert.isNull(storageReport.getType())
                && Assert.lessOrEqualZero(storageReport.getType())){
            throw SSException.get(EmenuException.ReportTypeError);
        }
        // 入库单存放点
        if (storageReport.getType() == StorageReportTypeEnum.StockInReport.getId()){
            if (Assert.isNull(storageReport.getDepotId())
                    || Assert.lessOrEqualZero(storageReport.getDepotId())){
                throw SSException.get(EmenuException.DepotIdError);
            }
        }
        // 单据编号
        Assert.isNotNull(storageReport.getSerialNumber(), EmenuException.SerialNumberError);
        // 单据金额
        Assert.isNotNull(storageReport.getMoney(), EmenuException.ReportMoneyError);
        return true;
    }

    /**
     * 新
     * 根据单据id删除单据
     * @param id
     * @return
     * @throws SSException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    private boolean delById(int id) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.ReportIdError);
            }
            storageReportMapper.delById(id);
            return true;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelReportOrItemFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void exportToExcel(StorageReport report,Date startTime, Date endTime,List<Integer> deports,
                              Integer handlerPartyId, Integer createdPartyId, HttpServletResponse response) throws SSException{
        OutputStream os = null;
        List<StorageReportDto> storageReportDtoList = Collections.emptyList();
        try{
            //从数据库中取数据
            ReportSearchDto reportSearchDto = new ReportSearchDto();
            reportSearchDto.setAuditPartyId(report.getAuditPartyId());
            reportSearchDto.setCreatedPartyId(createdPartyId);
            reportSearchDto.setDepotId(report.getDepotId());
            reportSearchDto.setEndTime(endTime);
            reportSearchDto.setHandlerPartyId(handlerPartyId);
            reportSearchDto.setIsAudited(report.getIsAudited());
            reportSearchDto.setIsSettlemented(report.getIsSettlemented());
            reportSearchDto.setStartTime(startTime);
            storageReportDtoList = this.listReportDtoBySerachDto(reportSearchDto);
            System.out.println("xiao");
            //storageReportDtoList = this.listReportDtoByCondition1(report,null,null,deports,startTime,endTime);
            for (StorageReportDto storageReportDto : storageReportDtoList){
                EntityUtil.setNullFieldDefault(storageReportDto);
            }
            //设置输出流
            //设置excel文件名和sheetName
            String filename = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            filename = ExcelExportTemplateEnums.AdminStorageReportList.getName()+sdf.format(new Date());
            response.setContentType("application/msexcel");
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(filename.getBytes("gbk"), "ISO8859-1") + ".xls");
            os = response.getOutputStream();
            //获取模板
            InputStream tplStream = IOUtil.getFileAsStream(ExcelExportTemplateEnums.AdminStorageReportList.getFilePath());
            Workbook tplWorkBook = Workbook.getWorkbook(tplStream);
            WritableWorkbook outBook = Workbook.createWorkbook(os,tplWorkBook);
            //获取sheet往sheet中写入数据
            WritableSheet sheet = outBook.getSheet(0);
            int row = 3;
            int rowchildren = 0;
            int up = 3;
            for (StorageReportDto storageReportDto : storageReportDtoList) {
                //类型
                Label labelReportType = new Label(0, row + rowchildren, StorageReportTypeEnum.valueOf(storageReportDto.getStorageReport().getType()).getName());
                sheet.addCell(labelReportType);
                // 单据编号
                Label labelSerialNumber = new Label(1, row + rowchildren, storageReportDto.getStorageReport().getSerialNumber());
                sheet.addCell(labelSerialNumber);
                // 存放点
                String deportName = null;
                if (storageReportDto.getStorageReport().getDepotId() != 0) {
                    deportName = storageDepotService.queryById(storageReportDto.getStorageReport().getDepotId()).getName();
                } else {
                    deportName = "无";
                }
                Label labelDepot = new Label(2, row + rowchildren, deportName);
                sheet.addCell(labelDepot);
                // 经手人
                String handlerName = employeeService.queryByPartyId(storageReportDto.getStorageReport().getHandlerPartyId()).getName();
                Label labelHandlerName = new Label(3, row + rowchildren, handlerName);
                sheet.addCell(labelHandlerName);
                // 操作人
                String createdName = employeeService.queryByPartyId(storageReportDto.getStorageReport().getCreatedPartyId()).getName();
                Label labelCreatedName = new Label(4, row + rowchildren, createdName);
                sheet.addCell(labelCreatedName);
                //审核人
                String auditName = null;
                if (storageReportDto.getStorageReport().getAuditPartyId() != 0) {
                    auditName = employeeService.queryByPartyId(storageReportDto.getStorageReport().getAuditPartyId()).getName();
                } else {
                    auditName = "无";
                }
                Label labelAuditName = new Label(5, row + rowchildren, auditName);
                sheet.addCell(labelAuditName);
                // 总金额
                Label labelMoney = new Label(6, row + rowchildren, storageReportDto.getStorageReport().getMoney().toString());
                sheet.addCell(labelMoney);
                // 单据备注
                String comment = ("".equals(storageReportDto.getStorageReport().getComment())?"无":storageReportDto.getStorageReport().getComment());
                Label labelReportComment = new Label(7, row + rowchildren, comment);
                sheet.addCell(labelReportComment);
                // 日期
                String createdTime = DateUtils.yearMonthDayFormat(storageReportDto.getStorageReport().getCreatedTime());
                Label labelCreatedTime = new Label(8, row + rowchildren, createdTime);
                sheet.addCell(labelCreatedTime);
                // 物品列表
                List<StorageReportItem> storageItemList = storageReportDto.getStorageReportItemList();
                List<StorageReportIngredient> storageReportIngredientList = storageReportDto.getStorageReportIngredientList();
                if ("入库单".equals(StorageReportTypeEnum.valueOf(storageReportDto.getStorageReport().getType()).getName())) {
                    for (StorageReportItem storageItem : storageItemList) {

                        // 物品名称
                        Label labelItemName = new Label(9, row + rowchildren, storageItem.getItemName());
                        sheet.addCell(labelItemName);
                        // 物品编号
                        String itemNumber = null;
                        if (storageItem.getItemId() != null &&
                                !"".equals(storageItem.getItemId()) &&
                                (storageItemService.queryById(storageItem.getItemId())) != null) {
                            itemNumber = storageItemService.queryById(storageItem.getItemId()).getItemNumber();
                        } else {
                            itemNumber = "无";
                        }
                        Label labelItemNumber = new Label(10, row + rowchildren, itemNumber);
                        sheet.addCell(labelItemNumber);

                        // 物品数量(库存订货)
                        Label labelQuantity = new Label(11, row + rowchildren, storageItem.getQuantity().toString());
                        sheet.addCell(labelQuantity);

                        // 物品单位(库存订货)
                        String unit = storageItem.getOrderUnitName();
                        Label labelUnit = new Label(12, row + rowchildren, unit);
                        sheet.addCell(labelUnit);

                        // 数量(成本卡)
                        String costCardQuantity = (storageItem.getCostCardQuantity() == null ? "无" : storageItem.getCostCardQuantity().toString());
                        Label label13 = new Label(13, row + rowchildren, costCardQuantity);
                        sheet.addCell(label13);
                        // 单位(成本卡)
                        Label label14 = new Label(14, row + rowchildren, storageItem.getCostCardUnitName());
                        sheet.addCell(label14);
                        // 物品价格
                        Label labelPrice = new Label(15, row + rowchildren, storageItem.getPrice().toString());
                        sheet.addCell(labelPrice);

                        //备注
                        String storageItemComment = ("".equals(storageItem.getComment())?"无":storageItem.getComment());
                        Label labelItemComment = new Label(16, row + rowchildren, storageItemComment);
                        sheet.addCell(labelItemComment);
                        rowchildren++;
                    }
                } else {
                    for (StorageReportIngredient storageIngredient : storageReportIngredientList) {

                        // 物品名称
                        Label labelItemName = new Label(9, row + rowchildren, storageIngredient.getIngredientName());
                        sheet.addCell(labelItemName);
                        // 物品编号
                        String itemNumber = storageIngredient.getIngredientNumber();
                        Label labelItemNumber = new Label(10, row + rowchildren, itemNumber);
                        sheet.addCell(labelItemNumber);

                        // 物品数量(库存订货)
                        Label labelQuantity = new Label(11, row + rowchildren, storageIngredient.getQuantity().toString());
                        sheet.addCell(labelQuantity);

                        // 物品单位(库存订货)
                        String unit = storageIngredient.getStorageUnitName();
                        Label labelUnit = new Label(12, row + rowchildren, unit);
                        sheet.addCell(labelUnit);

                        // 数量(成本卡)storageItemDto.getCostCardQuantity().toString()
                        String costCardQuantity = (storageIngredient.getCostCardQuantity() == null ? "无" : storageIngredient.getCostCardQuantity().toString());
                        Label label13 = new Label(13, row + rowchildren, costCardQuantity);
                        sheet.addCell(label13);

                        // 单位(成本卡)
                        Label label14 = new Label(14, row + rowchildren, storageIngredient.getCostCardUnitName());
                        sheet.addCell(label14);

                        // 物品价格
                        Label labelPrice = new Label(15, row + rowchildren, "无");
                        sheet.addCell(labelPrice);

                        //备注
                        String storageIngredientComment = ("".equals(storageIngredient.getComment())?"无":storageIngredient.getComment());
                        Label labelItemComment = new Label(16, row + rowchildren, storageIngredientComment);
                        sheet.addCell(labelItemComment);
                        rowchildren++;
                    }
                }

                // 合并单元格

                if((storageItemList==null&&storageReportIngredientList.size()>=1)){
                    sheet.mergeCells(0, up , 0, up+storageReportIngredientList.size()-1 );
                    sheet.mergeCells(1, up, 1, up+storageReportIngredientList.size()-1);
                    sheet.mergeCells(2, up, 2, up+storageReportIngredientList.size()-1);
                    sheet.mergeCells(3, up, 3, up+storageReportIngredientList.size()-1);
                    sheet.mergeCells(4, up, 4, up+storageReportIngredientList.size()-1);
                    sheet.mergeCells(5, up, 5, up+storageReportIngredientList.size()-1);
                    sheet.mergeCells(6, up, 6, up+storageReportIngredientList.size()-1);
                    sheet.mergeCells(7, up, 7, up+storageReportIngredientList.size()-1);
                    sheet.mergeCells(8, up, 8, up+storageReportIngredientList.size()-1);
                    up = up+storageReportIngredientList.size();
                    row++;
                    rowchildren--;
                }else if((storageItemList.size()>=1&&storageReportIngredientList == null)){
                    sheet.mergeCells(0, up , 0, up + storageItemList.size()-1);
                    sheet.mergeCells(1, up, 1, up + storageItemList.size()-1);
                    sheet.mergeCells(2, up, 2, up + storageItemList.size()-1);
                    sheet.mergeCells(3, up, 3, up + storageItemList.size()-1);
                    sheet.mergeCells(4, up, 4, up + storageItemList.size()-1);
                    sheet.mergeCells(5, up, 5, up + storageItemList.size()-1);
                    sheet.mergeCells(6, up, 6, up + storageItemList.size()-1);
                    sheet.mergeCells(7, up, 7, up + storageItemList.size()-1);
                    sheet.mergeCells(8, up, 8, up + storageItemList.size()-1);
                    up = up + storageItemList.size();
                    row++;
                    rowchildren--;
                }else{
                    row++;
                    rowchildren--;
                }
            }

                /*if((storageItemList==null&&storageReportIngredientList.size()>=1)||
                        (storageItemList.size()>=1&&storageReportIngredientList == null)){
                    sheet.mergeCells(0, row , 0, row + rowchildren-1);
                    sheet.mergeCells(1, row, 1, row + rowchildren-1);
                    sheet.mergeCells(2, row, 2, row + rowchildren-1);
                    sheet.mergeCells(3, row, 3, row + rowchildren-1);
                    sheet.mergeCells(4, row, 4, row + rowchildren-1);
                    sheet.mergeCells(5, row, 5, row + rowchildren-1);
                    sheet.mergeCells(6, row, 6, row + rowchildren-1);
                    sheet.mergeCells(7, row, 7, row + rowchildren-1);
                    sheet.mergeCells(8, row, 8, row + rowchildren-1);

            }
            grow = rowchildren;
            up = row+rowchildren;
            row++;*/


                /*// 单元格合并函数
                if(storageItemList.size()>1||){
                    sheet.mergeCells(0, row, 0, row + rowchildren-1);
                    sheet.mergeCells(1, row, 1, row + rowchildren-1);
                    sheet.mergeCells(2, row, 2, row + rowchildren-1);
                    sheet.mergeCells(3, row, 3, row + rowchildren-1);
                    sheet.mergeCells(4, row, 4, row + rowchildren-1);
                    sheet.mergeCells(5, row, 5, row + rowchildren-1);
                    sheet.mergeCells(6, row, 6, row + rowchildren-1);
                    sheet.mergeCells(7, row, 7, row + rowchildren-1);
                }
                if(rowchildren<=1){
                    row ++;
                }else{
                    row = row + rowchildren;
                }*/


            outBook.write();
            outBook.close();
            tplWorkBook.close();
            tplStream.close();
            os.close();
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            response.setContentType("text/html");
            response.setHeader("Content-Type", "text/html");
            response.setHeader("Content-disposition", "");
            response.setCharacterEncoding("UTF-8");
            try {
                String eMsg = "系统内部异常，请联系管理员！";
                eMsg= java.net.URLEncoder.encode(eMsg.toString(),"UTF-8");
                response.sendRedirect("/admin/storage/report?eMsg="+eMsg);
                os.close();
            } catch (IOException e1) {
                LogClerk.errLog.error(e1.getMessage());
            }
            throw SSException.get(EmenuException.ExportReportFail, e);
        }
        finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    LogClerk.errLog.error(e);
                    throw SSException.get(EmenuException.ExportReportFail, e);
                }
            }
        }
    }

    //@Override
    @Transactional(rollbackFor = {Exception.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void exportToExcelAll(HttpServletResponse response)throws SSException{
        OutputStream os = null;
        try {
            List<StorageReportDto> storageReportDtoList = listReportDto();
            //设置输出流
            //设置excel文件名和sheetName
            String filename = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            filename = ExcelExportTemplateEnums.AdminStorageReportList.getName() + sdf.format(new Date());
            response.setContentType("application/msexcel");
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(filename.getBytes("gbk"), "ISO8859-1") + ".xls");
            os = response.getOutputStream();
            //获取模板
            InputStream tplStream = IOUtil.getFileAsStream(ExcelExportTemplateEnums.AdminStorageReportList.getFilePath());
            Workbook tplWorkBook = Workbook.getWorkbook(tplStream);
            WritableWorkbook outBook = Workbook.createWorkbook(os, tplWorkBook);
            //获取sheet往sheet中写入数据
            WritableSheet sheet = outBook.getSheet(0);
            int row = 3;
            int rowchildren = 0;
            int up = 3;
            for (StorageReportDto storageReportDto : storageReportDtoList) {
                //类型
                Label labelReportType = new Label(0, row + rowchildren, StorageReportTypeEnum.valueOf(storageReportDto.getStorageReport().getType()).getName());
                sheet.addCell(labelReportType);
                // 单据编号
                Label labelSerialNumber = new Label(1, row + rowchildren, storageReportDto.getStorageReport().getSerialNumber());
                sheet.addCell(labelSerialNumber);
                // 存放点
                String deportName = null;
                if (storageReportDto.getStorageReport().getDepotId() != 0) {
                    deportName = storageDepotService.queryById(storageReportDto.getStorageReport().getDepotId()).getName();
                } else {
                    deportName = "无存放点";
                }
                Label labelDepot = new Label(2, row + rowchildren, deportName);
                sheet.addCell(labelDepot);
                // 经手人
                String handlerName = employeeService.queryByPartyId(storageReportDto.getStorageReport().getHandlerPartyId()).getName();
                Label labelHandlerName = new Label(3, row + rowchildren, handlerName);
                sheet.addCell(labelHandlerName);
                // 操作人
                String createdName = employeeService.queryByPartyId(storageReportDto.getStorageReport().getCreatedPartyId()).getName();
                Label labelCreatedName = new Label(4, row + rowchildren, createdName);
                sheet.addCell(labelCreatedName);
                //审核人
                String auditName = null;
                if (storageReportDto.getStorageReport().getAuditPartyId() != 0) {
                    auditName = employeeService.queryByPartyId(storageReportDto.getStorageReport().getAuditPartyId()).getName();
                } else {
                    auditName = "暂无";
                }
                Label labelAuditName = new Label(5, row + rowchildren, auditName);
                sheet.addCell(labelAuditName);
                // 总金额
                Label labelMoney = new Label(6, row + rowchildren, storageReportDto.getStorageReport().getMoney().toString());
                sheet.addCell(labelMoney);
                // 单据备注
                Label labelReportComment = new Label(7, row + rowchildren, storageReportDto.getStorageReport().getComment());
                sheet.addCell(labelReportComment);
                // 日期
                String createdTime = DateUtils.yearMonthDayFormat(storageReportDto.getStorageReport().getCreatedTime());
                Label labelCreatedTime = new Label(8, row + rowchildren, createdTime);
                sheet.addCell(labelCreatedTime);
                // 物品列表
                List<StorageReportItem> storageItemList = storageReportDto.getStorageReportItemList();
                List<StorageReportIngredient> storageReportIngredientList = storageReportDto.getStorageReportIngredientList();
                if ("入库单".equals(StorageReportTypeEnum.valueOf(storageReportDto.getStorageReport().getType()).getName())) {
                    for (StorageReportItem storageItem : storageItemList) {

                        // 物品名称
                        Label labelItemName = new Label(9, row + rowchildren, storageItem.getItemName());
                        sheet.addCell(labelItemName);
                        // 物品编号
                        String itemNumber = null;
                        if (storageItem.getItemId() != null &&
                                !"".equals(storageItem.getItemId()) &&
                                (storageItemService.queryById(storageItem.getItemId())) != null) {
                            itemNumber = storageItemService.queryById(storageItem.getItemId()).getItemNumber();
                        } else {
                            itemNumber = "无物品编号";
                        }
                        Label labelItemNumber = new Label(10, row + rowchildren, itemNumber);
                        sheet.addCell(labelItemNumber);

                        // 物品数量(库存订货)
                        Label labelQuantity = new Label(11, row + rowchildren, storageItem.getQuantity().toString());
                        sheet.addCell(labelQuantity);

                        // 物品单位(库存订货)
                        String unit = storageItem.getOrderUnitName();
                        Label labelUnit = new Label(12, row + rowchildren, unit);
                        sheet.addCell(labelUnit);

                        // 数量(成本卡)
                        String costCardQuantity = (storageItem.getCostCardQuantity() == null ? "无数量" : storageItem.getCostCardQuantity().toString());
                        Label label13 = new Label(13, row + rowchildren, costCardQuantity);
                        sheet.addCell(label13);
                        // 单位(成本卡)
                        Label label14 = new Label(14, row + rowchildren, storageItem.getCostCardUnitName());
                        sheet.addCell(label14);
                        // 物品价格
                        Label labelPrice = new Label(15, row + rowchildren, storageItem.getPrice().toString());
                        sheet.addCell(labelPrice);

                        //备注
                        Label labelItemComment = new Label(16, row + rowchildren, storageItem.getComment());
                        sheet.addCell(labelItemComment);
                        rowchildren++;
                    }
                } else {
                    for (StorageReportIngredient storageIngredient : storageReportIngredientList) {

                        // 物品名称
                        Label labelItemName = new Label(9, row + rowchildren, storageIngredient.getIngredientName());
                        sheet.addCell(labelItemName);
                        // 物品编号
                        String itemNumber = storageIngredient.getIngredientNumber();
                        Label labelItemNumber = new Label(10, row + rowchildren, itemNumber);
                        sheet.addCell(labelItemNumber);

                        // 物品数量(库存订货)
                        Label labelQuantity = new Label(11, row + rowchildren, storageIngredient.getQuantity().toString());
                        sheet.addCell(labelQuantity);

                        // 物品单位(库存订货)
                        String unit = storageIngredient.getStorageUnitName();
                        Label labelUnit = new Label(12, row + rowchildren, unit);
                        sheet.addCell(labelUnit);

                        // 数量(成本卡)storageItemDto.getCostCardQuantity().toString()
                        String costCardQuantity = (storageIngredient.getCostCardQuantity() == null ? "无数量" : storageIngredient.getCostCardQuantity().toString());
                        Label label13 = new Label(13, row + rowchildren, costCardQuantity);
                        sheet.addCell(label13);

                        // 单位(成本卡)
                        Label label14 = new Label(14, row + rowchildren, storageIngredient.getCostCardUnitName());
                        sheet.addCell(label14);

                        // 物品价格
                        Label labelPrice = new Label(15, row + rowchildren, "无价格");
                        sheet.addCell(labelPrice);

                        //备注
                        Label labelItemComment = new Label(16, row + rowchildren, storageIngredient.getComment());
                        sheet.addCell(labelItemComment);
                        rowchildren++;
                    }
                }

                // 合并单元格

                if((storageItemList==null&&storageReportIngredientList.size()>=1)){
                    sheet.mergeCells(0, up , 0, up+storageReportIngredientList.size()-1 );
                    sheet.mergeCells(1, up, 1, up+storageReportIngredientList.size()-1);
                    sheet.mergeCells(2, up, 2, up+storageReportIngredientList.size()-1);
                    sheet.mergeCells(3, up, 3, up+storageReportIngredientList.size()-1);
                    sheet.mergeCells(4, up, 4, up+storageReportIngredientList.size()-1);
                    sheet.mergeCells(5, up, 5, up+storageReportIngredientList.size()-1);
                    sheet.mergeCells(6, up, 6, up+storageReportIngredientList.size()-1);
                    sheet.mergeCells(7, up, 7, up+storageReportIngredientList.size()-1);
                    sheet.mergeCells(8, up, 8, up+storageReportIngredientList.size()-1);
                    up = up+storageReportIngredientList.size();
                    row++;
                    rowchildren--;
                }else if((storageItemList.size()>=1&&storageReportIngredientList == null)){
                    sheet.mergeCells(0, up , 0, up + storageItemList.size()-1);
                    sheet.mergeCells(1, up, 1, up + storageItemList.size()-1);
                    sheet.mergeCells(2, up, 2, up + storageItemList.size()-1);
                    sheet.mergeCells(3, up, 3, up + storageItemList.size()-1);
                    sheet.mergeCells(4, up, 4, up + storageItemList.size()-1);
                    sheet.mergeCells(5, up, 5, up + storageItemList.size()-1);
                    sheet.mergeCells(6, up, 6, up + storageItemList.size()-1);
                    sheet.mergeCells(7, up, 7, up + storageItemList.size()-1);
                    sheet.mergeCells(8, up, 8, up + storageItemList.size()-1);
                    up = up + storageItemList.size();
                    row++;
                    rowchildren--;
                }else{
                    row++;
                    rowchildren--;
                }
            }

                /*if((storageItemList==null&&storageReportIngredientList.size()>=1)||
                        (storageItemList.size()>=1&&storageReportIngredientList == null)){
                    sheet.mergeCells(0, row , 0, row + rowchildren-1);
                    sheet.mergeCells(1, row, 1, row + rowchildren-1);
                    sheet.mergeCells(2, row, 2, row + rowchildren-1);
                    sheet.mergeCells(3, row, 3, row + rowchildren-1);
                    sheet.mergeCells(4, row, 4, row + rowchildren-1);
                    sheet.mergeCells(5, row, 5, row + rowchildren-1);
                    sheet.mergeCells(6, row, 6, row + rowchildren-1);
                    sheet.mergeCells(7, row, 7, row + rowchildren-1);
                    sheet.mergeCells(8, row, 8, row + rowchildren-1);

            }
            grow = rowchildren;
            up = row+rowchildren;
            row++;*/


                /*// 单元格合并函数
                if(storageItemList.size()>1||){
                    sheet.mergeCells(0, row, 0, row + rowchildren-1);
                    sheet.mergeCells(1, row, 1, row + rowchildren-1);
                    sheet.mergeCells(2, row, 2, row + rowchildren-1);
                    sheet.mergeCells(3, row, 3, row + rowchildren-1);
                    sheet.mergeCells(4, row, 4, row + rowchildren-1);
                    sheet.mergeCells(5, row, 5, row + rowchildren-1);
                    sheet.mergeCells(6, row, 6, row + rowchildren-1);
                    sheet.mergeCells(7, row, 7, row + rowchildren-1);
                }
                if(rowchildren<=1){
                    row ++;
                }else{
                    row = row + rowchildren;
                }*/


            outBook.write();
            outBook.close();
            tplWorkBook.close();
            tplStream.close();
            os.close();
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            response.setContentType("text/html");
            response.setHeader("Content-Type", "text/html");
            response.setHeader("Content-disposition", "");
            response.setCharacterEncoding("UTF-8");
            try {
                String eMsg = "系统内部异常，请联系管理员！";
                eMsg= java.net.URLEncoder.encode(eMsg.toString(),"UTF-8");
                response.sendRedirect("/admin/storage/report?eMsg="+eMsg);
                os.close();
            } catch (IOException e1) {
                LogClerk.errLog.error(e1.getMessage());
            }
            throw SSException.get(EmenuException.ExportReportFail, e);
        }
        finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    LogClerk.errLog.error(e);
                    throw SSException.get(EmenuException.ExportReportFail, e);
                }
            }
        }
    }

}
