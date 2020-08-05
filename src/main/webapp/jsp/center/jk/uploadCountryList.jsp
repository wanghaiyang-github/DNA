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
                                  action="<%=path%>/center/jk/jkUploadCountryDB.html" method="post">
                                <div class="form-group">
                                    <label class="col-sm-2 col-sm-2 control-label">DNA编号</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="personNo"
                                               value="${criminalPersonInfo.personNo}">
                                    </div>
                                    <label class="col-sm-2 col-sm-2 control-label"
                                           style="text-align:center;">CODIS文件名</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control" name="codisFileName"
                                               value="${criminalPersonInfo.codisFileName}">
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-2 control-label">导入时间</label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" name="scanedDatetimeStart" readonly="readonly"
                                               type="text"
                                               value="<fmt:formatDate value='${criminalPersonInfo.scanedDatetimeStart}' pattern='yyyy-MM-dd HH:mm'/>">
                                    </div>
                                    <label class="col-sm-2 control-label" style="text-align:center;">至 </label>
                                    <div class="col-sm-3">
                                        <input class="form_datetime form-control" name="scanedDatetimeEnd" readonly="readonly"
                                               type="text"
                                               value="<fmt:formatDate value='${criminalPersonInfo.scanedDatetimeEnd}' pattern='yyyy-MM-dd HH:mm'/>">
                                    </div>

                                    <div class="col-sm-1">
                                        <input type="hidden" name="page" id="page" value="${pageInfo.page}"/>
                                        <button class="btn btn-primary" type="button" id="queryBtn"><i
                                                class="fa fa-search"></i> 查 询
                                        </button>
                                    </div>
                                    <div class="col-sm-1">
                                        <button class="btn btn-success " type="button" name="compareBtn"><i
                                                class="fa fa-cloud-upload"></i> 上 报
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
                                    <th>导入时间</th>
                                    <th>身份证号</th>
                                    <th>采集单位</th>
                                    <th>上报状态</th>
                                    <th><input type="checkbox" name="allEvidenceChecked" id="allEvidenceChecked"/>全选
                                    </th>
                                </tr>
                                </thead>
                                <tbody id="uploadNationalTbody">
                                <c:forEach items="${criminalPersonInfoVoList}" var="criminalPersonInfo" varStatus="ci">
                                    <tr>
                                        <td>${ci.count}</td>
                                        <td>${criminalPersonInfo.personNo}</td>
                                        <td>${criminalPersonInfo.personName}</td>
                                        <td>
                                            <fmt:formatDate value='${criminalPersonInfo.geneCreateDatetime}' pattern='yyyy-MM-dd HH:mm'/>
                                        </td>
                                        <td>${criminalPersonInfo.idcardNo}</td>
                                        <td>${criminalPersonInfo.collectOrgName}</td>
                                        <c:choose>
                                            <c:when test="${criminalPersonInfo.instoredFlag eq 2}">
                                                <td>
                                                 已上报
                                                </td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>未上报</td>
                                            </c:otherwise>
                                        </c:choose>
                                        <c:choose>
                                            <c:when test="${criminalPersonInfo.instoredFlag eq 1}">
                                                <td>
                                                    <input type="hidden" name="personId" value="${criminalPersonInfo.id}">
                                                    <input type="hidden" name="sampleGeneId" value="${criminalPersonInfo.sampleGeneId}">
                                                    <label class="checkbox-inline"><input type="checkbox"
                                                                                          name="uploadEvidenceBox"></label>
                                                    <button class="btn btn-success btn-sm" name="viewBtn"><i class="fa fa-list"></i> 查看</button>
                                                </td>
                                            </c:when>
                                            <c:otherwise>
                                                <td>--</td>
                                            </c:otherwise>
                                        </c:choose>
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

    $("button[name='compareBtn']").on("click", function () {

        var criminalPersonInfoList = GetCriminalPerson();


        if(criminalPersonInfoList.length<1){
            alert("请选择上报的样本!");
            return;
        }
        var params = {
            "criminalPersonInfoList": criminalPersonInfoList
        };

        $.ajax({
            url: "<%=path%>/center/jk/uploadQueue.html",
            type: "post",
            data: JSON.stringify(params),
            dataType: "json",
            contentType: "application/json; charset=utf-8",
            success: function (data) {
                if (data.success == true || data.success == "true") {
                    alert("上报成功!");
                    location.reload();
                } else {
                    alert("上报失败！");
                }
            },
            error: function (e) {
                alert(e);
            }
        });

    });

    function GetCriminalPerson() {
        var criminalPersonArr = new Array();

        var criminalPerson;
        var checkBox;
        if($("tr", "#uploadNationalTbody").length > 0){
            var $samplePersonTRArr = $("tr", "#uploadNationalTbody").not(".regedTr");
            var samplePersonCnt = $samplePersonTRArr.length;
            var $samplePersonTR;
            for (var i = 0; i < samplePersonCnt; i++) {
                criminalPerson = {};
                $samplePersonTR = $($samplePersonTRArr.get(i));
                criminalPerson.id = $("input[name='personId']", $samplePersonTR).val();
                criminalPerson.sampleGeneId = $("input[name='sampleGeneId']", $samplePersonTR).val();
                checkBox = $("input[name='uploadEvidenceBox']",$samplePersonTR).is(":checked");
                if (checkBox)
                    criminalPersonArr.push(criminalPerson);
            }
        }
        return criminalPersonArr;
    }

    $('.form_datetime').datetimepicker({
        format: 'yyyy-mm-dd hh:ii',
        language:  'zh',
        weekStart: 1,
        todayBtn:  1,
        minView: "hour",
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
        hrefFormer: '<%=path%>/center/jk/jkUploadCountryDB',
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

    $("#allEvidenceChecked").on("click", function () {
        var ch = document.getElementsByName("uploadEvidenceBox");
        if (document.getElementsByName("allEvidenceChecked")[0].checked == true) {
            for (var i = 0; i < ch.length; i++) {
                ch[i].checked = true;
            }
        } else {
            for (var i = 0; i < ch.length; i++) {
                ch[i].checked = false;
            }
        }
    });

    $("button[name='viewBtn']","#uploadNationalTbody").on("click",function(){
        var sampleGeneId = $("input[name='sampleGeneId']", $(this).parent()).val();
        location.href = "<%=path%>/center/jk/editGene.html?id=" + sampleGeneId;
    });

</script>

<!-- END JS -->
</body>
</html>
