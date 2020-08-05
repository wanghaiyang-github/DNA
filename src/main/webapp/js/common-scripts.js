
var Script = function () {
'use strict';
    /*
    $(function () {
        function responsiveView() {
            var wSize = $(window).width();
            if (wSize <= 768) {
                $('#container').addClass('sidebar-close');
                $('#sidebar > ul').hide();
            }
            if (wSize > 768) {
                $('#container').removeClass('sidebar-close');
                $('#sidebar > ul').show();
            }
        }

        $(window).on('load', responsiveView);
        $(window).on('resize', responsiveView);
    });*/
    /*$('.fa-bars').click(function () {
        if ($('#sidebar > ul').is(":visible") === true) {
            $('#main-content').css({
                'margin-left': '0px'
            });
            $('#sidebar').css({
                'margin-left': '-210px'
            });
            $('#sidebar > ul').hide();
            $("#container").addClass("sidebar-closed");
        } else {
            $('#main-content').css({
                'margin-left': '210px'
            });
            $('#sidebar > ul').show();
            $('#sidebar').css({
                'margin-left': '0'
            });
            $("#container").removeClass("sidebar-closed");
        }
    });*/
    //$("#sidebar").niceScroll({
    //    styler: "fb",
    //    cursorcolor: "#72d0eb",
    //    cursorwidth: '8',
    //    cursorborderradius: '10px',
    //    background: '#116077',
    //    spacebarenabled: false,
    //    cursorborder: '',
		//zindex: '1000'
    //});
    /*$("body").niceScroll({
        styler: "fb",
        cursorcolor: "#72d0eb",
        cursorwidth: '6',
        cursorborderradius: '10px',
        background: '#116077',
        spacebarenabled: false,
        cursorborder: '',
        zindex: '1000'
    });*/

    jQuery('.panel .tools .fa-chevron-down').click(function () {
        var el = jQuery(this).parents(".panel").children(".panel-body");
        if (jQuery(this).hasClass("fa-chevron-down")) {
            jQuery(this).removeClass("fa-chevron-down").addClass("fa-chevron-up");
            el.slideUp(200);
        } else {
            jQuery(this).removeClass("fa-chevron-up").addClass("fa-chevron-down");
            el.slideDown(200);
        }
    });

    jQuery('.panel .tools .fa-times').click(function () {
        jQuery(this).parents(".panel").parent().remove();
    });

    $('.tooltips').tooltip();
    $('.popovers').popover();
    if ($(".custom-bar-chart")) {
        $(".bar").each(function () {
            var i = $(this).find(".value").html();
            $(this).find(".value").html("");
            $(this).find(".value").animate({
                height: i
            }, 2000)
        })
    }

    $("#SaveDelegatePasswordBtn", "#profileModal").on("click", function(){
        var errorCnt = 0;
        $("input.required", "#profileModal").each(function(){
            if($(this).val() == "" || $.trim($(this).val()) == ""){
                $("div.has-error",$(this).parents("div.form-group")).removeClass("hide");
                errorCnt++;
            }else{
                $("div.has-error",$(this).parents("div.form-group")).addClass("hide");
            }
        });

        if(errorCnt > 0){
            return false;
        }

        var oldPassword = $("#oldPassword", "#profileModal").val();
        var newPassword = $("#newPassword", "#profileModal").val();
        var confirmNewPassword = $("#confirmNewPassword", "#profileModal").val();

        if(newPassword != confirmNewPassword){
            $("div.has-error",$("#confirmNewPassword").parents("div.form-group")).find("p").html("两次输入密码不一致！");
            return false;
        }
        var basePath = $("#updatePasswordUrl", "#profileModal").val();
        var url = basePath + "?oldPassword=" + oldPassword + "&newPassword=" + newPassword;

        $.ajax({
            type: "get",
            url: url,
            dataType:"json",
            async: false,
            cache: false,
            success: function(data){
                if(data == "0"){
                    $("#messagLabel", "#profileModal").html("密码修改成功！")
                        .removeClass("color_red").addClass("color_green");
                    setInterval(function(){
                        $("#profileModal").modal("hide");
                    }, 2000);
                }else if(data == "1"){
                    $("#messagLabel", "#profileModal").html("旧密码输入错误！")
                        .removeClass("color_green").addClass("color_red");
                }else{
                    $("#messagLabel", "#profileModal").html("密码修改失败，请重试！")
                        .removeClass("color_green").addClass("color_red");
                }
            }
        });
    });

    $("#SaveCenterPasswordBtn", "#profileModal").on("click", function(){
        var errorCnt = 0;
        $("input.required", "#profileModal").each(function(){
            if($(this).val() == "" || $.trim($(this).val()) == ""){
                $("div.has-error",$(this).parents("div.form-group")).removeClass("hide");
                errorCnt++;
            }else{
                $("div.has-error",$(this).parents("div.form-group")).addClass("hide");
            }
        });

        if(errorCnt > 0){
            return false;
        }

        var oldPassword = $("#oldPassword", "#profileModal").val();
        var newPassword = $("#newPassword", "#profileModal").val();
        var confirmNewPassword = $("#confirmNewPassword", "#profileModal").val();

        if(newPassword != confirmNewPassword){
            $("div.has-error",$("#confirmNewPassword").parents("div.form-group")).find("p").html("两次输入密码不一致！");
            return false;
        }
        var basePath = $("#updatePasswordUrl", "#profileModal").val();
        var url = basePath + "?oldPassword=" + oldPassword + "&newPassword=" + newPassword;

        $.ajax({
            type: "get",
            url: url,
            dataType:"json",
            async: false,
            cache: false,
            success: function(data){
                if(data == "0"){
                    $("#messagLabel", "#profileModal").html("密码修改成功！")
                        .removeClass("color_red").addClass("color_green");
                    setInterval(function(){
                        $("#profileModal").modal("hide");
                    }, 2000);
                }else if(data == "1"){
                    $("#messagLabel", "#profileModal").html("旧密码输入错误！")
                        .removeClass("color_green").addClass("color_red");
                }else{
                    $("#messagLabel", "#profileModal").html("密码修改失败，请重试！")
                        .removeClass("color_green").addClass("color_red");
                }
            }
        });
    });

    $("#profileModal").on('hidden.bs.modal', function() {
        $("#oldPassword", "#profileModal").val("");
        $("#newPassword", "#profileModal").val("");
        $("#confirmNewPassword", "#profileModal").val("");
        $("#messagLabel", "#profileModal").html("");
    });

}();

