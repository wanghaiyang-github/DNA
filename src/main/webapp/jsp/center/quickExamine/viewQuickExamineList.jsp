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
                        <span class="label label-primary">检验记录列表</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                    </header>
                    <div class="panel-body">
                        <div class="clearfix">
                            <div class="btn-group">
                                <button id="newBtn" class="btn btn-success green">
                                    添加检验记录 <i class="fa fa-plus"></i>
                                    <input type="hidden" id="caseId" value="${caseId}"/>
                                </button>
                            </div>
                        </div>
                        <div class="space15" style="height: 5px;"></div>
                        <table class="table table-striped table-advance table-bordered table-hover">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>检验时间</th>
                                <th>检验人</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="quickExamineRecordListTable">
                            <c:forEach items="${limsQuickExamineRecordList}" var="quickExamineRecord" varStatus="s">
                                <tr>
                                    <td>${s.count}</td>
                                    <td><fmt:formatDate value='${quickExamineRecord.createDatetime}' pattern='yyyy-MM-dd HH:mm'/></td>
                                    <td>${quickExamineRecord.createPerson}</td>
                                    <td>
                                        <input type="hidden" name="quickExamineRecordId" value="${quickExamineRecord.id}"/>
                                        <input type="hidden" name="extractRecordId" value="${quickExamineRecord.limsExtractRecordId}"/>
                                        <input type="hidden" name="pcrRecordId" value="${quickExamineRecord.limsPcrRecordId}"/>
                                        <input type="hidden" name="syRecordId" value="${quickExamineRecord.limsSyRecordId}"/>
                                        <button class="btn btn-primary btn-sm" name="editBtn"><i class="fa fa-pencil"></i> 编辑</button>
                                        <button class="btn btn-danger btn-sm" name="delBtn"><i class="fa fa-trash-o"></i> 删除</button>
                                        <button class="btn btn-info btn-sm" name="downloadExtractBtn"><i class="fa fa-download"></i> 下载提取记录表</button>
                                        <button class="btn btn-info btn-sm" name="downloadPcrBtn"><i class="fa fa-download"></i> 下载扩增记录表</button>
                                        <%--<button class="btn btn-info btn-sm" name="downloadSyTxtBtn"><i class="fa fa-file-text"></i> 下载上样表（txt）</button>--%>
                                        <button class="btn btn-info btn-sm" name="downloadSyBtn"><i class="fa fa-download"></i> 下载上样记录表</button>
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
            var quickExamineRecordId = $("input[name='quickExamineRecordId']", $(obj).parent()).val();
            $.ajax({
                url:'<%=path%>/center/examine/delByRecordId.html?quickExamineRecordId='+quickExamineRecordId,
                type:"get",
                dataType:"json",
                success:function(data){
                    if(data.success || data.success == true || data.success == "true") {
                        location.href='<%=path%>/center/examine/quickExamine.html';
                    }
                }
            });
        }

        $("#newBtn").on('click',function(){
            var caseId = $("#caseId").val();
            location.href='<%=path%>/center/examine/addQuickExamine.html?caseId='+caseId;
        });

        $("button[name='editBtn']", "#quickExamineRecordListTable").on("click",function(){
            var caseId = $("#caseId").val();
            var quickExamineRecordId=$("input[name='quickExamineRecordId']", $(this).parent()).val();
            location.href='<%=path%>/center/examine/editQuickExamine.html?quickExamineRecordId='+quickExamineRecordId + "&caseId=" + caseId;
        });


        $("button[name='delBtn']", "#quickExamineRecordListTable").on("click",function(){
            if (!confirm("是否删除本次实验记录！"))
                return;

            delRecord(this);
        });


        $("button[name='downloadExtractBtn']", "#quickExamineRecordListTable").on("click",function(){
            var extractRecordId=$("input[name='extractRecordId']", $(this).parent()).val();
            location.href='<%=path%>/center/extract/extractDoc.html?extractRecordId=' + extractRecordId;
        });

        $("button[name='downloadPcrBtn']", "#quickExamineRecordListTable").on("click",function(){
            var pcrRecordId=$("input[name='pcrRecordId']", $(this).parent()).val();
            location.href='<%=path%>/center/pcr/pcrDoc.html?pcrRecordId=' + pcrRecordId;
        });

        $("button[name='downloadSyTxtBtn']", "#quickExamineRecordListTable").on("click",function(){
            var syRecordId=$("input[name='syRecordId']", $(this).parent()).val();
            location.href='<%=path%>/center/sy/downloadSytable.html?syRecordId='+ syRecordId;
        });

        $("button[name='downloadSyBtn']", "#quickExamineRecordListTable").on("click",function(){
            var syRecordId=$("input[name='syRecordId']", $(this).parent()).val();
            location.href='<%=path%>/center/sy/syDoc.html?syRecordId='+ syRecordId;
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


