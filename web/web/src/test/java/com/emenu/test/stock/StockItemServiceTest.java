package com.emenu.test.stock;

import com.emenu.common.dto.stock.StockItemSearchDto;
import com.emenu.common.entity.stock.StockItem;
import com.emenu.service.stock.StockItemService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

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

    @Test
    public void listToString() throws SSException{
       List<Integer> list = new ArrayList<Integer>();
        list.add(0,1);
        list.add(1,2);
        String xixi = stockItemService.listToString(list);
        System.out.println(xixi);
    }

    @Test
    public void stringToList() throws SSException{
        List<Integer> list = new ArrayList<Integer>();
        String string = new String();
        string = "5,6,78,110,12";
        list = stockItemService.stringTolist(string);
        System.out.println("xixi");
    }

    @Test
    public void queryById() throws SSException{
        StockItem stockItem = stockItemService.queryById(5);
        System.out.println("111");
    }

    @Test
    public void updateStockItemStatusById() throws SSException{
        int itemId = 1;
        int status = 0;
        stockItemService.updateStockItemStatusById(itemId,status);
        System.out.println("xixi");

    }

    @Test
    public void testListItem()throws SSException{
        StockItemSearchDto searchDto = new StockItemSearchDto();
        List<StockItem> itemList = stockItemService.listItem(searchDto);
    }

    @Test
    public void listAll()throws SSException{
        List<StockItem> stockItemList = stockItemService.listAll();
        for(StockItem stockItem:stockItemList){
            System.out.println(stockItem.getName());
        }
    }

    @Test
    public void CountByTagId()throws SSException{
        int itemCount = stockItemService.countByTagId(2);
        System.out.println(itemCount);
    }
}
