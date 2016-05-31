package com.emenu.service.storage.impl;

import com.emenu.common.dto.storage.ItemAndIngredientSearchDto;
import com.emenu.common.dto.storage.StorageItemSearchDto;
import com.emenu.common.entity.dish.Unit;
import com.emenu.common.entity.storage.StorageItem;
import com.emenu.common.enums.storage.StorageItemStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.storage.StorageItemMapper;
import com.emenu.service.dish.UnitService;
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

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Override
    public List<StorageItem> listBySearchDto(ItemAndIngredientSearchDto searchDto) throws SSException {
        List<StorageItem> list = Collections.emptyList();
        int pageNo = searchDto.getPageNo() <= 0 ? 0 : searchDto.getPageNo() - 1;
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
        if (Assert.lessOrEqualZero(id)) {
            return ;
        }
        try {
            // TODO: 2015/11/12 查询是否有成本卡使用物品,查询结算的库存物品是否还有剩余
            // 删除即修改使用状态 Deleted(2, "删除")
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
            StorageItem StorageItem = storageItemMapper.queryById(id);
            if (Assert.isNull(StorageItem)){
                return StorageItem;
            }
            setUnitName(StorageItem);
            return StorageItem;
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
        for (StorageItem storageItem : storageItemList) {
            // 将数量和单位拼接成string，并将成本卡单位表示的数量转换为库存单位表示
            BigDecimal maxStorageQuantity = storageItem.getMaxStorageQuantity().divide(storageItem.getStorageToCostCardRatio());
            String maxStorageQuantityStr = maxStorageQuantity.toString() + storageItem.getStorageUnitName();
            storageItem.setMaxStorageQuantityStr(maxStorageQuantityStr);

            // 最小库存
            BigDecimal minStorageQuantity = storageItem.getMinStorageQuantity().divide(storageItem.getStorageToCostCardRatio());
            String minStorageQuantityStr = minStorageQuantity.toString() + storageItem.getStorageUnitName();
            storageItem.setMinStorageQuantityStr(minStorageQuantityStr);

            // 总数量
            BigDecimal totalStockInQuantityStr = storageItem.getTotalStockInQuantity().divide(storageItem.getTotalStockInQuantity());
            String totalQuantityStr = totalStockInQuantityStr.toString() + storageItem.getStorageUnitName();
            storageItem.setTotalStockInQuantityStr(totalQuantityStr);
        }
    }

    public void setQuantityFormat(StorageItem storageItem) throws SSException{
        // 将数量和单位拼接成string，并将成本卡单位表示的数量转换为库存单位表示
        BigDecimal maxStorageQuantity = storageItem.getMaxStorageQuantity().divide(storageItem.getStorageToCostCardRatio());
        String maxStorageQuantityStr = maxStorageQuantity.toString() + storageItem.getStorageUnitName();
        storageItem.setMaxStorageQuantityStr(maxStorageQuantityStr);

        // 最小库存
        BigDecimal minStorageQuantity = storageItem.getMinStorageQuantity().divide(storageItem.getStorageToCostCardRatio());
        String minStorageQuantityStr = minStorageQuantity.toString() + storageItem.getStorageUnitName();
        storageItem.setMinStorageQuantityStr(minStorageQuantityStr);

        // 总数量
        BigDecimal totalStockInQuantityStr = storageItem.getTotalStockInQuantity().divide(storageItem.getTotalStockInQuantity());
        String totalQuantityStr = totalStockInQuantityStr.toString() + storageItem.getStorageUnitName();
        storageItem.setTotalStockInQuantityStr(totalQuantityStr);
    }
}
