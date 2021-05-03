package com.fuyongbin.mapper;

import com.fuyongbin.domain.Permission;

import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer pid);

    int insert(Permission record);

    Permission selectByPrimaryKey(Integer pid);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);

    /*根据角色查询对应角色id*/
    List<Permission> selectPermissionListByRid(Integer rid);
}