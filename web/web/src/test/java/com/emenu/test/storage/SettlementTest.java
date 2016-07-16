package com.emenu.test.storage;

import com.emenu.common.dto.storage.StorageCheckDto;
import com.emenu.common.dto.storage.StorageItemDto;
import com.emenu.common.dto.storage.StorageReportDto;
import com.emenu.common.dto.storage.StorageSupplierDto;
import com.emenu.common.entity.storage.*;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.mapper.storage.StorageSettlementMapper;
import com.emenu.service.other.SerialNumService;
import com.emenu.service.storage.StorageItemService;
import com.emenu.service.storage.StorageReportService;
import com.emenu.service.storage.StorageSettlementService;
import com.emenu.test.AbstractTestCase;
import com.pandawork.core.common.exception.SSException;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * SettlementTest
 * 结算相关
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

    @Autowired
    private StorageSettlementMapper storageSettlementMapper;

    @Autowired
    private StorageReportService storageReportService;

    @Test
    public void generateSerialNum() throws SSException {
        System.out.print(serialNumService.generateSerialNum(SerialNumTemplateEnums.SettlementSerialNum));
    }

    /**
     * 结算
     * @throws SSException
     */
    @Test
    public void newSettlement() throws SSException {
        storageSettlementService.newSettlement();
    }

    @Test
    public void queryByDateAndItemId() throws Exception, ParseException {
        String startDateStr = "2015/11/10";
        //注意format的格式要与日期String的格式相匹配
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate = sdf.parse(startDateStr);
        StorageSettlementItem storageSettlementItem = storageSettlementMapper.queryByDateAndItemId(startDate,1);
        if(storageSettlementItem!=null) {
            System.out.println(storageSettlementItem.getId());
        }else {
            System.out.println("该对象为空");
        }

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

    /**
     * 结算中心
     * @throws SSException
     * @throws ParseException
     */
    @Test
    public void listSettlementSupplier() throws SSException, ParseException {
        String startDateStr = "2015/11/10";
        String endDateStr = "2015/11/19";
        //注意format的格式要与日期String的格式相匹配
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate = sdf.parse(startDateStr);
        Date endDate = sdf.parse(endDateStr);

//        List<StorageSupplierDto> storageSupplierDtoList = storageSettlementService.listSettlementSupplier(4,startDate,endDate);
        List<StorageSupplierDto> storageSupplierDtoList = storageSettlementService.listSettlementSupplier(null, null, null);
        for(StorageSupplierDto storageSupplierDto : storageSupplierDtoList){
            System.out.println(storageSupplierDto.getSupplierName() + "  " + storageSupplierDto.getTotalMoney());
            for(StorageItemDto storageItemDto : storageSupplierDto.getStorageItemDtoList()){
                System.out.println(storageItemDto.getItemName());
            }
        }
    }

    /**
     * 库存盘点
     * @throws Exception
     */
    @Test
    public void listSettlementCheck() throws Exception {
        String startDateStr = "2015/11/23";
        String endDateStr = "2015/11/30";
        //注意format的格式要与日期String的格式相匹配
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate = sdf.parse(startDateStr);
        Date endDate = sdf.parse(endDateStr);
        List<Integer> depotIds = new ArrayList<Integer>();
//        depotIds.add(2);
//        depotIds.add(3);
        List<Integer> tagIds = new ArrayList<Integer>();
//        tagIds.add(33);
//        tagIds.add(34);
//        tagIds.add(70);
//        List<StorageCheckDto> storageCheckDtoList = storageSettlementService.listSettlementCheck(startDate,endDate,depotIds,tagIds,null,1,20);
        List<StorageCheckDto> storageCheckDtoList = storageSettlementService.listSettlementCheck(null,null,tagIds,"1",1,10);
        for(StorageCheckDto storageCheckDto : storageCheckDtoList){
            System.out.println(storageCheckDto.getIngredientName());
        }

//        List<StorageSettlementItem> storageSettlementItemList = storageSettlementService.listSettlementItemByDate(startDate,depotIds,tagIds,null,null,null);
//        for (StorageSettlementItem storageSettlementItem : storageSettlementItemList){
//            System.out.println(storageSettlementItem.getItemId());
//        }
    }


    @Test
    public void listStorageReportDtoByCondition2() throws ParseException, SSException {
        String startDateStr = "2015/11/01";
        String endDateStr = "2015/11/30";
        //注意format的格式要与日期String的格式相匹配
        DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate = sdf.parse(startDateStr);
        Date endDate = sdf.parse(endDateStr);
        List<Integer> depotIds = new ArrayList<Integer>();
        List<Integer> tagIds = new ArrayList<Integer>();
        List<StorageReportDto> storageReportDtoList = storageReportService.listReportDtoByCondition(startDate, endDate, depotIds, tagIds);
        if(storageReportDtoList!=null) {
            for (StorageReportDto storageReportDto : storageReportDtoList) {
                System.out.println(storageReportDto.getStorageReport().getComment());
                for(StorageReportItem storageReportItem : storageReportDto.getStorageReportItemList()){
                    System.out.println(storageReportItem.getItemId()+"  "+storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                }
            }
        }
    }
}
