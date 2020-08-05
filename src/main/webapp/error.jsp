<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <!-- BEGIN META -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Custom Theme">
    <!-- END META -->

    <!-- BEGIN SHORTCUT ICON -->
    <link rel="shortcut icon" href="<%=path%>/img/dna.ico">
    <!-- END SHORTCUT ICON -->

    <title>404</title>

    <!-- BEGIN STYLESHEET -->
    <link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="<%=path%>/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="<%=path%>/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON STYLESHEET -->
    <link href="<%=path%>/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="<%=path%>/css/style-responsive.css" rel="stylesheet"><!-- THEME BASIC RESPONSIVE  CSS -->
    <!--[if lt IE 9]>
    <script src="<%=path%>/js/html5shiv.js"></script>
    <script src="<%=path%>/js/respond.min.js"></script>
    <![endif]-->
    <!-- END STYLESHEET -->
</head>
<body class="body-404 login-screen">
<div class="container">
    <!-- BEGIN MAIN CONTENT -->
    <section class="error-wrapper">
        <%--<h1>404</h1>
        <h2>未找到指定页面！</h2>--%>
        <br>
        <br>
        <br>
        <br>
        <br>
        <br>
        <p class="page-404">为保证系统数据安全，您需要重新验证登录！ </p>
        <p class="page-404"><a class="btn btn-primary btn-lg" href="<%=path%>/center/login.html"><i class="fa fa-hand-o-right"></i> 鉴定登录</a></p>
        <p class="page-404"><a class="btn btn-info btn-lg" href="<%=path%>/wt/login.html"><i class="fa fa-hand-o-right"></i> 委托登录</a></p>
    </section>
    <!-- END MAIN CONTENT -->
</div>
</body>
</html>


