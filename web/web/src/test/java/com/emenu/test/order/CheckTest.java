package com.emenu.test.order;

import com.emenu.common.entity.order.Checkout;
import com.emenu.common.entity.order.CheckoutPay;
import com.emenu.common.enums.order.CheckoutTypeEnums;
import com.emenu.service.order.CheckoutService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Date;

/**
 * CheckTest
 *
 * @author xubaorong
 * @date 2016/6/3.
 */
public class CheckTest extends AbstractTestCase {

    @Autowired
    private CheckoutService checkoutService;
    @Test
    public void queryByTableId() throws SSException{
        Checkout checkout = checkoutService.queryByTableIdAndStatus(1,1);
        if(checkout!=null){
            System.out.print(checkout.getId());
        }
    }

    @Test
    public void newCheckout() throws SSException{
        Checkout checkout = new Checkout();
        checkout.setStatus(1);
        checkout.setTableId(56);
        checkout.setCheckoutTime(new Date());

        checkoutService.newCheckout(checkout);
    }

    @Test
    public void updateCheckout() throws SSException{
        Checkout checkout = new Checkout();
        checkout.setId(1);
        checkout.setStatus(1);
        checkout.setTableId(56);
        checkout.setCheckoutTime(new Date());
        checkoutService.updateCheckout(checkout);
    }

    @Test
    public void checkout() throws SSException {
        int orderId = 91;
        Checkout checkout = checkoutService.queryByTableId(56);
        checkout.setCheckerPartyId(1);
        checkout.setWipeZeroMoney(new BigDecimal(48));
        checkout.setTotalPayMoney(new BigDecimal(1300));

        CheckoutPay checkoutPay = new CheckoutPay();
        checkoutPay.setPayMoney(checkout.getShouldPayMoney());
        checkoutPay.setCheckoutId(checkout.getId());
        checkoutPay.setCheckoutType(CheckoutTypeEnums.Alipay.getId());
        checkoutPay.setSerialNum("qwerty001");

        checkoutService.checkout(orderId, checkout, checkoutPay);
    }
}
