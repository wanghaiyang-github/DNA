/**
 * Created by Administrator on 2017/1/3.
 */
function siderMenuLink(obj,linkUrl) {
    'use strict';

    $("li",$(obj).parents('ul')).removeClass("active");
    $(obj).parent('li').addClass("active");

    $("#wrapper-content").load(linkUrl, function(){$("#wrapper-content").fadeIn(100);});
}

$(function(){
    'use strict';
    $("#homePageLink").trigger("click");
});

