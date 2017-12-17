<%--
  Created by IntelliJ IDEA.
  User: marco sun
  Date: 2017/12/9
  Time: 18:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%
    //    以斜线开始，不以斜线结束(例如/crud)
    pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<html>
<head>
    <title>word_ana</title>
    <link href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
    <script src="//cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
    <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">

    <div class="row">
        <form  class="form col-md-12 center-block" method="post" action="${APP_PATH}/word">
            <div class="row">
            <textarea style="margin: 0px; width: 630px; height: 410px;" name="text" id="text">
            </textarea>
            </div>
            <div class="row">
                <button id="submit">提交</button>
            </div>
        </form>
    </div>


</div>

</body>
</html>
