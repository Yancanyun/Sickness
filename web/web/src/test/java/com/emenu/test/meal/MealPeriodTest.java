package com.emenu.test.meal;

import com.emenu.common.entity.meal.MealPeriod;
import com.emenu.common.enums.meal.MealPeriodIsCurrentEnums;
import com.emenu.common.enums.meal.MealPeriodStateEnums;
import com.emenu.service.meal.MealPeriodService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * MealPeriodTest
 *
 * @author Wang Liming
 * @date 2015/11/10 16:13
 */
public class MealPeriodTest extends AbstractTestCase {

    @Autowired
    MealPeriodService mealPeriodService;

    @Test
    public void newMealPeriod() throws SSException{
        MealPeriod mealPeriod = new MealPeriod();
        mealPeriod.setName("早上");
        mealPeriod.setState(1);
        mealPeriod.setWeight(1);
        mealPeriodService.newMealPeriod(mealPeriod);
    }

    @Test
    public void updateMealPeriod() throws SSException{
        MealPeriod mealPeriod = new MealPeriod();
        mealPeriod.setId(6);
        mealPeriod.setName("早餐");
        mealPeriod.setWeight(2);
        mealPeriodService.updateMealPeriod(mealPeriod);
    }

    @Test
    public void deleteById() throws SSException{
        mealPeriodService.delById(8);
    }

    @Test
    public void updateStateById() throws SSException{
        mealPeriodService.updateStateById(8, MealPeriodStateEnums.Disabled);
    }

    @Test
    public void updateCurrentPeriod() throws SSException{
        mealPeriodService.updateCurrentMealPeriod(1, MealPeriodIsCurrentEnums.Using);
    }

    @Test
    public void listAll() throws SSException{
        List<MealPeriod> list = mealPeriodService.listAll();
        for (MealPeriod mealPeriod : list){
            System.out.println(mealPeriod);
        }
    }

    @Test
    public void queryById() throws SSException{
        System.out.println(mealPeriodService.queryById(1));
    }

    @Test
    public void queryByCurrentPeriod() throws SSException{
        System.out.println(mealPeriodService.queryByCurrentPeriod(MealPeriodIsCurrentEnums.UnUsing));
    }
}
