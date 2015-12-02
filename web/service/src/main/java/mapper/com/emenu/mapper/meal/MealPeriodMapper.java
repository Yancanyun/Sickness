package com.emenu.mapper.meal;

import com.emenu.common.entity.meal.MealPeriod;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * MealPeriodMapper
 *
 * @author Wang Liming
 * @date 2015/11/10 11:16
 */
public interface MealPeriodMapper {

    /**
     * 根据id修改启用状态
     * 0-未启用 1-启用
     *
     * @param id
     * @param status
     * @throws Exception
     */
    public void updateStatusById(@Param("id") int id, @Param("status") int status) throws Exception;

    /**
     * 根据id修改为当前餐段
     *
     * @param id
     * @param currentMealPeriod
     * @throws Exception
     */
    public void updateCurrentMealPeriod(@Param("id") int id, @Param("currentMealPeriod") int currentMealPeriod) throws Exception;

    /**
     * 查询所有餐段
     *
     * @return
     * @throws Exception
     */
    public List<MealPeriod> listAll() throws Exception;

    /**
     * 查询所有可用餐段
     *
     * @return
     * @throws Exception
     */
    public List<MealPeriod> listEnabledMealPeriod() throws Exception;

    /**
     * 判断同名餐段是否存在
     *
     * @param name
     * @return
     * @throws Exception
     */
    public Integer countByName(String name) throws Exception;

    /**
     * 查询关联表中是否正在使用
     *
     * @param id
     * @return
     * @throws Exception
     */
    public Integer countById(int id) throws Exception;

    /**
     * 查询当前餐段
     * 0-非当前餐段 1-当前餐段
     * 只能查询当前餐段且当前餐段只能有一个
     *
     * @param isCurrent
     * @return
     * @throws Exception
     */
    public MealPeriod queryByCurrentPeriod(int isCurrent) throws Exception;
}
