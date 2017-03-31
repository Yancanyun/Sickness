package com.emenu.service.stock.impl;

import com.emenu.common.entity.stock.StockKitchenItem;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.stock.StockKitchenItemMapper;
import com.emenu.service.stock.StockKitchenItemService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

/**
 * StockKitchenItemServiceImpl
 *
 * @author yaojf
 * @date 2017/3/13 10:16
 */
@Service("stockItemService")
public class StockKitchenItemServiceImpl implements StockKitchenItemService{
    @Autowired
    private StockKitchenItemMapper stockKitchenItemMapper;


    /**
     * 添加新的厨房物品
     * @param stockKitchenItem
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void newStockKitchenItem(StockKitchenItem stockKitchenItem)throws SSException {
        try {
            stockKitchenItemMapper.newStockKitchenItem(stockKitchenItem);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.AddKitchenItemFail, e);
        }
    }

    /**
     * 删除厨房的某个物品
     * @param id
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delStockKitchenItem(Integer id) throws SSException{
        try{
            //这里还要判断菜品表中是否有该物品，有的话不能删除，暂时未写
            stockKitchenItemMapper.delStockKitchenItem(id);
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelKitchenItemFail, e);
        }

    }

    /**
     * 更新库存物品列表
     * @param stockKitchenItem
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateStockKitchenItem(StockKitchenItem stockKitchenItem) throws SSException{
        try{
            stockKitchenItemMapper.updateStockKitchenItem(stockKitchenItem);
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateKitchenItemFail, e);
        }

    }

    /**
     * 修改厨房物品的备注
     * @param id
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void editRemark(Integer id) throws SSException{
        try{
            stockKitchenItemMapper.editRemark(id);
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.EditRemarkFail, e);
        }
    }

    /**
     * 列出所有的物品
     * @return
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public List<StockKitchenItem> queryAllItem() throws SSException{
        List<StockKitchenItem> stockKitchenItemList = Collections.emptyList();
        try{
            stockKitchenItemList = stockKitchenItemMapper.queryAllItem();
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryAllKitchenItemFail, e);
        }
        return stockKitchenItemList;
    }

    /**
     * 根据id查询某个物品
     * @param id
     * @return
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public StockKitchenItem queryById(Integer id) throws SSException{
        StockKitchenItem  stockKitchenItem = new StockKitchenItem();
        try{
            stockKitchenItem = stockKitchenItemMapper.queryById(id);
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryKitchenItemFail, e);
        }
        return stockKitchenItem;
    }

    /**
     * 根据itemId（物品表中的物品id）来查询物品
     * @param itemId
     * @param kitchenId
     * @return
     * @throws SSException
     */
    @Override
    public StockKitchenItem queryByItemId(Integer itemId,Integer kitchenId) throws SSException{
        StockKitchenItem  stockKitchenItem = new StockKitchenItem();
        try{
            stockKitchenItem = stockKitchenItemMapper.queryByItemId(itemId,kitchenId);
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryKitchenItemFail, e);
        }
        return stockKitchenItem;
    }
}
