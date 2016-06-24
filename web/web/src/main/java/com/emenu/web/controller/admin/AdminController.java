package com.emenu.web.controller.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.enums.TrueEnums;
import com.emenu.common.enums.other.ModuleEnums;
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

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

/**
 * LoginController
 *
 * @author: zhangteng
 * @time: 15/10/22 下午10:10
 */
@Controller
@RequestMapping(value = URLConstants.ADMIN_URL)
public class AdminController extends AbstractController {

    //@Module(ModuleEnums.AdminIndex)
    @IgnoreAuthorization
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
    @IgnoreAuthorization
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String toLogin() {
        try {
            Subject subject = loginManageService.isLogined(getRequest());
            if (!Assert.isNull(subject)) {
                return "redirect:/admin";
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
    @IgnoreAuthorization
    @RequestMapping(value = "ajax/login", method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxLogin(@RequestParam("loginName") String loginName,
                          @RequestParam("password") String password,
                          @RequestParam(value = "isRememberMe", required = false) Integer isRemember,
                          HttpSession httpSession) {
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
        if (!Assert.isNull(isRemember)
                && TrueEnums.True.equals(TrueEnums.valueOf(isRemember))) {
            token.setRememberMe(true);
        }
        try {
            loginManageService.validLogin(token, getRequest(), getResponse());
            httpSession.setAttribute("userName",loginName);
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
    @IgnoreAuthorization
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public String login() {
        return "redirect:/admin";
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
        return "redirect:/admin/login";
    }

    /**
     * 去404
     *
     * @return
     */
    @IgnoreLogin
    @IgnoreAuthorization
    @RequestMapping(value = "404", method = RequestMethod.GET)
    public String to404() {
        return ADMIN_NOT_FOUND_PAGE;
    }

    /**
     * 去403
     *
     * @return
     */
    @IgnoreLogin
    @IgnoreAuthorization
    @RequestMapping(value = "403", method = RequestMethod.GET)
    public String to403() {
        return ADMIN_FORBIDDEN_PAGE;
    }

    /**
     * 去500
     *
     * @return
     */
    @IgnoreLogin
    @IgnoreAuthorization
    @RequestMapping(value = "500", method = RequestMethod.GET)
    public String to500() {
        int statusCode = getResponse().getStatus();
        if (statusCode == HttpServletResponse.SC_BAD_REQUEST) {
            String url = (String) getRequest().getAttribute("eUrl");

            // ajax请求
            if (url.contains("ajax")) {
                JSONObject json = new JSONObject();
                json.put("code", AJAX_FAILURE_CODE);
                json.put("errMsg", "少传了参数!");

                try {
                    getResponse().setContentType("application/json;charset=UTF-8");
                    PrintWriter writer = getResponse().getWriter();
                    writer.write(json.toJSONString());
                    writer.close();
                    return "";
                } catch (Exception e) {
                    LogClerk.errLog.error(e);
                }
            } else {
                getRequest().setAttribute("eMsg", "少传了参数!");
                getRequest().setAttribute("javax.servlet.error.request_uri", url);
            }
        }
        return ADMIN_SYS_ERR_PAGE;
    }
}
