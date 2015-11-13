package com.emenu.web.controller.admin.dish.tag;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.dish.tag.TagDto;
import com.emenu.common.entity.dish.tag.Tag;
import com.emenu.common.enums.dish.TagEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    public String toListTag(Model model) throws Exception {
        List<TagDto> tagDtoList = tagFacadeService.listDishByCurrentId();
        Map<TagDto, Integer> tagDtoMap = new HashMap<TagDto, Integer>();
        Map<TagDto, Integer> childrenTagDtoMap = new HashMap<TagDto, Integer>();
        for(TagDto tagDto : tagDtoList){
            tagDtoMap.put(tagDto, 1);
            for(TagDto tagDto1 :tagDto.getChildTagList()){
                childrenTagDtoMap.put(tagDto1, 2);
            }
        }
        model.addAttribute("tagDtoMap", tagDtoMap);
        model.addAttribute("childrenTagDtoMap", childrenTagDtoMap);
        return "admin/dish/tag/list_home";
    }


    /**
     * Ajax添加
     * @param tag
     * @return
     */
    @Module(ModuleEnums.AdminDishTagNew)
    @RequestMapping(value = "ajax", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxNewTag(Tag tag,
                                 @RequestParam("printreId") Integer printerId){
        try{
            tagFacadeService.newTag(tag);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            return sendJsonObject(AJAX_FAILURE_CODE);
        }
    }

//    /**
//     * Ajax修改
//     * @param tag
//     * @return
//     */
//    @Module(ModuleEnums.AdminDishTagUpdate)
//    @RequestMapping(value = "ajax",method = RequestMethod.PUT)
//    @ResponseBody
//    public JSONObject ajaxUpdateTag(Tag tag){
//        try{
//            tagService.updateTag(tag);
//            return sendJsonObject(AJAX_SUCCESS_CODE);
//        }catch (SSException e){
//            LogClerk.errLog.error(e);
//            return sendErrMsgAndErrCode(e);
//        }
//    }
//
//    /**
//     * Ajax删除
//     * @param id
//     * @return
//     */
//    @Module(ModuleEnums.AdminDishTagDel)
//    @RequestMapping(value = "ajax/{id}",method = RequestMethod.DELETE)
//    @ResponseBody
//    public JSONObject ajaxDelTag(@PathVariable("id") Integer id){
//        try{
//            tagService.delById(id);
//            return sendJsonObject(AJAX_SUCCESS_CODE);
//        }catch (SSException e){
//            LogClerk.errLog.error(e);
//            return sendErrMsgAndErrCode(e);
//        }
//    }
}
