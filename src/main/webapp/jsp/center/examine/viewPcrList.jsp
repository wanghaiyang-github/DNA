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
                        <span class="label label-primary">扩增记录列表</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                    </header>
                    <div class="panel-body">
                        <div class="clearfix">
                            <div class="btn-group">
                                <input type="hidden" id="caseId" value="${caseId}"/>
                                <%--<c:forEach items="${limsPcrRecordList}" var="pcrRecord" varStatus="s">
                                    <c:choose>
                                        <c:when test="${pcrRecord.taskId eq 0  }">--%>
                                            <button id="newPcrBtn" class="btn btn-success green">
                                                添加扩增记录 <i class="fa fa-plus"></i>
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
                                <th>扩增记录编号</th>
                                <th>加样超净台</th>
                                <th>扩增试剂盒</th>
                                <th>扩增仪</th>
                                <th>扩增体系</th>
                                <th>扩增开始时间</th>
                                <th>扩增结束时间</th>
                                <th>扩增人</th>
                                <th>检材个数</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="pcrRecordListTable">
                            <c:forEach items="${limsPcrRecordList}" var="pcrRecord" varStatus="s">
                                <tr>
                                    <td>${pcrRecord.pcrTaskNo}</td>
                                    <td>${pcrRecord.pcrProgram}</td>
                                    <td>${pcrRecord.pcrReagent}</td>
                                    <td>${pcrRecord.pcrInstrument}</td>
                                    <td>${pcrRecord.pcrSystem}</td>
                                    <td><fmt:formatDate value='${pcrRecord.pcrStartDatetime}'
                                                        pattern='yyyy-MM-dd HH:mm'/></td>
                                    <td><fmt:formatDate value='${pcrRecord.pcrEndDatetime}'
                                                        pattern='yyyy-MM-dd HH:mm'/></td>
                                    <td>${pcrRecord.pcrPersonName1}、${pcrRecord.pcrPersonName2}</td>
                                    <td>${pcrRecord.sampleCount}</td>
                                    <td>
                                        <input type="hidden" name="pcrRecordId" value="${pcrRecord.id}"/>
                                        <input type="hidden" name="taskId" value="${pcrRecord.taskId}"/>
                                        <input type="hidden" name="caseId" value="${caseId}"/>
                                        <c:choose>
                                            <c:when test="${pcrRecord.taskId eq 0  }">
                                                <button class="btn btn-primary btn-sm" name="editBtn"><i
                                                        class="fa fa-pencil"></i> 编辑
                                                </button>
                                                <button class="btn btn-danger btn-sm" name="delBtn"><i
                                                        class="fa fa-trash-o"></i> 删除
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
            var pcrRecordId = $("input[name='pcrRecordId']", $(obj).parent()).val();
            $.ajax({
                url: '<%=path%>/center/pcr/delByRecordId.html?pcrRecordId=' + pcrRecordId,
                type: "get",
                dataType: "json",
                success: function (data) {
                    if (data.success || data.success == true || data.success == "true") {
                        var caseId = $("#caseId").val();
                        location.href = '<%=path%>/center/pcr/viewPcrRecord.html?caseId=' + caseId;
                    }
                }
            });
        }

        $("#newPcrBtn").on('click', function () {
            var caseId = $("#caseId").val();
            location.href = '<%=path%>/center/pcr/add.html?caseId=' + caseId;
        });

        $("button[name='editBtn']", "#pcrRecordListTable").on("click", function () {
            var pcrRecordId = $("input[name='pcrRecordId']", $(this).parent()).val();
            location.href = '<%=path%>/center/pcr/edit.html?pcrRecordId=' + pcrRecordId;
        });


        $("button[name='delBtn']", "#pcrRecordListTable").on("click", function () {
            if (!confirm("是否删除本次实验记录！"))
                return;

            delRecord(this);
        });


        $("button[name='downloadBtn']", "#pcrRecordListTable").on("click", function () {
            var pcrRecordId = $("input[name='pcrRecordId']", $(this).parent()).val();
            location.href = '<%=path%>/center/pcr/pcrDoc.html?pcrRecordId=' + pcrRecordId;
        });

        $("button[name='appDownloadBtn']", "#pcrRecordListTable").on("click", function () {
            var taskId = $("input[name='taskId']", $(this).parent()).val();
            var caseId = $("input[name='caseId']", $(this).parent()).val();
            location.href = '<%=path%>/center/pcr/appPcrDoc.html?taskId=' + taskId + "&caseId=" + caseId;
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


