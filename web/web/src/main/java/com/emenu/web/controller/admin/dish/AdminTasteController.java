package com.emenu.web.controller.admin.dish;

import com.emenu.common.annotation.Module;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    @Module(ModuleEnums.AdminDishTasteList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String list(){
        return "admin/dish/taste/list_home";
    }
}
