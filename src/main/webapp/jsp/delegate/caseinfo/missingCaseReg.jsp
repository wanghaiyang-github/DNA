<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions"  prefix="fn"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <!-- BEGIN META -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- END META -->

    <!-- BEGIN SHORTCUT ICON -->
    <link rel="shortcut icon" href="<%=path%>/img/dna.ico">
    <!-- END SHORTCUT ICON -->
    <title>
        博安智联LIMS - ${loginTitleName}
    </title>
    <!-- BEGIN STYLESHEET-->
    <link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="<%=path%>/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="<%=path%>/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON CSS -->
    <link rel="stylesheet" type="text/css" href="<%=path%>/assets/bootstrap-datetimepicker/css/datetimepicker.css">
    <link href="<%=path%>/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="<%=path%>/css/style-responsive.css" rel="stylesheet"><!-- THEME RESPONSIVE CSS -->
    <link href="<%=path%>/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
    <!--dashboard calendar-->
    <link href="<%=path%>/css/clndr.css" rel="stylesheet"><!-- CALENDER CSS -->
    <!--[if lt IE 9]>
    <script src="<%=path%>/js/html5shiv.js">
    </script>
    <script src="<%=path%>/js/respond.min.js">
    </script>
    <![endif]-->
    <!-- END STYLESHEET-->
</head>
<body>
<!-- BEGIN SECTION -->
<section id="container">
    <!-- BEGIN HEADER -->
    <jsp:include page="../hearder.jsp"/>
    <!-- END HEADER -->

    <jsp:include page="../updateDelegatePassword.jsp"/>

    <!-- BEGIN SIDEBAR -->
    <aside>
        <div id="sidebar" class="nav-collapse">
            <ul class="sidebar-menu" id="nav-accordion">
                <li>
                    <a href="<%=path%>/wt/reg/1.html">
                        案件委托登记
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/reg/2.html" >
                        身份不明人员委托登记
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/reg/3.html" class="active">
                        失踪人口委托登记
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/caseinfo/listPending.html">
                        未受理
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/caseinfo/listAccepted.html">
                        已受理
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/caseinfo/listRefused.html">
                        已退案
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/message/listIdentify.html">
                        鉴定领取通知
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/consignment/listDelegator.html">
                        委托人管理
                    </a>
                </li>
            </ul>
        </div>
    </aside>
    <!-- END SIDEBAR -->

    <section id="main-content" class="main-content-delegate">
        <!-- BEGIN WRAPPER  -->
        <section class="wrapper wrapper-delegate">
            <!-- BEGIN ROW  -->
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            <span class="label label-primary">失踪人员案件信息</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                            <form id="caseinfo_form" class="form-horizontal tasi-form" method="get">
                                <input type="hidden" id="caseId" value="${caseInfo.id}"/>

                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件名称 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" id="caseName" name="caseName" class="form-control required" value="">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案发地点</label>
                                    <div class="col-sm-5">
                                        <input type="text" id="caseLocationDesc" name="caseLocationDesc" class="form-control" value="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案发时间 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input class="form_datetime form-control required" id="caseDatetime"
                                               name="caseDatetime" type="text"
                                               value="<fmt:formatDate value='${caseInfo.caseDatetime}' pattern='yyyy-MM-dd'/>" readonly>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">简要案情 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-7" style="width: 42%">
                                        <textarea class="form-control required" id="caseBrief" name="caseBrief" rows="3">${caseInfo.caseBrief}</textarea>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </section>
                </div>
            </div>
            <!-- END ROW  -->
            <!-- BEGIN ROW  -->
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            <span class="label label-primary">失踪人口委托信息</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                            <form id="consignment_form" class="form-horizontal tasi-form" method="get">
                                <input type="hidden" id="additionalFlag" value="0"/>
                                <input type="hidden" id="consignmentId" value="${consignment.id}"/>

                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托单位 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control required" placeholder="XXX公安司法鉴定中心" id="delegateOrgDesc" name="delegateOrgDesc" value="<c:if test="${empty consignment.delegateOrgDesc}"><shiro:user><shiro:principal property="orgName"/></shiro:user></c:if>${consignment.delegateOrgDesc}"/>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托单位电话</label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control " id="delegateOrgPhone" name="delegateOrgPhone"  value=""/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托编号</label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control"  id="entrustingSerial" name="entrustingSerial" value=""/>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托时间 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input class="form_datetime form-control required" id="delegateDatetime"
                                               name="delegateDatetime" type="text"
                                               value="<fmt:formatDate value='${caseInfo.caseDatetime}' pattern='yyyy-MM-dd'/>" readonly>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托人1（姓名、电话） <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-2">
                                        <select class="form-control required" name="delegator1" id="delegator1">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${delegatorList}" var="list" varStatus="s">
                                                <option value="${list}" <c:if test="${consignment.delegator1 eq list}">selected</c:if>>${list}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-3">
                                        <select class="form-control required" name="delegator1Phone" id="delegator1Phone">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${delegatorPhoneList}" var="list" varStatus="s">
                                                <option value="${list}" <c:if test="${consignment.delegator1Phone eq list}">selected</c:if>>${list}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托人1（证件、证件号） <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-2">
                                        <select class="form-control required" name="delegator1Cname" id="delegator1Cname">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${dictItemCertificateList}" var="list" varStatus="s">
                                                <option value="${list.dictName}" <c:if test="${consignment.delegator1Cname eq list.dictName}">selected</c:if>>${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-3">
                                        <select class="form-control required" name="delegator1Cno" id="delegator1Cno">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${delegatorCertificateNoList}" var="list" varStatus="s">
                                                <option value="${list}" <c:if test="${consignment.delegator1Cno eq list}">selected</c:if>>${list}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托人2（姓名、电话） <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-2">
                                        <select class="form-control required" name="delegator2" id="delegator2">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${delegatorList}" var="list" varStatus="s">
                                                <option value="${list}" <c:if test="${consignment.delegator2 eq list}">selected</c:if>>${list}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-3">

                                        <select class="form-control required" name="delegator2Phone" id="delegator2Phone">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${delegatorPhoneList}" var="list" varStatus="s">
                                                <option value="${list}" <c:if test="${consignment.delegator2Phone eq list}">selected</c:if>>${list}</option>
                                            </c:forEach>
                                        </select>

                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托人2（证件、证件号） <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-2">
                                        <select class="form-control required" name="delegator2Cname" id="delegator2Cname">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${dictItemCertificateList}" var="list" varStatus="s">
                                                <option value="${list.dictName}" <c:if test="${consignment.delegator2Cname eq list.dictName}">selected</c:if>>${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-3">
                                        <select class="form-control required" name="delegator2Cno" id="delegator2Cno">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${delegatorCertificateNoList}" var="list" varStatus="s">
                                                <option value="${list}" <c:if test="${consignment.delegator2Cno eq list}">selected</c:if>>${list}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">鉴定要求 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control required"  id="identifyRequirement" name="identifyRequirement" value="${limsEntrustmentInformation.appraisalPoints}"/>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托详情 </label>
                                    <div class="col-sm-7" style="width: 41.5%">
                                        <textarea class="form-control " id="remark" name="remark" rows="3"> </textarea>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </section>
                </div>
            </div>
            <!-- END ROW  -->

            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <div class="panel-body">
                            <div class="form-group">
                                <div class="col-lg-offset-4 col-lg-10">
                                    <input type="hidden" id="operateType" value="${operateType}"/>
                                    <button class="btn btn-lg btn-primary" id="nextBtn" type="button"><i class="fa fa-save"></i>保存并下一步</button>
                                    <c:if test="${operateType eq 1}">
                                        <button id="resetBtn"  class="btn btn-lg btn-default" type="button"><i class="fa fa-undo"></i> 重 填 </button>
                                        <button id="returnBtn" class="btn btn-lg btn-info" type="button"><i class="fa fa-reply"></i> 返 回 </button>
                                    </c:if>
                                    <c:if test="${operateType eq 2}">
                                        <button id="backBtn" class="btn btn-lg btn-default" type="button"><i class="fa fa-reply"></i> 返 回 </button>
                                    </c:if>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>

        </section>
    </section>

    <!-- BEGIN FOOTER -->
    <footer class="site-footer">
        <div class="text-center" style="margin-top:20px;">
            Copyright © 2016 北京博安智联科技有限公司
        </div>
    </footer>
</section>
<!-- BEGIN JS -->
<script src="<%=path%>/js/jquery.js" ></script><!-- BASIC JS LIABRARY -->
<script src="<%=path%>/js/bootstrap.min.js" ></script><!-- BOOTSTRAP JS  -->
<script src="<%=path%>/js/jquery.dcjqaccordion.2.7.js"></script><!-- ACCORDING JS -->
<script src="<%=path%>/js/jquery.scrollTo.min.js" ></script><!-- SCROLLTO JS  -->
<script src="<%=path%>/js/jquery.nicescroll.js" > </script><!-- NICESCROLL JS  -->
<script src="<%=path%>/js/jquery-ui-1.9.2.custom.min.js" ></script><!-- JQUERY UI JS  -->
<script src="<%=path%>/js/bootstrap-switch.js" ></script> <!-- BOOTSTRAP SWITCH JS  -->
<script src="<%=path%>/js/jquery.tagsinput.js" ></script> <!-- TAGS INPUT JS  -->
<script src="<%=path%>/js/ga.js"></script><!-- GA JS  -->
<script src="<%=path%>/assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script><!-- DATEPICKER JS  -->
<script src="<%=path%>/assets/bootstrap-inputmask/bootstrap-inputmask.min.js"></script><!-- INPUT MASK JS  -->
<script src="<%=path%>/js/respond.min.js" ></script><!-- RESPOND JS  -->
<script src="<%=path%>/js/common-scripts.js" ></script> <!-- BASIC COMMON JS  -->
<script>

    getMatchCaseNo();

    function getMatchCaseNo() {
        var matchSample = $("#matchCaseNo").val();
        if (matchSample !=null && matchSample != "")
            $("#matchCaseNo").show();
    }

    $(function () {

        $("#delegator1", "#consignment_form").on("change", function(){

            var delegatorName = $("#delegator1 option:selected").val();

            if (delegatorName == "") {
                $("#delegator1Phone").val("");
                $("#delegator1Cname").val("");
                $("#delegator1Cno").val("");

            }

            $.ajax({
                url:"<%=path%>/wt/consignment/selectDelagatorQuery.html?delegatorName=" + delegatorName,
                type:"post",
                dataType:"json",
                contentType: "application/json; charset=utf-8",
                success:function(data){
                    if (data.length > 0) {
                        var dataLen = data.length;
                        for(var i = 0; i < dataLen;i++){
                            $("#delegator1Phone").val(data[i].delegatorPhone);
                            $("#delegator1Cname").val(data[i].delegatorCertificateName);
                            $("#delegator1Cno").val(data[i].delegatorCertificateNo);
                        }
                    }
                },
                error:function(data,e){
                    alert("查询失败!");
                }
            });
        });

        $("#delegator2", "#consignment_form").on("change", function(){

            var delegatorName = $("#delegator2 option:selected").val();

            if (delegatorName == "") {
                $("#delegator2Phone").val("");
                $("#delegator2Cname").val("");
                $("#delegator2Cno").val("");
            }

            $.ajax({
                url:"<%=path%>/wt/consignment/selectDelagatorQuery.html?delegatorName=" + delegatorName,
                type:"post",
                dataType:"json",
                contentType: "application/json; charset=utf-8",
                success:function(data){
                    if (data.length > 0) {
                        var dataLen = data.length;
                        for(var i = 0; i < dataLen;i++){
                            $("#delegator2Phone").val(data[i].delegatorPhone);
                            $("#delegator2Cname").val(data[i].delegatorCertificateName);
                            $("#delegator2Cno").val(data[i].delegatorCertificateNo);
                        }
                    }
                },
                error:function(data,e){
                    alert("查询失败!");
                }
            });
        });

        $("#identifyRequirementList","#consignment_form").on("click", function () {
            var identifyRequirementCode = $("option:selected", $(this)).val();
            var identifyRequirementName = $("option:selected", $(this)).text();

            if (identifyRequirementCode != "") {
                if (identifyRequirementCode == "05") {
                    var identifyRequirementOtherHide = $("#identifyRequirementOtherHide").val();
                    $("#identifyRequirementOther").val(identifyRequirementOtherHide);
                    $("input[name='identifyRequirementOther']").attr("placeholder","DNA检验鉴定与毕公司鉴（法物）字【XXX】XXX号鉴定文书中相关检材做比对。");
                    $("#matchCaseNo").show();
                }else {
                    $("input[name='identifyRequirementOther']").removeAttr("placeholder");
                    $("#identifyRequirementOther").val(identifyRequirementName);
                    $("#matchCaseNo").hide();
                }
            }else {
                $("input[name='identifyRequirementOther']").removeAttr("placeholder");
                $("#identifyRequirementOther").val("");
                $("#matchCaseNo").hide();
            }
        });

        $('.form_datetime').datetimepicker({
            format: 'yyyy-mm-dd',
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

        $('.form_date').datetimepicker({
            format: 'yyyy-mm-dd',
            language:  'zh',
            weekStart: 1,
            minView: "month",
            todayBtn:  1,
            autoclose: true,
            todayHighlight: true,
            forceParse: 0,
            showMeridian: 1
        }).on('changeDate', function (ev) {
            $(this).datetimepicker('hide');
        });

        $("#returnBtn").on("click",function(){
            if (!confirm("此操作不保存数据，是否返回！")) {
                return;
            }
            location.href="<%=path%>/wt/reg/3.html";
        });

        $("#resetBtn").on("click",function(){
            location.href="<%=path%>/wt/reg/3.html";
        });

        $("#backBtn").on("click",function(){
            location.href="<%=path%>/wt/caseinfo/listPending.html";
        });

        $("#getXkBtn").on("click",function(){
            var caseXkNo = $("#caseXkNo","#caseinfo_form").val();
            if(caseXkNo == ""){
                alert("请输入现堪编号！");
                return ;
            }
            location.href = "<%=path%>/wt/reg/getCaseInfoXk.html";
        });

        $("#nextBtn").on("click", function(){
            submitForm();
        });

        function submitForm() {
            if(!checkInputValidation()){
                return;
            }

            var caseInfo = GetCaseInfo();
            var consignment = GetConsignment();
            var operateType = $("#operateType").val();

            var params = {
                "caseInfo": caseInfo,
                "consignment": consignment
            };

            $.ajax({
                url : "<%=path%>/wt/reg/addMissingCase.html?operateType="+operateType,
                type:"post",
                contentType:  "application/json; charset=utf-8",
                data : JSON.stringify(params),
                dataType : "json",
                success : function(data) {
                    if (data.result){
                        var consignmentId = data.consignmentId;
                        var caseId = data.caseId;
                        location.href = "<%=path%>/wt/reg/identifyPersonSampleReg.html?consignmentId=" + consignmentId + "&operateType=" + data.operateType +"&caseId="+caseId;
                    }else {
                        alert("保存失败！");
                    }
                },
                error: function(e){
                    alert(e);
                }
            });
        }

        function checkInputValidation(){
            //caseInfo_form validate
            var caseErrCnt = 0;
            $(".required", "#caseinfo_form").each(function(){
                if($(this).val() == ""){
                    $("div.has-error",$(this).parents("div.form-group")).removeClass("hide");
                    caseErrCnt++;
                }else{
                    $("div.has-error",$(this).parents("div.form-group")).addClass("hide");
                }
            });

            var consignErrCnt = 0;
            $(".required", "#consignment_form").each(function(){
                if($(this).val() == ""){
                    $("div.has-error",$(this).parents("div.form-group")).removeClass("hide");
                    consignErrCnt++;
                }else{
                    $("div.has-error",$(this).parents("div.form-group")).addClass("hide");
                }
            });

            if(caseErrCnt > 0 || consignErrCnt > 0) {
                alert("请补全案件和委托信息的必填项！");
                return false;
            }

            return true;
        }

        function GetCaseInfo() {
            var caseInfo = {};
            caseInfo.caseName = $("#caseName", "#caseinfo_form").val();
            caseInfo.caseDatetime = $("#caseDatetime", "#caseinfo_form").val();
            caseInfo.caseBrief = $("#caseBrief", "#caseinfo_form").val();
            caseInfo.caseLocationDesc = $("#caseLocationDesc", "#caseinfo_form").val();
            caseInfo.entrustmentType = $("#entrustmentType", "#caseinfo_form").val();
            return caseInfo;
        }

        function GetConsignment() {
            var consignment = {}
            consignment.delegateOrg = $("#delegateOrg", "#consignment_form").val();
            consignment.delegateOrgDesc = $("#delegateOrgDesc", "#consignment_form").val();
            consignment.delegateOrgPhone = $("#delegateOrgPhone", "#consignment_form").val();
            consignment.entrustingSerial = $("#entrustingSerial", "#consignment_form").val();
            consignment.delegateDatetime = $("#delegateDatetime", "#consignment_form").val();
            consignment.delegator1 = $("#delegator1", "#consignment_form").val();
            consignment.delegator1Phone = $("#delegator1Phone", "#consignment_form").val();
            consignment.delegator1Cname = $("#delegator1Cname", "#consignment_form").val();
            consignment.delegator1Cno = $("#delegator1Cno", "#consignment_form").val();
            consignment.delegator2 = $("#delegator2", "#consignment_form").val();
            consignment.delegator2Phone = $("#delegator2Phone", "#consignment_form").val();
            consignment.delegator2Cname = $("#delegator2Cname", "#consignment_form").val();
            consignment.delegator2Cno = $("#delegator2Cno", "#consignment_form").val();
            consignment.caseId = $("#consignmentCaseId", "#consignment_form").val();
            consignment.identifyRequirement = $("#identifyRequirement", "#consignment_form").val();
            consignment.remark = $("#remark", "#consignment_form").val();

            return consignment
        }


    });
</script>
<!-- END JS -->
</body>
</html>


