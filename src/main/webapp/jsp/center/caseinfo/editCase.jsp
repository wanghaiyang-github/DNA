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


                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件受理编号 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
                                        <input type="text" id="caseNo" name="caseNo" class="form-control required"
                                               value="${caseInfo.caseNo}">
                                    </div>
                                    <div class="col-sm-2 has-error hide">
                                        <p class="help-block">必填项</p>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件现堪编号 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-5">
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
                                               <c:if test="${consignment.additionalFlag eq 1}">readonly="readonly"</c:if>>
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
                                    <div class="col-sm-8">
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
                                    <label class="col-sm-2 col-sm-2 control-label">委托单位</label>
                                    <div class="col-sm-5">
                                        <input type="text" class="form-control" id="delegateOrgDesc"
                                               value="${consignment.delegateOrgDesc}"/>
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
                            <c:if test="${fromUrl eq 'acceptedList'}">
                                <a class="btn btn-primary" href="javascript:;" id="newPerBtn">
                                    <i class="fa fa-plus"></i> 添加
                                </a>
                            </c:if>
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
                                    <c:if test="${fromUrl eq 'acceptedList'}">
                                        <th width="100px">操作</th>
                                    </c:if>
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
                                            <c:if test="${fromUrl eq 'acceptedList'}">
                                                <td>
                                                    <input type="hidden" name="personId" value="${acceptPerson.id}"/>
                                                    -- --
                                                        <%--&nbsp;
                                                        <button name='delPerBtn' class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i> 删除</button>--%>
                                                </td>
                                            </c:if>
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
                                            <c:if test="${fromUrl eq 'acceptedList'}">
                                                <td>
                                                    <input type="hidden" name="personId" value="${person.id}"/>
                                                    <input type="hidden" name="personNo" value="${person.personNo}"/>
                                                    <button name='editPerBtn' class='btn btn-primary btn-xs'><i
                                                            class='fa fa-pencil'></i> 修改
                                                    </button>
                                                        <%--&nbsp;
                                                        <button name='delPerBtn' class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i> 删除</button>--%>
                                                </td>
                                            </c:if>
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
                                                            class="fa fa-asterisk color_red required"></i></label>
                                                    <div class="col-md-5">
                                                        <select name="personGender" class="form-control small">
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
                                                               style="width:160px; display: inline" value="">
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
                            <c:if test="${fromUrl eq 'acceptedList'}">
                                <a class="btn btn-primary" href="javascript:;" id="newPersonBtn">
                                    <i class="fa fa-plus"></i> 添加人员检材
                                </a>
                            </c:if>

                            <c:if test="${consignment.additionalFlag eq 1}">
                                <a class="btn btn-info" id="showRegedTrBtn">
                                    <span id="hiddenSpan"><i class="fa fa-angle-double-up"></i> 只显示本次委托检材</span>
                                    <span id="showSpan" class="hide"><i
                                            class="fa fa-angle-double-down"></i> 显示全部检材</span>
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
                                    <th width="190px">特殊选项 <i class="fa fa-star"></i></th>
                                    <c:if test="${fromUrl eq 'acceptedList'}">
                                        <th width="100px">操作</th>
                                    </c:if>
                                </tr>
                                </thead>
                                <tbody id="samplePersonTable">
                                <c:if test="${fn:length(otherSampleInfoList) gt 0}">
                                    <c:forEach items="${otherSampleInfoList}" var="acceptedSample" varStatus="as">
                                        <c:if test="${acceptedSample.sampleFlag eq 1}">
                                            <tr class="regedTr">
                                                <td></td>
                                                <td><input type='hidden' name='sampleNo'
                                                           value='${acceptedSample.sampleNo}'/>${acceptedSample.sampleNo}
                                                </td>
                                                <td title="${acceptedSample.sampleName}">
                                                    <input type='hidden' name='sampleName'
                                                           value='${acceptedSample.sampleName}'/>
                                                    <c:if test="${fn:length(acceptedSample.sampleName) gt 15}">
                                                        ${fn:substring(acceptedSample.sampleName,0,14)} ...
                                                    </c:if>
                                                    <c:if test="${fn:length(acceptedSample.sampleName) lt 16}">
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
                                                <c:if test="${fromUrl eq 'acceptedList'}">
                                                    <td>
                                                        <input type="hidden" name="sampleId"
                                                               value="${acceptedSample.id}"/>
                                                        <input type="hidden" name="sampleFlag"
                                                               value="${acceptedSample.sampleFlag}"/>
                                                        -- --
                                                            <%--&nbsp;
                                                            <button name='delSampleBtn' class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i> 删除</button>--%>
                                                    </td>
                                                </c:if>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${fn:length(sampleInfoList) gt 0}">
                                    <c:forEach items="${sampleInfoList}" var="sample" varStatus="p">
                                        <c:if test="${sample.sampleFlag eq 1}">
                                            <tr>
                                                <td></td>
                                                <td><input type='hidden' name='sampleNo'
                                                           value='${sample.sampleNo}'/>${sample.sampleNo}</td>
                                                <td title="${sample.sampleName}">
                                                    <input type='hidden' name='sampleName'
                                                           value='${sample.sampleName}'/>
                                                    <c:if test="${fn:length(sample.sampleName) gt 15}">
                                                        ${fn:substring(sample.sampleName,0,14)} ...
                                                    </c:if>
                                                    <c:if test="${fn:length(sample.sampleName) lt 16}">
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
                                                        <input name="atomFlag" type="checkbox"
                                                               <c:if test="${sample.atomFlag eq 1}">checked</c:if> /> 微量
                                                    </label>
                                                    <label class="checkbox-inline">
                                                        <input name="urgentFlag" type="checkbox"
                                                               <c:if test="${sample.urgentFlag eq 1}">checked</c:if> />
                                                        加急
                                                    </label>
                                                    <label class="checkbox-inline">
                                                        <input name="difficultFlag" type="checkbox"
                                                               <c:if test="${sample.difficultFlag eq 1}">checked</c:if> />
                                                        疑难
                                                    </label>
                                                </td>
                                                <c:if test="${fromUrl eq 'acceptedList'}">
                                                    <td>
                                                        <input type="hidden" name="sampleId" value="${sample.id}"/>
                                                        <input type="hidden" name="sampleFlag"
                                                               value="${sample.sampleFlag}"/>
                                                        <button name='editSampleBtn' class='btn btn-primary btn-xs'><i
                                                                class='fa fa-pencil'></i> 修改
                                                        </button>
                                                        <button name="uploadPhotoBtn" class="btn btn-primary btn-xs"><i
                                                                class="fa fa-upload"></i>上传照片
                                                        </button>
                                                        <c:if test="${not empty sample.photoPath}">
                                                            <button name="viewPhotoBtn" class="btn btn-primary btn-xs">
                                                                <i class="fa fa-check-circle"></i>查看照片
                                                            </button>
                                                        </c:if>
                                                    </td>
                                                </c:if>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>

                            <c:if test="${fromUrl eq 'acceptedList'}">
                                <a class="btn btn-primary" href="javascript:;" id="newSampleBtn">
                                    <i class="fa fa-plus"></i> 添加物证检材
                                </a>
                            </c:if>
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
                                    <th width="190px">特殊选项 <i class="fa fa-star"></i></th>
                                    <c:if test="${fromUrl eq 'acceptedList'}">
                                        <th width="100px">操作</th>
                                    </c:if>
                                </tr>
                                </thead>
                                <tbody id="sampleEvidenceTable">
                                <c:if test="${fn:length(otherSampleInfoList) gt 0}">
                                    <c:forEach items="${otherSampleInfoList}" var="acceptedSample" varStatus="as">
                                        <c:if test="${acceptedSample.sampleFlag eq 0}">
                                            <tr class="regedTr">
                                                <td></td>
                                                <td><input type='hidden' name='sampleNo'
                                                           value='${acceptedSample.sampleNo}'/>${acceptedSample.sampleNo}
                                                </td>
                                                <td title="${acceptedSample.sampleName}">
                                                    <input type='hidden' name='sampleName'
                                                           value='${acceptedSample.sampleName}'/>
                                                    <c:if test="${fn:length(acceptedSample.sampleName) gt 15}">
                                                        ${fn:substring(acceptedSample.sampleName,0,14)} ...
                                                    </c:if>
                                                    <c:if test="${fn:length(acceptedSample.sampleName) lt 16}">
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
                                                <c:if test="${fromUrl eq 'acceptedList'}">
                                                    <td>
                                                        <input type="hidden" name="sampleId"
                                                               value="${acceptedSample.id}"/>
                                                        <input type="hidden" name="sampleFlag"
                                                               value="${acceptedSample.sampleFlag}"/>
                                                        -- --
                                                    </td>
                                                </c:if>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${fn:length(sampleInfoList) gt 0}">
                                    <c:forEach items="${sampleInfoList}" var="sample" varStatus="p">
                                        <c:if test="${sample.sampleFlag eq 0}">
                                            <tr>
                                                <td></td>
                                                <td name='sampleNo'><input type='hidden' name='sampleNo'
                                                                           value='${sample.sampleNo}'/>${sample.sampleNo}
                                                </td>
                                                <td title="${sample.sampleName}">
                                                    <input type='hidden' name='sampleName'
                                                           value='${sample.sampleName}'/>
                                                    <c:if test="${fn:length(sample.sampleName) gt 15}">
                                                        ${fn:substring(sample.sampleName,0,10)} ...
                                                    </c:if>
                                                    <c:if test="${fn:length(sample.sampleName) lt 16}">
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
                                                        <input name="atomFlag" type="checkbox"
                                                               <c:if test="${sample.atomFlag eq 1}">checked</c:if> /> 微量
                                                    </label>
                                                    <label class="checkbox-inline">
                                                        <input name="urgentFlag" type="checkbox"
                                                               <c:if test="${sample.urgentFlag eq 1}">checked</c:if> />
                                                        加急
                                                    </label>
                                                    <label class="checkbox-inline">
                                                        <input name="difficultFlag" type="checkbox"
                                                               <c:if test="${sample.difficultFlag eq 1}">checked</c:if> />
                                                        疑难
                                                    </label>
                                                </td>
                                                <c:if test="${fromUrl eq 'acceptedList'}">
                                                    <td>
                                                        <input type="hidden" name="sampleId" value="${sample.id}"/>
                                                        <input type="hidden" name="sampleFlag"
                                                               value="${sample.sampleFlag}"/>
                                                        <button name='editSampleBtn' class='btn btn-primary btn-xs'><i
                                                                class='fa fa-pencil'></i> 修改
                                                        </button>
                                                        <button name="uploadPhotoBtn" class="btn btn-primary btn-xs"><i
                                                                class="fa fa-upload"></i>上传照片
                                                        </button>
                                                        <c:if test="${not empty sample.photoPath}">
                                                            <button name="viewPhotoBtn" class="btn btn-primary btn-xs">
                                                                <i class="fa fa-check-circle"></i>查看照片
                                                            </button>
                                                        </c:if>

                                                        <button name='splitBtn' class='btn btn-primary btn-xs splitBtn'>
                                                            <i class='fa fa-pencil'></i> 拆分
                                                        </button>
                                                    </td>
                                                </c:if>
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
                                                            <input type="radio" id="sampleFlagEvidence"
                                                                   name="sampleFlag" value="0" checked> 物证
                                                        </label>
                                                        <label class="checkbox-inline">
                                                            <input type="radio" id="sampleFlagPerson" name="sampleFlag"
                                                                   value="1"> 人员
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
                                                    <label class="control-label col-md-3">检材编号</label>
                                                    <div class="col-md-5">
                                                        <input name="sampleNo" type="text" class="form-control small"
                                                               value="" readonly="readonly">
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
                        </div>
                    </section>
                </div>
            </div>
            <!-- END ROW  -->
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <div class="panel-body">
                            <form id="acceptForm" class="form-horizontal tasi-form" method="get">
                                <div class="form-group">
                                    <div class="col-lg-offset-6 col-lg-10">
                                        <input type="hidden" id="fromUrl" value="${fromUrl}"/>
                                        <c:choose>
                                            <c:when test="${fromUrl eq 'acceptedList'}">
                                                <button class="btn btn-lg btn-success" id="saveBtn" type="button"><i
                                                        class="fa fa-check"></i> 保 存
                                                </button>
                                                <button class="btn btn-lg btn-info" id="backBtn" type="button"><i
                                                        class="fa fa-reply"></i> 返 回
                                                </button>
                                            </c:when>
                                            <c:otherwise>
                                                <button class="btn btn-lg btn-info" type="button"
                                                        onclick="javascript:history.go(-1);"><i class="fa fa-reply"></i>
                                                    返 回
                                                </button>
                                            </c:otherwise>
                                        </c:choose>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </section>
                </div>
            </div>


            <div class="modal fade" id="EditCaseModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
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
                                        <h3 class="alert alert-success"><Strong>保存成功！</Strong></h3>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <i class="fa fa-hand-o-right"></i>
                            <button data-dismiss="modal" class="btn btn-default" type="button" id="backwardBtn"><i
                                    class="fa fa-reply"></i> 返 回
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</section>

<%@ include file="../../common/script.jsp" %>
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
        'use strict';

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
                        location.href = "<%=path%>/center/caseinfo/editCase.html?consignmentId=" + consignmentId + "&fromUrl=acceptedList";
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

        $("#sampleFlagEvidence").on("click", function () {
            $("input[name='sampleName']", "#sampleModal").removeAttr("placeholder");
        });

        $("#sampleFlagPerson").on("click", function () {
            $("input[name='sampleName']", "#sampleModal").attr("placeholder", "例如：XXX血样或口腔拭子");
        });

        function GetCaseInfo() {
            var caseInfo = {};
            caseInfo.id = $("#caseId", "#caseinfo_form").val();

            //补送案件受理时，案件信息只传case.id一个参数即可
            if ($("#additionalFlag", "#consignment_form").val() == "0") {
                caseInfo.caseNo = $("#caseNo", "#caseinfo_form").val();
                caseInfo.caseName = $("#caseName", "#caseinfo_form").val();
                caseInfo.caseXkNo = $("#caseXkNo", "#caseinfo_form").val();
                caseInfo.caseLocation = $("#caseLocation", "#caseinfo_form").val();
                caseInfo.caseDatetime = $("#caseDatetime", "#caseinfo_form").val();
                caseInfo.caseBrief = $("#caseBrief", "#caseinfo_form").val();
                caseInfo.caseType = $("#caseType", "#caseinfo_form").val();
                caseInfo.caseProperty = $("#caseProperty", "#caseinfo_form").val();
                caseInfo.caseLevel = $("#caseLevel", "#caseinfo_form").val();
                caseInfo.jiajiFlag = ($("#jiajiFlag", "#caseinfo_form").is(":checked") == true) ? "1" : "0";
                caseInfo.caseLevel = $("#caseLevel", "#caseinfo_form").val();
                caseInfo.caseSpecialRemark = $("#caseSpecialRemark", "#caseinfo_form").val();
            }

            return caseInfo;
        }

        function GetConsignment() {
            var consignment = {};
            consignment.id = $("#consignmentId", "#consignment_form").val();
            consignment.delegateOrg = $("#delegateOrg", "#consignment_form").val();
            consignment.delegateOrgDesc = $("#delegateOrgDesc", "#consignment_form").val();
            consignment.delegateOrgPhone = $("#delegateOrgPhone", "#consignment_form").val();
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

            var $personTR = $("tr", "#personInfoTable");
            var personCnt = $personTR.length;
            var person;
            for (var i = 0; i < personCnt; i++) {
                person = {};
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

        function GetSample() {
            var sampleArr = new Array();

            var sample;
            if ($("tr", "#samplePersonTable").length > 0) {
                var $samplePersonTRArr = $("tr", "#samplePersonTable").not(".regedTr");
                var samplePersonCnt = $samplePersonTRArr.length;
                var $samplePersonTR;
                for (var i = 0; i < samplePersonCnt; i++) {
                    sample = {};
                    $samplePersonTR = $($samplePersonTRArr.get(i));
                    sample.id = $("input[name='sampleId']", $samplePersonTR).val();
                    sample.sampleNo = $("input[name='sampleNo']", $samplePersonTR).val();
                    sample.sampleName = $("input[name='sampleName']", $samplePersonTR).val();
                    sample.sampleType = $("input[name='sampleType']", $samplePersonTR).val();
                    sample.extractDatetime = $("input[name='extractDatetime']", $samplePersonTR).val();
                    sample.extractPerson = $("input[name='extractPerson']", $samplePersonTR).val();
                    sample.sampleDesc = $("input[name='sampleDesc']", $samplePersonTR).val();
                    sample.samplePacking = $("input[name='samplePacking']", $samplePersonTR).val();
                    sample.sampleProperties = $("input[name='sampleProperties']", $samplePersonTR).val();
                    sample.otherPropertiesDesc = $("input[name='otherPropertiesDesc']", $samplePersonTR).val();
                    //sample.refPersonId = $("input[name='refPersonId']", $samplePersonTR.get(i)).val();
                    sample.refPersonName = $("input[name='refPersonName']", $samplePersonTR).val();
                    sample.sampleFlag = "1";
                    sample.atomFlag = ($("input[name='atomFlag']", $samplePersonTR).is(":checked") == true) ? "1" : "0";
                    sample.urgentFlag = ($("input[name='urgentFlag']", $samplePersonTR).is(":checked") == true) ? "1" : "0";
                    sample.difficultFlag = ($("input[name='difficultFlag']", $samplePersonTR).is(":checked") == true) ? "1" : "0";

                    sampleArr.push(sample);
                }
            }

            if ($("tr", "#sampleEvidenceTable").length > 0) {
                var $sampleEvidenceTRArr = $("tr", "#sampleEvidenceTable").not(".regedTr");
                var sampleEvidenceCnt = $sampleEvidenceTRArr.length;
                var $sampleEvidenceTR;
                for (var i = 0; i < sampleEvidenceCnt; i++) {
                    sample = {};
                    $sampleEvidenceTR = $($sampleEvidenceTRArr.get(i));
                    sample.id = $("input[name='sampleId']", $sampleEvidenceTR).val();
                    sample.sampleNo = $("input[name='sampleNo']", $sampleEvidenceTR).val();
                    sample.sampleName = $("input[name='sampleName']", $sampleEvidenceTR).val();
                    sample.sampleType = $("input[name='sampleType']", $sampleEvidenceTR).val();
                    sample.extractDatetime = $("input[name='extractDatetime']", $sampleEvidenceTR).val();
                    sample.extractPerson = $("input[name='extractPerson']", $sampleEvidenceTR).val();
                    sample.sampleDesc = $("input[name='sampleDesc']", $sampleEvidenceTR).val();
                    sample.samplePacking = $("input[name='samplePacking']", $sampleEvidenceTR).val();
                    sample.sampleProperties = $("input[name='sampleProperties']", $sampleEvidenceTR).val();
                    sample.otherPropertiesDesc = $("input[name='otherPropertiesDesc']", $sampleEvidenceTR).val();
                    sample.sampleFlag = "0";
                    sample.atomFlag = ($("input[name='atomFlag']", $sampleEvidenceTR).is(":checked") == true) ? "1" : "0";
                    sample.urgentFlag = ($("input[name='urgentFlag']", $sampleEvidenceTR).is(":checked") == true) ? "1" : "0";
                    sample.difficultFlag = ($("input[name='difficultFlag']", $sampleEvidenceTR).is(":checked") == true) ? "1" : "0";

                    sampleArr.push(sample);
                }
            }

            return sampleArr;
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

            var sampleTRLen = $("tr", "#samplePersonTable").not(".regedTr").length + $("tr", "#sampleEvidenceTable").not(".regedTr").length;
            if (sampleTRLen == 0) {
                alert("请添加案件检材信息！");
                return false;
            }

            return true;
        }

        function submitForm() {
            if (!checkInputValidation()) {
                return;
            }

            var caseInfo = GetCaseInfo();
            var consignment = GetConsignment();
            var personInfoList = GetPerson();
            var sampleInfoList = GetSample();

            var fromUrl = $("#fromUrl").val();

            var params = {
                "caseInfo": caseInfo,
                "consignment": consignment,
                "personInfoList": personInfoList,
                "sampleInfoList": sampleInfoList,
                "fromUrl": fromUrl
            };

            var caseNo = $("#caseNo", "#caseinfo_form").val();
            var caseId = $("#caseId", "#caseinfo_form").val();
            $('#load').fadeIn();
            $.ajax({
                url: "<%=path%>/center/caseinfo/selectCaseNoIsRepeat.html?caseNo=" + caseNo + "&caseId=" + caseId,
                type: "post",
                dataType: "json",
                success: function (data) {
                    if (data.success || data.success == true || data.success == "true") {
                        $.ajax({
                            url: "<%=path%>/center/caseinfo/update.html",
                            type: "post",
                            contentType: "application/json; charset=utf-8",
                            data: JSON.stringify(params),
                            dataType: "json",
                            success: function (data) {
                                if (data.success || data.success == true || data.success == "true") {
                                    $('#load').fadeOut();
                                    $("#EditCaseModal").modal('show');
                                }
                            },
                            error: function (e) {
                                alert(e);
                            }
                        });
                    } else {
                        alert("输入受理号已存在，请重新输入！");
                        $("#caseNo").focus();
                    }
                },
                error: function (e) {
                    alert(e);
                }
            });
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
            if ((person.personIdcardNo == "" || person.personIdcardNo == "无")
                    && person.noIdcardRemark != "") {
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

            $("input[name='personNo']", "#personModal").val(person.personNo);
            $("input[name='personId']", "#personModal").val(person.id);
            $("input[name='personOperateType']", "#personModal").val(operateType);
            $("input[name='personTableRownum']", "#personModal").val(rownum);

            $("#personModal").modal('show');
        }

        function AddPersonRow() {
            var person = {};
            person.personType = "";
            person.personName = "";
            person.personGender = "";
            person.personIdcardNo = "";
            person.noIdcardRemark = "";
            person.relativeIdentify = "";
            person.personNo = "";
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
            person.personNo = $("input[name='personNo']", $curTR).val();
            person.id = $("input[name='personId']", $curTR).val();

            var trIdx = $curTR.index();
            newPersonRow(person, "edit", trIdx);
        }

        function SavePersonRow() {
            var errorCnt = 0;
            $("input.required", "#personModal").each(function () {
                if ($(this).val() == "") {
                    $("div.has-error", $(this).parents("div.form-group")).removeClass("hide");
                    errorCnt++;
                } else {
                    $("div.has-error", $(this).parents("div.form-group")).addClass("hide");
                }
            });

            $("select.required", "#personModal").each(function () {
                if ($(this).find("option:selected").length == 0) {
                    $("div.has-error", $(this).parents("div.form-group")).removeClass("hide");
                    errorCnt++;
                } else {
                    $("div.has-error", $(this).parents("div.form-group")).addClass("hide");
                }
            });

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
                    url: "<%=path%>/center/checkIdcard.html?idcardNo=" + idcardNo,
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
            var personNo = $("input[name='personNo']", "#personModal").val();
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
            newRowHtml += "<td><input type='hidden' name='personId' value='" + personId + "'/><button name='editPerBtn' class='btn btn-primary btn-xs'><i class='fa fa-pencil'></i> 修改</button></td>";

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
            /*$("button[name='delPerBtn']","#personInfoTable").on("click",function(){
             DelPersonRow(this);
             });*/

            $("#personModal").modal('hide');
        }

// person end

// sample start
        function newSampleRow(sample, operateType, rownum) {
            $("div.has-error", "#sampleModal").addClass("hide");

            $("input[name='sampleId']", "#sampleModal").val(sample.id);
            $("input[name='sampleNo']", "#sampleModal").val(sample.sampleNo);
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
                $("#divRefPerson").hide();
                if (operateType == "edit") {
                    $("#sampleFlagPerson", "#sampleModal").prop("disabled", true);
                    $("#sampleFlagEvidence", "#sampleModal").prop("disabled", false);
                } else {
                    $("#sampleFlagPerson", "#sampleModal").prop("disabled", false);
                    $("#sampleFlagEvidence", "#sampleModal").prop("disabled", false);
                }
            } else {
                /*$("#sampleFlagEvidence", "#sampleModal").removeAttr("checked");
                 $("#sampleFlagPerson", "#sampleModal").prop("checked", true);*/
                $("input[name='sampleFlag']", "#sampleModal").val(sample.sampleFlag);
                $("#divRefPerson").show();
                if (operateType == "edit") {
                    $("#sampleFlagEvidence", "#sampleModal").prop("disabled", true);
                    $("#sampleFlagPerson", "#sampleModal").prop("disabled", false);
                } else {
                    $("#sampleFlagEvidence", "#sampleModal").prop("disabled", false);
                    $("#sampleFlagPerson", "#sampleModal").prop("disabled", false);
                }
            }

            $("input[name='sampleOperateType']", "#sampleModal").val(operateType);
            $("input[name='sampleTableRownum']", "#sampleModal").val(rownum);
            $("input[name='atomFlag']", "#sampleModal").val(sample.atomFlag);
            $("input[name='urgentFlag']", "#sampleModal").val(sample.urgentFlag);
            $("input[name='difficultFlag']", "#sampleModal").val(sample.difficultFlag);

            $("#sampleModal").modal('show');
        }


        function AddSampleRow(obj) {
            var sample = {};
            sample.id = "";
            sample.sampleNo = "";
            sample.sampleType = "";
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
            sample.atomFlag = "0";
            sample.urgentFlag = "0";
            sample.difficultFlag = "0";
            newSampleRow(sample, "add", 0);
        }

        function EditSampleRow(obj) {
            var $curTR = $(obj).parents("tr");
            var sample = {};
            sample.id = $("input[name='sampleId']", $curTR).val();
            sample.sampleNo = $("input[name='sampleNo']", $curTR).val();
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
            sample.atomFlag = $("input[name='atomFlag']", $curTR).is(":checked") == true ? "1" : "0";
            sample.urgentFlag = $("input[name='urgentFlag']", $curTR).is(":checked") == true ? "1" : "0";
            sample.difficultFlag = $("input[name='difficultFlag']", $curTR).is(":checked") == true ? "1" : "0";

            var trIdx = $curTR.index();
            newSampleRow(sample, "edit", trIdx);
        }


        /*function DelSampleRow(obj) {
         var sampleId = $("input[name='sampleId']", $(obj).parent()).val();
         if(sampleId == null || sampleId == "" || sampleId == 0 || sampleId == "0"){
         $(obj).parents("tr").remove();
         return;
         }

         $.ajax({
         url:"../caseinfo/delSample.html?sampleId=" + sampleId,
         dataType:"json",
         success:function(data){
         if(data.success == true
         || data.success == "true") {
         $(obj).parents("tr").remove();
         }
         }
         });
         }*/

        function SaveSampleRow() {
            var errorCnt = 0;


            $("input.required", "#sampleModal").each(function () {
                if ($(this).val() == "") {
                    $("div.has-error", $(this).parents("div.form-group")).removeClass("hide");
                    errorCnt++;
                } else {
                    $("div.has-error", $(this).parents("div.form-group")).addClass("hide");
                }
            });

            var sampleTypeVal = $("select[name='sampleType']", "#sampleModal").find("option:selected").val();
            if (sampleTypeVal == "") {
                $("div.has-error", $("select[name='sampleType']", "#sampleModal").parents("div.form-group")).removeClass("hide");
                errorCnt++;
            } else {
                $("div.has-error", $("select[name='sampleType']", "#sampleModal").parents("div.form-group")).addClass("hide");
            }

            var sampleFlagVal = $("input[name='sampleFlag']", "#sampleModal").val();
            if (sampleFlagVal == "1") {
                var refPeresonVal = $("select[name='refPerson']", "#sampleModal").find("option:selected").val();
                if (refPeresonVal == "") {
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
            var sampleNo = $("input[name='sampleNo']", "#sampleModal").val();
            var sampleName = $("input[name='sampleName']", "#sampleModal").val();
            var extractDatetime = $("input[name='extractDatetime']", "#sampleModal").val();
            var extractPerson = $("input[name='extractPerson']", "#sampleModal").val();
            var sampleDesc = $("input[name='sampleDesc']", "#sampleModal").val();
            var samplePacking = $("input[name='samplePacking']", "#sampleModal").val();
            var samplePropertiesName = $("select[name='sampleProperties']", "#sampleModal").find("option:selected").text();
            var refPersonCode = $("select[name='refPerson']", "#sampleModal").find("option:selected").val();
            var refPersonName = $("select[name='refPerson']", "#sampleModal").find("option:selected").text();

            var newRowHtml = "<td></td>";
            newRowHtml += "<td><input type='hidden' name='sampleNo' value='" + sampleNo + "'/>" + sampleNo + "</td>";
            newRowHtml += "<td><input type='hidden' name='sampleName' value='" + sampleName + "'/>" + sampleName + "</td>";
            newRowHtml += "<td><input type='hidden' name='sampleType' value='" + sampleTypeCode + "'/>" + sampleTypeName + "</td>";
            newRowHtml += "<td><input type='hidden' name='sampleDesc' value='" + sampleDesc + "'/>" + sampleDesc + "</td>";
            newRowHtml += "<td><input type='hidden' name='samplePacking' value='" + samplePacking + "'/>" + samplePacking + "</td>";
            newRowHtml += "<td><input type='hidden' name='sampleProperties' value='" + samplePropertiesVal + "'/><input type='hidden' name='otherPropertiesDesc' value='" + $.trim(otherPropertiesDescVal) + "'/>";
            if (samplePropertiesVal == "9999") {
                newRowHtml += otherPropertiesDescVal + "</td>";
            } else {
                newRowHtml += samplePropertiesName + "</td>";
            }
            if (sampleFlagVal == 1) {
                newRowHtml += "<td><input type='hidden' name='refPersonId' value='" + refPersonCode + "'/><input type='hidden' name='refPersonName' value='" + refPersonName + "'/>" + refPersonName + "</td>";
            }

            newRowHtml += "<td><input type='hidden' name='extractDatetime' value='" + extractDatetime + "'/>" + extractDatetime + "</td>";
            newRowHtml += "<td><input type='hidden' name='extractPerson' value='" + extractPerson + "'/>" + extractPerson + "</td>";

            var atomFlag = $("input[name='atomFlag']", "#sampleModal").val();
            var urgentFlag = $("input[name='urgentFlag']", "#sampleModal").val();
            var difficultFlag = $("input[name='difficultFlag']", "#sampleModal").val();
            newRowHtml += "<td><label class='checkbox-inline'><input name='atomFlag' type='checkbox' " + (atomFlag == "1" ? "checked" : "") + "/>微量</label>"
                    + "<label class='checkbox-inline'><input name='urgentFlag' type='checkbox' " + (urgentFlag == "1" ? "checked" : "") + "/>加急</label>"
                    + "<label class='checkbox-inline'><input name='difficultFlag' type='checkbox'" + (difficultFlag == "1" ? "checked" : "") + " />疑难</label></td>";


            newRowHtml += "<td><input type='hidden' name='sampleId' value='" + sampleId + "'/><input type='hidden' name='sampleFlag' value='" + sampleFlagVal + "'/><button name='editSampleBtn' class='btn btn-primary btn-xs'><i class='fa fa-pencil'></i> 修改</button></td>";
            /*newRowHtml += "<button name='uploadPhotoBtn' class='btn btn-primary btn-xs'><i class='fa fa-upload'></i>上传照片</button></td>";*/

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
                EditSampleRow(this);
            });
            $("button[name='editSampleBtn']", "#sampleEvidenceTable").on("click", function () {
                EditSampleRow(this);
            });
            $("button[name='uploadPhotoBtn']", "#sampleEvidenceTable").on("click", function () {
                EditPhotoRow(this);
            });

            $("button[name='uploadPhotoBtn']", "#samplePersonTable").on("click", function () {
                EditPhotoRow(this);
            });
            $("button[name='delSampleBtn']", "#sampleInfoTable").on("click", function () {
                DelSampleRow(this);
            });

            $("#sampleModal").modal('hide');
        }

// person end
        //更新
        $("#saveBtn").on("click", function () {
            submitForm();
        });

        /**
         * person start
         */
        $("#newPerBtn").on("click", function () {
            AddPersonRow();
        });

        $("button[name='editPerBtn']", "#personInfoTable").on("click", function () {
            EditPersonRow(this);
        });

        /*$("button[name='delPerBtn']","#personInfoTable").on("click",function(){
         DelPersonRow(this);
         });*/

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

        // person end

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

        $("#SavePersonBtn").on("click", function () {
            SavePersonRow();
        });
        $("button[name='editSampleBtn']", "#samplePersonTable").on("click", function () {
            EditSampleRow(this);
        });
        $("button[name='editSampleBtn']", "#sampleEvidenceTable").on("click", function () {
            EditSampleRow(this);
        });

        /*$("button[name='delSampleBtn']","#samplePersonTable").on("click",function(){
         DelSampleRow(this);
         });
         $("button[name='delSampleBtn']","#sampleEvidenceTable").on("click",function(){
         DelSampleRow(this);
         });*/

        $("#backBtn").on("click", function () {
            var fromUrl = $("#fromUrl").val();
            var url = "<%=path%>"
                    + "/center/caseinfo/"
                    + fromUrl
                    + ".html";
            location.href = url;
        });

        $("#EditCaseModal").on('hidden.bs.modal', function () {
            var fromUrl = $("#fromUrl").val();
            var url = "<%=path%>"
                    + "/center/caseinfo/"
                    + fromUrl
                    + ".html";
            location.href = url;
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

        $("input[name='sampleFlag']").on('change', function () {
            var checkedSampleFalg = $("input[name='sampleFlag']:checked").val();
            checkedSampleFalg == 0 ? $("#divRefPerson").hide() : $("#divRefPerson").show();
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
    });

    $(".splitBtn").click(function () {
        var sampleNoval = $(this).parent().siblings().eq(1).children().val();
        sampleNoval = $.trim(sampleNoval)
        var sampleName = $(this).parent().siblings().eq(2).children().val();
        sampleName = $.trim(sampleName)
        var sampleDesc = $(this).parent().siblings().eq(4).children().val();
        sampleDesc = $.trim(sampleDesc)
        var newTr = $(this).parent().parent().clone(true);
        newTr.children().eq(0).html("")
        newTr.children().eq(10).children("button[name='editSampleBtn']").remove();
        newTr.children().eq(10).children("button[name='uploadPhotoBtn']").remove();
        if (newTr.children().eq(10).children("button").length == 1) {
            newTr.children().eq(10).append("<button name='del' class='btn btn-danger btn-xs del' ><i class='fa fa-trash-o'></i> 删除</button>")
        }
        newTr.children().eq(1).text("")
        newTr.children().eq(1).append('<input type="text" name="sampleNo" value="' + sampleNoval + '">')
        newTr.children().eq(2).text("")
        newTr.children().eq(2).append('<input type="text" name="sampleName" value="' + sampleName + '">')
        newTr.children().eq(4).text("")
        newTr.children().eq(4).append('<input type="text" name="sampleDesc" value="' + sampleDesc + '">')
        $(this).parent().parent().after(newTr);
        $(".del").click(function () {
            $(this).parent().parent().remove();
        })
        generateSampleEvidenceIdx();
    })

</script>
</body>
</html>
