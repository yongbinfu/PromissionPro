package com.fuyongbin.service.impl;

import com.fuyongbin.domain.Department;
import com.fuyongbin.mapper.DepartmentMapper;
import com.fuyongbin.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> getDepartmentList() {
        List<Department> departmentList = departmentMapper.selectAll();
        return departmentList;
    }
}
