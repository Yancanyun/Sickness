<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
      <li><a href="#">用户管理</a></li>
      <li class="active">员工管理</li>
    </ol>
    <h2>员工管理-员工信息编辑</h2>
    <c:if test="${!empty msg}">
      <div class="alert alert-danger J_tip" role="alert">${msg}</div>
    </c:if>

  </div>
  <div class="col-sm-12 margin-bottom-30">
    <form class="form-horizontal J_operForm" action="${website}admin/party/group/employee/update" method="post">
      <div class="panel panel-info">
        <div class="panel-heading">
          <h4>员工信息编辑</h4>
        </div>
        <div class="panel-body">
          <input class="J_partyId" type="hidden" name="partyId" value="${employeeDto.employee.partyId}"  data-oper-type="0"/>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>用户名</label>
            <div class="col-sm-6">
              <input class="w180 J_username" type="text" value="${employeeDto.loginName}" name="loginName" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>员工编号</label>
            <div class="col-sm-6">
              <input class="w180 J_employeeNo" type="text" value="${employeeDto.employee.employeeNumber}" name="employeeNumber" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>姓名</label>
            <div class="col-sm-6">
              <input class="w180" type="text" data-valid-tip="请输入姓名|姓名不能为空，请重新输入" data-valid-rule="notNull" value="${employeeDto.employee.name}" name="name" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>电话</label>
            <div class="col-sm-6">
              <input class="w180 J_phone" type="text" value="${employeeDto.employee.phone}" name="phone" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>密码<!-- 新密码--> </label>
            <div class="col-sm-6">
              <input class="w180 J_passward" type="password" id="pwd" data-valid-tip="请输入密码|密码有误，请重新输入" data-valid-rule="isPassword&length(5,16) | isNull " value="" name="password" />
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>确认密码 <!--确认新密码--></label>
            <div class="col-sm-6">
              <input class="w180 J_confirmPwd" type="password" value="" name="" />
              <span></span>
            </div>
          </div>
          <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>角色</label>
            <div class="col-sm-6">
              <c:forEach var="role" items="${roleList}">
                <c:if test="${role.id != 4}">
                  <div class="checkbox block">
                    <label>
                      <input type="checkbox" value="${role.id}" name="roles" <c:if test="${roleMap[role.id] == 1}">checked="checked"</c:if>> ${role.name}
                    </label>
                  </div>
                </c:if>
                <c:if test="${role.id == 4}">
                  <div class="checkbox block">
                    <label>
                      <input class="J_waiter" type="checkbox" value="${role.id}" name="roles" <c:if test="${roleMap[role.id] == 1}"> checked="checked"</c:if>> ${role.name}
                    </label>
                    <div class="waiter-table J_waiterTable hidden">
                      <c:forEach var="areaDto" items="${areaDtoList}">
                        <div class="area-table clearfix">
                          <span>${areaDto.area.name}:</span>
                          <c:forEach var="tables" items="${areaDto.tableList}">
                            <label>
                              <input type="checkbox" value="${tables.id}" name="tables" <c:if test="${tableMap[tables.id] == 1}">checked="checked"</c:if>>${tables.name}
                            </label>
                          </c:forEach>
                        </div>
                      </c:forEach>
                    </div>
                  </div>
                </c:if>
              </c:forEach>
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