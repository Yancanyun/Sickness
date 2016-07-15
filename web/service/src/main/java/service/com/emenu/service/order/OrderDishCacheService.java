package com.emenu.service.order;

import com.emenu.common.cache.order.OrderDishCache;
import com.emenu.common.cache.order.TableOrderCache;
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
    public void newDish(int tableId, OrderDishCache orderDishCache) throws SSException;

    /**
     * 删除菜品
     * @param tableId
     * @param orderDishCacheId
     * @throws SSException
     */
    public void delDish(int tableId, int orderDishCacheId) throws SSException;

    /**
     * 编辑菜品
     * @param tableId
     * @param orderDishCache
     * @throws SSException
     */
    public void updateDish(int tableId, OrderDishCache orderDishCache) throws SSException;

    /**
     * 根据餐台ID查询餐台点餐的缓存
     * @param tableId
     * @return
     * @throws SSException
     */
    public TableOrderCache listByTableId(int tableId) throws SSException;

    /**
     * 根据餐台ID清空餐台点菜的缓存
     * @param tableId
     * @return
     * @throws SSException
     */
    public void cleanCacheByTableId(int tableId) throws SSException;

    /**
     * 根据餐台ID加上锁
     * @param tableId
     * @return
     * @throws SSException
     */
    public void tableLock(int tableId) throws SSException;

    /**
     * 根据餐台ID解除锁
     * @param tableId
     * @return
     * @throws SSException
     */
    public void tableLockRemove(int tableId) throws SSException;

    /**
     * 设置正在下单顾客的Ip
     * @param Ip
     * @return
     * @throws SSException
     */
    public void setCurrentOperateCustomerIp(String Ip) throws SSException;

    /**
     * 获取正在下单顾客的Ip
     * @param
     * @return
     * @throws SSException
     */
    public String getCurrentOperateCustomerIp() throws SSException;

    /**
     * 交换两个餐台中的缓存
     * @param oldTableId
     * @param newTableId
     * @throws SSException
     */
    public void changeCache(int oldTableId, int newTableId) throws SSException;
}
