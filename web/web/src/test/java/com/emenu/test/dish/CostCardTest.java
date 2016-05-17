package com.emenu.test.dish;

import com.emenu.common.entity.dish.CostCard;
import com.emenu.mapper.dish.CostCardMapper;
import com.emenu.service.dish.CostCardService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * 成本卡测试
 *
 * @author: quanyibo
 * @time: 2016/5/16 9:04
 **/
public class CostCardTest extends AbstractTestCase {

    @Autowired
    CostCardMapper costCardMapper;

    @Autowired
    CostCardService costCardService;

    @Test
    public void testNewCostCard() throws SSException
    {
        CostCard costCard = new CostCard();
        costCard.setCostCardNumber("0001");
        costCard.setDishId(6);
        costCard.setMainCost(new BigDecimal("1.0"));
        costCard.setAssistCost(new BigDecimal("1.0"));
        costCard.setDeliciousCost(new BigDecimal("1.0"));
        costCard.setStandardCost(new BigDecimal("3.0"));
        costCardService.newCostCard(costCard);
    }

    @Test
    public void testUpdateCostCard() throws SSException
    {
        CostCard costCard = new CostCard();
        costCard.setId(1);
        costCard.setCostCardNumber("4");
        costCard.setDishId(9);
        costCard.setMainCost(new BigDecimal("2.0"));
        costCard.setAssistCost(new BigDecimal("2.0"));
        costCard.setDeliciousCost(new BigDecimal("2.0"));
        costCard.setStandardCost(new BigDecimal("6.0"));
        costCardService.updateCostCard(costCard);
    }

    @Test
    public void testDelCostCardById() throws SSException
    {
        costCardService.delCostCardById(2);
    }


}