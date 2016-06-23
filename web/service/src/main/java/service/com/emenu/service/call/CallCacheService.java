package com.emenu.service.call;

import com.emenu.common.cache.call.CallCache;
import com.emenu.common.cache.call.TableCallCache;
import com.pandawork.core.common.exception.SSException;

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
     * @param
     * @throws SSException
     */
    public void delCallCachesByPartyId(Integer partyId) throws SSException;
}
