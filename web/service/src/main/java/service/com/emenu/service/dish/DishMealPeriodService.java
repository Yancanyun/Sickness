package com.emenu.service.dish;

import com.emenu.common.entity.dish.DishMealPeriod;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * 菜品-餐段Service
 *
 * @author: zhangteng
 * @time: 2015/11/24 14:13
 **/
public interface DishMealPeriodService {

    /**
     * 添加
     *
     * @param dishId
     * @param mealPeriodIdList
     * @throws SSException
     */
    public void newDishMealPeriod(int dishId,
                                  List<Integer> mealPeriodIdList) throws SSException;

    /**
     * 更新
     *
     * @param dishId
     * @param mealPeriodIdList
     * @throws SSException
     */
    public void updateDishMealPeriod(int dishId,
                                     List<Integer> mealPeriodIdList) throws SSException;

    /**
     * 根据菜品ID删除
     *
     * @param dishId
     * @throws SSException
     */
    public void delByDishId(int dishId) throws SSException;

    /**
     * 根据菜品ID查询
     *
     * @param dishId
     * @return
     * @throws SSException
     */
    public List<DishMealPeriod> listByDishId(int dishId) throws SSException;

    /**
     * 根据菜品ID获取餐段ID列表
     *
     * @param dishId
     * @return
     * @throws SSException
     */
    public List<Integer> listMealPeriodIdByDishId(int dishId) throws SSException;
}
