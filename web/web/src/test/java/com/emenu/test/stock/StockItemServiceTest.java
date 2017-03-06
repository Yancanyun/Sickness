package com.emenu.test.stock;

import com.emenu.common.entity.stock.StockItem;
import com.emenu.service.stock.StockItemService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * ItemServiceTest
 *
 * @author pengpeng
 * @time 2017/3/6 8:44
 */
public class StockItemServiceTest extends AbstractTestCase{

    @Autowired
    private StockItemService stockItemService;

    @Test
    public void newItem() throws SSException{
        StockItem stockItem = new StockItem();
        stockItem.setName("土豆");
        stockItem.setRemark("xixi");
        stockItemService.newItem(stockItem);
    }

    @Test
    public void checkIsExist() throws SSException{
        String name = "番茄";
        boolean a = stockItemService.checkIsExist(name);
        System.out.println("123");
    }
}