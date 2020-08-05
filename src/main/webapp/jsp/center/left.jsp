<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/3/24
  Time: 11:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
    String path = request.getContextPath();
%>
<html>
<head>
    <title>博安智联LIMS - 毕节市公安局DNA实验室信息管理系统</title>
    <%@ include file="../common/link.jsp" %>
</head>
<body>
<section id="container">
    <aside>
            <div id="sidebar" class="nav-collapse">
            <ul class="sidebar-menu" id="nav-accordion">
                <li>
                    <a id="homePageLink" href="<%=path%>/center/main.html" target="rightFrame" class="active">
                        <i class="fa fa-dashboard"></i>
                        <span>主 页</span>
                    </a>
                </li>

                <c:forEach items="${rootPermissionList}" var="root" varStatus="rp">
                    <c:choose>
                        <c:when test="${root.id eq 700}">
                            <li>
                                <a id="Testimonial" href="<%=path%>/center/4/01.html" target="rightFrame">
                                    <i class="fa fa-file-text-o"></i>
                                    <span>鉴定书管理</span>
                                </a>
                            </li>
                        </c:when>
                        <c:otherwise>
                            <li class="sub-menu">
                                <a href="javascript:;">
                                    <i class="fa ${root.iconStyle}"></i>
                                    <span>${root.permissionName}</span>
                                </a>
                                <ul class="sub">
                                    <c:forEach items="${childPermissionList}" var="child" varStatus="cp">
                                        <c:if test="${child.parentId eq root.id}">
                                            <li>
                                                <a href="<%=path%>/${child.permissionLink}" target="rightFrame">${child.permissionName}</a>
                                            </li>
                                        </c:if>
                                    </c:forEach>
                                </ul>

                            </li>
                        </c:otherwise>
                    </c:choose>
                </c:forEach>

            </ul>
        </div>
    </aside>
</section>

<%@ include file="../common/script.jsp" %>
<script>
    $(function () {
        'use strict';
        $('#nav-accordion').dcAccordion({
            eventType: 'click',
            autoClose: true,
            saveState: true,
            disableLink: true,
            speed: 'slow',
            showCount: false,
            autoExpand: true,
            classExpand: 'dcjq-current-parent'
        });

        $('#sidebar .sub-menu > a').click(function () {
            var o = ($(this).offset());
            //   diff = 250 - o.top;
            var offset = o.top - $('#sidebar').height() / 2;
            offset=offset+150;
            // alert(offset);
            $('#sidebar').animate({scrollTop: offset}, 500);
        });

        $(".sub > li > a").click(function(){
            $("li",$(this).parents('ul')).removeClass("active");
            $(this).parent('li').addClass("active");
        });

        $('.sub li a').each(function(){
            alert("S" + $($(this))[0].href);
            if($($(this))[0].href==String(window.location))
                $(this).parent().addClass('active');
        });

    });
</script>
</body>
</html>
