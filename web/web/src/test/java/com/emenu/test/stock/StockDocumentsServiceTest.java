package com.emenu.test.stock;

import com.emenu.common.dto.stock.DocumentsSearchDto;
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

    @Test
    public void listDocumentsBySearchDto() throws Exception{
        DocumentsSearchDto documentsSearchDto = new DocumentsSearchDto();
//        documentsSearchDto.setStartTime("");
//        documentsSearchDto.setEndTime();
        documentsSearchDto.setKitchenId(1);
        documentsSearchDto.setCreatedPartyId(1);
        documentsSearchDto.setHandlerPartyId(1);
        documentsSearchDto.setAuditPartyId(1);
        documentsSearchDto.setIsAudited(1);
        documentsSearchDto.setIsSettled(1);
        documentsSearchDto.setType(1);
        try{
            List<StockDocuments> list = stockDocumentsService.listStockDocumentsBySearchDto(documentsSearchDto);
            System.out.println(list.size());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Test
    public void queryById() throws Exception{
        StockDocuments stockDocuments = new StockDocuments();
        try{
            stockDocuments = stockDocumentsService.queryById(1);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Test
    public void delStockDocumentsById() throws Exception{
        try{
            stockDocumentsService.delDocumentsDtoById(1);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
