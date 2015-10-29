package com.emenu.web.controller.admin;

import com.alibaba.fastjson.JSON;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.enums.TrueEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * LoginController
 *
 * @author: zhangteng
 * @time: 15/10/22 下午10:10
 */
@Controller
@RequestMapping(value = URLConstants.ADMIN_URL)
public class AdminController extends AbstractController {

    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toIndex() {
        return "admin/index/index_home";
    }

    /**
     * 去登录页
     *
     * @return
     */
    @IgnoreLogin
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String toLogin() {
        try {
            Subject subject = loginManageService.isLogined(getRequest());
            if (!Assert.isNull(subject)) {
                return "admin/";
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }

        return "admin/login/login";
    }

    /**
     * ajax登录提交
     *
     * @param loginName
     * @param password
     * @param isRemember
     * @return
     */
    @IgnoreLogin
    @RequestMapping(value = "ajax/login", method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxLogin(@RequestParam("loginName") String loginName,
                          @RequestParam("password") String password,
                          @RequestParam(value = "isRememberMe", required = false) Integer isRemember) {
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
        if (!Assert.isNull(isRemember)
                && TrueEnums.True.equals(TrueEnums.valueOf(isRemember))) {
            token.setRememberMe(true);
        }
        try {
            loginManageService.validLogin(token, getRequest(), getResponse());
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * 登录
     *
     * @return
     */
    @IgnoreLogin
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login() {
        return "redirect:/admin";
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout() {
        try {
            loginManageService.logOut(getRequest());
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "redirect:/admin/login";
    }
}
