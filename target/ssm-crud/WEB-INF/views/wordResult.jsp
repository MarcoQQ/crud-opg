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
    <title>算符优先法</title>
        <link href="//cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css" rel="stylesheet">
        <script src="//cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
        <script src="//cdn.bootcss.com/bootstrap/3.3.5/js/bootstrap.min.js"></script>

</head>
<body>
    <div class="container">
        <%--标题--%>
        <div class="row">
            <div class="12">
                <h1>优先关系</h1>
            </div>
        </div>
        <div>
            <h1>Vt</h1>
        </div>
        <div class="panel panel-default">
            <tr>
                <c:forEach items="${vt}" var="item">
                    <th>${item}</th>
                </c:forEach>
            </tr>
        </div>
        <div>
            <h1>firstVt</h1>
        </div>
        <div class="row" >
            <div class="col-md-12">
                <table class="table table-hover">
                    <tr>
                        <th></th>
                        <c:forEach items="${vt}" var="item">
                            <th>${item}</th>
                        </c:forEach>
                    </tr>
                    <c:forEach items="${vn}" var="n" varStatus="status">
                        <tr>
                        <th>${n}</th>
                            <c:forEach items="${firstVt[status.index]}" var="first">
                                <th>${first}</th>>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <div>
            <h1>lastVt</h1>
        </div>
        <div class="row" >
            <div class="col-md-12">
                <table class="table table-hover">
                    <tr>
                        <th></th>
                        <c:forEach items="${vt}" var="item">
                            <th>${item}</th>
                        </c:forEach>
                    </tr>
                    <c:forEach items="${vn}" var="item" varStatus="status">
                        <tr>
                            <th>${item}</th>
                            <c:forEach items="${lastVt[status.index]}" var="last">
                                <th>${last}</th>>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>
        <div>
            <h1>优先关系矩阵</h1>
        </div>
        <div class="row">
            <div class="col-md-12">
                <table class="table table-hover">
                    <tr>
                        <th></th>
                        <c:forEach items="${vt}" var="item">
                            <th>${item}</th>
                        </c:forEach>
                        <th>#</th>
                    </tr>
                    <c:forEach items="${vt}" var="t" varStatus="status">
                        <tr>
                            <th>${t}</th>
                            <c:forEach items="${tb[status.index]}" var="b">
                                <c:if test="${b==-1}">
                                    <th></th>
                                </c:if>
                                <c:if test="${b==0}">
                                    <th>&lt</th>
                                </c:if>
                                <c:if test="${b==1}">
                                    <th>&gt</th>
                                </c:if>
                                <c:if test="${b==2}">
                                    <th>=</th>
                                </c:if>
                            </c:forEach>
                        </tr>
                    </c:forEach>
                    <tr>
                        <th>#</th>
                        <c:forEach items="${tb[vtSize]}" var="item">
                            <c:if test="${item==-1}">
                                <th></th>
                            </c:if>
                            <c:if test="${item==0}">
                                <th>&lt</th>
                            </c:if>
                            <c:if test="${item==1}">
                                <th>&gt</th>
                            </c:if>
                            <c:if test="${item==2}">
                                <th>=</th>
                            </c:if>
                        </c:forEach>
                    </tr>
                </table>
            </div>
        </div>
        <div>
            <c:if test="${isSuccessM == true}">
                <h1>成功</h1>
            </c:if>
            <c:if test="${isSuccessM == false}">
                <h1>失败</h1>
            </c:if>
        </div>
        <div class="row">
            <div class="col-md-12">
                <table class="table table-hover">
                    <tr>
                        <th>步骤</th>
                        <th>栈</th>
                        <th>优先关系</th>
                        <th>当前输入符</th>
                        <th>剩余输入符</th>
                        <th>动作</th>
                    </tr>

                    <c:forEach items="${stackStrM}" varStatus="status" var="s">
                        <tr>
                            <th>${status.count}</th>
                            <th>${s}</th>
                            <c:if test="${relationM.size() > status.index}">
                                <c:if test="${relationM.get(status.index) == '>'}">
                                    <th>&gt</th>
                                </c:if>
                                <c:if test="${relationM.get(status.index) == '<'}">
                                    <th>&lt</th>
                                </c:if>
                                <c:if test="${relationM.get(status.index) == '='}">
                                    <th>=</th>
                                </c:if>
                                <c:if test="${relationM.get(status.index) == '-'}">
                                    <th>没关系</th>
                                </c:if>
                            </c:if>
                            <c:if test="${relationM.size() <= status.index}">
                                <th></th>
                            </c:if>


                            <c:if test="${curM.size() > status.index}">
                                <th>${curM.get(status.index)}</th>
                            </c:if>
                            <c:if test="${curM.size() <= status.index}">
                                <th></th>
                            </c:if>
                            <c:if test="${queueStrM.size() > status.index}">
                                <th>${queueStrM.get(status.index)}</th>
                            </c:if>
                            <c:if test="${queueStrM.size() <= status.index}">
                                <th></th>
                            </c:if>
                            <c:if test="${actionM.size() > status.index}">
                                <c:if test="${actionM.get(status.index) == true}">
                                    <th>移进</th>
                                </c:if>
                                <c:if test="${actionM.get(status.index) == false}">
                                    <th>规约</th>
                                </c:if>
                            </c:if>
                            <c:if test="${actionM.size() <= status.index}">
                                <th></th>
                            </c:if>

                        </tr>
                    </c:forEach>
                </table>
            </div>
        </div>


    </div>



</body>
</html>
