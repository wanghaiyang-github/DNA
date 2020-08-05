<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/include.jsp" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <!-- BEGIN STYLESHEET-->
    <%@ include file="../../common/link.jsp" %>
    <link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="<%=path%>/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="<%=path%>/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON CSS -->
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
<!-- BEGIN WRAPPER  -->
<!-- END ROW  -->
<!-- BEGIN ROW  -->
<section id="container">
    <!-- BEGIN MAIN CONTENT -->
    <section id="main-content">
        <section class="wrapper site-min-height">
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            <span class="label label-primary">查询条件</span>
                            <span class="tools pull-right">
                                <a href="javascript:;" class="fa fa-chevron-down"></a>
                            </span>
                        </header>
                        <div class="panel-body">
                            <form id="reagentSuppliesInfoForm" action="<%=path%>/center/storage/listReagentSuppliesInfo.html" class="form-horizontal tasi-form" method="post">
                                <input type="hidden" name="operateType" id="operateType" value="${operateType}"/>

                                <div class="form-group">
                                    <label class="col-sm-1 control-label" style="text-align:center;">编号:</label>
                                    <div class="col-sm-3">
                                        <input class="form-control" id="reagentNo" name="reagentNo" type="text" value="${reagentSuppliesInfo.reagentNo}">
                                    </div>
                                    <label class="col-sm-1 control-label" style="text-align:center;">名称:</label>
                                    <div class="col-sm-3">
                                        <input class="form-control" id="reagentName" name="reagentName" type="text" value="${reagentSuppliesInfo.reagentName}">
                                    </div>

                                    <div class="col-sm-3">
                                        <label class="checkbox-inline">
                                            <input id="includeFlag" name="reagentType" type="radio" value="1" <c:if test="${reagentSuppliesInfo.reagentType eq '1'}">checked</c:if>/>试剂
                                        </label>
                                        <label class="checkbox-inline">
                                            <input id="exclusiveFlag" name="reagentType" type="radio" value="2" <c:if test="${reagentSuppliesInfo.reagentType eq '2'}">checked</c:if> />耗材
                                        </label>
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-1 control-label" style="text-align:center;">型号:</label>
                                    <div class="col-sm-3">
                                        <input class="form-control" id="reagentModel" name="reagentModel" type="text" value="${reagentSuppliesInfo.reagentModel}">
                                    </div>
                                    <label class="col-sm-1 control-label" style="text-align:center;">实验阶段:</label>
                                    <div class="col-sm-3">
                                        <select class="form-control required" id="experimentalStage" name="experimentalStage">
                                            <option value="">全部</option>
                                            <c:forEach items="${experimentalStageList}" var="experimentalStage" varStatus="do">
                                                <option value="${experimentalStage.dictCode}" <c:if test="${experimentalStage.dictCode eq reagentSuppliesInfo.experimentalStage}">selected</c:if>>${experimentalStage.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>

                                    <div class="col-sm-2">
                                        <input type="hidden" name="page" id="page" value="${pageInfo.page}"/>
                                        <a class="btn btn-primary" href="javascript:;" id="queryBtn"><i class="fa fa-search"></i> 查询</a>
                                    </div>
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
                            <span class="label label-primary">试剂耗材列表</span>
                        </header>
                        <div class="panel-body">
                            <div class="clearfix">
                                <div class="btn-group pull-right">
                                    <button id="addBtn" class="btn btn-success green">
                                        添加 <i class="fa fa-plus"></i>
                                    </button>
                                </div>
                            </div>
                            <div class="space15" style="height: 8px;"></div>
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>编号</th>
                                    <th>类型</th>
                                    <th>名称</th>
                                    <th>实验阶段</th>
                                    <th>品牌</th>
                                    <th>型号</th>
                                    <th>价格</th>
                                    <th>厂商</th>
                                    <%--<th>有效日期</th>--%>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="reagentSuppliesTbody">
                                <c:forEach items="${reagentSuppliesInfoList}" var="reagentSuppliesInfo" varStatus="g">
                                    <tr>
                                        <td>${g.count}</td>
                                        <td>${reagentSuppliesInfo.reagentNo}</td>
                                        <td>
                                            <c:if test="${reagentSuppliesInfo.reagentType eq '1'}">试剂</c:if>
                                            <c:if test="${reagentSuppliesInfo.reagentType eq '2'}">耗材</c:if>
                                        </td>
                                        <td>${reagentSuppliesInfo.reagentName}</td>
                                        <td>${reagentSuppliesInfo.experimentalStageName}</td>
                                        <td>${reagentSuppliesInfo.reagentBrand}</td>
                                        <td>${reagentSuppliesInfo.reagentModel}</td>
                                        <td>${reagentSuppliesInfo.reagentPrice}</td>
                                        <td>${reagentSuppliesInfo.reagentFirm}</td>
                                        <%--<td><fmt:formatDate value="${reagentSuppliesInfo.effectiveDatetime}" pattern="yyyy-MM-dd hh:mm"/></td>--%>
                                        <td>
                                            <input type="hidden" id="reagentSuppliesInfoId" name="reagentSuppliesInfoId" value="${reagentSuppliesInfo.id}">
                                            <button name="modifyBtn" class="btn btn-primary btn-xs"><i class="fa fa-pencil"></i>修改</button>
                                            <%--<button name="deleteBtn" class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i>删除</button>--%>
                                        </td>
                                    </tr>
                                </c:forEach>
                                </tbody>
                            </table>
                        </div>
                    </section>
                </div>

                <div class="row">
                    <div class="col-lg-12">
                        <section class="panel">
                            <div class="panel-body">
                                <div class="pagin"><br/>
                                    <div id="kkpager" style="margin-right:30px;margin-top:-22px"></div>
                                </div>
                            </div>
                        </section>
                    </div>
                </div>
            </div>
        </section>
    </section>
    <!-- END MAIN CONTENT -->
</section>
<!-- END ROW  -->
<!-- BEGIN JS -->
<%@ include file="../../common/script.jsp" %>
<script>
    $(function(){
        'use strict';

        kkpager.generPageHtml({
            pno : ${pageInfo.page},
            //总页码
            total : ${pageInfo.pageCount},
            //总数据条数
            totalRecords : ${pageInfo.allRecordCount},
            //链接前部
            hrefFormer : '<%=path%>/center/storage/listReagentSuppliesInfo',
            //链接尾部
            hrefLatter : '.html',
            getLink : function(page){
                return this.hrefFormer + this.hrefLatter + "?" + "page=" + page + "&" + $("#reagentSuppliesInfoForm").serialize();
            }
            ,lang				: {
                firstPageText			: '首页',
                firstPageTipText		: '首页',
                lastPageText			: '尾页',
                lastPageTipText			: '尾页',
                prePageText				: '上一页',
                prePageTipText			: '上一页',
                nextPageText			: '下一页',
                nextPageTipText			: '下一页',
                totalPageBeforeText		: '共',
                totalPageAfterText		: '页',
                currPageBeforeText		: '当前第',
                currPageAfterText		: '页',
                totalInfoSplitStr		: '/',
                totalRecordsBeforeText	: '共',
                totalRecordsAfterText	: '条数据',
                gopageBeforeText		: '&nbsp;转到',
                gopageButtonOkText		: '确定',
                gopageAfterText			: '页',
                buttonTipBeforeText		: '第',
                buttonTipAfterText		: '页'
            }
        });

        $("#addBtn").on("click", function(){
            location.href = "<%=path%>/center/storage/editReagentSupplies.html?operateType=1";
        });

        $("button[name='modifyBtn']","#reagentSuppliesTbody").on('click',function(){
            var reagentSuppliesInfoId = $("#reagentSuppliesInfoId",$(this).parent()).val();
            location.href='<%=path%>/center/storage/editReagentSupplies.html?id='+reagentSuppliesInfoId+"&operateType=2";
        });

        $("#queryBtn").on("click", function(){
            $("#page").val(1);
            $('#reagentSuppliesInfoForm').submit();
        });

 /*       $("button[name='deleteBtn']","#reagentSuppliesTbody").on("click",function(){
            var reagentSuppliesInfoId = $("#reagentSuppliesInfoId",$(this).parent()).val();

            if (!confirm("确认删除吗")) {
                return;
            }

            $.ajax({
                url: "<%=path%>/center/storage/delReagentSupplies.html?reagentSuppliesInfoId=" + reagentSuppliesInfoId,
                type: "post",
                dataType: "json",
                success: function (data) {
                    if (data.success || data.success == true || data.success == "true") {
                        location.href = "<%=path%>/center/storage/listReagentSuppliesInfo.html";
                    } else {
                        alert("删除失败!");
                    }
                },
                error: function (data) {
                    alert("删除失败!");
                }
            });
        })*/
    });
</script>
<!-- END JS -->
</body>
</html>