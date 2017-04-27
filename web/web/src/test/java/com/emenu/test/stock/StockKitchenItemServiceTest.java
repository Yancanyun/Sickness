package com.emenu.test.stock;


import com.emenu.common.entity.stock.StockKitchen;
import com.emenu.common.entity.stock.StockKitchenItem;
import com.emenu.service.stock.StockKitchenItemService;
import com.emenu.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * StockKitchenItemServiceTest
 *
 * @author renhongshuai
 * @Time 2017/3/16 8:50.
 */
public class StockKitchenItemServiceTest extends AbstractTestCase {

    @Autowired
    private StockKitchenItemService stockKitchenItemService;

    @Test
    public void queryKitchenItemById() throws Exception{
        List<StockKitchenItem> stockKitchenItems = stockKitchenItemService.queryByItemId(1,1);
        for(StockKitchenItem stockKitchenItem : stockKitchenItems){
            System.out.println(stockKitchenItem.getSpecifications());
        }
    }
}
