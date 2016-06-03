package com.emenu.service.order;

import com.emenu.common.entity.order.Order;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

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
}
