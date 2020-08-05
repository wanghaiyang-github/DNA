<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
<head>
    <!-- BEGIN META -->
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- END META -->

    <!-- BEGIN SHORTCUT ICON -->
    <link rel="shortcut icon" href="<%=path%>/img/dna.ico">
    <!-- END SHORTCUT ICON -->
    <title>
        博安智联LIMS - ${loginTitleName}
    </title>
    <!-- BEGIN STYLESHEET-->
    <link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="<%=path%>/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
    <link href="<%=path%>/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON CSS -->
    <link rel="stylesheet" type="text/css" href="<%=path%>/assets/bootstrap-datetimepicker/css/datetimepicker.css">
    <link href="<%=path%>/css/style.css" rel="stylesheet"><!-- THEME BASIC CSS -->
    <link href="<%=path%>/css/style-responsive.css" rel="stylesheet"><!-- THEME RESPONSIVE CSS -->
    <link href="<%=path%>/assets/morris.js-0.4.3/morris.css" rel="stylesheet"><!-- MORRIS CHART CSS -->
    <!--dashboard calendar-->
    <link href="<%=path%>/css/clndr.css" rel="stylesheet"><!-- CALENDER CSS -->
    <!--[if lt IE 9]>
    <script src="<%=path%>/js/html5shiv.js">
    </script>
    <script src="<%=path%>/js/respond.min.js">
    </script>
    <![endif]-->
    <!-- END STYLESHEET-->
    <style>
        .wrapper {
            padding-top: 20px;
        }

        .wrapper > div {
            background: #fff;
            height: 380px;
            position: relative;
            border-radius: 5px;
        }

        .wrapper > div > div {
            background: #fafafa;
            height: 382px;
            width: 500px;
            position: absolute;
            left: 50%;
            margin-left: -250px;
            top: 50%;
            margin-top: -191px;
            text-align: center;
            padding: 10px 75px;
        }

        .wrapper > div > div > h3 {
            color: #0c81f5;
            font-weight: 600;
            margin-top: 77px;

        }

        .wrapper > div > div > input {
            margin: 50px 0px;
        }

        .btn-info {
            margin-right: 20px;
        }

        .btn-success {
            margin-left: 20px;

        }
    </style>
</head>
<body>
<!-- BEGIN SECTION -->
<section id="container">
    <!-- BEGIN HEADER -->
    <jsp:include page="../hearder.jsp"/>
    <!-- END HEADER -->

    <jsp:include page="../updateDelegatePassword.jsp"/>

    <!-- BEGIN SIDEBAR -->
    <aside>
        <div id="sidebar" class="nav-collapse">
            <ul class="sidebar-menu" id="nav-accordion">
                <li>
                    <a href="<%=path%>/wt/reg/1.html" class="active">
                        案件委托登记
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/reg/2.html">
                        身份不明人员委托登记
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/reg/3.html">
                        失踪人口委托登记
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/caseinfo/listPending.html">
                        未受理
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/caseinfo/listAccepted.html">
                        已受理
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/caseinfo/listRefused.html">
                        已退案
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/message/listIdentify.html">
                        鉴定领取通知
                    </a>
                </li>
                <li>
                    <a href="<%=path%>/wt/consignment/listDelegator.html">
                        委托人管理
                    </a>
                </li>
            </ul>
        </div>
    </aside>
    <!-- END SIDEBAR -->

    <section id="main-content" class="main-content-delegate">
        <!-- BEGIN WRAPPER  -->
        <section class="wrapper wrapper-delegate">
            <!-- BEGIN ROW  -->
            <div>
                <div>
                    <h3>输入现勘编号</h3>
                    <input type="hidden" name="xkLoginName" value="${xkLoginName}">
                    <input type="hidden" name="xkLoginPassword" value="${xkLoginPassword}">
                    <input type="text" class="form-control" placeholder="请输入现勘编号" name="caseXkNo" id="caseXkNo">
                    <button class="btn btn-lg btn-info" name="reset" id="reset">重置</button>
                    <button class="btn btn-lg btn-success" name="nextStep" id="nextStep">下一步</button>
                </div>
            </div>
        </section>
    </section>

    <!-- BEGIN FOOTER -->
    <footer class="site-footer">
        <div class="text-center" style="margin-top:20px;padding-left: 210px;">
            Copyright © 2016 北京博安智联科技有限公司
        </div>
    </footer>
</section>
<!-- BEGIN JS -->
<script src="<%=path%>/js/jquery.js"></script><!-- BASIC JS LIABRARY -->
<script src="<%=path%>/js/bootstrap.min.js"></script><!-- BOOTSTRAP JS  -->
<script src="<%=path%>/js/jquery.dcjqaccordion.2.7.js"></script><!-- ACCORDING JS -->
<script src="<%=path%>/js/jquery.scrollTo.min.js"></script><!-- SCROLLTO JS  -->
<script src="<%=path%>/js/jquery.nicescroll.js"></script><!-- NICESCROLL JS  -->
<script src="<%=path%>/js/jquery-ui-1.9.2.custom.min.js"></script><!-- JQUERY UI JS  -->
<script src="<%=path%>/js/bootstrap-switch.js"></script> <!-- BOOTSTRAP SWITCH JS  -->
<script src="<%=path%>/js/jquery.tagsinput.js"></script> <!-- TAGS INPUT JS  -->
<script src="<%=path%>/js/ga.js"></script><!-- GA JS  -->
<script src="<%=path%>/assets/bootstrap-datetimepicker/js/bootstrap-datetimepicker.js"></script><!-- DATEPICKER JS  -->
<script src="<%=path%>/assets/bootstrap-inputmask/bootstrap-inputmask.min.js"></script><!-- INPUT MASK JS  -->
<script src="<%=path%>/js/respond.min.js"></script><!-- RESPOND JS  -->
<script src="<%=path%>/js/common-scripts.js"></script> <!-- BASIC COMMON JS  -->
<script>
    $(".wrapper").css("min-height", $(window).height() - 90 - 69 + "px")
    $(".wrapper").children().css("height", $(window).height() - 90 - 83 + "px")
    $(".btn-info").click(function () {
        $("input").val("")
    })

    $("button[name='reset']").on("click", function () {
        $("input[name='caseXkNo']").val("");
    })


    $("button[name='nextStep']").on("click", function () {
        var caseXkNo = $("input[name='caseXkNo']").val();
        var xkLoginName = $("input[name='xkLoginName']").val();
        var xkLoginPassword = $("input[name='xkLoginPassword']").val();

        if (caseXkNo == "") {
            alert("请输入现勘编号!")
            location.reload();
        }

        /* $.ajax({
         url: "http://10.8.41.15:8801/dataAssistant/getDatas?investigationNo="+caseXkNo+"&username="+xkLoginName+"$password="+xkLoginPassword,
         type: "get",
         success: function (data) {
         console.log(data);*/
        var paramsXk = {
            "data": {
                "anjianjibenxinxi": {
                    "anjianshoulihao": "",
                    "anjianbianhao": "",
                    "anjianleibie": "故意杀人案",
                    "faanquhua": "贵阳市公安局刑侦支队",
                    "faanshijian": "2009-10-13 11:30（起） —\n        2009-10-13 11:50（止）",
                    "shifoumingan": "是",
                    "poanshjian": "",
                    "shifouxingan": "是",
                    "jingweidu": "/"
                },
                "kanchajibenxinxi": {
                    "jiejingshijian": "2009-10-14 00:45",
                    "jiejingren": "殷筑平",
                    "zhipaibaogaodanwei": "贵阳市公安局刑侦支队",
                    "zhipaifangshi": "电话指派",
                    "kanyanshiyou": "贵阳市金阳新区金阳医院北门发现一具男尸，要求技术科派员出勘现场。",
                    "kanyanshijian": "2009-10-14 01:21（开始） —\n        2009-10-14 04:05（结束）",
                    "kanyandidian": "贵阳市金阳新区金阳医院北门石林路",
                    "tianqizhuangkuang": "阴 温度：8.0℃；相对湿度：80.0％；\n\t    风向：东风。",
                    "xianchangtiaojian": "变动现场",
                    "guangzhaotiaojian": "灯光",
                    "baohushijian": "2009-10-14 00:00",
                    "xianchangwupinfandongchengdu": "未翻动",
                    "baohuren": "",
                    "baohucuoshi": "",
                    "anjianfaxianguocheng": "据报案人刘万祥(金阳医院保安,男，联系电话:13511996729)介绍:其于10月13日晚上在岗亭值班，23点50分左右听到有人喊救命，于是便冲了出来，发现北门路上躺有一人（在医院医生的检查下确认已死亡），遂报案。",
                    "kanyanjianchaqingkuang": "死者位于金阳医院北门外石林路路边，距人行道绿化带110CM。头朝西南脚朝东北；死者上身着深蓝色棉外衣 ，棕色毛衣、白色T恤、下身着棕色灯芯绒长裤、白色运动鞋。腰间拴有一个黑色腰包，包内有手机一个、户口簿一个、现金90元（50元×1，20元×1，10元×2）、板栗一包、证件照片若干及二代身份证回执一张；在距尸体东南侧1.8M的绿化带中提取到红色单刃折叠匕首一把。在石林路隔离带南侧的道路上发现并提取血迹四处，在隔离带上发现了踩踏痕迹（无提取价值），在隔离带北侧的道路上发现并提取血迹三处、钥匙一把、钥匙挂圈两个以及断裂的烟头一个。",
                    "shangwangqingkuang": "伤?0?亡?0",
                    "sunshiwupinzongjiazhi": "0.0?元",
                    "beihairenbaoanren": [],
                    "sunshiwupin": [],
                    "xianchangyiliuwu": "",
                    "xianchangchuzhiyijian": "",
                    "xianchangzhihuirenyuan": "张闯",
                    "kanyanjiancharenyuan": "潘毅、张闯、殷筑平、张黎、张磊",
                    "qitadaodaxianchangrenyuan": "",
                    "jianzhengrens": [{
                        "xuhao": "",
                        "xingming": "彭学忠",
                        "xingbie": "男",
                        "chushengriqi": "",
                        "danweizhuzhi": "金阳医院"
                    }, {"xuhao": "", "xingming": "唐银富", "xingbie": "男", "chushengriqi": "", "danweizhuzhi": "金阳医院"}],
                    "jianzhengrenbeizhu": ""
                },
                "fenxiyijian": {
                    "qinhaimubiao": ["个体户", "其他处所", "其它"],
                    "zuoandidian": "贵阳市金阳新区金阳医院北门石林路",
                    "zuoanshiji": "阴天、夜、其他时机",
                    "zuoanjinkou": "其他",
                    "zuoanchukou": "",
                    "zuoanshouduan": "",
                    "qinrufangshi": "",
                    "zuoantedian": "",
                    "zuoangongju": "1、单刃锐器，使用工具类，随身携带，单刃锐器。",
                    "zuoandongjimudi": "",
                    "anjianxingzhi": "",
                    "zuoanrenshu": "",
                    "zuoanguocheng": "在发案地点，犯罪嫌疑人利用被害人较低的防范心理，趁其不备的情况下掏出随身揣的锐器将其杀死，随后逃逸。",
                    "zuoanrentedian": "低收入群体，有作案经验，对作案周围环境熟悉",
                    "chuanpinyijianyugenju": "与近期作案性质和手段相似的案件进行串并案",
                    "gongzuojianyi": "1.关于痕迹物证的保管、检验、应用、建库与查询等意见：无；2.关于现场保留、现场复勘、尸体处置等意见：无；3.关于侦查方向与范围、侦破措施及途径等意见：对与死者有仇恨关系的人群以及死者的摩托车去向进行重点排查，对当天晚上相应路段的监控录像进行查看，对与该案作案性质和手段相似的案件进行串并案。；4.关于技术防范对策：无；5.其他：无；",
                    "fenxidanwei": "贵阳市公安局刑侦支队",
                    "fenxiren": "潘毅",
                    "fenxishijian": "2009-10-15"
                },
                "hengjiwuzheng": {
                    "shengwu": [{
                        "leixing": "血迹",
                        "jibentezheng": "滴落状",
                        "yiliubuwei": "石林路隔离带南侧公路上",
                        "tiqufangfa": "转移提取",
                        "tiquren": "张黎、潘毅",
                        "tiquriqi": "2009-10-14",
                        "tupian": "DQoNCjxodG1sPg0KPGhlYWQ+DQo8bWV0YSBodHRwLWVxdWl2PSJDb250ZW50LVR5cGUiIGNvbnRlbnQ9InRleHQvaHRtbDsgY2hhcnNldD1VVEYtOCI+DQo8bWV0YSBodHRwLWVxdWl2PSJwcmFnbWEiIGNvbnRlbnQ9Im5vLWNhY2hlIj4NCjxtZXRhIGh0dHAtZXF1aXY9ImNhY2hlLWNvbnRyb2wiIGNvbnRlbnQ9Im5vLWNhY2hlIj4NCjxtZXRhIGh0dHAtZXF1aXY9ImV4cGlyZXMiIGNvbnRlbnQ9IjAiPg0KPHNjcmlwdCBsYW5ndWFnZT0iamF2YXNjcmlwdCI+DQoJZnVuY3Rpb24gdGltZU91dCgpew0KCQlhbGVydCgn55m75b2V6LaF5pe277yM6K+36YeN5paw55m75b2VJyk7DQoJCXdpbmRvdy5vcGVuKCcveGNreS9qX2FjZWdpX2xvZ291dCcpOw0KCQl3aW5kb3cub3BlbmVyPSBudWxsOw0KCQl3aW5kb3cuY2xvc2UoKTsNCgkJcGFyZW50LmNsb3NlKCk7DQoJfQ0KPC9zY3JpcHQ+DQo8L2hlYWQ+DQo8Ym9keSBvbmxvYWQ9InRpbWVPdXQoKTsiPg0KPC9ib2R5Pg0KPC9odG1sPg0K"
                    }, {
                        "leixing": "血迹",
                        "jibentezheng": "滴落状",
                        "yiliubuwei": "石林路隔离带南侧公路上",
                        "tiqufangfa": "转移提取",
                        "tiquren": "张黎、潘毅",
                        "tiquriqi": "2009-10-14",
                        "tupian": "DQoNCjxodG1sPg0KPGhlYWQ+DQo8bWV0YSBodHRwLWVxdWl2PSJDb250ZW50LVR5cGUiIGNvbnRlbnQ9InRleHQvaHRtbDsgY2hhcnNldD1VVEYtOCI+DQo8bWV0YSBodHRwLWVxdWl2PSJwcmFnbWEiIGNvbnRlbnQ9Im5vLWNhY2hlIj4NCjxtZXRhIGh0dHAtZXF1aXY9ImNhY2hlLWNvbnRyb2wiIGNvbnRlbnQ9Im5vLWNhY2hlIj4NCjxtZXRhIGh0dHAtZXF1aXY9ImV4cGlyZXMiIGNvbnRlbnQ9IjAiPg0KPHNjcmlwdCBsYW5ndWFnZT0iamF2YXNjcmlwdCI+DQoJZnVuY3Rpb24gdGltZU91dCgpew0KCQlhbGVydCgn55m75b2V6LaF5pe277yM6K+36YeN5paw55m75b2VJyk7DQoJCXdpbmRvdy5vcGVuKCcveGNreS9qX2FjZWdpX2xvZ291dCcpOw0KCQl3aW5kb3cub3BlbmVyPSBudWxsOw0KCQl3aW5kb3cuY2xvc2UoKTsNCgkJcGFyZW50LmNsb3NlKCk7DQoJfQ0KPC9zY3JpcHQ+DQo8L2hlYWQ+DQo8Ym9keSBvbmxvYWQ9InRpbWVPdXQoKTsiPg0KPC9ib2R5Pg0KPC9odG1sPg0K"
                    }, {
                        "leixing": "血迹",
                        "jibentezheng": "滴落状",
                        "yiliubuwei": "石林路隔离带南侧公路上",
                        "tiqufangfa": "转移提取",
                        "tiquren": "张黎、潘毅",
                        "tiquriqi": "2009-10-14",
                        "tupian": "DQoNCjxodG1sPg0KPGhlYWQ+DQo8bWV0YSBodHRwLWVxdWl2PSJDb250ZW50LVR5cGUiIGNvbnRlbnQ9InRleHQvaHRtbDsgY2hhcnNldD1VVEYtOCI+DQo8bWV0YSBodHRwLWVxdWl2PSJwcmFnbWEiIGNvbnRlbnQ9Im5vLWNhY2hlIj4NCjxtZXRhIGh0dHAtZXF1aXY9ImNhY2hlLWNvbnRyb2wiIGNvbnRlbnQ9Im5vLWNhY2hlIj4NCjxtZXRhIGh0dHAtZXF1aXY9ImV4cGlyZXMiIGNvbnRlbnQ9IjAiPg0KPHNjcmlwdCBsYW5ndWFnZT0iamF2YXNjcmlwdCI+DQoJZnVuY3Rpb24gdGltZU91dCgpew0KCQlhbGVydCgn55m75b2V6LaF5pe277yM6K+36YeN5paw55m75b2VJyk7DQoJCXdpbmRvdy5vcGVuKCcveGNreS9qX2FjZWdpX2xvZ291dCcpOw0KCQl3aW5kb3cub3BlbmVyPSBudWxsOw0KCQl3aW5kb3cuY2xvc2UoKTsNCgkJcGFyZW50LmNsb3NlKCk7DQoJfQ0KPC9zY3JpcHQ+DQo8L2hlYWQ+DQo8Ym9keSBvbmxvYWQ9InRpbWVPdXQoKTsiPg0KPC9ib2R5Pg0KPC9odG1sPg0K"
                    }, {
                        "leixing": "血迹",
                        "jibentezheng": "滴落状",
                        "yiliubuwei": "石林路隔离带南侧公路上",
                        "tiqufangfa": "转移提取",
                        "tiquren": "张黎、潘毅",
                        "tiquriqi": "2009-10-14",
                        "tupian": "DQoNCjxodG1sPg0KPGhlYWQ+DQo8bWV0YSBodHRwLWVxdWl2PSJDb250ZW50LVR5cGUiIGNvbnRlbnQ9InRleHQvaHRtbDsgY2hhcnNldD1VVEYtOCI+DQo8bWV0YSBodHRwLWVxdWl2PSJwcmFnbWEiIGNvbnRlbnQ9Im5vLWNhY2hlIj4NCjxtZXRhIGh0dHAtZXF1aXY9ImNhY2hlLWNvbnRyb2wiIGNvbnRlbnQ9Im5vLWNhY2hlIj4NCjxtZXRhIGh0dHAtZXF1aXY9ImV4cGlyZXMiIGNvbnRlbnQ9IjAiPg0KPHNjcmlwdCBsYW5ndWFnZT0iamF2YXNjcmlwdCI+DQoJZnVuY3Rpb24gdGltZU91dCgpew0KCQlhbGVydCgn55m75b2V6LaF5pe277yM6K+36YeN5paw55m75b2VJyk7DQoJCXdpbmRvdy5vcGVuKCcveGNreS9qX2FjZWdpX2xvZ291dCcpOw0KCQl3aW5kb3cub3BlbmVyPSBudWxsOw0KCQl3aW5kb3cuY2xvc2UoKTsNCgkJcGFyZW50LmNsb3NlKCk7DQoJfQ0KPC9zY3JpcHQ+DQo8L2hlYWQ+DQo8Ym9keSBvbmxvYWQ9InRpbWVPdXQoKTsiPg0KPC9ib2R5Pg0KPC9odG1sPg0K"
                    }, {
                        "leixing": "血迹",
                        "jibentezheng": "滴落状",
                        "yiliubuwei": "石林路隔离带北侧公路上",
                        "tiqufangfa": "转移提取",
                        "tiquren": "张黎、潘毅",
                        "tiquriqi": "2009-10-14",
                        "tupian": "DQoNCjxodG1sPg0KPGhlYWQ+DQo8bWV0YSBodHRwLWVxdWl2PSJDb250ZW50LVR5cGUiIGNvbnRlbnQ9InRleHQvaHRtbDsgY2hhcnNldD1VVEYtOCI+DQo8bWV0YSBodHRwLWVxdWl2PSJwcmFnbWEiIGNvbnRlbnQ9Im5vLWNhY2hlIj4NCjxtZXRhIGh0dHAtZXF1aXY9ImNhY2hlLWNvbnRyb2wiIGNvbnRlbnQ9Im5vLWNhY2hlIj4NCjxtZXRhIGh0dHAtZXF1aXY9ImV4cGlyZXMiIGNvbnRlbnQ9IjAiPg0KPHNjcmlwdCBsYW5ndWFnZT0iamF2YXNjcmlwdCI+DQoJZnVuY3Rpb24gdGltZU91dCgpew0KCQlhbGVydCgn55m75b2V6LaF5pe277yM6K+36YeN5paw55m75b2VJyk7DQoJCXdpbmRvdy5vcGVuKCcveGNreS9qX2FjZWdpX2xvZ291dCcpOw0KCQl3aW5kb3cub3BlbmVyPSBudWxsOw0KCQl3aW5kb3cuY2xvc2UoKTsNCgkJcGFyZW50LmNsb3NlKCk7DQoJfQ0KPC9zY3JpcHQ+DQo8L2hlYWQ+DQo8Ym9keSBvbmxvYWQ9InRpbWVPdXQoKTsiPg0KPC9ib2R5Pg0KPC9odG1sPg0K"
                    }, {
                        "leixing": "血迹",
                        "jibentezheng": "滴落状",
                        "yiliubuwei": "石林路隔离带北侧公路上",
                        "tiqufangfa": "转移提取",
                        "tiquren": "张黎、潘毅",
                        "tiquriqi": "2009-10-14",
                        "tupian": "DQoNCjxodG1sPg0KPGhlYWQ+DQo8bWV0YSBodHRwLWVxdWl2PSJDb250ZW50LVR5cGUiIGNvbnRlbnQ9InRleHQvaHRtbDsgY2hhcnNldD1VVEYtOCI+DQo8bWV0YSBodHRwLWVxdWl2PSJwcmFnbWEiIGNvbnRlbnQ9Im5vLWNhY2hlIj4NCjxtZXRhIGh0dHAtZXF1aXY9ImNhY2hlLWNvbnRyb2wiIGNvbnRlbnQ9Im5vLWNhY2hlIj4NCjxtZXRhIGh0dHAtZXF1aXY9ImV4cGlyZXMiIGNvbnRlbnQ9IjAiPg0KPHNjcmlwdCBsYW5ndWFnZT0iamF2YXNjcmlwdCI+DQoJZnVuY3Rpb24gdGltZU91dCgpew0KCQlhbGVydCgn55m75b2V6LaF5pe277yM6K+36YeN5paw55m75b2VJyk7DQoJCXdpbmRvdy5vcGVuKCcveGNreS9qX2FjZWdpX2xvZ291dCcpOw0KCQl3aW5kb3cub3BlbmVyPSBudWxsOw0KCQl3aW5kb3cuY2xvc2UoKTsNCgkJcGFyZW50LmNsb3NlKCk7DQoJfQ0KPC9zY3JpcHQ+DQo8L2hlYWQ+DQo8Ym9keSBvbmxvYWQ9InRpbWVPdXQoKTsiPg0KPC9ib2R5Pg0KPC9odG1sPg0K"
                    }, {
                        "leixing": "血迹",
                        "jibentezheng": "滴落状",
                        "yiliubuwei": "石林路隔离带北侧公路上",
                        "tiqufangfa": "转移提取",
                        "tiquren": "张黎、潘毅",
                        "tiquriqi": "2009-10-14",
                        "tupian": "DQoNCjxodG1sPg0KPGhlYWQ+DQo8bWV0YSBodHRwLWVxdWl2PSJDb250ZW50LVR5cGUiIGNvbnRlbnQ9InRleHQvaHRtbDsgY2hhcnNldD1VVEYtOCI+DQo8bWV0YSBodHRwLWVxdWl2PSJwcmFnbWEiIGNvbnRlbnQ9Im5vLWNhY2hlIj4NCjxtZXRhIGh0dHAtZXF1aXY9ImNhY2hlLWNvbnRyb2wiIGNvbnRlbnQ9Im5vLWNhY2hlIj4NCjxtZXRhIGh0dHAtZXF1aXY9ImV4cGlyZXMiIGNvbnRlbnQ9IjAiPg0KPHNjcmlwdCBsYW5ndWFnZT0iamF2YXNjcmlwdCI+DQoJZnVuY3Rpb24gdGltZU91dCgpew0KCQlhbGVydCgn55m75b2V6LaF5pe277yM6K+36YeN5paw55m75b2VJyk7DQoJCXdpbmRvdy5vcGVuKCcveGNreS9qX2FjZWdpX2xvZ291dCcpOw0KCQl3aW5kb3cub3BlbmVyPSBudWxsOw0KCQl3aW5kb3cuY2xvc2UoKTsNCgkJcGFyZW50LmNsb3NlKCk7DQoJfQ0KPC9zY3JpcHQ+DQo8L2hlYWQ+DQo8Ym9keSBvbmxvYWQ9InRpbWVPdXQoKTsiPg0KPC9ib2R5Pg0KPC9odG1sPg0K"
                    }, {
                        "leixing": "其他生物物证",
                        "jibentezheng": "新鲜的烟头",
                        "yiliubuwei": "石林路隔离带北侧公路上",
                        "tiqufangfa": "原物提取",
                        "tiquren": "张黎、潘毅",
                        "tiquriqi": "2009-10-14",
                        "tupian": "DQoNCjxodG1sPg0KPGhlYWQ+DQo8bWV0YSBodHRwLWVxdWl2PSJDb250ZW50LVR5cGUiIGNvbnRlbnQ9InRleHQvaHRtbDsgY2hhcnNldD1VVEYtOCI+DQo8bWV0YSBodHRwLWVxdWl2PSJwcmFnbWEiIGNvbnRlbnQ9Im5vLWNhY2hlIj4NCjxtZXRhIGh0dHAtZXF1aXY9ImNhY2hlLWNvbnRyb2wiIGNvbnRlbnQ9Im5vLWNhY2hlIj4NCjxtZXRhIGh0dHAtZXF1aXY9ImV4cGlyZXMiIGNvbnRlbnQ9IjAiPg0KPHNjcmlwdCBsYW5ndWFnZT0iamF2YXNjcmlwdCI+DQoJZnVuY3Rpb24gdGltZU91dCgpew0KCQlhbGVydCgn55m75b2V6LaF5pe277yM6K+36YeN5paw55m75b2VJyk7DQoJCXdpbmRvdy5vcGVuKCcveGNreS9qX2FjZWdpX2xvZ291dCcpOw0KCQl3aW5kb3cub3BlbmVyPSBudWxsOw0KCQl3aW5kb3cuY2xvc2UoKTsNCgkJcGFyZW50LmNsb3NlKCk7DQoJfQ0KPC9zY3JpcHQ+DQo8L2hlYWQ+DQo8Ym9keSBvbmxvYWQ9InRpbWVPdXQoKTsiPg0KPC9ib2R5Pg0KPC9odG1sPg0K"
                    }]
                },
                "startTime": "2019-02-19T01:30:16.134Z",
                "finishTime": "2019-02-19T01:30:26.728Z"
            }
        };
        //location.href='<%=path%>/wt/reg/analyticalXk.html?paramsXk='+paramsXk;
        /* },
         error: function (e) {
         alert(e);
         }
         });*/


        /*$.ajax({
            url: "<%=path%>/wt/reg/analyticalXk.html",
            type: "post",
            data: {'paramsXk': JSON.stringify(paramsXk)},
            dataType: "json",
            success: function (data) {
                console.log("成功");
            }
        });*/

    })

</script>
<!-- END JS -->
</body>
</html>


