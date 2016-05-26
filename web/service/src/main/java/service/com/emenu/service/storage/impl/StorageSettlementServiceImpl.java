package com.emenu.service.storage.impl;

import com.emenu.common.dto.storage.*;
import com.emenu.common.entity.party.group.supplier.Supplier;
import com.emenu.common.entity.storage.*;
import com.emenu.common.enums.ExcelExportTemplateEnums;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.enums.storage.StorageReportStatusEnum;
import com.emenu.common.enums.storage.StorageReportTypeEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.DateUtils;
import com.emenu.common.utils.EntityUtil;
import com.emenu.mapper.storage.StorageSettlementMapper;
import com.emenu.service.dish.UnitService;
import com.emenu.service.dish.tag.TagFacadeService;
import com.emenu.service.other.SerialNumService;
import com.emenu.service.party.group.supplier.SupplierService;
import com.emenu.service.storage.IngredientService;
import com.emenu.service.storage.StorageSettlementService;
import com.emenu.service.storage.StorageItemService;
import com.emenu.service.storage.StorageReportService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.common.util.IOUtil;
import com.pandawork.core.framework.dao.CommonDao;
import com.pandawork.core.pweio.excel.DataType;
import com.pandawork.core.pweio.excel.ExcelTemplateEnum;
import com.pandawork.core.pweio.excel.ExcelWriter;
import jxl.Workbook;
import jxl.format.*;
import jxl.write.*;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

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

    @Autowired
    private IngredientService ingredientService;

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
            //获取所有原配料
            List<Ingredient> ingredientList = ingredientService.listAll();
            //获得所有原配料
            List<StorageItem> storageItemList = storageItemService.listAll();
            if (Assert.isEmpty(ingredientList)){
                ingredientList = Collections.emptyList();
            }
            if(Assert.isEmpty(storageItemList)){
                storageItemList = Collections.emptyList();
            }
            //获取当前时间之前所有未结算单据（包括当前时间）
            List<StorageReportDto> storageReportDtoList = storageReportService.ListReportDtoUnsettledByEndTime(nowDate);
            if(Assert.isEmpty(storageReportDtoList)){
                storageReportDtoList = Collections.emptyList();
            }
            for (Ingredient ingredient : ingredientList){
                //入库数量
                BigDecimal ingredientStockInQuantity = new BigDecimal(0.00);
                //入库金额
                BigDecimal ingredientStockInMoney = new BigDecimal(0.00);
                //出库数量
                BigDecimal ingredientStockOutQuantity = new BigDecimal(0.00);
                //出库金额
                BigDecimal ingredientStockOutMoney = new BigDecimal(0.00);
                //盘盈数量
                BigDecimal ingredientIncomeOnQuantity = new BigDecimal(0.00);
                //盘盈金额
                BigDecimal ingredientIncomeOnMoney = new BigDecimal(0.00);
                //盘亏数量
                BigDecimal ingredientLossOnQuantity = new BigDecimal(0.00);
                //盘亏金额
                BigDecimal ingredientLossOnMoney = new BigDecimal(0.00);
                //实际库存数量
                BigDecimal ingredientRealQuantity = new BigDecimal(0.00);
                //实际金额
                BigDecimal ingredientRealMoney = new BigDecimal(0.00);
                //获取之前的总数量和总金额
                BigDecimal ingredientTotalQuantity = new BigDecimal(0.00);
                BigDecimal ingredientTotalMoney = new BigDecimal(0.00);

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
                    //实际数量 入库-出库+盘盈-盘亏
                    BigDecimal realQuantity = new BigDecimal(0.00);
                    //实际金额
                    BigDecimal realMoney = new BigDecimal(0.00);
                    //获取之前的总数量和总金额 这个指的的是实际库存
                    BigDecimal totalQuantity = new BigDecimal(0.00);
                    BigDecimal totalMoney = new BigDecimal(0.00);

                    //循环单据
                    for (StorageReportDto storageReportDto : storageReportDtoList) {
                        //获取该单据下物品详情
                        List<StorageReportItem> storageReportItemList = storageReportDto.getStorageReportItemList();
                        //循环订单下的详情
                        for (StorageReportItem storageReportItem : storageReportItemList) {
                            if (storageItem.getId() == storageReportItem.getItemId()) {
                                //入库单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockInReport.getId())) {
                                    stockInQuantity = stockInQuantity.add(storageReportItem.getQuantity());
                                    stockInMoney = stockInMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                                }
                                //出库单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockOutReport.getId())) {
                                    stockOutQuantity = stockOutQuantity.add(storageReportItem.getQuantity());
                                    stockOutMoney = stockOutMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                                }
                                //盘盈单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockOutReport.getId())) {
                                    incomeOnQuantity = incomeOnQuantity.add(storageReportItem.getQuantity());
                                    incomeOnMoney = incomeOnMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                                }
                                //盘亏单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.LossOnReport.getId())) {
                                    lossOnQuantity = lossOnQuantity.add(storageReportItem.getQuantity());
                                    lossOnMoney = lossOnMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                                }
                                StorageItem Item = storageItemService.queryById(storageItem.getId());
                                if (Assert.isNotNull(Item)
                                        && Item.getIngredientId() == ingredient.getId()){
                                    if (storageItem.getCountUnitId() == storageItem.getOrderUnitId()){
                                        //入库数量 这个数量单位是成本卡规格
                                        ingredientStockInQuantity = stockInQuantity.add(storageReportItem.getQuantity().multiply(storageItem.getOrderToStorageRatio()).multiply(storageItem.getStorageToCostCardRatio()));
                                    } else {
                                        ingredientStockInQuantity = stockInQuantity.add(storageReportItem.getQuantity().multiply(storageItem.getStorageToCostCardRatio()));
                                    }
                                    //入库金额
                                    ingredientStockInMoney = ingredientStockInMoney.add(stockInMoney);
                                    if (storageItem.getCountUnitId() == storageItem.getOrderUnitId()){
                                        //入库数量 这个数量单位是成本卡规格
                                        ingredientStockOutQuantity = ingredientStockOutQuantity.add(storageReportItem.getQuantity().multiply(storageItem.getOrderToStorageRatio()).multiply(storageItem.getStorageToCostCardRatio()));
                                    } else {
                                        ingredientStockOutQuantity = ingredientStockOutQuantity.add(storageReportItem.getQuantity().multiply(storageItem.getStorageToCostCardRatio()));
                                    }
                                    //出库金额
                                    ingredientStockOutMoney = ingredientStockOutMoney.add(stockOutMoney);
                                    if (storageItem.getCountUnitId() == storageItem.getOrderUnitId()){
                                        //入库数量 这个数量单位是成本卡规格
                                        ingredientIncomeOnQuantity = ingredientIncomeOnQuantity.add(storageReportItem.getQuantity().multiply(storageItem.getOrderToStorageRatio()).multiply(storageItem.getStorageToCostCardRatio()));
                                    } else {
                                        ingredientIncomeOnQuantity = ingredientIncomeOnQuantity.add(storageReportItem.getQuantity().multiply(storageItem.getStorageToCostCardRatio()));
                                    }
                                    //盘盈金额
                                    ingredientIncomeOnMoney = ingredientIncomeOnQuantity.add(incomeOnMoney);
                                    //盘亏数量
                                    if (storageItem.getCountUnitId() == storageItem.getOrderUnitId()){
                                        //入库数量 这个数量单位是成本卡规格
                                        ingredientLossOnQuantity = ingredientIncomeOnQuantity.add(storageReportItem.getQuantity().multiply(storageItem.getOrderToStorageRatio()).multiply(storageItem.getStorageToCostCardRatio()));
                                    } else {
                                        ingredientLossOnQuantity = ingredientIncomeOnQuantity.add(storageReportItem.getQuantity().multiply(storageItem.getStorageToCostCardRatio()));
                                    }
                                    //盘亏金额
                                    ingredientLossOnMoney = ingredientLossOnMoney.add(ingredientLossOnMoney);

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
                    if (Assert.isNotNull(beforeSettlementItem)) {
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
                //原配料实际库存数量
                ingredientRealQuantity = ingredientStockInQuantity.subtract(ingredientStockOutQuantity).add(ingredientIncomeOnQuantity.subtract(ingredientLossOnQuantity));;
                //原配料实际金额
                ingredientRealMoney = ingredientStockInMoney.subtract(ingredientStockOutMoney).add(ingredientIncomeOnMoney.subtract(ingredientLossOnMoney));
                //取出之前该库存物品的总数
                StorageSettlementIngredient beforeSettlementIngredient = storageSettlementMapper.queryByDateAndIngredientId(nowDate, ingredient.getId());
                if (Assert.isNotNull(beforeSettlementIngredient)) {
                    ingredientTotalQuantity = beforeSettlementIngredient.getTotalQuantity();
                    ingredientTotalMoney = beforeSettlementIngredient.getTotalMoney();
                }
                //总数
                ingredientTotalQuantity = ingredientTotalQuantity.add(ingredientRealQuantity);
                ingredientTotalMoney = ingredientTotalMoney.add(ingredientRealMoney);

                StorageSettlementIngredient settlementIngredient = new StorageSettlementIngredient();
                settlementIngredient.setId(ingredient.getId());
                settlementIngredient.setStockInQuantity(ingredientStockInQuantity);
                settlementIngredient.setStockInMoney(ingredientStockInMoney);
                settlementIngredient.setStockOutQuantity(ingredientStockOutQuantity);
                settlementIngredient.setIncomeOnQuantity(ingredientStockOutMoney);
                settlementIngredient.setIncomeOnMoney(ingredientIncomeOnQuantity);
                settlementIngredient.setLossOnQuantity(ingredientIncomeOnMoney);
                settlementIngredient.setLossOnMoney(ingredientLossOnQuantity);
                settlementIngredient.setRealQuantity(ingredientLossOnMoney);
                settlementIngredient.setRealMoney(ingredientRealQuantity);
                settlementIngredient.setTotalQuantity(ingredientTotalQuantity);
                settlementIngredient.setTotalMoney(ingredientTotalMoney);
                settlementIngredient.setSettlementId(settlementId);
                commonDao.insert(settlementIngredient);
            }
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertStorageSettlementItemFailed, e);
        }
    }

    @Override
    public List<StorageCheckDto>  listSettlementCheck(Date startDate,
                                                      Date endDate,
                                                      Integer supplierId,
                                                      List<Integer> depotIds,
                                                      List<Integer> tagIds,
                                                      String keyword,
                                                      Integer curPage,
                                                      Integer pageSize) throws SSException {
        List<StorageCheckDto> storageCheckDtoList = new ArrayList<StorageCheckDto>();
        try {

            //取出时间段之间的所有单据,根据条件
            List<StorageReportDto> storageReportList = storageReportService.listReportDtoByCondition(startDate, endDate, depotIds, tagIds);
            if (Assert.isEmpty(storageReportList)) {
                storageReportList = Collections.emptyList();
            }
            //取出开始时间之前的所有库存物品计算结果（不包括开始时间）
            List<StorageSettlementItem> beforeSettlementList = listSettlementItemByDate(startDate, supplierId, depotIds, tagIds, keyword, curPage, pageSize);
            if (Assert.isEmpty(beforeSettlementList)) {
                beforeSettlementList = Collections.emptyList();
            }

            for (StorageSettlementItem settlementItem : beforeSettlementList) {
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
                        if (settlementItem.getItemId() == storageReportItem.getItemId()) {
                            //入库单
                            if (storageReportDto.getStorageReport().getType() == StorageReportTypeEnum.StockInReport.getId()) {
                                stockInQuantity = stockInQuantity.add(storageReportItem.getQuantity());
                                stockInMoney = stockInMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                            }
                            //出库单
                            if (storageReportDto.getStorageReport().getType() == StorageReportTypeEnum.StockOutReport.getId()) {
                                stockOutQuantity = stockOutQuantity.add(storageReportItem.getQuantity());
                                stockOutMoney = stockOutMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                            }
                            //盘盈单
                            if (storageReportDto.getStorageReport().getType() == StorageReportTypeEnum.IncomeOnReport.getId()) {
                                incomeOnQuantity = incomeOnQuantity.add(storageReportItem.getQuantity());
                                incomeOnMoney = incomeOnMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                            }
                            //盘亏单
                            if (storageReportDto.getStorageReport().getType() == StorageReportTypeEnum.LossOnReport.getId()) {
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
                if (Assert.isNull(storageItem)) {
                    throw SSException.get(EmenuException.StorageItemQueryFailed);
                }
                storageCheckDto.setItemName(storageItem.getName());
                storageCheckDto.setItemNumber(storageItem.getItemNumber());
                storageCheckDto.setOrderUnitName(unitService.queryById(storageItem.getOrderUnitId()).getName());
                storageCheckDto.setStorageUnitName(unitService.queryById(storageItem.getStorageUnitId()).getName());
                storageCheckDto.setTagName(tagFacadeService.queryById(storageItem.getTagId()).getName());
                storageCheckDto.setLastStockInPrice(storageItem.getLastStockInPrice());
                storageCheckDto.setBeginQuantity(beginQuantity);
                storageCheckDto.setBeginMoney(beginMoney);
                storageCheckDto.setStockInQuantity(stockInQuantity);
                storageCheckDto.setStockInMoney(stockInMoney);
                storageCheckDto.setStockOutQuantity(stockOutQuantity);
                storageCheckDto.setStockOutMoney(stockOutMoney);
                storageCheckDto.setIncomeLossQuantity(incomeOnQuantity.subtract(lossOnQuantity));
                storageCheckDto.setIncomeLossMoney(incomeOnMoney.subtract(lossOnMoney));
                storageCheckDto.setTotalQuantity(totalQuantity);
                storageCheckDto.setTotalMoney(totalMoney);
                if(totalQuantity.compareTo(BigDecimal.ZERO)==0){
                    storageCheckDto.setTotalAveragePrice(BigDecimal.ZERO);
                }else {
                    storageCheckDto.setTotalAveragePrice(totalMoney.divide(totalQuantity));
                }
                storageCheckDto.setMaxStorageQuantity(storageItem.getMaxStorageQuantity());
                storageCheckDto.setMinStorageQuantity(storageItem.getMinStorageQuantity());
                storageCheckDtoList.add(storageCheckDto);
            }
            return storageCheckDtoList;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListStorageSettlementCheckFailed, e);
        }
    }

    @Override
    public List<StorageCheckDto> listSettlementIngredientCheck(Date startDate, Date endDate, List<Integer> tagIds, String keyword, Integer curPage, Integer pageSize) throws SSException {
        List<StorageCheckDto> storageCheckDtoList = new ArrayList<StorageCheckDto>();
        try {

            //取出时间段之间的所有单据,根据条件
            List<StorageReportDto> storageReportList = storageReportService.listReportDtoByCondition(startDate, endDate, null, tagIds);
            if (Assert.isEmpty(storageReportList)) {
                storageReportList = Collections.emptyList();
            }
            //取出开始时间之前的所有库存物品计算结果（不包括开始时间）
            List<StorageSettlementIngredient> beforeSettlementList = listSettlementIngredientByDate(startDate,tagIds, keyword, curPage, pageSize);
            if (Assert.isEmpty(beforeSettlementList)) {
                beforeSettlementList = Collections.emptyList();
            }

            for (StorageSettlementIngredient settlementIngredient : beforeSettlementList) {

                //入库数量
                BigDecimal ingredientStockInQuantity = new BigDecimal(0.00);
                //入库金额
                BigDecimal ingredientStockInMoney = new BigDecimal(0.00);
                //出库数量
                BigDecimal ingredientStockOutQuantity = new BigDecimal(0.00);
                //出库金额
                BigDecimal ingredientStockOutMoney = new BigDecimal(0.00);
                //盘盈数量
                BigDecimal ingredientIncomeOnQuantity = new BigDecimal(0.00);
                //盘盈金额
                BigDecimal ingredientIncomeOnMoney = new BigDecimal(0.00);
                //盘亏数量
                BigDecimal ingredientLossOnQuantity = new BigDecimal(0.00);
                //盘亏金额
                BigDecimal ingredientLossOnMoney = new BigDecimal(0.00);
                //实际库存数量
                BigDecimal ingredientRealQuantity = new BigDecimal(0.00);
                //实际金额
                BigDecimal ingredientRealMoney = new BigDecimal(0.00);
                //获取之前的总数量和总金额
                BigDecimal ingredientTotalQuantity = new BigDecimal(0.00);
                BigDecimal ingredientTotalMoney = new BigDecimal(0.00);

                List<Integer> storageItemIdList = Collections.emptyList();
                storageItemIdList = storageItemService.listIdsByIngredientId(settlementIngredient.getIngredientId());

                for (Integer storageitemId : storageItemIdList){
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
                            //获取该单据下物品详情
                            List<StorageReportItem> storageReportItemList = storageReportDto.getStorageReportItemList();
                            //循环订单下的详情
                            for (StorageReportItem storageReportItem : storageReportItemList) {
                                if (storageitemId == storageReportItem.getItemId()) {
                                    //入库单
                                    if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockInReport.getId())) {
                                        stockInQuantity = stockInQuantity.add(storageReportItem.getQuantity());
                                        stockInMoney = stockInMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                                    }
                                    //出库单
                                    if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockOutReport.getId())) {
                                        stockOutQuantity = stockOutQuantity.add(storageReportItem.getQuantity());
                                        stockOutMoney = stockOutMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                                    }
                                    //盘盈单
                                    if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockOutReport.getId())) {
                                        incomeOnQuantity = incomeOnQuantity.add(storageReportItem.getQuantity());
                                        incomeOnMoney = incomeOnMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                                    }
                                    //盘亏单
                                    if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.LossOnReport.getId())) {
                                        lossOnQuantity = lossOnQuantity.add(storageReportItem.getQuantity());
                                        lossOnMoney = lossOnMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                                    }
                                    StorageItem Item = storageItemService.queryById(storageitemId);
                                    if (Assert.isNotNull(Item)
                                            && Item.getIngredientId() == settlementIngredient.getIngredientId()){
                                        if (Item.getCountUnitId() == Item.getOrderUnitId()){
                                            //入库数量 这个数量单位是成本卡规格
                                            ingredientStockInQuantity = stockInQuantity.add(storageReportItem.getQuantity().multiply(Item.getOrderToStorageRatio()).multiply(Item.getStorageToCostCardRatio()));
                                        } else {
                                            ingredientStockInQuantity = stockInQuantity.add(storageReportItem.getQuantity().multiply(Item.getStorageToCostCardRatio()));
                                        }
                                        //入库金额
                                        ingredientStockInMoney = ingredientStockInMoney.add(stockInMoney);
                                        if (Item.getCountUnitId() == Item.getOrderUnitId()){
                                            //入库数量 这个数量单位是成本卡规格
                                            ingredientStockOutQuantity = ingredientStockOutQuantity.add(storageReportItem.getQuantity().multiply(Item.getOrderToStorageRatio()).multiply(Item.getStorageToCostCardRatio()));
                                        } else {
                                            ingredientStockOutQuantity = ingredientStockOutQuantity.add(storageReportItem.getQuantity().multiply(Item.getStorageToCostCardRatio()));
                                        }
                                        //出库金额
                                        ingredientStockOutMoney = ingredientStockOutMoney.add(stockOutMoney);
                                        if (Item.getCountUnitId() == Item.getOrderUnitId()){
                                            //入库数量 这个数量单位是成本卡规格
                                            ingredientIncomeOnQuantity = ingredientIncomeOnQuantity.add(storageReportItem.getQuantity().multiply(Item.getOrderToStorageRatio()).multiply(Item.getStorageToCostCardRatio()));
                                        } else {
                                            ingredientIncomeOnQuantity = ingredientIncomeOnQuantity.add(storageReportItem.getQuantity().multiply(Item.getStorageToCostCardRatio()));
                                        }
                                        //盘盈金额
                                        ingredientIncomeOnMoney = ingredientIncomeOnQuantity.add(incomeOnMoney);
                                        //盘亏数量
                                        if (Item.getCountUnitId() == Item.getOrderUnitId()){
                                            //入库数量 这个数量单位是成本卡规格
                                            ingredientLossOnQuantity = ingredientIncomeOnQuantity.add(storageReportItem.getQuantity().multiply(Item.getOrderToStorageRatio()).multiply(Item.getStorageToCostCardRatio()));
                                        } else {
                                            ingredientLossOnQuantity = ingredientIncomeOnQuantity.add(storageReportItem.getQuantity().multiply(Item.getStorageToCostCardRatio()));
                                        }
                                        //盘亏金额
                                        ingredientLossOnMoney = ingredientLossOnMoney.add(ingredientLossOnMoney);

                                    }
                                }
                            }
                        }
                    //实际数量
                    realQuantity = stockInQuantity.subtract(stockOutQuantity).add(incomeOnQuantity.subtract(lossOnQuantity));
                    realMoney = stockInMoney.subtract(stockOutMoney).add(incomeOnMoney.subtract(lossOnMoney));
                    //期初
                    beginQuantity = settlementIngredient.getTotalQuantity();
                    beginMoney = settlementIngredient.getTotalMoney();

                    //结存
                    totalQuantity = totalQuantity.add(realQuantity);
                    totalMoney = totalMoney.add(realMoney);

                    //将库存的每个物品的结算情况存到数据库
                    StorageCheckDto storageCheckDto = new StorageCheckDto();
                    //获取该物品的属性
                    Ingredient ingredient = ingredientService.queryById(settlementIngredient.getIngredientId());
                    if (Assert.isNull(ingredient)) {
                        throw SSException.get(EmenuException.StorageItemQueryFailed);
                    }
                    storageCheckDto.setItemName(ingredient.getName());
                    storageCheckDto.setItemNumber(ingredient.getIngredientNumber());
                    storageCheckDto.setOrderUnitName(unitService.queryById(ingredient.getOrderUnitId()).getName());
                    storageCheckDto.setStorageUnitName(unitService.queryById(ingredient.getStorageUnitId()).getName());
                    storageCheckDto.setTagName(tagFacadeService.queryById(ingredient.getTagId()).getName());
                    storageCheckDto.setBeginQuantity(beginQuantity);
                    storageCheckDto.setBeginMoney(beginMoney);
                    storageCheckDto.setStockInQuantity(ingredientStockInQuantity);
                    storageCheckDto.setStockInMoney(ingredientStockInMoney);
                    storageCheckDto.setStockOutQuantity(ingredientStockOutQuantity);
                    storageCheckDto.setStockOutMoney(ingredientStockOutMoney);
                    storageCheckDto.setIncomeLossQuantity(ingredientIncomeOnQuantity.subtract(lossOnQuantity));
                    storageCheckDto.setIncomeLossMoney(ingredientIncomeOnMoney.subtract(lossOnMoney));
                    storageCheckDto.setTotalQuantity(ingredientTotalQuantity);
                    storageCheckDto.setTotalMoney(ingredientTotalMoney);
                    if(totalQuantity.compareTo(BigDecimal.ZERO)==0){
                        storageCheckDto.setTotalAveragePrice(BigDecimal.ZERO);
                    }else {
                        storageCheckDto.setTotalAveragePrice(totalMoney.divide(totalQuantity));
                    }
                    storageCheckDto.setMaxStorageQuantity(ingredient.getMaxStorageQuantity());
                    storageCheckDto.setMinStorageQuantity(ingredient.getMinStorageQuantity());
                    storageCheckDtoList.add(storageCheckDto);
                }
            }
            return storageCheckDtoList;
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListStorageSettlementCheckFailed, e);
        }
    }

    @Override
    public int countSettlementCheck(Date startDate,
                                    Date endDate,
                                    Integer supplierId,
                                    List<Integer> depotIds,
                                    List<Integer> tagIds,
                                    String keyword) throws SSException {
        Integer count = 0;
        //判断供货商ID是否为空
        Integer supplierPartyId = 0;
        if(Assert.isNotNull(supplierId) && !Assert.lessOrEqualZero(supplierId)){
            Supplier supplier = supplierService.queryById(supplierId);
            if(Assert.isNotNull(supplier)) {
                supplierPartyId = supplier.getPartyId();
            }
        }
        try{
            count = storageSettlementMapper.countStorageItemByDepotAndTag(supplierPartyId,depotIds,tagIds,keyword);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CountStorageSettlementCheckFailed, e);
        }
        return count==null ? 0 : count;
    }

    @Override
    public List<StorageSupplierDto> listSettlementSupplier(Integer supplierId,
                                                           Date startDate,
                                                           Date endDate) throws SSException {
        List<StorageSupplierDto> storageSupplierDtoList = new ArrayList<StorageSupplierDto>();
        try {
            //供货商partyId
            Integer supplierPartyId = 0;
            if(Assert.isNotNull(supplierId) && !Assert.lessOrEqualZero(supplierId)){
                Supplier supplier = supplierService.queryById(supplierId);
                if(Assert.isNotNull(supplier)){
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
            return storageSupplierDtoList;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListStorageSettlementSupplierFailed, e);
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void exportSettlementCheckToExcel(Date startDate,
                                             Date endDate,
                                             Integer supplierId,
                                             List<Integer> depotIds,
                                             List<Integer> tagIds,
                                             String keyword,
                                             HttpServletResponse response) throws SSException{

        OutputStream os = null;
        try {
            //从数据库中获取数据
            List<StorageCheckDto> storageCheckDtoList = this.listSettlementCheck(startDate,endDate,supplierId,depotIds,tagIds,keyword,null,null);
            for(StorageCheckDto storageCheckDto : storageCheckDtoList){
                EntityUtil.setNullFieldDefault(storageCheckDto);
            }
            // 设置输出流
            // 设置excel文件名和sheetName
            String filename = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            filename = ExcelExportTemplateEnums.AdminSettlementCheckList.getName() + sdf.format(new Date());
            response.setContentType("application/msexcel");
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(filename.getBytes("gbk"), "ISO8859-1") + ".xls");
            os = response.getOutputStream();
            int startRow = 3;
            //调用core包里的工具类
            ExcelWriter.writeExcelByTemplate(storageCheckDtoList, startRow, os, ExcelExportTemplateEnums.AdminSettlementCheckList, checkDataTypes);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            response.setContentType("text/html");
            response.setHeader("Content-Type", "text/html");
            response.setHeader("Content-disposition", "");
            response.setCharacterEncoding("UTF-8");
            try {
                String eMsg = "系统内部异常，请联系管理员！";
                eMsg= java.net.URLEncoder.encode(eMsg.toString(),"UTF-8");
                response.sendRedirect("/admin/storage/settlement/check?eMsg="+eMsg);
                os.close();
            } catch (IOException e1) {
                LogClerk.errLog.error(e1.getMessage());
            }
            throw SSException.get(EmenuException.ExportStorageSettlementCheckFailed, e);
        }
        finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    LogClerk.errLog.error(e);
                    throw SSException.get(EmenuException.ExportStorageSettlementCheckFailed, e);
                }
            }
        }
    }

    @Override
    public void exportSettlementSupplierToExcel(Integer supplierId,
                                                Date startDate,
                                                Date endDate,
                                                HttpServletResponse response) throws SSException {
        OutputStream os = null;
        try {
           //从数据库中获取数据
           List<StorageSupplierDto> storageSupplierDtoList = this.listSettlementSupplier(supplierId,startDate,endDate);
           for (StorageSupplierDto storageSupplierDto : storageSupplierDtoList) {
               EntityUtil.setNullFieldDefault(storageSupplierDto);
           }
            // 设置输出流
            // 设置excel文件名和sheetName
            String filename = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            filename = ExcelExportTemplateEnums.AdminSettlementSupplierList.getName() + sdf.format(new Date());
            response.setContentType("application/msexcel");
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(filename.getBytes("gbk"), "ISO8859-1") + ".xls");
            os = response.getOutputStream();
            // 获取模板
            InputStream tplStream = IOUtil.getFileAsStream(ExcelExportTemplateEnums.AdminSettlementSupplierList.getFilePath());
            Workbook tplWorkBook = Workbook.getWorkbook(tplStream);
            WritableWorkbook outBook = Workbook.createWorkbook(os, tplWorkBook);
            // 获取sheet 往sheet中写入数据
            WritableSheet sheet = outBook.getSheet(0);
            int row =2;
            for(StorageSupplierDto storageSupplierDto : storageSupplierDtoList){
                //单元格居中格式
                WritableCellFormat cellFormat=new WritableCellFormat();
                cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
                cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                cellFormat.setWrap(true);
                //供货商名称
                Label labelSupplierName = new Label(0, row, storageSupplierDto.getSupplierName());
                labelSupplierName.setCellFormat(cellFormat);
                sheet.addCell(labelSupplierName);
                //总金额
                Label labelTotalMoney = new Label(6, row, storageSupplierDto.getTotalMoney().toString());
                labelTotalMoney.setCellFormat(cellFormat);
                sheet.addCell(labelTotalMoney);
                //物品列表
                List<StorageItemDto> storageItemDtoList = storageSupplierDto.getStorageItemDtoList();
                int rowchildren = 0;
                for(StorageItemDto storageItemDto : storageItemDtoList){
                    //物品名称
                    Label labelitemName = new Label(1, row+rowchildren, storageItemDto.getItemName());
                    sheet.addCell(labelitemName);
                    //数量
                    Label labelItemQuantity = new Label(2, row+rowchildren, storageItemDto.getItemQuantity().toString());
                    sheet.addCell(labelItemQuantity);
                    //金额
                    Label labelitemMoney = new Label(3, row+rowchildren, storageItemDto.getItemMoney().toString());
                    sheet.addCell(labelitemMoney);
                    //经手人
                    Label labelHandlerName = new Label(4, row+rowchildren, storageItemDto.getHandlerName());
                    sheet.addCell(labelHandlerName);
                    //操作人
                    Label labeCreatedName = new Label(5, row+rowchildren, storageItemDto.getCreatedName());
                    sheet.addCell(labeCreatedName);
                    rowchildren++;
                }
                if(storageItemDtoList.size()>1) {
                    sheet.mergeCells(0, row, 0, row + rowchildren-1);
                    sheet.mergeCells(6, row, 6, row + rowchildren-1);
                }
                if(rowchildren<=1) {
                    row ++;
                }else {
                    row = row + rowchildren;
                }
            }
            outBook.write();
            outBook.close();
            tplWorkBook.close();
            tplStream.close();
            os.close();
       } catch (Exception e) {
           LogClerk.errLog.error(e);
           response.setContentType("text/html");
           response.setHeader("Content-Type", "text/html");
           response.setHeader("Content-disposition", "");
           response.setCharacterEncoding("UTF-8");
           try {
               String eMsg = "系统内部异常，请联系管理员！";
               eMsg= java.net.URLEncoder.encode(eMsg.toString(),"UTF-8");
               response.sendRedirect("/admin/storage/settlement/supplier?eMsg="+eMsg);
               os.close();
           } catch (IOException e1) {
               LogClerk.errLog.error(e1.getMessage());
           }
           throw SSException.get(EmenuException.ExportStorageSettlementCheckFailed, e);
       }
       finally {
           if (os != null) {
               try {
                   os.flush();
                   os.close();
               } catch (Exception e) {
                   LogClerk.errLog.error(e);
                   throw SSException.get(EmenuException.ExportStorageSettlementCheckFailed, e);
               }
           }
       }
    }

    /**
     * 库存盘点
     */
    private DataType[] checkDataTypes = {
            new DataType("tagName", 0),
            new DataType("itemNumber", 1),
            new DataType("itemName", 2),
            new DataType("lastStockInPrice", 3),
            new DataType("storageUnitName", 4),
            new DataType("orderUnitName", 5),
            new DataType("beginQuantity", 6),
            new DataType("beginMoney", 7),
            new DataType("stockInQuantity", 8),
            new DataType("stockInMoney", 9),
            new DataType("stockOutQuantity", 10),
            new DataType("stockOutMoney", 11),
            new DataType("incomeLossQuantity", 12),
            new DataType("incomeLossMoney", 13),
            new DataType("totalQuantity", 14),
            new DataType("totalAveragePrice", 15),
            new DataType("totalMoney", 16),
            new DataType("maxStorageQuantity", 17),
            new DataType("minStorageQuantity", 18),
    };

    /**
     * 根据某个时间之前每个库存物品的库存结果
     * 主要是为了计算期初
     * @param startDate
     * @param depotIds
     * @param tagIds
     * @param keyword
     * @param curPage
     * @param pageSize
     * @return List<StorageSettlementItem>
     * @throws SSException
     */
    private List<StorageSettlementItem> listSettlementItemByDate(Date startDate,
                                                                 Integer supplierId,
                                                                 List<Integer> depotIds,
                                                                 List<Integer> tagIds,
                                                                 String keyword,
                                                                 Integer curPage,
                                                                 Integer pageSize) throws SSException{
        List<StorageSettlementItem> settlementItemList = new ArrayList<StorageSettlementItem>();
        Integer offset = null;
        //判断分页参数是否为空
        if(curPage!=null && pageSize!=null) {
            //处理分页参数
            curPage = curPage <= 0 ? 0 : curPage - 1;
            offset = curPage * pageSize;
            if (Assert.lessZero(offset)) {
                return settlementItemList;
            }
        }
        //判断供货商ID是否为空
        Integer supplierPartyId = 0;
        if(Assert.isNotNull(supplierId) && !Assert.lessOrEqualZero(supplierId)){
            Supplier supplier = supplierService.queryById(supplierId);
            if(Assert.isNotNull(supplier)) {
                supplierPartyId = supplier.getPartyId();
            }
        }
        try {
            //库存物品列表
            List<StorageItem> storageItemList = storageSettlementMapper.listStorageItemByDepotAndTag(supplierPartyId, depotIds, tagIds, keyword, offset, pageSize);
            if (Assert.isEmpty(storageItemList)) {
                storageItemList = Collections.emptyList();
            }
            //获取该时间之前最后一次结算时间(包括当前时间)
            StorageSettlement storageSettlement = storageSettlementMapper.queryLastSettlement(startDate);
            Date settlementDate = null;
            if (Assert.isNotNull(storageSettlement)) {
                settlementDate = storageSettlement.getCreatedTime();
            }
            //参数：开始时间settlementDate，结束时间startDate
            //单据列表——从上一次结算到开始时间之间的单据（不包括开始时间，不包括结束时间）
            List<StorageReportDto> storageReportDtoList = storageReportService.listReportDtoByCondition(settlementDate, startDate, depotIds, tagIds);
            if (Assert.isEmpty(storageReportDtoList)) {
                storageReportDtoList = Collections.emptyList();
            }
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
                        if (storageItem.getId() == storageReportItem.getItemId()) {
                            //入库单
                            if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockInReport.getId())) {
                                stockInQuantity = stockInQuantity.add(storageReportItem.getQuantity());
                                stockInMoney = stockInMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                            }
                            //出库单
                            if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockOutReport.getId())) {
                                stockOutQuantity = stockOutQuantity.add(storageReportItem.getQuantity());
                                stockOutMoney = stockOutMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                            }
                            //盘盈单
                            if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.IncomeOnReport.getId())) {
                                incomeOnQuantity = incomeOnQuantity.add(storageReportItem.getQuantity());
                                incomeOnMoney = incomeOnMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                            }
                            //盘亏单
                            if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.LossOnReport.getId())) {
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
                if (Assert.isNotNull(beforeSettlementItem)) {
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
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListStorageSettlementCheckFailed);
        }
        return settlementItemList;
    }

    /**
     * 根据某个时间之前每个库存物品的库存结果(原配料)
     * 主要是为了计算期初
     * @param startDate
     * @param tagIds
     * @param keyword
     * @param curPage
     * @param pageSize
     * @return List<StorageSettlementItem>
     * @throws SSException
     */
    private List<StorageSettlementIngredient> listSettlementIngredientByDate(Date startDate,
                                                                 List<Integer> tagIds,
                                                                 String keyword,
                                                                 Integer curPage,
                                                                 Integer pageSize) throws SSException{
        List<StorageSettlementIngredient> settlementIngredientList = new ArrayList<StorageSettlementIngredient>();
        Integer offset = null;
        //判断分页参数是否为空
        if(curPage!=null && pageSize!=null) {
            //处理分页参数
            curPage = curPage <= 0 ? 0 : curPage - 1;
            offset = curPage * pageSize;
            if (Assert.lessZero(offset)) {
                return settlementIngredientList;
            }
        }
        try {
            //库存物品列表
            List<StorageItem> storageItemList = storageSettlementMapper.listStorageItemByDepotAndTag(null, null, tagIds, keyword, offset, pageSize);
            //获取所有原配料
            ItemAndIngredientSearchDto searchDto = new ItemAndIngredientSearchDto();
            searchDto.setOffset(offset);
            searchDto.setPageSize(pageSize);
            List<Ingredient> ingredientList = ingredientService.listBySearchDto(searchDto);
            if (Assert.isEmpty(storageItemList)) {
                storageItemList = Collections.emptyList();
            }
            //获取该时间之前最后一次结算时间(包括当前时间)
            StorageSettlement storageSettlement = storageSettlementMapper.queryLastSettlement(startDate);
            Date settlementDate = null;
            if (Assert.isNotNull(storageSettlement)) {
                settlementDate = storageSettlement.getCreatedTime();
            }
            //参数：开始时间settlementDate，结束时间startDate
            //单据列表——从上一次结算到开始时间之间的单据（不包括开始时间，不包括结束时间）
            List<StorageReportDto> storageReportDtoList = storageReportService.listReportDtoByCondition(settlementDate, startDate, null, tagIds);
            if (Assert.isEmpty(storageReportDtoList)) {
                storageReportDtoList = Collections.emptyList();
            }
            for (Ingredient ingredient : ingredientList){
                //入库数量
                BigDecimal ingredientStockInQuantity = new BigDecimal(0.00);
                //入库金额
                BigDecimal ingredientStockInMoney = new BigDecimal(0.00);
                //出库数量
                BigDecimal ingredientStockOutQuantity = new BigDecimal(0.00);
                //出库金额
                BigDecimal ingredientStockOutMoney = new BigDecimal(0.00);
                //盘盈数量
                BigDecimal ingredientIncomeOnQuantity = new BigDecimal(0.00);
                //盘盈金额
                BigDecimal ingredientIncomeOnMoney = new BigDecimal(0.00);
                //盘亏数量
                BigDecimal ingredientLossOnQuantity = new BigDecimal(0.00);
                //盘亏金额
                BigDecimal ingredientLossOnMoney = new BigDecimal(0.00);
                //实际库存数量
                BigDecimal ingredientRealQuantity = new BigDecimal(0.00);
                //实际金额
                BigDecimal ingredientRealMoney = new BigDecimal(0.00);
                //获取之前的总数量和总金额
                BigDecimal ingredientTotalQuantity = new BigDecimal(0.00);
                BigDecimal ingredientTotalMoney = new BigDecimal(0.00);

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
                        //获取该单据下物品详情
                        List<StorageReportItem> storageReportItemList = storageReportDto.getStorageReportItemList();
                        //循环订单下的详情
                        for (StorageReportItem storageReportItem : storageReportItemList) {
                            if (storageItem.getId() == storageReportItem.getItemId()) {
                                //入库单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockInReport.getId())) {
                                    stockInQuantity = stockInQuantity.add(storageReportItem.getQuantity());
                                    stockInMoney = stockInMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                                }
                                //出库单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockOutReport.getId())) {
                                    stockOutQuantity = stockOutQuantity.add(storageReportItem.getQuantity());
                                    stockOutMoney = stockOutMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                                }
                                //盘盈单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockOutReport.getId())) {
                                    incomeOnQuantity = incomeOnQuantity.add(storageReportItem.getQuantity());
                                    incomeOnMoney = incomeOnMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                                }
                                //盘亏单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.LossOnReport.getId())) {
                                    lossOnQuantity = lossOnQuantity.add(storageReportItem.getQuantity());
                                    lossOnMoney = lossOnMoney.add(storageReportItem.getQuantity().multiply(storageReportItem.getPrice()));
                                }
                                StorageItem Item = storageItemService.queryById(storageItem.getId());
                                if (Assert.isNotNull(Item)
                                        && Item.getIngredientId() == ingredient.getId()){
                                    if (storageItem.getCountUnitId() == storageItem.getOrderUnitId()){
                                        //入库数量 这个数量单位是成本卡规格
                                        ingredientStockInQuantity = stockInQuantity.add(storageReportItem.getQuantity().multiply(storageItem.getOrderToStorageRatio()).multiply(storageItem.getStorageToCostCardRatio()));
                                    } else {
                                        ingredientStockInQuantity = stockInQuantity.add(storageReportItem.getQuantity().multiply(storageItem.getStorageToCostCardRatio()));
                                    }
                                    //入库金额
                                    ingredientStockInMoney = ingredientStockInMoney.add(stockInMoney);

                                    //出库数量 这个数量单位是成本卡规格
                                    if (storageItem.getCountUnitId() == storageItem.getOrderUnitId()){

                                        ingredientStockOutQuantity = ingredientStockOutQuantity.add(storageReportItem.getQuantity().multiply(storageItem.getOrderToStorageRatio()).multiply(storageItem.getStorageToCostCardRatio()));
                                    } else {
                                        ingredientStockOutQuantity = ingredientStockOutQuantity.add(storageReportItem.getQuantity().multiply(storageItem.getStorageToCostCardRatio()));
                                    }
                                    //出库金额
                                    ingredientStockOutMoney = ingredientStockOutMoney.add(stockOutMoney);

                                    //盘盈数量 这个数量单位是成本卡规格
                                    if (storageItem.getCountUnitId() == storageItem.getOrderUnitId()){
                                        ingredientIncomeOnQuantity = ingredientIncomeOnQuantity.add(storageReportItem.getQuantity().multiply(storageItem.getOrderToStorageRatio()).multiply(storageItem.getStorageToCostCardRatio()));
                                    } else {
                                        ingredientIncomeOnQuantity = ingredientIncomeOnQuantity.add(storageReportItem.getQuantity().multiply(storageItem.getStorageToCostCardRatio()));
                                    }
                                    //盘盈金额
                                    ingredientIncomeOnMoney = ingredientIncomeOnQuantity.add(incomeOnMoney);

                                    //盘亏数量
                                    if (storageItem.getCountUnitId() == storageItem.getOrderUnitId()){
                                        //入库数量 这个数量单位是成本卡规格
                                        ingredientLossOnQuantity = ingredientIncomeOnQuantity.add(storageReportItem.getQuantity().multiply(storageItem.getOrderToStorageRatio()).multiply(storageItem.getStorageToCostCardRatio()));
                                    } else {
                                        ingredientLossOnQuantity = ingredientIncomeOnQuantity.add(storageReportItem.getQuantity().multiply(storageItem.getStorageToCostCardRatio()));
                                    }
                                    //盘亏金额
                                    ingredientLossOnMoney = ingredientLossOnMoney.add(ingredientLossOnMoney);

                                }
                            }
                        }
                    }
                    //结存
                    if (storageItem.getCountUnitId() == storageItem.getOrderUnitId()){
                        //入库数量 这个数量单位是成本卡规格
                        ingredientRealQuantity = ingredientIncomeOnQuantity.add(realQuantity.multiply(storageItem.getOrderToStorageRatio()).multiply(storageItem.getStorageToCostCardRatio()));
                    } else {
                        ingredientRealQuantity = ingredientIncomeOnQuantity.add(realQuantity.multiply(storageItem.getStorageToCostCardRatio()));
                    }
                    ingredientRealMoney = stockInMoney.subtract(stockOutMoney).add(incomeOnMoney.subtract(lossOnMoney));

                    //取出之前该原配料的总数
                    StorageSettlementIngredient beforeSettlementIngredient = storageSettlementMapper.queryByDateAndIngredientId(startDate, ingredient.getId());
                    if (Assert.isNotNull(beforeSettlementIngredient)) {
                        ingredientTotalQuantity = beforeSettlementIngredient.getTotalQuantity();
                        ingredientTotalMoney = beforeSettlementIngredient.getTotalMoney();
                    }

                    //总数
                    ingredientTotalQuantity = ingredientTotalQuantity.add(realQuantity);
                    ingredientTotalMoney = ingredientTotalMoney.add(realMoney);

                    StorageSettlementIngredient storageSettlementIngredient = new StorageSettlementIngredient();
                    storageSettlementIngredient.setId(ingredient.getId());
                    storageSettlementIngredient.setStockInQuantity(ingredientStockInQuantity);
                    storageSettlementIngredient.setStockInMoney(ingredientStockInMoney);
                    storageSettlementIngredient.setStockOutQuantity(ingredientStockOutMoney);
                    storageSettlementIngredient.setIncomeOnQuantity(ingredientIncomeOnQuantity);
                    storageSettlementIngredient.setIncomeOnMoney(ingredientIncomeOnMoney);
                    storageSettlementIngredient.setLossOnQuantity(ingredientLossOnQuantity);
                    storageSettlementIngredient.setLossOnMoney(ingredientLossOnMoney);
                    storageSettlementIngredient.setRealQuantity(ingredientRealQuantity);
                    storageSettlementIngredient.setRealMoney(ingredientRealMoney);
                    storageSettlementIngredient.setTotalQuantity(ingredientTotalQuantity);
                    storageSettlementIngredient.setTotalMoney(ingredientTotalMoney);
                    //加到List里面
                    settlementIngredientList.add(storageSettlementIngredient);
                }
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListStorageSettlementCheckFailed);
        }
        return settlementIngredientList;
    }

    public void test() {
        System.out.println("test quartz");
    }
}
