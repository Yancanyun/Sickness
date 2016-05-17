package com.emenu.test.dish;

import com.emenu.common.entity.dish.CostCard;
import com.emenu.mapper.dish.CostCardMapper;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * Created by ALIENWARE on 2016/5/16.
 */
public class CostCardTest extends AbstractTestCase {

    @Autowired
    CostCardMapper costCardMapper;

    @Test
    public void testNewCostCard() throws SSException
    {
        CostCard costCard = new CostCard();
        costCard.setCostCardNumber("0001");
        costCard.setDishId(5);
        costCard.setMainCost(new BigDecimal("1.0"));
        costCard.setAssistCost(new BigDecimal("1.0"));
        costCard.setDeliciousCost(new BigDecimal("1.0"));
        costCard.setStandardCost(new BigDecimal("3.0"));
        costCardMapper.newCostCard(costCard);
    }

    @Test
    public void testDelCostCardById() throws SSException
    {
        costCardMapper.delCostCardById(2);
    }

}
