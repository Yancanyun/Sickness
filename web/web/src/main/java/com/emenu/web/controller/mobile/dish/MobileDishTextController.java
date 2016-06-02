package com.emenu.web.controller.mobile.dish;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.dto.order.OrderDishCache;
import com.emenu.common.dto.order.TableOrderCache;
import com.emenu.common.entity.call.CallWaiter;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
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
    public String toList(Model model)
    {
        try
        {
            // 获取二级分类
            List<Tag> tagList = new ArrayList<Tag>();
            tagList.addAll(tagFacadeService.listAllByTagId(TagEnum.Dishes.getId()));
            tagList.addAll(tagFacadeService.listAllByTagId(TagEnum.Goods.getId()));
            tagList.addAll(tagFacadeService.listAllByTagId(TagEnum.Drinks.getId()));
            tagList.addAll(tagFacadeService.listAllByTagId(TagEnum.Package.getId()));
            model.addAttribute("tagList", tagList);
        }
        catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
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
                                         @RequestParam("classify") Integer classify,
                                         @RequestParam("userId") Integer userId) {
        List<DishDto> dishDtoList = new ArrayList<DishDto>();
        DishSearchDto dishSearchDto = new DishSearchDto();
        Integer dataCount = 0;
        try{
            dishSearchDto.setPageNo(page);
            dishSearchDto.setPageSize(pageSize);
            dishSearchDto.setKeyword(keyword);
            dishDtoList = dishService.listBySearchDtoInMobile(dishSearchDto);
            dataCount = dishService.countBySearchDto(dishSearchDto);

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
                Integer number = 0;
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