package com.emenu.web.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ErrorInterceptor
 *
 * @author: zhangteng
 * @time: 2015/11/5 17:56
 **/
public class ErrorInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("after controller");
        String url = request.getScheme() + "://" +
                        request.getServerName() + request.getContextPath() +
                        request.getServerPort() + request.getServletPath();
        String queryString = request.getQueryString();
        queryString = queryString == null ? "" : queryString;
        url += queryString;
        System.out.println(url);
        request.setAttribute("javax.servlet.error.request_uri", url);
        super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        super.afterCompletion(request, response, handler, ex);
    }
}
