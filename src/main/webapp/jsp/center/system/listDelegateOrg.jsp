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
                            <span class="label label-primary">鉴定用户列表</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <div class="clearfix">
                                <div class="btn-group">
                                    <button id="newLabUserBtn" class="btn btn-success green">
                                        添加 <i class="fa fa-plus"></i>
                                    </button>
                                    &nbsp;&nbsp;&nbsp;&nbsp;
                                    <button id="addIdentifyKernelBtn" class="btn btn-success green tools pull-right">
                                        添加鉴定中心机构 <i class="fa fa-plus"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="space15" style="height: 8px;"></div>
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>单位名称</th>
                                    <th>单位编码</th>
                                    <th>IP地址范围</th>
                                    <th>联系电话</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="delegateOrgListTbody">
                                <c:forEach items="${delegateOrgList}" var="delegateOrg" varStatus="s">
                                    <tr>
                                        <td>${s.count}</td>
                                        <td><a href="<%=path%>/center/7/addConsignmentInfo.html?delegateOrgId=${delegateOrg.id}">${delegateOrg.orgName}</a></td>
                                        <td>${delegateOrg.orgCode}</td>
                                        <td>${delegateOrg.ipaddrRange}</td>
                                        <td>${delegateOrg.contactPhonenum}</td>
                                        <td>
                                            <input type="hidden" name="orgId" value="${delegateOrg.id}"/>
                                            <input type="hidden" name="orgName" value="${delegateOrg.orgName}"/>
                                            <input type="hidden" name="orgCode" value="${delegateOrg.orgCode}"/>
                                            <input type="hidden" name="ipaddrRange" value="${delegateOrg.ipaddrRange}"/>
                                            <input type="hidden" name="contactPhonenum" value="${delegateOrg.contactPhonenum}"/>
                                            <input type="hidden" name="password" value="${delegateOrg.loginPassword}">
                                            <button name="editBtn" class="btn btn-primary btn-xs"><i class="fa fa-pencil"></i> 修改</button>
                                            <button name="delBtn" class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> 删除</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                            <div class="modal fade" id="delegateOrgModal" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                &times;
                                            </button>
                                            <h4 class="modal-title">
                                                用户信息
                                            </h4>
                                        </div>
                                        <div class="modal-body">
                                            <form class="form-horizontal tasi-form">
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">单位名称:</label>
                                                    <div class="col-md-5"><input name="delegateOrgName" id="delegateOrgName" type="text" class="form-control small required"/></div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">登录密码:</label>
                                                    <div class="col-md-5">
                                                        <input name="loginPassword" id="loginPassword" type="password" class="form-control small required"/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">单位编码:</label>
                                                    <div class="col-md-5">
                                                        <input name="delegateOrgCode" id="delegateOrgCode" type="text" class="form-control small required"/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">IP地址范围:</label>
                                                    <div class="col-md-5">
                                                        <input name="IPAddress" id="IPAddress" type="text" class="form-control small required"/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">联系电话:</label>
                                                    <div class="col-md-5">
                                                        <input name="phoneNumber" id="phoneNumber" type="text" class="form-control small required"/>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <input type="hidden" name="delegateOrgId" value="${delegateOrg.id}"/>
                                            <button class="btn btn-success" type="button" id="saveBtn">确定</button>
                                            <button data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                        </div>
                    </section>
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

        $("#addIdentifyKernelBtn").on("click", function(){
            location.href = "<%=path%>/center/7/identifyKernel.html";
        });

        $("button[name='editBtn']","#delegateOrgListTbody").on('click',function(){
            EditDelegateOrgRow(this);
        });

        $("#newLabUserBtn").on("click", function(){
            EditDelegateOrgRow(this);
        });

        function EditDelegateOrgRow(obj){
            var $curTR = $(obj).parents("tr");
            var caseNo = $("input[name='caseNo']",$curTR).val();
            var delegateOrg = {};
            delegateOrg.orgId = $("input[name='orgId']", $curTR).val();
            delegateOrg.orgName = $("input[name='orgName']", $curTR).val();
            delegateOrg.orgCode = $("input[name='orgCode']", $curTR).val();
            delegateOrg.ipaddrRange = $("input[name='ipaddrRange']", $curTR).val();
            delegateOrg.contactPhonenum = $("input[name='contactPhonenum']", $curTR).val();
            delegateOrg.password = $("input[name='password']", $curTR).val();

            newDelegateOrgRow(delegateOrg);
        }

        function newDelegateOrgRow(delegateOrg){
            $("input[name='delegateOrgId']", "#delegateOrgModal").val(delegateOrg.orgId);
            $("input[name='delegateOrgName']", "#delegateOrgModal").val(delegateOrg.orgName);
            $("input[name='delegateOrgCode']", "#delegateOrgModal").val(delegateOrg.orgCode);
            $("input[name='IPAddress']", "#delegateOrgModal").val(delegateOrg.ipaddrRange);
            $("input[name='phoneNumber']", "#delegateOrgModal").val(delegateOrg.contactPhonenum);
            $("input[name='loginPassword']", "#delegateOrgModal").val(delegateOrg.password);
            $("#delegateOrgModal").modal('show');
        }

        function checkInputValids(){

            var delegateOrgName = $("#delegateOrgName").val();
            if(delegateOrgName == ""){
                alert("请输入单位名称!");
                $("#delegateOrgName").focus();
                return;
            }

            var loginPassword = $("#loginPassword").val();
            if(loginPassword == ""){
                alert("请输入登录密码!");
                $("#loginPassword").focus();
                return;
            }

            var delegateOrgCode = $("#delegateOrgCode").val();
            if(delegateOrgCode == ""){
                alert("请输入单位编码!");
                $("#delegateOrgCode").focus();
                return;
            }

            return true;

        }

        function params(){

            var delegateOrg = {};
            delegateOrg.id = $("input[name='delegateOrgId']").val();
            delegateOrg.orgName = $("input[name='delegateOrgName']").val();
            delegateOrg.orgCode = $("input[name='delegateOrgCode']").val();
            delegateOrg.ipaddrRange = $("input[name='IPAddress']").val();
            delegateOrg.contactPhonenum = $("input[name='phoneNumber']").val();
            delegateOrg.loginPassword = $("input[name='loginPassword']").val();

            return delegateOrg;
        }

        $("#saveBtn").on("click",function(){

            if(!checkInputValids())
                return;


            $.ajax({
                url:"<%=path%>/center/7/editDelegateOrg.html",
                type:"post",
                data:JSON.stringify(params()),
                dataType:"json",
                contentType:"application/json;charset=utf-8",
                success: function (data) {
                    if(data.success){
                        location.href = "<%=path%>/center/7/02.html";
                    }else {
                        alert("操作失败!");
                    }
                },
                error:function(data){
                    alert("操作失败!");
                }
            });
        });

        $("button[name='delBtn']","#delegateOrgListTbody").on("click",function(){

            if(!confirm("确认删除吗")){
                return;
            }
            var delegateOrgId = $("input[name='orgId']", $(this).parent()).val();
            $.ajax({
                url:"<%=path%>/center/7/delDelegateOrg.html?id=" + delegateOrgId,
                type:"post",
                dataType:"json",
                success: function (data) {
                    if(data.success){
                        location.href = "<%=path%>/center/7/02.html";
                    }else {
                        alert("删除失败!");
                    }
                },
                error:function(data){
                    alert("删除失败!");
                }
            });
        });

    });
</script>
<!-- END JS -->
</body>
</html>


