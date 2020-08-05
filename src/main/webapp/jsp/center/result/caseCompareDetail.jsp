<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/include.jsp" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <%@ include file="../../common/link.jsp" %>
    <style>
        #nextStep{
            background-image: url("<%=path%>/img/next.svg");
            background-repeat: no-repeat;
            background-position: left;
            background-size: 24px;
            padding-left: 26px;
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
                            <span class="label label-primary">同型比中条件</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <form class="form-horizontal tasi-form" method="get">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label"><strong>匹配数下限</strong></label>
                                    <div class="col-sm-2">
                                        <input type="text" id="matchLimitTxt" class="form-control" value="${matchLimit}"/>
                                    </div>
                                    <label class="col-sm-1 control-label"><strong>种群</strong></label>
                                    <div class="col-sm-2">
                                        <select class="form-control" name = "raceInfoSelect">
                                            <c:forEach items="${raceInfoList}" var="raceInfo" varStatus="r">
                                                <option value="${raceInfo.raceName}">${raceInfo.raceName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-1">
                                        <input type="hidden" name="caseId" id="caseId" value="${caseInfo.id}">
                                        <button class="btn btn-primary" type="button" id="importCodisBtn"><i class="fa fa-download"></i> 重新比对</button>
                                    </div>
                                    <div class="col-sm-1">
                                        <button class="btn btn-primary" type="button" id="saveBtn" style="padding-left: 0px;"><i class="fa fa-check"></i> 保存比对结果</button>
                                    </div>

                                    <%--<c:choose>
                                        <c:when test="${null eq caseInfo.identifyBookStatus}">--%>
                                            <div class="col-sm-1" >
                                                <input type="hidden" name="caseInfoId" id="caseInfoId" value="${caseInfo.id}">
                                                <button class="btn btn-primary" type="button" id="nextStep" >下一步</button>
                                            </div>
                                       <%-- </c:when>
                                        <c:otherwise>--%>
                                            <%--${matchedSample.totalProbability}--%>
                                       <%-- </c:otherwise>
                                    </c:choose>--%>


                                </div>
                            </form>
                        </div>
                    </section>
                </div>
            </div>

            <!-- BEGIN ROW  -->
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            <span class="label label-primary">比中检材列表</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>组</th>
                                    <th>检材编号</th>
                                    <th>检材名称</th>
                                    <th>LR</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="matchedGroupTBody">
                                <c:forEach items="${matchedGroupList}" var="matchedGroup" varStatus="group">
                                    <c:forEach items="${matchedGroup.matchedSamples}" var="matchedSample" varStatus="matched">
                                        <c:if test="${matched.index eq 0}">
                                            <tr>
                                                <td rowspan="${fn:length(matchedGroup.matchedSamples)}">
                                                    <input type="hidden" name="groupId" value="${matchedGroup.groupId}">${group.count}
                                                </td>
                                                <td><input type="hidden" name="sampleId" value="${matchedSample.sampleId}">${matchedSample.sampleNo}</td>
                                                <td title="${matchedSample.sampleName}">
                                                    <input type="hidden" name="createPerson" value="${matchedSample.createPerson}">
                                                    <c:if test="${fn:length(matchedSample.sampleName) gt 24}">${fn:substring(matchedSample.sampleName,0,23)} ...</c:if>
                                                    <c:if test="${fn:length(matchedSample.sampleName) lt 25}">${matchedSample.sampleName}</c:if>
                                                </td>
                                                <td><input type="hidden" name="geneInfo" value='${matchedSample.geneInfo}'/>
                                                    <c:choose>
                                                        <c:when test="${null eq matchedSample.totalProbability}">
                                                            -- --
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${matchedSample.totalProbability}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td></td>
                                            </tr>
                                        </c:if>
                                        <c:if test="${matched.index gt 0}">
                                            <tr>
                                                <td><input type="hidden" name="sampleId" value="${matchedSample.sampleId}">${matchedSample.sampleNo}</td>
                                                <td title="${matchedSample.sampleName}">
                                                    <input type="hidden" name="createPerson" value="${matchedSample.createPerson}">
                                                    <c:if test="${fn:length(matchedSample.sampleName) gt 24}">${fn:substring(matchedSample.sampleName,0,23)} ...</c:if>
                                                    <c:if test="${fn:length(matchedSample.sampleName) lt 25}">${matchedSample.sampleName}</c:if>
                                                </td>
                                                <td><input type="hidden" name="geneInfo" value='${matchedSample.geneInfo}'/>
                                                    <c:choose>
                                                        <c:when test="${null eq matchedSample.totalProbability}">
                                                            -- --
                                                        </c:when>
                                                        <c:otherwise>
                                                            ${matchedSample.totalProbability}
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td></td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </c:forEach>
                                </tbody>
                            </table>
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
                            <span class="label label-primary">未比中检材列表</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>检材编号</th>
                                    <th>检材名称</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                    <c:forEach items="${notMatchedGeneList}" var="notMatched" varStatus="nm">
                                        <tr>
                                            <td>${notMatched.sampleNo}</td>
                                            <td title="${notMatched.sampleName}">
                                                <c:if test="${fn:length(notMatched.sampleName) gt 24}">${fn:substring(notMatched.sampleName,0,23)} ...</c:if>
                                                <c:if test="${fn:length(notMatched.sampleName) lt 25}">${notMatched.sampleName}</c:if>
                                            </td>
                                            <td></td>
                                        </tr>
                                    </c:forEach>
                                </tbody>
                            </table>
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
                            <span class="label label-primary">亲缘比中条件</span>
                            <font style="color:red">&nbsp;&nbsp;&nbsp;&nbsp; 注: 亲缘比对的人员关系, 绿色表示比中; 红色表示未比中.</font>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <form id="relationFrom" class="form-horizontal tasi-form" method="get">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label"><strong>匹配数下限</strong></label>
                                    <div class="col-sm-2">
                                        <input type="text" id="relativeSameCount" class="form-control" value="${matchRelationLimit}"/>
                                    </div>
                                    <label class="col-sm-1 control-label">
                                        <input type="hidden" id="halfDiffCount" name="halfDiffCount" value="${halfDiffCount}">
                                        <strong>半容差&nbsp;&nbsp;${halfDiffCount}</strong>
                                    </label>
                                    <label class="col-sm-1 control-label"><strong>种群</strong></label>
                                    <div class="col-sm-2">
                                        <select class="form-control" id="relativePopulation" name = "raceInfoSelectPopulation">
                                            <c:forEach items="${raceInfoList}" var="raceInfo" varStatus="r">
                                                <option value="${raceInfo.id}">${raceInfo.raceName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-1">
                                        <button class="btn btn-primary" type="button" id="addRelationBtn"><i class="fa fa-plus"></i> 添加亲缘比对</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="panel-body">
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>组</th>
                                    <th>样本编号</th>
                                    <th>检材名称</th>
                                    <th>人员关系</th>
                                    <th>Panel</th>
                                    <th>匹配数</th>
                                    <th>累计PI值</th>
                                    <th>匹配下限</th>
                                    <th>种群名称</th>
                                </tr>
                                </thead>
                                <tbody id="raletionID">
                                      <c:if test="${fn:length(compareResultInfoList) gt 0}">
                                          <c:forEach items="${compareResultInfoList}" var="compareResultInfo" varStatus="c">
                                              <tr>
                                              <input type="hidden" id="relativeRowCount" value="${compareResultInfoList.size()}">
                                              <tr>
                                                  <td>${c.count}</td>
                                                  <td colspan="4"></td>
                                                  <td>${compareResultInfo.compareGeneNum}</td>
                                                  <td>${compareResultInfo.compareTotalProbability}</td>
                                                  <td>${compareResultInfo.sameCount}</td>
                                                  <td>${compareResultInfo.comparePopulationName}</td>
                                              </tr>
                                              <c:if test="${not empty compareResultInfo.fatherSampleNo}">
                                                  <tr>
                                                      <td></td>
                                                      <td>${compareResultInfo.fatherSampleNo}</td>
                                                      <td title="${compareResultInfo.fatherSampleName}">
                                                          <c:if test="${fn:length(compareResultInfo.fatherSampleName) gt 24}">${fn:substring(compareResultInfo.fatherSampleName,0,23)} ...</c:if>
                                                          <c:if test="${fn:length(compareResultInfo.fatherSampleName) lt 25}">${compareResultInfo.fatherSampleName}</c:if>
                                                      </td>
                                                      <td style='background:${compareResultInfo.bacgroundF};'>父<c:if test="${compareResultInfo.referenceId eq '0'}">(A)</c:if> </td>
                                                      <td>${compareResultInfo.reagentNameF}</td>
                                                      <td>-- --</td>
                                                      <td>-- --</td>
                                                      <td>-- --</td>
                                                      <td>-- --</td>
                                                  <tr>
                                              </c:if>
                                              <c:if test="${not empty compareResultInfo.motherSampleNo}">
                                                  <tr>
                                                      <td></td>
                                                      <td>${compareResultInfo.motherSampleNo}</td>
                                                      <td title="${compareResultInfo.motherSampleName}">
                                                          <c:if test="${fn:length(compareResultInfo.motherSampleName) gt 24}">${fn:substring(compareResultInfo.motherSampleName,0,23)} ...</c:if>
                                                          <c:if test="${fn:length(compareResultInfo.motherSampleName) lt 25}">${compareResultInfo.motherSampleName}</c:if>
                                                      </td>
                                                      <td style='background:${compareResultInfo.bacgroundM};'>母<c:if test="${compareResultInfo.referenceId eq '1'}">(A)</c:if> </td>
                                                      <td>${compareResultInfo.reagentNameM}</td>
                                                      <td>-- --</td>
                                                      <td>-- --</td>
                                                      <td>-- --</td>
                                                      <td>-- --</td>
                                                  <tr>
                                              </c:if>

                                              <c:if test="${not empty compareResultInfo.childSampleNo}">
                                                  <tr>
                                                      <td></td>
                                                      <td>${compareResultInfo.childSampleNo}</td>
                                                      <td title="${compareResultInfo.childSampleName}">
                                                          <c:if test="${fn:length(compareResultInfo.childSampleName) gt 24}">${fn:substring(compareResultInfo.childSampleName,0,23)} ...</c:if>
                                                          <c:if test="${fn:length(compareResultInfo.childSampleName) lt 25}">${compareResultInfo.childSampleName}</c:if>
                                                      </td>
                                                      <td>子<c:if test="${compareResultInfo.referenceId eq '2'}">(A)</c:if> </td>
                                                      <td>${compareResultInfo.reagentNameC}</td>
                                                      <td>-- --</td>
                                                      <td>-- --</td>
                                                      <td>-- --</td>
                                                      <td>-- --</td>
                                                  <tr>
                                              </c:if>
                                              <input type="hidden" name = "caseId" value="${compareResultInfo.caseId}">
                                              <input type="hidden" name = "fatherSampleNo" value="${compareResultInfo.fatherSampleNo}">
                                              <input type="hidden" name = "motherSampleNo" value="${compareResultInfo.motherSampleNo}">
                                              <input type="hidden" name = "childSampleNo" value="${compareResultInfo.childSampleNo}">
                                              <input type="hidden" name = "relativeMatchCount" value="${compareResultInfo.compareGeneNum}">
                                              <input type="hidden" name = "relativeTotalProbability" value="${compareResultInfo.compareTotalProbability}">
                                              <input type="hidden" name = "relativePopulationId" value="${compareResultInfo.comparePopulationId}">
                                              <input type="hidden" name = "relativeReferenceId" value="${compareResultInfo.referenceId}">
                                              <input type="hidden" name = "relativeSameCount" value="${compareResultInfo.sameCount}">
                                              <input type="hidden" name = "relativeDiffCount" value="${compareResultInfo.diffCount}">
                                              <input type="hidden" name = "relativeMatchMode" value="${compareResultInfo.matchedMode}">
                                              </tr>
                                          </c:forEach>
                                      </c:if>
                                </tbody>
                            </table>
                        </div>
                    </section>
                </div>
            </div>

            <div class="modal fade" id="ralationModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
                <div class="modal-dialog">
                    <div class="modal-content" style="width: 750px;">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                x
                            </button>
                            <h4 class="modal-title">
                                选择亲缘样本信息
                            </h4>
                        </div>
                        <div class="modal-body" style="height:auto;padding: 30px">
                            <table id="addTableZID" border="0">
                                <div class="form-group" style="margin-right:-30px;">
                                    <label class="control-label col-md-3">匹配目标</label>
                                    <label class="control-label col-md-2">样&nbsp;本</label>
                                    <label class="control-label col-md-2">匹配概率</label>
                                    <label class="control-label col-md-2">匹配基因座数</label>
                                    <label class="control-label col-md-2">比对模式</label>
                                </div>
                                <tr>
                                    <td class="control-label col-md-2" style="width: 12%"><input type="radio"  name="rcRadio"  value="0">父：</td>
                                    <td class="col-md-4">
                                        <select id="ComparedToCaseSampleFID" name="ComparedToCaseSampleF" class="form-control small" style="font-size: 13px">
                                            <option  value=''>请选择...</option>
                                            <c:forEach items="${sampleGeneList}" var="list" varStatus="s">
                                                <option value="${list.sampleNo}">${list.sampleNo}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tr>
                                    <td class="control-label col-md-2" style="width: 12%"><input type="radio" name="rcRadio" value="1">母：</td>
                                    <td class="col-md-4">
                                        <select id="ComparedToCaseSampleMID" name="ComparedToCaseSampleM" class="form-control small" style="font-size: 13px">
                                            <option  value=''>请选择...</option>
                                            <c:forEach items="${sampleGeneList}" var="list" varStatus="s">
                                                <option value="${list.sampleNo}">${list.sampleNo}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                </tr>
                                <tbody id="newAddZTbodyId">
                                <tr>
                                    <td class="control-label col-md-2" style="width: 12%"><input type="radio" name="rcRadio"  checked value="2">子：</td>
                                    <td class="col-md-4">
                                        <select id="ComparedToCaseSampleZID" name="ComparedToCaseSampleZ" class="form-control small" style="font-size: 13px">
                                            <option  value=''>请选择...</option>
                                            <c:forEach items="${sampleGeneList}" var="list" varStatus="s">
                                                <option value="${list.sampleNo}">${list.sampleNo}</option>
                                            </c:forEach>
                                        </select>
                                    </td>
                                    <td class="col-md-2"><input name="totalPi" class="form-control input-sm" readonly onmouseover="this.title=this.value"></td>
                                    <td class="col-md-2"><input name = "matchCount" class="form-control input-sm" readonly onmouseover="this.title=this.value"></td>
                                    <td class="col-md-2"><input name="matchModeName" class="form-control input-sm" readonly onmouseover="this.title=this.value"></td>
                                    <td>
                                        <button id="addRelationZID" class="btn btn-primary btn-sm" onclick="addRelationZID();"><i class="fa fa-plus"></i> 添 加</button>
                                    </td>
                                    <input name="panelNameF" type="hidden">
                                    <input name="panelNameM" type="hidden">
                                    <input name="panelNameC" type="hidden">
                                    <input name="sampleNameF" type="hidden">
                                    <input name="sampleNameM" type="hidden">
                                    <input name="sampleNameC" type="hidden">
                                    <input name="sampleNoF" type="hidden">
                                    <input name="sampleNoM" type="hidden">
                                    <input name="sampleNoC" type="hidden">
                                    <input name="sampleIdF" type="hidden">
                                    <input name="sampleIdM" type="hidden">
                                    <input name="sampleIdC" type="hidden">
                                    <input name="singleMatchMode" type="hidden" >
                                    <input name="singlePopulationID" type="hidden">
                                    <input name="singleSameCount"  type="hidden">
                                    <input name="singleDiffCount"  type="hidden">
                                </tr>
                                </tbody>
                            </table>
                        </div>
                        <div class="modal-footer" style="border-top: 0px;">
                            <button class="btn btn-success pull-left" type="button" id="matchBtn">比对</button>
                            <button class="btn btn-success pull-left" type="button" id="addCompareToResultId" disabled><i class="fa fa-plus"></i>添加</button>
                        </div>
                    </div>
                </div>
            </div>

            <!-- BEGIN ROW  -->
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            <span class="label label-primary">无结果检材列表</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>检材编号</th>
                                    <th>检材名称</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${noResultSampleList}" var="notResult" varStatus="nr">
                                    <tr>
                                        <td>${notResult.sampleNo}</td>
                                        <td title="${notResult.sampleName}">
                                            <c:if test="${fn:length(notResult.sampleName) gt 24}">${fn:substring(notResult.sampleName,0,23)} ...</c:if>
                                            <c:if test="${fn:length(notResult.sampleName) lt 25}">${notResult.sampleName}</c:if>
                                        </td>
                                        <td></td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </section>
                </div>
            </div>

            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            <span class="label label-primary">混合检材列表</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>组</th>
                                    <th>检材编号</th>
                                    <th>检材名称</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="mixSampleGeneList">
                                <c:if test="${fn:length(mixMatchedGroupList) gt 0}">
                                    <c:forEach items="${mixMatchedGroupList}" var="mixMatchedGroup" varStatus="mixGroup">
                                        <c:forEach items="${mixMatchedGroup.mixMatchedSamples}" var="mixMatchedSample" varStatus="mixMatched">
                                            <c:if test="${mixMatched.index eq 0}">
                                                <tr>
                                                    <td>
                                                        <input type="hidden" name="groupId" value="${mixMatchedGroup.groupId}">${mixGroup.count}
                                                    </td>
                                                    <td><input type="hidden" name="sampleId" value="${mixMatchedSample.sampleId}">${mixMatchedSample.sampleNo}</td>
                                                    <td title="${mixMatchedSample.sampleName}">
                                                        <input type="hidden" name="createPerson" value="${mixMatchedSample.createPerson}">
                                                        <c:if test="${fn:length(mixMatchedSample.sampleName) gt 24}">${fn:substring(mixMatchedSample.sampleName,0,23)} ...</c:if>
                                                        <c:if test="${fn:length(mixMatchedSample.sampleName) lt 25}">${mixMatchedSample.sampleName}</c:if>
                                                    </td>
                                                    <td><input type="hidden" name="geneInfo" value='${mixMatchedSample.geneInfo}'/></td>
                                                </tr>
                                            </c:if>
                                            <c:if test="${mixMatched.index gt 0}">
                                                <tr>
                                                    <td></td>
                                                    <td><input type="hidden" name="sampleId" value="${mixMatchedSample.sampleId}">${mixMatchedSample.sampleNo}</td>
                                                    <td title="${mixMatchedSample.sampleName}">
                                                        <input type="hidden" name="createPerson" value="${mixMatchedSample.createPerson}">
                                                        <c:if test="${fn:length(mixMatchedSample.sampleName) gt 24}">${fn:substring(mixMatchedSample.sampleName,0,23)} ...</c:if>
                                                        <c:if test="${fn:length(mixMatchedSample.sampleName) lt 25}">${mixMatchedSample.sampleName}</c:if>
                                                    </td>
                                                    <td><input type="hidden" name="geneInfo" value='${mixMatchedSample.geneInfo}'/></td>
                                                </tr>
                                            </c:if>
                                        </c:forEach>
                                    </c:forEach>
                                </c:if>
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
                            <div class="col-sm-2 form-group  pull-right">
                                <div class="col-sm-2">
                                    <button class="btn btn-primary" type="button" id="replyBtn" name="replyBtn"><i class="fa fa-reply"></i> 返 回</button>
                                </div>
                            </div>
                        </div>
                    </section>
                </div>
            </div>
</div>
</section>
</section>
<!-- BEGIN JS -->
<%@ include file="../../common/script.jsp" %>
<script>

    function addRelationZID(){
        var appRowVictim = "";
        var zListObj = document.getElementById("ComparedToCaseSampleZID");
        appRowVictim+=" <tr>";
        appRowVictim+=" <td class='control-label col-md-2' style='width: 12%'>";
        appRowVictim+=" <input type='radio'  name='rcRadio'  value='2'>子：</td>";
        appRowVictim+="<td class='col-md-4'><select id='newAddRelationCompareToZID' name='ComparedToCaseSampleZ' class='form-control small' style='font-size: 13px'>";
        appRowVictim+="<option  value=''>请选择...</option>";
        for(var i=0; i<zListObj.options.length; i++){
            if(zListObj.options[i].value != "")
                appRowVictim+="<option value='"+zListObj.options[i].value+"'>"+ zListObj.options[i].text+"</option>";
        }
        appRowVictim+= "</select></td>";
        appRowVictim+="<td class='col-md-2'><input name='totalPi' class='form-control input-sm' onmouseover='this.title=this.value' readonly></td>";
        appRowVictim+="<td class='col-md-2'><input name = 'matchCount' class='form-control input-sm' onmouseover='this.title=this.value' readonly></td>";
        appRowVictim+="<td class='col-md-2'><input name='matchModeName' class='form-control input-sm' onmouseover='this.title=this.value' readonly></td>";
        appRowVictim+="<input type='hidden' name='panelNameF' >";
        appRowVictim+="<input type='hidden' name='panelNameM' >";
        appRowVictim+="<input type='hidden' name='panelNameC' >";
        appRowVictim+="<input type='hidden' name='sampleNameF' >";
        appRowVictim+="<input type='hidden' name='sampleNameM' >";
        appRowVictim+="<input type='hidden' name='sampleNameC' >";
        appRowVictim+="<input type='hidden' name='sampleNoF' >";
        appRowVictim+="<input type='hidden' name='sampleNoM' >";
        appRowVictim+="<input type='hidden' name='sampleNoC' >";
        appRowVictim+="<input type='hidden' name='sampleIdF' >";
        appRowVictim+="<input type='hidden' name='sampleIdM' >";
        appRowVictim+="<input type='hidden' name='sampleIdC' >";
        appRowVictim+="<input type='hidden' name='singleMatchMode' >";
        appRowVictim+="<input type='hidden' name='singleSameCount'  >";
        appRowVictim+="<input type='hidden' name='singleDiffCount'  >";
        appRowVictim+="<input type='hidden' name='singlePopulationID' >";
        appRowVictim+="<td><button id='deleteRelationZID' class='btn btn-primary btn-sm' onclick = 'closeTr(this);'><i class='fa fa-trash-o'></i>删 除</button></td>"
        appRowVictim+="</tr>";
        $("#newAddZTbodyId").append(appRowVictim);
    }

    function closeTr(obj){
        $(obj).parent().parent().remove();
    }

    $(function(){
        'use strict';

        var number=0;
        if($("#relativeRowCount").val() != null){
            number = $("#relativeRowCount").val();
        }

        $("#matchBtn").on("click",function(){
            if($("#relativeSameCount").val() <0) {
                alert("匹配下限不能小于0");
                return false;
            }

            if($("#relativePopulation").val == "") {
                alert("请选择种族!");
                return false;
            }

            var Fval = $("#ComparedToCaseSampleFID").val();
            var Mval = $("#ComparedToCaseSampleMID").val();
            var Zval = document.getElementsByName("ComparedToCaseSampleZ");

            var chooseZ = false;
            if(Fval== '' &&  Mval== ''){
                alert("请选择父样本或母亲样本!");
                return false;
            }

            for(var i=0;i<Zval.length;i++) {
                if(Zval[i].value == "" || Zval[i].value == null) {
                    alert("子样本不能为空，请选择!");
                    return false;
                }
            }
            var tempZ = "";
            var zBarcodeStr ="";
            for(var i=0;i<Zval.length;i++) {
                if(Zval[i].value == Fval || Zval[i].value == Mval || Fval == Mval ||  Zval[i].value == tempZ) {
                    alert("比对样本不能重复，请选择!");
                    return false;
                }
                tempZ = Zval[i].value;
                zBarcodeStr += tempZ + ",";
            }

            var relationCompare = GetRelativeCompare(zBarcodeStr);

            $.ajax({
                url : "<%=path%>/center/3/realtionCompareTo.html",
                type:"post",
                contentType:  "application/json; charset=utf-8",
                data : JSON.stringify(relationCompare),
                dataType : "json",
                success : function(data) {
                    if(data.length > 0) {
                        var $trs=$("tr","#newAddZTbodyId");
                        for(var i=0;i < $trs.length;i++){
                            var $tr = $trs[i];
                            $("input[name='totalPi']",$tr).val(data[i].totalPi);
                            $("input[name='matchCount']",$tr).val(data[i].matchCount);
                            $("input[name='matchModeName']",$tr).val(data[i].matchModeName);
                            $("input[name='singlePopulationID']",$tr).val( $("#relativePopulation").find("option:selected").val());
                            $("input[name='singleSameCount']",$tr).val( $("#relativeSameCount").val());
                            $("input[name='singleDiffCount']",$tr).val( $("#halfDiffCount").val());
                            $("input[name='singleMatchMode']",$tr).val(data[i].matchMode);
                            $("input[name='panelNameF']",$tr).val(data[i].panelNameF);
                            $("input[name='panelNameM']",$tr).val(data[i].panelNameM);
                            $("input[name='panelNameC']",$tr).val(data[i].panelNameC);
                            $("input[name='sampleNameF']",$tr).val(data[i].sampleNameF);
                            $("input[name='sampleNameM']",$tr).val(data[i].sampleNameM);
                            $("input[name='sampleNameC']",$tr).val(data[i].sampleNameC);
                            $("input[name='sampleNoF']",$tr).val(data[i].sampleNoF);
                            $("input[name='sampleNoM']",$tr).val(data[i].sampleNoM);
                            $("input[name='sampleNoC']",$tr).val(data[i].sampleNoC);
                            $("input[name='sampleIdF']",$tr).val(data[i].sampleIdF);
                            $("input[name='sampleIdM']",$tr).val(data[i].sampleIdM);
                            $("input[name='sampleNoC']",$tr).val(data[i].sampleNoC);
                        }
                    }else {
                        alert("保存失败!");
                    }
                },
                error: function(e){
                    alert(e);
                }
            });

            $("#addCompareToResultId").prop("disabled",false);

        });

        $("#addCompareToResultId").on("click", function(){
            var Zval = document.getElementsByName("ComparedToCaseSampleZ");
            for(var i=0;i<Zval.length;i++) {

                if(Zval[i].value == "" || Zval[i].value == null) {
                    alert("子样本不能为空，请选择!");
                    return false;
                }
            }

            var matchCount = document.getElementsByName("matchCount");
            for(var i=0;i<matchCount.length;i++) {

                if(matchCount[i].value == "" || matchCount[i].value == null) {
                    alert("匹配概率不能为空，请选择!");
                    return false;
                }
            }

            var singleSameCount = document.getElementsByName("singleSameCount");
            for(var i=0;i<singleSameCount.length;i++) {

                if(singleSameCount[i].value == "" || singleSameCount[i].value == null) {
                    alert("匹配基因座数不能为空，请选择!");
                    return false;
                }
            }

            var matchModeName = document.getElementsByName("matchModeName");
            for(var i=0;i<matchModeName.length;i++) {

                if(matchModeName[i].value == "" || matchModeName[i].value == null) {
                    alert("比对模式不能为空，请选择!");
                    return false;
                }
            }

            var $trs=$("tr","#newAddZTbodyId");
            for(var i=0;i<$trs.length;i++){

                var $tr=$trs[i];
                var matchMode = $("input[name='singleMatchMode']",$tr).val();
                var refrenceId = $("input[name='rcRadio']:checked").val();
                var relationF = "", relationM ="", relationC = "";
                if(refrenceId == '0')
                    relationF = "(A)";
                else if(refrenceId == '1')
                    relationM = "(A)";
                else if(refrenceId == '2')
                    relationC = "(A)";

                var backgroudM = "", backgroudF = "";
                if(matchMode == '0' || matchMode == '4' || matchMode == '-1' || matchMode == '-2' ||matchMode == '-3') {
                    backgroudM = "red";
                    backgroudF = "red";
                }else if(matchMode == '1') {
                    backgroudM = "green";
                    backgroudF = "green";
                }else if(matchMode == '2') {
                    backgroudM = "red";
                    backgroudF = "green";
                }else if(matchMode == '3') {
                    backgroudM = "green";
                    backgroudF = "red";
                }

                var strTemp="";

                strTemp+="<tr><td>"+ (++number) + "</td>";
                strTemp+="<td colspan = '4'></td>";
                strTemp+= "<td>"+	$("input[name='matchCount']",$tr).val()	+"</td>";
                strTemp+= "<td>"+	$("input[name='totalPi']",$tr).val() 	+"</td>";
                strTemp+= "<td>"+	$("#relativeSameCount").val()	+"</td>";
                strTemp+= "<td>"+	$("select[name='raceInfoSelectPopulation']").find("option:selected").text()	+"</td>";
                strTemp+= "</tr>";

                if($("#ComparedToCaseSampleFID").val() != "") {
                    strTemp+="<tr><td></td>";
                    strTemp+= "<td>" + $("#ComparedToCaseSampleFID").val() + "</td>";
                    strTemp+= "<td>"+	$("input[name='sampleNameF']",$tr).val()	+"</td>";
                    strTemp+= "<td style='background:" + backgroudF + "'>父" + relationF + "</td>";
                    strTemp+= "<td>"+	$("input[name='panelNameF']",$tr).val()	+"</td>";
                    strTemp+= "<td>-- --</td>";
                    strTemp+= "<td>-- --</td>";
                    strTemp+= "<td>-- --</td>";
                    strTemp+= "<td>-- --</td>";
                    strTemp+= "</tr>";
                }

                if($("#ComparedToCaseSampleMID").val() != "") {
                    strTemp+="<tr><td></td>";
                    strTemp+= "<td>" + $("#ComparedToCaseSampleMID").val() + "</td>";
                    strTemp+= "<td>"+	$("input[name='sampleNameM']",$tr).val()	+"</td>";
                    strTemp+= "<td style='background:" + backgroudM + "'>母 " + relationM + "</td>";
                    strTemp+= "<td>"+	$("input[name='panelNameM']",$tr).val()	+"</td>";
                    strTemp+= "<td>-- --</td>";
                    strTemp+= "<td>-- --</td>";
                    strTemp+= "<td>-- --</td>";
                    strTemp+= "<td>-- --</td>";
                    strTemp+= "</tr>";
                }
                strTemp+="<tr><td></td>";
                strTemp+= "<td>" + $("select[name='ComparedToCaseSampleZ']",$tr).val() + "</td>";
                strTemp+= "<td>"+	$("input[name='sampleNameC']",$tr).val()	+"</td>";
                strTemp+= "<td>子" + relationC + "</td>";
                strTemp+= "<td>"+	$("input[name='panelNameC']",$tr).val()	+"</td>";
                strTemp+= "<td>-- --</td>";
                strTemp+= "<td>-- --</td>";
                strTemp+= "<td>-- --</td>";
                strTemp+= "<td>-- --</td>";

                strTemp+= "<input type='hidden' name= 'caseId' value= " + $("#caseId").val() + ">";
                strTemp+= "<input type='hidden' name= 'fatherSampleNo' value=" + $("#ComparedToCaseSampleFID").val() +">";
                strTemp+= "<input type='hidden' name= 'motherSampleNo' value=" + $("#ComparedToCaseSampleMID").val() +">";
                strTemp+= "<input type='hidden' name= 'childSampleNo' value=" + $("select[name='ComparedToCaseSampleZ']",$tr).val() +">";
                strTemp+= "<input type='hidden' name= 'relativeMatchCount' value=" + $("input[name='matchCount']",$tr).val() +">";
                strTemp+= "<input type='hidden' name= 'relativeTotalProbability' value=" + $("input[name='totalPi']",$tr).val() +">";
                strTemp+= "<input type='hidden' name= 'relativePopulationId' value=" + $("input[name='singlePopulationID']",$tr).val() +">";
                strTemp+= "<input type='hidden' name= 'relativeReferenceId' value=" + $("input[name='rcRadio']:checked").val()  +">";
                strTemp+= "<input type='hidden' name= 'relativeSameCount' value=" +  $("input[name='singleSameCount']",$tr).val() +">";
                strTemp+= "<input type='hidden' name= 'relativeDiffCount' value=" +  $("input[name='singleDiffCount']",$tr).val() +">";
                strTemp+= "<input type='hidden' name= 'relativeMatchMode' value=" +  $("input[name='singleMatchMode']",$tr).val() +">";
                strTemp+= "</tr>";

                $("#raletionID").append(strTemp);
            }
            $("tr:gt(0)","#newAddZTbodyId").remove();
            $('#ComparedToCaseSampleFID').val("");
            $('#ComparedToCaseSampleMID').val("");
            $('#ComparedToCaseSampleZID').val("");
            $("input[name='totalPi']").val("");
            $("input[name='matchCount']").val("");
            $("input[name='matchModeName']").val("");
            $("#addCompareToResultId").prop("disabled", true);

        });

        function GetRelativeCompare (zBarcodeStr) {
            var relationCompare = {};

            relationCompare.matchRelationLimit = $("#relativeSameCount").val();
            relationCompare.halfDiffCount = $("#halfDiffCount").val();
            relationCompare.populationName = $("#relativePopulation").find("option:selected").text();
            relationCompare.populationId = $("#relativePopulation").find("option:selected").val();
            relationCompare.refrenceId = $("input[name='rcRadio']:checked").val();
            relationCompare.fatherSampleNo = $("select[name='ComparedToCaseSampleF']").find("option:selected").val();
            relationCompare.motherSampleNo = $("select[name='ComparedToCaseSampleM']").find("option:selected").val();
            relationCompare.childSampleNoStr = zBarcodeStr;

            return relationCompare;
        }

        $("#addRelationBtn", "#relationFrom").on("click",function(){
            $("#ralationModal").modal('show');
        });

        $("#replyBtn").on("click",function(){
            javascript:history.go(-1);
        });

        $("#saveBtn").on("click",function(){

            var matchLimit = $("#matchLimitTxt").val();
            if(isNaN(matchLimit)){
                alert("请输入数字!");
                $("#matchLimitTxt").val("");
                $("#matchLimitTxt").focus();
                return false;
            }
            var relativeSameCount = $("#relativeSameCount").val();
            if(isNaN(relativeSameCount)){
                alert("请输入数字!");
                $("#relativeSameCount").val("");
                $("#relativeSameCount").focus();
                return false;
            }

            var caseInfo = GetCaseInfo();
            var caseCompareResultInfoList = GetCaseCompareResultInfo();
            var matchedGroupList = matchedSamples();
            var mixMatchedGroupList = mixMatchedGroup();

            if(matchedGroupList.length == 0 && caseCompareResultInfoList.length == 0 && mixMatchedGroupList.length == 0){
                alert("没有比中检材，无法保存!");
                return false;
            }

            var params = {
                "caseInfo": caseInfo,
                "caseCompareResultInfoList": caseCompareResultInfoList
            };

            $.ajax({
                url : "<%=path%>/center/3/saveComparisonResult.html",
                type:"post",
                contentType:  "application/json; charset=utf-8",
                data : JSON.stringify(params),
                dataType : "json",
                success : function(data) {
                    if(data.success || data.success == true || data.success == "true") {
                        var caseId = $("#caseId").val();

                        var url = "<%=path%>/center/3/compare.html?caseId="+ caseId + "&matchLimit=" + matchLimit.trim();
                        location.href=url;
                    }else {
                        alert("保存失败!");
                    }
                },
                error: function(e){
                    alert(e);
                }
            });

        });

        function GetCaseInfo(){
            var caseInfo = {};

            caseInfo.id = $("#caseId").val();
            caseInfo.raceName = $("select[name='raceInfoSelect']").find("option:selected").val();

            var matchLimit = $("#matchLimitTxt").val();
            if(isNaN(matchLimit)){
                alert("请输入数字!")
                $("#matchLimitTxt").focus();
                return false;
            }
            caseInfo.matchLimit = matchLimit;

            return caseInfo;
        }

        function GetCaseCompareResultInfo() {
            var caseCompareResultInfoArr = new Array();
            var caseCompareResultInfo;

            var $caseCompareResultInfoTR = $("tr", "#raletionID");
            var caseCompareResultInfoCnt = $caseCompareResultInfoTR.length;
            for (var i = 0; i < caseCompareResultInfoCnt; i++) {
                caseCompareResultInfo = {};

                caseCompareResultInfo.caseId = $("input[name='caseId']", $caseCompareResultInfoTR.get(i)).val();
                caseCompareResultInfo.fatherSampleNo = $("input[name='fatherSampleNo']", $caseCompareResultInfoTR.get(i)).val();
                caseCompareResultInfo.motherSampleNo = $("input[name='motherSampleNo']", $caseCompareResultInfoTR.get(i)).val();
                caseCompareResultInfo.childSampleNo = $("input[name='childSampleNo']", $caseCompareResultInfoTR.get(i)).val();
                caseCompareResultInfo.compareGeneNum = $("input[name='relativeMatchCount']", $caseCompareResultInfoTR.get(i)).val();
                caseCompareResultInfo.compareTotalProbability = $("input[name='relativeTotalProbability']", $caseCompareResultInfoTR.get(i)).val();
                caseCompareResultInfo.comparePopulationId = $("input[name='relativePopulationId']", $caseCompareResultInfoTR.get(i)).val();
                caseCompareResultInfo.referenceId = $("input[name='relativeReferenceId']", $caseCompareResultInfoTR.get(i)).val();
                caseCompareResultInfo.sameCount = $("input[name='relativeSameCount']", $caseCompareResultInfoTR.get(i)).val();
                caseCompareResultInfo.diffCount = $("input[name='relativeDiffCount']", $caseCompareResultInfoTR.get(i)).val();
                caseCompareResultInfo.matchedMode = $("input[name='relativeMatchMode']", $caseCompareResultInfoTR.get(i)).val();

                caseCompareResultInfoArr.push(caseCompareResultInfo);
            }

            return caseCompareResultInfoArr;
        }

        function matchedSamples(){
            var matchedSamplesArr = new Array();

            var $matchedSamplesTR = $("tr", "#matchedGroupTBody");
            var matchedSamplesCnt = $matchedSamplesTR.length;
            var matchedSamples;
            for (var i = 0; i < matchedSamplesCnt; i++) {
                matchedSamples = {};
                matchedSamples.sampleId = $("input[name='sampleId']", $matchedSamplesTR.get(i)).val();
                matchedSamples.createPerson = $("input[name='createPerson']", $matchedSamplesTR.get(i)).val();
                matchedSamples.geneInfo = $("input[name='geneInfo']", $matchedSamplesTR.get(i)).val();

                matchedSamplesArr.push(matchedSamples);
            }

            return matchedSamplesArr;
        }

        function mixMatchedGroup(){
            var mixMatchedSamplesArr = new Array();

            var $mixMatchedSamplesArrTR = $("tr", "#mixSampleGeneList");
            var mixMatchedSamplesArrCnt = $mixMatchedSamplesArrTR.length;
            var mixMatchedSamples;
            for (var i = 0; i < mixMatchedSamplesArrCnt; i++) {
                mixMatchedSamples = {};
                mixMatchedSamples.sampleId = $("input[name='sampleId']", $mixMatchedSamplesArrTR.get(i)).val();

                mixMatchedSamplesArr.push(mixMatchedSamples);
            }

            return mixMatchedSamplesArr;
        }

        $("#importCodisBtn").on("click",function(){
            var caseId = $("#caseId").val();
            var matchLimit = $("#matchLimitTxt").val();
            if(isNaN(matchLimit)){
                alert("请输入数字!");
                $("#matchLimitTxt").focus();
                return false;
            }
            var url = "<%=path%>/center/3/compare.html?caseId="+ caseId + "&matchLimit=" + matchLimit.trim();
            location.href=url;
        });

        $('.form_datetime').datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            language:  'zh',
            weekStart: 1,
            todayBtn:  1,
            minView: "hour",
            autoclose: true,
            todayHighlight: true,
            forceParse: 0,
            showMeridian: 1
        }).on('changeDate', function (ev) {
            $(this).datetimepicker('hide');
        });

        /*var active = parent.window.frames["leftFrame"].document.getElementById("Testimonial");

        if($(active).attr("class")=="active"){
            $("#nextStep").css("display","none")
        }*/
        $("#nextStep").on("click",function(){
            var caseId = $("input[name='caseInfoId']", $(this).parent()).val();
            var deviceType = $(parent.window.document.getElementsByClassName("layui-this"));
            deviceType.removeClass("layui-this").siblings(":contains('上报国家库')").addClass("layui-this");
            location.href = "<%=path%>/center/3/uploadCountryDB.html?caseId=" + caseId;
        });

    });
</script>
<!-- END JS -->
</body>
</html>
