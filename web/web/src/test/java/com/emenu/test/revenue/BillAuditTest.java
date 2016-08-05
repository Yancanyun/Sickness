package com.emenu.test.revenue;

import com.emenu.common.dto.revenue.CheckoutDto;
import com.emenu.common.dto.revenue.CheckoutEachItemSumDto;
import com.emenu.common.utils.DateUtils;
import com.emenu.service.revenue.BillAuditService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @athor pengpeng
 * @time 2016/8/4  10:48
 */
public class BillAuditTest extends AbstractTestCase{
    @Autowired
    private BillAuditService billAuditService;

    @Test
    public void countCheckoutByTimePeriod() throws SSException{
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
        Integer count = billAuditService.countCheckoutByTimePeriod(startDate, endDate);
        System.out.print(count);
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
        checkoutEachItemSumDto = billAuditService.sumCheckoutEachItem(billAuditService.queryCheckoutByTime(startDate, endDate));
        System.out.println(checkoutEachItemSumDto);
    }

    @Test
    public void queryCheckoutByTimePeriod() throws SSException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String d0 = DateUtils.getCurrentWeekFirstDay();
        String d2 = DateUtils.getCurrentWeekLastDay();
        String db = "00:00:00";
        String de = "23:59:59";
        d0 = d0 + " " + db;
        d2 = d2 + " " + de;
        ParsePosition pos1 = new ParsePosition(0);
        Date startDate = sdf.parse(d0,pos1);
        ParsePosition pos2 = new ParsePosition(0);
        Date endDate = sdf.parse(d2,pos2);

        CheckoutDto checkoutDto = new CheckoutDto();
        Integer pageNo = 1;
        Integer pageSize = 10;
        pageNo = pageNo == null ? 0 : pageNo;
        pageSize = pageSize == null ? 10 : pageSize;
        checkoutDto.setPageNo(pageNo);
        checkoutDto.setPageSize(pageSize);

        List<CheckoutDto> checkoutDtoList = new ArrayList<CheckoutDto>();
        checkoutDtoList = billAuditService.queryCheckoutByTimePeriod(startDate, endDate, checkoutDto);
        for(CheckoutDto checkoutDto1 : checkoutDtoList){
            System.out.println(checkoutDto1);
        }
    }
}
