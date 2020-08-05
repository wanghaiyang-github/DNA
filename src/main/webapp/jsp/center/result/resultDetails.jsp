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
            1 text-decoration: none
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

        .heji td {
            background: #feeed9 !important;
            color: #e37900;
        }
    </style>
</head>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" style="top: 13px;">
    <div class="modal-dialog" role="document" style="width: 80%; margin-top: 44px;">
        <div class="modal-content">
            <div class="modal-body" style="padding: 0px;">
                <button type="button" class="closeBtn" data-dismiss="modal" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                </button>
                <table class="table table-striped table-hover table-bordered">
                    <thead>
                    <tr>
                        <th rowspan="2" style="width: 15%;line-height: 56px;">序号</th>
                        <th rowspan="2" style="width: 15%;line-height: 56px;">基因座</th>
                        <th colspan="2" style="background: #1477e1">等位基因</th>
                        <th rowspan="2" style="width: 15%;line-height: 56px;">LR</th>
                        <th rowspan="2" style="width: 15%;line-height: 56px;">其他</th>
                    </tr>
                    <tr>
                        <th id="dwjy1" style="background: #085cd5"></th>
                        <th id="dwjy2" style="background: #085cd5"></th>
                    </tr>
                    </thead>
                    <tbody id="panelTbody">
                    </tbody>
                </table>
                <input type="hidden" class="totalProbability" value="${matchResult.totalProbability}">
            </div>
        </div>
    </div>
</div>

<body>
<section id="main-content">
    <section class="wrapper">
        <div id="wrapper-content">
            <!-- BEGIN ROW  -->
            <div class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading" style="border: none">
                            <span class="label label-primary">结果详情</span>
                        </header>
                        <div class="panel-body" style="border-top: 1px solid #ccc;">
                            <table class="table table-striped table-hover">
                                <thead>
                                <tr>
                                    <th>序号</th>
                                    <th>源案件名称</th>
                                    <th>检材名称</th>
                                    <th>对中样本</th>
                                    <th>对中案件</th>
                                    <th>比中类型</th>
                                    <th>状态</th>
                                    <th>匹配个数</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${matchResultList}" var="matchResult" varStatus="s">
                                <tr>
                                    <td>${s.index+1}</td>
                                    <td>${matchResult.sourceCaseName}</td>
                                    <td>${matchResult.sample1Name}</td>
                                    <td>${matchResult.sample2Name}</td>
                                    <td>${matchResult.targetCaseName}</td>
                                    <td>${matchResult.matchedModeName}</td>
                                    <td>已比中</td>
                                    <td>${matchResult.sameCount}</td>
                                    <td>
                                        <input type="hidden" name="matchResultString"
                                               value="${matchResult.matchResultString}">
                                        <input type="hidden" name="matchResultId" value="${matchResult.id}">
                                        <button class="btn btn-sm btn-red" type="button" name="delBtn">取消比中</button>
                                        <button class="btn btn-sm btn-blue" data-toggle="modal" data-target="#myModal" type="button" name="details">
                                            查看详情
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
        </div>
    </section>
</section>
<!-- BEGIN JS -->
<%@ include file="../../common/script.jsp" %>
<script>
    $(function () {
        'use strict';

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

        /**
         * 查看详情
         */
        $("button[name='details']").on('click', function () {

            var matchResultId = $("input[name='matchResultId']", $(this).parent()).val();
            $.ajax({
                url:"<%=path%>/center/3/queryMatchedDatails.html?matchResultId=" + matchResultId,
                type:"post",
                dataType:"json",
                contentType: "application/json; charset=utf-8",
                success:function(data){
                    var geneInfo1 = data.geneInfo1;
                    var geneInfo2 = data.geneInfo2;
                    var matchResultString = data.matchResultString;
                    var totalProbability = data.totalProbability;
                    var sample1Name = data.sample1Name;
                    var sample2Name = data.sample2Name;
                    var sameCount = data.sameCount;

                    viewGeneDetail(sample1Name, sample2Name, geneInfo1, geneInfo2, matchResultString, totalProbability, sameCount);
                    $("#myModal").show();
                },
                error:function(data,e){
                    alert("查询失败!");
                }
            });
        });

        function viewGeneDetail(sample1Name, sample2Name, geneInfo1, geneInfo2, Lr, totalProbability, sameCount) {
            if (typeof(geneInfo1) != "undefined" && geneInfo1 != 0) {
                var jsonInfo1;
                var len1;
                var jsonInfo2;
                var len2;

                if($.parseJSON(geneInfo1).length > $.parseJSON(geneInfo2).length){
                    $("#dwjy1").html(sample1Name);
                    $("#dwjy2").html(sample2Name);
                    jsonInfo1 = $.parseJSON(geneInfo1);
                    len1 = $.parseJSON(geneInfo1).length;
                    jsonInfo2 = $.parseJSON(geneInfo2);
                    len2 = $.parseJSON(geneInfo2).length;
                }else{
                    $("#dwjy1").html(sample2Name);
                    $("#dwjy2").html(sample1Name);
                    jsonInfo1 = $.parseJSON(geneInfo2);
                    len1 = $.parseJSON(geneInfo2).length;
                    jsonInfo2 = $.parseJSON(geneInfo1);
                    len2 = $.parseJSON(geneInfo1).length;
                }


                var jsonInfoLr = $.parseJSON(Lr);
                var lrLen = jsonInfoLr.length;

                var gene;
                $("#panelTbody").empty();
                var htmlStr = "";

                for (var i = 0; i < len1; i++) {
                    gene = jsonInfo1[i];
                    htmlStr += "<tr><td>" + (i + 1) + "</td>";
                    htmlStr += "<td>" + gene.geneName + "</td>";
                    htmlStr += "<td>" + gene.geneVal1 + "," + gene.geneVal2 + "</td>";

                    var flag = false;
                    for (var j = 0; j < len2; j++) {
                        if (gene.geneName == $.parseJSON(geneInfo2)[j].geneName) {
                            htmlStr += "<td>" + $.parseJSON(geneInfo2)[j].geneVal1 + "," + $.parseJSON(geneInfo2)[j].geneVal2 + "</td>";
                            flag = true;
                            break;
                        }
                    }
                    if(!flag){
                        htmlStr += "<td></td>";
                    }else{
                        flag = false;
                    }
                    for (var j = 0; j < lrLen; j++) {
                        if (gene.geneName == $.parseJSON(Lr)[j].geneName) {
                            htmlStr += "<td>" + $.parseJSON(Lr)[j].geneLr + "</td>";
                            flag = true;
                            break;
                        }
                    }
                    if(!flag){
                        htmlStr += "<td></td>";
                    }
                    htmlStr += "<td></td>";
                    htmlStr += "</tr>";
                }

                htmlStr += "<tr class='heji'><td>合计</td><td>比中基因座数:" + sameCount + "</td><td></td><td></td><td>LR:"+totalProbability+"</td><td></td></tr>"
                $("#panelTbody").append(htmlStr);
            }
        }

        $("button[name='delBtn']").on('click', function () {

            var matchResultId = $("input[name='matchResultId']", $(this).parent()).val();
            $.ajax({
                url:"<%=path%>/center/3/deleteByMatchResult.html?matchResultId=" + matchResultId,
                type:"post",
                dataType:"json",
                contentType: "application/json; charset=utf-8",
                success:function(data){

                    if(data.success == true || data.success == "true"){
                        alert("取消成功");
                        location.href='<%=path%>/center/3/comparisonResult.html';
                    }else if(data.success == false || data.success == "false"){
                        alert("取消失败!");
                    }

                },
                error:function(data,e){
                    alert("取消失败!");
                }
            });

        });

    });
</script>
<!-- END JS -->
</body>
</html>
