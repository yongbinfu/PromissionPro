package com.fuyongbin.aspect;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuyongbin.domain.Systemlog;
import com.fuyongbin.mapper.SystemlogMapper;
import com.fuyongbin.util.RequestUtil;
import org.aspectj.lang.JoinPoint;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

public class SystemAspect {
    @Autowired
    private SystemlogMapper systemlogMapper;

    public void writeLog(JoinPoint joinPoint) throws JsonProcessingException {
        System.out.println("--------------记录日志-----------------");
        /*设置时间*/
        Systemlog systemlog = new Systemlog();
        systemlog.setOptime(new Date());
        /*设置ip地址 request添加拦截器 获取请求对象*/
        HttpServletRequest request = RequestUtil.getRequest();
        if (request != null) {
            /*获取ip地址*/
            String IP = request.getRemoteAddr();
            System.out.println(IP);
            systemlog.setIp(IP);
        }
//        方法
//        获取目标执行方法的全路径
//        名字
        String name = joinPoint.getTarget().getClass().getName();
//        获取方法的签名名称
        String signature = joinPoint.getSignature().getName();
        String fuc = name + ":" + signature;
        System.out.println(fuc);
        systemlog.setFunctions(fuc);
//       获取方法参数
        String parmens = new ObjectMapper().writeValueAsString(joinPoint.getArgs());
        systemlog.setParams(parmens);
        System.out.println(systemlog);
        try {
            systemlogMapper.insert(systemlog);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
