package com.emenu.service.dish;

import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishPackageDto;
import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.dish.DishPackage;
import com.emenu.common.enums.dish.DishStatusEnums;
import com.pandawork.core.common.exception.SSException;
import org.apache.ibatis.annotations.Param;

import java.math.BigDecimal;
import java.util.List;

/**
 * DishPackageService
 * 套餐Service
 * @author dujuan
 * @date: 2015/12/11
 *
 * @modify yangch
 * @date: 2016/3/24
 */
public interface DishPackageService {

    /**
     * 新增一个套餐
     * @param dishDto
     * @param dishPackageList
     * @throws SSException
     */
    public void newDishPackage(DishDto dishDto, List<DishPackage> dishPackageList) throws SSException;

    /**
     * 修改套餐
     * @param dishDto
     * @param dishPackageList
     * @throws SSException
     */
    public void updateDishPackage(DishDto dishDto, List<DishPackage> dishPackageList) throws SSException;

    /**
     * 删除套餐
     * @param packageId
     * @throws SSException
     */
    public void delDishPackage(int packageId) throws SSException;

    /**
     * 查询单个套餐
     * @param packageId
     * @return
     * @throws SSException
     */
    public DishPackageDto queryDishPackageById(int packageId) throws SSException;

    /**
     * 修改套餐状态
     * @param packageId
     * @param dishStatusEnums
     * @throws SSException
     */
    public void updateDishPackageStatus(int packageId,  DishStatusEnums dishStatusEnums) throws SSException;

    /**
     * 根据packageId统计菜品总数
     * @return
     * @throws SSException
     */
    public int countByPackageId(int packageId) throws SSException;

    /**
     * 根据packageId统计菜品总价
     * @param packageId
     * @return
     * @throws SSException
     */
    public BigDecimal countPriceByPackageId(int packageId) throws SSException;

    /**
     * 查询全部
     *
     * @return
     * @throws SSException
     */
    public List<Dish> listAll() throws SSException;


    /**
     * 根据菜品Id来判断这个菜品是否属于套餐
     * 返回1为是套餐
     * @return
     * @throws SSException
     */
    public int judgeIsOrNotPackage(int dishId) throws SSException;

    /**
     * 根据套餐id和菜品id查询菜品数量
     * @param packageId
     * @param dishId
     * @return
     * @throws SSException
     */
    public Integer queryDishQuantityByPackageIdAndDishId(Integer packageId, Integer dishId) throws SSException;
}
