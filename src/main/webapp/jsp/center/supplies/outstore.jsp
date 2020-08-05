<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/include.jsp" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <!-- BEGIN META -->
    <meta charset="utf-8">
    <!-- END META -->
    <title>
        湛江DNA实验室信息系统
    </title>
    <%@ include file="../../common/link.jsp" %>
</head>
<body>
<!-- BEGIN SECTION -->
<section id="container">
    <!-- BEGIN MAIN CONTENT -->

    <section id="main-content">
        <section class="wrapper site-min-height">
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            <span class="label label-primary">出库</span>
                            <span class="tools pull-right">
                                <a href="javascript:;" class="fa fa-chevron-down"></a>
                            </span>
                        </header>
                        <div class="panel-body">
                            <form id="outstoreForm" class="form-horizontal tasi-form" method="get">
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">条码号：<i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <select id="barcode" name="barcode" class="form-control required">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${recordInfoList}" var="list" varStatus="g">
                                                <option value="${list.barcode}" <c:if test="${list.barcode eq storageRecordInfo.barcode}">selected</c:if>>${list.barcode}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">出库名称：<i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <select id="reagentSuppliesInfoId" name="reagentSuppliesInfoId" class="form-control required">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${reagentSuppliesInfoList}" var="list" varStatus="g">
                                                <option value="${list.id}" <c:if test="${storageRecordInfo.reagentSuppliesInfoId eq list.id}">selected</c:if>>${list.reagentName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">出库数量：<i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input class="form-control required" name="storageNum" id="storageNum" type="text" value="${storageRecordInfo.storageNum}">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">出库人：<i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input class="form-control required" name="storagePerson" id="storagePerson" type="text" value="${storageRecordInfo.storagePerson}">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">出库时间：<i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input  type="text" class="form_datetime form-control required" name="storageTime" id="storageTime" value="<fmt:formatDate value='${storageRecordInfo.storageDatetime}' pattern='yyyy-MM-dd hh:mm'/>" readonly>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">有效日期：<i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form_datetime form-control required" id="effectiveTime" name="effectiveTime" value="<fmt:formatDate value='${storageRecordInfo.effectiveDatetime}' pattern='yyyy-MM-dd hh:mm'/>" readonly>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">备注：</label>
                                    <div class="col-sm-5">
                                        <textarea class="form-control" rows="2" id="storageRemark" name="storageRemark">${storageRecordInfo.storageRemark}</textarea>
                                    </div>
                                </div>
                                <div class="form-group pull-right">
                                    <input type="hidden" id="operateType" name="operateType" value="${storageRecordInfo.operateType}">
                                    <input type="hidden" id="id" name="id" value="${storageRecordInfo.id}">
                                    <input type="hidden" id="recordType" name="recordType" value="1">
                                    <button class="btn btn-primary" type="button" id="saveBtn"><i class="fa fa-check"></i> 保 存</button>
                                    <button class="btn btn-primary" id="backBtn" type="button"><i class="fa fa-reply"></i> 返 回 </button>
                                </div>
                            </form>
                        </div>
                    </section>
                </div>
            </div>

            <div class="modal fade" id="EditModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
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
                            <button data-dismiss="modal" class="btn btn-default" type="button" id="backwardBtn"><i class="fa fa-reply"></i> 返 回</button>
                        </div>
                    </div>
                </div>
            </div>

        </section>
    </section>
    <!-- END MAIN CONTENT -->
</section>
<%@ include file="../../common/script.jsp" %>
<script>

    $(function(){
        "use strict";

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

        $("#barcode").on("change", function(){

            var barcode = $("#barcode").val();

            if (barcode == "") {
                $("#reagentSuppliesInfoId").val("");
                $("#effectiveTime").val("");
                return false;
            }

            $.ajax({
                url : "<%=path%>/center/storage/selectStorageInfoByBarcode.html?barcode=" + barcode,
                type:"get",
                dataType : "json",
                success : function(data) {
                    if(data.success || data.success == true || data.success == "true") {
                        $("#id").val(data.id);
                        $("#reagentSuppliesInfoId").val(data.reagentSuppliesInfoId);
                        $("#effectiveTime").val(data.effectiveTime);
                    }
                },
                error: function(e){
                    alert(e);
                }
            });
        });

        $("#storageNum").blur(function(){
            var id = $("#reagentSuppliesInfoId").val();

            $.ajax({
                url : "<%=path%>/center/storage/selectStorageInfo.html?id=" + id,
                type:"get",
                dataType : "json",
                success : function(data) {
                    if(data.success || data.success == true || data.success == "true") {
                        var storageNum = $("#storageNum").val();

                        if (parseInt(storageNum) > parseInt(data.storageNum)) {
                            var result = "";
                            result = "出库数量大于库存数量(库存数量为"+ data.storageNum + ")，请重新输入！";
                            alert(result);

                            $("#storageNum").val("");
                            $("#storageNum").focus();
                        }
                    }
                },
                error: function(e){
                    alert(e);
                }
            });
        });

        $("button[name='modifyBtn']", "#storageRecordInfoListTbody").on("click", function(){
            var storageRecordInfoId = $("#storageRecordInfoId", $(this).parent()).val();

            $.ajax({
                url: "<%=path%>/center/storage/getStorageRecordInfo.html?id=" + storageRecordInfoId,
                type:"get",
                dataType:"json",
                cache:false,
                success:function(data){
                    $("#id").val(data.id);
                    $("#barcode").val(data.barcode);
                    $("#reagentSuppliesInfoId").val(data.reagentSuppliesInfoId);
                    $("#storageNum").val(data.storageNum);
                    $("#storagePerson").val(data.storagePerson);
                    $("#storageTime").val(data.storageTime);
                    $("#effectiveTime").val(data.effectiveTime);
                    $("#storageRemark").val(data.storageRemark);
                    $("#operateType").val("2");
                    $("#recordType").val(data.recordType);
                },
                error : function(e) {
                    alert(e);
                }
            });
        });

        function checkInputValidation(){

            var errCnt = 0;
            $(".required", "#outstoreForm").each(function(){
                if($(this).val() == "" || $.trim($(this).val()) == ""){
                    $("div.has-error",$(this).parents("div.form-group")).removeClass("hide");
                    errCnt++;
                }else{
                    $("div.has-error",$(this).parents("div.form-group")).addClass("hide");
                }
            });

            if(errCnt > 0) {
                alert("请补全必填项！");
                return false;
            }

            var storageNum = $("#storageNum").val();

            if (isNaN(storageNum)) {
                alert('只能输入数字！');
                $("#storageNum").val("");
                $("#storageNum").focus();
                return false;
            }

            if (storageNum <= 0) {
                alert("输入的数值不能小于0，请重新输入！");
                $("#storageNum").val("");
                $("#storageNum").focus();
                return false;
            }

            if (storageNum.length > 10) {
                alert("输入的数值太大，请重新输入！");
                $("#storageNum").val("");
                $("#storageNum").focus();
                return false;
            }

            return true;
        }

        function GetStorageRecordInfo() {
            var storageRecordInfo = {};

            storageRecordInfo.id = $("#id").val();
            storageRecordInfo.barcode = $("#barcode").val();
            storageRecordInfo.reagentSuppliesInfoId = $("#reagentSuppliesInfoId").val();
            storageRecordInfo.storageNum = $("#storageNum").val().replace(/\b(0+)/gi,"");
            storageRecordInfo.storagePerson = $("#storagePerson").val();
            storageRecordInfo.storageTime = $("#storageTime").val();
            storageRecordInfo.effectiveTime = $("#effectiveTime").val();
            storageRecordInfo.storageRemark = $("#storageRemark").val();
            storageRecordInfo.operateType = $("#operateType").val();
            storageRecordInfo.recordType = $("#recordType").val();

            return storageRecordInfo;
        }

        $("#saveBtn").on("click", function(){
            if(!checkInputValidation()){
                return;
            }

            var storageRecordInfo = GetStorageRecordInfo();

            $.ajax({
                url: "<%=path%>/center/storage/saveOutStore.html",
                type:"post",
                contentType: "application/json; charset=utf-8",
                data : JSON.stringify(storageRecordInfo),
                dataType : "json",
                success:function(data){
                    if(data.success || data.success == true || data.success == "true") {
                        $("#EditModal").modal('show');
                    }else {
                        alert("保存失败！");
                    }
                },
                error : function(e) {
                    alert(e);
                }
            });
        });

        $("#EditModal").on('hidden.bs.modal', function(){
            location.href='<%=path%>/center/storage/outStorageList.html';
        });

        $("#backBtn").on("click",function(){
            location.href='<%=path%>/center/storage/outStorageList.html';
        });
    });
</script>
<!-- END JS -->
</body>
</html>