package com.emenu.service.party.login.impl;

import com.emenu.common.entity.party.security.SecurityUser;
import com.emenu.common.enums.party.EnableEnums;
import com.emenu.common.exception.PartyException;
import com.emenu.common.utils.DateUtils;
import com.emenu.service.party.login.LoginManageService;
import com.emenu.service.party.login.SubjectStore;
import com.emenu.service.party.security.SecurityUserService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.common.util.CommonUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.session.ExpiredSessionException;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.Serializable;

/**
 * LoginManageServiceImpl
 *
 * @author: zhangteng
 * @time: 2015/10/22 15:21
 **/
@Service("loginManageService")
public class LoginManageServiceImpl implements LoginManageService {

    // 登录用户的cookie标识
    public static final String USER_ID_COOKIE_NAME = "uid";

    @Autowired
    @Qualifier("subjectStoreLRUImpl")
    private SubjectStore subjectStore;

    @Autowired
    @Qualifier("securityManager")
    private DefaultSecurityManager securityManager;

    @Autowired
    @Qualifier("sessionIdCookie")
    private SimpleCookie simpleCookie;

    @Autowired
    @Qualifier("securityUserService")
    private SecurityUserService securityUserService;

    @Override
    public Subject isLogined(HttpServletRequest request) throws SSException {
        Subject subject = null;
        String uidCookie = this.queryCookieValue(request, USER_ID_COOKIE_NAME);
        // 用户ID为空，则说明用户没登录过
        if (Assert.isNull(uidCookie)) {
            return subject;
        }

        subject = this.querySubject(request);
        // 没有找到TGT，说明用户没有登录
        if (Assert.isNull(subject)) {
            return subject;
        }

        // 如果没有通过身份验证，说明没有登录
        if (!subject.isAuthenticated()) {
            return null;
        }

        String loginName = "";
        try {
            loginName = (String) subject.getPrincipal();
        } catch (UnknownSessionException e) { // 不存在的session
            LogClerk.errLog.error(e);

            // 删除subject
            this.delSubject(request);
            return null;
        } catch (ExpiredSessionException e) { // session过期
            LogClerk.errLog.error(e);

            // 删除subject
            this.delSubject(request);
            return null;
        }

        SecurityUser user = securityUserService.queryByLoginName(loginName);
        if (Assert.isNull(user)) {
            return null;
        }
        Integer uid = user.getId();
        EnableEnums statusEnums = EnableEnums.valueOf(user.getId());
        if (Assert.isNull(uid)
                || !uid.toString().equals(uidCookie)
                || Assert.isNull(statusEnums)
                || statusEnums.equals(EnableEnums.Disabled)) {
            return null;
        }

        // 绑定到当前的线程中
        ThreadContext.bind(subject);
        return subject;
    }

    @Override
    public Subject validLogin(UsernamePasswordToken token, HttpServletRequest request, HttpServletResponse response) throws SSException {
        Assert.isNotNull(token, PartyException.LoginTokenNotNull);
        Assert.isNotNull(token.getUsername(), PartyException.LoginNameNotNull);
        String password = String.valueOf(token.getPassword());
        Assert.isNotNull(password, PartyException.PasswordNotNull);

        // 进行md5加密
        password = CommonUtil.md5(password);
        token.setPassword(password.toCharArray());

        Subject subject = null;
        // 登陆验证
        try {
            subject = securityManager.login(null, token);
        } catch (UnknownAccountException e) {
            // 用户不存在
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.UserNotExist);
        } catch (DisabledAccountException e) {
            // 用户被禁用
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.UserDisabled);
        } catch (AuthenticationException e) {
            // 密码错误
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.LoginNameOrPasswordNotCorrect);
        } catch (ExpiredSessionException e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }

        // 添加到缓存中
        this.addSubject(subject);
        // 生成T票
        this.generateTGT(subject.getSession().getId().toString(), request, response, token.isRememberMe());

        return subject;
    }

    @Override
    public void logOut(HttpServletRequest request) throws SSException {
        String uidCookie = this.queryCookieValue(request, USER_ID_COOKIE_NAME);
        // 用户id为空，说明没有登录
        if (Assert.isNull(uidCookie)) {
            return ;
        }

        Subject subject = this.querySubject(request);
        // subject为空，说明没有登录
        if (Assert.isNull(subject)) {
            return ;
        }
        subject.logout();
    }

    @Override
    public void addSubject(Subject subject) throws SSException {
        subjectStore.addTGT(subject.getSession().getId().toString(), subject);
        ThreadContext.bind(subject);
    }

    @Override
    public void generateTGT(String sessionId, HttpServletRequest request, HttpServletResponse response, boolean isRemember) throws SSException {
        Subject subject = subjectStore.querySubject(sessionId);
        // 如果不存在,则不需要生成
        if (Assert.isNull(subject)) {
            return ;
        }

        // 保存到客户端中
        storeSessionIdToCookie(sessionId, request, response, isRemember);
        if (isRemember) {
            subject.getSession().setTimeout(DateUtils.SECONDS_OF_ONE_MONTH);
        }

        // 和当前线程绑定
        ThreadContext.bind(subject);

        // 根据用户名获取用户id
        String loginName = (String) subject.getPrincipal();
        SecurityUser user = securityUserService.queryByLoginName(loginName);
        if (Assert.isNull(user)) {
            return ;
        }
        // 保存uid到客户端中
        storeUidToCookie(user.getId(), request, response, isRemember);
    }

    @Override
    public Subject querySubject(HttpServletRequest request) throws SSException {
        // 获取subject cookie
        String subjectCookie = this.queryCookieValue(request, simpleCookie.getName());
        // tgt为空,说明用户没有登录过
        if (Assert.isNull(subjectCookie)) {
            return null;
        }

        Subject subject = subjectStore.querySubject(subjectCookie);

        return subject;
    }

    @Override
    public void delSubject(HttpServletRequest request) throws SSException {
        // 获取subject cookie
        String subjectCookie = this.queryCookieValue(request, simpleCookie.getName());
        // tgt为空,说明用户没有登录过
        if (Assert.isNull(subjectCookie)) {
            return ;
        }

        subjectStore.delTGT(subjectCookie);
    }

    /**
     * 把sessionId存储到客户端cookie中
     *
     * @param sessionId
     * @param request
     * @param response
     * @param isRemember
     */
    private void storeSessionIdToCookie(Serializable sessionId,
                                         HttpServletRequest request,
                                         HttpServletResponse response,
                                         boolean isRemember) {
        if (sessionId == null) {
            String msg = "sessionId cannot be null when persisting for subsequent requests.";
            throw new IllegalArgumentException(msg);
        }
        org.apache.shiro.web.servlet.Cookie template = simpleCookie;
        org.apache.shiro.web.servlet.Cookie cookie = new SimpleCookie(template);
        if (isRemember) {
            cookie.setMaxAge(DateUtils.SECONDS_OF_ONE_MONTH);
        }
        String idString = sessionId.toString();
        cookie.setValue(idString);
        cookie.saveTo(request, response);
    }

    /**
     * 把uid存储到客户端cookie中
     *
     * @param uid
     * @param request
     * @param response
     * @param isRemember
     */
    private void storeUidToCookie(Serializable uid,
                                   HttpServletRequest request,
                                   HttpServletResponse response,
                                   boolean isRemember) {
        org.apache.shiro.web.servlet.Cookie template = simpleCookie;
        org.apache.shiro.web.servlet.Cookie cookie = new SimpleCookie(template);
        if (isRemember) {
            cookie.setMaxAge(DateUtils.SECONDS_OF_ONE_MONTH);
        }
        cookie.setName(USER_ID_COOKIE_NAME);
        cookie.setValue(uid.toString());
        cookie.saveTo(request, response);
    }

    private String queryCookieValue(HttpServletRequest request,
                                     String cookieName) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals(cookieName)) {
                    return cookie.getValue();
                }
            }
        }
        return "";
    }
}
