package com.emenu.web.controller.mobile.dish;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author chenyuting
 * @date 2016/5/28 8:45
 */
@IgnoreLogin
@Controller
@RequestMapping(value = URLConstants.MOBILE_DISH_TEXT_URL)
public class MobileDishTextController extends AbstractController {


    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toMobileDishText(){
        return "";
    }

    /*@RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject mobileDishText() {
    }*/
}