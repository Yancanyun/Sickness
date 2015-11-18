<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
      <li><a href="#">用户管理</a></li>
      <li class="active">员工管理</li>
    </ol>
    <h2>员工管理-员工列表</h2>
    <c:if test="${!empty msg}">
      <div class="alert alert-success J_tip" role="alert">${msg}</div>
    </c:if>
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>搜索</h4>
      </div>
      <div class="panel-body">
        <form class="form-horizontal J_searchForm">
          <div class="form-group">
            <label class="col-sm-3 control-label">选择角色</label>
            <div class="col-sm-6">
              <div class="checkbox block">
                <label>
                  <input class="J_selectAll"  type="checkbox" value="0" name="roles"> 全部
                </label>
                <c:forEach var="role" items="${roleList}">
                  <label>
                    <input class="J_roleType" type="checkbox" value="${role.id}" name="roles"> ${role.name}
                  </label>
                </c:forEach>
              </div>
            </div>
          </div>
          <div class="col-sm-6 col-sm-offset-3">
            <div class="btn-toolbar">
              <button class="btn-primary btn" type="submit"><i class="fa fa-search"></i>&nbsp;搜索</button>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>员工列表</h4>
      </div>
      <div class="panel-body">
        <a class="btn btn-success margin-bottom-15" href="${website}admin/party/group/employee/add"><i class="fa fa-plus"></i>&nbsp;添加员工</a>
        <form class="J_operForm">
          <div class="table-responsive">
            <table class="table table-hover table-bordered">
              <thead>
              <tr>
                <th>用户名</th>
                <th>员工编号</th>
                <th>姓名</th>
                <th>电话</th>
                <th>角色（悬浮查看详情）</th>
                <th>状态</th>
                <th>操作</th>
              </tr>
              </thead>
              <tbody id="J_template">

              <c:forEach var="employeeDto" items="${employeeDtoList}">
                <tr data-employee-id="1" data-party-id="${employeeDto.employee.partyId}">
                  <td>${employeeDto.loginName}</td>
                  <td>${employeeDto.employee.employeeNumber}</td>
                  <td>${employeeDto.employee.name}</td>
                  <td>${employeeDto.employee.phone}</td>
                  <td>
                    <c:forEach var="role" items="${employeeDto.roleName}">
                      <c:choose>
                        <c:when test="${role == '服务员'}">
                          <a class="J_waiter" href="javascript:;" >服务员</a>
                        </c:when>
                        <c:otherwise>
                          ${role}
                        </c:otherwise>
                      </c:choose>
                      &nbsp;
                    </c:forEach>
                  </td>
                  <td data-employee-status="${employeeDto.employee.status}" class="J_status">${employeeDto.status}</td>
                  <td>
                    <a href="${website}admin/party/group/employee/toupdate/${employeeDto.employee.partyId}" class="label-info"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
                    <c:if test="${employeeDto.employee.status ==2}">
                      <a href="javascript:;" class="label-info J_convert"><i class="fa fa-check"></i>&nbsp;启用</a>
                    </c:if>
                    <c:if test="${employeeDto.employee.status ==1}">
                      <a href="javascript:;" class="label-info J_convert"><i class="fa fa-check"></i>&nbsp;禁用</a>
                    </c:if>
                    <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
                  </td>
                </tr>
              </c:forEach>

            <%--  <tr data-employee-id="1">
                <td>alltogether</td>
                <td>alltogether</td>
                <td>18888888888</td>
                <td>
                  吧台
                  <a class="tool-tip" href="javascript:;" data-tooltip-toggle="tooltip" data-tooltip-placement="bottom" data-tooltip-text="">服务员</a>
                  后厨
                </td>
                <td data-employee-status="1" class="J_status">启用</td>
                <td>
                  <a href="#" class="label-info"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
                  <a href="javascript:;" class="label-info J_convert"><i class="fa fa-circle"></i>&nbsp;停用</a>
                  <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
                </td>
              </tr>
              <tr data-employee-id="2">
                <td>alltogether1</td>
                <td>alltogether1</td>
                <td>16666666666</td>
                <td>
                  后厨
                  <a class="tool-tip" href="javascript:;" data-tooltip-toggle="tooltip" data-tooltip-placement="bottom" data-tooltip-text="">服务员</a>
                </td>
                <td data-employee-status="0" class="J_status">停用</td>
                <td>
                  <a href="#" class="label-info"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
                  <a href="javascript:;" class="label-info J_convert"><i class="fa fa-check"></i>&nbsp;启用</a>
                  <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
                </td>
              </tr>--%>
              </tbody>
            </table>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>