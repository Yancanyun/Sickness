package com.emenu.service.meal;

import com.emenu.common.entity.meal.MealPeriod;
import com.emenu.common.enums.meal.MealPeriodStateEnums;
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
     *
     * @param id
     * @param state
     * @throws SSException
     */
    public void updateStateById(int id, MealPeriodStateEnums state) throws SSException;

    /**
     * 查询所有餐段
     *
     * @return
     * @throws SSException
     */
    public List<MealPeriod> listAll() throws SSException;

    /**
     * 根据id查询餐段
     *
     * @param id
     * @return
     * @throws SSException
     */
    public MealPeriod queryById(int id) throws SSException;
}