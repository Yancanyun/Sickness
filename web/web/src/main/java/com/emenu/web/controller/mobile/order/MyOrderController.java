package com.emenu.web.controller.mobile.order;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.order.MyOrderDto;
import com.emenu.common.cache.order.OrderDishCache;
import com.emenu.common.cache.order.TableOrderCache;
import com.emenu.common.dto.table.AreaDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.dish.Unit;
import com.emenu.common.entity.order.Checkout;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.entity.remark.Remark;
import com.emenu.common.entity.table.Area;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.service.dish.DishService;
import com.emenu.service.dish.UnitService;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.java2d.opengl.OGLDrawImage;
import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
    public String toMyOrder(Model model,HttpSession httpSession)
    {
        List<String> remark = new ArrayList<String>();//备注
        List<OrderDishCache> orderDishCache = new ArrayList<OrderDishCache>();
        TableOrderCache tableOrderCache = new TableOrderCache();//一个餐桌的全部订单缓存
        List<MyOrderDto> myOrderDto = new ArrayList<MyOrderDto>();//数据传输对象
        String str = httpSession.getAttribute("tableId").toString();
        Integer tableId = Integer.parseInt(str);
        BigDecimal totalMoney = new BigDecimal(0);//已点菜品的总金额
        Table table = new Table();
        try
        {
            table=tableService.queryById(tableId);//查询出餐台信息
            //remark=remarkService.listAll();//查询出菜品的备注，不是全部备注
           // model.addAttribute("remark",remark);
            model.addAttribute("personNum",table.getPersonNum());//餐台实际人数
            model.addAttribute("seatPrice",table.getSeatFee());//餐位费用
            model.addAttribute("tablePrice",table.getTableFee());//餐台费用
            model.addAttribute("tableName",table.getName());//餐桌的名称
            tableOrderCache=orderDishCacheService.listByTableId(tableId);
            if(tableOrderCache!=null)//若对应桌的订单缓存不为空
            {
                orderDishCache=tableOrderCache.getOrderDishCacheList();//获取一个餐桌的全部订单缓存
                for(OrderDishCache dto :orderDishCache)
                {
                    DishDto dishDto = dishService.queryById(dto.getDishId());//通过dishId查询出菜品的信息
                    Unit unit = unitService.queryById(dishDto.getUnitId());//查询出菜品的单位
                    MyOrderDto temp = new MyOrderDto();//临时变量
                    temp.setDishId(dto.getDishId());//菜品id
                    temp.setName(dishDto.getName());//菜品名称
                    temp.setCount(dto.getQuantity());//缓存中的单个菜品数量
                    temp.setOrderDishCacheId(dto.getId());//缓存id
                    temp.setPrice(dishDto.getPrice());//菜品定价
                    temp.setSalePrice(dishDto.getSalePrice());//菜品售价
                    temp.setRemark(dto.getRemark());//缓存中的备注
                    temp.setUnit(unit);
                    temp.setSmallImg(dishDto.getSmallImg());
                    if(temp.getSmallImg()!=null)//存在小图则设置小图的路径
                    temp.setImgPath(dishDto.getSmallImg().getImgPath());//菜品小图路径
                    temp.setUnitName(unit.getName());//菜品单位名称
                    temp.setTasteList(dishDto.getTasteList());//菜品口味
                    totalMoney=totalMoney.add(new BigDecimal(temp.getCount()*temp.getPrice().doubleValue()));//菜品数量乘以菜品单价
                    myOrderDto.add(temp);                                   //price要转换成double类型
                }
            }
            model.addAttribute("myOrderDto",myOrderDto);//已经点了的缓存中的菜品
            model.addAttribute("tableId",tableId);//餐桌号
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
     *
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
        try
        {
            orderDishCacheService.delDish(tableId,orderDishCacheId);
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
     *
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
            orderDishCache = tableOrderCache.getOrderDishCacheList();
            OrderDishCache temp = new OrderDishCache();//临时变量
            temp=orderDishCache.get(id-1);//
            Integer quantity = temp.getQuantity();//菜品数量
            if(changeStatus==1)//改变状态为1的话为增加,为0的话为减少
            temp.setQuantity(quantity+1);//修改菜品数量
            else
            {
                if(temp.getQuantity()>1)//数量为1的话没办法再减少,但是可以删除
                    temp.setQuantity(quantity-1);
            }
            orderDishCacheService.updateDish(tableId,temp);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }
        catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 表单提交进行下单
     *
     * @param
     * @return
     */
    @Module(ModuleEnums.MobileMyOrderList)
    @RequestMapping(value = "mobile/confirm/order" ,method = RequestMethod.POST)
    public JSONObject confirmOrder (@RequestParam("confirmDishId")List<Integer> confirmDishId
            ,@RequestParam("confirmDishNumber")List<Integer> confirmDishNumber
            ,@RequestParam("serviceWay")Integer serviceWay
            ,@RequestParam("confirmOrderRemark") String confirmOrderRemark
            ,HttpSession httpSession)
    {
        String tableIdStr = httpSession.getAttribute("tableId").toString();
        Integer tableId = Integer.parseInt(tableIdStr);

            Checkout checkout = null;
            try {
                checkout = checkoutServcie.queryByTableId(tableId,0);
            } catch (SSException e) {
                LogClerk.errLog.error(e);
                sendErrMsg(e.getMessage());
                return sendErrMsgAndErrCode(e);
            }
            //新增结账单到数据表
            if(checkout==null)
            {
                checkout=new Checkout();
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
                checkout.setStatus(0);
                //checkout.setTotalPayMoney();
                //checkout.setWipeZeroMoney();

                try {
                    checkoutServcie.newCheckout(checkout);
                } catch (SSException e) {
                    LogClerk.errLog.error(e);
                    sendErrMsg(e.getMessage());
                    return sendErrMsgAndErrCode(e);
                }
            }



            //新增订单到数据表
            Order order=new Order();
            order.setCheckoutId(checkout.getId());
            order.setCreatedTime(new Date());
            //order.setEmployeePartyId();
            //order.setLastModifiedTime();
            //order.setLoginType();
            order.setOrderRemark(confirmOrderRemark);
            order.setOrderServeType(serviceWay);
            order.setStatus(1);
            order.setTableId(tableId);
            //order.setVipPartyId();


            try {
                orderService.newOrder(order);
            } catch (SSException e) {
                LogClerk.errLog.error(e);
                sendErrMsg(e.getMessage());
                return sendErrMsgAndErrCode(e);
            }

            //新增订单菜品到数据表
            OrderDish orderDish=new OrderDish();

            for(int i=0;i<confirmDishId.size();i++){
                //
                orderDish.setCreatedTime(new Date());
                orderDish.setDishId(confirmDishId.get(i));
                //设置菜品数量
                Integer temp = confirmDishNumber.get(i);
                String dishQuantityStr=temp+"";
                Float dishQuantity=Float.parseFloat(dishQuantityStr);

                orderDish.setDishQuantity(dishQuantity);
                orderDish.setOrderId(order.getId());
                orderDish.setServeType(serviceWay);
                //orderDish.setRemark();
                try {
                    orderDishService.newOrderDish(orderDish);
                } catch (SSException e) {
                    LogClerk.errLog.error(e);
                    sendErrMsg(e.getMessage());
                    return sendErrMsgAndErrCode(e);
                }
            }
         return    sendJsonObject(AJAX_SUCCESS_CODE);
    }
}
