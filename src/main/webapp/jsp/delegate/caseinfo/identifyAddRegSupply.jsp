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
                    <a href="<%=path%>/wt/reg/3.html" >
                        失踪人口委托登记
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/caseinfo/listPending.html" class="active">
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
                            <span class="label label-primary">案件信息</span>
                           <span class="tools pull-right">
                           <a name="chevronUp" href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                            <form id="caseinfo_form" class="form-horizontal tasi-form" method="get">
                                <input type="hidden" id="caseId" value="${caseInfo.id}"/>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件编号 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" id="caseNo" name="caseNo" class="form-control required" value="${caseInfo.caseNo}" readonly>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件现堪编号 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="hidden" name="xkLoginName" value="${xkLoginName}">
                                        <input type="hidden" name="xkLoginPassword" value="${xkLoginPassword}">
                                        <input type="text" id="caseXkNo" name="caseXkNo" class="form-control required" value="${caseInfo.caseXkNo}" readonly>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件名称 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" id="caseName" name="caseName" class="form-control required" value="${caseInfo.caseName}" readonly>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案发地点</label>
                                    <div class="col-sm-5">
                                        <input type="text" id="caseLocation" name="caseLocation" class="form-control" value="${caseInfo.caseLocation}" readonly>
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
                                    <div class="col-sm-7">
                                        <textarea class="form-control required" id="caseBrief" name="caseBrief" rows="3" readonly>${caseInfo.caseBrief}</textarea>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件类型 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-3">
                                        <select class="form-control required" name="caseType" id="caseType" value="${caseInfo.caseType}" readonly>
                                            <c:forEach items="${caseTypeList}" var="list" varStatus="s">
                                                <option value="${list.dictCode}" <c:if test="${caseInfo.caseType eq list.dictCode}">selected</c:if>>${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">案件性质 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-3">
                                        <select class="form-control required" name="caseProperty" id="caseProperty" value="${caseInfo.caseProperty}" readonly>
                                            <c:forEach items="${casePropertyList}" var="list" varStatus="s">
                                                <option value="${list.dictCode}" <c:if test="${caseInfo.caseProperty eq list.dictCode}">selected</c:if>>${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件级别</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" name="caseLevel" id="caseLevel" value="${caseInfo.caseLevel}" readonly>
                                            <option value=""></option>
                                            <c:forEach items="${caseLevelList}" var="list" varStatus="s">
                                                <option value="${list.dictCode}" <c:if test="${caseInfo.caseLevel eq list.dictCode}">selected</c:if>>${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">是否加急</label>
                                    <label class="col-sm-2 col-sm-2 control-label">
                                        <c:if test="${caseInfo.jiajiFlag eq 1}">是</c:if><c:if test="${caseInfo.jiajiFlag eq 0}">否</c:if>
                                        <input name="jiajiFlag" type="hidden" value="${caseInfo.jiajiFlag}"/>
                                    </label>
                                    <div class="col-sm-3">
                                        <label class="checkbox-inline">

                                        </label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">其他说明</label>
                                    <div class="col-sm-5">
                                        <textarea class="form-control" name="caseSpecialRemark" id="caseSpecialRemark" rows="2" readonly>${caseInfo.caseSpecialRemark}</textarea>
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
                            <span class="label label-primary">委托信息</span>
                           <span class="tools pull-right">
                           <a name="chevronUp" href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                            <form id="consignment_form" class="form-horizontal tasi-form" method="get">
                                <input type="hidden" id="additionalFlag" value="1"/>
                                <input type="hidden" id="consignmentId" value="${consignment.id}"/>
                                <input type="hidden" id="newConsignmentId" value=""/>

                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托人1（姓名、职务） <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-2">
                                        <input type="text" class="form-control required" placeholder="姓名"
                                               id="delegator1" value="${consignment.delegator1}" readonly/>
                                    </div>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control required" placeholder="职务"
                                               id="delegator1Position" value="${consignment.delegator1Position}" readonly/>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托人1（证件、证件号） <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-2">
                                        <input type="text" class="form-control required" placeholder="证件"
                                               id="delegator1Cname" value="${consignment.delegator1Cname}" readonly/>
                                    </div>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control required" placeholder="证件号码"
                                               id="delegator1Cno" value="${consignment.delegator1Cno}" readonly/>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托人1电话 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control required"
                                               id="delegator1Phone" value="${consignment.delegator1Phone}" readonly/>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托人2（姓名、职务） <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-2">
                                        <input type="text" class="form-control required" placeholder="姓名"
                                               id="delegator2" value="${consignment.delegator2}" readonly/>
                                    </div>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control required" placeholder="职务"
                                               id="delegator2Position" value="${consignment.delegator2Position}" readonly/>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托人2（证件、证件号） <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-2">
                                        <input type="text" class="form-control required" placeholder="姓名"
                                               id="delegator2Cname" value="${consignment.delegator2Cname}" readonly/>
                                    </div>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control required" placeholder="证件号码"
                                               id="delegator2Cno" value="${consignment.delegator2Cno}" readonly/>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托人2电话 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control required"
                                               id="delegator2Phone" value="${consignment.delegator2Phone}" readonly/>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">通讯地址 </label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control"
                                               id="postalAddress" value="${consignment.postalAddress}" readonly/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">邮政编号 </label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control"
                                               id="postNo" value="${consignment.postNo}" readonly/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">传真号码 </label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control"
                                               id="faxNo" value="${consignment.faxNo}" readonly/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">鉴定机构名称</label>
                                    <div class="col-sm-5">
                                        <select class="form-control" name="identifyKernelName" id="identifyKernelName" readonly>
                                            <c:forEach items="${identifyKernelList}" var="list" varStatus="s">
                                                <option value="${list.identifyKernelName}" <c:if test="${consignment.identifyKernelName eq list.identifyKernelName}">selected</c:if>>${list.identifyKernelName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">鉴定要求 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <select class="form-control" name="identifyRequirementList" id="identifyRequirementList" readonly>
                                            <option value="">请选择...</option>
                                            <c:forEach items="${identifyRequirementList}" var="list" varStatus="s">
                                                <option value="${list.dictCode}" <c:if test="${consignment.identifyRequirement eq list.dictCode}">selected</c:if>>${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                        <br>
                                        <input type="hidden" name="identifyRequirementOtherHide" id="identifyRequirementOtherHide" value="${consignment.identifyRequirementOther}">
                                        <input type="text" class="form-control" onmouseover="this.title=this.value" name="identifyRequirementOther" id="identifyRequirementOther" value="${consignment.identifyRequirementOther}" readonly>
                                    </div>
                                    <div class="col-sm-2">
                                        <input type="text" class="form-control" onmouseover="this.title=this.value" name="matchCaseNo" id="matchCaseNo" placeholder="比对案件编号" value="${consignment.matchCaseNo}" style="display:none" readonly>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">原鉴定情况（鉴定单位及结论）</label>
                                    <div class="col-sm-5">
                                        <textarea class="form-control" rows="2" id="preIdentifyDesc" readonly>${consignment.preIdentifyDesc}</textarea>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">重新鉴定原因</label>
                                    <div class="col-sm-5">
                                        <textarea class="form-control" rows="2" id="reidentifyReason" readonly>${consignment.reidentifyReason}</textarea>
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
                            <span class="label label-primary">人员信息</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                            <a class="btn btn-primary" data-toggle="modal" data-target="#personModal" id="newPerBtn">
                                <i class="fa fa-plus"></i> 添加
                            </a>
                            <div class="space15" style="height: 5px;"></div>
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                    <tr>
                                        <th>序号</th>
                                        <th>人员类型</th>
                                        <th>姓名</th>
                                        <th>性别</th>
                                        <th>身份证号</th>
                                        <th>与人员关系</th>
                                        <th width="150px">操作</th>
                                    </tr>
                                    </thead>
                                <tbody id="personInfoTable">
                                    <c:if test="${fn:length(personInfoList) gt 0}">
                                        <c:forEach items="${personInfoList}" var="person" varStatus="p">
                                            <tr class="regedTr">
                                                <td></td>
                                                <td><input type="hidden" name="personType" value="${person.personType}"/>${person.personTypeName}</td>
                                                <td><input type="hidden" name="personName" value="${person.personName}"/>${person.personName}</td>
                                                <td><input type="hidden" name="personGender" value="${person.personGender}"/>${person.personGenderName}</td>
                                                <td>
                                                    <input type="hidden" name="personIdcardNo" value="${person.personIdcardNo}"/>
                                                    <input type="hidden" name="noIdcardRemark" value="${person.noIdcardRemark}"/>
                                                        ${person.personIdcardNo} <c:if test="${not empty person.noIdcardRemark}">（${person.noIdcardRemark}）</c:if>
                                                </td>
                                                <td><input type="hidden" name="relativeIdentify" value="${person.relativeIdentify}"/>${person.relativeIdentifyName}</td>
                                                <td>
                                                    <input type="hidden" name="personId" value="${person.id}"/>
                                                    -- --
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>

                                    <c:if test="${fn:length(supplyPersonInfoList) gt 0}">
                                        <c:forEach items="${supplyPersonInfoList}" var="supplyPerson" varStatus="p">
                                            <tr>
                                                <td></td>
                                                <td><input type="hidden" name="personType" value="${supplyPerson.personType}"/>${supplyPerson.personTypeName}</td>
                                                <td><input type="hidden" name="personName" value="${supplyPerson.personName}"/>${supplyPerson.personName}</td>
                                                <td><input type="hidden" name="personGender" value="${supplyPerson.personGender}"/>${supplyPerson.personGenderName}</td>
                                                <td>
                                                    <input type="hidden" name="personIdcardNo" value="${supplyPerson.personIdcardNo}"/>
                                                    <input type="hidden" name="noIdcardRemark" value="${supplyPerson.noIdcardRemark}"/>
                                                        ${supplyPerson.personIdcardNo} <c:if test="${not empty supplyPerson.noIdcardRemark}">（${supplyPerson.noIdcardRemark}）</c:if>
                                                </td>
                                                <td><input type="hidden" name="relativeIdentify" value="${supplyPerson.relativeIdentify}"/>${supplyPerson.relativeIdentifyName}</td>
                                                <td>
                                                    <input type="hidden" name="personId" value="${supplyPerson.id}"/>
                                                    <button name='editPerBtn' class='btn btn-primary btn-xs'><i class='fa fa-pencil'></i> 修改</button>
                                                    &nbsp;
                                                    <button name='delPerBtn' class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i> 删除</button>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:if>
                                </tbody>
                            </table>

                            <div class="modal fade" id="personModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                &times;
                                            </button>
                                            <h4 class="modal-title">
                                                人员信息
                                            </h4>
                                        </div>
                                        <div class="modal-body">
                                            <form class="form-horizontal tasi-form">
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">人员类型 <i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <select name="personType" class="form-control small required">
                                                            <option value="">请选择...</option>
                                                            <c:forEach items="${personTypeList}" var="list" varStatus="s">
                                                                <option value="${list.dictCode}">${list.dictName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-3 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">姓名 <i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <input name="personName" type="text" class="form-control small required" value=""/>
                                                    </div>
                                                <div class="col-md-3 has-error hide">
                                                    <p class="help-block">必填项</p>
                                                </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">性别 <i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <select name="personGender" class="form-control small required">
                                                            <c:forEach items="${personGenderList}" var="list" varStatus="s">
                                                                <option value="${list.dictCode}">${list.dictName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-3 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">身份证号 <i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <input name="personIdcardNo" type="text" class="form-control small required" style="width:180px; display: inline" value="">
                                                        <input id="noIdcardRemarkCkb" type="checkbox" style="display:inline; margin: 5px 0 0 5px;"/> 无身份证
                                                        <input type="text" name="noIdcardRemark" style="margin-top:2px;" readonly="readonly"
                                                               class="form-control small" placeholder="无身份备注"/>
                                                    </div>
                                                    <div class="col-md-3 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">与人员关系 <i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <select name="relativeIdentify" class="form-control small">
                                                            <c:forEach items="${personRelationList}" var="list" varStatus="s">
                                                                <option value="${list.dictCode}">${list.dictName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-3 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <input type="hidden" name="personId" value=""/>
                                            <input type="hidden" name="personOperateType" value=""/>
                                            <input type="hidden" name="personTableRownum" value=""/>
                                            <button class="btn btn-success" type="button" id="SavePersonBtn">保存</button>
                                            <button data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
                                        </div>
                                    </div>
                                </div>
                            </div>
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
                            <span class="label label-primary">检材信息</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                            <a class="btn btn-primary" data-toggle="modal" <%--data-target="#sampleModal"--%> id="newPersonBtn">
                                <i class="fa fa-plus"></i> 添加人员检材
                            </a>

                            <a class="btn btn-info" id="showRegedTrBtn">
                                <span id="hiddenSpan"><i class="fa fa-angle-double-up"></i> 隐藏已登记检材</span>
                                <span id="showSpan" class="hide"><i class="fa fa-angle-double-down"></i> 显示已登记检材</span>
                            </a>

                            <div class="space15" style="height: 30px;padding-top:10px;">
                                <strong>人员检材 <i class="fa fa-hand-o-down"></i></strong>
                            </div>
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>检材名称</th>
                                    <th>检材类型</th>
                                    <th>检材描述</th>
                                    <th>包装</th>
                                    <th>性状</th>
                                    <th>关联人员 <i class="fa fa-user"></i></th>
                                    <th width="100px">提取时间</th>
                                    <th width="100px">提取人</th>
                                    <th width="150px">操作</th>
                                </tr>
                                </thead>
                                <tbody id="samplePersonTable">
                                    <c:if test="${fn:length(sampleInfoList) gt 0}">
                                        <c:forEach items="${sampleInfoList}" var="sample" varStatus="p">
                                            <c:if test="${sample.sampleFlag eq 1}">
                                                <tr class="regedTr">
                                                    <td></td>
                                                    <td title="${sample.sampleName}">
                                                        <input type='hidden' name='sampleName' value='${sample.sampleName}'/>
                                                        <c:if test="${fn:length(sample.sampleName) gt 43}">
                                                            ${fn:substring(sample.sampleName,0,42)} ...
                                                        </c:if>
                                                        <c:if test="${fn:length(sample.sampleName) lt 44}">
                                                            ${sample.sampleName}
                                                        </c:if>
                                                    </td>
                                                    <td><input type='hidden' name='sampleType' value='${sample.sampleType}'/>${sample.sampleTypeName}</td>
                                                    <td><input type='hidden' name='sampleDesc' value='${sample.sampleDesc}'/>${sample.sampleDesc}</td>
                                                    <td><input type='hidden' name='samplePacking' value='${sample.samplePacking}'/>${sample.samplePacking}</td>
                                                    <td>
                                                        <input type='hidden' name='sampleProperties' value='${sample.sampleProperties}'/>
                                                        <input type='hidden' name='otherPropertiesDesc' value='${sample.otherPropertiesDesc}'/>
                                                        <c:if test="${sample.sampleProperties != '9999'}">
                                                            ${sample.samplePropertiesName}
                                                        </c:if>
                                                        <c:if test="${sample.sampleProperties eq '9999'}">
                                                            ${sample.otherPropertiesDesc}
                                                        </c:if>
                                                    </td>
                                                    <td><input type='hidden' name='refPersonId' value='${sample.refPersonId}'/><input type='hidden' name='refPersonName' value='${sample.refPersonName}'/>${sample.refPersonName}</td>
                                                    <td>
                                                        <input type='hidden' name='extractDatetime' value="<fmt:formatDate value='${sample.extractDatetime}' pattern='yyyy-MM-dd'/>"/>
                                                        <fmt:formatDate value='${sample.extractDatetime}' pattern='yyyy-MM-dd'/>
                                                    </td>
                                                    <td>
                                                        <input type='hidden' name='extractPerson' value='${sample.extractPerson}'/>
                                                            ${sample.extractPerson}
                                                    </td>
                                                    <td>
                                                        <input type="hidden" name="sampleId" value="${sample.id}"/>
                                                        <input type="hidden" name="sampleFlag" value="${sample.sampleFlag}"/>

                                                        <c:if test="${sample.acceptStatus eq 0}"><span class="color_red"><i class="fa fa-ban"></i> 未受理</span></c:if>
                                                        <c:if test="${sample.acceptStatus eq 1}"><span class="color_green"><i class="fa fa-check"></i> 已受理</span></c:if>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>

                                    <c:if test="${fn:length(supplySampleInfoList) gt 0}">
                                        <c:forEach items="${supplySampleInfoList}" var="supplySample" varStatus="p">
                                            <c:if test="${supplySample.sampleFlag eq 1}">
                                                <tr>
                                                    <td></td>
                                                    <td title="${supplySample.sampleName}">
                                                        <input type='hidden' name='sampleName' value='${supplySample.sampleName}'/>
                                                        <c:if test="${fn:length(supplySample.sampleName) gt 43}">
                                                            ${fn:substring(supplySample.sampleName,0,42)} ...
                                                        </c:if>
                                                        <c:if test="${fn:length(supplySample.sampleName) lt 44}">
                                                            ${supplySample.sampleName}
                                                        </c:if>
                                                    </td>
                                                    <td><input type='hidden' name='sampleType' value='${supplySample.sampleType}'/>${supplySample.sampleTypeName}</td>
                                                    <td><input type='hidden' name='sampleDesc' value='${supplySample.sampleDesc}'/>${supplySample.sampleDesc}</td>
                                                    <td><input type='hidden' name='samplePacking' value='${supplySample.samplePacking}'/>${supplySample.samplePacking}</td>
                                                    <td>
                                                        <input type='hidden' name='sampleProperties' value='${supplySample.sampleProperties}'/>
                                                        <input type='hidden' name='otherPropertiesDesc' value='${supplySample.otherPropertiesDesc}'/>
                                                        <c:if test="${supplySample.sampleProperties != '9999'}">
                                                            ${supplySample.samplePropertiesName}
                                                        </c:if>
                                                        <c:if test="${supplySample.sampleProperties eq '9999'}">
                                                            ${supplySample.otherPropertiesDesc}
                                                        </c:if>
                                                    </td>
                                                    <td><input type='hidden' name='refPersonId' value='${supplySample.refPersonId}'/><input type='hidden' name='refPersonName' value='${supplySample.refPersonName}'/>${supplySample.refPersonName}</td>
                                                    <td>
                                                        <input type='hidden' name='extractDatetime' value="<fmt:formatDate value='${supplySample.extractDatetime}' pattern='yyyy-MM-dd'/>"/>
                                                        <fmt:formatDate value='${supplySample.extractDatetime}' pattern='yyyy-MM-dd'/>
                                                    </td>
                                                    <td>
                                                        <input type='hidden' name='extractPerson' value='${supplySample.extractPerson}'/>
                                                            ${supplySample.extractPerson}
                                                    </td>
                                                    <td>
                                                        <input type="hidden" name="sampleId" value="${supplySample.id}"/>
                                                        <input type="hidden" name="sampleFlag" value="${supplySample.sampleFlag}"/>
                                                        <button name='editSampleBtn' class='btn btn-primary btn-xs'><i class='fa fa-pencil'></i> 修改</button>
                                                        &nbsp;
                                                        <button name='delSampleBtn' class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i> 删除</button>
                                                    </td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </c:if>
                                </tbody>
                            </table>


                            <div class="space15" style="height: 30px;padding-top:10px;">
                                <strong>物证检材 <i class="fa fa-hand-o-down"></i></strong>
                            </div>

                            <a class="btn btn-primary" data-toggle="modal"  id="newSampleBtn">
                                <i class="fa fa-plus"></i> 添加物证检材
                            </a>

                            <a class="btn btn-warning"  id="refreshSampleBtn" name="refreshSampleBtn">
                                <i class="fa fa-spinner"></i> 刷新现勘物证
                            </a>

                            <span class="label label-info hide" id="sampleCountInFile">刷新出（99）条数据</span>

                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>物证编号</th>
                                    <th>检材名称</th>
                                    <th>检材类型</th>
                                    <th>检材描述</th>
                                    <th>包装</th>
                                    <th>性状</th>
                                    <th width="100px">提取时间</th>
                                    <th width="100px">提取人</th>
                                    <th width="150px">操作</th>
                                </tr>
                                </thead>
                                <tbody id="sampleEvidenceTable">
                                <c:if test="${fn:length(sampleInfoList) gt 0}">
                                    <c:forEach items="${sampleInfoList}" var="sample" varStatus="p">
                                        <c:if test="${sample.sampleFlag eq 0}">
                                            <tr class="regedTr">
                                                <td></td>
                                                <td><input type='hidden' name='evidenceNo' value='${sample.evidenceNo}'/>${sample.evidenceNo}</td>
                                                <td title="${sample.sampleName}">
                                                    <input type='hidden' name='sampleName' value='${sample.sampleName}'/>
                                                    <c:if test="${fn:length(sample.sampleName) gt 43}">
                                                        ${fn:substring(sample.sampleName,0,42)} ...
                                                    </c:if>
                                                    <c:if test="${fn:length(sample.sampleName) lt 44}">
                                                        ${sample.sampleName}
                                                    </c:if>
                                                </td>
                                                <td><input type='hidden' name='sampleType' value='${sample.sampleType}'/>${sample.sampleTypeName}</td>
                                                <td><input type='hidden' name='sampleDesc' value='${sample.sampleDesc}'/>${sample.sampleDesc}</td>
                                                <td><input type='hidden' name='samplePacking' value='${sample.samplePacking}'/>${sample.samplePacking}</td>
                                                <td>
                                                    <input type='hidden' name='sampleProperties' value='${sample.sampleProperties}'/>
                                                    <input type='hidden' name='otherPropertiesDesc' value='${sample.otherPropertiesDesc}'/>
                                                    <c:if test="${sample.sampleProperties != '9999'}">
                                                        ${sample.samplePropertiesName}
                                                    </c:if>
                                                    <c:if test="${sample.sampleProperties eq '9999'}">
                                                        ${sample.otherPropertiesDesc}
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <input type='hidden' name='extractDatetime' value="<fmt:formatDate value='${sample.extractDatetime}' pattern='yyyy-MM-dd'/>"/>
                                                    <fmt:formatDate value='${sample.extractDatetime}' pattern='yyyy-MM-dd'/>
                                                </td>
                                                <td>
                                                    <input type='hidden' name='extractPerson' value='${sample.extractPerson}'/>
                                                        ${sample.extractPerson}
                                                </td>
                                                <td>
                                                    <input type="hidden" name="sampleId" value="${sample.id}"/>
                                                    <input type="hidden" name="sampleFlag" value="${sample.sampleFlag}"/>

                                                    <c:if test="${sample.acceptStatus eq 0}"><span class="color_red"><i class="fa fa-ban"></i> 未受理</span></c:if>
                                                    <c:if test="${sample.acceptStatus eq 1}"><span class="color_green"><i class="fa fa-check"></i> 已受理</span></c:if>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </c:if>

                                <c:if test="${fn:length(supplySampleInfoList) gt 0}">
                                    <c:forEach items="${supplySampleInfoList}" var="supplySample" varStatus="p">
                                        <c:if test="${supplySample.sampleFlag eq 0}">
                                            <tr>
                                                <td></td>
                                                <td title="${supplySample.sampleName}">
                                                    <input type='hidden' name='sampleName' value='${supplySample.sampleName}'/>
                                                    <c:if test="${fn:length(supplySample.sampleName) gt 43}">
                                                        ${fn:substring(supplySample.sampleName,0,42)} ...
                                                    </c:if>
                                                    <c:if test="${fn:length(supplySample.sampleName) lt 44}">
                                                        ${supplySample.sampleName}
                                                    </c:if>
                                                </td>
                                                <td><input type='hidden' name='sampleType' value='${supplySample.sampleType}'/>${supplySample.sampleTypeName}</td>
                                                <td><input type='hidden' name='sampleDesc' value='${supplySample.sampleDesc}'/>${supplySample.sampleDesc}</td>
                                                <td><input type='hidden' name='samplePacking' value='${supplySample.samplePacking}'/>${supplySample.samplePacking}</td>

                                                <td>
                                                    <input type='hidden' name='sampleProperties' value='${supplySample.sampleProperties}'/>
                                                    <input type='hidden' name='otherPropertiesDesc' value='${supplySample.otherPropertiesDesc}'/>
                                                    <c:if test="${supplySample.sampleProperties != '9999'}">
                                                        ${supplySample.samplePropertiesName}
                                                    </c:if>
                                                    <c:if test="${supplySample.sampleProperties eq '9999'}">
                                                        ${supplySample.otherPropertiesDesc}
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <input type='hidden' name='extractDatetime' value="<fmt:formatDate value='${supplySample.extractDatetime}' pattern='yyyy-MM-dd'/>"/>
                                                    <fmt:formatDate value='${supplySample.extractDatetime}' pattern='yyyy-MM-dd'/>
                                                </td>
                                                <td>
                                                    <input type='hidden' name='extractPerson' value='${supplySample.extractPerson}'/>
                                                    ${supplySample.extractPerson}
                                                </td>
                                                <td>
                                                    <input type="hidden" name="sampleId" value="${supplySample.id}"/>
                                                    <input type="hidden" name="sampleFlag" value="${supplySample.sampleFlag}"/>
                                                    <button name='editSampleBtn' class='btn btn-primary btn-xs'><i class='fa fa-pencil'></i> 修改</button>
                                                    &nbsp;
                                                    <button name='delSampleBtn' class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i> 删除</button>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>

                            <div class="modal fade" id="sampleModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                &times;
                                            </button>
                                            <h4 class="modal-title">
                                                检材信息
                                            </h4>
                                        </div>
                                        <div class="modal-body">
                                            <form class="form-horizontal tasi-form">
                                                <%--<div class="form-group">
                                                    <label class="control-label col-md-2">&nbsp;</label>
                                                    <div class="col-md-6">
                                                        <label class="checkbox-inline">
                                                            <input type="radio" id="sampleFlagEvidence" name="sampleFlag" value="0" checked> 物证
                                                        </label>
                                                        <label class="checkbox-inline">
                                                            <input type="radio" id="sampleFlagPerson" name="sampleFlag" value="1"> 人员
                                                        </label>
                                                    </div>
                                                </div>--%>
                                                <div class="form-group" id="divRefPerson" style="display:none">
                                                    <label class="control-label col-md-3">关联人员 <i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <select name="refPerson" class="form-control small required">
                                                        </select>
                                                    </div>
                                                    <div class="col-md-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">检材名称 <i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <input name="sampleName" type="text" class="form-control small required" value="">
                                                    </div>
                                                    <div class="col-md-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">检材类型 <i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <select name="sampleType" class="form-control small required">
                                                            <option value="">请选择...</option>
                                                            <c:forEach items="${sampleTypeList}" var="list" varStatus="s">
                                                                <option value="${list.dictCode}" >${list.dictName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">提取时间 <i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <span style="position: relative; z-index: 9999;">
                                                            <input name="extractDatetime" type="text" class="form_date form-control small required" value="" readonly>
                                                        </span>
                                                    </div>
                                                    <div class="col-md-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">提取人 <i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <span style="position: relative; z-index: 9999;">
                                                            <input name="extractPerson" type="text" class="form-control small required" value="" >
                                                        </span>
                                                    </div>
                                                    <div class="col-md-3 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">样本描述 <i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <input name="sampleDesc" type="text" class="form-control small required" value="" placeholder="（例如：棉签1根）">
                                                    </div>
                                                    <div class="col-md-3 has-error hide">
                                                        <p class="help-block">必填项（例如：棉签1根）</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">样本包装 <i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <input name="samplePacking" type="text" class="form-control small required" value="物证袋">
                                                    </div>
                                                    <div class="col-md-3 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">样本性状 <i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <select name="sampleProperties" class="form-control small required">
                                                            <c:forEach items="${samplePropertiesList}" var="sp">
                                                                <option value="${sp.dictCode}">${sp.dictName}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <input type="text" name="otherPropertiesDesc" style="margin-top:2px;"
                                                               class="form-control small hide" placeholder="其他性状说明"/>
                                                    </div>
                                                    <div class="col-md-3 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <input type="hidden" name="sampleId" value=""/>
                                            <input type="hidden" name="sampleOperateType" value=""/>
                                            <input type="hidden" name="sampleTableRownum" value=""/>
                                            <input type="hidden" name="sampleFlag" value=""/>
                                            <button class="btn btn-success" type="button" id="SaveSampleBtn">保存</button>
                                            <button data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="modal fade" id="sampleInfoModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                &times;
                                            </button>
                                            <h4 class="modal-title">
                                                检材信息
                                            </h4>
                                        </div>
                                        <div class="modal-body">
                                            <form class="form-horizontal tasi-form">
                                                <%--<div class="form-group">
                                                    <label class="control-label col-md-2">&nbsp;</label>
                                                    <div class="col-md-6">
                                                        <label class="checkbox-inline">
                                                            <input type="radio" id="sampleFlagEvidence" name="sampleFlag" value="0" checked> 物证
                                                        </label>
                                                        <label class="checkbox-inline">
                                                            <input type="radio" id="sampleFlagPerson" name="sampleFlag" value="1"> 人员
                                                        </label>
                                                    </div>
                                                </div>--%>
                                                <div class="form-group" id="divRefPerson" style="display:none">
                                                    <label class="control-label col-md-3">关联人员 <i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <select name="refPerson" class="form-control small required">
                                                        </select>
                                                    </div>
                                                    <div class="col-md-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">检材名称 <i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <input name="evidenceNo" type="hidden" class="form-control small " value="">
                                                        <input name="sampleName" type="text" class="form-control small required" value="">
                                                    </div>
                                                    <div class="col-md-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">检材类型 <i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <select name="sampleType" class="form-control small required">
                                                            <option value="">请选择...</option>
                                                            <c:forEach items="${sampleTypeList}" var="list" varStatus="s">
                                                                <option value="${list.dictCode}" >${list.dictName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">提取时间 <i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <span style="position: relative; z-index: 9999;">
                                                            <input name="extractDatetime" type="text" class="form_date form-control small required" value="" readonly>
                                                        </span>
                                                    </div>
                                                    <div class="col-md-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">提取人 <i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <span style="position: relative; z-index: 9999;">
                                                            <input name="extractPerson" type="text" class="form-control small required" value="" >
                                                        </span>
                                                    </div>
                                                    <div class="col-md-3 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">样本描述 <i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <input name="sampleDesc" type="text" class="form-control small required" value="" placeholder="（例如：棉签1根）">
                                                    </div>
                                                    <div class="col-md-3 has-error hide">
                                                        <p class="help-block">必填项（例如：棉签1根）</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">样本包装 <i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <input name="samplePacking" type="text" class="form-control small required" value="物证袋">
                                                    </div>
                                                    <div class="col-md-3 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">样本性状 <i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <select name="sampleProperties" class="form-control small required">
                                                            <c:forEach items="${samplePropertiesList}" var="sp">
                                                                <option value="${sp.dictCode}">${sp.dictName}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <input type="text" name="otherPropertiesDesc" style="margin-top:2px;"
                                                               class="form-control small hide" placeholder="其他性状说明"/>
                                                    </div>
                                                    <div class="col-md-3 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <input type="hidden" name="sampleId" value=""/>
                                            <input type="hidden" name="sampleOperateType" value=""/>
                                            <input type="hidden" name="sampleTableRownum" value=""/>
                                            <input type="hidden" name="sampleFlag" value=""/>
                                            <button class="btn btn-success" type="button" id="SaveSampleInfoBtn">保存</button>
                                            <button data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
                                        </div>
                                    </div>
                                </div>
                            </div>

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
                                <div class="col-lg-offset-6 col-lg-10">
                                    <input type="hidden" id="operateType" value="${operateType}"/>
                                    <button class="btn btn-lg btn-primary" id="submitBtn" type="button"><i class="fa fa-save"></i> 完 成 </button>
                                    &nbsp;
                                    <button id="backBtn" class="btn btn-lg btn-default" type="button"><i class="fa fa-reply"></i> 返 回 </button>
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
                            <button data-dismiss="modal" class="btn btn-default" type="button"><i class="fa fa-list-alt"></i> 完成</button>
                            <button name="downloadDocBtn" class="btn btn-success btn-sm"><i class="fa fa-print"></i> 委托表</button>
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
<script src="<%=path%>/js/identifyAddRegSupply.js"></script>
<script>

    chevronUp();

    function chevronUp() {
        var el = $("a[name='chevronUp']").parents(".panel").children(".panel-body");
        if ($("a[name='chevronUp']").hasClass("fa-chevron-down")) {
            $("a[name='chevronUp']").removeClass("fa-chevron-down").addClass("fa-chevron-up");
            el.slideUp(200);
        } else {
            $("a[name='chevronUp']").removeClass("fa-chevron-up").addClass("fa-chevron-down");
            el.slideDown(200);
        }
    }

    generatePersonIdx();

    function generatePersonIdx() {
        $("tr","#personInfoTable").each(function(){
            $("td:first", $(this)).text($(this).index()+1);
        });
    }

    generateSamplePersonIdx();

    function generateSamplePersonIdx() {
        $("tr","#samplePersonTable").each(function(){
            $("td:first", $(this)).text($(this).index()+1);
        });
    }

    generateSampleEvidenceIdx();

    function generateSampleEvidenceIdx() {
        $("tr","#sampleEvidenceTable").each(function(){
            $("td:first", $(this)).text($(this).index()+1);
        });
    }

    getMatchCaseNo();

    function getMatchCaseNo() {
        var matchSample = $("#matchCaseNo").val();
        if (matchSample !=null && matchSample != "")
            $("#matchCaseNo").show();
    }

    $(function () {

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

        /*$("#sampleFlagEvidence").on("click", function(){
            $("input[name='sampleName']", "#sampleModal").removeAttr("placeholder");
        });

        $("#sampleFlagPerson").on("click", function(){
            $("input[name='sampleName']", "#sampleModal").attr("placeholder","例如：XXX血样或口腔拭子");
        });*/

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

        $("#backBtn").on("click",function(){
            location.href="<%=path%>/wt/caseinfo/listAccepted.html";
        });


        $("#regModal").on('hidden.bs.modal', function() {
            location.href = "<%=path%>/wt/caseinfo/listPending.html";
        });




        $("input[name='sampleFlag']").on('click',function(){
            var checkedSampleFalg = $("input[name='sampleFlag']:checked").val();
            checkedSampleFalg == 0 ? $("#divRefPerson").hide() : $("#divRefPerson").show();
        });

        $("select[name='refPerson']", "#sampleModal").on("change", function(){
            var refPersonName = $("option:selected", $(this)).val();
            var sampleName = $("input[name='sampleName']", "#sampleModal").val();

            var refPersonNameStr = $("input[name='refPersonName'][value='"+ refPersonName +"']","#samplePersonTable");
            if (refPersonNameStr.length > 0) {
                if (confirm("此人员已存在，是否继续添加！")) {
                    if(refPersonName !="" && sampleName == ""){
                        $("input[name='sampleName']", "#sampleModal").val(refPersonName);
                    }
                }else {
                    $("select[name='refPerson']", "#sampleModal").val(sampleName);
                }
            }else {
                if(refPersonName !="" && sampleName == ""){
                    $("input[name='sampleName']", "#sampleModal").val(refPersonName);
                }
            }
            $("input[name='sampleName']", "#sampleModal").focus();
        });

        $("select[name='sampleProperties']", "#sampleModal").on("change", function(){
            var selectedVal = $("option:selected", $(this)).val();
            if(selectedVal == "9999"){
                $("input[name='otherPropertiesDesc']", "#sampleModal").removeClass("hide");
                $("input[name='otherPropertiesDesc']", "#sampleModal").focus();
            }else{
                $("input[name='otherPropertiesDesc']", "#sampleModal").addClass("hide");
                $("input[name='otherPropertiesDesc']", "#sampleModal").focus();
            }
        });

        $("#showRegedTrBtn").on("click", function() {
            if ($("#hiddenSpan").hasClass("hide")) {
                $("tr.regedTr", "#samplePersonTable").removeClass("hide");
                $("tr.regedTr", "#sampleEvidenceTable").removeClass("hide");

                $("#hiddenSpan").removeClass("hide");
                $("#showSpan").addClass("hide");
            }else{
                $("tr.regedTr", "#samplePersonTable").addClass("hide");
                $("tr.regedTr", "#sampleEvidenceTable").addClass("hide");

                $("#hiddenSpan").addClass("hide");
                $("#showSpan").removeClass("hide");
            }
        });


        $("#noIdcardRemarkCkb", "#personModal").on("click", function(){
            if($(this).is(":checked")){
                $("input[name='personIdcardNo']", "#personModal").val("无");
                $("input[name='personIdcardNo']", "#personModal").prop("readonly","readonly");

                $("input[name='noIdcardRemark']", "#personModal").prop("readonly",false);
                $("input[name='noIdcardRemark']", "#personModal").val("");
                $("input[name='noIdcardRemark']", "#personModal").focus();
            } else {
                $("input[name='personIdcardNo']", "#personModal").val("");
                $("input[name='personIdcardNo']", "#personModal").prop("readonly",false);
                $("input[name='personIdcardNo']", "#personModal").focus();

                $("input[name='noIdcardRemark']", "#personModal").prop("readonly","readonly");
                $("input[name='noIdcardRemark']", "#personModal").val("");
            }
        });

        $("button[name='downloadDocBtn']","#regModal").on("click",function(){
            var newConsignmentId = $("#newConsignmentId","#consignment_form").val();
            location.href="<%=path%>/wt/caseinfo/delegateDoc.html?consignmentId=" + newConsignmentId;
        });

        //刷新现堪获取的物证
        $("#refreshSampleBtn").on("click", function(){
            $('#refreshSampleBtn').attr("disabled","disabled");

            var caseXkNo = $("#caseXkNo", "#caseinfo_form").val();
            var xkLoginName = $("input[name='xkLoginName']").val();
            var xkLoginPassword = $("input[name='xkLoginPassword']").val();
            var consignmentId = $("#consignmentId").val();

            if (caseXkNo == "") {
                alert("请输入现堪编号！");
                return;
            }

            /* $.ajax({
             url: "http://10.8.41.15:8801/dataAssistant/getDatas?investigationNo="+caseXkNo+"&username="+xkLoginName+"$password="+xkLoginPassword,
             type: "get",
             success: function (data) {
             console.log(data);*/
            $.ajax({
                url : "<%=path%>/wt/reg/refreshXkSample.html?caseXkNo="+caseXkNo +"&consignmentId="+consignmentId,
                type:"post",
                contentType:  "application/json; charset=utf-8",
                dataType : "json",
                success : function(data) {
                    var limsSampleInfoList = data.limsSampleInfoList;
                    $("#sampleCountInFile").text("刷新出（" + data.limsSampleInfoListCnt + "）条数据");
                    $("#sampleCountInFile").removeClass("hide");
                    if (limsSampleInfoList != undefined) {
                        $.each(limsSampleInfoList, function (n, sample) {

                            var sampleName = sample.sampleName;
                            var evidenceNo = sample.evidenceNo;
                            var extractDatetime = sample.extractDatetime;
                            var extractPerson = sample.extractPerson;
                            var sampleDesc = sample.sampleDesc;
                            var sampleType = sample.sampleType;
                            var sampleTypeName = sample.sampleTypeName;
                            var samplePacking = sample.samplePacking;
                            var sampleProperties = sample.sampleProperties;

                            var otherPropertiesDesc = sample.otherPropertiesDesc;
                            var sampleFlag = sample.sampleFlag;

                            var newRowHtml = "<td></td>";
                            newRowHtml += "<td><input type='hidden' name='evidenceNo' value='" + evidenceNo + "'/>" + evidenceNo + "</td>";
                            newRowHtml += "<td><input type='hidden' name='sampleName' value='" + sampleName + "'/>" + sampleName + "</td>";
                            newRowHtml += "<td style='display: none'><input type='hidden' name='sampleType' value='" + sampleType + "'/>" + sampleType + "</td>";
                            newRowHtml += "<td><input type='hidden' name='sampleTypeName' value='" + sampleTypeName + "'/>" + sampleTypeName + "</td>";
                            newRowHtml += "<td><input type='hidden' name='sampleDesc' value='" + sampleDesc + "'/>" + sampleDesc + "</td>";
                            newRowHtml += "<td><input type='hidden' name='samplePacking' value='" + samplePacking + "'/>" + samplePacking + "</td>";
                            newRowHtml += "<td style='display: none'><input type='hidden' name='sampleProperties' value='" + sampleProperties + "'/>" + sampleProperties + "</td>";
                            newRowHtml += "<td><input type='hidden' name='otherPropertiesDesc' value='" + otherPropertiesDesc + "'/>" + otherPropertiesDesc + "</td>";
                            newRowHtml += "<td><input type='hidden' name='extractDatetime' value='" + extractDatetime + "'/>" + extractDatetime + "</td>";
                            newRowHtml += "<td><input type='hidden' name='extractPerson' value='" + extractPerson + "'/>" + extractPerson + "</td>";
                            newRowHtml += "<td><button name='editSample' class='btn btn-primary btn-xs'><i class='fa fa-pencil'></i> 修改</button>  <button name='delPerBtn' class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i> 删除</button></td>";
                            newRowHtml += "<td style='display: none'><input type='hidden' name='sampleFlag' value='" + sampleFlag + "'/>" + sampleFlag + "</td>";
                            $("#sampleEvidenceTable").append("<tr>" + newRowHtml + "</tr>");

                            generateSampleEvidenceIdx();

                        });

                        $("button[name='editSample']","#sampleEvidenceTable").on("click",function(){
                            EditSampleInfo(this);
                        });

                        $("button[name='delPerBtn']","#sampleEvidenceTable").on("click",function(){
                            if (!confirm("确认删除吗")) {
                                return;
                            }
                            DelSampleRow(this);
                        });

                        function DelSampleRow(obj) {
                            var sampleId = $("input[name='sampleId']", $(obj).parent()).val();
                            if(sampleId == null || sampleId == "" || sampleId == 0 || sampleId == "0"){
                                $(obj).parents("tr").remove();
                                return;
                            }
                        }
                    }
                },
                error: function (e) {
                    alert(e);
                }
            });

        });

    });


    function EditSampleInfo(obj) {
        var $curTR = $(obj).parents("tr");
        var sample = {};
        sample.id=$("input[name='sampleId']",$curTR).val();
        sample.sampleType = $("input[name='sampleType']", $curTR).val();
        sample.evidenceNo = $("input[name='evidenceNo']", $curTR).val();
        sample.sampleName = $("input[name='sampleName']", $curTR).val();
        sample.extractDatetime = $("input[name='extractDatetime']", $curTR).val();
        sample.extractPerson = $("input[name='extractPerson']", $curTR).val();
        sample.sampleDesc = $("input[name='sampleDesc']", $curTR).val();
        sample.samplePacking = $("input[name='samplePacking']", $curTR).val();
        sample.sampleProperties = $("input[name='sampleProperties']", $curTR).val();
        sample.otherPropertiesDesc = $("input[name='otherPropertiesDesc']", $curTR).val();
        sample.refPersonId = $("input[name='refPersonId']", $curTR).val();
        sample.refPersonName = $("input[name='refPersonName']", $curTR).val();
        sample.sampleFlag = $("input[name='sampleFlag']", $curTR).val();
        sample.sampleFlag = $("input[name='sampleFlag']", $curTR).val();
        var trIdx = $curTR.index();
        newSampleInfoRow(sample, "edit", trIdx);
    }

    function newSampleInfoRow(sample, operateType, rownum) {

        $("div.has-error", "#sampleInfoModal").addClass("hide");

        $("input[name='sampleId']","#sampleInfoModal").val(sample.id);
        $("select[name='sampleType']", "#sampleInfoModal").val(sample.sampleType);
        $("input[name='evidenceNo']", "#sampleInfoModal").val(sample.evidenceNo);
        $("input[name='sampleName']", "#sampleInfoModal").val(sample.sampleName);
        $("input[name='extractDatetime']", "#sampleInfoModal").val(sample.extractDatetime);
        $("input[name='extractPerson']", "#sampleInfoModal").val(sample.extractPerson);
        $("input[name='sampleDesc']", "#sampleInfoModal").val(sample.sampleDesc);
        if(sample.samplePacking == "") {
            $("input[name='samplePacking']", "#sampleInfoModal").val("物证袋");
        }else{
            $("input[name='samplePacking']", "#sampleInfoModal").val(sample.samplePacking);
        }
        if(sample.sampleProperties != ""){
            $("select[name='sampleProperties']", "#sampleInfoModal").val(sample.sampleProperties);

            if(sample.sampleProperties == "9999"){
                $("input[name='otherPropertiesDesc']", "#sampleInfoModal").val(sample.otherPropertiesDesc);
                $("input[name='otherPropertiesDesc']", "#sampleInfoModal").removeClass("hide");
            }else{
                $("input[name='otherPropertiesDesc']", "#sampleInfoModal").val("");
                $("input[name='otherPropertiesDesc']", "#sampleInfoModal").addClass("hide");
            }
        }else{
            $("select[name='sampleProperties']", "#sampleInfoModal").prop('selectedIndex', 0);

            $("input[name='otherPropertiesDesc']", "#sampleInfoModal").val("");
            $("input[name='otherPropertiesDesc']", "#sampleInfoModal").addClass("hide");
        }

        $("select[name='refPerson']", "#sampleInfoModal").empty();
        $("select[name='refPerson']", "#sampleInfoModal").append("<option value=''>请选择</option>")
        $("input[name='personName']","#personInfoTable").each(function(){
            var refPersonName = $(this).val();
            if(operateType == "edit" && refPersonName == sample.refPersonName) {
                $("select[name='refPerson']", "#sampleInfoModal").append("<option value='" + refPersonName + "' selected>" + refPersonName + " </option>");

            } else {
                $("select[name='refPerson']", "#sampleInfoModal").append("<option value='" + refPersonName + "'>" + refPersonName + "</option>");
            }
        });

        if(sample.sampleFlag == "" || sample.sampleFlag == "0"){
            $("input[name='sampleFlag']", "#sampleInfoModal").val(sample.sampleFlag);
            $("input[name='evidenceNo']", "#sampleInfoModal").val(sample.evidenceNo);

            $("#divRefPerson").hide();
        }else{
            $("input[name='sampleFlag']", "#sampleInfoModal").val(sample.sampleFlag);
            $("#divRefPerson").show();
        }

        $("input[name='sampleOperateType']", "#sampleInfoModal").val(operateType);
        $("input[name='sampleTableRownum']", "#sampleInfoModal").val(rownum);
        $("#sampleInfoModal").modal();
    }
</script>
<!-- END JS -->
</body>
</html>


