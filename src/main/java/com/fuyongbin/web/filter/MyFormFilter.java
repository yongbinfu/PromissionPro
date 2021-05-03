package com.fuyongbin.web.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuyongbin.domain.AjaxRes;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

public class MyFormFilter extends FormAuthenticationFilter {
    @Override
    protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response) throws Exception {
        response.setCharacterEncoding("utf-8");
        System.out.println("认证成功");
        AjaxRes ajaxRes = new AjaxRes();
        ajaxRes.setSuccess(true);
        ajaxRes.setMessage("登录成功");
        String jsonString = new ObjectMapper().writeValueAsString(ajaxRes);
        response.getWriter().print(jsonString);
        return false;
    }

    @Override
    protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException e, ServletRequest request, ServletResponse response) {
        AjaxRes ajaxRes = new AjaxRes();
        ajaxRes.setSuccess(false);
        if (e!=null){
            /*获取异常名*/
            String excepetionName = e.getClass().getName();
            if (excepetionName.equals(UnknownAccountException.class.getName())){
                ajaxRes.setMessage("账号不正确");
            }else if (excepetionName.equals(IncorrectCredentialsException.class.getName())){
                ajaxRes.setMessage("密码不正确");
            }else {
                System.out.println(excepetionName);
                ajaxRes.setMessage("未知错误");
            }
        }
        try {
            response.setCharacterEncoding("utf-8");
            String jsonString = new ObjectMapper().writeValueAsString(ajaxRes);
            response.getWriter().print(jsonString);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return false;
    }
}
