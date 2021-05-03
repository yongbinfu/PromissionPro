package com.fuyongbin.interceptor;

import com.fuyongbin.util.RequestUtil;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RequestInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        System.out.println("来到了拦截器");
        /*存放在本地线程ThreadLocal当中*/
        RequestUtil.setRequest(request);
        return true;
    }
}
