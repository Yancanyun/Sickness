package com.emenu.web.controller.cook;

import com.alibaba.fastjson.JSON;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.enums.TrueEnums;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.party.LoginTypeEnums;
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

import javax.servlet.http.HttpSession;

/**
 * CookLoginController
 *
 * @author: yangch
 * @time: 2016/7/27 19:28
 */
@Controller
@RequestMapping(value = URLConstants.COOK_URL)
public class CookLoginController extends AbstractController{
    /**
     * 去登录页
     *
     * @return
     */
    @IgnoreLogin
    @IgnoreAuthorization
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toLogin() {
        try {
            Subject subject = loginManageService.isLogined(getRequest());
            if (!Assert.isNull(subject)) {
                return "redirect:/cook/ordermanage";
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }

        return "cook/login";
    }

    /**
     * 登录
     *
     * @return
     */
    @IgnoreLogin
    @IgnoreAuthorization
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String login() {
        return "redirect:/cook/ordermanage";
    }

    /**
     * ajax登录提交
     *
     * @param username
     * @param password
     * @return
     */
    @IgnoreLogin
    @IgnoreAuthorization
    @RequestMapping(value = "ajax/login", method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxLogin(@RequestParam("username") String username,
                          @RequestParam("password") String password,
                          HttpSession httpSession) {
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        try {
            loginManageService.validLogin(token, LoginTypeEnums.BackKitchenLogin, getRequest(), getResponse());
            httpSession.setAttribute("userName", username);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * 退出登录
     * @return
     */
    @IgnoreAuthorization
    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public String logout() {
        try {
            loginManageService.logOut(getRequest());
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "redirect:/cook";
    }
}
