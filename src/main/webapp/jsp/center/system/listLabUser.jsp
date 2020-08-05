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
                            <span class="label label-primary">鉴定用户列表</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <div class="clearfix">
                                <div class="btn-group">
                                    <button id="newLabUserBtn" class="btn btn-success green">
                                        添加 <i class="fa fa-plus"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="space15" style="height: 8px;"></div>
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>登录名</th>
                                    <th>姓名</th>
                                    <th>性别</th>
                                    <th>证件号</th>
                                    <th>联系电话</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="labUserListTbody">
                                <c:forEach items="${labUserList}" var="labUser" varStatus="s">
                                    <tr>
                                        <td>${s.count}</td>
                                        <td>${labUser.loginName}</td>
                                        <td>${labUser.userName}</td>
                                        <td>${labUser.gender}</td>
                                        <td>${labUser.cardId}</td>
                                        <td>${labUser.phoneNum}</td>
                                        <td>
                                            <input type="hidden" name="labUserId" value="${labUser.id}"/>
                                            <button name="editBtn" class="btn btn-primary btn-xs"><i class="fa fa-pencil"></i> 修改</button>
                                            <button name="delBtn" class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> 删除</button>
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

        $("#newLabUserBtn").on("click",function(){
            location.href='<%=path%>/center/7/editLabUser.html?operateType=1';
        });

        $("button[name='editBtn']","#labUserListTbody").on('click',function(){
            var labUserId = $("input[name='labUserId']", $(this).parent()).val();
            location.href='<%=path%>/center/7/editLabUser.html?labUserId='+labUserId +"&operateType=2";
        });

        $("button[name='delBtn']","#labUserListTbody").on("click",function(){
            if(confirm("确认要删除该用户信息吗？")){
                var labUserId=$("input[name='labUserId']", $(this).parent()).val();
                location.href='<%=path%>/center/7/delLabUser.html?labUserId='+labUserId;
            }
        });
    });
</script>
<!-- END JS -->
</body>
</html>


