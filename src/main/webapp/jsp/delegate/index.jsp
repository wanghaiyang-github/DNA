<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
  String path = request.getContextPath();
%>
<!DOCTYPE html>
<html>
  <head>
    <!-- BEGIN META -->
    <meta charset="utf-8">
    <!-- END META -->
    
     <!-- BEGIN SHORTCUT ICON -->
     <link rel="shortcut icon" href="<%=path%>/img/dna.ico">
     <!-- END SHORTCUT ICON -->
    <title>
      博安智联LIMS - 毕节市公安局DNA实验室信息管理系统
    </title>
     <!-- BEGIN STYLESHEET-->
		<link href="<%=path%>/css/bootstrap.min.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
		<link href="<%=path%>/css/bootstrap-reset.css" rel="stylesheet"><!-- BOOTSTRAP CSS -->
		<link href="<%=path%>/assets/font-awesome/css/font-awesome.css" rel="stylesheet"><!-- FONT AWESOME ICON CSS -->
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
  </head>
  <body>
    <!-- BEGIN SECTION -->
    <section id="container">
      <!-- BEGIN HEADER -->
      <jsp:include page="hearder.jsp"/>
      <!-- END HEADER -->

      <!-- BEGIN SIDEBAR -->
      <aside>
        <div id="sidebar" class="nav-collapse">
          <ul class="sidebar-menu" id="nav-accordion">
                <li>
                  <a href="<%=path%>/wt/reg/1.html" target="main-frame" class="active">
                    委托登记
                  </a>
                </li>
                <li>
                  <a href="<%=path%>/wt/caseinfo/listPending.html" target="main-frame">
                    未受理
                  </a>
                </li>
            <li>
              <a href="<%=path%>/wt/caseinfo/listAccepted.html" target="main-frame">
                已受理
              </a>
            </li>
            <li>
              <a href="<%=path%>/wt/caseinfo/listRefused.html" target="main-frame">
                已退案
              </a>
            </li>
                <li>
                  <a href="<%=path%>/wt/message/listIdentify.html" target="main-frame">
                    鉴定领取通知
                  </a>
                </li>
          </ul>
        </div>
      </aside>
      <!-- END SIDEBAR -->

      <section id="main-content">
        <!-- BEGIN WRAPPER  -->
        <section class="wrapper">
          <iframe name="main-frame" id="main-frame" frameborder="0" src="<%=path%>/wt/reg/1.html" scrolling="no"></iframe>
        </section>
      </section>

      <!-- BEGIN FOOTER -->
      <footer class="site-footer">
        <div class="text-center" style="margin-top:20px;">
          Copyright © 2016 北京博安智联科技有限公司
        </div>
      </footer>
      <!-- END  FOOTER -->
    </section>
    <!-- END SECTION -->
    <!-- BEGIN JS -->
    <script src="<%=path%>/js/jquery-1.8.3.min.js" ></script><!-- BASIC JQUERY 1.8.3 LIB. JS -->
    <script src="<%=path%>/js/bootstrap.min.js" ></script><!-- BOOTSTRAP JS -->
    <script src="<%=path%>/js/jquery.dcjqaccordion.2.7.js"></script><!-- ACCORDIN JS -->
    <script src="<%=path%>/js/jquery.scrollTo.min.js" ></script><!-- SCROLLTO JS -->
    <script src="<%=path%>/js/jquery.nicescroll.js" ></script><!-- NICESCROLL JS -->
    <script src="<%=path%>/js/respond.min.js" ></script><!-- RESPOND JS -->
    <script src="<%=path%>/js/jquery.sparkline.js"></script><!-- SPARKLINE JS -->
    <script src="<%=path%>/js/sparkline-chart.js"></script><!-- SPARKLINE CHART JS -->
    <script src="<%=path%>/js/common-scripts.js"></script><!-- BASIC COMMON JS -->

    <script>
      'use strict';
      $(function(){
        $("#main-frame").load(function () {
          var mainheight = $(this).contents().find("body").height() + 30;
          $(this).height(mainheight);
        });
      });
    </script>

    <!-- END JS -->
  </body>
</html>


