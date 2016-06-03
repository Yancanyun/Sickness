package com.emenu.test.order;

import com.emenu.common.entity.order.Checkout;
import com.emenu.service.order.CheckoutServcie;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

/**
 * CheckTest
 *
 * @author xubaorong
 * @date 2016/6/3.
 */
public class CheckTest extends AbstractTestCase {

    @Autowired
    private CheckoutServcie checkoutService;
    @Test
    public void queryByTableId() throws SSException{
        Checkout checkout = checkoutService.queryByTableId(1,1);
        if(checkout!=null){
            System.out.print(checkout.getId());
        }
    }

    @Test
    public void newCheckout() throws SSException{
        Checkout checkout = new Checkout();
        checkout.setStatus(1);
        checkout.setTableId(1);
        checkout.setCheckoutTime(new Date());
        checkoutService.newCheckout(checkout);
    }

    @Test
    public void updateCheckout() throws SSException{
        Checkout checkout = new Checkout();
        checkout.setId(1);
        checkout.setStatus(1);
        checkout.setTableId(1);
        checkout.setCheckoutTime(new Date());
        checkoutService.updateCheckout(checkout);
    }
}
