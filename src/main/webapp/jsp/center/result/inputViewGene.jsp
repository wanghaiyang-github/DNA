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
                            <form action="<%=path%>/center/3/inputViewGene.html" id="queryForm" class="form-horizontal tasi-form" method="post">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">案件编号</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="entity.caseNo" value="${caseInfoQuery.entity.caseNo}">
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align:center;">案件名称</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="entity.caseName" value="${caseInfoQuery.entity.caseName}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">委托单位</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" id="delegateOrgSelect" name="delegateOrg" value="${caseInfoQuery.delegateOrg}">
                                            <option value="">全部</option>
                                            <c:forEach items="${delegateOrgList}" var="delegateOrg" varStatus="do">
                                                <option value="${delegateOrg.orgCode}" <c:if test="${delegateOrg.orgCode eq caseInfoQuery.delegateOrg}">selected</c:if>>${delegateOrg.orgName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align:center;">受理人</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" name="delegateAcceptor" value="${caseInfoQuery.delegateAcceptor}">
                                            <option value="">全部</option>
                                            <c:forEach items="${labUserList}" var="labUser" varStatus="lu">
                                                <option value="${labUser.userName}" <c:if test="${labUser.userName eq caseInfoQuery.delegateAcceptor}">selected</c:if>>${labUser.userName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">受理时间</label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" name="acceptDateStart" readonly="readonly" type="text" value="<fmt:formatDate value='${caseInfoQuery.acceptDateStart}' pattern='yyyy-MM-dd'/>">
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align:center;">至 </label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" name="acceptDateEnd" readonly="readonly" type="text" value="<fmt:formatDate value='${caseInfoQuery.acceptDateEnd}' pattern='yyyy-MM-dd'/>">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" >委托类型</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" name="entity.entrustmentType" value="${caseInfoQuery.entity.entrustmentType}">
                                            <option value="">全部</option>
                                            <option value="01" <c:if test="${'01' eq caseInfoQuery.entity.entrustmentType}">selected</c:if>>身份不明人员委托</option>
                                            <option value="02" <c:if test="${'02' eq caseInfoQuery.entity.entrustmentType}">selected</c:if>>失踪人口委托</option>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align:center;">现勘编号</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="entity.caseXkNo"
                                               value="${caseInfoQuery.entity.caseXkNo}">
                                    </div>

                                    <div class="col-sm-2">
                                        <input type="hidden" name="page" id="page" value="${pageInfo.page}"/>
                                        <button id="queryBtn" class="btn btn-primary" type="button"><i class="fa fa-search"></i> 查 询</button>
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
                                    <th>案件名称</th>
                                    <th>案件编号</th>
                                    <th>案件性质</th>
                                    <th>委托单位</th>
                                    <th>委托人</th>
                                    <th>受理时间</th>
                                    <th>受理人</th>
                                    <th>录入状态</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="inputViewGeneTbody">
                                <c:forEach items="${caseInfoList}" var="caseInfo" varStatus="s">
                                    <tr>
                                        <td>${s.count}</td>
                                        <td>${caseInfo.entity.caseXkNo}</td>
                                        <td title="${caseInfo.entity.caseName}">
                                            <c:if test="${caseInfo.additionalFlag eq 1}"><span class="color_blue">（<i class="fa fa-tags"></i> 补送） </span></c:if>
                                            <c:if test="${fn:length(caseInfo.entity.caseName) gt 8}">${fn:substring(caseInfo.entity.caseName,0,7)} ...</c:if>
                                            <c:if test="${fn:length(caseInfo.entity.caseName) lt 9}">${caseInfo.entity.caseName}</c:if>
                                        </td>
                                        <td>${caseInfo.entity.caseNo}</td>
                                        <td>${caseInfo.casePropertyName}</td>
                                        <td>${caseInfo.delegateOrgName}</td>
                                        <td>${caseInfo.delegator1Name}、${caseInfo.delegator2Name}</td>
                                        <td>
                                            <fmt:formatDate value='${caseInfo.delegateAcceptDate}' pattern='yyyy-MM-dd'/>
                                        </td>
                                        <td>${caseInfo.delegateAcceptor}</td>
                                        <td>
                                            <c:if test="${caseInfo.checkCount eq 0}">未录入</c:if>
                                            <c:if test="${caseInfo.checkCount gt 0}">已录入</c:if>
                                        </td>
                                        <td>
                                            <input type="hidden" name="caseId" value="${caseInfo.entity.id}"/>
                                            <input type="hidden" name="consignmentId" value="${caseInfo.consignmentId}"/>
                                            <button name="inputViewBtn" class="btn btn-primary"><i class="fa fa-pencil"></i>录入</button>
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

        $("button[name='inputViewBtn']","#inputViewGeneTbody").on('click',function(){
            var consignmentId = $("input[name='consignmentId']", $(this).parent()).val();
            var page = $("#page").val();
            location.href = '<%=path%>/center/3/checkList.html?consignmentId=' + consignmentId + "&page=" + page;
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
            hrefFormer : '<%=path%>/center/3/inputViewGene',
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


