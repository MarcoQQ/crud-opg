package com.marco.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.marco.bean.Msg;
import com.marco.bean.Staff;
import com.marco.service.StaffService;
import com.marco.utils.MyCompiler.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.bind.SchemaOutputResolver;
import java.util.*;

/**
 * Created by marco sun on 2017/12/7.
 * 处理员工crud
 * /staff/{id} GET 查询员工
 * /staff POST 保存员工
 * /staff/{id} PUT 修改员工
 * /staff/{id} DELETE 删除员工
 */
@Controller
public class StaffController {

    @Autowired
    StaffService staffService;

    //删除员工信息,单个批量二合一
    //1         单个
    //1-2-3     批量
    @RequestMapping(value = "/staff/{ids}", method = RequestMethod.DELETE)
    @ResponseBody
    public Msg deleteStaffById(@PathVariable("ids") String ids){
        if(ids.contains("-")){
            List<Integer> del_ids = new ArrayList<Integer>();
            String[] str_ids = ids.split("-");
            for(String str : str_ids){
                del_ids.add(Integer.parseInt(str));
            }
            staffService.deleteBatch(del_ids);
        }else{
            Integer id = Integer.parseInt(ids);
            staffService.deleteStaff(id);
        }
        return Msg.success();
    }

    //修改员工信息
    @RequestMapping(value = "/staff/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public Msg updateStaff(@Valid Staff staff, BindingResult result){
        Map<String, Object>map = new HashMap<String, Object>();
        if(result.hasErrors()){
            List<FieldError> errors = result.getFieldErrors();
            for(FieldError fieldError : errors){
                System.out.println("错误字段：" + fieldError.getField());
                System.out.println("错误信息" + fieldError.getDefaultMessage());
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorField", map);
        }else{
            staffService.updateStaff(staff);
            return Msg.success();
        }

    }

    //按照id查询某个员工
    @RequestMapping(value = "/getStaff/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Msg getStaff(@PathVariable("id") Integer id){
        Staff staff = staffService.getStaff(id);
        return Msg.success().add("staff", staff);
    }

    //按页码查询显示员工
    @RequestMapping("/staff")
    //json风格, @responseBody可以将返回对象转为json, 如果想@ResponseBody工作成功，需要导入jackson包
    @ResponseBody
    public Msg getPageInfoWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
        PageHelper.startPage(pn, 5);

        List<Staff> staffs = staffService.getAll();

        PageInfo pageInfo = new PageInfo(staffs, 5);
        return Msg.success().add("pageInfo", pageInfo);
    }

    //提交员工增加请求
    @RequestMapping(value = "/staff", method = RequestMethod.POST)
    @ResponseBody
    public Msg saveStaff(@Valid Staff staff, BindingResult result){
        Map<String, Object> map = new HashMap<String, Object>();
        if(result.hasErrors()){
            List<FieldError> errors = result.getFieldErrors();
            for(FieldError fieldError : errors){
                System.out.println("错误字段：" + fieldError.getField());
                System.out.println("错误信息" + fieldError.getDefaultMessage());
                map.put(fieldError.getField(), fieldError.getDefaultMessage());
            }
            return Msg.fail().add("errorField", map);
        }else{
            staffService.saveStaff(staff);
            return Msg.success();
        }

    }



////    查询员工数据(分页查询)
//    @RequestMapping("/staff")
//    public String getStaff(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model){
//        //传入页码，以及每页的大小
//        PageHelper.startPage(pn, 5);
//
//        //startPage后面的查询即为分页查询
//        List<Staff> staffs = staffService.getAll();
//        //包括了详细的page信息,第二个参数是连续传入的页数
//        PageInfo pageInfo = new PageInfo(staffs, 5);
//        model.addAttribute(pageInfo);
//        return "list";
//    }
}
