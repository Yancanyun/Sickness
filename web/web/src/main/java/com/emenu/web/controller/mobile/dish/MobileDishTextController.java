package com.emenu.web.controller.mobile.dish;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.cache.order.OrderDishCache;
import com.emenu.common.cache.order.TableOrderCache;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyuting
 * @date 2016/5/28 8:45
 */
@Module(ModuleEnums.MobileDishText)
@Controller
@IgnoreLogin
@RequestMapping(value = URLConstants.MOBILE_DISH_TEXT_URL)
public class MobileDishTextController extends AbstractController {
    @Module(ModuleEnums.MobileDishTextList)
    @RequestMapping(value = {"","list"}, method = RequestMethod.GET)
    public String toList(@RequestParam(required = false) Integer classifyId,
                         @RequestParam(required = false) String keyword,
                         HttpSession session, Model model) {
        try {
            // 检查Session中是否存在TableId
            if (Assert.isNull(session.getAttribute("tableId"))) {
                return MOBILE_SESSION_OVERDUE_PAGE;
            }

            // 从Session中获取TableID
            Integer tableId = (Integer)session.getAttribute("tableId");
            model.addAttribute("tableId", tableId);

            // 检查餐台是否已开台
            if (tableService.queryStatusById(tableId) == TableStatusEnums.Disabled.getId()
                    || tableService.queryStatusById(tableId) == TableStatusEnums.Enabled.getId()) {
                return MOBILE_NOT_OPEN_PAGE;
            }

            // 把参数中传来的分类ID传到页面上，前端发Ajax请求需要用到
            if (classifyId != null) {
                model.addAttribute("classifyId", classifyId);
            }

            // 把参数中传来的搜索关键词传到页面上，前端发Ajax请求需要用到
            if (keyword != null) {
                model.addAttribute("keyword", keyword);
            }

            // 获取二级分类
            List<Tag> tagList = new ArrayList<Tag>();
            tagList.addAll(tagFacadeService.listChildrenByTagId(TagEnum.Dishes.getId()));
            tagList.addAll(tagFacadeService.listChildrenByTagId(TagEnum.Goods.getId()));
            tagList.addAll(tagFacadeService.listChildrenByTagId(TagEnum.Drinks.getId()));
            tagList.addAll(tagFacadeService.listChildrenByTagId(TagEnum.Package.getId()));
            model.addAttribute("tagList", tagList);

            // 从缓存中取出该餐台已点但未下单的菜品
            TableOrderCache tableOrderCache = orderDishCacheService.listByTableId(tableId);
            List<OrderDishCache> orderDishCacheList = new ArrayList<OrderDishCache>();
            if (tableOrderCache != null) {
                orderDishCacheList = tableOrderCache.getOrderDishCacheList();
            }
            int dishTotalNumber=0;
            for (OrderDishCache orderDishCache : orderDishCacheList) {
                dishTotalNumber = dishTotalNumber + orderDishCache.getQuantity().intValue();
            }
            if (dishTotalNumber != 0) {
                model.addAttribute("dishTotalNumber", dishTotalNumber);
            }
        }
        catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return MOBILE_SYS_ERR_PAGE;
        }
        return "mobile/dish/text/list_home";
    }

    @Module(ModuleEnums.MobileDishTextList)
    @RequestMapping(value = "ajax/list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject AjaxMobileDishText(HttpSession session,
                                         @RequestParam("page") Integer page,
                                         @RequestParam("pageSize") Integer pageSize,
                                         @RequestParam (value = "keyword",required = false) String keyword,
                                         @RequestParam("classify") Integer classify) {
        List<DishDto> dishDtoList = new ArrayList<DishDto>();
        DishSearchDto dishSearchDto = new DishSearchDto();
        Integer dataCount = 0;
        try{
            dishSearchDto.setPageNo(page);
            dishSearchDto.setPageSize(pageSize);
            dishSearchDto.setKeyword(keyword);
            if (classify != null) {
                List<Integer> tagIdList = new ArrayList<Integer>();
                tagIdList.add(classify);
                dishSearchDto.setTagIdList(tagIdList);
            }
            if (keyword != null) {
                dishSearchDto.setKeyword(keyword);
            }
            dishDtoList = dishService.listBySearchDtoInMobile(dishSearchDto);
            dataCount = dishService.countBySearchDto(dishSearchDto);

            // TODO: 看完之后禁止再次下拉，前台还没做
            if (dishDtoList.size() == 0) {
                return sendJsonObject(AJAX_FAILURE_CODE);
            }

            // 从缓存中取出该餐台已点但未下单的菜品
            Integer tableId = (Integer)session.getAttribute("tableId");
            TableOrderCache tableOrderCache = orderDishCacheService.listByTableId(tableId);
            List<OrderDishCache> orderDishCacheList = new ArrayList<OrderDishCache>();
            if (tableOrderCache != null) {
                orderDishCacheList = tableOrderCache.getOrderDishCacheList();
            }

            JSONArray jsonArray = new JSONArray();
            for (DishDto dishDto:dishDtoList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("dishId",dishDto.getId());
                jsonObject.put("name",dishDto.getName());
                jsonObject.put("price",dishDto.getPrice());
                jsonObject.put("sale",dishDto.getSalePrice());

                // 从OrderDishCacheList中找dishId相同的菜品，把数量加起来发给前台
                Float number = new Float(0);
                for (OrderDishCache orderDishCache : orderDishCacheList) {
                    if (orderDishCache.getDishId() != null && orderDishCache.getDishId().equals(dishDto.getId())) {
                        number = number + orderDishCache.getQuantity();
                    }
                }
                if (number != 0) {
                    jsonObject.put("number", number);
                }

                jsonArray.add(jsonObject);
            }

            return sendJsonArray(jsonArray,dataCount);
        } catch (SSException e) {
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }
}