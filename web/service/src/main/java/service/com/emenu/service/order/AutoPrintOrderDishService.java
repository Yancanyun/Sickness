package com.emenu.service.order;

import com.emenu.common.dto.order.PrintOrderDishDto;
import com.pandawork.core.common.exception.SSException;

import java.util.Map;
import java.util.concurrent.ConcurrentLinkedQueue;

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

}
