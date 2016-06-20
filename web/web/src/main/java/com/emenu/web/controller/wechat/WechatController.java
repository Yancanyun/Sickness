package com.emenu.web.controller.wechat;

import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.wechat.config.WeChatConfig;
import com.pandawork.wechat.utils.SignUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * WechatController
 *
 * @author: yangch
 * @time: 2016/6/6 15:55
 */
@IgnoreAuthorization
@IgnoreLogin
@Controller
@RequestMapping(value = URLConstants.WECHAT_URL)
public class WechatController extends AbstractController {
    /**
     * 微信服务器的Get验证
     *
     * @param signature
     * @param timestamp
     * @param nonce
     * @param echostr
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public void wechatGet(@RequestParam(value = "signature") String signature,
                          @RequestParam(value = "timestamp") String timestamp,
                          @RequestParam(value = "nonce") String nonce,
                          @RequestParam(value = "echostr") String echostr) {
        PrintWriter printWriter = null;
        if(SignUtil.checkSignature(WeChatConfig.token, signature, timestamp, nonce)) {
            try {
                LogClerk.errLog.error("echostr: " + echostr);
                printWriter = getResponse().getWriter();
                printWriter.write(echostr);
                printWriter.close();
            } catch (IOException e) {
                LogClerk.errLog.error(e);
            } finally {
                if(printWriter != null) {
                    printWriter.close();
                }
            }
        }
    }

    /**
     * 微信服务器的消息推送
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public void wechatPost() {
        try {
            wechatService.process(getRequest().getInputStream(),getResponse().getOutputStream());
            wechatService.close();
        } catch (IOException e) {
            LogClerk.errLog.error(e);
        }
    }
}
