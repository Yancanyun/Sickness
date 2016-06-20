package com.emenu.service.wechat;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.wechat.msg.Msg;
import com.pandawork.wechat.msg.Msg4Event;

/**
 * WechatMessageService
 * 微信发送消息
 *
 * @author: yangch
 * @time: 2016/6/20 14:16
 */
public interface WeChatMessageService {
    /**
     * 点击菜单中"Click"类型按钮时返回的消息
     * @param msg4Event
     * @return
     * @throws SSException
     */
    public Msg doMenuClickEventMessage(Msg4Event msg4Event) throws SSException;
}
