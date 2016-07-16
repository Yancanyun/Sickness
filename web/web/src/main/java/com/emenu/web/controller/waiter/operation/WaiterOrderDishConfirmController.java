package com.emenu.web.controller.waiter.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.cache.order.OrderDishCache;
import com.emenu.common.cache.order.TableOrderCache;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishPackageDto;
import com.emenu.common.dto.order.MyOrderDto;
import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.entity.dish.DishImg;
import com.emenu.common.entity.dish.Unit;
import com.emenu.common.entity.order.Checkout;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.checkout.CheckOutStatusEnums;
import com.emenu.common.enums.dish.PackageStatusEnums;
import com.emenu.common.enums.order.*;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractAppBarController;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.*;

/**
 * 服务员确认下单
 *
 * @author quanyibo
 * @date 2016/7/15
 */
@Controller
@Module(ModuleEnums.WaiterOrderList)
@RequestMapping(value = URLConstants.WAITER_ORDER_DISH_CONFIRM_URL)
public class WaiterOrderDishConfirmController extends AbstractController{

    /**
     * 服务员端进入确认下单页
     *
     * @param
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    @Module(ModuleEnums.WaiterOrderList)
    @ResponseBody
    public JSONObject ajaxToConfirmPage(@RequestParam("tabldId")Integer tableId
            ,HttpServletRequest request)
    {
        Set<String> uniqueRemark = new HashSet<String>();//去除重复后的备注
        List<OrderDishCache> orderDishCache = new ArrayList<OrderDishCache>();
        TableOrderCache tableOrderCache = new TableOrderCache();//一个餐桌的订单缓存
        BigDecimal totalMoney = new BigDecimal(0);//已点菜品的总金额
        Table table = new Table();
        BigDecimal orderTotalMoney = new BigDecimal(0);//已下单未结账订单菜品的总金额
        JSONObject jsonObject = new JSONObject();
        JSONArray jsonArray = new JSONArray();
        try {

            // 检查餐台是否已开台
            if (tableService.queryStatusById(tableId) == TableStatusEnums.Disabled.getId()
                    || tableService.queryStatusById(tableId) == TableStatusEnums.Enabled.getId()) {
                return sendJsonObject(AJAX_FAILURE_CODE);
            }
            String customerIp = request.getRemoteAddr();//获取当前的用户Ip
            String currentOperateCustomerIp = orderDishCacheService.getCurrentOperateCustomerIp();
            if(currentOperateCustomerIp!=null&&customerIp.equals(currentOperateCustomerIp))
            {
                //有人正在进行确认下单操作且该用户刷新了页面,则解除锁
                orderDishCacheService.tableLockRemove(tableId);
                orderDishCacheService.setCurrentOperateCustomerIp(new String());//清空原来的Ip
            }
            table=tableService.queryById(tableId);//查询出餐台信息
            HashMap<Integer,Integer> packageFlagMap = new HashMap<Integer, Integer>();//用来判断套餐标识是否出现过
            tableOrderCache=orderDishCacheService.listByTableId(tableId);//已经点的餐桌菜品缓存
            if(tableOrderCache!=null)//若对应桌的订单缓存不为空
            {
                orderDishCache=tableOrderCache.getOrderDishCacheList();//获取一个餐桌的全部菜品缓存
                for(OrderDishCache dto :orderDishCache)
                {
                    JSONObject temp = new JSONObject();//临时变量
                    DishDto dishDto = dishService.queryById(dto.getDishId());//通过dishId查询出菜品的信息
                    Unit unit = unitService.queryById(dishDto.getUnitId());//查询出菜品的单位

                    temp.put("dishCacheId",dto.getId());
                    temp.put("dishName",dishDto.getName());
                    if(tasteService.queryById(dto.getTasteId())!=null)
                    temp.put("taste",tasteService.queryById(dto.getTasteId()).getName());
                    else
                    temp.put("taste","");
                    temp.put("salePrice",dishDto.getSalePrice());
                    temp.put("number",dto.getQuantity());
                    //先前计算总金额在循环中,后来写了一个方法直接调用即可
                    //totalMoney=totalMoney.add(new BigDecimal(dishDto.getSalePrice().floatValue()*dto.getQuantity()));
                    temp.put("remarks", dto.getRemark());
                    jsonArray.add(temp);
                }
                jsonObject.put("myOrderDtoList",jsonArray);
                jsonObject.put("personNum",table.getPersonNum());
                jsonObject.put("seatFee",table.getSeatFee());
                jsonObject.put("tableFee",table.getTableFee());
                totalMoney = orderDishCacheService.returnTotalMoneyByTableId(tableId);
                java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.00");//保留两位小数
                String totalMoneyTemp = myformat.format(totalMoney);
                totalMoney=new BigDecimal(totalMoneyTemp);//保留两位小数,不保留的话传给前端数字将会显示的非常的长
                jsonObject.put("totalMoney",totalMoney);
            }
        }
        catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendJsonObject(AJAX_FAILURE_CODE);
        }
        return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
    }

    /**
     * 服务员端点击加减按钮修改菜品数量
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/change",method = RequestMethod.GET)
    @Module(ModuleEnums.WaiterOrderDishChangeQuantity)
    @ResponseBody
    public JSONObject ajaxChangeDishQuantity(@RequestParam("tableId")Integer tableId
            ,@RequestParam("dishCacheId")Integer dishCacheId
            ,@RequestParam("changeStatus") Integer changeStatus)
    {
        List<OrderDishCache> orderDishCache = new ArrayList<OrderDishCache>();
        TableOrderCache tableOrderCache = new TableOrderCache();
        JSONObject jsonObject = new JSONObject();
        try {
            tableOrderCache = orderDishCacheService.listByTableId(tableId);
            if(tableOrderCache!=null)
                orderDishCache = tableOrderCache.getOrderDishCacheList();
            if(orderDishCache!=null)
            {
                if(tableOrderCache.getLock()==false)//餐桌未锁死
                {

                    OrderDishCache temp = new OrderDishCache();//临时变量
                    for(OrderDishCache dto :orderDishCache)
                    {
                        if(dto.getId()==dishCacheId)//id为缓存id,遍历找到对应的菜品缓存
                        {
                            temp=dto;
                            break;
                        }
                    }
                    if(temp==null)//该菜品可能被其它用户删除掉了
                        sendErrMsgAndErrCode(SSException.get(EmenuException.UpdateOrderDishFailed));
                    Float quantity = temp.getQuantity();//菜品数量
                    if(changeStatus==1)//改变状态为1的话为增加,为0的话为减少
                    {
                        temp.setQuantity(quantity+1);//修改菜品数量
                        orderDishCacheService.updateDish(tableId,temp);//更新菜品缓存
                    }
                    else
                    {
                        if(temp.getQuantity()>1)//数量大于1的话可以减少
                        {
                            temp.setQuantity(quantity-1);
                            orderDishCacheService.updateDish(tableId,temp);//更新菜品缓存
                        }
                        else if(temp.getQuantity()==1)//数量等于1的话减少1变为0即为删除
                        orderDishCacheService.delDish(tableId,dishCacheId);
                        else  sendErrMsgAndErrCode(SSException.get(EmenuException.QuantityCanNotReduce));
                    }
                    jsonObject.put("totalMoney",orderDishCacheService.returnTotalMoneyByTableId(tableId));
                }
                else sendErrMsgAndErrCode(SSException.get(EmenuException.TableIsLock));
            }
            else sendErrMsgAndErrCode(SSException.get(EmenuException.OrderDishCacheIsNull));

        }
        catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendJsonObject(AJAX_FAILURE_CODE);
        }
        return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
    }

    /**
     * 服务员端点输入菜品数量（直接修改菜品数量）
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/change/number",method = RequestMethod.POST)
    @Module(ModuleEnums.WaiterOrderDishChangeQuantity)
    @ResponseBody
    public JSONObject ajaxChangeDishNumber(@RequestParam("tableId")Integer tableId
            ,@RequestParam("dishCacheId")Integer dishCacheId
            ,@RequestParam("number") Float number)
    {
        List<OrderDishCache> orderDishCache = new ArrayList<OrderDishCache>();
        TableOrderCache tableOrderCache = new TableOrderCache();
        JSONObject jsonObject = new JSONObject();
        try {
            tableOrderCache = orderDishCacheService.listByTableId(tableId);
            if(tableOrderCache!=null)
                orderDishCache = tableOrderCache.getOrderDishCacheList();
            if(orderDishCache!=null)
            {
                if(tableOrderCache.getLock()==false)//餐桌未锁死
                {

                    OrderDishCache temp = new OrderDishCache();//临时变量
                    for(OrderDishCache dto :orderDishCache)
                    {
                        if(dto.getId()==dishCacheId)//id为缓存id,遍历找到对应的菜品缓存
                        {
                            temp=dto;
                            break;
                        }
                    }
                    if(temp==null)//该菜品可能被其它用户删除掉了
                        sendErrMsgAndErrCode(SSException.get(EmenuException.UpdateOrderDishFailed));
                    Float quantity = temp.getQuantity();//菜品数量
                    if(number>0)//数量大于0的话改变菜品数量
                    {
                        temp.setQuantity(number);//修改菜品数量
                        orderDishCacheService.updateDish(tableId,temp);//更新相应的菜品缓存
                    }
                    else if(number==0)//数量为0的话则删除
                    {
                        orderDishCacheService.delDish(tableId,dishCacheId);
                    }
                    else
                        sendErrMsgAndErrCode(SSException.get(EmenuException. QuantityIsNegative));
                    jsonObject.put("totalMoney",orderDishCacheService.returnTotalMoneyByTableId(tableId));
                }
                else sendErrMsgAndErrCode(SSException.get(EmenuException.TableIsLock));
            }
            else sendErrMsgAndErrCode(SSException.get(EmenuException.OrderDishCacheIsNull));
        }
        catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendJsonObject(AJAX_FAILURE_CODE);
        }
        return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
    }

    /**
     * 服务员端删除菜品
     *
     * @param
     * @return
     */
    @RequestMapping(value = "/delete",method = RequestMethod.GET)
    @Module(ModuleEnums.WaiterOrderDishDelete)
    @ResponseBody
    public JSONObject ajaxDeleteOrderDish(@RequestParam("tableId")Integer tableId
            ,@RequestParam("dishCacheId")Integer dishCacheId)
    {
        JSONObject jsonObject = new JSONObject();
        TableOrderCache tableOrderCache = new TableOrderCache();
        try
        {
            tableOrderCache = orderDishCacheService.listByTableId(tableId);
            if(tableOrderCache.getLock()==false)//餐桌未锁死
                orderDishCacheService.delDish(tableId,dishCacheId);
            else
                sendErrMsgAndErrCode(SSException.get(EmenuException.TableIsLock));
           jsonObject.put("totalMoney",orderDishCacheService.returnTotalMoneyByTableId(tableId));
        }
        catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
    }

    /**
     * 服务员端确认下单—完成
     * orderStatus为1的时候把缓存锁死,0的话解除
     * @param
     * @return
     */
    @RequestMapping(value = "/again",method = RequestMethod.POST)
    @Module(ModuleEnums.WaiterOrderConfirm)
    @ResponseBody
    public JSONObject ajaxConfirmOrderAgain(@RequestParam("tableId")Integer tableId
            ,@RequestParam("orderStatus")Integer orderStatus
            ,HttpServletRequest request)
    {
        TableOrderCache tableOrderCache = new TableOrderCache();
        List<OrderDishCache> orderDishCache = new ArrayList<OrderDishCache>();
        try
        {
            tableOrderCache = orderDishCacheService.listByTableId(tableId);
            if(tableOrderCache!=null)
                orderDishCache = tableOrderCache.getOrderDishCacheList();//已点菜品缓存
            //orderStatus为0的时候点击的是返回按钮
            if(tableOrderCache.getLock()==true&&orderStatus==1)//已经加上了锁且点击的为确认菜品，即存在其他人正在下单
                return sendErrMsgAndErrCode(SSException.get(EmenuException.TableIsLock));
            if(orderStatus==1)//锁死菜品缓存
            {
                //把当前正在操作的顾客的Ip地址记录下
                orderDishCacheService.setCurrentOperateCustomerIp(request.getRemoteAddr());
            }
            else//若点击了返回则解除锁死
            {
                orderDishCacheService.tableLockRemove(tableId);
            }
        }
        catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * 服务员端确认下单—确认下单
     * 清空缓存，插入数据库
     * @param
     * @return
     */
    @RequestMapping(value = "/finish",method = RequestMethod.POST)
    @Module(ModuleEnums.WaiterOrderConfirm)
    @ResponseBody
    public JSONObject ajaxConfirmOrder(@RequestParam("tableId")Integer tableId
            ,@RequestParam("personNum")Integer personNum
            ,@RequestParam("orderServeType") Integer orderServeType
            ,@RequestParam("orderRemark") String orderRemark)
    {
        //下单时间
        Date orderTime=new Date();
        try {
            Checkout checkout = new Checkout();
            checkout = checkoutServcie.queryByTableId(tableId, CheckOutStatusEnums.IsNotCheckOut.getId());//是否存在未结账的结账单
            //新增结账单到数据表
            if (checkout == null) {
                checkout = new Checkout();
                checkout.setTableId(tableId);
                //checkout.setCheckerPartyId();
                //checkout.setCheckoutTime();
                //checkout.setConsumptionMoney();
                //checkout.setConsumptionType();
                checkout.setCreatedTime(new Date());
                //checkout.setFreeRemarkId();
                //checkout.setIsFreeOrder();
                //checkout.setIsInvoiced();
                //checkout.setLastModifiedTime();
                //checkout.setPrepayMoney();
                //checkout.setShouldPayMoney();
                checkout.setStatus(CheckOutStatusEnums.IsNotCheckOut.getId());
                //checkout.setTotalPayMoney();
                //checkout.setWipeZeroMoney();
                checkoutServcie.newCheckout(checkout);//若不存在结帐单再生成新的结账单,存在的话不用新生成结账单
            }

            //新增订单到数据表
            Order order = new Order();
            order.setCheckoutId(checkout.getId());
            order.setCreatedTime(new Date());
            //order.setEmployeePartyId();
            //order.setLoginType();
            order.setOrderRemark(orderRemark);
            order.setOrderServeType(orderServeType);
            order.setStatus(OrderStatusEnums.IsBooked.getId());
            order.setTableId(tableId);
            order.setIsSettlemented(OrderSettlementStatusEnums.IsNotSettlement.getId());//订单是否被盘点过
            //order.setVipPartyId();
            orderService.newOrder(order);

            //新增订单菜品到数据表
            TableOrderCache tableOrderCache = new TableOrderCache();//菜品缓存
            List<OrderDishCache> orderDishCache = new ArrayList<OrderDishCache>();
            tableOrderCache=orderDishCacheService.listByTableId(tableId);
            if(tableOrderCache!=null)//若对应桌的订单缓存不为空
            {
                orderDishCache=tableOrderCache.getOrderDishCacheList();//获取一个餐桌的全部订单缓存
            }
            for(OrderDishCache dto :orderDishCache)
            {
                DishDto dishDto = new DishDto();
                //是套餐,把套餐拆成一个一个的菜品存到书库库里
                if(dishPackageService.judgeIsOrNotPackage(dto.getDishId())>0)//是套餐,把套餐拆成一个一个的菜品存到书库库里
                {
                    DishPackageDto dishPackageDto = new DishPackageDto();
                    //根据套餐Id查询出套餐具体信息,是套餐的话dishId就是对应的套餐Id
                    dishPackageDto=dishPackageService.queryDishPackageById(dto.getDishId());
                    List<DishDto> dishDtos = new ArrayList<DishDto>();
                    dishDtos=dishPackageDto.getChildDishDtoList();//获得套餐所有的子菜品
                    //将套餐拆成单个菜品存到数据库里
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
                        //套餐菜品数量为单个套餐中单个菜品的数量*套餐数量
                        orderDish.setDishQuantity(packageDto.getDishPackage().getDishQuantity().floatValue()*temp.floatValue());
                        orderDish.setOrderId(order.getId());
                        //根据套餐的子菜品的Id来获取菜品信息
                        dishDto = dishService.queryById(dto.getDishId());//应该查询的是套餐的信息
                        orderDish.setSalePrice(dishDto.getSalePrice());//整体套餐售价,而不是套餐中单个菜品售价
                        orderDish.setStatus(OrderDishStatusEnums.IsBooked.getId());//菜品状态：1-已下单；2-正在做；3-已上菜
                        orderDish.setDiscount(new BigDecimal(dishDto.getDiscount()));//折扣
                        if(dto.getServeType()==null
                                ||dto.getServeType()== ServeTypeEnums.NotSet.getId())//未设置单个菜品的上菜方式,则上菜方式为整单上菜方式
                            orderDish.setServeType(orderServeType);
                        else orderDish.setServeType(dto.getServeType());
                        orderDish.setOrderTime(orderTime);
                        orderDish.setIsCall(OrderDishCallStatusEnums.IsNotCall.getId());//是否被催菜
                        orderDish.setIsChange(OrderDishTableChangeStatusEnums.IsNotChangeTable.getId());//是否换了桌
                        orderDish.setRemark(dto.getRemark());//菜品备注要从缓存中取出
                        if(dto.getTasteId()!=null)//菜品口味可以不选择,不选择的话为默认菜品口味
                            orderDish.setTasteId(dto.getTasteId());//设置菜品口味Id
                        orderDish.setPackageFlag(packageFlag);//设置套餐标识
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
                        orderDish.setServeType(orderServeType);
                    else orderDish.setServeType(dto.getServeType());
                    orderDish.setOrderTime(orderTime);
                    orderDish.setIsCall(OrderDishCallStatusEnums.IsNotCall.getId());//是否被催菜
                    orderDish.setIsChange(OrderDishTableChangeStatusEnums.IsNotChangeTable.getId());//是否换了桌
                    //快捷点菜的时候不加菜品的备注,在详情页点菜可以给菜品加备注,但是如果对应多个备注这里面怎么加进去呢
                    orderDish.setRemark(dto.getRemark());//菜品备注要从缓存中取出
                    if(dto.getTasteId()!=null)//菜品口味可以不选择,不选择的话为默认菜品口味
                        orderDish.setTasteId(dto.getTasteId());//设置菜品口味Id
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
