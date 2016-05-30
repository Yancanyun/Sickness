package com.emenu.test.dish;

import com.emenu.common.dto.dish.CostCardItemDto;
import com.emenu.common.entity.dish.CostCardItem;
import com.emenu.mapper.dish.CostCardItemMapper;
import com.emenu.service.dish.CostCardItemService;
import com.emenu.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * CostCardItem
 *
 * @author xubaorong
 * @date 2016/5/17.
 */
public class CostCardItemTest extends AbstractTestCase {
    @Autowired
    private CostCardItemService costCardItemService;

    @Autowired
    private CostCardItemMapper costCardItemMapper;
    @Test
    public void newcostCarditem() throws Exception{
       /* CostCardItem costCardItem = new CostCardItem();
        costCardItem.setCostCardId(1);
        costCardItem.setIngredientId(1);
        costCardItem.setIsAutoOut(1);
        costCardItem.setItemType(2);
        costCardItem.setNetCount(new BigDecimal(2.00));
        costCardItem.setTotalCount(new BigDecimal(3.00));
        costCardItemService.newCostCardItem(costCardItem);*/
        CostCardItem costCardItem1 = new CostCardItem();
        costCardItem1.setCostCardId(6);
        costCardItem1.setIngredientId(2);
        costCardItem1.setIsAutoOut(1);
        costCardItem1.setItemType(0);
        costCardItem1.setNetCount(new BigDecimal(4.00));
        costCardItem1.setOtherCount(new BigDecimal(5.00));
        CostCardItem costCardItem2 = new CostCardItem();
        costCardItem2.setCostCardId(6);
        costCardItem2.setIngredientId(3);
        costCardItem2.setIsAutoOut(1);
        costCardItem2.setItemType(0);
        costCardItem2.setNetCount(new BigDecimal(6.00));
        costCardItem2.setOtherCount(new BigDecimal(7.00));
        List<CostCardItem> costCardItemList = new ArrayList<CostCardItem>();
        costCardItemList.add(costCardItem1);
        costCardItemList.add(costCardItem2);
        costCardItemService.newCostCardItems(costCardItemList);
    }

    @Test
    public void delById() throws Exception{
        costCardItemService.delById(8);
    }

    @Test
    public void delByCostCardId() throws Exception{
        costCardItemService.delByCostCardId(6);
    }

    @Test
    public void updateCostCardItem() throws Exception{
        CostCardItem costCardItem = new CostCardItem();
        costCardItem.setId(7);
        costCardItem.setCostCardId(1);
        costCardItem.setIngredientId(1);
        costCardItem.setIsAutoOut(1);
        costCardItem.setItemType(2);
        costCardItem.setNetCount(new BigDecimal(2.00));
        costCardItem.setOtherCount(new BigDecimal(3.00));
        costCardItemService.updateCostCardItem(costCardItem);
    }

    @Test
    public void listByCostCardId() throws Exception{
        List<CostCardItemDto> lists = costCardItemService.listByCostCardId(5);
    }

    @Test
    public void updateByCostCardId() throws Exception{
            CostCardItem costCardItem = new CostCardItem();
            costCardItem.setCostCardId(6);
            costCardItem.setIngredientId(1);
            costCardItem.setNetCount(new BigDecimal(6));
            costCardItem.setOtherCount(new BigDecimal(6));
            costCardItem.setItemType(2);
            costCardItem.setIsAutoOut(1);
        costCardItemMapper.updateByCardId(costCardItem);
    }
}
