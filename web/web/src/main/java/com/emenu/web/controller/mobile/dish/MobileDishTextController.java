package com.emenu.web.controller.mobile.dish;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenyuting
 * @date 2016/5/28 8:45
 */
@Controller
@RequestMapping(value = URLConstants.MOBILE_DISH_TEXT_URL)
public class MobileDishTextController extends AbstractController {

    @IgnoreLogin
    @RequestMapping(value = {"","list"}, method = RequestMethod.GET)
    public String toList(){
        return "mobile/dish/text/list_home";
    }

    /*@RequestMapping(value = "ajax/list/{curPage}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject AjaxMobileDishText(@PathVariable("curPage") Integer curPage,
                                         @RequestParam Integer pageSize,
                                         @RequestParam ("keyword") String keyword) {
        List<Dish> dishList = new ArrayList<Dish>();
        List<DishDto> dishDtoList = new ArrayList<DishDto>();
        try{
            if (keyword == "" || keyword == null || keyword.equals("")) {
                dishDtoList = dishService
            } else {
                dishDtoList = vipInfoService.listByKeyword(keyword, curPage, pageSize);
            }
        }
    }*/


}