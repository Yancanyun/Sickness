package com.emenu.service.call.impl;

import com.emenu.common.cache.call.CallCache;
import com.emenu.common.cache.call.TableCallCache;
import com.emenu.common.entity.table.Table;
import com.emenu.common.entity.table.WaiterTable;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.call.CallCacheService;
import com.emenu.service.table.WaiterTableService;
import com.lowagie.text.ChapterAutoNumber;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import javafx.scene.control.Tab;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
    private static final  long TIMESTAMP = 60*1000l;
    //时间戳30秒
    private static final  long TIMESTAMP2 = 30*1000l;

    private Map<Integer,TableCallCache> tableCallCacheMap = new ConcurrentHashMap<Integer, TableCallCache>();

    @Override
    public void addCallCache(Integer tableId,CallCache callCache)throws SSException {
        TableCallCache tableCallCache = new TableCallCache();
        List<CallCache> callCaches = new ArrayList<CallCache>();
        try {
            tableCallCache = tableCallCacheMap.get(tableId);
            if(tableCallCache==null||tableCallCache.getCallCacheList().isEmpty())//未发出过呼叫
            {
                tableCallCache = new TableCallCache();
                callCaches.add(callCache);
                tableCallCache.setCallCacheList(callCaches);
                tableCallCacheMap.put(tableId,tableCallCache);
            }
            else//之前发出过呼叫服务
            {
                String name=callCache.getName();//获取当前的呼叫类型
                callCaches=tableCallCache.getCallCacheList();
                int ok = 0;//标志变量,1表示缓存中存在同名呼叫服务,0表示不存在同名呼叫服务
                for(CallCache dto : callCaches)
                {
                    if(dto.getName().equals(name))//呼叫服务缓存中已经存在同样的呼叫类型
                    {
                    //0为已经应答,已经应答了可再次发出此服务,但是发出两次相同的服务请求时间间隔必须超过60秒
                        if(dto.getStatus()==0
                                &&(callCache.getCallTime().getTime()-dto.getCallTime().getTime()>=TIMESTAMP))
                        {
                            ok=1;
                            dto.setStatus(1);
                            dto.setCallTime(new Date());
                            tableCallCache.setCallCacheList(callCaches);
                            tableCallCacheMap.put(tableId,tableCallCache);//更新缓存
                        }
                    }
                }
                if(ok==0)//不存在同名呼叫服务
                {
                    callCaches.add(callCache);
                    tableCallCache.setCallCacheList(callCaches);
                    tableCallCacheMap.put(tableId,tableCallCache);
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.AddCallCacheFail, e);
        }
    }

    @Override
    public List<TableCallCache> queryCallCacheByWaiterId(Integer partyId) throws SSException
    {
        List<TableCallCache> tableCallCache = new ArrayList<TableCallCache>();
        List<Integer> tableId = new ArrayList<Integer>();//
        try
        {
            tableId = waiterTableService.queryByPartyId(partyId);//查询出了服务员服务的餐桌,
            for(Integer dto :tableId)
            {
                TableCallCache temp = new TableCallCache();
                temp = tableCallCacheMap.get(dto);
                if(temp!=null&&!temp.getCallCacheList().isEmpty())
                {
                    tableCallCache.add(temp);
                }
            }
        }
        catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryCallCacheByWaiterIdFail, e);
        }
        return tableCallCache;
    }

    public void delTableCallCache(Integer tableId) throws SSException
    {
        try
        {
            if(!Assert.lessZero(tableId))
            tableCallCacheMap.put(tableId,new TableCallCache());
        }
        catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelTableCallCacheFail, e);
        }

    }
}
