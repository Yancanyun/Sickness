package com.emenu.test.call;

import com.emenu.common.cache.call.CallCache;
import com.emenu.common.entity.call.CallWaiter;
import com.emenu.service.call.CallCacheService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * CallServiceTest
 *
 * @author: quanyibo
 * @time: 2016/5/28
 */
public class CallCacheTest extends AbstractTestCase {

    @Autowired
    CallCacheService callCacheService;
    @Test
    public void testAddCallCache() throws Exception
    {
        CallCache callCache = new CallCache();
        callCache.setName("点菜");
        callCache.setStatus(1);
        callCache.setCallTime(new Date());
        callCacheService.addCallCache(1,callCache);
    }

    @Test
    public void testQueryCallCache() throws Exception
    {
        callCacheService.queryCallCacheByPartyId(1);
    }

    @Test
    public void testDelTableCallCache()throws Exception
    {
        callCacheService.delTableCallCache(1);
    }

    @Test
    public void testSort() throws  Exception
    {
        CallWaiter callWaiter1 = new CallWaiter();
        CallWaiter callWaiter2 = new CallWaiter();
        callWaiter1.setId(1);
        callWaiter2.setId(2);
        callWaiter1.setWeight(2);
        callWaiter2.setWeight(1);
        List<CallWaiter> callWaiters = new ArrayList<CallWaiter>();
        callWaiters.add(callWaiter1);
        callWaiters.add(callWaiter2);
        Collections.sort(callWaiters, new Comparator<CallWaiter>() {//将缓存重新排序一下
            @Override  //排序函数
            public int compare(CallWaiter o1, CallWaiter o2) {

                //先按处理状态进行升序排序,即把没处理的呼叫服务放在前面
                if (o1.getId() > o2.getId()) {
                    return 1;
                }
                if (o1.getId() == o2.getId()) {
                    //若状态值相同则按照时间的降序进行排序,呼叫时间小的靠前
                    if (o1.getWeight() < o2.getWeight()) {
                        return 1;
                    }
                    if (o1.getWeight() == o2.getWeight())
                        return 0;
                    return -1;
                }
                return -1;
            }
        });

        System.out.println(callWaiters);
    }
}
