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
                            <span class="label label-primary">案件信息</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <form id="caseinfo_form" class="form-horizontal tasi-form" method="get">
                                <input type="hidden" id="caseId" value="${caseInfo.id}"/>


                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件受理编号 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" id="caseNo" name="caseNo" class="form-control required" value="${caseInfo.caseNo}" readonly="readonly">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件名称 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" id="caseName" name="caseName" class="form-control required" value="${caseInfo.caseName}" <c:if test="${consignment.additionalFlag eq 1}">readonly="readonly"</c:if>>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案发地点</label>
                                    <div class="col-sm-5">
                                        <input type="text" id="caseLocation" name="caseLocation" class="form-control" value="${caseInfo.caseLocation}" <c:if test="${consignment.additionalFlag eq 1}">readonly="readonly"</c:if>>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案发时间 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input class="form_datetime form-control required" id="caseDatetime"
                                               name="caseDatetime" type="text"
                                               value="<fmt:formatDate value='${caseInfo.caseDatetime}' pattern='yyyy-MM-dd'/>" <c:if test="${consignment.additionalFlag eq 1}">readonly="readonly"</c:if>>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">简要案情 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <textarea class="form-control required" id="caseBrief" name="caseBrief" rows="3" <c:if test="${consignment.additionalFlag eq 1}">readonly="readonly"</c:if>>${caseInfo.caseBrief}</textarea>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件类型 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-3">
                                        <select class="form-control required" name="caseType" id="caseType" <c:if test="${consignment.additionalFlag eq 1}">readonly="readonly"</c:if>>
                                            <c:forEach items="${caseTypeList}" var="list" varStatus="s">
                                                <option value="${list.dictCode}" <c:if test="${caseInfo.caseType eq list.dictCode}">selected</c:if>>${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">案件性质 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-3">
                                        <select class="form-control required" name="caseProperty" id="caseProperty" <c:if test="${consignment.additionalFlag eq 1}">readonly="readonly"</c:if>>
                                            <c:forEach items="${casePropertyList}" var="list" varStatus="s">
                                                <option value="${list.dictCode}" <c:if test="${caseInfo.caseProperty eq list.dictCode}">selected</c:if>>${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件级别</label>
                                    <div class="col-sm-3">
                                        <select class="form-control required" name="caseLevel" id="caseLevel" <c:if test="${consignment.additionalFlag eq 1}">readonly="readonly"</c:if>>
                                            <c:forEach items="${caseLevelList}" var="list" varStatus="s">
                                                <option value="${list.dictCode}"<c:if test="${caseInfo.caseLevel eq list.dictCode}">selected</c:if>>${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">是否加急</label>
                                    <div class="col-sm-3">
                                        <label class="checkbox-inline">
                                            <input name="jiajiFlag" type="checkbox" id="jiajiFlag" <c:if test="${caseInfo.jiajiFlag eq 1}">checked</c:if>  <c:if test="${consignment.additionalFlag eq 1}">disabled="disabled"</c:if>/>
                                        </label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">其他说明</label>
                                    <div class="col-sm-5">
                                        <textarea class="form-control" name="caseSpecialRemark" id="caseSpecialRemark" rows="2" <c:if test="${consignment.additionalFlag eq 1}">readonly="readonly"</c:if>>${caseInfo.caseSpecialRemark}</textarea>
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
                                <input type="hidden" id="consignmentId" value="${consignment.id}"/>
                                <input type="hidden" id="additionalFlag" value="${consignment.additionalFlag}"/>
                                <input type="hidden" id="delegateOrg" value="${consignment.delegateOrg}"/>

                                   <div class="form-group">
                                        <label class="col-sm-2 col-sm-2 control-label">是否为补送</label>
                                        <div class="col-sm-5">
                                            <label class="checkbox-inline">
                                                <c:if test="${consignment.additionalFlag eq 1}">是</c:if>
                                                <c:if test="${consignment.additionalFlag eq 0}">否</c:if>
                                            </label>
                                        </div>
                                    </div>

                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托单位</label>
                                    <div class="col-sm-5">
                                        ${consignment.delegateOrgDesc}
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托单位电话</label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control" id="delegateOrgPhone" value="${consignment.delegateOrgPhone}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托人1（姓名、证件号） <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-2">
                                        <input type="text" class="form-control required" placeholder="姓名" id="delegator1" value="${consignment.delegator1}"/>
                                    </div>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control required" placeholder="证件号码" id="delegator1Cno" value="${consignment.delegator1Cno}"/>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托人1电话 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control required" id="delegator1Phone" value="${consignment.delegator1Phone}"/>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托人2（姓名、证件号） <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-2">
                                        <input type="text" class="form-control required" placeholder="姓名" id="delegator2" value="${consignment.delegator2}"/>
                                    </div>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control required" placeholder="证件号码" id="delegator2Cno" value="${consignment.delegator2Cno}"/>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托人2电话 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control required" id="delegator2Phone" value="${consignment.delegator2Phone}"/>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">鉴定要求 <i class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control required" id="identifyRequirement" value="${consignment.identifyRequirement}"/>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">原鉴定情况（鉴定单位及结论）</label>
                                    <div class="col-sm-5">
                                        <textarea class="form-control" rows="2" id="preIdentifyDesc">${consignment.preIdentifyDesc}</textarea>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">重新鉴定原因</label>
                                    <div class="col-sm-5">
                                        <textarea class="form-control" rows="2" id="reidentifyReason">${consignment.reidentifyReason}</textarea>
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
                                    <th>序号</th>
                                    <th>人员类型</th>
                                    <th>姓名</th>
                                    <th>性别</th>
                                    <th>身份证号</th>
                                    <th>与人员关系</th>
                                </tr>
                                </thead>
                                <tbody id="personInfoTable">
                                <c:if test="${fn:length(otherPersonInfoList) gt 0}">
                                    <c:forEach items="${otherPersonInfoList}" var="acceptPerson" varStatus="p">
                                        <tr class="regedTr">
                                            <td></td>
                                            <td><input type="hidden" name="personType" value="${acceptPerson.personType}"/>${acceptPerson.personTypeName}</td>
                                            <td><input type="hidden" name="personName" value="${acceptPerson.personName}"/>${acceptPerson.personName}</td>
                                            <td><input type="hidden" name="personGender" value="${acceptPerson.personGender}"/>${acceptPerson.personGenderName}</td>
                                            <td>
                                                <input type="hidden" name="personIdcardNo" value="${acceptPerson.personIdcardNo}"/>
                                                <input type="hidden" name="noIdcardRemark" value="${acceptPerson.noIdcardRemark}"/>
                                                    ${acceptPerson.personIdcardNo} <c:if test="${not empty acceptPerson.noIdcardRemark}">（${acceptPerson.noIdcardRemark}）</c:if>
                                            </td>
                                            <td>
                                                <input type="hidden" name="personId" value="${acceptPerson.id}"/>
                                                <input type="hidden" name="relativeIdentify" value="${acceptPerson.relativeIdentify}"/>
                                                ${acceptPerson.relativeIdentifyName}
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${fn:length(personInfoList) gt 0}">
                                    <c:forEach items="${personInfoList}" var="person" varStatus="p">
                                        <tr>
                                            <td></td>
                                            <td><input type="hidden" name="personType" value="${person.personType}"/>${person.personTypeName}</td>
                                            <td><input type="hidden" name="personName" value="${person.personName}"/>${person.personName}</td>
                                            <td><input type="hidden" name="personGender" value="${person.personGender}"/>${person.personGenderName}</td>
                                            <td>
                                                <input type="hidden" name="personIdcardNo" value="${person.personIdcardNo}"/>
                                                <input type="hidden" name="noIdcardRemark" value="${person.noIdcardRemark}"/>
                                                    ${person.personIdcardNo} <c:if test="${not empty person.noIdcardRemark}">（${person.noIdcardRemark}）</c:if>
                                            </td>
                                            <td>
                                                <input type="hidden" name="personId" value="${person.id}"/>
                                                <input type="hidden" name="personNo" value="${person.personNo}"/>
                                                <input type="hidden" name="relativeIdentify" value="${person.relativeIdentify}"/>
                                                ${person.relativeIdentifyName}
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>

                            <div class="modal fade" id="personModal" aria-hidden="true">
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
                                                            <c:forEach items="${personTypeList}" var="list" varStatus="s">
                                                                <option value="${list.dictCode}">${list.dictName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">姓名 <i class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <input name="personName" type="text" class="form-control small required" value=""/>
                                                    </div>
                                                    <div class="col-md-2 has-error hide">
                                                        <p class="help-block">必填项</p>
                                                    </div>
                                                </div>
                                                <div class="form-group">
                                                    <label class="control-label col-md-3">性别 <i class="fa fa-asterisk color_red required"></i></label>
                                                    <div class="col-md-5">
                                                        <select name="personGender" class="form-control small">
                                                            <c:forEach items="${personGenderList}" var="list" varStatus="s">
                                                                <option value="${list.dictCode}">${list.dictName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-2 has-error hide">
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
                                                        <select name="relativeIdentify" class="form-control small required">
                                                            <c:forEach items="${personRelationList}" var="list" varStatus="s">
                                                                <option value="${list.dictCode}">${list.dictName}</option>
                                                            </c:forEach>
                                                        </select>
                                                    </div>
                                                </div>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <input type="hidden" name="personId" value=""/>
                                            <input type="hidden" name="personOperateType" value=""/>
                                            <input type="hidden" name="personTableRownum" value=""/>
                                            <button class="btn btn-success" type="button" id="SavePersonBtn">确定</button>
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
                            <c:if test="${consignment.additionalFlag eq 1}">
                                <a class="btn btn-info" id="showRegedTrBtn">
                                    <span id="hiddenSpan" class="hide"><i class="fa fa-angle-double-up"></i> 只显示本次委托检材</span>
                                    <span id="showSpan"><i class="fa fa-angle-double-down"></i> 显示全部检材</span>
                                </a>
                            </c:if>
                            <div class="space15" style="height: 30px;padding-top:10px;">
                                <strong>人员检材 <i class="fa fa-hand-o-down"></i></strong>
                            </div>
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>检材编号</th>
                                    <th>检材名称</th>
                                    <th>检材类型</th>
                                    <th>检材描述</th>
                                    <th>包装</th>
                                    <th>性状</th>
                                    <th>关联人员 <i class="fa fa-user"></i></th>
                                    <th>提取时间</th>
                                    <th>提取人</th>
                                    <th width="200px">特殊选项  <i class="fa fa-star"></i></th>
                                    <th>审核状态</th>
                                    <th>上报状态</th>
                                    <th>全选<input type="checkbox" name="allPersonChecked" id="allPersonChecked"/></th>
                                </tr>
                                </thead>
                                <tbody id="samplePersonTable">
                                <c:if test="${fn:length(otherSampleInfoList) gt 0}">
                                    <c:forEach items="${otherSampleInfoList}" var="acceptedSample" varStatus="as">
                                        <c:if test="${acceptedSample.sampleFlag eq 1}">
                                            <tr class="regedTr">
                                                <td></td>
                                                <td><input type='hidden' name='sampleNo' value='${acceptedSample.sampleNo}'/>${acceptedSample.sampleNo}</td>
                                                <td title="${acceptedSample.sampleName}">
                                                    <input type='hidden' name='sampleName' value='${acceptedSample.sampleName}'/>
                                                    <c:if test="${fn:length(acceptedSample.sampleName) gt 7}">
                                                        ${fn:substring(acceptedSample.sampleName,0,6)} ...
                                                    </c:if>
                                                    <c:if test="${fn:length(acceptedSample.sampleName) lt 8}">
                                                        ${acceptedSample.sampleName}
                                                    </c:if>
                                                </td>
                                                <td><input type='hidden' name='sampleType' value='${acceptedSample.sampleType}'/>${acceptedSample.sampleTypeName}</td>
                                                <td><input type='hidden' name='sampleDesc' value='${acceptedSample.sampleDesc}'/>${acceptedSample.sampleDesc}</td>
                                                <td><input type='hidden' name='samplePacking' value='${acceptedSample.samplePacking}'/>${acceptedSample.samplePacking}</td>
                                                <td>
                                                    <input type='hidden' name='sampleProperties' value='${acceptedSample.sampleProperties}'/>
                                                    <input type='hidden' name='otherPropertiesDesc' value='${acceptedSample.otherPropertiesDesc}'/>
                                                    <c:if test="${acceptedSample.sampleProperties != '9999'}">
                                                        ${acceptedSample.samplePropertiesName}
                                                    </c:if>
                                                    <c:if test="${acceptedSample.sampleProperties eq '9999'}">
                                                        ${acceptedSample.otherPropertiesDesc}
                                                    </c:if>
                                                </td>
                                                <td><input type='hidden' name='refPersonId' value='${acceptedSample.refPersonId}'/><input type='hidden' name='refPersonName' value='${acceptedSample.refPersonName}'/>${acceptedSample.refPersonName}</td>
                                                <td>
                                                    <input type='hidden' name='extractDatetime' value="<fmt:formatDate value='${acceptedSample.extractDatetime}' pattern='yyyy-MM-dd'/>"/>
                                                    <fmt:formatDate value='${acceptedSample.extractDatetime}' pattern='yyyy-MM-dd'/>
                                                </td>
                                                <td>
                                                    <input type='hidden' name='extractPerson' value="${acceptedSample.extractPerson}"/>${acceptedSample.extractPerson}
                                                </td>
                                                <td>
                                                    <label class="checkbox-inline">
                                                        <input name="atomFlag" type="checkbox" <c:if test="${acceptedSample.atomFlag eq 1}">checked</c:if> />微量
                                                    </label>
                                                    <label class="checkbox-inline">
                                                        <input name="urgentFlag" type="checkbox" <c:if test="${acceptedSample.urgentFlag eq 1}">checked</c:if> />加急
                                                    </label>
                                                    <label class="checkbox-inline">
                                                        <input name="difficultFlag" type="checkbox" <c:if test="${acceptedSample.difficultFlag eq 1}">checked</c:if> />疑难
                                                    </label>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${acceptedSample.auditStatus eq 1}">已审核</c:when>
                                                        <c:otherwise>未审核</c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <input type="hidden" name="uploadStatus" value="${sample.uploadStatus}">
                                                    <c:choose>
                                                        <c:when test="${acceptedSample.uploadStatus eq 1}">已上报</c:when>
                                                        <c:otherwise>未上报</c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:if test="${consignment.additionalFlag eq 1}">-- --</c:if>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${fn:length(sampleInfoList) gt 0}">
                                    <c:forEach items="${sampleInfoList}" var="sample" varStatus="p">
                                        <c:if test="${sample.sampleFlag eq 1}">
                                            <tr>
                                                <td></td>
                                                <td><input type='hidden' name='sampleNo' value='${sample.sampleNo}'/>${sample.sampleNo}</td>
                                                <td title="${sample.sampleName}">
                                                    <input type='hidden' name='sampleName' value='${sample.sampleName}'/>
                                                    <c:if test="${fn:length(sample.sampleName) gt 7}">
                                                        ${fn:substring(sample.sampleName,0,6)} ...
                                                    </c:if>
                                                    <c:if test="${fn:length(sample.sampleName) lt 8}">
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
                                                    <input type='hidden' name='extractPerson' value="${sample.extractPerson}"/>${sample.extractPerson}
                                                </td>
                                                <td>
                                                    <label class="checkbox-inline">
                                                        <input name="atomFlag" type="checkbox" <c:if test="${sample.atomFlag eq 1}">checked</c:if> /> 微量
                                                    </label>
                                                    <label class="checkbox-inline">
                                                        <input name="urgentFlag" type="checkbox" <c:if test="${sample.urgentFlag eq 1}">checked</c:if> /> 加急
                                                    </label>
                                                    <label class="checkbox-inline">
                                                        <input name="difficultFlag" type="checkbox" <c:if test="${sample.difficultFlag eq 1}">checked</c:if> /> 疑难
                                                    </label>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${sample.auditStatus eq 1}">已审核</c:when>
                                                        <c:otherwise>未审核</c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <input type="hidden" name="uploadStatus" value="${sample.uploadStatus}">
                                                    <c:choose>
                                                        <c:when test="${sample.uploadStatus eq 1}">已上报</c:when>
                                                        <c:otherwise>未上报</c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <label class="checkbox-inline">
                                                        <input type="hidden" name="sampleId" value="${sample.id}"/>
                                                        <input type="hidden" name="sampleFlag" value="${sample.sampleFlag}"/>
                                                        <input type="checkbox" name="uploadPersonBox" <c:if test="${sample.auditStatus eq 1}">checked</c:if>>
                                                    </label>
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
                                    <th>序号</th>
                                    <th>检材编号</th>
                                    <th>检材名称</th>
                                    <th>检材类型</th>
                                    <th>检材描述</th>
                                    <th>包装</th>
                                    <th>性状</th>
                                    <th>提取时间</th>
                                    <th>提取人</th>
                                    <th width="200px">特殊选项  <i class="fa fa-star"></i></th>
                                    <th>审核状态</th>
                                    <th>上报状态</th>
                                    <th>全选<input type="checkbox" name="allEvidenceChecked" id="allEvidenceChecked"/></th>
                                </tr>
                                </thead>
                                <tbody id="sampleEvidenceTable">
                                <c:if test="${fn:length(otherSampleInfoList) gt 0}">
                                    <c:forEach items="${otherSampleInfoList}" var="acceptedSample" varStatus="as">
                                        <c:if test="${acceptedSample.sampleFlag eq 0}">
                                            <tr class="regedTr">
                                                <td></td>
                                                <td><input type='hidden' name='sampleNo' value='${acceptedSample.sampleNo}'/>${acceptedSample.sampleNo}</td>
                                                <td title="${acceptedSample.sampleName}">
                                                    <input type='hidden' name='sampleName' value='${acceptedSample.sampleName}'/>
                                                    <c:if test="${fn:length(acceptedSample.sampleName) gt 10}">
                                                        ${fn:substring(acceptedSample.sampleName,0,9)} ...
                                                    </c:if>
                                                    <c:if test="${fn:length(acceptedSample.sampleName) lt 11}">
                                                        ${acceptedSample.sampleName}
                                                    </c:if>
                                                </td>
                                                <td><input type='hidden' name='sampleType' value='${acceptedSample.sampleType}'/>${acceptedSample.sampleTypeName}</td>
                                                <td><input type='hidden' name='sampleDesc' value='${acceptedSample.sampleDesc}'/>${acceptedSample.sampleDesc}</td>
                                                <td><input type='hidden' name='samplePacking' value='${acceptedSample.samplePacking}'/>${acceptedSample.samplePacking}</td>
                                                <td>
                                                    <input type='hidden' name='sampleProperties' value='${acceptedSample.sampleProperties}'/>
                                                    <input type='hidden' name='otherPropertiesDesc' value='${acceptedSample.otherPropertiesDesc}'/>
                                                    <c:if test="${acceptedSample.sampleProperties != '9999'}">
                                                        ${acceptedSample.samplePropertiesName}
                                                    </c:if>
                                                    <c:if test="${acceptedSample.sampleProperties eq '9999'}">
                                                        ${acceptedSample.otherPropertiesDesc}
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <input type='hidden' name='extractDatetime' value="<fmt:formatDate value='${acceptedSample.extractDatetime}' pattern='yyyy-MM-dd'/>"/>
                                                    <fmt:formatDate value='${acceptedSample.extractDatetime}' pattern='yyyy-MM-dd'/>
                                                </td>
                                                <td>
                                                    <input type='hidden' name='extractPerson' value="${acceptedSample.extractPerson}"/>${acceptedSample.extractPerson}
                                                </td>
                                                <td>
                                                    <label class="checkbox-inline">
                                                        <input name="atomFlag" type="checkbox" <c:if test="${acceptedSample.atomFlag eq 1}">checked</c:if> />微量
                                                    </label>
                                                    <label class="checkbox-inline">
                                                        <input name="urgentFlag" type="checkbox" <c:if test="${acceptedSample.urgentFlag eq 1}">checked</c:if> />加急
                                                    </label>
                                                    <label class="checkbox-inline">
                                                        <input name="difficultFlag" type="checkbox" <c:if test="${acceptedSample.difficultFlag eq 1}">checked</c:if> />疑难
                                                    </label>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${acceptedSample.auditStatus eq 1}">已审核</c:when>
                                                        <c:otherwise>未审核</c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <input type="hidden" name="uploadStatus" value="${acceptedSample.uploadStatus}">
                                                    <c:choose>
                                                        <c:when test="${acceptedSample.uploadStatus eq 1}">已上报</c:when>
                                                        <c:otherwise>未上报</c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <c:if test="${consignment.additionalFlag eq 1}">-- --</c:if>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${fn:length(sampleInfoList) gt 0}">
                                    <c:forEach items="${sampleInfoList}" var="sample" varStatus="p">
                                        <c:if test="${sample.sampleFlag eq 0}">
                                            <tr>
                                                <td></td>
                                                <td><input type='hidden' name='sampleNo' value='${sample.sampleNo}'/>${sample.sampleNo}</td>
                                                <td title="${sample.sampleName}">
                                                    <input type='hidden' name='sampleName' value='${sample.sampleName}'/>
                                                    <c:if test="${fn:length(sample.sampleName) gt 10}">
                                                        ${fn:substring(sample.sampleName,0,9)} ...
                                                    </c:if>
                                                    <c:if test="${fn:length(sample.sampleName) lt 11}">
                                                        ${sample.sampleName}
                                                    </c:if>
                                                </td>
                                                <td><input type='hidden' name='sampleType' value='${sample.sampleType}'/>${sample.sampleTypeName}</td>
                                                <td title="${sample.sampleDesc}"><input type='hidden' name='sampleDesc' value='${sample.sampleDesc}'/>
                                                    <c:if test="${fn:length(sample.sampleDesc) gt 8}">
                                                        ${fn:substring(sample.sampleDesc,0,7)}
                                                    </c:if>
                                                    <c:if test="${fn:length(sample.sampleDesc) lt 9}">
                                                        ${sample.sampleDesc}
                                                    </c:if>
                                                </td>
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
                                                    <input type='hidden' name='extractPerson' value="${sample.extractPerson}"/>${sample.extractPerson}
                                                </td>
                                                <td>
                                                    <label class="checkbox-inline">
                                                        <input name="atomFlag" type="checkbox" <c:if test="${sample.atomFlag eq 1}">checked</c:if> /> 微量
                                                    </label>
                                                    <label class="checkbox-inline">
                                                        <input name="urgentFlag" type="checkbox" <c:if test="${sample.urgentFlag eq 1}">checked</c:if> /> 加急
                                                    </label>
                                                    <label class="checkbox-inline">
                                                        <input name="difficultFlag" type="checkbox" <c:if test="${sample.difficultFlag eq 1}">checked</c:if> /> 疑难
                                                    </label>
                                                </td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${sample.auditStatus eq 1}">已审核</c:when>
                                                        <c:otherwise>未审核</c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <input type="hidden" name="uploadStatus" value="${sample.uploadStatus}">
                                                    <c:choose>
                                                        <c:when test="${sample.uploadStatus eq 1}">已上报</c:when>
                                                        <c:otherwise>未上报</c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td>
                                                    <label class="checkbox-inline">
                                                        <input type="hidden" name="sampleId" value="${sample.id}"/>
                                                        <input type="hidden" name="sampleFlag" value="${sample.sampleFlag}"/>
                                                        <input type="checkbox" name="uploadEvidenceBox" <c:if test="${sample.auditStatus eq 1}">checked</c:if>>
                                                    </label>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>

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
                                    <button class="btn btn-lg btn-success" name="uploadBtn"><i class="fa fa-cloud-upload"></i> 上报</button>
                                    <button class="btn btn-lg btn-info" type="button" onclick="javascript:history.go(-1);"><i class="fa fa-reply"></i> 返 回 </button>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>

        </div>
    </section>
</section>

<%@ include file="../../common/script.jsp" %>
<script>

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

            var $TR = $(this);
            var checkBox = $("input[name='uploadPersonBox']",$TR).is(":checked");
            if(checkBox){
                $("input[name='allPersonChecked']").prop("checked", "checked");
            }
        });
    }

    generateSampleEvidenceIdx();

    function generateSampleEvidenceIdx() {
        $("tr","#sampleEvidenceTable").each(function(){
            $("td:first", $(this)).text($(this).index()+1);

            var $TR = $(this);
            var checkBox = $("input[name='uploadEvidenceBox']",$TR).is(":checked");
            if(checkBox){
                $("input[name='allEvidenceChecked']").prop("checked", "checked");
            }
        });
    }

    if ($("#hiddenSpan").hasClass("hide")) {
        $("tr.regedTr", "#samplePersonTable").addClass("hide");
        $("tr.regedTr", "#sampleEvidenceTable").addClass("hide");

        $("#hiddenSpan").addClass("hide");
        $("#showSpan").removeClass("hide");
    }else{
        $("tr.regedTr", "#samplePersonTable").removeClass("hide");
        $("tr.regedTr", "#sampleEvidenceTable").removeClass("hide");

        $("#hiddenSpan").removeClass("hide");
        $("#showSpan").addClass("hide");
    }

    $(function () {
        'use strict';

        $("#allPersonChecked").on("click",function(){
            var ch = document.getElementsByName("uploadPersonBox");
            if(document.getElementsByName("allPersonChecked")[0].checked==true){
                for(var i=0;i<ch.length;i++){
                    ch[i].checked=true;
                }
            }else{
                for(var i=0;i<ch.length;i++){
                    ch[i].checked=false;
                }
            }
        });

        $("#allEvidenceChecked").on("click",function(){
            var ch = document.getElementsByName("uploadEvidenceBox");
            if(document.getElementsByName("allEvidenceChecked")[0].checked==true){
                for(var i=0;i<ch.length;i++){
                    ch[i].checked=true;
                }
            }else{
                for(var i=0;i<ch.length;i++){
                    ch[i].checked=false;
                }
            }
        });

        $("button[name='uploadBtn']").on("click",function(){

            var caseInfo = GetCaseInfo();
            var consignment = GetConsignment();
            var sampleInfoList = GetSampleInfo();

            var params = {
                "caseInfo" : caseInfo,
                "consignment" : consignment,
                "sampleInfoList": sampleInfoList
            };

            $.ajax({
                url : "<%=path%>/center/3/uploadDataBase.html",
                type:"post",
                data:JSON.stringify(params),
                dataType:"json",
                contentType: "application/json; charset=utf-8",
                success : function(data) {
                    if(data.success || data.success == true || data.success == "true") {
                        alert("上报成功!");
                        var consignmentId = $("#consignmentId","#consignment_form").val();
                        location.href = "<%=path%>/center/3/upload.html?consignmentId=" + consignmentId;
                    }else {
                        alert("上报失败！");
                    }
                },
                error: function(e){
                    alert(e);
                }
            });
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

        function GetSampleInfo() {
            var sampleArr = new Array();

            var sample;
            var checkBox;
            if($("tr", "#samplePersonTable").length > 0){
                var $samplePersonTRArr = $("tr", "#samplePersonTable").not(".regedTr");
                var samplePersonCnt = $samplePersonTRArr.length;
                var $samplePersonTR;
                for (var i = 0; i < samplePersonCnt; i++) {
                    sample = {};
                    $samplePersonTR = $($samplePersonTRArr.get(i));
                    sample.caseId = $("#caseId").val();
                    sample.id = $("input[name='sampleId']", $samplePersonTR).val();
                    sample.sampleNo = $("input[name='sampleNo']", $samplePersonTR).val();
                    sample.sampleName = $("input[name='sampleName']", $samplePersonTR).val();
                    sample.sampleType = $("input[name='sampleType']", $samplePersonTR).val();
                    sample.extractDatetime = $("input[name='extractDatetime']", $samplePersonTR).val();
                    sample.extractPerson = $("input[name='extractPerson']", $samplePersonTR).val();
                    sample.sampleDesc = $("input[name='sampleDesc']", $samplePersonTR).val();
                    sample.samplePacking = $("input[name='samplePacking']", $samplePersonTR).val();
                    sample.sampleProperties = $("input[name='sampleProperties']", $samplePersonTR.get(i)).val();
                    sample.otherPropertiesDesc = $("input[name='otherPropertiesDesc']", $samplePersonTR.get(i)).val();
                    //sample.refPersonId = $("input[name='refPersonId']", $samplePersonTR.get(i)).val();
                    sample.refPersonName = $("input[name='refPersonName']", $samplePersonTR).val();
                    sample.sampleFlag =  "1";
                    sample.atomFlag = ($("input[name='atomFlag']", $samplePersonTR).is(":checked")==true) ? "1" : "0";
                    sample.urgentFlag = ($("input[name='urgentFlag']", $samplePersonTR).is(":checked")==true) ? "1" : "0";
                    sample.difficultFlag = ($("input[name='difficultFlag']", $samplePersonTR).is(":checked")==true) ? "1" : "0";

                    checkBox = $("input[name='uploadPersonBox']",$samplePersonTR).is(":checked");
                    if (checkBox)
                        sampleArr.push(sample);
                }
            }

            if($("tr", "#sampleEvidenceTable").length > 0) {
                var $sampleEvidenceTRArr = $("tr", "#sampleEvidenceTable").not(".regedTr");
                var sampleEvidenceCnt = $sampleEvidenceTRArr.length;
                var $sampleEvidenceTR;
                for (var i = 0; i < sampleEvidenceCnt; i++) {
                    sample = {};
                    $sampleEvidenceTR = $($sampleEvidenceTRArr.get(i));
                    sample.caseId = $("#caseId").val();
                    sample.id = $("input[name='sampleId']", $sampleEvidenceTR).val();
                    sample.sampleNo = $("input[name='sampleNo']", $sampleEvidenceTR).val();
                    sample.sampleName = $("input[name='sampleName']", $sampleEvidenceTR).val();
                    sample.sampleType = $("input[name='sampleType']", $sampleEvidenceTR).val();
                    sample.extractDatetime = $("input[name='extractDatetime']", $sampleEvidenceTR).val();
                    sample.extractPerson = $("input[name='extractPerson']", $sampleEvidenceTR).val();
                    sample.sampleDesc = $("input[name='sampleDesc']", $sampleEvidenceTR).val();
                    sample.samplePacking = $("input[name='samplePacking']", $sampleEvidenceTR).val();
                    sample.sampleProperties = $("input[name='sampleProperties']", $sampleEvidenceTR).val();
                    sample.otherPropertiesDesc = $("input[name='otherPropertiesDesc']", $sampleEvidenceTR.get(i)).val();
                    sample.sampleFlag = "0";
                    sample.atomFlag = ($("input[name='atomFlag']", $sampleEvidenceTR).is(":checked")==true) ? "1" : "0";
                    sample.urgentFlag = ($("input[name='urgentFlag']", $sampleEvidenceTR).is(":checked")==true) ? "1" : "0";
                    sample.difficultFlag = ($("input[name='difficultFlag']", $sampleEvidenceTR).is(":checked")==true) ? "1" : "0";

                    checkBox = $("input[name='uploadEvidenceBox']",$sampleEvidenceTR).is(":checked");
                    if (checkBox)
                        sampleArr.push(sample);
                }
            }

            return sampleArr;
        }

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

    });
</script>
</body>
</html>
