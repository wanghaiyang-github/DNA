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
                                    <div class="col-sm-4">
                                        <input type="text" id="personName" name="personName"
                                               class="form-control required" value="">
                                    </div>
                                    <div class="col-md-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                    <label class="col-sm-1  control-label" style="width: 140px;">人员类别<i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-4">
                                        <input type="text" id="personType" name="personType"
                                               class="form-control required"
                                               value="">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
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
                                    <label class="col-sm-1  control-label" style="width: 140px;">样本类型<i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-4">
                                        <input type="text" id="sampleType" name="sampleType"
                                               class="form-control required"
                                               value="">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1  control-label" style="width: 135px;">样本与本人关系<i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-4">
                                        <input type="text" id="oneselfContact" name="oneselfContact"
                                               class="form-control required" value="">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                    <label class="col-sm-1  control-label" style="width: 140px;">样本实验室编号 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-4">
                                        <input type="text" id="sampleLaboratoryNo" name="sampleLaboratoryNo"
                                               class="form-control" value="">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="form-group" style="padding-left: 25px;">

                            <button class="btn btn-primary" id="newPersonSampleBtn">
                                <i class="fa fa-plus"></i> 添加
                            </button>
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
                                <th>人员类别</th>
                                <th>样本名称</th>
                                <th>样本类型</th>
                                <th>样本与本人关系</th>
                                <th>样本实验室编号</th>
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
                                    <input type="hidden" id="entrustmentId" name="entrustmentId" value="${entrustmentId}"/>
                                    <input type="hidden" id="operateType" value="${operateType}"/>
                                    <%--<c:choose>
                                        <c:when test="${dataMap.status == '04'}">
                                            <button class="btn btn-lg btn-primary" id="reSubmitBtn" type="button"><i class="fa fa-save"></i>重新送检</button>
                                        </c:when>
                                        <c:otherwise>
                                            <button class="btn btn-lg btn-primary" id="submitBtn" type="button"><i class="fa fa-save"></i> 完 成 </button>
                                        </c:otherwise>
                                    </c:choose>--%>
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

            <%--<div class="modal fade" id="regModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
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
                            &lt;%&ndash;<button name="downloadDocBtn" class="btn btn-success btn-sm"><i class="fa fa-print"></i> 委托表</button>&ndash;%&gt;
                        </div>
                    </div>
                </div>
            </div>--%>
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

    function generatePersonIdx() {
        $("tr", "#personInfoTbody").each(function () {
            $("td:first", $(this)).text($(this).index() );
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

        function GetCaseInfo() {
            var caseInfo = {};
            caseInfo.id = $("#caseId").val();
            return caseInfo;
        }

        function GetConsignment() {
            var consignment = {};
            consignment.id = $("#consignmentId").val();
            return consignment;
        }

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
                   /* personSample.entrustmentId = $("input[name='entrustmentId']", $samplePersonTR).val();*/
                    personSample.personName = $("input[name='personName']", $samplePersonTR).val();
                    personSample.sampleName = $("input[name='sampleName']", $samplePersonTR).val();
                    personSample.sampleType = $("input[name='sampleType']", $samplePersonTR).val();
                    personSample.oneselfContact = $("input[name='oneselfContact']", $samplePersonTR).val();
                    personSample.sampleLaboratoryNo = $("input[name='sampleLaboratoryNo']", $samplePersonTR).val();
                    personSample.personType = $("input[name='personType']", $samplePersonTR).val();
                    personSampleArr.push(personSample);
                }
            }
            return personSampleArr;
        }

        $("#submitBtn").on("click", function () {

            var entrustmentId = $("input[name='entrustmentId']").val();
            var personSampleList = GetPersonSample();

            if (personSampleList == "") {
                alert("请添加人员样本信息！");
                return;
            }

            var params = {
                "personSampleList": personSampleList
            };

            console.log(params);

            $.ajax({
                url: "<%=path%>/wt/reg/addPersonSample.html?entrustmentId="+entrustmentId,
                type: "post",
                data: JSON.stringify(params),
                dataType: "json",
                contentType: "application/json;charset=utf-8",
                success: function (data) {
                    alert("添加成功")
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

            if (!checkInputValidation()) {
                return;
            }

            var personName = $("input[name='personName']").val();
            var personType = $("input[name='personType']").val();
            var sampleName = $("input[name='sampleName']").val();
            var sampleType = $("input[name='sampleType']").val();
            var oneselfContact = $("input[name='oneselfContact']").val();
            var sampleLaboratoryNo = $("input[name='sampleLaboratoryNo']").val();

            var newRowHtml = "<td></td>";
            //newRowHtml += "<td style='display: none;'><input type='hidden' name='entrustmentId' value='" + entrustmentId + "'/>" + entrustmentId + "</td>";
            newRowHtml += "<td><input type='hidden' name='personName' value='" + personName + "'/>" + personName + "</td>";
            newRowHtml += "<td><input type='hidden' name='personType' value='" + personType + "'/>" + personType + "</td>";
            newRowHtml += "<td><input type='hidden' name='sampleName' value='" + sampleName + "'/>" + sampleName + "</td>";
            newRowHtml += "<td><input type='hidden' name='sampleType' value='" + sampleType + "'/>" + sampleType + "</td>";
            newRowHtml += "<td><input type='hidden' name='oneselfContact' value='" + oneselfContact + "'/>" + oneselfContact + "</td>";
            newRowHtml += "<td><input type='hidden' name='sampleLaboratoryNo' value='" + sampleLaboratoryNo + "'/>" + sampleLaboratoryNo + "</td>";
            newRowHtml += "<td><button name='delSampleBtn'  class='btn btn-danger btn-sm' ><i class='fa fa-trash-o'></i> 删除</button></td>";
            $("#personInfoTbody").append("<tr>" + newRowHtml + "</tr>");

            $("button[name='delSampleBtn']", "#personInfoTbody").on("click", function () {
                $(this).parent().parent().remove();
            });
            generatePersonIdx();
        });


    });
</script>
<!-- END JS -->
</body>
</html>


