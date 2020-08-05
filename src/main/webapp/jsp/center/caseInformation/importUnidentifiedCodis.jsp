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
                            <span class="label label-primary">导入CODIS</span>
                               <span class="tools pull-right">
                               <a href="javascript:;" class="fa fa-chevron-down"></a>
                            </span>
                        </header>
                        <div class="panel-body">
                            <form class="form-horizontal tasi-form" method="get">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label"><strong>试剂盒</strong></label>
                                    <div class="col-sm-2">
                                        <select class="form-control" id="reagentSelect" name="reagent">
                                            <c:forEach items="${panelInfoList}" var="panelInfo" varStatus="do">
                                                <option value="${panelInfo.panelName}">${panelInfo.panelName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align: right;"><strong>CODIS文件</strong></label>
                                    <div class="col-sm-4">
                                        <input type="file" name="codisFile" id="codisFile" class="hide"/>
                                        <input type="text" id="codisFileTxt" class="form-control" readonly="readonly"/>
                                    </div>
                                    <div class="col-sm-2">
                                        <button class="btn btn-info" type="button" id="browserCodisBtn"><i class="fa  fa-folder-open"></i> 浏览...</button>
                                        <button class="btn btn-primary" type="button" id="importCodisBtn"><i class="fa fa-download"></i> 导入</button>
                                    </div>
                                    <div id="importResultDiv" class="col-sm-3 mt5 hide">
                                        <span class="label label-info" id="sampleCountInFile">总数（99）</span>
                                        <span class="label label-success" id="succeedCountInFile">成功（80）</span>
                                        <span class="label label-warning" id="failedCountInFile">失败（19）</span>
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
                                    <th>基因座</th>
                                    <th>基因分型</th>
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
<script src="<%=path%>/js/ajaxfileupload.js" ></script><!-- BASIC JS LIABRARY -->
<script>
    function viewGeneDetail(obj) {
        'use strict';
        var geneInfo = $("input[name='geneInfo']", $(obj).parent()).val();
        var jsonInfo = $.parseJSON(geneInfo);
        var len = jsonInfo.length;
        var gene;
        $("#geneDetailTable", "#GeneDetailModal").empty();

        var htmlStr = "";
        for(var i = 0; i < len; i++){
            gene = jsonInfo[i];

            htmlStr += "<tr><td>"+ gene.geneName +"</td>";
            htmlStr += "<td>" + gene.geneVal1 + ", ";
            htmlStr += gene.geneVal2;
            if(gene.geneVal3.length > 0){
                htmlStr += ", " + gene.geneVal3;
            }
            if(gene.geneVal4.length > 0){
                htmlStr += ", " + gene.geneVal4 + "</td>";
            }

            htmlStr += "</tr>";

        }

        $("#geneDetailTable", "#GeneDetailModal").append(htmlStr);

        $("#GeneDetailModal").modal('show')
    }

    $(function() {
        'use strict';

        function importCodisFtn() {

            var  reagentName = $("#reagentSelect").val();

            $.ajaxFileUpload({
                cache:false,
                url:"<%=path%>/center/caseInformation/uploadUnidentifiedCodis.html?reagentName=" + reagentName,
                secureuri:false,
                fileElementId:'codisFile',
                dataType: 'json',
                success: function (data) {
                    if(data.success || data.success == true || data.success == "true") {
                        $("#sampleCountInFile").text("总数（" + data.sampleCountInFile + "）");
                        $("#succeedCountInFile").text("成功（" + data.succeedCountInFile + "）");
                        $("#failedCountInFile").text("失败（" + data.failedCountInFile + "）");
                        $("#importResultDiv").removeClass("hide");

                        if(data.succeedCountInFile > 0) {
                            $("#succeedDiv").removeClass("hide");
                            $("#succeedListTable").empty();

                            var succeedList = data.succeedList;
                            var succeedSample;
                            var newRowHtml;
                            for (var i = 0; i < data.succeedCountInFile; i++) {
                                succeedSample = succeedList[i];
                                newRowHtml = "";
                                newRowHtml += "<tr><td>" + succeedSample.sampleNo + "</td>";
                                newRowHtml += "<td><input type='hidden' name='geneInfo' value='"+JSON.stringify(succeedSample.codisGeneInfoList)+"'/>";
                                newRowHtml += "<button name='detailBtn' class='btn btn-info btn-xs' onclick='viewGeneDetail(this);'><i class='fa fa-list-alt'></i> 查看</button>";
                                newRowHtml += "</td></tr>";
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
        $("#browserCodisBtn").on("click",function(){
            $("#codisFile").click();
        });

        $("#codisFile").on("change",function(){
            $("#codisFileTxt").val($(this).val());
        });

        $("#importCodisBtn").on("click",function(){
            importCodisFtn();
        });

    });
</script>

<!-- END JS -->
</body>
</html>


