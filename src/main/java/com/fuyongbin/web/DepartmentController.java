package com.fuyongbin.web;

import com.fuyongbin.domain.Department;
import com.fuyongbin.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.sound.midi.Soundbank;
import java.util.List;

@Controller
public class DepartmentController {
    @Autowired
    private DepartmentService departmentService;
    @RequestMapping("departmentList")
    @ResponseBody
    public List<Department> getDepartmentList(){
        List<Department> departmentList = departmentService.getDepartmentList();
        return departmentList;
    }


}
