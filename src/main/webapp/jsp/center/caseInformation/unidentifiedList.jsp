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
                            <form id="queryForm" action="<%=path%>/center/caseInformation/unidentifiedList.html"
                                  class="form-horizontal tasi-form" method="post">
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件编号</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="entity.caseNo"
                                               value="${caseInfoQuery.entity.caseNo}">
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align:center;">案件名称</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="entity.caseName"
                                               value="${caseInfoQuery.entity.caseName}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">委托单位</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" id="delegateOrgSelect" name="delegateOrg"
                                                value="${caseInfoQuery.delegateOrg}">
                                            <option value="">全部</option>
                                            <c:forEach items="${delegateOrgList}" var="delegateOrg" varStatus="do">
                                                <option value="${delegateOrg.orgCode}"
                                                        <c:if test="${delegateOrg.orgCode eq caseInfoQuery.delegateOrg}">selected</c:if>>${delegateOrg.orgName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align:center;">委托编号</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="entrustingSerial"
                                               value="${limsCaseInformation.entrustingSerial}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">委托时间</label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" readonly="readonly" type="text"
                                               name="delegateDatetimeStart"
                                               value="<fmt:formatDate value='${caseInfoQuery.delegateDatetimeStart}' pattern='yyyy-MM-dd'/>">
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align:center;">至 </label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" readonly="readonly" type="text"
                                               name="delegateDatetimeEnd"
                                               value="<fmt:formatDate value='${caseInfoQuery.delegateDatetimeEnd}' pattern='yyyy-MM-dd'/>">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">委托类型</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" id="entity.entrustmentType"
                                                name="entity.entrustmentType"
                                                value="${caseInfoQuery.entity.entrustmentType}">
                                            <option value="01"
                                                    <c:if test="${caseInfoQuery.entity.entrustmentType eq '01'}">selected</c:if>>
                                                身份不明人员
                                            </option>
                                            <option value="02"
                                                    <c:if test="${caseInfoQuery.entity.entrustmentType eq '02'}">selected</c:if>>
                                                失踪人口
                                            </option>
                                        </select>
                                    </div>
                                    <div class="col-sm-2">
                                        <input type="hidden" name="page" id="page" value="${pageInfo.page}"/>
                                        <button class="btn btn-primary" type="button" id="queryBtn"><i
                                                class="fa fa-search"></i> 查 询
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
                            <span class="label label-primary">委托列表</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>案件编号</th>
                                    <th>案件名称</th>
                                    <th>委托编号</th>
                                    <th>委托单位</th>
                                    <th>委托时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="acceptedListTbody">
                                <c:forEach items="${caseInfoList}" var="caseInfo" varStatus="s">
                                    <tr>
                                        <td>${s.count}</td>
                                        <td>${caseInfo.entity.caseNo}</td>
                                        <td>${caseInfo.entity.caseName}</td>
                                        <td>${caseInfo.entrustingSerial}</td>
                                        <td>${caseInfo.delegateOrgName}</td>
                                        <td>
                                            <fmt:formatDate value='${caseInfo.delegateDatetime}' pattern='yyyy-MM-dd'/>
                                        </td>
                                        <td>
                                            <input type="hidden" name="caseId" value="${caseInfo.entity.id}"/>
                                            <input type="hidden" name="additionalFlag"
                                                   value="${caseInfo.additionalFlag}">
                                            <input type="hidden" name="consignmentId"
                                                   value="${caseInfo.consignmentId}"/>
                                            <button name="acceptBtn" class="btn btn-primary btn-sm"><i
                                                    class="fa fa-check"></i> 编辑
                                            </button>
                                            <button name="delBtn" class="btn btn-danger btn-sm"><i
                                                    class="fa fa-pencil"></i> 删除
                                            </button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                            <div class="space15" style="height: 5px;"></div>
                            <div class="col-lg-6 pull-right">

                            </div>

                        </div>
                    </section>
                </div>
            </div>
            <!-- END ROW  -->
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
    $(function () {
        'use strict';

        $("button[name='acceptBtn']", "#acceptedListTbody").on('click', function () {
            var caseId = $("input[name='caseId']", $(this).parent()).val();
            location.href = '<%=path%>/center/caseInformation/editCaseInformation.html?caseId=' + caseId;
        });

        $("button[name='delBtn']", "#acceptedListTbody").on('click', function () {
            if (confirm("确认删除吗?")) {
                var caseId = $("input[name='caseId']", $(this).parent()).val();
                var consignmentId = $("input[name='consignmentId']", $(this).parent()).val();
                var additionalFlag = $("input[name='additionalFlag']", $(this).parent()).val();
                $.ajax({
                    url: "<%=path%>/center/caseinfo/delCaseInfoByCaseId.html?caseId=" + caseId + "&consignmentId=" + consignmentId + "&additionalFlag=" + additionalFlag,
                    type: "get",
                    dataType: "json",
                    success: function (data) {
                        if (data.success || data.success == true || data.success == "true") {
                            location.href = "<%=path%>/center/caseInformation/unidentifiedList.html";
                        }
                    }
                });
            }
        })

        $('.form_datetime').datetimepicker({
            format: 'yyyy-mm-dd',
            language: 'zh',
            weekStart: 1,
            todayBtn: 1,
            minView: "month",
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
            hrefFormer: '<%=path%>/center/caseInformation/unidentifiedList',
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

        $("#queryBtn").on("click", function () {
            $("#page").val(1);
            $('#queryForm').submit();
        });
    });
</script>
<!-- END JS -->
</body>
</html>


