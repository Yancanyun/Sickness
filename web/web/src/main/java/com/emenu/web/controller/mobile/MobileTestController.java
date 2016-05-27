package com.emenu.web.controller.mobile;

import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.web.spring.AbstractController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * MobileTestController
 *
 * @author: yangch
 * @time: 2016/5/27 10:39
 */
@Controller
@RequestMapping(value = "mobile/test")
public class MobileTestController extends AbstractController {
    @IgnoreAuthorization
    @IgnoreLogin
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String test() {
        return "mobile/test/list_home";
    }
}
