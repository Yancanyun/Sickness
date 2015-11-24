package com.emenu.mapper.dish;

import com.emenu.common.entity.dish.DishMealPeriod;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜品-餐段Mapper
 *
 * @author: zhangteng
 * @time: 2015/11/24 14:15
 **/
public interface DishMealPeriodMapper {

    /**
     * 根据菜品ID删除
     *
     * @param dishId
     * @throws Exception
     */
    public void delByDishId(@Param("dishId") int dishId) throws Exception;

    /**
     * 根据菜品ID查询
     *
     * @param dishId
     * @return
     * @throws Exception
     */
    public List<DishMealPeriod> listByDishId(@Param("dishId") int dishId) throws Exception;
}
