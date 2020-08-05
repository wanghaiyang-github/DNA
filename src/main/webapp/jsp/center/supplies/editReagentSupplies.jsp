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
                            <span class="label label-primary">设置</span>
                            <span class="tools pull-right">
                                <a href="javascript:;" class="fa fa-chevron-down"></a>
                            </span>
                        </header>
                        <div class="panel-body">
                            <form id="reagentSuppliesInfoForm" class="form-horizontal tasi-form">
                                <input type="hidden" name="id" id="id" value="${reagentSuppliesInfo.id}"/>
                                <input type="hidden" name="operateType" id="operateType" value="${operateType}"/>

                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">类型：<i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <label class="checkbox-inline">
                                            <input id="reagentFlag" name="reagentType" type="radio" value="1" <c:if test="${reagentSuppliesInfo.reagentType eq '1'}">checked</c:if>/>试剂
                                        </label>
                                        <label class="checkbox-inline">
                                            <input id="exclusiveFlag" name="reagentType" type="radio" value="2" <c:if test="${reagentSuppliesInfo.reagentType eq '2'}">checked</c:if> />耗材
                                        </label>
                                        <div class="col-sm-2 has-error hide">
                                            <p class="help-block">必填项</p>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">实验阶段<i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <select class="form-control required" id="experimentalStage">
                                            <option value="">全部</option>
                                            <c:forEach items="${experimentalStageList}" var="experimentalStage" varStatus="do">
                                                <option value="${experimentalStage.dictCode}" <c:if test="${experimentalStage.dictCode eq reagentSuppliesInfo.experimentalStage}">selected</c:if>>${experimentalStage.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">名称：<i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control required" id="reagentName" name="reagentName" value="${reagentSuppliesInfo.reagentName}">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">编号：<i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control required" id="reagentNo" name="reagentNo" value="${reagentSuppliesInfo.reagentNo}">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">品牌：</label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control" id="reagentBrand" name="reagentBrand" value="${reagentSuppliesInfo.reagentBrand}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">型号：</label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control" id="reagentModel" name="reagentModel" value="${reagentSuppliesInfo.reagentModel}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">价格：</label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control" id="reagentPrice" name="reagentPrice" value="${reagentSuppliesInfo.reagentPrice}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">厂商：</label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control" id="reagentFirm" name="reagentFirm" value="${reagentSuppliesInfo.reagentFirm}">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">描述：</label>
                                    <div class="col-sm-5">
                                        <textarea class="form-control" rows="2" id="remark" name="remark">${reagentSuppliesInfo.remark}</textarea>
                                    </div>
                                </div>
                                <div class="form-group pull-right">
                                    <button class="btn btn-lg btn-success" id="saveBtn" type="button"><i class="fa fa-check"></i> 保 存 </button>
                                    <button class="btn btn-lg btn-info" id="backBtn" type="button"><i class="fa fa-reply"></i> 返 回 </button>
                                </div>
                            </form>
                        </div>
                    </section>
                </div>
            </div>

            <div class="modal fade" id="resultModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
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

        function checkInputValidation(){

            if ($("input[name='reagentType']:checked").length == 0) {
                alert("请选择一种类型！");
                return false;
            }

            var errCnt = 0;
            $(".required", "#reagentSuppliesInfoForm").each(function(){
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

            return true;
        }

        $("#saveBtn").on("click", function(){
            if(!checkInputValidation()){
                return;
            }

            var reagentSuppliesInfo = GetReagentSuppliesInfo();
            var operateType = $("#operateType").val();

            $.ajax({
                url : "<%=path%>/center/storage/saveReagentSuppliesInfo.html?operateType=" + operateType,
                type:"post",
                contentType: "application/json; charset=utf-8",
                data : JSON.stringify(reagentSuppliesInfo),
                dataType : "json",
                success : function(data) {
                    if(data.success || data.success == true || data.success == "true") {
                        $("#resultModal").modal('show');
                    }else {
                        alert("保存失败！");
                    }
                },
                error: function(e){
                    alert(e);
                }
            });
        });

        function GetReagentSuppliesInfo() {
            var reagentSuppliesInfo = {};

            reagentSuppliesInfo.id=$("#id").val();
            reagentSuppliesInfo.experimentalStage = $("#experimentalStage").val();
            if ($("#reagentFlag").is(":checked")==true) {
                reagentSuppliesInfo.reagentType = $("#reagentFlag").val();
            }
            if ($("#exclusiveFlag").is(":checked")==true) {
                reagentSuppliesInfo.reagentType = $("#exclusiveFlag").val();
            }
            reagentSuppliesInfo.reagentName = $("#reagentName").val();
            reagentSuppliesInfo.reagentNo = $("#reagentNo").val();
            reagentSuppliesInfo.reagentBrand = $("#reagentBrand").val();
            reagentSuppliesInfo.reagentModel = $("#reagentModel").val();
            reagentSuppliesInfo.reagentPrice = $("#reagentPrice").val();
            reagentSuppliesInfo.reagentFirm = $("#reagentFirm").val();
            reagentSuppliesInfo.remark = $("#remark").val();

            return reagentSuppliesInfo;
        }

        $("#resultModal").on(
                'hidden.bs.modal', function(){
                    location.href='<%=path%>/center/storage/listReagentSuppliesInfo.html';
                }
        );

        $("#backBtn").on("click",function(){
            location.href='<%=path%>/center/storage/listReagentSuppliesInfo.html';
        });

        $('.form_datetime').datetimepicker({
            format: 'yyyy-mm-dd hh:mm',
            language:  'zh',
            weekStart: 1,
            todayBtn:  1,
            minView: "month",
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