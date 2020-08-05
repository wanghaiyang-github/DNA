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
                <span class="label label-primary">种族信息</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
            </header>
            <div class="panel-body">
                <form id="raceForm" action="<%=path%>/center/7/07.html" class="form-horizontal tasi-form" method="get">
                    <div class="form-group">
                        <label class="col-sm-1 control-label" style="text-align:center;">种族名称</label>
                        <div class="col-sm-2">
                            <input class="form-control" id="raceName" name="raceName" type="text">
                        </div>
                        <label class="col-sm-1 control-label" style="text-align:center;">提交者</label>
                        <div class="col-sm-2">
                            <select class="form-control" name="createPerson" id="createPerson" value="${raceInfo.createPerson}">
                                <option value="">全部</option>
                                <c:forEach items="${labUserList}" var="labUser" varStatus="lu">
                                    <option value="${labUser.userName}" <c:if test="${labUser.userName eq raceInfo.createPerson}">selected</c:if>>${labUser.userName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <label class="col-sm-1 control-label" style="text-align:center;">备注</label>
                        <div class="col-sm-2">
                            <input class="form-control" id="comments" name="comments" type="text">
                        </div>
                        <div class="col-sm-2">
                            <button class="btn btn-primary" type="button" id="submitBtn">查询</button>
                            <button class="btn btn-primary" type="button" id="addBtn">添加</button>
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
                <span class="label label-primary">种族列表</span>
                            <span class="tools pull-right">
                                <a href="javascript:;" class="fa fa-chevron-down"></a>
                            </span>
            </header>
            <div class="panel-body">
                <table class="table table-striped table-advance table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>种族名称</th>
                        <th>提交者</th>
                        <th>提交时间</th>
                        <th>备注</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="raceTbody">
                    <c:forEach items="${raceInfoList}" var="raceInfo" varStatus="r">
                        <tr>
                            <td><input type="hidden" name="countryName" value="${raceInfo.raceName}">${raceInfo.raceName}</td>
                            <td><input type="hidden" name="personName" value="${raceInfo.createPerson}">${raceInfo.createPerson}</td>
                            <td><fmt:formatDate value='${raceInfo.createDatetime}' pattern='yyyy-MM-dd hh:mm'/></td>
                            <td><input type="hidden" name="comment" value="${raceInfo.comments}">${raceInfo.comments}</td>
                            <td>
                                <input type="hidden" name="raceId" value="${raceInfo.id}">
                                <button name="modifyBtn" class="btn btn-primary btn-xs"><i class="fa fa-pencil"></i>修改</button>
                                <%--<button name="insertBtn" onclick='insertBtn(${raceInfo.id})' class="btn btn-primary btn-xs"><i class="fa fa-plus"></i>插入</button>--%>
                                <button name="deleteBtn" onclick='deleteBtn(${raceInfo.id})' class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i>删除</button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <div class="modal fade" id="raceInfoModal" aria-hidden="true">
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
                                        <label class="control-label col-md-3">种族名称:</label>
                                        <div class="col-md-5">
                                            <input name="raceInfoName" id="raceInfoName" type="text" class="form-control small required"/>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3">提交者:</label>
                                        <div class="col-md-5">
                                            <select class="form-control" name="createInfoPerson" id="createInfoPerson">
                                                <option value="">全部</option>
                                                <c:forEach items="${labUserList}" var="labUser" varStatus="lu">
                                                    <option value="${labUser.userName}">${labUser.userName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="control-label col-md-3">备注:</label>
                                        <div class="col-md-5">
                                            <input name="remark" id="remark" type="text" class="form-control small required"/>
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <input type="hidden" name="id" id="id" value=""/>
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
            $("#raceForm").submit();
        });

        $("button[name='modifyBtn']","#raceTbody").on("click",function(){
            EditRaceInfoRow(this);
        });

        function EditRaceInfoRow(obj){
            var $curTR = $(obj).parents("tr");
            var raceInfo = {};
            raceInfo.raceName = $("input[name='countryName']", $curTR).val();
            raceInfo.createPerson = $("input[name='personName']", $curTR).val();
            raceInfo.comments = $("input[name='comment']", $curTR).val();
            raceInfo.id = $("input[name='raceId']", $curTR).val();

            newRaceInfoRow(raceInfo);
        }

        function newRaceInfoRow(raceInfo) {
            $("input[name='id']", "#raceInfoModal").val(raceInfo.id);
            $("input[name='raceInfoName']", "#raceInfoModal").val(raceInfo.raceName);
            $("select[name='createInfoPerson']", "#raceInfoModal").val(raceInfo.createPerson);
            $("input[name='remark']", "#raceInfoModal").val(raceInfo.comments);

            $("#raceInfoModal").modal('show');
        }

        function getParams(){
            var raceInfo = { };

            raceInfo.raceName = $("#raceName").val();
            raceInfo.createPerson = $("#createPerson").val();
            raceInfo.comments = $("#comments").val();

            return raceInfo;
        }

        $("#addBtn").on("click",function(){

            var raceName = $("#raceName").val();
            if(raceName == ""){
                alert("请输入种族名称!");
                return;
            }

            $.ajax({
                url:"<%=path%>/center/7/selectRaceInfoRepeatQuery.html?raceName=" + raceName,
                type:"post",
                dataType:"json",
                success:function(data){
                    if(data > 0){
                        alert("此种族名称已存在，请重新填写!");
                        return;
                    }else {
                       $.ajax({
                            url:"<%=path%>/center/7/insertRaceInfo.html",
                            type:"post",
                            data:JSON.stringify(getParams()),
                            dataType:"json",
                            contentType:"application/json;charset=utf-8",
                            success: function (data) {
                                if(data.success){
                                    location.href = "<%=path%>/center/7/07.html";
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

        function params(){
            var raceInfo = { };

            raceInfo.id = $("#id").val();
            raceInfo.raceName = $("#raceInfoName").val();
            raceInfo.createPerson = $("#createInfoPerson").val();
            raceInfo.comments = $("#remark").val();

            return raceInfo;
        }

        $("#saveBtn").on("click",function(){

            var raceName = $("#raceInoName").val();
            if(raceName == ""){
                alert("请输入种族名称!");
                return;
            }

            var createPerson = $("#createInfoPerson").val();
            if (createPerson == ""){
                alert("请选择提交者!");
                return;
            }

            $.ajax({
                url:"<%=path%>/center/7/selectRaceInfoRepeatQuery.html?raceName=" + raceName,
                type:"post",
                dataType:"json",
                success:function(data){
                    if(data > 0){
                        alert("此种族名称已存在，请重新填写!");
                        return;
                    }else {
                        $.ajax({
                            url:"<%=path%>/center/7/updateRaceInfo.html",
                            type:"post",
                            data:JSON.stringify(params()),
                            dataType:"json",
                            contentType:"application/json;charset=utf-8",
                            success: function (data) {
                                if(data.success){
                                    location.href = "<%=path%>/center/7/07.html";
                                }else {
                                    alert("修改失败!");
                                }
                            },
                            error:function(data){
                                alert("修改失败!");
                            }
                        });

                    }
                }
            });
        });

    });

    function insertBtn(obj) {
        location.href = "<%=path%>/center/7/08.html?raceId=" + obj;
    }

    function deleteBtn(obj){
        if(!confirm("确认删除吗")){
            return;
        }

        $.ajax({
            url:"<%=path%>/center/7/deleteRaceInfo.html?raceId=" + obj,
            type:"post",
            dataType:"json",
            contentType:"application/json;charset=utf-8",
            success: function (data) {
                if(data){
                    location.href = "<%=path%>/center/7/07.html";
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


