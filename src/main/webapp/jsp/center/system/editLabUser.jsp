<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/include.jsp" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <%@ include file="../../common/link.jsp" %>
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
                            <form id="labUser_form" class="form-horizontal tasi-form">
                                <input type="hidden" id="labUserId" value="${labUser.id}"/>
                                <input type="hidden" id="operateType" value="${operateType}"/>

                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">登录名 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control required" id="loginName" value="${labUser.loginName}">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>

                                <c:if test="${operateType eq 1}">
                                    <div class="form-group">
                                        <label class="col-sm-2 col-sm-2 control-label">登录密码 <i class="fa fa-asterisk color_red"></i></label>
                                        <div class="col-sm-5">
                                            <input type="password" class="form-control required" name="loginPassword" value="">
                                        </div>
                                        <div class="col-sm-2 has-error hide">
                                            <p class="help-block">必填项</p>
                                        </div>
                                    </div>
                                </c:if>

                                <c:if test="${operateType eq 2}">
                                    <div class="form-group">
                                        <label class="col-sm-2 col-sm-2 control-label">登录密码</label>
                                        <div class="col-sm-5">
                                            <input type="password" class="form-control" name="loginPassword" value="${labUser.loginPassword}">
                                        </div>
                                            <%--<div class="col-sm-2">
                                                <a href="javascript:;" id="updatePassword"><i class="fa fa-edit"></i> 修改密码</a>
                                            </div>--%>
                                    </div>
                                </c:if>


                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">姓名 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control required" id="userName" value="${labUser.userName}"/>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">性别</label>
                                    <div class="col-sm-5">
                                        <select class="form-control" id="gender" value="${labUser.gender}">
                                            <option value="男" <c:if test="${labUser.gender eq '男'}">selected</c:if>>男</option>
                                            <option value="女" <c:if test="${labUser.gender eq '女'}">selected</c:if>>女</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">证件号码</label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control" id="cardId" value="${labUser.cardId}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">联系电话</label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control" id="phoneNum" value="${labUser.phoneNum}"/>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">备注</label>
                                    <div class="col-sm-5">
                                        <textarea class="form-control" rows="2" id="remark">${labUser.remark}</textarea>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">系统权限</label>
                                    <div class="col-sm-5">
                                        <c:if test="${operateType eq 1}">
                                            <c:forEach items="${labRoleList}" var="labRole" varStatus="lr">
                                                <label class="checkbox-inline">
                                                    <input type="checkbox" name="userRole" value="${labRole.id}"> ${labRole.roleName}
                                                </label>
                                            </c:forEach>
                                        </c:if>
                                        <c:if test="${operateType eq 2}">
                                            <c:forEach items="${labRoleList}" var="labRole" varStatus="lr">
                                                <label class="checkbox-inline">
                                                    <input type="checkbox"  name="userRole" value="${labRole.id}" <c:if test="${labRole.checkedForUser eq 1}">checked</c:if>> ${labRole.roleName}
                                                </label>
                                            </c:forEach>
                                        </c:if>
                                    </div>
                                </div>

                                <div class="form-group pull-right">
                                    <button class="btn btn-lg btn-success" id="saveBtn" type="button"><i class="fa fa-check"></i> 保 存 </button>
                                    <button class="btn btn-lg btn-info" id="backBtn" type="button"><i class="fa fa-reply"></i> 返 回 </button>
                                </div>
                            </form>
                        </div>
                    </section>
                </div>
            </div>

            <div class="modal fade" id="EditLabUserModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">
                                消息提示
                            </h4>
                        </div>
                        <div class="modal-body">
                            <form class="form-horizontal tasi-form">
                                <div class="form-group m-bot20"></div>
                                <div class="form-group m-bot20">
                                    <div class="col-md-12 text-center">
                                        <h3 class="alert alert-success"><Strong>保存成功！</Strong></h3>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <i class="fa fa-hand-o-right"></i>
                            <button data-dismiss="modal" class="btn btn-default" type="button" id="backwardBtn"><i class="fa fa-reply"></i> 返 回</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</section>
<!-- BEGIN JS -->
<%@ include file="../../common/script.jsp" %>
<script>
    $(function(){
        'use strict';

        function GetLabUser(){
            var labUser = {};
            labUser.id=$("#labUserId").val();
            labUser.userName = $("#userName").val();
            labUser.loginName = $("#loginName").val();
            labUser.loginPassword = $("input[name='loginPassword']").val();
            labUser.gender = $("#gender").val();
            labUser.cardId = $("#cardId").val();
            labUser.phoneNum = $("#phoneNum").val();
            labUser.remark = $("#remark").val();

            var userRoleArr = new Array();
            $("input[name='userRole']:checked").each(function(){
                var labRole = {};
                labRole.id=$(this).val();
                userRoleArr.push(labRole);
            });
            labUser.labRoleList = userRoleArr;

            return labUser;
        }

        function checkInputValidation(){
            var errCnt = 0;
            $(".required", "#labUser_form").each(function(){
                if($(this).val() == "" || $.trim($(this).val()) == ""){
                    $("div.has-error",$(this).parents("div.form-group")).removeClass("hide");
                    errCnt++;
                }else{
                    $("div.has-error",$(this).parents("div.form-group")).addClass("hide");
                }
            });

            if(errCnt > 0) {
                alert("请补全必填项！");
                return false;
            }

            if($("input[name='userRole']:checked").length == 0) {
                alert("请勾选该用户的系统权限！");
                return false;
            }

            return true;
        }

        $("#saveBtn").on('click',function(){
            if(!checkInputValidation()){
                return;
            }

            var operateType=$("#operateType").val();
            var labUser = GetLabUser();

            $.ajax({
                url : "<%=path%>/center/7/saveLabUser.html?operateType="+operateType,
                type:"post",
                contentType:  "application/json; charset=utf-8",
                data : JSON.stringify(labUser),
                dataType : "json",
                success : function(data) {
                    if(data.success || data.success == true || data.success == "true") {
                        $("#EditLabUserModal").modal('show');
                    }
                },
                error: function(e){
                    alert(e);
                }
            });
        });

        $("#EditLabUserModal").on('hidden.bs.modal', function(){
            location.href='<%=path%>/center/7/01.html';
        });

        $("#backBtn").on("click",function(){
            location.href='<%=path%>/center/7/01.html';
        });

    });
</script>
<!-- END JS -->
</body>
</html>


