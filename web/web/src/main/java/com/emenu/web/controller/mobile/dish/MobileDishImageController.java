package com.emenu.web.controller.mobile.dish;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.dto.dish.DishTagDto;
import com.emenu.common.dto.order.OrderDishCache;
import com.emenu.common.dto.order.TableOrderCache;
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
 * MobileDishImageController
 * 菜品分类图片版
 * @author: yangch
 * @time: 2016/5/28 10:11
 */
@Module(ModuleEnums.MobileDishImage)
@Controller
@IgnoreLogin
@RequestMapping(value = URLConstants.MOBILE_DISH_IMAGE_URL)
public class MobileDishImageController extends AbstractController {
    /**
     * 去列表页
     * @param model
     * @return
     */
    @Module(ModuleEnums.MobileDishImageList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(Model model) {
        try {
            // 获取二级分类
            List<Tag> tagList = new ArrayList<Tag>();
            tagList.addAll(tagFacadeService.listAllByTagId(TagEnum.Dishes.getId()));
            tagList.addAll(tagFacadeService.listAllByTagId(TagEnum.Goods.getId()));
            tagList.addAll(tagFacadeService.listAllByTagId(TagEnum.Drinks.getId()));
            tagList.addAll(tagFacadeService.listAllByTagId(TagEnum.Package.getId()));
            model.addAttribute("tagList", tagList);

            // 从今日特价中获取前两个
            List<DishTagDto> todayCheapList = dishTagService.listDtoByTagId(TagEnum.TodayCheap.getId());
            model.addAttribute("todayCheapActive", todayCheapList.get(0));
            model.addAttribute("todayCheapSecond", todayCheapList.get(1));

            // 从本店特色中获取前两个
            List<DishTagDto> featureList = dishTagService.listDtoByTagId(TagEnum.Feature.getId());
            if (featureList.size() > 2) {
                featureList.remove(2);
            }
            model.addAttribute("featureList", featureList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "mobile/dish/image/list_home";
    }

    /**
     * Ajax 获取分页数据
     * @param pageNo
     * @param pageSize
     * @param searchDto
     * @return
     */
    @Module(value = ModuleEnums.MobileDishImageList)
    @RequestMapping(value = "ajax/list", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxDishPackageList(HttpSession session,
                                    @RequestParam("page") Integer page,
                                    @RequestParam("classify") Integer classify) {

        try {
            // 从菜品中获取接下来的菜品
            DishSearchDto dishSearchDto = new DishSearchDto();
            dishSearchDto.setPageNo(page);
            dishSearchDto.setPageSize(8);
            List<DishDto> dishDtoList = dishService.listBySearchDtoInMobile(dishSearchDto);

            // 从缓存中取出该餐台已点但未下单的菜品
            Integer tableId = (Integer)session.getAttribute("tableId");
            TableOrderCache tableOrderCache = orderDishCacheService.listByTableId(tableId);
            List<OrderDishCache> orderDishCacheList = new ArrayList<OrderDishCache>();
            if (tableOrderCache != null) {
                orderDishCacheList = tableOrderCache.getOrderDishCacheList();
            }

            JSONArray jsonArray = new JSONArray();
            for (DishDto dishDto : dishDtoList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("dishId", dishDto.getId());
                jsonObject.put("name", dishDto.getName());
                if (dishDto.getSmallImg() != null) {
                    jsonObject.put("src", dishDto.getSmallImg().getImgPath());
                }
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

                jsonObject.put("price", dishDto.getPrice().toString());
                jsonObject.put("sale", dishDto.getSalePrice().toString());
                jsonArray.add(jsonObject);
            }

            return sendJsonArray(jsonArray);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
