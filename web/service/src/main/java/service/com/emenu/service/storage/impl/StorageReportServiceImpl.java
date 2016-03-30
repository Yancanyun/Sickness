package com.emenu.service.storage.impl;

import com.emenu.common.dto.storage.StorageItemSearchDto;
import com.emenu.common.dto.storage.StorageReportDto;
import com.emenu.common.dto.storage.StorageReportItemDto;
import com.emenu.common.entity.storage.StorageItem;
import com.emenu.common.entity.storage.StorageReport;
import com.emenu.common.entity.storage.StorageReportItem;
import com.emenu.common.enums.ExcelExportTemplateEnums;
import com.emenu.common.enums.storage.StorageReportStatusEnum;
import com.emenu.common.enums.storage.StorageReportTypeEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.exception.PartyException;
import com.emenu.common.utils.DateUtils;
import com.emenu.common.utils.EntityUtil;
import com.emenu.mapper.storage.StorageReportMapper;
import com.emenu.service.party.group.employee.EmployeeService;
import com.emenu.service.storage.StorageDepotService;
import com.emenu.service.storage.StorageItemService;
import com.emenu.service.storage.StorageReportItemService;
import com.emenu.service.storage.StorageReportService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.common.util.IOUtil;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.core.framework.dao.repository.SimpelNameToClassRepository;
import com.pandawork.core.pweio.excel.DataType;
import jxl.Workbook;
import jxl.write.Label;
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

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void newReportDto(StorageReportDto reportDto) throws SSException {
       try {
           if(Assert.isNull(reportDto)){
               throw SSException.get(EmenuException.ReportDtoIsNotNull);
           }
           if(Assert.isNull(reportDto.getStorageReport())){
               throw SSException.get(EmenuException.ReportIsNotNull);
           }
           if(Assert.isEmpty(reportDto.getStorageReportItemList()) ||Assert.lessOrEqualZero(reportDto.getStorageReportItemList().size())){
               throw SSException.get(EmenuException.ReportItemListIsNotNull);
           }
           StorageReport storageReport = this.newReport(reportDto.getStorageReport());
           for (StorageReportItem reportItem : reportDto.getStorageReportItemList()) {
               reportItem.setReportId(storageReport.getId());
               storageReportItemService.newReportItem(reportItem);
           }
       } catch (Exception e) {
           LogClerk.errLog.error(e);
           throw SSException.get(EmenuException.InsertReportFail, e);
       }
    }

    @Override
    public List<StorageReport> listAll() throws SSException{
        List<StorageReport> reportList = Collections.emptyList();
        try {
            reportList = storageReportMapper.listAll();
            return reportList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListReportFail, e);
        }
    }

    @Override
    public List<StorageReportDto> listReportDto() throws SSException {
        List<StorageReportDto> reportDtoList = new ArrayList();
        List<StorageReport> reportList = Collections.emptyList();
        try {
            //获取所有单据信息
            reportList = listAll();
            for (StorageReport report : reportList){
                StorageReportDto reportDto = new StorageReportDto();
                List<StorageReportItem> reportItemList = new ArrayList();
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
    public List<StorageReportDto> ListReportDtoUnsettledByEndTime(Date endTime) throws SSException {
        List<StorageReportDto> reportDtoList = Collections.emptyList();
        List<StorageReport> reportList = Collections.emptyList();
        try {
            //获取所有单据信息
            reportList = storageReportMapper.ListStorageReportUnsettled(endTime);
            if (Assert.isEmpty(reportList)){
                return reportDtoList;
            }
            reportDtoList = new ArrayList<StorageReportDto>();
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

  /*  @Override
    public List<StorageReportDto> listReportDtoByCondition1(StorageReport report,
                                                            int page,
                                                            int pageSize) throws SSException {
        page = page <= 0 ? 0 : page - 1;
        int offset = page * pageSize;
        List<StorageReportDto> storageReportDtoList =  Collections.emptyList();
        List<StorageReport> storageReportList = Collections.emptyList();
        if(Assert.lessZero(offset)){
            return storageReportDtoList;
        }
        if(Assert.lessZero(pageSize)){
            return storageReportDtoList;
        }
        storageReportDtoList = new ArrayList();
        try {
            if (Assert.isNull(report)){
                throw SSException.get(EmenuException.ReportIsNotNull);
            }
            //storageReportList = storageReportMapper.listStorageReportByCondition1(id,depotId,handlerPartyId,createdPartyId,offset,pageSize);
            storageReportList = storageReportMapper.listStorageReportByCondition1(report, offset, pageSize);
            for (StorageReport storageReport : storageReportList) {
                StorageReportDto storageReportDto = new StorageReportDto();
                List<StorageReportItem> storageReportItemList = new ArrayList();
                //根据单据id获取单据详情信息
                storageReportItemList = storageReportItemService.listByReportId(storageReport.getId());
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
    }*/

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
    public void updateStatusById(int id, StorageReportStatusEnum storageReportStatusEnum) throws SSException {
        try {
            storageReportMapper.updateStatusById(id, storageReportStatusEnum.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateStorageReportFail, e);
        }
    }

    @Override
    public StorageReport queryById(int id) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.ReportIdError);
            }
            return commonDao.queryById(StorageReport.class,id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryReportFail, e);
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
            StorageItemSearchDto searchDto = new StorageItemSearchDto();
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
                reportDto.setStorageReportItemDtoList(reportItemDtoList);
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
    public void delReportDtoById(int id) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.ReportIdError);
            }
            this.delById(id);
            storageReportItemService.delByReportId(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelReportOrItemFail, e);
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
            if (Assert.isEmpty(reportDto.getStorageReportItemList())){
                throw SSException.get(EmenuException.StorageReportItemListIsNotNull);
            }
            //更新后的单据
            StorageReport reportNew = reportDto.getStorageReport();
            //数据库中原始未修改的单据
            StorageReportDto reportDtoOld = queryReportDtoById(reportNew.getId());
            //更新单据
            updateById(reportNew);

            List<StorageReportItem> reportItemListNew = reportDto.getStorageReportItemList();
            List<StorageReportItem> reportItemListOld = reportDtoOld.getStorageReportItemList();

            //判断更新后的单据是否存在数据
            if (reportItemListNew.size() > 0){
                //判断以前的单据是否存在数据，若不存在，则直接到下一个if处添加物品
                if (reportItemListOld.size() > 0){
                    //判断新单据和老单据中每条物品id是否一致，若一致，则修改老单据的相应物品
                    for (StorageReportItem itemOld : reportItemListOld){
                        for (StorageReportItem itemNew : reportItemListNew){
                            if (!Assert.isNull(itemNew.getId()) && itemOld.getId().equals(itemNew.getId())){
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
                    if (reportItemListOld.size() > 0){
                        for (StorageReportItem itemOld : reportItemListOld){
                            storageReportItemService.delById(itemOld.getId());
                        }
                    }
                }
                //添加物品
                if (reportItemListNew.size() > 0){
                    for (StorageReportItem itemNew : reportItemListNew){
                        itemNew.setReportId(reportNew.getId());
                        storageReportItemService.newReportItem(itemNew);
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
    public int countByContition(StorageReport report, List<Integer> depotIdList, Date startTime, Date endTime) throws SSException {
        Integer count = 0;
        if (endTime != null) {
            endTime.setHours(23);
            endTime.setMinutes(59);
            endTime.setSeconds(59);
        }
        try {
            count = storageReportMapper.countByCondition(report, depotIdList, startTime, endTime);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CountReportFail, e);
        }
        return count == null ? 0 : count;
    }

    @Override
    public StorageReportDto queryReportDtoById(int id) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id)){
                throw SSException.get(EmenuException.ReportIdError);
            }
            StorageReport report = queryById(id);
            if (Assert.isNull(report)){
                throw SSException.get(EmenuException.QueryReportFail);
            }
            List<StorageReportItem> reportItemList = Collections.emptyList();
            reportItemList = storageReportItemService.listByReportId(report.getId());

            StorageReportDto reportDto = new StorageReportDto();
            reportDto.setStorageReport(report);
            reportDto.setStorageReportItemList(reportItemList);
            return reportDto;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryReportDtoFail, e);
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
     * 检查StorageReport中关键属性是否合法
     * @param storageReport
     * @return
     * @throws SSException
     */
    private boolean checkStorageReportBeforeSave(StorageReport storageReport) throws SSException{

        if (Assert.isNull(storageReport)) {
            return false;
        }
        if (Assert.isNull(storageReport.getCreatedPartyId()) || Assert.lessOrEqualZero(storageReport.getCreatedPartyId())){
            throw SSException.get(EmenuException.CreatedPartyIdError);
        }
        if (Assert.isNull(storageReport.getHandlerPartyId()) || Assert.lessOrEqualZero(storageReport.getHandlerPartyId())){
            throw SSException.get(EmenuException.HandlerPartyId);
        }
        if (Assert.isNull(storageReport.getDepotId()) || Assert.lessOrEqualZero(storageReport.getDepotId())){
            throw SSException.get(EmenuException.DepotIdError);
        }
        if (Assert.isNull(storageReport.getStatus()) || Assert.lessOrEqualZero(storageReport.getStatus())){
//            throw SSException.get(EmenuException.ReportStatusError);
        }
        if (Assert.isNull(storageReport.getType()) || Assert.lessOrEqualZero(storageReport.getType())){
            throw SSException.get(EmenuException.ReportTypeError);
        }
        Assert.isNotNull(storageReport.getSerialNumber(), EmenuException.SerialNumberError);
        Assert.isNotNull(storageReport.getMoney(), EmenuException.ReportMoneyError);
        return true;
    }

    /**
     * 根据单据id删除单据
     * @param id
     * @return
     * @throws SSException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    private boolean delById(int id) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id)){
                return false;
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
    public void exportToExcel(StorageReport report,Date startTime, Date endTime,List<Integer> deports, Integer handlerPartyId, Integer createdPartyId, HttpServletResponse response) throws SSException{
        OutputStream os = null;
        List<StorageReportDto> storageReportDtoList = Collections.emptyList();
        try{
            //从数据库中取数据
            storageReportDtoList = this.listReportDtoByCondition1(report,null,null,deports,startTime,endTime);
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
            int row = 2;
            for (StorageReportDto storageReportDto : storageReportDtoList){
                // 类型
                Label labelReportType = new Label(0, row, StorageReportTypeEnum.valueOf(storageReportDto.getStorageReport().getType()).getName());
                sheet.addCell(labelReportType);
                // 单据编号
                Label labelSerialNumber = new Label(1, row, storageReportDto.getStorageReport().getSerialNumber());
                sheet.addCell(labelSerialNumber);
                // 存放点
                String deportName = storageDepotService.queryById(storageReportDto.getStorageReport().getDepotId()).getName();
                Label labelDepot = new Label(2, row, deportName);
                sheet.addCell(labelDepot);
                // 经手人
                String handlerName = employeeService.queryByPartyId(storageReportDto.getStorageReport().getHandlerPartyId()).getName();
                Label labelHandlerName = new Label(3, row, handlerName);
                sheet.addCell(labelHandlerName);
                // 金额
                Label labelMoney = new Label(4, row, storageReportDto.getStorageReport().getMoney().toString());
                sheet.addCell(labelMoney);
                // 操作人
                String createdName = employeeService.queryByPartyId(storageReportDto.getStorageReport().getCreatedPartyId()).getName();
                Label labelCreatedName = new Label(5, row, createdName);
                sheet.addCell(labelCreatedName);
                // 单据备注
                Label labelReportComment = new Label(6, row, storageReportDto.getStorageReport().getComment());
                sheet.addCell(labelReportComment);
                // 日期
                String createdTime = DateUtils.yearMonthDayFormat(storageReportDto.getStorageReport().getCreatedTime());
                Label labelCreatedTime = new Label(7, row, createdTime);
                sheet.addCell(labelCreatedTime);
                // 物品列表
                List<StorageReportItemDto> storageItemDtoList = storageReportDto.getStorageReportItemDtoList();
                int rowchildren = 0;
                for (StorageReportItemDto storageItemDto : storageItemDtoList){
                    // 物品名称
                    Label labelItemName = new Label(8, row+rowchildren,storageItemDto.getItemName());
                    sheet.addCell(labelItemName);
                    // 物品数量
                    Label labelQuantity = new Label(9, row+rowchildren, storageItemDto.getQuantity().toString());
                    sheet.addCell(labelQuantity);
                    // 物品价格
                    Label labelPrice = new Label(10, row+rowchildren, storageItemDto.getPrice().toString());
                    sheet.addCell(labelPrice);
                    // 小计金额
                    BigDecimal price = storageItemDto.getPrice();
                    BigDecimal quantity = storageItemDto.getQuantity();
                    BigDecimal subTotal = price.multiply(quantity);
                    Label labelSubTotal = new Label(11, row, subTotal.toString());
                    sheet.addCell(labelSubTotal);
                    //备注
                    Label labelItemComment = new Label(12, row+rowchildren ,storageItemDto.getComment());
                    sheet.addCell(labelItemComment);
                    rowchildren++;
                }
                if(storageItemDtoList.size()>1) {
                    sheet.mergeCells(0, row, 0, row + rowchildren-1);
                    sheet.mergeCells(1, row, 1, row + rowchildren-1);
                    sheet.mergeCells(2, row, 2, row + rowchildren-1);
                    sheet.mergeCells(3, row, 3, row + rowchildren-1);
                    sheet.mergeCells(4, row, 4, row + rowchildren-1);
                    sheet.mergeCells(5, row, 5, row + rowchildren-1);
                    sheet.mergeCells(6, row, 6, row + rowchildren-1);
                    sheet.mergeCells(7, row, 7, row + rowchildren-1);
                }
                if(rowchildren<=1) {
                    row ++;
                }else {
                    row = row + rowchildren;
                }
            }
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
