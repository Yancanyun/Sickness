package com.emenu.web.controller.waiter.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.dish.Taste;
import com.emenu.common.entity.order.BackDish;
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
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.List;

/**
 * 服务员查单
 *
 * @author chenyuting
 * @date 2016/7/21 15:11
 */
@Controller
@Module(ModuleEnums.WaiterQueryCheckout)
@RequestMapping(value = URLConstants.WAITER_QUERY_CHECKOUT_URL)
public class WaiterQueryCheckoutController extends AbstractController {

    /**
     * 查看餐桌菜品信息
     * @param tableId
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject queryCheckoutList(@RequestParam("tableId") Integer tableId){
        JSONObject jsonObject = new JSONObject();
        try{
            // 查询本餐台所有的订单菜品
            List<OrderDishDto> orderDishDtoList = orderDishService.queryOrderDishListByTableId(tableId);

            // 消费清单列表
            JSONArray orderDishList = new JSONArray();
            if (Assert.isNotNull(orderDishDtoList)) {
                for(OrderDishDto orderDishDto : orderDishDtoList) {
                    JSONObject orderDishJsonObject = new JSONObject();
                    if(orderDishDto.getIsPackage() == PackageStatusEnums.IsPackage.getId()){
                        // 查询套餐信息
                        DishDto dishDto = dishService.queryById(orderDishDto.getPackageId());
                        orderDishJsonObject.put("assistantCode", dishDto.getAssistantCode());
                        orderDishJsonObject.put("unitName",dishDto.getUnitName());
                        // 判断整个套餐的状态
                        orderDishJsonObject.put("dishStatus",orderDishService.queryPackageStatusByFirstOrderDishId(orderDishDto.getId()).getId());
                    }else {
                        // 查询菜品信息
                        DishDto dishDto = dishService.queryById(orderDishDto.getDishId());
                        orderDishJsonObject.put("assistantCode", dishDto.getAssistantCode());
                        orderDishJsonObject.put("unitName",dishDto.getUnitName());
                        orderDishJsonObject.put("dishStatus",orderDishDto.getStatus());

                    }
                    orderDishJsonObject.put("salePrice", orderDishDto.getSalePrice());
                    orderDishJsonObject.put("dishName", orderDishDto.getDishName());
                    orderDishJsonObject.put("orderDishId", orderDishDto.getId());
                    orderDishJsonObject.put("number", orderDishDto.getDishQuantity());
                    if (orderDishDto.getTasteName() == null) {
                        orderDishJsonObject.put("taste", "");
                    } else {
                        orderDishJsonObject.put("taste", orderDishDto.getTasteName());
                    }
                    orderDishJsonObject.put("remarks",orderDishDto.getRemark());
                    orderDishJsonObject.put("serveType",orderDishDto.getServeType());
                    orderDishList.add(orderDishJsonObject);
                }
                jsonObject.put("orderDishList",orderDishList);

                // 退菜清单 根据餐台查询所有的order,再根据orderId去查询退菜
                JSONArray  backDishList = new JSONArray();
                // 根据餐台和状态查询所有未结账订单
                List<Order> orderList = orderService.listByTableIdAndStatus(tableId, OrderStatusEnums.IsBooked.getId());
                for (Order order: orderList){
                    // 根据订单id查询退菜列表
                    List<BackDish> backDishChildrenList = backDishService.queryBackDishListByOrderId(order.getId());
                    if (!backDishChildrenList.isEmpty()){
                        // 退菜列表不为空，加入jsonObject中
                        for (BackDish backDish:backDishChildrenList){
                            JSONObject backDishJsonObject = new JSONObject();

                            // 查询订单菜品信息
                            OrderDish orderDish = orderDishService.queryById(backDish.getOrderDishId());
                            if (orderDish.getIsPackage() == PackageStatusEnums.IsPackage.getId()){
                                // 查询套餐信息
                                DishDto dishDto = dishService.queryById(orderDish.getPackageId());
                                backDishJsonObject.put("dishName",dishDto.getName());
                                backDishJsonObject.put("assistantCode",dishDto.getAssistantCode());
                                backDishJsonObject.put("unitName",dishDto.getUnitName());
                                backDishJsonObject.put("salePrice",orderDish.getSalePrice());
                            }else {
                                // 查询菜品信息
                                DishDto dishDto = dishService.queryById(orderDish.getDishId());
                                backDishJsonObject.put("dishName",dishDto.getName());
                                backDishJsonObject.put("assistantCode",dishDto.getAssistantCode());
                                backDishJsonObject.put("unitName",dishDto.getUnitName());
                                backDishJsonObject.put("salePrice",orderDish.getSalePrice());
                            }

                            // 查询退菜口味信息
                            Taste taste = tasteService.queryById(backDish.getTasteId());
                            backDishJsonObject.put("number",backDish.getBackNumber());
                            if (taste != null){
                                backDishJsonObject.put("taste",taste.getName());
                            }else {
                                backDishJsonObject.put("taste","");
                            }
                            backDishJsonObject.put("dishStatus", OrderDishStatusEnums.IsBack.getId());
                            backDishJsonObject.put("backTime",backDish.getBackTimeStr());
                            backDishList.add(backDishJsonObject);
                        }
                    }
                }
                jsonObject.put("backDishList",backDishList);
                // 餐台信息
                Table table = tableService.queryById(tableId);
                jsonObject.put("personNum",table.getPersonNum());
                jsonObject.put("seatFee",table.getSeatFee());
                jsonObject.put("tableFee",table.getTableFee());

                // 总金额
                BigDecimal totalMoney = orderService.returnOrderTotalMoney(tableId);
                jsonObject.put("totalMoney",totalMoney);
            }
            return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 修改餐台人数
     * @param tableId
     * @param personNum
     * @return
     */
    @RequestMapping(value = "person",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updatePerson(@RequestParam("tableId") Integer tableId,
                                   @RequestParam("personNum") Integer personNum){
        try{
            TableDto tableDto = tableService.queryTableDtoById(tableId);
            Table table = tableDto.getTable();
            table.setPersonNum(personNum);
            tableDto.setTable(table);
            tableService.forceUpdateTable(tableId,tableDto);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 催菜
     * @param orderDishId
     * @return
     */
    @RequestMapping(value = "call/dish",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject callDish(@RequestParam("orderDishId") Integer orderDishId){
        try{
            orderDishService.callDish(orderDishId);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}