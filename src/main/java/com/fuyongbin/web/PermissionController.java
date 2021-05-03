package com.fuyongbin.web;

import com.fuyongbin.domain.Permission;
import com.fuyongbin.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class PermissionController {
@Autowired
    private PermissionService permissionService;
    @RequestMapping("/permissionList")
    @ResponseBody
    public List<Permission> permissionList(){
        System.out.println("来到了permission");
        List<Permission> permissions = permissionService.getpermissionList();
        return permissions;
    }
    @RequestMapping("/getPermissionListByRid")
    @ResponseBody
    public List<Permission> getPermissionListByRid(Integer rid){
        List<Permission> permissionListByRid = permissionService.getPermissionListByRid(rid);
        return permissionListByRid;
    }

}
