package com.emenu.service.order.impl;

import com.emenu.common.entity.order.Checkout;
import com.emenu.common.entity.order.Order;
import com.emenu.common.enums.order.OrderStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.order.CheckoutMapper;
import com.emenu.service.order.CheckoutServcie;
import com.emenu.service.order.OrderService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CheckoutServiceImpl
 *
 * @author xubaorong
 * @date 2016/6/3.
 */
@Service("checkoutServcie")
public class CheckoutServiceImpl implements CheckoutServcie{

    @Autowired
    private CheckoutMapper checkoutMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CommonDao commonDao;

    @Override
    public Checkout queryByTableId(int tableId,int status) throws SSException {
        Checkout checkout = null;
        try {
            Assert.lessOrEqualZero(tableId,EmenuException.TableIdError);
            checkout = checkoutMapper.queryByTableId(tableId,status);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryCheckoutByTableIdFailed);
        }
        return checkout;
    }

    @Override
    public Checkout newCheckout(Checkout checkout) throws SSException {
        Checkout checkout1 = null;
        try{
            Assert.isNotNull(checkout, EmenuException.CheckoutIsNotNull);
            checkout1= commonDao.insert(checkout);
            Assert.isNotNull(checkout,EmenuException.CheckoutIsNotNull);
            List<Order> orderList = orderService.listByTableIdAndStatus(checkout.getTableId(), OrderStatusEnums.IsBooked.getId());
            for(Order order:orderList){
                order.setCheckoutId(checkout1.getId());
                orderService.updateOrder(order);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewCheckoutFailed);
        }
        return checkout1;
    }

    @Override
    public void updateCheckout(Checkout checkout) throws SSException {
        try{
            Assert.isNotNull(checkout, EmenuException.CheckoutIsNotNull);
            commonDao.update(checkout);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateCheckoutFailed);
        }
    }
}
