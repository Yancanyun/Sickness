package com.emenu.web.controller.mobile.dish;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.order.OrderDishCache;
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

import javax.servlet.http.HttpSession;

/**
 * MobileDishNewController
 * 菜品操作(点菜、修改点菜内容、)Controller
 * @author: yangch
 * @time: 2016/6/1 17:11
 */
@Module(ModuleEnums.MobileDish)
@Controller
@IgnoreLogin
@RequestMapping(value = URLConstants.MOBILE_DISH_URL)
public class MobileDishOperateController extends AbstractController {

    /**
     * Ajax 快捷点菜
     * @param session
     * @param dishId
     * @return
     */
    @Module(ModuleEnums.MobileDishNew)
    @RequestMapping(value = "ajax/new/quickly", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxNewDish(HttpSession session, @RequestParam("dishId") Integer dishId) {
        try {
            // 从Session中获取餐台ID
            Integer tableId = (Integer)session.getAttribute("tableId");

            OrderDishCache orderDishCache = new OrderDishCache();
            orderDishCache.setDishId(dishId);
            orderDishCacheService.newDish(tableId, orderDishCache);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
