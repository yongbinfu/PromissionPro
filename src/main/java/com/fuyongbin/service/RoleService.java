package com.fuyongbin.service;

import com.fuyongbin.domain.PageListRes;
import com.fuyongbin.domain.QueryVo;
import com.fuyongbin.domain.Role;

import java.util.List;

public interface RoleService {
    public PageListRes getRoleList(QueryVo vo);

    public void saveRole(Role role);

    public void updataRole(Role role);

    public void deleteRole(Integer rid);

    public List<Role> getroleListCombobox();

    public List<Integer> selectRolesByid(Integer id);
}
