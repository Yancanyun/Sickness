package com.emenu.test.order;

import com.emenu.common.entity.order.Order;
import com.emenu.service.order.OrderService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.CollectionFactory;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * OrderTest
 *
 * @author xubaorong
 * @date 2016/6/3.
 */
public class OrderTest extends AbstractTestCase {
    @Autowired
    private OrderService orderService;

    @Test
    public void newOrder() throws SSException{
        Order order = new Order();
        order.setLoginType(1);
        order.setOrderRemark("都多放点辣");
        order.setOrderServeType(1);
        order.setStatus(2);
        order.setTableId(1);
        orderService.newOrder(order);
    }

    @Test
    public void updateOrder() throws SSException{
        Order order = new Order();
        order.setId(1);
        order.setLoginType(2);
        order.setOrderRemark("都多放点盐呗");
        order.setOrderServeType(2);
        order.setStatus(1);
        order.setTableId(1);
        orderService.updateOrder(order);
    }

    @Test
    public void listByTableIdAndStatus() throws SSException{
        List<Order> orderList = Collections.EMPTY_LIST;
        orderList = orderService.listByTableIdAndStatus(4,1);
        for(Order order:orderList){
            System.out.print(order.getOrderRemark());
        }
    }

    @Test
    public void testListCheckOrderDtoForCheck()throws SSException
    {
        Integer status = null;
        System.out.println(orderService.listCheckOrderDtoForCheck(status, 0, new Date()));
    }

    @Test
    public void testUpdateIsSettlement()throws SSException
    {
        orderService.updateOrderIsSettlementedById(29,1);
    }

    @Test
    public void testQueryOrderByTime2()throws SSException
    {
        orderService.queryOrderByTimePeroid2(new Date(), new Date());
    }

    @Test
    public void testQueryOrderByTime1()throws SSException
    {
        Date startTime = new Date();
        startTime.setTime(9999);
        orderService.queryOrderByTimePeroid1(startTime,new Date());
    }

    @Test
    public void testQueryOrderByCheckoutId() throws SSException{

        orderService.queryOrdersByCheckoutId(40);
    }
}
