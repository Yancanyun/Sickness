package com.emenu.service.vip;

import com.emenu.common.entity.vip.MultipleIntegralPlan;
import com.emenu.common.enums.vip.MultipleIntegralPlanStatusEnums;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * MultipleIntegralPlanService
 * 多倍积分方案Service
 *
 * @author WangLM
 * @date 2015/12/3 16:59
 */
public interface MultipleIntegralPlanService {

    /**
     * 查询所有积分方案
     *
     * @return
     * @throws SSException
     */
    public List<MultipleIntegralPlan> listAll() throws SSException;

    /**
     * 新增积分方案
     *
     * @param multipleIntegralPlan
     * @return
     * @throws SSException
     */
    public MultipleIntegralPlan newMultipleIntegralPlan(MultipleIntegralPlan multipleIntegralPlan) throws SSException;

    /**
     * 修改积分方案
     *
     * @param multipleIntegralPlan
     * @throws SSException
     */
    public void updateById(MultipleIntegralPlan multipleIntegralPlan) throws SSException;

    /**
     * 删除积分方案
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 根据id修改启用状态
     * 0-停用 1-启用
     *
     * @param id
     * @param status
     * @throws SSException
     */
    public void updateStatusById(int id, MultipleIntegralPlanStatusEnums status) throws SSException;

    /**
     * 列出启用的积分方案
     *
     * @return
     * @throws SSException
     */
    public List<MultipleIntegralPlan> listEnabledPlan() throws SSException;

    /**
     * 根据id查询积分方案
     *
     * @param id
     * @return
     * @throws SSException
     */
    public MultipleIntegralPlan queryById(int id) throws SSException;

    /**
     * 查询某会员价方案中存在几个多倍积分方案
     *
     * @return
     * @throws SSException
     */
    public int countByVipPricePlanId(int vipDishPricePlanId) throws SSException;
}
