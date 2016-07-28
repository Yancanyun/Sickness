package com.emenu.service.order.impl;

import com.emenu.common.cache.order.OrderDishCache;
import com.emenu.common.cache.order.TableOrderCache;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.dish.DishService;
import com.emenu.service.order.OrderDishCacheService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * OrderDishCacheServiceImpl
 *
 * @author: yangch
 * @time: 2016/5/31 17:14
 */
@Service("orderDishCacheService")
public class OrderDishCacheServiceImpl implements OrderDishCacheService {

    // 餐台点餐缓存的Map
    private Map<Integer, TableOrderCache> tableOrderCacheMap = new ConcurrentHashMap<Integer, TableOrderCache>();

    // 点的菜品存入OrderDishCache中的ID
    private int orderDishCacheId = 0;

    //当前正在下单的用户的Ip,用来判断如果是这个用户在确认订单的时候刷新了页面,要解除缓存的锁
    private String currentOperateCustomerIp;

    @Autowired
    DishService dishService;

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void newDish(int tableId, OrderDishCache orderDishCache) throws SSException {
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

            // 若OrderDishCache中没有数量，则是快捷点餐
            Boolean isQuickly = false;
            if (orderDishCache.getQuantity() == null) {
                isQuickly = true;
                orderDishCache.setQuantity(new Float(1)); // 把快捷点餐的菜品数量设为1
            }

            // 如果本餐台的点菜缓存是空的，则不需要接下来的判断，直接把数据放缓存里
            if (orderDishCacheList == null) {
                orderDishCache.setId(++orderDishCacheId); // 设置OrderDishCache中的ID
                orderDishCacheList = new ArrayList<OrderDishCache>();
                orderDishCacheList.add(orderDishCache);
            } else {
                // 若不是快捷点餐，直接将本次点餐加入缓存
                if (isQuickly == false) {
                    orderDishCache.setId(++orderDishCacheId); // 设置OrderDishCache中的ID
                    orderDishCacheList.add(orderDishCache);
                }
                // 若本次点餐是快捷点餐，则寻找OrderDishCacheList中同DishID的快捷点餐记录
                // (口味、备注、上菜方式均为空的记录即为快捷点餐)，有的话直接修改原有的记录
                else {
                    Boolean isAdded = false; // 是否已将本次快捷点餐与之前的快捷点餐进行了合并
                    for (OrderDishCache orderDishCache1 : orderDishCacheList) {
                        if (orderDishCache1.getDishId().equals(orderDishCache.getDishId())
                                && orderDishCache1.getTasteId() == null
                                && orderDishCache1.getServeType() == null
                                && orderDishCache1.getRemark() == null) {
                            orderDishCache.setId(orderDishCache1.getId());
                            orderDishCache.setQuantity(orderDishCache1.getQuantity() + 1);
                            orderDishCacheList.remove(orderDishCache1);
                            orderDishCacheList.add(orderDishCache);
                            isAdded = true; // 已将本次快捷点餐与之前的快捷点餐进行了合并
                            break;
                        }
                    }
                    // 若OrderDishCacheList中不存在同DishID的快捷点餐记录，则将本次快捷点餐加入缓存
                    if (isAdded == false) {
                        orderDishCache.setId(++orderDishCacheId); // 设置OrderDishCache中的ID
                        orderDishCacheList.add(orderDishCache);
                    }
                }
            }

            tableOrderCache.setOrderDishCacheList(orderDishCacheList);
            tableOrderCacheMap.put(tableId, tableOrderCache);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewDishError, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void delDish(int tableId, int orderDishCacheId) throws SSException {
        try {
            // 从缓存中取出本餐台的餐台点餐缓存(TableOrderCache)
            TableOrderCache tableOrderCache = tableOrderCacheMap.get(tableId);

            // 若缓存中不存在本餐台的TableOrderCache，则不可能进行删除操作，直接报错
            if (tableOrderCache == null) {
                throw SSException.get(EmenuException.TableIsNotHaveAnyDish);
            }

            // 若已被加锁，则不允许执行接下来的操作
            if (tableOrderCache.getLock() == true) {
                throw SSException.get(EmenuException.TableIsLock);
            }

            // 从TableOrderCache中获取本餐台中已点但仍未下单的全部菜品(OrderDishCacheList)
            List<OrderDishCache> orderDishCacheList = tableOrderCache.getOrderDishCacheList();

            // 若本餐台的OrderDishCacheList为空，则不可能进行删除操作，直接报错
            if (orderDishCacheList == null || orderDishCacheList.size() == 0) {
                throw SSException.get(EmenuException.TableIsNotHaveAnyDish);
            }


            // 从OrderDishCacheList寻找要删除的OrderDishCache，删除之
            for (OrderDishCache orderDishCache : orderDishCacheList) {
                if (orderDishCache.getId().equals(orderDishCacheId)) {
                    orderDishCacheList.remove(orderDishCache);
                    break;
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelDishError, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void updateDish(int tableId, OrderDishCache orderDishCache) throws SSException {
        try {
            // 从缓存中取出本餐台的餐台点餐缓存(TableOrderCache)
            TableOrderCache tableOrderCache = tableOrderCacheMap.get(tableId);

            // 若缓存中不存在本餐台的TableOrderCache，则不可能进行编辑操作，直接报错
            if (tableOrderCache == null) {
                throw SSException.get(EmenuException.TableIsNotHaveAnyDish);
            }

            // 若已被加锁，则不允许执行接下来的操作
            if (tableOrderCache.getLock() == true) {
                throw SSException.get(EmenuException.TableIsLock);
            }

            // 从TableOrderCache中获取本餐台中已点但仍未下单的全部菜品(OrderDishCacheList)
            List<OrderDishCache> orderDishCacheList = tableOrderCache.getOrderDishCacheList();

            // 若本餐台的OrderDishCacheList为空，则不可能进行编辑操作，直接报错
            if (orderDishCacheList == null || orderDishCacheList.size() == 0) {
                throw SSException.get(EmenuException.TableIsNotHaveAnyDish);
            }

            // 从OrderDishCacheList寻找要编辑的OrderDishCache，编辑之
            for (OrderDishCache orderDishCache1 : orderDishCacheList) {
                if (orderDishCache1.getId().equals(orderDishCache)) {
                    orderDishCacheList.remove(orderDishCache1);
                    orderDishCacheList.add(orderDishCache);
                    break;
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateDishError, e);
        }
    }

    @Override
    public TableOrderCache listByTableId(int tableId) throws SSException {
        try {
            return tableOrderCacheMap.get(tableId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryDishError, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void cleanCacheByTableId(int tableId) throws SSException {
        try {
            // 从缓存中取出本餐台的餐台点餐缓存(TableOrderCache)
            TableOrderCache tableOrderCache = tableOrderCacheMap.get(tableId);

            // 若已被加锁，则不允许执行接下来的操作
            if (tableOrderCache != null && tableOrderCache.getLock() == true) {
                throw SSException.get(EmenuException.TableIsLock);
            }

            tableOrderCacheMap.remove(tableId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CleanTableCacheError, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void tableLock(int tableId) throws SSException
    {
        try {
            // 从缓存中取出本餐台的餐台点餐缓存(TableOrderCache)
            TableOrderCache tableOrderCache = tableOrderCacheMap.get(tableId);
            // 加上锁
            if(tableOrderCache!=null)
            {
                tableOrderCache.setLock(true);
                tableOrderCacheMap.put(tableId,tableOrderCache);
            }


        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.TableLockFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void tableLockRemove(int tableId) throws SSException
    {
        try {
            // 从缓存中取出本餐台的餐台点餐缓存(TableOrderCache)
            TableOrderCache tableOrderCache = tableOrderCacheMap.get(tableId);
            // 解除锁
            if(tableOrderCache!=null)
            {
                tableOrderCache.setLock(false);
                tableOrderCacheMap.put(tableId,tableOrderCache);
            }


        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.TableLockRemoveFail, e);
        }
    }

    @Override
    public void setCurrentOperateCustomerIp(String Ip) throws SSException
    {
        try {
                this.currentOperateCustomerIp=Ip;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SetCurrentOperateCustomerIpFail, e);
        }
    }

    @Override
    public String getCurrentOperateCustomerIp() throws SSException
    {
        try {
                return this.currentOperateCustomerIp;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetCurrentOperateCustomerIpFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void changeCache(int oldTableId, int newTableId) throws SSException {
        try {
            // 从缓存中取出旧餐台的餐台点餐缓存(TableOrderCache)
            TableOrderCache oldTableOrderCache = tableOrderCacheMap.get(oldTableId);

            // 从缓存中取出新餐台的餐台点餐缓存(TableOrderCache)
            TableOrderCache newTableOrderCache = tableOrderCacheMap.get(newTableId);

            // 若已被加锁，则不允许执行接下来的操作
            if (oldTableOrderCache != null && oldTableOrderCache.getLock() == true) {
                throw SSException.get(EmenuException.TableIsLock);
            }
            if (newTableOrderCache != null && newTableOrderCache.getLock() == true) {
                throw SSException.get(EmenuException.TableIsLock);
            }

            tableOrderCacheMap.remove(oldTableId);
            tableOrderCacheMap.remove(newTableId);
            tableOrderCacheMap.put(oldTableId, newTableOrderCache);
            tableOrderCacheMap.put(newTableId, oldTableOrderCache);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CleanTableCacheError, e);
        }
    }

    @Override
    public BigDecimal returnTotalMoneyByTableId(int tableId) throws SSException
    {
        BigDecimal totalMoney = new BigDecimal(0);
        TableOrderCache tableOrderCache = new TableOrderCache();
        List<OrderDishCache> orderDishCaches = new ArrayList<OrderDishCache>();
        // 保留两位小数
        java.text.DecimalFormat  df=new java.text.DecimalFormat("#.00");
        try {
            if(!Assert.lessOrEqualZero(tableId))
            tableOrderCache = tableOrderCacheMap.get(tableId);
            if(tableOrderCache!=null)
                orderDishCaches = tableOrderCache.getOrderDishCacheList();
            if(orderDishCaches!=null&&!orderDishCaches.isEmpty())
            {
                for(OrderDishCache dto : orderDishCaches)
                {
                    DishDto dishDto = new DishDto();
                    dishDto = dishService.queryById(dto.getDishId());//查询菜品信息
                    totalMoney=totalMoney.add(new BigDecimal(dishDto.getSalePrice().floatValue() *dto.getQuantity()));
                }
            }
            // 保留两位小数
            String temp =df.format(totalMoney);
            totalMoney = new BigDecimal(temp);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ReturnTotalMoneyFail, e);
        }
        return totalMoney;
    }
}
