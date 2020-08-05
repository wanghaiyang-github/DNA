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
                                        <input type="text" class="form-control" name="caseNo"
                                               value="${limsCaseInformation.caseNo}">
                                    </div>
                                    <label class="col-sm-2 col-sm-2 control-label"
                                           style="text-align:center;">案件名称</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="caseName"
                                               value="${limsCaseInformation.caseName}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">委托单位</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" id="entrustCompanyCode" name="entrustCompanyCode"
                                                value="${limsCaseInformation.entrustCompanyCode}">
                                            <option value="">全部</option>
                                            <c:forEach items="${delegateOrgList}" var="delegateOrg" varStatus="do">
                                                <option value="${delegateOrg.orgCode}"
                                                        <c:if test="${delegateOrg.orgCode eq limsCaseInformation.entrustCompanyCode}">selected</c:if>>${delegateOrg.orgName}</option>
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
                                               name="entrustmentDateStart"
                                               value="<fmt:formatDate value='${limsCaseInformation.entrustmentDateStart}' pattern='yyyy-MM-dd'/>">
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align:center;">至 </label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" readonly="readonly" type="text"
                                               name="entrustmentDateEnd"
                                               value="<fmt:formatDate value='${limsCaseInformation.entrustmentDateEnd}' pattern='yyyy-MM-dd'/>">
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
                            <span class="label label-primary">提取记录列表</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead id="extractRecordListTable">
                                <tr>
                                    <th>序号</th>
                                    <th>案件编号</th>
                                    <th>案件名称</th>
                                    <th>委托编号</th>
                                    <th>委托单位</th>
                                    <th>委托时间</th>
                                    <th>提取状态</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="extractListTable">
                                <c:forEach items="${limsCaseInformationList}" var="caseInformation" varStatus="s">
                                    <tr>
                                        <td>${s.count}</td>
                                        <td>${caseInformation.caseNo}</td>
                                        <td>${caseInformation.caseName}</td>
                                        <td>${caseInformation.entrustingSerial}</td>
                                        <td>${caseInformation.entrustCompany}</td>
                                        <td>
                                            <fmt:formatDate value='${caseInformation.entrustmentDatetime}'
                                                            pattern='yyyy-MM-dd'/>
                                        </td>
                                        <td>
                                            <c:if test="${caseInformation.extractionState eq 0}"> 未提取</c:if>
                                            <c:if test="${caseInformation.extractionState gt 0}"> 已提取${caseInformation.extractionState}次</c:if>
                                        </td>
                                        <td>
                                            <input type="hidden" name="caseInformationId"
                                                   value="${caseInformation.id}"/>
                                            <input type="hidden" name="entrustmentType"
                                                   value="${caseInformation.entrustmentType}"/>
                                            <button name="acceptBtn" class="btn btn-primary btn-sm"><i
                                                    class="fa fa-plus"></i> 添加
                                            </button>
                                            <c:if test="${caseInformation.extractionState gt 0}">
                                                <button class="btn btn-success btn-sm" name="viewBtn"><i
                                                        class="fa fa-list"></i> 查看
                                                </button>
                                                <button name="delBtn" class="btn btn-danger btn-sm"><i
                                                        class="fa fa-pencil"></i> 删除
                                                </button>
                                            </c:if>
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

        $("button[name='acceptBtn']").on('click', function () {
            var caseInformationId = $("input[name='caseInformationId']", $(this).parent()).val();
            location.href = '<%=path%>/center/caseInformation/addExtraction.html?caseInformationId=' + caseInformationId;
        });

        $("button[name='viewBtn']", "#extractListTable").on("click",function(){
            var caseInformationId = $("input[name='caseInformationId']", $(this).parent()).val();
            location.href='<%=path%>/center/caseInformation/editExtractRecord.html?caseInformationId='+caseInformationId;
        });


        $("button[name='delBtn']", "#extractListTable").on("click",function(){
            if (!confirm("是否删除本次实验记录！"))
                return;
            delRecord(this);
        });

        function delRecord(obj) {
            var caseInformationId = $("input[name='caseInformationId']", $(obj).parent()).val();
            $.ajax({
                url:'<%=path%>/center/caseInformation/deleteByCaseId.html?caseInformationId='+caseInformationId,
                type:"get",
                dataType:"json",
                success:function(data){
                    if(data.success || data.success == true || data.success == "true") {
                        var caseId = $("#caseId").val();
                        location.href='<%=path%>/center/caseInformation/unidentifiedExtractionList.html?caseId=' + caseId;
                    }
                }
            });
        }

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


