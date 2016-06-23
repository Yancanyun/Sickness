package com.emenu.service.table.impl;

import com.emenu.common.exception.EmenuException;
import com.emenu.service.table.TableMergeCacheService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * MergeTableCacheServiceImpl
 *
 * @author: yangch
 * @time: 2016/6/22 16:14
 */
@Service("tableMergeCacheService")
public class TableMergeCacheServiceImpl implements TableMergeCacheService {
    // 并台缓存的Map
    private Map<Integer, List<Integer>> mergeTableCacheMap = new ConcurrentHashMap<Integer, List<Integer>>();

    @Override
    public void newTable(int partyId, int tableId) throws SSException {
        try {
            // 从缓存中取出本PartyId的并台缓存
            List<Integer> mergeTableList = mergeTableCacheMap.get(partyId);

            // 若缓存中不存在本PartyId的并台缓存，则新建之
            if (Assert.isNull(mergeTableList)) {
                mergeTableList = new ArrayList<Integer>();
            }

            mergeTableList.add(tableId);
            mergeTableCacheMap.put(partyId, mergeTableList);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.MergeTableFail, e);
        }
    }

    @Override
    public void delTable(int partyId, int tableId) throws SSException {
        try {
            // 从缓存中取出本PartyId的并台缓存
            List<Integer> mergeTableList = mergeTableCacheMap.get(partyId);

            // 若缓存中不存在本PartyId的并台缓存，则报错
            if (Assert.isNull(mergeTableList)) {
                throw SSException.get(EmenuException.DelMergeTableFail);
            }

            mergeTableList.remove(tableId);
            mergeTableCacheMap.put(partyId, mergeTableList);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.MergeTableFail, e);
        }
    }

    @Override
    public void cleanCacheByPartyId(int partyId) throws SSException {
        try {
            mergeTableCacheMap.remove(partyId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.MergeTableFail, e);
        }
    }

    @Override
    public List<Integer> queryByPartyId(int partyId) throws SSException {
        try {
            return mergeTableCacheMap.get(partyId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.MergeTableFail, e);
        }

    }
}
