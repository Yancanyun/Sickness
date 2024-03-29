package com.emenu.web.controller.bar.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.entity.dish.Taste;
import com.emenu.common.entity.order.BackDish;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.entity.party.group.employee.Employee;
import com.emenu.common.entity.party.security.SecurityUser;
import com.emenu.common.entity.remark.Remark;
import com.emenu.common.entity.remark.RemarkTag;
import com.emenu.common.enums.dish.PackageStatusEnums;
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
import java.util.Collections;
import java.util.List;

/**
 * 吧台退菜
 * @author chenyuting
 * @date 2016/7/19 19:57
 */
@Controller
@RequestMapping(value = URLConstants.BAR_BACK_DISH)
@Module(ModuleEnums.BarBackDish)
public class BarBackDishController extends AbstractController {

    /**
     * 退菜——消费清单列表、退菜列表
     * @param tableId
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    @Module(ModuleEnums.BarBackDishList)
    @ResponseBody
    public JSONObject backDishList(@RequestParam("tableId") Integer tableId,
                                   HttpSession httpSession){
        try{

            JSONObject jsonObject = new JSONObject();

            // 查询本餐台所有的订单菜品
            List<OrderDishDto> orderDishDtoList = orderDishService.queryOrderDishAndCombinePackageByTableId(tableId);

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
                        orderDishJsonObject.put("status",orderDishService.queryPackageStatusByFirstOrderDishId(orderDishDto.getId()).getId());
                    }else {
                        // 查询菜品信息
                        DishDto dishDto = dishService.queryById(orderDishDto.getDishId());
                        orderDishJsonObject.put("assistantCode", dishDto.getAssistantCode());
                        orderDishJsonObject.put("unitName",dishDto.getUnitName());
                        orderDishJsonObject.put("status",orderDishDto.getStatus());
                    }

                    orderDishJsonObject.put("orderDishId", orderDishDto.getId());
                    orderDishJsonObject.put("dishName", orderDishDto.getDishName());
                    orderDishJsonObject.put("dishNumber", orderDishDto.getDishQuantity());
                    orderDishJsonObject.put("salePrice", orderDishDto.getSalePrice());
                    if (orderDishDto.getTasteName() == null) {
                        orderDishJsonObject.put("taste", "");
                    } else {
                        orderDishJsonObject.put("taste", orderDishDto.getTasteName());
                    }
                    orderDishJsonObject.put("serveType",orderDishDto.getServeType());
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
                            backDishJsonObject.put("backDishNumber",backDish.getBackNumber());
                            backDishJsonObject.put("orderDishId",backDish.getOrderDishId());
                            // 查询退菜口味信息
                            Taste taste = tasteService.queryById(backDish.getTasteId());
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

    /**
     * 退菜确认
     * @param orderDishId
     * @param backNumber
     * @param backRemarks
     * @param uid
     * @return
     */
    @RequestMapping(value = "confirm", method = RequestMethod.POST)
    @Module(ModuleEnums.BarBackDishConfirm)
    @ResponseBody
    public JSONObject backDishConfirm(@RequestParam("orderDishId") Integer orderDishId,
                                      @RequestParam("backNumber") Float backNumber,
                                      @RequestParam("backRemarks") String backRemarks,
                                      @RequestParam("uid") Integer uid){
        try{

            // 根据uid获取收款人
            SecurityUser securityUser = securityUserService.queryById(uid);
            if (Assert.isNull(securityUser)) {
                throw SSException.get(EmenuException.QueryEmployeeInfoFail);
            }
            int partyId = securityUser.getPartyId();
            backDishService.backDishByOrderDishId(orderDishId, backNumber, backRemarks, partyId);
            // 更新餐台版本号
            OrderDish orderDish = orderDishService.queryById(orderDishId);
            cookTableCacheService.updateTableVersion(orderService.queryById(orderDish.getOrderId()).getTableId());
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}