<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <%@ include file="../../common/link.jsp" %>
</head>
<body>
<!-- BEGIN WRAPPER  -->
<!-- END ROW  -->

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
                <form id="queryForm" action="<%=path%>/center/qc/polluteRecord.html" class="form-horizontal tasi-form" method="post">
                    <div class="form-group">
                        <label class="col-sm-2 col-sm-2 control-label text-align-right">质控人员姓名</label>
                        <div class="col-sm-2">
                            <select class="form-control" name="entity.qcPersonId" id="caseSn" value="${query.entity.qcPersonId}">
                                <option value="">-- 全部 --</option>
                                <c:forEach items="${qcPersonGeneList}" var="qcPerson" varStatus="qp">
                                    <option value="${qcPerson.id}">${qcPerson.qcPersonName}</option>
                                </c:forEach>
                            </select>

                        </div>
                        <div class="col-sm-2">
                            <input type="hidden" name="casePushFlag" value="${caseInfoQuery.casePushFlag}"/>
                            <input type="hidden" name="page" id="page" value="${pageInfo.page}"/>
                            <button class="btn btn-primary" id="submitBtn"><i class="fa fa-search"></i> 查 询</button>
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
            <header class="panel-heading">
                <span class="label label-primary">污染记录列表</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
            </header>
            <div class="panel-body">
                <div class="space15" style="height: 8px;"></div>
                <table class="table table-striped table-advance table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>质控人员编号</th>
                        <th>质控人员姓名</th>
                        <th>污染样本编号</th>
                        <th>污染时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="qcPersonListTbody">
                    <c:forEach items="${polluteRecordList}" var="polluteRecord" varStatus="p">
                        <tr>
                            <td>${p.count}</td>
                            <td>${polluteRecord.qcPersonNo}</td>
                            <td>${polluteRecord.qcPersonName}</td>
                            <td>${polluteRecord.pollutedSampleNo}</td>
                            <td>
                                <fmt:formatDate value='${polluteRecord.createDatetime}' pattern='yyyy-MM-dd'/>
                            </td>
                            <td>
                                <input type="hidden" name="polluteRecordId" value="${polluteRecord.id}"/>
                                <button name="editBtn" class="btn btn-primary btn-xs"><i class="fa fa-pencil"></i> 查看</button>
                                <button name="delBtn" class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i> 删除</button>
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

<!-- END ROW  -->
<!-- BEGIN JS -->
<<%@ include file="../../common/script.jsp" %>
<script>
    $(function(){
        'use strict';

        $("#addBtn").on("click",function(){
            location.href='<%=path%>/center/qc/editQcPerson.html?operateType=1';
        });

        $("button[name='editBtn']","#qcPersonListTbody").on('click',function(){
            var qcPersonId = $("input[name='qcPersonId']", $(this).parent()).val();
            location.href='<%=path%>/center/qc/editQcPerson.html?qcPersonId='+qcPersonId +"&operateType=2";
        });



        $("button[name='delBtn']","#qcPersonListTbody").on("click",function(){
            if(confirm("确认要删除该质控人员信息吗？")){
                var qcPersonId=$("input[name='qcPersonId']", $(this).parent()).val();
                location.href='<%=path%>/center/qc/delQcPerson.html?qcPersonId='+qcPersonId;
            }
        });

        kkpager.generPageHtml({
            pno : ${pageInfo.page},
            //总页码
            total : ${pageInfo.pageCount},
            //总数据条数
            totalRecords : ${pageInfo.allRecordCount},
            //链接前部
            hrefFormer : '<%=path%>/center/qc/polluteRecord',
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
            $('#queryForm').submit();
        });
    });
</script>
<!-- END JS -->
</body>
</html>


