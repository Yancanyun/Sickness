package com.emenu.web.controller.admin.rank;

import com.emenu.common.annotation.Module;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * DishSaleRankController
 *
 * @author guofengrui
 * @date 2016/7/26.
 */
@Module(value = ModuleEnums.AdminDishSaleRanking)
@Controller
@RequestMapping(value = URLConstants.ADMIN_DISH_SALE_RANKING_URL)
public class DishSaleRankController extends AbstractController {

    @Module(value = ModuleEnums.AdminDishSaleRankingList)
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList(Module module){

        return "admin/dish/sale/ranking/list_home";
    }
}
