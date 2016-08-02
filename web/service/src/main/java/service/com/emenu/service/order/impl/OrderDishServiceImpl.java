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
            orderDish.setDiscount(new BigDecimal(10)); // 添加进来时，折扣默认都是10(100%)
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
                    else if(orderDish.getStatus()==OrderDishStatusEnums.IsFinish.getId())
                        throw SSException.get(EmenuException.OrderDishWipeIsFinsh);
                    else//修改订单菜品状态
                    {
                        orderDish.setStatus(OrderDishStatusEnums.IsFinish.getId());
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
            throw SSException.get(EmenuException. QueryMaxFlagFail,e);
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
            throw SSException.get(EmenuException. QueryMaxFlagFail,e);
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

            OrderDish orderDish = orderDishMapper.queryById(orderDishId);
            // 获取该菜品的信息
            DishDto dishDto = dishService.queryById(orderDish.getDishId());
            // 系统当前时间
            Date nowDate = new Date();
            // 获取已下单的分钟数
            Long time = (nowDate.getTime() - orderDish.getOrderTime().getTime())/60/1000;

            // 套餐催菜，整体菜品催菜
            if (orderDish.getIsPackage() == PackageStatusEnums.IsPackage.getId()){
                List<OrderDish> orderDishList = this.queryPackageOrderDishesByPackageFlag(orderDish.getPackageFlag());
                Integer minTime = dishDto.getTimeLimit();
                // 取出套餐中最小的上菜时限
                for (int i = 0; i < orderDishList.size(); i++){
                    DishDto tempDishDto = dishService.queryById(orderDish.getDishId());
                    if (tempDishDto.getTimeLimit() < minTime){
                        minTime = tempDishDto.getTimeLimit();
                    }
                }
                // 时间达到最小时限，则把套餐中所有菜品进行催菜
                if (minTime > 0 && minTime < time){
                    throw SSException.get(EmenuException.CallDishNotAllow);
                }else {
                    for (OrderDish tempOrderDish : orderDishList){
                        tempOrderDish.setIsCall(OrderDishCallStatusEnums.IsCall.getId());
                        commonDao.update(tempOrderDish);
                    }
                }

            } else {
                // 非套餐催菜
                // 催菜加时限判断，未到达菜品上菜时限不能催菜。
                if (dishDto.getTimeLimit() == 0){
                    // 没有时限的菜品可以催菜
                    orderDish.setIsCall(OrderDishCallStatusEnums.IsCall.getId());
                }else if (time > dishDto.getTimeLimit()){
                    orderDish.setIsCall(OrderDishCallStatusEnums.IsCall.getId());
                }else {
                    throw SSException.get(EmenuException.CallDishNotAllow);
                }
                commonDao.update(orderDish);
            }
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

    //@Override
    public List<OrderDish> listByOrderIdAndPackageId(int orderId, int packageId) throws SSException {
        List<OrderDish> orderDishList = Collections.emptyList();
        try {
            Assert.lessOrEqualZero(orderId, EmenuException.OrderIdError);
            Assert.lessOrEqualZero(packageId, EmenuException.DishPackageIdError);
            orderDishList = orderDishMapper.listByOrderIdAndPackageId(orderId, packageId);
        } catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListOrderDishByOrderIdFailed, e);
        }
        return orderDishList;
    }



    //@Override
    public void isOrderHaveEnoughIngredient(TableOrderCache tableOrderCache) throws SSException {
        // 得到前台订单中的所点的菜品和套餐
        List<OrderDishCache> orderDishCacheList = new ArrayList<OrderDishCache>();
        List<String> exceptionList = new ArrayList<String>();
        String exceptionString = "";
        try {
            /**下面的判断???????????????????????*/
            if (tableOrderCache != null)
                orderDishCacheList = tableOrderCache.getOrderDishCacheList();
            // dishId和quantity（菜品数量）
            Map<Integer, BigDecimal> dishMap = new HashMap<Integer, BigDecimal>();
            for(OrderDishCache orderDishCache : orderDishCacheList){
                // dishId为套餐
                if(dishPackageService.judgeIsOrNotPackage(orderDishCache.getDishId()) == PackageStatusEnums.IsPackage.getId()){
                    DishPackageDto dishPackageDto = dishPackageService.queryDishPackageById(orderDishCache.getDishId());
                    // 套餐的数量
                    BigDecimal packageQuantity = new BigDecimal(Float.toString(orderDishCache.getQuantity()));
                    // 获得套餐的菜品
                    List<DishDto> listDishDto = dishPackageDto.getChildDishDtoList();
                    // 遍历套餐中的菜品,加入到dishMap里面去
                    for(DishDto dishDto : listDishDto){
                        DishPackage dishPackage = dishDto.getDishPackage();
                        // 套餐中某菜品的数量
                        BigDecimal quantityDish = new BigDecimal(Integer.toString(dishPackage.getDishQuantity()));
                        // dishMap里没有该菜品
                        if(dishMap.get(dishDto.getId()) == null){
                            dishMap.put(dishDto.getId(), quantityDish.multiply(packageQuantity));
                        }else{
                            dishMap.put(dishDto.getId(), dishMap.get(dishDto.getId()).add((quantityDish.multiply(packageQuantity))));
                        }
                    }
                }else{
                    // dishId为普通菜品
                    DishDto dishDto = dishService.queryById(orderDishCache.getDishId());
                    BigDecimal quantityDish = new BigDecimal(Float.toString(orderDishCache.getQuantity()));
                    if(dishMap.get(dishDto.getId()) == null){
                        dishMap.put(dishDto.getId(), quantityDish);
                    }else{
                        dishMap.put(dishDto.getId(), dishMap.get(dishDto.getId()).add(quantityDish));
                    }
                }
            }
            // ingredientId和库存量
            Map<Integer, BigDecimal> ingredientMap = new HashMap<Integer, BigDecimal>();
            // 遍历map查看里面的菜品是否都原料充足
            for(Map.Entry<Integer, BigDecimal> entry : dishMap.entrySet()){
                Integer dishId = entry.getKey();
                // 菜的数量
                BigDecimal quantity = entry.getValue();
                // 找到菜品的dto
                DishDto dishDto = dishService.queryById(dishId);
                // 找到菜品的成本卡
                CostCard costCard = costCardService.queryCostCardByDishId(dishId);
                // 该菜存在
                if(costCard != null){
                    // 菜品的所有原料
                    List<CostCardItemDto> listCostCardItemDto = costCardItemService.listByCostCardId(costCard.getId());
                    // 该菜能做的数量,初始值0份
                    Float number = new Float(0);
                    // 遍历所用的原料，得到能做该菜的份数
                    for(CostCardItemDto costCardItemDto : listCostCardItemDto){
                        // 库存里没有这种原料
                        if(costCardItemDto == null){
                            number = new Float(0);
                            break;
                        }
                        // 原料Map里没有该原料
                        if (ingredientMap.get(costCardItemDto.getIngredientId()) == null) {
                            ingredientMap.put(costCardItemDto.getIngredientId(), storageSettlementService.queryCache(costCardItemDto.getId()));
                        }
                        // 原料的当前存量(在Map里)
                        BigDecimal allCount = ingredientMap.get(costCard.getId());
                        // 原料在Map库存量没了
                        if(allCount.equals(0)){
                            number = new Float(0);
                            break;
                        }else{
                            // 单份菜需要该原料的量
                            BigDecimal needCount = costCardItemDto.getOtherCount();
                            // 库存能用这种原料做几份
                            float tempnumber = allCount.divide(needCount).floatValue();
                            number = number <= tempnumber ? number : tempnumber;
                        }
                    }
                    if(number == 0){
                        exceptionList.add("菜品：" + dishDto.getName() + ", 某原材料库存为空，暂时无法提供该菜品");
                    }
                    // 该菜可以做，但是份数少于点的
                    else if(number < quantity.floatValue()){
                        exceptionList.add("菜品" + dishDto.getName() + ", 原材料不足，目前只能做" + number + dishDto.getName());
                        // 将该菜所耗费的原料的量从ingredientMap里减去
                        for (CostCardItemDto costCardItemDto : listCostCardItemDto) {
                            // 将做的份数float->BigDecimal转换
                            BigDecimal tempNumber = new BigDecimal(number.toString());
                            // 耗费的原料的量从ingredientMap里减去
                            ingredientMap.put(costCardItemDto.getIngredientId(),
                                    ingredientMap.get(costCardItemDto.getIngredientId()).subtract(tempNumber.multiply(costCardItemDto.getOtherCount())));
                        }
                    }
                    // 点的份数全部都做
                    else{
                        // 耗费的原料的量从ingredientMap里减去
                        for(CostCardItemDto costCardItemDto : listCostCardItemDto){
                            ingredientMap.put(costCardItemDto.getIngredientId(),
                                    ingredientMap.get(costCardItemDto.getIngredientId()).subtract(quantity.multiply(costCardItemDto.getOtherCount())));
                        }
                    }
                }
                if(!exceptionList.isEmpty()){
                    for(String list : exceptionList){
                        exceptionString.concat(list.toString()).concat("\n");
                    }
                    throw SSException.get(EmenuException.valueOf(exceptionString));
                }
            }
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.OrderNotEnoughIngredient, e);
        }
    }

    @Override
    public List<OrderDishDto> queryOrderDishAndCombinePackageByTableId(Integer tableId) throws SSException {
        List<OrderDishDto> orderDishDtoList = new ArrayList<OrderDishDto>();
        try {
            if (Assert.lessOrEqualZero(tableId)) {
                throw SSException.get(EmenuException.TableIdError);
            }
            List<OrderDishDto> orderDishChildrenList = this.queryOrderDishListByTableId(tableId);
            Map<Integer,Integer> map = new HashMap<Integer,Integer>();
            for (OrderDishDto orderDishDto: orderDishChildrenList){
                if (orderDishDto.getStatus() != OrderDishStatusEnums.IsBack.getId()){
                    // 是套餐并且未出现在Map里
                    if(orderDishDto.getIsPackage() == PackageStatusEnums.IsPackage.getId()
                            && map.get(orderDishDto.getPackageFlag()) == null){
                        // 通过packageId查询出菜品的信息
                        DishDto dishDtoTemp = dishService.queryById(orderDishDto.getPackageId());
                        // 原本的话套餐显示是单个菜品名字,这里要重新设置一下,设置成显示套餐的名字
                        orderDishDto.setDishName(dishDtoTemp.getName());
                        map.put(orderDishDto.getPackageFlag(),orderDishDto.getDishId());
                        orderDishDtoList.add(orderDishDto);
                    }
                    if(orderDishDto.getIsPackage() == PackageStatusEnums.IsNotPackage.getId()){
                        orderDishDtoList.add(orderDishDto);
                    }
                }
            }
            return orderDishDtoList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryOrderDishListFailed, e);
        }
    }

    @Override
    public List<OrderDish> queryPackageOrderDishByFirstOrderDishId(Integer orderDishId) throws SSException{
        List<OrderDish> orderDishList = new ArrayList<OrderDish>();
        try{
            if(Assert.lessOrEqualZero(orderDishId)){
                throw SSException.get(EmenuException.OrderDishIdError);
            }
            OrderDish orderDish = this.queryById(orderDishId);
            Integer packageFlag = orderDish.getPackageFlag();
            // 查询出该所有的订单菜品
            List<OrderDish> allOrderDish = this.listByOrderId(orderDish.getOrderId());
            for (OrderDish tempOrderDish: allOrderDish){
                if (tempOrderDish.getPackageFlag() == packageFlag){
                    orderDishList.add(tempOrderDish);
                }
            }
            return orderDishList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryOrderDishListFailed, e);
        }
    }

    @Override
    public OrderDishStatusEnums queryPackageStatusByFirstOrderDishId(Integer orderDishId) throws SSException{
        List<OrderDish> orderDishList = Collections.emptyList();
        try{
            if(Assert.lessOrEqualZero(orderDishId)){
                throw SSException.get(EmenuException.OrderDishIdError);
            }
            OrderDish firstOrderDish = this.queryById(orderDishId);
            if (firstOrderDish.getStatus() == OrderDishStatusEnums.IsBack.getId()){
                return OrderDishStatusEnums.IsBack;
            }
            // 取到套餐下的所有菜品
            orderDishList = this.queryPackageOrderDishByFirstOrderDishId(orderDishId);
            Integer isBooked = 0; // 已下单的菜品数量
            Integer isFinish = 0; // 已经完成的菜品数量
            for (OrderDish orderDish : orderDishList){
                Integer status = orderDish.getStatus();
                // 统计套餐下所有菜品的状态
                if (status == OrderDishStatusEnums.IsBooked.getId()){
                    isBooked++;
                }else if (status == OrderDishStatusEnums.IsFinish.getId()){
                    isFinish++;
                }
            }

            // 判断套餐状态
            Integer packageDishNumber = orderDishList.size();
            if(isBooked == packageDishNumber){
                return OrderDishStatusEnums.IsBooked;
            } else if (isFinish == packageDishNumber){
                return OrderDishStatusEnums.IsFinish;
            } else {
                return OrderDishStatusEnums.IsMake;
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryOrderDishListFailed, e);
        }
    }

    @Override
    public List<OrderDish> queryPackageOrderDishesByPackageFlag(Integer packageFlag) throws SSException{
        List<OrderDish> orderDishList = new ArrayList<OrderDish>();
        try{
            orderDishList = orderDishMapper.queryPackageOrderDishesByPackageFlag(packageFlag);
            return orderDishList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryOrderDishListFailed, e);
        }
    }


    @Override
    public List<OrderDish> listOrderDishByOrderIdAndStatus(Integer orderId,
                                                           Integer status) throws SSException{

        List<OrderDish> orderDishs = new ArrayList<OrderDish>();
        try{
            if(!Assert.lessOrEqualZero(orderId)
                    &&!Assert.lessOrEqualZero(status)){
                orderDishs = orderDishMapper.listOrderDishByOrderIdAndStatus(orderId,status);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryOrderDishListFailed, e);
        }
        return orderDishs;
    }

}
