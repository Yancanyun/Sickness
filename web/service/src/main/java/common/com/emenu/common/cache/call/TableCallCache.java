package com.emenu.common.cache.call;

import java.util.ArrayList;
import java.util.List;

/**
 * TableCallCache
 * 一个餐桌所对应的所有呼叫服务缓存
 * @author quanyibo
 * @date 2016/5/18.
 */
public class TableCallCache {

    List<CallCache> CallCacheList = new ArrayList<CallCache>();

    public List<CallCache> getCallCacheList() {
        return CallCacheList;
    }

    public void setCallCacheList(List<CallCache> callCacheList) {
        CallCacheList = callCacheList;
    }
}
