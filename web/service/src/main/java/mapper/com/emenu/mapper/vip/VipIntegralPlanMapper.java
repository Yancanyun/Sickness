package com.emenu.mapper.vip;

import com.emenu.common.entity.vip.VipIntegralPlan;

import java.util.List;

/**
 * 会员积分方案mapper
 *
 * @author chenyuting
 * @date 2016/1/19 9:05
 */
public interface VipIntegralPlanMapper {

    public List<VipIntegralPlan> listAll() throws Exception;

}
