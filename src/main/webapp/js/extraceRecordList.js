/**
 * Created by Administrator on 2017/1/4.
 */
$(function(){
    'use strict';

    $("#newPerBtn").on('click',function(){
        $("#wrapper-content").load('../extract/add.html', function(){$("#wrapper-content").fadeIn(100);});
    });
});
