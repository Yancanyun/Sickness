package com.emenu.test.stock;

import com.emenu.common.entity.stock.StockDocuments;
import com.emenu.service.stock.StockDocumentsService;
import com.emenu.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * StockDocumentsServiceTest
 *
 * @author: wychen
 * @time: 2017/3/8 20:35
 */
public class StockDocumentsServiceTest extends AbstractTestCase{

    @Autowired
    private StockDocumentsService stockDocumentsService;

    @Test
    public void listAll() throws Exception {
       try{
           List<StockDocuments> list = stockDocumentsService.listAll();
           System.out.println(list);
       }catch (Exception e){
           throw new Exception(e.getMessage());
       }
    }
}
