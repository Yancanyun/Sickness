package com.emenu.service.storage.impl;

import com.emenu.common.dto.storage.StorageCheckDto;
import com.emenu.common.dto.storage.StorageItemDto;
import com.emenu.common.dto.storage.StorageReportDto;
import com.emenu.common.dto.storage.StorageSupplierDto;
import com.emenu.common.entity.party.group.supplier.Supplier;
import com.emenu.common.entity.storage.*;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.enums.storage.StorageReportStatusEnum;
import com.emenu.common.enums.storage.StorageReportTypeEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.DateUtils;
import com.emenu.mapper.storage.StorageSettlementMapper;
import com.emenu.service.dish.UnitService;
import com.emenu.service.dish.tag.TagFacadeService;
import com.emenu.service.other.SerialNumService;
import com.emenu.service.party.group.supplier.SupplierService;
import com.emenu.service.storage.StorageSettlementService;
import com.emenu.service.storage.StorageItemService;
import com.emenu.service.storage.StorageReportService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * StorageSettlementServiceImpl
 * 库存结算
 * @author dujuan
 * @Date 2015/11/15
 */
@Service("storageSettlementService")
public class StorageSettlementServiceImpl implements StorageSettlementService {

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private SerialNumService serialNumService;

    @Autowired
    private StorageItemService storageItemService;

    @Autowired
    private StorageReportService storageReportService;

    @Autowired
    private StorageSettlementMapper storageSettlementMapper;

    @Autowired
    private UnitService unitService;

    @Autowired
    private TagFacadeService tagFacadeService;

    @Autowired
    private SupplierService supplierService;

    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void newSettlement() throws SSException {
        try {
            //第一步：添加一条结算数据:t_storage_settlement
            StorageSettlement settlement = new StorageSettlement();
            settlement.setSerialNumber(serialNumService.generateSerialNum(SerialNumTemplateEnums.SettlementSerialNum));
            Integer settlementId = commonDao.insert(settlement).getId();
            //第二步：把当前时间之前未结算的单据全部结算:t_storage_settlement_item
            //获取当前时间
            Date nowDate = DateUtils.now();
            //获得所有库存原料
            List<StorageItem> storageItemList = storageItemService.listAll();
            //获取当前时间之前所有未结算单据（包括当前时间）
            List<StorageReportDto> storageReportDtoList = storageReportService.ListStorageReportDtoUnsettled(nowDate);

            //循环库存物品
            for (StorageItem storageItem : storageItemList){
                //入库数量
                BigDecimal stockInQuantity = new BigDecimal(0.00);
                //入库金额
                BigDecimal stockInMoney = new BigDecimal(0.00);
                //出库数量
                BigDecimal stockOutQuantity = new BigDecimal(0.00);
                //出库金额
                BigDecimal stockOutMoney = new BigDecimal(0.00);
                //盘盈数量
                BigDecimal incomeOnQuantity = new BigDecimal(0.00);
                //盘盈金额
                BigDecimal incomeOnMoney = new BigDecimal(0.00);
                //盘亏数量
                BigDecimal lossOnQuantity = new BigDecimal(0.00);
                //盘亏金额
                BigDecimal lossOnMoney = new BigDecimal(0.00);
                //实际库存数量
                BigDecimal realQuantity = new BigDecimal(0.00);
                //实际金额
                BigDecimal realMoney = new BigDecimal(0.00);
                //获取之前的总数量和总金额
                BigDecimal totalQuantity = new BigDecimal(0.00);
                BigDecimal totalMoney = new BigDecimal(0.00);

                //循环单据
                for (StorageReportDto storageReportDto : storageReportDtoList){
                    //获取该单据下物品详情
                    List<StorageReportItem> storageReportItemList = storageReportDto.getStorageReportItemList();
                    //循环订单下的详情
                    for(StorageReportItem storageReportItem : storageReportItemList){
                        if(storageItem.getId()==storageReportItem.getItemId()){
                            //入库单
                            if(storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockInReport.getId()) ){
                                stockInQuantity = stockInQuantity.add(storageReportItem.getQuantity());
                                stockInMoney = stockInMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                            }
                            //出库单
                            if(storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockOutReport.getId())){
                                stockOutQuantity = stockOutQuantity.add(storageReportItem.getQuantity());
                                stockOutMoney = stockOutMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                            }
                            //盘盈单
                            if(storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockOutReport.getId())){
                                incomeOnQuantity = incomeOnQuantity.add(storageReportItem.getQuantity());
                                incomeOnMoney = incomeOnMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                            }
                            //盘亏单
                            if(storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.LossOnReport.getId()) ){
                                lossOnQuantity = lossOnQuantity.add(storageReportItem.getQuantity());
                                lossOnMoney = lossOnMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                            }
                        }
                    }
                    //第三步：把结算过的单据状态改为已结算
                    storageReportService.updateStatusById(storageReportDto.getStorageReport().getId(), StorageReportStatusEnum.Settled);
                }
                //实际
                realQuantity = stockInQuantity.subtract(stockOutQuantity).add(incomeOnQuantity.subtract(lossOnQuantity));
                realMoney = stockInMoney.subtract(stockOutMoney).add(incomeOnMoney.subtract(lossOnMoney));

                //取出之前该库存物品的总数
                StorageSettlementItem beforeSettlementItem = storageSettlementMapper.queryByDateAndItemId(nowDate, storageItem.getId());
                if(Assert.isNotNull(beforeSettlementItem)){
                    totalQuantity = beforeSettlementItem.getTotalQuantity();
                    totalMoney = beforeSettlementItem.getTotalMoney();
                }

                //总数
                totalQuantity = totalQuantity.add(realQuantity);
                totalMoney = totalMoney.add(realMoney);

                //将库存的每个物品的结算情况存到数据库
                StorageSettlementItem settlementItem = new StorageSettlementItem();
                settlementItem.setItemId(storageItem.getId());
                settlementItem.setStockInQuantity(stockInQuantity);
                settlementItem.setStockInMoney(stockInMoney);
                settlementItem.setStockOutQuantity(stockOutMoney);
                settlementItem.setIncomeOnQuantity(incomeOnQuantity);
                settlementItem.setIncomeOnMoney(incomeOnMoney);
                settlementItem.setLossOnQuantity(lossOnQuantity);
                settlementItem.setLossOnMoney(lossOnMoney);
                settlementItem.setRealQuantity(realQuantity);
                settlementItem.setRealMoney(realMoney);
                settlementItem.setTotalQuantity(totalQuantity);
                settlementItem.setTotalMoney(totalMoney);
                settlementItem.setSettlementId(settlementId);
                commonDao.insert(settlementItem);
            }
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertStorageSettlementItemFailed, e);
        }
    }

    @Override
    public List<StorageCheckDto> listSettlementCheck(Date startDate,
                                                     Date endDate,
                                                     List<Integer> depotIds,
                                                     List<Integer> tagIds,
                                                     String keyword) throws Exception {

        List<StorageCheckDto> storageCheckDtoList = new ArrayList<StorageCheckDto>();

        //取出时间段之间的所有单据,根据条件
        List<StorageReportDto> storageReportList = storageReportService.listStorageReportDtoByCondition2(startDate,endDate,depotIds,tagIds);

        //取出开始时间之前的所有库存物品计算结果（不包括开始时间）
        List<StorageSettlementItem> beforeSettlementList = listSettlementItemByDate(startDate,depotIds,tagIds,keyword);

        for(StorageSettlementItem settlementItem : beforeSettlementList){

            //期初数量
            BigDecimal beginQuantity = new BigDecimal(0.00);
            //期初金额
            BigDecimal beginMoney = new BigDecimal(0.00);
            //入库数量
            BigDecimal stockInQuantity = new BigDecimal(0.00);
            //入库金额
            BigDecimal stockInMoney = new BigDecimal(0.00);
            //出库数量
            BigDecimal stockOutQuantity = new BigDecimal(0.00);
            //出库金额
            BigDecimal stockOutMoney = new BigDecimal(0.00);
            //盘盈数量
            BigDecimal incomeOnQuantity = new BigDecimal(0.00);
            //盘盈金额
            BigDecimal incomeOnMoney = new BigDecimal(0.00);
            //盘亏数量
            BigDecimal lossOnQuantity = new BigDecimal(0.00);
            //盘亏金额
            BigDecimal lossOnMoney = new BigDecimal(0.00);
            //实际数量
            BigDecimal realQuantity = new BigDecimal(0.00);
            //实际金额
            BigDecimal realMoney = new BigDecimal(0.00);
            //结存
            BigDecimal totalQuantity = new BigDecimal(0.00);
            BigDecimal totalMoney = new BigDecimal(0.00);

            //循环单据
            for (StorageReportDto storageReportDto : storageReportList) {
                //循环单据详情
                List<StorageReportItem> storageReportItemList = storageReportDto.getStorageReportItemList();
                for (StorageReportItem storageReportItem : storageReportItemList) {
                    if(settlementItem.getItemId()==storageReportItem.getItemId()){
                        //入库单
                        if(storageReportDto.getStorageReport().getType()== StorageReportTypeEnum.StockInReport.getId()){
                            stockInQuantity = stockInQuantity.add(storageReportItem.getQuantity());
                            stockInMoney = stockInMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                        }
                        //出库单
                        if(storageReportDto.getStorageReport().getType()== StorageReportTypeEnum.StockOutReport.getId()){
                            stockOutQuantity = stockOutQuantity.add(storageReportItem.getQuantity());
                            stockOutMoney = stockOutMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                        }
                        //盘盈单
                        if(storageReportDto.getStorageReport().getType()== StorageReportTypeEnum.IncomeOnReport.getId()){
                            incomeOnQuantity = incomeOnQuantity.add(storageReportItem.getQuantity());
                            incomeOnMoney = incomeOnMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                        }
                        //盘亏单
                        if(storageReportDto.getStorageReport().getType()== StorageReportTypeEnum.LossOnReport.getId()){
                            lossOnQuantity = lossOnQuantity.add(storageReportItem.getQuantity());
                            lossOnMoney = lossOnMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                        }
                    }
                }
            }
            //实际数量
            realQuantity = stockInQuantity.subtract(stockOutQuantity).add(incomeOnQuantity.subtract(lossOnQuantity));
            realMoney = stockInMoney.subtract(stockOutMoney).add(incomeOnMoney.subtract(lossOnMoney));
            //期初
            beginQuantity = settlementItem.getTotalQuantity();
            beginMoney = settlementItem.getTotalMoney();

            //结存
            totalQuantity = totalQuantity.add(realQuantity);
            totalMoney = totalMoney.add(realMoney);

            //将库存的每个物品的结算情况存到数据库
            StorageCheckDto storageCheckDto = new StorageCheckDto();
            //获取该物品的属性
            StorageItem storageItem = storageItemService.queryById(settlementItem.getItemId());
            storageCheckDto.setName(storageItem.getName());
            storageCheckDto.setItemNumber(storageItem.getItemNumber());
            storageCheckDto.setOrderUnitName(unitService.queryById(storageItem.getOrderUnitId()).getName());
            storageCheckDto.setStorageUnitName(unitService.queryById(storageItem.getStorageUnitId()).getName());
            storageCheckDto.setTagName(tagFacadeService.queryById(storageItem.getTagId()).getName());
            storageCheckDto.setLastStockInPrice(storageItem.getLastStockInPrice());
            storageCheckDto.setBeginQuantity(beginQuantity);
            storageCheckDto.setBeginMoney(beginMoney);

            StorageSettlementItem newSettlementItem = new StorageSettlementItem();
            newSettlementItem.setItemId(storageItem.getId());
            newSettlementItem.setStockInQuantity(stockInQuantity);
            newSettlementItem.setStockInMoney(stockInMoney);
            newSettlementItem.setStockOutQuantity(stockOutMoney);
            newSettlementItem.setIncomeOnQuantity(incomeOnQuantity);
            newSettlementItem.setIncomeOnMoney(incomeOnMoney);
            newSettlementItem.setLossOnQuantity(lossOnQuantity);
            newSettlementItem.setLossOnMoney(lossOnMoney);
            newSettlementItem.setRealQuantity(realQuantity);
            newSettlementItem.setRealMoney(realMoney);
            newSettlementItem.setTotalQuantity(totalQuantity);
            newSettlementItem.setTotalMoney(totalMoney);

            storageCheckDto.setSettlementItem(newSettlementItem);

//            if(itemId == null || itemId == 0){
//                //加到List里面
//                storageCheckDtoList.add(storageCheckDto);
//            }else {
//                if(newSettlementItem.getItemId()==itemId){
//                    //加到List里面
//                    storageCheckDtoList.add(storageCheckDto);
//                }
//            }
        }
        return storageCheckDtoList;
    }

    @Override
    public List<StorageSupplierDto> listSettlementSupplier(Integer supplierId,
                                                           Date startDate,
                                                           Date endDate) throws SSException {
        List<StorageSupplierDto> storageSupplierDtoList = new ArrayList<StorageSupplierDto>();
        try {
            //供货商partyId
            Integer supplierPartyId = 0;
            if(supplierId != null && supplierId != 0){
                Supplier supplier = supplierService.queryById(supplierId);
                if(supplier!=null){
                    //获取供货商partyId
                    supplierPartyId = supplier.getPartyId();
                }
                //获取物品列表
                List<StorageItemDto> storageItemDtoList = storageSettlementMapper.listItemByDateAndSupplierId(supplierPartyId, startDate, endDate);
                StorageSupplierDto storageSupplierDto = new StorageSupplierDto();
                storageSupplierDto.setSupplierName(supplier.getName());
                List<StorageItemDto> childItemDtoList = new ArrayList<StorageItemDto>();
                //总金额
                BigDecimal totalMoeny = new BigDecimal(0.00);
                for (StorageItemDto storageItemDto : storageItemDtoList) {
                    if (storageItemDto.getSupplierPartyId().equals(supplier.getPartyId())) {
                        childItemDtoList.add(storageItemDto);
                        totalMoeny = totalMoeny.add(storageItemDto.getItemMoney());
                    }
                }
                storageSupplierDto.setStorageItemDtoList(childItemDtoList);
                storageSupplierDto.setTotalMoney(totalMoeny);
                storageSupplierDtoList.add(storageSupplierDto);
            }else {
                //获取物品列表
                List<StorageItemDto> storageItemDtoList = storageSettlementMapper.listItemByDateAndSupplierId(supplierPartyId, startDate, endDate);
                //取出所有供货商
                List<Supplier> supplierList = supplierService.listAll();
                for (Supplier supplier1 : supplierList) {
                    StorageSupplierDto storageSupplierDto = new StorageSupplierDto();
                    storageSupplierDto.setSupplierName(supplier1.getName());
                    List<StorageItemDto> childItemDtoList = new ArrayList<StorageItemDto>();
                    //总金额
                    BigDecimal totalMoney = new BigDecimal(0.00);
                    for (StorageItemDto storageItemDto : storageItemDtoList) {
                        if (storageItemDto.getSupplierPartyId() == supplier1.getPartyId()) {
                            childItemDtoList.add(storageItemDto);
                            totalMoney = totalMoney.add(storageItemDto.getItemMoney());
                        }
                    }
                    storageSupplierDto.setStorageItemDtoList(childItemDtoList);
                    storageSupplierDto.setTotalMoney(totalMoney);
                    storageSupplierDtoList.add(storageSupplierDto);
                }
            }
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelPrinterDishError, e);
        }
        return storageSupplierDtoList;
    }


    /**
     * 根据某个时间之前每个库存物品的库存结果
     * 主要是为了计算期初
     * @param startDate
     * @return List<StorageSettlementItem>
     * @throws SSException
     */
    private List<StorageSettlementItem> listSettlementItemByDate(Date startDate,
                                                                 List<Integer> depotIds,
                                                                 List<Integer> tagIds,
                                                                 String keyword) throws SSException{
        List<StorageSettlementItem> settlementItemList = new ArrayList<StorageSettlementItem>();
        //库存物品列表
        List<StorageItem> storageItemList = Collections.emptyList();
        if((depotIds==null&&depotIds.size()==0) && (tagIds==null && tagIds.size()==0) && (keyword==null)){
            //获得所有库存原料列表
            storageItemList = storageItemService.listAll();
        }else {
            //根据存放点Id和库存分类Id获取库存物品列表
            storageItemList = storageSettlementMapper.listStorageItemByDepotAndTag(depotIds, tagIds, keyword);
        }

        //获取该时间之前最后一次结算时间(包括当前时间)
        StorageSettlement storageSettlement = storageSettlementMapper.queryLastSettlement(startDate);
        Date settlementDate = null;
        if(storageSettlement!=null) {
            settlementDate =storageSettlement.getCreatedTime();
        }
        //TODO 参数：开始时间settlementDate，结束时间startDate
        //单据列表——从上一次结算到开始时间之间的单据（不包括开始时间，不包括结束时间）
        List<StorageReportDto> storageReportDtoList = new ArrayList<StorageReportDto>();

        //循环库存物品
        for (StorageItem storageItem : storageItemList) {
            //入库数量
            BigDecimal stockInQuantity = new BigDecimal(0.00);
            //入库金额
            BigDecimal stockInMoney = new BigDecimal(0.00);
            //出库数量
            BigDecimal stockOutQuantity = new BigDecimal(0.00);
            //出库金额
            BigDecimal stockOutMoney = new BigDecimal(0.00);
            //盘盈数量
            BigDecimal incomeOnQuantity = new BigDecimal(0.00);
            //盘盈金额
            BigDecimal incomeOnMoney = new BigDecimal(0.00);
            //盘亏数量
            BigDecimal lossOnQuantity = new BigDecimal(0.00);
            //盘亏金额
            BigDecimal lossOnMoney = new BigDecimal(0.00);
            //实际数量
            BigDecimal realQuantity = new BigDecimal(0.00);
            //实际金额
            BigDecimal realMoney = new BigDecimal(0.00);
            //结存
            BigDecimal totalQuantity = new BigDecimal(0.00);
            BigDecimal totalMoney = new BigDecimal(0.00);

            //循环单据
            for (StorageReportDto storageReportDto : storageReportDtoList) {
                //循环单据详情
                List<StorageReportItem> storageReportItemList = storageReportDto.getStorageReportItemList();
                for (StorageReportItem storageReportItem : storageReportItemList) {
                    if(storageItem.getId()==storageReportItem.getItemId()){
                        //入库单
                        if(storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockInReport.getId()) ){
                            stockInQuantity = stockInQuantity.add(storageReportItem.getQuantity());
                            stockInMoney = stockInMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                        }
                        //出库单
                        if(storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockInReport.getId())){
                            stockOutQuantity = stockOutQuantity.add(storageReportItem.getQuantity());
                            stockOutMoney = stockOutMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                        }
                        //盘盈单
                        if(storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockInReport.getId())){
                            incomeOnQuantity = incomeOnQuantity.add(storageReportItem.getQuantity());
                            incomeOnMoney = incomeOnMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                        }
                        //盘亏单
                        if(storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockInReport.getId())){
                            lossOnQuantity = lossOnQuantity.add(storageReportItem.getQuantity());
                            lossOnMoney = lossOnMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                        }
                    }
                }
            }
            //结存
            realQuantity = stockInQuantity.subtract(stockOutQuantity).add(incomeOnQuantity.subtract(lossOnQuantity));
            realMoney = stockInMoney.subtract(stockOutMoney).add(incomeOnMoney.subtract(lossOnMoney));

            //取出之前该库存物品的总数
            StorageSettlementItem beforeSettlementItem = storageSettlementMapper.queryByDateAndItemId(startDate, storageItem.getId());
            if(Assert.isNotNull(beforeSettlementItem)){
                totalQuantity = beforeSettlementItem.getTotalQuantity();
                totalMoney = beforeSettlementItem.getTotalMoney();
            }

            //总数
            totalQuantity = totalQuantity.add(realQuantity);
            totalMoney = totalMoney.add(realMoney);

            StorageSettlementItem settlementItem = new StorageSettlementItem();
            settlementItem.setItemId(storageItem.getId());
            settlementItem.setStockInQuantity(stockInQuantity);
            settlementItem.setStockInMoney(stockInMoney);
            settlementItem.setStockOutQuantity(stockOutMoney);
            settlementItem.setIncomeOnQuantity(incomeOnQuantity);
            settlementItem.setIncomeOnMoney(incomeOnMoney);
            settlementItem.setLossOnQuantity(lossOnQuantity);
            settlementItem.setLossOnMoney(lossOnMoney);
            settlementItem.setRealQuantity(realQuantity);
            settlementItem.setRealMoney(realMoney);
            settlementItem.setTotalQuantity(totalQuantity);
            settlementItem.setTotalMoney(totalMoney);
            //加到List里面
            settlementItemList.add(settlementItem);
        }
        return settlementItemList;
    }
}
