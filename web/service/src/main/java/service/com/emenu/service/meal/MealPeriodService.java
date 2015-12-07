package com.emenu.service.meal;

import com.emenu.common.entity.meal.MealPeriod;
import com.emenu.common.enums.meal.MealPeriodIsCurrentEnums;
import com.emenu.common.enums.meal.MealPeriodStatusEnums;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * MealPeriodService
 *
 * @author Wang Liming
 * @date 2015/11/10 10:45
 */
public interface MealPeriodService {

    /**
     * 新增餐段
     *
     * @param mealPeriod
     * @return
     * @throws SSException
     */
    public MealPeriod newMealPeriod(MealPeriod mealPeriod) throws SSException;

    /**
     * 根据id修改餐段
     *
     * @param mealPeriod
     * @throws SSException
     */
    public void updateMealPeriod(MealPeriod mealPeriod) throws SSException;

    /**
     * 根据id删除餐段
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
    public void updateStatusById(int id, MealPeriodStatusEnums status) throws SSException;

    /**
     * 根据id修改为当前餐段
     *
     * @param id
     * @param isCurrent
     * @throws SSException
     */
    public void updateCurrentMealPeriod(int id, MealPeriodIsCurrentEnums isCurrent) throws SSException;

    /**
     * 查询所有餐段
     *
     * @return
     * @throws SSException
     */
    public List<MealPeriod> listAll() throws SSException;

    /**
     * 查询所有可用餐段
     *
     * @return
     * @throws SSException
     */
    public List<MealPeriod> listEnabledMealPeriod() throws SSException;

    /**
     * 根据id查询餐段
     *
     * @param id
     * @return
     * @throws SSException
     */
    public MealPeriod queryById(int id) throws SSException;

    /**
     * 查询当前餐段
     * 0-非当前餐段 1-当前餐段
     * 只能查询当前餐段且当前餐段只能有一个
     *
     * @param isCurrent
     * @return
     * @throws SSException
     */
    public MealPeriod queryByCurrentPeriod(MealPeriodIsCurrentEnums isCurrent) throws SSException;
}
