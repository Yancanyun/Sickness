package com.emenu.web.controller.mobile.dish;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.cache.order.OrderDishCache;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.filter.ConstantsFilter;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletRequest;
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

    /**
     * 点菜提交
     * @param session
     * @param id
     * @param type
     * @param number
     * @param remarks
     * @return
     */
    @Module(ModuleEnums.MobileDishNew)
    @RequestMapping(value = "new/{id}", method = RequestMethod.POST)
    public String newDish(HttpSession session,
                          @PathVariable("id") Integer id,
                          @RequestParam("type") Integer type,
                          @RequestParam("number") Integer number,
                          @RequestParam("taste") Integer taste,
                          @RequestParam("remarks") String remarks) {
        try {
            // 检查Session中是否存在TableId
            if (Assert.isNull(session.getAttribute("tableId"))) {
                return MOBILE_SESSION_OVERDUE_PAGE;
            }

            // 从Session中获取TableID
            Integer tableId = (Integer)session.getAttribute("tableId");

            // 检查餐台是否已开台
            if (tableService.queryStatusById(tableId) == TableStatusEnums.Disabled.getId()
                    || tableService.queryStatusById(tableId) == TableStatusEnums.Enabled.getId()) {
                return MOBILE_NOT_OPEN_PAGE;
            }

            // 添加菜品至缓存
            OrderDishCache orderDishCache = new OrderDishCache();
            orderDishCache.setDishId(id);
            orderDishCache.setServeType(type);
            orderDishCache.setQuantity(new Float(number));
            orderDishCache.setTasteId(taste);
            orderDishCache.setRemark(remarks);
            orderDishCacheService.newDish(tableId, orderDishCache);

            return "redirect:../image";
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return MOBILE_SYS_ERR_PAGE;
        }
    }

    /**
     * Ajax 搜索
     * @param key
     */
    @RequestMapping(value = "ajax/search", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxDishSearch(@RequestParam("key") String key,
                               HttpServletRequest httpServletRequest) {

        try {
            DishSearchDto dishSearchDto = new DishSearchDto();
            dishSearchDto.setPageNo(0);
            dishSearchDto.setPageSize(10);
            dishSearchDto.setKeyword(key);
            List<DishDto> dishDtoList = dishService.listBySearchDtoInMobile(dishSearchDto);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dishListSrc", "image?keyword=");
            String path = httpServletRequest.getContextPath();
            int port = httpServletRequest.getServerPort();
            String href = httpServletRequest.getScheme() + "://" + httpServletRequest.getServerName()
                    + (port == 80 ? "" : (":" + port)) + path + "mobile/dish/detail/";
            jsonObject.put("dishDetailSrc", href);

            JSONArray contentList = new JSONArray();
            for (DishDto dishDto : dishDtoList) {
                JSONObject childJsonObject = new JSONObject();
                childJsonObject.put("dishId", dishDto.getId());
                childJsonObject.put("name", dishDto.getName());
                contentList.add(childJsonObject);
            }
            jsonObject.put("list", contentList);

            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 点赞
     * @param dishId
     * @return
     */
    @Module(ModuleEnums.MobileDishLike)
    @RequestMapping(value = "ajax/like", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxNewDish(@RequestParam("dishId") Integer dishId) {
        try {
            dishService.likeThisDish(dishId);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
