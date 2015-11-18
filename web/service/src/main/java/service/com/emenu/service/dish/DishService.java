package com.emenu.service.dish;

import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.dto.dish.tag.DishDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.enums.dish.DishStatusEnums;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * 菜品Service
 *
 * @author: zhangteng
 * @time: 2015/11/16 10:44
 **/
public interface DishService {

    /**
     * 查询全部
     *
     * @return
     * @throws SSException
     */
    public List<Dish> listAll() throws SSException;

    /**
     * 根据分页和搜索条件查询
     *
     * @param pageNo
     * @param pageSize
     * @param searchDto
     * @return
     * @throws SSException
     */
    public List<Dish> listByPageAndSearchDto(int pageNo,
                                             int pageSize,
                                             DishSearchDto searchDto) throws SSException;

    /**
     * 根据搜索条件计算数量
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    public List<Dish> countBySearchDto(DishSearchDto searchDto) throws SSException;

    /**
     * 添加菜品
     *
     * @param dishDto
     * @throws SSException
     */
    public void newDish(DishDto dishDto) throws SSException;

    /**
     * 更新菜品
     *
     * @param dishDto
     * @throws SSException
     */
    public void updateDish(DishDto dishDto) throws SSException;

    /**
     * 更改菜品状态
     *
     * @param id
     * @param statusEnums
     * @throws SSException
     */
    public void updateStatusById(int id,
                                 DishStatusEnums statusEnums) throws SSException;

    /**
     * 根据id删除
     *
     * @param id
     * @throws SSException
     */
    public void delById(int id) throws SSException;

    /**
     * 根据id查询
     *
     * @param id
     * @return
     * @throws SSException
     */
    public DishDto queryById(int id) throws SSException;
}
