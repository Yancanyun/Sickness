package com.emenu.service.order.impl;

import com.emenu.common.entity.order.BackDish;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.order.BackDishService;
import com.emenu.service.order.OrderDishService;
import com.emenu.service.order.OrderService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 退菜service
 *
 * @author chenyuting
 * @date 2016/7/18 11:26
 */
@Service("backDishService")
public class BackDishServiceImpl implements BackDishService {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderDishService orderDishService;

    public void newBackDishByOrderDishId(Integer orderDishId, Float backNumber, String backRemarks, Integer partyId) throws SSException{
        try{
            if (Assert.lessOrEqualZero(orderDishId)){
                throw SSException.get(EmenuException.OrderDishIdError);
            }
            if (backNumber <= 0){
                throw SSException.get(EmenuException.BackOrderNumberError);
            }
            BackDish backDish = new BackDish();
            OrderDish orderDish = orderDishService.queryById(orderDishId);
            // Order order = orderService.queryById(orderDish.getOrderId());
            backDish.setId(orderDish.getDishId());
            backDish.setBackNumber(backNumber);
            backDish.setBackRemarks(backRemarks);
            backDish.setTasteId(orderDish.getTasteId());
            backDish.setEmployeePartyId(partyId);
            Date date = new Date();
            backDish.setBackTime(date);
            commonDao.insert(backDish);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.BackOrderDishFailed, e);
        }
    }
}