package com.emenu.test.dish;

import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishSmallDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.service.dish.DishService;
import com.emenu.test.AbstractTestCase;
import org.apache.commons.beanutils.BeanUtils;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * DishTest
 *
 * @author: zhangteng
 * @time: 2015/11/19 21:07
 **/
public class DishTest extends AbstractTestCase {

    @Autowired
    private DishService dishService;

    public static void main(String[] args) {
        DishDto dishDto = new DishDto();
        dishDto.setName("鸡公煲");
        dishDto.setAssistantCode("jgb");
        dishDto.setPrice(new BigDecimal("39.00"));

        Dish dish = new Dish();
        dish = dish.constructFromDto(dishDto);

        System.out.println(dish.toString());
    }

    @Test
    public void queryById() throws Exception {
        DishDto dishDto = dishService.queryById(17);
        System.out.println(BeanUtils.describe(dishDto));
    }

    @Test
    public void listByKeyword() throws Exception{
        List<DishSmallDto> dishSmallDtoList = Collections.emptyList();
        dishSmallDtoList = dishService.listByKeyword("酒");
        for(DishSmallDto dishSmallDto:dishSmallDtoList){
            System.out.print(dishSmallDto.getName());
        }
    }
}
