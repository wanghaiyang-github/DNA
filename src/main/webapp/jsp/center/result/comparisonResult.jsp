<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/include.jsp" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <%@ include file="../../common/link.jsp" %>
    <style>
        .panel-body .table > tbody > tr > td,
        .panel-body .table > tbody > tr > th,
        .panel-body .table > tfoot > tr > td,
        .panel-body .table > tfoot > tr > th,
        .panel-body .table > thead > tr > td,
        .panel-body .table > thead > tr > th {
            border: none
        }

        .btn.focus,
        .btn:focus,
        .btn:hover {
            color: #fff;
            text-decoration: none
        }

        .btn-yellow {
            color: #fff;
            background: #ffaa31;
        }

        .btn-red {
            color: #fff;
            background: #ff5b55;
        }

        .btn-blue {
            color: #fff;
            background: #208be8;
        }

        .closeBtn {
            position: absolute;
            top: -40px;
            right: 100px;
            color: #fff;
            background: transparent;
            border: none;
        }

        .closeBtn span {
            display: inline-block;
            width: 30px;
            height: 30px;
            background: #e7e7e7;
            font-size: 30px;
            line-height: 30px;
            border-radius: 50%;
            color: #fff;
            opacity: 1;
        }

        #myModal thead {
            background: #208be8;
            color: #fff;
            text-align: center
        }

        #myModal td,
        #myModal th {
            text-align: center
        }

        #myModal .table {
            border: none
        }

        #myModal .table > thead > tr > td,
        #myModal .table > thead > tr > th {
            border: none
        }

        .tablecenter {
            width: 15%;
            line-height: 56px;
        }

        /*.row {*/
            /*margin: 0px;*/
        /*}*/

        /*.panel-body{*/
        /*padding: 0px;*/
        /*}*/

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
                            <span class="label label-primary">查询条件</span>
                                <span class="tools pull-right">
                                    <a href="javascript:;" class="fa fa-chevron-down"></a>
                                </span>
                        </header>
                        <div class="panel-body">
                            <form id="queryForm" action="<%=path%>/center/3/comparisonResult.html"
                                  class="form-horizontal tasi-form" method="post">
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件名称</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="sourceCaseName"
                                               value="${matchResult.sourceCaseName}">
                                    </div>
                                    <label class="col-sm-2 col-sm-2 control-label"
                                           style="text-align:center;">检材名称</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="sample1Name"
                                               value="${matchResult.sample1Name}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label text-align-right">入库样本类型</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" id="sampleEntryType" name="sampleEntryType"
                                                value="${matchResult.sampleEntryType}">
                                            <option value="">全部</option>
                                            <c:forEach items="${dictSampleEntryTypeList}" var="sampleEntryType"
                                                       varStatus="cs">
                                                <option value="${sampleEntryType.dictCode}"
                                                        <c:if test="${sampleEntryType.dictCode eq matchResult.sampleEntryType}">selected</c:if>>${sampleEntryType.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 col-sm-2 control-label"
                                           style="text-align:center;">检材条码</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="sample1No"
                                               value="${matchResult.sample1No}">
                                    </div>
                                    <div class="col-sm-2">
                                        <input type="hidden" name="page" id="page" value="${pageInfo.page}"/>
                                        <button class="btn btn-primary" type="button" id="queryBtn">
                                            <i class="fa fa-search"></i> 查 询
                                        </button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </section>
                </div>
            </div>
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading" style="border: none;">
                            <span class="label label-primary">样本列表</span>
                        </header>
                        <div class="panel-body">
                            <div class="row" style="margin: 0px">
                                <c:forEach items="${matchResultList}" var="matchResult" varStatus="s">
                                    <div class="col-md-12 TableBox">
                                        <table class="table table-hover table-striped">
                                            <thead>
                                            <tr>
                                                <th style="width: 5%;">序号</th>
                                                <th style="width: 15%;">案件名称</th>
                                                <th style="width: 20%;">检材条码</th>
                                                <th style="width: 20%;">检材名称</th>
                                                <th style="width: 15%;">入库样本类型</th>
                                                <th style="width: 15%;">所属分局</th>
                                                <th style="width: 10%">操作</th>
                                            </tr>
                                            </thead>
                                            <tbody nane="compareListTbody">
                                            <c:forEach items="${matchResult.value}" var="result" varStatus="c">
                                                <tr>
                                                    <td>${c.index+1}</td>
                                                    <td>${result.sourceCaseName}</td>
                                                    <td style="color: red">${result.sample1No}</td>
                                                    <td>${result.sample1Name}</td>
                                                    <td>${result.sampleEntryType}</td>
                                                    <td>${result.delegateOrgDesc}</td>
                                                    <td>
                                                        <input type="hidden" name="groupId" value="${result.groupId}">
                                                        <button class="btn btn-sm btn-primary btn-blue" type="button"
                                                                name="viewDetails">查看详情
                                                        </button>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                            </tbody>
                                        </table>
                                    </div>
                                </c:forEach>
                            </div>
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
<!-- BEGIN JS -->
<%@ include file="../../common/script.jsp" %>
<script>
    $(function () {
        'use strict';
        $(".TableBox").each(function(i,e){
            if((i+1)%3==1){
                $(this).css({"border-top":" 2px solid #ffcecc"})
            }else if((i+1)%3==2){
                $(this).css({"border-top":" 2px solid #bdddf8"})
            }else if((i+1)%3==0){
                $(this).css({"border-top":" 2px solid #ffe5c4"})
            }
        })

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

        $("#queryBtn").on("click", function () {
            $("#page").val(1);
            $('#queryForm').submit();
        });

        $("button[name='viewDetails']").on('click', function () {
            var groupId = $("input[name='groupId']", $(this).parent()).val();
            location.href = '<%=path%>/center/3/resultDetails.html?groupId=' + groupId;
        });

        kkpager.generPageHtml({
            pno : ${pageInfo.page},
            //总页码
            total : ${pageInfo.pageCount},
            //总数据条数
            totalRecords : ${pageInfo.allRecordCount},
            //链接前部
            hrefFormer : '<%=path%>/center/3/comparisonResult',
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
    });
</script>
<!-- END JS -->
</body>
</html>
