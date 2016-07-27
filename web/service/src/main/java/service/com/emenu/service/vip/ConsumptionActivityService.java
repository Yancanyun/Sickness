package com.emenu.service.vip;

import com.emenu.common.entity.vip.ConsumptionActivity;
import com.pandawork.core.common.exception.SSException;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * ConsumptionActivityService
 *
 * @author: yangch
 * @time: 2016/7/26 10:02
 */
public interface ConsumptionActivityService {
    /**
     * 根据会员PartyId及分页列出消费详情
     * @param partyId
     * @param curPage
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<ConsumptionActivity> listByPartyIdAndPageAndDate(int partyId,
                                                                 int curPage,
                                                                 int pageSize,
                                                                 Date startTime,
                                                                 Date endTime) throws SSException;

    /**
     * 根据会员PartyId列出消费详情数量
     * @param partyId
     * @return
     * @throws SSException
     */
    public int countByPartyIdAndDate(int partyId,
                                     Date startTime,
                                     Date endTime) throws SSException;

    /**
     * 查询该时间之后的所有现金充值
     * @param startTime
     * @return
     * @throws SSException
     */
    public BigDecimal queryCashRechargeFromDate(Date startTime) throws SSException;
}
