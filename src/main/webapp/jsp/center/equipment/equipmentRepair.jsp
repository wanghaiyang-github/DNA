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
                            <span class="label label-primary">添加维修记录</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <form id="queryForm" action="<%=path%>/center/equipment/equipmentRepair.html" class="form-horizontal tasi-form" method="post">
                                <input type="hidden" id="equipmentInfoId" value="${equipmentInfo.id}"/>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">设备名称</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" id="equipmentNameId" name="equipmentNameId" value="${equipmentInfo.equipmentNameId}" disabled>
                                            <option value="">全部</option>
                                            <c:forEach items="${equipmentNameInfoList}" var="equipmentNameInfo" varStatus="do">
                                                <option value="${equipmentNameInfo.id}" <c:if test="${equipmentNameInfo.id eq equipmentInfo.equipmentNameId}">selected</c:if>>${equipmentNameInfo.equipmentName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-1 control-label">维修人</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" id="equipmentRepairPerson" name="equipmentRepairPerson" value="${equipmentRepairInfo.equipmentRepairPerson}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">受理时间</label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" name="repairTimeDateStart" readonly="readonly" type="text" value="<fmt:formatDate value='${equipmentRepairInfo.repairTimeDateStart}' pattern='yyyy-MM-dd hh:mm'/>">
                                    </div>
                                    <label class="col-sm-1 control-label">至 </label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" name="repairTimeDateEnd" readonly="readonly" type="text" value="<fmt:formatDate value='${equipmentRepairInfo.repairTimeDateEnd}' pattern='yyyy-MM-dd hh:mm'/>">
                                    </div>
                                    <div class="col-sm-2">
                                        <button id="queryBtn" class="btn btn-primary" type="button"><i class="fa fa-search"></i> 查 询</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </section>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            <span class="label label-primary">设备维修记录</span>
                            <span class="tools pull-right">
                                <a href="javascript:;" class="fa fa-chevron-down"></a>
                            </span>
                        </header>
                        <div class="panel-body">
                            <div class="clearfix">
                                <div class="form-group pull-right">
                                    <button class="btn btn-primary" id="addBtn" type="button"><i class="fa fa-plus"></i> 添 加 </button>
                                    <button class="btn btn-primary" id="backBtn" type="button"><i class="fa fa-reply"></i> 返 回 </button>
                                </div>
                            </div>
                            <div class="space15" style="height: 8px;"></div>
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>设备名称</th>
                                    <th>维修人</th>
                                    <th>维修时间</th>
                                    <th>故障原因</th>
                                </tr>
                                </thead>
                                <tbody id="equipmentRepairInfoListTbody">
                                <c:forEach items="${equipmentRepairInfoList}" var="equipmentRepairInfo" varStatus="s">
                                    <tr>
                                        <td>${s.count}</td>
                                        <td>${equipmentRepairInfo.equipmentName}</td>
                                        <td>${equipmentRepairInfo.equipmentRepairPerson}</td>
                                        <td><fmt:formatDate value='${equipmentRepairInfo.equipmentRepairTime}' pattern='yyyy-MM-dd HH:mm'/></td>
                                        <td>${equipmentRepairInfo.failureCause}</td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                            <div class="modal fade" id="equipmentRepairModel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                &times;
                                            </button>
                                            <h4 class="modal-title">
                                                维修信息
                                            </h4>
                                        </div>
                                        <div class="modal-body">
                                            <form id="queryModelForm" class="form-horizontal tasi-form">
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">设备名称:<i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <select class="form-control required" id="equipmentNameInfoId" name="equipmentNameInfoId" disabled>
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
                                                    <label class="col-md-3 control-label">维修人<i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form-control required" id="equipmentRepairPersonModel"/>
                                                    </div>
                                                    <div class="col-sm-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">维修时间<i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <input type="text" class="form_datetime form-control required" id="repairTimeModel" <fmt:formatDate value='${equipmentRepairInfo.equipmentRepairTime}' pattern='yyyy-MM-dd hh:mm'/>>
                                                    </div>
                                                    <div class="col-sm-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="col-md-3 control-label">故障原因<i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <textarea class="form-control required" rows="2" id="failureCauseModel"></textarea>
                                                    </div>
                                                    <div class="col-sm-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <input type="hidden" id="operateTypeModel" name="operateTypeModel"/>
                                            <button class="btn btn-success" type="button" id="saveBtn">确定</button>
                                            <button data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
                                        </div>
                                    </div>
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
    $(function(){
        'use strict';

        $("#queryBtn").on("click", function(){
            $("#queryForm").submit();
        });

        function GetEquipmentRepairInfo(){
            var equipmentRepairInfo = {};
            equipmentRepairInfo.equipmentInfoId=$("#equipmentInfoId").val();
            equipmentRepairInfo.equipmentNameId = $("#equipmentNameInfoId").val();
            equipmentRepairInfo.equipmentRepairPerson = $("#equipmentRepairPersonModel").val();
            equipmentRepairInfo.repairTime = $("#repairTimeModel").val();
            equipmentRepairInfo.failureCause = $("#failureCauseModel").val();

            return equipmentRepairInfo;
        }

        function checkInputValidation(){
            var errCnt = 0;
            $(".required", "#queryModelForm").each(function(){
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

        $("#addBtn").on('click',function(){

            var equipmentNameId = $("#equipmentNameId").val();

            $("#equipmentNameInfoId").val(equipmentNameId);

            $("#equipmentRepairModel").modal('show');

        });

        $("#saveBtn").on('click',function(){
            if(!checkInputValidation()){
                return;
            }

            var equipmentRepairInfo = GetEquipmentRepairInfo();

            $.ajax({
                url : "<%=path%>/center/equipment/saveEquipmentRepairInfo.html",
                type:"post",
                contentType: "application/json; charset=utf-8",
                data : JSON.stringify(equipmentRepairInfo),
                dataType : "json",
                success : function(data) {
                    if(data.success || data.success == true || data.success == "true") {
                        var equipmentInfoId=$("#equipmentInfoId").val();
                        location.href='<%=path%>/center/equipment/equipmentRepair.html?equipmentInfoId=' + equipmentInfoId;
                    }else {
                        alert("保存失败！");
                    }
                },
                error: function(e){
                    alert(e);
                }
            });
        });

        $("#backBtn").on("click",function(){
            location.href='<%=path%>/center/equipment/equipmentRepairList.html';
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


