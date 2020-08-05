<%--<%@ page contentType="text/html;charset=UTF-8" language="java" %>--%>
<%--<%--%>
<%--String path = request.getContextPath();--%>
<%--%>--%>
<%--<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN"--%>
<%--"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">--%>
<%--<html xmlns="http://www.w3.org/1999/xhtml">--%>
<%--<head>--%>
<%--<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>--%>
<%--<title>博安智联LIMS - 毕节市公安局DNA实验室信息管理系统</title>--%>
<%--</head>--%>
<%--<frameset rows="88,*" cols="*" frameborder="no" border="0" framespacing="0">--%>
<%--<frame src="<%=path%>/center/top.html" name="topFrame" scrolling="No" noresize="noresize" id="topFrame"--%>
<%--title="topFrame"/>--%>
<%--<frameset cols="200,*" frameborder="no" border="0" framespacing="0">--%>
<%--<frame src="<%=path%>/center/left.html" name="leftFrame" scrolling="No" noresize="noresize" id="leftFrame"--%>
<%--title="leftFrame"/>--%>
<%--<frame src="<%=path%>/center/main.html" name="rightFrame" id="rightFrame" title="rightFrame"/>--%>
<%--</frameset>--%>
<%--</frameset>--%>
<%--<noframes>--%>
<%--<body>--%>
<%--</body>--%>
<%--</noframes>--%>
<%--</html>--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Frameset//EN"
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-frameset.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <title>博安智联LIMS - ${loginTitleName}</title>
    <link rel="stylesheet" href="<%=path%>/assets/layui-v2.3.0/layui/css/layui.css">
    <link rel="stylesheet" href="<%=path%>/assets/font-awesome/css/font-awesome.css">
    <style>
        .layui-layout-admin .layui-header {
            background: url("<%=path%>/img/topbar.png") no-repeat center;
            background-size: cover;
        }

        .layui-bg-black {
            background: url("<%=path%>/img/leftbar.png") no-repeat !important;
            background-size: cover !important;
        }

        .layui-nav {
            background-color: transparent;
        }

        .layui-nav-tree .layui-this > a,
        .layui-nav-tree .layui-nav-item a:hover {
            background-color: #4d8fff;
            color: #fff;
        }

        .layui-nav-tree .layui-nav-child dd.layui-this, .layui-nav-tree .layui-nav-child dd.layui-this a, .layui-nav-tree .layui-this, .layui-nav-tree .layui-this > a, .layui-nav-tree .layui-this > a:hover {
            background-color: #4d8fff;
            color: #fff;
        }
    </style>
</head>
<body>
<body class="layui-layout-body">
<div class="layui-layout layui-layout-admin">
    <div class="layui-header">
        <div class="layui-logo" style="width: 600px;">
            <img src="<%=path%>/img/login_logo.png"/>
            博安智联LIMS - ${loginTitleName}
        </div>
        <ul class="layui-nav layui-layout-right">
            <li class="layui-nav-item">
                <a href="javascript:;">
                    当前登录:<shiro:user><shiro:principal property="userName"/></shiro:user>
                </a>
                <dl class="layui-nav-child">
                    <dd><a href="<%=path%>/center/modifyPassword.html">修改密码</a></dd>
                </dl>
            </li>
            <li class="layui-nav-item"><a href="">退出</a></li>
        </ul>
    </div>

    <div class="layui-side layui-bg-black">
        <div class="layui-side-scroll">
            <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
            <ul class="layui-nav layui-nav-tree" lay-filter="test"  lay-shrink="all" id="nav-accordion">
                <li class="layui-nav-item"><a href="<%=path%>/center/main.html" target="rightFrame">主页</a></li>

                <c:forEach items="${rootPermissionList}" var="root" varStatus="rp">
                    <c:choose>
                        <c:when test="${root.id eq 700}">
                            <li class="layui-nav-item ">
                                <a id="Testimonial" href="<%=path%>/center/4/01.html" target="rightFrame"
                                   class="active">
                                    <i class="fa fa-file-text-o"></i>
                                    <span>鉴定书管理</span>
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="layui-nav-item ">
                                <a href="javascript:;">
                                    <i class="fa ${root.iconStyle}"></i>
                                    <span>${root.permissionName}</span>
                                </a>
                                <ul class="layui-nav-child ">
                                    <c:forEach items="${childPermissionList}" var="child" varStatus="cp">
                                        <c:if test="${child.parentId eq root.id}">
                                            <li>
                                                <a href="<%=path%>/${child.permissionLink}"
                                                   target="rightFrame">${child.permissionName}</a>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                </ul>
                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>
            </ul>
        </div>
    </div>

    <div class="layui-body">
        <div id="content" style="width: 100%;height: 100%">
            <iframe src="<%=path%>/center/main.html" frameborder="0" width="100%" height="100%"
                    name="rightFrame"></iframe>
        </div>
    </div>
    <div class="layui-footer">
        <!-- 底部固定区域 -->
        Copyright© 2017 北京博安智联科技有限公司
    </div>
</div>
<script src="<%=path%>/js/jquery-1.8.3.min.js"></script>
<script src="<%=path%>/assets/layui-v2.3.0/layui/layui.js"></script>
<script>
    layui.use('element', function () {
        var element = layui.element;
    });
</script>
</body>
<%--<frameset rows="88,*" cols="*" frameborder="no" border="0" framespacing="0">--%>
<%--<frame src="<%=path%>/center/top.html" name="topFrame" scrolling="No" noresize="noresize" id="topFrame"--%>
<%--title="topFrame"/>--%>
<%--<frameset cols="200,*" frameborder="no" border="0" framespacing="0">--%>
<%--<frame src="<%=path%>/center/left.html" name="leftFrame" scrolling="No" noresize="noresize" id="leftFrame"--%>
<%--title="leftFrame"/>--%>
<%--<frame src="<%=path%>/center/main.html" name="rightFrame" id="rightFrame" title="rightFrame"/>--%>
<%--</frameset>--%>
<%--</frameset>--%>
<%--<noframes>--%>
<%--<body>--%>
<%--</body>--%>
<%--</noframes>--%>
</html>