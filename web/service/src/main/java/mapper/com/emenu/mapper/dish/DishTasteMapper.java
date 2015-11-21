package com.emenu.mapper.dish;

import com.emenu.common.entity.dish.DishTaste;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 菜品口味Mapper
 *
 * @author: zhangteng
 * @time: 2015/11/20 15:02
 **/
public interface DishTasteMapper {

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
    public List<DishTaste> listByDishId(@Param("dishId") int dishId) throws Exception;
}
