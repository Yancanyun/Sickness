package com.emenu.web.controller.waiter.login;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.party.security.SecurityUser;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.party.LoginTypeEnums;
import com.emenu.common.exception.PartyException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * WaiterLoginController
 *
 * @author yangch
 * @date 2016/7/12 8:47
 */
@Controller
@Module(ModuleEnums.WaiterLogin)
@RequestMapping(value = URLConstants.WAITER_LOGIN_URL)
public class WaiterLoginController extends AbstractController {
    /**
     * Ajax 登录提交
     * @param loginName
     * @param password
     * @param httpSession
     * @return
     */
    @IgnoreLogin
    @IgnoreAuthorization
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxLogin(@RequestParam("loginName") String loginName,
                                @RequestParam("password") String password,
                                HttpSession httpSession) {
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
        try {
            Subject subject = loginManageService.validLogin(token, LoginTypeEnums.WaiterLogin, getRequest(), getResponse());

            if(subject != null) {
                SecurityUser user = securityUserService.queryByLoginName(loginName);

                // 把partyId放入Session
                Integer partyId = user.getPartyId();
                httpSession.setAttribute("partyId", partyId);
            } else {
                throw SSException.get(PartyException.WaiterLoginFail);
            }
        } catch (SSException e) {
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * Ajax 退出登录提交
     * @return
     */
    @RequestMapping(value = "logout", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxLogout() {
        try {
            loginManageService.logOut(getRequest());
        } catch (SSException e) {
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }
}