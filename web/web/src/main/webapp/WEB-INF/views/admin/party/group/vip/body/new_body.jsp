<%--
  User: chenyuting
  Time: 2015/11/3 09:54
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
      <li class="active">会员管理</li>
    </ol>
    <h2>会员管理-添加会员</h2>
  </div>
  <div class="col-sm-12">
    <form class="form-horizontal J_addForm" action="${website}admin/party/group/vip/new" method="post">
      <c:if test="{!empty msg}">
        <div class="alert alert-danger J_tip" role="alert">${msg}</div>
      </c:if>
      <div class="panel panel-info">
        <div class="panel-heading">
          <h4>添加</h4>
        </div>
        <div class="panel-body">
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>姓名</label>
            <div class="col-sm-6">
              <input class="w190" type="text" data-valid-tip="请输入姓名|姓名不能为空，请重新输入" data-valid-rule="notNull" value="" name="name" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">性别</label>
            <div class="col-sm-6">
              <div class="radio">
                <label>
                  <input type="radio" value="1" name="sex">男
                </label>
                <label>
                  <input type="radio" value="2" name="sex">女
                </label>
                <label>
                  <input type="radio" value="0" name="sex" checked="checked">未说明
                </label>
              </div>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">生日</label>
            <div class="col-sm-2">
              <input class="w190 date" readonly value="" name="birthday" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>手机号</label>
            <div class="col-sm-6">
              <input class="w190 J_phone" type="text" data-valid-tip="请输入员工电话|电话输入有误，请重新输入" data-valid-rule="notNull&isMobile" value="" name="phone" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">qq</label>
            <div class="col-sm-6">
              <input class="w190" type="text" data-valid-tip="请输入QQ号|填写有误，请重新输入" data-valid-rule="isNull|isQQ" value="" name="qq" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">邮箱</label>
            <div class="col-sm-6">
              <input class="w190" type="text" data-valid-tip="请输入邮箱|填写有误，请重新输入" data-valid-rule="isNull|isEmail" value="" name="email" />
            </div>
          </div>
        </div>
        <div class="panel-footer">
          <div class="row">
            <div class="col-sm-6 col-sm-offset-3">
              <div class="btn-toolbar">
                <button class="btn-primary btn J_submitBtn" type="submit" data-btn-type="loading" data-btn-loading-text="正在保存，请稍后">
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
