package com.emenu.test.dish;

import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishPackageDto;
import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.dish.DishPackage;
import com.emenu.common.entity.meal.MealPeriod;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.service.dish.DishPackageService;
import com.emenu.service.table.TableService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * dishPackageTest
 *
 * @author: yangch
 * @time: 2016/3/24 15:48
 */
public class DishPackageTest extends AbstractTestCase {
    @Autowired
    private DishPackageService dishPackageService;

    @Test
    public void newDishPackage() throws SSException {
        DishDto dishDto = new DishDto();
        dishDto.setName("套餐2");
        dishDto.setDishNumber("tc2");
        dishDto.setAssistantCode("tc2");
        dishDto.setUnitId(11);
        dishDto.setPrice(new BigDecimal("120"));
        dishDto.setSaleType(1);
        dishDto.setCategoryId(6);
        dishDto.setTagId(47);
        dishDto.setDescription("");
        dishDto.setIsNetworkAvailable(1);
        dishDto.setIsVipPriceAvailable(1);
        dishDto.setIsVoucherAvailable(1);
        dishDto.setTimeLimit(0);
        dishDto.setCreatedPartyId(1);
        dishDto.setPrinterId(-1);

        List<Integer> mealPeriodIdList = new ArrayList<Integer>();
        for (int i = 0; i < 6; i++) {
            mealPeriodIdList.add(i + 1);
        }
        dishDto.setMealPeriodIdList(mealPeriodIdList);


        List<DishPackage> dishPackageList = new ArrayList<DishPackage>();
        DishPackage dishPackage1 = new DishPackage();
        dishPackage1.setDishId(25);
        dishPackage1.setDishQuantity(1);
        DishPackage dishPackage2 = new DishPackage();
        dishPackage2.setDishId(26);
        dishPackage2.setDishQuantity(2);
        dishPackageList.add(dishPackage1);
        dishPackageList.add(dishPackage2);

        dishPackageService.newDishPackage(dishDto, dishPackageList);
    }

    @Test
    public void queryDishPackageById() throws SSException {
        DishPackageDto dishPackageDto = dishPackageService.queryDishPackageById(28);
        System.out.println(dishPackageDto.getDishDto().getName());
        List<DishDto> childDishDtoList = dishPackageDto.getChildDishDtoList();
        for (DishDto dishDto : childDishDtoList) {
            System.out.println(dishDto.getName());
        }
    }
}
