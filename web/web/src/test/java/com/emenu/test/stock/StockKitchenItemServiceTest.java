package com.emenu.test.stock;


import com.emenu.common.entity.stock.StockKitchenItem;
import com.emenu.service.stock.StockKitchenItemService;
import com.emenu.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
        StockKitchenItem stockKitchenItem = new StockKitchenItem();
        stockKitchenItem = stockKitchenItemService.queryByItemId(1,1);
        System.out.println(stockKitchenItem.getRemark());
    }
}
