<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<%
    String path = request.getContextPath();
%>
<div class="modal fade" id="profileModal" aria-hidden="true" style="z-index:999999;">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title">
                    修改登录密码
                </h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal tasi-form">
                    <div class="form-group">
                        <label class="control-label col-md-3">旧密码 <i class="fa fa-asterisk color_red"></i></label>
                        <div class="col-md-5">
                            <input id="oldPassword" type="password" class="form-control small required" value=""/>
                        </div>
                        <div class="col-md-2 has-error hide">
                            <p class="help-block">必填项</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3">新密码 <i class="fa fa-asterisk color_red"></i></label>
                        <div class="col-md-5">
                            <input id="newPassword" type="password" class="form-control small required" value=""/>
                        </div>
                        <div class="col-md-2 has-error hide">
                            <p class="help-block">必填项</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-3">再次输入新密码 <i class="fa fa-asterisk color_red"></i></label>
                        <div class="col-md-5">
                            <input id="confirmNewPassword" type="password" class="form-control small required" value=""/>
                        </div>
                        <div class="col-md-2 has-error hide">
                            <p class="help-block">必填项</p>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="control-label col-md-8" id="messagLabel" style="text-align:center; font-weight:bold; font-size:16px;"></label>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button class="btn btn-success" type="button" id="SaveDelegatePasswordBtn">确定</button>
                <input type="hidden" id="updatePasswordUrl" value="<%=path%>/wt/updatePassword.html"/>
                <button data-dismiss="modal" class="btn btn-default" type="button">关闭</button>
            </div>
        </div>
    </div>
</div>
