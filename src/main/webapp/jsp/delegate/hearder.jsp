<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
%>
<header class="header">
    <a href="#" class="logo">
        <img src="<%=path%>/img/login_logo.png"/>
        ${loginTitleName}
    </a>
    <!-- START USER LOGIN DROPDOWN  -->

    <div class="top-nav pull-right">
        <span><i class="fa fa-user"></i> 当前登录：<shiro:user><shiro:principal property="orgName"/></shiro:user></span>

        <a href="#profileModal" data-toggle="modal" class="profile">
            <i class=" fa fa-key"></i>
            修改密码
        </a>

        <a href="<%=path%>/wt/logout.html" class="logout">
            <i class="fa fa-lock"></i>
            退出系统
        </a>

    </div>

    <!-- END USER LOGIN DROPDOWN  -->
</header>
