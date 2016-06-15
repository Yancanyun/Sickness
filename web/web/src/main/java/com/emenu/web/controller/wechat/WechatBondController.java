package com.emenu.web.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.common.utils.WechatUtils;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * WechatBondController
 *
 * @author: yangch
 * @time: 2016/6/14 16:52
 */
@IgnoreAuthorization
@IgnoreLogin
@Controller
@RequestMapping(value = URLConstants.WECHAT_URL)
public class WechatBondController extends AbstractController {
    /**
     * 去绑定页
//     * @param code
     * @param model
     * @return
     */
    @RequestMapping(value = "bond", method = RequestMethod.GET)
    public String ToBondWechat(@RequestParam("code") String code,
                               Model model) {
        try {
            // 获取OpenId
            JSONObject accessTokenJsonObject = WechatUtils.getAccessTokenByCode(code);
            String openId = accessTokenJsonObject.getString("openid");

            if (Assert.isNull(openId)) {
                throw SSException.get(EmenuException.OpenIdError);
            }
            if (vipInfoService.countByOpenId(openId) > 0) {
                throw SSException.get(EmenuException.WechatIsBonded);
            }

            model.addAttribute("openId", openId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return WECHAT_SYS_ERR_PAGE;
        }

        return "wechat/bond";
    }

    /**
     * 绑定微信
     * @param openId
     * @param phone
     * @param password
     */
    @RequestMapping(value = "bond", method = RequestMethod.POST)
    public String  bondWechat(@RequestParam("openId") String openId,
                           @RequestParam("phone") String phone,
                           @RequestParam("password") String password) {
        try {
            vipInfoService.bondWechat(openId, phone, password);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return WECHAT_SYS_ERR_PAGE;
        }

        return "wechat/bondok";
    }
}
