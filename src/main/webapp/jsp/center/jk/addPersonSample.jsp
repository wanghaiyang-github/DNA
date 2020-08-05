<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="../../common/include.jsp" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <%@ include file="../../common/link.jsp" %>
    <style type="text/css">
        #load {
            position: fixed;
            top: 0px;
            right: 0px;
            bottom: 0px;
            filter: alpha(opacity=60);
            background-color: #1c1c1c;
            z-index: 1002;
            left: 0px;
            display: none;
            opacity: 0.5;
            -moz-opacity: 0.5;
            padding-top: 300px;
            color: #a7a7a7
        }

        .has-error {
            position: absolute;
            right: -11px;
            bottom: 16%;
        }

        .btn_yellow {
            background: #faa328;
            color: #fff;
            border: 1px solid #faa328;
        }

        .btn_yellow_border {
            background: #fff;
            color: #faa328;
            border: 1px solid #faa328;
        }

        .btn_yellow:hover {
            color: #fff;
        }

        .btn_yellow_border:hover {
            color: #faa328;
        }
    </style>
</head>
<body>
<section class="wrapper">
    <div class="row">
        <div class="col-lg-12">
            <section class="panel">
                <header class="panel-heading">
                    <div style="padding-bottom: 20px;">
                        <button class="btn  btn_yellow" name="addPersonBtn" type="button"><i
                                class="fa fa-plus"></i> 添加建库人员信息
                        </button>
                    </div>
                    <div>
                        <span class="label label-primary"> 建库人员样本列表 </span>
                                       <span class="tools pull-right">
                                       <a href="javascript:;" class="fa fa-chevron-down"></a>
                                       </span>&nbsp;
                        <button class="btn btn-info" name="nextPersonBtn" type="button"><i
                                class="fa fa-check"></i> 保 存
                        </button>

                    </div>

                </header>
                <div class="panel-body">
                    <table class="table table-striped table-advance table-bordered table-hover">
                        <thead>
                        <tr>
                            <th>序号</th>
                            <th>DNA编号</th>
                            <th>姓名</th>
                            <th>性别</th>
                            <th>名族</th>
                            <th>身份证号</th>
                            <th>人员类型</th>
                            <th>采集人</th>
                            <th>操作</th>
                        </tr>
                        </thead>
                        <tbody id="criminalPersonListTbody">
                        <tr class="regedTr">
                        </tr>
                        </tbody>
                    </table>

                </div>
            </section>
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
                    <form class="form-horizontal tasi-form" id="criminalPerson_Form" method="post">
                        <div class="form-group">
                            <label class="control-label col-md-3" style="padding-left: 80px;">DNA编号<i
                                    class="fa fa-asterisk color_red"></i></label>
                            <div class="col-md-6">
                                <input name="personNo" type="text"
                                       class="form-control required" value=""/>
                            </div>
                            <div class="col-md-2 has-error hide">
                                <p class="help-block">必填项</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3" style="padding-left: 80px;">姓名<i
                                    class="fa fa-asterisk color_red"></i></label>
                            <div class="col-md-6">
                                <input name="personName" type="text"
                                       class="form-control required" value=""/>
                            </div>
                            <div class="col-md-2 has-error hide">
                                <p class="help-block">必填项</p>
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
                            <label class="control-label col-md-3" style="padding-left: 80px;">身份证号<i
                                    class="fa fa-asterisk color_red"></i></label>
                            <div class="col-md-6" style="width: 260px;">
                                <input type="text" id="personIdcardNo" name="personIdcardNo"
                                       class="form-control required" value="">
                            </div>
                            <div class="col-lg-4">
                                <input id="noIdcardRemarkCkb" name="noIdcardRemarkCkb" type="checkbox"/>
                                无身份证
                            </div>
                            <div class="col-md-10" style="padding-top: 5px;padding-left: 187px;padding-right: 74px;">
                                <input type="text" name="noIdcardRemark" id="noIdcardRemark"
                                       readonly="readonly" class="form-control" placeholder="无身份备注"/>
                            </div>
                            <div class="col-md-2 has-error hide">
                                <p class="help-block">必填项</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3" style="padding-left: 80px;">人员类型<i
                                    class="fa fa-asterisk color_red"></i></label>
                            <div class="col-md-6">
                                <select name="personType" class="form-control required">
                                    <c:forEach items="${personTypeList}" var="list"
                                               varStatus="s">
                                        <option value="${list.dictCode}"
                                        >${list.dictName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                            <div class="col-md-2 has-error hide">
                                <p class="help-block">必填项</p>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3" style="padding-left: 80px;">名族</label>
                            <div class="col-md-6">
                                <select name="personRace" class="form-control small">
                                    <c:forEach items="${personRaceList}" var="list"
                                               varStatus="s">
                                        <option value="${list.dictCode}"
                                        >${list.dictName}</option>
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-md-3" style="padding-left: 80px;">采集人</label>
                            <div class="col-md-6">
                                <input name="collectPerson" type="text"
                                       class="form-control required " value=""/>
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
                    <input type="hidden" name="personOperateType" value=""/>
                    <input type="hidden" name="personTableRownum" value=""/>
                    <button class="btn btn-success" type="button" id="savePersonBtn">确定</button>
                    <button data-dismiss="modal" class="btn btn-default" type="button">关闭
                    </button>
                </div>
            </div>
        </div>
    </div>

</section>
<!-- BEGIN JS -->
<%@ include file="../../common/script.jsp" %>
<script>

    $("button[name='addPersonBtn']").on('click', function () {
        $("#personModal").modal('show');
    });

    $("button[name='delSampleBtn']", "#entrustedListTbody").on("click", function () {
        if (!confirm("是否删除！"))
            return;

        var personSampleId = $("input[name='personSampleId']").val();
        var entrustmentId = $("input[name='entrustmentId']").val();
        location.href = "<%=path%>/center/caseinfo/delPersonSample.html?personSampleId=" + personSampleId + "&entrustmentId=" + entrustmentId;

    });

    $("#savePersonBtn").on("click", function () {
        var personIdcardNo = $("input[name='personIdcardNo']").val();
        if (personIdcardNo == "无") {
            savePersonRow()
        } else {
            $.ajax({
                url: "../checkIdcard.html?idcardNo=" + personIdcardNo,
                type: "get",
                async: false,
                cache: false,
                dataTyp: "json",
                success: function (data) {
                    if (!data.success && data.success != "true") {
                        alert("身份证输入不正确，请重新输入！")
                        $("input[name='personIdcardNo']").focus()
                    } else {
                        savePersonRow()
                    }
                }
            });
        }
    });

    $("button[name='nextPersonBtn']").on('click', function () {
        var criminalPersonInfoList = GetPerson();

        var params = {
            "criminalPersonInfoList": criminalPersonInfoList,
        };

        $.ajax({
            url: "<%=path%>/center/jk/insertPerson.html",
            type: "post",
            data: JSON.stringify(params),
            dataType: "json",
            contentType: "application/json;charset=utf-8",
            success: function (data) {
                if(data.success == true || data.success == "true") {
                    alert("添加成功");
                } else {
                    alert("添加失败!");
                }
            },
            error: function (data) {
                alert("添加失败!");
            }
        });
    });

    $("#noIdcardRemarkCkb").on("click", function () {
        if ($(this).is(":checked")) {
            $("#personIdcardNo").val("无");
            $("#personIdcardNo").prop("readonly", "readonly");

            $("#noIdcardRemark").prop("readonly", false);
            $("#noIdcardRemark").val("");
            $("#noIdcardRemark").focus();
        } else {
            $("#personIdcardNo").val("");
            $("#personIdcardNo").prop("readonly", false);
            $("#personIdcardNo").focus();

            $("#noIdcardRemark").prop("readonly", "readonly");
            $("#noIdcardRemark").val("");
        }
    });

    function generateSamplePersonIdx() {
        $("tr", "#criminalPersonListTbody").each(function (index, ele) {
            $("td:first", $(this)).text(index);
        });
    }

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

    function savePersonRow() {
        var errorCnt = 0;
        $("input.required", "#personModal").each(function () {
            if ($(this).val() == "") {
                $("div.has-error", $(this).parents("div.col-md-6")).removeClass("hide");
                errorCnt++;
            } else {
                $("div.has-error", $(this).parents("div.col-md-6")).addClass("hide");
            }
        });
        $("select.required", "#personModal").each(function () {

            if ($(this).find("option:selected").length == 0) {
                $("div.has-error", $(this).parents("div.col-md-6")).removeClass("hide");
                errorCnt++;
            } else {
                $("div.has-error", $(this).parents("div.col-md-6")).addClass("hide");
            }
        });

        if (errorCnt > 0) {
            return;
        }

        var personNo = $("input[name='personNo']", "#criminalPerson_Form").val();
        var personName = $("input[name='personName']", "#criminalPerson_Form").val();
        var gender = $("select[name='personGender']", "#criminalPerson_Form").find("option:selected").text();
        var genderVal = $("select[name='personGender']", "#criminalPerson_Form").find("option:selected").val();
        var idcardNo = $("input[name='personIdcardNo']", "#criminalPerson_Form").val();
        var noIdcardRemark = $("input[name='noIdcardRemark']", "#criminalPerson_Form").val();
        var race = $("select[name='personRace']", "#criminalPerson_Form").find("option:selected").text() + "族";
        var raceVal = $("select[name='personRace']", "#criminalPerson_Form").find("option:selected").val();
        var collectPerson = $("input[name='collectPerson']", "#criminalPerson_Form").val();
        var personType = $("select[name='personType']", "#criminalPerson_Form").find("option:selected").text();
        var personTypeVal = $("select[name='personType']", "#criminalPerson_Form").find("option:selected").val();
        var remark = $("input[name='remark']", "#criminalPerson_Form").val();

        var newRowHtml = "<td></td>";
        newRowHtml += "<td><input type='hidden' name='personNo' value='" + personNo + "'/>" + personNo + "</td>";
        newRowHtml += "<td><input type='hidden' name='personName' value='" + personName + "'/>" + personName + "</td>";
        newRowHtml += "<td><input type='hidden' name='gender' value='" + gender + "'/>" + gender + "</td>";
        newRowHtml += "<td><input type='hidden' name='race' value='" + race + "'/>" + race + "</td>";
        newRowHtml += "<td><input type='hidden' name='idcardNo' value='" + idcardNo + "'/>" + idcardNo + "</td>";
        newRowHtml += "<td><input type='hidden' name='personType' value='" + personType + "'/>" + personType + "</td>";
        newRowHtml += "<td><input type='hidden' name='collectPerson' value='" + collectPerson + "'/>" + collectPerson + "</td>";
        newRowHtml += "<td style='display: none'><input type='hidden' name='remark' value='" + remark + "'/>" + remark + "</td>";
        newRowHtml += "<td style='display: none'><input type='hidden' name='noIdcardRemark' value='" + noIdcardRemark + "'/>" + noIdcardRemark + "</td>";
        newRowHtml += "<td style='display: none'><input type='hidden' name='genderVal' value='" + genderVal + "'/>" + genderVal + "</td>";
        newRowHtml += "<td style='display: none'><input type='hidden' name='raceVal' value='" + raceVal + "'/>" + raceVal + "</td>";
        newRowHtml += "<td style='display: none'><input type='hidden' name='personTypeVal' value='" + personTypeVal + "'/>" + personTypeVal + "</td>";

        newRowHtml += "<td><button name='editPerBtn' class='btn btn-primary btn-sm'><i class='fa fa-pencil'></i> 修改</button>&nbsp;<button name='delPerBtn' class='btn btn-danger btn-sm'><i class='fa fa-trash-o'></i> 删除</button></td>";

        var operateType = $("input[name='personOperateType']", "#personModal").val();

        if ("edit" == operateType) {
            var trIdx = $("input[name='personTableRownum']", "#personModal").val();
            $("tr:eq(" + trIdx + ")", "#criminalPersonListTbody").html(newRowHtml);
        } else {
            $("#criminalPersonListTbody").append("<tr>" + newRowHtml + "</tr>");
        }
        $("#personModal").modal('hide');

        generateSamplePersonIdx();

        $("button[name='editPerBtn']", "#criminalPersonListTbody").on("click", function () {
            EditPersonRow(this);
        });

        $("button[name='delPerBtn']", "#criminalPersonListTbody").on("click", function () {
            DelPersonRow(this);
        });

        $("input[name='personNo']",'#personModal').val("");
        $("input[name='personName']",'#personModal').val("");
        $("input[name='personIdcardNo']",'#personModal').val("");
        $("input[name='collectPerson']",'#personModal').val("");
        $("input[name='remark']",'#personModal').val("");
        $("input[name='noIdcardRemarkCkb']",'#personModal').removeAttr('checked');
        $("input[name='personIdcardNo']",'#personModal').removeAttr('readonly');
        $("input[name='noIdcardRemark']",'#personModal').val("");
        $("#noIdcardRemark").prop("readonly", "readonly");

    }

    function DelPersonRow(obj) {
        $(obj).parent().parent().remove();
    }

    function EditPersonRow(obj) {
        var $curTR = $(obj).parents("tr");
        var person = {};
        person.personNo = $("input[name='personNo']", $curTR).val();
        person.personName = $("input[name='personName']", $curTR).val();
        person.gender = $("input[name='gender']", $curTR).val();
        person.genderVal = $("input[name='genderVal']", $curTR).val();
        person.raceVal = $("input[name='raceVal']", $curTR).val();
        person.race = $("input[name='race']", $curTR).val();
        person.idcardNo = $("input[name='idcardNo']", $curTR).val();
        person.noIdcardRemark = $("input[name='noIdcardRemark']", $curTR).val();
        person.personType = $("input[name='personType']", $curTR).val();
        person.personTypeVal = $("input[name='personTypeVal']", $curTR).val();
        person.collectPerson = $("input[name='collectPerson']", $curTR).val();
        person.remark = $("input[name='remark']", $curTR).val();

        var trIdx = $curTR.index();
        newPersonRow(person, "edit", trIdx);
    }


    function newPersonRow(person, operateType, rownum) {
        if (person.personTypeVal == "") {
            $("select[name='personType']", "#personModal").prop('selectedIndex', 0);
        } else {
            $("select[name='personType']", "#personModal").val(person.personTypeVal);
        }
        $("input[name='personName']", "#personModal").val(person.personName);

        if (person.genderVal == "") {
            $("select[name='personGender']", "#personModal").prop('selectedIndex', 0);
        } else {
            $("select[name='personGender']", "#personModal").val(person.genderVal);
        }
        if (person.raceVal == "") {
            $("select[name='personRace']", "#personModal").prop('selectedIndex', 0);
        } else {
            $("select[name='personRace']", "#personModal").val(person.raceVal);
        }
        $("input[name='personIdcardNo']", "#personModal").val(person.personIdcardNo);
        if ((person.idcardNo == "" || person.idcardNo == "无")
                && person.noIdcardRemark != "") {
            $("input[name='personIdcardNo']", "#personModal").val("无");
            $("input[name='personIdcardNo']", "#personModal").prop("readonly", "readonly");
            $("#noIdcardRemarkCkb", "#personModal").prop("checked", "checked");
            $("input[name='noIdcardRemark']", "#personModal").val(person.noIdcardRemark);
            $("input[name='noIdcardRemark']", "#personModal").prop("readonly", false);
        } else {
            $("input[name='personIdcardNo']", "#personModal").val(person.idcardNo);
            $("input[name='personIdcardNo']", "#personModal").prop("readonly", false);
            $("#noIdcardRemarkCkb", "#personModal").prop("checked", false);
            $("input[name='noIdcardRemark']", "#personModal").val("");
            $("input[name='noIdcardRemark']", "#personModal").prop("readonly", "readonly");
        }
        $("input[name='personNo']", "#personModal").val(person.personNo);
        $("input[name='collectPerson']", "#personModal").val(person.collectPerson);
        $("input[name='remark']", "#personModal").val(person.remark);
        $("input[name='personOperateType']", "#personModal").val(operateType);
        $("input[name='personTableRownum']", "#personModal").val(rownum);

        $("#personModal").modal('show');
    }

    function GetPerson() {
        var personArr = new Array();

        var $personTR = $("tr", "#criminalPersonListTbody").not(".regedTr");
        var personCnt = $personTR.length;
        var person;
        for (var i = 0; i < personCnt; i++) {
            person = {};
            person.personNo = $("input[name='personNo']", $personTR.get(i)).val();
            person.personName = $("input[name='personName']", $personTR.get(i)).val();
            person.gender = $("input[name='gender']", $personTR.get(i)).val();
            person.race = $("input[name='race']", $personTR.get(i)).val();
            person.idcardNo = $("input[name='idcardNo']", $personTR.get(i)).val();
            person.personType = $("input[name='personType']", $personTR.get(i)).val();
            person.collectPerson = $("input[name='collectPerson']", $personTR.get(i)).val();
            person.remark = $("input[name='remark']", $personTR.get(i)).val();

            personArr.push(person);
        }

        return personArr;
    }


</script>
<!-- END JS -->
</body>
</html>


