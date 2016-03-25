package com.emenu.web.controller.admin.dish;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.DishSearchDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.enums.dish.DishStatusEnums;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.service.dish.tag.TagService;
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
 * AdminDishPackageController
 *
 * @author: yangch
 * @time: 2016/3/25 9:48
 */
@Module(value = ModuleEnums.AdminDishManagement)
@Controller
@RequestMapping(value = URLConstants.ADMIN_DISH_PACKAGE)
public class AdminDishPackageController extends AbstractController {
    /**
     * 列表页
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminDishPackage, extModule = ModuleEnums.AdminDishPackageList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(Model model) {
        List<Tag> tagList = new ArrayList<Tag>();
        try {
            tagList.addAll(tagFacadeService.listAllByTagId(TagEnum.Package.getId()));
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        model.addAttribute("tagList", tagList);
        return "admin/dish/package/list_home";
    }

    /**
     * Ajax 获取分页数据
     * @param pageNo
     * @param pageSize
     * @param searchDto
     * @return
     */
    @Module(value = ModuleEnums.AdminDishPackage, extModule = ModuleEnums.AdminDishPackageList)
    @RequestMapping(value = "ajax/list/{pageNo}", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(@PathVariable("pageNo") Integer pageNo,
                         @RequestParam("pageSize") Integer pageSize,
                         DishSearchDto searchDto) {
        pageNo = pageNo == null ? 0 : pageNo;
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        searchDto.setPageNo(pageNo);
        searchDto.setPageSize(pageSize);
        List<Dish> dishList = Collections.emptyList();
        try {
            dishList = dishPackageService.listBySearchDto(searchDto);
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
            try {
                jsonObject.put("categoryId", tagFacadeService.queryById(dish.getTagId()).getName());
            } catch (SSException e) {
                LogClerk.errLog.error(e);
                return sendErrMsgAndErrCode(e);
            }
            jsonObject.put("price", dish.getPrice());
            jsonObject.put("salePrice", dish.getSalePrice());
            jsonObject.put("status", dish.getStatus());
            jsonObject.put("likeNums", dish.getLikeNums());

            jsonArray.add(jsonObject);
        }

        int dataCount = 0;
        try {
            dataCount = dishPackageService.countBySearchDto(searchDto);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonArray(jsonArray, dataCount);
    }

    /**
     * Ajax 删除
     * @param id
     * @return
     */
    @Module(value = ModuleEnums.AdminDishPackage, extModule = ModuleEnums.AdminDishPackageDel)
    @RequestMapping(value = "ajax/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSON ajaxDelete(@PathVariable("id") Integer id) {
        try {
            // 因为套餐也是一个"菜品"，因而直接使用dishService中的方法
            dishService.delById(id);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * Ajax 修改状态
     * @param id
     * @param status
     * @return
     */
    @Module(value = ModuleEnums.AdminDishPackage, extModule = ModuleEnums.AdminDishPackageUpdate)
    @RequestMapping(value = "ajax/status/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JSON ajaxUpdateStatus(@PathVariable("id") Integer id,
                                 @RequestParam("status") Integer status) {
        try {
            // 因为套餐也是一个"菜品"，因而直接使用dishService中的方法
            dishService.updateStatusById(id, DishStatusEnums.valueOf(status));
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }
}
