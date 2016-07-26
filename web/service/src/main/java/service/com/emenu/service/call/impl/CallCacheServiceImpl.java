package com.emenu.service.call.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.cache.call.CallCache;
import com.emenu.common.cache.call.TableCallCache;
import com.emenu.common.entity.call.CallWaiter;
import com.emenu.common.entity.table.Table;
import com.emenu.common.entity.table.WaiterTable;
import com.emenu.common.enums.call.CallCacheResponseEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.call.CallCacheService;
import com.emenu.service.call.CallWaiterService;
import com.emenu.service.table.TableService;
import com.emenu.service.table.WaiterTableService;
import com.lowagie.text.ChapterAutoNumber;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
//import jdk.nashorn.internal.codegen.CompilerConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
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

    @Autowired
    CallWaiterService callWaiterService;

    @Autowired
    TableService tableService;

    //时间戳60秒
    private static final  long TIMESTAMP = 60*1000l;
    //时间戳30秒
    private static final  long TIMESTAMP2 = 30*1000l;

    private Integer callCacheId = 0;

    private Map<Integer,TableCallCache> tableCallCacheMap = new ConcurrentHashMap<Integer, TableCallCache>();

    // 所有桌子的呼叫服务缓存
    private List<CallCache> allCallCaches = new ArrayList<CallCache>();

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void addCallCache(Integer tableId,CallCache callCache)throws SSException {

        TableCallCache tableCallCache = new TableCallCache();
        List<CallCache> callCaches = new ArrayList<CallCache>();
        List<CallWaiter> callWaiters = new ArrayList<CallWaiter>();
        Integer id = new Integer(0);
        try {
            tableCallCache = tableCallCacheMap.get(tableId);
            // 获取所有的服务类型
            callWaiters = callWaiterService.queryAllCallWaiter();
            // 查询服务的主键
            for(CallWaiter callWaiter : callWaiters){
                if(callWaiter.getName().equals(callCache.getName())){
                    //设置主键
                    id = callWaiter.getId();
                    break;
                }
            }
            // 未发出过呼叫
            if(tableCallCache==null
                    ||tableCallCache.getCallCacheList().isEmpty()) {
                tableCallCache = new TableCallCache();
                // 设置缓存主键
                callCache.setCacheId(++callCacheId);
                // 设置服务的主键
                callCache.setId(id);
                callCaches.add(callCache);
                tableCallCache.setCallCacheList(callCaches);
                tableCallCacheMap.put(tableId,tableCallCache);
                allCallCaches.add(callCache);
            }
            // 之前发出过呼叫服务
            else {
                // 获取当前的呼叫名称
                String name=callCache.getName();
                callCaches=tableCallCache.getCallCacheList();
                // 标志变量,1表示缓存中存在同名呼叫服务,0表示不存在同名呼叫服务
                int ok = 0;
                for(CallCache dto : callCaches) {
                    // 呼叫服务缓存中已经存在同样的呼叫类型
                    if(dto.getName().equals(name)) {
                        ok=1;
                    // 发出两次相同的服务请求时间间隔必须超过60秒
                        if(callCache.getCallTime().getTime()-dto.getCallTime().getTime()>=TIMESTAMP) {
                            dto.setStatus(CallCacheResponseEnums.IsNotResponse.getId());
                            dto.setCallTime(new Date());
                            tableCallCache.setCallCacheList(callCaches);
                            tableCallCacheMap.put(tableId,tableCallCache);//更新缓存
                            for(CallCache temp :allCallCaches) {

                                if(temp.getName().equals(dto.getName())
                                        &&temp.getTableId()==dto.getTableId()) {

                                    temp.setCallTime(new Date());
                                    temp.setStatus(CallCacheResponseEnums.IsNotResponse.getId());
                                    break;
                                }
                            }
                        }
                       else {
                            throw SSException.get(EmenuException.CallCacheSendTimeLimit);
                        }
                    }
                }
                // 不存在同名呼叫服务
                if(ok==0)
                {
                    // 设置缓存主键
                    callCache.setCacheId(++callCacheId);
                    callCache.setId(id);
                    callCaches.add(callCache);
                    tableCallCache.setCallCacheList(callCaches);
                    tableCallCacheMap.put(tableId,tableCallCache);
                    // 加入到总队列
                    allCallCaches.add(callCache);
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.AddCallCacheFail, e);
        }
    }

    @Override
    public TableCallCache queryCallCacheByPartyId(Integer partyId) throws SSException {
        TableCallCache tableCallCache = new TableCallCache();
        List<Integer> tableId = new ArrayList<Integer>();//
        List<CallCache> callCaches = Collections.EMPTY_LIST;
        try {

            tableId = waiterTableService.queryByPartyId(partyId);//查询出了服务员服务的餐桌,
            for(Integer dto :tableId) {

               for(CallCache temp : allCallCaches) {

                   if(temp.getTableId()==dto) {
                       callCaches.add(temp);
                   }
               }
            }
            if(callCaches!=null) {

                Collections.sort(callCaches, new Comparator<CallCache>() {//将缓存重新排序一下
                    @Override  //排序函数
                    public int compare(CallCache o1, CallCache o2) {

                        //先按处理状态进行升序排序,即把没处理的呼叫服务放在前面
                        if(o1.getStatus() > o2.getStatus()){
                            return 1;
                        }
                        if(o1.getStatus() == o2.getStatus()) {

                            //若状态值相同则按照时间的升序进行排序,呼叫时间小的靠前
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
        }
        catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryCallCacheByWaiterIdFail, e);
        }
        return tableCallCache;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void delTableCallCache(Integer tableId) throws SSException
    {
        try
        {
            //不管服务员应答与否,都删除掉
            if(!Assert.lessZero(tableId))
            tableCallCacheMap.put(tableId,new TableCallCache());
            for(int i=allCallCaches.size()-1;i>=0;i--)//后加的list集合中也要把相应的缓存删掉
            {
                if(allCallCaches.get(i).getTableId()==tableId)
                    allCallCaches.remove(i);
            }
        }
        catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelTableCallCacheFail, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void delCallCachesByPartyId(Integer partyId) throws SSException {

        List<Integer> tableId = new ArrayList<Integer>();//
        try {
            tableId = waiterTableService.queryByPartyId(partyId);//查询出了服务员服务的餐桌,
            for(Integer dto :tableId) {

                // 在总列表里删除缓存
                for(int i = allCallCaches.size()-1;i>=0;i--) {

                    //0为服务员已经应答,1为服务员未应答,未应答的不能删除
                    if(allCallCaches.get(i).getTableId()==dto
                            &&allCallCaches.get(i).getStatus()==CallCacheResponseEnums.IsResponse.getId()) {
                        allCallCaches.remove(i);//删除掉这个缓存
                    }
                }

                // 删除餐桌未处理的缓存
                TableCallCache tableCallCache = tableCallCacheMap.get(dto);
                List<CallCache> callCaches = Collections.EMPTY_LIST;
                if(tableCallCache!=null)
                    callCaches=tableCallCache.getCallCacheList();
                for(int i = callCaches.size()-1;i>=0;i--) {
                    // 0为应答了,1为未应答
                    if(callCaches.get(i).getStatus()==CallCacheResponseEnums.IsResponse.getId()) {
                        callCaches.remove(i);
                    }
                }
                // 更新缓存
                if(!callCaches.isEmpty()){
                    tableCallCache.setCallCacheList(callCaches);
                    tableCallCacheMap.put(dto,tableCallCache);
                }
                // 为空则删除对应餐桌的缓存
                else{
                    this.delTableCallCache(dto);
                }
            }
        }
        catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelCallCachesByPartyIdFail, e);
        }
    }
    @Override
      public TableCallCache queryCallCachesByTableId(Integer tableId) throws SSException {
        TableCallCache tableCallCache = new TableCallCache();
        try {

           if(Assert.isNotNull(tableId)
                   &&!Assert.lessOrEqualZero(tableId)){
               tableCallCache=tableCallCacheMap.get(tableId);
           }
        }
        catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.TableNotHaveCallCache, e);
        }
        return tableCallCache;
    }

    @Override
    public int countNotResponseTotalMessage(Integer partyId) throws SSException{

        List<Integer> tableId = new ArrayList<Integer>();
        int count=0;
        try {
            tableId = waiterTableService.queryByPartyId(partyId);//查询出了服务员服务的餐桌
            for(Integer dto :tableId) {

                for(int i = allCallCaches.size()-1;i>=0;i--) {
                    if(allCallCaches.get(i).getTableId()==dto
                            &&allCallCaches.get(i).getStatus()==CallCacheResponseEnums.IsNotResponse.getId()) {
                        count++;
                    }
                }
            }
        }
        catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CountNotResponseTotalMessageFail, e);
        }
        return count;
    }

    @Override
    public JSONArray queryCallById(Integer id,Integer partyId) throws SSException{
        List<CallCache> callCaches = new ArrayList<CallCache>();
        JSONArray jsonArray = new JSONArray();
        List<Integer> tableId = new ArrayList<Integer>();
        try {
            // 获取服务员服务的餐桌
            tableId = waiterTableService.queryByPartyId(partyId);
            for(Integer dto : tableId){
                for(CallCache callCache : allCallCaches){
                    if(id==null
                            &&callCache.getTableId()==dto)
                        callCaches.add(callCache);
                    else if(id!=null
                            &&callCache.getId()==id
                            &&callCache.getTableId()==dto){
                        callCaches.add(callCache);
                    }
                }
            }
            // 排序
            if(callCaches!=null){

                Collections.sort(callCaches, new Comparator<CallCache>() {//将缓存重新排序一下
                    @Override  //排序函数
                    public int compare(CallCache o1, CallCache o2) {

                        //服务应答状态  0为应答了 1为未应答
                        //先按处理状态进行降序,即把没处理的呼叫服务放在前面
                        if(o1.getStatus() < o2.getStatus()){
                            return 1;
                        }
                        if(o1.getStatus() == o2.getStatus()) {

                            //若状态值相同则按照时间的升序进行排序,呼叫时间小的靠前
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
            }
            for(CallCache dto :callCaches){
                JSONObject temp = new JSONObject();
                temp.put("cacheId",dto.getCacheId());
                temp.put("tableId",dto.getTableId());

                // 获取桌子信息
                Table table = new Table();
                table = tableService.queryById(dto.getTableId());
                temp.put("tableName",table.getName());
                temp.put("name",dto.getName());
                // 只显示出时分秒
                DateFormat df = DateFormat.getTimeInstance();
                temp.put("time",df.format(dto.getCallTime()));
                temp.put("status",dto.getStatus());
                jsonArray.add(temp);
            }
        }
        catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryCallByIdFail, e);
        }
        return jsonArray;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void responseCall(Integer tableId, Integer cacheId) throws SSException{
        List<CallCache> callCaches = new ArrayList<CallCache>();
        TableCallCache tableCallCache = new TableCallCache();
        try {
            for(CallCache dto : allCallCaches){
                if(dto.getCacheId()==cacheId){
                    dto.setStatus(CallCacheResponseEnums.IsResponse.getId());
                    break;
                }
            }

            tableCallCache=tableCallCacheMap.get(tableId);
            if(tableCallCache!=null){
                callCaches = tableCallCache.getCallCacheList();
                for(CallCache dto : callCaches){
                    if(dto.getCacheId()==cacheId){
                        dto.setStatus(CallCacheResponseEnums.IsResponse.getId());
                        break;
                    }
                }
                tableCallCache.setCallCacheList(callCaches);
                // 更新一下
                tableCallCacheMap.put(tableId,tableCallCache);
            }
        }
        catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ResponseCallFail, e);
        }
    }
}
