package com.emenu.web.controller.bar.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.dto.party.group.employee.EmployeeDto;
import com.emenu.common.entity.dish.Taste;
import com.emenu.common.entity.order.BackDish;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.entity.remark.Remark;
import com.emenu.common.entity.remark.RemarkTag;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.order.OrderStatusEnums;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.exception.EmenuException;
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

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author chenyuting
 * @date 2016/7/19 19:57
 */
@Controller
@RequestMapping(value = URLConstants.BAR_BACK_DISH)
@Module(ModuleEnums.BarBackDish)
public class BarBackDishController extends AbstractController {

    @RequestMapping(value = "list",method = RequestMethod.GET)
    @Module(ModuleEnums.BarBackDishList)
    @ResponseBody
    public JSONObject backDishList(@RequestParam("tableId") Integer tableId,
                                   HttpSession httpSession){
        try{

            JSONObject jsonObject = new JSONObject();

            /*// 判断服务员是否可以服务该餐台
            Integer partyId = (Integer)httpSession.getAttribute("partyId");
            EmployeeDto employeeDto = employeeService.queryEmployeeDtoByPartyId(partyId);
            List<Integer> tableIdList = employeeDto.getTables();
            if (Assert.isNull(tableIdList)) {
                throw SSException.get(EmenuException.WaiterCanNotServiceThisTable);
            }
            Boolean canService = false;
            for (Integer integer : tableIdList) {
                if (tableId == integer) {
                    canService = true;
                    break;
                }
            }
            if (canService == false) {
                throw SSException.get(EmenuException.WaiterCanNotServiceThisTable);
            }*/

            // 查询本餐台所有的订单菜品
            List<OrderDishDto> orderDishDtoList = orderDishService.queryOrderDishListByTableId(tableId);

            // 消费清单列表
            JSONArray orderDishList = new JSONArray();
            if (Assert.isNotNull(orderDishDtoList)) {
                for(OrderDishDto orderDishDto : orderDishDtoList) {
                    JSONObject orderDishJsonObject = new JSONObject();

                    // 查询菜品信息
                    DishDto dishDto = dishService.queryById(orderDishDto.getDishId());

                    orderDishJsonObject.put("orderDishId", orderDishDto.getId());
                    orderDishJsonObject.put("dishName", orderDishDto.getDishName());
                    orderDishJsonObject.put("number", orderDishDto.getDishQuantity());
                    orderDishJsonObject.put("salePrice", orderDishDto.getSalePrice());
                    if (orderDishDto.getTasteName() == null) {
                        orderDishJsonObject.put("taste", "");
                    } else {
                        orderDishJsonObject.put("taste", orderDishDto.getTasteName());
                    }

                    orderDishJsonObject.put("assistantCode", dishDto.getAssistantCode());
                    orderDishJsonObject.put("unitName",dishDto.getUnitName());
                    orderDishJsonObject.put("serveType",orderDishDto.getServeType());
                    orderDishJsonObject.put("status",orderDishDto.getStatus());
                    orderDishJsonObject.put("orderTime",orderDishDto.getOrderTimeStr());
                    orderDishList.add(orderDishJsonObject);
                }
                jsonObject.put("orderDishList",orderDishList);

                // 退菜清单 根据餐台查询所有的order,再根据orderId去查询退菜
                JSONArray  backDishList = new JSONArray();
                // 根据餐台和状态查询所有未结账订单
                List<Order> orderList = orderService.listByTableIdAndStatus(tableId,OrderStatusEnums.IsBooked.getId());
                for (Order order: orderList){
                    // 根据订单id查询退菜列表
                    List<BackDish> backDishChildrenList = backDishService.queryBackDishListByOrderId(order.getId());
                    if (!backDishChildrenList.isEmpty()){
                        // 退菜列表不为空，加入jsonObject中
                        for (BackDish backDish:backDishChildrenList){
                            JSONObject backDishJsonObject = new JSONObject();
                            backDishJsonObject.put("orderDishId",backDish.getOrderDishId());
                            // 查询订单菜品信息
                            OrderDish orderDish = orderDishService.queryById(backDish.getOrderDishId());
                            // 查询菜品信息
                            DishDto dishDto = dishService.queryById(orderDish.getDishId());
                            // 查询退菜口味信息
                            Taste taste = tasteService.queryById(backDish.getTasteId());
                            backDishJsonObject.put("dishName",dishDto.getName());
                            backDishJsonObject.put("assistantCode",dishDto.getAssistantCode());
                            backDishJsonObject.put("unitName",dishDto.getUnitName());
                            backDishJsonObject.put("salePrice",orderDish.getSalePrice());
                            backDishJsonObject.put("backDishNumber",backDish.getBackNumber());
                            if (taste != null){
                                backDishJsonObject.put("taste",taste.getName());
                            }else {
                                backDishJsonObject.put("taste","");
                            }
                            backDishJsonObject.put("backTime",backDish.getBackTimeStr());
                            backDishList.add(backDishJsonObject);
                        }
                    }
                }
                jsonObject.put("backDishList",backDishList);


                List<RemarkTag> remarkTagList = Collections.emptyList();
                List<Remark> remarkList = Collections.emptyList();
                // 退菜备注
                JSONArray backRemarkList = new JSONArray();
                // 获取所有的退菜备注分类
                remarkTagList = remarkTagService.listByParentId(2);
                for (RemarkTag remarkTag: remarkTagList){
                    // 获取所有分类下的备注内容
                    remarkList = remarkService.listByRemarkTagId(remarkTag.getId());
                    if (remarkList.size() != 0 || !remarkList.isEmpty()){
                        for (Remark remark :remarkList){
                            JSONObject backRemarkObject = new JSONObject();
                            backRemarkObject.put("backRemark",remark.getName());
                            backRemarkList.add(backRemarkObject);
                        }
                    }
                }
                // 在备注的最后追加一个"其他"
                JSONObject backRemarkOther = new JSONObject();
                backRemarkOther.put("backRemark","其他");
                backRemarkList.add(backRemarkOther);
                jsonObject.put("backRemarkList",backRemarkList);

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

    @RequestMapping(value = "confirm", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject backDishConfirm(@RequestParam("orderDishId") Integer orderDishId,
                                      @RequestParam("backNumber") Float backNumber,
                                      @RequestParam("backRemarks") String backRemarks,
                                      HttpSession httpSession){
        try{
            Integer partyId = (Integer)httpSession.getAttribute("partyId");
            backDishService.backDishByOrderDishId(orderDishId, backNumber, backRemarks, partyId);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}