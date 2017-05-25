<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: Karl_SC
  Date: 2017/3/17
  Time: 11:13
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="container">
    <div class="row">
        <div class="col-sm-12">
            <ol class="breadcrumb">
                <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
                <li><a href="#">库存管理</a></li>
                <li><a href="#" class="active">库存物品添加</a></li>
            </ol>
            <h2>库存物品添加</h2>
            <div class="alert alert-warning J_tip">保存成功！</div>
        </div>
        <div class="col-sm-12">
            <form class="form-horizontal J_form" action="" method="">
                <div class="panel panel-info">
                    <div class="panel-heading">
                        <h4>添加</h4>
                    </div>
                    <div class="panel-body">
                        <!--编辑时添加-->
                        <!--
                            <input type="hidden" name="id" value="1">
                        -->
                        <div class="form-group">
                            <label class="col-sm-3 control-label"><span class="requires">*</span>名称</label>
                            <div class="col-sm-6">
                                <input type="text" name="name" class="form-control w180 J_name" placeholder="请输入名称" data-valid-rule="length(1,30,1)" data-valid-tip="请输入1-30个字符|输入有误，请重新填写">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">助记码</label>
                            <div class="col-sm-6">
                                <input type="text" name="assistantCode" class="form-control w180" placeholder="请输入助记码">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label"><span class="requires">*</span>所属分类</label>
                            <div class="col-sm-6">
                                <select class="w180 form-control" name="tagId">
                                    <option value="aa">aa</option>
                                    <option value="bb">bb</option>
                                    <option value="cc">cc</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3"><span class="requires">*</span>库存预警上限</label>
                            <div class="col-sm-9">
                                <input type="text" name="upperQuantity" class="w180 J_maxStorageQuantity" data-valid-rule="isFloat" data-valid-tip="请输入库存预警上限|输入有误，请重新填写" placeholder="请输入预警上限">
                                <input type="text" class="w50 J_storage" value="aa" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3"><span class="requires">*</span>库存预警下限</label>
                            <div class="col-sm-9">
                                <input type="text" name="lowerQuantity" class="w180 J_minStorageQuantity" data-valid-rule="isFloat" data-valid-tip="请输入库存预警下限|输入有误，请重新填写" placeholder="请输入预警下限">
                                <input type="text" class="w50 J_storage" value="aa" readonly>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3"><span class="requires">*</span>库存量</label>
                            <div class="col-sm-9">
                                <input type="text" name="storageQuantity" class="w180" data-valid-rule="isFloat" data-valid-tip="请输入库存量|输入有误，请重新填写" placeholder="请输入预警量">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="control-label col-sm-3">出库方式</label>
                            <div class="col-sm-6">
                                <select class="w180 form-control" name="stockOutType">
                                    <option value="1">自动出库</option>
                                    <option value="2">手动出库</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label">备注</label>
                            <div class="col-sm-6">
                                <input type="text" name="remark" placeholder="请输入备注" class="w180 form-control">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label"><span class="requires">*</span>成本卡单位</label>
                            <div class="col-sm-6">
                                <select class="w180 form-control" name="costCardUnitId">
                                    <option value="aa">aa</option>
                                    <option value="bb">bb</option>
                                    <option value="cc">cc</option>
                                </select>
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-3 control-label"><span class="requires">*</span>选择规格</label>
                            <div class="col-sm-6 clearfix">
                                <div class="taste-list J_contain">
                                    <div class="select-tag" data-id="1" data-orderUnitId="箱" data-orderToStorage="15" data-storageUnitId="瓶" data-storageToCost="500" data-costCardId="毫升">箱15瓶500毫升<i class="fa fa-times J_delTab"></i>
                                        <input type="hidden" value="1" name="specifications">
                                    </div>
                                    <div class="select-tag" data-id="2" data-orderUnitId="箱" data-orderToStorage="15" data-storageUnitId="瓶" data-storageToCost="500" data-costCardId="毫升">箱15瓶500毫升<i class="fa fa-times J_delTab"></i>
                                        <input type="hidden" value="2" name="specifications">
                                    </div>
                                </div>
                                <a href="javascript:;" class="btn btn-success J_addTrigger"><i class="fa fa-plus"></i>&nbsp;添加规格</a>
                            </div>
                        </div>
                        <div class="panel-footer">
                            <div class="row">
                                <div class="col-sm-6 col-sm-offset-3">
                                    <div class="btn-toolbar">
                                        <button class="btn btn-primary J_submit" type="button"><i class="fa fa-save"></i>&nbsp;保存</button>
                                        <button class="btn btn-default" type="reset"><i class="fa fa-undo"></i>&nbsp;重置</button>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
            </form>
        </div>
    </div>
</div>