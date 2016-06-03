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
    public String toList(@RequestParam(required = false) Integer classifyId,
                         HttpSession session, Model model) {
        try {
            // 把参数中传来的分类ID传到页面上，前端发Ajax请求需要用到
            if (classifyId != null) {
                model.addAttribute("classifyId", classifyId);
            }
            // 获取二级分类
            List<Tag> tagList = new ArrayList<Tag>();
            tagList.addAll(tagFacadeService.listChildrenByTagId(TagEnum.Dishes.getId()));
            tagList.addAll(tagFacadeService.listChildrenByTagId(TagEnum.Goods.getId()));
            tagList.addAll(tagFacadeService.listChildrenByTagId(TagEnum.Drinks.getId()));
            tagList.addAll(tagFacadeService.listChildrenByTagId(TagEnum.Package.getId()));
            model.addAttribute("tagList", tagList);

            // 从今日特价中获取前两个
            List<DishTagDto> todayCheapList = dishTagService.listDtoByTagId(TagEnum.TodayCheap.getId());
            model.addAttribute("todayCheapActive", todayCheapList.get(0));
            model.addAttribute("todayCheapSecond", todayCheapList.get(1));

            // 从本店特色中获取前两个
            List<DishTagDto> featureListAll = dishTagService.listDtoByTagId(TagEnum.Feature.getId());
            List<DishTagDto> featureList = new ArrayList<DishTagDto>();
            if (featureListAll.size() > 2) {
                featureList.add(featureListAll.get(0));
                featureList.add(featureListAll.get(1));
            } else {
                featureList = featureListAll;
            }
            model.addAttribute("featureList", featureList);

            // 从Session中获取TableID
            Integer tableId = (Integer)session.getAttribute("tableId");
            model.addAttribute("tableId", tableId);

            // 从缓存中取出该餐台已点但未下单的菜品
            TableOrderCache tableOrderCache = orderDishCacheService.listByTableId(tableId);
            List<OrderDishCache> orderDishCacheList = new ArrayList<OrderDishCache>();
            if (tableOrderCache != null) {
                orderDishCacheList = tableOrderCache.getOrderDishCacheList();
            }
            Integer dishTotalNumber = 0;
            for (OrderDishCache orderDishCache : orderDishCacheList) {
                dishTotalNumber = dishTotalNumber + orderDishCache.getQuantity();
            }
            if (dishTotalNumber != 0) {
                model.addAttribute("dishTotalNumber", dishTotalNumber);
            }
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
            // 根据分类ID从菜品中获取接下来的菜品
            DishSearchDto dishSearchDto = new DishSearchDto();
            dishSearchDto.setPageNo(page);
            dishSearchDto.setPageSize(8);
            if (classify != null) {
                List<Integer> tagIdList = new ArrayList<Integer>();
                tagIdList.add(classify);
                dishSearchDto.setTagIdList(tagIdList);
            }
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
