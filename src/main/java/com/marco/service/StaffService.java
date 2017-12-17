package com.marco.service;

import com.marco.bean.Staff;
import com.marco.bean.StaffExample;
import com.marco.dao.DepartmentMapper;
import com.marco.dao.StaffMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by marco sun on 2017/12/7.
 */
@Service
public class StaffService {

    @Autowired
    public StaffMapper staffMapper;
    public List<Staff> getAll() {
        return staffMapper.selectByExampleWithDepartment(null);
    }

    //员工保存
    public  void saveStaff(Staff staff) {
        staffMapper.insertSelective(staff);
    }

    public Staff getStaff(Integer id) {
        Staff staff = staffMapper.selectByPrimaryKey(id);
        return staff;
    }

    //员工更新
    public void updateStaff(Staff staff) {
        staffMapper.updateByPrimaryKeySelective(staff);
    }

    public void deleteStaff(Integer id) {
        staffMapper.deleteByPrimaryKey(id);
    }

    public void deleteBatch(List<Integer> ids) {
        StaffExample staffExample = new StaffExample();
        StaffExample.Criteria criteria = staffExample.createCriteria();
        criteria.andIdIn(ids);
        staffMapper.deleteByExample(staffExample);
    }
}
