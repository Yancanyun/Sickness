package com.emenu.service.wechat;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.wechat.HandleMessageAdapter;
import com.pandawork.wechat.msg.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * WechatMessageAdapter
 * 处理消息适配器的实现类
 * 暂时只实现了EventMsg
 *
 * @author: yangch
 * @time: 2016/6/20 14:21
 */
@Service("weChatMessageAdapter")
public class WeChatMessageAdapter implements HandleMessageAdapter {

    @Autowired
    @Qualifier("weChatMessageService")
    private WeChatMessageService wechatMessageService;

    @Override
    public Msg onTextMsg(Msg4Text msg) {
        return null;
    }

    @Override
    public Msg onImageMsg(Msg4Image msg) {
        return null;
    }

    @Override
    public Msg onEventMsg(Msg4Event msg) {
        Msg respMsg = null;
        // 菜单点击事件
        if (Msg4Event.CLICK.equals(msg.getEvent())) {
            try {
                respMsg = wechatMessageService.doMenuClickEventMessage(msg);
            } catch (SSException e) {
                LogClerk.errLog.error(e);
            }
        }

        return respMsg;
    }

    @Override
    public Msg onLinkMsg(Msg4Link msg) {
        return null;
    }

    @Override
    public Msg onLocationMsg(Msg4Location msg) {
        return null;
    }

    @Override
    public Msg onVoiceMsg(Msg4Voice msg) {
        return null;
    }

    @Override
    public Msg onErrorMsg(int errorCode) {
        return null;
    }

    @Override
    public Msg onVideoMsg(Msg4Video msg) {
        return null;
    }
}