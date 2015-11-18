package com.emenu.service.storage.impl;

import com.emenu.common.dto.storage.StorageItemSearchDto;
import com.emenu.common.entity.storage.StorageItem;
import com.emenu.common.enums.storage.StorageItemStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.storage.StorageItemMapper;
import com.emenu.service.storage.StorageItemService;
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
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Override
    public List<StorageItem> listBySearchDto(StorageItemSearchDto searchDto) throws SSException {
        List<StorageItem> list = Collections.emptyList();
        int pageNo = searchDto.getPageNo() <= 0 ? 0 : searchDto.getPageNo() - 1;
        int offset = pageNo * searchDto.getPageSize();

        try {
            list = storageItemMapper.listBySearchDto(offset, searchDto);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StorageItemQueryFailed, e);
        }
        return list;
    }

    @Override
    public int countBySearchDto(StorageItemSearchDto searchDto) throws SSException {
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

            commonDao.insert(storageItem);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StorageItemInsertFailed, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateStorageItem(StorageItem storageItem) throws SSException {
        try {
            if (!checkBeforeSave(storageItem)) {
                return ;
            }
            Assert.isNotNull(storageItem.getId(), EmenuException.StorageItemIdNotNull);
            if (Assert.lessOrEqualZero(storageItem.getId())) {
                return ;
            }

            commonDao.update(storageItem);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StorageItemUpdateFailed, e);
        }
    }

    @Override
    public void delById(int id) throws SSException {
        if (Assert.lessOrEqualZero(id)) {
            return ;
        }
        try {
            // TODO: 2015/11/12 查询是否有成本卡使用物品

            storageItemMapper.updateStatusById(id, StorageItemStatusEnums.Deleted.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StorageTagDeleteFailed, e);
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
            commonDao.queryById(StorageItem.class, id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StorageItemQueryFailed, e);
        }
        return null;
    }

    private boolean checkBeforeSave(StorageItem storageItem) throws SSException {
        if (Assert.isNull(storageItem)) {
            return false;
        }

        Assert.isNotNull(storageItem.getName(), EmenuException.StorageItemNameNotNull);
        Assert.isNotNull(storageItem.getSupplierPartyId(), EmenuException.StorageItemSupplierNotNull);
        Assert.isNotNull(storageItem.getTagId(), EmenuException.StorageItemTagNotNull);
        Assert.isNotNull(storageItem.getOrderUnitId(), EmenuException.StorageItemUnitNotNull);
        Assert.isNotNull(storageItem.getStorageUnitId(), EmenuException.StorageItemUnitNotNull);
        Assert.isNotNull(storageItem.getCostCardUnitId(), EmenuException.StorageItemUnitNotNull);
        Assert.isNotNull(storageItem.getOrderToStorageRatio(), EmenuException.StorageItemUnitRatioNotNull);
        Assert.isNotNull(storageItem.getStorageToCostCardRatio(), EmenuException.StorageItemUnitRatioNotNull);
        Assert.isNotNull(storageItem.getMaxStorageQuantity(), EmenuException.StorageItemMaxMinQuantity);
        Assert.isNotNull(storageItem.getMinStorageQuantity(), EmenuException.StorageItemMaxMinQuantity);

        return true;
    }
}
