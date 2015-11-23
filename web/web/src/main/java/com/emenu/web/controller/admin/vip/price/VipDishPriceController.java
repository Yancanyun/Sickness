package com.emenu.web.controller.admin.vip.price;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.entity.vip.VipDishPricePlan;
import com.emenu.common.utils.URLConstants;
import com.pandawork.core.common.exception.SSException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * @author chenyuting
 * @date 2015/11/23 17:56
 */
@Controller
@RequestMapping(value = URLConstants.ADMIN_VIP_VIP_DISH_PRICE_URL)
public class VipDishPriceController {

    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(){
        return "admin/vip/vip";
    }

    /*@RequestMapping(value = "ajax/list/{curPage}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxList(@PathVariable("curPage") Integer curPage,
                               @RequestParam Integer pageSize,
                               @RequestParam("keyword") String keyword,
                               @RequestParam("vipDishPricePlanId") String vipDishPricePlanId) throws SSException {
        List<VipDishPricePlan> vipDishPricePlanList = Collections.emptyList();
        try{
            if (keyword =="" || keyword == null || keyword.equals("")){

            }
        }
    }*/
}