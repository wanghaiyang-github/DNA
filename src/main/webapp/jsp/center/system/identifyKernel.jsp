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
                            <span class="label label-primary">鉴定机构名称列表</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <div class="clearfix">
                                <div class="btn-group">
                                    <button name="addBtn" class="btn btn-success green">
                                        添加 <i class="fa fa-plus"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="space15" style="height: 8px;"></div>
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>鉴定机构名称</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="identifyKernelListTbody">
                                <c:forEach items="${identifyKernelList}" var="identifyKernel" varStatus="s">
                                    <tr>
                                        <td>${s.count}</td>
                                        <td>${identifyKernel.identifyKernelName}</td>
                                        <td>
                                            <input type="hidden" name="id" value="${identifyKernel.id}"/>
                                            <input type="hidden" name="name" value="${identifyKernel.identifyKernelName}"/>
                                            <button name="editBtn" class="btn btn-primary btn-xs"><i class="fa fa-pencil"></i> 修改</button>
                                            <button name="delBtn" class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> 删除</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                            <div class="modal fade" id="identifyKernelModal" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                &times;
                                            </button>
                                            <h4 class="modal-title">
                                                鉴定机构信息
                                            </h4>
                                        </div>
                                        <div class="modal-body">
                                            <form class="form-horizontal tasi-form">
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">鉴定机构名称:</label>
                                                    <div class="col-md-5">
                                                        <input name="identifyKernelName" id="identifyKernelName" type="text" class="form-control small required"/>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <input type="hidden" name="identifyKernelId" value="${identifyKernel.id}"/>
                                            <button class="btn btn-success" type="button" id="saveBtn">确定</button>
                                            <button data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group pull-right">
                                <button class="btn btn-lg btn-info" id="backBtn" type="button"><i class="fa fa-reply"></i> 返 回 </button>
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

        $("button[name='editBtn']","#identifyKernelListTbody").on('click',function(){
            EditIdentifyKernelRow(this);
        });

        $("button[name='addBtn']").on('click',function(){
            EditIdentifyKernelRow(this);
        });

        function EditIdentifyKernelRow(obj){
            var $curTR = $(obj).parents("tr");
            var identifyKernel = {};
            identifyKernel.id = $("input[name='id']", $curTR).val();
            identifyKernel.name = $("input[name='name']", $curTR).val();

            newIdentifyKernelRow(identifyKernel);
        }

        function newIdentifyKernelRow(identifyKernel){
            $("input[name='identifyKernelId']", "#identifyKernelModal").val(identifyKernel.id);
            $("input[name='identifyKernelName']", "#identifyKernelModal").val(identifyKernel.name);

            $("#identifyKernelModal").modal('show');
        }

        function checkInputValids(){

            var identifyKernelName = $("#identifyKernelName").val();
            if(identifyKernelName == ""){
                alert("请输入鉴定机构名称!");
                $("#identifyKernelName").focus();
                return;
            }

            return true;

        }

        function params(){

            var identifyKernel = {};
            identifyKernel.id = $("input[name='identifyKernelId']").val();
            identifyKernel.identifyKernelName = $("input[name='identifyKernelName']").val();;

            return identifyKernel;
        }

        $("#saveBtn").on("click",function(){

            if(!checkInputValids())
                return;
            var identifyKernelName = $("input[name='identifyKernelName']").val();;
            $.ajax({
                url:"<%=path%>/center/7/selectRepeatIdentifyKernel.html?identifyKernelName=" + identifyKernelName,
                type:"post",
                dataType:"json",
                contentType:"application/json;charset=utf-8",
                success: function (data) {
                    if(data.success){
                        alert("已存在，请重新添加！");
                        $("#identifyKernelName").val("");
                        $("#identifyKernelName").focus();
                    }else {
                        $.ajax({
                            url:"<%=path%>/center/7/addOrEditIdentifyKernel.html",
                            type:"post",
                            data:JSON.stringify(params()),
                            dataType:"json",
                            contentType:"application/json;charset=utf-8",
                            success: function (data) {
                                if(data.success){
                                    location.href = "<%=path%>/center/7/identifyKernel.html";
                                }else {
                                    alert("操作失败!");
                                }
                            },
                            error:function(data){
                                alert("操作失败!");
                            }
                        });
                    }
                },
                error:function(data){
                    alert("操作失败!");
                }
            });
        });

        $("button[name='delBtn']","#identifyKernelListTbody").on("click",function(){

            if(!confirm("确认删除吗")){
                return;
            }
            var id = $("input[name='id']", $(this).parent()).val();

            $.ajax({
                url:"<%=path%>/center/7/delIdentifyKernel.html?id=" + id,
                type:"post",
                dataType:"json",
                success: function (data) {
                    if(data.success){
                        location.href = "<%=path%>/center/7/identifyKernel.html";
                    }else {
                        alert("删除失败!");
                    }
                },
                error:function(data){
                    alert("删除失败!");
                }
            });
        });

        $("#backBtn").on("click", function(){
            location.href = "<%=path%>/center/7/02.html";
        });

    });
</script>
<!-- END JS -->
</body>
</html>


