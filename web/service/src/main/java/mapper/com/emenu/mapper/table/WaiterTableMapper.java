package com.emenu.mapper.table;

import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaozl
 * @date 2015/11/4
 * @time 15:52
 */
public interface WaiterTableMapper {

    public List<Integer> queryByPartyId(@Param("partyId")int partyId) throws Exception;
}
