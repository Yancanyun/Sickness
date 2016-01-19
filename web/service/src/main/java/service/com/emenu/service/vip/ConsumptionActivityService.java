package com.emenu.service.vip;

import com.emenu.common.entity.vip.ConsumptionActivity;
import com.pandawork.core.common.exception.SSException;

/**
 * ConsumptionActivityService
 * 充值消费记录Service
 *
 * @author Wang LM
 * @date 2016/1/18 19:51
 */
public interface ConsumptionActivityService {

    /**
     * 新增充值记录
     *
     * @param consumptionActivity
     * @return
     * @throws SSException
     */
    public ConsumptionActivity newRechargeRecord(ConsumptionActivity consumptionActivity) throws SSException;
}
