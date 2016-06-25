package com.emenu.web.controller.waiter.login;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.wechat.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 服务员登录
 *
 * @author chenyuting
 * @date 2016/6/16 10:48
 */
@Controller
@RequestMapping(value = URLConstants.WAITER_LOGIN_URL)
public class WaiterLoginController extends AbstractController {

    /*@IgnoreLogin
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject login(@RequestParam("username") String username,
                            @RequestParam("passwork") String password){
        String tgt;
        JSONObject jsonObject = new JSONObject();
        try{
            User user = loginManageService
        }
    }*/
}