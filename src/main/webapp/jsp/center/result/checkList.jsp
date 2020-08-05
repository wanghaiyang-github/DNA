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
                                    <th>录入状态</th>
                                    <th width="150px">操作</th>
                                </tr>
                                </thead>
                                <tbody id="samplePersonTable">
                                <c:if test="${fn:length(otherSampleInfoList) gt 0}">
                                    <c:forEach items="${otherSampleInfoList}" var="acceptedSample" varStatus="as">
                                        <c:if test="${acceptedSample.sampleFlag eq 1}">
                                            <tr class="regedTr">
                                                <td><input type='hidden' name='sampleNo' value='${acceptedSample.sampleNo}'/>${acceptedSample.sampleNo}</td>
                                                <td title="${acceptedSample.sampleName}">
                                                    <input type='hidden' name='sampleName' value='${acceptedSample.sampleName}'/>
                                                    <c:if test="${fn:length(acceptedSample.sampleName) gt 8}">${fn:substring(acceptedSample.sampleName,0,7)} ...</c:if>
                                                    <c:if test="${fn:length(acceptedSample.sampleName) lt 9}">${acceptedSample.sampleName}</c:if>
                                                </td>
                                                <td><input type='hidden' name='sampleType' value='${acceptedSample.sampleType}'/>${acceptedSample.sampleTypeName}</td>
                                                <td title="${acceptedSample.sampleDesc}">
                                                    <input type='hidden' name='sampleDesc' value='${acceptedSample.sampleDesc}'/>
                                                    <c:if test="${fn:length(acceptedSample.sampleDesc) gt 8}">${fn:substring(acceptedSample.sampleDesc,0,7)} ...</c:if>
                                                    <c:if test="${fn:length(acceptedSample.sampleDesc) lt 9}">${acceptedSample.sampleDesc}</c:if>
                                                </td>
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
                                                <td title="${acceptedSample.refPersonName}">
                                                    <input type='hidden' name='refPersonId' value='${acceptedSample.refPersonId}'/>
                                                    <input type='hidden' name='refPersonName' value='${acceptedSample.refPersonName}'/>
                                                    <c:if test="${fn:length(acceptedSample.refPersonName) gt 5}">${fn:substring(acceptedSample.refPersonName,0,4)} ...</c:if>
                                                    <c:if test="${fn:length(acceptedSample.refPersonName) lt 6}">${acceptedSample.refPersonName}</c:if>
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
                                                    <c:if test="${acceptedSample.enterCount eq 0}">
                                                        未录入
                                                    </c:if>
                                                    <c:if test="${acceptedSample.enterCount gt 0}">
                                                        已录入 ${acceptedSample.enterCount} 次
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <input type="hidden" name="sampleId" value="${acceptedSample.id}"/>
                                                    <input type="hidden" name="sampleFlag" value="${acceptedSample.sampleFlag}"/>
                                                    <input type="hidden" name="sampleGeneId" value="${acceptedSample.sampleGeneId}"/>
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
                                                <td><input type='hidden' name='sampleNo' value='${sample.sampleNo}'/>${sample.sampleNo}</td>
                                                <td title="${sample.sampleName}">
                                                    <input type='hidden' name='sampleName' value='${sample.sampleName}'/>
                                                    <c:if test="${fn:length(sample.sampleName) gt 8}">${fn:substring(sample.sampleName,0,7)} ...</c:if>
                                                    <c:if test="${fn:length(sample.sampleName) lt 9}">${sample.sampleName}</c:if>
                                                </td>
                                                <td><input type='hidden' name='sampleType' value='${sample.sampleType}'/>${sample.sampleTypeName}</td>
                                                <td title="${sample.sampleDesc}">
                                                    <input type='hidden' name='sampleDesc' value='${sample.sampleDesc}'/>
                                                    <c:if test="${fn:length(sample.sampleDesc) gt 8}">${fn:substring(sample.sampleDesc,0,7)} ...</c:if>
                                                    <c:if test="${fn:length(sample.sampleDesc) lt 9}">${sample.sampleDesc}</c:if>
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
                                                <td title="${sample.refPersonName}">
                                                    <input type='hidden' name='refPersonId' value='${sample.refPersonId}'/>
                                                    <input type='hidden' name='refPersonName' value='${sample.refPersonName}'/>
                                                    <c:if test="${fn:length(sample.refPersonName) gt 5}">${fn:substring(sample.refPersonName,0,4)} ...</c:if>
                                                    <c:if test="${fn:length(sample.refPersonName) lt 6}">${sample.refPersonName}</c:if>
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
                                                    <c:if test="${sample.enterCount eq 0}">
                                                        未录入
                                                    </c:if>
                                                    <c:if test="${sample.enterCount gt 0}">
                                                        已录入 ${sample.enterCount} 次
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <input type="hidden" name="sampleId" value="${sample.id}"/>
                                                    <input type="hidden" name="sampleFlag" value="${sample.sampleFlag}"/>
                                                    <input type="hidden" name="sampleGeneId" value="${sample.sampleGeneId}"/>
                                                    <button class="btn btn-primary btn-sm" name="addBtn"><i class="fa fa-plus"></i> 添加</button>
                                                    <c:if test="${sample.enterCount gt 0}">
                                                        <button class="btn btn-success btn-sm" name="viewBtn"><i class="fa fa-list"></i> 查看</button>
                                                    </c:if>
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
                                    <th>提取时间</th>
                                    <th>提取人</th>
                                    <th width="200px">特殊选项  <i class="fa fa-star"></i></th>
                                    <th>录入状态</th>
                                    <th width="150px">操作</th>
                                </tr>
                                </thead>
                                <tbody id="sampleEvidenceTable">
                                <c:if test="${fn:length(otherSampleInfoList) gt 0}">
                                    <c:forEach items="${otherSampleInfoList}" var="acceptedSample" varStatus="as">
                                        <c:if test="${acceptedSample.sampleFlag eq 0}">
                                            <tr class="regedTr">
                                                <td><input type='hidden' name='sampleNo' value='${acceptedSample.sampleNo}'/>${acceptedSample.sampleNo}</td>
                                                <td title="${acceptedSample.sampleName}">
                                                    <input type='hidden' name='sampleName' value='${acceptedSample.sampleName}'/>
                                                    <c:if test="${fn:length(acceptedSample.sampleName) gt 8}">${fn:substring(acceptedSample.sampleName,0,7)} ...</c:if>
                                                    <c:if test="${fn:length(acceptedSample.sampleName) lt 9}">${acceptedSample.sampleName}</c:if>
                                                </td>
                                                <td><input type='hidden' name='sampleType' value='${acceptedSample.sampleType}'/>${acceptedSample.sampleTypeName}</td>
                                                <td title="${acceptedSample.sampleDesc}">
                                                    <input type='hidden' name='sampleDesc' value='${acceptedSample.sampleDesc}'/>
                                                    <c:if test="${fn:length(acceptedSample.sampleDesc) gt 8}">${fn:substring(acceptedSample.sampleDesc,0,7)} ...</c:if>
                                                    <c:if test="${fn:length(acceptedSample.sampleDesc) lt 9}">${acceptedSample.sampleDesc}</c:if>
                                                </td>
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
                                                    <c:if test="${acceptedSample.enterCount eq 0}">
                                                        未录入
                                                    </c:if>
                                                    <c:if test="${acceptedSample.enterCount gt 0}">
                                                        已录入 ${acceptedSample.enterCount} 次
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <input type="hidden" name="sampleId" value="${acceptedSample.id}"/>
                                                    <input type="hidden" name="sampleFlag" value="${acceptedSample.sampleFlag}"/>
                                                    <input type="hidden" name="sampleGeneId" value="${acceptedSample.sampleGeneId}"/>
                                                    -- --
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                                <c:if test="${fn:length(sampleInfoList) gt 0}">
                                    <c:forEach items="${sampleInfoList}" var="sample" varStatus="p">
                                        <c:if test="${sample.sampleFlag eq 0}">
                                            <tr>
                                                <td><input type='hidden' name='sampleNo' value='${sample.sampleNo}'/>${sample.sampleNo}</td>
                                                <td title="${sample.sampleName}">
                                                    <input type='hidden' name='sampleName' value='${sample.sampleName}'/>
                                                    <c:if test="${fn:length(sample.sampleName) gt 8}">${fn:substring(sample.sampleName,0,7)} ...</c:if>
                                                    <c:if test="${fn:length(sample.sampleName) lt 9}">${sample.sampleName}</c:if>
                                                </td>
                                                <td><input type='hidden' name='sampleType' value='${sample.sampleType}'/>${sample.sampleTypeName}</td>
                                                <td title="${sample.sampleDesc}">
                                                    <input type='hidden' name='sampleDesc' value='${sample.sampleDesc}'/>
                                                    <c:if test="${fn:length(sample.sampleDesc) gt 8}">${fn:substring(sample.sampleDesc,0,7)} ...</c:if>
                                                    <c:if test="${fn:length(sample.sampleDesc) lt 9}">${sample.sampleDesc}</c:if>
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
                                                    <c:if test="${sample.enterCount eq 0}">
                                                        未录入
                                                    </c:if>
                                                    <c:if test="${sample.enterCount gt 0}">
                                                        已录入 ${sample.enterCount} 次
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <input type="hidden" name="sampleId" value="${sample.id}"/>
                                                    <input type="hidden" name="sampleFlag" value="${sample.sampleFlag}"/>
                                                    <input type="hidden" name="sampleGeneId" value="${sample.sampleGeneId}"/>
                                                    <button class="btn btn-primary btn-sm" name="addBtn"><i class="fa fa-plus"></i> 添加</button>
                                                    <c:if test="${sample.enterCount gt 0}">
                                                        <button class="btn btn-success btn-sm" name="viewBtn"><i class="fa fa-list"></i> 查看</button>
                                                    </c:if>
                                                </td>
                                            </tr>
                                        </c:if>
                                    </c:forEach>
                                </c:if>
                                </tbody>
                            </table>

                            <div class="form-group pull-right">
                                <input type="hidden" id="page" name="page" value="${page}">
                                <button class="btn btn-lg btn-info" id="backBtn" type="button"><i class="fa fa-reply"></i> 返 回 </button>
                            </div>

                        </div>
                    </section>
                </div>
            </div>
            <!-- END ROW  -->

        </div>
    </section>
</section>

<%@ include file="../../common/script.jsp" %>
<script>

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

        $("button[name='addBtn']","#samplePersonTable").on("click",function(){
            location.href = "<%=path%>/center/3/inputGene.html?" + getParamsStr(this);
        });
        $("button[name='addBtn']","#sampleEvidenceTable").on("click",function(){
            location.href = "<%=path%>/center/3/inputGene.html?" + getParamsStr(this);
        });

        $("button[name='viewBtn']","#samplePersonTable").on("click",function(){
            var sampleGeneId = $("input[name='sampleGeneId']", $(this).parent()).val();
            location.href = "<%=path%>/center/3/editGene.html?id=" + sampleGeneId;
        });
        $("button[name='viewBtn']","#sampleEvidenceTable").on("click",function(){
            var sampleGeneId = $("input[name='sampleGeneId']", $(this).parent()).val();
            location.href = "<%=path%>/center/3/editGene.html?id=" + sampleGeneId;
        });

        function getParamsStr(obj){

            var $curTR = $(obj).parents("tr");
            var geneInfo = {};
            geneInfo.sampleId = $("input[name='sampleId']", $curTR).val();
            geneInfo.sampleNo = $("input[name='sampleNo']", $curTR).val();
            geneInfo.id = $("input[name='sampleGeneId']", $curTR).val();

            return "sampleId=" + geneInfo.sampleId +"&sampleNo=" + geneInfo.sampleNo + "&id=" + geneInfo.id;

        }


        $("#backBtn").on("click",function(){
            var page = $("#page").val();
            location.href = "<%=path%>/center/3/inputViewGene.html?page=" + page;
        });

    });
</script>
</body>
</html>
