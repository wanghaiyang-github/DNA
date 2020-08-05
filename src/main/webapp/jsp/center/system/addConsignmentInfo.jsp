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
                            <span class="label label-primary">委托列表</span>
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
                                    <input type="hidden" id="delegateOrgId" name="delegateOrgId" value="${delegateOrgId}">
                                    <input type="hidden" id="delegateOrgDefaultName" name="delegateOrgDefaultName" value="${delegateOrgName}">
                                </div>
                            </div>
                            <div class="space15" style="height: 8px;"></div>
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th style="text-align:center;">序号</th>
                                    <th style="text-align:center;">委托单位</th>
                                    <th style="text-align:center;">委托人</th>
                                    <th style="text-align:center;">委托人职务</th>
                                    <th style="text-align:center;">委托人证件</th>
                                    <th style="text-align:center;">委托人证件号</th>
                                    <th style="text-align:center;">委托人电话</th>
                                    <th style="text-align:center;">创建时间</th>
                                    <th style="text-align:center;">操作</th>
                                </tr>
                                </thead>
                                <tbody id="consignmentInfoListTbody">
                                <c:forEach items="${consignmentInfoList}" var="consignmentInfo" varStatus="s">
                                    <tr>
                                        <td style="text-align:center;">${s.count}</td>
                                        <td style="text-align:center;">${consignmentInfo.delegateOrgName}</td>
                                        <td style="text-align:center;">${consignmentInfo.delegator}</td>
                                        <td style="text-align:center;">${consignmentInfo.delegatorDutyName}</td>
                                        <td style="text-align:center;">${consignmentInfo.delegatorCertificateName}</td>
                                        <td style="text-align:center;">${consignmentInfo.delegatorCertificateNo}</td>
                                        <td style="text-align:center;">${consignmentInfo.delegatorPhone}</td>
                                        <td style="text-align:center;"><fmt:formatDate value="${consignmentInfo.createDatetime}" pattern="yyyy-MM-dd" /></td>
                                        <td style="text-align:center;">
                                            <input type="hidden" name="id" value="${consignmentInfo.id}"/>
                                            <input type="hidden" name="delegateOrgName" value="${consignmentInfo.delegateOrgName}"/>
                                            <input type="hidden" name="delegator" value="${consignmentInfo.delegator}"/>
                                            <input type="hidden" name="delegatorDuty" value="${consignmentInfo.delegatorDuty}"/>
                                            <input type="hidden" name="delegatorCertificate" value="${consignmentInfo.delegatorCertificate}"/>
                                            <input type="hidden" name="delegatorCertificateNo" value="${consignmentInfo.delegatorCertificateNo}"/>
                                            <input type="hidden" name="delegatorPhone" value="${consignmentInfo.delegatorPhone}"/>
                                            <button name="editBtn" class="btn btn-primary btn-sm"><i class="fa fa-pencil"></i> 修改</button>
                                            <button name="delBtn" class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 删除</button>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                            <div class="modal fade" id="consignmentInfoModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                &times;
                                            </button>
                                            <h4 class="modal-title">
                                                委托信息
                                            </h4>
                                        </div>
                                        <div class="modal-body">
                                            <form id="consignment_form" class="form-horizontal tasi-form" method="get">
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">委托单位<i class="fa fa-asterisk color_red"></i>:</label>
                                                    <div class="col-md-5">
                                                        <input id="delegateOrgName" type="text"  class="form-control small required"/>
                                                    </div>
                                                    <div class="col-sm-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">委托人<i class="fa fa-asterisk color_red"></i>:</label>
                                                    <div class="col-md-5">
                                                        <input id="delegatorName" name="delegatorName" type="text" class="form-control small required"/>
                                                    </div>
                                                    <div class="col-sm-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">委托人职务<i class="fa fa-asterisk color_red"></i>:</label>
                                                    <div class="col-md-5">
                                                        <select class="form-control small required" name="delegatorDuty" id="delegatorDuty">
                                                            <option value="">请选择...</option>
                                                            <c:forEach items="${dictItemDutyList}" var="list" varStatus="s">
                                                                <option value="${list.dictCode}" <c:if test="${delegatorDuty eq list.dictCode}">selected</c:if>>${list.dictName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="col-sm-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">委托人证件<i class="fa fa-asterisk color_red"></i>:</label>
                                                    <div class="col-md-5">
                                                        <select class="form-control small required" name="delegatorCertificate" id="delegatorCertificate">
                                                            <option value="">请选择...</option>
                                                            <c:forEach items="${dictItemCertificateList}" var="list" varStatus="s">
                                                                <option value="${list.dictCode}" <c:if test="${delegatorCertificate eq list.dictCode}">selected</c:if>>${list.dictName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="col-sm-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">委托人证件号<i class="fa fa-asterisk color_red"></i>:</label>
                                                    <div class="col-md-5">
                                                        <input id="delegatorCertificateNo" type="text" class="form-control small required"/>
                                                    </div>
                                                    <div class="col-sm-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">委托人电话<i class="fa fa-asterisk color_red"></i>:</label>
                                                    <div class="col-md-5">
                                                        <input id="delegatorPhone" type="text" class="form-control small required"/>
                                                    </div>
                                                    <div class="col-sm-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <input type="hidden" id="consignmentInfoId" name="consignmentInfoId"/>
                                            <input type="hidden" id="operate" name="operate"/>
                                            <button class="btn btn-success" type="button" id="saveBtn">确定</button>
                                            <button data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="form-group pull-right">
                                <button class="btn btn-info btn-sm" id="backBtn" type="button"><i class="fa fa-reply"></i> 返 回 </button>
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
            EditConsignmentInfoRow(this, "1");
        });

        $("button[name='editBtn']", "#consignmentInfoListTbody").on("click",function(){
            EditConsignmentInfoRow(this, "2");
        });

        function EditConsignmentInfoRow(obj, operate){
            var $curTR = $(obj).parents("tr");
            var limsConsignmentInfo = {};

            limsConsignmentInfo.id = $("input[name='id']", $curTR).val();
           if (operate == "1") {
                var delegateOrgDefaultName = $("#delegateOrgDefaultName").val();
                limsConsignmentInfo.delegateOrgName = delegateOrgDefaultName;
            }else {
                limsConsignmentInfo.delegateOrgName = $("input[name='delegateOrgName']", $curTR).val();
            }
            limsConsignmentInfo.delegator = $("input[name='delegator']", $curTR).val();
            limsConsignmentInfo.delegatorDuty = $("input[name='delegatorDuty']", $curTR).val();
            limsConsignmentInfo.delegatorCertificate = $("input[name='delegatorCertificate']", $curTR).val();
            limsConsignmentInfo.delegatorCertificateNo = $("input[name='delegatorCertificateNo']", $curTR).val();
            limsConsignmentInfo.delegatorPhone = $("input[name='delegatorPhone']", $curTR).val();
            limsConsignmentInfo.operate = operate;

            newLimsConsignmentInfoRow(limsConsignmentInfo);
        }

        function newLimsConsignmentInfoRow(limsConsignmentInfo) {
            $("#consignmentInfoId", "#consignmentInfoModal").val(limsConsignmentInfo.id);
            $("#delegateOrgName", "#consignmentInfoModal").val(limsConsignmentInfo.delegateOrgName);
            $("#delegatorName", "#consignmentInfoModal").val(limsConsignmentInfo.delegator);
            $("#delegatorDuty", "#consignmentInfoModal").val(limsConsignmentInfo.delegatorDuty);
            $("#delegatorCertificate", "#consignmentInfoModal").val(limsConsignmentInfo.delegatorCertificate);
            $("#delegatorCertificateNo", "#consignmentInfoModal").val(limsConsignmentInfo.delegatorCertificateNo);
            $("#delegatorPhone", "#consignmentInfoModal").val(limsConsignmentInfo.delegatorPhone);
            $("#operate", "#consignmentInfoModal").val(limsConsignmentInfo.operate);

            $("#consignmentInfoModal").modal('show');
        }

        function getParams(){
            var limsConsignmentInfo = {};

            limsConsignmentInfo.id=$("#consignmentInfoId").val();
            limsConsignmentInfo.delegateOrgId=$("#delegateOrgId").val();
            limsConsignmentInfo.delegateOrgName = $("#delegateOrgName").val();
            limsConsignmentInfo.delegator = $("#delegatorName").val();
            limsConsignmentInfo.delegatorDuty = $("#delegatorDuty").val();
            limsConsignmentInfo.delegatorCertificate = $("#delegatorCertificate").val();
            limsConsignmentInfo.delegatorCertificateNo = $("#delegatorCertificateNo").val();
            limsConsignmentInfo.delegatorPhone = $("#delegatorPhone").val();

            return limsConsignmentInfo;
        }

        function checkInputValidation(){

            var consignErrCnt = 0;
            $(".required", "#consignment_form").each(function(){
                if($(this).val() == ""){
                    $("div.has-error",$(this).parents("div.form-group")).removeClass("hide");
                    consignErrCnt++;
                }else{
                    $("div.has-error",$(this).parents("div.form-group")).addClass("hide");
                }
            });

            if(consignErrCnt > 0) {
                alert("请补全委托信息的必填项！");
                return false;
            }

            var operate = $("#operate").val();
            if (operate == "1") {
                var delegatorName = $("#delegatorName").val();
                var delegatorDuty = $("#delegatorDuty option:selected").val();
                var delegatorCertificate = $("#delegatorCertificate option:selected").val();
                var delegatorCertificateNo = $("#delegatorCertificateNo").val();
                var delegatorNameArr = $("input[name='delegator'][value='"+ delegatorName +"']","#consignmentInfoListTbody");
                var delegatorDutyArr = $("input[name='delegatorDuty'][value='"+ delegatorDuty +"']","#consignmentInfoListTbody");
                var delegatorCertificateArr = $("input[name='delegatorCertificate'][value='"+ delegatorCertificate +"']","#consignmentInfoListTbody");
                var delegatorCertificateNoArr = $("input[name='delegatorCertificateNo'][value='"+ delegatorCertificateNo +"']","#consignmentInfoListTbody");

                if(delegatorNameArr.length > 0 && delegatorDutyArr.length > 0 && delegatorCertificateArr.length > 0 && delegatorCertificateNoArr.length > 0) {
                    alert("此人员已存在，请重新输入！");
                    return false;
                }
            }

            return true;
        }

        $("#saveBtn").on("click",function(){
            if(!checkInputValidation()){
                return;
            }

            var operate = $("#operate").val();

            $.ajax({
                url:"<%=path%>/center/7/addOrEditLimsConsignmentInfo.html?operate=" + operate,
                type:"post",
                data:JSON.stringify(getParams()),
                dataType:"json",
                contentType:"application/json;charset=utf-8",
                success: function (data) {
                    if(data.success){
                        var delegateOrgId = $("#delegateOrgId").val();
                        location.href = "<%=path%>/center/7/addConsignmentInfo.html?delegateOrgId=" + delegateOrgId;
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
            location.href='/DNA/center/7/02.html';
        });

        $("button[name='delBtn']","#consignmentInfoListTbody").on("click",function(){
            if(confirm("确认要删除吗？")){
                var id = $("input[name='id']", $(this).parent()).val();

                $.ajax({
                    url:"<%=path%>/center/7/delLimsConsignmentInfo.html?id=" + id,
                    type:"get",
                    dataType:"json",
                    success: function (data) {
                        if(data.success){
                            var delegateOrgId = $("#delegateOrgId").val();
                            location.href = "<%=path%>/center/7/addConsignmentInfo.html?delegateOrgId=" + delegateOrgId;
                        }else {
                            alert("删除失败!");
                        }
                    },
                    error:function(data){
                        alert("删除失败!");
                    }
                });
            }
        });
    });
</script>
<!-- END JS -->
</body>
</html>


