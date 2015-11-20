package com.emenu.web.controller.admin.dish;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.dish.Unit;
import com.emenu.common.enums.dish.UnitEnum;
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
 * AdminUnitController
 * 菜品单位管理Controller
 * @author dujuan
 * @date 2015/10/28
 */
@Controller
@Module(ModuleEnums.AdminDishUnit)
@RequestMapping(value = URLConstants.ADMIN_DISH_UNIT)
public class AdminUnitController extends AbstractController{

    /**
     * 去列表页面
     * @return
     */
    @Module(ModuleEnums.AdminDishUnitList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toListDishUnit(){
        return "admin/dish/unit/list_home";
    }

    /**
     * Ajax获取分页数据
     * @param curPage
     * @param pageSize
     * @return
     */
    @Module(ModuleEnums.AdminDishUnitList)
    @RequestMapping(value = "ajax/list/{curPage}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxListDishUnit(@PathVariable("curPage") Integer curPage,
                                       @RequestParam Integer pageSize) {
        List<Unit> unitList = Collections.emptyList();
        try {
            unitList = unitService.listByPage(curPage, pageSize);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        JSONArray jsonArray = new JSONArray();
        for (Unit unit:unitList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", unit.getId());
            jsonObject.put("name", unit.getName());
            jsonObject.put("type", UnitEnum.valueOf(unit.getType()).getName());
            jsonArray.add(jsonObject);
        }

        int dataCount = 0;
        try {
            dataCount = unitService.countAll();
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray, dataCount);
    }

    /**
     * Ajax添加
     * @param unit
     * @return
     */
    @Module(ModuleEnums.AdminDishUnitNew)
    @RequestMapping(value = "ajax", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxNewDishUnit(Unit unit){
        try{
            unitService.newUnit(unit);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax修改
     * @param unit
     * @return
     */
    @Module(ModuleEnums.AdminDishUnitUpdate)
    @RequestMapping(value = "ajax",method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject ajaxUpdateDishUnit(Unit unit){
        try{
            unitService.updateUnit(unit);
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
    @Module(ModuleEnums.AdminDishUnitDel)
    @RequestMapping(value = "ajax/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject ajaxDelDishUint(@PathVariable("id") Integer id){
        try{
            unitService.delById(id);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

}
