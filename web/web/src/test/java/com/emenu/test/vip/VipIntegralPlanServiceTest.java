package com.emenu.test.vip;

import com.emenu.common.dto.vip.VipIntegralDto;
import com.emenu.common.entity.vip.VipIntegralPlan;
import com.emenu.service.vip.VipIntegralPlanService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author chenyuting
 * @date 2016/1/20 10:21
 */
public class VipIntegralPlanServiceTest extends AbstractTestCase {

    @Autowired
    private VipIntegralPlanService vipIntegralPlanService;

    @Test
    public void listAll() throws SSException{
        List<VipIntegralPlan> vipIntegralPlanList = new ArrayList<VipIntegralPlan>();
        vipIntegralPlanList = vipIntegralPlanService.listAll();
        for (VipIntegralPlan vipIntegralPlan: vipIntegralPlanList){
            System.out.print(vipIntegralPlan.getGradeId() + " ");
            System.out.print(vipIntegralPlan.getType() + " ");
            System.out.println(vipIntegralPlan.getValue());
            System.out.println("********************");
        }
    }

    @Test
    public void listAllDtos() throws SSException{
        List<VipIntegralDto> vipIntegralDtoList = Collections.emptyList();
        vipIntegralDtoList = vipIntegralPlanService.listAllDtos();
        for (VipIntegralDto vipIntegralDto: vipIntegralDtoList){
            System.out.print(vipIntegralDto.getIntegralType() + " ");
            System.out.println(vipIntegralDto.getValue());
        }
    }

    @Test
    public void newPlan() throws SSException{
        VipIntegralPlan vipIntegralPlan = new VipIntegralPlan();
        BigDecimal value = new BigDecimal("2.00");
        vipIntegralPlan.setGradeId(1);
        vipIntegralPlan.setType(0);
        vipIntegralPlan.setValue(value);
        vipIntegralPlanService.newPlan(vipIntegralPlan);
        System.out.println("插入成功！");
    }

}