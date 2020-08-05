<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/24
  Time: 11:23
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <title>博安智联LIMS - ${loginTitleName}</title>
    <%@ include file="../common/link.jsp" %>
</head>
<body>
<section id="container">
        <!-- BEGIN HEADER -->
        <header class="header">
            <a href="#" class="logo">
                <img src="<%=path%>/img/login_logo.png"/>
                ${loginTitleName}
                <%--毕节市公安局DNA实验室信息管理系统--%>
            </a>
            <!-- START USER LOGIN DROPDOWN  -->

            <div class="top-nav pull-right">
                <span><i class="fa fa-user"></i> 当前登录：<shiro:user><shiro:principal property="userName"/></shiro:user></span>

                    <a href="<%=path%>/center/modifyPassword.html"  class="profile" target="_parent">
                    <i class=" fa fa-key"></i>
                    修改密码
                </a>

                <a href="<%=path%>/center/logout.html" class="logout" target="_parent">
                    <i class="fa fa-lock"></i>
                    退出系统
                </a>

            </div>
            <!-- END USER LOGIN DROPDOWN  -->
        </header>

    <div class="modal fade" id="profileModal" aria-hidden="true" style="z-index:999999;">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                        &times;
                    </button>
                    <h4 class="modal-title">
                        修改登录密码
                    </h4>
                </div>
                <div class="modal-body">
                    <form class="form-horizontal tasi-form">
                        <div class="form-group">
                            <label class="control-label col-md-3">旧密码 <i class="fa fa-asterisk color_red"></i></label>
                            <div class="col-md-5">
                                <input id="oldPassword" type="password" class="form-control small required" value=""/>
                            </div>
                            <div class="col-md-2 has-error hide">
                                <p class="help-block">必填项</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">新密码 <i class="fa fa-asterisk color_red"></i></label>
                            <div class="col-md-5">
                                <input id="newPassword" type="password" class="form-control small required" value=""/>
                            </div>
                            <div class="col-md-2 has-error hide">
                                <p class="help-block">必填项</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3">再次输入新密码 <i class="fa fa-asterisk color_red"></i></label>
                            <div class="col-md-5">
                                <input id="confirmNewPassword" type="password" class="form-control small required" value=""/>
                            </div>
                            <div class="col-md-2 has-error hide">
                                <p class="help-block">必填项</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-8" id="messagLabel" style="text-align:center; font-weight:bold; font-size:16px;"></label>
                        </div>
                    </form>
                </div>
                <div class="modal-footer">
                    <button class="btn btn-success" type="button" id="SaveCenterPasswordBtn">确定</button>
                    <input type="hidden" id="updatePasswordUrl" value="<%=path%>/center/updatePassword.html"/>
                    <button data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
                </div>
            </div>
        </div>
    </div>
    </section>

    <%@ include file="../common/script.jsp" %>
</body>
</html>
