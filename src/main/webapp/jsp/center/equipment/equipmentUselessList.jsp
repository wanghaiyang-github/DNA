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
                            <span class="label label-primary">查询条件</span>
                            <span class="tools pull-right">
                            <a href="javascript:;" class="fa fa-chevron-down"></a>
                            </span>
                        </header>
                        <div class="panel-body">
                            <form action="<%=path%>/center/equipment/equipmentUselessList.html" id="queryForm" class="form-horizontal tasi-form" method="post">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">设备编号</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="equipmentNo" value="${equipmentInfo.equipmentNo}">
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align:center;">设备名称</label>
                                    <div class="col-sm-3">
                                        <select class="form-control required" id="equipmentNameId" name="equipmentNameId">
                                            <option value="">全部</option>
                                            <c:forEach items="${equipmentNameInfoList}" var="equipmentNameInfo" varStatus="do">
                                                <option value="${equipmentNameInfo.id}" <c:if test="${equipmentNameInfo.id eq equipmentInfo.equipmentNameId}">selected</c:if>>${equipmentNameInfo.equipmentName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">设备状态</label>
                                    <div class="col-sm-3">
                                        <select class="form-control required" name="equipmentStatus" value="${equipmentInfo.equipmentStatus}">
                                            <option value="">全部</option>
                                            <c:forEach items="${equipmentStatusList}" var="equipmentStatus" varStatus="do">
                                                <option value="${equipmentStatus.dictCode}" <c:if test="${equipmentStatus.dictCode eq equipmentInfo.equipmentStatus}">selected</c:if>>${equipmentStatus.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2">
                                        <button id="queryBtn" class="btn btn-primary" type="button"><i class="fa fa-search"></i> 查 询</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </section>
                </div>
            </div>
            <!-- END ROW  -->

            <!-- BEGIN ROW  -->
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            <span class="label label-primary">设备报废记录</span>
                               <span class="tools pull-right">
                                   <a href="javascript:;" class="fa fa-chevron-down"></a>
                               </span>
                        </header>
                        <div class="panel-body">
                            <div class="space15" style="height: 8px;"></div>
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>设备编号</th>
                                    <th>设备名称</th>
                                    <th>入库时间</th>
                                    <th>设备状态</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="equipmentInfoListTbody">
                                <c:forEach items="${equipmentInfoList}" var="equipmentInfo" varStatus="s">
                                    <tr>
                                        <td>${s.count}</td>
                                        <td>${equipmentInfo.equipmentNo}</td>
                                        <td>${equipmentInfo.equipmentName}</td>
                                        <td><fmt:formatDate value="${equipmentInfo.createDatetime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                        <td>${equipmentInfo.equipmentStatusName}</td>
                                        <td>
                                            <input type="hidden" name="id" value="${equipmentInfo.id}"/>
                                            <button name="applyUselessBtn" class="btn btn-primary btn-xs" <c:if test="${equipmentInfo.equipmentStatus eq '02'}">disabled</c:if> >
                                                <i class="fa fa-pencil"></i>申请报废
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
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

        $("button[name='applyUselessBtn']","#equipmentInfoListTbody").on("click",function(){
            if (confirm("是否申请报废！")) {
                var id = $("input[name='id']", $(this).parent()).val();
                $.ajax({
                    url : "<%=path%>/center/equipment/equipmentUseless.html?id=" + id,
                    type : "get",
                    dataType : "json",
                    success : function(data) {
                        if(data.success || data.success == true || data.success == "true") {
                            location.href = "<%=path%>/center/equipment/equipmentUselessList.html";
                        }else {
                            alert("申请失败！");
                        }
                    }
                });
            }
        });

        $("#queryBtn").on("click", function(){
            $('#queryForm').submit();
        });
    });
</script>
<!-- END JS -->
</body>
</html>


