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
                <span class="label label-primary">提取记录表</span>
               <span class="tools pull-right">
               <a href="javascript:;" class="fa fa-chevron-down"></a>
               </span>
            </header>
            <div class="panel-body">
                <form class="form-horizontal tasi-form">
                    <table class="table table-striped table-advance table-bordered table-hover">
                        <thead>
                        <tr>
                            <th rowspan="2"><label class="checkbox-inline"><input type="checkbox" name="allChecked" id="allChecked" checked/>全选</label></th>
                            <th rowspan="2" style='width:14%'>样本实验室编号</th>
                            <th rowspan="2">检材名称</th>
                            <th rowspan="2" style='width:8%'>检材类型</th>
                            <th rowspan="2">FOB</th>
                            <th rowspan="2">PSA</th>
                            <th rowspan="2" style='width:12%'>转移方法</th>
                            <th rowspan="2" style='width:12%'>提取部位</th>
                            <th rowspan="2">方法</th>
                            <th colspan="4">使用仪器</th>
                            <th rowspan="2">删除</th>
                        </tr>
                        <tr>
                            <th>离1</th>
                            <th>震1</th>
                            <th>搅1</th>
                            <th>浴1</th>
                        </tr>
                        </thead>
                        <tbody id="extractTable">
                            <c:forEach items="${limsExtractRecordSampleList}" var="sample" varStatus="s">
                                <tr>
                                    <td><label class="checkbox-inline"><input type="checkbox" name="box" checked></label></td>
                                    <td>
                                        <input name="sampleNo" type="text" class="form-control small" onmouseover="this.title=this.value" value="${sample.sampleNo}" readonly="readonly">
                                    </td>
                                    <td>
                                        <input name="sampleName" type="text" class="form-control small" onmouseover="this.title=this.value" value="${sample.sampleName}" readonly="readonly">
                                    </td>
                                    <td>
                                        <input name="sampleTypeName" type="text" class="form-control small" value="${sample.sampleTypeName}" readonly="readonly">
                                    </td>
                                    <td>
                                        <select name="extractHb" class="form-control small" style="font-size: 17px;" value="${sample.extractHb}">
                                            <option value=""></option>
                                            <option value="+" <c:if test="${sample.extractHb eq '+'}">selected</c:if>>+</option>
                                            <option value="-" <c:if test="${sample.extractHb eq '-'}">selected</c:if>>-</option>
                                         </select>
                                    </td>
                                    <td>
                                         <select name="extractPsa" class="form-control small" style="font-size: 17px;" value="${sample.extractPsa}">
                                             <option value=""></option>
                                             <option value="+" <c:if test="${sample.extractPsa eq '+'}">selected</c:if>>+</option>
                                             <option value="-" <c:if test="${sample.extractPsa eq '-'}">selected</c:if>>-</option>
                                         </select>
                                    </td>
                                    <td>
                                        <select name="changeMethod" class="form-control small" style='width:82%;float:left;' value="${sample.changeMethod}" onmouseover="this.title=this.value">
                                            <option value=""></option>
                                            <option value="剪取" <c:if test="${sample.changeMethod eq '剪取'}">selected</c:if>>剪取</option>
                                            <option value="棉签转移" <c:if test="${sample.changeMethod eq '棉签转移'}">selected</c:if>>棉签转移</option>
                                            <option value="其他" <c:if test="${sample.changeMethod eq '其他'}">selected</c:if>>其他</option>
                                        </select>
                                        <input type="text" name="otherChangeMethod" style='width:82%;float:left;' value="${sample.otherChangeMethod}"
                                               class="form-control small hide" placeholder="其他转移方法" onmouseover="this.title=this.value"/>
                                        <a href="#" name="methodBtnPullDown" title="向下填充"><img border="0" src="<%=path%>/images/field_Down.bmp" style="cursor:pointer;margin-left:3px;margin-top:5px;" ></a>
                                    </td>
                                    <td>
                                        <input name="extractPosition" type="text" class="form-control small" style='width:82%;float:left;' value="${sample.extractPosition}" onmouseover="this.title=this.value">
                                        <a href="#" name="positionBtnPullDown" title="向下填充"><img border="0" src="<%=path%>/images/field_Down.bmp" style="cursor:pointer;margin-left:3px;margin-top:5px;" ></a>
                                    </td>
                                    <td>
                                         <select name="extractMethod" class="form-control small" value="${sample.extractMethod}">
                                             <c:forEach items="${extractMethodList}" var="extractMethod" varStatus="em">
                                                 <option value="${extractMethod.dictCode}" <c:if test="${sample.extractMethod eq extractMethod.dictCode}">selected</c:if>>${extractMethod.dictCode}</option>
                                             </c:forEach>
                                         </select>
                                    </td>
                                    <td>
                                        <label class="checkbox-inline"><input type="checkbox" name="extractLiFlag" <c:if test="${sample.extractLiFlag eq 1}">checked</c:if>></label>
                                    </td>
                                    <td>
                                        <label class="checkbox-inline"><input type="checkbox" name="extractZhenFlag" <c:if test="${sample.extractZhenFlag eq 1}">checked</c:if>></label>
                                    </td>
                                    <td>
                                        <label class="checkbox-inline"><input type="checkbox" name="extractJiaoFlag" <c:if test="${sample.extractJiaoFlag eq 1}">checked</c:if>></label>
                                    </td>
                                    <td>
                                        <label class="checkbox-inline"><input type="checkbox" name="extractYuFlag" <c:if test="${sample.extractYuFlag eq 1}">checked</c:if>></label>
                                    </td>
                                    <td>
                                        <input type="hidden" name="id" value="${sample.id}"/>
                                        <input type="hidden" name="sampleId" value="${sample.sampleId}"/>
                                        <button type="button" name="delExtractBtn" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 删除</button>
                                    </td>
                                </tr>
                            </c:forEach>
                        </tbody>
                    </table>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label" style="text-align: right;">提取人 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-2">
                                        <select class="form-control inline-block" id="extractPerson1">
                                            <c:forEach items="${labUserList}" var="labUser" varStatus="lu">
                                                <option value="${labUser.id}" <c:if test="${limsExtractRecord.extractPersonId1 eq labUser.id}">selected</c:if>>${labUser.userName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2">
                                        <select class="form-control inline-block" id="extractPerson2">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${labUserList}" var="labUser" varStatus="lu">
                                                <option value="${labUser.id}" <c:if test="${limsExtractRecord.extractPersonId2 eq labUser.id}">selected</c:if>>${labUser.userName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align: right;">提取时间 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-2">
                                        <input type="text" class="form-control form_datetime" id="extractDatetime"
                                            value="<fmt:formatDate value='${limsExtractRecord.extractDatetime}' pattern='yyyy-MM-dd HH:mm'/>" readonly>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-sm-3 pull-right">
                                        <input type="hidden" id="operateType" value="${operateType}"/>
                                        <input type="hidden" id="caseId" value="${limsExtractRecord.caseId}"/>
                                        <input type="hidden" id="extractRecordId" value="${limsExtractRecord.id}"/>
                                        <button class="btn btn-primary" type="button" id="saveBtn"><i class="fa fa-check"></i> 保 存</button>
                                        &nbsp;
                                        <button class="btn btn-info" type="button" id="backToListBtn"><i class="fa fa-reply"></i> 返回列表页面</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </section>
                </div>
            </div>


            <!-- BEGIN ROW  -->
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            <span class="label label-primary">提取方法</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                            <form class="form-horizontal tasi-form">

                                <c:forEach items="${extractMethodList}" var="method" varStatus="em">
                                    <div class="form-group">
                                        <div class="col-sm-12">
                                            <p><strong>${method.dictCode}、 ${method.dictName}</strong> ${method.dictDesc}</p>
                                        </div>
                                    </div>
                                </c:forEach>

                            </form>
                        </div>
                    </section>
                </div>
            </div>

            <div class="modal fade" id="ExtractRecordModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <h4 class="modal-title">
                                消息提示
                            </h4>
                        </div>
                        <div class="modal-body">
                            <form class="form-horizontal tasi-form">
                                <div class="form-group m-bot20"></div>
                                <div class="form-group m-bot20">
                                    <div class="col-md-12 text-center">
                                        <h3 class="alert alert-success"><Strong>保存成功！</Strong></h3>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <i class="fa fa-hand-o-right"></i>
<%--
                            <button class="btn btn-success" type="button" id="OpenDocBtn"><i class="fa fa-file-text-o"></i> 下载提取记录表</button>
--%>
                            &nbsp;
                            <button class="btn btn-default" type="button" id="FinishedBtn"><i class="fa fa-list-alt"></i> 完成</button>
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

    getOtherChangeMethod();

    function getOtherChangeMethod() {
        $("tr","#extractTable").each(function(){
            var otherChangeMethod = $("input[name='otherChangeMethod']", $(this)).val();
            if (otherChangeMethod != "")
                $("input[name='otherChangeMethod']", $(this)).removeClass("hide");
        });
    }

    $(function(){
        'use strict';

        $("a[name='positionBtnPullDown']","#extractTable").on("click", function(){
            var $curTR = $(this).parents("tr");

            var extractPosition = $("input[name='extractPosition']", $curTR).val();
            evaluateExtractPosition($(this), extractPosition);
        });

        function evaluateExtractPosition(obj, extractPosition) {
            var totalRows = $("tr", "#extractTable").length;
            var curIdx = $(obj).parents("tr").index();

            var startIdx = curIdx + 1;
            for(var i = startIdx; i < totalRows; i++){
                $("input[name='extractPosition']","tbody tr:eq("+ i +")").val(extractPosition);
            }
        }

        $("a[name='methodBtnPullDown']","#extractTable").on("click", function(){
            var $curTR = $(this).parents("tr");

            var changeMethod = $("select[name='changeMethod']", $curTR).val();
            var otherChangeMethod = $("input[name='otherChangeMethod']", $curTR).val();
            evaluateMethod($(this), changeMethod, otherChangeMethod);
        });

        function evaluateMethod (obj, changeMethod, otherChangeMethod) {
            var totalRows = $("tr", "#extractTable").length;
            var curIdx = $(obj).parents("tr").index();

            var startIdx = curIdx + 1;
            for(var i = startIdx; i < totalRows; i++){
                $("select[name='changeMethod']","tbody tr:eq("+ i +")").val(changeMethod);
                if (changeMethod != "" && changeMethod == "其他") {
                    $("input[name='otherChangeMethod']","tbody tr:eq("+ i +")").val(otherChangeMethod);
                }else {
                    $("input[name='otherChangeMethod']","tbody tr:eq("+ i +")").val("");
                    $("input[name='otherChangeMethod']", "tbody tr:eq("+ i +")").addClass("hide");
                }
            }

            if (changeMethod != "" && changeMethod == "其他") {
                getOtherChangeMethod();
            }
        }

        $("select[name='changeMethod']","#extractTable").on("change",function(){
            var $curTR = $(this).parents("tr");

            var changeMethod = $("select[name='changeMethod']", $curTR).val();
            if (changeMethod != "" && changeMethod == "其他") {
                var otherChangeMethod = $("input[name='otherChangeMethod']", $curTR).val();
                $("input[name='otherChangeMethod']", $curTR).val(otherChangeMethod);
                $("input[name='otherChangeMethod']", $curTR).removeClass("hide");
            }else {
                $("input[name='otherChangeMethod']", $curTR).addClass("hide");
            }
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

        function GetLimsExtractRecordSample() {
            var limsExtractRecordSampleArr = new Array();
            var limsExtractRecordSample;

            $("#extractTable").find("tr").not(".hide").each(function(){
                limsExtractRecordSample = {};
                var $TR = $(this);
                var checkBox = $("input[name='box']",$TR).is(":checked");
                var changeMethod = $("select[name='changeMethod']", $TR).find("option:selected").val();
                if(checkBox){
                    limsExtractRecordSample.id=$("input[name='id']",$TR).val();
                    limsExtractRecordSample.sampleId = $("input[name='sampleId']", $TR).val();
                    limsExtractRecordSample.sampleNo = $("input[name='sampleNo']", $TR).val();
                    limsExtractRecordSample.extractHb = $("select[name='extractHb']", $TR).find("option:selected").val();
                    limsExtractRecordSample.extractPsa = $("select[name='extractPsa']", $TR).find("option:selected").val();
                    limsExtractRecordSample.extractPosition = $("input[name='extractPosition']", $TR).val();
                    limsExtractRecordSample.changeMethod = $("select[name='changeMethod']", $TR).find("option:selected").val();
                    if (changeMethod != "" && changeMethod == "其他") {
                        limsExtractRecordSample.otherChangeMethod = $("input[name='otherChangeMethod']", $TR).val();
                    }else {
                        limsExtractRecordSample.otherChangeMethod = "";
                    }
                    limsExtractRecordSample.extractMethod = $("select[name='extractMethod']", $TR).find("option:selected").val();
                    limsExtractRecordSample.extractLiFlag = ($("input[name='extractLiFlag']", $TR).is(":checked")==true) ? "1" : "0";
                    limsExtractRecordSample.extractZhenFlag = ($("input[name='extractZhenFlag']", $TR).is(":checked")==true) ? "1" : "0";
                    limsExtractRecordSample.extractJiaoFlag = ($("input[name='extractJiaoFlag']", $TR).is(":checked")==true) ? "1" : "0";
                    limsExtractRecordSample.extractYuFlag = ($("input[name='extractYuFlag']", $TR).is(":checked")==true) ? "1" : "0";

                    limsExtractRecordSampleArr.push(limsExtractRecordSample);
                }
            });

            return limsExtractRecordSampleArr;
        }

        function GetLimsExtractRecord() {
            var limsExtractRecord = {};
            limsExtractRecord.id=$("#extractRecordId").val();
            limsExtractRecord.caseId=$("#caseId").val();
            limsExtractRecord.extractDatetime = $("#extractDatetime").val();
            limsExtractRecord.extractPersonId1 = $("option:selected","#extractPerson1").val();
            limsExtractRecord.extractPersonName1 = $("option:selected","#extractPerson1").text();
            limsExtractRecord.extractPersonId2 = $("option:selected","#extractPerson2").val();
            limsExtractRecord.extractPersonName2 = $("option:selected","#extractPerson2").text();

            return limsExtractRecord;
        }

        function SaveExtractForm() {

            var operateType=$("#operateType").val();
            var limsExtractRecord = GetLimsExtractRecord();
            var limsExtractRecordSampleList = GetLimsExtractRecordSample();

            if(limsExtractRecord.extractDatetime == ""){
                alert("请选择提取时间！");
                $("#extractDatetime").addClass("text_error_border");
                return;
            }else{
                $("#extractDatetime").removeClass("text_error_border");
            }

            var extractPerson2= $("option:selected","#extractPerson2").val();
            if(extractPerson2 == ""){
                alert("请选择提取人！");
                return;
            }

            if(limsExtractRecordSampleList.length ==0){
                alert("请添加提取检材！");
                return;
            }

            var params = {
                "limsExtractRecord": limsExtractRecord,
                "limsExtractRecordSampleList": limsExtractRecordSampleList
            };

            $.ajax({
                url: "<%=path%>/center/extract/saveRecord.html?operateType="+operateType,
                type: "post",
                data: JSON.stringify(params),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success:function(data){
                    if(data.success || data.success == true || data.success == "true") {
                        $("#extractRecordId").val(data.extractRecordId);
                        $("#ExtractRecordModal").modal('show');
                    }
                }
            });
        }

        function downloadExtractRecordDoc() {
            var extractRecordId=$("#extractRecordId").val();
            location.href="<%=path%>/center/extract/extractDoc.html?extractRecordId=" + extractRecordId;
        }

        function finishExtract() {
            $("#ExtractRecordModal").modal('hide');
        }

        function AddSampleRows(sampleInfoList) {
            var hideRowHtml = $("tr.hide", "#extractTable").html();
            var newRowHtml;
            var sample;
            var existsInRow = false;
            for(var i = 0; i < sampleInfoList.length; i++){
                sample = sampleInfoList[i];

                $("input[name='sampleId']", "#extractTable").each(function(){
                    var valInRow = $(this).val();
                    if(valInRow == sample.id){
                        existsInRow = true;
                    }
                });

                if(existsInRow) continue;

                newRowHtml = "<tr>" + hideRowHtml + "</tr>";
                $("#extractTable").append(newRowHtml);
                $("tr:eq(0)", "#extractTable").find("input[name='sampleNo']").val(sample.sampleNo);
                $("tr:eq(0)", "#extractTable").find("input[name='sampleId']").val(sample.id);
                $("tr:eq(0)", "#extractTable").find("button[name='delExtractBtn']").on("click",function(){
                    $(this).parents("tr").remove();
                });
            }
        }

        function addSampleBySampleNo() {
            var sampleNo = $("#snoTxt").val();
            if(sampleNo == "") {
                $(".has-error", $("#snoTxt").parents(".form-group")).find("p").html("请输入检材编号！");
                $(".has-error", $("#snoTxt").parents(".form-group")).removeClass("hide");
                return;
            }

            $.ajax({
                url:"<%=path%>/center/examine/querySample.html?sampleNo=" + sampleNo,
                type:"get",
                dataType:"json",
                success:function(data){
                    if(data.sampleCnt > 0) {
                        AddSampleRows(data.sampleInfoList);

                        $("#snoTxt").val("");
                        $(".has-error", $("#snoTxt").parents(".form-group")).find("p").html("");
                        $(".has-error", $("#snoTxt").parents(".form-group")).addClass("hide");
                    } else {
                        $(".has-error", $("#snoTxt").parents(".form-group")).find("p").html("检材编号不存在！");
                        $(".has-error", $("#snoTxt").parents(".form-group")).removeClass("hide");
                        return;
                    }
                }
            });
        }

        function addSampleByCaseNo() {
            var caseNo = $("#cnoTxt").val();
            if(caseNo == "") {
                $(".has-error", $("#cnoTxt").parents(".form-group")).find("p").html("请输入案件编号！");
                $(".has-error", $("#cnoTxt").parents(".form-group")).removeClass("hide");
                return;
            }

            $.ajax({
                url:"<%=path%>/center/examine/querySample.html?caseNo=" + caseNo,
                type:"get",
                dataType:"json",
                success:function(data){
                    if(data.sampleCnt > 0) {
                        AddSampleRows(data.sampleInfoList);

                        $("#cnoTxt").val("");
                        $(".has-error", $("#cnoTxt").parents(".form-group")).find("p").html("");
                        $(".has-error", $("#cnoTxt").parents(".form-group")).addClass("hide");
                    } else {
                        $(".has-error", $("#cnoTxt").parents(".form-group")).find("p").html("案件编号不存在！");
                        $(".has-error", $("#cnoTxt").parents(".form-group")).removeClass("hide");
                        return;
                    }
                }
            });
        }

        $("#addBySampleNoBtn").on("click",function(){
            addSampleBySampleNo();
        });


        $("#addByCaseNoBtn").on("click",function(){
            addSampleByCaseNo();
        });

        $("#snoTxt").on("keydown",function(event){
            if(event.keyCode == 13){
                addSampleBySampleNo();
            }
        });

        $("#cnoTxt").on("keydown",function(event){
            if(event.keyCode == 13){
                addSampleByCaseNo();
            }
        });

        $("button[name='delExtractBtn']","#extractTable").on("click",function(){
            $(this).parents("tr").remove();
        });

        $("#backToListBtn").on("click",function(){
            location.href='<%=path%>/center/caseInformation/missingExtractionList.html';
        });

        $("#saveBtn").on("click",function(){
            SaveExtractForm();
        });


        $("#OpenDocBtn").on("click",function(){
            downloadExtractRecordDoc();
        });

        $("#FinishedBtn").on("click",function(){
            finishExtract();
        });

        $("#ExtractRecordModal").on('hidden.bs.modal', function(){
            location.href='<%=path%>/center/caseInformation/missingExtractionList.html';
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


