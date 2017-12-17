package com.marco.test;

import com.github.pagehelper.PageInfo;
import com.marco.bean.Staff;
import com.marco.service.StaffService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

/**
 * Created by marco sun on 2017/12/7.
 * 使用spring测试模块的测试请求功能，测试crud请求的正确性
 */

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:applicationContext.xml", "file:src/main/webapp/WEB-INF/SpringMVC-servlet.xml"})
public class MvcTest {
//    虚拟mvc请求，获取处理结果
    MockMvc mockMvc;
    //传入springmvc的ioc
    @Autowired
    WebApplicationContext context;

    //每次用都要初始化
    @Before
    public void initMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

    @Test
    public void testPage() throws Exception{
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/staff").param("pn", "1")).andReturn();
        //请求成功后，请求域中会有pageInfo，我们可以取出并进行验证
        MockHttpServletRequest mockHttpServletRequest = result.getRequest();
        PageInfo pageInfo = (PageInfo) mockHttpServletRequest.getAttribute("pageInfo");
        System.out.println("当前页码" + pageInfo.getPageNum());
        System.out.println("总页码" + pageInfo.getPages());
        System.out.println("总记录数" + pageInfo.getTotal());
        int[] nums = pageInfo.getNavigatepageNums();
        for(int i : nums)
        System.out.println("连续显示的页码" + i);

        //获取员工数据
        List<Staff> list = pageInfo.getList();
        for(Staff staff : list){
            System.out.println("id:" + staff.getId());
        }
    }
}
