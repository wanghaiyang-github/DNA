<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <!-- BEGIN META -->
    <meta charset="utf-8">
    <!-- BEGIN STYLESHEET-->
    <%@ include file="../common/link.jsp" %>
    <link href="<%=path%>/css/tasks.css" rel="stylesheet"><!-- TASK CSS-->
    <link href="<%=path%>/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
    <!-- END STYLESHEET-->
</head>
<body>
<section id="main-content">
    <section class="wrapper">
        <div id="wrapper-content">

    <!-- BEGIN WRAPPER  -->
        <!-- BEGIN ROW  -->
        <div class="row state-overview">
            <div class="col-lg-6 col-sm-6">
                <section class="panel">
                    <div class="symbol">
                        <i class="fa fa-book blue">
                        </i>
                    </div>
                    <div class="value">
                        <input type="hidden" id="caseCount" value="${caseCount}"/>
                        <h1 class="count">0</h1>
                        <p>
                            案件数
                        </p>
                    </div>
                </section>
            </div>
            <div class="col-lg-6 col-sm-6">
                <section class="panel">
                    <div class="symbol">
                        <i class="fa fa-tags red">
                        </i>
                    </div>
                    <div class="value">
                        <input type="hidden" id="sampleCount" value="${sampleCount}"/>
                        <h1 class="count2">
                            0
                        </h1>
                        <p>
                            检材数
                        </p>
                    </div>
                </section>
            </div>
        </div>
        <div class="row state-overview">
            <div class="col-lg-6 col-sm-6">
                <section class="panel">
                    <div class="symbol">
                        <i class="fa fa-check-square yellow">
                        </i>
                    </div>
                    <div class="value">
                        <input type="hidden" id="detectionRate" value="${detectionRate}">
                        <h1 class="count3">
                            0
                        </h1>
                        <p>
                            检出率
                        </p>
                    </div>
                </section>
            </div>
            <div class="col-lg-6 col-sm-6">
                <section class="panel">
                    <div class="symbol">
                        <i class="fa fa-male purple">
                        </i>
                    </div>
                    <div class="value">
                        <input type="hidden" id="actionRate" value="${actionRate}">
                        <h1 class="count4">
                            0
                        </h1>
                        <p>
                            作用率
                        </p>
                    </div>
                </section>
            </div>
        </div>
        <!-- END ROW  -->
    <div id="morris">
        <div class="row">
            <div class="col-sm-6">
                <section class="panel">
                    <header class="panel-heading">
                          <span class="label label-primary">
                            案件分类
                          </span>
                    </header>
                    <div class="panel-body">
                        <div id="case-graph-donut">
                        </div>
                    </div>
                </section>
            </div>
            <div class="col-sm-6">
                <section class="panel">
                    <header class="panel-heading">
                          <span class="label label-primary">
                            检材分类
                          </span>
                    </header>
                    <div class="panel-body">
                        <div id="sample-graph-donut">
                        </div>
                    </div>
                </section>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-12">
            <!-- START PANEL -->
            <section class="panel tasks-widget">
                <header class="panel-heading">
                    数据概况
                </header>
                <div class="panel-body">
                    <div class="task-content">
                        <ul class="task-list">
                            <li>
                                <div class="task-title">
                                  <span class="task-title-sp">
                                    未受理
                                  </span>
                                  <span class="pull-right badge badge-sm label-success">
                                    ${caseStatusPendingCount} 条
                                  </span>
                                </div>
                            </li>
                            <li>
                                <div class="task-title">
                                  <span class="task-title-sp">
                                    在检验
                                  </span>
                                  <span class="pull-right badge badge-sm label-danger">
                                    ${caseStatusAcceptedCount} 条
                                  </span>
                                </div>
                            </li>
                            <li>

                                <div class="task-title">
                                  <span class="task-title-sp">
                                    已导入结果
                                  </span>
                                  <span class="pull-right badge badge-sm label-warning">
                                    ${caseInfoCount} 条
                                  </span>
                                </div>
                            </li>
                            <li>
                                <div class="task-title">
                                      <span class="task-title-sp">
                                        未出鉴定书
                                      </span>
                                      <span class="pull-right badge badge-sm label-primary">
                                        ${identifyBookPendingCount} 条
                                      </span>
                                </div>
                            </li>
                            <li>
                                <div class="task-title">
                                  <span class="task-title-sp">
                                    待签发鉴定书
                                  </span>
                                  <span class="pull-right badge badge-sm label-info">
                                    ${identifyBookNeedSingCount} 条
                                  </span>
                                </div>
                            </li>
                            <li>
                                <div class="task-title">
                                  <span class="task-title-sp">
                                    待领取鉴定书
                                  </span>
                                  <span class="pull-right badge badge-sm label-danger">
                                    ${identifyBookSingedCount} 条
                                  </span>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>
            </section>
            <!-- END PANEL -->
        </div>
    </div>
    </div>
</section>
</section>

<!-- BEGIN JS -->
<%@ include file="../common/script.jsp" %>
<script src="<%=path%>/js/count.js"></script><!-- COUNT JS -->
<!--Morris-->
<script src="<%=path%>/assets/morris.js-0.4.3/morris.min.js" ></script><!-- MORRIS JS -->
<script src="<%=path%>/assets/morris.js-0.4.3/raphael-min.js" ></script><!-- MORRIS  JS -->
<%--<script src="<%=path%>/js/chart.js"></script>--%>
<script>
    $(function(){

        'use strict';

        function randomColor(size){
            var colorArr = ["#FF8800", "#DAA520", "#FFC0CB", "#ADD8E6", "#BC8F8F", "#7B68EE", "#4169E1", "#228B22", "#696969", "#00FFFF","#90EE90","#00A8B3","#3D20B3","#B34D0F","#B31C11","#FF20EB","#FF4A22"];


            var returnColorArr = new Array();
            for(var i = 0; i < size; i++){
                returnColorArr.push(colorArr[i]);
            }

            return returnColorArr;
        }

        var caseCount = $("#caseCount").val();
        var sampleCount= $("#sampleCount").val();
        var detectionRate= $("#detectionRate").val();
        var actionRate= $("#actionRate").val();
        countUp(caseCount);
        countUp2(sampleCount);
        countUp3(detectionRate);
        countUp4(actionRate);

        //根据案件性质统计案件数量占比
        $.ajax({
            type:"get",
            url:"<%=path%>/center/caseDataByProperty.html",
            dataType:"json",
            success:function(data){
                Morris.Donut({
                    element: 'case-graph-donut',
                    data: data,
                    backgroundColor: '#fff',
                    labelColor: '#2c99b9',
                    colors: randomColor(data.length),
                    formatter: function (x, data) { return data.formatted; }
                });
            }
        });

        //根据检查类型统计检材数量占比
        $.ajax({
            type:"get",
            url:"<%=path%>/center/sampleDataByType.html",
            dataType:"json",
            success:function(data){
                Morris.Donut({
                    element: 'sample-graph-donut',
                    data: data,
                    backgroundColor: '#fff',
                    labelColor: '#2c99b9',
                    colors: randomColor(data.length),
                    formatter: function (x, data) { return data.formatted; }
                });
            }
        });

    });
</script>
<!-- END JS -->
</body>
</html>


