<%--
  Created by IntelliJ IDEA.
  User: marco sun
  Date: 2017/12/7
  Time: 9:44
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page isELIgnored="false" %>
<%
    //    以斜线开始，不以斜线结束(例如/crud)
    pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<html>
<head>
    <%--不以斜杠开始的相对路径，找资源，以当前资源路径为标准，容易出问题。--%>
    <%--以/开始的相对路径，找资源，以服务器的路径为标准(http://127.0.0.1:80),需要加入项目名--%>
        <title>员工列表</title>
        <link href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
        <script src="//cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
        <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

</head>
<body>
<%--修改--%>
<div class="modal fade" id="staffUpdateModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel">员工修改</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">staffName</label>
                        <div class="col-sm-10">
                            <p class="form-control-static" id="staffName_update_static"></p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Email</label>
                        <div class="col-sm-10">
                            <input type="email" class="form-control" name="email" id="email_update_input" placeholder="email@qq.com">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">gender</label>
                        <label class="radio-inline">
                            <input type="radio" name="gender" id="gender1_update_input" value="M" checked="checked"> 男
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="gender" id="gender2_update_input" value="F"> 女
                        </label>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">deptName</label>
                        <div class="col-sm-4">
                            <select class="form-control" name="dptId" id="dept_update_select">

                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="staff_update">更新</button>
            </div>
        </div>
    </div>
</div>
<!-- 添加 -->
<div class="modal fade" id="staffAddModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
                <h4 class="modal-title" id="myModalLabel1">员工添加</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal">
                    <div class="form-group">
                        <label  class="col-sm-2 control-label">staffName</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" name="name" id="staffName_add_input" placeholder="staffName">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">Email</label>
                        <div class="col-sm-10">
                            <input type="email" class="form-control" name="email" id="email_add_input" placeholder="email@qq.com">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">gender</label>
                        <label class="radio-inline">
                            <input type="radio" name="gender" id="gender1_add_input" value="M" checked="checked"> 男
                        </label>
                        <label class="radio-inline">
                            <input type="radio" name="gender" id="gender2_add_input" value="F"> 女
                        </label>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">deptName</label>
                        <div class="col-sm-4">
                            <select class="form-control" name="dptId" id="dept_add_select">

                            </select>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" id="staff_save">保存</button>
            </div>
        </div>
    </div>
</div>
<div class="container">
    <%--标题--%>
    <div class="row"></div>
    <div class="12">
        <h1>SSM-CRUD</h1>
    </div>
    <%--按钮--%>
    <div class="row"></div>
    <div class="col-md-4 col-md-offset-8">
        <button class="btn btn-primary" id="staffAddBtn">新增</button>
        <button class="btn btn-danger" id="staffDeleteBtn">删除</button>
    </div>
    <%--表格--%>
    <div class="row"></div>
    <div class="col-md-12">
        <table class="table table-hover" id="staff_table">
            <thead>
                <tr>
                    <th>
                        <input type="checkbox" id="check_all">
                    </th>
                    <th>#</th>
                    <th>员工姓名</th>
                    <th>员工性别</th>
                    <th>邮箱</th>
                    <th>远员工部门</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>

            </tbody>

        </table>
    </div>
    <%--分页--%>
    <div class="row">
        <div class="col-md-6" id="page_info_area">

        </div>
        <div class="col-md-6" id="page_nav_area">
            <nav aria-label="Page navigation">
                <ul class="pagination">

                </ul>
            </nav>
        </div>
    </div>
</div>

<script type="text/javascript">
    var totalRecord;
    var currrntPage;
    //页面加载完成后，直接去发送ajax请求，得到分页数据
    $(function() {
        to_page(1);
    });

    function to_page(pn){
        $("#check_all").prop("checked", 0);
        $.ajax({
            url:"${APP_PATH}/staff",
            data:"pn="+pn,
            type:"get",
            success:function(result) {
//                console.log(result);
                build_staffs_table(result);
                build_page_info(result);
                build_page_nav(result);
            }
        });
    }

    function build_staffs_table(result) {
        //清空
        $("#staff_table tbody").empty();
        var staffs = result.extend.pageInfo.list;
        $.each(staffs, function (index, item) {
            var checkBoxTd = $("<td><input type='checkbox' class='check_item' /></td>")
            var idTd = $("<td></td>").append(item.id);
            var nameTd = $("<td></td>").append(item.name);
            var genderTd = $("<td></td>").append((item.gender == 'M') ? "男" : "女");
            var emailTd = $("<td></td>").append(item.email);
            var dptNameTd = $("<td></td>").append(item.department.name);
            var editBtn = $("<button></button>").addClass("btn btn-primary edit_btn")
                                                .append($("<span></span>").addClass("glyphicon glyphicon-pencil"))
                                                .append("编辑");
            //表示当前编辑员工的id
            editBtn.attr("edit-id", item.id);
            var delBtn = $("<button></button>").addClass("btn btn-danger delete_btn")
                .append($("<span></span>").addClass("glyphicon glyphicon-trash "))
                .append("删除");
            //表示当前删除员工的id
            delBtn.attr("delete-id", item.id);
            var btnTd = $("<td></td>").append(editBtn).append(" ").append(delBtn);
            $("<tr></tr>").append(checkBoxTd)
                        .append(idTd)
                        .append(nameTd)
                        .append(genderTd)
                        .append(emailTd)
                        .append(dptNameTd)
                        .append(btnTd)
                        .appendTo("#staff_table tbody");

        });
    }
    //分页信息
    function build_page_info(result) {
        //清空
        $("#page_info_area").empty();
        $("#page_info_area").append("当前第" + result.extend.pageInfo.pageNum + "页，总共"
                                + result.extend.pageInfo.pages + "页,总共" +
                                + result.extend.pageInfo.total + "条信息");
        totalRecord = result.extend.pageInfo.total;
        currrntPage = result.extend.pageInfo.pageNum;
    }
    //分页条
    function build_page_nav(result) {
        $("#page_nav_area").empty();
        var ul = $("<ul></ul>").addClass("pagination");
        var firstPageLi = $("<li></li>").append($("<a></a>").append("首页").attr("href", "#"));
        var prePageLi = $("<li></li>").append($("<a></a>").append("&laquo;").attr("href", "#"));
        if(result.extend.pageInfo.hasPreviousPage == false){
            firstPageLi.addClass("disabled");
            prePageLi.addClass("disabled");
        }else {
            firstPageLi.click(function () {
                to_page(1);
            });
            prePageLi.click(function () {
                to_page(result.extend.pageInfo.pageNum - 1);
            })
        }

        var nextPageLi = $("<li></li>").append($("<a></a>").append("&raquo;").attr("href", "#"));
        var lastPageLi = $("<li></li>").append($("<a></a>").append("末页").attr("href", "#"));

        if(result.extend.pageInfo.hasNextPage == false){
            lastPageLi.addClass("disabled");
            nextPageLi.addClass("disabled");
        }else {
            lastPageLi.click(function () {
                to_page(result.extend.pageInfo.pages);
            });
            nextPageLi.click(function () {
                to_page(result.extend.pageInfo.pageNum + 1);
            })
        }

        var navigatepageNums = result.extend.pageInfo.navigatepageNums;
        ul.append(firstPageLi).append(prePageLi);
        $.each(navigatepageNums, function (index, item) {
            var numLi = $("<li></li>").append($("<a></a>").append(item).attr("href", "#"));
            if(result.extend.pageInfo.pageNum == item)
                numLi.addClass("active");
            numLi.click(function () {
                to_page(item);
            });
            ul.append(numLi);
        })
        ul.append(nextPageLi).append(lastPageLi);
        var nav = $("<nav></nav>").append(ul);
        nav.appendTo("#page_nav_area");
    }

    $("#staffAddBtn").click(function () {
        //发送请求获得部门信息，显示下拉列表
        $("#staffAddModal form")[0].reset();
        getDepts("#staffAddModal select");
        //弹出模态框
        $("#staffAddModal").modal({
            backdrop:"static"
        });
    });

    //发送请求获得部门信息，显示下拉列表
    function getDepts(ele) {
        $(ele).empty();
        $.ajax({
            url:"${APP_PATH}/depts",
            type:"GET",
            success:function(result){
               console.log(result);

               $.each(result.extend.depts, function () {
                   var optionEle = $("<option></option>").append(this.name).attr("value", this.id);
                   optionEle.appendTo(ele);
               })
            }
        });
    }

    function validate_add_form() {
        //拿到要校验的数据
        var staffName = $("#staffName_add_input").val();
        var regName = /^[a-zA-Z0-9_-]{3,16}$/;
        if(!regName.test(staffName)){
            alert("用户名为6到16位的英文数字组合");
            return false;
        }
        var email = $("#email_add_input").val();
        var regEmail = /^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/
        if(!regEmail.test(email)){
            alert("请输入正确邮箱");
            return false;
        }
        return true;
    }

    //点击保存
    $("#staff_save").click(function () {
//        alert($("#staffAddModal form").serialize());
//        if(!validate_add_form()){
//            return false;
//        }
        $.ajax({
            url:"${APP_PATH}/staff",
            type:"POST",
            data:$("#staffAddModal form").serialize(),
            success:function (result) {
//                alert(result.msg);
                if(result.code == 100) {
                    $("#staffAddModal").modal("hide");
                    to_page(totalRecord);
                }else{
                    if(result.extend.errorField.name != undefined) {
                        alert(result.extend.errorField.name);
                    }else if(result.extend.errorField.email != undefined){
                        alert(result.extend.errorField.email);
                    }

                }
            }
            }
        )
    });

    //点击某个员工的编辑按钮
    $(document).on("click", ".edit_btn", function(){
        //获得下拉列表
        getDepts("#staffUpdateModal select");
        //按照id查询该员工信息并显示
        var id = $(this).attr("edit-id");
        getStaff(id);
        //把员工id传给模态框的更新按钮
        $("#staff_update").attr("edit-id", $(this).attr("edit-id"));
        $("#staffUpdateModal").modal({
            backdrop:"static"
        });
    });

    //点击某个员工的删除按钮(单个删除)
    $(document).on("click", ".delete_btn", function(){
        //弹出确认删除对话框
        var name = $(this).parents("tr").find("td:eq(2)").text();
        var id = $(this).attr("delete-id");
        if(confirm("确认删除【"+ name +"】吗")){
            //点击true则发送ajax请求
            $.ajax({
                url : "${APP_PATH}/staff/" +  id,
                type : "delete",
                success : function(result){
                    alert("处理成功");
                    to_page(currrntPage);
                }
            });
        }
    });

    //获得该员工的信息
    function getStaff(id){
        $.ajax({
            url:"${APP_PATH}/getStaff/" + id,
            type:"GET",
            success:function (result) {
                var staffData = result.extend.staff;
                $("#staffName_update_static").text(staffData.name);
                $("#email_update_input").val(staffData.email);
                $("#staffUpdateModal input[name=gender]").val([staffData.gender]);
                $("#dept_update_select").val(staffData.dptId)
            }
        })
    }

    //tomcat把参数封装为map，
    //request.getParameter("email") 从map中取值
    //SpringMvc封装POJO的时候会把每个属性的值都调用以上的方法
    //put请求不能直接发，put请求中的数据，后端拿不到。
    //因为tomcat一看发送请求的是PUT就不会封装参数为map，只有POST才可以封装参数为map
    //解决方案：在web.xml添加过滤器HttpPutFormContentFilter
    $("#staff_update").click(function () {
       //后端邮箱验证
        $.ajax({
            url : "${APP_PATH}/staff/" + $(this).attr("edit-id"),
            type : "PUT",
            data : $("#staffUpdateModal form").serialize(),
            success : function (result) {
                if(result.code == 100) {
                    $("#staffUpdateModal").modal("hide");
                    to_page(currrntPage);
                }else{
                    if(result.extend.errorField.email != undefined){
                        alert(result.extend.errorField.email);
                    }
                }
            }
        })

    });

    //完成最左边按钮的全选/全不选
    $("#check_all").click(function() {
        //原生dom的属性可以由prop获取，自定义的用attr获
        //选中为true，没选中为false
        var isChecked = $(this).prop("checked");
        $(".check_item").prop("checked", isChecked);
    });

    //对可能是后来动态添加的元素绑事件
    $(document).on("click", ".check_item", function () {
        //判断当前选择的元素是否为5个
        var flag = $(".check_item:checked").length == $(".check_item").length
        $("#check_all").prop("checked", flag);
    })

    //点击全部删除，批量删除
    $("#staffDeleteBtn").click(function(){
        var staffNames = "";
        var del_idstr = "";
        $.each($(".check_item:checked"), function(){
            //组装名字字符串
            staffNames += $(this).parents("tr").find("td:eq(2)").text() + ",";
            //组装id字符串
            del_idstr += $(this).parents("tr").find("td:eq(1)").text() + "-";
        });
        staffNames = staffNames.substring(0, staffNames.length-1);
        del_idstr = del_idstr.substr(0, del_idstr.length-1);
        if(confirm("确认删除【" + staffNames + "】吗")){
            $.ajax({
                url : "${APP_PATH}/staff/" + del_idstr,
                type : "Delete",
                success : function (result) {
                    alert(result.msg);
                    to_page(currrntPage);
                }
            })
        }
        });

</script>
</body>
</html>
