package com.emenu.service.storage.impl;

import com.emenu.common.dto.storage.StorageReportDto;
import com.emenu.common.entity.storage.StorageReport;
import com.emenu.common.entity.storage.StorageReportItem;
import com.emenu.common.exception.EmenuException;
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
           if(Assert.isNull(storageReportDto.getStorageReportItem())){
               throw SSException.get(EmenuException.ReportIsNotNull);
           }

           storageReportItemService.newStorageReportItem(storageReportDto.getStorageReportItem());
           newReport(storageReportDto.getStorageReport());

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
                StorageReportItem storageReportItem = new StorageReportItem();
                //根据单据id获取单据详情信息
                storageReportItem = storageReportItemService.queryByReportId(storageReport.getId());

                //数据存入reportDto
                storageReportDto.setStorageReport(storageReport);
                storageReportDto.setStorageReportItem(storageReportItem);

                storageReportDtoList.add(storageReportDto);
            }

            return storageReportDtoList;

        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }

    }

    @Override
    public List<StorageReportDto> listStorageReportDtoByPage(int page, int pageSize) throws SSException {
        int offset = page * pageSize;
        List<StorageReportDto> storageReportDtoList = new ArrayList();
        List<StorageReport> storageReportList = Collections.emptyList();
        if (Assert.lessOrEqualZero(offset)){
            return storageReportDtoList;
        }

        try {
            storageReportList = storageReportMapper.listByPage(offset,pageSize);
            for (StorageReport storageReport : storageReportList){
                StorageReportDto storageReportDto = new StorageReportDto();
                StorageReportItem storageReportItem = new StorageReportItem();
                //根据单据id获取单据详情信息
                storageReportItem = storageReportItemService.queryByReportId(storageReport.getId());

                //数据存入reportDto
                storageReportDto.setStorageReport(storageReport);
                storageReportDto.setStorageReportItem(storageReportItem);

                storageReportDtoList.add(storageReportDto);
            }

            return storageReportDtoList;

        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }

}
