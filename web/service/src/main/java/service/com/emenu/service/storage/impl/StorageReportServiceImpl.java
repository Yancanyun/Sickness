package com.emenu.service.storage.impl;

import com.emenu.common.dto.storage.StorageReportDto;
import com.emenu.common.entity.storage.StorageReport;
import com.emenu.common.entity.storage.StorageReportItem;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.CommonUtil;
import com.emenu.common.utils.DateUtils;
import com.emenu.mapper.storage.StorageReportMapper;
import com.emenu.service.storage.StorageReportItemService;
import com.emenu.service.storage.StorageReportService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public boolean newReportAndReportItem(StorageReportDto storageReportDto) throws SSException {
       try {
           if(Assert.isNull(storageReportDto)){
               throw SSException.get(EmenuException.ReportIsNotNull);
           }
           if(Assert.isNull(storageReportDto.getStorageReport())){
               throw SSException.get(EmenuException.ReportIsNotNull);
           }
           if(Assert.isNull(storageReportDto.getStorageReportItemList())){
               throw SSException.get(EmenuException.ReportIsNotNull);
           }

           this.newReport(storageReportDto.getStorageReport());

           for(StorageReportItem storageReportItem : storageReportDto.getStorageReportItemList())
           storageReportItemService.newStorageReportItem(storageReportItem);

       } catch (Exception e) {
           LogClerk.errLog.error(e);
           throw SSException.get(EmenuException.InsertReportFail, e);
       }

        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public StorageReport newReport(StorageReport storageReport) throws SSException {

        try {
            if(Assert.isNull(storageReport)){
                throw SSException.get(EmenuException.ReportIsNotNull);
            }

            if(checkStorageReportBeforeSave(storageReport)){
                return commonDao.insert(storageReport);
            }

        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertReportFail, e);
        }

        return null;
    }

    private boolean checkStorageReportBeforeSave(StorageReport storageReport) throws SSException{

        if (Assert.isNull(storageReport)) {
            return false;
        }

        Assert.lessOrEqualZero(storageReport.getCreatedPartyId(), EmenuException.CreatedPartyIdError);
        Assert.lessOrEqualZero(storageReport.getHandlerPartyId(), EmenuException.HandlerPartyId);
        Assert.lessOrEqualZero(storageReport.getDepotId(), EmenuException.DepotIdError);
        Assert.isNotNull(storageReport.getSerialNumber(), EmenuException.SerialNumberError);
        Assert.lessOrEqualZero(storageReport.getStatus(), EmenuException.ReportStatusError);
        Assert.isNotNull(storageReport.getMoney(), EmenuException.ReportMoneyError);
        Assert.lessOrEqualZero(storageReport.getType(), EmenuException.ReportTypeError);

        return true;
    }

    public List<StorageReport> listAll() throws SSException{

        List<StorageReport> storageReportList = Collections.emptyList();

        try {
            storageReportList = storageReportMapper.listAll();
            return storageReportList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }

    }

    @Override
    public List<StorageReportDto> listStorageReportDto() throws SSException {

        List<StorageReportDto> storageReportDtoList = new ArrayList();
        List<StorageReport> storageReportList = Collections.emptyList();
        try {
            //获取所有单据信息
            storageReportList = listAll();

            for (StorageReport storageReport : storageReportList){
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
            throw SSException.get(EmenuException.SystemException, e);
        }

    }

    @Override
    public List<StorageReportDto> ListStorageReportDtoUnsettled(Date endTime) throws SSException {
        List<StorageReportDto> storageReportDtoList = new ArrayList();
        List<StorageReport> storageReportList = Collections.emptyList();
        try {
            //获取所有单据信息
            storageReportList = storageReportMapper.ListStorageReportUnsettled(endTime);

            if (Assert.isNull(storageReportList)){
                return storageReportDtoList;
            }

            for (StorageReport storageReport : storageReportList){
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
            throw SSException.get(EmenuException.ListStorageReportFail, e);
        }
    }

  /*  @Override
    public List<StorageReportDto> ListStorageReportDtoUnsettle(Date endTime) throws SSException {
        List<StorageReportDto> storageReportList = Collections.emptyList();
        try {
            storageReportList = storageReportMapper.ListStorageReportDtoUnsettle(endTime);
       } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListStorageReportFail, e);
        }


    }*/

    @Override
    public List<StorageReportDto> listStorageReportDtoByPage(int page, int pageSize) throws SSException {
        int offset = page * pageSize;
        List<StorageReportDto> storageReportDtoList = new ArrayList();
        List<StorageReport> storageReportList = Collections.emptyList();
        if(Assert.lessZero(offset)){
            return storageReportDtoList;
        }

        try {
            storageReportList = storageReportMapper.listByPage(offset,pageSize);
            for (StorageReport storageReport : storageReportList){
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
            throw SSException.get(EmenuException.SystemException, e);
        }
    }

    @Override
    public List<StorageReportDto> listStorageReportDtoByCondition(Date startTime,
                                                                  Date endTime,
                                                                  String serialNumber,
                                                                  int depotId,
                                                                  int handlerPartyId,
                                                                  int createdPartyId,
                                                                  int page,
                                                                  int pageSize) throws SSException {

        int offset = page * pageSize;

        if (startTime == null && endTime == null) {
            startTime = DateUtils.getTodayStartTime();
            endTime = DateUtils.getTodayEndTime();
        }

        if (endTime != null) {
            endTime.setHours(23);
            endTime.setMinutes(59);
            endTime.setSeconds(59);
        }


        List<StorageReportDto> storageReportDtoList = new ArrayList();
        List<StorageReport> storageReportList = Collections.emptyList();

        try {
            if(Assert.lessZero(offset)){
                return storageReportDtoList;
            }
            storageReportList = storageReportMapper.listStorageReportByCondition(startTime,endTime,serialNumber,depotId,handlerPartyId,createdPartyId,offset,pageSize);

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
            throw SSException.get(EmenuException.ListStorageReportFail, e);
        }
    }

    @Override
    public List<StorageReportDto> listStorageReportDtoByCondition1(int id,
                                                                     int depotId,
                                                                     int handlerPartyId,
                                                                     int createdPartyId,
                                                                     int page,
                                                                     int pageSize) throws SSException {
        int offset = page * pageSize;
        List<StorageReportDto> storageReportDtoList = new ArrayList();
        List<StorageReport> storageReportList = Collections.emptyList();

        try {
            if(Assert.lessZero(offset)){
                return storageReportDtoList;
            }
            storageReportList = storageReportMapper.listStorageReportByCondition1(id,depotId,handlerPartyId,createdPartyId,offset,pageSize);

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
            throw SSException.get(EmenuException.ListStorageReportFail, e);
        }
    }

    @Override
    public boolean updateById(StorageReport storageReport) throws SSException {
        try {
              if(Assert.isNull(storageReport)){
                  throw SSException.get(EmenuException.ReportIsNotNull);
              }
            commonDao.update(storageReport);
            return true;
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
            throw SSException.get(EmenuException.QueryStorageReportFail, e);
        }
    }

}
