<%@ page contentType="text/html;charset=UTF-8" %>

<!--#include file="/pages/admin/common/footer.html"-->
<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('page/store-management/ingredients-management-edit', function(S){
            PW.page.StoreManagement.IngredientsManagementEdit();
        });
    });
</script>