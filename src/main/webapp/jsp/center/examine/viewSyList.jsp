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
        </div>
        <!-- END ROW  -->

        <!-- BEGIN ROW  -->
        <div class="row">
            <div class="col-lg-12">
                <section class="panel">
                    <header class="panel-heading">
                        <span class="label label-primary">上样记录列表</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                    </header>
                    <div class="panel-body">
                        <div class="clearfix">
                            <div class="btn-group">
                                <input type="hidden" id="caseId" name="caseId" value="${caseId}"/>
                                <%--<c:forEach items="${limsSyRecordList}" var="syRecord" varStatus="s">
                                    <c:choose>
                                        <c:when test="${syRecord.taskId eq 0  }">--%>
                                            <button id="newSyBtn" class="btn btn-success green">
                                                添加上样记录 <i class="fa fa-plus"></i>
                                            </button>
                                        <%--</c:when>
                                    </c:choose>
                                </c:forEach>--%>
                            </div>
                        </div>
                        <div class="space15" style="height: 5px;"></div>
                        <table class="table table-striped table-advance table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>上样记录编号</th>
                                <th>上样开始时间</th>
                                <th>上样结束时间</th>
                                <th>电泳仪</th>
                                <th>上样人</th>
                                <th>上样板号</th>
                                <th>检材个数</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="syRecordListTable">
                            <c:forEach items="${limsSyRecordList}" var="syRecord" varStatus="s">
                                <tr>
                                    <td>${syRecord.syTaskNo}</td>
                                    <td><fmt:formatDate value='${syRecord.startDatetime}'
                                                        pattern='yyyy-MM-dd HH:mm'/></td>
                                    <td><fmt:formatDate value='${syRecord.endDatetime}'
                                                        pattern='yyyy-MM-dd HH:mm'/></td>
                                    <td>${syRecord.elecInstrumentName}</td>
                                    <td>${syRecord.operatePersonName1}、${syRecord.operatePersonName2}</td>
                                    <td>${syRecord.boardNo}</td>
                                    <td>${syRecord.sampleCount}</td>
                                    <td>
                                        <input type="hidden" name="syRecordId" value="${syRecord.id}"/>
                                        <input type="hidden" name="taskId" value="${syRecord.taskId}"/>
                                        <input type="hidden" name="caseId" value="${caseId}"/>
                                        <c:choose>
                                            <c:when test="${syRecord.taskId eq 0  }">
                                                <button class="btn btn-primary btn-sm" name="editBtn"><i
                                                        class="fa fa-pencil"></i> 编辑
                                                </button>
                                                <button class="btn btn-danger btn-sm" name="delBtn"><i
                                                        class="fa fa-trash-o"></i> 删除
                                                </button>
                                                <button class="btn btn-info btn-sm" name="downloadTxtBtn"><i
                                                        class="fa fa-file-text"></i> 下载上样表（txt）
                                                </button>
                                                <button class="btn btn-info btn-sm" name="downloadBtn"><i
                                                        class="fa fa-download"></i> 下载记录表
                                                </button>
                                            </c:when>
                                            <c:otherwise>
                                                <button class="btn btn-info btn-sm" name="appDownloadBtn"><i
                                                        class="fa fa-download"></i> 下载记录表
                                                </button>
                                            </c:otherwise>
                                        </c:choose>

                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
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

        function delRecord(obj) {
            var syRecordId = $("input[name='syRecordId']", $(obj).parent()).val();
            $.ajax({
                url: '<%=path%>/center/sy/delByRecordId.html?syRecordId=' + syRecordId,
                type: "get",
                dataType: "json",
                success: function (data) {
                    if (data.success || data.success == true || data.success == "true") {
                        var caseId = $("#caseId").val();
                        location.href = "<%=path%>/center/sy/viewSyRecord.html?caseId=" + caseId;
                    }
                }
            });
        }

        $("#newSyBtn").on('click', function () {
            var caseId = $("#caseId").val();
            location.href = '<%=path%>/center/sy/add.html?caseId=' + caseId;
        });

        $("button[name='editBtn']", "#syRecordListTable").on("click", function () {
            var syRecordId = $("input[name='syRecordId']", $(this).parent()).val();
            location.href = '<%=path%>/center/sy/edit.html?syRecordId=' + syRecordId;
        });


        $("button[name='delBtn']", "#syRecordListTable").on("click", function () {
            if (!confirm("是否删除本次实验记录！"))
                return;

            delRecord(this);
        });


        $("button[name='downloadTxtBtn']", "#syRecordListTable").on("click", function () {
            var syRecordId = $("input[name='syRecordId']", $(this).parent()).val();
            location.href = '<%=path%>/center/sy/downloadSytable.html?syRecordId=' + syRecordId;
        });

        $("button[name='downloadBtn']", "#syRecordListTable").on("click", function () {
            var syRecordId = $("input[name='syRecordId']", $(this).parent()).val();
            location.href = '<%=path%>/center/sy/syDoc.html?syRecordId=' + syRecordId;
        });


        $("button[name='appDownloadBtn']", "#syRecordListTable").on("click", function () {
            var caseId = $("input[name='caseId']", $(this).parent()).val();
            var taskId = $("input[name='taskId']", $(this).parent()).val();
            location.href = '<%=path%>/center/sy/appSyDoc.html?caseId=' + caseId + "&taskId=" + taskId;
        });


        $('.form_datetime').datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            language: 'zh',
            weekStart: 1,
            todayBtn: 1,
            minView: "hour",
            autoclose: true,
            todayHighlight: true,
            forceParse: 0,
            showMeridian: 1
        }).on('changeDate', function (ev) {
            $(this).datetimepicker('hide');
        });

    });
</script>
<!-- END JS -->
</body>
</html>


