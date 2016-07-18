package com.emenu.service.storage.impl;

import com.emenu.common.dto.dish.CostCardItemDto;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishPackageDto;
import com.emenu.common.dto.dish.tag.TagDto;
import com.emenu.common.dto.order.CheckOrderDto;
import com.emenu.common.dto.storage.*;
import com.emenu.common.entity.dish.CostCard;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.dish.DishPackage;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
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
import com.emenu.service.dish.*;
import com.emenu.service.dish.tag.TagFacadeService;
import com.emenu.service.order.OrderService;
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

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

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

    @Autowired
    private OrderService orderService;

    @Autowired
    private DishService dishService;

    @Autowired
    private CostCardService costCardService;

    @Autowired
    private CostCardItemService costCardItemService;

    @Autowired
    private DishPackageService dishPackageService;

    // 盘点缓存下单时原配料是否还有剩余
    private Map<Integer, BigDecimal> settlementCache = new ConcurrentHashMap<Integer, BigDecimal>();

    /**
     * 初始化缓存
     * @PostConstruct
     * @throws Exception
     */

    public void initCache() throws SSException {
        // 新
        // 库存盘点（单据的一次结算）= 已下单的未结账的单据.未盘点已经结账后的订单中的菜品（转换成原配料）+ 单据（审核通过、未结算）中的原配料（入库单特殊处理）
        try {
            // 第二步取出已经审核通过和未结算的单据
            Date nowTime = new Date();
            List<StorageReportDto> storageReportDtoList = storageReportService.listUnsettleAndAuditedStorageReportByEndTime(nowTime);
            // 第三步获取未结算（盘点）的订单当前时间之前
            List<CheckOrderDto> orderIdDtolist = orderService.listCheckOrderDtoForCheck(null,0,nowTime);
            // 获取所有库存原配料
            List<Ingredient> ingredientList = ingredientService.listAll();
            // 循环库存原配料
            for (Ingredient ingredient : ingredientList) {
                //入库数量
                BigDecimal stockInQuantity = new BigDecimal(0.00);
                //出库数量
                BigDecimal stockOutQuantity = new BigDecimal(0.00);
                //盘盈数量
                BigDecimal incomeOnQuantity = new BigDecimal(0.00);
                //盘亏数量
                BigDecimal lossOnQuantity = new BigDecimal(0.00);
                //实际库存数量
                BigDecimal realQuantity = new BigDecimal(0.00);
                //获取之前的总数量
                BigDecimal totalQuantity = new BigDecimal(0.00);
                //循环单据
                for (StorageReportDto storageReportDto : storageReportDtoList) {
                    if (storageReportDto.getStorageReport().getType() != StorageReportTypeEnum.StockInReport.getId()) {
                        //获取该单据下物品详情
                        List<StorageReportIngredient> storageReportIngredientList = storageReportDto.getStorageReportIngredientList();
                        //循环订单下的详情
                        for (StorageReportIngredient storageReportIngredient : storageReportIngredientList) {
                            if (ingredient.getId() == storageReportIngredient.getIngredientId()) {
                                //出库单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockOutReport.getId())) {
                                    stockOutQuantity = stockOutQuantity.add(storageReportIngredient.getQuantity());
                                }
                                //盘盈单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockOutReport.getId())) {
                                    incomeOnQuantity = incomeOnQuantity.add(storageReportIngredient.getQuantity());
                                }
                                //盘亏单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.LossOnReport.getId())) {
                                    lossOnQuantity = lossOnQuantity.add(storageReportIngredient.getQuantity());
                                }
                            }
                        }
                    } else {
                        //获取该单据下物品详情
                        List<StorageReportItem> storageReportItemList = storageReportDto.getStorageReportItemList();
                        //循环订单下的详情
                        for (StorageReportItem storageReportItem : storageReportItemList) {
                            StorageItem storageItem = storageItemService.queryById(storageReportItem.getItemId());
                            if (ingredient.getId() == storageItem.getIngredientId()) {
                                //入库单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockInReport.getId())) {
                                    stockInQuantity = stockInQuantity.add(storageReportItem.getQuantity().multiply(storageItem.getOrderToStorageRatio()).multiply(storageItem.getStorageToCostCardRatio()));
                                }
                            }
                        }
                    }
                    // 订单--菜品--成本卡--原配料(出库)
                    for (CheckOrderDto orderDto : orderIdDtolist) {
                        List<OrderDish> orderDishList = orderDto.getOrderDishs();
                        for (OrderDish orderDish : orderDishList) {
                            // 订单中的菜品
                            if (orderDish.getIsPackage() == 0) {
                                CostCard costCard = costCardService.queryCostCardByDishId(orderDish.getDishId());
                                List<CostCardItemDto> costCardItemDtoList = costCardItemService.listByCostCardId(costCard.getId());
                                for (CostCardItemDto costCardItemDto : costCardItemDtoList) {
                                    if (costCardItemDto.getIsAutoOut() == 1) {
                                        if (costCardItemDto.getIngredientId() == ingredient.getId()) {
                                            stockOutQuantity.add(costCardItemDto.getNetCount().multiply(new BigDecimal(orderDish.getDishQuantity())));
                                        }
                                    }
                                }
                            } else {
                                // 套餐中包含菜品单独处理
                                DishPackageDto dishPackageDto = dishPackageService.queryDishPackageById(orderDish.getDishId());
                                List<DishDto> childDishDtoList = dishPackageDto.getChildDishDtoList();
                                for (DishDto dishDto : childDishDtoList) {
                                    CostCard costCard = costCardService.queryCostCardByDishId(dishDto.getId());
                                    List<CostCardItemDto> costCardItemDtoList = costCardItemService.listByCostCardId(costCard.getId());
                                    for (CostCardItemDto costCardItemDto : costCardItemDtoList) {
                                        if (costCardItemDto.getIsAutoOut() == 1) {
                                            if (costCardItemDto.getIngredientId() == ingredient.getId()) {
                                                stockOutQuantity.add(costCardItemDto.getNetCount().multiply(new BigDecimal(orderDish.getDishQuantity())));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // 计算库存实际编号和剩余库存
                    realQuantity = stockInQuantity.subtract(stockOutQuantity).add(incomeOnQuantity.subtract(lossOnQuantity));
                    StorageSettlement storageSettlementLast = storageSettlementMapper.queryLastSettlement(nowTime);
                    StorageSettlementIngredient beforeSettlementIngredient = storageSettlementMapper.queryBySettlementIdAndIngredientId(storageSettlementLast.getId(), ingredient.getId());
                    if (Assert.isNotNull(beforeSettlementIngredient)) {
                        totalQuantity = beforeSettlementIngredient.getTotalQuantity();
                    }
                    //总数
                    totalQuantity = totalQuantity.add(realQuantity);
                    // 存入缓存
                    settlementCache.put(ingredient.getId(),totalQuantity);
                }
            }
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InitSettlementCacheFail, e);
        }
    }

    public void updateSettlementCache(Integer key, BigDecimal quantity) throws SSException {
        try {
            if (Assert.isNull(key)){
                throw SSException.get(EmenuException.SettlementCacheKeyError);
            }
            if (Assert.isNull(quantity)
                    || quantity.compareTo(BigDecimal.ZERO) == -1){
                throw SSException.get(EmenuException.QuantityError);
            }
            settlementCache.put(key,quantity);
        } catch (Exception e) {
          LogClerk.errLog.error(e);
          throw SSException.get(EmenuException.UpdateSettlementCacheFail, e);
        }
    }

    public BigDecimal queryCache(Integer key) throws SSException {
        try {
            if (Assert.isNull(key)){
                throw SSException.get(EmenuException.SettlementCacheKeyError);
            }
            BigDecimal quantity = settlementCache.get(key);
            if (Assert.isNull(quantity)
                    || quantity.compareTo(BigDecimal.ZERO) == -1){
                throw SSException.get(EmenuException.QuantityError);
            }
            return quantity;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateSettlementCacheFail, e);
        }
    }


    @Override
    @Transactional(rollbackFor = {Exception.class,RuntimeException.class,SSException.class},propagation = Propagation.REQUIRED)
    public void newSettlement() throws SSException {
        // 新
        // 库存盘点（订单、单据的一次结算）= 未盘点已经结账后的订单中的菜品（转换成原配料）+ 单据（审核通过、未结算）中的原配料（入库单特殊处理）
        try {
            // 第一步：添加一条结算数据:t_storage_settlement
            StorageSettlement settlement = new StorageSettlement();
            settlement.setSerialNumber(serialNumService.generateSerialNum(SerialNumTemplateEnums.SettlementSerialNum));

            // 第二步取出已经审核通过且未结算的单据
            Date nowTime = new Date();
            List<StorageReportDto> storageReportDtoList = storageReportService.listUnsettleAndAuditedStorageReportByEndTime(nowTime);
            // 第三步获取已经结账未结算（盘点）的订单
            List<CheckOrderDto> orderIdDtolist = orderService.listCheckOrderDtoForCheck(2,0,nowTime);
            Integer settlementId = 0;
            if ((Assert.isNull(storageReportDtoList) || Assert.lessOrEqualZero(storageReportDtoList.size()))
            && (Assert.isNull(orderIdDtolist) || Assert.lessOrEqualZero(orderIdDtolist.size()))){
                return;
            } else {
                settlementId = commonDao.insert(settlement).getId();
            }

            // 获取所有库存原配料
            List<Ingredient> ingredientList = ingredientService.listAll();
            // 循环库存原配料
            for (Ingredient ingredient : ingredientList) {
                //入库数量
                BigDecimal stockInQuantity = new BigDecimal(0.00);
                //出库数量
                BigDecimal stockOutQuantity = new BigDecimal(0.00);
                //盘盈数量
                BigDecimal incomeOnQuantity = new BigDecimal(0.00);
                //盘亏数量
                BigDecimal lossOnQuantity = new BigDecimal(0.00);
                //实际库存数量(入库-出库+盘盈-盘亏)
                BigDecimal realQuantity = new BigDecimal(0.00);
                //获取之前的总数量
                BigDecimal totalQuantity = new BigDecimal(0.00);
                //循环单据
                for (StorageReportDto storageReportDto : storageReportDtoList) {
                    if (storageReportDto.getStorageReport().getType() != StorageReportTypeEnum.StockInReport.getId()) {
                        //非入库单获取该单据下原配料详情
                        List<StorageReportIngredient> storageReportIngredientList = storageReportDto.getStorageReportIngredientList();
                        //循环订单下的详情
                        for (StorageReportIngredient storageReportIngredient : storageReportIngredientList) {
                            if (ingredient.getId() == storageReportIngredient.getIngredientId()) {
                                //出库单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockOutReport.getId())) {
                                    stockOutQuantity = stockOutQuantity.add(storageReportIngredient.getQuantity());
                                }
                                //盘盈单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockOutReport.getId())) {
                                    incomeOnQuantity = incomeOnQuantity.add(storageReportIngredient.getQuantity());
                                }
                                //盘亏单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.LossOnReport.getId())) {
                                    lossOnQuantity = lossOnQuantity.add(storageReportIngredient.getQuantity());
                                }
                            }
                        }
                    } else {
                        //获取该单据下物品详情
                        List<StorageReportItem> storageReportItemList = storageReportDto.getStorageReportItemList();
                        //循环订单下的详情
                        for (StorageReportItem storageReportItem : storageReportItemList) {
                            StorageItem storageItem = storageItemService.queryById(storageReportItem.getItemId());
                            if (ingredient.getId() == storageItem.getIngredientId()) {
                                //入库单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockInReport.getId())) {
                                    stockInQuantity = stockInQuantity.add(storageReportItem.getQuantity().multiply(storageItem.getOrderToStorageRatio()).multiply(storageItem.getStorageToCostCardRatio()));
                                }
                            }
                        }
                    }

                    //第三步：把结算过的单据状态改为已结算
                    storageReportService.updateIsSettlementedById(storageReportDto.getStorageReport().getId(), StorageReportStatusEnum.Settled);

                    // 订单--菜品--成本卡--原配料(出库)
                    for (CheckOrderDto orderDto : orderIdDtolist) {
                        List<OrderDish> orderDishList = orderDto.getOrderDishs();
                        for (OrderDish orderDish : orderDishList) {
                            // 订单中的菜品
                            if (orderDish.getIsPackage() == 0) {
                                // 获取菜品成本卡
                                CostCard costCard = costCardService.queryCostCardByDishId(orderDish.getDishId());
                                // 根据成卡中的原配料和菜品数量计算出库数量
                                List<CostCardItemDto> costCardItemDtoList = costCardItemService.listByCostCardId(costCard.getId());
                                for (CostCardItemDto costCardItemDto : costCardItemDtoList) {
                                    // 判断是否自动出库
                                    if (costCardItemDto.getIsAutoOut() == 1) {
                                        if (costCardItemDto.getIngredientId() == ingredient.getId()) {
                                            stockOutQuantity.add(costCardItemDto.getNetCount().multiply(new BigDecimal(orderDish.getDishQuantity())));
                                        }
                                    }
                                }
                            } else {
                                // 套餐中包含菜品单独处理
                                DishPackageDto dishPackageDto = dishPackageService.queryDishPackageById(orderDish.getDishId());
                                // 获取套餐中的菜品
                                List<DishDto> childDishDtoList = dishPackageDto.getChildDishDtoList();
                                for (DishDto dishDto : childDishDtoList) {
                                    CostCard costCard = costCardService.queryCostCardByDishId(dishDto.getId());
                                    List<CostCardItemDto> costCardItemDtoList = costCardItemService.listByCostCardId(costCard.getId());
                                    for (CostCardItemDto costCardItemDto : costCardItemDtoList) {
                                        if (costCardItemDto.getIsAutoOut() == 1) {
                                            if (costCardItemDto.getIngredientId() == ingredient.getId()) {
                                                stockOutQuantity.add(costCardItemDto.getNetCount().multiply(new BigDecimal(orderDish.getDishQuantity())));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        // 修改订单状态为已盘点
                        orderService.updateOrderIsSettlementedById(orderDto.getOrder().getId(), 1);
                    }
                    // 计算库存实际编号和剩余库存
                    realQuantity = stockInQuantity.subtract(stockOutQuantity).add(incomeOnQuantity.subtract(lossOnQuantity));
                    StorageSettlement storageSettlementLast = storageSettlementMapper.queryLastSettlement(nowTime);
                    StorageSettlementIngredient beforeSettlementIngredient = storageSettlementMapper.queryBySettlementIdAndIngredientId(storageSettlementLast.getId(), ingredient.getId());
                    if (Assert.isNotNull(beforeSettlementIngredient)) {
                        totalQuantity = beforeSettlementIngredient.getTotalQuantity();
                    }
                    //总数
                    totalQuantity = totalQuantity.add(realQuantity);
                    //将库存的每个物品的结算情况存到数据库
                    StorageSettlementIngredient settlementIngredientNow = new StorageSettlementIngredient();
                    settlementIngredientNow.setIngredientId(ingredient.getId());
                    settlementIngredientNow.setStockInQuantity(stockInQuantity);
                    settlementIngredientNow.setStockOutQuantity(stockOutQuantity);
                    settlementIngredientNow.setIncomeOnQuantity(incomeOnQuantity);
                    settlementIngredientNow.setLossOnQuantity(lossOnQuantity);
                    settlementIngredientNow.setRealQuantity(realQuantity);
                    settlementIngredientNow.setTotalQuantity(totalQuantity);
                    settlementIngredientNow.setSettlementId(settlementId);
                    // 存入数据库
                    commonDao.insert(settlementIngredientNow);
                }
            }
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertStorageSettlementItemFailed, e);
        }
    }

    /**
     * 获取盘点信息
     * @param startTime
     * @param endTime
     * @param tagIds
     * @param curPage
     * @param pageSize
     * @return
     * @throws SSException
     */
    public List<StorageCheckDto>  listSettlementCheck(Date startTime,
                                                      Date endTime,
                                                      List<Integer> tagIds,
                                                      String keyword,
                                                      Integer curPage,
                                                      Integer pageSize) throws SSException {
        List<StorageCheckDto> storageCheckDtoList = new ArrayList<StorageCheckDto>();
        try {

            //取出时间段之间的所有已经审核通过的单据（结算和未结算都取出，包括startTime和endTime）
            List<StorageReportDto> storageReportDtoList = storageReportService.listReportDtoByTimeAndIsAudited(startTime, endTime,1);
            if (Assert.isEmpty(storageReportDtoList)) {
                storageReportDtoList = Collections.emptyList();
            }
            //取出开始时间之前的所有库存物品计算结果（不包括开始时间）、最近一次的结算结果+未结算单据的结果
            List<StorageSettlementIngredient> beforeSettlementList = listSettlementIngredientByDate(startTime, tagIds, keyword, curPage, pageSize);
            if (Assert.isEmpty(beforeSettlementList)) {
                beforeSettlementList = Collections.emptyList();
            }
            Date settlementDate = null;
            StorageSettlement storageSettlementLast = storageSettlementMapper.queryLastSettlement(startTime);
            if (Assert.isNotNull(storageSettlementLast)){
                settlementDate = storageSettlementLast.getCreatedTime();
            }
            // 获取时间段之间所有订单，包括开始和结束时间
            List<CheckOrderDto> checkOrderDtoList = orderService.queryOrderByTimePeroid2(settlementDate,startTime);
            for (StorageSettlementIngredient settlementIngredient : beforeSettlementList) {
                //期初数量
                BigDecimal beginQuantity = new BigDecimal(0.00);
                //入库数量
                BigDecimal stockInQuantity = new BigDecimal(0.00);
                //出库数量
                BigDecimal stockOutQuantity = new BigDecimal(0.00);
                //盘盈数量
                BigDecimal incomeOnQuantity = new BigDecimal(0.00);
                //盘亏数量
                BigDecimal lossOnQuantity = new BigDecimal(0.00);
                //实际数量
                BigDecimal realQuantity = new BigDecimal(0.00);
                //实际金额
                BigDecimal realMoney = new BigDecimal(0.00);
                //结存
                BigDecimal totalQuantity = new BigDecimal(0.00);
                //循环单据
                for (StorageReportDto storageReportDto : storageReportDtoList) {
                    if (storageReportDto.getStorageReport().getType() != StorageReportTypeEnum.StockInReport.getId()) {
                        //获取该单据下物品详情
                        List<StorageReportIngredient> storageReportIngredientList = storageReportDto.getStorageReportIngredientList();
                        //循环订单下的详情
                        for (StorageReportIngredient storageReportIngredient : storageReportIngredientList) {
                            if (settlementIngredient.getIngredientId() == storageReportIngredient.getIngredientId()) {
                                //出库单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockOutReport.getId())) {
                                    stockOutQuantity = stockOutQuantity.add(storageReportIngredient.getQuantity());
                                }
                                //盘盈单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockOutReport.getId())) {
                                    incomeOnQuantity = incomeOnQuantity.add(storageReportIngredient.getQuantity());
                                }
                                //盘亏单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.LossOnReport.getId())) {
                                    lossOnQuantity = lossOnQuantity.add(storageReportIngredient.getQuantity());
                                }
                            }
                        }
                    } else {
                        //获取该单据下物品详情
                        List<StorageReportItem> storageReportItemList = storageReportDto.getStorageReportItemList();
                        //循环订单下的详情
                        for (StorageReportItem storageReportItem : storageReportItemList) {
                            StorageItem storageItem = storageItemService.queryById(storageReportItem.getItemId());
                            if (settlementIngredient.getIngredientId() == storageItem.getIngredientId()) {
                                //入库单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockInReport.getId())) {
                                    stockInQuantity = stockInQuantity.add(storageReportItem.getQuantity().multiply(storageItem.getOrderToStorageRatio()).multiply(storageItem.getStorageToCostCardRatio()));
                                }
                            }
                        }
                    }
                }
                // 订单--菜品--成本卡--原配料(出库)
                for (CheckOrderDto checkOrderDto : checkOrderDtoList) {
                    List<OrderDish> orderDishList = checkOrderDto.getOrderDishs();
                    for (OrderDish orderDish : orderDishList) {
                        // 订单中的菜品
                        if (orderDish.getIsPackage() == 0) {
                            CostCard costCard = costCardService.queryCostCardByDishId(orderDish.getDishId());
                            List<CostCardItemDto> costCardItemDtoList = costCardItemService.listByCostCardId(costCard.getId());
                            for (CostCardItemDto costCardItemDto : costCardItemDtoList) {
                                if (costCardItemDto.getIsAutoOut() == 1) {
                                    if (costCardItemDto.getIngredientId() == settlementIngredient.getIngredientId()) {
                                        stockOutQuantity.add(costCardItemDto.getNetCount().multiply(new BigDecimal(orderDish.getDishQuantity())));
                                    }
                                }
                            }
                        } else {
                            // 套餐中包含菜品单独处理
                            DishPackageDto dishPackageDto = dishPackageService.queryDishPackageById(orderDish.getDishId());
                            List<DishDto> childDishDtoList = dishPackageDto.getChildDishDtoList();
                            for (DishDto dishDto : childDishDtoList) {
                                CostCard costCard = costCardService.queryCostCardByDishId(dishDto.getId());
                                List<CostCardItemDto> costCardItemDtoList = costCardItemService.listByCostCardId(costCard.getId());
                                for (CostCardItemDto costCardItemDto : costCardItemDtoList) {
                                    if (costCardItemDto.getIsAutoOut() == 1) {
                                        if (costCardItemDto.getIngredientId() == settlementIngredient.getIngredientId()) {
                                            stockOutQuantity.add(costCardItemDto.getNetCount().multiply(new BigDecimal(orderDish.getDishQuantity())));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                // 实际数量
                realQuantity = stockInQuantity.subtract(stockOutQuantity).add(incomeOnQuantity.subtract(lossOnQuantity));
                // 期初
                beginQuantity = settlementIngredient.getTotalQuantity();
                // 结存
                totalQuantity = totalQuantity.add(realQuantity);
                // 将库存的每个物品的结算情况存到数据库
                StorageCheckDto storageCheckDto = new StorageCheckDto();
                // 获取原配料属性
                Ingredient ingredient = ingredientService.queryById(settlementIngredient.getIngredientId());
                if (Assert.isNull(ingredient)) {
                    throw SSException.get(EmenuException.IngredientQueryFailed);
                }
                storageCheckDto.setIngredientName(ingredient.getName());
                storageCheckDto.setIngredientNumber(ingredient.getIngredientNumber());
                storageCheckDto.setOrderUnitName(ingredient.getOrderUnitName());
                storageCheckDto.setStorageUnitName(ingredient.getStorageUnitName());
                storageCheckDto.setTagName(ingredient.getCostCardUnitName());
                storageCheckDto.setBeginQuantity(beginQuantity);
                storageCheckDto.setStockInQuantity(stockInQuantity);
                storageCheckDto.setStockOutQuantity(stockOutQuantity);
                storageCheckDto.setIncomeLossQuantity(incomeOnQuantity.subtract(lossOnQuantity));
                storageCheckDto.setTotalQuantity(totalQuantity);
                if (totalQuantity.compareTo(BigDecimal.ZERO) == 0) {
                    storageCheckDto.setTotalAveragePrice(BigDecimal.ZERO);
                } else {
                    storageCheckDto.setTotalAveragePrice(ingredient.getAveragePrice());
                }
                storageCheckDto.setMaxStorageQuantity(ingredient.getMaxStorageQuantity());
                storageCheckDto.setMinStorageQuantity(ingredient.getMinStorageQuantity());
                storageCheckDtoList.add(storageCheckDto);
            }
            return storageCheckDtoList;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListStorageSettlementCheckFailed, e);
        }
    }


    public int countSettlementCheck(List<Integer> tagIds,
                                    String keyword) throws SSException {
        Integer count = 0;
        try{
            ItemAndIngredientSearchDto searchDto = new ItemAndIngredientSearchDto();
            searchDto.setKeyword(keyword);
            searchDto.setTagIdList(tagIds);
            count = ingredientService.countBySearchDto(searchDto);
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
                    //存放每个供货商供应的物品
                    List<StorageItemDto> childItemDtoList = new ArrayList<StorageItemDto>();
                    //总金额
                    BigDecimal totalMoney = new BigDecimal(0.00);
                    for (StorageItemDto storageItemDto : storageItemDtoList) {
                        if (storageItemDto.getSupplierPartyId() == supplier1.getPartyId()) {
                            // 每个供货商供应的物品
                            childItemDtoList.add(storageItemDto);
                            // 每个供货商总金额
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
            //List<StorageCheckDto> storageCheckDtoList = this.listSettlementCheck(startDate,endDate,supplierId,depotIds,tagIds,keyword,null,null);
            List<StorageCheckDto> storageCheckDtoList = null;
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
     * 新
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
            // 获取所有原配料
            // 设置查询条件
            ItemAndIngredientSearchDto searchDto = new ItemAndIngredientSearchDto();
            searchDto.setOffset(offset);
            searchDto.setPageSize(pageSize);
            searchDto.setTagIdList(tagIds);
            searchDto.setKeyword(keyword);
            List<Ingredient> ingredientList = ingredientService.listBySearchDto(searchDto);
            //获取该时间之前最后一次结算时间(包括当前时间)
            StorageSettlement storageSettlement = storageSettlementMapper.queryLastSettlement(startDate);
            Date settlementDate = null;
            if (Assert.isNotNull(storageSettlement)) {
                settlementDate = storageSettlement.getCreatedTime();
            }
            //参数：开始时间settlementDate，结束时间startDate
            //单据列表——从上一次结算到开始时间之间的单据（不包括开始时间，不包括结束时间）
            List<StorageReportDto> storageReportDtoList = storageReportService.listReportDtoByTime(settlementDate,startDate);
            if (Assert.isEmpty(storageReportDtoList)) {
                storageReportDtoList = Collections.emptyList();
            }
            // 从上一次结算到开始时间之间的订单（不包括开始时间，不包括结束时间）
            List<CheckOrderDto> orderIdDtolist = orderService.queryOrderByTimePeroid2(settlementDate,startDate);
            if (Assert.isEmpty(orderIdDtolist)) {
                orderIdDtolist = Collections.emptyList();
            }
            // 统计原配料库存情况
            for (Ingredient ingredient : ingredientList){
                //入库数量
                BigDecimal stockInQuantity = new BigDecimal(0.00);
                //出库数量
                BigDecimal stockOutQuantity = new BigDecimal(0.00);
                //盘盈数量
                BigDecimal incomeOnQuantity = new BigDecimal(0.00);
                //盘亏数量
                BigDecimal lossOnQuantity = new BigDecimal(0.00);
                //实际库存数量
                BigDecimal realQuantity = new BigDecimal(0.00);
                //获取之前的总数量
                BigDecimal totalQuantity = new BigDecimal(0.00);
                //循环单据
                for (StorageReportDto storageReportDto : storageReportDtoList) {
                    if (storageReportDto.getStorageReport().getType() != StorageReportTypeEnum.StockInReport.getId()) {
                        //获取该单据下物品详情
                        List<StorageReportIngredient> storageReportIngredientList = storageReportDto.getStorageReportIngredientList();
                        //循环订单下的详情
                        for (StorageReportIngredient storageReportIngredient : storageReportIngredientList) {
                            if (ingredient.getId() == storageReportIngredient.getIngredientId()) {
                                //出库单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockOutReport.getId())) {
                                    stockOutQuantity = stockOutQuantity.add(storageReportIngredient.getQuantity());
                                }
                                //盘盈单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockOutReport.getId())) {
                                    incomeOnQuantity = incomeOnQuantity.add(storageReportIngredient.getQuantity());
                                }
                                //盘亏单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.LossOnReport.getId())) {
                                    lossOnQuantity = lossOnQuantity.add(storageReportIngredient.getQuantity());
                                }
                            }
                        }
                    } else {
                        //获取该单据下物品详情
                        List<StorageReportItem> storageReportItemList = storageReportDto.getStorageReportItemList();
                        //循环订单下的详情
                        for (StorageReportItem storageReportItem : storageReportItemList) {
                            StorageItem storageItem = storageItemService.queryById(storageReportItem.getItemId());
                            if (ingredient.getId() == storageItem.getIngredientId()) {
                                //入库单
                                if (storageReportDto.getStorageReport().getType().equals(StorageReportTypeEnum.StockInReport.getId())) {
                                    stockInQuantity = stockInQuantity.add(storageReportItem.getQuantity().multiply(storageItem.getOrderToStorageRatio()).multiply(storageItem.getStorageToCostCardRatio()));
                                }
                            }
                        }
                    }

                    // 订单--菜品--成本卡--原配料(出库)
                    for (CheckOrderDto orderDto : orderIdDtolist) {
                        List<OrderDish> orderDishList = orderDto.getOrderDishs();
                        for (OrderDish orderDish : orderDishList) {
                            // 订单中的菜品
                            if (orderDish.getIsPackage() == 0) {
                                CostCard costCard = costCardService.queryCostCardByDishId(orderDish.getDishId());
                                List<CostCardItemDto> costCardItemDtoList = costCardItemService.listByCostCardId(costCard.getId());
                                for (CostCardItemDto costCardItemDto : costCardItemDtoList) {
                                    if (costCardItemDto.getIsAutoOut() == 1) {
                                        if (costCardItemDto.getIngredientId() == ingredient.getId()) {
                                            stockOutQuantity.add(costCardItemDto.getNetCount().multiply(new BigDecimal(orderDish.getDishQuantity())));
                                        }
                                    }
                                }
                            } else {
                                // 套餐中包含菜品单独处理
                                DishPackageDto dishPackageDto = dishPackageService.queryDishPackageById(orderDish.getDishId());
                                List<DishDto> childDishDtoList = dishPackageDto.getChildDishDtoList();
                                for (DishDto dishDto : childDishDtoList) {
                                    CostCard costCard = costCardService.queryCostCardByDishId(dishDto.getId());
                                    List<CostCardItemDto> costCardItemDtoList = costCardItemService.listByCostCardId(costCard.getId());
                                    for (CostCardItemDto costCardItemDto : costCardItemDtoList) {
                                        if (costCardItemDto.getIsAutoOut() == 1) {
                                            if (costCardItemDto.getIngredientId() == ingredient.getId()) {
                                                stockOutQuantity.add(costCardItemDto.getNetCount().multiply(new BigDecimal(orderDish.getDishQuantity())));
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                    // 计算库存实际编号和剩余库存
                    realQuantity = stockInQuantity.subtract(stockOutQuantity).add(incomeOnQuantity.subtract(lossOnQuantity));
                    Integer settlementId = null;
                    if (Assert.isNotNull(storageSettlement)){
                       settlementId = storageSettlement.getId();
                    }
                    StorageSettlementIngredient beforeSettlementIngredient = storageSettlementMapper.queryBySettlementIdAndIngredientId(settlementId, ingredient.getId());
                    if (Assert.isNotNull(beforeSettlementIngredient)) {
                        totalQuantity = beforeSettlementIngredient.getTotalQuantity();
                    }
                    //总数
                    totalQuantity = totalQuantity.add(realQuantity);
                    //将库存的每个物品的结算情况存到数据库
                    StorageSettlementIngredient storageSettlementIngredient = new StorageSettlementIngredient();
                    storageSettlementIngredient.setIngredientId(ingredient.getId());
                    storageSettlementIngredient.setStockInQuantity(stockInQuantity);
                    storageSettlementIngredient.setStockOutQuantity(stockOutQuantity);
                    storageSettlementIngredient.setIncomeOnQuantity(incomeOnQuantity);
                    storageSettlementIngredient.setLossOnQuantity(lossOnQuantity);
                    storageSettlementIngredient.setRealQuantity(realQuantity);
                    storageSettlementIngredient.setTotalQuantity(totalQuantity);
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
