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
                            <span class="label label-primary">检材记录列表</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>检材编号</th>
                                    <th>检材名称</th>
                                    <th>检材类型</th>
                                    <th>入库时间</th>
                                    <th>入库人</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="panelTbody">
                                <c:forEach items="${sampleGeneList}" var="sampleGene" varStatus="s">
                                    <tr>
                                        <td>${sampleGene.sampleNo}</td>
                                        <td>${sampleGene.sampleName}</td>
                                        <td>${sampleGene.sampleTypeName}</td>
                                        <td><fmt:formatDate value='${sampleGene.createDatetime}' pattern='yyyy-MM-dd HH:mm'/></td>
                                        <td>${sampleGene.createPerson}</td>
                                        <td> <button class="btn btn-primary btn-sm" id="editBtn" type="button" onclick="editBtn(${sampleGene.id})"><i class="fa fa-pencil"></i>编 辑 </button></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                            <div class="form-group pull-right">
                                <input type="hidden" value="${sampleInfo.consignmentId}" name="consignmentId" id="consignmentId">
                                <input type="hidden" value="${page}" name="page" id="page">
                                <button class="btn btn-lg btn-info" id="backBtn" type="button"><i class="fa fa-reply"></i> 返 回 </button>
                            </div>
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

    function editBtn(id){
        location.href = "<%=path%>/center/3/editGene.html?id=" + id;
    }

    $(function(){
        'use strict';

        $("#backBtn").on("click", function() {
            var consignmentId = $("#consignmentId").val();
            var page = $("#page").val();
            location.href = "<%=path%>/center/3/checkList.html?consignmentId=" + consignmentId + "&page=" + page;
        });
    });


</script>
<!-- END JS -->
</body>
</html>


