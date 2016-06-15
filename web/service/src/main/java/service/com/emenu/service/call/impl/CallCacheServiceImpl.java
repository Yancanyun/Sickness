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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
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
    //时间戳60秒
    private static final  long TIMESTAMP = 60*1000l;
    //时间戳30秒
    private static final  long TIMESTAMP2 = 30*1000l;

    private Map<Integer,TableCallCache> tableCallCacheMap = new ConcurrentHashMap<Integer, TableCallCache>();

    private List<CallCache> allCallCaches = new ArrayList<CallCache>();//所有桌子的呼叫服务缓存,根据时间从小到大排序

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
                allCallCaches.add(callCache);
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
                    //发出两次相同的服务请求时间间隔必须超过60秒
                        if(callCache.getCallTime().getTime()-dto.getCallTime().getTime()>=TIMESTAMP)//两次请求间隔大于60秒
                        {
                            ok=1;
                            dto.setStatus(1);
                            dto.setCallTime(new Date());
                            tableCallCache.setCallCacheList(callCaches);
                            tableCallCacheMap.put(tableId,tableCallCache);//更新缓存
                            for(CallCache temp :allCallCaches)
                            {
                                if(temp.getName().equals(dto.getName())
                                        &&temp.getTableId()==dto.getTableId())
                                {
                                    temp.setCallTime(new Date());
                                    temp.setStatus(1);
                                    //把所有的呼叫服务缓存排序
                                    break;
                                }
                            }
                        }
                       else
                        {
                            throw SSException.get(EmenuException. CallCacheSendTimeLimit);
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
    public TableCallCache queryCallCacheByWaiterId(Integer partyId) throws SSException
    {
        TableCallCache tableCallCache = new TableCallCache();
        List<Integer> tableId = new ArrayList<Integer>();//
        List<CallCache> callCaches = new ArrayList<CallCache>();
        try
        {
            tableId = waiterTableService.queryByPartyId(partyId);//查询出了服务员服务的餐桌,
            for(Integer dto :tableId)
            {
               for(CallCache temp : allCallCaches)
               {
                   if(temp.getTableId()==dto)
                   {
                       callCaches.add(temp);
                   }
               }
            }
            Collections.sort(callCaches, new Comparator<CallCache>() {//将缓存重新排序一下
                @Override  //排序函数
                public int compare(CallCache o1, CallCache o2) {

                    //先按处理状态进行升序排序,即把没处理的呼叫服务放在前面
                    if(o1.getStatus() > o2.getStatus()){
                        return 1;
                    }
                    if(o1.getStatus() == o2.getStatus())
                    {
                        //若状态值相同则按照时间的升序进行排序
                        if(o1.getCallTime().getTime() > o2.getCallTime().getTime()){
                            return 1;
                        }
                        if(o1.getCallTime().getTime() == o2.getCallTime().getTime())
                            return 0;
                        return -1;
                    }
                    return -1;
                }
            });
            tableCallCache.setCallCacheList(callCaches);
        }
        catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryCallCacheByWaiterIdFail, e);
        }
        return tableCallCache;
    }

    @Override
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

    @Override
    public void delAllCallCaches() throws SSException
    {
        try
        {
            tableCallCacheMap = new ConcurrentHashMap<Integer, TableCallCache>();
            allCallCaches = new ArrayList<CallCache>();
        }
        catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelTableCallCacheFail, e);
        }
    }
}
