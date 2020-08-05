<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <!-- BEGIN STYLESHEET-->
    <link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="<%=path%>/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="<%=path%>/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON CSS -->
    <link href="<%=path%>/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="<%=path%>/css/style-responsive.css" rel="stylesheet"><!-- THEME RESPONSIVE CSS -->
    <!--[if lt IE 9]>
    <script src="<%=path%>/js/html5shiv.js">
    </script>
    <script src="<%=path%>/js/respond.min.js">
    </script>
    <![endif]-->
    <!-- END STYLESHEET-->
</head>
<body>
<!-- BEGIN WRAPPER  -->
<!-- END ROW  -->

<!-- BEGIN ROW  -->
<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <header class="panel-heading">
                <span class="label label-primary">基因座添加</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
            </header>
            <div class="panel-body">
                <form id="geneManagementForm" action="<%=path%>/center/7/05.html" class="form-horizontal tasi-form" method="get">
                    <div class="form-group">
                        <label class="col-sm-2 control-label">基因座名称</label>
                        <div class="col-sm-2">
                            <input class="form-control" id="markerName" name="markerName" type="text" value="${markerInfo.markerName}">
                        </div>
                        <label class="col-sm-2 control-label">别名</label>
                        <div class="col-sm-2">
                            <input class="form-control" id="markerAlias" name="markerAlias" type="text" value="${markerInfo.markerAlias}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label">备注</label>
                        <div class="col-sm-2">
                            <input class="form-control" id="markerDesc" name="markerDesc" type="text" value="${markerInfo.markerDesc}">
                        </div>
                        <div class="col-sm-2">
                            <button class="btn btn-primary" type="button" id="addBtn">添加</button>
                            <button class="btn btn-primary" type="button" id="submitBtn">查询</button>
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
                <span class="label label-primary">基因座列表</span>
                            <span class="tools pull-right">
                                <a href="javascript:;" class="fa fa-chevron-down"></a>
                            </span>
            </header>
            <div class="panel-body">
                <table class="table table-striped table-advance table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>基因座名称</th>
                        <th>基因座别名</th>
                        <th>备注</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="geneManagementTbody">
                    <c:forEach items="${markerInfoList}" var="markerInfos" varStatus="m">
                        <tr>
                            <td>${m.count}</td>
                            <td><input type="hidden" name="markerInfoName" value="${markerInfos.markerName}"/>${markerInfos.markerName}</td>
                            <td><input type="hidden" name="markerInfoAlias" value="${markerInfos.markerAlias}"/>${markerInfos.markerAlias}</td>
                            <td><input type="hidden" name="markerInfoDesc" value="${markerInfos.markerDesc}"/>${markerInfos.markerDesc}</td>
                            <td>
                                <input type="hidden" name="markerInfoOrder" value="${markerInfos.markerOrder}"/>
                                <input type="hidden" name="markerInfoId" value="${markerInfos.id}">
                                <button name="modifyBtn" class="btn btn-primary btn-xs"><i class="fa fa-pencil"></i>修改</button>
                                <%--<button name="deleteBtn" onclick='deleteBtn(${markerInfos.id})' class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i>删除</button>--%>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <div class="modal fade" id="markerInfoModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                    &times;
                                </button>
                                <h4 class="modal-title">
                                    基因座信息
                                </h4>
                            </div>
                            <div class="modal-body">
                                <form class="form-horizontal tasi-form">
                                    <div class="form-group">
                                        <label class="control-label col-md-3">基因座名称:</label>
                                        <div class="col-md-5">
                                            <input name="geneName" id="geneName" type="text" readonly="readonly" class="form-control small required" value=""/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3">别名:</label>
                                        <div class="col-md-5">
                                            <input name="aliasName" id="aliasName" type="text" class="form-control small required" value=""/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3">备注:</label>
                                        <div class="col-md-5">
                                            <input name="remark" id="remark" type="text" class="form-control small required" value=""/>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <input type="hidden" name="markerId" id="markerId" value=""/>
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

<!-- END ROW  -->
<!-- BEGIN JS -->
<script src="<%=path%>/js/jquery.js" ></script><!-- BASIC JS LIABRARY -->
<script src="<%=path%>/js/bootstrap.min.js" ></script><!-- BOOTSTRAP JS  -->
<script src="<%=path%>/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script><!-- DATEPICKER JS  -->
<script src="<%=path%>/assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script><!-- DATEPICKER JS  -->
<script src="<%=path%>/js/respond.min.js" ></script><!-- RESPOND JS  -->
<script src="<%=path%>/js/jquery.paginate.js"></script>
<script>
    $(function(){
        'use strict';

        $("#submitBtn").on("click",function(){
            location.href = "<%=path%>/center/7/05.html?" + getParamsStr();
        });

        function getParamsStr(){

            return "markerName="+$("#markerName").val() + "&markerAlias="+$("#markerAlias").val() + "&markerDesc=" + $("#markerDesc").val();

        }

        function checkInputValid(){

            var markerName = $("#markerName").val();
            if(markerName == ""){
                alert("请输入基因座名称!");
                $("#markerName").focus();
                return;
            }

            return true;

        }


        function getParams(){
            var markerInfo = { };

            markerInfo.markerName = $("#markerName").val();
            markerInfo.markerAlias = $("#markerAlias").val();
            markerInfo.markerDesc = $("#markerDesc").val();

            return markerInfo;
        }

        $("#addBtn").on("click",function(){

            if(!checkInputValid())
                return;

            $.ajax({
                url:"<%=path%>/center/7/selectRepeatQuery.html",
                type:"post",
                data:JSON.stringify(getParams()),
                dataType:"json",
                contentType: "application/json; charset=utf-8",
                success:function(data){
                    if(data > 0){
                        alert("基因座已存在，请重新输入!");
                        return;
                    }else {
                        $.ajax({
                            url:"<%=path%>/center/7/add.html",
                            type:"post",
                            data:JSON.stringify(getParams()),
                            dataType:"json",
                            contentType:"application/json;charset=utf-8",
                            success: function (data) {
                                if(data.success){
                                    location.href = "<%=path%>/center/7/05.html";
                                }else {
                                    alert("添加失败!");
                                }
                            },
                            error:function(data){
                                alert("添加失败!");
                            }
                        });

                    }
                }
            });
        });

        $("button[name='modifyBtn']","#geneManagementTbody").on("click",function(){
            EditMarkerInfoRow(this);
        });

        function EditMarkerInfoRow(obj){
            var $curTR = $(obj).parents("tr");
            var markerInfo = {};
            markerInfo.markerName = $("input[name='markerInfoName']", $curTR).val();
            markerInfo.markerAlias = $("input[name='markerInfoAlias']", $curTR).val();
            markerInfo.markerOrder = $("input[name='markerInfoOrder']", $curTR).val();
            markerInfo.markerDesc = $("input[name='markerInfoDesc']", $curTR).val();
            markerInfo.id = $("input[name='markerInfoId']", $curTR).val();

            newMarkerInfoRow(markerInfo);
        }

        function newMarkerInfoRow(markerInfo) {
            $("input[name='markerId']", "#markerInfoModal").val(markerInfo.id);
            $("input[name='geneName']", "#markerInfoModal").val(markerInfo.markerName);
            $("input[name='aliasName']", "#markerInfoModal").val(markerInfo.markerAlias);
            $("input[name='remark']", "#markerInfoModal").val(markerInfo.markerDesc);

            $("#markerInfoModal").modal('show');
        }

        function checkInputValids(){

            var geneName = $("#geneName").val();
            if(geneName == ""){
                alert("请输入基因座名称!");
                $("#geneName").focus();
                return;
            }

            $("#markerInfoModal").modal('hide');
            return true;

        }

        function params(){
            var markerInfo = { };

            markerInfo.markerName = $("#geneName").val();
            markerInfo.markerAlias = $("#aliasName").val();
            markerInfo.markerDesc = $("#remark").val();
            markerInfo.id = $("#markerId").val();

            return markerInfo;
        }

        $("#saveBtn").on("click",function(){

            if(!checkInputValids())
                return;


            $.ajax({
                url:"<%=path%>/center/7/update.html",
                type:"post",
                data:JSON.stringify(params()),
                dataType:"json",
                contentType:"application/json;charset=utf-8",
                success: function (data) {
                    if(data.success){
                        alert("修改成功!");
                        location.href = "<%=path%>/center/7/05.html";
                    }else {
                        alert("修改失败!");
                    }
                },
                error:function(data){
                    alert("修改失败!");
                }
            });
        });

    });


    function getParam(obj){
        var markerInfo = {};

        markerInfo.markerName = $("#markerName").val();
        markerInfo.markerAlias = $("#markerAlias").val();
        markerInfo.markerDesc = $("#markerDesc").val();
        markerInfo.id = obj;

        return markerInfo;
    }
    function deleteBtn(obj){
        if(!confirm("确认删除吗")){
            return;
        }

        $.ajax({
            url:"<%=path%>/center/7/delete.html",
            type:"post",
            data:JSON.stringify(getParam(obj)),
            dataType:"json",
            contentType:"application/json;charset=utf-8",
            success: function (data) {
                if(data.success){
                    alert("删除成功!");
                    location.href = "<%=path%>/center/7/05.html";
                }else {
                    alert("删除失败!");
                }
            },
            error:function(data){
                alert("删除失败!");
            }
        });
    }
</script>
<!-- END JS -->
</body>
</html>


