<%--
  User: chenyuting
  Time: 2015/11/3 09:54
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
      <li><a href="#">用户信息管理</a></li>
      <li class="active">会员管理</li>
    </ol>
    <h2>会员管理-编辑会员</h2>
  </div>
  <div class="col-sm-12">
    <form class="form-horizontal J_addForm" action="${website}admin/party/group/vip/update/{id}" method="post">
      <div class="panel panel-info">
        <div class="panel-heading">
          <h4>编辑</h4>
        </div>
        <div class="panel-body">
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>姓名</label>
            <div class="col-sm-6">
              <input class="w180" type="text" data-valid-tip="请输入姓名|姓名不能为空，请重新输入" data-valid-rule="notNull" value="${vipInfo.name}" name="name" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">性别</label>
            <div class="col-sm-6">
              <div class="radio">
                <label>
                  <input type="radio" value="0" name="sex" checked="${vipInfo.sex==0?'checked':''}">未说明
                </label>
                <label>
                  <input type="radio" value="1" name="sex" checked="${vipInfo.sex==1?'checked':''}">男
                </label>
                <label>
                  <input type="radio" value="2" name="sex" checked="${vipInfo.sex==2?'checked':''}">女
                </label>
              </div>
            </div>
          </div>
          <div class="form-group">
            <label class="control-label">生日</label>
            <div class="col-sm-2">
              <input class="w180 date" readonly value="${birthday}" name="birthday" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>手机号</label>
            <div class="col-sm-6">
              <input class="w180 J_phone" type="text" data-valid-tip="请输入员工电话|电话输入有误，请重新输入" data-valid-rule="notNull&isMobile" value="${vipInfo.phone}" name="phone" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">qq</label>
            <div class="col-sm-6">
              <input class="w180" type="text" data-valid-tip="请输入QQ号|填写有误，请重新输入" data-valid-rule="isNull|isQQ" value="${vipInfo.qq}" name="qq" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label">邮箱</label>
            <div class="col-sm-6">
              <input class="w180" type="text" data-valid-tip="请输入邮箱|填写有误，请重新输入" data-valid-rule="isNull|isEmail" value="${vipInfo.email}" name="email" />
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
