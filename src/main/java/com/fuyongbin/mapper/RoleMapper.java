package com.fuyongbin.mapper;

import com.fuyongbin.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer rid);

    int insert(Role record);

    Role selectByPrimaryKey(Integer rid);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);

    void insertRoleAndPermission(@Param("rid") Integer rid, @Param("pid") Integer pid);

    void deletePermissionRel(Integer rid);

    List<Integer> selectRolesByid(Integer id);
}