package com.emenu.mapper.table;

import com.emenu.common.entity.table.Table;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaozl
 * @date 2015/11/4
 * @time 15:52
 */
public interface WaiterTableMapper {
    public List<Integer> queryByPartyId(@Param("partyId")int partyId) throws Exception;

    public List<Integer> queryByPartyIdAndStatus(@Param("partyId") int partyId,
                                                 @Param("status") int status) throws Exception;

    public List<Integer> listTableByPartyIdAndAreaIdAndStatus(@Param("partyId")int partyId,
                                                              @Param("areaId") int areaId,
                                                              @Param("status")int status) throws Exception;
}
