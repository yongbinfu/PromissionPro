package com.fuyongbin.web.realm;

import com.fuyongbin.domain.Employee;
import com.fuyongbin.service.EmployeeService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class EmployeeRealm extends AuthorizingRealm {
    @Autowired
    private EmployeeService employeeService;

    /*认证*/
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        System.out.println("来到了认证-------");
        /*从form表单获取值*/
        String username = (String) authenticationToken.getPrincipal();
        System.out.println(username);
        /*从数据库查询employee*/
        Employee employee = employeeService.getEmployeeWithUsername(username);
        System.out.println(employee);
        if (username == null) {
            System.out.println("未查询到用户");
            return null;
        }
        /*认证*/
        /*参数：主体，正确的密码，盐，当前realm名称*/
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(employee, employee.getPassword(),ByteSource.Util.bytes(employee.getUsername()),this.getName());
        return info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        System.out.println("授权调用");
        /*获取用户的身份的信息*/
        Employee employee = (Employee) principalCollection.getPrimaryPrincipal();
        System.out.println(employee);
        /*角色*/
        List<String> roles = new ArrayList<>();
        /*权限*/
        List<String> permissions = new ArrayList<>();

        if (employee.getAdmin()) {
            permissions.add("*:*");
        } else {
            /*查询角色*/
            roles = employeeService.getRolesById(employee.getId());
            /*查询权限*/
            permissions = employeeService.getPermissionById(employee.getId());
        }


        /*给授权信息*/
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        info.addRoles(roles);
        info.addStringPermissions(permissions);
        return info;
    }
}
