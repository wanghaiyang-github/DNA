<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
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
    <link rel="stylesheet" type="text/css" href="<%=path%>/assets/bootstrap-datepicker/css/datepicker.css">
    <link rel="stylesheet" type="text/css" href="<%=path%>/assets/bootstrap-datetimepicker/css/datetimepicker.css">
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
                    <a href="<%=path%>/wt/reg/2.html">
                        身份不明人员委托登记
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/reg/3.html">
                        失踪人口委托登记
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/caseinfo/listPending.html">
                        未受理
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/caseinfo/listAccepted.html" class="active">
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
    <!-- BEGIN MAIN CONTENT -->


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
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                            <form id="caseinfo_form" class="form-horizontal tasi-form" method="get">
                                <input type="hidden" id="caseId" value="${caseInfo.id}"/>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">案件编号 <i class="fa fa-asterisk color_red"></i></label>
                                    <label class="col-sm-4 control-label"><strong>${caseInfo.caseNo}</strong></label>
                                    <label class="col-sm-2 control-label">案件现堪编号 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <label class="col-sm-4 control-label"><strong>${caseInfo.caseXkNo}</strong></label>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件名称 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" id="caseName" name="caseName" class="form-control required"
                                               value="${caseInfo.caseName}">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案发地点</label>
                                    <div class="col-sm-5">
                                        <input type="text" id="caseLocation" name="caseLocation" class="form-control"
                                               value="${caseInfo.caseLocation}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案发时间 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input class="form_datetime form-control" id="caseDatetime"
                                               name="caseDatetime" type="text"
                                               value="<fmt:formatDate value='${caseInfo.caseDatetime}' pattern='yyyy-MM-dd'/>"
                                               readonly>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">简要案情 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-7">
                                        <textarea class="form-control required" id="caseBrief" name="caseBrief"
                                                  rows="3">${caseInfo.caseBrief}</textarea>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件类型 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-3">
                                        <select class="form-control required" name="caseType" id="caseType"
                                                value="${caseInfo.caseType}">
                                            <c:forEach items="${caseTypeList}" var="list" varStatus="s">
                                                <option value="${list.dictCode}">${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">案件性质 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-3">
                                        <select class="form-control required" name="caseProperty" id="caseProperty"
                                                value="${caseInfo.caseProperty}">
                                            <c:forEach items="${casePropertyList}" var="list" varStatus="s">
                                                <option value="${list.dictCode}">${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件级别</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" name="caseLevel" id="caseLevel"
                                                value="${caseInfo.caseLevel}">
                                            <c:forEach items="${caseLevelList}" var="list" varStatus="s">
                                                <option value="${list.dictCode}">${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 col-sm-2 control-label"
                                           style="text-align:center;">是否加急</label>
                                    <div class="col-sm-3">
                                        <label class="checkbox-inline">
                                            <input name="jiajiFlag" type="checkbox" id="jiajiFlag"
                                                   <c:if test="${caseInfo.jiajiFlag eq 1}">checked</c:if>/>
                                        </label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">其他说明</label>
                                    <div class="col-sm-5">
                                        <textarea class="form-control" name="caseSpecialRemark" id="caseSpecialRemark"
                                                  rows="2">${caseInfo.caseSpecialRemark}</textarea>
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
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                            <form id="consignment_form" class="form-horizontal tasi-form" method="get">
                                <input type="hidden" name="delegateOrg" id="delegateOrg"
                                       value="${consignment.delegateOrg}">
                                <input type="hidden" id="consignmentId" value="${consignment.id}"/>
                                <input type="hidden" id="additionalFlag" value="${consignment.additionalFlag}"/>

                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">是否为补送</label>
                                    <div class="col-sm-6">
                                        <label class="checkbox-inline">
                                            <c:if test="${consignment.additionalFlag eq 1}">
                                                <input name="additionalFlag" type="checkbox" disabled="disabled"
                                                       checked> 是
                                            </c:if>
                                            <c:if test="${consignment.additionalFlag eq 0}">
                                                <input name="additionalFlag" type="checkbox" disabled="disabled"> 否
                                            </c:if>
                                        </label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托单位 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control required" placeholder="XXX公安司法鉴定中心"
                                               id="delegateOrgDesc" value="${consignment.delegateOrgDesc}"/>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托人1（姓名、职务） <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-2">
                                        <select class="form-control required" name="delegator1" id="delegator1">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${delegatorList}" var="list" varStatus="s">
                                                <option value="${list}"
                                                        <c:if test="${consignment.delegator1 eq list}">selected</c:if>>${list}</option>
                                            </c:forEach>
                                        </select>
                                        <%--<input type="text" class="form-control required" placeholder="姓名"--%>
                                        <%--id="delegator1" value="${consignment.delegator1}"/>--%>
                                    </div>
                                    <div class="col-sm-3">
                                        <select class="form-control required" name="delegator1Position"
                                                id="delegator1Position">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${dictItemDutyList}" var="list" varStatus="s">
                                                <option value="${list.dictName}"
                                                        <c:if test="${consignment.delegator1Position eq list.dictName}">selected</c:if>>${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                        <%--<input type="text" class="form-control required" placeholder="职务"--%>
                                        <%--id="delegator1Position" value="${consignment.delegator1Position}"/>--%>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托人1（证件、证件号） <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-2">
                                        <select class="form-control required" name="delegator1Cname"
                                                id="delegator1Cname">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${dictItemCertificateList}" var="list" varStatus="s">
                                                <option value="${list.dictName}"
                                                        <c:if test="${consignment.delegator1Cname eq list.dictName}">selected</c:if>>${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                        <%--<input type="text" class="form-control required" placeholder="证件名称"--%>
                                        <%--id="delegator1Cname" value="${consignment.delegator1Cname}"/>--%>
                                    </div>
                                    <div class="col-sm-3">
                                        <select class="form-control required" name="delegator1Cno" id="delegator1Cno">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${delegatorCertificateNoList}" var="list" varStatus="s">
                                                <option value="${list}"
                                                        <c:if test="${consignment.delegator1Cno eq list}">selected</c:if>>${list}</option>
                                            </c:forEach>
                                        </select>
                                        <%--<input type="text" class="form-control required" placeholder="证件号码"--%>
                                        <%--id="delegator1Cno" value="${consignment.delegator1Cno}"/>--%>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托人1电话 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <select class="form-control required" name="delegator1Phone"
                                                id="delegator1Phone">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${delegatorPhoneList}" var="list" varStatus="s">
                                                <option value="${list}"
                                                        <c:if test="${consignment.delegator1Phone eq list}">selected</c:if>>${list}</option>
                                            </c:forEach>
                                        </select>
                                        <%--<input type="text" class="form-control required"--%>
                                        <%--id="delegator1Phone" value="${consignment.delegator1Phone}"/>--%>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托人2（姓名、职务） <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-2">
                                        <select class="form-control required" name="delegator2" id="delegator2">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${delegatorList}" var="list" varStatus="s">
                                                <option value="${list}"
                                                        <c:if test="${consignment.delegator2 eq list}">selected</c:if>>${list}</option>
                                            </c:forEach>
                                        </select>
                                        <%--<input type="text" class="form-control required" placeholder="姓名"--%>
                                        <%--id="delegator2" value="${consignment.delegator2}"/>--%>
                                    </div>
                                    <div class="col-sm-3">
                                        <select class="form-control required" name="delegator2Position"
                                                id="delegator2Position">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${dictItemDutyList}" var="list" varStatus="s">
                                                <option value="${list.dictName}"
                                                        <c:if test="${consignment.delegator2Position eq list.dictName}">selected</c:if>>${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                        <%--<input type="text" class="form-control required" placeholder="职务"--%>
                                        <%--id="delegator2Position" value="${consignment.delegator2Position}"/>--%>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托人2（证件、证件号） <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-2">
                                        <select class="form-control required" name="delegator2Cname"
                                                id="delegator2Cname">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${dictItemCertificateList}" var="list" varStatus="s">
                                                <option value="${list.dictName}"
                                                        <c:if test="${consignment.delegator2Cname eq list.dictName}">selected</c:if>>${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                        <%--<input type="text" class="form-control required" placeholder="证件名称"--%>
                                        <%--id="delegator2Cname" value="${consignment.delegator2Cname}"/>--%>
                                    </div>
                                    <div class="col-sm-3">
                                        <select class="form-control required" name="delegator2Cno" id="delegator2Cno">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${delegatorCertificateNoList}" var="list" varStatus="s">
                                                <option value="${list}"
                                                        <c:if test="${consignment.delegator2Cno eq list}">selected</c:if>>${list}</option>
                                            </c:forEach>
                                        </select>
                                        <%--<input type="text" class="form-control required" placeholder="证件号码"--%>
                                        <%--id="delegator2Cno" value="${consignment.delegator2Cno}"/>--%>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托人2电话 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <select class="form-control required" name="delegator2Phone"
                                                id="delegator2Phone">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${delegatorPhoneList}" var="list" varStatus="s">
                                                <option value="${list}"
                                                        <c:if test="${consignment.delegator2Phone eq list}">selected</c:if>>${list}</option>
                                            </c:forEach>
                                        </select>
                                        <%--<input type="text" class="form-control required"--%>
                                        <%--id="delegator2Phone" value="${consignment.delegator2Phone}"/>--%>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">通讯地址 </label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control"
                                               id="postalAddress" value="${consignment.postalAddress}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">邮政编号 </label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control"
                                               id="postNo" value="${consignment.postNo}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">传真号码 </label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control"
                                               id="faxNo" value="${consignment.postNo}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">鉴定要求 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <select class="form-control required" name="identifyRequirementList"
                                                id="identifyRequirementList">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${identifyRequirementList}" var="list" varStatus="s">
                                                <option value="${list.dictCode}"
                                                        <c:if test="${consignment.identifyRequirement eq list.dictCode}">selected</c:if>>${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                        <br>
                                        <input type="hidden" name="identifyRequirementOtherHide"
                                               id="identifyRequirementOtherHide"
                                               value="${consignment.identifyRequirementOther}">
                                        <input type="text" class="form-control required"
                                               onmouseover="this.title=this.value" name="identifyRequirementOther"
                                               id="identifyRequirementOther"
                                               value="${consignment.identifyRequirementOther}">
                                    </div>
                                    <div class="col-sm-2">
                                        <input type="text" class="form-control" onmouseover="this.title=this.value"
                                               name="matchCaseNo" id="matchCaseNo" placeholder="比对案件编号"
                                               value="${consignment.matchCaseNo}" style="display:none">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">原鉴定情况（鉴定单位及结论）</label>
                                    <div class="col-sm-5">
                                        <textarea class="form-control" rows="2"
                                                  id="preIdentifyDesc">${consignment.preIdentifyDesc}</textarea>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">重新鉴定原因</label>
                                    <div class="col-sm-5">
                                        <textarea class="form-control" rows="2"
                                                  id="reidentifyReason">${consignment.reidentifyReason}</textarea>
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
                            <div class="space15" style="height: 5px;"></div>
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>人员类型</th>
                                    <th>姓名</th>
                                    <th>性别</th>
                                    <th>身份证号</th>
                                    <th>与人员关系</th>
                                    <th width="100px">操作</th>
                                </tr>
                                </thead>
                                <tbody id="personInfoTable">
                                <c:if test="${fn:length(personInfoList) gt 0}">
                                    <c:forEach items="${personInfoList}" var="person" varStatus="p">
                                        <tr>
                                            <td><input type="hidden" name="personType"
                                                       value="${person.personType}"/>${person.personTypeName}</td>
                                            <td><input type="hidden" name="personName"
                                                       value="${person.personName}"/>${person.personName}</td>
                                            <td><input type="hidden" name="personGender"
                                                       value="${person.personGender}"/>${person.personGenderName}</td>
                                            <td>
                                                <input type="hidden" name="personIdcardNo"
                                                       value="${person.personIdcardNo}"/>
                                                <input type="hidden" name="noIdcardRemark"
                                                       value="${person.noIdcardRemark}"/>
                                                    ${person.personIdcardNo} <c:if
                                                    test="${not empty person.noIdcardRemark}">（${person.noIdcardRemark}）</c:if>
                                            </td>
                                            <td><input type="hidden" name="relativeIdentify"
                                                       value="${person.relativeIdentify}"/>${person.relativeIdentifyName}
                                            </td>
                                            <td>
                                                <input type="hidden" name="personId" value="${person.id}"/>
                                                <button name='editPerBtn' class='btn btn-primary btn-xs'><i
                                                        class='fa fa-list-alt'></i> 查看
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>

                            <div class="modal fade" id="personModal" aria-hidden="true" data-backdrop="static"
                                 data-keyboard="false">
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
                                                    <label class="control-label col-md-3">人员类型 <i
                                                            class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <select name="personType" class="form-control small required">
                                                            <option value="">请选择...</option>
                                                            <c:forEach items="${personTypeList}" var="list"
                                                                       varStatus="s">
                                                                <option value="${list.dictCode}">${list.dictName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">姓名 <i
                                                            class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <input name="personName" type="text"
                                                               class="form-control small required" value="">
                                                    </div>
                                                    <div class="col-md-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">性别 <i
                                                            class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <select name="personGender" class="form-control small required">
                                                            <c:forEach items="${personGenderList}" var="list"
                                                                       varStatus="s">
                                                                <option value="${list.dictCode}">${list.dictName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">身份证号 <i
                                                            class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <input name="personIdcardNo" type="text"
                                                               class="form-control small required"
                                                               style="width:180px; display: inline" value="">
                                                        <input id="noIdcardRemarkCkb" type="checkbox"
                                                               style="display:inline; margin: 5px 0 0 5px;"/> 无身份证
                                                        <input type="text" name="noIdcardRemark" style="margin-top:2px;"
                                                               readonly="readonly"
                                                               class="form-control small" placeholder="无身份备注"/>
                                                    </div>
                                                    <div class="col-md-3 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">与人员关系 <i
                                                            class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <select name="relativeIdentify"
                                                                class="form-control small required">
                                                            <c:forEach items="${personRelationList}" var="list"
                                                                       varStatus="s">
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
                                            <button data-dismiss="modal" class="btn btn-default" type="button">关闭
                                            </button>
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

                            <div class="space15" style="height: 30px;padding-top:10px;">
                                <strong>人员检材 <i class="fa fa-hand-o-down"></i></strong>
                            </div>
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>检材编号</th>
                                    <th>检材名称</th>
                                    <th>检材类型</th>
                                    <th>检材描述</th>
                                    <th>包装</th>
                                    <th>性状</th>
                                    <th>关联人员 <i class="fa fa-user"></i></th>
                                    <th width="100px">提取时间</th>
                                    <th width="100px">提取人</th>
                                    <th width="120px">受理状态</th>
                                    <th width="100px">操作</th>
                                </tr>
                                </thead>
                                <tbody id="samplePersonTable">
                                <c:if test="${fn:length(sampleInfoList) gt 0}">
                                    <c:forEach items="${sampleInfoList}" var="sample" varStatus="p">
                                        <c:if test="${sample.sampleFlag eq 1}">
                                            <tr>
                                                <td><input type='hidden' name='sampleNo'
                                                           value='${sample.sampleNo}'/>${sample.sampleNo}</td>
                                                <td title="${sample.sampleName}">
                                                    <input type='hidden' name='sampleName'
                                                           value='${sample.sampleName}'/>
                                                    <c:if test="${fn:length(sample.sampleName) gt 33}">
                                                        ${fn:substring(sample.sampleName,0,32)} ...
                                                    </c:if>
                                                    <c:if test="${fn:length(sample.sampleName) lt 34}">
                                                        ${sample.sampleName}
                                                    </c:if>
                                                </td>
                                                <td><input type='hidden' name='sampleType'
                                                           value='${sample.sampleType}'/>${sample.sampleTypeName}</td>
                                                <td><input type='hidden' name='sampleDesc'
                                                           value='${sample.sampleDesc}'/>${sample.sampleDesc}</td>
                                                <td><input type='hidden' name='samplePacking'
                                                           value='${sample.samplePacking}'/>${sample.samplePacking}</td>
                                                <td>
                                                    <input type='hidden' name='sampleProperties'
                                                           value='${sample.sampleProperties}'/>
                                                    <input type='hidden' name='otherPropertiesDesc'
                                                           value='${sample.otherPropertiesDesc}'/>
                                                    <c:if test="${sample.sampleProperties != '9999'}">
                                                        ${sample.samplePropertiesName}
                                                    </c:if>
                                                    <c:if test="${sample.sampleProperties eq '9999'}">
                                                        ${sample.otherPropertiesDesc}
                                                    </c:if>
                                                </td>
                                                <td><input type='hidden' name='refPersonId'
                                                           value='${sample.refPersonId}'/><input type='hidden'
                                                                                                 name='refPersonName'
                                                                                                 value='${sample.refPersonName}'/>${sample.refPersonName}
                                                </td>
                                                <td>
                                                    <input type='hidden' name='extractDatetime'
                                                           value="<fmt:formatDate value='${sample.extractDatetime}' pattern='yyyy-MM-dd'/>"/>
                                                    <fmt:formatDate value='${sample.extractDatetime}'
                                                                    pattern='yyyy-MM-dd'/>
                                                </td>
                                                <td>
                                                    <input type='hidden' name="extractPerson"
                                                           value="${sample.extractPerson}"/>
                                                        ${sample.extractPerson}
                                                </td>
                                                <td>
                                                    <c:if test="${sample.acceptStatus eq 0}"><span class="color_red"><i
                                                            class="fa fa-ban"></i> 未受理</span></c:if>
                                                    <c:if test="${sample.acceptStatus eq 1}"><span
                                                            class="color_green"><i
                                                            class="fa fa-check"></i> 已受理</span></c:if>
                                                </td>
                                                <td>
                                                    <input type="hidden" name="sampleId" value="${sample.id}"/>
                                                    <input type="hidden" name="sampleFlag"
                                                           value="${sample.sampleFlag}"/>
                                                    <button name='editSampleBtn' class='btn btn-primary btn-xs'><i
                                                            class='fa fa-list-alt'></i> 查看
                                                    </button>
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
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>检材编号</th>
                                    <th>检材名称</th>
                                    <th>检材类型</th>
                                    <th>检材描述</th>
                                    <th>包装</th>
                                    <th>性状</th>
                                    <th width="100px">提取时间</th>
                                    <th width="100px">提取人</th>
                                    <th width="120px">受理状态</th>
                                    <th width="100px">操作</th>
                                </tr>
                                </thead>
                                <tbody id="sampleEvidenceTable">
                                <c:if test="${fn:length(sampleInfoList) gt 0}">
                                    <c:forEach items="${sampleInfoList}" var="sample" varStatus="p">
                                        <c:if test="${sample.sampleFlag eq 0}">
                                            <tr>
                                                <td><input type='hidden' name='sampleNo'
                                                           value='${sample.sampleNo}'/>${sample.sampleNo}</td>
                                                <td title="${sample.sampleName}">
                                                    <input type='hidden' name='sampleName'
                                                           value='${sample.sampleName}'/>
                                                    <c:if test="${fn:length(sample.sampleName) gt 33}">
                                                        ${fn:substring(sample.sampleName,0,32)} ...
                                                    </c:if>
                                                    <c:if test="${fn:length(sample.sampleName) lt 34}">
                                                        ${sample.sampleName}
                                                    </c:if>
                                                </td>
                                                <td><input type='hidden' name='sampleType'
                                                           value='${sample.sampleType}'/>${sample.sampleTypeName}</td>
                                                <td><input type='hidden' name='sampleDesc'
                                                           value='${sample.sampleDesc}'/>${sample.sampleDesc}</td>
                                                <td><input type='hidden' name='samplePacking'
                                                           value='${sample.samplePacking}'/>${sample.samplePacking}</td>
                                                <td>
                                                    <input type='hidden' name='sampleProperties'
                                                           value='${sample.sampleProperties}'/>
                                                    <input type='hidden' name='otherPropertiesDesc'
                                                           value='${sample.otherPropertiesDesc}'/>
                                                    <c:if test="${sample.sampleProperties != '9999'}">
                                                        ${sample.samplePropertiesName}
                                                    </c:if>
                                                    <c:if test="${sample.sampleProperties eq '9999'}">
                                                        ${sample.otherPropertiesDesc}
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <input type='hidden' name='extractDatetime'
                                                           value="<fmt:formatDate value='${sample.extractDatetime}' pattern='yyyy-MM-dd'/>"/>
                                                    <fmt:formatDate value='${sample.extractDatetime}'
                                                                    pattern='yyyy-MM-dd'/>
                                                </td>
                                                <td>
                                                    <input type='hidden' name="extractPerson"
                                                           value="${sample.extractPerson}"/>
                                                        ${sample.extractPerson}
                                                </td>
                                                <td>
                                                    <c:if test="${sample.acceptStatus eq 0}"><span class="color_red"><i
                                                            class="fa fa-ban"></i> 未受理</span></c:if>
                                                    <c:if test="${sample.acceptStatus eq 1}"><span
                                                            class="color_green"><i
                                                            class="fa fa-check"></i> 已受理</span></c:if>
                                                </td>
                                                <td>
                                                    <input type="hidden" name="sampleId" value="${sample.id}"/>
                                                    <input type="hidden" name="sampleFlag"
                                                           value="${sample.sampleFlag}"/>
                                                    <button name='editSampleBtn' class='btn btn-primary btn-xs'><i
                                                            class='fa fa-list-alt'></i> 查看
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>

                            <div class="modal fade" id="sampleModal" aria-hidden="true" data-backdrop="static"
                                 data-keyboard="false">
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
                                                <div class="form-group">
                                                    <label class="control-label col-md-2">&nbsp;</label>
                                                    <div class="col-md-6">
                                                        <label class="checkbox-inline">
                                                            <input type="radio" id="sampleFlagEvidence"
                                                                   name="sampleFlag" value="0"> 物证
                                                        </label>
                                                        <label class="checkbox-inline">
                                                            <input type="radio" id="sampleFlagPerson" name="sampleFlag"
                                                                   value="1"> 人员
                                                        </label>
                                                    </div>
                                                </div>
                                                <div class="form-group" id="divRefPerson" style="display:none">
                                                    <label class="control-label col-md-3">关联人员 <i
                                                            class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <select name="refPerson" class="form-control small">
                                                            <option value="">无</option>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">检材名称 <i
                                                            class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <input name="sampleName" type="text"
                                                               class="form-control small required" value="">
                                                    </div>
                                                    <div class="col-md-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">检材类型 <i
                                                            class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <select name="sampleType" class="form-control small required">
                                                            <c:forEach items="${sampleTypeList}" var="list"
                                                                       varStatus="s">
                                                                <option value="${list.dictCode}">${list.dictName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">提取时间 <i
                                                            class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <span style="position: relative; z-index: 9999;">
                                                            <input name="extractDatetime" type="text"
                                                                   class="form_date form-control small required"
                                                                   value="" readonly>
                                                        </span>
                                                    </div>
                                                    <div class="col-md-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">提取人 <i
                                                            class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <span style="position: relative; z-index: 9999;">
                                                            <input name="extractPerson" type="text"
                                                                   class="form-control small required" value="">
                                                        </span>
                                                    </div>
                                                    <div class="col-md-3 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">样本描述 <i
                                                            class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <input name="sampleDesc" type="text" class="form-control small"
                                                               value="">
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">样本包装 <i
                                                            class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <input name="samplePacking" type="text"
                                                               class="form-control small" value="" readonly>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">样本性状 <i
                                                            class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <select name="sampleProperties"
                                                                class="form-control small required">
                                                            <c:forEach items="${samplePropertiesList}" var="sp">
                                                                <option value="${sp.dictCode}">${sp.dictName}</option>
                                                            </c:forEach>
                                                        </select>
                                                        <input type="text" name="otherPropertiesDesc"
                                                               style="margin-top:2px;"
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
                                            <button data-dismiss="modal" class="btn btn-default" type="button">关闭
                                            </button>
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
                                <div class="col-lg-offset-9 col-lg-10">
                                    <input type="hidden" id="fromUrl" value="${fromUrl}"/>
                                    <button class="btn btn-lg btn-primary" id="backBtn" type="button"><i
                                            class="fa fa-reply"></i> 返 回
                                    </button>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>

        </section>
        <!-- END WRAPPER  -->
    </section>
    <!-- END MAIN CONTENT -->
    <!-- BEGIN FOOTER -->
    <%--<footer class="site-footer">--%>
    <%--<div class="text-center">--%>
    <%--Copyright © 2016 北京博安智联科技有限公司--%>
    <%--</div>--%>
    <%--</footer>--%>
    <!-- END  FOOTER -->
</section>
<!-- END SECTION -->
<!-- BEGIN JS -->
<script src="<%=path%>/js/jquery.js"></script><!-- BASIC JS LIABRARY -->
<script src="<%=path%>/js/bootstrap.min.js"></script><!-- BOOTSTRAP JS  -->
<script src="<%=path%>/js/jquery.dcjqaccordion.2.7.js"></script><!-- ACCORDING JS -->
<script src="<%=path%>/js/jquery.scrollTo.min.js"></script><!-- SCROLLTO JS  -->
<script src="<%=path%>/js/jquery.nicescroll.js"></script><!-- NICESCROLL JS  -->
<script src="<%=path%>/js/jquery-ui-1.9.2.custom.min.js"></script><!-- JQUERY UI JS  -->
<script src="<%=path%>/js/jquery.dcjqaccordion.2.7.js"></script><!-- ACCORDIN JS  -->
<script src="<%=path%>/js/bootstrap-switch.js"></script> <!-- BOOTSTRAP SWITCH JS  -->
<script src="<%=path%>/js/jquery.tagsinput.js"></script> <!-- TAGS INPUT JS  -->
<script src="<%=path%>/js/ga.js"></script><!-- GA JS  -->
<script src="<%=path%>/assets/bootstrap-datepicker/js/bootstrap-datepicker.js"></script><!-- DATEPICKER JS  -->
<script src="<%=path%>/assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script><!-- DATEPICKER JS  -->
<script src="<%=path%>/assets/bootstrap-inputmask/bootstrap-inputmask.min.js"></script><!-- INPUT MASK JS  -->
<script src="<%=path%>/js/respond.min.js"></script><!-- RESPOND JS  -->
<script src="<%=path%>/js/common-scripts.js"></script> <!-- BASIC COMMON JS  -->
<script src="<%=path%>/js/identifyReg.js"></script>
<script>

    getMatchCaseNo();

    function getMatchCaseNo() {
        var matchSample = $("#matchCaseNo").val();
        if (matchSample != null && matchSample != "")
            $("#matchCaseNo").show();
    }

    $(function () {

        $("#identifyRequirementList", "#consignment_form").on("click", function () {
            var identifyRequirementCode = $("option:selected", $(this)).val();
            var identifyRequirementName = $("option:selected", $(this)).text();

            if (identifyRequirementCode != "") {
                if (identifyRequirementCode == "05") {
                    var identifyRequirementOtherHide = $("#identifyRequirementOtherHide").val();
                    $("#identifyRequirementOther").val(identifyRequirementOtherHide);
                    $("input[name='identifyRequirementOther']").attr("placeholder", "DNA检验鉴定与毕公司鉴（法物）字【XXX】XXX号鉴定文书中相关检材做比对。");
                    $("#matchCaseNo").show();
                } else {
                    $("input[name='identifyRequirementOther']").removeAttr("placeholder");
                    $("#identifyRequirementOther").val(identifyRequirementName);
                    $("#matchCaseNo").hide();
                }
            } else {
                $("input[name='identifyRequirementOther']").removeAttr("placeholder");
                $("#identifyRequirementOther").val("");
                $("#matchCaseNo").hide();
            }
        });

        $("#sampleFlagEvidence").on("click", function () {
            $("input[name='sampleName']", "#sampleModal").removeAttr("placeholder");
        });

        $("#sampleFlagPerson").on("click", function () {
            $("input[name='sampleName']", "#sampleModal").attr("placeholder", "例如：XXX血样或口腔拭子");
        });

        $('.form_datetime').datetimepicker({
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

        $("#backBtn").on("click", function () {
            location.href = "<%=path%>/wt/caseinfo/listAccepted.html";
        });

    });
</script>
<!-- END JS -->
</body>
</html>


