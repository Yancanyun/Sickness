package com.emenu.service.call;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.cache.call.CallCache;
import com.emenu.common.cache.call.TableCallCache;
import com.pandawork.core.common.exception.SSException;

import javax.management.relation.InvalidRelationTypeException;
import javax.persistence.Table;
import java.util.List;

/**
 * CallCacheService
 * 顾客端呼叫缓存Service
 * @author quanyibo
 * @date: 2016/5/23
 */
public interface CallCacheService {

    /**
     * 呼叫服务加入缓存
     * @param callCache
     * @throws SSException
     */
    public void addCallCache(Integer tableId,CallCache callCache)throws SSException;

    /**
     * 获取服务员对应餐桌的所有服务请求
     * @param partyId
     * @throws SSException
     */
    public TableCallCache queryCallCacheByPartyId(Integer partyId)throws SSException;

    /**
     * 清除对应餐桌的呼叫服务缓存
     * 无论应答与否都删除掉
     * @param tableId
     * @throws SSException
     */

    public void delTableCallCache(Integer tableId) throws SSException;

    /**
     * 清除服务员服务餐桌的呼叫缓存
     * 只能删除已经应答的呼叫服务缓存
     * @param partyId
     * @throws SSException
     */
    public void delCallCachesByPartyId(Integer partyId) throws SSException;

    /**
     * 查询对应餐桌的呼叫服务缓存
     * @param tableId
     * @throws SSException
     */

    public TableCallCache queryCallCachesByTableId(Integer tableId) throws SSException;

    /**
     * 根据服务员查询服务员负责的所有餐桌有多少消息未应答
     * @param partyId
     * @throws SSException
     */
    public int countNotResponseTotalMessage(Integer partyId) throws SSException;

    /**
     * 根据服务类型的Id和partyId来获取相同服务类型的请求
     * @param id
     * @throws SSException
     */
    public JSONObject queryCallById(Integer id,Integer partyId) throws SSException;

    /**
     * 应答服务请求
     *
     * @param cacheId,tableId
     * @throws SSException
     */
    public void responseCall(Integer tableId, Integer cacheId) throws SSException;
}
