package com.emenu.service.sms;

import com.pandawork.core.common.exception.SSException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * SmsService
 * 发送短信验证码
 *
 * @author: yangch
 * @time: 2016/6/15 15:57
 */
public interface SmsService {
    /**
     * 发送短信验证码
     * @param phone
     * @param session
     * @return
     * @throws SSException
     */
    public void sendSms(String phone, HttpSession session) throws SSException;
}
