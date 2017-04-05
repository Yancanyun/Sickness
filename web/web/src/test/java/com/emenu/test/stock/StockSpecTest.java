package com.emenu.test.stock;

import com.emenu.common.entity.stock.Specifications;
import com.emenu.service.stock.SpecificationsService;
import com.emenu.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;

/**
 * Created by apple on 17/3/6.
 */
public class StockSpecTest extends AbstractTestCase {
    @Autowired
    SpecificationsService specificationsService;

    @Test
    public void add() throws Exception {
        Specifications specifications = new Specifications();
        specifications.setOrderUnitId(34);
        specifications.setStorageUnitId(12);
        specifications.setCostCardUnitId(56);
        specifications.setOrderToStorage(BigDecimal.valueOf(23.31));
        specifications.setStorageToCost(BigDecimal.valueOf(34.32));
        specificationsService.add(specifications);
    }

    @Test
    public void deleteById() throws Exception{
        specificationsService.deleteById(13);
    }

    @Test
    public void update()throws Exception{
        Specifications specifications = new Specifications();
        specifications.setId(3);
        specifications.setOrderUnitId(3);
        specifications.setStorageUnitId(3);
        specifications.setCostCardUnitId(3);
        specifications.setOrderToStorage(BigDecimal.valueOf(1003.33));
        specifications.setStorageToCost(BigDecimal.valueOf(100.22));
        specificationsService.update(specifications);
    }

    @Test
    public void queryById()throws Exception{
        specificationsService.queryById(2);
    }

    @Test
    public void listAll()throws Exception{
        List<Specifications> specificationsList = specificationsService.listAll();
        for(Specifications specifications:specificationsList){
            System.out.println(specifications.getOrderUnitId());
        }
    }

    @Test
    public void listByPage()throws Exception{
        List<Specifications> specificationsList = specificationsService.listByPage(0,10);
        for(Specifications specifications:specificationsList){
            System.out.print(specifications.getOrderUnitName());
            System.out.print(specifications.getStorageUnitName());
            System.out.println(specifications.getCostCardUnitName());
        }
    }

    @Test
    public void count()throws Exception{
        System.out.println(specificationsService.count());
    }
}
