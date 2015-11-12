package com.emenu.test.storage;

import com.emenu.common.dto.storage.StorageReportDto;
import com.emenu.common.entity.storage.StorageReport;
import com.emenu.common.entity.storage.StorageReportItem;
import com.emenu.service.storage.StorageReportItemService;
import com.emenu.service.storage.StorageReportService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.math.BigDecimal;
import java.util.ArrayList;
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


        storageReportItem.setCount(count);
        storageReportItem.setItemId(1);
        storageReportItem.setPrice(price);
        storageReportItem.setQuantity(quantity);
        storageReportItem.setReportId(1);

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



        StorageReportDtoList = storageReportService.listStorageReportDtoByPage(2,10);

    }
}
