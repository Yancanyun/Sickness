package com.emenu.mapper.vip;

import com.emenu.common.entity.vip.MultipleIntegralPlan;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MultipleIntegralPlanMapper
 *
 * @author WangLM
 * @date 2015/12/7 14:23
 */
public interface MultipleIntegralPlanMapper {

    /**
     * 查询所有积分方案
     *
     * @return
     * @throws Exception
     */
    public List<MultipleIntegralPlan> listAll() throws Exception;

    /**
     * 根据id修改启用状态
     *
     * @param id
     * @param status
     * @throws Exception
     */
    public void updateStatusById(@Param("id") int id, @Param("status") int status) throws Exception;

    /**
     * 列出启用的积分方案
     *
     * @return
     * @throws Exception
     */
    public List<MultipleIntegralPlan> listEnabledPlan() throws Exception;

    /**
     * 查询是否有同名方案存在
     *
     * @param name
     * @return
     * @throws Exception
     */
    public int countByName(String name) throws Exception;

    /**
     * 查询某会员价方案中存在几个多倍积分方案
     *
     * @return
     * @throws Exception
     */
    public int countByVipPricePlanId(int vipDishPricePlanId) throws Exception;
}
