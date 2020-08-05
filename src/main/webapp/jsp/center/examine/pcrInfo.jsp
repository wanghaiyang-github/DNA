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
                            <span class="label label-primary">扩增记录</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <form class="form-horizontal tasi-form" method="get">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" style="text-align: right;">扩增人</label>
                                    <div class="col-sm-2">
                                        <select class="form-control" id="pcrPerson1" value="${limsPcrRecord.pcrPersonId1}">
                                            <c:forEach items="${labUserList}" var="labUser" varStatus="lu">
                                                <option value="${labUser.id}" <c:if test="${limsPcrRecord.pcrPersonId1 eq labUser.id}">selected</c:if>>${labUser.userName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2">
                                        <select class="form-control" id="pcrPerson2" value="${limsPcrRecord.pcrPersonId2}">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${labUserList}" var="labUser" varStatus="lu">
                                                <option value="${labUser.id}" <c:if test="${limsPcrRecord.pcrPersonId2 eq labUser.id}">selected</c:if>>${labUser.userName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align: right;">扩增时间 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-4">
                                        <div class="input-group input-large">
                                            <input type="text" class="form-control form_datetime" placeholder="开始时间" id="pcrStartDatetime"
                                                   value="<fmt:formatDate value='${limsPcrRecord.pcrStartDatetime}' pattern='yyyy-MM-dd HH:mm'/>" readonly/>
                                            <span class="input-group-addon">
                                            至
                                            </span>
                                            <input type="text" class="form-control form_datetime" placeholder="结束时间" id="pcrEndDatetime"
                                                   value="<fmt:formatDate value='${limsPcrRecord.pcrEndDatetime}' pattern='yyyy-MM-dd HH:mm'/>" readonly/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" style="text-align: right;">加样超净台</label>
                                    <div class="col-sm-4">
                                        <select class="form-control" id="pcrProgram" value="${limsPcrRecord.pcrProgram}">
                                            <c:forEach items="${pcrInstrumentProgramList}" var="pcrProgram" varStatus="pp">
                                                <option value="${pcrProgram.id}" <c:if test="${limsPcrRecord.pcrProgram eq pcrProgram.equipmentNo}">selected</c:if>>${pcrProgram.equipmentName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align: right;">扩增试剂盒</label>
                                    <div class="col-sm-4">
                                        <select class="form-control" id="pcrReagent" value="${limsPcrRecord.pcrReagent}">
                                            <c:forEach items="${panelInfoList}" var="panelInfo" varStatus="pr">
                                                <option value="${panelInfo.id}" <c:if test="${limsPcrRecord.pcrReagent eq panelInfo.id}">selected</c:if>>${panelInfo.panelName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" style="text-align: right;">扩增仪</label>
                                    <div class="col-sm-4">
                                        <select class="form-control" id="pcrInstrument" value="${limsPcrRecord.pcrInstrument}">
                                            <c:forEach items="${pcrInstrumentList}" var="pcrInstrument" varStatus="pi">
                                                <option value="${pcrInstrument.id}" <c:if test="${limsPcrRecord.pcrInstrument eq pcrInstrument.equipmentNo}">selected</c:if>>${pcrInstrument.equipmentName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align: right;">扩增体系</label>
                                    <div class="col-sm-4">
                                        <select class="form-control" id="pcrSystem" value="${limsPcrRecord.pcrSystem}">
                                            <c:forEach items="${pcrSystemList}" var="pcrSystem" varStatus="ps">
                                                <option value="${pcrSystem.dictCode}" <c:if test="${limsPcrRecord.pcrSystem eq pcrSystem.dictCode}">selected</c:if>>${pcrSystem.dictName}</option>
                                            </c:forEach>
                                        </select>
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
                            <span class="label label-primary">扩增检材列表</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                            <form class="form-horizontal tasi-form">

                                <table class="table table-striped table-advance table-bordered table-hover">
                        <thead>
                        <tr>
                            <th><label class="checkbox-inline"><input type="checkbox" name="allChecked" id="allChecked" checked/>全&nbsp;选</label></th>
                            <th>检材编号</th>
                            <th>检材名称</th>
                            <th>检材类型</th>
                            <th style='width:10%'>Primer ul</th>
                            <th style='width:10%'>Master Mix ul</th>
                            <th style='width:10%'>模板  ul</th>
                            <th style='width:10%'>D.D.W. ul</th>
                            <th>板孔位置</th>
                            <th>删除</th>
                        </tr>
                        </thead>
                        <tbody id="pcrTable">
                        <c:forEach items="${limsPcrRecordSampleList}" var="sample" varStatus="s">
                            <tr>
                                <td><label class="checkbox-inline"><input type="checkbox" name="box" checked></label></td>
                                <td>
                                    <input name="sampleNo" type="text" class="form-control small" value="${sample.sampleNo}" readonly="readonly">
                                </td>
                                <td>
                                    <input name="sampleName" type="text" class="form-control small" onmouseover="this.title=this.value" value="${sample.sampleName}" readonly="readonly">
                                </td>
                                <td>
                                    <input name="sampleTypeName" type="text" class="form-control small" value="${sample.sampleTypeName}" readonly="readonly">

                                </td>
                                <td>
                                    <input name="primerUl" type="text" class="form-control small" value="${sample.primerUl}">
                                </td>
                                <td>
                                    <input name="bufferUl" type="text" class="form-control small" value="${sample.bufferUl}">
                                </td>
                                <td>
                                    <input name="templateUl" type="text" class="form-control small" value="${sample.templateUl}">
                                </td>
                                <td>
                                    <input name="ddwUl" type="text" class="form-control small" value="${sample.ddwUl}">
                                </td>

                                <td>
                                    <select name="samplePosition" class="form-control small" value="${sample.samplePosition}" style='width:84%;float:left;'>
                                        <c:forEach items="${samplePositionList}" var="position" varStatus="s">
                                            <option value="${position}" <c:if test="${position eq sample.samplePosition}">selected</c:if>>${position}</option>
                                        </c:forEach>
                                    </select>
                                </td>

                                <td>
                                    <input type="hidden" name="id" value="${sample.id}"/>
                                    <input type="hidden" name="sampleId" value="${sample.sampleId}"/>
                                    <input type="hidden" name="standardFlag" value="${sample.standardFlag}"/>
                                    <button type="button" name="delPcrBtn" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 删除</button>
                                </td>
                            </tr>
                        </c:forEach>
                        <tr class="hide">
                            <td><input name="sampleNo" type="text" class="form-control small" value="" readonly="readonly"></td>
                            <td><input name="primerUl" type="text" class="form-control small" value=""></td>
                            <td><input name="bufferUl" type="text" class="form-control small" value=""></td>
                            <td><input name="templateUl" type="text" class="form-control small" value=""></td>
                            <td><input name="ddwUl" type="text" class="form-control small" value=""></td>
                            <td>
                                <input type="hidden" name="id" value=""/>
                                <input type="hidden" name="sampleId" value=""/>
                                <button type="button" name="delPcrBtn" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 删除</button>
                            </td>
                        </tr>
                        </tbody>
                    </table>
                                <div class="form-group">
                        <div class="col-lg-offset-10 col-lg-12">
                            <input type="hidden" id="operateType" value="${operateType}"/>
                            <input type="hidden" id="pcrRecordId" value="${limsPcrRecord.id}"/>
                            <input type="hidden" id="caseId" value="${limsPcrRecord.caseId}"/>
                            <input type="hidden" id="extractDatetime" value="<fmt:formatDate value='${limsExtractRecord.extractDatetime}' pattern='yyyy-MM-dd HH:mm'/>">
                            <button class="btn btn-primary" type="button" id="saveBtn"><i class="fa fa-check"></i> 保 存</button>

                            <button class="btn btn-info" type="button" id="backToListBtn"><i class="fa fa-reply"></i> 返回列表页面</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </section>
                </div>
            </div>


            <div class="modal fade" id="PcrRecordModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
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
                            <button class="btn btn-success" type="button" id="OpenDocBtn"><i class="fa fa-file-text-o"></i> 下载扩增记录表</button>
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
    $(function(){
        'use strict';

        $("#pcrStartDatetime").on("change",function(){
            var pcrStartDatetime = $("#pcrStartDatetime").val();
            var dateTime = new Date(pcrStartDatetime).getTime();
            var endDataTime = new Date(dateTime + (2*60*60*1000 + 30*60*1000));
            var Y = endDataTime.getFullYear() + "-";
            var M = (endDataTime.getMonth() + 1 < 10?"0"+(endDataTime.getMonth() + 1):endDataTime.getMonth() + 1) + "-";
            var D = (endDataTime.getDate() + 1 < 10?"0"+(endDataTime.getDate()):endDataTime.getDate()) + " ";
            var h = (endDataTime.getHours() + 1 < 10?"0"+(endDataTime.getHours()):endDataTime.getHours()) + ":";
            var m = (endDataTime.getMinutes() + 1 < 10?"0"+(endDataTime.getMinutes()):endDataTime.getMinutes());
            $("#pcrEndDatetime").val(Y + M + D + h + m);
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

        function GetLimsPcrRecordSample() {
            var limsPcrRecordSampleArr = new Array();
            var limsPcrRecordSample;

            $("#pcrTable").find("tr").not(".hide").each(function(){
                limsPcrRecordSample = {};
                var $TR = $(this);
                var checkBox = $("input[name='box']",$TR).is(":checked");
                if(checkBox){
                    limsPcrRecordSample.id=$("input[name='id']",$TR).val();
                    limsPcrRecordSample.sampleId = $("input[name='sampleId']", $TR).val();
                    limsPcrRecordSample.sampleNo = $("input[name='sampleNo']", $TR).val();
                    limsPcrRecordSample.standardFlag = $("input[name='standardFlag']", $TR).val();
                    limsPcrRecordSample.primerUl = $("input[name='primerUl']", $TR).val();
                    limsPcrRecordSample.bufferUl = $("input[name='bufferUl']", $TR).val();
                    limsPcrRecordSample.templateUl = $("input[name='templateUl']", $TR).val();
                    limsPcrRecordSample.ddwUl = $("input[name='ddwUl']", $TR).val();
                    limsPcrRecordSample.samplePosition = $("select[name='samplePosition']", $TR).find("option:selected").val();
                    limsPcrRecordSampleArr.push(limsPcrRecordSample);
                }
            });

            return limsPcrRecordSampleArr;
        }

        function GetLimsPcrRecord() {
            var limsPcrRecord = {};
            limsPcrRecord.id=$("#pcrRecordId").val();
            limsPcrRecord.caseId=$("#caseId").val();
            limsPcrRecord.pcrPersonId1 = $("option:selected", "#pcrPerson1").val();
            limsPcrRecord.pcrPersonName1 = $("option:selected", "#pcrPerson1").text();
            limsPcrRecord.pcrPersonId2 = $("option:selected", "#pcrPerson2").val();
            limsPcrRecord.pcrPersonName2 = $("option:selected", "#pcrPerson2").text();
            limsPcrRecord.pcrStartDatetime = $("#pcrStartDatetime").val();
            limsPcrRecord.pcrEndDatetime = $("#pcrEndDatetime").val();
            limsPcrRecord.pcrProgram = $("option:selected", "#pcrProgram").val();
            limsPcrRecord.pcrInstrument = $("option:selected", "#pcrInstrument").val();
            limsPcrRecord.pcrSystem = $("option:selected", "#pcrSystem").val();
            limsPcrRecord.pcrReagent = $("option:selected", "#pcrReagent").val();

            return limsPcrRecord;
        }

        function SavePcrForm() {
            var operateType=$("#operateType").val();
            var limsPcrRecord = GetLimsPcrRecord();
            var limsPcrRecordSampleList = GetLimsPcrRecordSample();

            if(limsPcrRecord.pcrStartDatetime == ""){
                alert("请选择扩增开始时间！");
                $("#pcrStartDatetime").addClass("text_error_border");
                return;
            }else{
                $("#pcrStartDatetime").removeClass("text_error_border");
            }

            if(limsPcrRecord.pcrEndDatetime == ""){
                alert("请选择扩增结束时间！");
                $("#pcrEndDatetime").addClass("text_error_border");
                return;
            }else{
                $("#pcrEndDatetime").removeClass("text_error_border");
            }


            var pcrPerson2= $("option:selected","#pcrPerson2").val();
            if(pcrPerson2 == ""){
                alert("请选择扩增人！");
                return;
            }


            if(limsPcrRecordSampleList.length ==0){
                alert("请添加扩增检材！");
                return;
            }

            var params = {
                "limsPcrRecord": limsPcrRecord,
                "limsPcrRecordSampleList": limsPcrRecordSampleList
            };

            $.ajax({
                url: "<%=path%>/center/pcr/saveRecord.html?operateType="+operateType,
                type: "post",
                data: JSON.stringify(params),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success:function(data){
                    if(data.success || data.success == true || data.success == "true") {
                        $("#pcrRecordId").val(data.pcrRecordId);
                        $("#PcrRecordModal").modal('show');
                    }
                }
            });
        }

        function downloadPcrRecordDoc() {
            var pcrRecordId=$("#pcrRecordId").val();
            location.href="<%=path%>/center/pcr/pcrDoc.html?pcrRecordId=" + pcrRecordId;
        }

        function finishExtract() {
            $("#PcrRecordModal").modal('hide');
        }

        function AddSampleRows(sampleInfoList) {
            var hideRowHtml = $("tr.hide", "#pcrTable").html();
            var newRowHtml;
            var sample;
            var existsInRow = false;
            for(var i = 0; i < sampleInfoList.length; i++){
                sample = sampleInfoList[i];

                $("input[name='sampleId']", "#pcrTable").each(function(){
                    var valInRow = $(this).val();
                    if(valInRow == sample.id){
                        existsInRow = true;
                    }
                });

                if(existsInRow) continue;

                newRowHtml = "<tr>" + hideRowHtml + "</tr>";
                $("#pcrTable").append(newRowHtml);
                $("tr:eq(0)", "#pcrTable").find("input[name='sampleNo']").val(sample.sampleNo);
                $("tr:eq(0)", "#pcrTable").find("input[name='sampleId']").val(sample.id);
                $("tr:eq(0)", "#pcrTable").find("button[name='delPcrBtn']").on("click",function(){
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

        $("button[name='delPcrBtn']","#pcrTable").on("click",function(){
            $(this).parents("tr").remove();
        });

        $("#backToListBtn").on("click",function(){
            location.href='<%=path%>/center/pcr/list.html';
        });

        $("#saveBtn").on("click",function(){
            var pcrStartDatetime = $("#pcrStartDatetime").val();
            var extractDatetime = $("#extractDatetime").val();

            var d1 = new Date(pcrStartDatetime.replace(/\-/g, "\/"));
            var d2 = new Date(extractDatetime.replace(/\-/g, "\/"));

            if(pcrStartDatetime!="" && extractDatetime!="" && d1 < d2)
            {
                alert("扩增时间小于提取时间，请重新选择时间!");
                return false;
            }

            var pcrStartDate = new Date(pcrStartDatetime).getTime();
            var extractDate = new Date(extractDatetime).getTime();
            var total = pcrStartDate - extractDate;

            var days = Math.floor(total/(24*3600*1000));

            var hour = total%(24*60*60*1000);
            var hours =Math.floor(hour/(3600*1000));

            if(psaExist()){
                if(days < 1){
                    alert("精斑检材提取时间不到24小时，请重新选择时间!");
                    return false;
                }
            }else {
                if(days < 1 && hours < 1){
                    alert("样本检材提取时间不到1小时，请重新选择时间!");
                    return false;
                }
            }

            $('#saveBtn').attr("disabled", "disabled");

            SavePcrForm();
        });

        function psaExist() {
            var $sampleTypeNameTR = $("tr", "#pcrTable");
            var sampleTypeNameCnt = $sampleTypeNameTR.length;
            var sampleTypeName = "";
            for (var i = 0; i < sampleTypeNameCnt; i++) {
                sampleTypeName = $("input[name='sampleTypeName']", $sampleTypeNameTR.get(i)).val();
                if (sampleTypeName == "精斑") {
                    return true;
                    break;
                }
            }

            return false;
        }


        $("#OpenDocBtn").on("click",function(){
            downloadPcrRecordDoc();
        });

        $("#FinishedBtn").on("click",function(){
            finishExtract();
        });


        $("#PcrRecordModal").on('hidden.bs.modal', function(){
            location.href='<%=path%>/center/pcr/list.html';
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


