package com.emenu.test.call;

import com.emenu.common.entity.call.CallWaiter;
import com.emenu.mapper.call.CallWaiterMapper;
import com.emenu.service.call.CallWaiterService;
import com.emenu.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * CallServiceTest
 *
 * @author: quanyibo
 * @time: 2016/5/23
 */
public class CallWaiterTest extends AbstractTestCase {

    @Autowired
    private CallWaiterMapper callWaiterMapper;

    @Autowired
    private CallWaiterService callWaiterService;

    @Test
    public void testNewCallWaiter() throws Exception
    {
        CallWaiter callWaiter  =new CallWaiter();
        callWaiter.setName("点菜");
        callWaiter.setStatus(1);
        callWaiter.setWeight(2);
        callWaiterService.newCallWaiter(callWaiter);
        //callServiceService.newCallService(callService);
    }

    @Test
    public void testDelCallWaiter()throws Exception
    {
        callWaiterService.delCallWaiter(1);
    }

    @Test
    public void testUpdateCallWaiter()throws Exception
    {
        CallWaiter callWaiter  =new CallWaiter();
        callWaiter.setName("锅里加水");
        callWaiter.setStatus(1);
        callWaiter.setWeight(2);
        callWaiter.setId(5);
        callWaiterService.updateCallWaiter(callWaiter);
    }

    @Test
    public void testQueryAllCallWaiter()throws Exception
    {

        System.out.println(callWaiterService.queryAllCallWaiter());
    }

    @Test
    public void testUpdateCallWaiterStatus()throws Exception
    {

        System.out.println(callWaiterService.updateCallWaiterStatus(1,5));
    }
}
