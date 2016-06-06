package com.emenu.service.wechat;

import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.wechat.HandleMessageAdapter;
import com.pandawork.wechat.msg.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * WeChatMessageAdapter
 *
 * @author: zhangteng
 * @time: 2014/12/12 15:07
 */
@Service("weChatMessageAdapter")
public class WeChatMessageAdapter implements HandleMessageAdapter {

    @Override
    public Msg onTextMsg(Msg4Text msg) {
        return msg;
    }

    @Override
    public Msg onImageMsg(Msg4Image msg) {
        return null;
    }

    @Override
    public Msg onEventMsg(Msg4Event msg) {
       return null;
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
