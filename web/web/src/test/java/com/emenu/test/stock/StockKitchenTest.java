package com.emenu.test.stock;

import com.emenu.common.entity.stock.StockKitchen;
import com.emenu.service.stock.StockKitchenService;
import com.emenu.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * StockKitchenTest
 *
 * @author nigbo
 * @date 2017/3/6 16:12
 */
public class StockKitchenTest extends AbstractTestCase {
    @Autowired
    private StockKitchenService stockKitchenService;

    @Test
    public void toStockKitchenList()throws Exception{
        try{
            List<StockKitchen> stockKitchenList = new ArrayList<StockKitchen>();
            stockKitchenList = stockKitchenService.listStockKitchen();
            for(StockKitchen stockKitchen:stockKitchenList){
                System.out.println("kitchen name is "+ stockKitchen.getName()+",instruction is "+stockKitchen.getIntroduction()+"\n");
            }
        }catch (Exception e){throw new Exception(e.getMessage());}
    }

    @Test
    public void addStockKitchen()throws Exception{
        try{
            StockKitchen stockKitchen = new StockKitchen();
            stockKitchen.setName("相楠的厨房");
            stockKitchen.setIntroduction("吉林口味");
            //不能为空
//            stockKitchen.setPrincipal("坤神");
            stockKitchenService.addStockKitchen(stockKitchen);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @Test
    public void updateStockKitchen()throws Exception{
        try{
            StockKitchen stockKitchen = new StockKitchen();
            stockKitchen.setName("鹏神的厨房");
            stockKitchen.setIntroduction("湖北口味+1");
            stockKitchen.setPrincipal("鹏神");
            stockKitchenService.updateStockKitchen(2,stockKitchen);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @Test
    public void updateStockKitchenStatus()throws Exception{
        try{
            stockKitchenService.updateStockKitchenStatus(2,1);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @Test
    public void queryStockKitchenDetails()throws Exception{
        StockKitchen stockKitchen= new StockKitchen();
        try{
            stockKitchen = stockKitchenService.queryStockKitchenDetails(2);
            System.out.println("name is "+ stockKitchen.getName()+", principal is "
                    +stockKitchen.getPrincipal());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
    @Test
    public void checkNameIsExist()throws Exception{
        Boolean n ;
        try{
           n =stockKitchenService.checkNameIsExist("山哥的厨房");
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        System.out.println("山哥的厨房存在是"+ n);
    }
}
