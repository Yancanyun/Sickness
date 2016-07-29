package com.emenu.service.order;

import com.pandawork.core.common.exception.SSException;

/**
 * AutoPrintOrderDishService
 * 智能排菜(自动打印菜品)Service
 * @author: quanyibo
 * @time: 2016/7/27
 */
public interface AutoPrintOrderDishService {

    /**
     * 构建排菜队列
     * 首先获取所有未结账的订单,再获取订单下的已下单的菜品
     * @param
     * @throws com.pandawork.core.common.exception.SSException
     */
    public void createQue() throws SSException;

    /**
     * 清空智能排菜队列
     * @param
     * @throws com.pandawork.core.common.exception.SSException
     */
    public void clearOrderDishQue() throws SSException;

    /**
     * 上菜扫码后对应的打印机正在做的菜品数量相应的减少
     * @param orderDishId
     * @throws com.pandawork.core.common.exception.SSException
     */
    public void reducePrinterMakeDishQuantity(Integer orderDishId) throws SSException;
}
