package com.emenu.test.vip;

import com.emenu.common.entity.vip.DishPricePlan;
import com.emenu.service.vip.DishPricePlanService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * 会员价方案测试用例
 *
 * @author chenyuting
 * @date 2015/11/11 14:34
 */
public class DishPricePlanTest extends AbstractTestCase{

    @Autowired
    private DishPricePlanService dishPricePlanService;

    @Test
    public void newPlan() throws SSException{
        DishPricePlan dishPricePlan = new DishPricePlan();
        dishPricePlan.setName("九折");
        dishPricePlan.setDescription("会员可享受9折的菜品价钱");
        dishPricePlanService.newPlan(dishPricePlan);
    }

    @Test
    public void listAll() throws SSException{
        List<DishPricePlan> dishPricePlanList = dishPricePlanService.listAll();
        for (DishPricePlan dishPricePlan: dishPricePlanList){
            System.out.println(dishPricePlan.getName());
            System.out.println(dishPricePlan.getDescription());
        }
    }

    @Test
    public void queryById() throws SSException{
        Integer id = 1;
        DishPricePlan dishPricePlan = dishPricePlanService.queryById(id);
        System.out.println(dishPricePlan.getName());
        System.out.println(dishPricePlan.getDescription());
    }

    @Test
    public void updatePlan() throws SSException{
        DishPricePlan dishPricePlan = new DishPricePlan();
        dishPricePlan.setId(2);
        dishPricePlan.setName("八折");
        dishPricePlan.setDescription("会员可享受8折菜品优惠");
        dishPricePlanService.updatePlan(dishPricePlan);
    }

    @Test
    public void delPlanById() throws SSException{
        Integer id = 2;
        dishPricePlanService.delPlanById(id);
    }
}