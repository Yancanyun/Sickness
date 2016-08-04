package com.emenu.web.controller.bar.operation;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 催菜controller
 *
 * @author chenyuting
 * @date 2016/7/19 17:28
 */
@Controller
@Module(ModuleEnums.BarCallDish)
@RequestMapping(value = URLConstants.BAR_CALL_DISH)
public class BarCallDishController extends AbstractController {

    /**
     * 催菜
     * @param orderDishId
     * @return
     */
    @RequestMapping(value = "",method = RequestMethod.POST)
    @Module(ModuleEnums.BarCallDish)
    @ResponseBody
    public JSONObject callDish(@RequestParam("orderDishId") Integer orderDishId){
        try{
            orderDishService.callDish(orderDishId);
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