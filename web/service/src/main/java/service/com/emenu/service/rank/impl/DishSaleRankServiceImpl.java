package com.emenu.service.rank.impl;

import com.emenu.common.dto.order.CheckOrderDto;
import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.enums.order.OrderDishPresentedEnums;
import com.emenu.common.enums.order.OrderDishStatusEnums;
import com.emenu.service.order.OrderService;
import com.emenu.service.rank.DishSaleRankService;
import com.pandawork.core.common.exception.SSException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/7/26.
 */
@Service("dishSaleRankService")
public class DishSaleRankServiceImpl implements DishSaleRankService {
    @Autowired private OrderService orderService;

    @Override
    public List<OrderDish> queryOrderDishByTimePeroid(Date startTime ,Date endTime) throws SSException{
        // 得到这个时间段中所下的订单，CheckOrderDto里面包含订单信息和订单菜品信息
        List<CheckOrderDto> checkOrderDtoList= orderService.queryOrderByTimePeroid2(startTime, endTime);
        List<OrderDish> orderDishList = new ArrayList<OrderDish>();
        for(CheckOrderDto checkOrderDto :checkOrderDtoList){
            // 某个订单的订单菜品的List，中间可能包含赠送的和退菜的
            List<OrderDish> list = checkOrderDto.getOrderDishs();
            // 把集合中退菜和赠菜的菜品去除
            for(OrderDish orderDish :list){
                if(orderDish.getIsPresentedDish() != OrderDishPresentedEnums.IsPresentedDish.getId()
                        ||orderDish.getIsPresentedDish() != OrderDishStatusEnums.IsBack.getId()){
                    orderDishList.add(orderDish);
                }
            }
        }
        return orderDishList;
    }


}
