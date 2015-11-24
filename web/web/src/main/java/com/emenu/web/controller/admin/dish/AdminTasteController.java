package com.emenu.web.controller.admin.dish;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.dish.Taste;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * AdminTasteController
 * 菜品口味
 * @author dujuan
 * @date 2015/11/23
 */
@Controller
@Module(ModuleEnums.AdminDishTaste)
@RequestMapping(value = URLConstants.ADMIN_DISH_TASTE)
public class AdminTasteController extends AbstractController{

    /**
     * 去列表页
     * @return
     */
    @Module(ModuleEnums.AdminDishTasteList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(){
        return "admin/dish/taste/list_home";
    }

    /**
     * Ajax获取分页数据
     * @param curPage
     * @param pageSize
     * @return
     */
    @Module(ModuleEnums.AdminDishTasteList)
    @RequestMapping(value = "ajax/list/{curPage}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxListDishTaste(@PathVariable("curPage") Integer curPage,
                                       @RequestParam("pageSize") Integer pageSize) {
        List<Taste> tasteList = Collections.emptyList();
        try {
            tasteList = tasteService.listAll(curPage,pageSize);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONArray jsonArray = new JSONArray();
        for (Taste taste:tasteList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", taste.getId());
            jsonObject.put("name", taste.getName());
            jsonObject.put("relatedCharge", taste.getRelatedCharge());
            jsonArray.add(jsonObject);
        }
        int dataCount = 0;
        try {
            dataCount = tasteService.countAll();
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray, dataCount);
    }

    /**
     * Ajax添加
     * @param taste
     * @return
     */
    @Module(ModuleEnums.AdminDishTasteNew)
    @RequestMapping(value = "ajax", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxNewDishTaste(Taste taste){
        try{
            tasteService.newTaste(taste);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax修改
     * @param taste
     * @return
     */
    @Module(ModuleEnums.AdminDishTasteUpdate)
    @RequestMapping(value = "ajax",method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject ajaxUpdateDishTaste(Taste taste){
        try{
            tasteService.update(taste);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax删除
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminDishTasteDel)
    @RequestMapping(value = "ajax/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject ajaxDelDishTaste(@PathVariable("id") Integer id){
        try{
            tasteService.delById(id);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
