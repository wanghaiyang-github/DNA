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
                            <span class="label label-primary">权限信息</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <form id="labUser_form" class="form-horizontal tasi-form">
                                <input type="hidden" id="labRoleId" value="${labRole.id}"/>
                                <input type="hidden" id="operateType" value="${operateType}"/>

                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">权限名称 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control required" id="roleName" value="${labRole.roleName}">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </section>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            <span class="label label-primary">授权列表</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <form id="labPermissionForm" class="form-horizontal tasi-form">
                                <c:if test="${operateType eq 1}">
                                    <c:forEach items="${labPermissionList}" var="labPermission" varStatus="lr">
                                        <div class="form-group">
                                            <c:if test="${labPermission.rootFlag eq 0}">
                                                <div class="col-sm-1">&nbsp;</div>
                                            </c:if>
                                            <div class="col-sm-6">
                                                <label class="checkbox-inline">
                                                    <input type="hidden" id="rootFlag" value="${labPermission.rootFlag}">
                                                    <input type="checkbox" name="rolePermission" value="${labPermission.id}"> ${labPermission.permissionName}
                                                </label>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${operateType eq 2}">
                                    <c:forEach items="${labPermissionList}" var="labPermission" varStatus="lr">
                                        <div class="form-group">
                                            <c:if test="${labPermission.rootFlag eq 0}">
                                                <div class="col-sm-1">&nbsp;</div>
                                            </c:if>
                                            <div class="col-sm-6">
                                                <label class="checkbox-inline">
                                                    <input type="hidden" id="rootFlag" value="${labPermission.rootFlag}">
                                                    <input type="checkbox"  name="rolePermission" value="${labPermission.id}" <c:if test="${labPermission.checkedForRole eq 1}">checked</c:if>> ${labPermission.permissionName}
                                                </label>
                                            </div>
                                        </div>
                                    </c:forEach>
                                </c:if>
                            </form>
                        </div>
                    </section>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <div class="panel-body">
                            <form class="form-horizontal tasi-form">
                                <div class="form-group pull-right">
                                    <button class="btn btn-lg btn-success" id="saveBtn" type="button"><i class="fa fa-check"></i> 保 存 </button>
                                    <button class="btn btn-lg btn-info" id="backBtn" type="button"><i class="fa fa-reply"></i> 返 回 </button>
                                </div>
                            </form>
                        </div>
                    </section>
                </div>
            </div>

            <div class="modal fade" id="EditLabRolePermissionModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
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

        $("input[name='rolePermission']", "#labPermissionForm").on("click", function(){
            var rootFlag = $("#rootFlag", $(this).parents("div.col-sm-6")).val();
            var rolePermission = $("input[name='rolePermission']", $(this).parents("div.col-sm-6")).val();

            if (rootFlag != "" && rootFlag == 1) {
                evaluate((this), rolePermission, rootFlag);
            }
        });

        function evaluate (obj, rolePermission, rootFlag) {
            var div$TR = $("div.col-sm-6", "#labPermissionForm");
            var totalRows = div$TR.length;

            var permission;
            var flag = false;
            var index = 0;
            for(var i = 0; i < totalRows; i++){
                permission = $("input[name='rolePermission']", div$TR.get(i)).val();

                if (flag) {
                    var checkRootFlag = $("#rootFlag", div$TR.get(i)).val();
                    if (rootFlag != checkRootFlag) {
                        if ($("input[name='rolePermission']", div$TR)[index].checked == true) {
                            $("input[name='rolePermission']", div$TR)[i].checked = true;
                        }else {
                            $("input[name='rolePermission']", div$TR)[i].checked = false;
                        }
                    }else {
                        break;
                    }
                }
                if (permission != "" && permission == rolePermission) {
                    flag = true;
                    index = i;
                }
            }
        }

        function GetLabRolePermission(){
            var labRolePermission = {};
            labRolePermission.roleId=$("#labRoleId").val();
            labRolePermission.roleName=$("#roleName").val();

            var labRolePermissionArr = new Array();
            $("input[name='rolePermission']:checked").each(function(){
                var labPermission = {};
                labPermission.permissionId = $(this).val();

                labRolePermissionArr.push(labPermission);
            });
            labRolePermission.labRolePermissionList = labRolePermissionArr;

            return labRolePermission;
        }

        function checkInputValidation(){
            var roleName = $("#roleName").val();
            if (roleName == "" || roleName == null || roleName.length == 0) {
                alert("请输入权限名称！");
                $("#roleName").focus();
                return false;
            }

            if($("input[name='rolePermission']:checked").length == 0) {
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
            var labRolePermission = GetLabRolePermission();

            $.ajax({
                url : "<%=path%>/center/7/saveLabRolePermission.html?operateType="+operateType,
                type:"post",
                contentType:  "application/json; charset=utf-8",
                data : JSON.stringify(labRolePermission),
                dataType : "json",
                success : function(data) {
                    if(data.success == true || data.success == "true") {
                        $("#EditLabRolePermissionModal").modal('show');
                    }else {
                        alert("保存失败！");
                    }
                },
                error: function(e){
                    alert(e);
                }
            });
        });

        $("#EditLabRolePermissionModal").on('hidden.bs.modal', function(){
            location.href='<%=path%>/center/7/04.html';
        });

        $("#backBtn").on("click",function(){
            location.href='<%=path%>/center/7/04.html';
        });

    });
</script>
<!-- END JS -->
</body>
</html>


