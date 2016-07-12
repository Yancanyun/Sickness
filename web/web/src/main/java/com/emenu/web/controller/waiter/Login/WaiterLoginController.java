package com.emenu.web.controller.waiter.login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.party.security.SecurityUser;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.party.LoginTypeEnums;
import com.emenu.common.exception.PartyException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractAppBarController;
import com.pandawork.core.common.exception.SSException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;

/**
 * 服务员登录
 *
 * @author yangch
 * @date 2016/7/12 8:47
 */
@IgnoreLogin
@IgnoreAuthorization
@Controller
@Module(ModuleEnums.WaiterLogin)
@RequestMapping(value = URLConstants.WAITER_LOGIN_URL)
public class WaiterLoginController extends AbstractAppBarController {
    /**
     * Ajax 登录提交
     * @param loginName
     * @param password
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxLogin(@RequestParam("loginName") String loginName,
                          @RequestParam("password") String password,
                          HttpSession httpSession) {
        JSONObject jsonObject = new JSONObject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
        try {
            Subject subject = loginManageService.validLogin(token, LoginTypeEnums.WaiterLogin, getRequest(), getResponse());

            if(subject != null) {
                SecurityUser user = securityUserService.queryByLoginName(loginName);

                Integer partyId = user.getPartyId();

                httpSession.setAttribute("partyId", partyId);

                jsonObject.put("partyId", partyId);
            } else {
                throw SSException.get(PartyException.WaiterLoginFail);
            }
        } catch (SSException e) {
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
    }
}