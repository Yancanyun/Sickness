package com.emenu.test.stock;

import com.emenu.common.entity.stock.StockWarn;
import com.emenu.service.stock.StockWarnService;
import com.emenu.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

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
        int itemId = 4;
        stockWarnService.updateStateToResolvedByItemId(itemId);
    }

    @Test
    public void updateStateToIgnoredByItemId() throws Exception{
        int itemId = 5;
        stockWarnService.updateStateToIgnoredByItemId(itemId);
    }

    @Test
    public void queryAllUntreatedWarn() throws Exception{
        int kitchenId = 1;
        List<StockWarn> list = Collections.emptyList();
        list =  stockWarnService.queryAllUntreatedWarn(kitchenId);
        System.out.println(list);

    }

    @Test
    public void countAllWarn() throws Exception{
        int count = 0;
        count = stockWarnService.countAllWarn();
        System.out.println(count);
    }

    @Test
    public void listByPage() throws Exception{
        int offset = 0;
        int pageSize = 10;
        List<StockWarn> list = Collections.emptyList();
        list = stockWarnService.listByPage(offset,pageSize);
        System.out.println(list);
    }

}

