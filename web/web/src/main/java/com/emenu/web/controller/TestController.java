package com.emenu.web.controller;

import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.IpUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * TestController
 *
 * @author: zhangteng
 * @time: 15/10/14 上午8:56
 */
@Controller
@IgnoreLogin
@RequestMapping(value = "test")
public class TestController extends AbstractController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String test(@RequestParam String openId,
                       @RequestParam Integer id,
                       Model model) {
        //LogClerk.bizLog.info("basePath: " + CommonUtil.getBasepath(getRequest()));
        LogClerk.bizLog.info("client ip: " + IpUtil.getClientIP(getRequest()));
        model.addAttribute("openId", openId);
        return "touch/index";
    }
}
