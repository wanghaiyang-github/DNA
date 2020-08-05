<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/include.jsp" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <%@ include file="../../common/link.jsp" %>
    <style>
        #main-content .table-bordered > thead > tr > th,
        #main-content .table-bordered > tbody > tr > th,
        #main-content .table-bordered > tfoot > tr > th,
        #main-content .table-bordered > thead > tr > td,
        #main-content .table-bordered > tbody > tr > td,
        #main-content .table-bordered > tfoot > tr > td {
            border: 1px solid #f0f0f0;
        }

        #main-content .panel-body {
            padding: 0px;
        }

        #main-content .table-striped > tbody > tr:nth-child(odd) > td,
        #main-content .table-striped > tbody > tr:nth-child(odd) > th {
            background-color: #f5f5f5;
        }

        #main-content .select {
            position: relative;
        }

        #main-content select {
            border: 1px solid #e8e8e8 !important;
            border-radius: 0px !important;
            outline: none;
            width: 100%;
            height: 34px;
            line-height: 34px;
            appearance: none;
            -webkit-appearance: none;
            -moz-appearance: none;
            padding-left: 10px;
            background: #fafafa;
        }

        #main-content .select:after {
            content: "";
            width: 14px;
            height: 8px;
            background: url("<%=path%>/img/xiala.png") no-repeat center;
            position: absolute;
            right: 8px;
            top: 40%;
            pointer-events: none;
        }

        #main-content input[type=checkbox] {
            /*margin: 50px;*/
            /*同样，首先去除浏览器默认样式*/
            -webkit-appearance: none;
            -moz-appearance: none;
            appearance: none;
            /*编辑我们自己的样式*/
            position: relative;
            width: 16px;
            height: 15px;
            background: transparent;
            border: 1px solid #ccc;
            -webkit-border-radius: 4px;
            -moz-border-radius: 4px;
            border-radius: 3px;
            outline: none;
            cursor: pointer;
            margin-right: 5px;
            top: 3px;
        }

        #main-content input[type=checkbox]::after {
            content: '√';
            position: absolute;
            display: block;
            width: 100%;
            height: 100%;
            background: #1f83db;
            border: 1px solid transparent;
            color: #fff;
            text-align: center;
            line-height: 13px;
            /*增加动画*/
            -webkit-transition: all ease-in-out 300ms;
            -moz-transition: all ease-in-out 300ms;
            transition: all ease-in-out 300ms;
            /*利用border-radius和opacity达到填充的假象，首先隐藏此元素*/
            -webkit-border-radius: 20px;
            -moz-border-radius: 20px;
            border-radius: 20px;
            opacity: 0;
        }

        #main-content input[type=checkbox]:checked:after {
            -webkit-border-radius: 0;
            -moz-border-radius: 0;
            border-radius: 0;
            opacity: 1;
        }

        #main-content .panel-heading {
            border-color: #eff2f7;
            font-size: 16px;
            font-weight: 300;
            padding: 15px 10px;
            /* border-top: 2px solid #2a8ba7; */
        }

        #main-content .col-md-3 {
            padding: 0px;
            width: 25%;
        }

        #main-content2 .table {
            border-collapse: separate;
            border-spacing: 0px 5px;
        }

        #main-content2 .table th {
            text-align: center;
            border: none !important;
        }

        #main-content2 .table th:nth-child(3) {
            /*text-align: left !important;*/
            border: none !important;
            /*padding-left: 70px;*/
        }

        #main-content2 .table td {
            text-align: center !important;
        }

        #main-content2 .table tbody tr td:nth-child(2),
        #main-content2 .table tbody tr td:nth-child(3) {
            padding: 0px;
        }

        #main-content2 .table tbody tr td p {
            font-weight: 600;
            background: #fee6c5;
            color: #b49461;
            height: 33px;
            line-height: 33px;
            width: 99%;
            font-size: 12px;
        }

        #main-content2 .table tbody tr td:nth-child(4) {
            color: #d4666c;
        }

        #main-content2 .panel-heading {
            border-color: #eff2f7;
            font-size: 16px;
            font-weight: 300;
            border-top: none;
        }

        #main-content2 .succes {
            text-align: center;
            color: #50ce8c;
            background: #fff;
            margin-bottom: 10px;
            height: 85px;
            border-radius: 5px;
            line-height: 85px;
            font-size: 25px;
        }

        #main-content2 .error {
            text-align: center;
            color: red;
            background: #fff;
            margin-bottom: 10px;
            height: 85px;
            border-radius: 5px;
            line-height: 85px;
            font-size: 25px;
        }
    </style>
</head>
<body>
<section id="main-content">
    <section class="wrapper">
        <!-- BEGIN ROW  -->
        <div class="row">
            <div class="col-lg-12">
                <section class="panel">
                    <header class="panel-heading">
                        <span class="label label-primary" style="border-radius: 2px">样本列表</span>
                    </header>
                    <div class="panel-body">
                        <table class="table table-striped table-advance table-bordered table-hover"
                               style="font-size: 13px">
                            <thead>
                            <tr>
                                <th style="width: 3%;">序号</th>
                                <th style="width: 9%;">样本编号</th>
                                <th style="width: 8%;">样本名称</th>
                                <th>入库样本类型</th>
                                <th style="width: 6%;">板孔位置</th>
                                <th style="width: 4%;">状态</th>
                                <th style="width: 6%;">完成时间</th>
                                <th style=" width: 20%;">
                                    <input type="checkbox" name="allBox">操作
                                </th>
                            </tr>
                            </thead>
                            <tbody id="waerhousingTbody">
                            <c:forEach items="${limsSampleInfoList}" var="sampleInfo" varStatus="ci">
                                <tr>
                                    <td>${ci.count}</td>
                                    <td>${sampleInfo.sampleNo}</td>
                                    <td>${sampleInfo.sampleName}</td>
                                    <td style="width: 60%">
                                        <div class="row" style="margin: 0px">
                                            <div class="col-md-3">
                                                <div class="form-group">
                                                    <div class="col-sm-12">
                                                        <div class="select">
                                                            <select name="SampleEntry">
                                                                <option value=''>请选择入库类型</option>
                                                                <c:forEach items="${dictSampleEntryTypeList}"
                                                                           var="SampleEntryType"
                                                                           varStatus="s">
                                                                    <option value="${SampleEntryType.dictCode}"
                                                                            <c:if test="${sampleInfo.sampleEntryType eq SampleEntryType.dictCode}">selected</c:if>  >${SampleEntryType.dictName}</option>
                                                                </c:forEach>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-3 hidden">
                                                <div class="form-group">
                                                    <div class="col-sm-12">
                                                        <div class="select">
                                                            <select name="match">
                                                                <option value=''>请选择目标样本角色</option>
                                                                <option value="子女"
                                                                        <c:if test="${sampleInfo.targetSampleRole eq '子女'}">selected</c:if> >
                                                                    子女
                                                                </option>
                                                                <option value="配偶"
                                                                        <c:if test="${sampleInfo.targetSampleRole eq '配偶'}">selected</c:if> >
                                                                    配偶
                                                                </option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-3 hidden">
                                                <div class="form-group">
                                                    <div class="col-sm-12">
                                                        <div class="select">
                                                            <select name="sameGroupSample">
                                                                <option value=''>请选择同组样本</option>
                                                            </select>
                                                            <input type="hidden" id="sameGroupSample"
                                                                   value="${sampleInfo.sameGroupSample}"/>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="col-md-3 hidden">
                                                <div class="form-group">
                                                    <div class="col-sm-12">
                                                        <div class="select">
                                                            <select name="GroupSampleRole">
                                                                <option value=''>请选择同组样本角色</option>
                                                                <option value="父/母"
                                                                        <c:if test="${sampleInfo.sameGroupSampleRole eq '父/母'}">selected</c:if> >
                                                                    父/母
                                                                </option>
                                                                <option value="孩子"
                                                                        <c:if test="${sampleInfo.sameGroupSampleRole eq '孩子'}">selected</c:if> >
                                                                    孩子
                                                                </option>
                                                                <option value="父亲"
                                                                        <c:if test="${sampleInfo.sameGroupSampleRole eq '父亲'}">selected</c:if> >
                                                                    父亲
                                                                </option>
                                                                <option value="母亲"
                                                                        <c:if test="${sampleInfo.sameGroupSampleRole eq '母亲'}">selected</c:if> >
                                                                    母亲
                                                                </option>
                                                            </select>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </td>
                                    <c:choose>
                                        <c:when test="${sampleInfo.samplePostion eq null}">
                                            <td style="text-align: center">--</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td style="text-align: center">${sampleInfo.samplePostion}</td>
                                        </c:otherwise>
                                    </c:choose>

                                    <c:choose>
                                        <c:when test="${sampleInfo.submitFlag eq '1'}">
                                            <td style="color: red">已入库</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>未入库</td>
                                        </c:otherwise>
                                    </c:choose>

                                    <c:choose>
                                        <c:when test="${sampleInfo.submitDatetime eq null}">
                                            <td style="text-align: center">--</td>
                                        </c:when>
                                        <c:otherwise>
                                            <td>
                                                <fmt:formatDate value='${sampleInfo.submitDatetime}'
                                                                pattern='yyyy-MM-dd'/>
                                            </td>
                                        </c:otherwise>
                                    </c:choose>

                                    <c:choose>
                                        <c:when test="${sampleInfo.auditStatus eq '1'}">
                                            <td>
                                                <input type="checkbox" name="box" value="${sampleInfo.sampleNo}">
                                                <a>入库</a>
                                            </td>
                                        </c:when>
                                        <c:otherwise>
                                            <td style="text-align: center">--</td>
                                        </c:otherwise>
                                    </c:choose>
                                    <input type="hidden" name="sampleId" value="${sampleInfo.id}"/>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <div class="form-group pull-right">
                            <button class="btn btn-sm btn-info" id="compareBtn" name="compareBtn" type="button"
                                    style="margin-right: 10px;border-radius: 2px"><i
                                    class="fa fa-pencil"></i>确认提交
                            </button>
                        </div>
                    </div>
                </section>
            </div>
        </div>

        </div>
    </section>
</section>
<section id="main-content2" class="hidden">
    <section class="wrapper">
        <!-- BEGIN ROW  -->
        <div class="row">
            <div class="col-md-12">
                <p class="succes"></p>
            </div>
        </div>
        <div class="row">
            <div class="col-lg-12">
                <section class="panel">
                    <header class="panel-heading">
                        <span class="label label-primary" style="border-radius: 2px">详情展示</span>
                    </header>
                    <div class="panel-body">
                        <table class="table">
                            <thead>
                            <tr>
                                <th style="width: 7%;">序号</th>
                                <th style="width: 25%;">入库样本编号</th>
                                <th style="width: 25%;">库中样本编号</th>
                                <th style="text-align: right!important;padding-right: 80px">比中状况</th>
                            </tr>
                            </thead>
                            <tbody id="resultTbody">
                            </tbody>
                        </table>
                        <div class="row">
                            <div class="col-md-12" style="text-align: center">
                                <button class="btn btn-sm btn-info" type="button">返回比对结果页</button>
                                <button class="btn btn-primary btn-sm backHidden" type="button">返回入库比对</button>
                            </div>
                        </div>
                    </div>
                </section>
            </div>
        </div>
    </section>
</section>
<!-- BEGIN JS -->
<%@ include file="../../common/script.jsp" %>
<script>
    $(function () {
        'use strict';
        $('#waerhousingTbody').find('tr').each(function () {
            var newOption = ''
            $(this).siblings().each(function () {
                var content = $(this).children('td').eq(1).text() + "&nbsp;&nbsp;&nbsp;" + $(this).children('td').eq(2).text()
                newOption += '<option value="' + $(this).find("input[name='sampleId']").val() + '">' + content + '</option>'
            })
            $(this).find("select[name='sameGroupSample']").append(newOption)

            if ($(this).find('select[name="match"]').val() == '配偶') {
                $(this).find('select[name="match"]').parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(1).removeAttr('class')
                $(this).find('select[name="match"]').parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(2).removeAttr('class')
                $(this).find('select[name="match"]').parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(3).removeAttr('class').addClass('hidden')
                $(this).find('select[name="match"]').parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(4).removeAttr('class').addClass('hidden')
            } else if ($(this).find('select[name="match"]').val() == '') {
                $(this).find('select[name="match"]').parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(1).removeAttr('class').addClass('hidden')
                $(this).find('select[name="match"]').parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(2).removeAttr('class').addClass('hidden')
                $(this).find('select[name="match"]').parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(3).removeAttr('class').addClass('hidden')
                $(this).find('select[name="match"]').parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(4).removeAttr('class').addClass('hidden')
            } else {
                $(this).find('select[name="match"]').parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(1).removeAttr('class').addClass('hidden')
                $(this).find('select[name="match"]').parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(2).removeAttr('class').addClass('hidden')
                $(this).find('select[name="match"]').parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(3).removeAttr('class')
                $(this).find('select[name="match"]').parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(4).removeAttr('class')
            }

            var selectChildren = $(this).find('select[name="SampleEntry"]')
            if (selectChildren.val() == '08' || selectChildren.val() == '09' || selectChildren.val() == '10') {
                selectChildren.parents('.col-md-3').siblings().removeClass('hidden')
                selectChildren.parents('.col-md-3').siblings().find('select[name="sameGroupSample"]').children('option[value="' + selectChildren.parents('.col-md-3').siblings().find('select[name="sameGroupSample"]').next().val() + '"]').prop('selected', true)
            }
        })
        $('input[name="allBox"]').change(function () {
            if ($(this).is(':checked')) {
                $("#waerhousingTbody").find('input:checkbox').prop('checked', true)
            } else {
                $("#waerhousingTbody").find('input:checkbox').prop('checked', false)
            }
        })
        $("#waerhousingTbody").find('input:checkbox').change(function () {
            if ($("#waerhousingTbody").find('input:checked').length == $("#waerhousingTbody").find('input:checkbox').length) {
                $('input[name="allBox"]').prop('checked', true)
            } else {
                $('input[name="allBox"]').prop('checked', false)
            }
        })

        $('select[name="SampleEntry"]').change(function () {
            if ($(this).val() == '08' || $(this).val() == '09' || $(this).val() == '10') {
                $(this).parents('.col-md-3').siblings().removeClass('hidden')
            } else {
                console.log($(this).parents('.col-md-3').siblings().find('select').find('option[value=""]'))
                $(this).parents('.col-md-3').siblings().find('select').val("")
                $(this).parents('.col-md-3').siblings().addClass('hidden')
            }
        })
        $('select[name="match"]').change(function () {
            $(this).parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').val('')
            if ($(this).val() == '配偶') {
                $(this).parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(1).removeAttr('class')
                $(this).parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(2).removeAttr('class')
                $(this).parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(3).removeAttr('class').addClass('hidden')
                $(this).parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(4).removeAttr('class').addClass('hidden')
            } else if ($(this).val() == '') {
                $(this).parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(1).removeAttr('class').addClass('hidden')
                $(this).parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(2).removeAttr('class').addClass('hidden')
                $(this).parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(3).removeAttr('class').addClass('hidden')
                $(this).parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(4).removeAttr('class').addClass('hidden')
            } else {
                $(this).parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(1).removeAttr('class').addClass('hidden')
                $(this).parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(2).removeAttr('class').addClass('hidden')
                $(this).parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(3).removeAttr('class')
                $(this).parents('.col-md-3').siblings().find('select[name="GroupSampleRole"]').children().eq(4).removeAttr('class')
            }
        })
//        $('select[name="sameGroupSample"]').change(function () {
//            console.log( $(this).children("option[value='"+$(this).val()+"']"))
//           console.log( $(this).children("option[value='"+$(this).val()+"']").index())
//        })
        $("button[name='compareBtn']", "#caseCompareTbody").on("click", function () {
            var caseId = $("input[name='caseId']", $(this).parent()).val();
            var url = "<%=path%>/center/3/enterDnaCompare.html?caseId="
                    + caseId + "&matchLimit=0";
            location.href = url;
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

        $("#compareBtn").on("click", function () {

            var sampleEntryTypeFlag = true;

            var checkCount = 0;
            var sampleInfoArr = new Array();
            var sampleInfo;
            $("tr", "#waerhousingTbody").each(function () {
                sampleInfo = {};
                var checkBox = $("input[name='box']", $(this)).is(":checked");
                if (checkBox) {
                    checkCount++;
                    sampleInfo.id = $("input[name='sampleId']", $(this)).val();
                    sampleInfo.sampleEntryType = $("select[name='SampleEntry']", $(this)).val();
                    sampleInfo.targetSampleRole = $("select[name='match']", $(this)).val();
                    sampleInfo.sameGroupSample = $("select[name='sameGroupSample']", $(this)).val();
                    sampleInfo.sameGroupSampleRole = $("select[name='GroupSampleRole']", $(this)).val();
                    sampleInfoArr.push(sampleInfo);
                    if ($("select[name='SampleEntry']", $(this)).val() == '') {
                        sampleEntryTypeFlag = false;
                    }
                }
            });

            if (!sampleEntryTypeFlag) {
                alert("请选择入库样本类型！");
                return false;
            }

            if (checkCount <= 0) {
                alert("请选择要入库比对的样本!");
                return false;
            }

            var params = {
                "sampleInfoList": sampleInfoArr
            };

            $.ajax({
                url: "<%=path%>/center/3/submitAndCompare.html",
                type: "post",
                data: JSON.stringify(params),
                contentType: "application/json; charset=utf-8",
                dataType: "json",
                success: function (data) {
                    if (data.success || data.success == true || data.success == "true") {
                        $("#main-content2").removeClass('hidden')
                        $("#main-content").addClass('hidden')
                        $("#main-content2").find('.col-md-12').children('p').html('<i class="fa fa-check-circle" aria-hidden="true"></i>已经成功入库').addClass('succes')

                        var matchResultList = data.matchResultList;
                        addMatchedResult(matchResultList);

                    } else {
                        $("#main-content2").removeClass('hidden')
                        $("#main-content").addClass('hidden')
                        $("#main-content2").find('.col-md-12').children('p').html('<i class="fa fa-times-circle" aria-hidden="true"></i>入库失败').addClass('error')

                    }
                },
                error: function (data) {
                    alert("提交失败!");
                }
            });


        });
        $(".backHidden").click(function () {
            $("#main-content2").addClass('hidden')
            $("#main-content").removeClass('hidden')
            $('#resultTbody').children().remove()
        })

        function addMatchedResult(matchResultList) {
            if (matchResultList.length > 0) {
                var htmlStr = "";
                for (var i = 0; i < matchResultList.length; i++) {
                    var matchResult = matchResultList[i];
                    var matchType = matchResult.matchedMode;
                    if (matchType == "01") {
                        htmlStr += "<tr><td>" + Number(i + 1) + "</td><td><p>" + matchResult.sample1No + "</p></td><td><p>" + matchResult.sample2No + "</p></td><td style='text-align: right!important;padding-right: 80px'>比中成功</td></tr>"
                    }
                    if (matchType == "02" || matchType == "03") {
                        htmlStr += "<tr><td>" + Number(i + 1) + "</td><td><p>" + matchResult.sample1No + "、" + matchResult.sample3No + "</p></td><td><p>" + matchResult.sample2No + "</p></td><td style='text-align: right!important;padding-right: 80px'>比中成功</td></tr>"
                    }
                    if (matchType == "04" || matchType == "05") {
                        htmlStr += "<tr><td>" + Number(i + 1) + "</td><td><p>" + matchResult.sample1No + "</p></td><td><p>" + matchResult.sample2No + "、" + matchResult.sample3No + "</p></td><td style='text-align: right!important;padding-right: 80px'>比中成功</td></tr>"
                    }
                }
                $("#resultTbody").append(htmlStr);
            }
        }

    });
</script>
<!-- END JS -->
</body>
</html>
