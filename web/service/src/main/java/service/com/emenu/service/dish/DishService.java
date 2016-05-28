package com.emenu.service.dish;

import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishSmallDto;
import com.emenu.common.dto.dish.DishTagDto;
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
     * @param searchDto
     * @return
     * @throws SSException
     */
    public List<Dish> listBySearchDto(DishSearchDto searchDto) throws SSException;

    /**
     * 根据搜索条件计算数量
     *
     * @param searchDto
     * @return
     * @throws SSException
     */
    public int countBySearchDto(DishSearchDto searchDto) throws SSException;

    /**
     * 添加菜品
     *
     * @param dishDto
     * @throws SSException
     */
    public DishDto newDish(DishDto dishDto) throws SSException;

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

    /**
     * 根据菜品名或助记码模糊查询DishSmallDto
     *
     * @param keyword
     * @return
     * @throws SSException
     */
    public List<DishSmallDto> listByKeyword(String keyword) throws SSException;


    //***************************顾客端Service*************************************

    /**
     * 顾客端菜品列表（分页）
     * keyword为空返回所有结果
     * @author: chenyuting
     * @return
     * @throws SSException
     */
    public List<DishDto> listBySearchDtoInMobile(DishSearchDto dishSearchDto) throws SSException;
}

