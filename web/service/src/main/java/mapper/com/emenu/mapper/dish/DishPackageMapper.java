package com.emenu.mapper.dish;

import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.dish.DishPackage;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * DishPackageMapper
 * 套餐Mapper
 * @author dujuan
 * @date: 2015/12/12
 */
public interface DishPackageMapper {

    /**
     * 根据套餐ID删除套餐与菜品关联表列表
     * @param packageId
     * @throws SSException
     */
    public void delByPackageId(@Param("packageId") int packageId) throws SSException;


    /**
     * 据套餐ID查询菜品关联表列表
     * @param packageId
     * @return
     * @throws SSException
     */
    public List<DishPackage> listDishPackage(@Param("packageId") int packageId) throws SSException;

    /**
     * 根据PackageId计算总数
     * @param packageId
     * @return
     * @throws SSException
     */
    public int countByPackageId(int packageId) throws SSException;

    /**
     * 查询全部
     *
     * @return
     * @throws Exception
     */
    public List<Dish> listAll() throws Exception;

    /**
     * 根据菜品Id来判断这个菜品是否是套餐
     *
     * @return
     * @throws Exception
     */
    public Integer judgeIsOrNotPackage(@Param("dishId") Integer dishId) throws Exception;

    /**
     * 根据套餐id和菜品id查询菜品的数量
     * @param packageId
     * @param dishId
     * @return
     * @throws Exception
     */
    public Integer queryDishQuantityByPackageIdAndDishId(@Param("packageId") Integer packageId,
                                                         @Param("dishId") Integer dishId) throws Exception;
}
