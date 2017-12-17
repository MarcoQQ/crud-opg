package com.marco.controller;

import com.marco.bean.Department;
import com.marco.bean.Msg;
import com.marco.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by marco sun on 2017/12/11.
 * 处理和部门有关的请求
 */

@Controller
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;

    @RequestMapping("/depts")
    @ResponseBody
    //返回所有的部门信息
    public Msg getDepts(){
        List<Department>list = departmentService.getDepts();
        return Msg.success().add("depts", list);
    }
}
