package com.emenu.web.controller.bar.login;

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

/**
 * BarLoginController
 *
 * @author: yangch
 * @time: 2016/7/11 11:08
 */
@IgnoreLogin
@IgnoreAuthorization
@Controller
@Module(ModuleEnums.BarLogin)
@RequestMapping(value = URLConstants.BAR_LOGIN)
public class BarLoginController extends AbstractAppBarController {
    /**
     * Ajax 登录提交
     * @param loginName
     * @param password
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxLogin(@RequestParam("loginName") String loginName,
                          @RequestParam("password") String password) {
        JSONObject jsonObject = new JSONObject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
        try {
            Subject subject = loginManageService.validLogin(token, LoginTypeEnums.BarLogin, getRequest(), getResponse());

            if(subject != null) {
                String sessionId = subject.getSession().getId().toString();
                // 生成T票
                String tgt = sessionId.toString();
                // Base64 Encode
                tgt = Base64.encodeToString(tgt.getBytes());

                SecurityUser user = securityUserService.queryByLoginName(loginName);

                jsonObject.put("uid", user.getId());
                jsonObject.put("tgt", tgt);
            } else {
                throw SSException.get(PartyException.BarLoginFail);
            }
        } catch (SSException e) {
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
    }
}
