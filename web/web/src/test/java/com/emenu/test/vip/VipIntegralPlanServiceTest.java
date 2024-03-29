package com.emenu.test.vip;

import com.emenu.common.dto.vip.VipIntegralDto;
import com.emenu.common.entity.vip.VipGrade;
import com.emenu.common.entity.vip.VipIntegralPlan;
import com.emenu.service.vip.VipGradeService;
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

    @Autowired
    private VipGradeService vipGradeService;

    @Test
    public void listAll() throws SSException{
        List<VipIntegralPlan> vipIntegralPlanList = new ArrayList<VipIntegralPlan>();
        Integer gradeId = 1;
        vipIntegralPlanList = vipIntegralPlanService.listByGradeId(gradeId);
        VipGrade vipGrade = vipGradeService.queryById(gradeId);
        System.out.println("当前等级为" + vipGrade.getName());
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
        Integer gradeId = 1;
        vipIntegralDtoList = vipIntegralPlanService.listDtosGradeId(gradeId);
        VipGrade vipGrade = vipGradeService.queryById(gradeId);
        System.out.println("当前等级为" + vipGrade.getName());
        for (VipIntegralDto vipIntegralDto: vipIntegralDtoList){
            System.out.print(vipIntegralDto.getIntegralType() + " ");
            System.out.println(vipIntegralDto.getValue());
        }
    }

    /*@Test
    public void newPlan() throws SSException{
        VipIntegralPlan vipIntegralPlan = new VipIntegralPlan();
        BigDecimal value = new BigDecimal("8.00");
        vipIntegralPlan.setGradeId(1);
        vipIntegralPlan.setType(7);
        vipIntegralPlan.setValue(value);
        vipIntegralPlanService.newPlan(vipIntegralPlan);
        System.out.println("插入成功！");
    }*/

    @Test
    public void newPlans() throws SSException{
        List<VipIntegralPlan> vipIntegralPlanList = new ArrayList<VipIntegralPlan>();
        VipIntegralPlan vipIntegralPlan = new VipIntegralPlan();
        BigDecimal value = new BigDecimal("1.01");
        Integer gradeId = 2;
        vipIntegralPlan.setType(1);
        vipIntegralPlan.setValue(value);
        vipIntegralPlanList.add(vipIntegralPlan);
        vipIntegralPlanService.newPlans(vipIntegralPlanList, gradeId);
        System.out.println("*********success***********");
    }

}