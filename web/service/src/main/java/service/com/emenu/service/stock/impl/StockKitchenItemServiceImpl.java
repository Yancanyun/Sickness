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
public class StockKitchenItemServiceImpl implements StockKitchenItemService{
    @Autowired
    private StockKitchenItemMapper stockKitchenItemMapper;


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

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delStockKitchenItem(Integer id) throws SSException{
        try{
            stockKitchenItemMapper.delStockKitchenItem(id);
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelKitchenItemFail, e);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateStockKitchenItem(Integer id) throws SSException{
        try{
            stockKitchenItemMapper.updateStockKitchenItem(id);
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateKitchenItemFail, e);
        }

    }

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
}
