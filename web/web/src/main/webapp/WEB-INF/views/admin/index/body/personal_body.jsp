<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li class="active">修改个人信息</li>
        </ol>
    </div>
    <div class="col-sm-12 margin-bottom-30">
        <c:if test="${!empty msg}">
            <div class="alert alert-danger col-sm-12 J_msg" role="alert">${msg}</div>
        </c:if>
        <form class="form-horizontal J_operForm" action="${website}admin/personal/information" method="POST">
            <div class="panel panel-info">
                <div class="panel-heading">
                    <h4>修改个人信息</h4>
                </div>
                <div class="panel-body">
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>电话</label>
                        <!-- 添加了手机号码验证 -->
                        <div class="col-sm-6">
                            <input class="w180 J_phone" type="text" data-valid-tip="请输入电话号码|电话号码输入有误，请重新输入" data-valid-rule="notNull&isMobile" value="${phone}" name="phone" />
                        </div>
                        <!-- modify -->
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>原密码</label>
                        <div class="col-sm-6">
                            <input class="w180" type="password" id="oldPassword" data-valid-tip="请输入密码|密码有误，请重新输入" data-valid-rule="isPassword&length(5,16)" value="" name="oldPassword" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>新密码</label>
                        <div class="col-sm-6">
                            <input class="w180" type="password" id="newPassword" data-valid-tip="请输入密码|密码有误，请重新输入" data-valid-rule="isPassword&length(5,16)" value="" name="newPassword" />
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-3 control-label"><span class="requires">*</span>确认密码 <!--确认新密码--></label>
                        <div class="col-sm-6">
                            <input class="w180" type="password" data-valid-tip="请再次输入一遍密码|两次密码不同，请重新输入" data-valid-rule="isPassword&require(newPassword)&equal(newPassword)" value="" name="confirmPassword" />
                        </div>
                    </div>
                </div>
                <div class="panel-footer">
                    <div class="row">
                        <div class="col-sm-6 col-sm-offset-3">
                            <div class="btn-toolbar">
                                <button class="btn-primary btn J_submitBtn" type="submit" data-btn-type="loading" data-btn-loading-text="正在保存，请稍后...">
                                    <i class="fa fa-save"></i>
                                    &nbsp;保存
                                </button>
                                <button class="btn-default btn" type="reset"><i class="fa fa-undo"></i>&nbsp;重置</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>