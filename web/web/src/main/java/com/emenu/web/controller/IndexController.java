package com.emenu.web.controller;

import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * IndexController
 *
 * @author: zhangteng
 * @time: 2015/11/4 16:16
 **/
@IgnoreAuthorization
@IgnoreLogin
@Controller
@RequestMapping(value = "")
public class IndexController extends AbstractController {

    @RequestMapping(value = {"", "/"}, method = RequestMethod.GET)
    public String toIndex() {
        return "redirect:/admin";
    }
}
