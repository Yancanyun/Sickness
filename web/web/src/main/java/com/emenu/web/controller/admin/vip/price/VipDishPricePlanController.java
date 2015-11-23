package com.emenu.web.controller.admin.vip.price;

import com.emenu.common.entity.vip.VipDishPricePlan;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * 会员价方案Controller
 *
 * @author chenyuting
 * @date 2015/11/23 15:07
 */
@Controller
@RequestMapping(value = URLConstants.ADMIN_VIP_VIP_DISH_PRICE_PLAN_URL)
public class VipDishPricePlanController extends AbstractController{

    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(Model model){
        List<VipDishPricePlan> vipDishPricePlanList = Collections.emptyList();
        model.addAttribute("vipDishPricePlanList",vipDishPricePlanList);
        return "admin/vip/vip_";
    }

    /*@RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxListVipInfo() throws SSException{
        List<VipDishPricePlan> vipDishPricePlanList = Collections.emptyList();
        try{
        }
    }*/
}