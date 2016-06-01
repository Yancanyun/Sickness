package com.emenu.test.call;

import com.emenu.common.cache.call.CallCache;
import com.emenu.service.call.CallCacheService;
import com.emenu.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;

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
        callCacheService.queryCallCacheByWaiterId(1);
    }
}
