package com.emenu.service.table;

import com.emenu.common.dto.table.AreaDto;
import com.emenu.common.entity.table.WaiterTable;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * @author xiaozl
 * @date 2015/10/27
 * @time 16:29
 */
public interface WaiterTableService {

    /**
     * 添加服务员餐桌信息
     * @param waiterTables
     * @throws SSException
     */

    public void insertWaiterTable(List<WaiterTable> waiterTables) throws SSException;

    /**
     * 查询服务员服务的餐桌
     * @param partyId
     * @return
     * @throws SSException
     */
    public List<Integer> queryByPartyId(int partyId) throws SSException;

    public List<AreaDto> queryAreaDtoByPartyId(int partyId) throws SSException;
}
