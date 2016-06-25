package com.emenu.service.table;

import com.emenu.common.dto.table.AreaDto;
import com.emenu.common.entity.table.Table;
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

    /**
     * 查询服务员负责的餐桌
     * @param partyId
     * @return
     * @throws SSException
     */
    public List<AreaDto> queryAreaDtoByPartyId(int partyId) throws SSException;

    /**
     * 根据状态查询服务员负责的餐桌
     * @param partyId
     * @param status
     * @return
     * @throws SSException
     */
    public List<Integer> queryByPartyIdAndStatus(int partyId, int status) throws SSException;

    /**
     * 根据状态查询服务员负责的餐桌
     * @param partyId
     * @param status
     * @return
     * @throws SSException
     */
    public List<AreaDto> queryAreaDtoByPartyIdAndStatus(int partyId, int status) throws SSException;

    /**
     * 根据状态List、区域ID、PartyId查询服务员负责的餐桌
     * @param partyId
     * @param areaId
     * @param statusList
     * @return
     * @throws SSException
     */
    public AreaDto queryAreaDtoByPartyIdAndAreaIdAndStatusList(int partyId, int areaId, List<Integer> statusList) throws SSException;

    /**
     * 查询所有区域和餐桌
     * @return
     * @throws SSException
     */
    public List<AreaDto> queryAreaDto() throws SSException;

}
