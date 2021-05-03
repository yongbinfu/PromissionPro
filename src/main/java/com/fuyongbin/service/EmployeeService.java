package com.fuyongbin.service;

import com.fuyongbin.domain.Employee;
import com.fuyongbin.domain.PageListRes;
import com.fuyongbin.domain.QueryVo;
import com.fuyongbin.domain.Role;
import org.omg.CORBA.PUBLIC_MEMBER;

import java.util.List;

public interface EmployeeService {
    public PageListRes getEmployee(QueryVo vo);
    public void saveEmployee(Employee employee);
    public void updataEmployee(Employee employee);
    public void updateState(Integer id);
    public Employee getEmployeeWithUsername(String username);
    public List<String> getRolesById(Integer id);
    public List<String> getPermissionById(Integer id);
}
