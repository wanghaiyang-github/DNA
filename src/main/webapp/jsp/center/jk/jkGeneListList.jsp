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
                            <span class="label label-primary">查询条件</span>
                            <span class="tools pull-right">
                            <a href="javascript:;" class="fa fa-chevron-down"></a>
                            </span>
                        </header>
                        <div class="panel-body">
                            <form action="<%=path%>/center/jk/queryJkGeneList.html" id="queryForm"
                                  class="form-horizontal tasi-form" method="post">
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">人员编号</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="entity.personNo"
                                               value="${query.entity.personNo}">
                                    </div>
                                    <label class="col-sm-1 control-label" style="text-align:center;">人员姓名</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="entity.personName"
                                               value="${query.entity.personName}">
                                    </div>

                                    <label class="col-sm-1 control-label" style="text-align:center;">身份证号</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="entity.idcardNo"
                                               value="${query.entity.idcardNo}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-1 control-label">采集单位</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" name="collectOrgId"
                                                value="${query.collectOrgId}">
                                            <option value="">全部</option>
                                            <c:forEach items="${delegateOrgList}" var="org" varStatus="lu">
                                                <option value="${org.id}"
                                                        <c:if test="${org.id eq query.collectOrgId}">selected</c:if>>${org.orgName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <label class="col-sm-1 control-label" style="text-align:center;">人员类型</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" name="entity.personType"
                                                value="${query.entity.personType}">
                                            <option value="">全部</option>
                                            <c:forEach items="${personTypeDictItemList}" var="personType" varStatus="lu">
                                                <option value="${personType.dictCode}"
                                                        <c:if test="${personType.dictCode eq query.entity.personType}">selected</c:if>>${personType.dictName}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-1" style="text-align:center;">
                                        <input type="hidden" name="page" id="page" value="${pageInfo.page}"/>
                                        <button id="queryBtn" class="btn btn-primary" type="button"><i
                                                class="fa fa-search"></i> 查 询
                                        </button>
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
                            <span class="label label-primary">人员列表</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>人员编号</th>
                                    <th>人员姓名</th>
                                    <th>身份证号</th>
                                    <th>人员类型</th>
                                    <th>采集单位</th>
                                    <th>采集时间</th>
                                    <th>位点数</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="acceptedListTbody">
                                <c:forEach items="${criminalPersonInfoVoList}" var="personInfo" varStatus="s">
                                    <tr>
                                        <td>${s.count}</td>
                                        <td>${personInfo.entity.personNo}</td>
                                        <td>${personInfo.entity.personName}</td>
                                        <td>${personInfo.entity.idcardNo}</td>
                                        <td>${personInfo.personTypeName}</td>

                                        <td>${personInfo.entity.collectOrgName}</td>
                                        <td>
                                            <fmt:formatDate value='${personInfo.entity.collectDatetime}'
                                                            pattern='yyyy-MM-dd'/>
                                        </td>
                                        <td>${personInfo.geneCount}</td>
                                        <td>
                                            <input type="hidden" name="personId" value="${personInfo.entity.id}"/>
                                            <input type="hidden" name="sampleGeneId" value="${personInfo.sampleGeneId}"/>
                                            <button name="viewBtn" class="btn btn-primary btn-sm"><i
                                                    class="fa fa-pencil"></i> 查看基因型
                                            </button>

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
<!-- BEGIN JS -->
<%@ include file="../../common/script.jsp" %>
<script>
    $(function () {
        'use strict';

        kkpager.generPageHtml({
            pno: ${pageInfo.page},
            //总页码
            total: ${pageInfo.pageCount},
            //总数据条数
            totalRecords: ${pageInfo.allRecordCount},
            //链接前部
            hrefFormer: '<%=path%>/center/jk/queryJkGeneList',
            //链接尾部
            hrefLatter: '.html',
            getLink: function (page) {
                return this.hrefFormer + this.hrefLatter + "?" + "page=" + page + "&" + $("#queryForm").serialize();
            }
            , lang: {
                firstPageText: '首页',
                firstPageTipText: '首页',
                lastPageText: '尾页',
                lastPageTipText: '尾页',
                prePageText: '上一页',
                prePageTipText: '上一页',
                nextPageText: '下一页',
                nextPageTipText: '下一页',
                totalPageBeforeText: '共',
                totalPageAfterText: '页',
                currPageBeforeText: '当前第',
                currPageAfterText: '页',
                totalInfoSplitStr: '/',
                totalRecordsBeforeText: '共',
                totalRecordsAfterText: '条数据',
                gopageBeforeText: '&nbsp;转到',
                gopageButtonOkText: '确定',
                gopageAfterText: '页',
                buttonTipBeforeText: '第',
                buttonTipAfterText: '页'
            }
        });

        $("button[name='viewBtn']", "#acceptedListTbody").on('click', function () {
            var sampleGeneId = $("input[name='sampleGeneId']", $(this).parent()).val();
            location.href = '<%=path%>/center/jk/editGene.html?id=' + sampleGeneId;
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

        $("#queryBtn").on("click", function () {
            $("#page").val(1);
            $('#queryForm').submit();
        });
    });
</script>
<!-- END JS -->
</body>
</html>


