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
                            <span class="label label-primary">字典列表</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                            <div class="clearfix">
                                <div class="btn-group">
                                    <button name="addBtn" class="btn btn-success green">
                                        添加 <i class="fa fa-plus"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="space15" style="height: 8px;"></div>
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th style="text-align:center;">序号</th>
                                    <th style="text-align:center;">字典类型</th>
                                    <th style="text-align:center;">字典编号</th>
                                    <th style="text-align:center;">字典名称</th>
                                    <th style="text-align:center;">字典详情</th>
                                    <th style="text-align:center;">创建时间</th>
                                    <th style="text-align:center;">创建人</th>
                                    <th style="text-align:center;">操作</th>
                                </tr>
                                </thead>
                                <tbody id="dictItemListTbody">
                                <c:forEach items="${dictItemList}" var="dictItem" varStatus="s">
                                    <tr>
                                        <td style="text-align:center;">${s.count}</td>
                                        <td style="text-align:center;">${dictItem.dictTypeCode}</td>
                                        <td style="text-align:center;">${dictItem.dictCode}</td>
                                        <td style="text-align:center;">${dictItem.dictName}</td>
                                        <td style="text-align:center;" width="350">${dictItem.dictDesc}</td>
                                        <td style="text-align:center;"><fmt:formatDate value="${dictItem.createDatetime}" pattern="yyyy-MM-dd" /></td>
                                        <td style="text-align:center;">${dictItem.createPerson}</td>
                                        <td style="text-align:center;">
                                            <input type="hidden" name="dictId" value="${dictItem.id}"/>
                                            <input type="hidden" name="dictTypeCode" value="${dictItem.dictTypeCode}"/>
                                            <input type="hidden" name="dictItemCode" value="${dictItem.dictCode}"/>
                                            <input type="hidden" name="dictName" value="${dictItem.dictName}"/>
                                            <input type="hidden" name="dictDesc" value="${dictItem.dictDesc}"/>
                                            <button name="editBtn" class="btn btn-primary btn-xs"><i class="fa fa-pencil"></i> 修改</button>
                                            <button name="delBtn" class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> 删除</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                            <div class="modal fade" id="dictItemModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                &times;
                                            </button>
                                            <h4 class="modal-title">
                                                字典信息
                                            </h4>
                                        </div>
                                        <div class="modal-body">
                                            <form class="form-horizontal tasi-form">
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">字典类型<i class="fa fa-asterisk color_red"></i>:</label>
                                                    <div class="col-md-5">
                                                        <input id="dictTypeCode" type="text" value="${dictTypeCode}" readonly="readonly" class="form-control small required"/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">字典编号<i class="fa fa-asterisk color_red"></i>:</label>
                                                    <div class="col-md-5">
                                                        <input id="dictCode" name="dictCode"  type="text" class="form-control small required" value=""/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">字典名称<i class="fa fa-asterisk color_red"></i>:</label>
                                                    <div class="col-md-5">
                                                        <input id="dictItemName" name="dictItemName"  type="text" class="form-control small required" value=""/>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">字典详情:</label>
                                                    <div class="col-md-5">
                                                        <input id="dictItemDesc" name="dictItemDesc" type="text" class="form-control small required" value=""/>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <input type="hidden" id="dictItemId" name="dictItemId"/>
                                            <input type="hidden" id="dictIndex" name="dictIndex"/>
                                            <button class="btn btn-success" type="button" id="saveBtn">确定</button>
                                            <button data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group pull-right">
                                <button class="btn btn-lg btn-info" id="backBtn" type="button"><i class="fa fa-reply"></i> 返 回 </button>
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

        $("button[name='addBtn']").on('click',function(){
            EditDictItemRow(this);
        });

        $("button[name='editBtn']").on("click",function(){
            EditDictItemRow(this);
        });

        function EditDictItemRow(obj){
            var $curTR = $(obj).parents("tr");
            var dictItem = {};

            dictItem.id = $("input[name='dictId']", $curTR).val();
            dictItem.dictCode = $("input[name='dictItemCode']", $curTR).val();
            dictItem.dictTypeName = $("input[name='dictName']", $curTR).val();
            dictItem.dictDesc = $("input[name='dictDesc']", $curTR).val();
            dictItem.index = $(obj).parents("tr").index();

            newDictItemRow(dictItem);
        }

        function newDictItemRow(dictItem) {
            $("input[name='dictItemId']", "#dictItemModal").val(dictItem.id);
            $("input[name='dictCode']", "#dictItemModal").val(dictItem.dictCode);
            $("input[name='dictItemName']", "#dictItemModal").val(dictItem.dictTypeName);
            $("input[name='dictItemDesc']", "#dictItemModal").val(dictItem.dictDesc);
            $("input[name='dictIndex']", "#dictItemModal").val(dictItem.index);

            $("#dictItemModal").modal('show');
        }

        function checkInputValids(){
            var dictCode = $("#dictCode").val();
            if(dictCode == ""){
                alert("请输入字典编号!");
                $("#dictCode").focus();
                return;
            }

            var dictItemName = $("#dictItemName").val();
            if(dictItemName == ""){
                alert("请输入字典名称!");
                $("#dictItemName").focus();
                return;
            }

            var totalRows = $("tr", "#dictItemListTbody").length;
            var dictIndex = $("input[name='dictIndex']", "#dictItemModal").val();
            var code = $("input[name='dictCode']", "#dictItemModal").val();

            var itemCode;
            for (var i = 0;i < totalRows;i++) {
                if (dictIndex == i)
                    continue;

                itemCode = $("input[name='dictItemCode']", "tbody tr:eq("+ i +")").val();
                if (itemCode == code) {
                    alert("输入的字典编号重复！");
                    return;
                    break;
                }
            }

            return true;
        }

        function getParams(){
            var dictItem = {};

            dictItem.id=$("#dictItemId").val();
            dictItem.dictTypeCode = $("#dictTypeCode").val();
            dictItem.dictCode = $("#dictCode").val();
            dictItem.dictName = $("#dictItemName").val();
            dictItem.dictDesc = $("#dictItemDesc").val();

            return dictItem;
        }

        $("#saveBtn").on("click",function(){
            if(!checkInputValids())
                return;

            $.ajax({
                url:"<%=path%>/center/7/addOrEditDictItem.html",
                type:"post",
                data:JSON.stringify(getParams()),
                dataType:"json",
                contentType:"application/json;charset=utf-8",
                success: function (data) {
                    if(data.success){
                        var dictTypeCode = $("#dictTypeCode").val();
                        location.href = "<%=path%>/center/7/getChildrenDictItem.html?dictTypeCode="+dictTypeCode;
                    }else {
                        alert("操作失败!");
                    }
                },
                error:function(data){
                    alert("操作失败!");
                }
            });
        });

        $("#backBtn").on("click",function(){
            location.href='/DNA/center/7/03.html';
        });

        $("button[name='delBtn']","#dictItemListTbody").on("click",function(){
            if(confirm("确认要删除该字典信息吗？")){
                var dictId=$("input[name='dictId']", $(this).parent()).val();
                var dictTypeCode=$("#dictTypeCode").val();

                location.href='<%=path%>/center/7/delDictItem.html?dictItemId='+dictId+"&dictTypeCode="+dictTypeCode;
            }
        });
    });
</script>
<!-- END JS -->
</body>
</html>


