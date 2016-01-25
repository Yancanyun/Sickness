package com.emenu.service.vip;

import com.emenu.common.dto.vip.VipIntegralDto;
import com.emenu.common.entity.vip.VipIntegralPlan;
import com.emenu.common.enums.vip.StatusEnums;
import com.emenu.common.enums.vip.grade.IntegralEnableStatusEnums;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * 会员积分方案Service
 *
 * @author chenyuting
 * @date 2016/1/19 9:15
 */
public interface VipIntegralPlanService {

    /**
     * 获取会员积分方案列表
     * @return
     * @throws SSException
     */
    public List<VipIntegralPlan> listByGradeId(int gradeId) throws SSException;

    /**
     * 获取会员积分方案的所有兑换方式
     * @return
     * @throws SSException
     */
    public List<VipIntegralDto> listDtosGradeId(int gradeId) throws SSException;

   /* *//**
     * 添加积分管理方案
     * @param vipIntegralPlan
     * @throws SSException
     *//*
    public void newPlan(VipIntegralPlan vipIntegralPlan) throws SSException;*/

    /**
     * 批量添加会员积分管理方案
     * @param vipIntegralPlanList
     * @param gradeId
     * @throws SSException
     */
    public void newPlans(List<VipIntegralPlan> vipIntegralPlanList, Integer gradeId) throws SSException;

    public void generateBeforeSave(List<VipIntegralDto> vipIntegralDtoList) throws SSException;

    /**
     * 保存前检查
     * @param vipIntegralPlan
     * @return
     * @throws SSException
     */
    public boolean checkBeforeSave(VipIntegralPlan vipIntegralPlan) throws SSException;

    /**
     * 更新会员积分
     * @param vipIntegralPlan
     * @param integralStatus
     * @throws SSException
     */
    public void updatePlan(VipIntegralPlan vipIntegralPlan, IntegralEnableStatusEnums integralStatus) throws SSException;

    /**
     *批量更改会员积分
     * @param vipIntegralPlans
     * @param gradeId
     * @param integralStatus
     * @throws SSException
     */
    public void updatePlans(List<VipIntegralPlan> vipIntegralPlans,
                            Integer gradeId,
                            IntegralEnableStatusEnums integralStatus ) throws SSException;

    /**
     * 根据会员等级id和type删除积分方案
     * @param id
     * @throws SSException
     */
    public void deletePlanById(Integer id) throws SSException;

    /**
     * 根据等级id更改方案状态
     * @param gradeId
     * @param status
     * @throws SSException
     */
    public void updateStatus(int gradeId, int status) throws SSException;
}
