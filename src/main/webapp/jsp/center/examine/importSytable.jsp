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
                            <span class="label label-primary">导入上样表</span>
                               <span class="tools pull-right">
                               <a href="javascript:;" class="fa fa-chevron-down"></a>
                            </span>
                        </header>
                        <div class="panel-body">
                            <form class="form-horizontal tasi-form" method="get">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label"><strong>上样表文件</strong></label>
                                    <div class="col-sm-3">
                                        <input type="file" name="sytableFile" id="sytableFile" class="hide"/>
                                        <input type="text" id="sytableFileTxt" class="form-control" readonly="readonly"/>
                                    </div>
                                    <div class="col-sm-2">
                                        <button class="btn btn-info" type="button" id="browserSytableBtn"><i class="fa  fa-folder-open"></i> 浏览...</button>
                                        <button class="btn btn-primary" type="button" id="importSytableBtn"><i class="fa fa-download"></i> 导入</button>
                                    </div>
                                    <div id="importResultDiv" class="col-sm-3 mt5 hide">
                                        <span class="label label-info" id="caseCountInFile">总数（0）</span>
                                        <span class="label label-success" id="sampleCountInFile">成功（0）</span>
                                        <span class="label label-warning" id="failedCountInFile">失败（0）</span>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </section>
                </div>
            </div>
            <!-- END ROW  -->
            <!-- BEGIN ROW  -->
            <div id="succeedDiv" class="row hide">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            <span class="label label-primary">导入成功列表</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                            <div class="space15" style="height: 5px;"></div>
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>检材编号</th>
                                    <th>查看基因型</th>
                                </tr>
                                </thead>
                                <tbody id="succeedListTable">

                                </tbody>
                            </table>
                        </div>
                    </section>
                </div>
            </div>
            <!-- END ROW  -->

            <!-- BEGIN ROW  -->
            <div id="failedDiv" class="row hide">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            <span class="label label-primary">导入失败列表</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                            <div class="space15" style="height: 5px;"></div>
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>检材编号</th>
                                    <th>导入失败原因</th>
                                </tr>
                                </thead>
                                <tbody id="failedListTable">

                                </tbody>
                            </table>
                        </div>
                    </section>
                </div>
            </div>


            <div class="modal fade" id="GeneDetailModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">
                                基因型信息
                            </h4>
                        </div>
                        <div class="modal-body">
                            <div class="space15" style="height: 5px;"></div>
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>案件编号</th>
                                    <th>案件名称</th>
                                    <th>检材个数</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="geneDetailTable">
                                </tbody>
                            </table>
                        </div>
                        <div class="modal-footer">
                            <i class="fa fa-hand-o-right"></i>
                            <button data-dismiss="modal"  class="btn btn-default" type="button" id="FinishedBtn"><i class="fa fa-times"></i> 关闭</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</section>
<!-- BEGIN JS -->
<%@ include file="../../common/script.jsp" %>
<script>
    function editSyRecord(obj) {
        'use strict';
        var caseId = $("input[name='caseId']", $(obj).parent()).val();
        var jsonInfo = $.parseJSON(geneInfo);
        var len = jsonInfo.length;
        var gene;
        $("#geneDetailTable", "#GeneDetailModal").empty();
        for(var i = 0; i < len; i++){
            gene = jsonInfo[i];
            $("#geneDetailTable", "#GeneDetailModal").append("<tr><td>" + gene.geneName + "</td><td>" + gene.geneVal1 + ", " + gene.geneVal2 + "</td></tr>");
        }

        $("#GeneDetailModal").modal('show')
    }

    $(function() {
        'use strict';

        function importCodisFtn() {
            $.ajaxFileUpload({
                cache: false,
                url: "<%=path%>/center/sy/uploadSyTable.html",
                secureuri: false,
                fileElementId: 'sytableFile',
                dataType: 'json',
                success: function (data) {
                    if(data.success || data.success == true || data.success == "true") {
                        $("#caseCountInFile").text("案件数（" + data.caseCount + "）");
                        $("#sampleCountInFile").text("检材数（" + data.sampleCount + "）");
                        $("#failedCountInFile").text("导入失败检材（" + data.failedCount + "）");
                        $("#importResultDiv").removeClass("hide");

                        if(data.succeedCountInFile > 0) {
                            $("#succeedDiv").removeClass("hide");
                            $("#succeedListTable").empty();

                            var caseInfoList = data.caseInfoList;
                            var succeedCase;
                            var newRowHtml;
                            for (var i = 0; i < data.caseCountInFile; i++) {
                                succeedCase = caseInfoList[i];
                                newRowHtml = "";
                                newRowHtml += "<tr><td>" + succeedCase.caseNo + "</td>";
                                newRowHtml += "<td>" + succeedCase.caseName + "</td>";
                                newRowHtml += "<td>" + succeedCase.sampleCount + "</td>";
                                newRowHtml += "<td><input type='hidden' name='caseId' value='"+succeedCase.caseId+"' /><button name='detailBtn' class='btn btn-info btn-xs' onclick='editSyRecord(this);'><i class='fa fa-edit'></i> 编辑</button>";
                                newRowHtml += "</td></tr>"
                                $("#succeedListTable").append(newRowHtml);
                            }
                        }

                        if(data.failedCountInFile > 0) {
                            $("#failedDiv").removeClass("hide");
                            $("#failedListTable").empty();

                            var failedList = data.failedList;
                            var failedSample;
                            for (var i = 0; i < data.failedCountInFile; i++) {
                                failedSample = failedList[i];
                                $("#failedListTable").append("<tr><td>" + failedSample.sampleNo + "</td><td>"+failedSample.failedReason+"</td></tr>");
                            }
                        }
                    }
                },
                error: function (data,status,e) {
                    alert(e);
                }
            });

            return true;
        }

        //导入CODIS start
        $("#browserSytableBtn").on("click",function(){
            $("#codisFile").click();
        });

        $("#sytableFile").on("change",function(){
            $("#sytableFileTxt").val($(this).val());
        });

        $("#importSytableBtn").on("click",function(){
            importCodisFtn();
        });
    });
</script>

<!-- END JS -->
</body>
</html>