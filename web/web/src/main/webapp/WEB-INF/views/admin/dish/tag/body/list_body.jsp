<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">菜品管理</a></li>
            <li class="active">菜品分类管理</li>
        </ol>
        <h2>菜品管理-菜品分类列表</h2>
    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>搜索</h4>
            </div>
            <div class="panel-body">
                <form class="form-horizontal J_searchForm">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">选择菜品总分类</label>
                        <div class="col-sm-3">
                            <select class="form-control" name="rootId">
                                <option value="0">全部</option>
                                <option value="3">菜品</option>
                                <option value="5">酒水</option>
                                <option value="4">商品</option>
                                <option value="6">套餐</option>
                                <option value="7">其他</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-6 col-sm-offset-3">
                        <div class="btn-toolbar">
                            <button class="btn-primary btn J_search" type="submit"><i class="fa fa-search"></i>&nbsp;搜索</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>菜品分类列表</h4>
            </div>
            <div class="panel-body">
                <form class="J_operForm" autocomplete="off">
                    <a class="btn btn-success margin-bottom-15 J_addBigTag" href="javascript:;"><i class="fa fa-plus"></i>&nbsp;添加菜品大类</a>
                    <ul class="classify J_classify">
                        <c:forE ach var="tagDto" items="${tagDtoMap}">
                        <li class="clearfix" data-big-tag-id="${tagDto.key.tag.id}" data-root-tag-id="${tagDto.key.tag.pId}"
                            data-root-tag-name="<c:choose>
                                    <c:when test="${tagDto.key.tag.pId == 3}">
                                        菜品
                                    </c:when>
                                    <c:when test="${tagDto.key.tag.pId == 4}">
                                        商品
                                    </c:when>
                                    <c:when test="${tagDto.key.tag.pId == 5}">
                                        酒水
                                    </c:when>
                                    <c:when test="${tagDto.key.tag.pId == 6}">
                                        套餐
                                    </c:when>
                                    <c:when test="${tagDto.key.tag.pId == 7}">
                                        其他
                                    </c:when>
                                    <c:otherwise>
                                        未知
                                    </c:otherwise>
                                </c:choose>" data-big-tag-name="${tagDto.key.tag.name}">
                            <div class="J_inputGroup">
                                <input type="hidden" name="pId" value="${tagDto.key.tag.pId}" />
                                <input type="hidden" name="id" value="${tagDto.key.tag.id}" />
                                <input type="hidden" name="name" value="${tagDto.key.tag.name}" />
                                <input type="hidden" name="weight" value="${tagDto.key.tag.weight}" />
                                <input class="J_printAfterConfirmOrder" type="hidden" name="printAfterConfirmOrder" value="${tagDto.key.tag.printAfterConfirmOrder}" />
                                <input type="hidden" name="maxPrintNum" value="${tagDto.key.tag.maxPrintNum}" />
                                <input class="J_printId" type="hidden" name="printerId" value="${tagDto.value}" />
                                <input type="hidden" name="timeLimit" value="${tagDto.key.tag.timeLimit}" />
                                <input class="J_remarkIds" type="hidden" name="remarkId" value="${remarkId}" />
                                <input type="hidden" name="type" value="1" />
                            </div>
                            <span class="root-tag">
                                <c:choose>
                                    <c:when test="${tagDto.key.tag.pId == 3}">
                                        [菜品]
                                    </c:when>
                                    <c:when test="${tagDto.key.tag.pId == 4}">
                                        [商品]
                                    </c:when>
                                    <c:when test="${tagDto.key.tag.pId == 5}">
                                        [酒水]
                                    </c:when>
                                    <c:when test="${tagDto.key.tag.pId == 6}">
                                        [套餐]
                                    </c:when>
                                    <c:when test="${tagDto.key.tag.pId == 7}">
                                        [其他]
                                    </c:when>
                                    <c:otherwise>
                                        [未知]
                                    </c:otherwise>
                                </c:choose>
                            </span>
                            <span class="big-tag">${tagDto.key.tag.name}</span>
                            <a class="J_foldToggle" href="javascript:;"></a>
                            <a href="javascript:;" class="label-info pull-right oper J_delBigTag"><i class="fa fa-times"></i>&nbsp;删除大类</a>
                            <a href="javascript:;" class="label-info pull-right oper J_editBigTag"><i class="fa fa-pencil"></i>&nbsp;编辑大类</a>
                            <a href="javascript:;" class="label-info pull-right oper J_addSmallTag"><i class="fa fa-plus"></i>&nbsp;添加小类</a>
                            <ul style="display: none;" class="margin-top-20 J_smallClassify" data-big-tag-id="${tagDto.key.tag.id}" data-big-tag-name="${tagDto.key.tag.name}">
                                <c:forEach var="childTagDto" items="${childrenTagDtoMap}">
                                    <c:if test="${childTagDto.key.tag.pId==tagDto.key.tag.id}">
                                <li class="clearfix" data-small-tag-name="${childTagDto.key.tag.name}" data-small-tag-id="${childTagDto.key.tag.id}">
                                    <input type="hidden" name="pId" value="${childTagDto.key.tag.pId}" />
                                    <input type="hidden" name="id" value="${childTagDto.key.tag.id}" />
                                    <input type="hidden" name="name" value="${childTagDto.key.tag.name}" />
                                    <input type="hidden" name="weight" value="${childTagDto.key.tag.weight}" />
                                    <input class="J_printAfterConfirmOrderSmall" type="hidden" name="printAfterConfirmOrder" value="${childTagDto.key.tag.printAfterConfirmOrder}" />
                                    <input type="hidden" name="maxPrintNum" value="${childTagDto.key.tag.maxPrintNum}" />
                                    <input class="J_printId" type="hidden" name="printerId" value="${childTagDto.value}" />
                                    <input type="hidden" name="timeLimit" value="${childTagDto.key.tag.timeLimit}" />
                                    <input type="hidden" name="type" value="1" />
                                    <span class="small-tag">${childTagDto.key.tag.name}</span>
                                    <a href="javascript:;" class="label-info pull-right oper J_delSmallTag"><i class="fa fa-times"></i>&nbsp;删除小类</a>
                                    <a href="javascript:;" class="label-info pull-right oper J_editSmallTag"><i class="fa fa-pencil"></i>&nbsp;编辑小类</a>
                                </li>
                                    </c:if>
                                </c:forEach>
                            </ul>
                        </li>
                        </c:forEach>
                    </ul>
                    <select class="form-control hidden J_bigClassifyHidden" disabled="disabled" name="pId">
                        <option value="3">菜品</option>
                        <option value="5">酒水</option>
                        <option value="4">商品</option>
                        <option value="6">套餐</option>
                        <option value="7">其他</option>
                    </select>
                </form>
            </div>
        </div>
    </div>
</div>
<!-- 存放备注的div -->
<div class="checkbox block hidden J_remarkHidden">
    <c:forEach items="${remarkTags}" var="remarkTag">
    <label><input type="checkbox" class="J_selectAll"name="remarkId" value="${remarkTag.id}">${remarkTag.name}</label>
    </c:forEach>
   <%-- <label><input type="checkbox" class="J_select" name="remarkId" value="2">多辣</label>
    <label><input type="checkbox" class="J_select" name="remarkId" value="3">少辣</label>--%>
</div>