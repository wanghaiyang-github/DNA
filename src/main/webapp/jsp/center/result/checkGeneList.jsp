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
                                    <th>入库状态</th>
                                    <th>查看基因型</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="geneTbody">
                                <c:forEach items="${sampleGeneList}" var="sampleGene" varStatus="s">
                                    <tr>
                                        <td>${sampleGene.sampleNo}</td>
                                        <td title="${sampleGene.sampleName}">
                                            <c:if test="${fn:length(sampleGene.sampleName) gt 8}">${fn:substring(sampleGene.sampleName,0,7)} ...</c:if>
                                            <c:if test="${fn:length(sampleGene.sampleName) lt 9}">${sampleGene.sampleName}</c:if>
                                        </td>
                                        <td>${sampleGene.sampleTypeName}</td>
                                        <td><fmt:formatDate value='${sampleGene.createDatetime}' pattern='yyyy-MM-dd HH:mm'/></td>
                                        <td>${sampleGene.createPerson}</td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${sampleGene.auditStatus eq 1}">
                                                    已审核
                                                </c:when>
                                                <c:otherwise>
                                                    未审核
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td><a href="<%=path%>/center/3/viewCheckGene.html?id=${sampleGene.id}">基因型</a></td>
                                        <td>
                                            <input type="hidden" name="id" value="${sampleGene.id}">
                                            <input type="hidden" name="sampleId" value="${sampleGene.sampleId}">
                                            <input type="checkbox" name="box" id="box" onClick="chooseOne(this)" <c:if test="${sampleGene.auditStatus eq 1}">disabled="disabled"</c:if> />
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                            <div class="form-group pull-right">
                                <input type="hidden" id="page" name="page" value="${page}">
                                <button class="btn btn-lg btn-info" id="editBtn" type="button"><i class="fa fa-pencil"></i>确认审核</button>
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
    function chooseOne(chk){

        var obj = document.getElementsByName("box");
        
        for (i=0; i<obj.length; i++){

            if (obj[i]!=chk) obj[i].checked = false;

            else obj[i].checked = true;
        }
    }
    $(function(){
        'use strict';

        $("#backBtn").on("click", function(){
            var page = $("#page").val();
            location.href = "<%=path%>/center/3/listGene.html?page=" + page;
        });

        $("#editBtn").on("click",function(){

            var id = "";
            var sampleId = "";

            var obj = document.getElementsByName("box");
            var checkCount = 0;
            for(var j=0; j < obj.length; j++){
                if(obj[j].checked){
                    checkCount ++;
                    id = $("input[name='id']", $("#geneTbody").find("tr")[j]).val();
                    sampleId = $("input[name='sampleId']",$("#geneTbody").find("tr")[j]).val();
                }
            }

            if(checkCount <= 0){
                alert("请选择要审核的检材!");
                return false;
            } else if (checkCount > 1) {
                alert("只能选择一个检材审核！");
                return false;
            }

            $.ajax({
                url: "<%=path%>/center/3/updateAuditStaus.html?id=" + id,
                type: "get",
                dataType: "json",
                success:function(data){
                    if(data.success || data.success == true || data.success == "true") {
                        var page = $("#page").val();
                        location.href = "<%=path%>/center/3/checkGene.html?sampleId=" + sampleId + "&page=" + page;
                    }else {
                        alert("审核失败!");
                    }
                },
                error: function (data) {
                    alert("审核失败!");
                }
            });

        });

    });


</script>
<!-- END JS -->
</body>
</html>


