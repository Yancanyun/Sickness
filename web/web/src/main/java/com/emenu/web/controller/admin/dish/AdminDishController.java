package com.emenu.web.controller.admin.dish;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishSearchDto;
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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 菜品管理Controller
 *
 * @author: zhangteng
 * @time: 2015/11/20 16:52
 **/
@Module(value = ModuleEnums.AdminDishManagement)
@Controller
@RequestMapping(value = URLConstants.ADMIN_DISH_URL)
public class AdminDishController extends AbstractController {

    @Module(value = ModuleEnums.AdminDish, extModule = ModuleEnums.AdminDishList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(Model model) {
        List<Tag> tagList = new ArrayList<Tag>();
        try {
            tagList.addAll(tagFacadeService.listAllByTagId(TagEnum.Dishes.getId()));
            tagList.addAll(tagFacadeService.listAllByTagId(TagEnum.Drinks.getId()));
            tagList.addAll(tagFacadeService.listAllByTagId(TagEnum.Goods.getId()));
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        model.addAttribute("tagList", tagList);
        return "admin/dish/dish/list_home";
    }

    @Module(value = ModuleEnums.AdminDish, extModule = ModuleEnums.AdminDishList)
    @RequestMapping(value = "ajax/list/{pageNo}", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(@PathVariable("pageNo") Integer pageNo,
                         @RequestParam("pageSize") Integer pageSize,
                         DishSearchDto searchDto) {
        pageNo = pageNo == null ? 0 : pageSize;
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageNo;
        searchDto.setPageNo(pageNo);
        searchDto.setPageSize(pageSize);
        List<Dish> dishList = Collections.emptyList();
        try {
            dishList = dishService.listBySearchDto(searchDto);
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
            jsonObject.put("unitName", dish.getUnitName());
            jsonObject.put("price", dish.getPrice());
            jsonObject.put("salePrice", dish.getSalePrice());
            jsonObject.put("status", dish.getStatus());

            jsonArray.add(jsonObject);
        }

        int dataCount = 0;
        try {
            dataCount = dishService.countBySearchDto(searchDto);
        } catch (SSException e) {
        	LogClerk.errLog.error(e);
        	return sendErrMsgAndErrCode(e);
        }

        return sendJsonArray(jsonArray, dataCount);
    }
}
