package com.emenu.test.stock;

import com.emenu.common.entity.stock.StockKitchenItem;
import com.emenu.service.stock.StockKitchenItemService;
import com.emenu.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
            stockKitchenItem.setId(3);
            stockKitchenItem.setItemId(6);
            stockKitchenItem.setRemark("你好");
            stockKitchenItemService.newStockKitchenItem(stockKitchenItem);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
