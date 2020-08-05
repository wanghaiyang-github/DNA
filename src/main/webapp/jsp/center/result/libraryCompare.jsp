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
                    <form id="queryForm" action="<%=path%>/center/3/libraryCompare.html" class="form-horizontal tasi-form" method="post">
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">案件名称</label>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" name="entity.caseName" value="${caseInfoQuery.entity.caseName}">
                            </div>
                            <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">案件状态</label>
                            <div class="col-sm-3">
                                <select class="form-control" id="caseStatusSelect" name="entity.caseStatus" value="${caseInfoQuery.entity.caseStatus}">
                                    <option value="">全部</option>
                                    <c:forEach items="${caseStatusList}" var="caseStatus" varStatus="cs">
                                        <option value="${caseStatus.dictCode}" <c:if test="${caseStatus.dictCode eq caseInfoQuery.entity.caseStatus}">selected</c:if>>${caseStatus.dictName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label text-align-right">案件类型</label>
                            <div class="col-sm-3">
                                <select class="form-control" id="caseTypeSelect" name="entity.caseType" value="${caseInfoQuery.entity.caseType}">
                                    <option value="">全部</option>
                                    <c:forEach items="${caseTypeList}" var="caseType" varStatus="cs">
                                        <option value="${caseType.dictCode}" <c:if test="${caseType.dictCode eq caseInfoQuery.entity.caseType}">selected</c:if>>${caseType.dictName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">案件性质</label>
                            <div class="col-sm-3">
                                <select class="form-control" id="casePropertySelect" name="entity.caseProperty" value="${caseInfoQuery.entity.caseProperty}">
                                    <option value="">全部</option>
                                    <c:forEach items="${casePropertyList}" var="caseProperty" varStatus="cs">
                                        <option value="${caseProperty.dictCode}" <c:if test="${caseProperty.dictCode eq caseInfoQuery.entity.caseProperty}">selected</c:if>>${caseProperty.dictName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">受理时间</label>
                            <div class="col-sm-3">
                                <input class="form_datetime form-control" name="acceptDateStart" readonly="readonly" type="text" value="<fmt:formatDate value='${caseInfoQuery.acceptDateStart}' pattern='yyyy-MM-dd'/>">
                            </div>
                            <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">至 </label>
                            <div class="col-sm-3">
                                <input class="form_datetime form-control" name="acceptDateEnd" readonly="readonly" type="text" value="<fmt:formatDate value='${caseInfoQuery.acceptDateEnd}' pattern='yyyy-MM-dd'/>">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">案件编号</label>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" name="entity.caseNo" value="${caseInfoQuery.entity.caseNo}">
                            </div>
                            <label class="col-sm-2 control-label" style="text-align:center;">现勘编号</label>
                            <div class="col-sm-3">
                                <input type="text" class="form-control" name="entity.caseXkNo"
                                       value="${caseInfoQuery.entity.caseXkNo}">
                            </div>

                            <div class="col-sm-2">
                                <input type="hidden" name="page" id="page" value="${pageInfo.page}"/>
                                <button class="btn btn-primary" type="button" id="queryBtn"><i class="fa fa-search"></i> 查 询</button>
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
                        <span class="label label-primary">案件列表</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                    </header>
                    <div class="panel-body">
                        <table class="table table-striped table-advance table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>现勘编号</th>
                                <th>案件编号</th>
                                <th>案件名称</th>
                                <%--<th>案件类型</th>--%>
                                <th>案件性质</th>
                                <th>案件状态</th>
                                <th>受理时间</th>
                                <th>委托单位</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="caseCompareTbody">
                            <c:forEach items="${caseInfoList}" var="caseInfo" varStatus="ci">
                                <tr>
                                    <td>${ci.count}</td>
                                    <td>${caseInfo.entity.caseXkNo}</td>
                                    <td>${caseInfo.entity.caseNo}</td>
                                    <td title="${caseInfo.entity.caseName}">
                                        <c:if test="${fn:length(caseInfo.entity.caseName) gt 8}">
                                            <a style="color: #428bca;" href="<%=path%>/center/caseinfo/editCase.html?consignmentId=${caseInfo.consignmentId}">${fn:substring(caseInfo.entity.caseName,0,7)} ...</a>
                                        </c:if>
                                        <c:if test="${fn:length(caseInfo.entity.caseName) lt 9}">
                                            <a style="color: #428bca;" href="<%=path%>/center/caseinfo/editCase.html?consignmentId=${caseInfo.consignmentId}">${caseInfo.entity.caseName}</a>
                                        </c:if>
                                    </td>
                                    <%--<td>${caseInfo.caseTypeName}</td>--%>
                                    <td>${caseInfo.casePropertyName}</td>
                                    <td>${caseInfo.caseStatusName}</td>
                                    <td>
                                        <fmt:formatDate value='${caseInfo.delegateAcceptDate}' pattern='yyyy-MM-dd'/>
                                    </td>
                                    <td>${caseInfo.delegateOrgName}</td>
                                    <td>
                                        <input type="hidden" name="caseId" value="${caseInfo.entity.id}"/>
                                        <button class="btn btn-success btn-sm" name="compareBtn"><i class="fa fa-exchange"></i> 入DNA库</button>
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
    $(function(){
        'use strict';

       $("button[name='compareBtn']","#caseCompareTbody").on("click", function(){
            var caseId = $("input[name='caseId']", $(this).parent()).val();
            var url = "<%=path%>/center/3/enterDnaCompare.html?caseId="
                            +caseId + "&matchLimit=0";
            location.href=url;
        });

        $('.form_datetime').datetimepicker({
            format: 'yyyy-mm-dd',
            language:  'zh',
            weekStart: 1,
            todayBtn:  1,
            minView: "month",
            autoclose: true,
            todayHighlight: true,
            forceParse: 0,
            showMeridian: 1
        }).on('changeDate', function (ev) {
            $(this).datetimepicker('hide');
        });

        kkpager.generPageHtml({
            pno : ${pageInfo.page},
            //总页码
            total : ${pageInfo.pageCount},
            //总数据条数
            totalRecords : ${pageInfo.allRecordCount},
            //链接前部
            hrefFormer : '<%=path%>/center/3/libraryCompare',
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

        $("#queryBtn").on("click", function(){
            $("#page").val(1);
            $('#queryForm').submit();
        });

    });
</script>
<!-- END JS -->
</body>
</html>
