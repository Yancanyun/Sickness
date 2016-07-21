package com.emenu.test.storage;

import com.emenu.common.dto.storage.StorageReportDto;
import com.emenu.common.entity.storage.StorageReport;
import com.emenu.common.entity.storage.StorageReportIngredient;
import com.emenu.common.entity.storage.StorageReportItem;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.enums.storage.StorageReportStatusEnum;
import com.emenu.common.enums.storage.StorageReportTypeEnum;
import com.emenu.common.utils.DateUtils;
import com.emenu.mapper.storage.StorageReportMapper;
import com.emenu.service.other.SerialNumService;
import com.emenu.service.storage.StorageReportIngredientService;
import com.emenu.service.storage.StorageReportItemService;
import com.emenu.service.storage.StorageReportService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.util.Assert;
import org.apache.xpath.SourceTree;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 单据相关方法测试文件
 * @author xiaozl
 * @date 2015/11/12
 * @time 15:11
 */
public class ReportTest extends AbstractTestCase {

    @Autowired
    StorageReportService storageReportService;

    @Autowired
    StorageReportItemService storageReportItemService;

    @Autowired
    SerialNumService serialNumService;

    /**
     * 添加单据测试
     *
     * @throws SSException
     */
    @Test
    public void newReport() throws SSException {

        StorageReport storageReport = new StorageReport();

        storageReport.setIsSettlemented(0);
        storageReport.setComment("来一个");
        storageReport.setCreatedPartyId(6);
        storageReport.setDepotId(4);
        storageReport.setHandlerPartyId(5);
        storageReport.setSerialNumber("kdl-201510120001");
        storageReport.setIsSettlemented(1);

        BigDecimal money = new BigDecimal("35.55");

        storageReport.setMoney(money);
        storageReport.setType(1);

        // storageReportService.newReport(storageReport);

    }

    @Test
    public void newReportItem() throws SSException {
        StorageReportItem storageReportItem = new StorageReportItem();

        storageReportItem.setComment("你好");

        BigDecimal count = new BigDecimal("0.00");
        BigDecimal price = new BigDecimal("0.00");
        BigDecimal quantity = new BigDecimal("0.00");


        storageReportItem.setCount(count);
        storageReportItem.setItemId(1);
        storageReportItem.setPrice(price);
        storageReportItem.setQuantity(quantity);
        storageReportItem.setReportId(1);

        storageReportItemService.newReportItem(storageReportItem);
    }

    @Test
    public void newReportAndReportItem() throws SSException {

        List<StorageReportItem> storageReportItemList = new ArrayList();

        StorageReportItem storageReportItem = new StorageReportItem();

        storageReportItem.setComment("不好");

        BigDecimal count = new BigDecimal("0.00");
        BigDecimal price = new BigDecimal("0.00");
        BigDecimal quantity = new BigDecimal("0.00");

        String setSerialNumber = serialNumService.generateSerialNum(SerialNumTemplateEnums.StockInSerialNum);

        storageReportItem.setCount(count);
        storageReportItem.setItemId(1);
        storageReportItem.setPrice(price);
        storageReportItem.setQuantity(quantity);
        storageReportItem.setReportId(1);

        StorageReport storageReport = new StorageReport();

        storageReport.setIsSettlemented(0);
        storageReport.setComment("来两个");
        storageReport.setCreatedPartyId(2);
        storageReport.setDepotId(2);
        storageReport.setHandlerPartyId(2);
        storageReport.setSerialNumber(setSerialNumber);
        storageReport.setIsSettlemented(1);

        BigDecimal money = new BigDecimal("35.55");

        storageReport.setMoney(money);
        storageReport.setType(1);

        storageReportItemList.add(storageReportItem);

        StorageReportDto storageReportDto = new StorageReportDto();

        storageReportDto.setStorageReport(storageReport);
        storageReportDto.setStorageReportItemList(storageReportItemList);

        storageReportService.newReportDto(storageReportDto);

    }

    @Test
    public void listReport() throws SSException {
        List<StorageReport> storageReportList = storageReportService.listAll();
        for (StorageReport storageReport : storageReportList) {
            System.out.println(storageReport.getComment());

        }
    }


    @Test
    public void listReportDto() throws SSException {
        List<StorageReportDto> StorageReportDtoList = new ArrayList();
        StorageReportDtoList = storageReportService.listReportDto();

        for (StorageReportDto storageReportDto : StorageReportDtoList) {
            List<StorageReportItem> storageReportItemList = storageReportDto.getStorageReportItemList();
            System.out.println(storageReportDto.getStorageReport().getComment());
            for (StorageReportItem storageReportItem : storageReportItemList) {
                System.out.println(storageReportItem.getComment());
            }

        }

    }

    @Test
    public void listReportDtoUnsettled() throws SSException {
        List<StorageReportDto> StorageReportDtoList = new ArrayList();

        Date endTime = new Date();

        endTime = DateUtils.getTodayEndTime();

        // StorageReportDtoList = storageReportService.listReportDtoUnsettledByEndTime(endTime);

        for (StorageReportDto storageReportDto : StorageReportDtoList) {
            List<StorageReportItem> storageReportItemList = storageReportDto.getStorageReportItemList();
            System.out.println(storageReportDto.getStorageReport().getComment());
            for (StorageReportItem storageReportItem : storageReportItemList) {
                System.out.println(storageReportItem.getComment());
            }

        }

    }

    @Test
    public void listReportDtoByPage() throws SSException {
        List<StorageReportDto> StorageReportDtoList = new ArrayList();
        StorageReportDtoList = storageReportService.listReportDtoByPage(1, 10);
        for (StorageReportDto storageReportDto : StorageReportDtoList) {
            List<StorageReportItem> storageReportItemList = storageReportDto.getStorageReportItemList();
            System.out.println(storageReportDto.getStorageReport().getComment());
            for (StorageReportItem storageReportItem : storageReportItemList) {
                System.out.println(storageReportItem.getComment());
            }

        }

    }

    @Test
    public void listReportDtoByCondition() throws SSException {
        List<StorageReportDto> StorageReportDtoList = new ArrayList();

        Date startTime = new Date();
        Date endTime = new Date();

        startTime = DateUtils.getTodayStartTime();
        endTime = DateUtils.getTodayEndTime();

        StorageReport storageReport = new StorageReport();

        StorageReportDtoList = storageReportService.listReportDtoByCondition(storageReport, 0, 10, startTime, endTime);
        for (StorageReportDto storageReportDto : StorageReportDtoList) {
            List<StorageReportItem> storageReportItemList = storageReportDto.getStorageReportItemList();
            System.out.println(storageReportDto.getStorageReport().getComment());
            for (StorageReportItem storageReportItem : storageReportItemList) {
                System.out.println(storageReportItem.getComment());
            }

        }
        System.out.println("he");
    }

    /**
     * 根据经手人id、操作人id、单据id分页单据详情获取单据和单据详情
     *
     * @throws SSException
     */
    @Test
    public void listStorageReportByCondition1() throws SSException {
        List<StorageReportDto> StorageReportDtoList = new ArrayList();

        Date startTime = new Date();
        Date endTime = new Date();

        startTime = DateUtils.getTodayStartTime();
        endTime = DateUtils.getTodayEndTime();

        StorageReport storageReport = new StorageReport();
        storageReport.setId(5);
        storageReport.setSerialNumber("RKD-201511260001");
        storageReport.setCreatedPartyId(2);
        storageReport.setDepotId(2);
        storageReport.setHandlerPartyId(2);
        StorageReportDtoList = storageReportService.listReportDtoByCondition(storageReport, 0, 10, startTime, endTime);

        for (StorageReportDto storageReportDto : StorageReportDtoList) {
            System.out.println(storageReportDto.getStorageReport().getComment());
        }
        System.out.println("he");
    }

    @Test
    public void updateById() throws SSException {
        StorageReport storageReport = new StorageReport();
        storageReport.setId(5);
        storageReport.setSerialNumber("RKD-201511260001");
        storageReport.setCreatedPartyId(2);
        storageReport.setDepotId(3);
        storageReport.setHandlerPartyId(2);
        storageReportService.updateById(storageReport);
    }


    @Test
    public void updateStorageReport() throws SSException {
        StorageReport storageReport = storageReportService.queryById(2);
        storageReport.setComment("hello");

        storageReportService.updateById(storageReport);

        System.out.println(storageReport.getComment());

    }

    @Test
    public void updateStatusById() throws SSException {
        storageReportService.updateIsSettlementedById(2, StorageReportStatusEnum.Settled);
    }

    @Test
    public void listStorageReportDtoByCondition2() throws SSException {

        List<StorageReportDto> StorageReportDtoList = new ArrayList();

        Date startTime = new Date("2015/01/01");

        Date endTime = new Date("2015/12/31");

        List<Integer> deportIdList = new ArrayList<Integer>();
        //deportIdList.add(2);
        //deportIdList.add(3);

        List<Integer> tagIdList = new ArrayList<Integer>();
        //tagIdList.add(33);
        StorageReportDtoList = storageReportService.listReportDtoByCondition(null, null, deportIdList, tagIdList);
        if (Assert.isNull(StorageReportDtoList = storageReportService.listReportDtoByCondition(null, null, deportIdList, tagIdList))) {
        }

        for (StorageReportDto storageReportDto : StorageReportDtoList) {
            System.out.println(storageReportDto.getStorageReport().getComment());
            for (StorageReportItem storageReportItem : storageReportDto.getStorageReportItemList()) {
                System.out.println(storageReportItem.getItemId());
            }
        }

    }

    @Test
    public void count() throws SSException {
        ArrayList<Integer> idList = new ArrayList<Integer>();
        idList.add(1);
        idList.add(2);
        idList.add(3);

        for (Integer i : idList) {
            if (i == 2) {
                idList.remove(i);
            }
        }
        /*for (Integer i : idList){
            System.out.println(i);
        }*/
        System.out.println(idList.size());
       /* if(idList.size()==0){
            System.out.println("hello");
        }

        System.out.println(storageReportService.count());*/
    }

    @Test
    public void updateReportDto() throws SSException {

        StorageReportDto reportDto = new StorageReportDto();

        StorageReport storageReport = new StorageReport();
        storageReport.setId(4);
        storageReport.setIsSettlemented(0);
        storageReport.setComment("你好杜哥");
        storageReport.setCreatedPartyId(6);
        storageReport.setDepotId(4);
        storageReport.setHandlerPartyId(5);
        storageReport.setSerialNumber("kdl-201510120001");
        storageReport.setIsSettlemented(1);


        BigDecimal money = new BigDecimal("35.55");


        storageReport.setMoney(money);
        storageReport.setType(1);

        reportDto.setStorageReport(storageReport);

        StorageReportItem storageReportItem = new StorageReportItem();
        storageReportItem.setId(2);
        storageReportItem.setComment("唱好");

        BigDecimal count = new BigDecimal("0.00");
        BigDecimal price = new BigDecimal("0.00");
        BigDecimal quantity = new BigDecimal("0.00");


        storageReportItem.setCount(count);
        storageReportItem.setItemId(1);
        storageReportItem.setPrice(price);
        storageReportItem.setQuantity(quantity);
        storageReportItem.setReportId(4);

        List<StorageReportItem> storageReportItemList = new ArrayList<StorageReportItem>();
        storageReportItemList.add(storageReportItem);
        reportDto.setStorageReportItemList(storageReportItemList);

        storageReportService.updateReportDto(reportDto);
        // storageReportService.newReport(storageReport);
    }


}


