package com.emenu.service.order;

import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;
import org.apache.xpath.operations.Or;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.List;

/**
 * OrderDishService
 *
 * @author xubaorong
 * @date 2016/6/3.
 */
public interface OrderDishService {

    /**
     * 根据订单id查询订单菜品列表
     * @param orderId
     * @return
     * @throws Exception
     */
    public List<OrderDish> listByOrderId(int orderId) throws SSException;

    /**
     * 根据订单菜品主键id查询订单菜品
     * @param id
     * @return
     * @throws Exception
     */
    public OrderDish queryById(int id) throws SSException;

    /**
     * 根据订单id查询订单菜品列表dto列表
     * @param orderId
     * @return
     * @throws Exception
     */
    public List<OrderDishDto> listDtoByOrderId(int orderId) throws SSException;

    /**
     * 根据订单菜品主键id查询订单菜品dto
     * @param id
     * @return
     * @throws Exception
     */
    public OrderDishDto queryDtoById(int id) throws SSException;

    /**
     * 根据订单菜品主键id修改上菜状态，下单，正在做，已上菜，1,2,3
     * @param id
     * @param status
     * @throws Exception
     */
    public void updateDishStatus(int id,int status) throws SSException;

    /**
     *根据订单菜品主键id修改服务类型，即起1，叫起2
     * @param id
     * @param serveType
     * @throws Exception
     */
    public void updateServeType(int id,int serveType)throws SSException;

    /**
     * 根据订单菜品主键id确定是否赠送
     * @param id
     * @param isPresentedDish
     * @throws Exception
     */
    public void updatePresentedDish(int id,int isPresentedDish) throws SSException;

    /**
     *修改订单菜品
     * @param orderDish
     * @throws SSException
     */
    public void updateOrderDish(OrderDish orderDish) throws SSException;

    /**
     *添加一个订单菜品
     * @param orderDish
     * @throws SSException
     */
    public void newOrderDish(OrderDish orderDish) throws SSException;

    /**
     * 批量添加订单菜品
     * @param orderDishs
     * @throws SSException
     */
    public void newOrderDishs(List<OrderDish> orderDishs) throws SSException;

    /**
     * 判断一个餐桌是否有未上的菜
     * @param tableId
     * @throws SSException
     */
    public int isTableHaveOrderDish(Integer tableId) throws SSException;
}
