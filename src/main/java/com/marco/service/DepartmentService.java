package com.marco.service;

import com.marco.bean.Department;
import com.marco.dao.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by marco sun on 2017/12/11.
 */
@Service
public class DepartmentService {
    @Autowired
    private DepartmentMapper departmentMapper;
    public List<Department> getDepts() {
        List<Department> list = departmentMapper.selectByExample(null);
        return list;
    }
}
