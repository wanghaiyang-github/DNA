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
                            <span class="label label-primary">添加设备名称</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <form id="queryForm" action="<%=path%>/center/equipment/equipmentNameList.html" class="form-horizontal tasi-form" method="post">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label" style="text-align:center;">设备编号</label>
                                    <div class="col-sm-3">
                                        <input class="form-control" id="equipmentNo" name="equipmentNo" value="${equipmentNameInfo.equipmentNo}" />
                                    </div>
                                    <label class="col-sm-1 control-label" style="text-align:center;">设备名称</label>
                                    <div class="col-sm-3">
                                        <input class="form-control" id="equipmentName" name="equipmentName" value="${equipmentNameInfo.equipmentName}" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label" style="text-align:center;">设备类型</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" id="equipmentTypeId" name="equipmentTypeId">
                                            <option value="">全部</option>
                                            <c:forEach items="${equipmentTypeInfoList}" var="equipmentTypeInfo" varStatus="do">
                                                <option value="${equipmentTypeInfo.id}" <c:if test="${equipmentTypeInfo.id eq equipmentNameInfo.equipmentTypeId}">selected</c:if>>${equipmentTypeInfo.equipmentTypeName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-3" style="text-align:center;">
                                        <button name="queryBtn" class="btn btn-primary"><i class="fa fa-search"></i> 查 询</button>
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
                            <span class="label label-primary">设备名称列表</span>
                               <span class="tools pull-right">
                                   <a href="javascript:;" class="fa fa-chevron-down"></a>
                               </span>
                        </header>
                        <div class="panel-body">
                            <div class="clearfix">
                                <div class="btn-group pull-right">
                                    <button id="addBtn" class="btn btn-success green">
                                        添加 <i class="fa fa-plus"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="space15" style="height: 8px;"></div>
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>设备类型</th>
                                    <th>设备编号</th>
                                    <th>设备名称</th>
                                    <th>入库时间</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="equipmentNameInfoList">
                                <c:forEach items="${equipmentNameInfoList}" var="equipmentNameInfo" varStatus="s">
                                    <tr>
                                        <td>${s.count}</td>
                                        <td>${equipmentNameInfo.equipmentTypeName}</td>
                                        <td>${equipmentNameInfo.equipmentNo}</td>
                                        <td>${equipmentNameInfo.equipmentName}</td>
                                        <td><fmt:formatDate value="${equipmentNameInfo.createDatetime}" pattern="yyyy-MM-dd HH:mm"/></td>
                                        <td>
                                            <input type="hidden" name="id" value="${equipmentNameInfo.id}"/>
                                            <input type="hidden" name="equipmentTypeId" value="${equipmentNameInfo.equipmentTypeId}"/>
                                            <input type="hidden" name="equipmentNo" value="${equipmentNameInfo.equipmentNo}"/>
                                            <input type="hidden" name="equipmentName" value="${equipmentNameInfo.equipmentName}"/>
                                            <button name="editBtn" class="btn btn-primary btn-xs"><i class="fa fa-pencil"></i> 修改</button>
                                            <button name="delBtn" class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> 删除</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                            <div class="modal fade" id="equipmentNameInfoModel" aria-hidden="true">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                &times;
                                            </button>
                                            <h4 class="modal-title">
                                                名称信息
                                            </h4>
                                        </div>
                                        <div class="modal-body">
                                            <form id="queryModelForm" class="form-horizontal tasi-form">
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">设备类型:<i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <select class="form-control required" id="equipmentTypeIdModel" name="equipmentTypeIdModel">
                                                            <option value="">全部</option>
                                                            <c:forEach items="${equipmentTypeInfoList}" var="equipmentTypeInfo" varStatus="do">
                                                                <option value="${equipmentTypeInfo.id}">${equipmentTypeInfo.equipmentTypeName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="col-sm-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">设备编号:<i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <input id="equipmentNoModel" name="equipmentNoModel" type="text" class="form-control required"/>
                                                    </div>
                                                    <div class="col-sm-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">设备名称:<i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <input id="equipmentNameModel" name="equipmentNameModel" type="text" class="form-control required"/>
                                                    </div>
                                                    <div class="col-sm-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <input type="hidden" id="idModel" name="idModel"/>
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

            $("#operateTypeModel").val("1");

            $("#equipmentNameInfoModel").modal('show');

        });

        function GetEquipmentNameInfoModel() {
            var equipmentNameInfo = {};
            equipmentNameInfo.id=$("#idModel").val();
            equipmentNameInfo.equipmentTypeId=$("#equipmentTypeIdModel").val();
            equipmentNameInfo.equipmentNo = $("#equipmentNoModel").val();
            equipmentNameInfo.equipmentName = $("#equipmentNameModel").val();

            return equipmentNameInfo;
        }

        $("#saveBtn").on('click',function(){
            if(!checkInputValidation()){
                return;
            }

            var equipmentNameInfo = GetEquipmentNameInfoModel();
            var operateType = $("#operateTypeModel").val();

            saveEquipmentNameInfo(equipmentNameInfo, operateType);

        });

        function saveEquipmentNameInfo(equipmentNameInfo, operateType) {
            $.ajax({
                url : "<%=path%>/center/equipment/saveEquipmentNameInfo.html?operateType=" + operateType,
                type:"post",
                contentType: "application/json; charset=utf-8",
                data : JSON.stringify(equipmentNameInfo),
                dataType : "json",
                success : function(data) {
                    if(data.success || data.success == true || data.success == "true") {
                        location.href='<%=path%>/center/equipment/equipmentNameList.html';
                    }else {
                        alert("添加失败！");
                    }
                },
                error: function(e){
                    alert(e);
                }
            });
        }

        $("button[name='editBtn']","#equipmentNameInfoList").on("click",function(){
            EditEquipmentNameInfoRow(this);
        });

        function EditEquipmentNameInfoRow(obj){
            var $curTR = $(obj).parents("tr");
            var equipmentNameInfo = {};
            equipmentNameInfo.id = $("input[name='id']", $curTR).val();
            equipmentNameInfo.equipmentTypeId = $("input[name='equipmentTypeId']", $curTR).val();
            equipmentNameInfo.equipmentNo = $("input[name='equipmentNo']", $curTR).val();
            equipmentNameInfo.equipmentName = $("input[name='equipmentName']", $curTR).val();

            newEquipmentNameInfoRow(equipmentNameInfo);
        }

        function newEquipmentNameInfoRow(equipmentNameInfo){
            $("#idModel", "#equipmentNameInfoModel").val(equipmentNameInfo.id);
            $("#equipmentTypeIdModel", "#equipmentNameInfoModel").val(equipmentNameInfo.equipmentTypeId);
            $("#equipmentNoModel", "#equipmentNameInfoModel").val(equipmentNameInfo.equipmentNo);
            $("#equipmentNameModel", "#equipmentNameInfoModel").val(equipmentNameInfo.equipmentName);
            $("#operateTypeModel", "#equipmentNameInfoModel").val("2");

            $("#equipmentNameInfoModel").modal('show');
        }

        $("button[name='delBtn']","#equipmentNameInfoList").on("click",function(){
            if(confirm("确认删除吗？")){
                var id = $("input[name='id']", $(this).parent()).val();
                $.ajax({
                    url : "<%=path%>/center/equipment/delEquipmentNameInfo.html?id=" + id,
                    type:"get",
                    dataType : "json",
                    success : function(data) {
                        if(data.success || data.success == true || data.success == "true") {
                            location.href = "<%=path%>/center/equipment/equipmentNameList.html";
                        }else {
                            alert("删除失败！");
                        }
                    },
                    error: function(e){
                        alert(e);
                    }
                });
            }
        });
    });
</script>
<!-- END JS -->
</body>
</html>


