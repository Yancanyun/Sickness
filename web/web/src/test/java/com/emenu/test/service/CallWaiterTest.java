package com.emenu.test.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.dto.remark.RemarkDto;
import com.emenu.common.dto.remark.RemarkTagDto;
import com.emenu.common.entity.remark.Remark;
import com.emenu.common.entity.remark.RemarkTag;
import com.emenu.common.entity.service.CallWaiter;
import com.emenu.mapper.service.CallWaiterMapper;
import com.emenu.service.remark.RemarkService;
import com.emenu.service.remark.RemarkTagService;
import com.emenu.service.service.CallWaiterService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.ArrayList;
import java.util.List;
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
