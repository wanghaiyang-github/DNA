<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
  String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
  <head>
    <!-- BEGIN META -->
    <meta charset="utf-8">
    <!-- END META -->
    
     <!-- BEGIN SHORTCUT ICON -->
     <link rel="shortcut icon" href="<%=path%>/img/dna.ico">
     <!-- END SHORTCUT ICON -->
    <title>
      博安智联LIMS - 毕节市公安局DNA实验室信息管理系统
    </title>
     <!-- BEGIN STYLESHEET-->
		<link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
		<link href="<%=path%>/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
		<link href="<%=path%>/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON CSS -->
		<link href="<%=path%>/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
		<link href="<%=path%>/css/style-responsive.css" rel="stylesheet"><!-- THEME RESPONSIVE CSS -->
     <!--[if lt IE 9]>
    <script src="<%=path%>/js/html5shiv.js"></script>
    <script src="<%=path%>/js/respond.min.js"></script>
    <![endif]-->
     <!-- END STYLESHEET-->
  </head>
  <body>
    <!-- BEGIN SECTION -->
    <section id="container">
      <!-- BEGIN HEADER -->
      <header class="header">
        <a href="#" class="logo">
		  <img src="<%=path%>/img/login_logo.png"/>
          毕节市公安局DNA实验室信息管理系统
		</a>
		 <!-- START USER LOGIN DROPDOWN  -->

        <div class="top-nav pull-right">
          <span><i class="fa fa-user"></i> 当前登录：<shiro:user><shiro:principal property="userName"/></shiro:user></span>

          <a href="#profileModal" data-toggle="modal" class="profile">
            <i class=" fa fa-key"></i>
            修改密码
          </a>

          <a href="<%=path%>/center/logout.html" class="logout">
            <i class="fa fa-lock"></i>
            退出系统
          </a>

        </div>
		<!-- END USER LOGIN DROPDOWN  -->
      </header>
      <!-- END HEADER -->
      <!-- BEGIN SIDEBAR -->
      <aside>
        <div id="sidebar" class="nav-collapse">
          <ul class="sidebar-menu" id="nav-accordion">
            <li>
              <a id="homePageLink" href="javascript:;" onclick="siderMenuLink(this, '<%=path%>/center/index/content.html')" class="active">
                <i class="fa fa-dashboard"></i>
                <span>主 页</span>
              </a>
            </li>

            <c:forEach items="${rootPermissionList}" var="root" varStatus="rp">
              <li class="sub-menu">
                <a href="javascript:;">
                  <i class="fa ${root.iconStyle}"></i>
                  <span>${root.permissionName}</span>
                </a>
                <ul class="sub">
                  <c:forEach items="${childPermissionList}" var="child" varStatus="cp">
                    <c:if test="${child.parentId eq root.id}">
                      <li>
                        <a href="javascript:;" onclick="siderMenuLink(this, '<%=path%>/${child.permissionLink}')">${child.permissionName}</a>
                      </li>
                    </c:if>
                  </c:forEach>
                </ul>
              </li>
            </c:forEach>
          </ul>
        </div>
      </aside>
      <!-- END SIDEBAR -->
      <!-- BEGIN MAIN CONTENT -->

      <section id="main-content">
        <section class="wrapper">
          <div id="wrapper-content">
          </div>

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
      </section>
      <!-- END MAIN CONTENT -->
    </section>

    <!-- END SECTION -->
    <!-- BEGIN JS -->
    <script src="<%=path%>/js/jquery.js" ></script><!-- BASIC JS LIABRARY -->
    <script src="<%=path%>/js/bootstrap.min.js" ></script><!-- BOOTSTRAP JS  -->
    <script src="<%=path%>/js/jquery.dcjqaccordion.2.7.js"></script><!-- ACCORDING JS -->
    <script src="<%=path%>/js/jquery.scrollTo.min.js" ></script><!-- SCROLLTO JS -->
    <script src="<%=path%>/js/jquery.nicescroll.js" ></script><!-- NICESCROLL JS -->
    <script src="<%=path%>/js/jquery-ui-1.9.2.custom.min.js" ></script><!-- JQUERY UI JS  -->
    <script src="<%=path%>/assets/bootstrap-inputmask/bootstrap-inputmask.min.js"></script><!-- INPUT MASK JS  -->
    <script src="<%=path%>/js/respond.min.js" ></script><!-- RESPOND JS -->
    <script src="<%=path%>/js/common-scripts.js"></script><!-- BASIC COMMON JS -->
    <script src="<%=path%>/js/left-menu.js"></script>
    <script>
      var Script = function () {
        'use strict';

        jQuery('.panel .tools .fa-chevron-down').click(function () {
          var el = jQuery(this).parents(".panel").children(".panel-body");
          if (jQuery(this).hasClass("fa-chevron-down")) {
            jQuery(this).removeClass("fa-chevron-down").addClass("fa-chevron-up");
            el.slideUp(200);
          } else {
            jQuery(this).removeClass("fa-chevron-up").addClass("fa-chevron-down");
            el.slideDown(200);
          }
        });
        jQuery('.panel .tools .fa-times').click(function () {
          jQuery(this).parents(".panel").parent().remove();
        });
      }();
    </script>


    <!-- END JS -->
  </body>
</html>


