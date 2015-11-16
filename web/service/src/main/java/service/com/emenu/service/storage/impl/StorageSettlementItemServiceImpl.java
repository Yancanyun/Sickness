package com.emenu.service.storage.impl;

import com.emenu.common.dto.storage.StorageReportDto;
import com.emenu.common.entity.storage.*;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.enums.storage.StorageReportStatusEnum;
import com.emenu.common.enums.storage.StorageReportTypeEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.DateUtils;
import com.emenu.mapper.storage.StorageSettlementMapper;
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
                    storageReportDto.getStorageReport().setStatus(StorageReportStatusEnum.Settled.getId());
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
}
