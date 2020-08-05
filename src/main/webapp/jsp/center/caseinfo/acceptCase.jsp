<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/include.jsp" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <%@ include file="../../common/link.jsp" %>
    <style type="text/css">
        #load {
            position: fixed;
            top: 0px;
            right: 0px;
            bottom: 0px;
            filter: alpha(opacity=60);
            background-color: #1c1c1c;
            z-index: 1002;
            left: 0px;
            display: none;
            opacity: 0.5;
            -moz-opacity: 0.5;
            padding-top: 300px;
            color: #a7a7a7
        }
    </style>
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

                                <c:if test="${consignment.additionalFlag eq 1}">
                                    <div class="form-group">
                                        <label class="col-sm-2 col-sm-2 control-label">案件受理编号 <i
                                                class="fa fa-asterisk color_red"></i></label>
                                        <div class="col-sm-5">
                                            <input type="text" id="caseNo" name="caseNo" class="form-control required"
                                                   value="${caseInfo.caseNo}" readonly="readonly">
                                        </div>
                                        <div class="col-sm-2 has-error hide">
                                            <p class="help-block">必填项</p>
                                        </div>
                                    </div>
                                </c:if>

                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件现堪编号 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="hidden" name="xkLoginName" value="${xkLoginName}">
                                        <input type="hidden" name="xkLoginPassword" value="${xkLoginPassword}">
                                        <input type="text" id="caseXkNo" name="caseXkNo" class="form-control required"
                                               value="${caseInfo.caseXkNo}">
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
                                               value="${caseInfo.caseName}"
                                               <c:if test="${consignment.additionalFlag eq 1}">readonly="readonly"</c:if>/>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案发地点</label>
                                    <div class="col-sm-5">
                                        <input type="text" id="caseLocation" name="caseLocation" class="form-control"
                                               value="${caseInfo.caseLocation}"
                                               <c:if test="${consignment.additionalFlag eq 1}">readonly="readonly"</c:if>>
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
                                        <textarea class="form-control required" id="caseBrief" name="caseBrief" rows="3"
                                                  <c:if test="${consignment.additionalFlag eq 1}">readonly="readonly"</c:if>>${caseInfo.caseBrief}</textarea>
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
                                                <c:if test="${consignment.additionalFlag eq 1}">readonly="readonly"</c:if>>
                                            <c:forEach items="${caseTypeList}" var="list" varStatus="s">
                                                <option value="${list.dictCode}"
                                                        <c:if test="${caseInfo.caseType eq list.dictCode}">selected</c:if>>${list.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">案件性质 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-3">
                                        <select class="form-control required" name="caseProperty" id="caseProperty"
                                                <c:if test="${consignment.additionalFlag eq 1}">readonly="readonly"</c:if>>
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
                                        <select class="form-control required" name="caseLevel" id="caseLevel"
                                                value="${caseInfo.caseLevel}"
                                                <c:if test="${consignment.additionalFlag eq 1}">readonly="readonly"</c:if>>
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
                                                   <c:if test="${caseInfo.jiajiFlag eq 1}">checked</c:if>
                                                   <c:if test="${consignment.additionalFlag eq 1}">disabled="disabled"</c:if>/>
                                        </label>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">其他说明</label>
                                    <div class="col-sm-5">
                                        <textarea class="form-control" name="caseSpecialRemark" id="caseSpecialRemark"
                                                  rows="2"
                                                  <c:if test="${consignment.additionalFlag eq 1}">readonly="readonly"</c:if>>${caseInfo.caseSpecialRemark}</textarea>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </section>
                </div>
            </div>
            <!-- END ROW  -->

            <div id="load" align="center"><img src="<%=path%>/images/loading.gif" width="100" height="100"
                                               align="absmiddle"/>加载中...
            </div>

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
                                <input type="hidden" id="delegateOrgId" value="${delegateOrg.id}"/>

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
                                    <label class="col-sm-2 col-sm-2 control-label">委托单位 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control" id="delegateOrgDesc"
                                               value="${consignment.delegateOrgDesc}"/>
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>
                                <%--<div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">委托单位电话</label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control" id="delegateOrgPhone"
                                               value="${consignment.delegateOrgPhone}"/>
                                    </div>
                                </div>--%>
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
                                        <input type="text" class="form-control" id="postalAddress"
                                               value="${consignment.postalAddress}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">邮政编号 </label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control" id="postNo"
                                               value="${consignment.postNo}"/>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">传真号码 </label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control" id="faxNo"
                                               value="${consignment.faxNo}"/>
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
                            <a class="btn btn-primary" href="javascript:;" id="newPerBtn">
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
                                    <th width="100px">操作</th>
                                </tr>
                                </thead>
                                <tbody id="personInfoTable">
                                <c:if test="${fn:length(otherPersonInfoList) gt 0}">
                                    <c:forEach items="${otherPersonInfoList}" var="acceptPerson" varStatus="p">
                                        <tr class="regedTr">
                                            <td></td>
                                            <td><input type="hidden" name="personType"
                                                       value="${acceptPerson.personType}"/>${acceptPerson.personTypeName}
                                            </td>
                                            <td><input type="hidden" name="personName"
                                                       value="${acceptPerson.personName}"/>${acceptPerson.personName}
                                            </td>
                                            <td><input type="hidden" name="personGender"
                                                       value="${acceptPerson.personGender}"/>${acceptPerson.personGenderName}
                                            </td>
                                            <td>
                                                <input type="hidden" name="personIdcardNo"
                                                       value="${acceptPerson.personIdcardNo}"/>
                                                <input type="hidden" name="noIdcardRemark"
                                                       value="${acceptPerson.noIdcardRemark}"/>
                                                    ${acceptPerson.personIdcardNo} <c:if
                                                    test="${not empty acceptPerson.noIdcardRemark}">（${acceptPerson.noIdcardRemark}）</c:if>
                                            </td>
                                            <td><input type="hidden" name="relativeIdentify"
                                                       value="${acceptPerson.relativeIdentify}"/>${acceptPerson.relativeIdentifyName}
                                            </td>
                                            <td>
                                                <input type="hidden" name="personId" value="${acceptPerson.id}"/>
                                                -- --
                                                    <%--&nbsp;
                                                    <button name='delPerBtn' class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i> 删除</button>--%>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                </c:if>
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
                                                    <%--&nbsp;
                                                    <button name='delPerBtn' class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i> 删除</button>--%>
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
                                                               class="form-control small required" value=""/>
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
                                                </div>
                                            </form>
                                        </div>
                                        <div class="modal-footer">
                                            <input type="hidden" name="personId" value=""/>
                                            <input type="hidden" name="personOperateType" value=""/>
                                            <input type="hidden" name="personTableRownum" value=""/>
                                            <button class="btn btn-success" type="button" id="SavePersonBtn">确定</button>
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
                            <a class="btn btn-primary" href="javascript:;" id="newPersonBtn">
                                <i class="fa fa-plus"></i> 添加人员检材
                            </a>

                            <c:if test="${consignment.additionalFlag eq 1}">
                                <a class="btn btn-info" id="showRegedTrBtn">
                                    <span id="hiddenSpan"><i class="fa fa-angle-double-up"></i> 隐藏已受理检材</span>
                                    <span id="showSpan" class="hide"><i
                                            class="fa fa-angle-double-down"></i> 显示已受理检材</span>
                                </a>
                            </c:if>


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
                                    <th>提取时间</th>
                                    <th>提取人</th>
                                    <th width="190px">特殊选项 <i class="fa fa-star"></i></th>
                                    <th width="60px"><strong>受理</strong></th>
                                    <th width="100px">操作</th>
                                </tr>
                                </thead>
                                <tbody id="samplePersonTable">
                                <c:if test="${fn:length(otherSampleInfoList) gt 0}">
                                    <c:forEach items="${otherSampleInfoList}" var="acceptedSample" varStatus="as">
                                        <c:if test="${acceptedSample.sampleFlag eq 1}">
                                            <tr class="regedTr">
                                                <td></td>
                                                <td title="${acceptedSample.sampleName}">
                                                    <input type='hidden' name='sampleName'
                                                           value='${acceptedSample.sampleName}'/>
                                                    <c:if test="${fn:length(acceptedSample.sampleName) gt 20}">
                                                        ${fn:substring(acceptedSample.sampleName,0,19)} ...
                                                    </c:if>
                                                    <c:if test="${fn:length(acceptedSample.sampleName) lt 21}">
                                                        ${acceptedSample.sampleName}
                                                    </c:if>
                                                </td>
                                                <td><input type='hidden' name='sampleType'
                                                           value='${acceptedSample.sampleType}'/>${acceptedSample.sampleTypeName}
                                                </td>
                                                <td><input type='hidden' name='sampleDesc'
                                                           value='${acceptedSample.sampleDesc}'/>${acceptedSample.sampleDesc}
                                                </td>
                                                <td><input type='hidden' name='samplePacking'
                                                           value='${acceptedSample.samplePacking}'/>${acceptedSample.samplePacking}
                                                </td>
                                                <td>
                                                    <input type='hidden' name='sampleProperties'
                                                           value='${acceptedSample.sampleProperties}'/>
                                                    <input type='hidden' name='otherPropertiesDesc'
                                                           value='${acceptedSample.otherPropertiesDesc}'/>
                                                    <c:if test="${acceptedSample.sampleProperties != '9999'}">
                                                        ${acceptedSample.samplePropertiesName}
                                                    </c:if>
                                                    <c:if test="${acceptedSample.sampleProperties eq '9999'}">
                                                        ${acceptedSample.otherPropertiesDesc}
                                                    </c:if>
                                                </td>
                                                <td><input type='hidden' name='refPersonId'
                                                           value='${acceptedSample.refPersonId}'/><input type='hidden'
                                                                                                         name='refPersonName'
                                                                                                         value='${acceptedSample.refPersonName}'/>${acceptedSample.refPersonName}
                                                </td>
                                                <td>
                                                    <input type='hidden' name='extractDatetime'
                                                           value="<fmt:formatDate value='${acceptedSample.extractDatetime}' pattern='yyyy-MM-dd'/>"/>
                                                    <fmt:formatDate value='${acceptedSample.extractDatetime}'
                                                                    pattern='yyyy-MM-dd'/>
                                                </td>
                                                <td>
                                                    <input type='hidden' name='extractPerson'
                                                           value="${acceptedSample.extractPerson}"/>${acceptedSample.extractPerson}
                                                </td>
                                                <td>
                                                    <label class="checkbox-inline">
                                                        <input name="atomFlag" type="checkbox"
                                                               <c:if test="${acceptedSample.atomFlag eq 1}">checked</c:if> />微量
                                                    </label>
                                                    <label class="checkbox-inline">
                                                        <input name="urgentFlag" type="checkbox"
                                                               <c:if test="${acceptedSample.urgentFlag eq 1}">checked</c:if> />加急
                                                    </label>
                                                    <label class="checkbox-inline">
                                                        <input name="difficultFlag" type="checkbox"
                                                               <c:if test="${acceptedSample.difficultFlag eq 1}">checked</c:if> />疑难
                                                    </label>
                                                </td>
                                                <td>
                                                    <span class="color_green"><i class="fa fa-check"></i> 已受理</span>
                                                </td>
                                                <td>
                                                    <input type="hidden" name="sampleId" value="${acceptedSample.id}"/>
                                                    <input type="hidden" name="sampleFlag"
                                                           value="${acceptedSample.sampleFlag}"/>
                                                    -- --
                                                        <%--&nbsp;
                                                        <button name='delSampleBtn' class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i> 删除</button>--%>
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
                                                <td title="${sample.sampleName}">
                                                    <input type='hidden' name='sampleName'
                                                           value='${sample.sampleName}'/>
                                                    <c:if test="${fn:length(sample.sampleName) gt 20}">
                                                        ${fn:substring(sample.sampleName,0,19)} ...
                                                    </c:if>
                                                    <c:if test="${fn:length(sample.sampleName) lt 21}">
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
                                                    <input type='hidden' name='extractPerson'
                                                           value="${sample.extractPerson}"/>${sample.extractPerson}
                                                </td>
                                                <td>
                                                    <label class="checkbox-inline">
                                                        <input name="atomFlag" type="checkbox"/>微量
                                                    </label>
                                                    <label class="checkbox-inline">
                                                        <input name="urgentFlag" type="checkbox"/>加急
                                                    </label>
                                                    <label class="checkbox-inline">
                                                        <input name="difficultFlag" type="checkbox"/>疑难
                                                    </label>
                                                </td>
                                                <td>
                                                    <label class="checkbox-inline">
                                                        <input name="acceptSampleFlag" type="checkbox" checked/>
                                                    </label>
                                                </td>
                                                <td>
                                                    <input type="hidden" name="sampleId" value="${sample.id}"/>
                                                    <input type="hidden" name="sampleFlag"
                                                           value="${sample.sampleFlag}"/>
                                                    <input type="hidden" name="photoPath" value="${sample.photoPath}"/>
                                                    <button name='editSampleBtn' class='btn btn-primary btn-xs'><i
                                                            class='fa fa-pencil'></i> 修改
                                                    </button>
                                                    <button name="uploadPhotoBtn" class="btn btn-primary btn-xs"><i
                                                            class="fa fa-upload"></i>上传照片
                                                    </button>
                                                    <c:if test="${not empty sample.photoPath}">
                                                        <button name="viewPhotoBtn" class="btn btn-primary btn-xs"><i
                                                                class="fa fa-check-circle"></i>查看照片
                                                        </button>
                                                    </c:if>
                                                        <%--&nbsp;
                                                    <button name='delSampleBtn' class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i> 删除</button>--%>
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
                            <a class="btn btn-primary" href="javascript:;" id="newSampleBtn">
                                <i class="fa fa-plus"></i> 添加物证检材
                            </a>

                            <a class="btn btn-warning" id="refreshSampleBtn" name="refreshSampleBtn">
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
                                    <th>提取时间</th>
                                    <th>提取人</th>
                                    <th width="190px">特殊选项 <i class="fa fa-star"></i></th>
                                    <th width="60px"><strong>受理</strong></th>
                                    <th width="100px">操作</th>
                                </tr>
                                </thead>
                                <tbody id="sampleEvidenceTable">
                                <c:if test="${fn:length(otherSampleInfoList) gt 0}">
                                    <c:forEach items="${otherSampleInfoList}" var="acceptedSample" varStatus="as">
                                        <c:if test="${acceptedSample.sampleFlag eq 0}">
                                            <tr class="regedTr">
                                                <td></td>
                                                <td><input type='hidden' name='evidenceNo'
                                                           value='${acceptedSample.evidenceNo}'/>${acceptedSample.evidenceNo}
                                                </td>
                                                <td title="${acceptedSample.sampleName}">
                                                    <input type='hidden' name='sampleName'
                                                           value='${acceptedSample.sampleName}'/>
                                                    <c:if test="${fn:length(acceptedSample.sampleName) gt 20}">
                                                        ${fn:substring(acceptedSample.sampleName,0,19)} ...
                                                    </c:if>
                                                    <c:if test="${fn:length(acceptedSample.sampleName) lt 21}">
                                                        ${acceptedSample.sampleName}
                                                    </c:if>
                                                </td>
                                                <td><input type='hidden' name='sampleType'
                                                           value='${acceptedSample.sampleType}'/>${acceptedSample.sampleTypeName}
                                                </td>
                                                <td><input type='hidden' name='sampleDesc'
                                                           value='${acceptedSample.sampleDesc}'/>${acceptedSample.sampleDesc}
                                                </td>
                                                <td><input type='hidden' name='samplePacking'
                                                           value='${acceptedSample.samplePacking}'/>${acceptedSample.samplePacking}
                                                </td>
                                                <td>
                                                    <input type='hidden' name='sampleProperties'
                                                           value='${acceptedSample.sampleProperties}'/>
                                                    <input type='hidden' name='otherPropertiesDesc'
                                                           value='${acceptedSample.otherPropertiesDesc}'/>
                                                    <c:if test="${acceptedSample.sampleProperties != '9999'}">
                                                        ${acceptedSample.samplePropertiesName}
                                                    </c:if>
                                                    <c:if test="${acceptedSample.sampleProperties eq '9999'}">
                                                        ${acceptedSample.otherPropertiesDesc}
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <input type='hidden' name='extractDatetime'
                                                           value="<fmt:formatDate value='${acceptedSample.extractDatetime}' pattern='yyyy-MM-dd'/>"/>
                                                    <fmt:formatDate value='${acceptedSample.extractDatetime}'
                                                                    pattern='yyyy-MM-dd'/>
                                                </td>
                                                <td>
                                                    <input type='hidden' name='extractPerson'
                                                           value="${acceptedSample.extractPerson}"/>${acceptedSample.extractPerson}
                                                </td>
                                                <td>
                                                    <label class="checkbox-inline">
                                                        <input name="atomFlag" type="checkbox"
                                                               <c:if test="${acceptedSample.atomFlag eq 1}">checked</c:if> />微量
                                                    </label>
                                                    <label class="checkbox-inline">
                                                        <input name="urgentFlag" type="checkbox"
                                                               <c:if test="${acceptedSample.urgentFlag eq 1}">checked</c:if> />加急
                                                    </label>
                                                    <label class="checkbox-inline">
                                                        <input name="difficultFlag" type="checkbox"
                                                               <c:if test="${acceptedSample.difficultFlag eq 1}">checked</c:if> />疑难
                                                    </label>
                                                </td>
                                                <td>
                                                    <span class="color_green"><i class="fa fa-check"></i> 已受理</span>
                                                </td>
                                                <td>
                                                    <input type="hidden" name="sampleId" value="${acceptedSample.id}"/>
                                                    <input type="hidden" name="sampleFlag"
                                                           value="${acceptedSample.sampleFlag}"/>
                                                    -- --
                                                        <%--&nbsp;
                                                        <button name='delSampleBtn' class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i> 删除</button>--%>
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
                                                <td><input type='hidden' name='evidenceNo'
                                                           value='${sample.evidenceNo}'/>${sample.evidenceNo}</td>
                                                <td title="${sample.sampleName}">
                                                    <input type='hidden' name='sampleName'
                                                           value='${sample.sampleName}'/>
                                                    <c:if test="${fn:length(sample.sampleName) gt 20}">
                                                        ${fn:substring(sample.sampleName,0,19)} ...
                                                    </c:if>
                                                    <c:if test="${fn:length(sample.sampleName) lt 21}">
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
                                                           value="${sample.extractPerson}"/>${sample.extractPerson}
                                                </td>
                                                <td>
                                                    <label class="checkbox-inline">
                                                        <input name="atomFlag" type="checkbox"/>微量
                                                    </label>
                                                    <label class="checkbox-inline">
                                                        <input name="urgentFlag" type="checkbox"/>加急
                                                    </label>
                                                    <label class="checkbox-inline">
                                                        <input name="difficultFlag" type="checkbox"/>疑难
                                                    </label>
                                                </td>
                                                <td>
                                                    <label class="checkbox-inline">
                                                        <input name="acceptSampleFlag" type="checkbox" checked/>
                                                    </label>
                                                </td>
                                                <td>
                                                    <input type="hidden" name="sampleId" value="${sample.id}"/>
                                                    <input type="hidden" name="sampleFlag"
                                                           value="${sample.sampleFlag}"/>
                                                    <input type="hidden" name="photoPath" value="${sample.photoPath}"/>
                                                    <button name='editSampleBtn' class='btn btn-primary btn-xs'><i
                                                            class='fa fa-pencil'></i> 修改
                                                    </button>
                                                    <button name="uploadPhotoBtn" class="btn btn-primary btn-xs"><i
                                                            class="fa fa-upload"></i>上传照片
                                                    </button>
                                                    <c:if test="${not empty sample.photoPath}">
                                                        <button name="viewPhotoBtn" class="btn btn-primary btn-xs"><i
                                                                class="fa fa-check-circle"></i>查看照片
                                                        </button>
                                                    </c:if>
                                                        <%--&nbsp;
                                                            <button name='delSampleBtn' class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i> 删除</button>--%>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>

                            <div class="modal fade" id="photoModal" aria-hidden="true" data-backdrop="static"
                                 data-keyboard="false">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                &times;
                                            </button>
                                            <h4 class="modal-title">
                                                上传照片
                                            </h4>
                                        </div>
                                        <div class="modal-body">
                                            <div class="form-group">
                                                <div class="col-sm-8">
                                                    <input type="file" name="photoFile" id="photoFile" class="hide"/>
                                                    <input type="text" id="photoFileTxt" class="form-control"
                                                           readonly="readonly"/>
                                                </div>
                                                <div class="col-sm-4">
                                                    <input type="hidden" id="sampleIdModel" name="sampleIdModel">
                                                    <button class="btn btn-info btn-sm" type="button" id="browserBtn"><i
                                                            class="fa  fa-folder-open"></i> 浏览...
                                                    </button>
                                                    <button class="btn btn-primary btn-sm" name="uploadBtn"
                                                            id="uploadBtn"><i class="fa fa-upload"></i> 上 传
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button data-dismiss="modal" class="btn btn-default" type="button">关闭
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="modal fade" id="viewPhotoModel" aria-hidden="true" data-backdrop="static"
                                 data-keyboard="false">
                                <div class="modal-dialog">
                                    <div class="modal-content">
                                        <div class="modal-header">
                                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                                &times;
                                            </button>
                                            <h4 class="modal-title">
                                                查看照片
                                            </h4>
                                        </div>
                                        <div class="modal-body">
                                            <div class="form-group">
                                                <img id="viewPhoto" name="viewPhoto" src=""
                                                     style="max-width: 100%;max-height: 100%">
                                            </div>
                                        </div>
                                        <div class="modal-footer">
                                            <button data-dismiss="modal" class="btn btn-default" type="button">关闭
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>

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
                                                    <label class="control-label col-md-3">关联人员 <i
                                                            class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <select name="refPerson" class="form-control small required">
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
                                                        <input name="evidenceNo" type="hidden"
                                                               class="form-control small" value="">
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
                                                    <div class="col-md-2 has-error hide">
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
                                                               class="form-control small required" value="">
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
                                            <input type="hidden" name="atmoFlag" value=""/>
                                            <input type="hidden" name="urgentFlag" value=""/>
                                            <input type="hidden" name="difficultFlag" value=""/>
                                            <input type="hidden" name="sampleFlag" value=""/>
                                            <button class="btn btn-success" type="button" id="SaveSampleBtn">确定</button>
                                            <button data-dismiss="modal" class="btn btn-default" type="button">关闭
                                            </button>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <div class="modal fade" id="sampleInfoModal" aria-hidden="true" data-backdrop="static"
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
                                                    <label class="control-label col-md-3">关联人员 <i
                                                            class="fa fa-asterisk color_red"></i></label>
                                                    <div class="col-md-5">
                                                        <select name="refPerson" class="form-control small required">
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
                                                        <input name="evidenceNo" type="hidden"
                                                               class="form-control small " value="">
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
                                            <button class="btn btn-success" type="button" id="SaveSampleInfoBtn">保存
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
                <div class="col-lg-6">
                    <section class="panel">
                        <div class="panel-body">
                            <form id="acceptForm" class="form-horizontal tasi-form" method="get">
                                <div class="form-group">
                                    <div class="col-lg-3">
                                        <button class="btn btn-lg btn-success" id="acceptBtn" type="button"><i
                                                class="fa fa-check"></i> 确认受理
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </section>
                </div>
                <div class="col-lg-6">
                    <section class="panel">
                        <div class="panel-body">
                            <form id="refuseForm" class="form-horizontal tasi-form" method="get">
                                <div class="form-group">
                                    <div class="col-lg-3">
                                        <button class="btn btn-lg btn-warning" id="refuseBtn" type="button"><i
                                                class="fa fa-ban"></i> 退案
                                        </button>
                                    </div>
                                    <div class="col-lg-8">
                                        <textarea class="form-control" rows="2" id="refuseReason"
                                                  placeholder="退案说明">${consignment.refuseReason}</textarea>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </section>
                </div>

            </div>
        </div>
        <!-- END ROW  -->

        <div class="modal fade" id="AcceptedModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h4 class="modal-title">
                            消息提示
                        </h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal tasi-form">
                            <div class="form-group m-bot19"></div>
                            <div class="form-group m-bot19">
                                <div class="col-md-12 text-center">
                                    <h3 class="alert alert-success"><Strong>受理成功！</Strong></h3>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <i class="fa fa-hand-o-right"></i>
                        <button class="btn btn-success" type="button" id="OpenAcceptDocBtn">
                            <i class="fa fa-file-text-o"></i> 受理登记表
                        </button>

                        <c:choose>
                            <c:when test="${consignment.additionalFlag eq 1}">
                                <button class="btn btn-success" type="button" id="repairCirculationRecordDocBtn">
                                    <i class="fa fa-file-text-o"></i> 检材样本流转记录表
                                </button>
                            </c:when>
                            <c:otherwise>
                                <button class="btn btn-success" type="button" id="CirculationRecordDocBtn">
                                    <i class="fa fa-file-text-o"></i> 检材样本流转记录表
                                </button>
                            </c:otherwise>
                        </c:choose>

                        <button class="btn btn-success" type="button" id="downloadIdentifyApproveFormDocBtn">
                            <i class="fa fa-file-text-o"></i> 鉴定文书审批表
                        </button>

                        <c:choose>
                            <c:when test="${consignment.additionalFlag eq 1}">
                                <button class="btn btn-success" type="button" id="repairDownloadExcelBtn">
                                    <i class="fa fa-file-text-o"></i> 条形码列表
                                </button>
                            </c:when>
                            <c:otherwise>
                                <button class="btn btn-success" type="button" id="downloadExcelBtn">
                                    <i class="fa fa-file-text-o"></i> 条形码列表
                                </button>
                            </c:otherwise>
                        </c:choose>


                        <button class="btn btn-default" type="button" id="BackPendingListBtn">
                            <i class="fa fa-list-alt"></i> 返回
                        </button>
                    </div>
                </div>
            </div>
        </div>
        </div>
        </div>
    </section>
</section>

<%@ include file="../../common/script.jsp" %>
<script src="<%=path%>/js/center/newAcceptCase.js"></script>
<script>

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
                url: "<%=path%>/center/7/selectDelagatorQuery.html?delegateOrgId=" + delegateOrgId + "&delegatorName=" + delegatorName,
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

            var delegateOrgId = $("#delegateOrgId").val();
            var delegatorName = $("#delegator2 option:selected").val();

            if (delegatorName == "") {
                $("#delegator2Position").val("");
                $("#delegator2Cname").val("");
                $("#delegator2Cno").val("");
                $("#delegator2Phone").val("");
            }

            $.ajax({
                url: "<%=path%>/center/7/selectDelagatorQuery.html?delegateOrgId=" + delegateOrgId + "&delegatorName=" + delegatorName,
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

        /* $("#sampleFlagEvidence").on("click", function(){
         $("input[name='sampleName']", "#sampleModal").removeAttr("placeholder");
         });

         $("#sampleFlagPerson").on("click", function(){
         $("input[name='sampleName']", "#sampleModal").attr("placeholder","例如：XXX血样或口腔拭子");
         });*/

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

    //补送下载样本流转记录
    $("#repairCirculationRecordDocBtn").on("click", function () {
        var caseId = $("#caseId", "#caseinfo_form").val();
        var consignmentId = $("#consignmentId", "#consignment_form").val();
        location.href = "../../center/caseinfo/repairCirculationRecordDoc.html?caseId=" + caseId + "&consignmentId=" + consignmentId;
    });

    //    $("#newPersonBtn").on("click", function(){
    //        console.log(123)
    //        $("input[name='sampleName']", "#sampleModal").attr("placeholder","例如：XXX血样或口腔拭子");
    //        /*AddNewPersonRow();*/
    //    });

    $("#downloadIdentifyApproveFormDocBtn").on("click", function () {
        var caseId = $("#caseId", "#caseinfo_form").val();
        location.href = "<%=path%>/center/6/identifyApprovalFrom.html?caseId=" + caseId;
    });

    $("#downloadExcelBtn").on("click", function () {
        var caseId = $("#caseId", "#caseinfo_form").val();
        location.href = "<%=path%>/center/6/downloadSampleExcel.html?caseId=" + caseId;
    });

    //补送下载条形码列表
    $("#repairDownloadExcelBtn").on("click", function () {
        var caseId = $("#caseId", "#caseinfo_form").val();
        location.href = "<%=path%>/center/6/repairDownloadSampleExcel.html?caseId=" + caseId;
    });

    //刷新现堪获取的物证
    $("#refreshSampleBtn").on("click", function () {

        $('#refreshSampleBtn').attr("disabled", "disabled");

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

                        var atomFlag = $("input[name='atomFlag']", "#sampleModal").val();
                        var urgentFlag = $("input[name='urgentFlag']", "#sampleModal").val();
                        var difficultFlag = $("input[name='difficultFlag']", "#sampleModal").val();
                        newRowHtml += "<td><label class='checkbox-inline'><input name='atomFlag' type='checkbox' " + (atomFlag == "1" ? "checked" : "") + "/>微量</label>"
                                + "<label class='checkbox-inline'><input name='urgentFlag' type='checkbox' " + (urgentFlag == "1" ? "checked" : "") + "/>加急</label>"
                                + "<label class='checkbox-inline'><input name='difficultFlag' type='checkbox'" + (difficultFlag == "1" ? "checked" : "") + " />疑难</label></td>";

                        newRowHtml += "<td><label class='checkbox-inline'><input name='acceptSampleFlag' type='checkbox' checked/></label></td>";

                        newRowHtml += "<td><button name='editSample' class='btn btn-primary btn-xs'><i class='fa fa-pencil'></i> 修改</button>  <button name='delSampleBtn' class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i> 删除</button></td>";
                        newRowHtml += "<td style='display: none'><input type='hidden' name='sampleFlag' value='" + sampleFlag + "'/>" + sampleFlag + "</td>";
                        $("#sampleEvidenceTable").append("<tr>" + newRowHtml + "</tr>");

                        generateSampleEvidenceIdx();

                    });
                    $("button[name='editSample']", "#sampleEvidenceTable").on("click", function () {
                        EditSampleInfo(this);
                    });

                    $("button[name='delSampleBtn']", "#sampleEvidenceTable").on("click", function () {
                        if (!confirm("确认删除吗")) {
                            return;
                        }
                        DelSampleInfoRow(this);
                    });
                }
            },
            error: function (e) {
                alert(e);
            }
        });

    });

    $("button[name='editSampleBtn']", "#sampleEvidenceTable").on("click", function () {
        EditSampleInfo(this);
    });

    $("button[name='delSampleBtn']", "#sampleEvidenceTable").on("click", function () {
        DelSampleInfoRow(this);
    });

    function DelSampleInfoRow(obj) {
        var sampleId = $("input[name='sampleId']", $(obj).parent()).val();
        if (sampleId == null || sampleId == "" || sampleId == 0 || sampleId == "0") {
            $(obj).parents("tr").remove();
            return;
        }

    }

    function DelSampleRow(obj) {
        var sampleId = $("input[name='sampleId']", $(obj).parent()).val();
        if (sampleId == null || sampleId == "" || sampleId == 0 || sampleId == "0") {
            $(obj).parents("tr").remove();
            return;
        }
    }

    function EditSampleRow(obj) {
        var $curTR = $(obj).parents("tr");
        var sample = {};
        sample.id = $("input[name='sampleId']", $curTR).val();
        sample.evidenceNo = $("input[name='evidenceNo']", $curTR).val();
        sample.sampleType = $("input[name='sampleType']", $curTR).val();
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
        var trIdx = $curTR.index();
        newSample(sample, "edit", trIdx);
    }

    function newSample(sample, operateType, rownum) {
        $("div.has-error", "#sampleModal").addClass("hide");

        $("input[name='sampleId']", "#sampleModal").val(sample.id);
        if (sample.sampleType != null && sample.sampleType != "") {
            $("select[name='sampleType']", "#sampleModal").val(sample.sampleType);
        } else {
            $("select[name='sampleType']", "#sampleModal").prop('selectedIndex', 0);
        }
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
                $("select[name='refPerson']", "#sampleModal").append("<option value='" + refPersonName + "' selected>" + refPersonName + "</option>");
            } else {
                $("select[name='refPerson']", "#sampleModal").append("<option value='" + refPersonName + "'>" + refPersonName + "</option>");
            }
        });

        if (sample.sampleFlag == "" || sample.sampleFlag == "0") {
            /*$("#sampleFlagPerson", "#sampleModal").removeAttr("checked");
             $("#sampleFlagEvidence", "#sampleModal").prop("checked", true);*/
            $("input[name='sampleFlag']", "#sampleModal").val(sample.sampleFlag);
            $("input[name='evidenceNo']", "#sampleModal").val(sample.evidenceNo);
            $("#divRefPerson").hide();
        } else {
            /*$("#sampleFlagEvidence", "#sampleModal").removeAttr("checked");
             $("#sampleFlagPerson", "#sampleModal").prop("checked", true);*/
            $("input[name='sampleFlag']", "#sampleModal").val(sample.sampleFlag);
            $("#divRefPerson").show();
        }

        $("input[name='sampleOperateType']", "#sampleModal").val(operateType);
        $("input[name='sampleTableRownum']", "#sampleModal").val(rownum);

        $("#sampleModal").modal('show');
    }

    function EditSample(obj) {
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

    function EditSampleInfo(obj) {
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
        newSampleInfoRow(sample, "edit", trIdx);
    }

    function newSampleInfoRow(sample, operateType, rownum) {

        $("div.has-error", "#sampleInfoModal").addClass("hide");

        $("input[name='sampleId']", "#sampleInfoModal").val(sample.id);
        $("select[name='sampleType']", "#sampleInfoModal").val(sample.sampleType);
        $("input[name='evidenceNo']", "#sampleInfoModal").val(sample.evidenceNo);
        $("input[name='sampleName']", "#sampleInfoModal").val(sample.sampleName);
        $("input[name='extractDatetime']", "#sampleInfoModal").val(sample.extractDatetime);
        $("input[name='extractPerson']", "#sampleInfoModal").val(sample.extractPerson);
        $("input[name='sampleDesc']", "#sampleInfoModal").val(sample.sampleDesc);
        if (sample.samplePacking == "") {
            $("input[name='samplePacking']", "#sampleInfoModal").val("物证袋");
        } else {
            $("input[name='samplePacking']", "#sampleInfoModal").val(sample.samplePacking);
        }
        if (sample.sampleProperties != "") {
            $("select[name='sampleProperties']", "#sampleInfoModal").val(sample.sampleProperties);

            if (sample.sampleProperties == "9999") {
                $("input[name='otherPropertiesDesc']", "#sampleInfoModal").val(sample.otherPropertiesDesc);
                $("input[name='otherPropertiesDesc']", "#sampleInfoModal").removeClass("hide");
            } else {
                $("input[name='otherPropertiesDesc']", "#sampleInfoModal").val("");
                $("input[name='otherPropertiesDesc']", "#sampleInfoModal").addClass("hide");
            }
        } else {
            $("select[name='sampleProperties']", "#sampleInfoModal").prop('selectedIndex', 0);

            $("input[name='otherPropertiesDesc']", "#sampleInfoModal").val("");
            $("input[name='otherPropertiesDesc']", "#sampleInfoModal").addClass("hide");
        }

        $("select[name='refPerson']", "#sampleInfoModal").empty();
        $("select[name='refPerson']", "#sampleInfoModal").append("<option value=''>请选择</option>")
        $("input[name='personName']", "#personInfoTable").each(function () {
            var refPersonName = $(this).val();
            if (operateType == "edit" && refPersonName == sample.refPersonName) {
                $("select[name='refPerson']", "#sampleInfoModal").append("<option value='" + refPersonName + "' selected>" + refPersonName + " </option>");

            } else {
                $("select[name='refPerson']", "#sampleInfoModal").append("<option value='" + refPersonName + "'>" + refPersonName + "</option>");
            }
        });

        if (sample.sampleFlag == "" || sample.sampleFlag == "0") {
            $("input[name='sampleFlag']", "#sampleInfoModal").val(sample.sampleFlag);
            $("input[name='evidenceNo']", "#sampleInfoModal").val(sample.evidenceNo);

            $("#divRefPerson").hide();
        } else {
            $("input[name='sampleFlag']", "#sampleInfoModal").val(sample.sampleFlag);
            $("#divRefPerson").show();
        }

        $("input[name='sampleOperateType']", "#sampleInfoModal").val(operateType);
        $("input[name='sampleTableRownum']", "#sampleInfoModal").val(rownum);
        $("#sampleInfoModal").modal();
    }


    $("#SaveSampleInfoBtn").on("click", function () {
        var sampleName = $("input[name='sampleName']", "#sampleInfoModal").val().trim();
        var sampleNameArr = $("input[name='sampleName'][value='" + sampleName + "']", "#sampleEvidenceTable");

        if (sampleNameArr.length > 0) {
            if (confirm("此检材名称已存在，是否继续添加！")) {
                SaveSampleInfoRow();
            } else {
                $("input[name='sampleName']", "#sampleInfoModal").focus();
            }
        } else {
            SaveSampleInfoRow();
        }
    });

    function SaveSampleInfoRow() {
        var errorCnt = 0;

        var sampleTypeVal = $("select[name='sampleType']", "#sampleInfoModal").find("option:selected").val();
        if (sampleTypeVal == "" || $.trim(sampleTypeVal) == "") {
            $("div.has-error", $("select[name='sampleType']", "#sampleInfoModal").parents("div.form-group")).removeClass("hide");
            errorCnt++;
        } else {
            $("div.has-error", $("select[name='sampleType']", "#sampleInfoModal").parents("div.form-group")).addClass("hide");
        }

        var sampleFlagVal = $("input[name='sampleFlag']", "#sampleInfoModal").val();

        if (sampleFlagVal == "1") {
            var refPeresonVal = $("select[name='refPerson']", "#sampleInfoModal").find("option:selected").val();
            if (refPeresonVal == "" || $.trim(refPeresonVal) == "") {
                $("div.has-error", $("select[name='refPerson']", "#sampleInfoModal").parents("div.form-group")).removeClass("hide");
                errorCnt++;
            } else {

                $("div.has-error", $("select[name='refPerson']", "#sampleInfoModal").parents("div.form-group")).addClass("hide");
            }
        }

        var samplePropertiesVal = $("select[name='sampleProperties']", "#sampleInfoModal").find("option:selected").val();
        var otherPropertiesDescVal = $("input[name='otherPropertiesDesc']", "#sampleInfoModal").val();

        if (samplePropertiesVal == "9999") {
            if (otherPropertiesDescVal == "" || $.trim(otherPropertiesDescVal) == "") {
                $("div.has-error", $("input[name='otherPropertiesDesc']", "#sampleInfoModal").parents("div.form-group")).removeClass("hide");
            } else {
                $("div.has-error", $("input[name='otherPropertiesDesc']", "#sampleInfoModal").parents("div.form-group")).addClass("hide");
            }
        }

        if (errorCnt > 0) {
            return;
        }

        var sampleId = $("input[name='sampleId']", "#sampleInfoModal").val();
        var sampleTypeCode = $("select[name='sampleType']", "#sampleInfoModal").find("option:selected").val();

        var sampleTypeName = $("select[name='sampleType']", "#sampleInfoModal").find("option:selected").text();
        var sampleName = $("input[name='sampleName']", "#sampleInfoModal").val();
        var evidenceNo = $("input[name='evidenceNo']", "#sampleInfoModal").val();
        var extractDatetime = $("input[name='extractDatetime']", "#sampleInfoModal").val();
        var extractPerson = $("input[name='extractPerson']", "#sampleInfoModal").val();
        var sampleDesc = $("input[name='sampleDesc']", "#sampleInfoModal").val();
        var samplePacking = $("input[name='samplePacking']", "#sampleInfoModal").val();
        var samplePropertiesName = $("select[name='sampleProperties']", "#sampleInfoModal").find("option:selected").text();
        //var refPersonCode = $("select[name='refPerson']", "#sampleModal").find("option:selected").val();
        var refPersonName = $("select[name='refPerson']", "#sampleInfoModal").find("option:selected").text();


        var newRowHtml = "<td></td>";

        newRowHtml += "<td><input type='hidden' name='evidenceNo' value='" + $.trim(evidenceNo) + "'/>" + evidenceNo + "</td>";
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

        var atomFlag = $("input[name='atomFlag']", "#sampleInfoModal").val();
        var urgentFlag = $("input[name='urgentFlag']", "#sampleInfoModal").val();
        var difficultFlag = $("input[name='difficultFlag']", "#sampleInfoModal").val();
        newRowHtml += "<td><label class='checkbox-inline'><input name='atomFlag' type='checkbox' " + (atomFlag == "1" ? "checked" : "") + "/>微量</label>"
                + "<label class='checkbox-inline'><input name='urgentFlag' type='checkbox' " + (urgentFlag == "1" ? "checked" : "") + "/>加急</label>"
                + "<label class='checkbox-inline'><input name='difficultFlag' type='checkbox'" + (difficultFlag == "1" ? "checked" : "") + " />疑难</label></td>";

        newRowHtml += "<td><label class='checkbox-inline'><input name='acceptSampleFlag' type='checkbox' checked/></label></td>";

        newRowHtml += "<td><input type='hidden' name='sampleId' value='" + sampleId + "'/><input type='hidden' name='sampleFlag' value='" + sampleFlagVal + "'/><button name='editSampleBtn' class='btn btn-primary btn-xs'><i class='fa fa-pencil'></i> 修改</button>  <button name='delSampleInfoBtn' class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i> 删除</button></td>";

        var operateType = $("input[name='sampleOperateType']", "#sampleInfoModal").val();
        if ("add" == operateType) {
            $("#samplePersonTable").append("<tr>" + newRowHtml + "</tr>");
            generateSamplePersonIdx();
        } else {
            var trIdx = $("input[name='sampleTableRownum']", "#sampleInfoModal").val();
            if (sampleFlagVal == 1) {
                $("tr:eq(" + trIdx + ")", "#samplePersonTable").html(newRowHtml);
                generateSamplePersonIdx();
            } else {
                $("tr:eq(" + trIdx + ")", "#sampleEvidenceTable").html(newRowHtml);
                generateSampleEvidenceIdx();
            }
        }

        $("button[name='editSampleBtn']", "#sampleEvidenceTable").on("click", function () {
            $("input[name='sampleFlag']", "#sampleInfoModal").val('0');
            EditSampleInfo(this);
        });
        $("button[name='delSampleInfoBtn']", "#sampleEvidenceTable").on("click", function () {
            if (!confirm("确认删除吗")) {
                return;
            }
            DelSampleInfoRow(this);
        });

        function DelSampleInfoRow(obj) {
            var sampleId = $("input[name='sampleId']", $(obj).parent()).val();

            if (sampleId == null || sampleId == "" || sampleId == 0 || sampleId == "0") {
                $(obj).parents("tr").remove();
                return;
            } else {
                deleteSample(sampleId);
            }
        }

        $("#sampleInfoModal").modal('hide');
    }

    function deleteSample(sampleId) {

        $.ajax({
            url: "<%=path%>/wt/reg/delSample.html?sampleId=" + sampleId,
            type: "post",
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            success: function (data) {
                alert("删除成功！")
                location.reload();
            },
            error: function (e) {
                alert(e);
            }
        });
    }

</script>
</body>
</html>
