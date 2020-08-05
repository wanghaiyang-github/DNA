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
                            <form id="queryForm" class="form-horizontal tasi-form"
                                  action="<%=path%>/center/jk/registerInfoList.html" method="post">
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">DNA编号</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="personNo"
                                               value="${criminalPersonInfo.personNo}">
                                    </div>
                                    <label class="col-sm-2 col-sm-2 control-label"
                                           style="text-align:center;">人员姓名</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="personName"
                                               value="${criminalPersonInfo.personName}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label text-align-right">身份证号</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="idcardNo"
                                               value="${criminalPersonInfo.idcardNo}">
                                    </div>
                                    <label class="col-sm-2 col-sm-2 control-label"
                                           style="text-align:center;">人员类型</label>
                                    <div class="col-sm-3">
                                        <select class="form-control" id="personType" name="personType"
                                                value="${criminalPersonInfo.personTypeName}">
                                            <option value="">全部</option>
                                            <c:forEach items="${personTypeList}" var="personType" varStatus="s">
                                                <option value="${personType.dictCode}"
                                                        <c:if test="${personType.dictCode eq criminalPersonInfo.personType}">selected</c:if>>${personType.dictName}</option>
                                            </c:forEach>

                                        </select>
                                    </div>

                                    <div class="col-sm-1">
                                        <input type="hidden" name="page" id="page" value="${pageInfo.page}"/>
                                        <button class="btn btn-primary" type="button" id="queryBtn"><i
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
                                    <th>DNA编号</th>
                                    <th>人员名称</th>
                                    <th>人员类型</th>
                                    <th>身份证号</th>
                                    <th>采集单位</th>
                                    <th>操作</th>
                                </tr>
                                </thead>
                                <tbody id="criminalPersonTbody">
                                <c:forEach items="${criminalPersonInfoVoList}" var="criminalPersonInfo" varStatus="ci">
                                    <tr>
                                        <td>${ci.count}</td>
                                        <td>${criminalPersonInfo.personNo}</td>
                                        <td>${criminalPersonInfo.personName}</td>
                                        <td>${criminalPersonInfo.personTypeName}</td>
                                        <td>${criminalPersonInfo.idcardNo}</td>
                                        <td>${criminalPersonInfo.collectOrgName}</td>
                                        <td>
                                            <input type="hidden" name="criminalPersonId"
                                                   value="${criminalPersonInfo.id}">
                                            <button name="acceptBtn" class="btn btn-primary btn-sm"><i
                                                    class="fa fa-check"></i> 编辑
                                            </button>
                                            <button name="delBtn" class="btn btn-danger btn-sm"><i
                                                    class="fa fa-pencil"></i> 删除
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

        <div class="modal fade" id="personModal" aria-hidden="true" data-backdrop="static" data-keyboard="false">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                            &times;
                        </button>
                        <h4 class="modal-title">
                            人员信息
                        </h4>
                    </div>
                    <div class="modal-body">
                        <form class="form-horizontal tasi-form" id="criminalPersonForm">
                            <div class="form-group">
                                <label class="control-label col-md-3" style="padding-left: 80px;">姓名</label>
                                <div class="col-md-6">

                                    <input name="personName" type="text"
                                           class="form-control small " value=""/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3" style="padding-left: 80px;">性别</label>
                                <div class="col-md-6">
                                    <select name="personGender" class="form-control small">
                                        <c:forEach items="${personGenderList}" var="list"
                                                   varStatus="s">
                                            <option value="${list.dictCode}">${list.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3" style="padding-left: 80px;">人员类型</label>
                                <div class="col-md-6">
                                    <select name="personType" class="form-control small">
                                        <c:forEach items="${personTypeList}" var="list"
                                                   varStatus="s">
                                            <option value="${list.dictCode}"
                                            >${list.dictName}</option>
                                        </c:forEach>
                                    </select>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3" style="padding-left: 80px;">身份证号</label>
                                <div class="col-md-6">
                                    <input name="personIdcardNo" type="text"
                                           class="form-control small "
                                           value="">
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3" style="padding-left: 80px;">DNA编号</label>
                                <div class="col-md-6">
                                    <input name="personNo" type="text"
                                           class="form-control small " value="" readonly/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="control-label col-md-3" style="padding-left: 80px;">采集人</label>
                                <div class="col-md-6">
                                    <input name="collectPerson" type="text"
                                           class="form-control small " value=""/>
                                </div>
                            </div>
                            <div class="form-group">
                                <label class="control-label col-md-3" style="padding-left: 80px;">备注</label>
                                <div class="col-md-6">
                                    <input name="remark" type="text"
                                           class="form-control small " value=""/>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer">
                        <input type="hidden" name="criminalPersonId" value="">
                        <button class="btn btn-success" type="button" name="savePersonBtn">确定</button>
                        <button data-dismiss="modal" class="btn btn-default" type="button">关闭
                        </button>
                    </div>
                </div>
            </div>
        </div>

    </section>
</section>
<!-- BEGIN JS -->
<%@ include file="../../common/script.jsp" %>
<script>


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

    kkpager.generPageHtml({
        pno: ${pageInfo.page},
        //总页码
        total: ${pageInfo.pageCount},
        //总数据条数
        totalRecords: ${pageInfo.allRecordCount},
        //链接前部
        hrefFormer: '<%=path%>/center/jk/registerInfoList',
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

    $("#queryBtn").on("click", function () {
        $("#page").val(1);
        $('#queryForm').submit();
    });

    $("button[name='acceptBtn']", "#criminalPersonTbody").on('click', function () {
        var criminalPersonId = $("input[name='criminalPersonId']", $(this).parent()).val();

        $.ajax({
            url: "<%=path%>/center/jk/acceptCriminalPerson.html?criminalPersonId=" + criminalPersonId,
            type: "get",
            dataType: "json",
            success: function (data) {
                var criminalPerson = data.criminalPersonInfo;

                var criminalPersonId = criminalPerson.id;
                var personName = criminalPerson.personName;
                var gender = criminalPerson.gender;
                var idcardNo = criminalPerson.idcardNo;
                var personType = criminalPerson.personType;
                var personNo = criminalPerson.personNo;
                var collectPerson = criminalPerson.collectPerson;
                var remark = criminalPerson.remark;

                $("input[name='criminalPersonId']").val(criminalPersonId);
                $("input[name='personName']").val(personName);
                $("select[name='personGender']", "#personModal").val(gender);
                $("input[name='personIdcardNo']").val(idcardNo);
                $("select[name='personType']", "#personModal").val(personType);
                $("input[name='personNo']").val(personNo);
                $("input[name='collectPerson']").val(collectPerson);
                $("input[name='remark']").val(remark);

                $("#personModal").modal('show');

            }
        });
    });

    function GetCriminalPersonInfo() {
        var criminalPerson = {};

        criminalPerson.personName = $("input[name='personName']", "#criminalPersonForm").val();
        criminalPerson.gender = $("select[name='personGender'] option:checked", "#criminalPersonForm").val();
        criminalPerson.idcardNo = $("input[name='personIdcardNo']", "#criminalPersonForm").val();
        criminalPerson.personType = $("select[name='personType'] option:checked", "#criminalPersonForm").val();
        criminalPerson.personNo = $("input[name='personNo']", "#criminalPersonForm").val();
        criminalPerson.collectPerson = $("input[name='collectPerson']", "#criminalPersonForm").val();
        criminalPerson.remark = $("input[name='remark']", "#criminalPersonForm").val();

        return criminalPerson;
    }


    $("button[name='savePersonBtn']", "#personModal").on('click', function () {
        var criminalPersonId = $("input[name='criminalPersonId']", $(this).parent()).val();

        var criminalPerson = GetCriminalPersonInfo();
        var params = {
            "criminalPerson": criminalPerson
        };

        $.ajax({
            url: "<%=path%>/center/jk/updateCriminalPerson.html?criminalPersonId=" + criminalPersonId,
            type: "post",
            data: JSON.stringify(params),
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.success == true || data.success == "true") {
                    alert("保存成功");
                    location.reload();
                } else {
                    alert("保存失败！");
                }
            },
            error: function (e) {
                alert(e);
            }
        });
    });

    $("button[name='delBtn']", "#criminalPersonTbody").on('click', function () {
        var criminalPersonId = $("input[name='criminalPersonId']", $(this).parent()).val();

        if (!confirm("确认删除吗？")) {
            return;
        }

        $.ajax({
            url: "<%=path%>/center/jk/delCriminalPerson.html?criminalPersonId=" + criminalPersonId,
            type: "get",
            dataType: "json",
            success: function (data) {
                if (data.success == true || data.success == "true") {
                    alert("删除成功");
                    location.reload();
                } else {
                    alert("删除失败！");
                }
            },
            error: function (e) {
                alert(e);
            }
        });
    });

</script>

<!-- END JS -->
</body>
</html>
