package com.emenu.service.vip.impl;

import com.emenu.common.entity.vip.ConsumptionActivity;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.vip.ConsumptionActivityMapper;
import com.emenu.service.vip.ConsumptionActivityService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * ConsumptionActivityServiceImpl
 *
 * @author: yangch
 * @time: 2016/7/26 10:02
 */
@Service(value = "consumptionActivityService")
public class ConsumptionActivityServiceImpl implements ConsumptionActivityService {
    @Autowired
    private CommonDao commonDao;

    @Autowired
    private ConsumptionActivityMapper consumptionActivityMapper;

    @Override
    public List<ConsumptionActivity> listByPartyIdAndPageAndDate(int partyId,
                                                                 int curPage,
                                                                 int pageSize,
                                                                 Date startTime,
                                                                 Date endTime) throws SSException {
        List<ConsumptionActivity> consumptionActivityList  = Collections.emptyList();
        curPage = curPage <= 0 ? 0 : curPage - 1;
        int offset = curPage * pageSize;

        if (Assert.lessZero(offset)) {
            return consumptionActivityList;
        }

        try {
            Assert.lessOrEqualZero(partyId, EmenuException.PartyIdError);

            consumptionActivityList = consumptionActivityMapper.listByPartyIdAndPageAndDate(partyId, offset, pageSize, startTime, endTime);
        } catch(Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryConsumptionActivityFail, e);
        }
        return consumptionActivityList;
    }

    @Override
    public int countByPartyIdAndDate(int partyId,
                                     Date startTime,
                                     Date endTime) throws SSException {
        try {
            Assert.lessOrEqualZero(partyId, EmenuException.PartyIdError);

            int count = consumptionActivityMapper.countByPartyIdAndPageAndDate(partyId, startTime, endTime);
            return count;
        } catch(Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryConsumptionActivityFail, e);
        }
    }

    @Override
    public BigDecimal queryCashRechargeFromDate(Date startTime) throws SSException {
        try {
            BigDecimal cashRecharge = consumptionActivityMapper.queryCashRechargeFromDate(startTime);

            if (Assert.isNull(cashRecharge)) {
                cashRecharge = new BigDecimal(0);
            }

            return cashRecharge;
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryConsumptionActivityFail, e);
        }
    }
}
