package com.emenu.service.order.impl;

import com.emenu.common.dto.order.CheckOrderDto;
import com.emenu.common.entity.order.Order;
import com.emenu.common.enums.order.OrderStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.order.OrderMapper;
import com.emenu.service.order.OrderDishService;
import com.emenu.service.order.OrderService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.Log;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * OrderServiceImpl
 *
 * @author xubaorong
 * @date 2016/6/3.
 */
@Service("orderService")
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private OrderDishService orderDishService;

    @Override
    public List<Order> listByTableIdAndStatus(int tableId, int status) throws SSException {
        List<Order> orderList = Collections.emptyList();
        try{
            Assert.lessOrEqualZero(tableId, EmenuException.TableIdError);
            Assert.lessOrEqualZero(status,EmenuException.OrderStatusError);
            if(status==1){
                status = OrderStatusEnums.IsBooked.getId();
            }
            if(status == 2){
                status = OrderStatusEnums.IsCheckouted.getId();
            }
            orderList = orderMapper.listByTableIdAndStatus(tableId,status);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListByTableIdAndStatusFailed);
        }
        return orderList;
    }

    @Override
    public void newOrder(Order order) throws SSException {
        try{
            Assert.isNotNull(order,EmenuException.OrderIsNotNull);
            commonDao.insert(order);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewOrderFailed);
        }
    }

    @Override
    public void updateOrder(Order order) throws SSException {
        try{
            Assert.isNotNull(order,EmenuException.OrderIsNotNull);
            commonDao.update(order);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateOrderFailed);
        }
    }

    @Override
    public List<CheckOrderDto> listCheckOrderDtoForCheck(Integer status
            ,Integer isSettlemented
            ,Date date) throws SSException
    {
        List<CheckOrderDto> checkOrderDtos = new ArrayList<CheckOrderDto>();
        List<Order> orders = new ArrayList<Order>();
        try{
            if(!Assert.lessOrEqualZero(status)
                    &&Assert.isNotNull(isSettlemented)
                    &&Assert.isNotNull(date))
            {
                //查询出对应的订单
                orders = orderMapper.listOrderByStatusAndIsCheckAndDate(status,isSettlemented,date);
                for(Order dto : orders)
                {
                    CheckOrderDto checkOrderDto = new CheckOrderDto();
                    checkOrderDto.setOrder(dto);
                    checkOrderDto.setOrderDishs(orderDishService.listByOrderId(dto.getId()));
                }
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryCheckOrderDtoFail);
        }
        return checkOrderDtos;
    }
    @Override
    public void updateOrderIsSettlementedById(int id , int isSettlemented) throws SSException
    {
        Order order = new Order();
        try{
            if(!Assert.lessOrEqualZero(id)&&Assert.isNotNull(isSettlemented))
            order=orderMapper.queryOrderById(id);
            if(order!=null)
            {
                order.setIsSettlemented(isSettlemented);
                commonDao.update(order);
            }
            else
                throw SSException.get(EmenuException.OrderNotExist);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateOrderIsSettlementedFail);
        }
    }
}
