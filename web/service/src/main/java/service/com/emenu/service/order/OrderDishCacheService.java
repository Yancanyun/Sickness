package com.emenu.service.order;

import com.emenu.common.dto.order.OrderDishCache;
import com.pandawork.core.common.exception.SSException;

/**
 * OrderDishCacheService
 * 点餐缓存(已点但仍未下单的菜品)
 *
 * @author: yangch
 * @time: 2016/5/31 17:10
 */
public interface OrderDishCacheService {
    /**
     * 添加菜品
     * @param tableId
     * @param orderDishCache
     * @throws SSException
     */
    public void newOrderDish (int tableId, OrderDishCache orderDishCache) throws SSException;

//    public void delOrderDish (int tableId, OrderDishCache orderDishCache) throws SSException;
}
