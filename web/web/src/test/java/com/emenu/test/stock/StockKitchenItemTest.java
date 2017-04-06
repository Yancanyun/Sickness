package com.emenu.test.stock;

import com.emenu.common.entity.stock.StockKitchenItem;
import com.emenu.service.stock.StockKitchenItemService;
import com.emenu.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

/**
 * @author yaojf
 * @time 2017/3/31 9:38
 */
public class StockKitchenItemTest extends AbstractTestCase {
    @Autowired
    private StockKitchenItemService stockKitchenItemService;

    @Test
    public void testNewStockKitchenItem()throws Exception{
        try{
            StockKitchenItem stockKitchenItem = new StockKitchenItem();
            stockKitchenItem.setId(1);
            stockKitchenItem.setItemId(1);
            stockKitchenItem.setKitchenId(1);
            stockKitchenItem.setRemark("你好");
            stockKitchenItem.setItemNumber(1);
            stockKitchenItem.setAssistantCode("happy");
            stockKitchenItem.setSpecifications("规格");
            BigDecimal quality = new BigDecimal(3.00);
            stockKitchenItem.setStorageQuantity(quality);
            stockKitchenItem.setCostCardUnitId(1);
            stockKitchenItem.setStatus(0);
            stockKitchenItemService.newStockKitchenItem(stockKitchenItem);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
