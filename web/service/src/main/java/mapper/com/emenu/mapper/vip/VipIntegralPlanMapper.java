package com.emenu.mapper.vip;

import com.emenu.common.entity.vip.VipIntegralPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 会员积分方案mapper
 *
 * @author chenyuting
 * @date 2016/1/19 9:05
 */
public interface VipIntegralPlanMapper {

    /**
     * 根据等级id获取会员积分方案列表
     * @param gradeId
     * @return
     * @throws Exception
     */
    public List<VipIntegralPlan> listByGradeId(@Param("gradeId") int gradeId) throws Exception;

    /**
     * 根据等级id更改会员积分状态
     * @param gradeId
     * @param status
     * @throws Exception
     */
    public void updateStatus(@Param("gradeId") int gradeId,
                             @Param("status") int status) throws Exception;

    /**
     * 插入/更新会员积分方案
     * @param vipIntegralPlanList
     * @throws Exception
     */
    public void insetAll(@Param("vipIntegralPlanList") List<VipIntegralPlan> vipIntegralPlanList) throws Exception;

    public void deletePlanById(@Param("id") int id) throws Exception;

}
