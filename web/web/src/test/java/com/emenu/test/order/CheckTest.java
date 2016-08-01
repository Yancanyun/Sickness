package com.emenu.test.order;

import com.emenu.common.dto.rank.CheckoutDto;
import com.emenu.common.dto.rank.CheckoutEachItemSumDto;
import com.emenu.common.entity.order.Checkout;
import com.emenu.common.enums.checkout.CheckoutTypeEnums;
import com.emenu.common.utils.DateUtils;
import com.emenu.service.order.CheckoutService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
        System.out.println(checkoutService.calcConsumptionMoney(62));
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

    @Test
    public void isPrinterOk() throws SSException {
        System.out.println(checkoutService.isPrinterOk());
    }

    @Test
    public void queryCheckoutByTimePeriod() throws SSException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d0 = DateUtils.getLastWeekFirstDay();
        String d2 = DateUtils.getLastWeekLastDay();
        String db = "00:00:00";
        String de = "23:59:59";
        d0 = d0 + " " + db;
        d2 = d2 + " " + de;
        ParsePosition pos1 =new ParsePosition(0);
        Date startDate = sdf.parse(d0,pos1);
        ParsePosition pos2 = new ParsePosition(0);
        Date endDate = sdf.parse(d2,pos2);
       List<CheckoutDto> checkoutDtoList = new ArrayList<CheckoutDto>();
        checkoutDtoList = checkoutService.queryCheckoutByTimePeriod(startDate, endDate);
        for(CheckoutDto checkoutDto : checkoutDtoList){
            System.out.println(checkoutDto);
        }
    }

    @Test
    public void sumCheckoutEachItem() throws SSException{
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d0 = DateUtils.getLastWeekFirstDay();
        String d2 = DateUtils.getLastWeekLastDay();
        String db = "00:00:00";
        String de = "23:59:59";
        d0 = d0 + " " + db;
        d2 = d2 + " " + de;
        ParsePosition pos1 =new ParsePosition(0);
        Date startDate = sdf.parse(d0,pos1);
        ParsePosition pos2 = new ParsePosition(0);
        Date endDate = sdf.parse(d2,pos2);
        CheckoutEachItemSumDto checkoutEachItemSumDto = new  CheckoutEachItemSumDto();
        checkoutEachItemSumDto = checkoutService.sumCheckoutEachItem(checkoutService.queryCheckoutByTimePeriod(startDate, endDate));
        System.out.println(checkoutEachItemSumDto);
    }
}
