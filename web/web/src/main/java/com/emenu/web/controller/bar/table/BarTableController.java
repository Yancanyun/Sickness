package com.emenu.web.controller.bar.table;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.meal.MealPeriod;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.table.Area;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.dish.PackageStatusEnums;
import com.emenu.common.enums.meal.MealPeriodIsCurrentEnums;
import com.emenu.common.enums.order.OrderDishStatusEnums;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.utils.DateUtils;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
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
public class BarTableController extends AbstractController {
    /**
     * Ajax 获取区域列表
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

            if (Assert.isNull(tableList)) {
                return sendJsonArray(jsonArray);
            }

            // 根据餐段来判断哪些餐台可以显示出来
            for (Table table : tableList) {
                JSONObject jsonObject = new JSONObject();

                MealPeriod nowMealPeriod = mealPeriodService.queryByCurrentPeriod(MealPeriodIsCurrentEnums.Using);
                TableDto tableDto = tableService.queryTableDtoById(table.getId());
                if (tableDto.getMealPeriodList() == null || tableDto.getMealPeriodList().size() == 0) {
                    break;
                }
                for (MealPeriod mealPeriod : tableDto.getMealPeriodList()) {
                    if (mealPeriod.getId() == nowMealPeriod.getId()) {
                        jsonObject.put("tableId", table.getId());
                        jsonObject.put("tableName", table.getName());
                        jsonObject.put("status", table.getStatus());
                        jsonArray.add(jsonObject);
                        break;
                    }
                }
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
            if (role == "") {
                role = "无";
            }

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tableName", table.getName());
            jsonObject.put("status", table.getStatus());
            jsonObject.put("tableFee", table.getTableFee());
            if (Assert.isNotNull(table.getOpenTime())) {
                jsonObject.put("openTime", DateUtils.formatDatetime(table.getOpenTime()));
                jsonObject.put("takesTime", DateUtils.calculateDiffTimeAndFormat(table.getOpenTime(), new Date()));
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
            // 用来判断套餐标识是否出现过
            HashMap<Integer, Integer> packageFlagMap = new HashMap<Integer, Integer>();

            if (Assert.isNotNull(orderDishDtoList)) {
                for (OrderDishDto orderDishDto : orderDishDtoList) {
                    // 非套餐且不为退菜时，按如下方法发数据
                    if(orderDishDto.getIsPackage() == PackageStatusEnums.IsNotPackage.getId() &&
                            orderDishDto.getStatus()!= OrderDishStatusEnums.IsBack.getId()) {
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

                    // 是套餐且不为退菜时，按如下方法发数据(在数据库里套餐被拆成菜品，因而要做特殊处理)
                    if(orderDishDto.getIsPackage() == PackageStatusEnums.IsPackage.getId() &&
                            orderDishDto.getStatus()!= OrderDishStatusEnums.IsBack.getId()) {
                        JSONObject jsonObject = new JSONObject();

                        // 没有出现过的套餐
                        if(packageFlagMap.get(orderDishDto.getPackageFlag()) == null) {
                            // 标记为出现过
                            packageFlagMap.put(orderDishDto.getPackageFlag(), 1);

                            // 通过packageId查询出菜品的信息
                            DishDto dishDto = dishService.queryById(orderDishDto.getPackageId());
                            // 原本的话套餐显示是单个菜品名字,这里要重新设置一下，设置成显示套餐的名字
                            orderDishDto.setDishName(dishDto.getName());

                            jsonObject.put("id", orderDishDto.getId());
                            jsonObject.put("dishName", orderDishDto.getDishName());
                            String assistantCode = "";
                            assistantCode = dishDto.getAssistantCode();
                            jsonObject.put("assistantCode", assistantCode);
                            jsonObject.put("discount", orderDishDto.getDiscount());
                            jsonObject.put("dishQuantity", orderDishDto.getPackageQuantity());
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
                }
            }

            return sendJsonArray(jsonArray);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 搜索餐台
     * @param keywords
     * @return
     */
    @Module(ModuleEnums.BarTableSearch)
    @RequestMapping(value = "search", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject search(@RequestParam("keywords") String keywords) {
        try {
            Table table = tableService.queryByKeywords(keywords);

            JSONObject jsonObject = new JSONObject();

            if (Assert.isNull(table)) {
                jsonObject.put("areaId", null);
                jsonObject.put("tableId", null);
            } else {
                jsonObject.put("areaId", table.getAreaId());
                jsonObject.put("tableId", table.getId());
            }

            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
