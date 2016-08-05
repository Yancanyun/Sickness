package com.emenu.service.order;

import com.emenu.common.dto.order.PrintOrderDishDto;
import com.pandawork.core.common.exception.SSException;
import org.springframework.jdbc.datasource.init.ScriptStatementFailedException;

import java.util.List;

/**
 * OrderDishPrintService
 * 后厨管理端打印订单菜品
 *
 * @author: quanyibo
 * @time: 2016/6/24
 */
public interface OrderDishPrintService {

    /**
     * 根据订单菜品Id打印订单菜品
     * @param orderDishId
     * @param
     * @throws com.pandawork.core.common.exception.SSException
     */
    public void printOrderDishById(Integer orderDishId) throws SSException;

    /**
     * 根据订单菜品Id获取打印信息
     * 这个版本的话套餐的话拆成单个菜品存了
     * 不用考虑一个套餐包含多个菜品
     * @param orderDishId
     * @param
     * @throws com.pandawork.core.common.exception.SSException
     */
    public PrintOrderDishDto getPrintOrderDishDtoById(Integer orderDishId) throws SSException;

    /**
     * 打印一个订单菜品
     * @param printOrderDishDto 要打印的菜品信息
     * @throws com.pandawork.core.common.exception.SSException
     */
    public void printOrderDish(PrintOrderDishDto printOrderDishDto) throws SSException;

    /**
     * 检查所有已下单的菜品的打印机是否已设置和是否能成功和打印机成功进行连接
     * 即是否能成功的打印出菜品
     *
     * @param
     * @return 返回异常的信息
     * @throws com.pandawork.core.common.exception.SSException
     */
    public String checkOrderDishPrinter() throws SSException;

    /**
     * 店家可在小类上设置下单后自动打印
     * 下单时判断哪些菜品自动打印然后自动打印出来
     *
     * @param orderId
     * @return 返回异常的信息
     * @throws com.pandawork.core.common.exception.SSException
     */
    public void autoPrintOrderDish (Integer orderId) throws SSException;
}
