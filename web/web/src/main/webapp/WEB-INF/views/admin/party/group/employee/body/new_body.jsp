<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
      <li><a href="#">用户管理</a></li>
      <li class="active">员工管理</li>
    </ol>
    <h2>员工管理-添加员工</h2>
  </div>
  <div class="col-sm-12 margin-bottom-30">
    <form class="form-horizontal J_operForm" action="${website}admin/party/group/employee/new" method="post">
      <div class="panel panel-info">
        <div class="panel-heading">
          <h4>添加员工</h4>
        </div>
        <div class="panel-body">
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>用户名</label>
            <div class="col-sm-6">
              <input class="w180 J_username" type="text" value="" name="loginName" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>姓名</label>
            <div class="col-sm-6">
              <input class="w180" type="text" data-valid-tip="请输入姓名|姓名不能为空，请重新输入" data-valid-rule="notNull" value="" name="name" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>电话</label>
            <div class="col-sm-6">
              <input class="w180 J_phone" type="text" value="" name="phone" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>密码<!-- 新密码--> </label>
            <div class="col-sm-6">
              <input class="w180" type="password" id="pwd" data-valid-tip="请输入密码|密码有误，请重新输入" data-valid-rule="isPassword&length(7,16)" value="" name="password" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>确认密码 <!--确认新密码--></label>
            <div class="col-sm-6">
              <input class="w180" type="password" data-valid-tip="请再次输入一遍密码|两次密码不同，请重新输入" data-valid-rule="isPassword&require(pwd)&equal(pwd)" value="" name="" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>角色</label>
            <div class="col-sm-6">
              <div class="checkbox block">
                <label>
                  <input type="checkbox" value="11" name="role" > 吧台
                </label>
              </div>
              <div class="checkbox block">
                <label>
                  <input type="checkbox" value="12" name="role"> 后厨
                </label>
              </div>
              <div class="checkbox block">
                <label>
                  <input class="J_waiter" type="checkbox" value="13" name="role"> 服务员
                </label>
                <div class="waiter-table J_waiterTable hidden">
                  <div class="area-table clearfix">
                    <span>1楼:</span>
                    <label>
                      <input type="checkbox" value="1" name="table" checked="checked"> 1号桌
                    </label>
                    <label>
                      <input type="checkbox" value="2" name="table" checked="checked"> 2号桌
                    </label>
                  </div>
                  <div class="area-table clearfix">
                    <span>2楼:</span>
                    <label>
                      <input type="checkbox" value="1" name=""> 1号桌
                    </label>
                    <label>
                      <input type="checkbox" value="2" name=""> 2号桌
                    </label>
                    <label>
                      <input type="checkbox" value="2" name=""> 2号桌
                    </label>
                    <label>
                      <input type="checkbox" value="2" name=""> 2号桌
                    </label>
                    <label>
                      <input type="checkbox" value="2" name=""> 2号桌
                    </label>
                    <label>
                      <input type="checkbox" value="2" name=""> 2号桌
                    </label>
                    <label>
                      <input type="checkbox" value="2" name=""> 2号桌
                    </label>
                    <label>
                      <input type="checkbox" value="2" name=""> 2号桌
                    </label>
                    <label>
                      <input type="checkbox" value="2" name=""> 2号桌
                    </label>
                    <label>
                      <input type="checkbox" value="2" name=""> 2号桌
                    </label>
                    <label>
                      <input type="checkbox" value="2" name=""> 2号桌
                    </label>
                  </div>
                </div>
              </div>
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