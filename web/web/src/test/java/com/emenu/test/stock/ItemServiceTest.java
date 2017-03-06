package com.emenu.test.stock;

import com.emenu.common.entity.stock.StockItem;
import com.emenu.service.stock.ItemService;
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
public class ItemServiceTest extends AbstractTestCase{

    @Autowired
    private ItemService itemService;

    @Test
    public void newItem() throws SSException{
        StockItem stockItem = new StockItem();

    }
}
