package com.emenu.service.order.impl;

import com.emenu.common.entity.order.Order;
import com.emenu.common.enums.order.OrderStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.order.OrderMapper;
import com.emenu.service.order.OrderService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.Log;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
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
}
