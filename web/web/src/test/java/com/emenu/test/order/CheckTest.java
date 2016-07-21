package com.emenu.test.order;

import com.emenu.common.entity.order.Checkout;
import com.emenu.common.entity.order.CheckoutPay;
import com.emenu.common.enums.checkout.CheckOutStatusEnums;
import com.emenu.common.enums.order.CheckoutConsumptionTypeEnums;
import com.emenu.common.enums.order.CheckoutTypeEnums;
import com.emenu.common.enums.order.OrderStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.order.CheckoutService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.util.Assert;
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
    public void calcConsumptionMoney() throws SSException {
        System.out.println(checkoutService.calcConsumptionMoney(17));
    }

    @Test
    public void checkout() throws SSException {
        int tableId = 17;
        int partyId = 1;
        BigDecimal consumptionMoney = checkoutService.calcConsumptionMoney(tableId);
        BigDecimal wipeZeroMoney = new BigDecimal(1.7);
        BigDecimal totalPayMoney = new BigDecimal(200);
        int checkoutType = CheckoutTypeEnums.Alipay.getId();
        String serialNum = "Alipay0001";
        int isInvoiced = 0;

        checkoutService.checkout(tableId, partyId, consumptionMoney, wipeZeroMoney,
                totalPayMoney, checkoutType, serialNum, isInvoiced);
    }

    @Test
    public void freeOrder() throws SSException {
        int tableId = 17;
        int partyId = 1;
        BigDecimal consumptionMoney = checkoutService.calcConsumptionMoney(tableId);
        String freeRemark = "没有理由，就是免单，任性";

        checkoutService.freeOrder(tableId, partyId, consumptionMoney, freeRemark);
    }
}
