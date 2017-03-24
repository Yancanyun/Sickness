package com.emenu.test.stock;

import com.emenu.common.entity.stock.StockWarn;
import com.emenu.service.stock.StockWarnService;
import com.emenu.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * StockWarnTest
 *
 * @author yuzhengfei
 * @date 2017/3/23 9:27
 */
public class StockWarnTest extends AbstractTestCase{

    @Autowired
    private StockWarnService stockWarnService;

    @Test
    public void newWarn() throws Exception {
        StockWarn stockWarn = new StockWarn();
        stockWarn.setItemId(2);
        stockWarn.setKitchenId(2);
        stockWarn.setState(0);
        stockWarn.setContent("原配料不足");
        stockWarnService.newWarn(stockWarn);
    }

    @Test
    public void updateStateToResolvedByItemId() throws Exception{
        int itemId = 1;
        stockWarnService.updateStateToResolvedByItemId(itemId);
    }

    @Test
    public void updateStateToIgnoredByItemId() throws Exception{
        int itemId = 1;
        stockWarnService.updateStateToIgnoredByItemId(itemId);
    }



}

