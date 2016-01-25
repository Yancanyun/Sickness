<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/1/25
  Time: 18:52
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<script type="text/template" id="addTpl">
  <tr data-type-num="&{payTypeNum}" >
    <td>&{payType}</td>
    <td>
      <input type="text" class="w180 h20" data-valid-rule="isNumber & notNull" name="&{inputName}" value="">&nbsp;&nbsp;元&nbsp;&nbsp;=&nbsp;&nbsp;1积分
    </td>
    <td>
      <a href="javascript:;" class="label-info J_cancel"><i class="fa fa-times"></i>&nbsp;取消</a>
    </td>
  </tr>
</script>
<script type="text/javascript">
  KISSY.ready(function(S){
    S.use('page/vip-management/vip-points-management',function(){
      PW.page.VipPointsManagement.Core();
    })
  });
</script>