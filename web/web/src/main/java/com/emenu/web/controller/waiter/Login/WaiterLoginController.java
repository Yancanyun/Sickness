package com.emenu.web.controller.waiter.Login;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.entity.party.group.Party;
import com.emenu.common.entity.party.security.SecurityUser;
import com.emenu.common.enums.party.LoginTypeEnums;
import com.emenu.common.exception.PartyException;
import com.pandawork.core.common.exception.SSException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.emenu.web.spring.AbstractController;
/**
 * IngredientController
 * 原配料管理
 * @author xiaozl
 * @date: 2016/5/20
 */
@Controller
@RequestMapping(value = "waiter/login")
public class WaiterLoginController extends AbstractController {

    @IgnoreLogin
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public JSON login(@RequestParam("loginName") String loginName,
                      @RequestParam("password") String password){

        String tgt;
        JSONObject json=new JSONObject();
        UsernamePasswordToken token = new UsernamePasswordToken(loginName, password);
        try {
            Subject subject = loginManageService.validLogin(token, LoginTypeEnums.WariterLogin, getRequest(), getResponse());
            if(subject!=null){
                String sessionId = subject.getSession().getId().toString();
                //生成t票
                String idString = sessionId.toString();
                // base64 encode
                idString = Base64.encodeToString(idString.getBytes());
                SecurityUser user = securityUserService.queryByLoginName(loginName);
                json.put("uid",user.getId());
                json.put("tgt",idString);
            }
            else{
                throw SSException.get(PartyException.WaiterLoginFail);
            }

        } catch (SSException e) {
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(json,0);
    }

}
