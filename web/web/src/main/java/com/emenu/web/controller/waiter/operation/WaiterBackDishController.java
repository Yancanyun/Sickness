package com.emenu.web.controller.waiter.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.order.BackDish;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.DateUtils;
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

import java.util.ArrayList;
import java.util.List;

/**
 * 退菜
 *
 * @author chenyuting
 * @date 2016/7/15 9:02
 */
@Controller
@Module(ModuleEnums.WaiterOrderDish)
@RequestMapping(value = URLConstants.WAITER_BACK_DISH_URL)
public class WaiterBackDishController extends AbstractController {

    /**
     * 获取当前餐台消费列表（不包含已结账菜品）
     * @param tableId
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject table(@RequestParam("tableId") Integer tableId){
        try{

            JSONObject jsonObject = new JSONObject();

            // 查询本餐台所有的订单菜品
            List<Order> orderList = new ArrayList<Order>();
            List<OrderDishDto> orderDishDtoList = new ArrayList<OrderDishDto>();
            orderList = orderService.listByTableIdAndStatus(tableId, 1);// 查询出对应餐桌所有已下单的订单, 已结账的订单不显示
            if (Assert.isNotNull(orderList)) {
                for (Order order : orderList) {
                    Integer orderId = order.getId();
                    orderDishDtoList.addAll(orderDishService.listDtoByOrderId(orderId));
                }
            }

            JSONArray orderDishList = new JSONArray();
            if (Assert.isNotNull(orderDishDtoList)) {
                for(OrderDishDto orderDishDto : orderDishDtoList) {
                    JSONObject orderDishJsonObject = new JSONObject();

                    orderDishJsonObject.put("orderDishId", orderDishDto.getId());
                    orderDishJsonObject.put("dishName", orderDishDto.getDishName());
                    orderDishJsonObject.put("number", orderDishDto.getDishQuantity());
                    orderDishJsonObject.put("salePrice", orderDishDto.getSalePrice());
                    if (orderDishDto.getTasteName() == null) {
                        orderDishJsonObject.put("taste", "");
                    } else {
                        orderDishJsonObject.put("taste", orderDishDto.getTasteName());
                    }
                    orderDishJsonObject.put("dishStatus", orderDishDto.getStatus());
                    orderDishJsonObject.put("remarks", orderDishDto.getRemark());
                    orderDishList.add(jsonObject);
                }
                jsonObject.put("orderDishList",orderDishList);
                Table table = tableService.queryById(tableId);
                jsonObject.put("personNum",table.getPersonNum());
                jsonObject.put("seatFee",table.getSeatFee());
                jsonObject.put("tableFee",table.getTableFee());

                // TODO totalMoney
            }
            return sendJsonArray(orderDishList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /*@RequestMapping(value = "back", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject bachDish(@RequestParam("orderDishId") Integer orderDishId){
        try{
            BackDish backDish = new BackDish();
            OrderDish orderDish = orderDishService.queryById(orderDishId);
            backDish.setId(orderDish.getDishId());
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }*/

}