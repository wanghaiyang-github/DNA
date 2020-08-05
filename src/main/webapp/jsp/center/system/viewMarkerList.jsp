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
                <span class="label label-primary">panel信息</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
            </header>
            <div class="panel-body">
                <form id="panelForm" action="<%=path%>/center/7/06.html" class="form-horizontal tasi-form" method="get">
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">panel名称</label>
                        <div class="col-sm-2">
                            <input class="form-control" id="panelName" name="panelName" readonly="readonly" type="text" value="${panelInfo.panelName}">
                        </div>
                        <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">panel版本</label>
                        <div class="col-sm-2">
                            <input class="form-control" id="panelVersion" name="panelVersion" readonly="readonly" type="text" value="${panelInfo.panelVersion}">
                        </div>
                        <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">panel厂商</label>
                        <div class="col-sm-2">
                            <input class="form-control" id="panelProducer" name="panelProducer" readonly="readonly" type="text" value="${panelInfo.panelProducer}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">panel生成时间</label>
                        <div class="col-sm-2">
                            <input class="form-control" id="createDatetime" name="createDatetime" readonly="readonly" type="text" value="<fmt:formatDate value='${panelInfo.createDatetime}' pattern='yyyy-MM-dd'/>">
                        </div>
                        <label class="col-sm-2 col-sm-2 control-label"  style="text-align:center;">基因座名称</label>
                        <div class="col-sm-2">
                            <select class="form-control" id="markerNameSelect">
                                <option value="">请选择...</option>
                                <c:forEach items="${markerInfoList}" var="markerInfo" varStatus="do">
                                    <option value="${markerInfo.markerName}">${markerInfo.markerName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-sm-2">
                            <input type="hidden" name="id" id="id" value="${panelInfo.id}">
                            <input type="hidden" name="view" id="view" value="${view}">
                            <%--<c:if test="${view == 'view'}">--%>
                                <button class="btn btn-primary" type="button" id="addBtn">添加marker</button>
<%--                            </c:if>--%>
                            <%--<button class="btn btn-primary" type="button" id="submitBtn">查询</button>--%>
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
                        <th>基因座</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="panelTbody">
                    <c:forEach items="${panelList}" var="panel" varStatus="p">
                        <tr>
                            <td>${p.count}</td>
                            <td>${panel.markerName}</td>
                            <td>
                                <button name="deleteBtn" onclick='deleteBtn(${panel.id})' class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i>删除</button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <div class="form-group pull-right">
                    <button class="btn btn-lg btn-info" id="backBtn" type="button"><i class="fa fa-reply"></i> 返 回 </button>
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
            location.href = "<%=path%>/center/7/viewMarkerList.html?"+getParamsStr() + "&view=" + $("#view").val();
        });

        function getParamsStr(){

            return "panelName="+$("#panelName").val() + "&markerName="+$("#markerNameSelect").val()+ "&panelInfoId="+$("#id").val();

        }

        function getParams(){
            var panel = { };

            panel.panelInfoId = $("#id").val();
            panel.panelName = $("#panelName").val();
            panel.markerName = $("#markerNameSelect").val();

            return panel;
        }

        $("#addBtn").on("click",function(){

            var markerNameSelect = $("#markerNameSelect").val();
            if(markerNameSelect == ""){
                alert("请选择基因座名称!");
                return;
            }

            $.ajax({
                url:"<%=path%>/center/7/selectPanelRepeatQuery.html",
                type:"post",
                data:JSON.stringify(getParams()),
                dataType:"json",
                contentType: "application/json; charset=utf-8",
                success:function(data){
                    if(data > 0){
                        alert("此基因座已存在,请重新选择!");
                        return;
                    }else {
                       $.ajax({
                            url:"<%=path%>/center/7/addPanel.html",
                            type:"post",
                            data:JSON.stringify(getParams()),
                            dataType:"json",
                            contentType:"application/json;charset=utf-8",
                            success: function (data) {
                                if(data.success){
                                    var panelInfoId = $("#id").val();
                                    location.href = "<%=path%>/center/7/viewMarkerList.html?panelInfoId=" + panelInfoId + "&view=" + $("#view").val();
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

        $("#backBtn").on("click",function(){
            location.href='<%=path%>/center/7/06.html';
        });

    });

    function getParam(obj){
        var panel = {};

        panel.id = obj;

        return panel;
    }
    function deleteBtn(obj){
        if(!confirm("确认删除吗")){
            return;
        }

        var panelInfoId = $("#id").val();
        $.ajax({
            url:"<%=path%>/center/7/deletePanel.html",
            type:"post",
            data:JSON.stringify(getParam(obj)),
            dataType:"json",
            contentType:"application/json;charset=utf-8",
            success: function (data) {
                if(data){
                    var panelInfoId = $("#id").val();
                    location.href = "<%=path%>/center/7/viewMarkerList.html?panelInfoId=" + panelInfoId + "&view=" + $("#view").val();
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


