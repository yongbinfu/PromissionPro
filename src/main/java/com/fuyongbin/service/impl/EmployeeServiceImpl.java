package com.fuyongbin.service.impl;

import com.fuyongbin.domain.Employee;
import com.fuyongbin.domain.PageListRes;
import com.fuyongbin.domain.QueryVo;
import com.fuyongbin.domain.Role;
import com.fuyongbin.mapper.EmployeeMapper;
import com.fuyongbin.service.EmployeeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {
    @Autowired
    private EmployeeMapper employeeMapper;
    @Override
    public PageListRes getEmployee(QueryVo vo) {
        Page<Object> page = PageHelper.startPage(vo.getPage(), vo.getRows());
        List<Employee> employees = employeeMapper.selectAll(vo);
        System.out.println(employees);
        /*封装成pageLst*/
        PageListRes pageListRes = new PageListRes();
        pageListRes.setTotal(page.getTotal());
        pageListRes.setRows(employees);
        return pageListRes;
    }

    @Override
    public void saveEmployee(Employee employee) {
        /*在保存用户时，给用户密码进行加密处理*/
        Md5Hash md5Hash = new Md5Hash(employee.getPassword(),employee.getUsername(),2);
        employee.setPassword(md5Hash.toString());
        /*保存员工*/
        employeeMapper.insert(employee);
        /*保存角色 获取employee当中的Role集合*/
        for (Role role : employee.getRoles()) {
            /*通过mapper插入员工与角色之间的关系*/
            employeeMapper.insertEmployeeAndRoleRel(role.getRid(),employee.getId());
        }
    }

    @Override
    public void updataEmployee(Employee employee) {
        /*打破与角色之间的关系*/
        employeeMapper.deleteRoleRel(employee.getId());
        /*更新员工的信息*/
        employeeMapper.updateByPrimaryKey(employee);
        /*重新建立与角色之间的关系*/
        for (Role role : employee.getRoles()) {
            /*通过mapper插入员工与角色之间的关系*/
            employeeMapper.insertEmployeeAndRoleRel(role.getRid(),employee.getId());
        }
    }

    @Override
    public void updateState(Integer id) {
        employeeMapper.updateState(id);
    }

    @Override
    public Employee getEmployeeWithUsername(String username) {

        return employeeMapper.getEmployeeWithUsername(username);
    }

    @Override
    public List<String> getRolesById(Integer id) {
        return employeeMapper.getRolesById(id);
    }

    @Override
    public List<String> getPermissionById(Integer id) {
        return employeeMapper.getPermissionById(id);
    }


}
