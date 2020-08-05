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
                        <span class="label label-primary">上样参数</span>
                               <span class="tools pull-right">
                               <a href="javascript:;" class="fa fa-chevron-down"></a>
                            </span>
                    </header>
                    <div class="panel-body">
                        <form class="form-horizontal tasi-form" method="get">
                            <div class="form-group">
                                <label class="col-sm-2 control-label" style="text-align: right;">操作人 <i class="fa fa-asterisk color_red"></i></label>
                                <div class="col-sm-2">
                                    <select class="form-control" id="syPerson1" value="${limsSyRecord.operatePersonId1}">
                                        <c:forEach items="${labUserList}" var="labUser" varStatus="lu">
                                            <option value="${labUser.id}" <c:if test="${limsSyRecord.operatePersonId1 eq labUser.id}">selected</c:if>>${labUser.userName}</option>

                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-sm-2">
                                    <select class="form-control" id="syPerson2" value="${limsSyRecord.operatePersonId2}">
                                        <option value="">请选择...</option>
                                        <c:forEach items="${labUserList}" var="labUser" varStatus="lu">
                                            <option value="${labUser.id}" <c:if test="${limsSyRecord.operatePersonId2 eq labUser.id}">selected</c:if>>${labUser.userName}</option>

                                        </c:forEach>
                                    </select>
                                </div>
                                <label class="col-sm-2 control-label" style="text-align: right;">电泳时间 <i class="fa fa-asterisk color_red"></i></label>
                                <div class="col-sm-4">
                                    <div class="input-group input-large">
                                        <input type="text" class="form-control form_datetime" placeholder="开始时间" id="startDatetime"
                                               value="<fmt:formatDate value='${limsSyRecord.startDatetime}' pattern='yyyy-MM-dd HH:mm'/>" readonly/>
                                            <span class="input-group-addon">
                                            至
                                            </span>
                                        <input type="text" class="form-control form_datetime" placeholder="结束时间" id="endDatetime"
                                               value="<fmt:formatDate value='${limsSyRecord.endDatetime}' pattern='yyyy-MM-dd HH:mm'/>" readonly/>
                                    </div>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" style="text-align: right;">产物ul</label>
                                <div class="col-sm-4">
                                    <select class="form-control" id="chanwu" value="${limsSyRecord.chanwuFlag}">
                                        <c:forEach items="${chanwuList}" var="chanwu" varStatus="pp">
                                            <option value="${chanwu.dictCode}" <c:if test="${limsSyRecord.chanwuFlag eq chanwu.dictCode}">selected</c:if>>${chanwu.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label class="col-sm-2 control-label" style="text-align: right;">甲酰胺ul</label>
                                <div class="col-sm-4">
                                    <select class="form-control" id="jiaxianan" value="${limsSyRecord.jiaxiananFlag}">
                                        <c:forEach items="${jiaxiananList}" var="jiaxianan" varStatus="pp">
                                            <option value="${jiaxianan.dictCode}" <c:if test="${limsSyRecord.jiaxiananFlag eq jiaxianan.dictCode}">selected</c:if>>${jiaxianan.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" style="text-align: right;">内标ul</label>
                                <div class="col-sm-4">
                                    <select class="form-control" id="neibiaoul" value="${limsSyRecord.neibiaoUlFlag}">
                                        <c:forEach items="${neibiaoulList}" var="neibiaoul" varStatus="pp">
                                            <option value="${neibiaoul.dictCode}" <c:if test="${limsSyRecord.neibiaoUlFlag eq neibiaoul.dictCode}">selected</c:if>>${neibiaoul.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <label class="col-sm-2 control-label" style="text-align: right;">内 标</label>
                                <div class="col-sm-4">
                                    <select class="form-control" id="neibiao" value="${limsSyRecord.neibiaoFlag}">
                                        <c:forEach items="${neibiaoList}" var="neibiao" varStatus="pp">
                                            <option value="${neibiao.dictCode}" <c:if test="${limsSyRecord.neibiaoFlag eq neibiao.dictCode}">selected</c:if>>${neibiao.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="col-sm-2 control-label" style="text-align: right;">电泳仪</label>
                                <div class="col-sm-4">
                                    <select class="form-control" id="elecInstrument" value="${limsSyRecord.elecInstrument}">
                                        <c:forEach items="${elecInstrumentList}" var="elecInstrument" varStatus="pp">
                                            <option value="${elecInstrument.id}" <c:if test="${limsSyRecord.elecInstrument eq elecInstrument.equipmentNo}">selected</c:if>>${elecInstrument.equipmentName}</option>
                                        </c:forEach>
                                        <option value="">其他</option>
                                    </select>
                                </div>
                                <%--<label class="col-sm-2 control-label" style="text-align: right;">上样板号 <i class="fa fa-asterisk color_red"></i></label>
                                <div class="col-sm-4">
                                    <input type="text" class="form-control" id="boardNo" value="${limsSyRecord.boardNo}">
                                </div>--%>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label" style="text-align: right;"><strong>导入上样表</strong></label>
                                <div class="col-sm-3">
                                    <input type="file" name="syFile" id="syFile" class="hide"/>
                                    <input type="text" id="syFileTxt" class="form-control" readonly="readonly"/>
                                </div>
                                <div class="col-sm-2">
                                    <button class="btn btn-info" type="button" id="browserBtn"><i class="fa  fa-folder-open"></i> 浏览...</button>
                                    <button class="btn btn-primary" type="button" id="importBtn"><i class="fa fa-download"></i> 导入</button>
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
                        <span class="label label-primary">上样检材列表</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                    </header>
                    <div class="panel-body">
                        <form class="form-horizontal tasi-form">

                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>检材编号</th>
                                    <th>检材名称</th>
                                    <th>检材类型</th>
                                    <th>上样板号<i class="fa fa-asterisk color_red"></i></th>
                                    <th>板孔位置</th>
                                    <th>删除</th>
                                    <%--<th><input type="checkbox" name="allChecked" id="allChecked" checked/>全选</th>--%>
                                </tr>
                                </thead>
                                <tbody id="syTable">
                                <c:forEach items="${limsSyRecordSampleList}" var="sample" varStatus="s">
                                    <tr>
                                        <td></td>
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
                                            <input name="boardNo" type="text" class="form-control small"  style='width:84%;float:left;' value="${sample.boardNo}">
                                            <a href="#" name="btnBoardNoPullDown" title="向下填充板号"><img border="0" src="<%=path%>/images/field_Down.bmp" style="cursor:pointer;margin-left:3px;margin-top:5px;" ></a>
                                        </td>
                                        <td>
                                            <select name="samplePosition" class="form-control small" value="${sample.samplePosition}" style='width:84%;float:left;'>
                                                <c:forEach items="${samplePositionList}" var="position" varStatus="s">
                                                    <option value="${position}" <c:if test="${position eq sample.samplePosition}">selected</c:if>>${position}</option>
                                                </c:forEach>
                                            </select>
                                            <%--<a href="#" name="btnPositionPullDown" title="向下填充板位"><img border="0" src="<%=path%>/images/field_Down.bmp" style="cursor:pointer;margin-left:3px;margin-top:5px;" ></a>--%>
                                        </td>
                                        <td>
                                            <input type="hidden" name="id" value="${sample.id}"/>
                                            <input type="hidden" name="sampleId" value="${sample.sampleId}"/>
                                            <input type="hidden" name="standardFlag" value="${sample.standardFlag}"/>
                                            <button type="button" name="delSySampleBtn" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 删除</button>
                                        </td>
                                            <%--<td><input type="checkbox" name="box" checked></td>--%>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                            <div class="form-group">
                                <div class="col-lg-offset-10 col-lg-12">
                                    <input type="hidden" id="operateType" value="${operateType}"/>
                                    <input type="hidden" id="syRecordId" value="${limsSyRecord.id}"/>
                                    <input type="hidden" id="caseId" value="${limsSyRecord.caseId}"/>
                                    <input type="hidden" id="pcrDatetime" value="<fmt:formatDate value='${limsPcrRecord.pcrEndDatetime}' pattern='yyyy-MM-dd HH:mm'/>">
                                    <button class="btn btn-primary" type="button" id="saveBtn"><i class="fa fa-check"></i> 保 存</button>

                                    <button class="btn btn-info" type="button" id="backToListBtn"><i class="fa fa-reply"></i> 返回列表页面</button>
                                </div>
                            </div>
                        </form>
                    </div>
                </section>
            </div>
        </div>

        <div class="modal fade" id="SyRecordModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
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
                        <%--<button class="btn btn-success" type="button" id="OpenTxtBtn"><i class="fa fa-file"></i> 下载上样表（txt）</button>
                        &nbsp;--%>
                        <button class="btn btn-success" type="button" id="OpenDocBtn"><i class="fa fa-file-text-o"></i> 下载上样记录表</button>
                        &nbsp;
                        <button class="btn btn-default" type="button" id="FinishedBtn"><i class="fa fa-list-alt"></i> 完成</button>
                    </div>
                </div>
            </div>
        </div>

        <div class="modal fade" id="StandardSampleModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">
                            添加参照样本
                        </h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal tasi-form">
                            <div class="form-group">
                                <label class="control-label col-md-3">位置 <i class="fa fa-asterisk color_red"></i></label>
                                <div class="col-md-5">
                                    <select name="postionSelect" class="form-control small">
                                        <c:forEach items="${positionArr}" var="pos" varStatus="s">
                                            <option value="${pos}">${pos}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                                <div class="col-md-2 has-error hide">
                                    <p class="help-block">必填项</p>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3">标准样本名称 <i class="fa fa-asterisk color_red"></i></label>
                                <div class="col-md-5">
                                    <select name="standardSampleName" class="form-control small">
                                        <option value="9947A">9947A</option>
                                        <option value="YIN">YIN</option>
                                        <option value="LAD">LAD</option>
                                    </select>
                                </div>
                                <div class="col-md-2 has-error hide">
                                    <p class="help-block">必填项</p>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <button class="btn btn-success" type="button" id="SaveStandardSampleBtn">确定</button>
                        <button data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
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

    generateIdx();

    function generateIdx() {
        $("tr","#syTable").each(function(){
            $("td:first", $(this)).text($(this).index()+1);
        });
    }

    $(function(){
        'use strict';

        $("#startDatetime").on("change",function(){
            var startDatetime = $("#startDatetime").val();
            var dateTime = new Date(startDatetime).getTime();
            var endDataTime = new Date(dateTime + (2*60*60*1000 + 30*60*1000));
            var Y = endDataTime.getFullYear() + "-";
            var M = (endDataTime.getMonth() + 1 < 10?"0"+(endDataTime.getMonth() + 1):endDataTime.getMonth() + 1) + "-";
            var D = (endDataTime.getDate() + 1 < 10?"0"+(endDataTime.getDate()):endDataTime.getDate()) + " ";
            var h = (endDataTime.getHours() + 1 < 10?"0"+(endDataTime.getHours()):endDataTime.getHours()) + ":";
            var m = (endDataTime.getMinutes() + 1 < 10?"0"+(endDataTime.getMinutes()):endDataTime.getMinutes());
            $("#endDatetime").val(Y + M + D + h + m);
        });

        $("a[name='btnBoardNoPullDown']","#syTable").on("click", function(){
            var boardNo = $("input[name='boardNo']", $(this).parent().parent()).val();

            if (boardNo == "") {
                alert("请输入上样板号！");
                $("input[name='boardNo']", $(this).parent().parent()).focus();
            }else {
                evaluate($(this), boardNo);
            }

        });

        function evaluate (obj, boardNo) {
            var totalRows = $("tr", "#syTable").length;
            var curIdx = $(obj).parents("tr").index();

            var startIdx = curIdx + 1;
            var endRowIdx = curIdx + 96;
            for(var i = startIdx; i < endRowIdx; i++){
                $("input[name='boardNo']","tbody tr:eq("+ i +")").val(boardNo);
            }

            var syRow = totalRows - curIdx;         //剩余行数
            if(syRow > 96){
                $("input[name='boardNo']","tbody tr:eq("+(endRowIdx)+")").focus();
                alert("样本超出96个，请转到" + (endRowIdx + 1) + "行添加新的板号！");
            }
        }

        $("a[name='btnPositionPullDown']","#syTable").on("click", function(){
            var samplePosition = $("select[name='samplePosition']", $(this).parent().parent()).val();

            if (samplePosition == "") {
                alert("请选择板孔位值！");
                return;
            }

            evaluatePosition($(this), samplePosition);
        });

        function evaluatePosition(obj, samplePosition) {
            var totalRows = $("tr", "#syTable").length;
            var curIdx = $(obj).parents("tr").index();

            var startIdx = curIdx + 1;
            var endRowIdx = curIdx + 96;
            var selectedIndex = 0;

            for(var i = startIdx; i < endRowIdx; i++){
//                var positionValue = $("select[name='samplePosition']").options[i].value;

                selectedIndex = $("select[name='samplePosition']").selectedIndex;
                $("select[name='samplePosition'] option[selectedIndex]").prop("selected", true);

            }

            var syRow = totalRows - curIdx;         //剩余行数
            if(syRow > 96){
                alert("样本超出96个，请转到" + (endRowIdx + 1) + "行选择新的板孔！");
            }
        }

        $("#browserBtn").on("click",function(){
            $("#syFile").click();
        });

        $("#syFile").on("change",function(){
            $("#syFileTxt").val($(this).val());
        });

        $("#importBtn").on("click",function(){
            importBtn();
        });

        function importBtn(){
            var caseId = $("#caseId").val();

            $.ajaxFileUpload({
                cache:false,
                url:"<%=path%>/center/sy/upLoadSyTable.html?caseId="+caseId,
                secureuri:false,
                fileElementId:'syFile',
                dataType: 'json',
                success: function (data) {
                    if(data.success || data.success == true || data.success == "true") {

                        var syRecordSampleList = data.syRecordSampleList;
                        var syRecordLen = syRecordSampleList.length;

                        if (syRecordLen > 0){
                            $("#syTable").empty();

                            var samplePositionList = data.samplePositionList;
                            var samplePosLen = samplePositionList.length;

                            var syRecordSample;

                            for (var i = 0; i < syRecordLen; i++) {
                                syRecordSample = syRecordSampleList[i];
                                var newRowHtml = "";;
                                newRowHtml += "<tr><td></td>";
                                newRowHtml += "<td><input name='sampleNo' type='text' class='form-control small' value='"+ syRecordSample.sampleNo +"' readonly='readonly'></td>";
                                newRowHtml += "<td><input name='sampleName' type='text' class='form-control small' value='"+ syRecordSample.sampleName +"' readonly='readonly</td>'>";
                                newRowHtml += "<td><input name='sampleTypeName' type='text' class='form-control small' value='"+ syRecordSample.sampleTypeName +"' readonly='readonly'></td>";
                                newRowHtml += "<td><input name='boardNo' type='text' class='form-control small' style='width:84%;float:left;' /><a href='#' name='btnBoardNoPullDown' title='向下填充板号'><img border='0' src='<%=path%>/images/field_Down.bmp' style='cursor:pointer;margin-left:3px;margin-top:5px;' ></a></td>";

                                newRowHtml += "<td><select name='samplePosition' class='form-control small' style='width:84%;float:left;' value='"+ syRecordSample.samplePosition +"'>";
                                if (samplePosLen > 0){
                                    var position = syRecordSample.samplePosition;

                                    for (var j = 0;j < samplePosLen; j++){
                                        var samplePosition = samplePositionList[j];
                                        if (position == samplePosition){
                                            newRowHtml += "<option value='"+ samplePosition +"' selected>"+ samplePosition +"</option>";
                                        }else {
                                            newRowHtml += "<option value='"+ samplePosition +"'>"+ samplePosition +"</option>";
                                        }
                                    }
                                }else {
                                    newRowHtml += "<option value=''></option>";
                                }
                                newRowHtml += "</select></td>";

                                newRowHtml += "<td><input type='hidden' name='id' value='"+ syRecordSample.id +"'/>";
                                newRowHtml += "<input type='hidden' name='sampleId' value='"+ syRecordSample.sampleId +"'/>";
                                newRowHtml += "<input type='hidden' name='standardFlag' value='"+ syRecordSample.standardFlag +"'/>";
                                newRowHtml += "<button type='button' name='delSySampleBtn' class='btn btn-danger btn-sm'><i class='fa fa-trash-o'></i> 删除</button>";
                                newRowHtml += "</td></tr>";

                                $("#syTable").append(newRowHtml);

                                $("button[name='delSySampleBtn']","#syTable").on("click",function(){
                                    $(this).parents("tr").remove();
                                    generateIdx();
                                });
                            }
                        }else {
                            alert("上样表中没有当前案件检材!");
                            window.location.reload();
                        }

                    }
                },
                error: function (data,status,e) {
                    alert(e);
                }
            });

            return true;
        }

        $("button[name='delSySampleBtn']","#syTable").on("click",function(){
            $(this).parents("tr").remove();
            generateIdx();
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

        function GetLimsSyRecordSample() {
            var limsSyRecordSampleArr = new Array();
            var limsSyRecordSample;

            $("tr", "#syTable").each(function(){
                limsSyRecordSample = {};
                var $TR = $(this);
                limsSyRecordSample.id=$("input[name='id']", $TR).val();
                limsSyRecordSample.sampleId = $("input[name='sampleId']", $TR).val();
                limsSyRecordSample.standardFlag = $("input[name='standardFlag']", $TR).val();
                limsSyRecordSample.sampleNo = $("input[name='sampleNo']", $TR).val();
                limsSyRecordSample.boardNo = $("input[name='boardNo']", $TR).val();
                limsSyRecordSample.samplePosition = $("select[name='samplePosition']", $TR).find("option:selected").val();

                if (limsSyRecordSample.standardFlag == "null")
                    limsSyRecordSample.standardFlag = ""

                limsSyRecordSampleArr.push(limsSyRecordSample);
            });

            return limsSyRecordSampleArr;
        }

        function GetLimsSyRecord() {
            var limsSyRecord = {};
            limsSyRecord.id=$("#syRecordId").val();
            limsSyRecord.caseId=$("#caseId").val();
            limsSyRecord.boardNo = getBoardNoValue();
            limsSyRecord.operatePersonId1 = $("option:selected", "#syPerson1").val();
            limsSyRecord.operatePersonName1 = $("option:selected", "#syPerson1").text();
            limsSyRecord.operatePersonId2 = $("option:selected", "#syPerson2").val();
            limsSyRecord.operatePersonName2 = $("option:selected", "#syPerson2").text();
            limsSyRecord.startDatetime = $("#startDatetime").val();
            limsSyRecord.endDatetime = $("#endDatetime").val();
            limsSyRecord.chanwuFlag = $("option:selected", "#chanwu").val();
            limsSyRecord.jiaxiananFlag = $("option:selected", "#jiaxianan").val();
            limsSyRecord.neibiaoUlFlag = $("option:selected", "#neibiaoul").val();
            limsSyRecord.neibiaoFlag = $("option:selected", "#neibiao").val();
            limsSyRecord.elecInstrument = $("option:selected", "#elecInstrument").val();

            return limsSyRecord;
        }

        function getBoardNoValue() {
            var $boardNoTR = $("tr", "#syTable");
            var boardNoCnt = $boardNoTR.length;
            var boardNo = "";
            var newBoardNo =  $("input[name='boardNo']", $boardNoTR.get(0)).val();
            var oldBoardNo = "";
            var operateType=$("#operateType").val();
            if (operateType == "2") {
                boardNo = newBoardNo;
            }else {
                for (var i = 0; i < boardNoCnt; i++) {
                    oldBoardNo = $("input[name='boardNo']", $boardNoTR.get(i)).val();
                    if (oldBoardNo != newBoardNo) {
                        boardNo += newBoardNo + "," + oldBoardNo + ",";
                        newBoardNo = oldBoardNo;
                    }
                }

                if (boardNo == "" || boardNo.length == 0)
                    boardNo = newBoardNo;
            }

            return  boardNo;
        }

        function SaveSyForm() {

            var operateType=$("#operateType").val();
            var limsSyRecord = GetLimsSyRecord();
            var limsSyRecordSampleList = GetLimsSyRecordSample();

            var syPerson2= $("option:selected","#syPerson2").val();
            if(syPerson2 == ""){
                alert("请选择上样人！");
                return;
            }

            if(limsSyRecord.startDatetime == ""){
                alert("请选择电泳开始时间！");
                $("#startDatetime").addClass("text_error_border");
                return;
            }else{
                $("#startDatetime").removeClass("text_error_border");
            }

            if(limsSyRecord.endDatetime == ""){
                alert("请选择电泳结束时间！");
                $("#endDatetime").addClass("text_error_border");
                return;
            }else{
                $("#endDatetime").removeClass("text_error_border");
            }

            var boardNo = getBoardNo();
            if(boardNo == "" || boardNo.length == 0){
                alert("请输入上样板号！");
                return;
            }

            if(limsSyRecordSampleList.length ==0){
                alert("请添加上样检材！");
                return;
            }

            var samplePositionArr = new Array();
            var hasSamePosition = false;
            $("select[name='samplePosition']", "#syTable").each(function(){
                var pos = $("option:selected", $(this)).val();
                if($.inArray(pos, samplePositionArr) != -1){
                    hasSamePosition = true;
                }else {
                    samplePositionArr.push(pos);
                }
            });

//            if(hasSamePosition){
//                alert("检材位置不能相同！");
//                return;
//            }

            var params = {
                "limsSyRecord": limsSyRecord,
                "limsSyRecordSampleList": limsSyRecordSampleList
            };

            $.ajax({
                url: "<%=path%>/center/sy/saveRecord.html?operateType="+operateType,
                type: "post",
                data: JSON.stringify(params),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success:function(data){
                    if(data.success || data.success == true || data.success == "true") {
                        $("#syRecordId").val(data.syRecordIds);
                        $("#SyRecordModal").modal('show');
                    }
                }
            });
        }

        function getBoardNo () {
            var $boardNoTR = $("tr", "#syTable");
            var boardNoCnt = $boardNoTR.length;
            var boardNo = "";
            for (var i = 0; i < boardNoCnt; i++) {
                boardNo = $("input[name='boardNo']", $boardNoTR.get(i)).val();
                if (boardNo == "" || boardNo.length == 0)
                    break;
            }

            return boardNo;
        }

        function downloadSytable(){
            var syRecordId=$("#syRecordId").val();
            location.href="<%=path%>/center/sy/downloadSytable.html?syRecordId=" + syRecordId;
        }

        function downloadPcrRecordDoc() {
            var syRecordId=$("#syRecordId").val();
            location.href="<%=path%>/center/sy/syDoc.html?syRecordId=" + syRecordId;
        }

        function finishExtract() {
            $("#SyRecordModal").modal('hide');
        }

        function AddSampleRows(sampleInfoList) {
            var sample = sampleInfoList[0];

            var hasNotInput = false;

            var $Rows = $("tr","#syTable");
            for (var colIdx = 1; colIdx <= 8; colIdx++) {
                for(var rowIdx = 0; rowIdx < 12; rowIdx++) {
                    var $row = $Rows.get(rowIdx);
                    if ($("textarea", $("td", $row).get(colIdx)).hasClass("notinput")) {
                        $("textarea", $("td", $row).get(colIdx)).val(sample.sampleNo);
                        $("input[name='id']", $("td", $row).get(colIdx)).val(sample.id);
                        $("input[name='sampleId']", $("td", $row).get(colIdx)).val(sample.sampleId);
                        $("input[name='standardFlag']",$("td", $row).get(colIdx)).val("0");

                        $("textarea", $("td", $row).get(colIdx)).removeClass("notinput");
                        hasNotInput = true;
                        break;
                    }
                }

                if(hasNotInput)
                    break;
            }

            /*      竖排
             for(var rowIdx = 0; rowIdx < 12; rowIdx++) {
             var $row = $Rows.get(rowIdx);
             for (var colIdx = 1; colIdx <= 8; colIdx++) {
             if ($("textarea", $("td", $row).get(colIdx)).hasClass("notinput")) {
             $("textarea", $("td", $row).get(colIdx)).val(sample.sampleNo);
             $("input[name='id']", $("td", $row).get(colIdx)).val(sample.id);
             $("input[name='sampleId']", $("td", $row).get(colIdx)).val(sample.sampleId);

             $("textarea", $("td", $row).get(colIdx)).removeClass("notinput");
             hasNotInput = true;
             break;
             }
             }

             if(hasNotInput)
             break;
             }
             */

            if(!hasNotInput) {
                alert("位置已满！");
                return;
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
                        $("#snoTxt").focus();
                        $(".has-error", $("#snoTxt").parents(".form-group")).find("p").html("");
                        $(".has-error", $("#snoTxt").parents(".form-group")).addClass("hide");
                    } else {
                        $(".has-error", $("#snoTxt").parents(".form-group")).find("p").html("检材编号["+sampleNo+"]不存在！");
                        $(".has-error", $("#snoTxt").parents(".form-group")).removeClass("hide");
                        $("#snoTxt").val("");
                        $("#snoTxt").focus();
                        return;
                    }
                }
            });
        }

        function ShowStandardSampleModalFtn(){
            $("#StandardSampleModal").modal('show');
        }


        function AddStandardSampleFtn(){
            var posNo = $("select[name='postionSelect']","#StandardSampleModal").find("option:selected").val();
            var standardSname = $("select[name='standardSampleName']","#StandardSampleModal").find("option:selected").val();

            var $targetTextarea = $("#" + posNo);
            $targetTextarea.val(standardSname);
            $targetTextarea.parent().find("input[name='standardFlag']").val("1");

            $targetTextarea.removeClass("notinput");
            $("#StandardSampleModal").modal('hide');
        }

        function importSytableFtn(){
            $.ajaxFileUpload({
                cache:false,
                url:"<%=path%>/center/sy/uploadSyTable.html",
                secureuri:false,
                fileElementId:'sytableFile',
                dataType: 'json',
                success: function (data) {
                    if(data.success || data.success == true || data.success == "true") {
                        var samplesInFile = data.syRecordSampleList;
                        var sampleInFile;
                        for(var i = 0; i < samplesInFile.length; i++){
                            sampleInFile = samplesInFile[i];
                            $("#" + sampleInFile.samplePosition).val(sampleInFile.sampleNo);
                            $("#" + sampleInFile.samplePosition).parent().find("input[name='id']").val(sampleInFile.id);
                            $("#" + sampleInFile.samplePosition).parent().find("input[name='sampleId']").val(sampleInFile.sampleId);
                            $("#" + sampleInFile.samplePosition).parent().find("input[name='standardFlag']").val(sampleInFile.standardFlag);

                            $("#" + sampleInFile.samplePosition).removeClass("notinput");
                        }
                    }
                },
                error: function (data,status,e) {
                    alert(e);
                }
            });

            return true;
        }

        $("#addBySampleNoBtn").on("click",function(){
            addSampleBySampleNo();
        });

        $("#snoTxt").on("keydown",function(event){
            if(event.keyCode == 13){
                addSampleBySampleNo();
                event.preventDefault();
            }
        });

        $("#backToListBtn").on("click",function(){
            location.href='<%=path%>/center/sy/list.html';
        });

        $("#saveBtn").on("click",function(){
            var startDatetime = $("#startDatetime").val();
            var pcrDatetime = $("#pcrDatetime").val();

            var d1 = new Date(startDatetime.replace(/\-/g, "\/"));
            var d2 = new Date(pcrDatetime.replace(/\-/g, "\/"));

            if(startDatetime!="" && pcrDatetime!="" && d1 < d2)
            {
                alert("上样时间小于扩增时间，请重新选择时间!");
                return false;
            }
            $('#saveBtn').attr("disabled", "disabled");
            SaveSyForm();
        });

        $("#OpenTxtBtn").on("click",function(){
            downloadSytable();
        });

        $("#OpenDocBtn").on("click",function(){
            downloadPcrRecordDoc();
        });

        $("#FinishedBtn").on("click",function(){
            finishExtract();
        });

        $("#SyRecordModal").on('hidden.bs.modal', function(){
            location.href='<%=path%>/center/sy/list.html';
        });

        //参照样本
        $("#addStandardSampleBtn").on("click",function(e){
            ShowStandardSampleModalFtn();
            e.preventDefault();
        });

        $("#SaveStandardSampleBtn").on("click",function(){
            AddStandardSampleFtn();
        });

        //导入上样表 start
        $("#browserSytableBtn").on("click",function(){
            $("#sytableFile").click();
        });

        $("#sytableFile").on("change",function(){
            $("#sytableTxt").val($(this).val());
        });

        $("#importSytableBtn").on("click",function(){
            importSytableFtn();
        });
        //导入上样表 end

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


