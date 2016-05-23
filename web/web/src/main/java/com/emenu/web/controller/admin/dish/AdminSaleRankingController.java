package com.emenu.web.controller.admin.dish;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
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
 * AdminSaleRankingController
 * 销量排行
 * @author: yangch
 * @time: 2016/5/23 14:19
 */
@Module(ModuleEnums.AdminDishManagement)
@Controller
@RequestMapping(URLConstants.ADMIN_DISH_SALE_RANKING_URL)
public class AdminSaleRankingController extends AbstractController {
    /**
     * 去列表页
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminDishSaleRanking, extModule = ModuleEnums.AdminDishSaleRankingList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toSaleRankingList(Model model) {
        try {
            List<DishTagDto> dishTagDtoList = dishTagService.listDtoByTagId(TagEnum.SaleRanking.getId());
            model.addAttribute("dishTagDtoList", dishTagDtoList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/dish/sale/ranking/list_home";
    }

    /**
     * 去添加页
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminDishSaleRanking, extModule = ModuleEnums.AdminDishSaleRankingNew)
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String toSaleRankingNew(Model model) {
        List<Tag> tagList = new ArrayList<Tag>();
        try {
            tagList.addAll(tagFacadeService.listAllByTagId(TagEnum.Dishes.getId()));
            tagList.addAll(tagFacadeService.listAllByTagId(TagEnum.Drinks.getId()));
            tagList.addAll(tagFacadeService.listAllByTagId(TagEnum.Goods.getId()));
            tagList.addAll(tagFacadeService.listAllByTagId(TagEnum.Package.getId()));
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        model.addAttribute("tagList", tagList);
        return "admin/dish/sale/ranking/new_home";
    }

    /**
     * Ajax 获取没有选择的菜品
     * @param tagIdList
     * @return
     */
    @Module(value = ModuleEnums.AdminDishSaleRanking)
    @RequestMapping(value = "dish/ajax/list", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxDishList(Integer[] tagIdList) {
        List<Dish> dishList = Collections.emptyList();
        List<Integer> searchTagIdList = Collections.emptyList();
        if (Assert.isNotNull(tagIdList)) {
            searchTagIdList = Arrays.asList(tagIdList);
        }
        try {
            dishList = dishTagService.listNotSelectedByTagId(TagEnum.SaleRanking.getId(), searchTagIdList);
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
     * Ajax 添加
     * @param dishId
     * @return
     */
    @Module(value = ModuleEnums.AdminDishSaleRanking, extModule = ModuleEnums.AdminDishSaleRankingNew)
    @RequestMapping(value = "ajax", method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxNewSaleRanking(Integer[] dishId) {
        try {
            dishTagService.newByTagId(TagEnum.SaleRanking.getId(), dishId);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * Ajax 删除
     * @param id
     * @return
     */
    @Module(value = ModuleEnums.AdminDishSaleRanking, extModule = ModuleEnums.AdminDishSaleRankingDel)
    @RequestMapping(value = "ajax/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSON ajaxDelSaleRanking(@PathVariable("id") Integer id) {
        try {
            dishTagService.delById(id);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }
}
