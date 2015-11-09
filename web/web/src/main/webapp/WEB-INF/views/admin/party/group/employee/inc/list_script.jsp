<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<script type="text/template" id="tooltipTpl">
  {@each list as it}
       <p>
           <span>&{it.area}</span>:
           <span>&{it.tables}</span>
       </p>
  {@/each}
</script>
<script type="text/template" id="searchTpl">
  {@each list as it}
  <tr data-employee-id="&{it.id}" data-party-id = "&{it.partyId}">
    <td>&{it.loginName}</td>
    <td>&{it.employeeNumber}</td>
    <td>&{it.name}</td>
    <td>&{it.phone}</td>
    <td>
      {@each it.roles as item}
      {@if item == 1}管理员{@/if}
      {@if item == 2}吧台{@/if}
      {@if item == 3}后厨{@/if}
      {@if item == 4}<a class="J_waiter" href="javascript:;" >服务员</a>{@/if}
      {@if item == 5}顾客{@/if}
      {@/each}
    </td>
    <td data-employee-status="&{it.status}" class="J_status">
      {@if it.status == 1}启用{@/if}
      {@if it.status == 2}禁用{@/if}
    </td>
    <td>
      <a href="#" class="label-info"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
      {@if it.status == 1}
      <a href="javascript:;" class="label-info J_convert"><i class="fa fa-check"></i>&nbsp;启用</a>
      {@/if}
      {@if it.status == 2}
      <a href="javascript:;" class="label-info J_convert"><i class="fa fa-circle"></i>&nbsp;禁用</a>
      {@/if}
      <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
    </td>
  </tr>
  {@/each}
</script>
<script type="text/javascript">
  KISSY.ready(function(S){
    S.use('page/user-management/employee-management-list', function(S){
      PW.page.UserManagement.EmployeeManagement.List({});
    });
  });
</script>