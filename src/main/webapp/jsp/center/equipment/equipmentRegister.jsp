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
                            <span class="label label-primary">设备信息</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <form id="equipmentInfoform" class="form-horizontal tasi-form">
                                <input type="hidden" id="equipmentInfoId" value="${equipmentInfo.id}"/>
                                <input type="hidden" id="operateType" value="${operateType}"/>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">设备名称<i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <select class="form-control required" id="equipmentNameInfoSelect" name="equipmentNameInfoSelect">
                                            <option value="">全部</option>
                                            <c:forEach items="${equipmentNameInfoList}" var="equipmentNameInfo" varStatus="do">
                                                <option value="${equipmentNameInfo.id}" <c:if test="${equipmentNameInfo.id eq equipmentInfo.equipmentNameId}">selected</c:if>>${equipmentNameInfo.equipmentName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">设备编号 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="hidden" class="form-control" id="oldEquipmentNo" value="${equipmentInfo.equipmentNo}">
                                        <input type="text" class="form-control required" id="equipmentNo" value="${equipmentInfo.equipmentNo}">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">规 格 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control required" id="equipmentSpecification" value="${equipmentInfo.equipmentSpecification}">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">设备状态<i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <select class="form-control required" id="equipmentStatusSelect" value="${equipmentInfo.equipmentStatus}">
                                            <c:forEach items="${equipmentStatusList}" var="equipmentStatus" varStatus="do">
                                                <option value="${equipmentStatus.dictCode}" <c:if test="${equipmentStatus.dictCode eq equipmentInfo.equipmentStatus}">selected</c:if>>${equipmentStatus.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">数 量 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control required" id="equipmentNum" value="${equipmentInfo.equipmentNum}"/>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">使用期限</label>
                                    <div class="col-sm-1">蓝色预警</div>
                                    <div class="col-sm-1">
                                        <input type="text" class="form-control" id="useBlueWarn"  value="${equipmentInfo.useBlueWarn}"/>
                                    </div>
                                    <div class="col-sm-1">月</div>
                                    <div class="col-sm-1">红色预警</div>
                                    <div class="col-sm-1">
                                        <input type="text" class="form-control" id="useRedWarn" value="${equipmentInfo.useRedWarn}"/>
                                    </div>
                                    <div class="col-sm-1">月</div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">维修周期</label>
                                    <div class="col-sm-1">蓝色预警</div>
                                    <div class="col-sm-1">
                                        <input type="text" class="form-control" id="repairBlueWarn"  value="${equipmentInfo.repairBlueWarn}"/>
                                    </div>
                                    <div class="col-sm-1">天</div>
                                    <div class="col-sm-1">红色预警</div>
                                    <div class="col-sm-1">
                                        <input type="text" class="form-control" id="repairRedWarn" value="${equipmentInfo.repairRedWarn}"/>
                                    </div>
                                    <div class="col-sm-1">天</div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">描 述</label>
                                    <div class="col-sm-5">
                                        <textarea class="form-control" rows="2" id="remark">${equipmentInfo.remark}</textarea>
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

        function GetEquipmentInfo(){
            var equipmentInfo = {};
            equipmentInfo.id=$("#equipmentInfoId").val();
            equipmentInfo.equipmentNo = $("#equipmentNo").val();
            equipmentInfo.equipmentSpecification = $("#equipmentSpecification").val();
            equipmentInfo.equipmentNameId = $("#equipmentNameInfoSelect").val();
            equipmentInfo.equipmentStatus = $("#equipmentStatusSelect").val();
            equipmentInfo.equipmentNum = $("#equipmentNum").val();
            equipmentInfo.useBlueWarn = $("#useBlueWarn").val();
            equipmentInfo.useRedWarn = $("#useRedWarn").val();
            equipmentInfo.repairBlueWarn = $("#repairBlueWarn").val();
            equipmentInfo.repairRedWarn = $("#repairRedWarn").val();
            equipmentInfo.remark = $("#remark").val();

            return equipmentInfo;
        }

        function checkInputValidation(){
            var errCnt = 0;
            $(".required", "#equipmentInfoform").each(function(){
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
            var equipmentNo = $("#equipmentNo").val();
            var oldEquipmentNo = $("#oldEquipmentNo").val();
            $.ajax({
                url : "<%=path%>/center/equipment/selectIsRepeat.html?equipmentNo=" + equipmentNo,
                type : "get",
                dataType : "json",
                success : function(data) {
                    if(data > 0) {
                        if (operateType == "1") {
                            alert("设备编号重复，请重新输入！");
                            $("#equipmentNo").focus();
                        }else {
                            if (equipmentNo.trim() == oldEquipmentNo.trim()) {
                                saveEquipmentInfo(operateType);
                            }else {
                                if (data > 0) {
                                    alert("设备编号重复，请重新输入！");
                                    $("#equipmentNo").focus();
                                }
                            }
                        }
                    }else {
                        if (data < 0) {
                            alert("保存失败!");
                        }else {
                            saveEquipmentInfo(operateType);
                        }
                    }
                }
            });
        });


        function saveEquipmentInfo(operateType) {
            var equipmentInfo = GetEquipmentInfo();

            if(equipmentInfo.equipmentNum <= 0) {
                alert("数量不能小于1,请重新输入！")
                $("#equipmentNum").val("");
                $("#equipmentNum").focus();
                return;
            }

            $.ajax({
                url : "<%=path%>/center/equipment/saveEquipmentInfo.html?operateType=" + operateType,
                type:"post",
                contentType: "application/json; charset=utf-8",
                data : JSON.stringify(equipmentInfo),
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
        }

        $("#EditEquipmentModal").on('hidden.bs.modal', function(){
            location.href='<%=path%>/center/equipment/equipmentInfoList.html';
        });

        $("#backBtn").on("click",function(){
            location.href='<%=path%>/center/equipment/equipmentInfoList.html';
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


