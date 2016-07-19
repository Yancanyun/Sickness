package com.emenu.common.cache.order;

import java.util.List;

/**
 * TableOrderCache
 * 餐台点餐的缓存
 *
 * @author: yangch
 * @time: 2016/6/1 14:36
 */
public class TableOrderCache {
    // 本餐台中已点但仍未下单的全部菜品
    private List<OrderDishCache> orderDishCacheList;

    // 整单备注
    private String orderRemark;

    // 整单上菜方式(1-即起, 2-叫起)
    private Integer orderServeType;

    // 锁
    private Boolean lock = false;


    public List<OrderDishCache> getOrderDishCacheList() {
        return orderDishCacheList;
    }

    public void setOrderDishCacheList(List<OrderDishCache> orderDishCacheList) {
        this.orderDishCacheList = orderDishCacheList;
    }

    public String getOrderRemark() {
        return orderRemark;
    }

    public void setOrderRemark(String orderRemark) {
        this.orderRemark = orderRemark;
    }

    public Integer getOrderServeType() {
        return orderServeType;
    }

    public void setOrderServeType(Integer orderServeType) {
        this.orderServeType = orderServeType;
    }

    public Boolean getLock() {
        return lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }
}
