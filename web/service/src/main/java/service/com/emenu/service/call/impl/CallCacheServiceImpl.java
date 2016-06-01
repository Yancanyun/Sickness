package com.emenu.service.call.impl;

import com.emenu.common.cache.call.CallCache;
import com.emenu.common.entity.table.WaiterTable;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.call.CallCacheService;
import com.emenu.service.table.WaiterTableService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * CallCacheServiceImpl
 *
 * @author quanyibo
 * @date: 2016/5/28
 */

@Service("callCacheService")
public class CallCacheServiceImpl implements CallCacheService {

    @Autowired
    WaiterTableService waiterTableService;
    //时间戳10秒
    private static final  long TIMESTAMP = 10*1000l;
    //时间戳30秒
    private static final  long TIMESTAMP2 = 30*1000l;

    private Map<Integer,CallCache> callCacheMap = new ConcurrentHashMap<Integer, CallCache>();

    @Override
    public void addCallCache(Integer tableId,CallCache callCache)throws SSException {
        CallCache beforeCall = callCacheMap.get(tableId);
        try {
            if (beforeCall != null)//之前有过呼叫请求
            {
                if (callCache.getCallTime().getTime() - beforeCall.getCallTime().getTime() < TIMESTAMP)
                    return;//十秒之内不允许再次呼叫
            }
            callCacheMap.put(tableId,callCache);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.AddCallCacheFail, e);
        }
    }

    @Override
    public List<CallCache> queryCallCacheByWaiterId(Integer waiterId) throws SSException
    {
        List<CallCache> callCache = new ArrayList<CallCache>();
        List<Integer> tableId = new ArrayList<Integer>();
        try
        {
            tableId = waiterTableService.queryByPartyId(waiterId);//根据服务员的id查询出对应负责的餐桌Id
            int i = 0;
            for(Integer dto :tableId)
            {
                if(!callCacheMap.get(dto).equals(null))//呼叫服务不为空
                callCache.add(i++,callCacheMap.get(dto));
            }
        }
        catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryCallCacheByWaiterIdFail, e);
        }
        return callCache;
    }

}
