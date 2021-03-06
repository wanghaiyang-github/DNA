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
                            <span class="label label-primary">设备类型列表</span>
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
                                    <th>类型编号</th>
                                    <th>类型名称</th>
                                    <th>实验阶段</th>
                                    <th>入库时间</th>
                                    <th>描 述</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="equipmentTypeInfoList">
                                <c:forEach items="${equipmentTypeInfoList}" var="equipmentTypeInfo" varStatus="s">
                                    <tr>
                                        <td>${s.count}</td>
                                        <td>${equipmentTypeInfo.equipmentTypeNo}</td>
                                        <td>${equipmentTypeInfo.equipmentTypeName}</td>
                                        <td>${equipmentTypeInfo.experimentalStageName}</td>
                                        <td><fmt:formatDate value="${equipmentTypeInfo.createDatetime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                        <td>${equipmentTypeInfo.remark}</td>
                                        <td>
                                            <input type="hidden" name="id" value="${equipmentTypeInfo.id}"/>
                                            <button name="editBtn" class="btn btn-primary btn-xs"><i class="fa fa-pencil"></i> 修改</button>
                                            <%--<button name="delBtn" class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> 删除</button>--%>
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

        $("button[name='addBtn']").on('click',function(){
            location.href = "<%=path%>/center/equipment/equipmentTypeInfo.html";
        });

        $("button[name='editBtn']","#equipmentTypeInfoList").on("click",function(){
            var id = $("input[name='id']", $(this).parent()).val();
            location.href = "<%=path%>/center/equipment/equipmentTypeInfo.html?id="+ id + "&operateType=2";
        });

        $("button[name='delBtn']","#equipmentTypeInfoList").on("click",function(){
            if(confirm("确认删除吗？")){
                var id = $("input[name='id']", $(this).parent()).val();
                $.ajax({
                    url : "<%=path%>/center/equipment/delEquipmentTypeInfo.html?id=" + id,
                    type:"get",
                    dataType : "json",
                    success : function(data) {
                        if(data.success || data.success == true || data.success == "true") {
                            location.href = "<%=path%>/center/equipment/equipmentTypeList.html";
                        }else {
                            alert("删除失败！");
                        }
                    },
                    error: function(e){
                        alert(e);
                    }
                });
            }
        });
    });
</script>
<!-- END JS -->
</body>
</html>


