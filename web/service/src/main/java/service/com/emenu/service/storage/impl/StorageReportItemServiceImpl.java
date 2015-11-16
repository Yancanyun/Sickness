package com.emenu.service.storage.impl;

import com.emenu.common.entity.storage.StorageReportItem;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.storage.StorageReportItemMapper;
import com.emenu.service.storage.StorageReportItemService;
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
 * @author xiaozl
 * @date 2015/11/12
 * @time 14:35
 */
@Service("storageReportItemService")
public class StorageReportItemServiceImpl implements StorageReportItemService {


    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private StorageReportItemMapper storageReportItemMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public StorageReportItem newStorageReportItem(StorageReportItem storageReportItem) throws SSException {

        try {
            if(Assert.isNull(storageReportItem)){
                throw SSException.get(EmenuException.ReportIsNotNull);
            }

            if(checkStorageReportItemBeforeSave(storageReportItem)){
                return commonDao.insert(storageReportItem);
            }
        } catch (Exception e) {

            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertReportItemFail, e);
        }
        return null;
    }

    @Override
    public List<StorageReportItem> listAll() throws SSException {

        List<StorageReportItem> storageReportItemList = Collections.emptyList();

        try {
            storageReportItemList = storageReportItemMapper.listAll();
            return storageReportItemList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListStorageReportItemFail, e);
        }
    }

    @Override
    public StorageReportItem queryByReportId(int reportId) throws SSException {
        StorageReportItem storageReportItem = new StorageReportItem();
        try {
           storageReportItem = storageReportItemMapper.queryByReportId(reportId);
            return storageReportItem;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryStorageReportItemFail, e);
        }
    }

    @Override
    public List<StorageReportItem> listByReportId(int reportId) throws SSException {
        List<StorageReportItem> storageReportItemList =  Collections.emptyList();
        try {
            storageReportItemList = storageReportItemMapper.listByReportId(reportId);
            return storageReportItemList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryStorageReportItemFail, e);
        }
    }

    @Override
    public boolean updateById(StorageReportItem storageReportItem) throws SSException {
        try {
            if(Assert.isNull(storageReportItem)){
                throw SSException.get(EmenuException.ReportIsNotNull);
            }
            commonDao.update(storageReportItem);
            return true;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateStorageReportItemFail, e);
        }
    }

    private boolean checkStorageReportItemBeforeSave(StorageReportItem storageReportItem) throws SSException{

        if (Assert.isNull(storageReportItem)){
            return false;
        }

        Assert.isNotNull(storageReportItem.getQuantity(),EmenuException.QuantityError);
        Assert.isNotNull(storageReportItem.getPrice(),EmenuException.PriceError);
        Assert.lessOrEqualZero(storageReportItem.getReportId(),EmenuException.ReportIdError);

        return true;
    }

}
