package com.emenu.web.interceptor;

import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.entity.party.group.Party;
import com.emenu.common.entity.party.security.SecurityUser;
import com.emenu.common.utils.WebConstants;
import com.emenu.service.party.group.PartyService;
import com.emenu.service.party.login.LoginManageService;
import com.emenu.service.party.security.SecurityUserService;
import com.emenu.service.register.RegisterService;
import com.pandawork.core.common.log.LogClerk;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * 登录拦截器
 *
 * @author: zhangteng
 * @time: 2015/8/26 15:26
 **/
public class LoginInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private LoginManageService loginManageService;

    @Autowired
    private SecurityUserService securityUserService;

    @Autowired
    private PartyService partyService;

    @Autowired
    private RegisterService registerService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {


        String ssoServerURL = (String) request.getAttribute("website");
        if (!(handler instanceof HandlerMethod)) {
            // 如果不是方法请求地址，则不拦截
            return true;
        }

        Subject subject = loginManageService.isLogined(request);
        // 已经登录过
        if (subject != null) {
            SecurityUser user = securityUserService.queryByLoginName((String) subject.getPrincipal());

            // 将当前登录用户的LogiName、id放入cookie中
            /*CookieUtil.setCookie(response, CookieNameEnums.WebLoginName, String.valueOf(user.getUserName()));
            CookieUtil.setCookie(response, CookieNameEnums.WebUserId, );*/

            this.setRequestAttribute(user, request);

            return true;
        }

        // 判断类和方式是否有忽略登录
        IgnoreLogin ignoreClazz = ((HandlerMethod) handler).getBean().getClass().getAnnotation(IgnoreLogin.class);
        IgnoreLogin ignoreMethod = ((HandlerMethod) handler).getMethod().getAnnotation(IgnoreLogin.class);
        if (ignoreClazz != null
                || ignoreMethod != null) {
            return true;
        }
        LogClerk.sysout.debug("class or method IgnoreLogin annotation is null, means this request need interceptor");

        // 若未进行注册，则把所有请求都拦截到注册页面
        if (!registerService.isRegistered()){
            String path = request.getContextPath();
            int port = request.getServerPort();
            String basePath = request.getScheme() + "://" + request.getServerName()
                    + (port == 80 ? "" : (":" + port)) + path + "/";

            response.sendRedirect(basePath + "register");

            return false;
        }

        // 执行到这里，说明用户没有记住登录

        // ajax请求
        if (((HandlerMethod) handler).getMethod().getAnnotation(ResponseBody.class) != null) {
            response.getWriter().append("0");
            response.getWriter().flush();
            return false;
        }

        // 不是ajax请求
        String serviceURL = this.getBasePath(request);
        String rURL = serviceURL + request.getServletPath();
        RequestMethod[] requestMethods = ((HandlerMethod) handler).getMethod().getAnnotation(RequestMapping.class).method();
        boolean fountGet = false;
        for (RequestMethod requestMethod : requestMethods) {
            if (requestMethod.equals(RequestMethod.GET)) {
                fountGet = true;
                break;
            }
        }
        if (!fountGet) {
            rURL = "";
        } else {
            Enumeration<String> paramsEnum = request.getParameterNames();
            StringBuffer sb = new StringBuffer();
            while (paramsEnum.hasMoreElements()) {
                String paramName = paramsEnum.nextElement();
                String[] values = request.getParameterValues(paramName);
                for (int i = 0;i < values.length; ++i) {
                    sb.append("&" + paramName + "=" + values[i]);
                }
            }
            if (sb.length() > 0) {
                String params = "?" + sb.toString().substring(1);
                rURL += params;
            }
        }
        String redirectURL = constructSspLoginUrl(ssoServerURL, rURL);
        response.sendRedirect(redirectURL);
        return false;
    }

    private String constructSspLoginUrl(String sUrl, String returnUrl) {
        sUrl = sUrl + "admin/login?returnURL=" + returnUrl;
        LogClerk.sysout.debug("sURL for login is " + sUrl);
        return sUrl;
    }

    private String getBasePath(HttpServletRequest request) {
        String basePath = request.getScheme() + "://" +
                request.getServerName() +
                (request.getServerPort() == 80 ? "" : (":" + request.getServerPort())) +
                request.getContextPath();
        return basePath;
    }

    private void setRequestAttribute(SecurityUser user, HttpServletRequest request) throws Exception {
        // 用户的userId
        request.setAttribute(WebConstants.WEB_SECURITY_USER_ID, user.getId());
        // 用户partyId
        request.setAttribute(WebConstants.WEB_PARTY_ID, user.getPartyId());
        // 登录名 loginName
        request.setAttribute(WebConstants.WEB_USER_LOGIN_NAME, user.getLoginName());
        // 用户类型 partyType
        Integer partyType = partyService.queryPartyTypeById(user.getPartyId());
        request.setAttribute(WebConstants.WEB_PARTY_TYPE,partyType);
    }
}
