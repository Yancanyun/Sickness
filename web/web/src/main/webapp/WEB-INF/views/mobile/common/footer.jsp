<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="footer clearfix" id="footer">
    <ul class="clearfix menu-list">
        <li class="active oper"><a href="#"><i class="fa fa-list list-trigger"></i><span>菜品选择</span></a></li>
        <li class="oper J_subMenuListTrigger">
            <i class="fa fa-heart-o list-trigger"></i><span>猜你喜欢</span>
            <ul class="call-service-list J_subMenuList">
                <li><a href="">为您推荐</a></li>
                <li><a href="#">历史消费</a></li>
                <li><a href="${website}mobile/dish/prefer/cheap/list">今日特价</a></li>
                <li><a href="${website}mobile/dish/prefer/rank/list">销量排行</a></li>
                <li><a href="${website}mobile/dish/prefer/feature/list">本店特色</a></li>
            </ul>
        </li>
        <li class="my-order J_myOrder oper"><a href="#"><i class="fa fa-file-text-o list-trigger"></i><span>我的订单</span><em class="dish-total-number J_dishTotalNumber hidden" data-dish-total-number="5"></em></a></li>
        <li class="oper J_subMenuListTrigger J_getServiceList">
            <i class="fa fa-phone list-trigger"></i><span>呼叫服务</span>
            <ul class="call-service-list J_subMenuList">
              <%--  <c:forEach var="callWaiter" items="${callWaiter}">
                    <c:choose>
                        <c:when test="${callWaiter.status eq 1}">
                            <li class="J_callService">${callWaiter.name}</li>
                        </c:when>
                    </c:choose>
                </c:forEach>--%>
            </ul>
        </li>
    </ul>
    <input type="hidden" class="J_tableId" name="tableId" value="1">
</div>
<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('module/footer', function(){});
    });
</script>
<script type="text/javascript" src="${staticWebsite}js/mobile/module/loading.js"></script>
