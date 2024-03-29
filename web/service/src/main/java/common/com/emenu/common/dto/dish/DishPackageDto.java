package com.emenu.common.dto.dish;

import com.emenu.common.entity.dish.DishPackage;

import java.util.List;

/**
 * DishPackageDto
 *
 * @author dujuan
 * @date: 2015/12/14
 *
 * @modify yangch
 * @date: 2016/4/16
 */
public class DishPackageDto {
    // 菜品
    private DishDto dishDto;

    // 套餐下具体菜品
    private List<DishDto> childDishDtoList;

    public DishDto getDishDto() {
        return dishDto;
    }

    public void setDishDto(DishDto dishDto) {
        this.dishDto = dishDto;
    }

    public List<DishDto> getChildDishDtoList() {
        return childDishDtoList;
    }

    public void setChildDishDtoList(List<DishDto> childDishDtoList) {
        this.childDishDtoList = childDishDtoList;
    }
}
