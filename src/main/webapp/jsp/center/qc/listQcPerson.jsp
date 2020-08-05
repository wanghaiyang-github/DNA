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
                <span class="label label-primary">质控人员列表</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
            </header>
            <div class="panel-body">
                <div class="clearfix">
                    <div class="btn-group">
                        <button id="addBtn" class="btn btn-success green">
                            添加 <i class="fa fa-plus"></i>
                        </button>
                    </div>
                </div>
                <div class="space15" style="height: 8px;"></div>
                <table class="table table-striped table-advance table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>人员编号</th>
                        <th>姓名</th>
                        <th>性别</th>
                        <th>证件号码</th>
                        <th>联系电话</th>
                        <th>单位名称</th>
                        <th>实验室人员</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="qcPersonListTbody">
                    <c:forEach items="${qcPersonGeneList}" var="qcPerson" varStatus="s">
                        <tr>
                            <td>${s.count}</td>
                            <td>${qcPerson.qcPersonNo}</td>
                            <td>${qcPerson.qcPersonName}</td>
                            <td>${qcPerson.qcPersonGender}</td>
                            <td>${qcPerson.qcPersonCardId}</td>
                            <td>${qcPerson.qcPersonPhonenum}</td>
                            <td>${qcPerson.qcPersonOrgName}</td>
                            <td>
                                <c:if test="${qcPerson.qcLabUserFlag eq 0}">否</c:if><c:if test="${qcPerson.qcLabUserFlag eq 1}">是</c:if>
                            </td>
                            <td>
                                <input type="hidden" name="qcPersonId" value="${qcPerson.id}"/>
                                <button name="editBtn" class="btn btn-primary btn-sm"><i class="fa fa-pencil"></i> 查看</button>
                                <button name="delBtn" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 删除</button>
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
<script src="<%=path%>/js/content.js"></script>
<script>
    $(function(){
        'use strict';

        $("#addBtn").on("click",function(){
            location.href='<%=path%>/center/qc/editQcPerson.html?operateType=1';
        });

        $("button[name='editBtn']","#qcPersonListTbody").on('click',function(){
            var qcPersonId = $("input[name='qcPersonId']", $(this).parent()).val();
            location.href='<%=path%>/center/qc/editQcPerson.html?qcPersonId='+qcPersonId +"&operateType=2";
        });



        $("button[name='delBtn']","#qcPersonListTbody").on("click",function(){
            if(confirm("确认要删除该质控人员信息吗？")){
                var qcPersonId=$("input[name='qcPersonId']", $(this).parent()).val();
                location.href='<%=path%>/center/qc/delQcPerson.html?qcPersonId='+qcPersonId;
            }
        });
    });
</script>
<!-- END JS -->
</body>
</html>


