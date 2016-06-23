package com.emenu.service.wechat.impl;

import com.emenu.common.dto.vip.VipAccountInfoDto;
import com.emenu.common.entity.vip.VipAccountInfo;
import com.emenu.common.enums.wechat.WeChatMenuEnums;
import com.emenu.common.utils.WeChatUtils;
import com.emenu.service.party.group.vip.VipInfoService;
import com.emenu.service.vip.VipAccountInfoService;
import com.emenu.service.wechat.WeChatMessageService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.util.Assert;
import com.pandawork.wechat.msg.Msg;
import com.pandawork.wechat.msg.Msg4Event;
import com.pandawork.wechat.msg.Msg4Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * WechatMessageServiceImpl
 *
 * @author: yangch
 * @time: 2016/6/20 14:17
 */
@Service("weChatMessageService")
public class WeChatMessageServiceImpl implements WeChatMessageService {
    @Autowired
    private VipInfoService vipInfoService;

    @Autowired
    private VipAccountInfoService vipAccountInfoService;

    private String bondUrl = WeChatUtils.createAuthorizationUrl("http://emenu2.pandawork.net/wechat/bond", true);
    private String updateVipInfoUrl = WeChatUtils.createAuthorizationUrl("http://emenu2.pandawork.net/wechat/vipinfo", true);
    private String updatePhoneUrl = WeChatUtils.createAuthorizationUrl("http://emenu2.pandawork.net/wechat/phone", true);

    @Override
    public Msg doSubscribeEventMessage(Msg4Event msg4Event) throws SSException {
        if (Msg4Event.SUBSCRIBE.equals(msg4Event.getEvent())) { // 添加关注
            Msg4Text msg4Text = new Msg4Text();
            msg4Text.setFromUserName(msg4Event.getToUserName());
            msg4Text.setToUserName(msg4Event.getFromUserName());
            msg4Text.setContent("欢迎关注聚客多电子点餐系统!");
            msg4Text.setFuncFlag("0");

            return msg4Text;
        }
        return null;
    }

    @Override
    public Msg doMenuClickEventMessage(Msg4Event msg4Event) throws SSException {
        WeChatMenuEnums wechatMenuEnums = WeChatMenuEnums.valueOfByKey(msg4Event.getEventKey());
        Msg msg = null;
        switch (wechatMenuEnums) {
            // 修改会员信息
            case UpdateVipInfo: msg = updateVipInfo(msg4Event); break;
            // 修改绑定手机
            case UpdatePhone: msg = updatePhone(msg4Event); break;
            // 查询积分
            case QueryPoint: msg = queryPoint(msg4Event); break;
            // 查询余额
            case QueryBalance: msg = queryBalance(msg4Event); break;
            default:
        }
        return msg;
    }

    /**
     * 修改会员信息
     * @param msg4Event
     * @return
     * @throws SSException
     */
    private Msg updateVipInfo(Msg4Event msg4Event) throws SSException {
        String msg = "";

        // 检查是否已经绑定
        String openId = msg4Event.getFromUserName();
        if (Assert.lessOrEqualZero(vipInfoService.countByOpenId(openId))) {
            msg = "您的微信尚未绑定会员，请先进行<a href =\"" + bondUrl + "\">绑定</a>";
        } else {
            msg = "<a href=\"" + updateVipInfoUrl + "\">修改会员信息</a>";
        }

        Msg4Text msg4Text = new Msg4Text();
        msg4Text.setFromUserName(msg4Event.getToUserName());
        msg4Text.setToUserName(msg4Event.getFromUserName());
        msg4Text.setContent(msg);
        msg4Text.setFuncFlag("0");

        return msg4Text;
    }

    /**
     * 修改绑定手机
     * @param msg4Event
     * @return
     * @throws SSException
     */
    private Msg updatePhone(Msg4Event msg4Event) throws SSException {
        String msg = "";

        // 检查是否已经绑定
        String openId = msg4Event.getFromUserName();
        if (Assert.lessOrEqualZero(vipInfoService.countByOpenId(openId))) {
            msg = "您的微信尚未绑定会员，请先进行<a href =\"" + bondUrl + "\">绑定</a>";
        } else {
            msg = "<a href=\"" + updatePhoneUrl + "\">修改绑定手机</a>";
        }

        Msg4Text msg4Text = new Msg4Text();
        msg4Text.setFromUserName(msg4Event.getToUserName());
        msg4Text.setToUserName(msg4Event.getFromUserName());
        msg4Text.setContent(msg);
        msg4Text.setFuncFlag("0");

        return msg4Text;
    }

    /**
     * 查询积分
     * @param msg4Event
     * @return
     * @throws SSException
     */
    private Msg queryPoint(Msg4Event msg4Event) throws SSException {
        String msg = "";

        // 检查是否已经绑定
        String openId = msg4Event.getFromUserName();
        if (Assert.lessOrEqualZero(vipInfoService.countByOpenId(openId))) {
            msg = "您的微信尚未绑定会员，请先进行<a href=\"" + bondUrl + "\">绑定</a>";
        } else {
            // 根据OpenId获取积分
            VipAccountInfoDto vipAccountInfoDto = vipAccountInfoService.queryByOpenId(openId);
            msg = "您当前积分为: " + vipAccountInfoDto.getIntegral();
        }

        Msg4Text msg4Text = new Msg4Text();
        msg4Text.setFromUserName(msg4Event.getToUserName());
        msg4Text.setToUserName(msg4Event.getFromUserName());
        msg4Text.setContent(msg);
        msg4Text.setFuncFlag("0");

        return msg4Text;
    }

    /**
     * 查询余额
     * @param msg4Event
     * @return
     * @throws SSException
     */
    private Msg queryBalance(Msg4Event msg4Event) throws SSException {
        String msg = "";

        // 检查是否已经绑定
        String openId = msg4Event.getFromUserName();
        if (Assert.lessOrEqualZero(vipInfoService.countByOpenId(openId))) {
            msg = "您的微信尚未绑定会员，请先进行<a href=\"" + bondUrl + "\">绑定</a>";
        } else {
            // 根据OpenId获取余额
            VipAccountInfoDto vipAccountInfoDto = vipAccountInfoService.queryByOpenId(openId);
            msg = "您当前余额为: " + vipAccountInfoDto.getBalance();
        }

        Msg4Text msg4Text = new Msg4Text();
        msg4Text.setFromUserName(msg4Event.getToUserName());
        msg4Text.setToUserName(msg4Event.getFromUserName());
        msg4Text.setContent(msg);
        msg4Text.setFuncFlag("0");

        return msg4Text;
    }
}
