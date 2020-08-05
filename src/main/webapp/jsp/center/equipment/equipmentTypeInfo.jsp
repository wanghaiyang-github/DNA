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
                            <span class="label label-primary">设备类型信息</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <form id="equipmentTypeInfoform" class="form-horizontal tasi-form">
                                <input type="hidden" id="equipmentTypeInfoId" value="${equipmentTypeInfo.id}"/>
                                <input type="hidden" id="operateType" value="${operateType}"/>

                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">类型编号 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control required" id="equipmentTypeNo" value="${equipmentTypeInfo.equipmentTypeNo}">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">类型名称<i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control required" id="equipmentTypeName" value="${equipmentTypeInfo.equipmentTypeName}">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">实验阶段<i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <select class="form-control required" id="experimentalStage" value="${equipmentTypeInfo.experimentalStage}">
                                            <option value="">全部</option>
                                            <c:forEach items="${experimentalStageList}" var="experimentalStage" varStatus="do">
                                                <option value="${experimentalStage.dictCode}" <c:if test="${experimentalStage.dictCode eq equipmentTypeInfo.experimentalStage}">selected</c:if>>${experimentalStage.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">使用期限</label>
                                    <div class="col-sm-1">蓝色预警</div>
                                    <div class="col-sm-1">
                                        <input type="text" class="form-control" id="useBlueWarn"  value="${equipmentTypeInfo.useBlueWarn}"/>
                                    </div>
                                    <div class="col-sm-1">月</div>
                                    <div class="col-sm-1">红色预警</div>
                                    <div class="col-sm-1">
                                        <input type="text" class="form-control" id="useRedWarn" value="${equipmentTypeInfo.useRedWarn}"/>
                                    </div>
                                    <div class="col-sm-1">月</div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">维修周期</label>
                                    <div class="col-sm-1">蓝色预警</div>
                                    <div class="col-sm-1">
                                        <input type="text" class="form-control" id="repairBlueWarn"  value="${equipmentTypeInfo.repairBlueWarn}"/>
                                    </div>
                                    <div class="col-sm-1">天</div>
                                    <div class="col-sm-1">红色预警</div>
                                    <div class="col-sm-1">
                                        <input type="text" class="form-control" id="repairRedWarn" value="${equipmentTypeInfo.repairRedWarn}"/>
                                    </div>
                                    <div class="col-sm-1">天</div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">描 述</label>
                                    <div class="col-sm-5">
                                        <textarea class="form-control" rows="2" id="remark">${equipmentTypeInfo.remark}</textarea>
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

            <div class="modal fade" id="EditEquipmentModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
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
        </div>
    </section>
</section>
<!-- BEGIN JS -->
<%@ include file="../../common/script.jsp" %>
<script>
    $(function(){
        'use strict';

        function GetEquipmentTypeInfo(){
            var equipmentTypeInfo = {};
            equipmentTypeInfo.id=$("#equipmentTypeInfoId").val();
            equipmentTypeInfo.equipmentTypeNo = $("#equipmentTypeNo").val();
            equipmentTypeInfo.equipmentTypeName = $("#equipmentTypeName").val();
            equipmentTypeInfo.experimentalStage = $("#experimentalStage").val();
            equipmentTypeInfo.useBlueWarn = $("#useBlueWarn").val();
            equipmentTypeInfo.useRedWarn = $("#useRedWarn").val();
            equipmentTypeInfo.repairBlueWarn = $("#repairBlueWarn").val();
            equipmentTypeInfo.repairRedWarn = $("#repairRedWarn").val();
            equipmentTypeInfo.remark = $("#remark").val();

            return equipmentTypeInfo;
        }

        function checkInputValidation(){
            var errCnt = 0;
            $(".required", "#equipmentTypeInfoform").each(function(){
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

        $("#saveBtn").on('click',function(){
            if(!checkInputValidation()){
                return;
            }

            var operateType = $("#operateType").val();
            var equipmentTypeInfo = GetEquipmentTypeInfo();

            $.ajax({
                url : "<%=path%>/center/equipment/saveEquipmentTypeInfo.html?operateType=" + operateType,
                type:"post",
                contentType: "application/json; charset=utf-8",
                data : JSON.stringify(equipmentTypeInfo),
                dataType : "json",
                success : function(data) {
                    if(data.success || data.success == true || data.success == "true") {
                        $("#EditEquipmentModal").modal('show');
                    }else {
                        alert("保存失败！");
                    }
                },
                error: function(e){
                    alert(e);
                }
            });

        });

        $("#EditEquipmentModal").on('hidden.bs.modal', function(){
            location.href='<%=path%>/center/equipment/equipmentTypeList.html';
        });

        $("#backBtn").on("click",function(){
            location.href='<%=path%>/center/equipment/equipmentTypeList.html';
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


