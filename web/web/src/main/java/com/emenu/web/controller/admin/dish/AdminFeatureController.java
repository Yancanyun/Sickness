package com.emenu.web.controller.admin.dish;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishTagDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * AdminFeatureController
 *
 * @author: zhangteng
 * @time: 2015/12/3 14:31
 **/
@Module(ModuleEnums.AdminDishManagement)
@Controller
@RequestMapping(URLConstants.ADMIN_DISH_FEATURE_URL)
public class AdminFeatureController extends AbstractController {

    /**
     * 去列表页
     *
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminDishFeature, extModule = ModuleEnums.AdminDishFeatureList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(Model model) {
        try {
            List<DishTagDto> dishTagDtoList = dishTagService.listDtoByTagId(TagEnum.Feature.getId());
            model.addAttribute("dishTagDtoList", dishTagDtoList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/dish/feature/list_home";
    }

    /**
     * 去添加页
     *
     * @return
     */
    @Module(value = ModuleEnums.AdminDishFeature, extModule = ModuleEnums.AdminDishFeatureNew)
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String toNew(Model model) {
        List<Tag> tagList = new ArrayList<Tag>();
        try {
            tagList.addAll(tagFacadeService.listChildrenByTagId(TagEnum.Dishes.getId()));
            tagList.addAll(tagFacadeService.listChildrenByTagId(TagEnum.Drinks.getId()));
            tagList.addAll(tagFacadeService.listChildrenByTagId(TagEnum.Goods.getId()));
            tagList.addAll(tagFacadeService.listChildrenByTagId(TagEnum.Package.getId()));
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        model.addAttribute("tagList", tagList);
        return "admin/dish/feature/new_home";
    }

    /**
     * ajax获取没有选择的菜品
     *
     * @param tagIdList
     * @return
     */
    @Module(value = ModuleEnums.AdminDishFeature)
    @RequestMapping(value = "dish/ajax/list", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxDishList(Integer[] tagIdList) {
        List<Dish> dishList = Collections.emptyList();
        List<Integer> searchTagIdList = Collections.emptyList();
        if (Assert.isNotNull(tagIdList) && tagIdList[0] != -1) { // 若选择"全部，则tagIdList=-1"，此时无需传-1给Service
            searchTagIdList = Arrays.asList(tagIdList);
        }
        try {
            dishList = dishTagService.listNotSelectedByTagId(TagEnum.Feature.getId(), searchTagIdList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        JSONArray jsonArray = new JSONArray();
        for (Dish dish : dishList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", dish.getId());
            jsonObject.put("dishNumber", dish.getDishNumber());
            jsonObject.put("assistantCode", dish.getAssistantCode());
            jsonObject.put("name", dish.getName());
            jsonObject.put("unit", dish.getUnitName());
            jsonObject.put("price", dish.getPrice());
            if (dish.getDiscount() != 10) {
                jsonObject.put("discount", dish.getDiscount().toString() + " 折");
            } else {
                jsonObject.put("discount", "无");
            }
            jsonObject.put("salePrice", dish.getSalePrice());
            jsonObject.put("status", dish.getStatus());
            jsonObject.put("category", dish.getCategoryNameStr());
            jsonObject.put("tag", dish.getTagNameStr());

            jsonArray.add(jsonObject);
        }

        return sendJsonArray(jsonArray);
    }

    /**
     * ajax添加
     *
     * @param dishIds
     * @return
     */
    @Module(value = ModuleEnums.AdminDishFeature, extModule = ModuleEnums.AdminDishFeatureNew)
    @RequestMapping(value = "ajax", method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxNew(Integer[] dishIds) {
        try {
            dishTagService.newByTagId(TagEnum.Feature.getId(), dishIds);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * ajax删除
     *
     * @param id
     * @return
     */
    @Module(value = ModuleEnums.AdminDishFeature, extModule = ModuleEnums.AdminDishFeatureDel)
    @RequestMapping(value = "ajax/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSON ajaxDel(@PathVariable("id") Integer id) {
        try {
            dishTagService.delById(id);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }
}
