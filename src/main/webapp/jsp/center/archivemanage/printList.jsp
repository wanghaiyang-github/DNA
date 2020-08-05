<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/include.jsp" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../../common/link.jsp" %>
</head>
<body>
<!-- BEGIN WRAPPER  -->
<!-- BEGIN ROW  -->
<!-- BEGIN ROW  -->
<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <header class="panel-heading">
                <span class="label label-primary">案件打印</span>
                   <span class="tools pull-right">
                   <a href="javascript:;" class="fa fa-chevron-down"></a>
               </span>
            </header>
            <div class="panel-body">
                <div class="form-group">
                    <div class="col-sm-3">
                        <p>
                            <tr>
                                &nbsp;&nbsp;<input type="checkbox" id="allChecked" name="allChecked">
                                <a href="javascript:;" id="checkAll"> 全 选 </a>
                            </tr>
                        </p>
                        <br>
                        <p>
                            <tr>&nbsp;&nbsp;<input type="checkbox" name="box" value="1">
                            <a href="<%=path%>/center/6/fileCatalog.html?caseId=${caseId}">鉴定文书目录</a>
                            </tr>
                        </p>
                        <br>
                        <p>
                            <tr>
                                &nbsp;&nbsp;<input type="checkbox" name="box" value="2">
                                <a href="<%=path%>/center/6/identifyCover.html?caseId=${caseId}">鉴定文书封面</a>
                            </tr>
                        </p>
                        <br>
                        <p>
                            <tr>&nbsp;&nbsp;<input type="checkbox" name="box" value="3">
                                <a href="<%=path%>/center/6/identifyApprovalFrom.html?caseId=${caseId}">鉴定文书审批表</a>
                            </tr>
                        </p>
                        <c:if test="${ not empty extractRecordId }">
                            <br>
                            <p>
                                <tr>&nbsp;&nbsp;<input type="checkbox" name="box" value="4">
                                    <a href="<%=path%>/center/extract/extractDoc.html?extractRecordId=${extractRecordId}">下载提取记录表</a>
                                </tr>
                            </p>
                        </c:if>
                        <c:if test="${ not empty pcrRecordId }">
                            <br>
                            <p>
                                <tr>&nbsp;&nbsp;<input type="checkbox" name="box" value="5">
                                    <a href="<%=path%>/center/pcr/pcrDoc.html?pcrRecordId=${pcrRecordId}">下载扩增记录表</a>
                                </tr>
                            </p>
                        </c:if>
                        <%--<c:if test="${ not empty syRecordId }">
                            <br>
                            <p>
                                <tr>&nbsp;&nbsp;<input type="checkbox" name="box" value="6">
                                    <a href="<%=path%>/center/sy/downloadSytable.html?caseId=${caseId}">下载上样表</a>
                                </tr>
                            </p>
                        </c:if>--%>
                        <c:if test="${ not empty syRecordId }">
                            <br>
                            <p>
                                <tr>&nbsp;&nbsp;<input type="checkbox" name="box" value="7">
                                    <a href="<%=path%>/center/sy/syDoc.html?caseId=${caseId}">下载上样记录表</a>
                                </tr>
                            </p>
                        </c:if>
                        <br>
                        <c:if test="${not empty identifyBookPath}">
                            <p>
                                <tr>&nbsp;&nbsp;<input type="checkbox" name="box" value="8">
                                    <a href="<%=path%>/center/4/downloadUpload.html?caseId=${caseId}&generate=download">生成鉴定书</a>
                                </tr>
                            </p>
                        </c:if>
                        <%--<br>
                        <p>
                            <tr>&nbsp;&nbsp;<input type="checkbox" name="box" value="9">
                                <a href="#">审批单</a>
                            </tr>
                        </p>--%>
                        <br>
                        <%--<p>
                            <tr>&nbsp;&nbsp;<input type="checkbox" name="box" value="10">
                                <a href="<%=path%>/wt/message/printReceiveFrom.html?caseId=${caseId}">打印领取单</a>
                            </tr>
                        </p>--%>
                        <%--<p>--%>
                            <%--<tr>--%>
                                <%--&nbsp;&nbsp;<input type="checkbox" name="box" value="11">--%>
                                <%--<a href="<%=path%>/wt/caseinfo/delegateDoc.html?consignmentId=${consignmentId}">委托表</a>--%>
                            <%--</tr>--%>
                        <%--</p>--%>
                        <%--<br>--%>
                        <%--<p>--%>
                            <%--<tr>--%>
                                <%--&nbsp;&nbsp;<input type="checkbox" name="box" value="2">--%>
                                <%--<a href="<%=path%>/center/caseinfo/acceptDoc.html?consignmentId=${consignmentId}">受理登记表</a>--%>
                            <%--</tr>--%>
                        <%--</p>--%>
                        <%--<br>--%>
                        <%--<p>--%>
                            <%--<tr>&nbsp;&nbsp;<input type="checkbox" name="box" value="3">--%>
                                <%--<a href="<%=path%>/center/caseinfo/sampleExchangeDoc.html?consignmentId=${consignmentId}">检材及样本流转记录</a>--%>
                            <%--</tr>--%>
                        <%--</p>--%>
                        <br>
                        <br>
                        <br>
                        <br>
                        &nbsp;&nbsp;
                        <button id="printBtn" class="btn btn-primary btn-sm"><i class="fa fa-print"></i> 打 印 </button>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                        <br>
                        <input type="hidden" id="caseId" value="${caseId}">
                        <input type="hidden" id="consignmentId" value="${consignmentId}">
                        <input type="hidden" id="extractRecordId" value="${extractRecordId}">
                        <input type="hidden" id="pcrRecordId" value="${pcrRecordId}">
                        <input type="hidden" id="syRecordId" value="${syRecordId}">
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>
<!-- END ROW  -->
<!-- BEGIN JS -->
<%@ include file="../../common/script.jsp" %>
<script>
    $(function(){
        'use strict';

        $("#checkAll").on("click", function(){
            $("#allChecked").trigger('click');
        });

        $("#allChecked").on("click",function(){
            var ch = document.getElementsByName("box");
            if(document.getElementsByName("allChecked")[0].checked==true){
                for(var i=0;i<ch.length;i++){
                    ch[i].checked=true;
                }
            }else{
                for(var i=0;i<ch.length;i++){
                    ch[i].checked=false;
                }
            }
        });


        $("#printBtn").on("click", function() {
            var obj = document.getElementsByName("box");
            var checkCount = 0;
            var printManage = "";
            for (var i = 0; i < obj.length; i++) {
                if (obj[i].checked) {
                    checkCount++;
                    printManage += obj[i].value + ',';
                }
            }

            if (checkCount <= 0) {
                alert("请选择要打印的信息!");
                return false;
            }

            $.ajax({
                url : "<%=path%>/center/6/allCheckPrintManage.html",
                type:"post",
                contentType:  "application/json; charset=utf-8",
                data : JSON.stringify(params(printManage)),
                dataType : "json",
                success : function(data) {
                    if(data.success !=null && data.success.length > 0) {
                        location.href = '<%=path%>/center/6/downLoadZip.html?zipFilePath=' + data.success;
                    }else {
                        alert("下载失败!");
                    }
                },
                error: function(e){
                    alert(e);
                }
            });

        });

        function params(printManage) {
            var allCheckPrint = {};

            allCheckPrint.caseId = $("#caseId").val();
            allCheckPrint.consignmentId = $("#consignmentId").val();
            allCheckPrint.extractRecordId = $("#extractRecordId").val();
            allCheckPrint.pcrRecordId = $("#pcrRecordId").val();
            allCheckPrint.syRecordId = $("#syRecordId").val();
            allCheckPrint.printManage = printManage;

            return allCheckPrint;
        }

    });
</script>
<!-- END JS -->
</body>
</html>
