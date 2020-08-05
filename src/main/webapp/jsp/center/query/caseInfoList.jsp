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
                            <form action="<%=path%>/center/query/caseInfoList.html" id="queryForm" class="form-horizontal tasi-form" method="post">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">案件编号</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="entity.caseNo" value="${caseInfoQuery.entity.caseNo}">
                                    </div>
                                    <label class="col-sm-1 control-label" style="text-align:center;">案件名称</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="entity.caseName" value="${caseInfoQuery.entity.caseName}">
                                    </div>
                                    <label class="col-sm-1 control-label" style="text-align:center;">案件状态</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" id="caseStatusSelect" name="entity.caseStatus" value="">
                                            <option value="">全部</option>
                                            <c:forEach items="${caseStatusList}" var="caseStatus" varStatus="cs">
                                                <option value="${caseStatus.dictCode}" <c:if test="${caseStatus.dictCode eq caseInfoQuery.entity.caseStatus}">selected</c:if>>${caseStatus.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label text-align-right">案件类型</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" id="caseTypeSelect" name="entity.caseType" value="">
                                            <option value="">全部</option>
                                            <c:forEach items="${caseTypeList}" var="caseType" varStatus="cs">
                                                <option value="${caseType.dictCode}" <c:if test="${caseType.dictCode eq caseInfoQuery.entity.caseType}">selected</c:if>>${caseType.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-1 control-label" style="text-align:center;">案件性质</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" id="casePropertySelect" name="entity.caseProperty" value="">
                                            <option value="">全部</option>
                                            <c:forEach items="${casePropertyList}" var="caseProperty" varStatus="cs">
                                                <option value="${caseProperty.dictCode}" <c:if test="${caseProperty.dictCode eq caseInfoQuery.entity.caseProperty}">selected</c:if>>${caseProperty.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-1 control-label" style="text-align:center;">委托单位</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" id="delegateOrgSelect" name="delegateOrg" value="${caseInfoQuery.delegateOrg}">
                                            <option value="">全部</option>
                                            <c:forEach items="${delegateOrgList}" var="delegateOrg" varStatus="do">
                                                <option value="${delegateOrg.orgCode}" <c:if test="${delegateOrg.orgCode eq caseInfoQuery.delegateOrg}">selected</c:if>>${delegateOrg.orgName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">受理时间</label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" name="acceptDateStart" readonly="readonly" type="text" value="<fmt:formatDate value='${caseInfoQuery.acceptDateStart}' pattern='yyyy-MM-dd'/>">
                                    </div>
                                    <label class="col-sm-1 control-label" style="text-align:center;">至 </label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" name="acceptDateEnd" readonly="readonly" type="text" value="<fmt:formatDate value='${caseInfoQuery.acceptDateEnd}' pattern='yyyy-MM-dd'/>">
                                    </div>
                                    <label class="col-sm-1 control-label" style="text-align:center;">受理人</label>
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
                                    <label class="col-sm-1 control-label">委托时间</label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" readonly="readonly" type="text" name="delegateDatetimeStart" value="<fmt:formatDate value='${caseInfoQuery.delegateDatetimeStart}' pattern='yyyy-MM-dd'/>">
                                    </div>
                                    <label class="col-sm-1 control-label" style="text-align:center;">至 </label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" readonly="readonly" type="text" name="delegateDatetimeEnd" value="<fmt:formatDate value='${caseInfoQuery.delegateDatetimeEnd}' pattern='yyyy-MM-dd'/>">
                                    </div>
                                    <label class="col-sm-1 control-label" style="text-align:center;">委托人</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="delegatorName" value="${caseInfoQuery.delegatorName}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">案发时间</label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" name="caseDateStart" readonly="readonly" type="text" value="<fmt:formatDate value='${caseInfoQuery.caseDateStart}' pattern='yyyy-MM-dd'/>">
                                    </div>
                                    <label class="col-sm-1 control-label" style="text-align:center;">至 </label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" name="caseDateEnd" readonly="readonly" type="text" value="<fmt:formatDate value='${caseInfoQuery.caseDateEnd}' pattern='yyyy-MM-dd'/>">
                                    </div>

                                    <label class="col-sm-1 control-label" style="text-align:center;">是否删除</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" name="entity.deleteFlag" value="${caseInfoQuery.entity.deleteFlag}">
                                            <option value="">全部</option>
                                            <option value="0" <c:if test="${caseInfoQuery.entity.deleteFlag eq 0}">selected</c:if>>未删除</option>
                                            <option value="1" <c:if test="${caseInfoQuery.entity.deleteFlag eq 1}">selected</c:if>>已删除</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label" >是否补送</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" name="additionalFlag" value="${caseInfoQuery.additionalFlag}">
                                            <option value="">全部</option>
                                            <option value="0" <c:if test="${caseInfoQuery.additionalFlag eq 0}">selected</c:if>>未补送</option>
                                            <option value="1" <c:if test="${caseInfoQuery.additionalFlag eq 1}">selected</c:if>>已补送</option>
                                        </select>
                                    </div>

                                    <label class="col-sm-1 control-label" style="text-align:center;">现勘编号</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="entity.caseXkNo"
                                               value="${caseInfoQuery.entity.caseXkNo}">
                                    </div>

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
                                </tr>
                                </thead>
                                <tbody id="caseInfoListTbody">
                                    <c:forEach items="${caseInfoList}" var="caseInfo" varStatus="s">
                                        <tr>
                                            <td>${s.count}</td>
                                            <td>${caseInfo.entity.caseXkNo}</td>
                                            <td title="${caseInfo.entity.caseName}">
                                                <c:if test="${caseInfo.additionalFlag eq 1}"><span class="color_blue">（<i class="fa fa-tags"></i> 补送） </span></c:if>
                                                <c:if test="${fn:length(caseInfo.entity.caseName) gt 8}">
                                                    <a href="<%=path%>/center/caseinfo/editCase.html?consignmentId=${caseInfo.consignmentId}<c:if test="${caseInfo.entity.deleteFlag eq 1}">&fromUrl=query</c:if>">${fn:substring(caseInfo.entity.caseName,0,7)} ...</a>
                                                </c:if>
                                                <c:if test="${fn:length(caseInfo.entity.caseName) lt 9}">
                                                    <a href="<%=path%>/center/caseinfo/editCase.html?consignmentId=${caseInfo.consignmentId}<c:if test="${caseInfo.entity.deleteFlag eq 1}">&fromUrl=query</c:if>">${caseInfo.entity.caseName}</a>
                                                </c:if>
                                            </td>
                                            <td>${caseInfo.entity.caseNo}</td>
                                            <td>${caseInfo.casePropertyName}</td>
                                            <td title="${caseInfo.delegateOrgName}">
                                                <c:if test="${fn:length(caseInfo.delegateOrgName) gt 9}">${fn:substring(caseInfo.delegateOrgName,0,8)} ...</c:if>
                                                <c:if test="${fn:length(caseInfo.delegateOrgName) lt 10}">${caseInfo.delegateOrgName}</c:if>
                                            </td>
                                            <td>${caseInfo.delegator1Name}、${caseInfo.delegator2Name}</td>
                                            <td>
                                                <fmt:formatDate value='${caseInfo.delegateAcceptDate}' pattern='yyyy-MM-dd'/>
                                            </td>
                                            <td>${caseInfo.delegateAcceptor}</td>
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

        $("button[name='editBtn']","#acceptedListTbody").on('click',function(){
            var consignmentId = $("input[name='consignmentId']", $(this).parent()).val();
            location.href = '<%=path%>/center/caseinfo/editCase.html?consignmentId='+consignmentId+"&fromUrl=acceptedList";
        });

        $("button[name='delBtn']","#acceptedListTbody").on('click', function () {

            if (confirm("确认删除吗?")){
                var caseId = $("input[name='caseId']",$(this).parent()).val();
                $.ajax({
                    url:"<%=path%>/center/caseinfo/delCaseInfoByCaseId.html?caseId=" + caseId,
                    type:"get",
                    dataType:"json",
                    success:function(data){
                        if(data.success || data.success == true || data.success == "true") {
                            location.href = "<%=path%>/center/caseinfo/acceptedList.html";
                        }
                    }
                });
            }
        })

        $("button[name='downloadDocBtn']","#acceptedListTbody").on("click",function(){
            var consignmentId=$("input[name='consignmentId']", $(this).parent()).val();
            location.href="<%=path%>/center/caseinfo/acceptDoc.html?consignmentId=" + consignmentId;
        });

        $("button[name='downloadSampleExchangeDocBtn']", "#acceptedListTbody").on("click",function(){
            var consignmentId=$("input[name='consignmentId']", $(this).parent()).val();
            location.href="<%=path%>/center/caseinfo/sampleExchangeDoc.html?consignmentId=" + consignmentId;
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
            hrefFormer : '<%=path%>/center/query/caseInfoList',
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


