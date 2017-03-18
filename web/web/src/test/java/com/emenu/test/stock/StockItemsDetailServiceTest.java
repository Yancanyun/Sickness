package com.emenu.test.stock;


import com.emenu.common.entity.stock.StockItemDetail;
import com.emenu.service.stock.StockItemDetailService;
import com.emenu.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

/**
 * StockItemsDetailServiceTest
 *
 * @author renhongshuai
 * @Time 2017/3/10 11:21.
 */
public class StockItemsDetailServiceTest extends AbstractTestCase {

    @Autowired
    private StockItemDetailService stockItemDetailService;

    @Test
    public void newStockItemDetail() throws Exception {
        StockItemDetail stockItemDetail = new StockItemDetail();
        stockItemDetail.setUnitId(22);
        stockItemDetail.setSpecificationId(2);
        stockItemDetail.setKitchenId(1);
        stockItemDetail.setItemId(1);
        stockItemDetail.setQuantity(BigDecimal.TEN);
        stockItemDetailService.newStockItemDetail(stockItemDetail);
    }
    @Test
    public void delStockItemDetail() throws Exception {
        stockItemDetailService.deleteStockItemDetailById(2);
    }
    @Test
    public void queryDetailById() throws Exception{
        List<StockItemDetail> itemDetailList = stockItemDetailService.queryDetailById(4,1);
        for(StockItemDetail stockItemDetail:itemDetailList){
            System.out.println(stockItemDetail.getQuantity());
        }
    }
    @Test
    public void updateStockItemDetail() throws Exception{
        StockItemDetail stockItemDetail = new StockItemDetail();
        stockItemDetail.setId(3);
        stockItemDetail.setQuantity(BigDecimal.valueOf(60.00));
        stockItemDetailService.updateStockItemDetail(stockItemDetail);
    }
}
