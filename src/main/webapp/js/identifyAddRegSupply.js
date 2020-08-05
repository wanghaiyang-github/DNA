/**
 * Created by Administrator on 2017/1/6.
 */
$(function () {

    function validateForm() {

    }

    function GetPerson() {
        var personArr = new Array();

        var $personTR = $("tr", "#personInfoTable");
        var personCnt = $personTR.length;
        var person;
        for (var i = 0; i < personCnt; i++) {
            person = {};
            person.id = $("input[name='personId']", $personTR.get(i)).val();
            person.personType = $("input[name='personType']", $personTR.get(i)).val();
            person.personName = $("input[name='personName']", $personTR.get(i)).val();
            person.personGender = $("input[name='personGender']", $personTR.get(i)).val();
            person.personIdcardNo = $("input[name='personIdcardNo']", $personTR.get(i)).val();
            person.noIdcardRemark = $("input[name='noIdcardRemark']", $personTR.get(i)).val();
            person.relativeIdentify = $("input[name='relativeIdentify']", $personTR.get(i)).val();

            personArr.push(person);
        }

        return personArr;
    }

    function checkInputValidation() {
        //caseInfo_form validate
        var caseErrCnt = 0;
        $(".required", "#caseinfo_form").each(function () {
            if ($(this).val() == "") {
                $("div.has-error", $(this).parents("div.form-group")).removeClass("hide");
                caseErrCnt++;
            } else {
                $("div.has-error", $(this).parents("div.form-group")).addClass("hide");
            }
        });

        var consignErrCnt = 0;
        $(".required", "#consignment_form").each(function () {
            if ($(this).val() == "") {
                $("div.has-error", $(this).parents("div.form-group")).removeClass("hide");
                consignErrCnt++;
            } else {
                $("div.has-error", $(this).parents("div.form-group")).addClass("hide");
            }
        });

        if (caseErrCnt > 0 || consignErrCnt > 0) {
            alert("请补全案件和委托信息的必填项！");
            return false;
        }

        var sampleTRLen = $("tr", "#samplePersonTable").not(".regedTr").length + $("tr", "#sampleEvidenceTable").not(".regedTr").length;
        if (sampleTRLen == 0) {
            alert("请添加案件检材信息！");
            return false;
        }

        return true;
    }


// person start
    function newPersonRow(person, operateType, rownum) {
        if (person.personType == "") {
            $("select[name='personType']", "#personModal").prop('selectedIndex', 0);
        } else {
            $("select[name='personType']", "#personModal").val(person.personType);
        }

        $("input[name='personName']", "#personModal").val(person.personName);

        if (person.personGender == "") {
            $("select[name='personGender']", "#personModal").prop('selectedIndex', 0);
        } else {
            $("select[name='personGender']", "#personModal").val(person.personGender);
        }

        $("input[name='personIdcardNo']", "#personModal").val(person.personIdcardNo);
        if ((person.personIdcardNo == "" || person.personIdcardNo == "无")
            && person.noIdcardRemark != "") {
            $("input[name='personIdcardNo']", "#personModal").val("无");
            $("input[name='personIdcardNo']", "#personModal").prop("readonly", "readonly");

            $("#noIdcardRemarkCkb", "#personModal").prop("checked", "checked");
            $("input[name='noIdcardRemark']", "#personModal").val(person.noIdcardRemark);
            $("input[name='noIdcardRemark']", "#personModal").prop("readonly", false);
        } else {
            $("input[name='personIdcardNo']", "#personModal").val(person.personIdcardNo);
            $("input[name='personIdcardNo']", "#personModal").prop("readonly", false);

            $("#noIdcardRemarkCkb", "#personModal").prop("checked", false);
            $("input[name='noIdcardRemark']", "#personModal").val("");
            $("input[name='noIdcardRemark']", "#personModal").prop("readonly", "readonly");
        }

        if (person.relativeIdentify == "") {
            $("select[name='relativeIdentify']", "#personModal").val("08");
        } else {
            $("select[name='relativeIdentify']", "#personModal").val(person.relativeIdentify);
        }

        $("input[name='personId']", "#personModal").val(person.id);
        $("input[name='personOperateType']", "#personModal").val(operateType);
        $("input[name='personTableRownum']", "#personModal").val(rownum);

        $("#personModal").modal();
    }

    function AddPersonRow() {
        var person = {};
        person.personType = "";
        person.personName = "";
        person.personGender = "";
        person.personIdcardNo = "";
        person.noIdcardRemark = "";
        person.relativeIdentify = "";
        person.id = "";

        newPersonRow(person, "add", 0);
    }

    function EditPersonRow(obj) {
        var $curTR = $(obj).parents("tr");
        var person = {};
        person.personType = $("input[name='personType']", $curTR).val();
        person.personName = $("input[name='personName']", $curTR).val();
        person.personGender = $("input[name='personGender']", $curTR).val();
        person.personIdcardNo = $("input[name='personIdcardNo']", $curTR).val();
        person.noIdcardRemark = $("input[name='noIdcardRemark']", $curTR).val();
        person.relativeIdentify = $("input[name='relativeIdentify']", $curTR).val();
        person.id = $("input[name='personId']", $curTR).val();

        var trIdx = $curTR.index();
        newPersonRow(person, "edit", trIdx);

    }

    function DelPersonRow(obj) {
        var personId = $("input[name='personId']", $(obj).parent()).val();
        if (personId == null || personId == "" || personId == 0 || personId == "0") {
            $(obj).parents("tr").remove();
            return;
        }

        $.ajax({
            url: "../caseinfo/delPersonById.html?personId=" + personId,
            type: "get",
            dataType: "json",
            success: function (data) {
                if (data.success || data.success == true || data.success == "true") {
                    $(obj).parents("tr").remove();
                }
            }
        });
    }

    function SavePersonRow() {
        var errorCnt = 0;
        $(".required", "#personModal").each(function () {
            if ($(this).val() == "") {
                $("div.has-error", $(this).parents("div.form-group")).removeClass("hide");
                errorCnt++;
            } else {
                $("div.has-error", $(this).parents("div.form-group")).addClass("hide");
            }
        });

        var personTypeVal = $("select[name='personType']", "#personModal").find("option:selected").val();
        if (personTypeVal == "" || $.trim(personTypeVal) == "") {
            $("div.has-error", $("select[name='personType']", "#personModal").parents("div.form-group")).removeClass("hide");
            errorCnt++;
        } else {
            $("div.has-error", $("select[name='personType']", "#personModal").parents("div.form-group")).addClass("hide");
        }

        var relativeIdentifyVal = $("select[name='relativeIdentify']", "#personModal").find("option:selected").val();
        if (relativeIdentifyVal == "" || $.trim(relativeIdentifyVal) == "") {
            $("div.has-error", $("select[name='relativeIdentify']", "#personModal").parents("div.form-group")).removeClass("hide");
            errorCnt++;
        } else {
            $("div.has-error", $("select[name='relativeIdentify']", "#personModal").parents("div.form-group")).addClass("hide");
        }

        if (errorCnt > 0) {
            return;
        }

        var noIdcardRemarkVal = "";
        if ($("#noIdcardRemarkCkb", "#personModal").is(":checked")) {
            noIdcardRemarkVal = $("input[name='noIdcardRemark']", "#personModal").val();

            if (noIdcardRemarkVal == "" || $.trim(noIdcardRemarkVal) == "") {
                $("div.has-error", $("input[name='personIdcardNo']", "#personModal").parents("div.form-group")).removeClass("hide");
                return;
            } else {
                $("div.has-error", $("input[name='personIdcardNo']", "#personModal").parents("div.form-group")).addClass("hide");
            }
        } else {
            var idcardNo = $("input[name='personIdcardNo']", "#personModal").val();
            var isValid = true;
            var checkMsg = "";
            $.ajax({
                url: "../checkIdcard.html?idcardNo=" + idcardNo,
                type: "get",
                async: false,
                cache: false,
                dataTyp: "json",
                success: function (data) {
                    if (!data.success && data.success != "true") {
                        isValid = false;
                        checkMsg = data.errorMsg;
                    }
                }
            });

            if (!isValid) {
                $("div.has-error", $("input[name='personIdcardNo']", "#personModal").parents("div.form-group")).removeClass("hide");
                $("div.has-error", $("input[name='personIdcardNo']", "#personModal").parents("div.form-group")).html("<p class='help-block'>" + checkMsg + "</p>");
                return;
            } else {
                $("div.has-error", $("input[name='personIdcardNo']", "#personModal").parents("div.form-group")).addClass("hide");
                $("div.has-error", $("input[name='personIdcardNo']", "#personModal").parents("div.form-group")).html("<p class='help-block'>必填项</p>");
            }
        }

        var personId = $("input[name='personId']", "#personModal").val();
        var personTypeCode = $("select[name='personType']", "#personModal").find("option:selected").val();
        var personTypeName = $("select[name='personType']", "#personModal").find("option:selected").text();
        var personName = $("input[name='personName']", "#personModal").val();
        var personGenderCode = $("select[name='personGender']", "#personModal").find("option:selected").val();
        var personGenderName = $("select[name='personGender']", "#personModal").find("option:selected").text();
        var personIdcardNo = $("input[name='personIdcardNo']", "#personModal").val();
        var relativeIdentifyCode = $("select[name='relativeIdentify']", "#personModal").find("option:selected").val();
        var relativeIdentifyName = $("select[name='relativeIdentify']", "#personModal").find("option:selected").text();

        var personArr = new Array();

        var person = {};
        person.caseId = $("#caseId", "#caseinfo_form").val();
        person.consignmentId = $("#consignmentId", "#consignment_form").val();
        person.id = personId;
        person.personType = personTypeCode;
        person.personName = personName;
        person.personGender = personGenderCode;
        person.personIdcardNo = personIdcardNo;
        person.noIdcardRemark = noIdcardRemarkVal;
        person.relativeIdentify = relativeIdentifyCode;

        personArr.push(person);

        var personInfoList = personArr;
        var newConsignmentId = $("#newConsignmentId").val();

        var params = {
            "personInfoList": personInfoList
        };

        $.ajax({
            url: "../reg/submitPersonSupply.html?newConsignmentId=" + newConsignmentId,
            type: "post",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(params),
            dataType: "json",
            success: function (data) {

                $("#newConsignmentId").val(data.newConsignmentId);

                var newRowHtml = "<td></td>";
                newRowHtml += "<td><input type='hidden' name='personType' value='" + personTypeCode + "'/>" + personTypeName + "</td>";
                newRowHtml += "<td><input type='hidden' name='personName' value='" + personName + "'/>" + personName + "</td>";
                newRowHtml += "<td><input type='hidden' name='personGender' value='" + personGenderCode + "'/>" + personGenderName + "</td>";

                newRowHtml += "<td><input type='hidden' name='personIdcardNo' value='" + personIdcardNo + "'/><input type='hidden' name='noIdcardRemark' value='" + noIdcardRemarkVal + "'/>" + personIdcardNo;
                if ($("#noIdcardRemarkCkb", "#personModal").is(":checked")) {
                    newRowHtml += "（" + noIdcardRemarkVal + "）";
                }
                newRowHtml += "</td>";

                newRowHtml += "<td><input type='hidden' name='relativeIdentify' value='" + relativeIdentifyCode + "'/>" + relativeIdentifyName + "</td>";
                newRowHtml += "<td><input type='hidden' name='personId' value='" + data.personId + "'/><button name='editPerBtn' class='btn btn-primary btn-xs'><i class='fa fa-pencil'></i> 修改</button>  <button name='delPerBtn' class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i> 删除</button></td>";


                var operateType = $("input[name='personOperateType']", "#personModal").val();
                if ("add" == operateType) {
                    $("#personInfoTable").append("<tr>" + newRowHtml + "</tr>");
                } else {
                    var trIdx = $("input[name='personTableRownum']", "#personModal").val();
                    $("tr:eq(" + trIdx + ")", "#personInfoTable").html(newRowHtml);
                }

                generatePersonIdx();

                $("button[name='editPerBtn']", "#personInfoTable").on("click", function () {
                    EditPersonRow(this);
                });
                $("button[name='delPerBtn']", "#personInfoTable").on("click", function () {
                    DelPersonRow(this);
                });

                $("#personModal").modal('hide');

            },
            error: function (e) {
                alert(e);
            }
        });

    }

// person end

// sample start
    function newSampleRow(sample, operateType, rownum) {
        $("div.has-error", "#sampleModal").addClass("hide");

        $("input[name='sampleId']", "#sampleModal").val(sample.id);
        $("select[name='sampleType']", "#sampleModal").val(sample.sampleType);
        $("input[name='sampleName']", "#sampleModal").val(sample.sampleName);
        $("input[name='extractDatetime']", "#sampleModal").val(sample.extractDatetime);
        $("input[name='extractPerson']", "#sampleModal").val(sample.extractPerson);
        $("input[name='sampleDesc']", "#sampleModal").val(sample.sampleDesc);
        if (sample.samplePacking == "" || $.trim(sample.samplePacking) == "") {
            $("input[name='samplePacking']", "#sampleModal").val("物证袋");
        } else {
            $("input[name='samplePacking']", "#sampleModal").val(sample.samplePacking);
        }

        if (sample.sampleProperties != "") {
            $("select[name='sampleProperties']", "#sampleModal").val(sample.sampleProperties);

            if (sample.sampleProperties == "9999") {
                $("input[name='otherPropertiesDesc']", "#sampleModal").val(sample.otherPropertiesDesc);
                $("input[name='otherPropertiesDesc']", "#sampleModal").removeClass("hide");
            } else {
                $("input[name='otherPropertiesDesc']", "#sampleModal").val("");
                $("input[name='otherPropertiesDesc']", "#sampleModal").addClass("hide");
            }
        } else {
            $("select[name='sampleProperties']", "#sampleModal").prop('selectedIndex', 0);

            $("input[name='otherPropertiesDesc']", "#sampleModal").val("");
            $("input[name='otherPropertiesDesc']", "#sampleModal").addClass("hide");
        }

        $("select[name='refPerson']", "#sampleModal").empty();
        $("select[name='refPerson']", "#sampleModal").append("<option value=''>请选择</option>")
        $("input[name='personName']", "#personInfoTable").each(function () {
            var refPersonName = $(this).val();
            if (operateType == "edit" && refPersonName == sample.refPersonName) {
                $("select[name='refPerson']", "#sampleModal").append("<option value='" + refPersonName + "' selected>" + refPersonName + "</option>");
            } else {
                $("select[name='refPerson']", "#sampleModal").append("<option value='" + refPersonName + "'>" + refPersonName + "</option>");
            }
        });

        if (sample.sampleFlag == "" || sample.sampleFlag == "0") {
            //$("#sampleFlagPerson", "#sampleModal").removeAttr("checked");
            //$("#sampleFlagEvidence", "#sampleModal").prop("checked", true);
            $("input[name='sampleFlag']", "#sampleModal").val(sample.sampleFlag);
            $("#divRefPerson").hide();
        } else {
            /*$("#sampleFlagEvidence", "#sampleModal").removeAttr("checked");
             $("#sampleFlagPerson", "#sampleModal").prop("checked", true);*/
            $("input[name='sampleFlag']", "#sampleModal").val(sample.sampleFlag);
            $("#divRefPerson").show();
        }

        $("input[name='sampleOperateType']", "#sampleModal").val(operateType);
        $("input[name='sampleTableRownum']", "#sampleModal").val(rownum);

        $("#sampleModal").modal();
    }

    function AddSampleRow(obj) {
        var sample = {};
        sample.id = "";
        sample.sampleType = "";
        sample.sampleName = "";
        sample.extractDatetime = "";
        sample.extractPerson = "";
        sample.sampleDesc = "";
        sample.samplePacking = "";
        sample.sampleProperties = "";
        sample.otherPropertiesDesc = "";
        sample.refPersonId = "";
        sample.refPersonName = "";
        sample.sampleFlag = obj;
        newSampleRow(sample, "add", 0);
    }

    function EditSampleRow(obj) {
        var $curTR = $(obj).parents("tr");
        var sample = {};
        sample.id = $("input[name='sampleId']", $curTR).val();
        sample.sampleType = $("input[name='sampleType']", $curTR).val();
        sample.sampleName = $("input[name='sampleName']", $curTR).val();
        sample.extractDatetime = $("input[name='extractDatetime']", $curTR).val();
        sample.extractPerson = $("input[name='extractPerson']", $curTR).val();
        sample.sampleDesc = $("input[name='sampleDesc']", $curTR).val();
        sample.samplePacking = $("input[name='samplePacking']", $curTR).val();
        sample.sampleProperties = $("input[name='sampleProperties']", $curTR).val();
        sample.otherPropertiesDesc = $("input[name='otherPropertiesDesc']", $curTR).val();
        sample.refPersonId = $("input[name='refPersonId']", $curTR).val();
        sample.refPersonName = $("input[name='refPersonName']", $curTR).val();
        sample.sampleFlag = $("input[name='sampleFlag']", $curTR).val();

        var trIdx = $curTR.index();
        newSampleRow(sample, "edit", trIdx);
    }

    function EditSampleRowRemove(obj) {
        var $curTR = $(obj).parents("tr");
        var sample = {};
        sample.id = $("input[name='sampleId']", $curTR).val();
        sample.sampleType = $("input[name='sampleType']", $curTR).val();
        sample.sampleName = $("input[name='sampleName']", $curTR).val();
        sample.extractDatetime = $("input[name='extractDatetime']", $curTR).val();
        sample.extractPerson = $("input[name='extractPerson']", $curTR).val();
        sample.sampleDesc = $("input[name='sampleDesc']", $curTR).val();
        sample.samplePacking = $("input[name='samplePacking']", $curTR).val();
        sample.sampleProperties = $("input[name='sampleProperties']", $curTR).val();
        sample.otherPropertiesDesc = $("input[name='otherPropertiesDesc']", $curTR).val();
        sample.refPersonId = $("input[name='refPersonId']", $curTR).val();
        sample.refPersonName = $("input[name='refPersonName']", $curTR).val();
        sample.sampleFlag = $("input[name='sampleFlag']", $curTR).val();

        var trIdx = $curTR.index();
        newSampleRow(sample, "edit", trIdx);
        $curTR.remove();
    }

    function DelSampleRow(obj) {
        var sampleId = $("input[name='sampleId']", $(obj).parent()).val();
        if (sampleId == null || sampleId == "" || sampleId == 0 || sampleId == "0") {
            $(obj).parents("tr").remove();
            return;
        }

        $.ajax({
            url: "../caseinfo/delSampleById.html?sampleId=" + sampleId,
            type: "get",
            dataType: "json",
            success: function (data) {
                if (data.success || data.success == true || data.success == "true") {
                    $(obj).parents("tr").remove();
                }
            }
        });
    }

    function SaveSampleRow() {
        var errorCnt = 0;

        $("input.required", "#sampleModal").each(function () {
            if ($(this).val() == "") {
                $("div.has-error", $(this).parents("div.form-group")).removeClass("hide");
                errorCnt++;
            } else {
                $("div.has-error", $(this).parents("div.form-group")).addClass("hide");
            }
        });

        var sampleTypeVal = $("select[name='sampleType']", "#sampleModal").find("option:selected").val();
        if (sampleTypeVal == "") {
            $("div.has-error", $("select[name='sampleType']", "#sampleModal").parents("div.form-group")).removeClass("hide");
            errorCnt++;
        } else {
            $("div.has-error", $("select[name='sampleType']", "#sampleModal").parents("div.form-group")).addClass("hide");
        }

        var sampleFlagVal = $("input[name='sampleFlag']", "#sampleModal").val();

        if (sampleFlagVal == "1") {
            var refPeresonVal = $("select[name='refPerson']", "#sampleModal").find("option:selected").val();
            if (refPeresonVal == "" || $.trim(refPeresonVal) == "") {
                $("div.has-error", $("select[name='refPerson']", "#sampleModal").parents("div.form-group")).removeClass("hide");
                errorCnt++;
            } else {
                $("div.has-error", $("select[name='refPerson']", "#sampleModal").parents("div.form-group")).addClass("hide");
            }
        }

        var samplePropertiesVal = $("select[name='sampleProperties']", "#sampleModal").find("option:selected").val();
        var otherPropertiesDescVal = $("input[name='otherPropertiesDesc']", "#sampleModal").val();
        if (samplePropertiesVal == "9999") {
            if (otherPropertiesDescVal == "" || $.trim(otherPropertiesDescVal) == "") {
                $("div.has-error", $("input[name='otherPropertiesDesc']", "#sampleModal").parents("div.form-group")).removeClass("hide");
            } else {
                $("div.has-error", $("input[name='otherPropertiesDesc']", "#sampleModal").parents("div.form-group")).addClass("hide");
            }
        }

        if (errorCnt > 0) {
            return;
        }

        var sampleId = $("input[name='sampleId']", "#sampleModal").val();
        var sampleTypeCode = $("select[name='sampleType']", "#sampleModal").find("option:selected").val();
        var sampleTypeName = $("select[name='sampleType']", "#sampleModal").find("option:selected").text();
        var sampleName = $("input[name='sampleName']", "#sampleModal").val();
        var extractDatetime = $("input[name='extractDatetime']", "#sampleModal").val();
        var extractPerson = $("input[name='extractPerson']", "#sampleModal").val();
        var sampleDesc = $("input[name='sampleDesc']", "#sampleModal").val();
        var samplePacking = $("input[name='samplePacking']", "#sampleModal").val();
        var samplePropertiesName = $("select[name='sampleProperties']", "#sampleModal").find("option:selected").text();
        //var refPersonCode = $("select[name='refPerson']", "#sampleModal").find("option:selected").val();
        var refPersonName = $("select[name='refPerson']", "#sampleModal").find("option:selected").text();

        var sampleArr = new Array();

        var sample = {};
        sample.caseId = $("#caseId", "#caseinfo_form").val();
        sample.consignmentId = $("#consignmentId", "#consignment_form").val();
        sample.id = sampleId;
        sample.sampleName = $.trim(sampleName);
        sample.sampleType = $.trim(sampleTypeCode);
        sample.extractDatetime = extractDatetime;
        sample.extractPerson = extractPerson;
        sample.sampleDesc = $.trim(sampleDesc);
        sample.samplePacking = $.trim(samplePacking);
        sample.sampleProperties = samplePropertiesVal;
        sample.otherPropertiesDesc = $.trim(otherPropertiesDescVal);

        if (sampleFlagVal == 1) {
            sample.refPersonName = refPersonName;
            sample.sampleFlag = "1";
        } else {
            sample.sampleFlag = "0";
        }

        sampleArr.push(sample);

        var personInfoList = GetPerson();
        var sampleInfoList = sampleArr;
        var newConsignmentId = $("#newConsignmentId").val();

        var params = {
            "personInfoList": personInfoList,
            "sampleInfoList": sampleInfoList
        };

        $.ajax({
            url: "../reg/submitSampleSupply.html?newConsignmentId=" + newConsignmentId,
            type: "post",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(params),
            dataType: "json",
            success: function (data) {

                $("#newConsignmentId").val(data.newConsignmentId);

                var newRowHtml = "<td></td>";
                newRowHtml += "<td><input type='hidden' name='evidenceNo' value=''/></td>";
                newRowHtml += "<td><input type='hidden' name='sampleName' value='" + $.trim(sampleName) + "'/>" + sampleName + "</td>";
                newRowHtml += "<td><input type='hidden' name='sampleType' value='" + sampleTypeCode + "'/>" + sampleTypeName + "</td>";
                newRowHtml += "<td><input type='hidden' name='sampleDesc' value='" + sampleDesc + "'/>" + sampleDesc + "</td>";
                newRowHtml += "<td><input type='hidden' name='samplePacking' value='" + samplePacking + "'/>" + samplePacking + "</td>";
                newRowHtml += "<td><input type='hidden' name='sampleProperties' value='" + samplePropertiesVal + "'/><input type='hidden' name='otherPropertiesDesc' value='" + $.trim(otherPropertiesDescVal) + "'/>";
                if (samplePropertiesVal == "9999") {
                    newRowHtml += otherPropertiesDescVal + "</td>";
                } else {
                    newRowHtml += samplePropertiesName + "</td>";
                }

                if (sampleFlagVal == 1) {
                    newRowHtml += "<td><input type='hidden' name='refPersonName' value='" + refPersonName + "'/>" + refPersonName + "</td>";
                }
                newRowHtml += "<td><input type='hidden' name='extractDatetime' value='" + extractDatetime + "'/>" + extractDatetime + "</td>";
                newRowHtml += "<td><input type='hidden' name='extractPerson' value='" + extractPerson + "'/>" + extractPerson + "</td>";
                newRowHtml += "<td><input type='hidden' name='sampleId' value='" + data.sampleId + "'/><input type='hidden' name='sampleFlag' value='" + sampleFlagVal + "'/><button name='editSampleBtn' class='btn btn-primary btn-xs'><i class='fa fa-pencil'></i> 修改</button>  <button name='delSampleBtn' class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i> 删除</button></td>";

                var operateType = $("input[name='sampleOperateType']", "#sampleModal").val();
                if ("add" == operateType) {
                    if (sampleFlagVal == 1) {
                        $("#samplePersonTable").append("<tr>" + newRowHtml + "</tr>");
                        generateSamplePersonIdx();
                    } else {
                        $("#sampleEvidenceTable").append("<tr>" + newRowHtml + "</tr>");
                        generateSampleEvidenceIdx();
                    }
                } else {

                    if (data.oldSampleFlag == sampleFlagVal) {
                        var trIdx = $("input[name='sampleTableRownum']", "#sampleModal").val();
                        if (sampleFlagVal == 1) {
                            $("tr:eq(" + trIdx + ")", "#samplePersonTable").html(newRowHtml);
                            generateSamplePersonIdx();
                        } else {
                            $("tr:eq(" + trIdx + ")", "#sampleEvidenceTable").html(newRowHtml);
                            generateSampleEvidenceIdx();
                        }
                    } else {
                        if (sampleFlagVal == 1) {
                            $("#samplePersonTable").append("<tr>" + newRowHtml + "</tr>");
                            generateSamplePersonIdx();
                        } else {
                            $("#sampleEvidenceTable").append("<tr>" + newRowHtml + "</tr>");
                            generateSampleEvidenceIdx();
                        }
                    }
                }

                $("button[name='editSampleBtn']", "#samplePersonTable").on("click", function () {

                    EditSampleRow(this);

                });
                $("button[name='delSampleBtn']", "#samplePersonTable").on("click", function () {
                    DelSampleRow(this);
                });

                $("button[name='editSampleBtn']", "#sampleEvidenceTable").on("click", function () {

                    EditSampleRow(this);

                });
                $("button[name='delSampleBtn']", "#sampleEvidenceTable").on("click", function () {
                    DelSampleRow(this);
                });

                $("#sampleModal").modal('hide');
            },
            error: function (e) {
                alert(e);
            }
        });
    }

// person end


    $("#submitBtn").on("click", function () {
        if (!checkInputValidation()) {
            return;
        }
        var newConsignmentId = $("#newConsignmentId").val();

        var sampleArr = new Array();
        var $sampleEvidenceTR = $("tr", "#sampleEvidenceTable").not(".regedTr");
        var sampleEvidenceCnt = $sampleEvidenceTR.length;
        var sample;
        for (var i = 0; i < sampleEvidenceCnt; i++) {
            sample = {};
            sample.caseId = $("#caseId", "#caseinfo_form").val();
            sample.consignmentId = $("#consignmentId", "#consignment_form").val();
            sample.evidenceNo = $("input[name='evidenceNo']", $sampleEvidenceTR.get(i)).val();
            sample.sampleName = $("input[name='sampleName']", $sampleEvidenceTR.get(i)).val();
            sample.extractDatetime = $("input[name='extractDatetime']", $sampleEvidenceTR.get(i)).val();
            sample.extractPerson = $("input[name='extractPerson']", $sampleEvidenceTR.get(i)).val();
            sample.sampleDesc = $("input[name='sampleDesc']", $sampleEvidenceTR.get(i)).val();
            sample.sampleFlag = $("input[name='sampleFlag']", $sampleEvidenceTR.get(i)).val();
            sample.otherPropertiesDesc = $("input[name='otherPropertiesDesc']", $sampleEvidenceTR.get(i)).val();
            sample.sampleType = $("input[name='sampleType']", $sampleEvidenceTR.get(i)).val();
            sample.sampleTypeName = $("input[name='sampleTypeName']", $sampleEvidenceTR.get(i)).val();
            sample.samplePacking = $("input[name='samplePacking']", $sampleEvidenceTR.get(i)).val();
            sample.sampleProperties = $("input[name='sampleProperties']", $sampleEvidenceTR.get(i)).val();
            sample.refPersonId = $("input[name='refPersonId']", $sampleEvidenceTR.get(i)).val();
            sample.refPersonName = $("input[name='refPersonName']", $sampleEvidenceTR.get(i)).val();

            sampleArr.push(sample);
        }

        var sampleInfoList = sampleArr;

        var params = {
            "sampleInfoList": sampleInfoList
        };

        $.ajax({
            url: "../reg/submitSampleSupply.html?newConsignmentId=" + newConsignmentId,
            type: "post",
            contentType: "application/json; charset=utf-8",
            data: JSON.stringify(params),
            dataType: "json",
            success: function (data) {

                $("#regModal").modal('show');
            },
            error: function (e) {
                alert(e);
            }
        });


    });

    /**
     * person start
     */
    $("#newPerBtn").on("click", function () {
        AddPersonRow();
    });

    $("button[name='editPerBtn']", "#personInfoTable").on("click", function () {
        EditPersonRow(this);
    });

    $("button[name='delPerBtn']", "#personInfoTable").on("click", function () {
        DelPersonRow(this);
    });

    $("#SavePersonBtn").on("click", function () {
        SavePersonRow();
    });

    $("#newSampleBtn").on("click", function () {
        $("input[name='sampleName']", "#sampleModal").removeAttr("placeholder");
        AddSampleRow('0');
    });

    $("#newPersonBtn").on("click", function () {
        $("input[name='sampleName']", "#samplePersonModal").attr("placeholder", "例如：XXX血样或口腔拭子");
        AddSampleRow('1');
    });

    $("button[name='editSampleBtn']", "#samplePersonTable").on("click", function () {
        EditSampleRow(this);
    });
    $("button[name='editSampleBtn']", "#sampleEvidenceTable").on("click", function () {
        EditSampleRow(this);
    });

    $("button[name='delSampleBtn']", "#samplePersonTable").on("click", function () {
        DelSampleRow(this);
    });
    $("button[name='delSampleBtn']", "#sampleEvidenceTable").on("click", function () {
        DelSampleRow(this);
    });

    $("#SaveSampleBtn").on("click", function () {
        var sampleName = $("input[name='sampleName']", "#sampleModal").val().trim();
        var sampleNameArr = $("input[name='sampleName'][value='" + sampleName + "']", "#sampleEvidenceTable");

        if (sampleNameArr.length > 0) {
            if (confirm("此检材名称已存在，是否继续添加！")) {
                SaveSampleRow();
            } else {
                $("input[name='sampleName']", "#sampleModal").focus();
            }
        } else {
            SaveSampleRow();
        }
    });

    $("#SaveSampleInfoBtn").on("click", function () {
        var sampleName = $("input[name='sampleName']", "#sampleInfoModal").val().trim();
        var sampleNameArr = $("input[name='sampleName'][value='" + sampleName + "']", "#sampleEvidenceTable");

        if (sampleNameArr.length > 0) {
            if (confirm("此检材名称已存在，是否继续添加！")) {
                SaveSampleInfoRow();
            } else {
                $("input[name='sampleName']", "#sampleInfoModal").focus();
            }
        } else {
            SaveSampleInfoRow();
        }
    });

    function SaveSampleInfoRow() {
        var errorCnt = 0;

        var sampleTypeVal = $("select[name='sampleType']", "#sampleInfoModal").find("option:selected").val();
        if (sampleTypeVal == "" || $.trim(sampleTypeVal) == "") {
            $("div.has-error", $("select[name='sampleType']", "#sampleInfoModal").parents("div.form-group")).removeClass("hide");
            errorCnt++;
        } else {
            $("div.has-error", $("select[name='sampleType']", "#sampleInfoModal").parents("div.form-group")).addClass("hide");
        }

        var sampleFlagVal = $("input[name='sampleFlag']", "#sampleInfoModal").val();

        if (sampleFlagVal == "1") {
            var refPeresonVal = $("select[name='refPerson']", "#sampleInfoModal").find("option:selected").val();
            if (refPeresonVal == "" || $.trim(refPeresonVal) == "") {
                $("div.has-error", $("select[name='refPerson']", "#sampleInfoModal").parents("div.form-group")).removeClass("hide");
                errorCnt++;
            } else {

                $("div.has-error", $("select[name='refPerson']", "#sampleInfoModal").parents("div.form-group")).addClass("hide");
            }
        }

        var samplePropertiesVal = $("select[name='sampleProperties']", "#sampleInfoModal").find("option:selected").val();
        var otherPropertiesDescVal = $("input[name='otherPropertiesDesc']", "#sampleInfoModal").val();

        if (samplePropertiesVal == "9999") {
            if (otherPropertiesDescVal == "" || $.trim(otherPropertiesDescVal) == "") {
                $("div.has-error", $("input[name='otherPropertiesDesc']", "#sampleInfoModal").parents("div.form-group")).removeClass("hide");
            } else {
                $("div.has-error", $("input[name='otherPropertiesDesc']", "#sampleInfoModal").parents("div.form-group")).addClass("hide");
            }
        }

        if (errorCnt > 0) {
            return;
        }

        var sampleId = $("input[name='sampleId']", "#sampleInfoModal").val();
        var sampleTypeCode = $("select[name='sampleType']", "#sampleInfoModal").find("option:selected").val();

        var sampleTypeName = $("select[name='sampleType']", "#sampleInfoModal").find("option:selected").text();
        var sampleName = $("input[name='sampleName']", "#sampleInfoModal").val();
        var evidenceNo = $("input[name='evidenceNo']", "#sampleInfoModal").val();
        var extractDatetime = $("input[name='extractDatetime']", "#sampleInfoModal").val();
        var extractPerson = $("input[name='extractPerson']", "#sampleInfoModal").val();
        var sampleDesc = $("input[name='sampleDesc']", "#sampleInfoModal").val();
        var samplePacking = $("input[name='samplePacking']", "#sampleInfoModal").val();
        var samplePropertiesName = $("select[name='sampleProperties']", "#sampleInfoModal").find("option:selected").text();
        //var refPersonCode = $("select[name='refPerson']", "#sampleModal").find("option:selected").val();
        var refPersonName = $("select[name='refPerson']", "#sampleInfoModal").find("option:selected").text();


        var newRowHtml = "<td></td>";

        newRowHtml += "<td><input type='hidden' name='evidenceNo' value='" + $.trim(evidenceNo) + "'/>" + evidenceNo + "</td>";
        newRowHtml += "<td><input type='hidden' name='sampleName' value='" + $.trim(sampleName) + "'/>" + sampleName + "</td>";
        newRowHtml += "<td><input type='hidden' name='sampleType' value='" + $.trim(sampleTypeCode) + "'/>" + sampleTypeName + "</td>";
        newRowHtml += "<td><input type='hidden' name='sampleDesc' value='" + $.trim(sampleDesc) + "'/>" + sampleDesc + "</td>";
        newRowHtml += "<td><input type='hidden' name='samplePacking' value='" + $.trim(samplePacking) + "'/>" + samplePacking + "</td>";
        newRowHtml += "<td><input type='hidden' name='sampleProperties' value='" + samplePropertiesVal + "'/><input type='hidden' name='otherPropertiesDesc' value='" + $.trim(otherPropertiesDescVal) + "'/>";
        if (samplePropertiesVal == "9999") {
            newRowHtml += otherPropertiesDescVal + "</td>";
        } else {
            newRowHtml += samplePropertiesName + "</td>";
        }

        if (sampleFlagVal == 1) {
            newRowHtml += "<td><input type='hidden' name='refPersonName' value='" + refPersonName + "'/>" + refPersonName + "</td>";
        }
        newRowHtml += "<td><input type='hidden' name='extractDatetime' value='" + extractDatetime + "'/>" + extractDatetime + "</td>";
        newRowHtml += "<td><input type='hidden' name='extractPerson' value='" + extractPerson + "'/>" + extractPerson + "</td>";
        newRowHtml += "<td><input type='hidden' name='sampleId' value='" + sampleId + "'/><input type='hidden' name='sampleFlag' value='" + sampleFlagVal + "'/><button name='editSampleBtn' class='btn btn-primary btn-xs'><i class='fa fa-pencil'></i> 修改</button>  <button name='delSampleBtn' class='btn btn-danger btn-xs'><i class='fa fa-trash-o'></i> 删除</button></td>";

        var operateType = $("input[name='sampleOperateType']", "#sampleInfoModal").val();
        if ("add" == operateType) {
            $("#samplePersonTable").append("<tr>" + newRowHtml + "</tr>");
            generateSamplePersonIdx();
        } else {
            var trIdx = $("input[name='sampleTableRownum']", "#sampleInfoModal").val();
            if (sampleFlagVal == 1) {
                $("tr:eq(" + trIdx + ")", "#samplePersonTable").html(newRowHtml);
                generateSamplePersonIdx();
            } else {
                $("tr:eq(" + trIdx + ")", "#sampleEvidenceTable").html(newRowHtml);
                generateSampleEvidenceIdx();
            }
        }

        $("button[name='editSampleBtn']", "#sampleEvidenceTable").on("click", function () {
            $("input[name='sampleFlag']", "#sampleInfoModal").val('0');
            EditSampleInfo(this);
        });
        $("button[name='delSampleBtn']", "#sampleEvidenceTable").on("click", function () {
            DelSampleRow(this);
        });

        $("#sampleInfoModal").modal('hide');

    }

    function EditSampleInfo(obj) {
        var $curTR = $(obj).parents("tr");
        var sample = {};
        sample.id = $("input[name='sampleId']", $curTR).val();
        sample.sampleType = $("input[name='sampleType']", $curTR).val();
        sample.evidenceNo = $("input[name='evidenceNo']", $curTR).val();
        sample.sampleName = $("input[name='sampleName']", $curTR).val();
        sample.extractDatetime = $("input[name='extractDatetime']", $curTR).val();
        sample.extractPerson = $("input[name='extractPerson']", $curTR).val();
        sample.sampleDesc = $("input[name='sampleDesc']", $curTR).val();
        sample.samplePacking = $("input[name='samplePacking']", $curTR).val();
        sample.sampleProperties = $("input[name='sampleProperties']", $curTR).val();
        sample.otherPropertiesDesc = $("input[name='otherPropertiesDesc']", $curTR).val();
        sample.refPersonId = $("input[name='refPersonId']", $curTR).val();
        sample.refPersonName = $("input[name='refPersonName']", $curTR).val();
        sample.sampleFlag = $("input[name='sampleFlag']", $curTR).val();
        sample.sampleFlag = $("input[name='sampleFlag']", $curTR).val();
        var trIdx = $curTR.index();
        newSampleInfoRow(sample, "edit", trIdx);
    }

    function newSampleInfoRow(sample, operateType, rownum) {

        $("div.has-error", "#sampleInfoModal").addClass("hide");

        $("input[name='sampleId']", "#sampleInfoModal").val(sample.id);
        $("select[name='sampleType']", "#sampleInfoModal").val(sample.sampleType);
        $("input[name='evidenceNo']", "#sampleInfoModal").val(sample.evidenceNo);
        $("input[name='sampleName']", "#sampleInfoModal").val(sample.sampleName);
        $("input[name='extractDatetime']", "#sampleInfoModal").val(sample.extractDatetime);
        $("input[name='extractPerson']", "#sampleInfoModal").val(sample.extractPerson);
        $("input[name='sampleDesc']", "#sampleInfoModal").val(sample.sampleDesc);
        if (sample.samplePacking == "") {
            $("input[name='samplePacking']", "#sampleInfoModal").val("物证袋");
        } else {
            $("input[name='samplePacking']", "#sampleInfoModal").val(sample.samplePacking);
        }
        if (sample.sampleProperties != "") {
            $("select[name='sampleProperties']", "#sampleInfoModal").val(sample.sampleProperties);

            if (sample.sampleProperties == "9999") {
                $("input[name='otherPropertiesDesc']", "#sampleInfoModal").val(sample.otherPropertiesDesc);
                $("input[name='otherPropertiesDesc']", "#sampleInfoModal").removeClass("hide");
            } else {
                $("input[name='otherPropertiesDesc']", "#sampleInfoModal").val("");
                $("input[name='otherPropertiesDesc']", "#sampleInfoModal").addClass("hide");
            }
        } else {
            $("select[name='sampleProperties']", "#sampleInfoModal").prop('selectedIndex', 0);

            $("input[name='otherPropertiesDesc']", "#sampleInfoModal").val("");
            $("input[name='otherPropertiesDesc']", "#sampleInfoModal").addClass("hide");
        }

        $("select[name='refPerson']", "#sampleInfoModal").empty();
        $("select[name='refPerson']", "#sampleInfoModal").append("<option value=''>请选择</option>")
        $("input[name='personName']", "#personInfoTable").each(function () {
            var refPersonName = $(this).val();
            if (operateType == "edit" && refPersonName == sample.refPersonName) {
                $("select[name='refPerson']", "#sampleInfoModal").append("<option value='" + refPersonName + "' selected>" + refPersonName + " </option>");

            } else {
                $("select[name='refPerson']", "#sampleInfoModal").append("<option value='" + refPersonName + "'>" + refPersonName + "</option>");
            }
        });

        if (sample.sampleFlag == "" || sample.sampleFlag == "0") {
            $("input[name='sampleFlag']", "#sampleInfoModal").val(sample.sampleFlag);
            $("input[name='evidenceNo']", "#sampleInfoModal").val(sample.evidenceNo);

            $("#divRefPerson").hide();
        } else {
            $("input[name='sampleFlag']", "#sampleInfoModal").val(sample.sampleFlag);
            $("#divRefPerson").show();
        }

        $("input[name='sampleOperateType']", "#sampleInfoModal").val(operateType);
        $("input[name='sampleTableRownum']", "#sampleInfoModal").val(rownum);
        $("#sampleInfoModal").modal();
    }

});
