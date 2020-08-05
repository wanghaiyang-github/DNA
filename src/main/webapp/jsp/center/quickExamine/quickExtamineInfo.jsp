<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/include.jsp" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <%@ include file="../../common/link.jsp" %>
</head>
<body style="overflow:auto;">
<section id="main-content">
    <section class="wrapper">
        <div id="wrapper-content">
            <!-- BEGIN ROW  -->
            <div class="row">
                <div class="col-lg-12" style="width: 125%">
                    <section class="panel">
                        <header class="panel-heading">
                            <span class="label label-primary">提取信息</span>
                               <span class="tools pull-right">
                               <a href="javascript:;" class="fa fa-chevron-down"></a>
                               </span>
                        </header>
                        <div class="panel-body">
                            <form class="form-horizontal tasi-form" method="get">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" style="text-align: right;">提取人 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-2">
                                        <select class="form-control inline-block" id="extractPerson1">
                                            <c:forEach items="${labUserList}" var="labUser" varStatus="lu">
                                                <option value="${labUser.id}"
                                                        <c:if test="${limsExtractRecord.extractPersonId1 eq labUser.id}">selected</c:if>>${labUser.userName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2">
                                        <select class="form-control inline-block" id="extractPerson2">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${labUserList}" var="labUser" varStatus="lu">
                                                <option value="${labUser.id}"
                                                        <c:if test="${limsExtractRecord.extractPersonId2 eq labUser.id}">selected</c:if>>${labUser.userName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align: right;">提取时间 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-2">
                                        <input type="text" class="form-control form_datetime" id="extractDatetime"
                                               value="<fmt:formatDate value='${limsExtractRecord.extractDatetime}' pattern='yyyy-MM-dd HH:mm'/>"
                                               readonly>
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
                <div class="col-lg-12" style="width: 125%">
                    <section class="panel">
                        <header class="panel-heading">
                            <span class="label label-primary">扩增信息</span>
                               <span class="tools pull-right">
                               <a href="javascript:;" class="fa fa-chevron-down"></a>
                               </span>
                        </header>
                        <div class="panel-body">
                            <form class="form-horizontal tasi-form" method="get">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" style="text-align: right;">扩增人<i
                                            class="fa fa-asterisk color_red"></i></label></label>
                                    <div class="col-sm-2">
                                        <select class="form-control" id="pcrPerson1"
                                                value="${limsPcrRecord.pcrPersonId1}">
                                            <c:forEach items="${labUserList}" var="labUser" varStatus="lu">
                                                <option value="${labUser.id}"
                                                        <c:if test="${limsPcrRecord.pcrPersonId1 eq labUser.id}">selected</c:if>>${labUser.userName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2">
                                        <select class="form-control" id="pcrPerson2"
                                                value="${limsPcrRecord.pcrPersonId2}">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${labUserList}" var="labUser" varStatus="lu">
                                                <option value="${labUser.id}"
                                                        <c:if test="${limsPcrRecord.pcrPersonId2 eq labUser.id}">selected</c:if>>${labUser.userName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align: right;">扩增时间 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-4">
                                        <div class="input-group input-large">
                                            <input type="text" class="form-control form_datetime" placeholder="开始时间"
                                                   id="pcrStartDatetime"
                                                   value="<fmt:formatDate value='${limsPcrRecord.pcrStartDatetime}' pattern='yyyy-MM-dd HH:mm'/>"
                                                   readonly/>
                                            <span class="input-group-addon">
                                            至
                                            </span>
                                            <input type="text" class="form-control form_datetime" placeholder="结束时间"
                                                   id="pcrEndDatetime"
                                                   value="<fmt:formatDate  value='${limsPcrRecord.pcrEndDatetime}'  pattern='yyyy-MM-dd HH:mm'/>"
                                                   readonly/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" style="text-align: right;">加样超净台</label>
                                    <div class="col-sm-4">
                                        <select class="form-control" id="pcrProgram"
                                                value="${limsPcrRecord.pcrProgram}">
                                            <c:forEach items="${pcrInstrumentProgramList}" var="pcrProgram" varStatus="pp">
                                                <option value="${pcrProgram.id}"
                                                        <c:if test="${limsPcrRecord.pcrProgram eq pcrProgram.id}">selected</c:if>>${pcrProgram.equipmentName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align: right;">扩增试剂盒</label>
                                    <div class="col-sm-4">
                                        <select class="form-control" id="pcrReagent"
                                                value="${limsPcrRecord.pcrReagent}">
                                            <%--<c:forEach items="${pcrReagentKitList}" var="pcrReagent" varStatus="pr">
                                                <option value="${pcrReagent.dictCode}"
                                                        <c:if test="${limsPcrRecord.pcrReagent eq pcrReagent.dictCode}">selected</c:if>>${pcrReagent.dictName}</option>
                                            </c:forEach>--%>
                                                <c:forEach items="${panelInfoList}" var="panelInfo" varStatus="pr">
                                                    <option value="${panelInfo.id}"
                                                            <c:if test="${limsPcrRecord.pcrReagent eq panelInfo.id}">selected</c:if>>${panelInfo.panelName}</option>
                                                </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" style="text-align: right;">扩增仪</label>
                                    <div class="col-sm-4">
                                        <select class="form-control" id="pcrInstrument"
                                                value="${limsPcrRecord.pcrInstrument}">
                                            <c:forEach items="${pcrInstrumentList}" var="pcrInstrument" varStatus="pi">
                                                <option value="${pcrInstrument.id}"
                                                        <c:if test="${limsPcrRecord.pcrInstrument eq pcrInstrument.id}">selected</c:if>>${pcrInstrument.equipmentName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align: right;">扩增体系</label>
                                    <div class="col-sm-4">
                                        <select class="form-control" id="pcrSystem" value="${limsPcrRecord.pcrSystem}">
                                            <c:forEach items="${pcrSystemList}" var="pcrSystem" varStatus="ps">
                                                <option value="${pcrSystem.dictCode}"
                                                        <c:if test="${limsPcrRecord.pcrSystem eq pcrSystem.dictCode}">selected</c:if>>${pcrSystem.dictName}</option>
                                            </c:forEach>
                                        </select>
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
                <div class="col-lg-12" style="width: 125%">
                    <section class="panel">
                        <header class="panel-heading">
                            <span class="label label-primary">上样信息</span>
                               <span class="tools pull-right">
                               <a href="javascript:;" class="fa fa-chevron-down"></a>
                            </span>
                        </header>
                        <div class="panel-body">
                            <form class="form-horizontal tasi-form" method="get">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" style="text-align: right;">操作人 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-2">
                                        <select class="form-control" id="syPerson1"
                                                value="${limsSyRecord.operatePersonId1}">
                                            <c:forEach items="${labUserList}" var="labUser" varStatus="lu">
                                                <option value="${labUser.id}"
                                                        <c:if test="${limsSyRecord.operatePersonId1 eq labUser.id}">selected</c:if>>${labUser.userName}</option>

                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2">
                                        <select class="form-control" id="syPerson2"
                                                value="${limsSyRecord.operatePersonId2}">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${labUserList}" var="labUser" varStatus="lu">
                                                <option value="${labUser.id}"
                                                        <c:if test="${limsSyRecord.operatePersonId2 eq labUser.id}">selected</c:if>>${labUser.userName}</option>

                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align: right;">电泳时间 <i
                                            class="fa fa-asterisk color_red"></i></label>
                                    <div class="col-sm-4">
                                        <div class="input-group input-large">
                                            <input type="text" class="form-control form_datetime" placeholder="开始时间"
                                                   id="startDatetime"
                                                   value="<fmt:formatDate value='${limsSyRecord.startDatetime}' pattern='yyyy-MM-dd HH:mm'/>"
                                                   readonly/>
                                            <span class="input-group-addon">
                                            至
                                            </span>
                                            <input type="text" class="form-control form_datetime" placeholder="结束时间"
                                                   id="endDatetime"
                                                   value="<fmt:formatDate value='${limsSyRecord.endDatetime}' pattern='yyyy-MM-dd HH:mm'/>"
                                                   readonly/>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" style="text-align: right;">产物ul</label>
                                    <div class="col-sm-4">
                                        <select class="form-control" id="chanwu" value="${limsSyRecord.chanwuFlag}">
                                            <c:forEach items="${chanwuList}" var="chanwu" varStatus="pp">
                                                <option value="${chanwu.dictCode}"
                                                        <c:if test="${limsSyRecord.chanwuFlag eq chanwu.dictCode}">selected</c:if>>${chanwu.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align: right;">甲酰胺ul</label>
                                    <div class="col-sm-4">
                                        <select class="form-control" id="jiaxianan"
                                                value="${limsSyRecord.jiaxiananFlag}">
                                            <c:forEach items="${jiaxiananList}" var="jiaxianan" varStatus="pp">
                                                <option value="${jiaxianan.dictCode}"
                                                        <c:if test="${limsSyRecord.jiaxiananFlag eq jiaxianan.dictCode}">selected</c:if>>${jiaxianan.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" style="text-align: right;">内标ul</label>
                                    <div class="col-sm-4">
                                        <select class="form-control" id="neibiaoul"
                                                value="${limsSyRecord.neibiaoUlFlag}">
                                            <c:forEach items="${neibiaoulList}" var="neibiaoul" varStatus="pp">
                                                <option value="${neibiaoul.dictCode}"
                                                        <c:if test="${limsSyRecord.neibiaoUlFlag eq neibiaoul.dictCode}">selected</c:if>>${neibiaoul.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align: right;">内 标</label>
                                    <div class="col-sm-4">
                                        <select class="form-control" id="neibiao" value="${limsSyRecord.neibiaoFlag}">
                                            <c:forEach items="${neibiaoList}" var="neibiao" varStatus="pp">
                                                <option value="${neibiao.dictCode}"
                                                        <c:if test="${limsSyRecord.neibiaoFlag eq neibiao.dictCode}">selected</c:if>>${neibiao.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" style="text-align: right;">电泳仪</label>
                                    <div class="col-sm-4">
                                        <select class="form-control" id="elecInstrument"
                                                value="${limsSyRecord.elecInstrument}">
                                            <c:forEach items="${elecInstrumentList}" var="elecInstrument"
                                                       varStatus="pp">
                                                <option value="${elecInstrument.id}"
                                                        <c:if test="${limsSyRecord.elecInstrument eq elecInstrument.id}">selected</c:if>>${elecInstrument.equipmentName}</option>
                                            </c:forEach>
                                            <option value="">其他</option>
                                        </select>
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
                <div class="col-lg-12" style="width: 125%">
                    <section class="panel">
                        <header class="panel-heading">
                            <span class="label label-primary">样本列表</span>
               <span class="tools pull-right">
               <a href="javascript:;" class="fa fa-chevron-down"></a>
               </span>
                        </header>
                        <div class="panel-body">
                            <form class="form-horizontal tasi-form">
                                <table class="table table-striped table-advance table-bordered table-hover">
                                    <thead>
                                    <tr>
                                        <th rowspan="2" style='width:2.2%;font-size: 12px;"'>
                                            <label class="checkbox-inline"><input type="checkbox" name="allChecked"
                                                                                  id="allChecked" checked/>全选</label>
                                        </th>
                                        <th rowspan="2" style='width:1%;font-size: 12px;"'>序号</th>
                                        <th rowspan="2" style='width:9%;font-size: 12px;"'>检材编号</th>
                                        <th rowspan="2" style='width:9%;font-size: 12px;"'>检材名称</th>
                                        <th rowspan="2" style='width:7%;font-size: 12px;"'>检材类型</th>
                                        <th rowspan="2" style='width:4%;font-size: 12px;"'>FOB</th>
                                        <th rowspan="2" style='width:4%;font-size: 12px;"'>PSA</th>
                                        <th rowspan="2" style='width:8%;font-size: 12px;"'>转移方法</th>
                                        <th rowspan="2" style='width:8%;font-size: 12px;"'>提取部位</th>
                                        <th rowspan="2" style='width:4%;font-size: 12px;"'>方法</th>
                                        <th rowspan="2" style='width:7%;font-size: 12px;"'>上样板号<i
                                                class="fa fa-asterisk color_red"></i></th>
                                        <th rowspan="2" style='width:5.5%;font-size: 12px;"'>板孔位置</th>
                                        <th rowspan="2" style='width:3.5%;font-size: 12px;"'>Primer ul</th>
                                        <th rowspan="2" style='width:3.5%;font-size: 12px;"'>Master Mix ul</th>
                                        <th rowspan="2" style='width:3.5%;font-size: 12px;"'>模板 ul</th>
                                        <th rowspan="2" style='width:3.5%;font-size: 12px;"'>D.D.W. ul</th>
                                        <th colspan="4" style="font-size: 12px;width:17%;">使用仪器</th>
                                        <th rowspan="4" style="font-size: 12px;">操作</th>
                                    </tr>
                                    <tr>
                                        <th>离心机</th>
                                        <th>震荡器</th>
                                        <th>工作站</th>
                                        <th>金属浴</th>
                                    </tr>
                                    </thead>
                                    <tbody id="quickExaminTable">
                                    <c:forEach items="${limsQuickExamineSampleList}" var="sample" varStatus="s">
                                        <tr>
                                            <td><label class="checkbox-inline"><input type="checkbox" name="box"
                                                                                      checked></label></td>
                                            <td></td>
                                            <td>
                                                <input name="sampleNo" style="font-size: 12px;" type="text"
                                                       class="form-control small" onmouseover="this.title=this.value"
                                                       value="${sample.sampleNo}" readonly="readonly">
                                            </td>
                                            <td>
                                                <input name="sampleName" style="font-size: 12px;" type="text"
                                                       class="form-control small" onmouseover="this.title=this.value"
                                                       value="${sample.sampleName}" readonly="readonly">
                                            </td>
                                            <td>
                                                    <%--<input name="sampleTypeName" style="font-size: 13px;" type="text" class="form-control small" value="${sample.sampleTypeName}" readonly="readonly">--%>
                                                <select name="sampleTypeName" class="form-control small"
                                                        style="font-size: 15px;padding-top: 1px;"
                                                        value="${sample.sampleTypeName}">
                                                    <option value="01"
                                                            <c:if test="${sample.sampleTypeName eq '血斑/血液'}">selected</c:if>>
                                                        血斑/血液
                                                    </option>
                                                    <option value="02"
                                                            <c:if test="${sample.sampleTypeName eq '精斑'}">selected</c:if>>
                                                        精斑
                                                    </option>
                                                    <option value="03"
                                                            <c:if test="${sample.sampleTypeName eq '毛发'}">selected</c:if>>
                                                        毛发
                                                    </option>
                                                    <option value="04"
                                                            <c:if test="${sample.sampleTypeName eq '指甲'}">selected</c:if>>
                                                        指甲
                                                    </option>
                                                    <option value="05"
                                                            <c:if test="${sample.sampleTypeName eq '烟蒂'}">selected</c:if>>
                                                        烟蒂
                                                    </option>
                                                    <option value="06"
                                                            <c:if test="${sample.sampleTypeName eq '唾液（斑）'}">selected</c:if>>
                                                        唾液（斑）
                                                    </option>
                                                    <option value="07"
                                                            <c:if test="${sample.sampleTypeName eq '骨骼/牙齿'}">selected</c:if>>
                                                        骨骼/牙齿
                                                    </option>
                                                    <option value="08"
                                                            <c:if test="${sample.sampleTypeName eq '肌肉（组织）'}">selected</c:if>>
                                                        肌肉（组织）
                                                    </option>
                                                    <option value="09"
                                                            <c:if test="${sample.sampleTypeName eq '组织'}">selected</c:if>>
                                                        组织
                                                    </option>
                                                    <option value="10"
                                                            <c:if test="${sample.sampleTypeName eq '指纹'}">selected</c:if>>
                                                        指纹
                                                    </option>
                                                    <option value="11"
                                                            <c:if test="${sample.sampleTypeName eq '脱落细胞'}">selected</c:if>>
                                                        脱落细胞
                                                    </option>
                                                    <option value="99"
                                                            <c:if test="${sample.sampleTypeName eq '其他'}">selected</c:if>>
                                                        其他
                                                    </option>
                                                </select>
                                            </td>
                                            <td>
                                                <select name="extractHb" class="form-control small"
                                                        style="font-size: 17px;" value="${sample.extractHb}">
                                                    <option value=""></option>
                                                    <option value="+"
                                                            <c:if test="${sample.extractHb eq '+'}">selected</c:if>>+
                                                    </option>
                                                    <option value="-"
                                                            <c:if test="${sample.extractHb eq '-'}">selected</c:if>>-
                                                    </option>
                                                </select>
                                            </td>
                                            <td>
                                                <select name="extractPsa" class="form-control small"
                                                        style="font-size: 17px;" value="${sample.extractPsa}">
                                                    <option value=""></option>
                                                    <option value="+"
                                                            <c:if test="${sample.extractPsa eq '+'}">selected</c:if>>+
                                                    </option>
                                                    <option value="-"
                                                            <c:if test="${sample.extractPsa eq '-'}">selected</c:if>>-
                                                    </option>
                                                </select>
                                            </td>
                                            <td>
                                                <select name="changeMethod" class="form-control small"
                                                        style='width:82%;float:left;font-size: 13px;'
                                                        value="${sample.changeMethod}"
                                                        onmouseover="this.title=this.value">
                                                    <option value=""></option>
                                                    <option value="剪取"
                                                            <c:if test="${sample.changeMethod eq '剪取'}">selected</c:if>>
                                                        剪取
                                                    </option>
                                                    <option value="棉签转移"
                                                            <c:if test="${sample.changeMethod eq '棉签转移'}">selected</c:if>>
                                                        棉签转移
                                                    </option>
                                                    <option value="其他"
                                                            <c:if test="${sample.changeMethod eq '其他'}">selected</c:if>>
                                                        其他
                                                    </option>
                                                </select>
                                                <input type="text" name="otherChangeMethod"
                                                       style='width:82%;float:left;' value="${sample.otherChangeMethod}"
                                                       class="form-control small hide" placeholder="其他转移方法"
                                                       onmouseover="this.title=this.value"/>
                                                <a href="#" name="methodBtnPullDown" title="向下填充"><img border="0"
                                                                                                       src="<%=path%>/images/field_Down.bmp"
                                                                                                       style="cursor:pointer;margin-left:3px;margin-top:5px;"></a>
                                            </td>
                                            <td>
                                                <input name="extractPosition"
                                                       style="width:82%;float:left;font-size: 12px;" type="text"
                                                       class="form-control small" value="${sample.extractPosition}"
                                                       onmouseover="this.title=this.value">
                                                <a href="#" name="positionBtnPullDown" title="向下填充"><img border="0"
                                                                                                         src="<%=path%>/images/field_Down.bmp"
                                                                                                         style="cursor:pointer;margin-left:3px;margin-top:5px;"></a>
                                            </td>
                                            <td>
                                                <select name="extractMethod" style="font-size: 12px;"
                                                        class="form-control small" value="${sample.extractMethod}">
                                                    <c:forEach items="${extractMethodList}" var="extractMethod"
                                                               varStatus="em">
                                                        <option value="${extractMethod.dictCode}"
                                                                <c:if test="${sample.extractMethod eq extractMethod.dictCode}">selected</c:if>>${extractMethod.dictCode}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input name="boardNo" type="text" class="form-control small"
                                                       style='width:82%;float:left;font-size: 12px;'
                                                       value="${sample.syBoardNo}" onmouseover="this.title=this.value">
                                                <a href="#" name="btnBoardNoPullDown" title="向下填充板号"><img border="0"
                                                                                                          src="<%=path%>/images/field_Down.bmp"
                                                                                                          style="cursor:pointer;margin-left:3px;margin-top:5px;"></a>
                                            </td>
                                            <td>
                                                <select name="samplePosition" class="form-control small"
                                                        value="${sample.samplePosition}"
                                                        style='width:82%;float:left;font-size: 12px;'>
                                                    <c:forEach items="${samplePositionList}" var="position"
                                                               varStatus="s">
                                                        <option value="${position}"
                                                                <c:if test="${position eq sample.samplePosition}">selected</c:if>>${position}</option>
                                                    </c:forEach>
                                                </select>
                                            </td>
                                            <td>
                                                <input name="primerUl" type="text" class="form-control small"
                                                       value="${sample.primerUl}">
                                            </td>
                                            <td>
                                                <input name="bufferUl" type="text" class="form-control small"
                                                       value="${sample.bufferUl}">
                                            </td>
                                            <td>
                                                <input name="templateUl" type="text" class="form-control small"
                                                       value="${sample.templateUl}">
                                            </td>
                                            <td>
                                                <input name="ddwUl" type="text" class="form-control small"
                                                       value="${sample.ddwUl}">
                                            </td>
                                            <td>
                                                <%--<label class="checkbox-inline"><input type="checkbox"
                                                                                      name="extractLiFlag"
                                                                                      <c:if test="${sample.extractLiFlag eq 1}">checked</c:if>></label>--%>
                                                    <select name="extractLiFlag" class="form-control">
                                                        <c:forEach items="${pcrInstrumentLxjList}" var="pcrInstrumentLxj"  varStatus="em">
                                                            <option value="${pcrInstrumentLxj.id}"
                                                                    <c:if test="${sample.extractLiFlag eq pcrInstrumentLxj.id}">selected</c:if>
                                                            >${pcrInstrumentLxj.equipmentNo}</option>
                                                        </c:forEach>
                                                    </select>
                                            </td>
                                            <td>
                                                <%--<label class="checkbox-inline"><input type="checkbox"
                                                                                      name="extractZhenFlag"
                                                                                      <c:if test="${sample.extractZhenFlag eq 1}">checked</c:if>></label>--%>
                                                    <select name="extractZhenFlag" class="form-control small">
                                                        <c:forEach items="${pcrInstrumentZtqList}" var="pcrInstrumentZtq"  varStatus="em">
                                                            <option value="${pcrInstrumentZtq.id}"
                                                                    <c:if test="${sample.extractZhenFlag eq pcrInstrumentZtq.id}">selected</c:if>
                                                            >${pcrInstrumentZtq.equipmentNo}</option>
                                                        </c:forEach>
                                                    </select>
                                            </td>
                                            <td>
                                                <%--<label class="checkbox-inline"><input type="checkbox"
                                                                                      name="extractJiaoFlag"
                                                                                      <c:if test="${sample.extractJiaoFlag eq 1}">checked</c:if>></label>--%>
                                                    <select name="extractJiaoFlag" class="form-control small">
                                                        <c:forEach items="${pcrInstrumentGzzList}" var="pcrInstrumentGzz"  varStatus="em">
                                                            <option value="${pcrInstrumentGzz.id}"
                                                                    <c:if test="${sample.extractJiaoFlag eq pcrInstrumentGzz.id}">selected</c:if>
                                                            >${pcrInstrumentGzz.equipmentNo}</option>
                                                        </c:forEach>
                                                    </select>
                                            </td>
                                            <td>
                                                <%--<label class="checkbox-inline"><input type="checkbox"
                                                                                      name="extractYuFlag"
                                                                                      <c:if test="${sample.extractYuFlag eq 1}">checked</c:if>></label>--%>
                                                    <select name="extractYuFlag" class="form-control small">
                                                        <c:forEach items="${pcrInstrumentJsyList}" var="pcrInstrumentJsy"  varStatus="em">
                                                            <option value="${pcrInstrumentJsy.id}"
                                                                    <c:if test="${sample.extractYuFlag eq pcrInstrumentJsy.id}">selected</c:if>
                                                            >${pcrInstrumentJsy.equipmentNo}</option>
                                                        </c:forEach>
                                                    </select>
                                            </td>
                                            <td>
                                                <input type="hidden" name="extractRecordSampleId"
                                                       value="${sample.extractRecordSampleId}"/>
                                                <input type="hidden" name="pcrRecordSampleId"
                                                       value="${sample.pcrRecordSampleId}"/>
                                                <input type="hidden" name="syRecordSampleId"
                                                       value="${sample.syRecordSampleId}"/>
                                                <input type="hidden" name="extractRecordId"
                                                       value="${sample.extractRecordId}"/>
                                                <input type="hidden" name="pcrRecordId" value="${sample.pcrRecordId}"/>
                                                <input type="hidden" name="syRecordId" value="${sample.syRecordId}"/>
                                                <input type="hidden" name="sampleId" value="${sample.sampleId}"/>
                                                <input type="hidden" name="standardPcrFlag"
                                                       value="${sample.standardPcrFlag}"/>
                                                <input type="hidden" name="standardSyFlag"
                                                       value="${sample.standardSyFlag}"/>
                                                <button type="button" name="delExtractBtn"
                                                        class="btn btn-danger btn-sm"><i class="fa fa-trash-o"></i> 删除
                                                </button>
                                            </td>
                                        </tr>
                                    </c:forEach>
                                    </tbody>
                                </table>
                                <div class="form-group">
                                    <div class="col-sm-3 pull-right">
                                        <input type="hidden" id="operateType" value="${operateType}"/>
                                        <input type="hidden" id="caseId" value="${caseId}"/>
                                        <input type="hidden" id="extractRecordId"
                                               value="${limsQuickExamineRecord.limsExtractRecordId}"/>
                                        <input type="hidden" id="pcrRecordId"
                                               value="${limsQuickExamineRecord.limsPcrRecordId}"/>
                                        <input type="hidden" id="syRecordId"
                                               value="${limsQuickExamineRecord.limsSyRecordId}"/>
                                        <input type="hidden" id="quickExamineRecordId"
                                               value="${limsQuickExamineRecord.id}"/>
                                        <button class="btn btn-primary" type="button" id="saveBtn"><i
                                                class="fa fa-check"></i> 保 存
                                        </button>
                                        &nbsp;
                                        <button class="btn btn-info" type="button" id="backToListBtn"><i
                                                class="fa fa-reply"></i> 返回列表页面
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
                <div class="col-lg-12" style="width: 125%">
                    <section class="panel">
                        <header class="panel-heading">
                            <span class="label label-primary">提取方法</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                            <form class="form-horizontal tasi-form">

                                <c:forEach items="${extractMethodList}" var="method" varStatus="em">
                                    <div class="form-group">
                                        <div class="col-sm-12">
                                            <p>
                                                <strong>${method.dictCode}、 ${method.dictName}</strong> ${method.dictDesc}
                                            </p>
                                        </div>
                                    </div>
                                </c:forEach>

                            </form>
                        </div>
                    </section>
                </div>
            </div>
            <!-- END ROW  -->
            <div class="modal fade" id="quickExamineRecordModal" aria-hidden="true" data-backdrop="static"
                 data-keyboard="false">
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
                            <button class="btn btn-success btn-sm" type="button" id="OpenExtractDocBtn"><i
                                    class="fa fa-file-text-o"></i> 下载提取记录表
                            </button>
                            &nbsp;
                            <button class="btn btn-success btn-sm" type="button" id="OpenPcrDocBtn"><i
                                    class="fa fa-file-text-o"></i> 下载扩增记录表
                            </button>
                            &nbsp;
                            <button class="btn btn-success btn-sm" type="button" id="OpenSyDocBtn"><i
                                    class="fa fa-file-text-o"></i> 下载上样记录表
                            </button>
                            &nbsp;
                            <button class="btn btn-default btn-sm" type="button" id="FinishedBtn"><i
                                    class="fa fa-list-alt"></i> 完成
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</section>
<!-- BEGIN JS -->
<%@ include file="../../common/script.jsp" %>
<script>

    generateIdx();

    function generateIdx() {
        $("tr", "#quickExaminTable").each(function () {
            $("td:nth-child(2)", $(this)).text($(this).index() + 1);
        });
    }

    getOtherChangeMethod();

    function getOtherChangeMethod() {
        $("tr", "#quickExaminTable").each(function () {
            var otherChangeMethod = $("input[name='otherChangeMethod']", $(this)).val();
            if (otherChangeMethod != "")
                $("input[name='otherChangeMethod']", $(this)).removeClass("hide");
        });
    }

    $(function () {
        'use strict';

        $("#pcrStartDatetime").on("change", function () {
            var pcrStartDatetime = $("#pcrStartDatetime").val();
            var dateTime = new Date(pcrStartDatetime).getTime();
            var endDataTime = new Date(dateTime + (2 * 60 * 60 * 1000 + 30 * 60 * 1000));
            var Y = endDataTime.getFullYear() + "-";
            var M = (endDataTime.getMonth() + 1 < 10 ? "0" + (endDataTime.getMonth() + 1) : endDataTime.getMonth() + 1) + "-";
            var D = (endDataTime.getDate() + 1 < 10 ? "0" + (endDataTime.getDate()) : endDataTime.getDate()) + " ";
            var h = (endDataTime.getHours() + 1 < 10 ? "0" + (endDataTime.getHours()) : endDataTime.getHours()) + ":";
            var m = (endDataTime.getMinutes() + 1 < 10 ? "0" + (endDataTime.getMinutes()) : endDataTime.getMinutes());
            $("#pcrEndDatetime").val(Y + M + D + h + m);
        });

        $("#startDatetime").on("change", function () {
            var startDatetime = $("#startDatetime").val();
            var dateTime = new Date(startDatetime).getTime();
            var endDataTime = new Date(dateTime + (2 * 60 * 60 * 1000 + 30 * 60 * 1000));
            var Y = endDataTime.getFullYear() + "-";
            var M = (endDataTime.getMonth() + 1 < 10 ? "0" + (endDataTime.getMonth() + 1) : endDataTime.getMonth() + 1) + "-";
            var D = (endDataTime.getDate() + 1 < 10 ? "0" + (endDataTime.getDate()) : endDataTime.getDate()) + " ";
            var h = (endDataTime.getHours() + 1 < 10 ? "0" + (endDataTime.getHours()) : endDataTime.getHours()) + ":";
            var m = (endDataTime.getMinutes() + 1 < 10 ? "0" + (endDataTime.getMinutes()) : endDataTime.getMinutes());
            $("#endDatetime").val(Y + M + D + h + m);
        });

        $("a[name='positionBtnPullDown']", "#quickExaminTable").on("click", function () {
            var $curTR = $(this).parents("tr");

            var extractPosition = $("input[name='extractPosition']", $curTR).val();
            evaluateExtractPosition($(this), extractPosition);
        });

        function evaluateExtractPosition(obj, extractPosition) {
            var totalRows = $("tr", "#quickExaminTable").length;
            var curIdx = $(obj).parents("tr").index();

            var startIdx = curIdx + 1;
            for (var i = startIdx; i < totalRows; i++) {
                $("input[name='extractPosition']", "tbody tr:eq(" + i + ")").val(extractPosition);
            }
        }

        $("a[name='methodBtnPullDown']", "#quickExaminTable").on("click", function () {
            var $curTR = $(this).parents("tr");

            var changeMethod = $("select[name='changeMethod']", $curTR).val();
            var otherChangeMethod = $("input[name='otherChangeMethod']", $curTR).val();
            if (changeMethod != "") {
                evaluateChangeMethod($(this), changeMethod, otherChangeMethod);
            } else {
                alert("请选择转移方法！");
            }
        });

        function evaluateChangeMethod(obj, changeMethod, otherChangeMethod) {
            var totalRows = $("tr", "#quickExaminTable").length;
            var curIdx = $(obj).parents("tr").index();

            var startIdx = curIdx + 1;
            for (var i = startIdx; i < totalRows; i++) {
                $("select[name='changeMethod']", "tbody tr:eq(" + i + ")").val(changeMethod);
                if (changeMethod != "" && changeMethod == "其他") {
                    $("input[name='otherChangeMethod']", "tbody tr:eq(" + i + ")").val(otherChangeMethod);
                } else {
                    $("input[name='otherChangeMethod']", "tbody tr:eq(" + i + ")").val("");
                    $("input[name='otherChangeMethod']", "tbody tr:eq(" + i + ")").addClass("hide");
                }
            }

            if (changeMethod != "" && changeMethod == "其他") {
                getOtherChangeMethod();
            }
        }

        $("select[name='changeMethod']", "#quickExaminTable").on("change", function () {
            var $curTR = $(this).parents("tr");

            var changeMethod = $("select[name='changeMethod']", $curTR).val();
            if (changeMethod != "" && changeMethod == "其他") {
                var otherChangeMethod = $("input[name='otherChangeMethod']", $curTR).val();
                $("input[name='otherChangeMethod']", $curTR).val(otherChangeMethod);
                $("input[name='otherChangeMethod']", $curTR).removeClass("hide");
            } else {
                $("input[name='otherChangeMethod']", $curTR).addClass("hide");
            }
        });

        $("#allChecked").on("click", function () {
            var ch = document.getElementsByName("box");
            if (document.getElementsByName("allChecked")[0].checked == true) {
                for (var i = 0; i < ch.length; i++) {
                    ch[i].checked = true;
                }
            } else {
                for (var i = 0; i < ch.length; i++) {
                    ch[i].checked = false;
                }
            }
        });

        $("a[name='btnBoardNoPullDown']", "#quickExaminTable").on("click", function () {
            var boardNo = $("input[name='boardNo']", $(this).parent().parent()).val();

            if (boardNo == "") {
                alert("请输入上样板号！");
                $("input[name='boardNo']", $(this).parent().parent()).focus();
            } else {
                evaluateBoardNo($(this), boardNo);
            }

        });

        function evaluateBoardNo(obj, boardNo) {
            var totalRows = $("tr", "#quickExaminTable").length;
            var curIdx = $(obj).parents("tr").index();

            var startIdx = curIdx + 1;
            var endRowIdx = curIdx + 96;
            for (var i = startIdx; i < endRowIdx; i++) {
                $("input[name='boardNo']", "tbody tr:eq(" + i + ")").val(boardNo);
            }

            var syRow = totalRows - curIdx;         //剩余行数
            if (syRow > 96) {
                $("input[name='boardNo']", "tbody tr:eq(" + (endRowIdx) + ")").focus();
                alert("样本超出96个，请转到" + (endRowIdx + 1) + "行添加新的板号！");
            }
        }

        $("a[name='btnPositionPullDown']", "#quickExaminTable").on("click", function () {
            var samplePosition = $("select[name='samplePosition']", $(this).parent().parent()).val();

            if (samplePosition == "") {
                alert("请选择板孔位值！");
                return;
            }

            evaluatePosition($(this), samplePosition);
        });

        function evaluatePosition(obj, samplePosition) {
            var totalRows = $("tr", "#quickExaminTable").length;
            var curIdx = $(obj).parents("tr").index();

            var startIdx = curIdx + 1;
            var endRowIdx = curIdx + 96;
            var selectedIndex = 0;

            for (var i = startIdx; i < endRowIdx; i++) {
//                var positionValue = $("select[name='samplePosition']").options[i].value;

                selectedIndex = $("select[name='samplePosition']").selectedIndex;
                $("select[name='samplePosition'] option[selectedIndex]").prop("selected", true);

            }

            var syRow = totalRows - curIdx;         //剩余行数
            if (syRow > 96) {
                alert("样本超出96个，请转到" + (endRowIdx + 1) + "行选择新的板孔！");
            }
        }

        function GetLimsExtractRecord() {
            var limsExtractRecord = {};
            limsExtractRecord.caseId = $("#caseId").val();
            limsExtractRecord.extractDatetime = $("#extractDatetime").val();
            limsExtractRecord.extractPersonId1 = $("option:selected", "#extractPerson1").val();
            limsExtractRecord.extractPersonName1 = $("option:selected", "#extractPerson1").text();
            limsExtractRecord.extractPersonId2 = $("option:selected", "#extractPerson2").val();
            limsExtractRecord.extractPersonName2 = $("option:selected", "#extractPerson2").text();

            return limsExtractRecord;
        }

        function GetLimsPcrRecord() {
            var limsPcrRecord = {};
            limsPcrRecord.caseId = $("#caseId").val();
            limsPcrRecord.pcrPersonId1 = $("option:selected", "#pcrPerson1").val();
            limsPcrRecord.pcrPersonName1 = $("option:selected", "#pcrPerson1").text();
            limsPcrRecord.pcrPersonId2 = $("option:selected", "#pcrPerson2").val();
            limsPcrRecord.pcrPersonName2 = $("option:selected", "#pcrPerson2").text();
            limsPcrRecord.pcrStartDatetime = $("#pcrStartDatetime").val();
            limsPcrRecord.pcrEndDatetime = $("#pcrEndDatetime").val();
            limsPcrRecord.pcrProgram = $("option:selected", "#pcrProgram").val();
            limsPcrRecord.pcrInstrument = $("option:selected", "#pcrInstrument").val();
            limsPcrRecord.pcrSystem = $("option:selected", "#pcrSystem").val();
            limsPcrRecord.pcrReagent = $("option:selected", "#pcrReagent").val();

            return limsPcrRecord;
        }

        function GetLimsSyRecord() {
            var limsSyRecord = {};
            limsSyRecord.caseId = $("#caseId").val();
            limsSyRecord.boardNo = getBoardNoValue();
            limsSyRecord.operatePersonId1 = $("option:selected", "#syPerson1").val();
            limsSyRecord.operatePersonName1 = $("option:selected", "#syPerson1").text();
            limsSyRecord.operatePersonId2 = $("option:selected", "#syPerson2").val();
            limsSyRecord.operatePersonName2 = $("option:selected", "#syPerson2").text();
            limsSyRecord.startDatetime = $("#startDatetime").val();
            limsSyRecord.endDatetime = $("#endDatetime").val();
            limsSyRecord.chanwuFlag = $("option:selected", "#chanwu").val();
            limsSyRecord.jiaxiananFlag = $("option:selected", "#jiaxianan").val();
            limsSyRecord.neibiaoUlFlag = $("option:selected", "#neibiaoul").val();
            limsSyRecord.neibiaoFlag = $("option:selected", "#neibiao").val();
            limsSyRecord.elecInstrument = $("option:selected", "#elecInstrument").val();

            return limsSyRecord;
        }

        function getBoardNoValue() {
            var $boardNoTR = $("tr", "#quickExaminTable");
            var boardNoCnt = $boardNoTR.length;
            var boardNo = "";
            var newBoardNo = $("input[name='boardNo']", $boardNoTR.get(0)).val().trim();
            var oldBoardNo = "";

            for (var i = 0; i < boardNoCnt; i++) {
                oldBoardNo = $("input[name='boardNo']", $boardNoTR.get(i)).val().trim();
                if (oldBoardNo != newBoardNo) {
                    boardNo += newBoardNo + "," + oldBoardNo + ",";
                    newBoardNo = oldBoardNo;
                }
            }

            if (boardNo == "" || boardNo.length == 0)
                boardNo = newBoardNo;

            return boardNo;
        }

        function GetLimsQuickExamineRecordSample() {
            var LimsQuickExamineRecordSampleArr = new Array();
            var LimsQuickExamineRecordSample;

            $("#quickExaminTable").find("tr").not(".hide").each(function () {
                LimsQuickExamineRecordSample = {};
                var $TR = $(this);
                var checkBox = $("input[name='box']", $TR).is(":checked");
                var changeMethod = $("select[name='changeMethod']", $TR).find("option:selected").val();
                if (checkBox) {
                    LimsQuickExamineRecordSample.extractRecordSampleId = $("input[name='extractRecordSampleId']", $TR).val();
                    LimsQuickExamineRecordSample.pcrRecordSampleId = $("input[name='pcrRecordSampleId']", $TR).val();
                    LimsQuickExamineRecordSample.syRecordSampleId = $("input[name='syRecordSampleId']", $TR).val();
                    LimsQuickExamineRecordSample.extractRecordId = $("input[name='extractRecordId']", $TR).val();
                    LimsQuickExamineRecordSample.pcrRecordId = $("input[name='pcrRecordId']", $TR).val();
                    LimsQuickExamineRecordSample.syRecordId = $("input[name='syRecordId']", $TR).val();
                    LimsQuickExamineRecordSample.standardPcrFlag = $("input[name='standardPcrFlag']", $TR).val();
                    LimsQuickExamineRecordSample.standardSyFlag = $("input[name='standardSyFlag']", $TR).val();
                    LimsQuickExamineRecordSample.sampleId = $("input[name='sampleId']", $TR).val();
                    LimsQuickExamineRecordSample.sampleNo = $("input[name='sampleNo']", $TR).val();
                    LimsQuickExamineRecordSample.sampleType = $("select[name='sampleTypeName']", $TR).find("option:selected").val();
                    LimsQuickExamineRecordSample.extractHb = $("select[name='extractHb']", $TR).find("option:selected").val();
                    LimsQuickExamineRecordSample.extractPsa = $("select[name='extractPsa']", $TR).find("option:selected").val();
                    LimsQuickExamineRecordSample.extractPosition = $("input[name='extractPosition']", $TR).val();
                    LimsQuickExamineRecordSample.changeMethod = $("select[name='changeMethod']", $TR).find("option:selected").val();
                    if (changeMethod != "" && changeMethod == "其他") {
                        LimsQuickExamineRecordSample.otherChangeMethod = $("input[name='otherChangeMethod']", $TR).val();
                    } else {
                        LimsQuickExamineRecordSample.otherChangeMethod = "";
                    }
                    LimsQuickExamineRecordSample.extractMethod = $("select[name='extractMethod']", $TR).find("option:selected").text();
                    LimsQuickExamineRecordSample.syBoardNo = $("input[name='boardNo']", $TR).val();
                    LimsQuickExamineRecordSample.samplePosition = $("select[name='samplePosition']", $TR).find("option:selected").val();
                    LimsQuickExamineRecordSample.primerUl = $("input[name='primerUl']", $TR).val();
                    LimsQuickExamineRecordSample.bufferUl = $("input[name='bufferUl']", $TR).val();
                    LimsQuickExamineRecordSample.templateUl = $("input[name='templateUl']", $TR).val();
                    LimsQuickExamineRecordSample.ddwUl = $("input[name='ddwUl']", $TR).val();

                    LimsQuickExamineRecordSample.extractLiFlag = ($("select[name='extractLiFlag']", $TR).find("option:selected").val());
                    LimsQuickExamineRecordSample.extractZhenFlag = ($("select[name='extractZhenFlag']", $TR).find("option:selected").val());
                    LimsQuickExamineRecordSample.extractJiaoFlag = ($("select[name='extractJiaoFlag']", $TR).find("option:selected").val());
                    LimsQuickExamineRecordSample.extractYuFlag = ($("select[name='extractYuFlag']", $TR).find("option:selected").val());

                    LimsQuickExamineRecordSampleArr.push(LimsQuickExamineRecordSample);
                }
            });

            return LimsQuickExamineRecordSampleArr;
        }

        function SaveForm() {

            var operateType = $("#operateType").val();
            var limsExtractRecord = GetLimsExtractRecord();
            var limsPcrRecord = GetLimsPcrRecord();
            var limsSyRecord = GetLimsSyRecord();
            var limsQuickExamineRecordSampleList = GetLimsQuickExamineRecordSample();

            if (!checkInputValidation(limsExtractRecord, limsPcrRecord, limsSyRecord, limsQuickExamineRecordSampleList)) {
                return false;
            }

            var quickExamineRecordId = $("#quickExamineRecordId").val();

            var params = {
                "quickExamineRecordId": quickExamineRecordId,
                "limsExtractRecord": limsExtractRecord,
                "limsPcrRecord": limsPcrRecord,
                "limsSyRecord": limsSyRecord,
                "limsQuickExamineRecordSampleList": limsQuickExamineRecordSampleList
            };

            $.ajax({
                url: "<%=path%>/center/examine/saveQuickExamineRecord.html?operateType=" + operateType,
                type: "post",
                data: JSON.stringify(params),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (data) {
                    if (data.success == true || data.success == "true") {
                        $("#extractRecordId").val(data.extractRecordId);
                        $("#pcrRecordId").val(data.pcrRecordId);
                        $("#syRecordId").val(data.syRecordIds);
                        $("#quickExamineRecordModal").modal('show');
                    } else {
                        alert("保存失败！");
                    }
                },
                error: function (e) {
                    alert("保存失败！");
                }
            });
        }

        function checkInputValidation(limsExtractRecord, limsPcrRecord, limsSyRecord, limsQuickExamineRecordSampleList) {
            if (limsExtractRecord.extractDatetime == "") {
                alert("请选择提取时间！");
                $("#extractDatetime").addClass("text_error_border");
                return false;
            } else {
                $("#extractDatetime").removeClass("text_error_border");
            }

            var extractPerson2 = $("option:selected", "#extractPerson2").val();
            if (extractPerson2 == "") {
                alert("请选择提取人！");
                return false;
            }

            if (limsPcrRecord.pcrStartDatetime == "") {
                alert("请选择扩增开始时间！");
                $("#pcrStartDatetime").addClass("text_error_border");
                return false;
            } else {
                $("#pcrStartDatetime").removeClass("text_error_border");
            }

            if (limsPcrRecord.pcrEndDatetime == "") {
                alert("请选择扩增结束时间！");
                $("#pcrEndDatetime").addClass("text_error_border");
                return false;
            } else {
                $("#pcrEndDatetime").removeClass("text_error_border");
            }

            var pcrPerson2 = $("option:selected", "#pcrPerson2").val();
            if (pcrPerson2 == "") {
                alert("请选择扩增人！");
                return false;
            }

            var pcrStartDatetime = $("#pcrStartDatetime").val();
            var extractDatetime = $("#extractDatetime").val();

            var d1 = new Date(pcrStartDatetime.replace(/\-/g, "\/"));
            var d2 = new Date(extractDatetime.replace(/\-/g, "\/"));

            if (pcrStartDatetime != "" && extractDatetime != "" && d1 < d2) {
                alert("扩增时间小于提取时间，请重新选择时间!");
                return false;
            }

            var pcrStartDate = new Date(pcrStartDatetime).getTime();
            var extractDate = new Date(extractDatetime).getTime();
            var total = pcrStartDate - extractDate;

            var days = Math.floor(total / (24 * 3600 * 1000));

            var hour = total % (24 * 60 * 60 * 1000);
            var hours = Math.floor(hour / (3600 * 1000));

            if (psaExist()) {
                if (days < 1) {
                    alert("精斑检材提取时间不到24小时，请重新选择时间!");
                    return false;
                }
            } else {
                if (days < 1 && hours < 1) {
                    alert("样本检材提取时间不到1小时，请重新选择时间!");
                    return false;
                }
            }

            var syPerson2 = $("option:selected", "#syPerson2").val();
            if (syPerson2 == "") {
                alert("请选择上样人！");
                return;
            }

            var startDatetime = $("#startDatetime").val();
            var pcrDatetime = $("#pcrEndDatetime").val();

            var d1 = new Date(startDatetime.replace(/\-/g, "\/"));
            var d2 = new Date(pcrDatetime.replace(/\-/g, "\/"));

            if (startDatetime != "" && pcrDatetime != "" && d1 < d2) {
                alert("上样时间小于扩增时间，请重新选择时间!");
                return false;
            }

            if (limsSyRecord.startDatetime == "") {
                alert("请选择电泳开始时间！");
                $("#startDatetime").addClass("text_error_border");
                return;
            } else {
                $("#startDatetime").removeClass("text_error_border");
            }

            if (limsSyRecord.endDatetime == "") {
                alert("请选择电泳结束时间！");
                $("#endDatetime").addClass("text_error_border");
                return;
            } else {
                $("#endDatetime").removeClass("text_error_border");
            }

            var boardNo = getBoardNo();
            if (boardNo == "" || boardNo.length == 0) {
                alert("请输入上样板号！");
                return;
            }

            if (limsQuickExamineRecordSampleList.length == 0) {
                alert("请选择检材！");
                return false;
            }

            var samplePositionArr = new Array();
            var hasSamePosition = false;
            $("select[name='samplePosition']", "#quickExaminTable").each(function () {
                var pos = $("option:selected", $(this)).val();
                if ($.inArray(pos, samplePositionArr) != -1) {
                    hasSamePosition = true;
                } else {
                    samplePositionArr.push(pos);
                }
            });

            return true;
        }

        function psaExist() {
            var $sampleTypeNameTR = $("tr", "#quickExaminTable");
            var sampleTypeNameCnt = $sampleTypeNameTR.length;
            var sampleTypeName = "";
            for (var i = 0; i < sampleTypeNameCnt; i++) {
                sampleTypeName = $("input[name='sampleTypeName']", $sampleTypeNameTR.get(i)).val();
                if (sampleTypeName == "精斑") {
                    return true;
                    break;
                }
            }

            return false;
        }

        function getBoardNo() {
            var $boardNoTR = $("tr", "#quickExaminTable");
            var boardNoCnt = $boardNoTR.length;
            var boardNo = "";
            for (var i = 0; i < boardNoCnt; i++) {
                boardNo = $("input[name='boardNo']", $boardNoTR.get(i)).val();
                if (boardNo == "" || boardNo.length == 0)
                    break;
            }

            return boardNo;
        }

        function finishExtract() {
            $("#quickExamineRecordModal").modal('hide');
        }

        function AddSampleRows(sampleInfoList) {
            var hideRowHtml = $("tr.hide", "#quickExaminTable").html();
            var newRowHtml;
            var sample;
            var existsInRow = false;
            for (var i = 0; i < sampleInfoList.length; i++) {
                sample = sampleInfoList[i];

                $("input[name='sampleId']", "#quickExaminTable").each(function () {
                    var valInRow = $(this).val();
                    if (valInRow == sample.id) {
                        existsInRow = true;
                    }
                });

                if (existsInRow) continue;

                newRowHtml = "<tr>" + hideRowHtml + "</tr>";
                $("#quickExaminTable").append(newRowHtml);
                $("tr:eq(0)", "#quickExaminTable").find("input[name='sampleNo']").val(sample.sampleNo);
                $("tr:eq(0)", "#quickExaminTable").find("input[name='sampleId']").val(sample.id);
                $("tr:eq(0)", "#quickExaminTable").find("button[name='delExtractBtn']").on("click", function () {
                    $(this).parents("tr").remove();
                });
            }
        }

        $("button[name='delExtractBtn']", "#quickExaminTable").on("click", function () {
            $(this).parents("tr").remove();
            generateIdx();
        });

        $("#backToListBtn").on("click", function () {
            location.href = '<%=path%>/center/examine/quickExamine.html';
        });

        $("#saveBtn").on("click", function () {
            $('#saveBtn').attr("disabled", "disabled");
            SaveForm();
        });


        $("#OpenExtractDocBtn").on("click", function () {
            var extractRecordId = $("#extractRecordId").val();
            location.href = "<%=path%>/center/extract/extractDoc.html?extractRecordId=" + extractRecordId;
        });

        $("#OpenPcrDocBtn").on("click", function () {
            var pcrRecordId = $("#pcrRecordId").val();
            location.href = "<%=path%>/center/pcr/pcrDoc.html?pcrRecordId=" + pcrRecordId;
        });

        $("#OpenSyDocBtn").on("click", function () {
            var syRecordId = $("#syRecordId").val();
            location.href = "<%=path%>/center/sy/syDoc.html?syRecordId=" + syRecordId;
        });

        $("#FinishedBtn").on("click", function () {
            finishExtract();
        });


        $("#quickExamineRecordModal").on('hidden.bs.modal', function () {
            location.href = '<%=path%>/center/examine/quickExamine.html';
        });

        $('.form_datetime').datetimepicker({
            format: 'yyyy-mm-dd hh:ii',
            language: 'zh',
            weekStart: 1,
            todayBtn: 1,
            minView: "hour",
            autoclose: true,
            todayHighlight: true,
            forceParse: 0,
            showMeridian: 1
        }).on('changeDate', function (ev) {
            $(this).datetimepicker('hide');
        });
    });

    $("select[name='sampleTypeName']", "#quickExaminTable").change(function () {
        var sampleType = $(this).children('option:selected').val()
        if (sampleType == '01' || sampleType == '05' || sampleType == '03' || sampleType == '09' || sampleType == '06' || sampleType == '08') {
            var ExtractMethod = "A";
            $(this).parent().siblings().eq(8).children().find("option:selected").text(ExtractMethod);
        } else if (sampleType == '02') {
            var ExtractMethod = "E";
            $(this).parent().siblings().eq(8).children().find("option:selected").text(ExtractMethod);
        } else if (sampleType == '07') {
            var ExtractMethod = "B";
            $(this).parent().siblings().eq(8).children().find("option:selected").text(ExtractMethod);
        } else {
            var ExtractMethod = "D";
            $(this).parent().siblings().eq(8).children().find("option:selected").text(ExtractMethod);
        }
    });

</script>

<!-- END JS -->
</body>
</html>


