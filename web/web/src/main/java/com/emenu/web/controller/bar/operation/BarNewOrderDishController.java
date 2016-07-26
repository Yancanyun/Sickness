package com.emenu.web.controller.bar.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.cache.order.OrderDishCache;
import com.emenu.common.cache.order.TableOrderCache;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishPackageDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.order.Checkout;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.enums.checkout.CheckOutStatusEnums;
import com.emenu.common.enums.dish.PackageStatusEnums;
import com.emenu.common.enums.order.*;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 增加消费controller
 *
 * @author quanyibo
 * @date 2016/7/21
 */
@Controller
@RequestMapping(value = URLConstants.BAR_ORDER_DISH_ADD_URL)
@Module(ModuleEnums.BarOrderDishAdd)
public class BarNewOrderDishController extends AbstractController{

    /**
     * 增加消费-获取总分类和总分类下的大类
     * @param
     * @return
     */

    @RequestMapping(value = "/list/tag",method = RequestMethod.GET)
    @Module(ModuleEnums.BarOrderDishAddListTag)
    @ResponseBody
    public JSONObject listTag() {

        JSONObject jsonObject = new JSONObject();
        try{
             jsonObject = barOrderDishNewService.queryTag();
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
    }

    /**
     * 增加消费-选择具体大类返回大类下的所有菜品信息
     * @param
     * @return
     */

    @RequestMapping(value = "/all/dish",method = RequestMethod.GET)
    @Module(ModuleEnums.BarOrderDishAddListDish)
    @ResponseBody
    public JSONObject listDishByTagId(@RequestParam("tagId") Integer tagId) {

        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject = barOrderDishNewService.queryAllDishByBigTag(tagId);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
    }

    /**
     * 增加消费-根据关键字和菜品大类搜索
     * @param
     * @return
     */

    @RequestMapping(value = "/search",method = RequestMethod.GET)
    @Module(ModuleEnums.BarOrderDishAddSearch)
    @ResponseBody
    public JSONObject searchDishByTagIdAndKey(@RequestParam("tagId") Integer tagId,
                                              @RequestParam("key") String key) {

        JSONObject jsonObject = new JSONObject();
        try{
            jsonObject = barOrderDishNewService.queryDishByBigTagAndKey(tagId,key);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
    }

    /**
     * 增加消费-增加到菜品缓存中
     * 添加的时候一次性只能选择一个菜品
     * @param
     * @return
     */

    @RequestMapping(value = "/new",method = RequestMethod.POST)
    @Module(ModuleEnums.BarOrderDishAddListDish)
    @ResponseBody
    public JSONObject listDishByTagId(@RequestParam("tableId")Integer tableId,
                                      @RequestParam("dishId")Integer dishId,
                                      @RequestParam("quantity")Float quantity,
                                      @RequestParam("tasteId")Integer tasteId,
                                      @RequestParam("serveType")Integer serveType,
                                      @RequestParam("remark")String remark) {

        JSONObject jsonObject = new JSONObject();
        OrderDishCache orderDishCache = new OrderDishCache();
        TableOrderCache tableOrderCache = new TableOrderCache();
        try{
            tableOrderCache = orderDishCacheService.listByTableId(tableId);
            if(tableOrderCache!=null) {
                // 餐桌未锁死可以进行添加操作
                if(tableOrderCache.getLock()==false){
                    orderDishCache.setDishId(dishId);
                    orderDishCache.setQuantity(quantity);
                    orderDishCache.setRemark(remark);
                    orderDishCache.setServeType(serveType);
                    orderDishCache.setTasteId(tasteId);
                    orderDishCacheService.newDish(tableId,orderDishCache);
                }
                else
                    sendErrMsgAndErrCode(SSException.get(EmenuException.TableIsLock));
            }
            // 可能为头一次点餐
            else{
                orderDishCache.setDishId(dishId);
                orderDishCache.setQuantity(quantity);
                orderDishCache.setRemark(remark);
                orderDishCache.setServeType(serveType);
                orderDishCache.setTasteId(tasteId);
                orderDishCacheService.newDish(tableId,orderDishCache);
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * 增加消费-返回增加了的菜品信息(缓存中的所有菜品)
     * 缓存由服务员,吧台和顾客共用
     * @param
     * @return
     */

    @RequestMapping(value = "/list/dish",method = RequestMethod.GET)
    @Module(ModuleEnums.BarOrderDishAddListCacheDish)
    @ResponseBody
    public JSONObject listCacheDish(@RequestParam("tableId")Integer tableId){

        JSONObject jsonObject = new JSONObject();
        TableOrderCache tableOrderCache = new TableOrderCache();
        List<OrderDishCache> orderDishCaches = new ArrayList<OrderDishCache>();
        try{
           tableOrderCache = orderDishCacheService.listByTableId(tableId);
           if(tableOrderCache!=null){
               orderDishCaches=tableOrderCache.getOrderDishCacheList();
               JSONArray jsonArray = new JSONArray();
               for(OrderDishCache dto : orderDishCaches){
                    JSONObject temp = new JSONObject();
                   // 缓存Id
                   temp.put("dishCacheId",dto.getId());
                   temp.put("dishId",dto.getDishId());
                   // 查询出菜品信息
                   DishDto dishDto = dishService.queryById(dto.getDishId() );
                   if(dishDto.getDishNumber()!=null)
                   temp.put("dishNumber",dishDto.getDishNumber());
                   else
                   temp.put("dishNumber","无");
                   temp.put("assistantCode",dishDto.getAssistantCode());
                   temp.put("name",dishDto.getName());
                   temp.put("unit",dishDto.getUnitName());
                   temp.put("salePrice",dishDto.getSalePrice());
                   temp.put("dishQuantity",dto.getQuantity());
                   if(dto.getTasteId()!=null
                           &&dto.getTasteId()!=0) {
                       // 查询出菜品口味
                       temp.put("tasteName",tasteService.queryById(dto.getTasteId()).getName());
                   }
                   else{
                       temp.put("tasteName","正常");
                   }
                   temp.put("isPresentedDish",dto.getIsPresentedDish());
                   jsonArray.add(temp);
               }
               jsonObject.put("orderDishList",jsonArray);
           }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
    }

    /**
     * 增加消费-赠送菜品
     *
     * @param
     * @return
     */

    @RequestMapping(value = "/present",method = RequestMethod.POST)
    @Module(ModuleEnums.BarOrderDishAddPresent)
    @ResponseBody
    public JSONObject orderDishPresent(@RequestParam("tableId") Integer tableId,
                                       @RequestParam("dishCacheId") Integer dishCacheId,
                                       @RequestParam("status") Integer status) {

        JSONObject jsonObject = new JSONObject();
        TableOrderCache tableOrderCache = new TableOrderCache();
        List<OrderDishCache> orderDishCaches = new ArrayList<OrderDishCache>();
        try{
            tableOrderCache=orderDishCacheService.listByTableId(tableId);
            if(tableOrderCache!=null){
                orderDishCaches = tableOrderCache.getOrderDishCacheList();
                if(tableOrderCache.getLock()==true)
                    return sendErrMsgAndErrCode( SSException.get(EmenuException.TableIsLock));
                OrderDishCache orderDishCache = new OrderDishCache();
                for(OrderDishCache dto :orderDishCaches){
                    if(dto.getId()==dishCacheId){
                        orderDishCache=dto;
                        break;
                    }
                }
                if(orderDishCache==null)
                    return sendErrMsgAndErrCode(SSException.get(EmenuException.OrderDishCacheNotExist));
                orderDishCache.setIsPresentedDish(status);
                // 更新缓存
                orderDishCacheService.updateDish(tableId,orderDishCache);
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
    }

    /**
     * 增加消费-删除菜品
     *
     * @param
     * @return
     */

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @Module(ModuleEnums.BarOrderDishAddDelete)
    @ResponseBody
    public JSONObject orderDishDelete(@RequestParam("tableId") Integer tableId,
                                       @RequestParam("dishCacheId") Integer dishCacheId) {

        JSONObject jsonObject = new JSONObject();
        TableOrderCache tableOrderCache = new TableOrderCache();
        List<OrderDishCache> orderDishCaches = new ArrayList<OrderDishCache>();
        try{
            tableOrderCache=orderDishCacheService.listByTableId(tableId);
            if(tableOrderCache!=null){
                orderDishCaches = tableOrderCache.getOrderDishCacheList();
                if(tableOrderCache.getLock()==true)
                    throw SSException.get(EmenuException.TableIsLock);
                OrderDishCache orderDishCache = new OrderDishCache();
                for(OrderDishCache dto :orderDishCaches){
                    if(dto.getId()==dishCacheId){
                        orderDishCache=dto;
                        break;
                    }
                }
                if(orderDishCache==null)
                    return sendErrMsgAndErrCode(SSException.get(EmenuException.OrderDishCacheNotExist));
                // 删除菜品缓存
                orderDishCacheService.delDish(tableId,dishCacheId);
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
    }

    /**
     * 增加消费-再次确认
     *
     * @param
     * @return
     */

    @RequestMapping(value = "/reconfirm",method = RequestMethod.POST)
    @Module(ModuleEnums.BarOrderDishAddDelete)
    @ResponseBody
    public JSONObject orderDishReconfirm(@RequestParam("tableId") Integer tableId,
                                      @RequestParam("status") Integer status) {

        JSONObject jsonObject = new JSONObject();
        TableOrderCache tableOrderCache = new TableOrderCache();
        List<OrderDishCache> orderDishCaches = new ArrayList<OrderDishCache>();
        try{
            tableOrderCache=orderDishCacheService.listByTableId(tableId);
            if(tableOrderCache!=null){
                // 1为要锁死餐桌,0为解锁餐桌
                if(status==1
                        &&tableOrderCache.getLock()==false){
                    orderDishCacheService.tableLock(tableId);
                }
                else if(status==1
                        &&tableOrderCache.getLock()==true)
                    return sendErrMsgAndErrCode(SSException.get(EmenuException.TableIsLock));
                else{
                    orderDishCacheService.tableLockRemove(tableId);
                }
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
    }

    /**
     * 增加消费-完成
     * 添加到数据库中
     * @param
     * @return
     */

    @RequestMapping(value = "/finish",method = RequestMethod.POST)
    @Module(ModuleEnums.BarOrderDishAddFinish)
    @ResponseBody
    public JSONObject listDishByTagId(@RequestParam("tableId") Integer tableId,
                                      @RequestParam("serveType") Integer serveType,
                                      @RequestParam("orderRemark")String orderRemark) {

        //下单时间
        Date orderTime=new Date();
        try {
            Checkout checkout = new Checkout();
            checkout = checkoutService.queryByTableIdAndStatus(tableId, CheckOutStatusEnums.IsNotCheckOut.getId());//是否存在未结账的结账单
            //新增结账单到数据表
            if (checkout == null) {
                checkout = new Checkout();
                // 餐桌状态为占用已结账的话则不是第一次消费
                if(tableService.queryById(tableId).getStatus()==TableStatusEnums.Checkouted.getId()){
                    checkout.setConsumptionType(CheckoutConsumptionTypeEnums.IsSecondConsumption.getId());
                    // 第二次消费的话餐桌状态由占有已经账变为占用未结账
                    tableService.updateStatus(tableId,TableStatusEnums.Uncheckouted.getId());
                }
                else
                    checkout.setConsumptionType(CheckoutConsumptionTypeEnums.IsFirstConsumption.getId());
                checkout.setTableId(tableId);
                checkout.setCreatedTime(new Date());
                checkout.setStatus(CheckOutStatusEnums.IsNotCheckOut.getId());
                checkoutService.newCheckout(checkout);//若不存在结帐单再生成新的结账单,存在的话不用新生成结账单
                // 获取到新生成的结账单,主要是要获取到checkOut的Id这个属性
                checkout = checkoutService.queryByTableIdAndStatus(tableId, CheckOutStatusEnums.IsNotCheckOut.getId());
            }

            // 新增订单到数据库
            Order order = new Order();
            order.setCheckoutId(checkout.getId());
            order.setCreatedTime(new Date());
            order.setOrderRemark(orderRemark);
            order.setOrderServeType(serveType);
            order.setStatus(OrderStatusEnums.IsBooked.getId());
            order.setTableId(tableId);
            // 订单是否被盘点过
            order.setIsSettlemented(OrderSettlementStatusEnums.IsNotSettlement.getId());
            orderService.newOrder(order);

            // 新增订单菜品到数据库
            TableOrderCache tableOrderCache = new TableOrderCache();
            List<OrderDishCache> orderDishCache = new ArrayList<OrderDishCache>();
            tableOrderCache=orderDishCacheService.listByTableId(tableId);
            if(tableOrderCache!=null)
            {
                // 获取一个餐桌的全部订单缓存
                orderDishCache=tableOrderCache.getOrderDishCacheList();
            }
            for(OrderDishCache dto :orderDishCache)
            {
                DishDto dishDto = new DishDto();
                //是套餐,把套餐拆成一个一个的菜品存到书库库里
                if(dishPackageService.judgeIsOrNotPackage(dto.getDishId())>0)//是套餐,把套餐拆成一个一个的菜品存到书库库里
                {
                    DishPackageDto dishPackageDto = new DishPackageDto();
                    // 根据套餐Id查询出套餐具体信息,是套餐的话dishId就是对应的套餐Id
                    dishPackageDto=dishPackageService.queryDishPackageById(dto.getDishId());
                    List<DishDto> dishDtos = new ArrayList<DishDto>();
                    dishDtos=dishPackageDto.getChildDishDtoList();//获得套餐所有的子菜品
                    // 将套餐拆成单个菜品存到数据库里
                    Integer packageFlag;
                    packageFlag = orderDishService.queryMaxPackageFlag();//获取套餐标识最大值
                    packageFlag+=1;//新增的套餐的套餐标识为最大值加1
                    for(DishDto packageDto :dishDtos)
                    {
                        OrderDish orderDish = new OrderDish();
                        orderDish.setCreatedTime(new Date());
                        orderDish.setDishId(packageDto.getDishPackage().getDishId());
                        Float temp = dto.getQuantity();
                        orderDish.setIsPackage(PackageStatusEnums.IsPackage.getId());
                        orderDish.setPackageId(dto.getDishId());
                        orderDish.setPackageQuantity(temp.intValue());
                        // 套餐菜品数量为单个套餐中单个菜品的数量*套餐数量
                        orderDish.setDishQuantity(packageDto.getDishPackage().getDishQuantity().floatValue()*temp.floatValue());
                        orderDish.setOrderId(order.getId());
                        // 根据套餐的子菜品的Id来获取菜品信息
                        dishDto = dishService.queryById(dto.getDishId());
                        // 整体套餐售价,而不是套餐中单个菜品售价
                        orderDish.setSalePrice(dishDto.getSalePrice());
                        orderDish.setStatus(OrderDishStatusEnums.IsBooked.getId());
                        orderDish.setDiscount(new BigDecimal(dishDto.getDiscount()));
                        // 未设置单个菜品的上菜方式,则上菜方式为整单上菜方式
                        if(dto.getServeType()==null
                                ||dto.getServeType()== ServeTypeEnums.NotSet.getId())
                            orderDish.setServeType(serveType);
                        else orderDish.setServeType(dto.getServeType());
                        orderDish.setOrderTime(orderTime);
                        orderDish.setIsCall(OrderDishCallStatusEnums.IsNotCall.getId());
                        orderDish.setIsChange(OrderDishTableChangeStatusEnums.IsNotChangeTable.getId());
                        orderDish.setRemark(dto.getRemark());//菜品备注要从缓存中取出
                        if(dto.getTasteId()!=null
                                &&dto.getTasteId()!=0)//菜品口味可以不选择,不选择的话为默认菜品口味
                            orderDish.setTasteId(dto.getTasteId());//设置菜品口味Id
                        orderDish.setPackageFlag(packageFlag);//设置套餐标识
                        orderDish.setIsPresentedDish(dto.getIsPresentedDish());
                        orderDishService.newOrderDish(orderDish);
                    }
                }
                else
                {
                    OrderDish orderDish=new OrderDish();
                    orderDish.setCreatedTime(new Date());
                    orderDish.setDishId(dto.getDishId());
                    Float temp = dto.getQuantity(); //设置菜品数量
                    orderDish.setDishQuantity(temp);
                    orderDish.setIsPackage(PackageStatusEnums.IsNotPackage.getId());
                    dishDto = dishService.queryById(dto.getDishId());
                    orderDish.setOrderId(order.getId());
                    orderDish.setStatus(OrderDishStatusEnums.IsBooked.getId());//菜品状态：1-已下单；2-正在做；3-已上菜
                    orderDish.setDiscount(new BigDecimal(dishDto.getDiscount()));//折扣
                    orderDish.setSalePrice(dishDto.getSalePrice());
                    if(dto.getServeType()==null
                            ||dto.getServeType()==ServeTypeEnums.NotSet.getId())//未设置单个菜品的上菜方式,则上菜方式为整单上菜方式
                        orderDish.setServeType(serveType);
                    else orderDish.setServeType(dto.getServeType());
                    orderDish.setOrderTime(orderTime);
                    orderDish.setIsCall(OrderDishCallStatusEnums.IsNotCall.getId());//是否被催菜
                    orderDish.setIsChange(OrderDishTableChangeStatusEnums.IsNotChangeTable.getId());//是否换了桌
                    //快捷点菜的时候不加菜品的备注,在详情页点菜可以给菜品加备注,但是如果对应多个备注这里面怎么加进去呢
                    orderDish.setRemark(dto.getRemark());//菜品备注要从缓存中取出
                    if(dto.getTasteId()!=null
                            &&dto.getTasteId()!=0)//菜品口味可以不选择,不选择的话为默认菜品口味
                        orderDish.setTasteId(dto.getTasteId());//设置菜品口味Id
                    orderDish.setIsPresentedDish(dto.getIsPresentedDish());
                    orderDishService.newOrderDish(orderDish);
                }
            }
            //下面的这两个顺序很重要,餐台在锁定的情况下不允许任何操作,所以要先给餐台解锁
            orderDishCacheService.tableLockRemove(tableId);//点击确认点菜的时候上了锁,返回和确认下单后都要把锁解除,否则不能继续点菜
            orderDishCacheService.cleanCacheByTableId(tableId);//清除掉菜品缓存
            cookTableCacheService.updateTableVersion(tableId);//更新餐桌版本,以便获后厨管理端获得新的餐桌版本后更新页面
        }catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

}
