<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/include.jsp" %>
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
                <span class="label label-primary">基因频率信息</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
            </header>
            <div class="panel-body">
                <form id="queryForm" action="<%=path%>/center/7/08.html" class="form-horizontal tasi-form" method="get">
                    <div class="form-group">
                        <label class="col-sm-2 control-label" style="text-align:center;">种族名称</label>
                        <div class="col-sm-3">
                            <select class="form-control" name="raceName" id="raceInfoSelect" value="${alleleFrequency.raceName}">
                                <option value="">全部</option>
                                <c:forEach items="${raceInfoList}" var="raceInfo" varStatus="do">
                                    <option value="${raceInfo.raceName}" <c:if test="${raceInfo.raceName eq alleleFrequency.raceName}">selected</c:if>>${raceInfo.raceName}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <label class="col-sm-2 control-label" style="text-align:center;">基因座名称</label>
                        <div class="col-sm-3">
                            <select class="form-control" name="markerName" id="markerInfoSelect" value="${alleleFrequency.markerName}">
                                <option value="">全部</option>
                                <c:forEach items="${markerInfoList}" var="markerInfo" varStatus="do">
                                    <option value="${markerInfo.markerName}" <c:if test="${markerInfo.markerName eq alleleFrequency.markerName}">selected</c:if>>${markerInfo.markerName}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-2 control-label" style="text-align:center;">基因</label>
                        <div class="col-sm-3">
                            <input class="form-control" id="alleleName" name="alleleName" type="text">
                        </div>
                        <label class="col-sm-2 control-label" style="text-align:center;">频率</label>
                        <div class="col-sm-3">
                            <input class="form-control" id="alleleFrequency" name="frequency" type="text">
                        </div>
                        <div class="col-sm-2">
                            <input type="hidden" id="raceId" value="${alleleFrequency.raceId}">
                            <input type="hidden" name="page" id="page" value="${pageInfo.page}"/>
                            <button class="btn btn-primary" type="button" id="submitBtn">查询</button>
                            <button class="btn btn-primary" type="button" id="addBtn">添加</button>
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
                <span class="label label-primary">基因频率列表</span>
                            <span class="tools pull-right">
                                <a href="javascript:;" class="fa fa-chevron-down"></a>
                            </span>
            </header>
            <div class="panel-body">
                <table class="table table-striped table-advance table-bordered table-hover">
                    <thead>
                    <tr>
                        <th>序号</th>
                        <th>种族名称</th>
                        <th>基因座名称</th>
                        <th>基因</th>
                        <th>频率</th>
                        <th>提交者</th>
                        <th>提交时间</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody id="alleleFrequencyTbody">
                    <c:forEach  items="${alleleFrequencyList}" var="alleleFrequency" varStatus="allele">
                        <tr>
                            <td>${allele.count}</td>
                            <td><input type="hidden" name="alleleRaceName" value="${alleleFrequency.raceName}">${alleleFrequency.raceName}</td>
                            <td><input type="hidden" name="alleleMarkerName" value="${alleleFrequency.markerName}">${alleleFrequency.markerName}</td>
                            <td><input type="hidden" name="alleleFreName" value="${alleleFrequency.alleleName}">${alleleFrequency.alleleName}</td>
                            <td><input type="hidden" name="alleleFrequency" value="${alleleFrequency.alleleFrequency}">${alleleFrequency.frequency}</td>
                            <td><input type="hidden" name="alleleCreatePerson" value="${alleleFrequency.createPerson}">${alleleFrequency.createPerson}</td>
                            <td><fmt:formatDate value="${alleleFrequency.createDatetime}" pattern="yyyy-MM-dd hh:mm"/></td>
                            <td>
                                <input type="hidden" name="alleleId" value="${alleleFrequency.id}">
                                <input type="hidden" name="raceId" value="${alleleFrequency.raceId}">
                                <button name="modifyBtn" class="btn btn-primary btn-xs"><i class="fa fa-pencil"></i>修改</button>
                                <button name="deleteBtn" onclick='deleteBtn(${alleleFrequency.id})' class="btn btn-danger btn-xs"><i class="fa fa-trash-o"></i>删除</button>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>

                <div class="modal fade" id="alleleFrequencyModal" aria-hidden="true">
                    <div class="modal-dialog">
                        <div class="modal-content">
                            <div class="modal-header">
                                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                    &times;
                                </button>
                                <h4 class="modal-title">
                                    基因频率信息
                                </h4>
                            </div>
                            <div class="modal-body">
                                <form class="form-horizontal tasi-form">
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label" style="text-align:center;">种族名称</label>
                                        <div class="col-sm-3">
                                            <select class="form-control" name="raceInfoName">
                                                <option value="">全部</option>
                                                <c:forEach items="${raceInfoList}" var="raceInfo" varStatus="do">
                                                    <option value="${raceInfo.raceName}">${raceInfo.raceName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <label class="col-sm-2 control-label" style="text-align:center;">基因座名称</label>
                                        <div class="col-sm-3">
                                            <select class="form-control" name="markerInfoName">
                                                <option value="">全部</option>
                                                <c:forEach items="${markerInfoList}" var="markerInfo" varStatus="do">
                                                    <option value="${markerInfo.markerName}">${markerInfo.markerName}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </div>
                                    <div class="form-group">
                                        <label class="col-sm-2 control-label" style="text-align:center;">基因</label>
                                        <div class="col-sm-3">
                                            <input class="form-control" name="alleleNam" type="text">
                                        </div>
                                        <label class="col-sm-2 control-label" style="text-align:center;">频率</label>
                                        <div class="col-sm-3">
                                            <input class="form-control" name="alleleFre" type="text">
                                        </div>
                                    </div>
                                </form>
                            </div>
                            <div class="modal-footer">
                                <input type="hidden" name="createPerson" id="createPerson">
                                <input type="hidden" name="raceInfoId" id="raceInfoId"/>
                                <input type="hidden" name="id" id="id"/>
                                <button class="btn btn-success" type="button" id="saveBtn">确定</button>
                                <button data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
                            </div>
                        </div>
                    </div>
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
            hrefFormer : '<%=path%>/center/7/08',
            //链接尾部
            hrefLatter : '.html',
            getLink : function(page){
                $("#page").val(page);
                return this.hrefFormer + this.hrefLatter + "?" + $("#queryForm").serialize();
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

        $("button[name='modifyBtn']","#alleleFrequencyTbody").on("click",function(){
            EditRaceInfoRow(this);
        });

        function EditRaceInfoRow(obj){
            var $curTR = $(obj).parents("tr");
            var alleleFrequency = {};
            alleleFrequency.raceName = $("input[name='alleleRaceName']", $curTR).val();
            alleleFrequency.markerName = $("input[name='alleleMarkerName']", $curTR).val();
            alleleFrequency.alleleName = $("input[name='alleleFreName']", $curTR).val();
            alleleFrequency.alleleFrequency = $("input[name='alleleFrequency']", $curTR).val();
            alleleFrequency.createPerson = $("input[name='alleleCreatePerson']", $curTR).val();
            alleleFrequency.raceId = $("input[name='raceId']", $curTR).val();
            alleleFrequency.id = $("input[name='alleleId']", $curTR).val();

            newRaceInfoRow(alleleFrequency);
        }

        function newRaceInfoRow(alleleFrequency) {
            $("select[name='raceInfoName']", "#alleleFrequencyModal").val(alleleFrequency.raceName);
            $("select[name='markerInfoName']", "#alleleFrequencyModal").val(alleleFrequency.markerName);
            $("input[name='alleleNam']", "#alleleFrequencyModal").val(alleleFrequency.alleleName);
            $("input[name='alleleFre']", "#alleleFrequencyModal").val(alleleFrequency.alleleFrequency);
            $("input[name='createPerson']", "#alleleFrequencyModal").val(alleleFrequency.createPerson);
            $("input[name='raceInfoId']", "#alleleFrequencyModal").val(alleleFrequency.raceId);
            $("input[name='id']", "#alleleFrequencyModal").val(alleleFrequency.id);

            $("#alleleFrequencyModal").modal('show');
        }

        function getParams(){
            var alleleFrequency = { };

            alleleFrequency.raceId = $("#raceId").val();
            alleleFrequency.raceName = $("#raceInfoSelect").val();
            alleleFrequency.markerName = $("#markerInfoSelect").val();
            alleleFrequency.alleleName = $("#alleleName").val();
            alleleFrequency.alleleFrequency = $("#alleleFrequency").val().trim();
            alleleFrequency.frequency = $("#alleleFrequency").val().trim();

            return alleleFrequency;
        }

        function getAlleleFrequency(){
            return "raceName=" + $("#raceInfoSelect").val() + "&markerName=" + $("#markerInfoSelect").val() + "&page=" + ${pageInfo.page};
        }

        $("#addBtn").on("click",function(){

            var raceName = $("#raceInfoSelect").val();
            if (raceName == ""){
                alert("请选择种族名称!");
                return;
            }

            var markerName = $("#markerInfoSelect").val();
            if (markerName == ""){
                alert("请选择基因座名称!");
                return;
            }

            var alleleName = $("#alleleName").val();
            if (alleleName == ""){
                alert("请输入基因!");
                return;
            }

            var alleleFrequency = $("#alleleFrequency").val();
            if (alleleFrequency == ""){
                alert("请输入频率!");
                return;
            }

            $.ajax({
                url:"<%=path%>/center/7/selectAlleleRepeatQuery.html?markerName=" + markerName + "&alleleName=" + alleleName + "&raceName=" + raceName,
                type:"post",
                dataType:"json",
                success:function(data){
                    if(data > 0){
                        alert("此基因座下已存在该基因，请重新填写!");
                        return;
                    }else {
                       $.ajax({
                            url:"<%=path%>/center/7/insertAlleleFrequency.html",
                            type:"post",
                            data:JSON.stringify(getParams()),
                            dataType:"json",
                            contentType:"application/json;charset=utf-8",
                            success: function (data) {
                                if(data.success){
                                    location.href = "<%=path%>/center/7/08.html?" + getAlleleFrequency();
                                }else {
                                    alert("添加失败!");
                                }
                            },
                            error:function(data){
                                alert("添加失败!");
                            }
                        });

                    }
                }
            });

        });

        function params(){
            var alleleFrequency = { };

            alleleFrequency.raceName = $("select[name='raceInfoName']").val();
            alleleFrequency.markerName = $("select[name='markerInfoName']").val();
            alleleFrequency.alleleName = $("input[name='alleleNam']").val();
            alleleFrequency.alleleFrequency = $("input[name='alleleFre']").val().trim();
            alleleFrequency.frequency = $("input[name='alleleFre']").val().trim();
            alleleFrequency.createPerson = $("input[name='createPerson']").val();
            alleleFrequency.raceId = $("input[name='raceInfoId']").val();
            alleleFrequency.id = $("input[name='id']").val();

            return alleleFrequency;
        }

        $("#saveBtn").on("click",function(){

            var raceName = $("select[name='raceInfoName']").val();
            if (raceName == ""){
                alert("请选择种族名称!");
                return;
            }

            var markerName = $("select[name='markerInfoName']").val();
            if (markerName == ""){
                alert("请选择基因座名称!");
                return;
            }

            var alleleName = $("input[name='alleleNam']").val();
            if (alleleName == ""){
                alert("请输入基因!");
                return;
            }

            var alleleFrequency = $("input[name='alleleFre']").val();
            if (alleleFrequency == ""){
                alert("请输入频率!");
                return;
            }

            $.ajax({
                url:"<%=path%>/center/7/updateAlleleFrequency.html",
                type:"post",
                data:JSON.stringify(params()),
                dataType:"json",
                contentType:"application/json;charset=utf-8",
                success: function (data) {
                    if(data.success){
                        location.href = "<%=path%>/center/7/08.html?" + getAlleleFrequency();
                    }else {
                        alert("修改失败!");
                    }
                },
                error:function(data){
                    alert("修改失败!");
                }
            });
        });

    });

    function deleteBtn(obj){
        if(!confirm("确认删除吗")){
            return;
        }

        $.ajax({
            url:"<%=path%>/center/7/deleteAlleleFrequency.html?id=" + obj,
            type:"post",
            dataType:"json",
            contentType:"application/json;charset=utf-8",
            success: function (data) {
                if(data){
                    location.href = "<%=path%>/center/7/08.html?" + getFrequency();
                }else {
                    alert("删除失败!");
                }
            },
            error:function(data){
                alert("删除失败!");
            }
        });
    }

    function getFrequency(){
        return "raceName=" + $("#raceInfoSelect").val() + "&markerName=" + $("#markerInfoSelect").val();
    }
</script>
<!-- END JS -->
</body>
</html>


