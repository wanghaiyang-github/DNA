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
                            <span class="label label-primary">权限列表</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <div class="clearfix">
                                <div class="btn-group">
                                    <button id="newLabPermissionBtn" class="btn btn-success green">
                                        添加 <i class="fa fa-plus"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="space15" style="height: 8px;"></div>
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>授权名</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="labRoleListTbody">
                                <c:forEach items="${labRoleList}" var="labRole" varStatus="s">
                                    <tr>
                                        <td>${s.count}</td>
                                        <td>${labRole.roleName}</td>
                                        <td>
                                            <input type="hidden" name="labRoleId" value="${labRole.id}"/>
                                            <button name="editBtn" class="btn btn-primary btn-xs"><i class="fa fa-pencil"></i> 编 辑</button>
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

        $("#newLabPermissionBtn").on("click",function(){
            location.href='<%=path%>/center/7/editPermission.html?operateType=1';
        });

        $("button[name='editBtn']","#labRoleListTbody").on('click',function(){
            var labRoleId = $("input[name='labRoleId']", $(this).parent()).val();
            location.href='<%=path%>/center/7/editPermission.html?labRoleId='+labRoleId +"&operateType=2";
        });

        <%--$("button[name='delBtn']","#labRoleListTbody").on("click",function(){--%>
            <%--if(confirm("确认要删除该用户信息吗？")){--%>
                <%--var labRoleId=$("input[name='labRoleId']", $(this).parent()).val();--%>
                <%--$("#wrapper-content").load('<%=path%>/center/7/delLabPermission.html?labRoleId='+labRoleId, function(){$("#wrapper-content").fadeIn(100);});--%>
            <%--}--%>
        <%--});--%>
    });
</script>
<!-- END JS -->
</body>
</html>


