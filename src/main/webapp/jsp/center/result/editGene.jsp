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

            <div id="geneDetailDiv" class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            <span class="label label-primary">基因分型</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                            <div class="form-group">
                                <label class="col-sm-2 col-sm-2 control-label"  style="text-align:center;">试剂盒名称</label>
                                <div class="col-sm-2">
                                    <select class="form-control" id="panelNameSelect">
                                        <c:forEach items="${panelInfoList}" var="panelInfo" varStatus="do">
                                            <option value="${panelInfo.panelName}" <c:if test="${limsSampleGene.reagentName eq panelInfo.panelName}">selected</c:if>>${panelInfo.panelName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>

                            <div class="space15" style="height: 5px;"></div>
                            <div class="form-group">
                                <div class="col-sm-11">
                                    <div class="space15" style="height: 5px;"></div>
                                    <table class="table table-striped table-advance table-bordered table-hover" style="table-layout: fixed;width: 50%">
                                        <thead>
                                        <tr>
                                            <th>序号</th>
                                            <th>基因座名称</th>
                                            <th>基因型1</th>
                                            <th>基因型2</th>
                                            <th>基因型3</th>
                                            <th>基因型4</th>
                                        </tr>
                                        </thead>
                                        <tbody id="panelTbody">
                                        <c:forEach items="${sampleGeneList}" var="sampleGene" varStatus="s">
                                            <textarea class="hide">${sampleGene.geneInfo}</textarea>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>

                            <div class="form-group">
                                <div class="col-lg-offset-6 col-lg-10">
                                    <input type="hidden" name="sampleId" id="sampleId" value="${sampleInfo.id}">
                                    <input type="hidden" name="sampleNo" id="sampleNo" value="${sampleInfo.sampleNo}">
                                    <input type="hidden" name="consignmentId" id="consignmentId" value="${sampleInfo.consignmentId}">
                                    <input type="hidden" name="panelId" id="panelId" value="${limsSampleGene.panelId}">
                                    <input type="hidden" name="geneId" id="geneId" value="${limsSampleGene.id}">
                                    <button class="btn btn-lg btn-success" id="saveBtn" type="button"><i class="fa fa-check"></i> 保 存 </button>
                                    <button class="btn btn-lg btn-info" id="backBtn" type="button"  onclick="self.location=document.referrer;"><i class="fa fa-reply"></i> 返 回 </button>
                                </div>
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

    window.onkeydown = function(e) {
        if(9 === e.keyCode) { // Tab 键

        }
    };

    $(function() {
        'use strict';

        function viewGeneDetail(geneInfo) {
            if(typeof(geneInfo)!="undefined" && geneInfo!=0){
                $("#panelNameSelect").append("<option value='' <c:if test='${empty limsSampleGene.reagentName}'>selected</c:if>></option>");

                var jsonInfo = $.parseJSON(geneInfo);
                var len = jsonInfo.length;
                var gene;
                $("#panelTbody").empty();
                var htmlStr = "";
                for(var i = 0; i < len; i++){
                    gene = jsonInfo[i];
                    htmlStr += "<tr><td>"+(i + 1)+"</td>";
                    htmlStr += "<td><input type='hidden' name='markerName' id='markerName' value="+ gene.geneName +">" + gene.geneName + "</td>";
                    htmlStr += "<td><input type='text' name='gene1' id='gene1' class='form-control' tabindex="+(i + 1)+" value="+ gene.geneVal1 +"></td>";
                    htmlStr += "<td><input type='text' name='gene2' id='gene2' class='form-control' tabindex="+(i + 2)+" value="+ gene.geneVal2 +"></td>";
                    htmlStr += "<td><input type='text' name='gene3' id='gene3' class='form-control' value="+ gene.geneVal3 +"></td>";
                    htmlStr += "<td><input type='text' name='gene4' id='gene4' class='form-control' value="+ gene.geneVal4 +"></td>";
                    htmlStr +="</tr>";
                }
                $("#panelTbody").append(htmlStr);
            }else {
                panelSelectChange();
            }
        }

        viewGeneDetail($("textarea.hide").val());

        function GetSampleInfo(){
            var sampleInfo = {};
            sampleInfo.id = $("input[name='sampleId']").val();
            sampleInfo.sampleNo = $("input[name='sampleNo']").val();

            return sampleInfo;
        }

        function GetGeneInfo(){

            var geneArr = new Array();

            var $geneInfoTR = $("tr", "#panelTbody");
            var geneInfoCnt = $geneInfoTR.length;
            var geneLength = "";
            for (var i = 0; i < geneInfoCnt; i++) {
                var geneInfo  = {};
                geneInfo.geneName = $("input[name='markerName']", $geneInfoTR[i]).val();
                geneInfo.geneVal1 = $("input[name='gene1']", $geneInfoTR[i]).val();
                geneInfo.geneVal2 = $("input[name='gene2']", $geneInfoTR[i]).val();
                geneInfo.geneVal3 = $("input[name='gene3']", $geneInfoTR[i]).val();
                geneInfo.geneVal4 = $("input[name='gene4']", $geneInfoTR[i]).val();

                geneArr.push(geneInfo);
                geneLength += geneInfo.geneVal1 + geneInfo.geneVal2 + geneInfo.geneVal3 + geneInfo.geneVal4;
            }

            if(geneLength.length == 0){
                return;
            }else {
                return geneArr;
            }

        }

        $("#saveBtn").on("click",function(){

            var panelNameSelect = $("#panelNameSelect").find("option:selected").val();
            if (panelNameSelect == "") {
                alert("请选择试剂盒名称！");
                return;
            }

            var entity = GetSampleInfo();
            var geneInfo = GetGeneInfo();

            if(geneInfo == null){
                alert("至少输入一个基因型!");
                return false;
            }

            var params = {
                "entity": entity,
                "reagentName":$("#panelNameSelect").val(),
                "geneInfo": JSON.stringify(geneInfo)
            };

            var geneId = $("#geneId").val();

            $.ajax({
                url:"<%=path%>/center/3/saveGeneInfoQuery.html?geneId=" + geneId,
                type:"post",
                data:JSON.stringify(params),
                dataType:"json",
                contentType: "application/json; charset=utf-8",
                success:function(data){
                    if(data){
                        alert("保存成功!");
                        $("#geneId").val(data.geneId);
                    }else {
                        alert("保存失败!");
                    }
                },
                error:function(data,e){
                    alert("保存失败!");
                }
            });
        });

        $("#panelNameSelect").on("change", function () {
            panelSelectChange();
        });

        function getParams(){
            var geneInfo = {};

            geneInfo.sampleId = $("input[name='sampleId']").val();
            geneInfo.sampleNo = $("input[name='sampleNo']").val();
            geneInfo.reagentName = $("#panelNameSelect").val();
            geneInfo.id = $("input[name='geneId']").val();

            return geneInfo;
        }

        function panelSelectChange(){

            $.ajax({
                url:"<%=path%>/center/3/selectGeneInfoQuery.html",
                type:"post",
                data:JSON.stringify(getParams()),
                dataType:"json",
                contentType: "application/json; charset=utf-8",
                success:function(data){

                    $("#panelTbody").html("");

                    var htmlStr = "";
                    if(typeof(data[0].geneInfo)!="undefined" && data[0].geneInfo!=null){
                        var jsonInfo = $.parseJSON(data[0].geneInfo);
                        var len = jsonInfo.length;
                        var gene;
                        for(var i = 0; i < len; i++){
                            gene = jsonInfo[i];
                            htmlStr += "<tr><td>"+(i + 1)+"</td>";
                            htmlStr += "<td><input type='hidden' name='markerName' id='markerName' value="+ gene.geneName +">" + gene.geneName + "</td>";
                            htmlStr += "<td><input type='text' name='gene1' id='gene1' class='form-control' tabindex="+(i + 1)+" value="+ gene.geneVal1 +"></td>";
                            htmlStr += "<td><input type='text' name='gene2' id='gene2' class='form-control' tabindex="+(i + 2)+" value="+ gene.geneVal2 +"></td>";
                            htmlStr += "<td><input type='text' name='gene3' id='gene3' class='form-control' value="+ gene.geneVal3 +"></td>";
                            htmlStr += "<td><input type='text' name='gene4' id='gene4' class='form-control' value="+ gene.geneVal4 +"></td>";
                            htmlStr +="</tr>";
                        }
                    }else {
                        var dataLen = data.length;
                        for(var i =0; i < dataLen;i++){
                            htmlStr += "<tr><td>"+(i + 1)+"</td>";
                            htmlStr += "<td><input type='hidden' name='markerName' id='markerName' value="+ data[i].markerName +">" + data[i].markerName + "</td>";
                            htmlStr += "<td><input type='text' name='gene1' id='gene1' class='form-control' tabindex="+(i + 1)+" value="+ data[i].geneVal1 +"></td>";
                            htmlStr += "<td><input type='text' name='gene2' id='gene2' class='form-control' tabindex="+(i + 2)+" value="+ data[i].geneVal2 +"></td>";
                            htmlStr += "<td><input type='text' name='gene3' id='gene3' class='form-control' value="+ data[i].geneVal3 +"></td>";
                            htmlStr += "<td><input type='text' name='gene4' id='gene4' class='form-control' value="+ data[i].geneVal4 +"></td>";
                            htmlStr +="</tr>";
                        }
                    }

                    $("#panelTbody").append(htmlStr);
                },
                error:function(data,e){
                    alert("查询失败!");
                }
            });

        }

    });
</script>
<!-- END JS -->
</body>
</html>
