package com.emenu.test.vip;

import com.emenu.common.entity.vip.VipDishPricePlan;
import com.emenu.service.vip.VipDishPricePlanService;
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
public class VipVipDishPricePlanTest extends AbstractTestCase{

    @Autowired
    private VipDishPricePlanService vipDishPricePlanService;

    @Test
    public void newPlan() throws SSException{
        VipDishPricePlan vipDishPricePlan = new VipDishPricePlan();
        vipDishPricePlan.setName("八五折");
        vipDishPricePlan.setDescription("会员可享受8.5折的菜品价钱");
        vipDishPricePlanService.newPlan(vipDishPricePlan);
    }

    @Test
    public void listAll() throws SSException{
        List<VipDishPricePlan> vipDishPricePlanList = vipDishPricePlanService.listAll();
        for (VipDishPricePlan vipDishPricePlan : vipDishPricePlanList){
            System.out.println(vipDishPricePlan.getName());
            System.out.println(vipDishPricePlan.getDescription());
        }
    }

    @Test
    public void queryById() throws SSException{
        Integer id = 1;
        VipDishPricePlan vipDishPricePlan = vipDishPricePlanService.queryById(id);
        System.out.println(vipDishPricePlan.getName());
        System.out.println(vipDishPricePlan.getDescription());
    }

    @Test
    public void updatePlan() throws SSException{
        VipDishPricePlan vipDishPricePlan = new VipDishPricePlan();
        vipDishPricePlan.setId(2);
        vipDishPricePlan.setName("八折");
        vipDishPricePlan.setDescription("会员可享受8折菜品优惠");
        vipDishPricePlanService.updatePlan(vipDishPricePlan);
    }

    @Test
    public void delPlanById() throws SSException{
        Integer id = 2;
        vipDishPricePlanService.delPlanById(id);
    }
}