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
                            <span class="label label-primary">设备列表</span>
                               <span class="tools pull-right">
                                   <a href="javascript:;" class="fa fa-chevron-down"></a>
                               </span>
                        </header>
                        <div class="panel-body">
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
                                        <c:if test="${equipmentInfo.equipmentStatus eq '01'}"><td style="background-color: #13ff48;"></c:if>
                                        <c:if test="${equipmentInfo.equipmentStatus eq '02'}"><td style="background-color: #ff0b0a;"></c:if>
                                            ${equipmentInfo.equipmentStatusName}
                                        </td>
                                        <td>
                                            <input type="hidden" name="id" value="${equipmentInfo.id}"/>
                                            <button name="repairBtn" class="btn btn-primary btn-sm"><i class="fa fa-edit"></i>维修记录</button>
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

        $("button[name='repairBtn']", "#equipmentInfoListTbody").on('click',function(){
            var id = $("input[name='id']", $(this).parent()).val();
            location.href = "<%=path%>/center/equipment/equipmentRepair.html?equipmentInfoId=" + id;
        });

    });
</script>
<!-- END JS -->
</body>
</html>


