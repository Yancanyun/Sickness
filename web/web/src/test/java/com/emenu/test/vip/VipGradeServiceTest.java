package com.emenu.test.vip;

import com.emenu.common.entity.vip.VipGrade;
import com.emenu.service.vip.VipGradeService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

/**
 * VipGradeServiceTest
 *
 * @author Wang LM
 * @date 2015/12/16 14:01
 */
public class VipGradeServiceTest extends AbstractTestCase {

    @Autowired
    private VipGradeService vipGradeService;

    @Test
    public void newVipGrade() throws SSException{
        VipGrade vipGrade = new VipGrade();
        vipGrade.setName("春节庆典");
        vipGrade.setVipDishPricePlanId(1);
        vipGrade.setMinConsumption(new BigDecimal(1.00));
        vipGrade.setCreditAmount(new BigDecimal(1.00));
        vipGrade.setSettlementCycle(1);
        vipGrade.setPreReminderAmount(new BigDecimal(1.00));
        vipGradeService.newVipGrade(vipGrade);
    }

    @Test
    public void listAll() throws SSException{
        List<VipGrade> list = vipGradeService.listAll();
        for (VipGrade vipGrade : list){
            System.out.println(vipGrade);
        }
    }

    @Test
    public void updateById() throws SSException{
        VipGrade vipGrade = new VipGrade();
        vipGrade.setId(2);
        vipGrade.setName("中秋庆典");
        vipGrade.setVipDishPricePlanId(1);
        vipGrade.setMinConsumption(new BigDecimal(1.00));
        vipGrade.setCreditAmount(new BigDecimal(1.00));
        vipGrade.setSettlementCycle(1);
        vipGrade.setPreReminderAmount(new BigDecimal(1.00));
        vipGradeService.updateById(vipGrade);
    }

    @Test
    public void queryById() throws SSException{
        VipGrade vipGrade = vipGradeService.queryById(2);
        System.out.println(vipGrade);
    }

    @Test
    public void delById() throws SSException{
        vipGradeService.delById(2);
    }

    @Test
    public void countByVipPricePlanId() throws SSException{
        System.out.println(vipGradeService.countByVipPricePlanId(1));
    }
}
