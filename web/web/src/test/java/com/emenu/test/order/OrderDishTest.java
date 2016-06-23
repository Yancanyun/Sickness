package com.emenu.test.order;

import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.service.order.OrderDishService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * OrderDishTest
 *
 * @author xubaorong
 * @date 2016/6/3.
 */
public class OrderDishTest extends AbstractTestCase {
    @Autowired
    private OrderDishService orderDishService;

    @Test
    public void newOrderDish() throws SSException{
       /* OrderDish orderDish = new OrderDish();
        orderDish.setStatus(1);
        orderDish.setDishId(1);
        orderDish.setDishQuantity((float) 1);
        orderDish.setIsPackage(0);
        orderDish.setIsPresentedDish(0);
        orderDish.setOrderId(1);
        orderDish.setPresentedRemarkId(1);
        orderDish.setOrderTime(new Date());
        orderDish.setRemark("少放盐");
        orderDish.setSalePrice(new BigDecimal(18.00));
        orderDish.setServeType(2);
        orderDish.setTasteId(1);
        orderDishService.newOrderDish(orderDish);*/
        List<OrderDish> orderDishs = new ArrayList<OrderDish>();
        for(int i=2;i<=5;i++) {
            OrderDish orderDish1 = new OrderDish();
            orderDish1.setStatus(1);
            orderDish1.setDishId(i);
            orderDish1.setDishQuantity((float) 1);
            orderDish1.setIsPackage(0);
            orderDish1.setIsPresentedDish(0);
            orderDish1.setOrderId(2);
            orderDish1.setPresentedRemarkId(i);
            orderDish1.setOrderTime(new Date());
            orderDish1.setRemark("少放盐"+i+i+i);
            orderDish1.setSalePrice(new BigDecimal(18.00));
            orderDish1.setServeType(2);
            orderDish1.setTasteId(i);
            orderDish1.setIsCall(1);
            orderDish1.setIsChange(0);
            orderDishs.add(orderDish1);
        }
        orderDishService.newOrderDishs(orderDishs);
    }

    @Test
    public void listByOrderId() throws SSException{
        List<OrderDish> orderDishs = Collections.emptyList();
        orderDishs = orderDishService.listByOrderId(2);
        for(OrderDish orderDish:orderDishs){
            System.out.print(orderDish.getDishId()+orderDish.getPresentedRemarkId());
        }
    }

    @Test
    public void queryById() throws SSException{
        OrderDish orderDish = orderDishService.queryById(2);
        if(orderDish!=null){
            System.out.print(orderDish.getDishId()+orderDish.getPresentedRemarkId());
        }
    }

    @Test
    public void listDtoByOrderId() throws SSException{
        List<OrderDishDto> orderDishDtos = Collections.emptyList();
        orderDishDtos = orderDishService.listDtoByOrderId(2);
        for(OrderDishDto orderDishDto:orderDishDtos){
            System.out.print(orderDishDto.getDishName()+orderDishDto.getPresentedRemarkName());
        }
    }

    @Test
    public void queryDtoById() throws SSException{
        OrderDishDto orderDishDto = orderDishService.queryDtoById(2);
        if(orderDishDto!=null){
            System.out.print(orderDishDto.getDishName()+orderDishDto.getPresentedRemarkName());
        }
    }

    @Test
    public void updateDishStatus() throws SSException{
        orderDishService.updateDishStatus(3,2);
    }

    @Test
    public void updateServeType()throws SSException{
        orderDishService.updateServeType(3,1);
    }

    @Test
    public void updatePresentedDish() throws SSException{
        orderDishService.updatePresentedDish(3,1);
    }

    @Test
    public void updateOrderDish() throws SSException{
        OrderDish orderDish = new OrderDish();
        orderDish.setId(3);
        orderDish.setStatus(2);
        orderDish.setDishId(1);
        orderDish.setDishQuantity((float) 1);
        orderDish.setIsPackage(0);
        orderDish.setIsPresentedDish(0);
        orderDish.setOrderId(1);
        orderDish.setPresentedRemarkId(1);
        orderDish.setOrderTime(new Date());
        orderDish.setRemark("少放盐少放盐少放盐少放盐少放盐少放盐");
        orderDish.setSalePrice(new BigDecimal(18.00));
        orderDish.setServeType(2);
        orderDish.setTasteId(2);

        orderDish.setIsCall(0);
        orderDish.setIsChange(1);
    }
    @Test
    public void testIsTableHaveOrderDish() throws SSException
    {
        System.out.println(orderDishService.isTableHaveOrderDish(1));
    }
}
