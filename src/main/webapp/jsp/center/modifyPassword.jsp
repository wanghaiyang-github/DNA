<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../common/include.jsp" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <%@ include file="../common/link.jsp" %>
</head>
<body>
<section id="main-content">
    <section class="wrapper">
        <div id="wrapper-content">
            <!-- BEGIN ROW  -->
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            <span class="label label-primary">用户信息</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
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
                            <button class="btn btn-lg btn-success" id="saveBtn" type="button"><i class="fa fa-check"></i> 保 存 </button>
                            <button class="btn btn-lg btn-info" type="button" onclick="javascript:history.go(-1);"><i class="fa fa-reply"></i> 返 回 </button>
                            <input type="hidden" id="updatePasswordUrl" value="<%=path%>/center/updatePassword.html"/>
                        </div>
                    </section>
                </div>
            </div>

        </div>
    </section>
</section>
<!-- BEGIN JS -->
<%@ include file="../common/script.jsp" %>
<script>
    $(function(){
        'use strict';

        $("#saveBtn").on('click',function(){
            var oldPassword = $("#oldPassword").val();
            if (oldPassword == "") {
                alert("请输入旧密码！");
                $("#oldPassword").focus();
                return;
            }
            var newPassword = $("#newPassword").val();
            if (newPassword == "") {
                alert("请输入新密码！");
                $("#newPassword").focus();
                return;
            }
            var confirmNewPassword = $("#confirmNewPassword").val();
            if (confirmNewPassword == "") {
                alert("请再次输入新密码！");
                $("#confirmNewPassword").focus();
                return;
            }
            if (confirmNewPassword == newPassword){

            }else {
                alert("新密码不一致，请重新输入！");
                $("#confirmNewPassword").val("");
                $("#confirmNewPassword").focus();
                return;
            }

            $.ajax({
                url:"<%=path%>/center/updatePassword.html?oldPassword=" + oldPassword + "&newPassword=" + newPassword,
                type:"get",
                dataType:"json",
                success:function(data){
                    alert("修改成功！");
                    window.history.go(-1);
                },
                error:function(e) {
                    alert("修改失败！");
                }
            });
        });

    });
</script>
<!-- END JS -->
</body>
</html>


