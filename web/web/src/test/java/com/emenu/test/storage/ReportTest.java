package com.emenu.test.storage;

import com.emenu.common.dto.storage.StorageReportDto;
import com.emenu.common.entity.storage.StorageReport;
import com.emenu.common.entity.storage.StorageReportItem;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.utils.DateUtils;
import com.emenu.service.other.SerialNumService;
import com.emenu.service.storage.StorageReportItemService;
import com.emenu.service.storage.StorageReportService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

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
public class ReportTest extends AbstractTestCase{

    @Autowired
    StorageReportService storageReportService;

    @Autowired
    StorageReportItemService storageReportItemService;

    @Autowired
    SerialNumService serialNumService;

    /**
     * 添加单据测试
     * @throws SSException
     */
    @Test
    public void newReport() throws SSException{

        StorageReport storageReport = new StorageReport();

        storageReport.setStatus(0);
        storageReport.setComment("来一个");
        storageReport.setCreatedPartyId(6);
        storageReport.setDepotId(4);
        storageReport.setHandlerPartyId(5);
        storageReport.setSerialNumber("kdl-201510120001");
        storageReport.setStatus(1);

        BigDecimal money = new BigDecimal("35.55");

        storageReport.setMoney(money);
        storageReport.setType(1);

        storageReportService.newReport(storageReport);

    }

    @Test
    public void newReportItem() throws SSException{
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

        storageReportItemService.newStorageReportItem(storageReportItem);
    }

    @Test
    public void newReportAndReportItem() throws SSException{

        StorageReportItem storageReportItem = new StorageReportItem();

        storageReportItem.setComment("你好");

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

        storageReport.setStatus(0);
        storageReport.setComment("来一个");
        storageReport.setCreatedPartyId(2);
        storageReport.setDepotId(2);
        storageReport.setHandlerPartyId(2);
        storageReport.setSerialNumber(setSerialNumber);
        storageReport.setStatus(1);

        BigDecimal money = new BigDecimal("35.55");

        storageReport.setMoney(money);
        storageReport.setType(1);

        StorageReportDto storageReportDto = new StorageReportDto();

        storageReportDto.setStorageReport(storageReport);
        storageReportDto.setStorageReportItem(storageReportItem);

        storageReportService.newReportAndReportItem(storageReportDto);

    }

    @Test
    public void listReportDto() throws SSException{
        List<StorageReportDto> StorageReportDtoList = new ArrayList();
        StorageReportDtoList = storageReportService.listStorageReportDto();
    }

    @Test
    public void listReportDtoByPage() throws SSException{
        List<StorageReportDto> StorageReportDtoList = new ArrayList();



        StorageReportDtoList = storageReportService.listStorageReportDtoByPage(2, 10);

    }

    @Test
    public void listReportDtoByCondition() throws SSException{
        List<StorageReportDto> StorageReportDtoList = new ArrayList();

        Date startTime = new Date();
        Date endTime = new Date();

        startTime = DateUtils.getTodayStartTime();
        endTime = DateUtils.getTodayEndTime();

        StorageReportDtoList = storageReportService.listStorageReportDtoByCondition(startTime, endTime, "RKD-201511160002", 0, 0, 0, 0, 10);

        System.out.println("he");

    }

    @Test
    public void listStorageReportByCondition1() throws SSException{
        List<StorageReportDto> StorageReportDtoList = new ArrayList();

        StorageReportDtoList = storageReportService.listStorageReportDtoByCondition1(2,0, 0, 0, 0, 10);

        System.out.println("he");
    }
}
