package com.emenu.mapper.dish;

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

}