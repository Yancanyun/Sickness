package com.emenu.test.vip;

import com.emenu.common.entity.vip.MultipleIntegralPlan;
import com.emenu.common.enums.vip.MultipleIntegralPlanStatusEnums;
import com.emenu.service.vip.MultipleIntegralPlanService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

/**
 * MultipleIntegralPlanServiceTest
 *
 * @author Wang Liming
 * @date 2015/12/9 10:28
 */
public class MultipleIntegralPlanServiceTest extends AbstractTestCase {

    @Autowired
    MultipleIntegralPlanService multipleIntegralPlanService;

    @Test
    public void newPlan() throws SSException {
        MultipleIntegralPlan plan = new MultipleIntegralPlan();
        plan.setName("店庆2");
        plan.setStatus(0);
        plan.setIntegralMultiples(new BigDecimal(2.5));
        multipleIntegralPlanService.newMultipleIntegralPlan(plan);
    }

    @Test
    public void listAll() throws SSException {
        List<MultipleIntegralPlan> list = multipleIntegralPlanService.listAll();
        for (MultipleIntegralPlan plan : list) {
            System.out.println(plan);
        }
    }

    @Test
    public void updatePlan() throws SSException {
        MultipleIntegralPlan plan = new MultipleIntegralPlan();
        plan.setId(2);
        plan.setName("店庆2");
        plan.setStatus(1);
        plan.setIntegralMultiples(new BigDecimal(1.50));
        multipleIntegralPlanService.updateById(plan);
    }

    @Test
    public void delPlan() throws SSException {
        multipleIntegralPlanService.delById(2);
    }

    @Test
    public void listEnabled() throws SSException {
        List<MultipleIntegralPlan> list = multipleIntegralPlanService.listEnabledPlan();
        for (MultipleIntegralPlan plan : list) {
            System.out.println(plan);
        }
    }

    @Test
    public void updateStatus() throws SSException {
        multipleIntegralPlanService.updateStatusById(3, MultipleIntegralPlanStatusEnums.Enabled);
    }
}
