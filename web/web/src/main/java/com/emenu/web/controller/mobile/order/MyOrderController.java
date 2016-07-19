package com.emenu.web.controller.mobile.order;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishPackageDto;
import com.emenu.common.dto.order.MyOrderDto;
import com.emenu.common.cache.order.OrderDishCache;
import com.emenu.common.cache.order.TableOrderCache;
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
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.*;

/**
 * MyOrderController
 * 我的订单Controller
 * @author: quanyibo
 * @time: 2016/6/2
 */

@Controller
@IgnoreLogin
@Module(ModuleEnums.MobileMyOrder)
@RequestMapping(value = URLConstants.MOBILE_MY_ORDER_URL)
public class MyOrderController  extends AbstractController {

    /**
     * 去我的订单首页
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.MobileMyOrderList)
    @RequestMapping(value = {"","/list"},method = RequestMethod.GET)
    public String toMyOrder(Model model,HttpSession httpSession
            ,HttpServletRequest request) {
        Set<String> uniqueRemark = new HashSet<String>();//去除重复后的备注
        List<OrderDishCache> orderDishCache = new ArrayList<OrderDishCache>();
        TableOrderCache tableOrderCache = new TableOrderCache();//一个餐桌的订单缓存
        List<MyOrderDto> myOrderDto = new ArrayList<MyOrderDto>();//数据传输对象
        BigDecimal totalMoney = new BigDecimal(0);//已点菜品的总金额
        Map<String,Integer> stringMap = new HashMap<String, Integer>();//用来存放已经有的菜品关联备注
        Table table = new Table();
        List<Order> orders = new ArrayList<Order>();//订单
        List<OrderDishDto> orderDishDtos= new ArrayList<OrderDishDto>();//所有的订单菜品
        BigDecimal orderTotalMoney = new BigDecimal(0);//已下单未结账订单菜品的总金额
        try {
            // 检查Session中是否存在TableId
            if (Assert.isNull(httpSession.getAttribute("tableId"))) {
                return MOBILE_SESSION_OVERDUE_PAGE;
            }

            // 从Session中获取TableID
            String str = httpSession.getAttribute("tableId").toString();
            Integer tableId = Integer.parseInt(str);

            // 检查餐台是否已开台
            if (tableService.queryStatusById(tableId) == TableStatusEnums.Disabled.getId()
                    || tableService.queryStatusById(tableId) == TableStatusEnums.Enabled.getId()) {
                return MOBILE_NOT_OPEN_PAGE;
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
            model.addAttribute("personNum",table.getPersonNum());//餐台实际人数
            model.addAttribute("seatPrice",table.getSeatFee());//餐位费用
            model.addAttribute("tablePrice",table.getTableFee());//餐台费用
            model.addAttribute("tableName",table.getName());//餐桌的名称

            orders = orderService.listByTableIdAndStatus(tableId,OrderStatusEnums.IsBooked.getId());//查询出对应餐桌所有已下单的订单,已结账的订单不显示
            if(orders!=null)//存在对应餐桌已下单的订单
            {
                for(Order orderDto:orders)
                {
                    Integer orderId= orderDto.getId();//订单Id
                    orderDishDtos.addAll(orderDishService.listDtoByOrderId(orderId));
                }
            }
            HashMap<Integer,Integer> packageFlagMap = new HashMap<Integer, Integer>();//用来判断套餐标识是否出现过
            for(OrderDishDto tempOrderDishDto :orderDishDtos)
            {
                if(tempOrderDishDto.getIsPackage()== PackageStatusEnums.IsNotPackage.getId()
                        &&tempOrderDishDto.getStatus()!= OrderDishStatusEnums.IsBack.getId())//非套餐，status为4的时候为退菜,退了的菜不做处理
                {
                    //设置一下定价,宝荣写的类里面没有定价属性，但是定价属性可以通过售价和折扣计算得到
                    tempOrderDishDto.setPrice();
                    DishDto dishDtoTemp = dishService.queryById(tempOrderDishDto.getDishId());//通过dishId查询出菜品的信息
                    Unit unitTemp = unitService.queryById(dishDtoTemp.getUnitId());//查询出菜品的单位
                    if(unitTemp!=null)//若菜品单位不为空
                        tempOrderDishDto.setUnitName(unitService.queryById(unitTemp.getId()).getName());

                    //设置一下图片路径,宝荣写的里面没有图片路径
                    DishImg dishImg =  new DishImg();
                    dishImg=dishService.queryById(tempOrderDishDto.getDishId()).getSmallImg();
                    if(dishImg!=null)//图片可能为空，为空的话则没有图片路径，否则直接取出图片路径回是空指针异常
                        tempOrderDishDto.setImgPath(dishImg.getImgPath());
                }
                else if(tempOrderDishDto.getIsPackage()==PackageStatusEnums.IsPackage.getId()
                        &&tempOrderDishDto.getStatus()!=OrderDishStatusEnums.IsBack.getId())//套餐的话会有重复，在数据库里套餐被拆成菜品
                {
                    if(packageFlagMap.get(tempOrderDishDto.getPackageFlag())==null)//没有出现过的套餐
                    {
                        packageFlagMap.put(tempOrderDishDto.getPackageFlag(),1);//标记为出现过
                        //设置一下定价,宝荣写的类里面没有定价属性，但是定价属性可以通过售价和折扣计算得到
                        tempOrderDishDto.setPrice();
                        DishDto dishDtoTemp = dishService.queryById(tempOrderDishDto.getPackageId());//通过packageId查询出菜品的信息
                        tempOrderDishDto.setDishName(dishDtoTemp.getName());//原本的话套餐显示是单个菜品名字,这里要重新设置一下,设置成显示套餐的名字
                        Unit unitTemp = unitService.queryById(dishDtoTemp.getUnitId());//查询出菜品的单位
                        if(unitTemp!=null)//若菜品单位不为空
                            tempOrderDishDto.setUnitName(unitService.queryById(unitTemp.getId()).getName());

                        //设置一下图片路径,宝荣写的里面没有图片路径
                        DishImg dishImg =  new DishImg();
                        //套餐的话packageId对应的才是数据库t_dish表里面的主键
                        dishImg=dishService.queryById(tempOrderDishDto.getPackageId()).getSmallImg();
                        if(dishImg!=null)//图片可能为空，为空的话则没有图片路径，否则直接取出图片路径回是空指针异常
                            tempOrderDishDto.setImgPath(dishImg.getImgPath());
                    }
                }
            }
            //一个套餐只留下一条记录,例如一个套餐包含三个菜,给前端传递过去的时候只给传一个菜
            List<OrderDishDto> sendOrderDishDtos = new ArrayList<OrderDishDto>();
            int ok = -1;//记录套餐的packageFlag
            for(OrderDishDto tempOrderDishDto :orderDishDtos)
            {
                if(packageFlagMap.get(tempOrderDishDto.getPackageFlag())!=null
                        &&tempOrderDishDto.getPackageFlag()>0
                        &&ok!=tempOrderDishDto.getPackageFlag()
                        &&tempOrderDishDto.getStatus()!=OrderDishStatusEnums.IsBack.getId())//为4的话为退了的菜
                {
                    ok = tempOrderDishDto.getPackageFlag();
                    sendOrderDishDtos.add(tempOrderDishDto);
                }
                else if(packageFlagMap.get(tempOrderDishDto.getPackageFlag())==null
                        &&tempOrderDishDto.getStatus()!=OrderDishStatusEnums.IsBack.getId())//为4的话为退了的菜
                {
                    sendOrderDishDtos.add(tempOrderDishDto);
                }
            }
            orderTotalMoney = orderService.returnOrderTotalMoney(tableId);
            java.text.DecimalFormat myformat=new java.text.DecimalFormat("0.00");//保留两位小数
            String orderTotalMoneyTemp = myformat.format(orderTotalMoney);
            orderTotalMoney=new BigDecimal(orderTotalMoneyTemp);//保留两位小数,不保留的话传给前端数字将会显示的非常的长
            model.addAttribute("orderTotalMoney",orderTotalMoney);//已下单订单菜品总金额
            model.addAttribute("orderDishDto",sendOrderDishDtos);//已下单的订单菜品

            tableOrderCache=orderDishCacheService.listByTableId(tableId);
            if(tableOrderCache!=null)//若对应桌的订单缓存不为空
            {
                orderDishCache=tableOrderCache.getOrderDishCacheList();//获取一个餐桌的全部订单缓存
                for(OrderDishCache dto :orderDishCache)
                {
                    DishDto dishDto = dishService.queryById(dto.getDishId());//通过dishId查询出菜品的信息
                    Unit unit = unitService.queryById(dishDto.getUnitId());//查询出菜品的单位
                    if(remarkService.queryDishRemarkByDishId(dto.getDishId())!=null)//查询出的菜品相关备注不为空
                    {
                        List<String> tempStr = new ArrayList<String>();//临时变量
                        tempStr=remarkService.queryDishRemarkByDishId(dto.getDishId());//查询菜品关联的菜品备注
                        for(String remarkStr : tempStr)
                        {
                            if(stringMap.get(remarkStr)==null)//不存在这个备注则加到集合中去
                            {
                                stringMap.put(remarkStr,1);
                                uniqueRemark.add(remarkStr);//将菜品的相关备注加入到集合中
                            }
                        }
                    }
                    MyOrderDto temp = new MyOrderDto();//临时变量
                    temp.setDishId(dto.getDishId());//菜品id
                    temp.setName(dishDto.getName());//菜品名称
                    temp.setCount(dto.getQuantity().intValue());//缓存中的单个菜品数量
                    temp.setOrderDishCacheId(dto.getId());//缓存id
                    temp.setPrice(dishDto.getPrice());//菜品定价
                    temp.setSalePrice(dishDto.getSalePrice());//菜品售价
                    temp.setRemark(dto.getRemark());//缓存中的备注
                    temp.setUnit(unit);
                    temp.setSmallImg(dishDto.getSmallImg());
                    if(temp.getSmallImg()!=null)//存在小图则设置小图的路径
                    temp.setImgPath(dishDto.getSmallImg().getImgPath());//菜品小图路径
                    temp.setUnitName(unit.getName());//菜品单位名称
                    if(dto.getTasteId()!=null)//选择的菜品口味,没有选择菜品口味的话传递的是null值会报错
                    temp.setTaste(tasteService.queryById(dto.getTasteId()));//菜品口味,菜品详情页选择的菜品口味只能选择一个
                    //之前返回已点菜品总金额的话用的是下面这句话,后来改写成调用一个方法返回
                    //totalMoney=totalMoney.add(new BigDecimal(temp.getCount()*temp.getSalePrice().doubleValue()));//菜品数量乘以菜品单价
                    myOrderDto.add(temp);//price要转换成double类型
                }
            }
            model.addAttribute("uniqueRemark",uniqueRemark);//菜品关联的菜品备注
            model.addAttribute("myOrderDto",myOrderDto);//已经点了的缓存中的菜品
            model.addAttribute("tableId",tableId);//餐桌号
            totalMoney = orderDishCacheService.returnTotalMoneyByTableId(tableId);
            String totalMoneyTemp = myformat.format(totalMoney);
            totalMoney=new BigDecimal(totalMoneyTemp);//保留两位小数,不保留的话传给前端数字将会显示的非常的长
            model.addAttribute("totalMoney",totalMoney);//已经点的菜品的总金额
        }
        catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return MOBILE_NOT_FOUND_PAGE;
        }
        return "mobile/order/order_list_home";
    }

    /**
     * ajax删除单个菜品缓存
     * 若正在下单则即餐桌锁死的情况不允许操作
     * @param orderDishCacheId
     * @return
     */
    @Module(ModuleEnums.MobileMyOrderDel)
    @RequestMapping(value = "/ajax/del/order/cache",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxDelOrderCache(@RequestParam("deleteDishId") Integer orderDishCacheId
            ,HttpSession httpSession)//传过来的deleteDishId实际上是菜品缓存的Id
    {
        String str = httpSession.getAttribute("tableId").toString();
        Integer tableId = Integer.parseInt(str);
        TableOrderCache tableOrderCache = new TableOrderCache();
        try
        {
            tableOrderCache = orderDishCacheService.listByTableId(tableId);
            if(tableOrderCache.getLock()==false)//餐桌未锁死
            orderDishCacheService.delDish(tableId,orderDishCacheId);
            else
            sendErrMsgAndErrCode(SSException.get(EmenuException.TableIsLock));
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }
        catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax修改单个菜品数量
     * 若正在下单则即餐桌锁死的情况不允许操作
     * @param changeStatus,id
     *
     * @return
     */
    @Module(ModuleEnums.MobileMyOrderQuantityChange)
    @RequestMapping(value = "/ajax/dish/quantity/change",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxDishQuantityChange(@RequestParam("changeStatus") Integer changeStatus
            ,@RequestParam("id")Integer id,HttpSession httpSession)
    {
        String str = httpSession.getAttribute("tableId").toString();
        Integer tableId = Integer.parseInt(str);
        List<OrderDishCache> orderDishCache = new ArrayList<OrderDishCache>();
        TableOrderCache tableOrderCache = new TableOrderCache();
        try
        {
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
                        if(dto.getId()==id)//id为缓存id,遍历找到对应的菜品缓存
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
                        orderDishCacheService.updateDish(tableId,temp);//更新相应的菜品缓存
                    }
                    else
                    {
                        if(temp.getQuantity()>1)//数量为1的话没办法再减少,但是可以删除
                        {
                            temp.setQuantity(quantity-1);
                            orderDishCacheService.updateDish(tableId,temp);
                        }
                    }
                    orderDishCacheService.updateDish(tableId,temp);
                }
                else sendErrMsgAndErrCode(SSException.get(EmenuException.TableIsLock));
            }
            else sendErrMsgAndErrCode(SSException.get(EmenuException.OrderDishCacheIsNull));

            return sendJsonObject(AJAX_SUCCESS_CODE);
        }
        catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax返回确认下单的菜品的总金额
     * 同时把缓存锁死
     * 存在问题(已解决)刷新页面后餐台的锁没有被解除
     * @param orderStatus
     *
     * @return
     */
    @Module(ModuleEnums.MobileMyOrderTotalMoney)
    @RequestMapping(value = "/ajax/return/money",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxReturnMoney(@RequestParam("orderStatus") Integer orderStatus
            ,HttpSession httpSession
            ,Model model
            ,HttpServletRequest request)
    {
        String str = httpSession.getAttribute("tableId").toString();
        Integer tableId = Integer.parseInt(str);
        List<OrderDishCache> orderDishCache = new ArrayList<OrderDishCache>();
        TableOrderCache tableOrderCache = new TableOrderCache();
        BigDecimal totalMoney = new BigDecimal(0);//总金额
        JSONObject jsonObject = new JSONObject();
        try
        {
            tableOrderCache = orderDishCacheService.listByTableId(tableId);
            if(tableOrderCache!=null)
            orderDishCache = tableOrderCache.getOrderDishCacheList();//已点菜品缓存
            else//菜品缓存为空,则没有必要展示给用户空的列表
            {
                return sendErrMsgAndErrCode(SSException.get(EmenuException.OrderDishCacheIsNull));
            }
            //orderStatus为0的时候点击的是返回按钮
            if(tableOrderCache.getLock()==true&&orderStatus==1)//已经加上了锁且点击的为确认菜品，即存在其他人正在下单
                return sendErrMsgAndErrCode(SSException.get(EmenuException.TableIsLock));
            for(OrderDishCache dto :orderDishCache)
            {
                DishDto dishDto = dishService.queryById(dto.getDishId());//通过dishId查询出菜品的信息
                totalMoney=totalMoney.add(new BigDecimal(dto.getQuantity()*dishDto.getSalePrice().doubleValue()));
            }
            if(orderStatus==1)//锁死菜品缓存
            {
                //把当前正在操作的顾客的Ip地址记录下
                orderDishCacheService.setCurrentOperateCustomerIp(request.getRemoteAddr());
                orderDishCacheService.tableLock(tableId);
                jsonObject.put("customPrice", totalMoney);
            }
            else//若点击了返回则解除锁死
            {
                orderDishCacheService.tableLockRemove(tableId);
                return sendJsonObject(AJAX_SUCCESS_CODE);
            }
        }
        catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
    }

    /**
     * ajax确认下单
     *
     * @param
     * @return
     */
    @Module(ModuleEnums.MobileMyOrderList)
    @RequestMapping(value = "/ajax/confirm/order" ,method = RequestMethod.POST)
    public JSONObject confirmOrder (@RequestParam("confirmDishId")List<Integer> confirmDishId
            ,@RequestParam("confirmDishNumber")List<Float> confirmDishNumber
            ,@RequestParam("serviceWay")Integer serviceWay
            ,@RequestParam("confirmOrderRemark") String confirmOrderRemark
            ,HttpSession httpSession)
    {
        //下单时间
        Date orderTime=new Date();
        //获取桌子号
        String tableIdStr = httpSession.getAttribute("tableId").toString();
        Integer tableId = Integer.parseInt(tableIdStr);
        try {
            Checkout checkout = new Checkout();
            checkout = checkoutService.queryByTableId(tableId, CheckOutStatusEnums.IsNotCheckOut.getId());//是否存在未结账的结账单
            //新增结账单到数据表
            if (checkout == null) {
                checkout = new Checkout();
                checkout.setTableId(tableId);
                checkout.setCreatedTime(new Date());
                checkout.setStatus(CheckOutStatusEnums.IsNotCheckOut.getId());
                checkoutService.newCheckout(checkout);//若不存在结帐单再生成新的结账单,存在的话不用新生成结账单
                // 获取到新生成的结账单,主要是要获取到checkOut的Id这个属性
                checkout = checkoutService.queryByTableId(tableId, CheckOutStatusEnums.IsNotCheckOut.getId());
            }

            //新增订单到数据表
            Order order = new Order();
            order.setCheckoutId(checkout.getId());
            order.setCreatedTime(new Date());
            //order.setEmployeePartyId();
            //order.setLoginType();
            order.setOrderRemark(confirmOrderRemark);
            order.setOrderServeType(serviceWay);
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
                            orderDish.setServeType(serviceWay);
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
                        orderDish.setServeType(serviceWay);
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
