package com.emenu.service.wechat.impl;

import com.emenu.common.enums.wechat.WeChatMenuEnums;
import com.emenu.service.wechat.WeChatMessageService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.wechat.msg.Msg;
import com.pandawork.wechat.msg.Msg4Event;
import com.pandawork.wechat.msg.Msg4Text;
import org.springframework.stereotype.Service;

/**
 * WechatMessageServiceImpl
 *
 * @author: yangch
 * @time: 2016/6/20 14:17
 */
@Service("weChatMessageService")
public class WeChatMessageServiceImpl implements WeChatMessageService {
    @Override
    public Msg doMenuClickEventMessage(Msg4Event msg4Event) throws SSException {
        WeChatMenuEnums wechatMenuEnums = WeChatMenuEnums.valueOfByKey(msg4Event.getEventKey());
        Msg msg = null;
        switch (wechatMenuEnums) {
            // 查询积分
            case QueryPoint: msg = queryPoint(msg4Event); break;
            default:
        }
        return msg;
    }

    private Msg queryPoint(Msg4Event msg4Event) throws SSException {
        String msgTemp = "您当前积分为：";
        Msg4Text msg4Text = new Msg4Text();
        msg4Text.setFromUserName(msg4Event.getToUserName());
        msg4Text.setToUserName(msg4Event.getFromUserName());
        msg4Text.setContent(msgTemp);
        msg4Text.setFuncFlag("0");
        return msg4Text;
    }
}
