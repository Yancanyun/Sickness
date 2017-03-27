package com.emenu.test.stock;

import com.emenu.common.dto.stock.DocumentsDto;
import com.emenu.common.dto.stock.DocumentsSearchDto;
import com.emenu.common.entity.stock.StockDocuments;
import com.emenu.common.entity.stock.StockDocumentsItem;
import com.emenu.service.stock.StockDocumentsService;
import com.emenu.test.AbstractTestCase;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * StockDocumentsServiceTest
 *
 * @author: wychen
 * @time: 2017/3/8 20:35
 */
public class StockDocumentsServiceTest extends AbstractTestCase {


    @Autowired
    private StockDocumentsService stockDocumentsService;

    @Test
    public void newDocumentsDto()throws Exception{

        StockDocuments stockDocuments = new StockDocuments();
        stockDocuments.setComment("不嘛不辣家糖醋");
        stockDocuments.setAuditPartyId(1);
        stockDocuments.setKitchenId(1);
        stockDocuments.setHandlerPartyId(1);
        stockDocuments.setSupplierId(1);
        stockDocuments.setType(1);
        stockDocuments.setCreatedPartyId(1);

        List<StockDocumentsItem> itemList = new ArrayList<StockDocumentsItem>();
        StockDocumentsItem stockDocumentsItem = new StockDocumentsItem();
        stockDocumentsItem.setDocumentsId(1);
        stockDocumentsItem.setItemId(4);
        stockDocumentsItem.setSpecificationId(2);
        stockDocumentsItem.setQuantity(BigDecimal.TEN);
        stockDocumentsItem.setUnitId(22);
        stockDocumentsItem.setPrice(BigDecimal.TEN);
        stockDocumentsItem.setUnitPrice(BigDecimal.ONE);
        itemList.add(stockDocumentsItem);

        DocumentsDto documentsDto = new DocumentsDto();
        documentsDto.setStockDocuments(stockDocuments);
        documentsDto.setStockDocumentsItemList(itemList);

        try{
            stockDocumentsService.newDocumentsDto(documentsDto);

        }catch(Exception e){
           throw new Exception(e.getMessage());
        }
    }

    @Test
    public void updateIsAudited()throws Exception{
        try{
            stockDocumentsService.updateIsAudited(1,1);
            stockDocumentsService.updateIsSettled(1,1);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Test
    public void updateDocuments()throws Exception{
        try{
            StockDocuments stockDocuments = stockDocumentsService.queryById(1);
            stockDocuments.setMoney(BigDecimal.TEN);
            stockDocumentsService.updateDocuments(stockDocuments);
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }
    }


    @Test
    public void listAll() throws Exception {
        try {
            List<StockDocuments> list = stockDocumentsService.listAll();
            System.out.println(list);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Test
    public void listDocumentsBySearchDto() throws Exception {
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
        try {
            List<StockDocuments> list = stockDocumentsService.listStockDocumentsBySearchDto(documentsSearchDto);
            System.out.println(list.size());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Test
    public void queryById() throws Exception {
        StockDocuments stockDocuments = new StockDocuments();
        try {
            stockDocuments = stockDocumentsService.queryById(1);
            System.out.println(stockDocuments.getMoney());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Test
    public void queryDocumentsDtoById() throws Exception{
        DocumentsDto documentsDto = new DocumentsDto();
        try{
            documentsDto = stockDocumentsService.queryDocumentsDtoById(1);
            System.out.println(documentsDto);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Test
    public void delStockDocumentsById() throws Exception {
        Boolean result;
        try {
            result = stockDocumentsService.delDocumentsDtoById(1);
            System.out.println(result);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Test
    public void listStockDocumentsDtoBySearchDto() throws Exception {
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
        try {
            List<DocumentsDto> list = stockDocumentsService.listDocumentsDtoBySearchDto(documentsSearchDto);
            System.out.println(list.size());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


    @Test
    public void listStockDocumentsByTime() throws Exception {
        Date startTime = new Date();
        Date endTime = new Date();

        List<StockDocuments> stockDocuments = new ArrayList<StockDocuments>();

        try {
            stockDocuments = stockDocumentsService.listDocumentsByTime(startTime, endTime);
            System.out.println(stockDocuments.size());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Test
    public void listDocumentsDtoByTime() throws Exception {
        Date startTime = new Date();
        Date endTime = new Date();
        List<DocumentsDto> documentsDtos = new ArrayList<DocumentsDto>();
        try {
            documentsDtos = stockDocumentsService.listDocumentsDtoByTime(startTime, endTime);
            System.out.println(documentsDtos.size());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Test
    public void listStockDocumentsByTimeAndIsAudited() throws Exception {
        Date startTime = new Date();
        Date endTime = new Date();
        int isAudited = 1;
        List<StockDocuments> stockDocuments = new ArrayList<StockDocuments>();

        try {
            stockDocuments = stockDocumentsService.listDocumentsByTimeAndIsAudited(startTime, endTime, isAudited);
            System.out.println(stockDocuments.size());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Test
    public void listStockDocumentsDtoByTimeAndIsAudited() throws Exception {
        Date startTime = new Date();
        Date endTime = new Date();
        int isAudited = 1;
        List<DocumentsDto> documentsDtos = new ArrayList<DocumentsDto>();
        try {
            documentsDtos = stockDocumentsService.listDocumentsDtoByTimeAndIsAudited(startTime, endTime, isAudited);
            System.out.println(documentsDtos.size());
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Test
    public void listDocumentsByTimeAndIsAudited1() throws Exception{
        Date startTime = new Date();
        Date endTime = new Date();
        int isAudited = 1;
        List<StockDocuments> stockDocuments = new ArrayList<StockDocuments>();
        try{
            stockDocuments = stockDocumentsService.listDocumentsByTimeAndIsAudited1(startTime,endTime,isAudited);
            System.out.println(stockDocuments.size());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Test
    public void listDocumentsDtoByTimeAndIsAudited1() throws Exception{
        Date startTime = new Date();
        Date endTime = new Date();
        int isAudited = 1;
        List<DocumentsDto> documentsDtos = Collections.emptyList();
        try{
            documentsDtos = stockDocumentsService.listDocumentsDtoByTimeAndIsAudited1(startTime,endTime,isAudited);
            System.out.println(documentsDtos.size());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @Test
    public void countByDocumentsSearchDto() throws Exception{
        DocumentsSearchDto documentsSearchDto = new DocumentsSearchDto();
        documentsSearchDto.setKitchenId(1);
        documentsSearchDto.setCreatedPartyId(1);
        documentsSearchDto.setHandlerPartyId(1);
        try {
            int count = stockDocumentsService.countByDocumentsSearchDto(documentsSearchDto);
            System.out.println(count);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }

    @Test
    public void listUnsettleAndAuditedDocumentsByEndTime() throws Exception{
        List<StockDocuments> stockDocuments = new ArrayList<StockDocuments>();
        try{
            stockDocuments = stockDocumentsService.listUnsettleAndAuditedDocumentsByEndTime(new Date());
            System.out.println(stockDocuments.size());
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }
}
