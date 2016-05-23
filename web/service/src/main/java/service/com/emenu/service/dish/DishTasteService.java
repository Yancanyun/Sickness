package com.emenu.service.dish;

import com.emenu.common.entity.dish.DishTaste;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * 菜品-口味Service
 *
 * @author: zhangteng
 * @time: 2015/11/20 15:03
 **/
public interface DishTasteService {

    /**
     * 添加菜品口味
     *
     * @param dishId
     * @param tasteIdList
     * @throws SSException
     */
    public void newDishTaste(int dishId,
                             List<Integer> tasteIdList) throws SSException;

    /**
     * 添加菜品口味
     *
     * @param dishTaste
     * @throws SSException
     */
    public void newDishTaste(DishTaste dishTaste) throws SSException;

    /**
     * 更新菜品口味
     *
     * @param dishTaste
     * @throws SSException
     */
    public void updateDishTaste(DishTaste dishTaste) throws SSException;

    /**
     * 更新菜品口味
     *
     * @param dishId
     * @param tasteIdList
     * @throws SSException
     */
    public void updateDishTaste(int dishId,
                                List<Integer> tasteIdList) throws SSException;

    /**
     * 根据菜品ID删除
     *
     * @throws SSException
     * @param dishId
     */
    public void delByDishId(int dishId) throws SSException;

    /**
     * 根据菜品ID查询
     * @param dishId
     * @return
     * @throws SSException
     */
    public List<DishTaste> listByDishId(int dishId) throws SSException;
}
