<%--
  Created by IntelliJ IDEA.
  User: Karl_SC
  Date: 2017/3/17
  Time: 11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!--#include file="/pages/admin/common/footer.html"-->
<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('page/dish-management/specification-management-add', function(S){
            PW.page.DishManagement.SpecificationManagementAdd();
        });
    });
</script>