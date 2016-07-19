package com.emenu.web.controller.waiter.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.dto.party.group.employee.EmployeeDto;
import com.emenu.common.dto.remark.RemarkDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.dish.Taste;
import com.emenu.common.entity.order.BackDish;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.entity.remark.Remark;
import com.emenu.common.entity.remark.RemarkTag;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.DateUtils;
import com.emenu.common.utils.URLConstants;
import com.emenu.service.remark.RemarkTagService;
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

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 退菜controller
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
    public JSONObject table(@RequestParam("tableId") Integer tableId,
                            HttpSession httpSession){
        try{

            JSONObject jsonObject = new JSONObject();

            // 判断服务员是否可以服务该餐台
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
            }

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

    @RequestMapping(value = "back", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject backDishConfirm(@RequestParam("orderDishId") Integer orderDishId){
        List<RemarkTag> remarkTagList = Collections.emptyList();
        List<Remark> remarkList = Collections.emptyList();
        JSONArray backRemarkList = new JSONArray();
        try{
            OrderDishDto orderDishDto = orderDishService.queryDtoById(orderDishId);
            Taste taste = tasteService.queryById(orderDishDto.getTasteId());
            // 获取所有的退菜备注分类
            remarkTagList = remarkTagService.listByParentId(2);
            for (RemarkTag remarkTag: remarkTagList){
                // 获取所有分类下的备注内容
                remarkList = remarkService.listByRemarkTagId(remarkTag.getId());
                if (remarkList.size() == 0 || remarkList.isEmpty()){
                    for (Remark remark :remarkList){
                        JSONObject backRemark = new JSONObject();
                        backRemark.put("backRemark",remark.getName());
                        backRemarkList.add(backRemark);
                    }
                }
            }
            // 在备注的最后追加一个"其他"
            JSONObject backRemarkOther = new JSONObject();
            backRemarkOther.put("backRemark","其他");
            backRemarkList.add(backRemarkOther);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("orderDishId",orderDishId);
            jsonObject.put("orderDishName",orderDishDto.getDishName());
            jsonObject.put("taste",taste.getName());
            jsonObject.put("number",orderDishDto.getDishQuantity());
            jsonObject.put("backRemarkList",backRemarkList);

            return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    @RequestMapping(value = "back/finish", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject bachDish(@RequestParam("orderDishId") Integer orderDishId,
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