<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <!-- BEGIN STYLESHEET-->
    <link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="<%=path%>/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="<%=path%>/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON CSS -->
    <link href="<%=path%>/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="<%=path%>/css/style-responsive.css" rel="stylesheet"><!-- THEME RESPONSIVE CSS -->
    <!--[if lt IE 9]>
    <script src="<%=path%>/js/html5shiv.js">
    </script>
    <script src="<%=path%>/js/respond.min.js">
    </script>
    <![endif]-->
    <!-- END STYLESHEET-->
</head>
<body>
<!-- BEGIN WRAPPER  -->
<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <header class="panel-heading">
                <span class="label label-primary">质控人员信息</span>
                   <span class="tools pull-right">
                   <a href="javascript:;" class="fa fa-chevron-down"></a>
                </span>
            </header>
            <div class="panel-body">
                <form id="qcPersonForm" class="form-horizontal tasi-form">
                    <input type="hidden" id="qcPersonId" value="${qcPerson.id}"/>
                    <input type="hidden" id="operateType" value="${operateType}"/>
                    <input type="hidden" id="qcPersonStrGene" value="${qcPerson.qcPersonStrGene}"/>

                    <c:if test="${operateType eq 1}">
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">人员编号 <i class="fa fa-asterisk color_red"></i></label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control required" id="qcPersonNo1" value="${qcPerson.qcPersonNo}"/>
                            </div>
                            <div class="col-sm-2 has-error hide">
                                <p class="help-block">必填项</p>
                            </div>
                        </div>
                    </c:if>

                    <c:if test="${operateType eq 2}">
                        <div class="form-group">
                            <label class="col-sm-2 col-sm-2 control-label">证件编号</label>
                            <div class="col-sm-5">
                                <input type="text" class="form-control required" id="qcPersonNo2" value="${qcPerson.qcPersonNo}" readonly/>
                            </div>
                        </div>
                    </c:if>

                    <div class="form-group">
                        <label class="col-sm-2 col-sm-2 control-label">姓名 <i class="fa fa-asterisk color_red"></i></label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control required" id="qcPersonName" value="${qcPerson.qcPersonName}">
                        </div>
                        <div class="col-sm-2 has-error hide">
                            <p class="help-block">必填项</p>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 col-sm-2 control-label">性别</label>
                        <div class="col-sm-5">
                            <select class="form-control" id="qcPersonNameGender" value="${qcPerson.qcPersonGender}">
                                <option value="男" <c:if test="${qcPerson.qcPersonGender eq '男'}">selected</c:if>>男</option>
                                <option value="女" <c:if test="${qcPerson.qcPersonGender eq '女'}">selected</c:if>>女</option>
                            </select>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 col-sm-2 control-label">证件号码</label>
                        <div class="col-sm-5">
                            <input class="form-control" type="text" id="qcPersonCardId" value="${qcPerson.qcPersonCardId}"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 col-sm-2 control-label">联系电话</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="qcPersonPhonenum" value="${qcPerson.qcPersonPhonenum}"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 col-sm-2 control-label">单位名称</label>
                        <div class="col-sm-5">
                            <input type="text" class="form-control" id="qcPersonOrgName" value="${qcPerson.qcPersonOrgName}"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-2 control-label">是否为实验室人员</label>
                        <div class="col-sm-5">
                            <label class="checkbox-inline">
                                <input type="checkbox" name="qcLabUserFlag" value="${qcPerson.qcLabUserFlag}" <c:if test="${qcPerson.qcLabUserFlag eq 1}">checked</c:if>>
                            </label>
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
                <span class="label label-primary">基因型信息</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
            </header>
            <div class="panel-body">
                <div class="form-group">
                    <label class="col-sm-2 col-sm-2 control-label"  style="text-align:center;">试剂盒名称</label>
                    <div class="col-sm-2">
                        <select class="form-control" id="panelNameSelect">
                            <c:forEach items="${panelInfoList}" var="panelInfo" varStatus="do">
                                <option value="${panelInfo.id}" <c:if test="${qcPerson.panelName eq panelInfo.panelName}">selected</c:if>>${panelInfo.panelName}</option>
                            </c:forEach>
                        </select>
                    </div>
                </div>

                <div class="form-group">
                    <div class="col-sm-12">
                        <table class="table table-striped table-advance table-bordered table-hover" style="table-layout: fixed;width: 50%">
                            <thead>
                            <tr>
                                <th>序号</th>
                                <th>基因座名称</th>
                                <th>基因型1</th>
                                <th>基因型2</th>
                                <th>基因型3</th>
                                <th>基因型4</th>
                            </tr>
                            </thead>
                            <tbody id="geneInfoListTbody">
                            <c:forEach items="${qcPersonGeneList}" var="personGene" varStatus="s">
                                <textarea class="hide">${personGene.qcPersonStrGene}</textarea>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </section>
    </div>
</div>

<div class="row">
    <div class="col-lg-12">
        <section class="panel">
            <div class="panel-body">
                <form id="acceptForm" class="form-horizontal tasi-form" method="get">
                    <div class="form-group pull-right margin-r-20">
                        <button class="btn btn-lg btn-success" id="saveBtn" type="button"><i class="fa fa-check"></i> 保 存 </button>
                        <button class="btn btn-lg btn-info" id="backBtn" type="button"><i class="fa fa-reply"></i> 返 回 </button>
                    </div>
                </form>
            </div>
        </section>
    </div>
</div>

<div class="modal fade" id="EditQcPersonModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
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

<!-- END ROW  -->
<!-- BEGIN JS -->
<script src="<%=path%>/js/jquery.js" ></script><!-- BASIC JS LIABRARY -->
<script src="<%=path%>/js/bootstrap.min.js" ></script><!-- BOOTSTRAP JS  -->
<script src="<%=path%>/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script><!-- DATEPICKER JS  -->
<script src="<%=path%>/assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script><!-- DATEPICKER JS  -->
<script src="<%=path%>/js/respond.min.js" ></script><!-- RESPOND JS  -->
<script src="<%=path%>/js/jquery.paginate.js"></script>
<script src="<%=path%>/js/content.js"></script>
<script>

    window.onkeydown = function(e) {
        if(9 === e.keyCode) { // Tab 键

        }
    };

    $(function(){
        'use strict';

        function viewGeneDetail(geneInfo) {
            if(typeof(geneInfo)!="undefined" && geneInfo!=0){
                $("#panelNameSelect").append("<option value='' <c:if test='${empty qcPerson.panelName}'>selected</c:if>></option>");

                var jsonInfo = $.parseJSON(geneInfo);
                var len = jsonInfo.length;
                var gene;
                $("#geneInfoListTbody").empty();
                var htmlStr = "";
                for(var i = 0; i < len; i++){
                    gene = jsonInfo[i];
                    htmlStr += "<tr><td>"+(i + 1)+"</td>";
                    htmlStr += "<td><input type='hidden' name='locusName' id='locusName' value="+ gene.locusName +">" + gene.locusName + "</td>";
                    htmlStr += "<td><input type='text' name='geneVal1' id='geneVal1' class='form-control' tabindex="+(i + 1)+" value="+ gene.geneVal1 +"></td>";
                    htmlStr += "<td><input type='text' name='geneVal2' id='geneVal2' class='form-control' tabindex="+(i + 2)+" value="+ gene.geneVal2 +"></td>";
                    htmlStr += "<td><input type='text' name='geneVal3' id='geneVal3' class='form-control' value="+ gene.geneVal3 +"></td>";
                    htmlStr += "<td><input type='text' name='geneVal4' id='geneVal4' class='form-control' value="+ gene.geneVal4 +"></td>";
                    htmlStr +="</tr>";
                }
                $("#geneInfoListTbody").append(htmlStr);
            }else {
                panelSelectChange();
            }
        }

        viewGeneDetail($("textarea.hide").val());

        $("#panelNameSelect").on("change", function () {
            panelSelectChange();
        });

        function panelSelectChange(){

            var operateType = $("#operateType").val();

            $.ajax({
                url:"<%=path%>/center/qc/selectQcPersonQuery.html?operateType=" + operateType,
                type:"post",
                data:JSON.stringify(getParams()),
                dataType:"json",
                contentType: "application/json; charset=utf-8",
                success:function(data){

                    $("#geneInfoListTbody").html("");

                    var htmlStr = "";
                    if(typeof(data[0].geneInfo)!="undefined" && data[0].geneInfo!=null){

                        var jsonInfo = $.parseJSON(data[0].geneInfo);
                        var len = jsonInfo.length;
                        var gene;
                        for(var i = 0; i < len; i++){
                            gene = jsonInfo[i];
                            htmlStr += "<tr><td>"+(i + 1)+"</td>";
                            htmlStr += "<td><input type='hidden' name='locusName' id='locusName' value="+ gene.locusName +">" + gene.locusName + "</td>";
                            htmlStr += "<td><input type='text' name='geneVal1' id='geneVal1' class='form-control' tabindex="+(i + 1)+" value="+ gene.geneVal1 +"></td>";
                            htmlStr += "<td><input type='text' name='geneVal2' id='geneVal2' class='form-control' tabindex="+(i + 2)+" value="+ gene.geneVal2 +"></td>";
                            htmlStr += "<td><input type='text' name='geneVal3' id='geneVal3' class='form-control' value="+ gene.geneVal3 +"></td>";
                            htmlStr += "<td><input type='text' name='geneVal4' id='geneVal4' class='form-control' value="+ gene.geneVal4 +"></td>";
                            htmlStr +="</tr>";
                        }
                    }else {
                        var dataLen = data.length;
                        for(var i =0; i < dataLen;i++){
                            htmlStr += "<tr><td>"+(i + 1)+"</td>";
                            htmlStr += "<td><input type='hidden' name='locusName' id='locusName' value="+ data[i].markerName +">" + data[i].markerName + "</td>";
                            htmlStr += "<td><input type='text' name='geneVal1' id='geneVal1' class='form-control' tabindex="+(i + 1)+" value="+ data[i].geneVal1 +"></td>";
                            htmlStr += "<td><input type='text' name='geneVal2' id='geneVal2' class='form-control' tabindex="+(i + 2)+" value="+ data[i].geneVal2 +"></td>";
                            htmlStr += "<td><input type='text' name='geneVal3' id='geneVal3' class='form-control' value="+ data[i].geneVal3 +"></td>";
                            htmlStr += "<td><input type='text' name='geneVal4' id='geneVal4' class='form-control' value="+ data[i].geneVal4 +"></td>";
                            htmlStr +="</tr>";
                        }
                    }

                    $("#geneInfoListTbody").append(htmlStr);
                },
                error:function(data,e){
                    alert("查询失败!");
                }
            });

        }

        function getParams(){
            var qcPersonGene = {};

            qcPersonGene.qcPersonStrGene = $("#qcPersonStrGene").val();
            qcPersonGene.panelId = $("#panelNameSelect option:selected").val();
            qcPersonGene.panelName = $("#panelNameSelect option:selected").text();

            return qcPersonGene;
        }

        function GetQcPersonGene(operateType){
            var qcPersonGene = {};
            qcPersonGene.id = $("#qcPersonId").val();
            if (operateType == 1) {
                qcPersonGene.qcPersonNo = $("#qcPersonNo1").val();
            }else if (operateType == 2) {
                qcPersonGene.qcPersonNo = $("#qcPersonNo2").val();
            }
            qcPersonGene.qcPersonName = $("#qcPersonName").val();
            qcPersonGene.qcPersonGender = $("#qcPersonNameGender").val();
            qcPersonGene.qcPersonCardId = $("#qcPersonCardId").val();
            qcPersonGene.qcPersonPhonenum = $("#qcPersonPhonenum").val();
            qcPersonGene.qcPersonOrgName = $("#qcPersonOrgName").val();
            qcPersonGene.qcLabUserFlag = ($("input[name='qcLabUserFlag']").is(":checked")==true) ? "1" : "0";
            qcPersonGene.panelId = $("#panelNameSelect option:selected").val();
            qcPersonGene.panelName = $("#panelNameSelect option:selected").text();

            var geneArr = new Array();

            var $geneInfoTR = $("tr", "#geneInfoListTbody");
            var geneInfoCnt = $geneInfoTR.length;

            var geneLength = "";
            for (var i = 0; i < geneInfoCnt; i++) {
                var geneInfo  = {};
                geneInfo.locusName = $("input[name='locusName']", $geneInfoTR[i]).val();
                geneInfo.geneVal1 = $("input[name='geneVal1']", $geneInfoTR[i]).val();
                geneInfo.geneVal2 = $("input[name='geneVal2']", $geneInfoTR[i]).val();
                geneInfo.geneVal3 = $("input[name='geneVal3']", $geneInfoTR[i]).val();
                geneInfo.geneVal4 = $("input[name='geneVal4']", $geneInfoTR[i]).val();

                geneArr.push(geneInfo);
                geneLength += geneInfo.geneVal1 + geneInfo.geneVal2 + geneInfo.geneVal3 + geneInfo.geneVal4;
            }
            qcPersonGene.qcPersonStrGene = JSON.stringify(geneArr);

            return qcPersonGene;
        }

        function checkInputValidation(){
            var errCnt = 0;
            $(".required", "#qcPersonForm").each(function(){
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

            var panelNameSelect = $("#panelNameSelect").find("option:selected").val();
            if (panelNameSelect == "") {
                alert("请选择试剂盒名称！");
                return;
            }

            var geneInfoTRLen = GetGeneInfo();
            if(geneInfoTRLen == 0) {
                alert("请添加基因分型信息！");
                return false;
            }

            return true;
        }

        function GetGeneInfo(){

            var geneArr = new Array();

            var $geneInfoTR = $("tr", "#geneInfoListTbody");
            var geneInfoCnt = $geneInfoTR.length;
            var geneLength = "";
            for (var i = 0; i < geneInfoCnt; i++) {
                var geneInfo  = {};
                geneInfo.locusName = $("input[name='locusName']", $geneInfoTR[i]).val();
                geneInfo.geneVal1 = $("input[name='geneVal1']", $geneInfoTR[i]).val();
                geneInfo.geneVal2 = $("input[name='geneVal2']", $geneInfoTR[i]).val();
                geneInfo.geneVal3 = $("input[name='geneVal3']", $geneInfoTR[i]).val();
                geneInfo.geneVal4 = $("input[name='geneVal4']", $geneInfoTR[i]).val();

                geneArr.push(geneInfo);
                geneLength += geneInfo.geneVal1 + geneInfo.geneVal2 + geneInfo.geneVal3 + geneInfo.geneVal4;
            }

            if(geneLength.length == 0){
                return 0;
            }else {
                return geneArr;
            }

        }

        $("#saveBtn").on('click',function(){
            if(!checkInputValidation()){
                return;
            }

            var operateType=$("#operateType").val();
            var qcPersonGene = GetQcPersonGene(operateType);

            $.ajax({
                url : "<%=path%>/center/qc/saveQcPersonGene.html?operateType="+operateType,
                type:"post",
                contentType:  "application/json; charset=utf-8",
                data : JSON.stringify(qcPersonGene),
                dataType : "json",
                success : function(data) {
                    if(data.success == true || data.success == "true") {
                        $("#EditQcPersonModal").modal('show');
                    }else if (data.success == "repeat") {
                        alert("质控人员已存在，请重新填写！");
                        $("#qcPersonName").val('');
                        $("#qcPersonName").focus();
                    }else {
                        alert("保存失败！");
                    }
                },
                error: function(e){
                    alert(e);
                }
            });
        });

        $("#EditQcPersonModal").on('hidden.bs.modal', function(){
            location.href='<%=path%>/center/qc/1.html';
        });

        $("#backBtn").on("click",function(){
            location.href='<%=path%>/center/qc/1.html';
        });

    });
</script>
<!-- END JS -->
</body>
</html>


