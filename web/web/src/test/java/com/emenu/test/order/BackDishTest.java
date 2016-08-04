package com.emenu.test.order;

import com.emenu.common.entity.order.BackDish;
import com.emenu.service.order.BackDishService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author chenyuting
 * @date 2016/7/20 15:32
 */
public class BackDishTest extends AbstractTestCase{

    @Autowired
    private BackDishService backDishService;

    @Test
    public void backDishByOrderDishId() throws SSException{
        Integer orderDishId = 381;
        Float backNumber = 1f;
        String backRemarks = "48khkcvhk";
        Integer partyId = 2;
        backDishService.backDishByOrderDishId(orderDishId, backNumber, backRemarks, partyId);
        System.out.println("退菜成功");
    }

    @Test
    public void queryBackDishListByOrderId() throws SSException{
        Integer orderId = 182;
        List<BackDish> backDishList = backDishService.queryBackDishListByOrderId(orderId);
        for (BackDish backDish: backDishList){
            System.out.print("订单菜品id：" + backDish.getOrderDishId());
            System.out.println(" 退菜数量：" + backDish.getBackNumber());
        }
    }

    @Test
    public void testQueryById() throws SSException{

        backDishService.queryBackDishById(22);
    }
}