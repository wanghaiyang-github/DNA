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
                <span class="label label-primary">panel添加</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
            </header>
            <div class="panel-body">
                <form id="panelForm" action="<%=path%>/center/7/06.html" class="form-horizontal tasi-form" method="get">
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">试剂盒名称</label>
                        <div class="col-sm-2">
                            <input class="form-control" id="panelInfoName" name="panelInfoName" type="text" value="${panelInfo.panelName}">
                        </div>
                        <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">厂商</label>
                        <div class="col-sm-2">
                            <input class="form-control" id="producer" name="producer" type="text" value="${panelInfo.panelProducer}">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">版本</label>
                        <div class="col-sm-2">
                            <input class="form-control" id="version" name="version" type="text" value="${panelInfo.panelVersion}">
                        </div>
                        <div class="col-sm-2">
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
                <span class="label label-primary">panel列表</span>
                            <span class="tools pull-right">
                                <a href="javascript:;" class="fa fa-chevron-down"></a>
                            </span>
            </header>
            <div class="panel-body">
                <table class="table table-striped table-advance table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>试剂盒名称</th>
                        <th>厂商</th>
                        <th>提交时间</th>
                        <th>版本</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="panelTbody">
                    <c:forEach items="${panelInfoList}" var="panelInfo" varStatus="p">
                        <tr>
                            <td>${p.count}</td>
                            <td><a href="<%=path%>/center/7/viewMarkerList.html?panelInfoId=${panelInfo.id}&view=view">${panelInfo.panelName}</a></td>
                            <td><input type="hidden" name="panelProducer" value="${panelInfo.panelProducer}"/>${panelInfo.panelProducer}</td>
                            <td><input type="hidden" name="createDatetime" value="${panelInfo.createDatetime}"/><fmt:formatDate value='${panelInfo.createDatetime}' pattern='yyyy-MM-dd'/></td>
                            <td><input type="hidden" name="panelVersion" value="${panelInfo.panelVersion}"/>${panelInfo.panelVersion}</td>
                            <td>
                                <input type="hidden" name="panelId" value="${panelInfo.id}">
                                <button name="modifyBtn" onclick='modifyBtn(${panelInfo.id})' class="btn btn-primary btn-xs"><i class="fa fa-pencil"></i>修改</button>
                                <button name="deleteBtn" onclick='deleteBtn(${panelInfo.id})' class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i>删除</button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
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
            location.href = "<%=path%>/center/7/06.html?"+getParamsStr();
        });

        function getParamsStr(){

            return "panelName="+$("#panelInfoName").val() + "&panelProducer="+$("#producer").val()+ "&panelVersion="+$("#version").val();

        }

        function getParams(){
            var panelInfo = { };

            panelInfo.panelName = $("#panelInfoName").val();
            panelInfo.panelProducer = $("#producer").val();
            panelInfo.panelVersion = $("#version").val();

            return panelInfo;
        }

        $("#addBtn").on("click",function(){

            var panelName = $("#panelInfoName").val();
            if(panelName == ""){
                alert("请输入试剂盒名称!");
                $("#panelInfoName").focus();
                return;
            }

            $.ajax({
                url:"<%=path%>/center/7/selectPanelInfoRepeatQuery.html",
                type:"post",
                data:JSON.stringify(getParams()),
                dataType:"json",
                contentType: "application/json; charset=utf-8",
                success:function(data){
                    if(data > 0){
                        alert("试剂盒已存在,请重新添加!");
                        $("#panelInfoName").focus();
                        return;
                    }else {
                       $.ajax({
                            url:"<%=path%>/center/7/addPanelInfo.html",
                            type:"post",
                            data:JSON.stringify(getParams()),
                            dataType:"json",
                            contentType:"application/json;charset=utf-8",
                            success: function (data) {
                                if(data.success){
                                    location.href = "<%=path%>/center/7/06.html";
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

    });

    function modifyBtn(obj) {
        location.href = "<%=path%>/center/7/viewMarkerList.html?panelInfoId=" + obj + "&view=" + "view";
    }

    function getParam(obj){
        var panelInfo = {};

        panelInfo.id = obj;

        return panelInfo;
    }
    function deleteBtn(obj){
        if(!confirm("确认删除吗")){
            return;
        }

        $.ajax({
            url:"<%=path%>/center/7/deletePanelInfo.html",
            type:"post",
            data:JSON.stringify(getParam(obj)),
            dataType:"json",
            contentType:"application/json;charset=utf-8",
            success: function (data) {
                if(data){
                    location.href = "<%=path%>/center/7/06.html";
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


