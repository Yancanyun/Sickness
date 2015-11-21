package com.emenu.service.vip;

import com.emenu.common.entity.vip.VipDishPricePlan;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * 会员价方案管理service
 *
 * @author chenyuting
 * @date 2015/11/11 9:30
 */
public interface VipDishPricePlanService {

    /**
     * 会员价方案列表
     * @return
     * @throws SSException
     */
    public List<VipDishPricePlan> listAll() throws SSException;

    /**
     * 根据id查询会员价方案
     * @param id
     * @return
     * @throws SSException
     */
    public VipDishPricePlan queryById(int id) throws SSException;

    /**
     * 添加会员价方案
     * @param vipDishPricePlan
     * @return
     * @throws SSException
     */
    public VipDishPricePlan newPlan(VipDishPricePlan vipDishPricePlan) throws SSException;

    /**
     * 更新会员价方案
     * @param vipDishPricePlan
     * @throws SSException
     */
    public void updatePlan(VipDishPricePlan vipDishPricePlan) throws SSException;

    /**
     * 删除一个会员价方案
     * @param id
     * @throws SSException
     */
    public void delPlanById(int id) throws SSException;

    /**
     * 判断会员价方案名称是否为空
     * @param vipDishPricePlan
     * @return
     * @throws SSException
     */
    public boolean checkBeforeSave(VipDishPricePlan vipDishPricePlan) throws SSException;
}
