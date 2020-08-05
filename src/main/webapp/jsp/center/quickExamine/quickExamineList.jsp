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
                            <form id="queryForm" action="<%=path%>/center/examine/quickExamine.html" class="form-horizontal tasi-form" method="post">
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
                                    <label class="col-sm-2 control-label">委托类型</label>
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
                                <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th>现勘编号</th>
                                        <th>案件名称</th>
                                        <th>案件编号</th>
                                        <th>案件性质</th>
                                        <th>委托单位</th>
                                        <th>受理时间</th>
                                        <th>受理人</th>
                                        <th>检验状态</th>
                                        <th>检验记录</th>
                                    </tr>
                                </thead>
                                <tbody id="quickExamineListTable">
                                    <c:forEach items="${caseInfoVOList}" var="caseInfo" varStatus="s">
                                        <tr>
                                            <td>${s.count}</td>
                                            <td>${caseInfo.entity.caseXkNo}</td>
                                            <td title="${caseInfo.entity.caseName}">
                                                <c:if test="${fn:length(caseInfo.entity.caseName) gt 8}">
                                                    <a href="<%=path%>/center/caseinfo/editCase.html?consignmentId=${caseInfo.consignmentId}">${fn:substring(caseInfo.entity.caseName,0,7)} ...</a>
                                                </c:if>
                                                <c:if test="${fn:length(caseInfo.entity.caseName) lt 9}">
                                                    <a href="<%=path%>/center/caseinfo/editCase.html?consignmentId=${caseInfo.consignmentId}">${caseInfo.entity.caseName}</a>
                                                </c:if>
                                            </td>
                                            <td>${caseInfo.entity.caseNo}</td>
                                            <td>${caseInfo.casePropertyName}</td>
                                            <td>${caseInfo.delegateOrgName}</td>
                                            <td>
                                                <fmt:formatDate value='${caseInfo.delegateAcceptDate}' pattern='yyyy-MM-dd'/>
                                            </td>
                                            <td>${caseInfo.delegateAcceptor}</td>
                                            <td>
                                                <c:if test="${caseInfo.quickExamineCount eq 0}">
                                                    未检验
                                                </c:if>
                                                <c:if test="${caseInfo.quickExamineCount gt 0}">
                                                    已检验${caseInfo.quickExamineCount}次
                                                </c:if>
                                            </td>
                                            <td>
                                                <input type="hidden" name="caseId" value="${caseInfo.entity.id}"/>
                                                <input type="hidden" name="quickExamineCount" value="${caseInfo.quickExamineCount}"/>
                                                <button class="btn btn-primary btn-sm" name="addBtn"><i class="fa fa-plus"></i> 添加</button>
                                                <c:if test="${caseInfo.quickExamineCount gt 0}">
                                                    <button class="btn btn-success btn-sm" name="viewBtn"><i class="fa fa-list"></i> 查看</button>
                                                    <button class="btn btn-danger btn-sm" name="delBtn"><i class="fa fa-trash-o"></i> 删除</button>
                                                    <button class="btn btn-info btn-sm" name="downloadBtn"><i class="fa fa-download"></i> 下载记录表</button>
                                                </c:if>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
                        </div>

                    </section>
                </div>
            </div>
        <div class="modal fade" id="downloadModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            下载记录表
                        </h4>
                    </div>
                    <div class="modal-body">
                        <div class="form-group">
                            <input type="hidden" id="caseIdDownloadModel" name="caseIdDownloadModel">
                            <div class="col-sm-4" style="text-align:left;">
                                <button class="btn btn-success btn-sm" type="button" id="OpenExtractDocBtn"><i class="fa fa-file-text-o"></i> 下载提取记录表</button>
                            </div>
                            <div class="col-sm-4" style="text-align:center;">
                                <button class="btn btn-success btn-sm" type="button" id="OpenPcrDocBtn"><i class="fa fa-file-text-o"></i> 下载扩增记录表</button>
                            </div>
                            <div class="col-sm-4" style="text-align:right;">
                                <button class="btn btn-success btn-sm" type="button" id="OpenSyDocBtn"><i class="fa fa-file-text-o"></i> 下载上样记录表</button>
                            </div>
                        </div>
                    </div>
                    <div class="modal-footer">
                        <button data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
                    </div>
                </div>
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

        $("button[name='addBtn']", "#quickExamineListTable").on('click',function(){
            var caseId = $("input[name='caseId']", $(this).parent()).val();
            location.href='<%=path%>/center/examine/addQuickExamine.html?caseId='+caseId;
        });

        $("button[name='viewBtn']", "#quickExamineListTable").on("click",function(){
            var caseId=$("input[name='caseId']", $(this).parent()).val();
            var examineCount = $("input[name='quickExamineCount']", $(this).parent()).val();
            location.href='<%=path%>/center/examine/viewExamineRecord.html?caseId='+caseId + "&examineCount=" + examineCount;
        });

        function delRecord(obj) {
            $.ajax({
                url:'<%=path%>/center/examine/deleteExamineRecord.html?caseId='+ obj,
                type:"get",
                dataType:"json",
                success:function(data){
                    if(data.success || data.success == true || data.success == "true") {
                        location.href='<%=path%>/center/examine/quickExamine.html';
                    }
                }
            })
        }

        $("button[name='delBtn']", "#quickExamineListTable").on("click",function(){
            var caseId=$("input[name='caseId']", $(this).parent()).val();
            var examineCount = $("input[name='quickExamineCount']", $(this).parent()).val();
            if(examineCount == 1){
                if (!confirm("是否删除本次实验记录！"))
                    return;

                delRecord(caseId);
            }else{
                location.href='<%=path%>/center/examine/viewExamineRecord.html?caseId='+ caseId + "&examineCount=" + examineCount;
            }
        });

        $("button[name='downloadBtn']", "#quickExamineListTable").on("click",function(){
            var caseId=$("input[name='caseId']", $(this).parent()).val();
            var examineCount = $("input[name='quickExamineCount']", $(this).parent()).val();
            if(examineCount == 1){
                $("input[name='caseIdDownloadModel']", "#downloadModal").val(caseId);
                $("#downloadModal").modal('show');
            }else{
                location.href='<%=path%>/center/examine/viewExamineRecord.html?caseId='+ caseId + "&examineCount=" + examineCount;
            }
        });

        $("#OpenExtractDocBtn").on("click",function(){
            var caseId = $("#caseIdDownloadModel").val();
            location.href="<%=path%>/center/extract/extractDoc.html?caseId=" + caseId;
        });

        $("#OpenPcrDocBtn").on("click",function(){
            var caseId = $("#caseIdDownloadModel").val();
            location.href="<%=path%>/center/pcr/pcrDoc.html?caseId=" + caseId;
        });

        $("#OpenSyDocBtn").on("click",function(){
            var caseId = $("#caseIdDownloadModel").val();
            location.href="<%=path%>/center/sy/syDoc.html?caseId=" + caseId;
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
            hrefFormer : '<%=path%>/center/examine/quickExamine',
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


