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
    <link href="<%=path %>/css/base.css" rel="stylesheet">
    <link href="<%=path %>/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <style>
        body {
            background: url("<%=path%>/img/login-bg.png") no-repeat center;
            background-size: cover;
            box-sizing: border-box;
            /*padding-top: 20px;*/
        }

        header {
            text-align: center;
            /*width: 60%;*/
            margin: 0 auto;
            position: relative;
            top: 50%;
            margin-top: -345px;
        }

        header img {
            /*width: 100px;*/
            margin-bottom: 10px;
        }

        header > h1 {
            letter-spacing: 4px;
            font-weight: 600;
        }

        header > h1,
        header > h3 {
            color: #fff;
        }

        header > h3 {
            padding: 0 50px;
            letter-spacing: 1px;
            font-size: 20px;
            margin-top: 5px;
        }

        .loginBox {
            width: 30%;
            background: hotpink;
            margin: 0 auto;
            background: rgba(30, 39, 55, .8);
            margin-top: 40px;
            color: #fff;
            padding: 30px 70px;

        }

        .loginBox > p {
            text-align: center;
            font-size: 25px;
            position: relative;
        }

        .loginBox > p > span {
            position: absolute;
            height: 100%;
            width: 42%;
            border-top: 1px dashed;
            top: 50%;
        }

        .loginBox .inputBox {
            padding: 0 15px;
            margin-top: 35px;
        }

        .form-group {
            margin-bottom: 20px;
        }

        .form-control {
            border-radius: 0px;
            height: 35px;
        }

        #loginBtn {
            border-radius: 2px;
            background: rgb(83, 148, 252);
            padding: 9px;
        }

        #loginBtn:hover {
            color: #fff;
        }

        .inputBox p {
            text-align: center;
            margin-top: 45px;
        }

        .inputBox p a {
            color: rgb(207, 205, 118);
        }

        .password {
            background-image: url("<%=path%>/img/password.png");
            background-repeat: no-repeat;
            background-position: 6px 10px;
            padding-left: 30px;
        }

        .user {
            background-image: url("<%=path%>/img/user.png");
            background-repeat: no-repeat;
            background-position: 6px 10px;
            padding-left: 30px;
        }

        input[type=checkbox] {
            /*margin: 50px;*/
            /*同样，首先去除浏览器默认样式*/
            -webkit-appearance: none;
            -moz-appearance: none;
            appearance: none;
            /*编辑我们自己的样式*/
            position: relative;
            width: 15px;
            height: 15px;
            background: #fff;
            /*border: 1px solid transparent;*/
            -moz-border-radius: 4px;
            border-radius: 3px;
            outline: none;
            cursor: pointer;
            margin: 0px;
        }

        input[type=checkbox]::after {
            content: '√';
            position: absolute;
            display: block;
            width: 100%;
            height: 100%;
            background: #1f83db;
            /*border: 1px solid transparent;*/
            color: #fff;
            text-align: center;
            line-height: 18px;
            /*增加动画*/
            -webkit-transition: all ease-in-out 300ms;
            -moz-transition: all ease-in-out 300ms;
            transition: all ease-in-out 300ms;
            /*利用border-radius和opacity达到填充的假象，首先隐藏此元素*/
            -webkit-border-radius: 20px;
            -moz-border-radius: 20px;
            border-radius: 20px;
            opacity: 0;
        }

        input[type=checkbox]:checked:after {
            -webkit-border-radius: 0;
            -moz-border-radius: 0;
            border-radius: 0;
            opacity: 1;
        }

        footer {
            position: fixed;
            bottom: 0px;
            text-align: center;
            width: 100%;
            background: rgba(30, 39, 55, .6);
            line-height: 35px;
            color: #fff;
            opacity: .6;
        }

        /*background: url("../img/login-footer.png")no-repeat center;*/

        /*main .loginBox .inputBox input + input {*/
        /*margin-top: 20px;*/
        /*}*/
    </style>
    <!--[if lt IE 9]>
    <script src="<%=path %>/js/html5shiv.js">
    </script>
    <script src="<%=path %>/js/respond.min.js">
    </script>
    <![endif]-->
    <!-- END STYLESHEET-->
</head>
<body>
<%--<body class="login-screen">--%>
<header>
    <img src="<%=path%>/img/login_logo.png" alt="">
    <h1>${loginTitleName}</h1>
    <%--<h3>${loginEnglishTitleName}</h3>--%>
    <%--<h3>DNA Laboratory Management System</h3>--%>
    <form class="form-signin" action="<%=path %>/center/login.html" method="post">
        <div class="loginBox">
            <p><span style="left: 0px"></span>登录<span style="right: 0px"></span></p>
            <div class="inputBox">
                <div class="form-group">
                    <input type="text" name="loginname" class="form-control user" placeholder="用户名">
                </div>
                <div class="form-group">
                    <input type="password" name="password" class="form-control password" placeholder="密码">
                </div>
                <div class="checkbox" style="text-align: left !important;">
                    <label>
                        <input type="checkbox" name="rememberMe" value="remember-me" checked="checked">记住密码
                    </label>
                </div>
                <button class="btn btn-block" type="submit" id="loginBtn">登录</button>
                <p>下载谷歌浏览器:
                    <a href="<%=path%>/chromeForXp.html">Windows XP版</a>
                    <a href="<%=path%>/chromeForWin7.html">Win7/8/10版</a>
                </p>
            </div>
        </div>
    </form>
</header>
<footer>Copyright © 2016 北京博安智联科技有限公司</footer>
<script src="<%=path %>/js/jquery.js"></script><!-- BASIC JQUERY LIB. JS -->
<script src="<%=path %>/js/bootstrap.min.js"></script><!-- BOOTSTRAP JS -->
<!-- END JS -->
</body>
</html>

