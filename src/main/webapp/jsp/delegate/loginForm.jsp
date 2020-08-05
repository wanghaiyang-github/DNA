<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <!-- BEGIN META -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="">
    <meta name="author" content="Custom Theme">
    <!-- END META -->

    <!-- BEGIN SHORTCUT ICON -->
    <link rel="shortcut icon" href="<%=path %>/img/dna.ico">
    <!-- END SHORTCUT ICON -->
    <title>
        博安智联LIMS - ${loginTitleName}
    </title>
    <!-- BEGIN STYLESHEET-->
    <link href="<%=path %>/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="<%=path %>/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="<%=path %>/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON CSS -->
    <link href="<%=path %>/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="<%=path %>/css/style-responsive.css" rel="stylesheet"><!-- THEME RESPONSIVE CSS -->
    <!--[if lt IE 9]>
    <script src="<%=path %>/js/html5shiv.js">
    </script>
    <script src="<%=path %>/js/respond.min.js">
    </script>
    <![endif]-->
    <!-- END STYLESHEET-->
</head>
<body class="login-screen">
<!-- BEGIN SECTION -->
<section class="container">
    <header class="login-header">
        <a href="#" class="logo">
            <img src="<%=path %>/img/login_logo.png"/>
            <%--毕节市公安局DNA实验室信息管理系统--%>
            ${loginTitleName}
        </a>
    </header>
    <section id="login-main-content">
        <form class="form-signin" action="<%=path %>/wt/login.html" method="post">
            <!-- LOGIN WRAPPER  -->
            <div class="login-wrap">
                <div class="login-iconic-input">
                    <img src="<%=path %>/img/login_username.png" />
                    <select name="loginname" class="form-control m-bot15">
                        <option value="">请选择登录单位</option>
                        <c:forEach items="${delegateOrgList}" var="list" varStatus="s">
                            <option value="${list.orgName}">${list.orgName}</option>
                        </c:forEach>
                    </select>
                </div>

                <div class="login-iconic-input">
                    <img src="<%=path %>/img/login_pwd.png" />
                    <input name="password" type="password" class="form-control" placeholder="密码">
                </div>
                <label class="checkbox pull-right">
                    <input name="rememberMe" type="checkbox" value="remember-me" checked="checked">
                    记住账号
                </label>
                <button class="btn btn-lg btn-login btn-block" type="submit">
                    <i class="fa fa-tags"></i> 登  录
                </button>
                <div class="h5" style="color:#fff;margin:15px;padding: 10px;">
                    <span>下载Google Chrome浏览器：</span>
                    <a style="color:red;" href="<%=path%>/chromeForXp.html">Windows XP版</a>
                    <a style="color:greenyellow; margin-left:10px;" href="<%=path%>/chromeForWin7.html">Win7/8/10版</a>
                </div>

            </div>
            <!-- END LOGIN WRAPPER -->
        </form>
    </section>
    <div class="login-footer">
        Copyright © 2016 北京博安智联科技有限公司
    </div>
</section>
<!-- END SECTION -->
<!-- BEGIN JS -->
<script src="<%=path %>/js/jquery.js" ></script><!-- BASIC JQUERY LIB. JS -->
<script src="<%=path %>/js/bootstrap.min.js" ></script><!-- BOOTSTRAP JS -->
<!-- END JS -->
</body>
</html>

