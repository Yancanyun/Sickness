package com.emenu.test.stock;

import com.emenu.common.entity.stock.StockDocumentsItem;
import com.emenu.service.stock.StockDocumentsItemService;
import com.emenu.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

/**
 * StockDocumentsItemServiceTest
 *
 * @author renhongshuai
 * @Time 2017/3/23 9:30.
 */
public class StockDocumentsItemServiceTest extends AbstractTestCase {

    @Autowired
    private StockDocumentsItemService stockDocumentsItemService;

    @Test
    public void newDocumentsItem()throws Exception{
        StockDocumentsItem stockDocumentsItem = new StockDocumentsItem();
        stockDocumentsItem.setDocumentsId(1);
        stockDocumentsItem.setItemId(4);
        stockDocumentsItem.setSpecificationId(2);
        stockDocumentsItem.setQuantity(BigDecimal.TEN);
        stockDocumentsItem.setUnitId(22);
        stockDocumentsItem.setPrice(BigDecimal.TEN);
        stockDocumentsItem.setUnitPrice(BigDecimal.ONE);
        try{
            stockDocumentsItemService.newDocumentsItem(stockDocumentsItem);
        }catch (Exception e){
        throw new Exception(e.getMessage());
        }
    }
    @Test
    public void updateDocumentsItem()throws Exception{
        StockDocumentsItem stockDocumentsItem = new StockDocumentsItem();
        stockDocumentsItem.setDocumentsId(1);
        stockDocumentsItem.setId(1);
        stockDocumentsItem.setItemId(4);
        stockDocumentsItem.setSpecificationId(2);
        stockDocumentsItem.setQuantity(BigDecimal.TEN);
        stockDocumentsItem.setUnitId(221);
        stockDocumentsItem.setPrice(BigDecimal.TEN);
        stockDocumentsItem.setUnitPrice(BigDecimal.TEN);
        try{
            stockDocumentsItemService.updateDocumentsItem(stockDocumentsItem);

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @Test
    public void delByDocumentsId()throws Exception{
        try{
            stockDocumentsItemService.delByDocumentsId(2);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @Test
    public void queryByDocumentsId()throws Exception{
        try{
            List<StockDocumentsItem> stockDocumentsItemList = stockDocumentsItemService.queryByDocumentsId(1);
            for(StockDocumentsItem stockDocumentsItem:stockDocumentsItemList){
                System.out.println(stockDocumentsItem.getQuantity());
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @Test
    public void delDocumentsItemById()throws Exception{
        try{
            stockDocumentsItemService.delDocumentsItemById(2);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
