<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/include.jsp" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <%@ include file="../../common/link.jsp" %>
    <style>
        #nextStep {
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
                            <span class="label label-primary">结果列表</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>检材编号</th>
                                    <th>检材名称</th>
                                    <th>检材类型</th>
                                    <th>查看基因型</th>
                                    <th>入库状态</th>
                                    <th><input type="checkbox" name="allChecked" id="allChecked"/>全选</th>
                                </tr>
                                </thead>
                                <tbody id="samplesTbody">
                                <c:forEach items="${checkSampleGeneList}" var="sampleGene" varStatus="s">
                                    <tr>
                                        <td>${s.count}</td>
                                        <td><input type="hidden" name="sampleNo" id="sampleNo"
                                                   value="${sampleGene.sampleNo}">${sampleGene.sampleNo}</td>
                                        <td title="${sampleGene.sampleName}">
                                            <c:if test="${fn:length(sampleGene.sampleName) gt 39}">
                                                ${fn:substring(sampleGene.sampleName,0,38)} ...
                                            </c:if>
                                            <c:if test="${fn:length(sampleGene.sampleName) lt 40}">
                                                ${sampleGene.sampleName}
                                            </c:if>
                                        </td>
                                        <td>${sampleGene.sampleTypeName}</td>
                                        <td><a href="<%=path%>/center/3/viewCheckGene.html?id=${sampleGene.id}">基因型</a>
                                        </td>
                                        <td>
                                            <input type="hidden" name="auditStatus" id="auditStatus"
                                                   value="${sampleGene.auditStatus}">
                                            <c:choose>
                                                <c:when test="${sampleGene.auditStatus eq 1}">
                                                    已审核
                                                </c:when>
                                                <c:otherwise>
                                                    未审核
                                                </c:otherwise>
                                            </c:choose>
                                        </td>
                                        <td>
                                            <input type="hidden" name="geneId" id="geneId" value="${sampleGene.id}">
                                            <input type="hidden" name="sampleId" id="sampleId"
                                                   value="${sampleGene.sampleId}">
                                            <input type="checkbox" name="box" value="${sampleGene.sampleNo}"
                                                   <c:if test="${sampleGene.auditStatus eq 1}">disabled</c:if>>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>

                            <div class="form-group pull-right">
                                <input type="hidden" name="caseId" id="caseId" value="${caseId}">

                                <button class="btn btn-primary" id="editBtn" type="button"><i class="fa fa-pencil"></i>确认审核
                                </button>
                                <button class="btn btn-primary" onclick="javascript:history.go(-1);" type="button"><i
                                        class="fa fa-reply"></i> 返 回
                                </button>
                                <button class="btn btn-primary" id="nextStep" type="button">下一步</button>
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

    $(function () {
        'use strict';

        $("#allChecked").on("click", function () {
            getAuditStatus();
        });

        $("input[name='box']", "#samplesTbody").on("click", function () {
            var $curTR = $(this).parents("tr");

            var selectSampleNo = $("input[name='box']", $curTR).val();//选中条码

            $("tr", "#samplesTbody").each(function () {
                var sampleNo = $("input[name='box']", $(this)).val();
                if (selectSampleNo == sampleNo) {
                    if ($("input[name='box']", $curTR).prop('checked')) {
                        $("input[name='box']", $(this)).prop('checked', false);
                        $("input[name='box']", $curTR).prop('checked', true);
                    }
                }
            });
        });

        function getAuditStatus() {
            var sampleNo;
            $("tr", "#samplesTbody").each(function () {
                var auditStatus = $("input[name='auditStatus']", $(this)).val();
                var newSampleNo = $("input[name='box']", $(this)).val();
                if (auditStatus == 1) {
                    $("input[name='box']", $(this)).prop('checked', false);
                } else {
                    if ($("#allChecked").prop('checked')) {
                        if (sampleNo == newSampleNo) {
                            $("input[name='box']", $(this)).prop('checked', false);
                        } else {
                            $("input[name='box']", $(this)).prop('checked', true);
                            sampleNo = newSampleNo;
                        }
                    } else {
                        $("input[name='box']", $(this)).prop('checked', false);
                    }
                }
            });
        }

        $("#editBtn").on("click", function () {

            var checkCount = 0;
            var sampleGeneArr = new Array();
            var sampleGene;
            $("tr", "#samplesTbody").each(function () {

                sampleGene = {};
                var checkBox = $("input[name='box']", $(this)).is(":checked");

                if (checkBox) {
                    checkCount++;

                    sampleGene.id = $("input[name='geneId']", $(this)).val();
                    sampleGene.sampleId = $("input[name='sampleId']", $(this)).val();

                    sampleGeneArr.push(sampleGene);
                }
            });

            if (checkCount <= 0) {
                alert("请选择要审核的检材!");
                return false;
            }

            var params = {
                "sampleGeneList": sampleGeneArr
            };

            $.ajax({
                url: "<%=path%>/center/3/updateAuditStaus.html",
                type: "post",
                data: JSON.stringify(params),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (data) {
                    if (data.success || data.success == true || data.success == "true") {
                        var caseId = $("input[name='caseId']").val();
                        location.href = '<%=path%>/center/3/checkSampleGene.html?caseId=' + caseId;
                    } else {
                        alert("审核失败!");
                    }
                },
                error: function (data) {
                    alert("审核失败!");
                }
            });

        });

        $("#nextStep").on("click", function () {

            var checkCount = 0;
            $("tr", "#samplesTbody").each(function () {

                var auditStatus = $("input[name='auditStatus']", $(this)).val();

                if (auditStatus == 1) {
                    checkCount++;
                    return false;
                }
            });

            if (checkCount <= 0) {
                alert("请先审核检材!");
                return false;
            }

            var caseId = $("input[name='caseId']", $(this).parent()).val();
            var deviceType = $(parent.window.document.getElementsByClassName("layui-this"));
            deviceType.removeClass("layui-this").next().addClass("layui-this");
            location.href = "<%=path%>/center/3/caseCompare.html?caseId=" + caseId;

        });
    });
</script>
<!-- END JS -->
</body>
</html>
