package com.emenu.service.table;

import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * MergeTableCacheService
 * 并台缓存
 *
 * @author: yangch
 * @time: 2016/6/22 16:06
 */
public interface TableMergeCacheService {
    /**
     * 添加要并台的餐台
     * @param partyId
     * @param tableId
     * @throws SSException
     */
    public void newTable(int partyId, int tableId) throws SSException;

    /**
     * 删除要并台的餐台
     * @param partyId
     * @param tableId
     * @throws SSException
     */
    public void delTable(int partyId, int tableId) throws SSException;

    /**
     * 根据PartyId清空并台信息
     * @param partyId
     * @return
     * @throws SSException
     */
    public void cleanCacheByPartyId(int partyId) throws SSException;

    /**
     * 根据PartyId查看清空缓存中的并台信息
     * @param partyId
     * @return
     * @throws SSException
     */
    public List<Integer> queryByPartyId(int partyId) throws SSException;
}
