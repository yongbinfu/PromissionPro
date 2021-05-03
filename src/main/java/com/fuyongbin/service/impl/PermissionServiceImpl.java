package com.fuyongbin.service.impl;

import com.fuyongbin.domain.Permission;
import com.fuyongbin.mapper.PermissionMapper;
import com.fuyongbin.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Service
@Transactional
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Override
    public List<Permission> getpermissionList() {
        List<Permission> permissions = permissionMapper.selectAll();
        return permissions;
    }

    @Override
    public List<Permission> getPermissionListByRid(Integer rid) {
        List<Permission> permissionList = permissionMapper.selectPermissionListByRid(rid);
        return permissionList;
    }
}
