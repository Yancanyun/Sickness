package com.emenu.service.storage.impl;

import com.emenu.common.dto.storage.StorageCheckDto;
import com.emenu.common.dto.storage.StorageReportDto;
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
import com.emenu.service.storage.StorageSettlementItemService;
import com.emenu.service.storage.StorageItemService;
import com.emenu.service.storage.StorageReportService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * StorageSettlementItemServiceImpl
 * 库存结算
 * @author dujuan
 * @date 2015/11/15
 */
@Service("storageSettlementItemService")
public class StorageSettlementItemServiceImpl implements StorageSettlementItemService {

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

    @Override
    public void newSettlementItem() throws SSException {
        try {
            //第一步：添加一条结算数据:t_storage_settlement
            Settlement settlement = new Settlement();
            settlement.setSerialNumber(serialNumService.generateSerialNum(SerialNumTemplateEnums.SettlementSerialNum));
            Integer settlementId = commonDao.insert(settlement).getId();
            //第二步：把当前时间之前未结算的单据全部结算:t_storage_settlement_item
            //获取当前时间
            Date nowDate = DateUtils.now();
            //获得所有库存原料
            List<StorageItem> storageItemList = storageItemService.listAll();
            //获取当前时间之前所有未结算单据
            List<StorageReportDto> storageReportDtoList = storageReportService.ListStorageReportDtoUnsettled(nowDate);
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

            //循环库存物品
            for (StorageItem storageItem : storageItemList){
                //循环订单
                for (StorageReportDto storageReportDto : storageReportDtoList){
                    //获取该单据下物品详情
                    List<StorageReportItem> storageReportItemList = storageReportDto.getStorageReportItemList();
                    //循环订单下的详情
                    for(StorageReportItem storageReportItem : storageReportItemList){
                        if(storageItem.getId()==storageReportItem.getItemId()){
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
                    //第三步：把结算过的单据状态改为已结算
                    storageReportService.updateStatusById(storageReportDto.getStorageReport().getId(), StorageReportStatusEnum.Settled);
                }
                //实际
                realQuantity = stockInQuantity.subtract(stockOutQuantity).add(incomeOnQuantity.subtract(lossOnQuantity));
                realMoney = stockInMoney.subtract(stockOutMoney).add(incomeOnMoney.subtract(lossOnMoney));

                //取出之前该库存物品的总数
                SettlementItem beforeSettlementItem = storageSettlementMapper.queryByDate(nowDate, storageItem.getId());
                if(Assert.isNotNull(beforeSettlementItem)){
                    totalQuantity = beforeSettlementItem.getTotalQuantity();
                    totalMoney = beforeSettlementItem.getTotalMoney();
                }

                //总数
                totalQuantity = totalQuantity.add(realQuantity);
                totalMoney = totalMoney.add(realMoney);

                //将库存的每个物品的结算情况存到数据库
                SettlementItem settlementItem = new SettlementItem();
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
    public List<StorageCheckDto> listStorageItemCheck(Date startDate,
                                                      Date endDate,
                                                      List<Integer> depotIds,
                                                      List<Integer> tagIds,
                                                      Integer itemId) throws Exception {
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

        List<StorageCheckDto> storageCheckDtoList = new ArrayList<StorageCheckDto>();

        //取出时间段之间的所有单据,根据条件
        //TODO
        List<StorageReportDto> storageReportList = new ArrayList<StorageReportDto>();

        //取出开始时间之前的所有单据详情以及结算结果
        List<SettlementItem> beforeSettlementList = listSettlementItemByDate(startDate, storageReportList);
        for(SettlementItem settlementItem : beforeSettlementList){
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

            SettlementItem newSettlementItem = new SettlementItem();
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

            if(itemId == null || itemId == 0){
                //加到List里面
                storageCheckDtoList.add(storageCheckDto);
            }else {
                if(newSettlementItem.getItemId()==itemId){
                    //加到List里面
                    storageCheckDtoList.add(storageCheckDto);
                }
            }
        }
        return storageCheckDtoList;
    }


    /**
     * 根据某个时间段的单据详情并结算出每个库存物品的结果
     * @param startDate
     * @param storageReportDtoList
     * @return List<SettlementItem>
     * @throws SSException
     */
    private List<SettlementItem> listSettlementItemByDate(Date startDate,
                                                          List<StorageReportDto> storageReportDtoList) throws SSException{
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

        List<SettlementItem> settlementItemList = new ArrayList<SettlementItem>();

        //获得所有库存原料
        List<StorageItem> storageItemList = storageItemService.listAll();
        //循环库存物品
        for (StorageItem storageItem : storageItemList) {
            //循环单据
            for (StorageReportDto storageReportDto : storageReportDtoList) {
                //循环单据详情
                List<StorageReportItem> storageReportItemList = storageReportDto.getStorageReportItemList();
                for (StorageReportItem storageReportItem : storageReportItemList) {
                    if(storageItem.getId()==storageReportItem.getItemId()){
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
            //结存
            realQuantity = stockInQuantity.subtract(stockOutQuantity).add(incomeOnQuantity.subtract(lossOnQuantity));
            realMoney = stockInMoney.subtract(stockOutMoney).add(incomeOnMoney.subtract(lossOnMoney));

            //取出之前该库存物品的总数
            SettlementItem beforeSettlementItem = storageSettlementMapper.queryByDate(startDate, storageItem.getId());
            if(Assert.isNotNull(beforeSettlementItem)){
                totalQuantity = beforeSettlementItem.getTotalQuantity();
                totalMoney = beforeSettlementItem.getTotalMoney();
            }

            //总数
            totalQuantity = totalQuantity.add(realQuantity);
            totalMoney = totalMoney.add(realMoney);

            SettlementItem settlementItem = new SettlementItem();
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
