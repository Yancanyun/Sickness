package com.emenu.service.stock;

import com.emenu.common.entity.stock.StockKitchenItem;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * StockKitchenItemService
 *
 * @author yaojf
 * @date 2017/3/13 10:15
 */
public interface StockKitchenItemService {

    public void newStockKitchenItem(StockKitchenItem stockKitchenItem)throws SSException;

    public void delStockKitchenItem(Integer id) throws SSException;

    public void updateStockKitchenItem(Integer id) throws SSException;

    public List<StockKitchenItem> queryAllItem() throws SSException;

    public StockKitchenItem queryById(Integer id) throws SSException;

    public StockKitchenItem queryByItemId(Integer id) throws SSException;
}
