<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/template" id="dishTpl">
  {@each list as it}
  <li class="dish clearfix"  data-dish-id="&{it.dishId}">
    <a href="${website}mobile/dish/detail/&{it.dishId}" class="dish-img"><img class="lazy-load" src="${staticWebsite}img/mobile/common/default-img.png" data-original="${tinyWebsite}&{it.src}" data-lazy-container=".dish-list" alt="&{it.name}"></a>
    <p class="dish-name">&{it.name}</p>
    <p class="dish-like"><i class="fa fa-thumbs-up"></i>&nbsp;<span class="dish-like-number">146</span></p>
    <div class="add-dish J_addDishTrigger" data-dish-number="&{it.number}">
      点餐
      <span class="dish-number J_dishNumber hidden"></span>
    </div>
    <div class="dish-info clearfix">
      <%--<p class="dish-star">--%>
        <%--<i class="fa fa-star active"></i>--%>
        <%--<i class="fa fa-star active"></i>--%>
        <%--<i class="fa fa-star active"></i>--%>
        <%--<i class="fa fa-star active"></i>--%>
        <%--<i class="fa fa-star"></i>--%>
      <%--</p>--%>
      <p class="dish-price-sale">
        <span class="dish-price">￥&{it.price}</span>
        <span class="dish-sale">￥&{it.sale}</span>
      </p>
    </div>
  </li>
  {@/each}
</script>
<script type="text/javascript">
  KISSY.ready(function(S){
    S.use('page/classify-img', function(){
      PW.page.ClassifyImg({});
    });
  });
</script>