package com.emenu.mapper.vip;

import com.emenu.common.entity.vip.ConsumptionActivity;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * ConsumptionActivityMapper
 *
 * @author: yangch
 * @time: 2016/7/26 10:10
 */
public interface ConsumptionActivityMapper {
    /**
     * 根据会员PartyId及分页列出消费详情
     * @param partyId
     * @param offset
     * @param pageSize
     * @return
     * @throws Exception
     */
    public List<ConsumptionActivity> listByPartyIdAndPageAndDate(@Param("partyId") int partyId,
                                                                 @Param("offset") int offset,
                                                                 @Param("pageSize") int pageSize,
                                                                 @Param("startTime") Date startTime,
                                                                 @Param("endTime") Date endTime) throws Exception;
    /**
     * 根据会员PartyId列出消费详情数量
     * @param partyId
     * @return
     * @throws Exception
     */
    public int countByPartyIdAndPageAndDate(@Param("partyId") int partyId,
                                            @Param("startTime") Date startTime,
                                            @Param("endTime") Date endTime) throws Exception;
}
