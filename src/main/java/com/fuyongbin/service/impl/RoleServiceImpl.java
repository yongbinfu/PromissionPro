package com.fuyongbin.service.impl;

import com.fuyongbin.domain.PageListRes;
import com.fuyongbin.domain.Permission;
import com.fuyongbin.domain.QueryVo;
import com.fuyongbin.domain.Role;
import com.fuyongbin.mapper.RoleMapper;
import com.fuyongbin.service.RoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Permissions;
import java.util.List;
@Service
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Override
    public PageListRes getRoleList(QueryVo vo) {
        Page<Object> page = PageHelper.startPage(vo.getPage(), vo.getRows());
        List<Role> roles = roleMapper.selectAll();
        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(roles);
        return pageListRes;
    }

    @Override
    public void saveRole(Role role) {
        System.out.println("来到了service层"+role);
        roleMapper.insert(role);
        for (Permission permission : role.getPermissions()) {
            roleMapper.insertRoleAndPermission(role.getRid(),permission.getPid());
        }


    }

    @Override
    public void updataRole(Role role) {
        /*打破角色与权限之间的关系*/
        roleMapper.deletePermissionRel(role.getRid());
        /*更新角色*/
        roleMapper.updateByPrimaryKey(role);
        /*重新建立关系*/
        for (Permission permission : role.getPermissions()) {
            roleMapper.insertRoleAndPermission(role.getRid(),permission.getPid());
        }
    }

    @Override
    public void deleteRole(Integer rid) {
        /*打破角色与权限之间的关系*/
        roleMapper.deletePermissionRel(rid);
        /*删除记录*/
        roleMapper.deleteByPrimaryKey(rid);
    }

    @Override
    public List<Role> getroleListCombobox() {
        List<Role> roles = roleMapper.selectAll();
        return roles;
    }

    @Override
    public List<Integer> selectRolesByid(Integer id) {
        return roleMapper.selectRolesByid(id);

    }
}
