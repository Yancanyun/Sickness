package com.emenu.service.order.impl;

import com.emenu.common.dto.order.OrderDishCache;
import com.emenu.common.dto.order.TableOrderCache;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.order.OrderDishCacheService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * OrderDishCacheServiceImpl
 *
 * @author: yangch
 * @time: 2016/5/31 17:14
 */
@Service("orderDishCacheService")
public class OrderDishCacheServiceImpl implements OrderDishCacheService {
    // 餐台点餐缓存的Map
    private Map<Integer, TableOrderCache> tableOrderCacheMap = new HashMap<Integer, TableOrderCache>();

    // 点的菜品存入OrderDishCache中的ID
    private int orderDishCacheId = 0;

    @Override
    public void newOrderDish (int tableId, OrderDishCache orderDishCache) throws SSException {
        try {
            // 从缓存中取出本餐台的餐台点餐缓存(TableOrderCache)
            TableOrderCache tableOrderCache = tableOrderCacheMap.get(tableId);

            // 若缓存中不存在本餐台的TableOrderCache，则新建之
            if (tableOrderCache == null) {
                tableOrderCache = new TableOrderCache();
            }

            // 若已被加锁，则不允许执行接下来的操作
            if (tableOrderCache.getLock() == true) {
                throw SSException.get(EmenuException.TableIsLock);
            }

            // 从TableOrderCache中获取本餐台中已点但仍未下单的全部菜品(OrderDishCacheList)
            List<OrderDishCache> orderDishCacheList = tableOrderCache.getOrderDishCacheList();

            // 设置OrderDishCache中的ID
            orderDishCache.setId(++orderDishCacheId);

            // 把点的菜加入缓存中
            if (orderDishCacheList == null) {
                orderDishCacheList = new ArrayList<OrderDishCache>();
            }
            orderDishCacheList.add(orderDishCache);
            tableOrderCache.setOrderDishCacheList(orderDishCacheList);
            tableOrderCacheMap.put(tableId, tableOrderCache);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewDishError, e);
        }
    }
}
