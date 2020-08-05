<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/include.jsp" %>
<%
    String path = request.getContextPath();
%>
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
<section id="main-content">
    <section class="wrapper">
        <div id="wrapper-content">
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
                            <form id="queryForm" action="<%=path%>/center/storage/inStorageList.html" class="form-horizontal tasi-form" method="post">
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" style="text-align:center;">条码号</label>
                                    <div class="col-sm-3">
                                        <select id="barcode" name="barcode" class="form-control">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${recordInfoList}" var="list" varStatus="g">
                                                <option value="${list.barcode}" <c:if test="${storageRecordInfo.barcode eq list.barcode}">selected</c:if>>${list.barcode}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align:center;">入库名称</label>
                                    <div class="col-sm-3">
                                        <select id="reagentSuppliesInfoId" name="reagentSuppliesInfoId" class="form-control">
                                            <option value="">请选择...</option>
                                            <c:forEach items="${recordInfoList}" var="list" varStatus="g">
                                                <option value="${list.reagentSuppliesInfoId}" <c:if test="${storageRecordInfo.reagentSuppliesInfoId eq list.reagentSuppliesInfoId}">selected</c:if>>${list.reagentName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" style="text-align:center;">入库时间</label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" name="storageDatetimeStart" readonly="readonly" type="text" value="<fmt:formatDate value='${storageRecordInfo.storageDatetimeStart}' pattern='yyyy-MM-dd hh:mm'/>">
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align:center;">至 </label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" name="storageDatetimeEnd" readonly="readonly" type="text" value="<fmt:formatDate value='${storageRecordInfo.storageDatetimeEnd}' pattern='yyyy-MM-dd hh:mm'/>">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label" style="text-align:center;">入库人</label>
                                    <div class="col-sm-3">
                                        <input class="form-control" name="storagePerson" id="storagePerson" type="text" value="${storageRecordInfo.storagePerson}">
                                    </div>
                                    <div class="col-sm-2" style="text-align:center;">
                                        <input type="hidden" name="page" id="page" value="${pageInfo.page}"/>
                                        <button class="btn btn-primary" type="submit" id="submitBtn"><i class="fa fa-search"></i> 查询</button>
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
                            <span class="label label-primary">入库列表</span>
                            <span class="tools pull-right">
                                <a href="javascript:;" class="fa fa-chevron-down"></a>
                            </span>
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
                                    <th>条码号</th>
                                    <th>入库名称</th>
                                    <th>入库数量</th>
                                    <th>入库人</th>
                                    <th>入库时间</th>
                                    <th>有效时间</th>
                                    <th>备注</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="storageRecordInfoListTbody">
                                <c:forEach items="${storageRecordInfoList}" var="storageRecordInfo" varStatus="s">
                                    <tr>
                                        <td>${s.count}</td>
                                        <td>${storageRecordInfo.barcode}</td>
                                        <td>${storageRecordInfo.reagentName}</td>
                                        <td>${storageRecordInfo.storageNum}</td>
                                        <td>${storageRecordInfo.storagePerson}</td>
                                        <td><fmt:formatDate value='${storageRecordInfo.storageDatetime}' pattern='yyyy-MM-dd hh:mm'/></td>
                                        <td><fmt:formatDate value='${storageRecordInfo.effectiveDatetime}' pattern='yyyy-MM-dd hh:mm'/></td>
                                        <td>${storageRecordInfo.storageRemark}</td>
                                        <td>
                                            <input type="hidden" id="barcodeTbody" name="barcodeTbody" value="${storageRecordInfo.barcode}">
                                            <input type="hidden" id="storageRecordInfoId" name="storageRecordInfoId" value="${storageRecordInfo.id}">
                                            <button name="modifyBtn" class="btn btn-primary btn-xs"><i class="fa fa-pencil"></i>修改</button>
                                        </td>
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
<!-- END ROW  -->
<!-- BEGIN JS -->
<%@ include file="../../common/script.jsp" %>
<script>
    $(function(){
        'use strict';

        $("#addBtn").on("click", function(){
            location.href = "<%=path%>/center/storage/inStorage.html?operateType=1";
        });

        $("button[name='modifyBtn']", "#storageRecordInfoListTbody").on("click", function(){
            var id = $("#storageRecordInfoId", $(this).parent()).val();

            location.href = "<%=path%>/center/storage/inStorage.html?id=" + id + "&operateType=2";
        });

        $('.form_datetime').datetimepicker({
            format: 'yyyy-mm-dd hh:mm',
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

        kkpager.generPageHtml({
            pno : ${pageInfo.page},
            //总页码
            total : ${pageInfo.pageCount},
            //总数据条数
            totalRecords : ${pageInfo.allRecordCount},
            //链接前部
            hrefFormer : '<%=path%>/center/storage/inStorageList',
            //链接尾部
            hrefLatter : '.html',
            getLink : function(page){
                return this.hrefFormer + this.hrefLatter + "?" + "page=" + page + "&" + $("#queryForm").serialize();
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

        $("#submitBtn").on("click", function(){
            $("#page").val(1);
            $("#recordInfoForm").submit();
        });
    });
</script>
<!-- END JS -->
</body>
</html>