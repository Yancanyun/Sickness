package com.emenu.web.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.wechat.config.WeChatConfig;
import com.pandawork.wechat.utils.HttpsUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * WechatTestController
 *
 * @author: yangch
 * @time: 2016/6/6 15:29
 */
@IgnoreAuthorization
@IgnoreLogin
@Controller
@RequestMapping(value = URLConstants.WECHAT_URL)
public class WechatTestController extends AbstractController {
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test(@RequestParam("code") String code,
                       Model model) {
        try {
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
            url = url.replaceAll("APPID", WeChatConfig.appid).
                    replaceAll("SECRET", WeChatConfig.secret).
                    replaceAll("CODE", code);

            JSONObject jsonObject = HttpsUtil.sendRequest(url, HttpsUtil.GET, null);
            String openId = jsonObject.getString("openid");
//            Integer errCode = jsonObject.getInt("errcode");
//            if (errCode.compareTo(0) != 0) {
//                throw new WeChatException(errCode);
//            }

            model.addAttribute("openId", openId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }

        return "wechat/test";
    }
}
