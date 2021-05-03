package com.fuyongbin.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fuyongbin.domain.AjaxRes;
import com.fuyongbin.domain.Employee;
import com.fuyongbin.domain.PageListRes;
import com.fuyongbin.domain.QueryVo;
import com.fuyongbin.service.EmployeeService;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.method.HandlerMethod;

import javax.print.DocFlavor;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.List;

@Controller
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @RequestMapping("/employee")
    @RequiresPermissions("employee:index")
    public String employee() {
        System.out.println("来到了这");
        return "employee";
    }

    @RequestMapping("/employeeList")
    @ResponseBody
    public PageListRes employeeList(QueryVo vo) {
        System.out.println(vo);
        PageListRes pageListRes = employeeService.getEmployee(vo);
        return pageListRes;
    }


    @RequestMapping("saveEmployee")
    @ResponseBody
    @RequiresPermissions("employee:add")
    public AjaxRes saveEmployee(Employee employee){
        System.out.println("------------------------"+employee);
        AjaxRes ajaxRes = new AjaxRes();
        try {
            employee.setState(true);
            employeeService.saveEmployee(employee);
            ajaxRes.setSuccess(true);
            ajaxRes.setMessage("保存成功");
        }catch (Exception e){
            ajaxRes.setSuccess(false);
            ajaxRes.setMessage("保存失败");
        }
        return ajaxRes;
    }

    @RequestMapping("updataEmployee")
    @ResponseBody
    @RequiresPermissions("employee:edit")
    public AjaxRes updataEmployee(Employee employee){
        System.out.println(employee);
        System.out.println("来到了controller");
        AjaxRes ajaxRes = new AjaxRes();
        try {
            employee.setState(true);
            employeeService.updataEmployee(employee);
            ajaxRes.setSuccess(true);
            ajaxRes.setMessage("更新成功");
        }catch (Exception e){
            System.out.println(e.getMessage());
            ajaxRes.setSuccess(false);
            ajaxRes.setMessage("更新失败");
        }
        return ajaxRes;
    }
    @RequestMapping("/updateState")
    @ResponseBody
    @RequiresPermissions("employee:delect")
    public AjaxRes updataState(Integer id){
        AjaxRes ajaxRes = new AjaxRes();
        try {
            employeeService.updateState(id);
            ajaxRes.setSuccess(true);
            ajaxRes.setMessage("删除成功");
        }catch (Exception e){
            System.out.println(e.getMessage());
            ajaxRes.setSuccess(false);
            ajaxRes.setMessage("删除失败");
        }
        return ajaxRes;
    }
    @ExceptionHandler(AuthorizationException.class)
    public void handleShiroException(HandlerMethod method, HttpServletResponse response) throws Exception {
        /*跳转到一个界面，界面提示没有 权限*/
        /*判断 当前的请求是不是json 请求 如果是 返回json 给浏览器 让它自己来做跳转*/
        /*获取方法上的注解*/
        ResponseBody methodAnnotation = method.getMethodAnnotation(ResponseBody.class);
        if (methodAnnotation!=null){
            AjaxRes ajaxRes = new AjaxRes();
            ajaxRes.setSuccess(false);
            ajaxRes.setMessage("没有权限操作");
            /*转成json字符串*/
            response.setCharacterEncoding("utf-8");
            String jsonString = new ObjectMapper().writeValueAsString(ajaxRes);
            response.getWriter().print(jsonString);
        }else {
            response.sendRedirect("nopermission.jsp");
        }

    }
}
