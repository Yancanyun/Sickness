package com.emenu.service.order;

import com.emenu.common.dto.order.CheckOrderDto;
import com.emenu.common.entity.order.Order;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * OrderService
 *
 * @author xubaorong
 * @date 2016/6/3.
 */
public interface OrderService {

    /**
     * 根据桌号和订单状态查询订单列表
     * @param tableId
     * @param status
     * @return
     * @throws Exception
     */
    public List<Order> listByTableIdAndStatus(int tableId, int status) throws SSException;

    /**
     * 添加订单
     * @param order
     * @throws SSException
     */
    public void newOrder(Order order) throws SSException;

    /**
     * 修改订单
     * @param order
     * @throws SSException
     */
    public void updateOrder(Order order) throws SSException;

    /**
     * 根据订单状态,盘点状态和当前时间之前查询订单盘点dto
     * 订单dto包括order和对应的orderDish
     * @param status,isSettlemented,date
     * @return
     * @throws Exception
     */
    public List<CheckOrderDto> listCheckOrderDtoForCheck(Integer status
            ,Integer isSettlemented
            ,Date date) throws SSException;

    /**
     * 修改订单盘点状态
     * @param id,isSettlemented
     * @return
     * @throws Exception
     */
    public void updateOrderIsSettlementedById(int id , int isSettlemented) throws SSException;
}
