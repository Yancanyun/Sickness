package com.emenu.service.vip;

import com.emenu.common.entity.vip.DishPricePlan;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * 会员价方案管理service
 *
 * @author chenyuting
 * @date 2015/11/11 9:30
 */
public interface DishPricePlanService {

    /**
     * 会员价方案列表
     * @return
     * @throws SSException
     */
    public List<DishPricePlan> listAll() throws SSException;

    /**
     * 根据id查询会员价方案
     * @param id
     * @return
     * @throws SSException
     */
    public DishPricePlan queryById(int id) throws SSException;

    /**
     * 添加会员价方案
     * @param dishPricePlan
     * @return
     * @throws SSException
     */
    public DishPricePlan newPlan(DishPricePlan dishPricePlan) throws SSException;

    /**
     * 更新会员价方案
     * @param dishPricePlan
     * @throws SSException
     */
    public void updatePlan(DishPricePlan dishPricePlan) throws SSException;

    /**
     * 删除一个会员价方案
     * @param id
     * @throws SSException
     */
    public void delPlanById(int id) throws SSException;

    /**
     * 判断会员价方案名称是否为空
     * @param dishPricePlan
     * @return
     * @throws SSException
     */
    public boolean checkBeforeSave(DishPricePlan dishPricePlan) throws SSException;
}
