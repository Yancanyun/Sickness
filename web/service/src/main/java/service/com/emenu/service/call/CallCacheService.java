package com.emenu.service.call;

import com.emenu.common.cache.call.CallCache;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * CallCacheService
 * 顾客端呼叫缓存Service
 * @author quanyibo
 * @date: 2016/5/23
 */
public interface CallCacheService {

    /**
     * 把呼叫服务加入缓存
     * @param callCache
     * @throws Exception
     */
    public void addCallCache(Integer tableId,CallCache callCache)throws SSException;

    /**
     * 获取服务员对应餐桌的所有服务请求
     * @param waiterId
     * @throws Exception
     */
    public List<CallCache> queryCallCacheByWaiterId(Integer waiterId)throws SSException;
}
