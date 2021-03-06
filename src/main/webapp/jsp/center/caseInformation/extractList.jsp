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
                            <span class="label label-primary">提取记录列表</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <div class="clearfix">
                                <div class="btn-group">
                                    <button id="newExtractBtn" class="btn btn-success green">
                                        添加提取记录 <i class="fa fa-plus"></i>
                                        <input type="hidden" name="caseInformationId" value="${caseId}"/>
                                    </button>
                                </div>
                            </div>
                            <div class="space15" style="height: 5px;"></div>
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>提取记录编号</th>
                                    <th>提取时间</th>
                                    <th>提取人</th>
                                    <th>检材个数</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="extractRecordListTable">
                                <c:forEach items="${limsExtractRecordList}" var="extractRecord" varStatus="s">
                                    <tr>
                                        <td>${extractRecord.extractTaskNo}</td>
                                        <td><fmt:formatDate value='${extractRecord.extractDatetime}' pattern='yyyy-MM-dd HH:mm'/></td>
                                        <td>${extractRecord.extractPersonName1}、${extractRecord.extractPersonName2}</td>
                                        <td>${extractRecord.sampleCount}</td>
                                        <td>
                                            <input type="hidden" name="extractRecordId" value="${extractRecord.id}"/>
                                            <button class="btn btn-success btn-sm" name="viewBtn"><i class="fa fa-list"></i> 查看</button>
                                            <button class="btn btn-danger btn-sm" name="delBtn"><i class="fa fa-trash-o"></i> 删除</button>
                                            <%--<button class="btn btn-info btn-sm" name="downloadBtn"><i class="fa fa-download"></i> 下载记录表</button>--%>
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
    $(function(){
        'use strict';

        function delRecord(obj) {
            var extractRecordId = $("input[name='extractRecordId']", $(obj).parent()).val();
            $.ajax({
                url:'<%=path%>/center/extract/delByRecordId.html?extractRecordId='+extractRecordId,
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

        $("#newExtractBtn").on('click',function(){
            var caseInformationId = $("input[name='caseInformationId']", $(this).parent()).val();
            console.log(caseInformationId)
            location.href = '<%=path%>/center/caseInformation/addExtraction.html?caseInformationId=' + caseInformationId;
        });

        $("button[name='viewBtn']", "#extractRecordListTable").on("click",function(){
            var extractRecordId=$("input[name='extractRecordId']", $(this).parent()).val();
            location.href='<%=path%>/center/caseInformation/editExtract.html?extractRecordId='+extractRecordId;
        });


        $("button[name='delBtn']", "#extractRecordListTable").on("click",function(){
            if (!confirm("是否删除本次实验记录！"))
                return;
            delRecord(this);
        });


        $("button[name='downloadBtn']", "#extractRecordListTable").on("click",function(){
            var extractRecordId=$("input[name='extractRecordId']", $(this).parent()).val();
            location.href='<%=path%>/center/extract/extractDoc.html?extractRecordId='+extractRecordId;
        });

        $('.form_datetime').datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            language:  'zh',
            weekStart: 1,
            todayBtn:  1,
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


