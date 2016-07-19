package com.emenu.service.order.impl;

import com.emenu.common.entity.order.CheckoutPay;
import com.emenu.common.entity.order.Order;
import com.emenu.common.enums.order.OrderStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.order.CheckoutPayMapper;
import com.emenu.service.order.CheckoutPayService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * CheckoutPayServiceImpl
 *
 * @author: yangch
 * @time: 2016/7/19 19:24
 */
@Service("checkoutPayService")
public class CheckoutPayServiceImpl implements CheckoutPayService {
    @Autowired
    private CommonDao commonDao;

    @Autowired
    private CheckoutPayMapper checkoutPayMapper;

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void newCheckoutPay (CheckoutPay checkoutPay) throws SSException {
        try {
            if (Assert.isNull(checkoutPay)) {
                throw SSException.get(EmenuException.CheckoutPayIsNull);
            }

            commonDao.insert(checkoutPay);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewCheckoutPayFailed, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void updateCheckoutPay (CheckoutPay checkoutPay) throws SSException {
        try {
            if (Assert.isNull(checkoutPay)) {
                throw SSException.get(EmenuException.CheckoutPayIsNull);
            }

            commonDao.update(checkoutPay);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateCheckoutPayFailed, e);
        }
    }

    @Override
    public CheckoutPay queryByCheckoutId (int checkoutId) throws SSException {
        try {
            Assert.lessOrEqualZero(checkoutId, EmenuException.CheckoutIdError);

            return checkoutPayMapper.queryByCheckoutId(checkoutId);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryCheckoutPayFailed, e);
        }
    }
}
