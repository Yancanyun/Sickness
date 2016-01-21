package com.emenu.service.storage.impl;

import com.emenu.common.dto.storage.StorageReportItemDto;
import com.emenu.common.entity.party.security.SecurityUserGroup;
import com.emenu.common.entity.storage.StorageReportItem;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.exception.PartyException;
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
    public StorageReportItem newReportItem(StorageReportItem reportItem) throws SSException {

        try {
            if(Assert.isNull(reportItem)){
                throw SSException.get(EmenuException.ReportIsNotNull);
            }
            if(!checkStorageReportItemBeforeSave(reportItem)){
                return null;
            }
            return commonDao.insert(reportItem);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertReportItemFail, e);
        }
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
            throw SSException.get(EmenuException.QueryReportItemFail, e);
        }
    }

    @Override
    public List<StorageReportItem> listByReportId(int reportId) throws SSException {
        List<StorageReportItem> reportItemList =  Collections.emptyList();
        try {
            reportItemList = storageReportItemMapper.listByReportId(reportId);
            return reportItemList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryReportItemFail, e);
        }
    }

    @Override
    public List<StorageReportItemDto> listDtoByReportId(int reportId) throws SSException {
        List<StorageReportItemDto> reportItemDtoList = Collections.emptyList();
        try {
            reportItemDtoList = storageReportItemMapper.listDtoByReportId(reportId);
            return reportItemDtoList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryReportItemFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateById(StorageReportItem reportItem) throws SSException {
        try {
            if(Assert.isNull(reportItem)){
                throw SSException.get(EmenuException.ReportIsNotNull);
            }
            commonDao.update(reportItem);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateStorageReportItemFail, e);
        }
    }

    @Override
    public List<StorageReportItem> listByReportIdAndItemIdList(int reportId, List<Integer> itemIdList) throws SSException {
        List<StorageReportItem> storageReportItemList =  Collections.emptyList();
        try {
            storageReportItemList = storageReportItemMapper.listByReportIdAndItemIdList(reportId,itemIdList);
            return storageReportItemList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryReportItemFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delByReportId(int id) throws SSException {
        try {
              if (Assert.lessOrEqualZero(id)){
                  throw SSException.get(EmenuException.ReportIdError);
              }
            storageReportItemMapper.delByReportId(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelReportOrItemFail, e);
        }
    }

    @Override
    public void delById(int id) throws SSException {
        if (Assert.lessOrEqualZero(id)){
            return;
        }
        try{
            commonDao.deleteById(StorageReportItem.class, id);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelReportOrItemFail, e);
        }
    }

    /**
     * 保存StorageReportItem检查关键字段是否合法
     * @param storageReportItem
     * @return
     * @throws SSException
     */
    private boolean checkStorageReportItemBeforeSave(StorageReportItem storageReportItem) throws SSException{
        if (Assert.isNull(storageReportItem)){
            return false;
        }
        //单据详情数量字段
        Assert.isNotNull(storageReportItem.getQuantity(),EmenuException.QuantityError);
        //单据详情成本价
        Assert.isNotNull(storageReportItem.getPrice(), EmenuException.PriceError);
        if (Assert.lessOrEqualZero(storageReportItem.getReportId())||Assert.isNull(storageReportItem.getReportId())){
            Assert.lessOrEqualZero(storageReportItem.getReportId(),EmenuException.ReportIdError);
        }
        return true;
    }

}
