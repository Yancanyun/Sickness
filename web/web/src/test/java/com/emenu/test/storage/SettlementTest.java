package com.emenu.test.storage;

import com.emenu.common.dto.storage.StorageItemDto;
import com.emenu.common.dto.storage.StorageSupplierDto;
import com.emenu.common.entity.storage.StorageItem;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.service.other.SerialNumService;
import com.emenu.service.storage.StorageItemService;
import com.emenu.service.storage.StorageSettlementService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * SettlementTest
 *
 * @author dujuan
 * @date 2015/11/15
 */
public class SettlementTest extends AbstractTestCase{

    @Autowired
    private SerialNumService serialNumService;

    @Autowired
    private StorageSettlementService storageSettlementService;

    @Autowired
    private StorageItemService storageItemService;


    @Test
    public void generateSerialNum() throws SSException {
        System.out.print(serialNumService.generateSerialNum(SerialNumTemplateEnums.SettlementSerialNum));
    }

    @Test
    public void newSettlement() throws SSException {
        storageSettlementService.newSettlement();
    }

    /**
     * 获取库存物品列表
     * @throws SSException
     */
    @Test
    public void listAllStorageItem() throws SSException {
        List<StorageItem> storageItemList = storageItemService.listAll();
        for(StorageItem storageItem : storageItemList){
            System.out.println(storageItem.getName());
        }
    }

    @Test
    public void listSettlementSupplier() throws SSException, ParseException {

        String startDateStr = "2015/11/10";
        String endDateStr = "2015/11/19";
        Date date = new Date();
        //注意format的格式要与日期String的格式相匹配
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate = sdf.parse(startDateStr);
        Date endDate = sdf.parse(endDateStr);
        List<StorageSupplierDto> storageSupplierDtoList = storageSettlementService.listSettlementSupplier(4,startDate,endDate);
        for(StorageSupplierDto storageSupplierDto : storageSupplierDtoList){
            System.out.println(storageSupplierDto.getSupplierName() + "  " + storageSupplierDto.getTotalMoney());
            for(StorageItemDto storageItemDto : storageSupplierDto.getStorageItemDtoList()){
                System.out.println(storageItemDto.getItemName());
            }
        }
    }
}
