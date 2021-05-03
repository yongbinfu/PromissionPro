package com.fuyongbin.mapper;

import com.fuyongbin.domain.Employee;
import com.fuyongbin.domain.QueryVo;
import com.fuyongbin.domain.Role;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface EmployeeMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Employee record);

    Employee selectByPrimaryKey(Integer id);

    List<Employee> selectAll(QueryVo vo);

    int updateByPrimaryKey(Employee record);

    void updateState(Integer id);

    void insertEmployeeAndRoleRel(@Param("rid") Integer rid, @Param("eid") Integer id);

    void deleteRoleRel(Integer id);

    Employee getEmployeeWithUsername(String username);


    List<String> getRolesById(Integer id);

    List<String> getPermissionById(Integer id);
}