package com.fuyongbin.web;

import com.fuyongbin.domain.AjaxRes;
import com.fuyongbin.domain.PageListRes;
import com.fuyongbin.domain.QueryVo;
import com.fuyongbin.domain.Role;
import com.fuyongbin.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class RoleController {
    @Autowired
    private RoleService roleService;

    @RequestMapping("/role")
    public String role() {
        return "role";
    }

    @RequestMapping("/roleList")
    @ResponseBody
    public PageListRes roleList(QueryVo vo) {
        System.out.println("roleList");
        PageListRes pageListRes = roleService.getRoleList(vo);
        System.out.println(pageListRes);
        return pageListRes;
    }

    @RequestMapping("/saveRole")
    @ResponseBody
    public AjaxRes saveRole(Role role) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            ajaxRes.setSuccess(true);
            ajaxRes.setMessage("保存成功");
            roleService.saveRole(role);
        } catch (Exception e) {
            ajaxRes.setSuccess(false);
            ajaxRes.setMessage("保存失败");
            System.out.println(e);
        }
        return ajaxRes;
    }

    @RequestMapping("/updataRole")
    @ResponseBody
    public AjaxRes updataRole(Role role) {
        AjaxRes ajaxRes = new AjaxRes();
        try {
            ajaxRes.setSuccess(true);
            ajaxRes.setMessage("更新角色成功");
            roleService.updataRole(role);
        } catch (Exception e) {
            ajaxRes.setSuccess(false);
            ajaxRes.setMessage("更新角色失败");
            System.out.println(e);
        }
        return ajaxRes;
    }
    @RequestMapping("/deleteRole")
    @ResponseBody
    public AjaxRes deleteRole(Integer rid){
        System.out.println(rid);
        AjaxRes ajaxRes = new AjaxRes();
        try {
            ajaxRes.setSuccess(true);
            ajaxRes.setMessage("删除角色成功");
            roleService.deleteRole(rid);
        }catch (Exception e){
            System.out.println(e);
            ajaxRes.setSuccess(false);
            ajaxRes.setMessage("删除角色失败");
        }
        return ajaxRes;
    }

    @RequestMapping("/roleListCombobox")
    @ResponseBody
    public List<Role> roleListCombobox(){
        System.out.println("kjhkfajsdhfkjh");
        List<Role> roles = roleService.getroleListCombobox();
        return roles;
    }
    @RequestMapping("/getRolesByid")
    @ResponseBody
    public List<Integer> getRolesByid(Integer id){
       return roleService.selectRolesByid(id);
    }
}
