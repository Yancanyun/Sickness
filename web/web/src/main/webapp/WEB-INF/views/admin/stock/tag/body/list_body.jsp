<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">库存管理</a></li>
            <li class="active">库存分类管理</li>
        </ol>
        <h2>库存分类管理-库存分类列表</h2>
    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>库存分类列表</h4>
            </div>
            <div class="panel-body">
                <form class="J_operForm">
                    <a class="btn btn-success margin-bottom-15 J_addBigTag" href="javascript:;"><i
                            class="fa fa-plus"></i>&nbsp;添加库存分类</a>
                    <ul class="classify J_classify">
                        <c:forEach var="dishTagDto" items="${tagDtoList}">
                            <li class="clearfix" data-big-tag-id="${dishTagDto.tag.id}" data-root-tag-id="2" data-root-tag-name="顶级分类"
                                data-big-tag-name="${dishTagDto.tag.name}">
                                <div class="J_inputGroup">
                                    <input type="hidden" name="pId" value="${dishTagDto.tag.pId}"/>
                                    <input type="hidden" name="id" value="${dishTagDto.tag.id}"/>
                                    <input type="hidden" name="name" value="${dishTagDto.tag.name}"/>
                                </div>
                                <span class="root-tag">[顶级分类]</span>
                                <span class="big-tag">${dishTagDto.tag.name}</span>
                                <a href="javascript:;" class="J_foldToggle">展开 <<</a>
                                <a href="javascript:;" class="label-info pull-right oper J_delBigTag"><i
                                        class="fa fa-times"></i>&nbsp;删除大类</a>
                                <a href="javascript:;" class="label-info pull-right oper J_editBigTag"><i
                                        class="fa fa-pencil"></i>&nbsp;编辑大类</a>
                                <a href="javascript:;" class="label-info pull-right oper J_addSmallTag"><i
                                        class="fa fa-plus"></i>&nbsp;添加小类</a>
                                <ul style="display: none;" class="margin-top-20 J_smallClassify" data-big-tag-id="${dishTagDto.tag.id}"
                                    data-big-tag-name="${dishTagDto.tag.name}">
                                    <c:forEach var="dto" items="${dishTagDto.childTagList}">
                                        <li class="clearfix" data-small-tag-name="${dto.tag.name}" data-small-tag-id="${dto.tag.id}">
                                            <input type="hidden" name="pId" value="${dto.tag.pId}"/>
                                            <input type="hidden" name="id" value="${dto.tag.id}"/>
                                            <input type="hidden" name="name" value="${dto.tag.name}"/>
                                            <span class="small-tag">${dto.tag.name}</span>
                                            <a href="javascript:;" class="label-info pull-right oper J_delSmallTag"><i
                                                    class="fa fa-times"></i>&nbsp;删除小类</a>
                                            <a href="javascript:;" class="label-info pull-right oper J_editSmallTag"><i
                                                    class="fa fa-pencil"></i>&nbsp;编辑小类</a>
                                        </li>
                                    </c:forEach>

                                </ul>
                            </li>
                        </c:forEach>
                    </ul>
                </form>
            </div>
        </div>
    </div>
</div>