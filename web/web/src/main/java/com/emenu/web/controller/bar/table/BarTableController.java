package com.emenu.web.controller.bar.table;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.entity.dish.DishImg;
import com.emenu.common.entity.dish.Unit;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.table.Area;
import com.emenu.common.entity.table.Table;
import com.emenu.common.entity.table.TableMerge;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.DateUtils;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractAppBarController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * BarTableController
 *
 * @author: yangch
 * @time: 2015/12/10 16:29
 */
@Controller
@Module(ModuleEnums.BarTable)
@RequestMapping(value = URLConstants.BAR_TABLE_URL)
public class BarTableController extends AbstractAppBarController {
    /**
     * Ajax 获取区域列表
     *
     * @return
     */
    @Module(ModuleEnums.BarTableAreaList)
    @RequestMapping(value = "arealist", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject areaList() {
        try {
            List<Area> areaList = areaService.listAll();

            JSONArray jsonArray = new JSONArray();

            for (Area area : areaList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("areaId", area.getId());
                jsonObject.put("areaName", area.getName());

                jsonArray.add(jsonObject);
            }

            return sendJsonArray(jsonArray);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 获取某区域下的餐台列表
     *
     * @param areaId
     * @return
     */
    @Module(ModuleEnums.BarTableList)
    @RequestMapping(value = "tablelist", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject tableList(@RequestParam("areaId") int areaId) {
        try {
            //将该区域内所有餐台存入tableList
            List<Table> tableList = tableService.listByAreaId(areaId);

            JSONArray jsonArray = new JSONArray();

            for (Table table : tableList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("tableId", table.getId());
                jsonObject.put("tableName", table.getName());
                jsonObject.put("status", table.getStatus());

                jsonArray.add(jsonObject);
            }

            return sendJsonArray(jsonArray);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 获取某餐台的具体信息
     *
     * @param tableId
     * @return
     */
    @Module(ModuleEnums.BarTableDetail)
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject tableDetail(@RequestParam("tableId") int tableId) {
        try {
            Table table = tableService.queryById(tableId);

            // 计算此餐台的总金额
            BigDecimal totalCost = new BigDecimal(0); // 已下单未结账订单菜品的总金额
            List<Order> orderList = new ArrayList<Order>(); // 订单List
            List<OrderDishDto> orderDishDtoList = new ArrayList<OrderDishDto>(); // 所有的订单菜品
            orderList = orderService.listByTableIdAndStatus(tableId, 1);// 查询出对应餐桌所有已下单的订单, 已结账的订单不显示
            if (Assert.isNotNull(orderList)) {
                for (Order order : orderList) {
                    Integer orderId = order.getId(); // 订单Id
                    orderDishDtoList.addAll(orderDishService.listDtoByOrderId(orderId));
                }
            }
            for (OrderDishDto orderDishDto : orderDishDtoList) {
                if (orderDishDto.getIsPackage() == 0) {
                    totalCost = totalCost.add(new BigDecimal(orderDishDto.getSalePrice().doubleValue() * orderDishDto.getDishQuantity()));
                } else {
                    totalCost = totalCost.add(new BigDecimal(orderDishDto.getSalePrice().doubleValue() * orderDishDto.getPackageQuantity()));
                }
            }

            // 找与本餐台并台的餐台
            String role = "";
            List<Table> tableList = tableMergeService.listOtherTableByTableId(tableId);
            for (int i = 0; i < tableList.size(); i++) {
                Table t = tableList.get(i);
                Area area = areaService.queryById(t.getAreaId());

                if (i != tableList.size() - 1) {
                    role += area.getName() + " " + t.getName() + ", ";
                } else {
                    role += area.getName() + " " + t.getName();
                }
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tableName", table.getName());
            jsonObject.put("status", table.getStatus());
            jsonObject.put("tableFee", table.getTableFee());
            if (Assert.isNotNull(table.getOpenTime())) {
                jsonObject.put("openTime", DateUtils.formatDatetime(table.getOpenTime()));
                jsonObject.put("takesTime", DateUtils.calculateDiffTime(table.getOpenTime(), new Date()));
            } else {
                jsonObject.put("openTime", "");
                jsonObject.put("takesTime", "");
            }
            jsonObject.put("totalCost", totalCost);
            jsonObject.put("role", role);
            jsonObject.put("seatFee", table.getSeatFee());

            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 根据餐台ID获取该餐台下的所有订单-菜品
     * @param tableId
     * @return
     */
    @Module(ModuleEnums.BarTableOrderDish)
    @RequestMapping(value = "orderdishlist", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject orderDishList(@RequestParam("tableId") int tableId) {
        try {

            JSONArray jsonArray = new JSONArray();

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

            if (Assert.isNotNull(orderDishDtoList)) {
                for(OrderDishDto orderDishDto : orderDishDtoList) {
                    JSONObject jsonObject = new JSONObject();

                    jsonObject.put("id", orderDishDto.getId());
                    jsonObject.put("dishName", orderDishDto.getDishName());
                    String assistantCode = "";
                    DishDto dishDto = dishService.queryById(orderDishDto.getDishId());
                    assistantCode = dishDto.getAssistantCode();
                    jsonObject.put("assistantCode", assistantCode);
                    jsonObject.put("discount", orderDishDto.getDiscount());
                    jsonObject.put("dishQuantity", orderDishDto.getDishQuantity());
                    String unitName = "";
                    unitName = dishDto.getUnitName();
                    jsonObject.put("unitName", unitName);
                    jsonObject.put("salePrice", orderDishDto.getSalePrice());
                    if (orderDishDto.getTasteName() == null) {
                        jsonObject.put("tasteName", "");
                    } else {
                        jsonObject.put("tasteName", orderDishDto.getTasteName());
                    }
                    jsonObject.put("serveType", orderDishDto.getServeType());
                    jsonObject.put("status", orderDishDto.getStatus());
                    if (Assert.isNotNull(orderDishDto.getOrderTime())) {
                        jsonObject.put("orderTime", DateUtils.formatDatetime(orderDishDto.getOrderTime()));
                    } else {
                        jsonObject.put("orderTime", "");
                    }
                    jsonObject.put("remark", orderDishDto.getRemark());

                    jsonArray.add(jsonObject);
                }
            }

            return sendJsonArray(jsonArray);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
