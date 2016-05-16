<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">菜品管理</a></li>
            <li class="active">套餐管理</li>
        </ol>
        <h2>菜品管理-套餐管理-编辑套餐</h2>
        <c:if test="${!empty msg}">
            <div class="alert alert-danger J_tip">${msg}</div>
        </c:if>
    </div>
    <div class="col-sm-12 margin-bottom-30">
        <form class="form-horizontal J_operForm" autocomplete="off" action="${website}admin/dish/package/new" method="POST">
            <!--菜品id-->
            <input class="J_id" type="hidden" name="id" value="${dishPackageDto.dishDto.id}">
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="active">
                    <a class="tab" data-tabpanel="#single" role="tab" data-toggle="tab">编辑套餐</a>
                </li>
                <li role="presentation">
                    <!--如果是编辑页面, "批量添加"改为编辑图片-->
                    <a class="tab" data-tabpanel="#batch" role="tab" data-toggle="tab">编辑套餐图片</a>
                </li>
            </ul>
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane active" id="single">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h4>编辑套餐</h4>
                        </div>
                        <div class="panel-body">
                            <div class="">
                                <h4>编辑套餐信息</h4>
                                <hr>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><span class="requires">*</span>选择分类</label>
                                    <div class="col-sm-2 no-padding-right">
                                        <select class="form-control J_rootClass w180" name="categoryId">
                                            <option value="6" selected="selected">套餐</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-2 no-padding-right">
                                        <select class="form-control J_rootClass w180" name="tagId">
                                            <option value="-1" selected="selected">请选择</option>
                                            <c:forEach var="childTag" items="${childTagList}">
                                                <option value="${childTag.id}" <c:if test="${dishPackageDto.dishDto.tagId == childTag.id}">selected="selected"</c:if>> ${childTag.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><span class="requires">*</span>套餐名称</label>
                                    <div class="col-sm-6">
                                        <input class="w180 J_name" type="text" data-valid-tip="请输入套餐名称|套餐名称不能为空，请重新输入" data-valid-rule="notNull" value="${dishPackageDto.dishDto.name}" name="name" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">套餐编号</label>
                                    <div class="col-sm-6">
                                        <input class="w180" type="text" value="${dishPackageDto.dishDto.dishNumber}" name="dishNumber" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">助记码</label>
                                    <div class="col-sm-6">
                                        <input class="w180 J_assistantCode" type="text" value="${dishPackageDto.dishDto.assistantCode}" name="assistantCode" />
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><span class="requires">*</span>定价</label>
                                    <div class="col-sm-6">
                                        <input class="w180 J_price" id="price" type="text" data-valid-tip="请输入定价|定价出现非数字，请重新输入" data-valid-rule="isFloat" value="${dishPackageDto.dishDto.price}" name="price" />&nbsp;元
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
                                            <option value="1" <c:if test="${dishPackageDto.dishDto.saleType == 1}">selected="selected"</c:if>>无促销</option>
                                            <option value="2" <c:if test="${dishPackageDto.dishDto.saleType == 1}">selected="selected"</c:if> data-name="discount" data-value="${dishPackageDto.dishDto.discount}" data-valid-rule="scale(0,10)" data-valid-tip="请输入大于0小于10的整数或小数，如6或6.5|折扣超范围，请重新输入">折扣</option>
                                            <option value="3" <c:if test="${dishPackageDto.dishDto.saleType == 1}">selected="selected"</c:if> data-name="salePrice" data-value="${dishPackageDto.dishDto.salePrice}" data-valid-rule="isFloat" data-valid-tip="请输入售价|售价不能为空，请重新输入">售价</option>
                                        </select>
                                    </div>
                                    <div class="col-sm-6">
                                        <span class="J_noPromotion help-inline J_renderPosition"></span>
                                        <span class="help-inline J_promotionUnit"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="J_dishDetail">
                                <h4 class="margin-top-30">套餐详情</h4>
                                <hr>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label"><span class="requires">*</span>选择菜品</label>
                                    <div class="col-sm-6 clearfix">
                                        <div class="taste-list J_dishList">
                                            <c:forEach var="childDishDto" items="${dishPackageDto.childDishDtoList}">
                                                <div data-result-unit="${childDishDto.unitName}" data-result-price="${childDishDto.price}" data-result-name="[${childDishDto.assistantCode}] ${childDishDto.name}" data-result-id="${childDishDto.id}" data-result-quantity="${childDishDto.dishPackage.dishQuantity}" class="select-tag">[${childDishDto.assistantCode}] ${childDishDto.name} ${childDishDto.dishPackage.dishQuantity}${childDishDto.unitName}<i class="fa fa-times J_delSelectTag"></i><input type="hidden" value="${childDishDto.dishPackage.dishId}" name="dishId"></div>
                                            </c:forEach>
                                        </div>
                                        <a href="javascript:;" class="J_addDish btn btn-success"><i class="fa fa-plus">&nbsp;</i>添加菜品</a>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">上菜时限</label>
                                    <div class="col-sm-6 clearfix">
                                        <input class="w180" type="text" name="timeLimit" value="${dishPackageDto.dishDto.timeLimit}">&nbsp;(0为无限制)
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">可点餐段</label>
                                    <div class="col-sm-6 clearfix">
                                        <div class="checkbox block">
                                            <c:forEach var="mealPeriod" items="${mealPeriodList}">
                                                <label>
                                                    <input class="J_selectAll" type="checkbox" value="${mealPeriod.id}" name="period" <c:forEach var="nowMealPeriod" items="${dishPackageDto.dishDto.mealPeriodList}"><c:if test="${nowMealPeriod.mealPeriodId == mealPeriod.id}">checked="checked"</c:if></c:forEach>> ${mealPeriod.name}
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
                                                <option value="${printer.id}" <c:if test="${dishPackageDto.dishDto.printerId == printer.id}">selected="selected"</c:if>>${printer.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">是否网络可点</label>
                                    <div class="col-sm-9">
                                        <div class="radio">
                                            <label>
                                                <input type="radio" value="1" name="isNetworkAvailable" <c:if test="${dishPackageDto.dishDto.isNetworkAvailable == 1}">checked="checked"</c:if>>是
                                            </label>
                                            <label>
                                                <input type="radio" value="0" name="isNetworkAvailable" <c:if test="${dishPackageDto.dishDto.isNetworkAvailable == 0}">checked="checked"</c:if>>否
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">是否使用会员价</label>
                                    <div class="col-sm-9">
                                        <div class="radio">
                                            <label>
                                                <input type="radio" value="1" name="isVipPriceAvailable" <c:if test="${dishPackageDto.dishDto.isVipPriceAvailable == 1}">checked="checked"</c:if>>是
                                            </label>
                                            <label>
                                                <input type="radio" value="0" name="isVipPriceAvailable" <c:if test="${dishPackageDto.dishDto.isVipPriceAvailable == 0}">checked="checked"</c:if>>否
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">是否可用代金卷</label>
                                    <div class="col-sm-9">
                                        <div class="radio">
                                            <label>
                                                <input type="radio" value="1" name="isVoucherAvailable" <c:if test="${dishPackageDto.dishDto.isVoucherAvailable == 1}">checked="checked"</c:if>>是
                                            </label>
                                            <label>
                                                <input type="radio" value="0" name="isVoucherAvailable" <c:if test="${dishPackageDto.dishDto.isVoucherAvailable == 0}">checked="checked"</c:if>>否
                                            </label>
                                        </div>
                                    </div>
                                </div>
                                <!--编辑页刷点赞人数-->
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">点赞人数</label>
                                    <div class="col-sm-9">
                                        <input class="w180" type="text" name="likeNums" value="${dishPackageDto.dishDto.likeNums}"/>
                                    </div>
                                </div>
                                <div class="form-group dish">
                                    <label class="col-sm-3 control-label">菜品介绍</label>
                                    <div class="col-sm-6 clearfix">
                                        <textarea class="form-control" rows="6" name="description">${dishPackageDto.dishDto.description}</textarea>
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
                                        <%--<button class="btn-default btn" type="reset"><i class="fa fa-undo"></i>&nbsp;重置</button>--%>
                                        <!--如果是编辑页面,请刷下面的按钮-->
                                        <button class="btn-default btn" type="button"><i class="fa fa-undo"></i>&nbsp;返回</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <select class="selectpicker show-tick form-control hidden" data-live-search="true">
                    <c:forEach var="dish" items="${dishList}">
                        <option value="${dish.id}" data-price="${dish.salePrice}" data-code="${dish.assistantCode}" data-unit="${dish.unitName}">${dish.name}</option>
                    </c:forEach>
                </select>
                <!--<div role="tabpanel" class="tab-pane" id="batch">-->
                <!--批量-->
                <!--</div>-->
                <div role="tabpanel" class="tab-pane" id="batch">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h4>编辑套餐大图</h4>
                        </div>
                        <div class="panel-body">
                            <div class="col-sm-12">
                                <div class="row">
                                    <h4>当前套餐大图</h4>
                                    <hr>
                                    <c:forEach var="dishImg" items="${dishPackageDto.dishDto.bigImgList}">
                                        <div class="col-sm-3 J_imgContainer" data-img-id="${dishImg.id}">
                                            <div class="img-container">
                                                <img class="img-responsive J_currentBigPic" src="${tinyStaticWebsite}${dishImg.imgPath}" alt="套餐大图">
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
                            <h4>编辑套餐小图</h4>
                        </div>
                        <div class="panel-body">
                            <div class="col-sm-12">
                                <div class="row">
                                    <h4>当前套餐小图</h4>
                                    <hr>
                                    <c:if test="${dishPackageDto.dishDto.smallImg != null}">
                                        <div class="col-sm-3 J_imgContainer" data-img-id="${dishPackageDto.dishDto.smallImg.id}">
                                            <div class="img-container">
                                                <img class="img-responsive J_currentBigPic" src="${tinyStaticWebsite}${dishPackageDto.dishDto.smallImg.imgPath}" alt="套餐小图">
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
        </form>
    </div>
</div>
