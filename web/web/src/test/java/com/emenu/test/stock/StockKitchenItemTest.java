package com.emenu.test.stock;

import com.emenu.common.entity.stock.StockKitchenItem;
import com.emenu.service.stock.StockKitchenItemService;
import com.emenu.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yaojf
 * @time 2017/3/31 9:38
 */
public class StockKitchenItemTest extends AbstractTestCase {
    @Autowired
    private StockKitchenItemService stockKitchenItemService;

    //测试添加厨房物品
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

    @Test
    public void testDelStockKitchenItem()throws Exception{
        try{
            stockKitchenItemService.delStockKitchenItem(1);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Test
    public void testUpdateStockKitchenItem()throws Exception{
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
            stockKitchenItemService.updateStockKitchenItem(stockKitchenItem,2);

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Test
    public void testEditRemark()throws Exception{
        try{
            stockKitchenItemService.editRemark(2,"你不好吗");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Test
    public void testQueryAll()throws Exception{
        List<StockKitchenItem> stockKitchenItems = new ArrayList<StockKitchenItem>();
        try{
            for(StockKitchenItem stockKitchenItem:stockKitchenItems){
                System.out.println("厨房物品id是 "+ stockKitchenItem.getId()+"\n");
            }
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Test
    public void testQueryById()throws Exception{
        StockKitchenItem stockKitchenItem = new StockKitchenItem();
        try{
            stockKitchenItem = stockKitchenItemService.queryById(2);
            System.out.println("这个厨房物品的备注是："+stockKitchenItem.getRemark());
            System.out.println("这个厨房物品的备注是："+stockKitchenItem.getRemark());
            System.out.println("这个厨房物品的备注是："+stockKitchenItem.getRemark());
            System.out.println("这个厨房物品的备注是："+stockKitchenItem.getRemark());
            System.out.println("这个厨房物品的备注是："+stockKitchenItem.getRemark());
            System.out.println("这个厨房物品的备注是："+stockKitchenItem.getRemark());
            System.out.println("这个厨房物品的备注是："+stockKitchenItem.getRemark());

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

}
