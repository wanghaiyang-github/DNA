<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <!-- BEGIN META -->
    <meta charset="utf-8">
    <!-- END META -->

    <!-- BEGIN SHORTCUT ICON -->
    <link rel="shortcut icon" href="<%=path%>/img/dna.ico">
    <!-- END SHORTCUT ICON -->
    <title>
        博安智联LIMS - ${loginTitleName}
    </title>
    <!-- BEGIN STYLESHEET-->
    <%@ include file="../../common/link.jsp" %>
    <!-- END STYLESHEET-->
</head>
<body>
<!-- BEGIN SECTION -->
<section id="container">
    <!-- BEGIN HEADER -->
    <jsp:include page="../hearder.jsp"/>
    <!-- END HEADER -->

    <jsp:include page="../updateDelegatePassword.jsp"/>
    <!-- BEGIN SIDEBAR -->
    <aside>
        <div id="sidebar" class="nav-collapse">
            <ul class="sidebar-menu" id="nav-accordion">
                <%--<li >--%>
                <%--<a href="<%=path%>/wt/index.html">--%>
                <%--<i class="fa fa-dashboard">--%>
                <%--</i>--%>
                <%--<span>--%>
                <%--主 页--%>
                <%--</span>--%>
                <%--</a>--%>
                <%--</li>--%>

                <li>
                    <a href="<%=path%>/wt/reg/1.html">
                        案件委托登记
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/reg/2.html">
                        身份不明人员委托登记
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/reg/3.html">
                        失踪人口委托登记
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/caseinfo/listPending.html">
                        未受理
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/caseinfo/listAccepted.html">
                        已受理
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/caseinfo/listRefused.html">
                        已退案
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/message/listIdentify.html" class="active">
                        鉴定领取通知
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/consignment/listDelegator.html">
                        委托人管理
                    </a>
                </li>
            </ul>
        </div>
    </aside>
    <!-- END SIDEBAR -->
    <!-- BEGIN MAIN CONTENT -->


    <section id="main-content" class="main-content-delegate">
        <!-- BEGIN WRAPPER  -->
        <section class="wrapper wrapper-delegate">
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
                            <form id="queryForm" action="<%=path%>/wt/message/listIdentify.html"
                                  class="form-horizontal tasi-form" method="post">
                                <div class="form-group">
                                    <label class="col-sm-1 col-sm-1 control-label">案件名称</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="entity.caseName"
                                               value="${query.entity.caseName}">
                                    </div>
                                    <label class="col-sm-1 col-sm-1 control-label"
                                           style="text-align:center;">案件性质</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" name="entity.caseProperty" id="caseProperty"
                                                value="${query.entity.caseProperty}">
                                            <option value="">全部...</option>
                                            <c:forEach items="${casePropertyList}" var="list" varStatus="s">
                                                <option value="${list.dictCode}" <c:if test="${list.dictCode eq query.entity.caseProperty}">selected</c:if> >${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-1 col-sm-1 control-label" style="text-align:center;">委托人</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="delegatorName"
                                               value="${query.delegatorName}">
                                    </div>

                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 col-sm-1 control-label"
                                           >案件编号</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="entity.caseNo"
                                               value="${query.entity.caseNo}">
                                    </div>
                                    <label class="col-sm-1 col-sm-1 control-label" style="text-align:center;">委托时间</label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" name="delegateDatetimeStart"
                                               readonly="readonly"
                                               type="text"
                                               value="<fmt:formatDate value='${query.delegateDatetimeStart}' pattern='yyyy-MM-dd'/>">
                                    </div>
                                    <label class="col-sm-1 col-sm-1 control-label" style="text-align:center;">至 </label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" name="delegateDatetimeEnd"
                                               readonly="readonly"
                                               type="text"
                                               value="<fmt:formatDate value='${query.delegateDatetimeEnd}' pattern='yyyy-MM-dd'/>">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 col-sm-1 control-label">现勘编号</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="entity.caseXkNo"
                                               value="${query.entity.caseXkNo}">
                                    </div>
                                    <div class="col-sm-2">
                                        <input type="hidden" name="page" id="page" value="${pageInfo.page}"/>
                                        <button class="btn btn-primary" id="submitBtn"><i class="fa fa-search"></i> 查 询
                                        </button>
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
                            <span class="label label-primary">通知列表</span>
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
                                    <th>案件名称</th>
                                    <th>案件编号</th>
                                    <th>案件类型</th>
                                    <th>案件性质</th>
                                    <th>委托时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="receiveTBody">
                                <c:forEach items="${caseInfoList}" var="caseInfo" varStatus="ci">
                                    <tr>
                                        <td>${ci.count}</td>
                                        <td>${caseInfo.entity.caseXkNo}</td>
                                        <td title="${caseInfo.entity.caseName}">
                                            <c:if test="${fn:length(caseInfo.entity.caseName) gt 10}">
                                                ${fn:substring(caseInfo.entity.caseName,0,9)} ...
                                            </c:if>
                                            <c:if test="${fn:length(caseInfo.entity.caseName) lt 11}">
                                                ${caseInfo.entity.caseName}
                                            </c:if>
                                        </td>
                                        <td>${caseInfo.entity.caseNo}</td>
                                        <td>${caseInfo.caseTypeName}</td>
                                        <td>${caseInfo.casePropertyName}</td>
                                        <td><fmt:formatDate value='${caseInfo.delegateAcceptDate}'
                                                            pattern='yyyy-MM-dd'/></td>
                                        <td>
                                            <input type="hidden" name="caseId" value="${caseInfo.entity.id}"/>
                                            <button class="btn btn-primary btn-sm" name="printReceiveFrom"><i
                                                    class="fa fa-print"></i> 打印领取单
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
            <!-- END ROW  -->
        </section>
        <!-- END WRAPPER  -->
    </section>
    <!-- END MAIN CONTENT -->
    <!-- BEGIN FOOTER -->
    <%--<footer class="site-footer">--%>
    <%--<div class="text-center">--%>
    <%--Copyright © 2016 北京博安智联科技有限公司--%>
    <%--</div>--%>
    <%--</footer>--%>
    <!-- END  FOOTER -->
</section>
<!-- END SECTION -->
<!-- BEGIN JS -->
<%@ include file="../../common/script.jsp" %>
<script>
    $(function () {
        'use strict';

        $('.form_datetime').datetimepicker({
            format: 'yyyy-mm-dd',
            language: 'zh',
            minView: "month",
            weekStart: 1,
            todayBtn: 1,
            autoclose: true,
            todayHighlight: true,
            forceParse: 0,
            showMeridian: 1
        }).on('changeDate', function (ev) {
            $(this).datetimepicker('hide');
        });

        $("button[name='printReceiveFrom']", "#receiveTBody").on("click", function () {
            var caseId = $("input[name='caseId']", $(this).parent()).val();
            location.href = "<%=path%>/wt/message/printReceiveFrom.html?caseId=" + caseId;
        });

        kkpager.generPageHtml({
            pno: ${pageInfo.page},
            //总页码
            total: ${pageInfo.pageCount},
            //总数据条数
            totalRecords: ${pageInfo.allRecordCount},
            //链接前部
            hrefFormer: '<%=path%>/wt/message/listIdentify',
            //链接尾部
            hrefLatter: '.html',
            getLink: function (page) {
                return this.hrefFormer + this.hrefLatter + "?" + "page=" + page + "&" + $("#queryForm").serialize();
            }
            , lang: {
                firstPageText: '首页',
                firstPageTipText: '首页',
                lastPageText: '尾页',
                lastPageTipText: '尾页',
                prePageText: '上一页',
                prePageTipText: '上一页',
                nextPageText: '下一页',
                nextPageTipText: '下一页',
                totalPageBeforeText: '共',
                totalPageAfterText: '页',
                currPageBeforeText: '当前第',
                currPageAfterText: '页',
                totalInfoSplitStr: '/',
                totalRecordsBeforeText: '共',
                totalRecordsAfterText: '条数据',
                gopageBeforeText: '&nbsp;转到',
                gopageButtonOkText: '确定',
                gopageAfterText: '页',
                buttonTipBeforeText: '第',
                buttonTipAfterText: '页'
            }
        });

        $("#submitBtn").on("click", function () {
            $("#page").val(1);
            $('#queryForm').submit();
        });

    });

</script>
<!-- END JS -->
</body>
</html>


