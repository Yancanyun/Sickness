<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="${website}admin/restaurant">饭店管理</a></li>
            <li class="active">常用备注管理</li>
        </ol>
        <h2>常用备注管理-常用备注列表</h2>
    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>备注上级分类</h4>
            </div>
            <div class="panel-body clearfix">
                <div class="row">
                    <div class="col-sm-12">
                        <label>请选择常用备注的上级分类：</label>

                        <div class="big-remark-tags margin-top-15 margin-bottom-15 margin-left-50">
                            <input type="hidden" class="J_saveBigTagIdInp" name="saveBigTagId" value="">
                            <c:forEach var="bigTag" items="${bigTagList}">
                                <a class="label-info J_chooseBigRemarkTag" href="javascript:;"
                                   data-big-tag-id="${bigTag.id}"
                                   data-big-tag-name="${bigTag.name}"><i
                                        class="fa fa-bookmark-o"></i>&nbsp;${bigTag.name}</a>&nbsp;
                            </c:forEach>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>当前选择上级分类：<span class="J_bigTagInfo">${firstBigTag.name}</span></h4>
            </div>
            <div class="panel-body clearfix">
                <div class="row">
                    <div class="col-sm-12" data-big-tag-id="${firstBigTag.id}" data-big-tag-name="${firstBigTag.name}">
                        <a class="btn btn-success margin-bottom-15     margin-left-15 J_addSmallRemarkTag"
                           href="javascript:;"><i class="fa fa-plus"></i>&nbsp;添加备注分类</a>

                        <form class="J_operForm" autocomplete="off">
                            <div class="col-sm-12 table-responsive J_classify">
                                <c:forEach var="firstChildTagDto" items="${firstChildTagDtoList}">
                                    <div class="clearfix col-sm-12 remark-classify">
                                        <p class="col-sm-9 no-padding-left
												J_classifyName" data-big-tag-id="${firstBigTag.id}"
                                           data-big-tag-name="${firstBigTag.name}"
                                           data-small-tag-id="${firstChildTagDto.remarkTag.id}"
                                           data-small-tag-name="${firstChildTagDto.remarkTag.name}">
                                            备注分类名称 : ${firstChildTagDto.remarkTag.name}
                                            <a href="javascript:;" class="J_foldToggle"></a>
                                        </p>
                                        <!-- 备注分类隐藏域 -->
                                        <div class="J_remarkHiddenInp">
                                            <input type="hidden" name="pId" value="${firstChildTagDto.remarkTag.pId}">
                                            <input type="hidden" name="id" value="${firstChildTagDto.remarkTag.id}">
                                            <input type="hidden" name="name" value="${firstChildTagDto.remarkTag.name}">
                                        </div>
                                        <a href="javascript:;" class="label-info oper pull-right J_delSmallRemarkTag"><i
                                                class="fa fa-times"></i>&nbsp;删除</a>
                                        <a href="javascript:;"
                                           class="label-info oper pull-right J_editSmallRemarkTag"><i
                                                class="fa fa-pencil"></i>&nbsp;编辑</a>
                                        <a href="javascript:;" class="label-info oper pull-right J_addRemarkContent"><i
                                                class="fa fa-plus"></i>&nbsp;添加备注内容</a>
                                    </div>
                                        <table class="table table-hover table-bordered J_contentTable"
                                               data-small-tag-id="${firstChildTagDto.remarkTag.id}"
                                               data-small-tag-name="${firstChildTagDto.remarkTag.name}"
                                               style="display:none;">
                                            <thead>
                                            <th>备注内容</th>
                                            <th>权重</th>
                                            <th>关联收费</th>
                                            <th>是否常用</th>
                                            <th>操作</th>
                                            </thead>
                                            <tbody>
                                    <c:forEach var="remarkDto" items="${firstChildTagDto.remarkDtoList}">
                                            <tr data-remark-content-id="${remarkDto.remark.id}" data-remark-content="${remarkDto.remark.name}">
                                                <!-- 备注内容隐藏域 -->
                                                <input type="hidden" name="smallTagId" value="${remarkDto.remark.remarkTagId}">
                                                <input type="hidden" name="id" value="${remarkDto.remark.id}">
                                                <input type="hidden" name="name" value="${remarkDto.remark.name}">
                                                <input type="hidden" name="weight" value="${remarkDto.remark.weight}">
                                                <input type="hidden" name="relatedCharge" value="${remarkDto.remark.relatedCharge}">
                                                <input type="hidden" name="isCommon" value="${remarkDto.remark.isCommon}">
                                                <td class="col-sm-5 J_content">${remarkDto.remark.name}</td>
                                                <td class="col-sm-1 J_weight">${remarkDto.remark.weight}</td>
                                                <td class="col-sm-3 J_relatedCharge">${remarkDto.remark.relatedCharge}</td>
                                                <td class="col-sm-1 J_isCommon"><c:choose><c:when test="${remarkDto.remark.isCommon == 0}">否</c:when><c:otherwise>是</c:otherwise></c:choose></td>
                                                <td class="col-sm-2">
                                                    <a href="javascript:;"
                                                       class="label-info oper J_editRemarkContent"><i
                                                            class="fa fa-pencil"></i>&nbsp;编辑</a>
                                                    <a href="javascript:;" class="label-info oper J_delRemarkContent"><i
                                                            class="fa fa-times"></i>&nbsp;删除</a>
                                                </td>
                                            </tr>
                                    </c:forEach>
                                            </tbody>
                                        </table>
                                </c:forEach>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>