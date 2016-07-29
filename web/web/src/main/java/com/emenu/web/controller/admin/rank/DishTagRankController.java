package com.emenu.web.controller.admin.rank;

import com.emenu.common.annotation.Module;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.persistence.SqlResultSetMapping;

/**
 * DishTagRankController
 * @Author guofengrui
 * @Date 2016/7/28.
 */
@Module(value = ModuleEnums.AdminCountDishTagRankingList)
@Controller
@RequestMapping(value = URLConstants.ADMIN_COUNT_DISH_TAG_RANKING_URL)
public class DishTagRankController extends AbstractController{
    @Module(value = ModuleEnums.AdminCountDishTagRankingList)
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList(Model model){

        return "admin/dish/tag/ranking/list_home";
    }
}
