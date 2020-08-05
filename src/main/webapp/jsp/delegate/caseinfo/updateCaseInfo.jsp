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
                    <a href="<%=path%>/wt/reg/1.html" >
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
                            <form id="caseinfo_form" class="form-horizontal tasi-form" method="post">
                                <input type="hidden" id="entrustmentType" name="entrustmentType"
                                       value="${caseInfo.entrustmentType}">
                                <input type="hidden" name="caseId" id="caseId"
                                       value="${caseInfo.id}">
                                <input type="hidden" name="caseNo" id="caseNo"
                                       value="${caseInfo.caseNo}">
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件名称 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" id="caseName" name="caseName" class="form-control required"
                                               value="${caseInfo.caseName}" readonly>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案发地点</label>
                                    <div class="col-sm-5">
                                        <input type="text" id="caseLocationDesc" name="caseLocationDesc"
                                               class="form-control"
                                               value="${caseInfo.caseLocationDesc}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案发时间 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input class="form_datetime form-control required" id="caseDatetime"
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
                                    <div class="col-sm-7" style="width: 42%">
                                <textarea class="form-control required" id="caseBrief" name="caseBrief"
                                          rows="3">${caseInfo.caseBrief}</textarea>
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
                            <span class="label label-primary">委托信息</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                            <form id="consignment_form" class="form-horizontal tasi-form" method="get">
                                <input type="hidden" id="additionalFlag" value="0"/>
                                <input type="hidden" id="consignmentId" value="${limsConsignment.id}"/>
                                <input type="hidden" id="consignmentCaseId" value="${limsConsignment.caseId}"/>
                                <input type="hidden" id="delegateOrg" value="${limsConsignment.delegateOrg}"/>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托单位 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control required" placeholder="XXX公安司法鉴定中心"
                                               id="delegateOrgDesc" name="delegateOrgDesc"
                                               value="<c:if test="${empty limsConsignment.delegateOrgDesc}"><shiro:user><shiro:principal property="orgName"/></shiro:user></c:if>${limsConsignment.delegateOrgDesc}"
                                               readonly/>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托单位电话</label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control " id="delegateOrgPhone"
                                               name="delegateOrgPhone"
                                               value="${limsConsignment.delegateOrgPhone}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托编号</label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control" id="entrustingSerial"
                                               name="entrustingSerial" value="${limsConsignment.entrustingSerial}"/>
                                    </div>
                                    <%--<div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>--%>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托时间 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input class="form_datetime form-control required" id="delegateDatetime"
                                               name="delegateDatetime" type="text"
                                               value="<fmt:formatDate value='${limsConsignment.delegateDatetime}' pattern='yyyy-MM-dd'/>"
                                               readonly>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托人1（姓名、电话） <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-2">
                                        <select class="form-control required" name="delegator1" id="delegator1">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${delegatorList}" var="list" varStatus="s">
                                                <option value="${list}"
                                                        <c:if test="${limsConsignment.delegator1 eq list}">selected</c:if>>${list}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-3">
                                        <select class="form-control required" name="delegator1Phone"
                                                id="delegator1Phone">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${delegatorPhoneList}" var="list" varStatus="s">
                                                <option value="${list}"
                                                        <c:if test="${limsConsignment.delegator1Phone eq list}">selected</c:if>>${list}</option>
                                            </c:forEach>
                                        </select>
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
                                                        <c:if test="${limsConsignment.delegator1Cname eq list.dictName}">selected</c:if>>${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-3">
                                        <select class="form-control required" name="delegator1Cno" id="delegator1Cno">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${delegatorCertificateNoList}" var="list" varStatus="s">
                                                <option value="${list}"
                                                        <c:if test="${limsConsignment.delegator1Cno eq list}">selected</c:if>>${list}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托人2（姓名、电话） <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-2">
                                        <select class="form-control required" name="delegator2" id="delegator2">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${delegatorList}" var="list" varStatus="s">
                                                <option value="${list}"
                                                        <c:if test="${limsConsignment.delegator2 eq list}">selected</c:if>>${list}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-3">

                                        <select class="form-control required" name="delegator2Phone"
                                                id="delegator2Phone">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${delegatorPhoneList}" var="list" varStatus="s">
                                                <option value="${list}"
                                                        <c:if test="${limsConsignment.delegator2Phone eq list}">selected</c:if>>${list}</option>
                                            </c:forEach>
                                        </select>

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
                                                        <c:if test="${limsConsignment.delegator2Cname eq list.dictName}">selected</c:if>>${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-3">
                                        <select class="form-control required" name="delegator2Cno" id="delegator2Cno">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${delegatorCertificateNoList}" var="list" varStatus="s">
                                                <option value="${list}"
                                                        <c:if test="${limsConsignment.delegator2Cno eq list}">selected</c:if>>${list}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">鉴定要求 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control required" id="identifyRequirement"
                                               name="identifyRequirement"
                                               value="${limsConsignment.identifyRequirement}"/>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托详情 </label>
                                    <div class="col-sm-7" style="width: 41.5%">
                                <textarea class="form-control " id="remark" name="remark"
                                          rows="3">${limsConsignment.remark} </textarea>
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
                                <c:forEach items="${personSampleList}" var="personSample" varStatus="s">
                                    <tr class="regedTr">
                                        <td>${s.index+1}</td>
                                        <td><input type="hidden" name="personName"
                                                   value="${personSample.personName}"> ${personSample.personName}</td>
                                        <td><input type="hidden" name="personType"
                                                   value="${personSample.personType}"> ${personSample.personTypeCode}
                                        </td>
                                        <td><input type="hidden" name="sampleName"
                                                   value="${personSample.sampleName}"> ${personSample.sampleName}</td>
                                        <td><input type="hidden" name="sampleType"
                                                   value="${personSample.sampleType}"> ${personSample.sampleTypeCode}
                                        </td>
                                        <td><input type="hidden" name="oneselfContact"
                                                   value="${personSample.oneselfContact}"> ${personSample.oneselfContactCode}
                                        </td>
                                        <td><input type="hidden" name="sampleLaboratoryNo"
                                                   value="${personSample.sampleLaboratoryNo}"> ${personSample.sampleLaboratoryNo}
                                        </td>
                                        <td>
                                            <input type="hidden" name="personId" value="${personSample.id}">
                                            <input type="hidden" name="sampleId" value="${personSample.sampleId}">
                                            <input type="hidden" name="entrustmentId"
                                                   value="${personSample.entrustmentId}">
                                            <input type="hidden" name="personTypeCode"
                                                   value="${personSample.personType}">
                                            <input type="hidden" name="sampelTypeCode"
                                                   value="${personSample.sampleType}">
                                            <input type="hidden" name="caseInformationId"
                                                   value="${personSample.caseInformationId}">
                                            <input type="hidden" name="oneselfContactCode"
                                                   value="${personSample.oneselfContact}">
                                            <input type="hidden" name="createDatetime"
                                                   value=" <fmt:formatDate value='${personSample.createDatetime}' pattern='yyyy-MM-dd'/> ">
                                            <button name='updateSampleBtn' class='btn btn-primary btn-sm'><i
                                                    class='fa fa-pencil'></i> 修改
                                            </button>
                                            <button name='delSampleBtn' class='btn btn-danger btn-sm'><i
                                                    class='fa fa-trash-o'></i> 删除
                                            </button>

                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </section>
                </div>
            </div>

            <div class="modal fade" id="personModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                &times;
                            </button>
                            <h4 class="modal-title">
                                人员样本信息
                            </h4>
                        </div>
                        <div class="modal-body">
                            <form class="form-horizontal tasi-form" id="personSample_form">
                                <input type="hidden" name="personId" id="personId" value=""/>
                                <input type="hidden" name="sampleId" id="sampleId" value=""/>
                                <input type="hidden" name="entrustId" id="entrustId" value=""/>
                                <input type="hidden" name="caseInformationId" id="caseInformationId" value="">
                                <input type="hidden" name="createDatetime" id="createDatetime" value="">
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label" style="width:164px;">姓 名<i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-7">
                                        <input type="text" id="personName" name="personName"
                                               class="form-control required"
                                               value="${personSample.personName}">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label" style="width:164px;">人员类别<i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-7">
                                        <select class="form-control required" name="personType">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${personTypeList}" var="persontype" varStatus="s">
                                                <option value="${persontype.dictCode}">${persontype.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label" style="width:164px;">样本名称<i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-7">
                                        <input type="text" name="sampleName" id="sampleName"
                                               class="form-control required" value="${personSample.sampleName}">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label" style="width:164px;">样本类型</label>
                                    <div class="col-sm-7">
                                        <select class="form-control" name="sampelType">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${sampleTypeList}" var="sampleType" varStatus="s">
                                                <option value="${sampleType.dictCode}"
                                                        <c:if test="${sampleType.dictCode eq personSample.sampleType}">selected</c:if> >${sampleType.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label" style="width:164px;">样本与本人关系</label>
                                    <div class="col-sm-7">
                                        <select class="form-control" name="oneselfContact" id="oneselfContact">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${personRelationList}" var="personRelation" varStatus="s">
                                                <option value="${personRelation.dictCode}"
                                                        <c:if test="${personRelation.dictCode eq personSample.oneselfContact}">selected</c:if> >${personRelation.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label" style="width:164px;">样本实验室编号<i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-7">
                                        <input type="text" name="sampleLaboratoryNo" id="sampleLaboratoryNo"
                                               class="form-control required" value="${personSample.sampleLaboratoryNo}">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button class="btn btn-success" type="button" id="personUpdateBtn">确定</button>
                            <button data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <div class="panel-body">
                            <div class="form-group">
                                <div class="col-lg-offset-4 col-lg-10">
                                    <input type="hidden" id="operateType" value="${operateType}"/>
                                    <button class="btn btn-lg btn-primary" id="nextBtn" type="button"><i
                                            class="fa fa-save"></i>完成
                                    </button>
                                    <button id="backBtn" class="btn btn-lg btn-default" type="button"><i
                                            class="fa fa-reply"></i> 返 回
                                    </button>
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

    getMatchCaseNo();

    function getMatchCaseNo() {
        var matchSample = $("#matchCaseNo").val();
        if (matchSample != null && matchSample != "")
            $("#matchCaseNo").show();
    }

    $(function () {

        $("#delegator1", "#consignment_form").on("change", function () {

            var delegatorName = $("#delegator1 option:selected").val();

            if (delegatorName == "") {
                $("#delegator1Phone").val("");
                $("#delegator1Cname").val("");
                $("#delegator1Cno").val("");

            }

            $.ajax({
                url: "<%=path%>/wt/consignment/selectDelagatorQuery.html?delegatorName=" + delegatorName,
                type: "post",
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    if (data.length > 0) {
                        var dataLen = data.length;
                        for (var i = 0; i < dataLen; i++) {
                            $("#delegator1Phone").val(data[i].delegatorPhone);
                            $("#delegator1Cname").val(data[i].delegatorCertificateName);
                            $("#delegator1Cno").val(data[i].delegatorCertificateNo);
                        }
                    }
                },
                error: function (data, e) {
                    alert("查询失败!");
                }
            });
        });

        $("#delegator2", "#consignment_form").on("change", function () {

            var delegatorName = $("#delegator2 option:selected").val();

            if (delegatorName == "") {
                $("#delegator2Phone").val("");
                $("#delegator2Cname").val("");
                $("#delegator2Cno").val("");
            }

            $.ajax({
                url: "<%=path%>/wt/consignment/selectDelagatorQuery.html?delegatorName=" + delegatorName,
                type: "post",
                dataType: "json",
                contentType: "application/json; charset=utf-8",
                success: function (data) {
                    if (data.length > 0) {
                        var dataLen = data.length;
                        for (var i = 0; i < dataLen; i++) {
                            $("#delegator2Phone").val(data[i].delegatorPhone);
                            $("#delegator2Cname").val(data[i].delegatorCertificateName);
                            $("#delegator2Cno").val(data[i].delegatorCertificateNo);
                        }
                    }
                },
                error: function (data, e) {
                    alert("查询失败!");
                }
            });
        });

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

        $("#returnBtn").on("click", function () {
            if (!confirm("此操作不保存数据，是否返回！")) {
                return;
            }
            location.href = "<%=path%>/wt/reg/2.html";
        });

        $("#resetBtn").on("click", function () {
            location.href = "<%=path%>/wt/reg/2.html";
        });

        $("#getXkBtn").on("click", function () {
            var caseXkNo = $("#caseXkNo", "#caseinfo_form").val();
            if (caseXkNo == "") {
                alert("请输入现堪编号！");
                return;
            }
            location.href = "<%=path%>/wt/reg/getCaseInfoXk.html";
        });

        $("#backBtn").on("click", function () {
            history.go(-1);
            /*var entrustmentType = $("#entrustmentType", "#caseinfo_form").val();

            if (entrustmentType == "01") {
                location.href = "<%=path%>/center/caseInformation/unidentifiedList.html";

            } else if (entrustmentType == "02") {
                location.href = "<%=path%>/center/caseInformation/missingList.html";
            }*/

        });

        $("button[name='delSampleBtn']", "#personInfoTbody").on("click", function () {
            if (!confirm("是否删除！"))
                return;

            var personId = $("input[name='personSampleId']").val();

            $.ajax({
                url: '<%=path%>/center/caseInformation/delPerson.html?personId=' + personId,
                type: "get",
                dataType: "json",
                success: function (data) {
                    if (data.success || data.success == true || data.success == "true") {
                        window.location.reload();
                    }
                }
            });
        });

        $("#personUpdateBtn").on("click", function () {
            var personErrCnt = 0;
            $(".required", "#consignment_form").each(function () {
                if ($(this).val() == "") {
                    $("div.has-error", $(this).parents("div.form-group")).removeClass("hide");
                    personErrCnt++;
                } else {
                    $("div.has-error", $(this).parents("div.form-group")).addClass("hide");
                }
            });

            if (personErrCnt > 0) {
                alert("请补全必填项！");
                return false;
            }
            var personSample = GetpersonSample();
            var params = {
                "personSample": personSample
            };
            $.ajax({
                url: "<%=path%>/center/caseInformation/updatePersonSample.html",
                type: "post",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(params),
                dataType: "json",
                success: function (data) {
                    alert("保存成功")
                    window.location.reload();
                },
                error: function (e) {
                    alert(e);
                }
            });


        });


        $("#nextBtn").on("click", function () {
            submitForm();
        });

        function submitForm() {
            if (!checkInputValidation()) {
                return;
            }

            var caseInformation = GetCaseIfornmation();
            var consignment = GetConsignment();
            var operateType = $("#operateType").val();

            var params = {
                "caseInformation": caseInformation,
                "consignment": consignment
            };

            $.ajax({
                url: "<%=path%>/center/caseInformation/updateCase.html?operateType=" + operateType,
                type: "post",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(params),
                dataType: "json",
                success: function (data) {
                    alert("保存成功")
                    window.location.reload();
                    /* var caseId = data.caseId;
                    console.log(caseId);
                    location.href = "<%=path%>/center/caseInformation/editCaseInformation.html?caseId=" + caseId;*/
                },
                error: function (e) {
                    alert(e);
                }
            });
        }

        function checkInputValidation() {
            //caseInfo_form validate
            var caseErrCnt = 0;
            $(".required", "#caseinfo_form").each(function () {
                if ($(this).val() == "") {
                    $("div.has-error", $(this).parents("div.form-group")).removeClass("hide");
                    caseErrCnt++;
                } else {
                    $("div.has-error", $(this).parents("div.form-group")).addClass("hide");
                }
            });

            var consignErrCnt = 0;
            $(".required", "#consignment_form").each(function () {
                if ($(this).val() == "") {
                    $("div.has-error", $(this).parents("div.form-group")).removeClass("hide");
                    consignErrCnt++;
                } else {
                    $("div.has-error", $(this).parents("div.form-group")).addClass("hide");
                }
            });

            if (caseErrCnt > 0 || consignErrCnt > 0) {
                alert("请补全案件和委托信息的必填项！");
                return false;
            }

            return true;
        }

        function GetCaseIfornmation() {
            var caseInfo = {};
            caseInfo.id = $("#caseId", "#caseinfo_form").val();
            caseInfo.caseNo = $("#caseNo", "#caseinfo_form").val();
            caseInfo.caseName = $("#caseName", "#caseinfo_form").val();
            caseInfo.caseDatetime = $("#caseDatetime", "#caseinfo_form").val();
            caseInfo.caseBrief = $("#caseBrief", "#caseinfo_form").val();
            caseInfo.caseLocationDesc = $("#caseLocationDesc", "#caseinfo_form").val();
            caseInfo.entrustmentType = $("#entrustmentType", "#caseinfo_form").val();
            return caseInfo;
        }

        function GetConsignment() {
            var consignment = {}
            consignment.id = $("#consignmentId", "#consignment_form").val();
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

        function GetpersonSample() {
            var personSample = {}
            personSample.id = $("#personId", "#personSample_form").val();
            personSample.sampleId = $("#sampleId", "#personSample_form").val();
            personSample.entrustmentId = $("#entrustId", "#personSample_form").val();
            personSample.caseInformationId = $("#caseInformationId", "#personSample_form").val();
            personSample.personName = $("input[name='personName']", "#personSample_form").val();
            personSample.personType = $("select[name='personType']", "#personSample_form").val();
            personSample.sampleName = $("input[name='sampleName']", "#personSample_form").val();
            personSample.sampleType = $("select[name='sampelType']", "#personSample_form").val();
            personSample.oneselfContact = $("select[name='oneselfContact']", "#personSample_form").val();
            personSample.sampleLaboratoryNo = $("input[name='sampleLaboratoryNo']", "#personSample_form").val();
            personSample.createDatetime = $("input[name='createDatetime']", "#personSample_form").val();
            return personSample;
        }

        $("button[name='updateSampleBtn']", "#personInfoTbody").on("click", function () {
            AddSampleRow(this);
        });

        function AddSampleRow(obj) {
            var $curTR = $(obj).parents("tr");
            var personSample = {};
            personSample.personId = $("input[name='personId']", $curTR).val();
            personSample.sampleId = $("input[name='sampleId']", $curTR).val();
            personSample.personName = $("input[name='personName']", $curTR).val();
            personSample.personTypeCode = $("input[name='personTypeCode']", $curTR).val();
            personSample.sampleName = $("input[name='sampleName']", $curTR).val();
            personSample.sampleTypeCode = $("input[name='sampelTypeCode']", $curTR).val();
            personSample.oneselfContactCode = $("input[name='oneselfContactCode']", $curTR).val();
            personSample.sampleLaboratoryNo = $("input[name='sampleLaboratoryNo']", $curTR).val();
            personSample.entrustmentId = $("input[name='entrustmentId']", $curTR).val();
            personSample.caseInformationId = $("input[name='caseInformationId']", $curTR).val();
            personSample.createDatetime = $("input[name='createDatetime']", $curTR).val();
            newSampleRow(personSample, "add", 0);
        }

        function newSampleRow(personSample, operateType, rownum) {
            $("input[name='personId']", "#personModal").val(personSample.personId);
            $("input[name='sampleId']", "#personModal").val(personSample.sampleId);
            $("input[name='personName']", "#personModal").val(personSample.personName);
            $("select[name='personType']", "#personModal").val(personSample.personTypeCode);
            $("input[name='sampleName']", "#personModal").val(personSample.sampleName);
            $("select[name='sampelType']", "#personModal").val(personSample.sampleTypeCode);
            $("select[name='oneselfContact']", "#personModal").val(personSample.oneselfContactCode);
            $("input[name='sampleLaboratoryNo']", "#personModal").val(personSample.sampleLaboratoryNo);
            $("input[name='entrustId']", "#personModal").val(personSample.entrustmentId);
            $("input[name='caseInformationId']", "#personModal").val(personSample.caseInformationId);
            $("input[name='createDatetime']", "#personModal").val(personSample.createDatetime);
            $("#personModal").modal('show');
        }

    });
</script>
<!-- END JS -->
</body>
</html>


