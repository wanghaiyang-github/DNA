<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/include.jsp" %>
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
                    <a href="<%=path%>/wt/reg/unentrustedList.html" class="active">
                        未委托现堪案件
                    </a>
                </li>

                <li>
                    <a href="<%=path%>/wt/caseinfo/listPending.html" >
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
                    <a href="<%=path%>/wt/message/listIdentify.html">
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
                            <form id="queryForm" action="<%=path%>/wt/reg/unentrustedList.html"
                                  class="form-horizontal tasi-form" method="post">

                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">现勘编号</label>
                                    <div class="col-sm-3">
                                        <input class="form-control" name="investigationNo" id="investigationNo"
                                               type="text" value="${sceneInfoVO.investigationNo}">
                                    </div>
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">勘验地点</label>
                                    <div class="col-sm-3">
                                        <input class="form-control" name="invesPlace" id="invesPlace"
                                               type="text" value="${sceneInfoVO.invesPlace}">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">勘验时间</label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" name="investDateFrom"
                                               readonly="readonly"
                                               type="text"
                                               value="<fmt:formatDate value='${sceneInfoVO.investDateFrom}' pattern='yyyy-MM-dd HH:mm'/>">
                                    </div>
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">至 </label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" name="investDateTo"
                                               readonly="readonly"
                                               type="text"
                                               value="<fmt:formatDate value='${sceneInfoVO.investDateTo}' pattern='yyyy-MM-dd HH:mm'/>">
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
                                    <th>现堪编号</th>
                                    <th>勘验时间</th>
                                    <th>案发时间</th>
                                    <th>案件类别</th>
                                    <th>勘验地点</th>
                                    <th>勘验人</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="caseListTbody">
                                <c:forEach items="${xkList}" var="returnXk" varStatus="s">
                                    <tr>
                                        <td>${s.count}</td>
                                        <td>${returnXk.investigationNo}</td>
                                        <td>
                                            <fmt:formatDate value='${returnXk.investDateFrom}'
                                                            pattern='yyyy-MM-dd HH:mm'/>
                                        </td>
                                        <td>
                                            <fmt:formatDate value='${returnXk.occuDateFrom}'
                                                            pattern='yyyy-MM-dd HH:mm'/>
                                        </td>
                                        <td>${returnXk.caseTypeCn}</td>
                                        <td>${returnXk.invesPlace}</td>
                                        <td>${returnXk.investigators}</td>
                                        <td>
                                            <input type="hidden" name="caseXkNo" value="${returnXk.investigationNo}"/>
                                            <button name="entrustingBtn" id="entrustingBtn" class="btn btn-primary"><i
                                                    class="fa fa fa-plus"></i>
                                                发起委托
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

    $('.form_datetime').datetimepicker({
        format: 'yyyy-mm-dd hh:ii',
        language: 'zh',
        weekStart: 1,
        todayBtn: 1,
        minView: 0,
        autoclose: true,
        todayHighlight: true,
        forceParse: 0,
        showMeridian: 1
    }).on('changeDate', function (ev) {
        $(this).datetimepicker('hide');
    });


    kkpager.generPageHtml({
        pno: ${pageInfo.page},
        //总页码
        total: ${pageInfo.pageCount},
        //总数据条数
        totalRecords: ${pageInfo.allRecordCount},
        //链接前部
        hrefFormer: '<%=path%>/wt/reg/unentrustedList',
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

    $("button[name='entrustingBtn']", "#caseListTbody").on("click", function () {
        var caseXkNo = $("input[name='caseXkNo']", $(this).parent()).val();
        location.href = "<%=path%>/wt/reg/1.html?caseXkNo=" + caseXkNo;
     });

</script>
<!-- END JS -->
</body>
</html>


