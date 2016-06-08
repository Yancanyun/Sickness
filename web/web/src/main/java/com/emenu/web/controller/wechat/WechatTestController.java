package com.emenu.web.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.utils.URLConstants;
import com.emenu.common.utils.WechatUtils;
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
@RequestMapping(URLConstants.WECHAT_URL)
public class WechatTestController extends AbstractController {
    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test(@RequestParam("code") String code,
                       Model model) {
        try {
            JSONObject accessTokenJsonObject = WechatUtils.getAccessTokenByCode(code);
            String openId = accessTokenJsonObject.getString("openid");
            String accessToken = accessTokenJsonObject.getString("access_token");

            JSONObject userInfoJsonObject = WechatUtils.getUserInfo(openId, accessToken);
            String nickname = userInfoJsonObject.getString("nickname");
            String sex = userInfoJsonObject.getString("sex");
            String country = userInfoJsonObject.getString("country");
            String province = userInfoJsonObject.getString("province");
            String city = userInfoJsonObject.getString("city");
            String headImgUrl = userInfoJsonObject.getString("headimgurl");

            model.addAttribute("openId", openId);
            model.addAttribute("accessToken", accessToken);
            model.addAttribute("nickname", nickname);
            model.addAttribute("sex", sex);
            model.addAttribute("country", country);
            model.addAttribute("province", province);
            model.addAttribute("city", city);
            model.addAttribute("headImgUrl", headImgUrl);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }

        return "wechat/test";
    }
}
