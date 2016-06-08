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
@RequestMapping(URLConstants.WECHAT_URL)
public class WechatTestController extends AbstractController {
    /**
     * 通过Code换取网页授权Access_Token
     * 今后可以加到core包中
     * @author yangch
     * @param code
     * @return
     */
    public JSONObject getAccessTokenByCode(String code) {
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";
        url = url.replaceAll("APPID", WeChatConfig.appid).
                replaceAll("SECRET", WeChatConfig.secret).
                replaceAll("CODE", code);

        JSONObject jsonObject = HttpsUtil.sendRequest(url, HttpsUtil.GET, null);
//            Integer errCode = jsonObject.getInt("errcode");
//            if (errCode.compareTo(0) != 0) {
//                throw new WeChatException(errCode);
//            }

        return jsonObject;
    }

    @RequestMapping(value = "test", method = RequestMethod.GET)
    public String test(@RequestParam("code") String code,
                       Model model) {
        try {
            JSONObject jsonObject = getAccessTokenByCode(code);
            String openId = jsonObject.getString("openid");

            model.addAttribute("openId", openId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }

        return "wechat/test";
    }
}
