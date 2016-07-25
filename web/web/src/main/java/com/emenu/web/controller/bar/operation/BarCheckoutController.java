package com.emenu.web.controller.bar.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.cache.order.OrderDishCache;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.entity.party.group.employee.Employee;
import com.emenu.common.entity.party.security.SecurityUser;
import com.emenu.common.entity.remark.Remark;
import com.emenu.common.entity.remark.RemarkTag;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.dish.PackageStatusEnums;
import com.emenu.common.enums.order.CheckoutTypeEnums;
import com.emenu.common.enums.order.OrderDishStatusEnums;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.exception.EmenuException;
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
 * 吧台结账controller
 *
 * @author quanyibo
 * @date 2016/7/20
 */
@Controller
@Module(ModuleEnums.BarCheckout)
@RequestMapping(value = URLConstants.BAR_CHECKOUT_URL)
public class BarCheckoutController extends AbstractController {

    /**
     * 获取结账窗口的信息
     * @param tableId
     * @param uid
     * @return
     *
     * @author: yangch
     * @time: 2016/7/22 08:59
     */
    @Module(ModuleEnums.BarCheckout)
    @RequestMapping(value = "",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject checkoutInfo(@RequestParam("tableId") Integer tableId,
                                   @RequestParam("uid") Integer uid) {
        try {
            JSONObject jsonObject = new JSONObject();

            // 获取餐台基本信息
            TableDto tableDto = tableService.queryTableDtoById(tableId);
            Table table = tableDto.getTable();
            if (Assert.isNull(tableDto) || Assert.isNull(table)) {
                throw SSException.get(EmenuException.TableIdError);
            }
            jsonObject.put("tableName", table.getName());
            jsonObject.put("personNum", table.getPersonNum());
            jsonObject.put("areaName", tableDto.getAreaName());
            if (Assert.isNotNull(table.getOpenTime())) {
                jsonObject.put("openTime", DateUtils.formatDatetime(table.getOpenTime()));
            } else {
                jsonObject.put("openTime", "");
            }

            // 计算消费金额与房间费用
            jsonObject.put("tableMoney", checkoutService.calcTableMoney(tableId));
            jsonObject.put("consumptionMoney", checkoutService.calcConsumptionMoney(tableId));

            // 根据uid获取收款人
            SecurityUser securityUser = securityUserService.queryById(uid);
            if (Assert.isNull(securityUser)) {
                throw SSException.get(EmenuException.QueryEmployeeInfoFail);
            }
            int partyId = securityUser.getPartyId();
            Employee employee = employeeService.queryByPartyId(partyId);
            if (Assert.isNull(employee)) {
                throw SSException.get(EmenuException.QueryEmployeeInfoFail);
            }
            jsonObject.put("name", employee.getName());
            // 打印机是否连接成功
            jsonObject.put("isPrinterOk", checkoutService.isPrinterOk());

            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 结账操作
     * @param tableId
     * @param uid
     * @param wipeZeroMoney
     * @param totalPayMoney
     * @param checkoutType
     * @param isInvoiced
     * @param serialNum
     * @return
     *
     * @author: yangch
     * @time: 2016/7/22 09:28
     */
    @Module(ModuleEnums.BarCheckout)
    @RequestMapping(value = "",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkoutInfo(@RequestParam("tableId") Integer tableId,
                                   @RequestParam("uid") Integer uid,
                                   @RequestParam("wipeZeroMoney") BigDecimal wipeZeroMoney,
                                   @RequestParam("totalPayMoney") BigDecimal totalPayMoney,
                                   @RequestParam("checkoutType") int checkoutType,
                                   @RequestParam("isInvoiced") int isInvoiced,
                                   @RequestParam(required = false) String serialNum) {
        try {
            Table table = tableService.queryById(tableId);
            if (Assert.isNull(table)) {
                throw SSException.get(EmenuException.TableIdError);
            }

            // 计算消费金额
            BigDecimal consumptionMoney = checkoutService.calcConsumptionMoney(tableId);

            // 根据uid获取PartyID
            SecurityUser securityUser = securityUserService.queryById(uid);
            if (Assert.isNull(securityUser)) {
                throw SSException.get(EmenuException.QueryEmployeeInfoFail);
            }
            int partyId = securityUser.getPartyId();

            // 如果结账方式为现金，则不需要有流水号
            if (checkoutType == CheckoutTypeEnums.Cash.getId()) {
                serialNum = null;
            }

            // 打印消费清单
            checkoutService.printCheckOutByTableId(tableId);

            // 结账
            checkoutService.checkout(tableId, partyId, consumptionMoney, wipeZeroMoney, totalPayMoney,
                    checkoutType, serialNum, isInvoiced);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 获取免单备注
     * @return
     *
     * @author: yangch
     * @time: 2016/7/22 16:13
     */
    @Module(ModuleEnums.BarCheckoutFreeOrder)
    @RequestMapping(value = "freeorder",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject freeOrderRemark() {
        try {
            JSONArray jsonArray = new JSONArray();

            // 获取免单备注上级分类下的所有备注分类
            List<RemarkTag> remarkTagList = remarkTagService.listByParentId(4);
            if (Assert.isNull(remarkTagList) || remarkTagList.size() == 0) {
                return sendJsonArray(jsonArray);
            }
            List<Remark> remarkList = new ArrayList<Remark>();

            // 获取所有免单备注
            for (RemarkTag remarkTag : remarkTagList) {
                remarkList.addAll(remarkService.listByRemarkTagId(remarkTag.getId()));
            }

            // 把所有免单备注传给C#端
            if (Assert.isNull(remarkTagList) || remarkTagList.size() == 0) {
                return sendJsonArray(jsonArray);
            }
            for (Remark remark : remarkList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", remark.getId());
                jsonObject.put("name", remark.getName());
                jsonArray.add(jsonObject);
            }

            return sendJsonArray(jsonArray);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 免单操作
     * @param tableId
     * @param uid
     * @param freeRemark
     * @return
     *
     * @author: yangch
     * @time: 2016/7/22 14:50
     */
    @Module(ModuleEnums.BarCheckoutFreeOrder)
    @RequestMapping(value = "freeorder",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject freeOrder(@RequestParam("tableId") Integer tableId,
                                @RequestParam("uid") Integer uid,
                                @RequestParam(required = false) String freeRemark) {
        try {
            Table table = tableService.queryById(tableId);
            if (Assert.isNull(table)) {
                throw SSException.get(EmenuException.TableIdError);
            }

            // 计算消费金额
            BigDecimal consumptionMoney = checkoutService.calcConsumptionMoney(tableId);

            // 根据uid获取PartyID
            SecurityUser securityUser = securityUserService.queryById(uid);
            if (Assert.isNull(securityUser)) {
                throw SSException.get(EmenuException.QueryEmployeeInfoFail);
            }
            int partyId = securityUser.getPartyId();

            // 打印消费清单
            checkoutService.printCheckOutByTableId(tableId);

            // 免单
            checkoutService.freeOrder(tableId, partyId, consumptionMoney, freeRemark);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 吧台结账显示消费清单
     * @param tableId
     * @return
     *
     * @author: yangch
     * @time: 2016/7/22 15:00
     */
    @Module(ModuleEnums.BarCheckoutOrderDish)
    @RequestMapping(value = "orderdishlist", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject orderDishList(@RequestParam("tableId") int tableId) {
        try {
            JSONArray jsonArray = new JSONArray();

            Table table = tableService.queryById(tableId);
            if (Assert.isNull(table)) {
                throw SSException.get(EmenuException.TableIdError);
            }

            List<Order> orderList = new ArrayList<Order>();
            List<OrderDishDto> orderDishDtoList = new ArrayList<OrderDishDto>();

            // 查本餐台的订单
            orderList = orderService.listByTableIdAndStatus(tableId, 1);

            // 若已并台，则需要查询出与本餐台并台的所有餐台的订单
            if (table.getStatus().equals(TableStatusEnums.Merged.getId())) {
                // 与本餐台并台的其他餐台的列表
                List<Table> tableList = tableMergeService.listOtherTableByTableId(tableId);
                if (Assert.isNull(tableList) || tableList.size() == 0) {
                    throw SSException.get(EmenuException.MergeIdError);
                }
                // 与本餐台并台的所有餐台的订单
                for (Table t : tableList) {
                    orderList.addAll(orderService.listByTableIdAndStatus(t.getId(), 1));
                }
            }

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
                        jsonObject.put("discount", orderDishDto.getDiscount().multiply(new BigDecimal(10)));
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
                        // 若没有并台，则直接显示该餐台的餐台名
                        if (!table.getStatus().equals(TableStatusEnums.Merged.getId())) {
                            jsonObject.put("tableName", table.getName());
                        } else {
                            // 否则显示Order对应餐台的餐台名
                            Order order = orderService.queryById(orderDishDto.getOrderId());
                            Table t = tableService.queryById(order.getTableId());
                            jsonObject.put("tableName", t.getName());
                        }

                        jsonArray.add(jsonObject);
                    }

                    // 是套餐且不为退菜时，按如下方法发数据(在数据库里套餐被拆成菜品，因而要做特殊处理)
                    if(orderDishDto.getIsPackage() == PackageStatusEnums.IsPackage.getId() &&
                            orderDishDto.getStatus()!= OrderDishStatusEnums.IsBack.getId()) {
                        JSONObject jsonObject = new JSONObject();

                        // 没有出现过的套餐
                        if (packageFlagMap.get(orderDishDto.getPackageFlag()) == null) {
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
                            jsonObject.put("discount", orderDishDto.getDiscount().multiply(new BigDecimal(10)));
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
                            // 若没有并台，则直接显示该餐台的餐台名
                            if (!table.getStatus().equals(TableStatusEnums.Merged.getId())) {
                                jsonObject.put("tableName", table.getName());
                            } else {
                                // 否则显示Order对应餐台的餐台名
                                Order order = orderService.queryById(orderDishDto.getOrderId());
                                Table t = tableService.queryById(order.getTableId());
                                jsonObject.put("tableName", t.getName());
                            }

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
     * 修改菜品的折扣
     * @param orderDishId
     * @param discount
     * @return
     *
     * @author: yangch
     * @time: 2016/7/22 17:09
     */
    @Module(ModuleEnums.BarCheckoutOrderDish)
    @RequestMapping(value = "orderdish", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject updateDiscount(@RequestParam("orderDishId") Integer orderDishId,
                                     @RequestParam("discount") BigDecimal discount) {
        try {
            OrderDish orderDish = orderDishService.queryById(orderDishId);

            // 若不是套餐，直接改就行
            if (orderDish.getIsPackage() == 0) {
                orderDish.setDiscount(discount.divide(new BigDecimal(10)));
                orderDishService.updateOrderDish(orderDish);
            }

            // 若是套餐，则需要修改该套餐下的所有菜品的OrderDish
            if (orderDish.getIsPackage() == 1) {
                // 查询该套餐下的所有菜品的OrderDish
                int orderId = orderDish.getOrderId();
                int packageId = orderDish.getPackageId();
                List<OrderDish> orderDishList = orderDishService.listByOrderIdAndPackageId(orderId, packageId);

                // 修改该套餐下的所有菜品的OrderDish
                for (OrderDish oD : orderDishList) {
                    oD.setDiscount(discount.divide(new BigDecimal(10)));
                    orderDishService.updateOrderDish(oD);
                }
            }

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 打印消费清单
     * @param tableId
     * @return
     */
    @Module(ModuleEnums.BarCheckoutPrint)
    @RequestMapping(value = "print",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkoutPrint(@RequestParam("tableId")Integer tableId) {
        JSONObject jsonObject = new JSONObject();
        try {
            return checkoutService.printCheckOutByTableId(tableId);
        }
        catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            jsonObject.put("code",3);
            return jsonObject;
        }
    }
}
