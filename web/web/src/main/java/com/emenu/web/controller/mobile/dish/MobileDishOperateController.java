package com.emenu.web.controller.mobile.dish;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.order.OrderDishCache;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

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


    @Module(ModuleEnums.MobileDishNew)
    @RequestMapping(value = "new/{id}", method = RequestMethod.POST)
    public String newDish(HttpSession session,
                          @PathVariable("id") Integer id,
                          @RequestParam("type") Integer type,
                          @RequestParam("number") Integer number,
                          @RequestParam("remarks") String  remarks) {
        try {
            // 从Session中获取餐台ID
            Integer tableId = (Integer)session.getAttribute("tableId");

            // 添加菜品至缓存
            OrderDishCache orderDishCache = new OrderDishCache();
            orderDishCache.setDishId(id);
            orderDishCache.setServeType(type);
            orderDishCache.setQuantity(number);
            orderDishCache.setRemark(remarks);
            orderDishCacheService.newDish(tableId, orderDishCache);

            return "redirect:../image";
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return MOBILE_SYS_ERR_PAGE;
        }
    }

}
