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
    public List<TableCallCache> queryCallCacheByWaiterId(Integer partyId)throws SSException;

    /**
     * 清除餐桌的呼叫服务缓存
     * @param tableId
     * @throws SSException
     */

    public void delTableCallCache(Integer tableId) throws SSException;
}
