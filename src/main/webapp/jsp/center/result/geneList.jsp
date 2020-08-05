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
                            <form id="queryForm" action="<%=path%>/center/3/listGene.html" class="form-horizontal tasi-form" method="post">
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件编号</label>
                                    <div class="col-sm-2">
                                        <input type="text" class="form-control" name="caseNo" id="caseNo" value="${query.caseNo}">
                                    </div>
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">检材编号</label>
                                    <div class="col-sm-2">
                                        <input type="text" class="form-control" name="entity.sampleNo" id="entity.sampleNo" value="${query.entity.sampleNo}">
                                    </div>
                                    <div class="col-sm-2 text-align-center">
                                        <input type="hidden" name="page" id="page" value="${pageInfo.page}"/>
                                        <button class="btn btn-primary" type="button" id="submitBtn">查询</button>
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
                            <span class="label label-primary">结果列表</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>检材编号</th>
                                    <th>检材名称</th>
                                    <th>案件编号</th>
                                    <th>案件名称</th>
                                    <th>导入结果个数</th>
                                    <th>入库状态</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="samplesTbody">
                                <c:forEach items="${limsSampleGeneVOList}" var="sampleGene" varStatus="s">
                                    <tr>
                                        <td>${s.count}</td>
                                        <td>${sampleGene.entity.sampleNo}</td>
                                        <td title="${sampleGene.sampleName}">
                                            <c:if test="${fn:length(sampleGene.sampleName) gt 39}">
                                                ${fn:substring(sampleGene.sampleName,0,38)} ...
                                            </c:if>
                                            <c:if test="${fn:length(sampleGene.sampleName) lt 40}">
                                                ${sampleGene.sampleName}
                                            </c:if>
                                        </td>
                                        <td>${sampleGene.caseNo}</td>
                                        <td>${sampleGene.caseName}</td>
                                        <td>
                                            <c:if test="${sampleGene.entity.enterCount eq 0}">
                                                未录入
                                            </c:if>
                                            <c:if test="${sampleGene.entity.enterCount gt 0}">
                                                已录入 ${sampleGene.entity.enterCount} 次
                                            </c:if>
                                        </td>
                                        <td>
                                            <c:choose>
                                                <c:when test="${sampleGene.entity.auditStatus eq 1}">
                                                    已审核
                                                </c:when>
                                                <c:otherwise>
                                                    未审核
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <button name="editBtn" class="btn btn-primary btn-xs" onclick="editBtn(${sampleGene.entity.sampleId})"><i class="fa fa-list-alt"></i> 复核</button>
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

            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <div class="panel-body">
                            <div class="pagin"><br/>
                                <div id="kkpager" style="margin-right:30px;margin-top:-22px"></div>
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

    function editBtn(sampleId){
        var page = $("#page").val();
        location.href = "<%=path%>/center/3/checkGene.html?sampleId=" + sampleId + "&page=" + page;
    }

    $(function() {
        'use strict';

        $("#submitBtn").on("click",function(){
            $("#page").val(1);
            $('#queryForm').submit();
        });

        kkpager.generPageHtml({
            pno : ${pageInfo.page},
            //总页码
            total : ${pageInfo.pageCount},
            //总数据条数
            totalRecords : ${pageInfo.allRecordCount},
            //链接前部
            hrefFormer : '<%=path%>/center/3/listGene',
            //链接尾部
            hrefLatter : '.html',
            getLink : function(page){
                return this.hrefFormer + this.hrefLatter + "?" + "page=" + page + "&" + $("#queryForm").serialize();
            }
            ,lang				: {
                firstPageText			: '首页',
                firstPageTipText		: '首页',
                lastPageText			: '尾页',
                lastPageTipText			: '尾页',
                prePageText				: '上一页',
                prePageTipText			: '上一页',
                nextPageText			: '下一页',
                nextPageTipText			: '下一页',
                totalPageBeforeText		: '共',
                totalPageAfterText		: '页',
                currPageBeforeText		: '当前第',
                currPageAfterText		: '页',
                totalInfoSplitStr		: '/',
                totalRecordsBeforeText	: '共',
                totalRecordsAfterText	: '条数据',
                gopageBeforeText		: '&nbsp;转到',
                gopageButtonOkText		: '确定',
                gopageAfterText			: '页',
                buttonTipBeforeText		: '第',
                buttonTipAfterText		: '页'
            }
        });

    });
</script>
<!-- END JS -->
</body>
</html>
