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
     * 如果为套餐的话可能包含多个菜品
     * @param orderDishId
     * @param
     * @throws com.pandawork.core.common.exception.SSException
     */
    public List<PrintOrderDishDto> getPrintOrderDishDtoById(Integer orderDishId) throws SSException;

    /**
     * 打印一个订单菜品
     * @param printOrderDishDto 要打印的菜品信息
     * @throws com.pandawork.core.common.exception.SSException
     */
    public void printOrderDish(PrintOrderDishDto printOrderDishDto) throws SSException;


}
