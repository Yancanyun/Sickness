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
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * WeChatInfoController
 *
 * @author: yangch
 * @time: 2016/6/20 16:23
 */
@IgnoreAuthorization
@IgnoreLogin
@Controller
@RequestMapping(value = URLConstants.WECHAT_URL)
public class WeChatVipInfoController extends AbstractController {
    /**
     * 去修改会员信息页
     * @param code
     * @param model
     * @return
     */
    @RequestMapping(value = "vipinfo", method = RequestMethod.GET)
    public String ToUpdateVipInfo(@RequestParam("code") String code,
                                  Model model) {
        try {
            // 获取OpenId
            JSONObject accessTokenJsonObject = WeChatUtils.getAccessTokenByCode(code);
            String openId = accessTokenJsonObject.getString("openid");

            if (Assert.isNull(openId)) {
                throw SSException.get(EmenuException.OpenIdError);
            }

            VipInfo vipInfo = vipInfoService.queryByOpenId(openId);

            String birthday;
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            if (vipInfo.getBirthday() == null) {
                birthday = "";
            } else {
                birthday = sdf.format(vipInfo.getBirthday());
            }

            model.addAttribute("openId", openId);
            model.addAttribute("vipInfo", vipInfo);
            model.addAttribute("birthday", birthday);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return WECHAT_SYS_ERR_PAGE;
        }

        return "wechat/vipinfo";
    }

    /**
     * 修改会员信息
     * @param openId
     * @param name
     * @param sex
     * @param birthday
     * @param qq
     * @param email
     * @param model
     * @return
     */
    @RequestMapping(value = "vipinfo", method = RequestMethod.POST)
    public String updateVipInfo(@RequestParam("openId") String openId,
                                @RequestParam("name") String name,
                                @RequestParam("sex") Integer sex,
                                @RequestParam("birthday") Date birthday,
                                @RequestParam("qq") String qq,
                                @RequestParam("email") String email,
                                Model model) {
        try {
            VipInfo vipInfo = vipInfoService.queryByOpenId(openId);
            vipInfo.setName(name);
            vipInfo.setSex(sex);
            vipInfo.setBirthday(birthday);
            vipInfo.setQq(qq);
            vipInfo.setEmail(email);

            vipInfoService.updateVipInfo(vipInfo);
            model.addAttribute("msg", "修改成功!");

            return "wechat/info";
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return WECHAT_SYS_ERR_PAGE;
        }
    }

    /**
     * 去修改绑定手机页
     * @param code
     * @param model
     * @return
     */
    @RequestMapping(value = "phone", method = RequestMethod.GET)
    public String ToUpdatePhone(@RequestParam("code") String code,
                                Model model) {
        try {
            // 获取OpenId
            JSONObject accessTokenJsonObject = WeChatUtils.getAccessTokenByCode(code);
            String openId = accessTokenJsonObject.getString("openid");

            if (Assert.isNull(openId)) {
                throw SSException.get(EmenuException.OpenIdError);
            }

            VipInfo vipInfo = vipInfoService.queryByOpenId(openId);

            model.addAttribute("openId", openId);
            model.addAttribute("vipInfo", vipInfo);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return WECHAT_SYS_ERR_PAGE;
        }

        return "wechat/phone";
    }

    /**
     * 修改绑定手机
     * @param openId
     */
    @RequestMapping(value = "phone", method = RequestMethod.POST)
    public String updatePhone(@RequestParam("openId") String openId,
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

            VipInfo vipInfo = vipInfoService.queryByOpenId(openId);
            vipInfo.setPhone(phone);

            vipInfoService.updateVipInfo(vipInfo);
            model.addAttribute("msg", "修改绑定手机成功!");

            return "wechat/info";
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return WECHAT_SYS_ERR_PAGE;
        }
    }
}
