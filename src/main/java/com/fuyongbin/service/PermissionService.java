package com.fuyongbin.service;

import com.fuyongbin.domain.Permission;

import java.util.List;

public interface PermissionService {
    public List<Permission> getpermissionList();

    public List<Permission> getPermissionListByRid(Integer rid);
}
