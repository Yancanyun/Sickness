package com.emenu.web.controller.admin.dish.tag;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.dish.tag.Tag;
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
 * AdminTagController
 * 菜品分类管理Controller
 * @author dujuan
 * @date 2015/10/28
 */
//TODO 等前端页面出来之后再继续写这个COntroller，现在方法不完善
@Controller
@Module(ModuleEnums.AdminDishTag)
@RequestMapping(value = URLConstants.ADMIN_DISH_TAG)
public class AdminTagController extends AbstractController{

    /**
     * 去列表页
     * @return
     */
    @RequestMapping(value = {"","list"}, method = RequestMethod.GET)
    @Module(ModuleEnums.AdminDishTagList)
    public String toListTag(){
        return "admin/dish/tag/list_home";
    }

    /**
     * Ajax分页获取列表
     * @param curPage
     * @param pageSize
     * @return
     */
    @RequestMapping(value = {"ajax/list/{curPage}"},method = RequestMethod.GET)
    @Module(ModuleEnums.AdminDishTagList)
    @ResponseBody
    public JSONObject ajaxListTag(@PathVariable("curPage") Integer curPage,
                                  @RequestParam Integer pageSize){
        List<Tag> tagList = Collections.emptyList();
        try {
            tagList = tagService.listByPage(curPage, pageSize);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        JSONArray jsonArray = new JSONArray();
        for (Tag tag:tagList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", tag.getId());
            jsonObject.put("name", tag.getName());
            jsonObject.put("pid", tag.getpId());
            jsonObject.put("weight", tag.getWeight());
            jsonObject.put("type", tag.getType());
            jsonArray.add(jsonObject);
        }
        int dataCount = 0;
        try {
            dataCount = tagService.countAll();
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray, dataCount);
    }

    /**
     * Ajax添加
     * @param tag
     * @return
     */
    @Module(ModuleEnums.AdminDishTagNew)
    @RequestMapping(value = "ajax", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxNewTag(Tag tag){
        try{
            tagService.newTag(tag);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax修改
     * @param tag
     * @return
     */
    @Module(ModuleEnums.AdminDishTagUpdate)
    @RequestMapping(value = "ajax",method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject ajaxUpdateTag(Tag tag){
        try{
            tagService.updateTag(tag);
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
    @Module(ModuleEnums.AdminDishTagDel)
    @RequestMapping(value = "ajax/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject ajaxDelTag(@PathVariable("id") Integer id){
        try{
            tagService.delById(id);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
