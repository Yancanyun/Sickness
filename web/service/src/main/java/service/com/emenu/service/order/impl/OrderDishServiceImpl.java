package com.emenu.service.order.impl;

import com.emenu.common.cache.order.OrderDishCache;
import com.emenu.common.cache.order.TableOrderCache;
import com.emenu.common.dto.dish.CostCardItemDto;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishPackageDto;
import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.entity.dish.CostCard;
import com.emenu.common.entity.dish.DishPackage;
import com.emenu.common.entity.order.BackDish;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.entity.storage.Ingredient;
import com.emenu.common.enums.dish.PackageStatusEnums;
import com.emenu.common.enums.order.OrderDishCallStatusEnums;
import com.emenu.common.enums.order.OrderDishStatusEnums;
import com.emenu.common.enums.order.OrderStatusEnums;
import com.emenu.common.enums.order.ServeTypeEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.order.OrderDishMapper;
import com.emenu.service.dish.CostCardItemService;
import com.emenu.service.dish.CostCardService;
import com.emenu.service.dish.DishPackageService;
import com.emenu.service.dish.DishService;
import com.emenu.service.order.BackDishService;
import com.emenu.service.order.OrderDishService;
import com.emenu.service.order.OrderService;
import com.emenu.service.storage.IngredientService;
import com.emenu.service.storage.StorageSettlementService;
import com.pandawork.core.common.exception.IBizExceptionMes;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

/**
 * OrderDishServiceImpl
 *
 * @author xubaorong
 * @date 2016/6/3.
 */
@Service("orderDishService")
public class OrderDishServiceImpl implements OrderDishService{

    @Autowired
    private OrderDishMapper orderDishMapper;

    @Autowired
    private OrderService orderService;

    @Autowired
    private BackDishService backDishService;

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private DishPackageService dishPackageService;

    @Autowired
    private CostCardService costCardService;

    @Autowired
    private CostCardItemService costCardItemService;

    @Autowired
    private IngredientService ingredientService;

    @Autowired DishService dishService;

    @Autowired
    private StorageSettlementService storageSettlementService;

    @Override
    public List<OrderDishDto> listDtoByOrderId(int orderId) throws SSException {
        List<OrderDishDto> orderDishDtoList = Collections.emptyList();
        try{
            Assert.lessOrEqualZero(orderId, EmenuException.OrderIdError);
            orderDishDtoList = orderDishMapper.listDtoByOrderId(orderId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListOrderDishByOrderIdFailed,e);
        }
        return orderDishDtoList;
    }

    @Override
    public List<OrderDish> listByOrderId(int orderId) throws SSException {
        List<OrderDish> orderDishList = Collections.emptyList();
        try{
            Assert.lessOrEqualZero(orderId, EmenuException.OrderIdError);
            orderDishList = orderDishMapper.listByOrderId(orderId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListOrderDishByOrderIdFailed,e);
        }
        return orderDishList;
    }

    @Override
    public OrderDishDto queryDtoById(int id) throws SSException {
        OrderDishDto orderDishDto = null;
        try{
            Assert.lessOrEqualZero(id,EmenuException.OrderDishIdError);
            orderDishDto = orderDishMapper.queryDtoById(id);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryOrderDishByIdFailed,e);
        }
        return orderDishDto;
    }

    @Override
    public OrderDish queryById(int id) throws SSException {
        OrderDish orderDish = null;
        try{
            Assert.lessOrEqualZero(id,EmenuException.OrderDishIdError);
            orderDish = orderDishMapper.queryById(id);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryOrderDishByIdFailed,e);
        }
        return orderDish;
    }

    @Override
    public void updateDishStatus(int id, int status) throws SSException {
        try{
            Assert.lessOrEqualZero(id,EmenuException.OrderDishIdError);
            Assert.lessOrEqualZero(status,EmenuException.OrderDishStatusError);
            if(status==1){
                status = OrderStatusEnums.IsBooked.getId();
            }
            if(status==2){
                status = OrderStatusEnums.IsCheckouted.getId();
            }
            orderDishMapper.updateDishStatus(id, status);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateDishStatusFailed,e);
        }
    }

    @Override
    public void updateServeType(int id, int serveType) throws SSException {
        try{
            Assert.lessOrEqualZero(id,EmenuException.OrderDishIdError);
            Assert.lessOrEqualZero(serveType,EmenuException.OrderServeTypeError);
            if(serveType==1){
                serveType = ServeTypeEnums.Instant.getId();
            }
            if(serveType==2){
                serveType = ServeTypeEnums.Later.getId();
            }
            orderDishMapper.updateServeType(id, serveType);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateServeTypeFailed,e);
        }
    }

    @Override
    public void updatePresentedDish(int id, int isPresentedDish) throws SSException {
        try{
            Assert.lessOrEqualZero(id, EmenuException.OrderDishIdError);
            orderDishMapper.updatePresentedDish(id, isPresentedDish);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdatePresentedDishFailed,e);
        }
    }

    @Override
    public void updateOrderDish(OrderDish orderDish) throws SSException {
        try {
            Assert.isNotNull(orderDish, EmenuException.OrderDishIsNotNull);
            commonDao.update(orderDish);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateOrderDishFailed,e);
        }
    }

    @Override
    public void newOrderDish(OrderDish orderDish) throws SSException {
        try {
            Assert.isNotNull(orderDish, EmenuException.OrderDishIsNotNull);
            commonDao.insert(orderDish);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewOrderDishFailed,e);
        }

    }

    @Override
    public void newOrderDishs(List<OrderDish> orderDishs) throws SSException {
        try{
            Assert.isNotNull(orderDishs,EmenuException.OrderDishIsNotNull);
            Assert.isNotEmpty(orderDishs, EmenuException.OrderdishsIsNotEmpty);
            commonDao.insertAll(orderDishs);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.NewOrderDishsFailed,e);
        }
    }

    @Override
    public int isTableHaveOrderDish(Integer tableId) throws SSException
    {
        int count = 0;//未上菜的菜品个数
        try{
            if(!Assert.lessOrEqualZero(tableId))
                count = orderDishMapper.isTableHaveOrderDish(tableId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryIsHaveOrderDishFailed,e);
        }
        return count;
    }

    @Override
    public int queryOrderDishTableId(Integer orderDishId) throws SSException
    {
        Integer tableId = new Integer(0);
        try{
            if(!Assert.lessOrEqualZero(orderDishId))
            tableId = orderDishMapper.queryOrderDishTableId(orderDishId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryOrderDishTableIdFail,e);
        }
        return tableId;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void wipeOrderDish(Integer orderDishId) throws SSException
    {
        try{
            if(!Assert.lessOrEqualZero(orderDishId))
            {
                OrderDish orderDish = new OrderDish();
                orderDish = this.queryById(orderDishId);//查询出菜品信息
                if(orderDish==null)//不存在该订单菜品
                {
                    throw SSException.get(EmenuException.OrderDishNotExist);
                }
                else
                {
                    if(orderDish.getStatus()== OrderDishStatusEnums.IsBooked.getId())//划单的菜品状态必须是2.正在做 1为已下单,打印了菜品的话菜品的状态会从1变为2
                        throw SSException.get(EmenuException.OrderDishStatusWrong);
                    else if(orderDish.getStatus()==OrderDishStatusEnums.IsFinsh.getId())
                        throw SSException.get(EmenuException.OrderDishWipeIsFinsh);
                    else//修改订单菜品状态
                    {
                        orderDish.setStatus(OrderDishStatusEnums.IsFinsh.getId());
                        this.updateOrderDish(orderDish);
                    }
                }
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.WipeOrderDishFail,e);
        }
    }

    @Override
    public int queryMaxPackageFlag() throws SSException
    {
        List<Integer> packageFlagList = new ArrayList<Integer>();
        try{
            packageFlagList=orderDishMapper.queryMaxPackageFlag();
            if(packageFlagList==null||packageFlagList.isEmpty())
            return 0;
            else
                return packageFlagList.get(packageFlagList.size()-1);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException. QueryMaxFalgFail,e);
        }
    }

    @Override
    public int isOrderHaveOrderDish(Integer orderId) throws SSException
    {
        int count = 0;
        try{
            if(!Assert.lessOrEqualZero(orderId))
            {
                count=orderDishMapper.isOrderHaveOrderDish(orderId);
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException. QueryMaxFalgFail,e);
        }
        return count;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class, RuntimeException.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void callDish(Integer orderDishId)throws SSException{
        try{
            if (Assert.lessOrEqualZero(orderDishId)){
                throw SSException.get(EmenuException.OrderDishIdError);
            }

            // TODO 多次催菜实现多次记录，现在只做1次催菜记录

            OrderDish orderDish = orderDishMapper.queryById(orderDishId);
            orderDish.setIsCall(OrderDishCallStatusEnums.IsCall.getId());
            commonDao.update(orderDish);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException. CallDishFailed,e);
        }
    }

    @Override
    public List<OrderDishDto> queryOrderDishListByTableId(Integer tableId) throws SSException{
        List<OrderDishDto> orderDishDtoList = new ArrayList<OrderDishDto>();
        try{
            if (Assert.lessOrEqualZero(tableId)){
                throw SSException.get(EmenuException.TableIdError);
            }

            List<Order> orderList = new ArrayList<com.emenu.common.entity.order.Order>();
            // 查询出对应餐桌所有已下单的订单, 已结账的订单不显示
            orderList = orderService.listByTableIdAndStatus(tableId,  OrderStatusEnums.IsBooked.getId());
            if (Assert.isNotNull(orderList)) {
                for (Order order : orderList) {
                    Integer orderId = order.getId();
                    List<OrderDishDto> orderDishDtoChildrenList = this.listDtoByOrderId(orderId);
                    for (OrderDishDto orderDishDto: orderDishDtoChildrenList){
                        if (orderDishDto.getStatus() != OrderDishStatusEnums.IsBack.getId()){
                            orderDishDtoList.add(orderDishDto);
                        }
                    }
                }
            }
            return orderDishDtoList;
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryOrderDishListFailed,e);
        }
    }
    @Override
    public void isOrderHaveEnoughIngredient(TableOrderCache tableOrderCache) throws SSException{
        // 得到前台订单中的所点的菜品和套餐
        List<OrderDishCache> orderDishCacheList =  new ArrayList<OrderDishCache>();
        try {
            if (tableOrderCache != null)
                orderDishCacheList = tableOrderCache.getOrderDishCacheList();
            Map<Integer, BigDecimal> map = new HashMap<Integer, BigDecimal>();
            for (OrderDishCache orderDishCache : orderDishCacheList) {
                // 判断是否为套餐
                if (dishPackageService.judgeIsOrNotPackage(orderDishCache.getDishId()) == PackageStatusEnums.IsPackage.getId()) {
                    DishPackageDto dashPackageDto = dishPackageService.queryDishPackageById(orderDishCache.getDishId());
                    // 套餐的数量
                    BigDecimal qualityPackage =new BigDecimal(Float.toString(orderDishCache.getQuantity()));
                    List<DishDto> listDishDto = dashPackageDto.getChildDishDtoList();
                    // 遍历套餐中的菜品
                    for (DishDto dishDto : listDishDto) {
                        DishPackage dishPackage = dishDto.getDishPackage();
                        // 套餐中某菜品的数量
                        BigDecimal qualityDish =new BigDecimal(Integer.toString(dishPackage.getDishQuantity()));
                        map.put(dishDto.getId(),(qualityDish.multiply(qualityPackage)));
                    }

                }else{
                    // 普通菜品
                    DishDto dishDto = dishService.queryById(orderDishCache.getDishId());
                    BigDecimal qualityDish =new BigDecimal(Float.toString(orderDishCache.getQuantity()));
                    map.put(dishDto.getId(),qualityDish);
                }
                // 所有的套餐里面的菜品和普通的菜品都已经在map里面了，遍历map使得相同的菜品数量相加
                Iterator iter = map.entrySet().iterator();
                while(iter.hasNext()){
                    Map.Entry entry = (Map.Entry) iter.next();
                    Integer key = (Integer)entry.getKey();
                    BigDecimal val = (BigDecimal)entry.getValue();
                    while(iter.hasNext()){
                        Map.Entry entryNext = (Map.Entry) iter.next();
                        Integer keyNext = (Integer)entryNext.getKey();
                        BigDecimal valNext = (BigDecimal)entryNext.getValue();
                        if(key == keyNext){
                           val = val.add(valNext);
                            map.remove(entryNext.getKey());
                        }
                    }
                }
                //遍历map查看里面的菜品是否都原料充足
               List<String> list = new ArrayList<String>();
               for(Map.Entry<Integer, BigDecimal> entry:map.entrySet()){
                   Integer dishId = entry.getKey();
                   BigDecimal quality = entry.getValue();
                   // 找到菜品的dto
                   DishDto dishDto = dishService.queryById(dishId);
                   // 找到菜品的成本卡
                   CostCard costCard = costCardService.queryCostCardByDishId(dishId);
                   if (costCard != null) {
                       List<CostCardItemDto> listCostCardItemDto = costCardItemService.listByCostCardId(costCard.getId());
                       for(CostCardItemDto costCardItemDto:listCostCardItemDto){
                           BigDecimal allCount = storageSettlementService.queryCache(costCardItemDto.getId());
                       }
                   }
                }
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.OrderNotEnoughIngredient,e);
        }



            for(OrderDishCache orderDishCache :orderDishCacheList){
                // 套餐
                if(dishPackageService.judgeIsOrNotPackage(orderDishCache.getDishId()) == PackageStatusEnums.IsPackage.getId()) {

                    DishPackageDto dashPackageDto = dishPackageService.queryDishPackageById(orderDishCache.getDishId());
                    // 套餐的数量
                    Float qualityPackage = orderDishCache.getQuantity();

                    BigDecimal numberPackage = quality;
                    // 该套餐下的菜品集合
                    List<DishDto> listDishDto = dashPackageDto.getChildDishDtoList();
                    for (DishDto dishDto : listDishDto) {
                        CostCard costCard = costCardService.queryCostCardByDishId(dishDto.getId());
                        if (costCard != null) {
                            List<CostCardItemDto> listCostCardItemDto = costCardItemService.listByCostCardId(costCard.getId());
                            for (CostCardItemDto costCardItemDto : listCostCardItemDto) {
                                Ingredient ingredient = ingredientService.queryById(costCardItemDto.getIngredientId());
                                BigDecimal oneNeedCout = costCardItemDto.getOtherCount();
                                // 得到顾客需要该菜品的全部原材料数
                                BigDecimal needCout = oneNeedCout.multiply(quality);
                                BigDecimal allCout = ingredient.getOrderQuantity();
                                BigDecimal number = BigDecimal.valueOf(1);
                                if (allCout != null) {
                                    BigDecimal consult = allCout.divide(needCout, 2, BigDecimal.ROUND_HALF_UP);
                                    number = (((number.compareTo(consult) == -1) || ((number.compareTo(consult) == 0))) ? number : consult);
                                } else {
                                    numberPackage.subtract(BigDecimal.valueOf(1));
                                    throw SSException.get(EmenuException.valueOf("菜品：" + dishDto.getName() + ",原材料不足，目前材料可做0份"));
                                }
                                // 得到顾客所下单中某套餐某个菜品的能够做出来几份
                                if (number.compareTo(quality) == -1) {
                                    numberPackage.subtract(new BigDecimal(1));
                                    throw SSException.get(EmenuException.valueOf("套餐：" + dashPackageDto.getDishDto().getName() + "中" + "菜品：" + dishDto.getName() + ",原材料不足，目前材料可做" + number + "份"));
                                }

                            }
                        }
                        if (numberPackage.compareTo(quality) == -1) {
                            throw SSException.get(EmenuException.valueOf("套餐：" + dashPackageDto.getDishDto().getName() + "目前只能做" + numberPackage + "份"));
                        }
                    }
                }else {
                    // 普通菜品
                    DishDto dishDto = dishService.queryById(orderDishCache.getDishId());
                    // 找到原料卡List
                    CostCard costCard = costCardService.queryCostCardByDishId(orderDishCache.getDishId());
                    if (costCard != null) {
                        List<CostCardItemDto> listCostCardItemDto = costCardItemService.listByCostCardId(costCard.getId());
                        // 遍历原料List
                        BigDecimal number = new BigDecimal(1);
                        BigDecimal quality = new BigDecimal(1);
                        for (CostCardItemDto costCardItemDto : listCostCardItemDto) {
                            // 找到库存
                            Ingredient ingredient = ingredientService.queryById(costCardItemDto.getIngredientId());
                            BigDecimal oneNeedCout = costCardItemDto.getOtherCount();
                            quality = new BigDecimal(Float.toString(orderDishCache.getQuantity()));
                            BigDecimal needCout = oneNeedCout.multiply(quality);
                            BigDecimal allCout = ingredient.getOrderQuantity();
                            if (allCout != null) {
                                BigDecimal consult = allCout.divide(needCout, 2, BigDecimal.ROUND_HALF_UP);
                                number = (((number.compareTo(consult) == -1) || ((number.compareTo(consult) == 0))) ? number : consult);
                            } else {
                                throw SSException.get(EmenuException.valueOf("菜品：" + dishDto.getName() + ",原材料不足，目前材料可做0份"));
                            }
                        }
                        if (number.compareTo(quality) == -1) {
                            throw SSException.get(EmenuException.valueOf("菜品：" + dishDto.getName() + ",原材料不足，目前材料可做" + number + "份"));
                        }
                    }
                }
            }

        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.OrderNotEnoughIngredient,e);
        }

    }
}
