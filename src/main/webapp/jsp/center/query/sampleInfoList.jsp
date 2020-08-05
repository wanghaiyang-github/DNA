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
                            <form action="<%=path%>/center/query/sampleInfoList.html" id="queryForm" class="form-horizontal tasi-form" method="post">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">样本编号</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="entity.sampleNo" value="${query.entity.sampleNo}">
                                    </div>
                                    <label class="col-sm-1 control-label">样本名称</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="entity.sampleName" value="${query.entity.sampleName}">
                                    </div>
                                    <label class="col-sm-1 control-label" style="text-align:center;">样本类型</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" id="entity.sampleType" name="entity.sampleType" value="">
                                            <option value="">全部</option>
                                            <c:forEach items="${samplelTypeList}" var="sampleType" varStatus="cs">
                                                <option value="${sampleType.dictCode}" <c:if test="${sampleType.dictCode eq query.entity.sampleType}">selected</c:if>>${sampleType.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">受理时间</label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" name="acceptDatetimeStart" readonly="readonly" type="text" value="<fmt:formatDate value='${query.acceptDatetimeStart}' pattern='yyyy-MM-dd'/>">
                                    </div>
                                    <label class="col-sm-1 control-label" style="text-align:center;">至 </label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" name="acceptDatetimeEnd" readonly="readonly" type="text" value="<fmt:formatDate value='${query.acceptDatetimeEnd}' pattern='yyyy-MM-dd'/>">
                                    </div>

                                    <label class="col-sm-1 control-label" style="text-align:center;">受理人</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" name="entity.acceptPerson" value="${query.entity.acceptPerson}">
                                            <option value="">全部</option>
                                            <c:forEach items="${labUserList}" var="labUser" varStatus="lu">
                                                <option value="${labUser.userName}" <c:if test="${labUser.userName eq query.entity.acceptPerson}">selected</c:if>>${labUser.userName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>

                                <div class="form-group" style="float: right">
                                    <div class="col-sm-2" style="padding-right: 30px;">
                                        <input type="hidden" name="page" id="page" value="${pageInfo.page}"/>
                                        <button id="queryBtn" class="btn btn-primary" type="button"><i class="fa fa-search"></i> 查 询</button>
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
                            <span class="label label-primary">信息列表</span>
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
                                    <th>案件性质</th>
                                    <th>样本编号</th>
                                    <th>样本名称</th>
                                    <th>样本类型</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="samplefoListTbody">
                                    <c:forEach items="${sampleInfoList}" var="sampleInfo" varStatus="s">
                                        <tr>
                                            <td>${s.count}</td>
                                            <td>${sampleInfo.caseNo}</td>
                                            <td>${sampleInfo.caseName}</td>
                                            <td>${sampleInfo.caseProperty}</td>
                                            <td>${sampleInfo.entity.sampleNo}</td>
                                            <td>${sampleInfo.entity.sampleName}</td>
                                            <td>${sampleInfo.sampleTypeName}</td>
                                            <td>
                                                <input type="hidden" name="caseId" id="caseId" value="${sampleInfo.entity.caseId}">
                                                <input type="hidden"  name="consignmentId" value="${sampleInfo.entity.consignmentId}"  >
                                                <button class="btn btn-primary btn-xs" name="queryDetails">查看详情</button>
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

        $("button[name='queryDetails']","#samplefoListTbody").on("click",function(){
            var consignmentId=$("input[name='consignmentId']", $(this).parent()).val();
            location.href="<%=path%>/center/query/detailsSample.html?consignmentId=" + consignmentId+"&sampleFlag="+0;
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
            hrefFormer : '<%=path%>/center/query/sampleInfoList',
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


