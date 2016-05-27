<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">菜品管理</a></li>
            <li class="active">菜品管理</li>
        </ol>
        <h2>菜品管理-编辑菜品</h2>
        <c:if test="${code == 0}">
            <div class="alert alert-success J_tip">${msg}</div>
        </c:if>
        <c:if test="${code == 1}">
            <div class="alert alert-danger J_tip">${msg}</div>
        </c:if>
    </div>
    <div class="col-sm-12 margin-bottom-30">
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active">
                <a class="tab" data-tabpanel="#single" role="tab" data-toggle="tab">编辑菜品</a>
            </li>
            <li role="presentation" >
                <a class="tab" data-tabpanel="#batch" role="tab" data-toggle="tab">编辑菜品图片</a>
            </li>
        </ul>
        <div class="tab-content">
            <div role="tabpanel" class="tab-pane active" id="single">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h4>编辑菜品</h4>
                    </div>
                    <div class="panel-body">
                        <form class="form-horizontal J_operForm" autocomplete="off" action="${website}admin/dish" method="post">
                            <!--菜品id-->
                            <input type="hidden" value="put" name="_method"/>
                            <input class="J_id" type="hidden" name="id" value="${dishDto.id}">
                            <div class="">
                                <h4>添加菜品信息</h4>
                                <hr>
                                <div class="form-group">
                                    <!--联动级数,如果是三级联动,请刷3,并且刷3个select,如果是二级联动同理-->
                                    <input class="J_linkage" type="hidden" value="${categoryLayer}">
                                    <label class="col-sm-3 control-label"><span class="requires">*</span>选择分类</label>
                                    <div class="col-sm-2 no-padding-right">
                                        <select class="form-control J_rootClass w180" name="categoryId">
                                            <option value="3" <c:if test="${dishDto.categoryId == 3}">selected="selected"</c:if>>菜品</option>
                                            <option value="5" <c:if test="${dishDto.categoryId == 5}">selected="selected"</c:if>>酒水</option>
                                            <option value="4" <c:if test="${dishDto.categoryId == 4}">selected="selected"</c:if>>商品</option>
                                        </select>
                                    </div>
                                    <c:if test="${categoryLayer == 2}">
                                        <div class="col-sm-2 no-padding-right J_bigClassSelect">
                                            <select class="form-control J_bigClass w180" name="tagId">
                                                <c:forEach var="tag" items="${tagList}">
                                                    <option value="${tag.id}" <c:if test="${tag.id == dishDto.tagId}">selected="selected"</c:if>>${tag.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </c:if>
                                    <c:if test="${categoryLayer == 3}">
                                        <div class="col-sm-2 no-padding-right J_bigClassSelect">
                                            <!--如果是编辑页面, 就刷下方的下拉列表-->
                                            <select class="form-control J_bigClass w180" name="">
                                                <c:forEach var="tag" items="${bigTagList}">
                                                    <%--<c:if test="${tag.pId == dishDto.categoryId}">--%>
                                                        <option value="${tag.id}" <c:if test="${tag.id == bigTagId}">selected="selected"</c:if>>${tag.name}</option>
                                                    <%--</c:if>--%>
                                                </c:forEach>
                                            </select>
                                        </div>
                                        <div class="col-sm-2 no-padding-right J_smallClassSelect">
                                            <select class="form-control J_smallClass w180" name="tagId">
                                                <c:forEach var="tag" items="${smallTagList}">
                                                    <option value="${tag.id}" <c:if test="${tag.id == dishDto.tagId}">selected="selected"</c:if>>${tag.name}</option>
                                                </c:forEach>
                                            </select>
                                        </div>
                                    </c:if>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><span class="requires">*</span>菜品名称</label>
                                    <div class="col-sm-6">
                                        <input class="w180 J_name" type="text" data-valid-tip="请输入菜品名称|菜品名称不能为空，请重新输入" data-valid-rule="notNull" value="${dishDto.name}" name="name" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">菜品编号</label>
                                    <div class="col-sm-6">
                                        <input class="w180" type="text" value="${dishDto.dishNumber}" name="dishNumber" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">助记码</label>
                                    <div class="col-sm-6">
                                        <input class="w180 J_assistantCode" type="text" value="${dishDto.assistantCode}" name="assistantCode" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><span class="requires">*</span>单位</label>
                                    <div class="col-sm-2 no-padding-right">
                                        <select class="form-control J_unitClass w180">
                                            <option value="1" <c:if test="${dishDto.unitType == 1}">selected="selected"</c:if> >重量单位</option>
                                            <option value="2" <c:if test="${dishDto.unitType == 2}">selected="selected"</c:if> >数量单位</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 no-padding-right J_unitNumber <c:if test="${dishDto.unitType != 2}">hidden</c:if>">
                                        <select class="form-control J_unitNumberSelect w180" name="unitId" <c:if test="${dishDto.unitType != 2}">disabled="disabled"</c:if>>
                                            <c:forEach var="unit" items="${quantityUnitList}">
                                                <option value="${unit.id}" <c:if test="${unit.id == dishDto.unitId}">selected="selected"</c:if>>${unit.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 no-padding-right J_unitWeight <c:if test="${dishDto.unitType != 1}">hidden</c:if>">
                                        <select class="form-control J_unitWeightSelect w180" name="unitId" <c:if test="${dishDto.unitType != 1}">disabled="disabled"</c:if>>
                                            <c:forEach var="unit" items="${weightUnitList}">
                                                <option value="${unit.id}" <c:if test="${unit.id == dishDto.unitId}">selected="selected"</c:if>>${unit.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><span class="requires">*</span>定价</label>
                                    <div class="col-sm-6">
                                        <input class="w180 J_price" id="price" type="text" data-valid-tip="请输入定价|定价出现非数字，请重新输入" data-valid-rule="isFloat" value="${dishDto.price}" name="price" />&nbsp;元
                                    </div>
                                </div>
                            </div>
                            <div class="J_dishPromotion">
                                <h4 class="margin-top-30">促销价格</h4>
                                <hr>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><span class="requires">*</span>促销方式</label>
                                    <div class="col-sm-2 no-padding-right">
                                        <select class="form-control J_promotionType J_select w180" name="saleType">
                                            <option value="1" <c:if test="${dishDto.saleType == 1}">selected="selected"</c:if>>无促销</option>
                                            <option value="2" <c:if test="${dishDto.saleType == 2}">selected="selected"</c:if> data-name="discount" data-value="${dishDto.discount}" data-valid-rule="scale(0,10)" data-valid-tip="请输入大于0小于10的整数或小数，如6或6.5|折扣超范围，请重新输入">折扣</option>
                                            <option value="3" <c:if test="${dishDto.saleType == 3}">selected="selected"</c:if> data-name="salePrice" data-value="${dishDto.salePrice}" data-valid-rule="isFloat" data-valid-tip="请输入售价|售价不能为空，请重新输入">售价</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-6">
                                        <span class="J_noPromotion help-inline J_renderPosition"></span>
                                        <span class="help-inline J_promotionUnit"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="J_dishDetail">
                                <h4 class="margin-top-30">菜品详情</h4>
                                <hr>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">口味</label>
                                    <div class="col-sm-4 clearfix">
                                        <div id="basic" data-isEdit="true">
                                            <c:forEach var="taste" items="${selectTasteList}">
                                                <span class="J_tagEdit hidden" data-result-id="${taste.id}" data-result-name="${taste.name}" data-result-price=""></span>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">上菜时限</label>
                                    <div class="col-sm-6 clearfix">
                                        <input class="w180" type="text" name="timeLimit" value="${dishDto.timeLimit}">&nbsp;(0为无限制)
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">可点餐段</label>
                                    <div class="col-sm-6 clearfix">
                                        <div class="checkbox block">
                                            <c:forEach var="mealPeriod" items="${mealPeriodList}">
                                                <label>
                                                    <input class="J_selectAll"  type="checkbox" value="${mealPeriod.id}" name="mealPeriodIdList" <c:if test="${selectedMealPeriod[mealPeriod.id] == 1}">checked="checked"</c:if>> ${mealPeriod.name}
                                                </label>
                                            </c:forEach>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">打印机</label>
                                    <div class="col-sm-6 clearfix">
                                        <select class="form-control w180" name="printerId">
                                            <option value="-1">不选择,默认使用分类打印机</option>
                                            <c:forEach var="printer" items="${printerList}">
                                                <option value="${printer.id}" <c:if test="${dishDto.printerId == printer.id}">selected="selected"</c:if>>${printer.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">是否网络可点</label>
                                    <div class="col-sm-9">
                                        <div class="radio">
                                            <label>
                                                <input type="radio" value="1" name="isNetworkAvailable" <c:if test="${dishDto.isNetworkAvailable == 1}">checked="checked"</c:if> >是
                                            </label>
                                            <label>
                                                <input type="radio" value="0" name="isNetworkAvailable" <c:if test="${dishDto.isNetworkAvailable == 0}">checked="checked"</c:if>>否
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">是否使用会员价</label>
                                    <div class="col-sm-9">
                                        <div class="radio">
                                            <label>
                                                <input type="radio" value="1" name="isVipPriceAvailable" <c:if test="${dishDto.isVipPriceAvailable == 1}">checked="checked"</c:if>>是
                                            </label>
                                            <label>
                                                <input type="radio" value="0" name="isVipPriceAvailable" <c:if test="${dishDto.isVipPriceAvailable == 0}">checked="checked"</c:if>>否
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">是否可用代金卷</label>
                                    <div class="col-sm-9">
                                        <div class="radio">
                                            <label>
                                                <input type="radio" value="1" name="isVoucherAvailable" <c:if test="${dishDto.isVoucherAvailable == 1}">checked="checked"</c:if>>是
                                            </label>
                                            <label>
                                                <input type="radio" value="0" name="isVoucherAvailable" <c:if test="${dishDto.isVoucherAvailable == 0}">checked="checked"</c:if>>否
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <!--编辑页刷点赞人数-->
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">点赞人数</label>
                                    <div class="col-sm-9">
                                        <input class="w180" type="text"  name="likeNums" value="${dishDto.likeNums}"/>
                                    </div>
                                </div>
                                <div class="form-group dish">
                                    <label class="col-sm-3 control-label">菜品介绍</label>
                                    <div class="col-sm-6 clearfix">
                                        <textarea class="form-control" rows="6" name="description">${dishDto.description}</textarea>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="panel-footer">
                        <div class="row">
                            <div class="col-sm-6 col-sm-offset-3">
                                <div class="btn-toolbar">
                                    <button class="btn-primary btn J_submitBtn" type="submit" data-btn-type="loading" data-btn-loading-text="正在保存，请稍后">
                                        <i class="fa fa-save"></i>
                                        &nbsp;保存
                                    </button>
                                    <%--<button class="btn-default btn" type="reset"><i class="fa fa-undo"></i>&nbsp;重置</button>--%>
                                    <!--如果是编辑页面,请刷下面的按钮-->
                                    <button class="btn-default btn" type="button" onclick="history.go(-1);"><i class="fa fa-undo"></i>&nbsp;返回</button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <select class="selectpicker show-tick form-control hidden" data-live-search="true">
                <c:forEach var="taste" items="${tasteList}">
                    <option value="${taste.id}" data-price="" data-code="">${taste.name}</option>
                </c:forEach>
            </select>
            <div role="tabpanel" class="tab-pane" id="batch">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h4>编辑菜品大图</h4>
                    </div>
                    <div class="panel-body">
                        <div class="col-sm-12">
                            <div class="row">
                                <h4>当前菜品大图</h4>
                                <hr>
                                <c:forEach var="dishImg" items="${dishDto.bigImgList}">
                                    <div class="col-sm-3 J_imgContainer" data-img-id="${dishImg.id}">
                                        <div class="img-container">
                                            <img class="img-responsive J_currentBigPic" src="${tinyStaticWebsite}${dishImg.imgPath}" alt="菜品大图">
                                            <a href="javascript:;" class="J_del del"><i class="fa fa-times"></i></a>
                                        </div>
                                    </div>
                                </c:forEach>
                            </div>
                        </div>
                        <div class="col-sm-12 margin-top-15">
                            <div class="clearfix uploader">
                                <div id="dndArea1" class="placeholder">
                                    <div id="fileList1" class="uploader-list clearfix"></div>
                                </div>
                                <div id="filePicker1" class="pull-left">选择图片</div>
                                <button id="ctlBtn1" class="btn btn-default">开始上传</button>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h4>编辑菜品小图</h4>
                    </div>
                    <div class="panel-body">
                        <div class="col-sm-12">
                            <div class="row">
                                <h4>当前菜品小图</h4>
                                <hr>
                                <c:if test="${dishDto.smallImg != null}">
                                    <div class="col-sm-3 J_imgContainer" data-img-id="${dishDto.smallImg.id}">
                                        <div class="img-container">
                                            <img class="img-responsive J_currentBigPic" src="${tinyStaticWebsite}${dishDto.smallImg.imgPath}" alt="菜品大图">
                                            <a href="javascript:;" class="J_del del"><i class="fa fa-times"></i></a>
                                        </div>
                                    </div>
                                </c:if>
                            </div>
                        </div>
                        <div class="col-sm-12 margin-top-15">
                            <div class="clearfix uploader">
                                <div id="dndArea2" class="placeholder">
                                    <div id="fileList2" class="uploader-list clearfix"></div>
                                </div>
                                <div id="filePicker2" class="pull-left">选择图片</div>
                                <button id="ctlBtn2" class="btn btn-default">开始上传</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
