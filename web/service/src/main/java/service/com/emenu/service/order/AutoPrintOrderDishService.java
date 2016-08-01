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

    /**
     * 清空智能排菜队列
     * @param
     * @throws com.pandawork.core.common.exception.SSException
     */
    public void clearOrderDishQue() throws SSException;

    /**
     * 获取智能排菜队列
     * @param
     * @throws com.pandawork.core.common.exception.SSException
     */
    public ConcurrentLinkedQueue<PrintOrderDishDto> getOrderDishQue() throws SSException;

    /**
     * 获取打印机打印出的正在做菜品的map
     *
     * @param
     * @throws com.pandawork.core.common.exception.SSException
     */
    public Map<String, Integer> getPrinterPrintTotalDishMap() throws SSException;

    /**
     * 更新打印机打印出的正在做菜品的map
     *
     * @param
     * @throws com.pandawork.core.common.exception.SSException
     */
    public void updatePrinterPrintTotalDishMap(String printerIp,Integer dishQuantity) throws SSException;

}
