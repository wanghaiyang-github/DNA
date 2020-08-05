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
                            <form class="form-horizontal tasi-form" method="get">
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案件名称</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control">
                                    </div>
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">案件状态</label>
                                    <div class="col-sm-3">
                                        <select class="form-control">
                                            <option>未受理</option>
                                            <option>已受理</option>
                                            <option>已退案</option>
                                            <option>已完成</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">案发时间</label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" readonly="readonly" type="text" value="">
                                    </div>
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">至 </label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" readonly="readonly" type="text" value="">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label text-align-right">案件类型</label>
                                    <div class="col-sm-3">
                                        <select class="form-control">
                                            <option>刑事案件</option>
                                            <option>民事案件</option>
                                            <option>其他案件</option>
                                        </select>
                                    </div>
                                    <label class="col-sm-2 col-sm-2 control-label" style="text-align:center;">案件性质</label>
                                    <div class="col-sm-3">
                                        <select class="form-control">
                                            <option>凶杀</option>
                                            <option>伤害</option>
                                            <option>强奸</option>
                                            <option>抢夺抢劫</option>
                                            <option>入室盗窃</option>
                                            <option>盗窃机动车</option>
                                            <option>盗窃车内财物</option>
                                            <option>其他盗窃</option>
                                            <option>走失人口</option>
                                            <option>治安</option>
                                            <option>其它</option>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="col-lg-offset-9 col-lg-10">
                                        <button class="btn btn-primary" type="submit"><i class="fa fa-search"></i> 查 询</button>
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
                            <span class="label label-primary">案件列表</span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>
                        </header>
                        <div class="panel-body">
                            <table class="table table-striped table-advance table-bordered table-hover">
                                <thead>
                                <tr>
                                    <th>案件名称</th>
                                    <th>案件编号</th>
                                    <th>案件类型</th>
                                    <th>案件性质</th>
                                    <th>案发时间</th>
                                    <th>委托单位</th>
                                    <th>是否为补送</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody>
                                <tr>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td></td>
                                    <td>
                                        <button class="btn btn-success btn-sm"><i class="fa fa-tag"></i> 入库&比对</button>
                                    </td>
                                </tr>
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
<!-- END JS -->
</body>
</html>
