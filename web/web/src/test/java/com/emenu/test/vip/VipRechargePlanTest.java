package com.emenu.test.vip;

import com.emenu.common.entity.vip.VipRechargePlan;
import com.emenu.common.enums.vip.VipRechargePlanStatusEnums;
import com.emenu.service.vip.VipRechargePlanService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

/**
 * VipRechargePlanTest
 *
 * @author: yangch
 * @time: 2015/10/23 18:41
 */
public class VipRechargePlanTest extends AbstractTestCase {
    @Autowired
    private VipRechargePlanService vipRechargePlanService;

    @Test
    public void newVipRechargePlan() throws SSException{
        VipRechargePlan vipRechargePlan = new VipRechargePlan();
        vipRechargePlan.setName("充500送50");
        vipRechargePlan.setPayAmount(new BigDecimal(500));
        vipRechargePlan.setRechargeAmount(new BigDecimal(550));
        vipRechargePlanService.newVipRechargePlan(vipRechargePlan);
    }

    @Test
    public void queryAllVipRechargePlan() throws SSException {
        List<VipRechargePlan> vipRechargePlanList = vipRechargePlanService.listAll();

        for (VipRechargePlan vipRechargePlan : vipRechargePlanList){
            System.out.println(vipRechargePlan.getName() + "   " + vipRechargePlan.getPayAmount() + "   " + vipRechargePlan.getRechargeAmount());
        }
    }

    @Test
    public void queryVipRechargePlanById() throws SSException {
        VipRechargePlan vipRechargePlan = vipRechargePlanService.queryById(1);

        System.out.println(vipRechargePlan.getName() + "   " + vipRechargePlan.getPayAmount() + "   " + vipRechargePlan.getRechargeAmount());
    }

    @Test
    public void updateVipRechargePlan() throws SSException{
        VipRechargePlan vipRechargePlan = new VipRechargePlan();
        vipRechargePlan.setId(2);
        vipRechargePlan.setName("充200送25");
        vipRechargePlan.setPayAmount(new BigDecimal(200));
        vipRechargePlan.setRechargeAmount(new BigDecimal(225));
        vipRechargePlanService.updateVipRechargePlan(1, vipRechargePlan);
    }

    @Test
    public void updateStatus() throws SSException{
        vipRechargePlanService.updateStatus(1, VipRechargePlanStatusEnums.Deleted.getId());
    }

    @Test
    public void delVipRechargePlanById() throws SSException{
        vipRechargePlanService.delById(1);
    }
}
