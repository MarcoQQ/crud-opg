package com.marco.bean;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.Pattern;

public class Staff {
    private Integer id;

    @Pattern(regexp = "^[a-zA-Z0-9_-]{3,16}$", message = "用户名不正确")
    private String name;

    private String gender;

    @Pattern(regexp = "^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$", message = "邮箱不正确")
    private String email;

    private Integer dptId;

    //希望查询员工的时候也查询部门
    private Department department;

    public Staff(Integer id, String name, String gender, String email, Integer dptId) {
        this.id = id;
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.dptId = dptId;
    }

    public Staff() {
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    public Integer getDptId() {
        return dptId;
    }

    public void setDptId(Integer dptId) {
        this.dptId = dptId;
    }
}