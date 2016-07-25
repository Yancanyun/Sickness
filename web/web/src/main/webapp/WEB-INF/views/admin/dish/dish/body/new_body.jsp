<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">菜品管理</a></li>
            <li class="active">菜品管理</li>
        </ol>
        <h2>菜品管理-添加菜品</h2>
        <c:if test="${!empty msg}">
            <div class="alert alert-danger J_tip">提示信息!</div>
        </c:if>
    </div>
    <div class="col-sm-12 margin-bottom-30">
        <form class="form-horizontal J_operForm" autocomplete="off" action="${website}admin/dish" method="post">
            <!--菜品id-->
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active">
                    <a class="tab" data-tabpanel="#single" role="tab" data-toggle="tab">添加菜品</a>
                </li>
                <!--<li role="presentation">-->
                <!--如果是编辑页面, "批量添加"改为编辑图片-->
                <!--<a class="tab" data-tabpanel="#batch" role="tab" data-toggle="tab">批量添加</a>-->
                <!--</li>-->
            </ul>
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="single">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h4>单个菜品添加</h4>
                        </div>
                        <div class="panel-body">
                            <div class="">
                                <h4>添加菜品信息</h4>
                                <hr>
                                <div class="form-group">
                                    <!--联动级数,如果是三级联动,请刷3,并且刷3个select,如果是二级联动同理-->
                                    <input class="J_linkage" type="hidden" value="${categoryLayer}">
                                    <label class="col-sm-3 control-label"><span class="requires">*</span>选择分类</label>
                                    <div class="col-sm-2 no-padding-right">
                                        <select class="form-control J_rootClass w180" name="categoryId">
                                            <option value="-1" selected="selected">请选择</option>
                                            <option value="3">菜品</option>
                                            <option value="5">酒水</option>
                                            <option value="4">商品</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 no-padding-right J_bigClassSelect">
                                        <!--如果是编辑页面, 就刷下方的下拉列表-->
                                        <select class="form-control J_bigClass w180" name="tagId">
                                            <option value="-1" selected="selected">请选择</option>
                                        </select>
                                    </div>
                                    <c:if test="${categoryLayer == 3}">
                                        <div class="col-sm-2 no-padding-right J_smallClassSelect">
                                            <select class="form-control J_smallClass w180" name="tagId">
                                                <option value="-1" selected="selected">请选择</option>
                                            </select>
                                        </div>
                                    </c:if>

                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><span class="requires">*</span>菜品名称</label>
                                    <div class="col-sm-6">
                                        <input class="w180 J_name" type="text" data-valid-tip="请输入菜品名称|菜品名称不能为空，请重新输入" data-valid-rule="notNull" value="" name="name" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">菜品编号</label>
                                    <div class="col-sm-6">
                                        <input class="w180" type="text" value="" name="dishNumber" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">助记码</label>
                                    <div class="col-sm-6">
                                        <input class="w180 J_assistantCode" type="text" value="" name="assistantCode" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><span class="requires">*</span>单位</label>
                                    <div class="col-sm-2 no-padding-right">
                                        <select class="form-control J_unitClass w180">
                                            <option value="2" selected="selected">数量单位</option>
                                            <option value="1">重量单位</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 no-padding-right J_unitNumber">
                                        <select class="form-control J_unitNumberSelect w180" name="unitId">
                                            <c:forEach var="unit" items="${quantityUnitList}">
                                                <option value="${unit.id}">${unit.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 no-padding-right J_unitWeight hidden">
                                        <select class="form-control J_unitWeightSelect w180" name="unitId" disabled="disabled">
                                            <c:forEach var="unit" items="${weightUnitList}">
                                                <option value="${unit.id}">${unit.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><span class="requires">*</span>定价</label>
                                    <div class="col-sm-6">
                                        <input class="w180 J_price" id="price" type="text" data-valid-tip="请输入定价|定价出现非数字，请重新输入" data-valid-rule="isFloat" value="" name="price" />&nbsp;元
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
                                            <option value="1">无促销</option>
                                            <option value="2" data-name="discount" data-value="0" data-valid-rule="scale(0,10)" data-valid-tip="请输入大于0小于10的整数或小数，如6或6.5|折扣超范围，请重新输入">折扣</option>
                                            <option value="3" data-name="salePrice" data-value="0" data-valid-rule="isFloat" data-valid-tip="请输入售价|售价不能为空，请重新输入">售价</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-6">
                                        <span class="J_noPromotion help-inline J_renderPosition"></span>
                                        <!-- input class="w180 J_promotion J_renderTo" type="text" value="" name="" />&nbsp; -->
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
                                        <div id="basic"></div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">上菜时限</label>
                                    <div class="col-sm-6 clearfix">
                                        <input class="w180" type="text" name="timeLimit" value="0">&nbsp;(0为无限制)
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">可点餐段</label>
                                    <div class="col-sm-6 clearfix">
                                        <div class="checkbox block">
                                            <c:forEach var="mealPeriod" items="${mealPeriodList}">
                                                <label>
                                                    <input class="J_selectAll"  type="checkbox" value="${mealPeriod.id}" name="mealPeriodIdList" checked="checked"> ${mealPeriod.name}
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
                                                <option value="${printer.id}">${printer.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">是否网络可点</label>
                                    <div class="col-sm-9">
                                        <div class="radio">
                                            <label>
                                                <input type="radio" value="1" name="isNetworkAvailable" checked="checked">是
                                            </label>
                                            <label>
                                                <input type="radio" value="0" name="isNetworkAvailable">否
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">是否使用会员价</label>
                                    <div class="col-sm-9">
                                        <div class="radio">
                                            <label>
                                                <input type="radio" value="1" name="isVipPriceAvailable" checked="checked">是
                                            </label>
                                            <label>
                                                <input type="radio" value="0" name="isVipPriceAvailable">否
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">是否可用代金卷</label>
                                    <div class="col-sm-9">
                                        <div class="radio">
                                            <label>
                                                <input type="radio" value="1" name="isVoucherAvailable" checked="checked">是
                                            </label>
                                            <label>
                                                <input type="radio" value="0" name="isVoucherAvailable">否
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group dish">
                                    <label class="col-sm-3 control-label">菜品介绍</label>
                                    <div class="col-sm-6 clearfix">
                                        <textarea class="form-control" rows="6" name="description"></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <div class="panel-footer">
                            <div class="row">
                                <div class="col-sm-6 col-sm-offset-3">
                                    <div class="btn-toolbar">
                                        <button class="btn-primary btn J_submitBtn" type="submit" data-btn-type="loading" data-btn-loading-text="正在保存，请稍后">
                                            <i class="fa fa-arrow-right"></i>
                                            &nbsp;下一步
                                        </button>
                                        <button class="btn-default btn" type="reset"><i class="fa fa-undo"></i>&nbsp;重置</button>
                                        <!--如果是编辑页面,请刷下面的按钮-->
                                        <!--<button class="btn-default btn" type="button"><i class="fa fa-undo"></i>&nbsp;返回</button>-->
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
                <!--<div role="tabpanel" class="tab-pane" id="batch">-->
                <!--批量-->
                <!--</div>-->
            </div>
        </form>
    </div>
</div>