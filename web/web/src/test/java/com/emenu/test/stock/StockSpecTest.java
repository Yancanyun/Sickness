package com.emenu.test.stock;

import com.emenu.common.entity.stock.Specifications;
import com.emenu.service.stock.SpecificationsService;
import com.emenu.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;

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
        specifications.setCostCardId(56);
        specifications.setOrderToStorage(BigDecimal.valueOf(23.31));
        specifications.setStorageToCost(BigDecimal.valueOf(34.32));
        specificationsService.add(specifications);
    }

    @Test
    public void deleteById() throws Exception{
        specificationsService.deleteById(1);
    }

    @Test
    public void update()throws Exception{
        Specifications specifications = new Specifications();
        specifications.setOrderUnitId(233);
        specifications.setStorageUnitId(22);
        specifications.setCostCardId(33);
        specifications.setOrderToStorage(BigDecimal.valueOf(23.33));
        specifications.setStorageToCost(BigDecimal.valueOf(33.22));
        specificationsService.update(4,specifications);
    }

    @Test
    public void queryById()throws Exception{
        specificationsService.queryById(2);
    }

    @Test
    public void listAll()throws Exception{
        specificationsService.listAll();
    }
}
