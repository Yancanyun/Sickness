package com.emenu.common.dto.order;

import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;

import java.util.List;

/**
 * CheckOrderDto
 * 订单盘点dto
 *
 * @author: quanyibo
 * @time: 2016/7/12
 **/
public class CheckOrderDto {

    private Order order;

    private List<OrderDish> orderDishs;

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public List<OrderDish> getOrderDishs() {
        return orderDishs;
    }

    public void setOrderDishs(List<OrderDish> orderDishs) {
        this.orderDishs = orderDishs;
    }
}
