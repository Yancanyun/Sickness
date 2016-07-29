package com.emenu.web.controller.cook;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.entity.dish.Unit;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.dish.PackageStatusEnums;
import com.emenu.common.enums.order.OrderDishStatusEnums;
import com.emenu.common.enums.order.OrderStatusEnums;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * OrderManagementController
 * 后厨管理Controller
 * @author: quanyibo
 * @time: 2016/6/22
 */

@Controller
@Module(ModuleEnums.Cook)
@RequestMapping(value = URLConstants.COOK_ORDER_MANAGEMENT_URL)
public class OrderManagementController extends AbstractController {

    /**
     * 去后厨管理主页面
     * @return
     */
    @Module(ModuleEnums.CookOrderList)
    @RequestMapping(value = {"","/list"},method = RequestMethod.GET)
    public String toOrderManagementPage(HttpSession httpSession,Model model) {

        return "cook/order_management";
    }

    /**
     * 去上菜扫码页（划单）
     * @return
     */
    @Module(ModuleEnums.CookOrderDishWipe)
    @RequestMapping(value = "/wipe",method = RequestMethod.GET)
    public String toWipePage(){
        return "cook/wipe";
    }

    /**
     * ajax获取所有餐台的信息
     * @return
     */
    @Module(ModuleEnums.CookOrderTableList)
    @RequestMapping(value = "/ajax/tables/version",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxGetAllTable()
    {
        JSONObject jsonObject = new JSONObject();
        try {
           jsonObject = cookTableCacheService.getAllTableVersion();
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
    }

    /**
     * ajax根据桌号获取餐台的订单及订单菜品信息
     * @param tableId
     * @return
     */
    @Module(ModuleEnums.CookOrderTableList)
    @RequestMapping(value = "/ajax/{tableId}",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxListAllOrderDish(@PathVariable("tableId")Integer tableId)
    {
        List<Order> orders = new ArrayList<Order>();
        List<OrderDish> orderDishs = new ArrayList<OrderDish>();
        Table table = new Table();
        JSONObject jsonObject = new JSONObject();
        try {
            table=tableService.queryById(tableId);//获取餐桌信息
            jsonObject.put("name",table.getName());//餐桌名称
            jsonObject.put("version",cookTableCacheService.getVersionByTableId(tableId));//餐桌版本
            orders=orderService.listByTableIdAndStatus(tableId, OrderStatusEnums.IsBooked.getId());//获取到已下单未结账的订单
            JSONArray all = new JSONArray();
            for(Order order : orders)
            {
                JSONArray jsonArray = new JSONArray();//存放订单的所有菜品
                if(orderDishService.isOrderHaveOrderDish(order.getId())>0)//订单中有未上的菜则显示订单
                {
                    orderDishs = orderDishService.listByOrderId(order.getId());//获取订单菜品
                    for(OrderDish orderDish :orderDishs)
                    {
                        if(orderDish.getStatus()== OrderDishStatusEnums.IsBooked.getId()
                                ||orderDish.getStatus()==OrderDishStatusEnums.IsMake.getId())//显示已经下单和正在做的菜品
                        {
                            JSONObject temp = new JSONObject();
                            DishDto dishDto = dishService.queryById(orderDish.getDishId());//查询出菜品信息
                            Unit unit = unitService.queryById(dishDto.getUnitId());//查询菜品单位信息
                            temp.put("id",orderDish.getId());
                            temp.put("name",dishDto.getName());
                            temp.put("state",orderDish.getStatus());//菜品状态,问下学姐以上菜的菜品显示不显示
                            temp.put("num",orderDish.getDishQuantity());
                            temp.put("unitName",unit.getName());//菜品单位名称
                            if(orderDish.getIsPackage()== PackageStatusEnums.IsNotPackage.getId())
                                temp.put("bigTagName",tagService.queryLayer2TagByDishId(orderDish.getDishId()).getName());//菜品二级分类,菜品的大类
                            else//是套餐
                                temp.put("bigTagName",tagService.queryLayer2TagByDishId(orderDish.getPackageId()).getName());//菜品二级分类,菜品的大类
                            temp.put("isCall",orderDish.getIsCall());
                            temp.put("isChange",orderDish.getIsChange());
                            temp.put("serveType",orderDish.getServeType());

                            jsonArray.add(temp);
                        }
                    }
                    JSONObject temp = new JSONObject();
                    temp.put("orderDishes",jsonArray);
                    temp.put("id",order.getId());//订单id
                    //现在时间和订单创建时间的差值,为毫秒数,再除以1000转换成秒,再除以60换算成分钟数
                    temp.put("time",order.getCreatedTime().getTime());//返回订单时间,前端计算把分钟数显示出来
                    temp.put("remark",order.getOrderRemark());//订单备注
                    all.add(temp);//作为一个订单的整体放到JSONArray里面
                }
            }
            jsonObject.put("orders",all);//所有订单
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
    }

    /**
     * 打印菜品
     * @param orderDishId
     * @return
     */
    @Module(ModuleEnums.CookPrintOrderDish)
    @RequestMapping(value = "/ajax/print" ,method = RequestMethod.POST)
    @ResponseBody
    public  JSONObject ajaxPrintOrderDishById(@RequestParam("orderDishId") Integer orderDishId){

        try {
            orderDishPrintService.printOrderDishById(orderDishId);
            //orderDishPrintService.getPrintOrderDishDtoById(orderDishId);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * 上菜扫码(划单)
     * @param orderDishId
     * @return
     */
    @Module(ModuleEnums.CookOrderDishWipe)
    @RequestMapping(value = "/ajax/wipe" ,method = RequestMethod.POST)
    @ResponseBody
    public  JSONObject ajaxWipe(@RequestParam("orderDishId") Integer orderDishId){
        try {
            orderDishService.wipeOrderDish(orderDishId);
            //划单后要对餐桌是否还有未上的菜品进行判断
            if(orderDishService.isTableHaveOrderDish(orderDishService.queryOrderDishTableId(orderDishId))==0)
            {
                //判断这个餐桌是否还有菜品,没有的话餐台版本号清空
                cookTableCacheService.deleteTable(orderDishService.queryOrderDishTableId(orderDishId));
            }
            else
            {
                //否则更新餐桌版本号
                cookTableCacheService.updateTableVersion(orderDishService.queryOrderDishTableId(orderDishId));
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }
}
