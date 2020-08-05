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
    <link href="<%=path %>/js/layui-v2.2.45/layui/css/layui.css" rel="stylesheet" type="text/css"/>
    <!--[if lt IE 9]>
    <script src="<%=path%>/js/html5shiv.js">
    </script>
    <script src="<%=path%>/js/respond.min.js">
    </script>
    <![endif]-->
    <!-- END STYLESHEET-->
    <style>
        .loading {
            font-size: 100px;
            position: fixed;
            top: 50%;
            left: 50%;
            color: #fff;
            margin-left: -50px;
            margin-top: -50px;
        }

        #nav {
            padding: 0px;

        }

        #nav .breadcrumb {
            margin-bottom: 0px;
            background: #fff;
        }

        #nav .breadcrumb > li + li:before {
            content: ">>";
            color: #ccc;
        }

        #nav .breadcrumb > li a {
            font-size: 13px;
            color: #0a6def;
        }

        #nav .breadcrumb > .active {
            font-size: 13px;
            color: #49535e;
        }
    </style>
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
                    <a href="<%=path%>/wt/reg/1.html" class="active">
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

                <%--<li>
                    <a href="<%=path%>/wt/reg/unentrustedList.html">
                        未委托现堪案件
                    </a>
                </li>--%>

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
                            <span class="label label-primary">案件信息</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                            <form id="caseinfo_form" class="form-horizontal tasi-form" method="get">
                                <input type="hidden" id="caseId" value="${caseInfo.id}"/>

                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件现堪编号 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" id="caseXkNo" name="caseXkNo" class="form-control required"
                                               value="${caseInfo.caseXkNo}">
                                        <input type="hidden" id="caseXkAno" name="caseXkAno" class="form-control"
                                               value="${caseInfo.caseXkAno}">
                                    </div>
                                    <div class="col-sm-5">
                                        <a class="btn btn-primary" id="getXkBtn">
                                            <i class="fa fa-plus"></i> 获取现堪数据
                                        </a>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
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
                                        <select class="form-control required" name="caseType" id="caseType">
                                            <c:forEach items="${caseTypeList}" var="list" varStatus="s">
                                                <option value="${list.dictCode}"
                                                        <c:if test="${caseInfo.caseType eq list.dictCode}">selected</c:if>>${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">案件性质 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-3">
                                        <select class="form-control required" name="caseProperty" id="caseProperty">
                                            <c:forEach items="${casePropertyList}" var="list" varStatus="s">
                                                <option value="${list.dictCode}"
                                                        <c:if test="${caseInfo.caseProperty eq list.dictCode}">selected</c:if>>${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件级别</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" name="caseLevel" id="caseLevel">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${caseLevelList}" var="list" varStatus="s">
                                                <option value="${list.dictCode}"
                                                        <c:if test="${caseInfo.caseLevel eq list.dictCode}">selected</c:if>>${list.dictName}</option>
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
                                <input type="hidden" id="additionalFlag" value="0"/>
                                <input type="hidden" id="consignmentId" value="${consignment.id}"/>
                                <input type="hidden" id="consignmentNo" name="consignmentNo"
                                       value="${consignment.consignmentNo}"/>

                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托单位 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control required" placeholder="XXX公安司法鉴定中心"
                                               id="delegateOrgDesc"
                                               value="<c:if test="${empty consignment.delegateOrgDesc}"><shiro:user><shiro:principal property="orgName"/></shiro:user></c:if>${consignment.delegateOrgDesc}"/>
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

                                    </div>
                                    <div class="col-sm-3">
                                        <select class="form-control required" name="delegator1Cno" id="delegator1Cno">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${delegatorCertificateNoList}" var="list" varStatus="s">
                                                <option value="${list}"
                                                        <c:if test="${consignment.delegator1Cno eq list}">selected</c:if>>${list}</option>
                                            </c:forEach>
                                        </select>

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
                                    </div>
                                    <div class="col-sm-3">
                                        <select class="form-control required" name="delegator2Cno" id="delegator2Cno">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${delegatorCertificateNoList}" var="list" varStatus="s">
                                                <option value="${list}"
                                                        <c:if test="${consignment.delegator2Cno eq list}">selected</c:if>>${list}</option>
                                            </c:forEach>
                                        </select>
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
                                               id="faxNo" value="${consignment.faxNo}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">鉴定机构名称</label>
                                    <div class="col-sm-5">
                                        <select class="form-control" name="identifyKernelName" id="identifyKernelName">
                                            <c:forEach items="${identifyKernelList}" var="list" varStatus="s">
                                                <option value="${list.identifyKernelName}"
                                                        <c:if test="${consignment.identifyKernelName eq list.identifyKernelName}">selected</c:if>>${list.identifyKernelName}</option>
                                            </c:forEach>
                                        </select>
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
                                        <tr>
                                            <td></td>
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
                                                        class='fa fa-pencil'></i> 修改
                                                </button>
                                                &nbsp;
                                                <button name='delPerBtn' class='btn btn-danger btn-xs'><i
                                                        class='fa fa-trash-o'></i> 删除
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
                                                    <div class="col-md-3 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">姓名 <i
                                                            class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <input name="personName" type="text"
                                                               class="form-control small required" value=""/>
                                                    </div>
                                                    <div class="col-md-3 has-error hide">
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
                                                    <div class="col-md-3 has-error hide">
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
                                            <button class="btn btn-success" type="button" id="SavePersonBtn">保 存
                                            </button>
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
                            <a class="btn btn-primary" data-toggle="modal" data-target="#sampleModal" id="newPersonBtn">
                                <i class="fa fa-plus"></i> 添加人员检材
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
                                            <tr>
                                                <td></td>
                                                <td title="${sample.sampleName}">
                                                    <input type='hidden' name='sampleName'
                                                           value='${sample.sampleName}'/>
                                                    <c:if test="${fn:length(sample.sampleName) gt 43}">
                                                        ${fn:substring(sample.sampleName,0,42)} ...
                                                    </c:if>
                                                    <c:if test="${fn:length(sample.sampleName) lt 44}">
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
                                                    <input type="hidden" name="sampleId" value="${sample.id}"/>
                                                    <input type="hidden" name="sampleFlag"
                                                           value="${sample.sampleFlag}"/>
                                                    <button name='editSampleBtn' class='btn btn-primary btn-xs'><i
                                                            class='fa fa-pencil'></i> 修改
                                                    </button>
                                                    &nbsp;
                                                    <button name='delSampleBtn' class='btn btn-danger btn-xs'><i
                                                            class='fa fa-trash-o'></i> 删除
                                                    </button>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>

                            <a class="btn btn-primary" data-toggle="modal" data-target="#sampleModal" id="newSampleBtn">
                                <i class="fa fa-plus"></i> 添加物证检材
                            </a>
                            <c:if test="${operateType eq 2}">

                                <a class="btn btn-warning" id="refreshSampleBtn" name="refreshSampleBtn">
                                    <i class="fa fa-spinner"></i> 刷新现勘物证
                                </a>
                                <span class="label label-info hide" id="sampleCountInFile">刷新出（99）条数据</span>

                            </c:if>

                            <div class="space15" style="height: 30px;padding-top:10px;">
                                <strong>物证检材 <i class="fa fa-hand-o-down"></i></strong>
                            </div>
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
                                            <tr>
                                                <td></td>
                                                <td><input type='hidden' name='evidenceNo'
                                                           value='${sample.evidenceNo}'/>${sample.evidenceNo}</td>
                                                <td title="${sample.sampleName}">
                                                    <input type='hidden' name='sampleName'
                                                           value='${sample.sampleName}'/>
                                                    <c:if test="${fn:length(sample.sampleName) gt 43}">
                                                        ${fn:substring(sample.sampleName,0,42)} ...
                                                    </c:if>
                                                    <c:if test="${fn:length(sample.sampleName) lt 44}">
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
                                                    <input type='hidden' name='extractPerson'
                                                           value='${sample.extractPerson}'/>
                                                        ${sample.extractPerson}
                                                </td>
                                                <td>
                                                    <input type="hidden" name="sampleId" value="${sample.id}"/>
                                                    <input type="hidden" name="sampleFlag"
                                                           value="${sample.sampleFlag}"/>
                                                    <button name='editSampleBtn' class='btn btn-primary btn-xs'><i
                                                            class='fa fa-pencil'></i> 修改
                                                    </button>
                                                    &nbsp;
                                                    <button name='delSampleBtn' class='btn btn-danger btn-xs'><i
                                                            class='fa fa-trash-o'></i> 删除
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
                                                <div class="form-group" id="divRefPerson" style="display:none">
                                                    <label class="control-label col-md-3">关联人员 <i
                                                            class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <select name="refPerson" class="form-control small">
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
                                                            <option value="">请选择...</option>
                                                            <c:forEach items="${sampleTypeList}" var="list"
                                                                       varStatus="s">
                                                                <option value="${list.dictCode}">${list.dictName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-3 has-error hide">
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
                                                    <div class="col-md-3 has-error hide">
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
                                                        <input name="sampleDesc" type="text"
                                                               class="form-control small required" value=""
                                                               placeholder="（例如：棉签1根）">
                                                    </div>
                                                    <div class="col-md-3 has-error hide">
                                                        <p class="help-block">必填项（例如：棉签1根）</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">样本包装 <i
                                                            class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <input name="samplePacking" type="text"
                                                               class="form-control small required" value="物证袋">
                                                    </div>
                                                    <div class="col-md-3 has-error hide">
                                                        <p class="help-block">必填项</p>
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
                                            <input type="hidden" name="sampleFlag" value=""/>
                                            <input name="evidenceNo" type="hidden" value="">
                                            <button class="btn btn-success" type="button" id="SaveSampleBtn">保 存
                                            </button>
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

            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <div class="panel-body">
                            <div class="form-group">
                                <div class="col-lg-offset-6 col-lg-10">
                                    <input type="hidden" id="operateType" value="${operateType}"/>
                                    <button class="btn btn-lg btn-primary" id="nextBtn" type="button"><i
                                            class="fa fa-save"></i>保存
                                    </button>
                                    <c:if test="${operateType eq 1}">
                                        <button id="resetBtn" class="btn btn-lg btn-default" type="button"><i
                                                class="fa fa-undo"></i> 重 填
                                        </button>
                                        <button id="returnBtn" class="btn btn-lg btn-info" type="button"><i
                                                class="fa fa-reply"></i> 返 回
                                        </button>
                                    </c:if>
                                    <c:if test="${operateType eq 2}">
                                        <button id="backBtn" class="btn btn-lg btn-default" type="button"><i
                                                class="fa fa-reply"></i> 返 回
                                        </button>
                                    </c:if>
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
                            <button name="downloadDocBtn" class="btn btn-success btn-sm"><i class="fa fa-print"></i> 委托表
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
<script src="<%=path%>/js/layui-v2.2.45/layui/layui.js"></script> <!-- LAYUI JS  -->
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
                $("#delegator1Position").val("");
                $("#delegator1Cname").val("");
                $("#delegator1Cno").val("");
                $("#delegator1Phone").val("");
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
                            $("#delegator1Position").val(data[i].delegatorDutyName);
                            $("#delegator1Cname").val(data[i].delegatorCertificateName);
                            $("#delegator1Cno").val(data[i].delegatorCertificateNo);
                            $("#delegator1Phone").val(data[i].delegatorPhone);
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
                $("#delegator2Position").val("");
                $("#delegator2Cname").val("");
                $("#delegator2Cno").val("");
                $("#delegator2Phone").val("");
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
                            $("#delegator2Position").val(data[i].delegatorDutyName);
                            $("#delegator2Cname").val(data[i].delegatorCertificateName);
                            $("#delegator2Cno").val(data[i].delegatorCertificateNo);
                            $("#delegator2Phone").val(data[i].delegatorPhone);
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
            location.href = "<%=path%>/wt/reg/1.html";
        });

        $("#resetBtn").on("click", function () {
            location.href = "<%=path%>/wt/reg/1.html";
        });

        $("#backBtn").on("click", function () {
            location.href = "<%=path%>/wt/caseinfo/listPending.html";
        });

        layui.use('layer', function () {
            var layer = layui.layer;

            function loading() {
                //示范一个公告层
                layer.open({
                    type: 1
                    , title: false //不显示标题栏
                    , closeBtn: false
                    , shade: 0.4
                    , id: 'LAY_layuipro' //设定一个id，防止重复弹出
                    , btnAlign: 'c'
                    , moveType: 1 //拖拽模式，0或者1
                    , content: '<i class="layui-icon layui-anim layui-anim-rotate layui-anim-loop loading">&#xe63d;</i>'
                });
            }

            //点击调取现勘数据
            $("#getXkBtn").on("click", function () {
                //弹出层
                loading();

                var caseXkNo = $("#caseXkNo", "#caseinfo_form").val();

                if (caseXkNo == "") {
                    alert("请输入现堪编号！");
                    layer.closeAll();
                    return;
                }

                $.ajax({
                    url: "<%=path%>/wt/reg/getCaseInfoXk.html?caseXkNo=" + caseXkNo,
                    type: "post",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function (data) {
                        var caseInfo = data.limsCaseInfo;
                        var consignment = data.limsConsignment;
                        var limsSampleInfoList = data.limsSampleInfoList;

                        if (consignment != "") {
                            var consignmentNo = consignment.consignmentNo;
                            $("#consignmentNo", "#consignment_form").val(consignmentNo);
                        }

                        if (caseInfo != "") {
                            var caseName = caseInfo.caseName;
                            var caseXkAno = caseInfo.caseXkAno;
                            var caseLocation = caseInfo.caseLocation;
                            var caseDatetime = caseInfo.caseDatetime;
                            var caseBrief = caseInfo.caseBrief;
                            var caseType = caseInfo.caseType;
                            var caseProperty = caseInfo.caseProperty;

                            $("#caseName", "#caseinfo_form").val(caseName);
                            $("#caseXkAno", "#caseinfo_form").val(caseXkAno);

                            $("#caseLocation", "#caseinfo_form").val(caseLocation);
                            $("#caseDatetime", "#caseinfo_form").val(caseDatetime);
                            $("#caseBrief", "#caseinfo_form").val(caseBrief);
                            $("#caseType", "#caseinfo_form").val(caseType);
                            $("#caseProperty", "#caseinfo_form").val(caseProperty);
                        }

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

                                var newRowHtml = "<tr><td></td>";
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
                                newRowHtml += "<td><button name='editSampleBtn' class='btn btn-primary btn-xs' type='button'><i class='fa fa-pencil'></i> 修改</button>  <button name='deleteSampleBtn' class='btn btn-danger btn-xs' type='button'><i class='fa fa-trash-o'></i> 删除</button></td>";
                                newRowHtml += "<td style='display: none'><input type='hidden' name='sampleFlag' value='" + sampleFlag + "'/>" + sampleFlag + "</td>";
                                newRowHtml += "</tr>";
                                $("#sampleEvidenceTable").append(newRowHtml);

                                generateSampleEvidenceIdx();

                            });
                        }

                        $("button[name='editSampleBtn']", "#sampleEvidenceTable").on("click", function () {
                            EditSampleRow(this);
                        });

                        $("button[name='deleteSampleBtn']", "#sampleEvidenceTable").on("click", function () {
                            if (!confirm("确认删除吗")) {
                                return;
                            }
                            DelSampleRow(this);
                        });
                        //关闭弹出层
                        layer.closeAll();
                    }

                });

            });

            //输入完现勘号自动调取现勘数据
            $("input[name='caseXkNo']", "#caseinfo_form").blur(function () {
                //弹出层
                loading();

                var caseXkNo = $("#caseXkNo", "#caseinfo_form").val();

                var caseXkNoStatus = caseXkNo.indexOf("无") != -1;

                if (caseXkNo == "") {
                    alert("请输入现堪编号！");
                    layer.closeAll();
                    return;
                }
                if (caseXkNoStatus == true) {
                    layer.closeAll();
                    return;
                }

                $.ajax({
                    url: "<%=path%>/wt/reg/getCaseInfoXk.html?caseXkNo=" + caseXkNo,
                    type: "post",
                    contentType: "application/json; charset=utf-8",
                    dataType: "json",
                    success: function (data) {
                        var caseInfo = data.limsCaseInfo;
                        var consignment = data.limsConsignment;
                        var limsSampleInfoList = data.limsSampleInfoList;

                        if (consignment != "") {
                            var consignmentNo = consignment.consignmentNo;
                            $("#consignmentNo", "#consignment_form").val(consignmentNo);
                        }

                        if (caseInfo != "") {
                            var caseName = caseInfo.caseName;
                            var caseXkAno = caseInfo.caseXkAno;
                            var caseLocation = caseInfo.caseLocation;
                            var caseDatetime = caseInfo.caseDatetime;
                            var caseBrief = caseInfo.caseBrief;
                            var caseType = caseInfo.caseType;
                            var caseProperty = caseInfo.caseProperty;

                            $("#caseName", "#caseinfo_form").val(caseName);
                            $("#caseXkAno", "#caseinfo_form").val(caseXkAno);

                            $("#caseLocation", "#caseinfo_form").val(caseLocation);
                            $("#caseDatetime", "#caseinfo_form").val(caseDatetime);
                            $("#caseBrief", "#caseinfo_form").val(caseBrief);
                            $("#caseType", "#caseinfo_form").val(caseType);
                            $("#caseProperty", "#caseinfo_form").val(caseProperty);
                        }

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

                                var newRowHtml = "<tr><td></td>";
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
                                newRowHtml += "<td><button name='editSampleBtn' class='btn btn-primary btn-xs' type='button'><i class='fa fa-pencil'></i> 修改</button>  <button name='deleteSampleBtn' class='btn btn-danger btn-xs' type='button'><i class='fa fa-trash-o'></i> 删除</button></td>";
                                newRowHtml += "<td style='display: none'><input type='hidden' name='sampleFlag' value='" + sampleFlag + "'/>" + sampleFlag + "</td>";
                                newRowHtml += "</tr>";
                                $("#sampleEvidenceTable").append(newRowHtml);

                                generateSampleEvidenceIdx();

                            });
                        }

                        $("button[name='editSampleBtn']", "#sampleEvidenceTable").on("click", function () {
                            EditSampleRow(this);
                        });

                        $("button[name='deleteSampleBtn']", "#sampleEvidenceTable").on("click", function () {
                            if (!confirm("确认删除吗")) {
                                return;
                            }
                            DelSampleRow(this);
                        });
                        //关闭弹出层
                        layer.closeAll();
                    }

                });

            });

        });
        /* $.ajax({
         url: "http://10.8.41.15:8801/dataAssistant/getDatas?investigationNo="+caseXkNo+"&username="+xkLoginName+"$password="+xkLoginPassword,
         type: "get",
         success: function (data) {
         console.log(data);*/

        /* $.ajax({
         url: "
        <%=path%>/wt/reg/analyticalXk.html",
         type: "post",
         data: {'paramsXk': JSON.stringify(data)},
         dataType: "json",
         success: function (data) {
         var caseInfo = data.caseInfo;
         var limsSampleInfoList = data.limsSampleInfoList;

         if (caseInfo != "") {
         var caseName = caseInfo.caseName;
         var caseLocation = caseInfo.caseLocation;
         var caseDatetime = caseInfo.caseDatetime;
         var caseBrief = caseInfo.caseBrief;
         var caseType = caseInfo.caseType;

         $("#caseName", "#caseinfo_form").val(caseName);
         $("#caseLocation", "#caseinfo_form").val(caseLocation);
         $("#caseDatetime", "#caseinfo_form").val(caseDatetime);
         $("#caseBrief", "#caseinfo_form").val(caseBrief);
         $("#caseType", "#caseinfo_form").val(caseType);
         }

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
         newRowHtml += "<td><button name='editPerBtn' class='btn btn-primary btn-xs'><i class='fa fa-pencil'></i> 修改</button>  <button name='delPerBtn' class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i> 删除</button></td>";
         newRowHtml += "<td style='display: none'><input type='hidden' name='sampleFlag' value='" + sampleFlag + "'/>" + sampleFlag + "</td>";
         $("#sampleEvidenceTable").append("<tr>" + newRowHtml + "</tr>");

         generateSampleEvidenceIdx();

         $("button[name='editPerBtn']","#sampleEvidenceTable").on("click",function(){
         EditSampleRow(this);
         });
         $("button[name='delPerBtn']","#sampleEvidenceTable").on("click",function(){
         DelSampleRow(this);
         });

         });
         }
         },
         error: function (e) {
         alert(e);
         }
         });*/


        $("#nextBtn").on("click", function () {
            submitForm();
        });

        function submitForm() {

            if (!checkInputValidation()) {
                return;
            }

            var caseInfo = GetCaseInfo();
            var consignment = GetConsignment();
            var operateType = $("#operateType").val();
            var personInfoList = GetPerson();
            var sampleInfoList = GetSample();

            var params = {
                "caseInfo": caseInfo,
                "consignment": consignment,
                "personInfoList": personInfoList,
                "sampleInfoList": sampleInfoList
            };

            $.ajax({
                url: "<%=path%>/wt/reg/submitCase.html?operateType=" + operateType,
                type: "post",
                contentType: "application/json; charset=utf-8",
                data: JSON.stringify(params),
                dataType: "json",
                success: function (data) {
                    $("#regModal").modal('show');
                    $("#consignmentId").val(data.consignmentId)
                },
                error: function (e) {
                    alert("失败！");
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

        function GetCaseInfo() {
            var caseInfo = {};
            caseInfo.id = $("#caseId", "#caseinfo_form").val();
            caseInfo.caseXkNo = $("#caseXkNo", "#caseinfo_form").val();
            caseInfo.caseXkAno = $("#caseXkAno", "#caseinfo_form").val();
            caseInfo.caseName = $("#caseName", "#caseinfo_form").val();
            caseInfo.caseLocation = $("#caseLocation", "#caseinfo_form").val();
            caseInfo.caseDatetime = $("#caseDatetime", "#caseinfo_form").val();
            caseInfo.caseBrief = $("#caseBrief", "#caseinfo_form").val();
            caseInfo.caseType = $("#caseType", "#caseinfo_form").val();
            caseInfo.caseProperty = $("#caseProperty", "#caseinfo_form").val();
            caseInfo.caseLevel = $("#caseLevel", "#caseinfo_form").val();
            caseInfo.jiajiFlag = ($("#jiajiFlag", "#caseinfo_form").is(":checked") == true) ? "1" : "0";
            caseInfo.caseLevel = $("#caseLevel", "#caseinfo_form").val();
            caseInfo.caseSpecialRemark = $("#caseSpecialRemark", "#caseinfo_form").val();

            return caseInfo;
        }

        function GetConsignment() {
            var consignment = {};
            consignment.id = $("#consignmentId", "#consignment_form").val();
            consignment.consignmentNo = $("#consignmentNo", "#consignment_form").val();
            consignment.delegateOrgDesc = $("#delegateOrgDesc", "#consignment_form").val();
            consignment.delegator1 = $("#delegator1", "#consignment_form").val();
            consignment.delegator1Cno = $("#delegator1Cno", "#consignment_form").val();
            consignment.delegator1Phone = $("#delegator1Phone", "#consignment_form").val();
            consignment.delegator2 = $("#delegator2", "#consignment_form").val();
            consignment.delegator2Cno = $("#delegator2Cno", "#consignment_form").val();
            consignment.delegator2Phone = $("#delegator2Phone", "#consignment_form").val();
            consignment.identifyRequirement = $("#identifyRequirementList", "#consignment_form").val();
            consignment.matchCaseNo = $("#matchCaseNo", "#consignment_form").val();
            consignment.identifyRequirementOther = $("#identifyRequirementOther", "#consignment_form").val();
            consignment.preIdentifyDesc = $("#preIdentifyDesc", "#consignment_form").val();
            consignment.reidentifyReason = $("#reidentifyReason", "#consignment_form").val();
            consignment.additionalFlag = $("#additionalFlag", "#consignment_form").val();

            consignment.delegator1Position = $("#delegator1Position", "#consignment_form").val();
            consignment.delegator2Position = $("#delegator2Position", "#consignment_form").val();
            consignment.delegator1Cname = $("#delegator1Cname", "#consignment_form").val();
            consignment.delegator2Cname = $("#delegator2Cname", "#consignment_form").val();
            consignment.postalAddress = $("#postalAddress", "#consignment_form").val();
            consignment.postNo = $("#postNo", "#consignment_form").val();
            consignment.faxNo = $("#faxNo", "#consignment_form").val();
            consignment.identifyKernelName = $("#identifyKernelName", "#consignment_form").val();

            return consignment;
        }

        function GetPerson() {
            var personArr = new Array();
            var $personTR = $("tr", "#personInfoTable").not(".regedTr");
            var personCnt = $personTR.length;
            var person;
            for (var i = 0; i < personCnt; i++) {
                person = {};
                person.id = $("input[name='personId']", $personTR.get(i)).val();
                person.personType = $("input[name='personType']", $personTR.get(i)).val();
                person.personName = $("input[name='personName']", $personTR.get(i)).val();
                person.personGender = $("input[name='personGender']", $personTR.get(i)).val();
                person.personIdcardNo = $("input[name='personIdcardNo']", $personTR.get(i)).val();
                person.relativeIdentify = $("input[name='relativeIdentify']", $personTR.get(i)).val();

                personArr.push(person);
            }
            return personArr;
        }

        function GetSample() {
            var sampleArr = new Array();
            var $samplePersonTR = $("tr", "#samplePersonTable").not(".regedTr");
            var $sampleEvidenceTR = $("tr", "#sampleEvidenceTable").not(".regedTr");
            var sampleCnt = $samplePersonTR.length;
            var sampleEvidenceCnt = $sampleEvidenceTR.length;
            var sample;
            var sampleEvidence;
            for (var i = 0; i < sampleCnt; i++) {
                sample = {};
                sample.id = $("input[name='sampleId']", $samplePersonTR.get(i)).val();
                sample.sampleName = $("input[name='sampleName']", $samplePersonTR.get(i)).val();
                sample.extractDatetime = $("input[name='extractDatetime']", $samplePersonTR.get(i)).val();
                sample.extractPerson = $("input[name='extractPerson']", $samplePersonTR.get(i)).val();
                sample.sampleDesc = $("input[name='sampleDesc']", $samplePersonTR.get(i)).val();
                sample.sampleFlag = $("input[name='sampleFlag']", $samplePersonTR.get(i)).val();
                sample.sampleType = $("input[name='sampleType']", $samplePersonTR.get(i)).val();
                sample.sampleTypeName = $("input[name='sampleTypeName']", $samplePersonTR.get(i)).val();
                sample.samplePacking = $("input[name='samplePacking']", $samplePersonTR.get(i)).val();
                sample.sampleProperties = $("input[name='sampleProperties']", $samplePersonTR.get(i)).val();
                sample.refPersonId = $("input[name='refPersonId']", $samplePersonTR.get(i)).val();
                sample.refPersonName = $("input[name='refPersonName']", $samplePersonTR.get(i)).val();

                sampleArr.push(sample);
            }

            for (var i = 0; i < sampleEvidenceCnt; i++) {
                sampleEvidence = {};
                sampleEvidence.id = $("input[name='sampleId']", $sampleEvidenceTR.get(i)).val();
                sampleEvidence.evidenceNo = $("input[name='evidenceNo']", $sampleEvidenceTR.get(i)).val();
                sampleEvidence.sampleName = $("input[name='sampleName']", $sampleEvidenceTR.get(i)).val();
                sampleEvidence.extractDatetime = $("input[name='extractDatetime']", $sampleEvidenceTR.get(i)).val();
                sampleEvidence.extractPerson = $("input[name='extractPerson']", $sampleEvidenceTR.get(i)).val();
                sampleEvidence.sampleDesc = $("input[name='sampleDesc']", $sampleEvidenceTR.get(i)).val();
                sampleEvidence.sampleFlag = $("input[name='sampleFlag']", $sampleEvidenceTR.get(i)).val();
                sampleEvidence.otherPropertiesDesc = $("input[name='otherPropertiesDesc']", $sampleEvidenceTR.get(i)).val();
                sampleEvidence.sampleType = $("input[name='sampleType']", $sampleEvidenceTR.get(i)).val();
                sampleEvidence.sampleTypeName = $("input[name='sampleTypeName']", $sampleEvidenceTR.get(i)).val();
                sampleEvidence.samplePacking = $("input[name='samplePacking']", $sampleEvidenceTR.get(i)).val();
                sampleEvidence.sampleProperties = $("input[name='sampleProperties']", $sampleEvidenceTR.get(i)).val();
                sampleEvidence.refPersonId = $("input[name='refPersonId']", $sampleEvidenceTR.get(i)).val();
                sampleEvidence.refPersonName = $("input[name='refPersonName']", $sampleEvidenceTR.get(i)).val();

                sampleArr.push(sampleEvidence);
            }

            return sampleArr;
        }

        /*sample*/
        generatePersonIdx();

        function generatePersonIdx() {
            $("tr", "#personInfoTable").each(function () {
                $("td:first", $(this)).text($(this).index() + 1);
            });
        }

        generateSamplePersonIdx();

        function generateSamplePersonIdx() {
            $("tr", "#samplePersonTable").each(function () {
                $("td:first", $(this)).text($(this).index() + 1);
            });
        }

        generateSampleEvidenceIdx();

        function generateSampleEvidenceIdx() {
            $("tr", "#sampleEvidenceTable").each(function () {
                $("td:first", $(this)).text($(this).index() + 1);
            });

        }

        getMatchCaseNo();

        function getMatchCaseNo() {
            var matchSample = $("#matchCaseNo").val();
            if (matchSample != null && matchSample != "")
                $("#matchCaseNo").show();
        }

        $(function () {

            $("#delegator1", "#consignment_form").on("change", function () {

                var delegateOrgId = $("#delegateOrgId").val();
                var delegatorName = $("#delegator1 option:selected").val();

                if (delegatorName == "") {
                    $("#delegator1Position").val("");
                    $("#delegator1Cname").val("");
                    $("#delegator1Cno").val("");
                    $("#delegator1Phone").val("");
                }

                $.ajax({
                    url: "<%=path%>/center/7/selectDelagatorQuery.html?delegatorName=" + delegatorName,
                    type: "post",
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    success: function (data) {
                        if (data.length > 0) {
                            var dataLen = data.length;
                            for (var i = 0; i < dataLen; i++) {
                                $("#delegator1Position").val(data[i].delegatorDutyName);
                                $("#delegator1Cname").val(data[i].delegatorCertificateName);
                                $("#delegator1Cno").val(data[i].delegatorCertificateNo);
                                $("#delegator1Phone").val(data[i].delegatorPhone);
                            }
                        }
                    },
                    error: function (data, e) {
                        alert("查询失败!");
                    }
                });
            });

            $("#delegator2", "#consignment_form").on("change", function () {

                //var delegateOrgId = $("#delegateOrgId").val();
                var delegatorName = $("#delegator2 option:selected").val();

                if (delegatorName == "") {
                    $("#delegator2Position").val("");
                    $("#delegator2Cname").val("");
                    $("#delegator2Cno").val("");
                    $("#delegator2Phone").val("");
                }

                $.ajax({
                    url: "<%=path%>/center/7/selectDelagatorQuery.html?delegatorName=" + delegatorName,
                    type: "post",
                    dataType: "json",
                    contentType: "application/json; charset=utf-8",
                    success: function (data) {
                        if (data.length > 0) {
                            var dataLen = data.length;
                            for (var i = 0; i < dataLen; i++) {
                                $("#delegator2Position").val(data[i].delegatorDutyName);
                                $("#delegator2Cname").val(data[i].delegatorCertificateName);
                                $("#delegator2Cno").val(data[i].delegatorCertificateNo);
                                $("#delegator2Phone").val(data[i].delegatorPhone);
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


            $("select[name='sampleProperties']", "#sampleModal").on("change", function () {
                var selectedVal = $("option:selected", $(this)).val();
                if (selectedVal == "9999") {
                    $("input[name='otherPropertiesDesc']", "#sampleModal").removeClass("hide");
                    $("input[name='otherPropertiesDesc']", "#sampleModal").focus();
                } else {
                    $("input[name='otherPropertiesDesc']", "#sampleModal").addClass("hide");
                    $("input[name='otherPropertiesDesc']", "#sampleModal").focus();
                }
            });


            $("#showRegedTrBtn").on("click", function () {
                if ($("#hiddenSpan").hasClass("hide")) {
                    $("tr.regedTr", "#samplePersonTable").removeClass("hide");
                    $("tr.regedTr", "#sampleEvidenceTable").removeClass("hide");

                    $("#hiddenSpan").removeClass("hide");
                    $("#showSpan").addClass("hide");
                } else {
                    $("tr.regedTr", "#samplePersonTable").addClass("hide");
                    $("tr.regedTr", "#sampleEvidenceTable").addClass("hide");

                    $("#hiddenSpan").addClass("hide");
                    $("#showSpan").removeClass("hide");
                }
            });


            $("#noIdcardRemarkCkb", "#personModal").on("click", function () {
                if ($(this).is(":checked")) {
                    $("input[name='personIdcardNo']", "#personModal").val("无");
                    $("input[name='personIdcardNo']", "#personModal").prop("readonly", "readonly");

                    $("input[name='noIdcardRemark']", "#personModal").prop("readonly", false);
                    $("input[name='noIdcardRemark']", "#personModal").val("");
                    $("input[name='noIdcardRemark']", "#personModal").focus();
                } else {
                    $("input[name='personIdcardNo']", "#personModal").val("");
                    $("input[name='personIdcardNo']", "#personModal").prop("readonly", false);
                    $("input[name='personIdcardNo']", "#personModal").focus();

                    $("input[name='noIdcardRemark']", "#personModal").prop("readonly", "readonly");
                    $("input[name='noIdcardRemark']", "#personModal").val("");
                }
            });

            $("button[name='viewPhotoBtn']", "#sampleEvidenceTable").on("click", function () {
                var sampleId = $("input[name='sampleId']", $(this).parent()).val();
                var url = "<%=path%>/center/caseinfo/viewPhoto.html?sampleId=" + sampleId;

                $("#viewPhoto").attr("src", url);
                $("#viewPhotoModel").modal('show');

            });

            $("button[name='viewPhotoBtn']", "#samplePersonTable").on("click", function () {
                var sampleId = $("input[name='sampleId']", $(this).parent()).val();
                var url = "<%=path%>/center/caseinfo/viewPhoto.html?sampleId=" + sampleId;

                $("#viewPhoto").attr("src", url);
                $("#viewPhotoModel").modal('show');

            });

            $("button[name='uploadPhotoBtn']", "#sampleEvidenceTable").on("click", function () {
                EditPhotoRow(this);
            });

            $("button[name='uploadPhotoBtn']", "#samplePersonTable").on("click", function () {
                EditPhotoRow(this);
            });

            function EditPhotoRow(obj) {
                var $curTR = $(obj).parents("tr");
                var sampleId = $("input[name='sampleId']", $curTR).val();

                newPhotoRow(sampleId);
            }

            function newPhotoRow(sampleId) {
                $("input[name='sampleIdModel']", "#photoModal").val(sampleId);

                $("#photoModal").modal('show');
            }

            $("#browserBtn").on("click", function () {
                $("#photoFile").click();
            });

            $("#photoFile").on("change", function () {
                $("#photoFileTxt").val($(this).val());
            });

            $("#uploadBtn").on("click", function () {
                var fileValue = $("#photoFileTxt").val();

                if (fileValue == "") {
                    alert("请选择上传文件!");
                    return false;
                }

                uploadFtn();

            });

            function uploadFtn() {

                var sampleId = $("#sampleIdModel").val();

                $.ajaxFileUpload({
                    cache: false,
                    url: "<%=path%>/center/caseinfo/uploadPhoto.html?sampleId=" + sampleId,
                    secureuri: false,
                    fileElementId: 'photoFile',
                    dataType: 'json',
                    success: function (data) {
                        if (data.success || data.success == true || data.success == "true") {
                            alert("上传成功!");
                            var consignmentId = $("#consignmentId", "#consignment_form").val();
                            location.href = "<%=path%>/center/caseinfo/acceptCase.html?consignmentId=" + consignmentId;
                        } else {
                            alert("上传失败！");
                        }
                    },
                    error: function (data, status, e) {
                        alert("上传失败！");
                    }
                });

                return true;
            }
        });

        $("#CirculationRecordDocBtn").on("click", function () {
            var caseId = $("#caseId", "#caseinfo_form").val();
            var consignmentId = $("#consignmentId", "#consignment_form").val();

            location.href = "../../center/caseinfo/CirculationRecordDoc.html?caseId=" + caseId + "&consignmentId=" + consignmentId;
        });

        $("#downloadIdentifyApproveFormDocBtn").on("click", function () {
            var caseId = $("#caseId", "#caseinfo_form").val();
            location.href = "<%=path%>/center/6/identifyApprovalFrom.html?caseId=" + caseId;
        });

        $("#downloadExcelBtn").on("click", function () {
            var caseId = $("#caseId", "#caseinfo_form").val();
            location.href = "<%=path%>/center/6/downloadSampleExcel.html?caseId=" + caseId;
        });
    });

    /*----sample----*/

    generatePersonIdx();

    function generatePersonIdx() {
        $("tr", "#personInfoTable").each(function () {
            $("td:first", $(this)).text($(this).index() + 1);
        });
    }

    generateSamplePersonIdx();

    function generateSamplePersonIdx() {
        $("tr", "#samplePersonTable").each(function () {
            $("td:first", $(this)).text($(this).index() + 1);
        });
    }

    generateSampleEvidenceIdx();

    function generateSampleEvidenceIdx() {
        $("tr", "#sampleEvidenceTable").each(function () {
            $("td:first", $(this)).text($(this).index() + 1);
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

        $("#regModal").on('hidden.bs.modal', function () {
            location.href = "<%=path%>/wt/caseinfo/listPending.html";
        });

        $("select[name='refPerson']", "#sampleModal").on("change", function () {
            var refPersonName = $("option:selected", $(this)).val();
            var sampleName = $("input[name='sampleName']", "#sampleModal").val();

            var refPersonNameStr = $("input[name='refPersonName'][value='" + refPersonName + "']", "#samplePersonTable");
            if (refPersonNameStr.length > 0) {
                if (confirm("此人员已存在，是否继续添加！")) {
                    if (refPersonName != "" && sampleName == "") {
                        $("input[name='sampleName']", "#sampleModal").val(refPersonName);
                    }
                } else {
                    $("select[name='refPerson']", "#sampleModal").val(sampleName);
                }
            } else {
                if (refPersonName != "" && sampleName == "") {
                    $("input[name='sampleName']", "#sampleModal").val(refPersonName);
                }
            }
            $("input[name='sampleName']", "#sampleModal").focus();
        });

        $("select[name='sampleProperties']", "#sampleModal").on("change", function () {
            var selectedVal = $("option:selected", $(this)).val();
            if (selectedVal == "9999") {
                $("input[name='otherPropertiesDesc']", "#sampleModal").removeClass("hide");
                $("input[name='otherPropertiesDesc']", "#sampleModal").focus();
            } else {
                $("input[name='otherPropertiesDesc']", "#sampleModal").addClass("hide");
                $("input[name='otherPropertiesDesc']", "#sampleModal").focus();
            }
        });

        $("#noIdcardRemarkCkb", "#personModal").on("click", function () {
            if ($(this).is(":checked")) {
                $("input[name='personIdcardNo']", "#personModal").val("无");
                $("input[name='personIdcardNo']", "#personModal").prop("readonly", "readonly");

                $("input[name='noIdcardRemark']", "#personModal").prop("readonly", false);
                $("input[name='noIdcardRemark']", "#personModal").val("");
                $("input[name='noIdcardRemark']", "#personModal").focus();
            } else {
                $("input[name='personIdcardNo']", "#personModal").val("");
                $("input[name='personIdcardNo']", "#personModal").prop("readonly", false);
                $("input[name='personIdcardNo']", "#personModal").focus();

                $("input[name='noIdcardRemark']", "#personModal").prop("readonly", "readonly");
                $("input[name='noIdcardRemark']", "#personModal").val("");
            }
        });

        $("button[name='downloadDocBtn']", "#regModal").on("click", function () {
            var consignmentId = $("#consignmentId").val();
            location.href = "<%=path%>/wt/caseinfo/delegateDoc.html?consignmentId=" + consignmentId;
        });

        function checkSampleInputValidation() {

            var sampleTRLen = $("tr", "#samplePersonTable").length + $("tr", "#sampleEvidenceTable").length;
            if (sampleTRLen == 0) {
                alert("请添加案件检材信息！");
                return false;
            }

            return true;
        }

        $("#submitBtn").on("click", function () {

            if (!checkSampleInputValidation()) {
                return;
            }

            $("#regModal").modal('show');
        });
    });

    $("#regModal").on('hidden.bs.modal', function () {
        location.href = "<%=path%>/wt/caseinfo/listPending.html";
    });

    function GetPerson() {
        var personArr = new Array();

        var $personTR = $("tr", "#personInfoTable").not(".regedTr");
        var personCnt = $personTR.length;
        var person;
        for (var i = 0; i < personCnt; i++) {
            person = {};
            person.caseId = $("#caseId").val();
            person.consignmentId = $("#consignmentId").val();
            person.id = $("input[name='personId']", $personTR.get(i)).val();
            person.personType = $("input[name='personType']", $personTR.get(i)).val();
            person.personName = $("input[name='personName']", $personTR.get(i)).val();
            person.personGender = $("input[name='personGender']", $personTR.get(i)).val();
            person.personIdcardNo = $("input[name='personIdcardNo']", $personTR.get(i)).val();
            person.noIdcardRemark = $("input[name='noIdcardRemark']", $personTR.get(i)).val();
            person.relativeIdentify = $("input[name='relativeIdentify']", $personTR.get(i)).val();

            personArr.push(person);
        }

        return personArr;
    }

    function checkSampleInputValidation() {

        var sampleTRLen = $("tr", "#samplePersonTable").length + $("tr", "#sampleEvidenceTable").length;
        if (sampleTRLen == 0) {
            alert("请添加案件检材信息！");
            return false;
        }

        return true;
    }

    // person start
    function newPersonRow(person, operateType, rownum) {
        if (person.personType == "") {
            $("select[name='personType']", "#personModal").prop('selectedIndex', 0);
        } else {
            $("select[name='personType']", "#personModal").val(person.personType);
        }

        $("input[name='personName']", "#personModal").val(person.personName);

        if (person.personGender == "") {
            $("select[name='personGender']", "#personModal").prop('selectedIndex', 0);
        } else {
            $("select[name='personGender']", "#personModal").val(person.personGender);
        }

        $("input[name='personIdcardNo']", "#personModal").val(person.personIdcardNo);
        if (person.noIdcardRemark != "") {
            $("input[name='personIdcardNo']", "#personModal").val("无");
            $("input[name='personIdcardNo']", "#personModal").prop("readonly", "readonly");

            $("#noIdcardRemarkCkb", "#personModal").prop("checked", "checked");
            $("input[name='noIdcardRemark']", "#personModal").val(person.noIdcardRemark);
            $("input[name='noIdcardRemark']", "#personModal").prop("readonly", false);
        } else {
            $("input[name='personIdcardNo']", "#personModal").val(person.personIdcardNo);
            $("input[name='personIdcardNo']", "#personModal").prop("readonly", false);

            $("#noIdcardRemarkCkb", "#personModal").prop("checked", false);
            $("input[name='noIdcardRemark']", "#personModal").val("");
            $("input[name='noIdcardRemark']", "#personModal").prop("readonly", "readonly");
        }

        if (person.relativeIdentify == "") {
            $("select[name='relativeIdentify']", "#personModal").val("08");
        } else {
            $("select[name='relativeIdentify']", "#personModal").val(person.relativeIdentify);
        }

        $("input[name='personId']", "#personModal").val(person.id);
        $("input[name='personOperateType']", "#personModal").val(operateType);
        $("input[name='personTableRownum']", "#personModal").val(rownum);
        $("#personModal").modal();
    }

    function AddPersonRow() {
        var person = {};
        person.personType = "";
        person.personName = "";
        person.personGender = "";
        person.personIdcardNo = "";
        person.noIdcardRemark = "";
        person.relativeIdentify = "";
        person.id = "";

        newPersonRow(person, "add", 0);
    }

    function EditPersonRow(obj) {
        var $curTR = $(obj).parents("tr");
        var person = {};
        person.personType = $("input[name='personType']", $curTR).val();
        person.personName = $("input[name='personName']", $curTR).val();
        person.personGender = $("input[name='personGender']", $curTR).val();
        person.personIdcardNo = $("input[name='personIdcardNo']", $curTR).val();
        person.noIdcardRemark = $("input[name='noIdcardRemark']", $curTR).val();
        person.relativeIdentify = $("input[name='relativeIdentify']", $curTR).val();
        person.id = $("input[name='personId']", $curTR).val();

        var trIdx = $curTR.index();
        newPersonRow(person, "edit", trIdx);
    }

    function DelPersonRow(obj) {
        var personId = $("input[name='personId']", $(obj).parent()).val();
        if (personId == null || personId == "" || personId == 0 || personId == "0") {
            $(obj).parents("tr").remove();
            return;
        } else {
            deletePerson(personId, obj);
        }

    }

    function SavePersonRow() {
        var errorCnt = 0;
        $(".required", "#personModal").each(function () {
            if ($(this).val() == "") {
                $("div.has-error", $(this).parents("div.form-group")).removeClass("hide");
                errorCnt++;
            } else {
                $("div.has-error", $(this).parents("div.form-group")).addClass("hide");
            }
        });

        var personTypeVal = $("select[name='personType']", "#personModal").find("option:selected").val();
        if (personTypeVal == "" || $.trim(personTypeVal) == "") {
            $("div.has-error", $("select[name='personType']", "#personModal").parents("div.form-group")).removeClass("hide");
            errorCnt++;
        } else {
            $("div.has-error", $("select[name='personType']", "#personModal").parents("div.form-group")).addClass("hide");
        }

        var relativeIdentifyVal = $("select[name='relativeIdentify']", "#personModal").find("option:selected").val();
        if (relativeIdentifyVal == "" || $.trim(relativeIdentifyVal) == "") {
            $("div.has-error", $("select[name='relativeIdentify']", "#personModal").parents("div.form-group")).removeClass("hide");
            errorCnt++;
        } else {
            $("div.has-error", $("select[name='relativeIdentify']", "#personModal").parents("div.form-group")).addClass("hide");
        }

        if (errorCnt > 0) {
            return;
        }

        var noIdcardRemarkVal = "";
        if ($("#noIdcardRemarkCkb", "#personModal").is(":checked")) {
            noIdcardRemarkVal = $("input[name='noIdcardRemark']", "#personModal").val();

            if (noIdcardRemarkVal == "" || $.trim(noIdcardRemarkVal) == "") {
                $("div.has-error", $("input[name='personIdcardNo']", "#personModal").parents("div.form-group")).removeClass("hide");
                return;
            } else {
                $("div.has-error", $("input[name='personIdcardNo']", "#personModal").parents("div.form-group")).addClass("hide");
            }
        } else {
            var idcardNo = $("input[name='personIdcardNo']", "#personModal").val();
            var isValid = true;
            var checkMsg = "";
            $.ajax({
                url: "../checkIdcard.html?idcardNo=" + idcardNo,
                type: "get",
                async: false,
                cache: false,
                dataTyp: "json",
                success: function (data) {
                    if (!data.success && data.success != "true") {
                        isValid = false;
                        checkMsg = data.errorMsg;
                    }
                }
            });

            if (!isValid) {
                $("div.has-error", $("input[name='personIdcardNo']", "#personModal").parents("div.form-group")).removeClass("hide");
                $("div.has-error", $("input[name='personIdcardNo']", "#personModal").parents("div.form-group")).html("<p class='help-block'>" + checkMsg + "</p>");
                return;
            } else {
                $("div.has-error", $("input[name='personIdcardNo']", "#personModal").parents("div.form-group")).addClass("hide");
                $("div.has-error", $("input[name='personIdcardNo']", "#personModal").parents("div.form-group")).html("<p class='help-block'>必填项</p>");
            }
        }

        var personId = $("input[name='personId']", "#personModal").val();
        var personTypeCode = $("select[name='personType']", "#personModal").find("option:selected").val();
        var personTypeName = $("select[name='personType']", "#personModal").find("option:selected").text();
        var personName = $("input[name='personName']", "#personModal").val();
        var personGenderCode = $("select[name='personGender']", "#personModal").find("option:selected").val();
        var personGenderName = $("select[name='personGender']", "#personModal").find("option:selected").text();
        var personIdcardNo = $("input[name='personIdcardNo']", "#personModal").val();
        var relativeIdentifyCode = $("select[name='relativeIdentify']", "#personModal").find("option:selected").val();
        var relativeIdentifyName = $("select[name='relativeIdentify']", "#personModal").find("option:selected").text();

        var newRowHtml = "<td></td>";
        newRowHtml += "<td><input type='hidden' name='personType' value='" + personTypeCode + "'/>" + personTypeName + "</td>";
        newRowHtml += "<td><input type='hidden' name='personName' value='" + personName + "'/>" + personName + "</td>";
        newRowHtml += "<td><input type='hidden' name='personGender' value='" + personGenderCode + "'/>" + personGenderName + "</td>";
        newRowHtml += "<td><input type='hidden' name='personIdcardNo' value='" + personIdcardNo + "'/><input type='hidden' name='noIdcardRemark' value='" + noIdcardRemarkVal + "'/>" + personIdcardNo;
        if ($("#noIdcardRemarkCkb", "#personModal").is(":checked")) {
            newRowHtml += "（" + noIdcardRemarkVal + "）";
        }
        newRowHtml += "</td>";

        newRowHtml += "<td><input type='hidden' name='relativeIdentify' value='" + relativeIdentifyCode + "'/>" + relativeIdentifyName + "</td>";
        newRowHtml += "<td><input type='hidden' name='personId' value='" + personId + "'/><button name='editPerBtn' class='btn btn-primary btn-xs'><i class='fa fa-pencil'></i> 修改</button>  <button name='delPerSamBtn' class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i> 删除</button></td>";


        var operateType = $("input[name='personOperateType']", "#personModal").val();
        if ("add" == operateType) {
            $("#personInfoTable").append("<tr>" + newRowHtml + "</tr>");
        } else {
            var trIdx = $("input[name='personTableRownum']", "#personModal").val();
            $("tr:eq(" + trIdx + ")", "#personInfoTable").html(newRowHtml);
        }
        generatePersonIdx();

        $("button[name='editPerBtn']", "#personInfoTable").on("click", function () {
            EditPersonRow(this);
        });
        $("button[name='delPerSamBtn']", "#personInfoTable").on("click", function () {
            DelPersonRow(this);
        });

        $("#personModal").modal('hide');

    }
    // person end

    // sample start
    function newSampleRow(sample, operateType, rownum) {

        $("div.has-error", "#sampleModal").addClass("hide");

        $("input[name='sampleId']", "#sampleModal").val(sample.id);
        $("select[name='sampleType']", "#sampleModal").val(sample.sampleType);
        $("input[name='evidenceNo']", "#sampleModal").val(sample.evidenceNo);
        $("input[name='sampleName']", "#sampleModal").val(sample.sampleName);
        $("input[name='extractDatetime']", "#sampleModal").val(sample.extractDatetime);
        $("input[name='extractPerson']", "#sampleModal").val(sample.extractPerson);
        $("input[name='sampleDesc']", "#sampleModal").val(sample.sampleDesc);
        if (sample.samplePacking == "") {
            $("input[name='samplePacking']", "#sampleModal").val("物证袋");
        } else {
            $("input[name='samplePacking']", "#sampleModal").val(sample.samplePacking);
        }
        if (sample.sampleProperties != "") {
            $("select[name='sampleProperties']", "#sampleModal").val(sample.sampleProperties);

            if (sample.sampleProperties == "9999") {
                $("input[name='otherPropertiesDesc']", "#sampleModal").val(sample.otherPropertiesDesc);
                $("input[name='otherPropertiesDesc']", "#sampleModal").removeClass("hide");
            } else {
                $("input[name='otherPropertiesDesc']", "#sampleModal").val("");
                $("input[name='otherPropertiesDesc']", "#sampleModal").addClass("hide");
            }
        } else {
            $("select[name='sampleProperties']", "#sampleModal").prop('selectedIndex', 0);

            $("input[name='otherPropertiesDesc']", "#sampleModal").val("");
            $("input[name='otherPropertiesDesc']", "#sampleModal").addClass("hide");
        }

        $("select[name='refPerson']", "#sampleModal").empty();
        $("select[name='refPerson']", "#sampleModal").append("<option value=''>请选择</option>")
        $("input[name='personName']", "#personInfoTable").each(function () {
            var refPersonName = $(this).val();
            if (operateType == "edit" && refPersonName == sample.refPersonName) {
                $("select[name='refPerson']", "#sampleModal").append("<option value='" + refPersonName + "' selected>" + refPersonName + " </option>");

            } else {
                $("select[name='refPerson']", "#sampleModal").append("<option value='" + refPersonName + "'>" + refPersonName + "</option>");
            }
        });

        if (sample.sampleFlag == "" || sample.sampleFlag == "0") {
            $("input[name='sampleFlag']", "#sampleModal").val(sample.sampleFlag);
            $("#divRefPerson").hide();
        } else {
            $("input[name='sampleFlag']", "#sampleModal").val(sample.sampleFlag);
            $("#divRefPerson").show();
        }

        $("input[name='sampleOperateType']", "#sampleModal").val(operateType);
        $("input[name='sampleTableRownum']", "#sampleModal").val(rownum);
        $("#sampleModal").modal();
    }

    function AddSampleRow(obj) {
        var sample = {};
        sample.id = "";
        sample.sampleType = "";
        sample.evidenceNo = "";
        sample.sampleName = "";
        sample.extractDatetime = "";
        sample.extractPerson = "";
        sample.sampleDesc = "";
        sample.samplePacking = "";
        sample.sampleProperties = "";
        sample.otherPropertiesDesc = "";
        sample.refPersonId = "";
        sample.refPersonName = "";
        sample.sampleFlag = obj;
        newSampleRow(sample, "add", 0);
    }

    function EditSampleRow(obj) {
        var $curTR = $(obj).parents("tr");
        var sample = {};
        sample.id = $("input[name='sampleId']", $curTR).val();
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
        newSampleRow(sample, "edit", trIdx);
    }

    function DelSampleRow(obj) {

        var sampleId = $("input[name='sampleId']", $(obj).parent()).val();
        if (sampleId == null || sampleId == "" || sampleId == 0 || sampleId == "0") {
            $(obj).parents("tr").remove();
            return;
        } else {
            deleteSample(sampleId, obj);
        }
    }


    function SaveSampleRow() {
        var errorCnt = 0;

        $(".required", "#sampleModal").each(function () {
            if ($(this).val() == "") {
                $("div.has-error", $(this).parents("div.form-group")).removeClass("hide");
                errorCnt++;
            } else {

                $("div.has-error", $(this).parents("div.form-group")).addClass("hide");
            }
        });

        var sampleTypeVal = $("select[name='sampleType']", "#sampleModal").find("option:selected").val();
        if (sampleTypeVal == "" || $.trim(sampleTypeVal) == "") {
            $("div.has-error", $("select[name='sampleType']", "#sampleModal").parents("div.form-group")).removeClass("hide");
            errorCnt++;
        } else {
            $("div.has-error", $("select[name='sampleType']", "#sampleModal").parents("div.form-group")).addClass("hide");
        }

        var sampleFlagVal = $("input[name='sampleFlag']", "#sampleModal").val();

        if (sampleFlagVal == "1") {
            var refPeresonVal = $("select[name='refPerson']", "#sampleModal").find("option:selected").val();
            if (refPeresonVal == "" || $.trim(refPeresonVal) == "") {
                $("div.has-error", $("select[name='refPerson']", "#sampleModal").parents("div.form-group")).removeClass("hide");
                errorCnt++;
            } else {
                $("div.has-error", $("select[name='refPerson']", "#sampleModal").parents("div.form-group")).addClass("hide");
            }
        }

        var samplePropertiesVal = $("select[name='sampleProperties']", "#sampleModal").find("option:selected").val();
        var otherPropertiesDescVal = $("input[name='otherPropertiesDesc']", "#sampleModal").val();

        if (samplePropertiesVal == "9999") {
            if (otherPropertiesDescVal == "" || $.trim(otherPropertiesDescVal) == "") {
                $("div.has-error", $("input[name='otherPropertiesDesc']", "#sampleModal").parents("div.form-group")).removeClass("hide");
            } else {
                $("div.has-error", $("input[name='otherPropertiesDesc']", "#sampleModal").parents("div.form-group")).addClass("hide");
            }
        }


        if (errorCnt > 0) {
            return;
        }

        var sampleId = $("input[name='sampleId']", "#sampleModal").val();
        var sampleTypeCode = $("select[name='sampleType']", "#sampleModal").find("option:selected").val();

        var sampleTypeName = $("select[name='sampleType']", "#sampleModal").find("option:selected").text();
        var sampleName = $("input[name='sampleName']", "#sampleModal").val();
        var evidenceNo = $("input[name='evidenceNo']", "#sampleModal").val();
        var extractDatetime = $("input[name='extractDatetime']", "#sampleModal").val();
        var extractPerson = $("input[name='extractPerson']", "#sampleModal").val();
        var sampleDesc = $("input[name='sampleDesc']", "#sampleModal").val();
        var samplePacking = $("input[name='samplePacking']", "#sampleModal").val();
        var samplePropertiesName = $("select[name='sampleProperties']", "#sampleModal").find("option:selected").text();
        //var refPersonCode = $("select[name='refPerson']", "#sampleModal").find("option:selected").val();
        var refPersonName = $("select[name='refPerson']", "#sampleModal").find("option:selected").text();

        var newRowHtml = "<td></td>";
        if (sampleFlagVal != 1) {
            newRowHtml += "<td><input type='hidden' name='evidenceNo' value='" + $.trim(evidenceNo) + "'/>" + evidenceNo + "</td>";
        }
        newRowHtml += "<td><input type='hidden' name='sampleName' value='" + $.trim(sampleName) + "'/>" + sampleName + "</td>";
        newRowHtml += "<td><input type='hidden' name='sampleType' value='" + $.trim(sampleTypeCode) + "'/>" + sampleTypeName + "</td>";
        newRowHtml += "<td><input type='hidden' name='sampleDesc' value='" + $.trim(sampleDesc) + "'/>" + sampleDesc + "</td>";
        newRowHtml += "<td><input type='hidden' name='samplePacking' value='" + $.trim(samplePacking) + "'/>" + samplePacking + "</td>";
        newRowHtml += "<td><input type='hidden' name='sampleProperties' value='" + samplePropertiesVal + "'/><input type='hidden' name='otherPropertiesDesc' value='" + $.trim(otherPropertiesDescVal) + "'/>";
        if (samplePropertiesVal == "9999") {
            newRowHtml += otherPropertiesDescVal + "</td>";
        } else {
            newRowHtml += samplePropertiesName + "</td>";
        }

        if (sampleFlagVal == 1) {
            newRowHtml += "<td><input type='hidden' name='refPersonName' value='" + refPersonName + "'/>" + refPersonName + "</td>";
        }
        newRowHtml += "<td><input type='hidden' name='extractDatetime' value='" + extractDatetime + "'/>" + extractDatetime + "</td>";
        newRowHtml += "<td><input type='hidden' name='extractPerson' value='" + extractPerson + "'/>" + extractPerson + "</td>";
        newRowHtml += "<td><input type='hidden' name='sampleId' value='" + sampleId + "'/><input type='hidden' name='sampleFlag' type='button' value='" + sampleFlagVal + "'/><button name='editSampleBtn' type='button' class='btn btn-primary btn-xs'><i class='fa fa-pencil'></i> 修改</button>  <button name='delSampleInfoBtn' type='button' class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i> 删除</button></td>";

        var operateType = $("input[name='sampleOperateType']", "#sampleModal").val();
        if ("add" == operateType) {
            if (sampleFlagVal == 1) {
                $("#samplePersonTable").append("<tr>" + newRowHtml + "</tr>");
                generateSamplePersonIdx();
            } else {
                $("#sampleEvidenceTable").append("<tr>" + newRowHtml + "</tr>");
                generateSampleEvidenceIdx();
            }
        } else {
            var trIdx = $("input[name='sampleTableRownum']", "#sampleModal").val();
            if (sampleFlagVal == 1) {
                $("tr:eq(" + trIdx + ")", "#samplePersonTable").html(newRowHtml);
                generateSamplePersonIdx();
            } else {
                $("tr:eq(" + trIdx + ")", "#sampleEvidenceTable").html(newRowHtml);
                generateSampleEvidenceIdx();
            }
        }

        $("button[name='editSampleBtn']", "#samplePersonTable").on("click", function () {
            $("input[name='sampleFlag']", "#sampleModal").val('1');
            EditSampleRow(this);
        });

        $("button[name='delSampleInfoBtn']", "#samplePersonTable").on("click", function () {
            if (!confirm("确认删除吗")) {
                return;
            }
            DelSampleRow(this);

        });

        $("button[name='editSampleBtn']", "#sampleEvidenceTable").on("click", function () {
            $("input[name='sampleFlag']", "#sampleModal").val('0');
            EditSampleRow(this);
        });

        $("button[name='delSampleInfoBtn']", "#sampleEvidenceTable").on("click", function () {

            if (!confirm("确认删除吗")) {
                return;
            }
            DelSampleRow(this);
        });

        $("#sampleModal").modal('hide');
    }
    // person end

    /**
     * person start
     */
    $("#newPerBtn").on("click", function () {
        AddPersonRow();
    });

    $("button[name='editPerBtn']", "#personInfoTable").on("click", function () {
        EditPersonRow(this);
    });

    $("button[name='delPerBtn']", "#personInfoTable").on("click", function () {
        if (!confirm("确认删除吗")) {
            return;
        }
        DelPersonRow(this);
    });

    // 添加人员信息
    $("#SavePersonBtn").on("click", function () {
        SavePersonRow();
    });

    /**
     * sample start
     */
    $("#newSampleBtn").on("click", function () {
        $("input[name='sampleName']", "#sampleModal").removeAttr("placeholder");
        AddSampleRow(0);
    });

    $("#newPersonBtn").on("click", function () {
        $("input[name='sampleName']", "#samplePersonModal").attr("placeholder", "例如：XXX血样或口腔拭子");
        AddSampleRow(1);
    });

    $("button[name='editSampleBtn']", "#samplePersonTable").on("click", function () {
        $("input[name='sampleFlag']", "#sampleModal").val('1');
        EditSampleRow(this);
    });
    $("button[name='editSampleBtn']", "#sampleEvidenceTable").on("click", function () {
        $("input[name='sampleFlag']", "#sampleModal").val('0');
        EditSampleRow(this);
    });

    $("button[name='delSampleBtn']", "#samplePersonTable").on("click", function () {
        if (!confirm("确认删除吗")) {
            return;
        }
        DelSampleRow(this);
    });

    $("button[name='delSampleBtn']", "#sampleEvidenceTable").on("click", function () {
        if (!confirm("确认删除吗")) {
            return;
        }

        DelSampleRow(this);
    });

    $("#SaveSampleBtn").on("click", function () {
        var sampleName = $("input[name='sampleName']", "#sampleModal").val().trim();
        var sampleNameArr = $("input[name='sampleName'][value='" + sampleName + "']", "#sampleEvidenceTable");

        if (sampleNameArr.length > 0) {
            if (confirm("此检材名称已存在，是否继续添加！")) {
                SaveSampleRow();
            } else {
                $("input[name='sampleName']", "#sampleModal").focus();
            }
        } else {

            SaveSampleRow();
        }
    });


    $("input[name='sampleFlag']").on('change', function () {
        var checkedSampleFalg = $("input[name='sampleFlag']:checked").val();
        checkedSampleFalg == 0 ? $("#divRefPerson").hide() : $("#divRefPerson").show();
    });

    //刷新现堪获取的物证
    $("#refreshSampleBtn").on("click", function () {

        $('#refreshSampleBtn').attr("disabled", "disabled");

        var caseXkNo = $("#caseXkNo", "#caseinfo_form").val();
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
            url: "<%=path%>/wt/reg/refreshXkSample.html?caseXkNo=" + caseXkNo + "&consignmentId=" + consignmentId,
            type: "post",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
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
                        newRowHtml += "<td><input type='hidden' name='evidenceNo' value='" + evidenceNo + "'/>" + evidenceNo + " <span class='label-warning'>(新)</span> </td>";
                        newRowHtml += "<td><input type='hidden' name='sampleName' value='" + sampleName + "'/>" + sampleName + "</td>";
                        newRowHtml += "<td style='display: none'><input type='hidden' name='sampleType' value='" + sampleType + "'/>" + sampleType + "</td>";
                        newRowHtml += "<td><input type='hidden' name='sampleTypeName' value='" + sampleTypeName + "'/>" + sampleTypeName + "</td>";
                        newRowHtml += "<td><input type='hidden' name='sampleDesc' value='" + sampleDesc + "'/>" + sampleDesc + "</td>";
                        newRowHtml += "<td><input type='hidden' name='samplePacking' value='" + samplePacking + "'/>" + samplePacking + "</td>";
                        newRowHtml += "<td style='display: none'><input type='hidden' name='sampleProperties' value='" + sampleProperties + "'/>" + sampleProperties + "</td>";
                        newRowHtml += "<td><input type='hidden' name='otherPropertiesDesc' value='" + otherPropertiesDesc + "'/>" + otherPropertiesDesc + "</td>";
                        newRowHtml += "<td><input type='hidden' name='extractDatetime' value='" + extractDatetime + "'/>" + extractDatetime + "</td>";
                        newRowHtml += "<td><input type='hidden' name='extractPerson' value='" + extractPerson + "'/>" + extractPerson + "</td>";
                        newRowHtml += "<td><button name='editPerBtn' class='btn btn-primary btn-xs' type='button'><i class='fa fa-pencil'></i> 修改</button>  <button name='deleteSample' type='button' class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i> 删除</button>  </td>";
                        newRowHtml += "<td style='display: none'><input type='hidden' name='sampleFlag' value='" + sampleFlag + "'/>" + sampleFlag + "</td>";
                        $("#sampleEvidenceTable").append("<tr>" + newRowHtml + "</tr>");

                        generateSampleEvidenceIdx();
                    });
                }

                $("button[name='editPerBtn']", "#sampleEvidenceTable").on("click", function () {
                    EditSampleRow(this);
                });

                $("button[name='deleteSample']", "#sampleEvidenceTable").on("click", function () {
                    if (!confirm("确认删除吗")) {
                        return;
                    }
                    DelSampleRow(this);
                });

            },
            error: function (e) {
                alert(e);
            }
        });

    });

    function deleteSample(sampleId, obj) {

        $.ajax({
            url: "<%=path%>/wt/reg/delSample.html?sampleId=" + sampleId,
            type: "post",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                /*alert("删除成功！")*/
                /*location.reload();*/
                $(obj).parents("tr").remove();
                return;
            },
            error: function (e) {
                alert(e);
            }
        });
    }

    function deletePerson(personId, obj) {

        $.ajax({
            url: "<%=path%>/wt/reg/delPerson.html?personId=" + personId,
            type: "post",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                /*alert("删除成功！")*/
                /*location.reload();*/
                $(obj).parents("tr").remove();
                return;
            },
            error: function (e) {
                alert(e);
            }
        });
    }

</script>
<!-- END JS -->
</body>
</html>


