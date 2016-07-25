package com.emenu.mapper.party.group.employee;

import com.pandawork.core.common.exception.SSException;

/**
 * EmployeeWaiterTableMapper
 *
 * @author xiaozl
 * @date: 2016/7/25
 */
public interface EmployeeWaiterTableMapper {

    /**
     * 根据partyId删除员工和餐桌的关联
     * @param partyId
     * @throws SSException
     */
    public void delByPartyId(int partyId) throws Exception;

}
