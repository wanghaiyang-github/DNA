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


            <div id="geneDetailDiv" class="row">
                <div class="col-lg-12">
                    <section class="panel">
                        <header class="panel-heading">
                            <span class="label label-primary">基因分型</span>
                           <span class="tools pull-right">
                           <a href="javascript:;" class="fa fa-chevron-down"></a>
                           </span>
                        </header>
                        <div class="panel-body">
                            <div class="space15" style="height: 5px;"></div>
                            <div class="form-group">
                                <div class="col-sm-11">
                                    <div class="space15" style="height: 5px;"></div>
                                    <table class="table table-striped table-advance table-bordered table-hover" style="table-layout: fixed;width: 50%">
                                        <thead>
                                        <tr>
                                            <th>序号</th>
                                            <th>基因座名称</th>
                                            <th>基因型1</th>
                                            <th>基因型2</th>
                                            <th>基因型3</th>
                                            <th>基因型4</th>
                                        </tr>
                                        </thead>
                                        <tbody id="panelTbody">
                                        <c:forEach items="${sampleGeneList}" var="sampleGene" varStatus="s">
                                            <textarea class="hide">${sampleGene.geneInfo}</textarea>
                                        </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>


                            <div class="form-group">
                                <div class="col-lg-offset-6 col-lg-10">
                                    <button class="btn btn-lg btn-info" id="backBtn" name="backBtn" type="button" ><i class="fa fa-reply"></i> 返 回 </button>
                                </div>
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
    $(function() {
        'use strict';

        function viewGeneDetail(geneInfo) {
            if(typeof(geneInfo)!="undefined" && geneInfo!=0){

                var jsonInfo = $.parseJSON(geneInfo);
                var len = jsonInfo.length;
                var gene;
                $("#panelTbody").empty();
                var htmlStr = "";
                for(var i = 0; i < len; i++){
                    gene = jsonInfo[i];
                    htmlStr += "<tr><td>"+(i + 1)+"</td>";
                    htmlStr += "<td><input type='hidden' name='markerName' id='markerName' value="+ gene.geneName +">" + gene.geneName + "</td>";
                    htmlStr += "<td><input type='text' name='gene1' id='gene1' class='form-control' value="+ gene.geneVal1 +"></td>";
                    htmlStr += "<td><input type='text' name='gene2' id='gene2' class='form-control' value="+ gene.geneVal2 +"></td>";
                    htmlStr += "<td><input type='text' name='gene3' id='gene3' class='form-control' value="+ gene.geneVal3 +"></td>";
                    htmlStr += "<td><input type='text' name='gene4' id='gene4' class='form-control' value="+ gene.geneVal4 +"></td>";
                    htmlStr +="</tr>";
                }
                $("#panelTbody").append(htmlStr);
            }
        }

        viewGeneDetail($("textarea.hide").val());


        $("button[name='backBtn']").on('click', function () {
            location.href = '<%=path%>/center/jk/queryJkGeneList.html';
        });

    });
</script>
<!-- END JS -->
</body>
</html>
