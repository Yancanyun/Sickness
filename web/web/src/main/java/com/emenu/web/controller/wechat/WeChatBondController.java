package com.emenu.web.controller.wechat;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.entity.party.group.vip.VipInfo;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.common.utils.WeChatUtils;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.Date;

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
public class WeChatBondController extends AbstractController {
    /**
     * 去绑定页
     * @param code
     * @param model
     * @return
     */
    @RequestMapping(value = "bond", method = RequestMethod.GET)
    public String ToBondWechat(@RequestParam("code") String code,
                               Model model) {
        try {
            // 获取OpenId
            JSONObject accessTokenJsonObject = WeChatUtils.getAccessTokenByCode(code);
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
     * @param validCode
     */
    @RequestMapping(value = "bond", method = RequestMethod.POST)
    public String bondWechat(@RequestParam("openId") String openId,
                             @RequestParam("validCode") String validCode,
                             HttpSession session,
                             Model model) {
        try {
            if (!validCode.equals(session.getAttribute("validCode"))) {
                throw SSException.get(EmenuException.ValidCodeWrong);
            }

            String phone = (String)session.getAttribute("phone");
            if (Assert.isNull(phone)) {
                throw SSException.get(EmenuException.PhoneError);
            }

            vipInfoService.bondWechat(openId, phone);
            model.addAttribute("msg", "绑定成功!");

            return "wechat/info";
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return WECHAT_SYS_ERR_PAGE;
        }
    }

    /**
     * Ajax 发送验证码
     * @param phone
     * @return
     */
    @RequestMapping(value = "ajax/valid", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxSendSms(@RequestParam("phone") String phone,
                                  HttpSession session) {
        try {
            if (Assert.isNull(phone)) {
                throw SSException.get(EmenuException.PhoneError);
            }

            // 120秒内只可发送一次
            Boolean canSend = false;
            Date nowTime = new Date();
            if (session.getAttribute("sendTime") == null || session.getAttribute("sendTime") == "") {
                canSend = true;
            } else {
                Date sendTime = (Date)session.getAttribute("sendTime");
                long diff = (nowTime.getTime() - sendTime.getTime()) / 1000;
                if (diff > 120) {
                    canSend = true;
                }
            }

            if (!canSend) {
                throw SSException.get(EmenuException.SendTooFrequently);
            }

            // 生成四位验证码
            int[] validCode = new int[4];
            for (int i = 0; i < 4; i++) {
                validCode[i] = (int)(Math.random() * 10);
            }
            String validCodeStr = "" + validCode[0] + validCode[1] + validCode[2] + validCode[3];

            String text = "【聚客多】您正在进行绑定微信操作，验证码为：" + validCodeStr;

            // 把验证码塞入Session中
            session.setAttribute("validCode", validCodeStr);
            // 把电话号码塞入Session中
            session.setAttribute("phone", phone);
            // 把发送时间塞入Session中
            session.setAttribute("sendTime", nowTime);

            smsService.sendSms(phone, text);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 去解绑页
     * @param code
     * @param model
     * @return
     */
    @RequestMapping(value = "unbond", method = RequestMethod.GET)
    public String ToUnbondWechat(@RequestParam("code") String code,
                                 Model model) {
        try {
            // 获取OpenId
            JSONObject accessTokenJsonObject = WeChatUtils.getAccessTokenByCode(code);
            String openId = accessTokenJsonObject.getString("openid");

            if (Assert.isNull(openId)) {
                throw SSException.get(EmenuException.OpenIdError);
            }
            if (vipInfoService.countByOpenId(openId) == 0) {
                throw SSException.get(EmenuException.WechatIsNotBonded);
            }

            // 获取会员信息
            VipInfo vipInfo = vipInfoService.queryByOpenId(openId);

            model.addAttribute("openId", openId);
            model.addAttribute("vipInfo", vipInfo);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return WECHAT_SYS_ERR_PAGE;
        }

        return "wechat/unbond";
    }

    /**
     * 解绑微信
     * @param openId
     */
    @RequestMapping(value = "unbond", method = RequestMethod.POST)
    public String unbondWechat(@RequestParam("openId") String openId,
                               Model model) {
        try {
            vipInfoService.unbondWechat(openId);

            model.addAttribute("msg", "解绑成功!");
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return WECHAT_SYS_ERR_PAGE;
        }

        return "wechat/info";
    }
}
