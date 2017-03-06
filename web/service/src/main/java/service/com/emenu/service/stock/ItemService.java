package com.emenu.service.stock;

import com.emenu.common.entity.stock.StockItem;
import com.pandawork.core.common.exception.SSException;

/**
 * ItemService
 * 物品管理Service
 *
 * @author pengpeng
 * @time 2017/3/4 9:55
 */
public interface ItemService {

    public StockItem newItem(StockItem stockItem) throws SSException;
}
