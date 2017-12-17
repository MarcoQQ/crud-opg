package com.marco.test;

import com.marco.bean.Department;
import com.marco.bean.Staff;
import com.marco.dao.DepartmentMapper;

import com.marco.dao.StaffMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

/**
 * Created by marco sun on 2017/12/6.
 * 测试dao层的工作
 * 1.导入SpringTest的单元模块
 * 2.@ContextConfiguration指定Spring配置文件的位置
 * 3.直接autoWired要用的组件即可
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class MapperTest {
    //测试department

    @Autowired
    DepartmentMapper departmentMapper;

    @Autowired
    SqlSession sqlSession;
    @Test
    public void testCRUD(){
        //创建SpringIOC容器
//        ApplicationContext ioc = new ClassPathXmlApplicationContext("applicationContext.xml");
//        //从容器中拿到mapper
//        ioc.getBean(DepartmentMapper.class);
       System.out.print(departmentMapper);
       departmentMapper.insertSelective(new Department(1, "开发部"));
       departmentMapper.insertSelective(new Department(2, "采购部"));
       StaffMapper staffMapper = sqlSession.getMapper(StaffMapper.class);
       for(int i = 0; i < 100; i++) {
           String uuid = UUID.randomUUID().toString().substring(0,5) + i;
           staffMapper.insertSelective(new Staff(null, uuid, "M", uuid + "qq.com", 1));
       }
    }
}
