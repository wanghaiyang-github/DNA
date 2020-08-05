<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
                    <c:choose>
                        <c:when test="${caseInfo.entrustmentType eq '01'}">
                            <a href="<%=path%>/wt/reg/2.html" class="active">
                                身份不明人员委托登记
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="<%=path%>/wt/reg/2.html">
                                身份不明人员委托登记
                            </a>
                        </c:otherwise>
                    </c:choose>
                </li>
                <li>
                    <c:choose>
                        <c:when test="${caseInfo.entrustmentType eq '02'}">
                            <a href="<%=path%>/wt/reg/3.html" class="active">
                                失踪人口委托登记
                            </a>
                        </c:when>
                        <c:otherwise>
                            <a href="<%=path%>/wt/reg/3.html">
                                失踪人口委托登记
                            </a>
                        </c:otherwise>
                    </c:choose>
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

            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            <span class="label label-primary">人员检材信息</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                            <form id="personinfo_form" class="form-horizontal tasi-form" method="post">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label" style="width: 135px;">人员名称 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-4" style="padding-left: 20px">
                                        <input type="text" id="personName" name="personName"
                                               class="form-control required" value="">
                                    </div>
                                    <div class="col-sm-1 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                    <label class="col-sm-1  control-label" style="width: 140px;">人员类别<i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-4">
                                        <select class="form-control required" id="personType" name="personType">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${personTypeList}" var="person" varStatus="s">
                                                <option value="${person.dictCode}">${person.dictName}</option>

                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-1 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-1 control-label" style="width: 135px;">性别</label>
                                    <div class="col-sm-4" style="padding-left: 20px">
                                        <select class="form-control" id="personGender" name="personGender">
                                            <c:forEach items="${personGenderList}" var="personGender" varStatus="s">
                                                <option value="${personGender.dictCode}">${personGender.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-1  control-label" style="width: 140px;letter-spacing: 25px;">名族</label>
                                    <div class="col-sm-4">
                                        <select class="form-control" id="personRace" name="personRace">
                                            <c:forEach items="${personRaceList}" var="personRace" varStatus="s">
                                                <option value="${personRace.dictCode}">${personRace.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1  control-label">身份证号<i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-4" style="padding-left: 0px">
                                        <div class="col-lg-8">
                                            <input type="text" id="personIdcardNo" name="personIdcardNo"
                                                   class="form-control required" value="">
                                        </div>
                                        <div class="col-lg-4">
                                            <input id="noIdcardRemarkCkb" name="noIdcardRemarkCkb" type="checkbox"/>
                                            无身份证
                                        </div>
                                        <div class="col-lg-12" style="margin-top: 4px">
                                            <input type="text" name="noIdcardRemark" id="noIdcardRemark"
                                                   style="width:455px;"
                                                   readonly="readonly" class="form-control" placeholder="无身份备注"/>
                                        </div>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                    <label class="col-sm-1  control-label" style="width: 135px;">样本名称<i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-4">
                                        <input type="text" id="sampleName" name="sampleName"
                                               class="form-control required"
                                               value="">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">

                                    <label class="col-sm-1  control-label" style="width: 140px;">样本类型<i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-4">
                                        <select class="form-control required" id="sampleType" name="sampleType">
                                            <c:forEach items="${sampleTypeList}" var="sampleType" varStatus="s">
                                                <option value="${sampleType.dictCode}">${sampleType.dictName}</option>

                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                    <label class="col-sm-1  control-label" style="width: 135px;">样本与本人关系<i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-4">
                                        <select class="form-control required" id="oneselfContact" name="oneselfContact">
                                            <c:forEach items="${personRelationList}" var="personRelation" varStatus="s">
                                                <option value="${personRelation.dictCode}">${personRelation.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                    <div class="form-group" style="padding-left: 20px;">
                                        <button class="btn btn-lg btn-primary" type="button" id="newPersonSampleBtn">
                                            <i class="fa fa-plus"></i> 添加
                                        </button>
                                    </div>
                                </div>
                            </form>

                        </div>
                    </section>
                </div>
            </div>
            <!-- BEGIN ROW  -->
            <div class="row">
                <div class="col-lg-12">
                    <header class="panel-body">
                        <span class="label label-primary">人员样本列表</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                    </header>
                    <section class="panel">
                        <div class="panel-body">
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>人员姓名</th>
                                    <th>性别</th>
                                    <th>名族</th>
                                    <th>身份证号</th>
                                    <th>人员类别</th>
                                    <th>样本名称</th>
                                    <th>样本类型</th>
                                    <th>样本与本人关系</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="personInfoTbody">
                                <tr class="regedTr">
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </section>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <div class="panel-body">
                            <div class="form-group">
                                <div class="col-lg-offset-4 col-lg-10">
                                    <input type="hidden" id="consignmentId" name="consignmentId"
                                           value="${consignmentId}"/>
                                    <input type="hidden" id="operateType" value="${dataMap.operateType}"/>
                                    <input type="hidden" id="delegateOrgDesc" value="${delegateOrgDesc}"/>
                                    <input type="hidden" id="caseId" value="${caseId}"/>
                                    <button class="btn btn-lg btn-primary" id="submitBtn" type="button"><i
                                            class="fa fa-save"></i> 完 成
                                    </button>
                                    <button class="btn btn-lg btn-info" type="button"
                                            onclick="javascript:history.go(-1);"><i class="fa fa-reply"></i> 返 回
                                    </button>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>

            <div class="modal fade" id="regModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
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
                            <button data-dismiss="modal" class="btn btn-default" type="button"><i
                                    class="fa fa-list-alt"></i> 完成
                            </button>
                        </div>
                    </div>
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
<script src="<%=path%>/js/jquery.js"></script><!-- BASIC JS LIABRARY -->
<script src="<%=path%>/js/bootstrap.min.js"></script><!-- BOOTSTRAP JS  -->
<script src="<%=path%>/js/jquery.dcjqaccordion.2.7.js"></script><!-- ACCORDING JS -->
<script src="<%=path%>/js/jquery.scrollTo.min.js"></script><!-- SCROLLTO JS  -->
<script src="<%=path%>/js/jquery.nicescroll.js"></script><!-- NICESCROLL JS  -->
<script src="<%=path%>/js/jquery-ui-1.9.2.custom.min.js"></script><!-- JQUERY UI JS  -->
<script src="<%=path%>/js/bootstrap-switch.js"></script> <!-- BOOTSTRAP SWITCH JS  -->
<script src="<%=path%>/js/jquery.tagsinput.js"></script> <!-- TAGS INPUT JS  -->
<script src="<%=path%>/js/ga.js"></script><!-- GA JS  -->
<script src="<%=path%>/assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script><!-- DATEPICKER JS  -->
<script src="<%=path%>/assets/bootstrap-inputmask/bootstrap-inputmask.min.js"></script><!-- INPUT MASK JS  -->
<script src="<%=path%>/js/respond.min.js"></script><!-- RESPOND JS  -->
<script src="<%=path%>/js/common-scripts.js"></script> <!-- BASIC COMMON JS  -->
<script>

    $("#noIdcardRemarkCkb").on("click", function () {
        if ($(this).is(":checked")) {
            $("#personIdcardNo").val("无");
            $("#personIdcardNo").prop("readonly", "readonly");

            $("#noIdcardRemark").prop("readonly", false);
            $("#noIdcardRemark").val("");
            $("#noIdcardRemark").focus();
        } else {
            $("#personIdcardNo").val("");
            $("#personIdcardNo").prop("readonly", false);
            $("#personIdcardNo").focus();

            $("#noIdcardRemark").prop("readonly", "readonly");
            $("#noIdcardRemark").val("");
        }
    });

    function generatePersonIdx() {
        $("tr", "#personInfoTbody").each(function () {
            $("td:first", $(this)).text($(this).index());
        });
    }

    $(function () {
        $('.form_datetime').datetimepicker({
            format: 'yyyy-mm-dd',
            language: 'zh',
            weekStart: 1,
            todayBtn: 1,
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
            language: 'zh',
            weekStart: 1,
            minView: "month",
            todayBtn: 1,
            autoclose: true,
            todayHighlight: true,
            forceParse: 0,
            showMeridian: 1
        }).on('changeDate', function (ev) {
            $(this).datetimepicker('hide');
        });

        function GetPersonSample() {
            var personSampleArr = new Array();
            var personSample;
            if ($("tr", "#personInfoTbody").length > 0) {
                var $samplePersonTRArr = $("tr", "#personInfoTbody").not(".regedTr");
                var samplePersonCnt = $samplePersonTRArr.length;
                var $samplePersonTR;
                for (var i = 0; i < samplePersonCnt; i++) {
                    personSample = {};
                    $samplePersonTR = $($samplePersonTRArr.get(i));
                    personSample.entrustmentId = $("#consignmentId").val();
                    personSample.caseInformationId = $("input[name='caseId']", $samplePersonTR).val();
                    personSample.personName = $("input[name='personName']", $samplePersonTR).val();
                    personSample.personGenderName = $("input[name='personGenderName']", $samplePersonTR).val();
                    personSample.personGender = $("input[name='personGender']", $samplePersonTR).val();
                    personSample.personIdcardNo = $("input[name='personIdcardNo']", $samplePersonTR).val();
                    personSample.noIdcardRemark = $("input[name='noIdcardRemark']", $samplePersonTR).val();
                    personSample.personRace = $("input[name='personRace']", $samplePersonTR).val();
                    personSample.sampleName = $("input[name='sampleName']", $samplePersonTR).val();
                    personSample.sampleType = $("input[name='sampleType']", $samplePersonTR).val();
                    personSample.oneselfContact = $("input[name='oneselfContact']", $samplePersonTR).val();
                    personSample.personType = $("input[name='personType']", $samplePersonTR).val();
                    personSampleArr.push(personSample);
                }
            }
            return personSampleArr;
        }

        $("#submitBtn").on("click", function () {

            var consignmentId = $("input[name='consignmentId']").val();
            var personSampleList = GetPersonSample();

            if (personSampleList == "") {
                alert("请添加人员样本信息！");
                return;
            }

            var params = {
                "personSampleList": personSampleList
            };

            $.ajax({
                url: "<%=path%>/wt/reg/addPersonSample.html?consignmentId=" + consignmentId,
                type: "post",
                data: JSON.stringify(params),
                dataType: "json",
                contentType: "application/json;charset=utf-8",
                success: function (data) {
                    alert("添加成功")
                    location.href = "<%=path%>/wt/caseinfo/listPending.html";
                },
                error: function (data) {
                    alert("添加失败!");
                }
            });

        });

        function checkInputValidation() {
            var caseErrCnt = 0;
            $(".required", "#personinfo_form").each(function () {
                if ($(this).val() == "") {
                    $("div.has-error", $(this).parents("div.form-group")).removeClass("hide");
                    caseErrCnt++;
                } else {
                    $("div.has-error", $(this).parents("div.form-group")).addClass("hide");
                }
            });

            if (caseErrCnt > 0) {
                alert("请补全人员检材信息的必填项！");
                return false;
            }
            return true;
        }

        $("#newPersonSampleBtn").on("click", function () {

            /*var sampleLaboratoryNo = $("input[name='sampleLaboratoryNo']").val();

            $.ajax({
             url: "
            <%=path%>/wt/reg/checkSampleLaboratoryNo.html?sampleLaboratoryNo=" + sampleLaboratoryNo,
             type: "post",
             async: false,
             cache: false,
             dataTyp: "json",
             success: function (data) {
             if (!data.success && data.success != "true") {
             alert("该实验室编号已存在，请重新输入！")
             document.getElementById("sampleLaboratoryNo").value="";
             document.getElementById("sampleLaboratoryNo").focus();
             } else {

             }
             }
             });*/
            newPersonSample();
        });

        function newPersonSample() {
            if (!checkInputValidation()) {
                return;
            }
            var personName = $("input[name='personName']").val();
            var personType = $("select[name='personType']").find("option:selected").text();

            var personGender = $("select[name='personGender']").val();
            var personGenderName = $("select[name='personGender']").find("option:selected").text();
            var personRace = $("select[name='personRace']").find("option:selected").text();
            var personIdcardNo = $("input[name='personIdcardNo']").val();
            var noIdcardRemark = $("input[name='noIdcardRemark']").val();

            var sampleName = $("input[name='sampleName']").val();
            var sampleType = $("select[name='sampleType']").find("option:selected").text();
            var oneselfContact = $("select[name='oneselfContact']").find("option:selected").text();

            var personIdCard = personIdcardNo+"("+noIdcardRemark+")";

            if (personIdcardNo == "无") {
                var newRowHtml = "<td></td>";
                newRowHtml += "<td><input type='hidden' name='personName' value='" + personName + "'/>" + personName + "</td>";

                newRowHtml += "<td><input type='hidden' name='personGenderName' value='" + personGenderName + "'/>" + personGenderName + "</td>";
                newRowHtml += "<td><input type='hidden' name='personRace' value='" + personRace + "'/>" + personRace + "</td>";
                newRowHtml += "<td><input type='hidden' name='noIdcardRemark' value='" + personIdCard + "'/>" + personIdCard + "</td>";
                newRowHtml += "<td style='display: none'><input type='hidden' name='personGender' value='" + personGender + "'/>" + personGender + "</td>";
                newRowHtml += "<td style='display: none'><input type='hidden' name='personIdcardNo' value='" + personIdcardNo + "'/>" + personIdcardNo + "</td>";
                newRowHtml += "<td><input type='hidden' name='personType' value='" + personType + "'/>" + personType + "</td>";
                newRowHtml += "<td><input type='hidden' name='sampleName' value='" + sampleName + "'/>" + sampleName + "</td>";
                newRowHtml += "<td><input type='hidden' name='sampleType' value='" + sampleType + "'/>" + sampleType + "</td>";
                newRowHtml += "<td><input type='hidden' name='oneselfContact' value='" + oneselfContact + "'/>" + oneselfContact + "</td>";
                newRowHtml += "<td><button name='delSampleBtn'  class='btn btn-danger btn-sm' ><i class='fa fa-trash-o'></i> 删除</button></td>";
                $("#personInfoTbody").append("<tr>" + newRowHtml + "</tr>");
                document.getElementById("personinfo_form").reset();
            } else {
                $.ajax({
                    url: "../checkIdcard.html?idcardNo=" + personIdcardNo,
                    type: "get",
                    async: false,
                    cache: false,
                    dataTyp: "json",
                    success: function (data) {
                        if (!data.success && data.success != "true") {
                            alert("身份证输入不正确，请重新输入！")
                        } else {
                            var newRowHtml = "<td></td>";
                            newRowHtml += "<td><input type='hidden' name='personName' value='" + personName + "'/>" + personName + "</td>";

                            newRowHtml += "<td><input type='hidden' name='personGenderName' value='" + personGenderName + "'/>" + personGenderName + "</td>";
                            newRowHtml += "<td><input type='hidden' name='personRace' value='" + personRace + "'/>" + personRace + "</td>";
                            newRowHtml += "<td><input type='hidden' name='personIdcardNo' value='" + personIdcardNo + "'/>" + personIdcardNo + "</td>";
                            newRowHtml += "<td style='display: none'><input type='hidden' name='personGender' value='" + personGender + "'/>" + personGender + "</td>";
                            newRowHtml += "<td style='display: none'><input type='hidden' name='noIdcardRemark' value='" + noIdcardRemark + "'/>" + noIdcardRemark + "</td>";

                            newRowHtml += "<td><input type='hidden' name='personType' value='" + personType + "'/>" + personType + "</td>";
                            newRowHtml += "<td><input type='hidden' name='sampleName' value='" + sampleName + "'/>" + sampleName + "</td>";
                            newRowHtml += "<td><input type='hidden' name='sampleType' value='" + sampleType + "'/>" + sampleType + "</td>";
                            newRowHtml += "<td><input type='hidden' name='oneselfContact' value='" + oneselfContact + "'/>" + oneselfContact + "</td>";
                            newRowHtml += "<td><button name='delSampleBtn'  class='btn btn-danger btn-sm' ><i class='fa fa-trash-o'></i> 删除</button></td>";
                            $("#personInfoTbody").append("<tr>" + newRowHtml + "</tr>");
                            document.getElementById("personinfo_form").reset();
                        }
                    }
                });
            }

            $("button[name='delSampleBtn']", "#personInfoTbody").on("click", function () {
                $(this).parent().parent().remove();
            });
            generatePersonIdx();

        }

        $("#regModal").on('hidden.bs.modal', function () {
            location.href = "<%=path%>/wt/caseinfo/listPending.html";
        });
    });
</script>
<!-- END JS -->
</body>
</html>


