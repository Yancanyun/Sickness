package com.emenu.web.controller.admin.dish.tag;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * AdminTagController
 * 菜品分类管理Controller
 * @author dujuan
 * @date 2015/10/28
 */
@Controller
@Module(ModuleEnums.AdminDishTag)
@RequestMapping(value = "admin/dish/tag")
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
        return null;
    }
}
