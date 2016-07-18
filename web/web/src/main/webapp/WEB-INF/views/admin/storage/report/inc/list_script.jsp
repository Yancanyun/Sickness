<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!-- 以下模板all modify -->
<!-- 搜索原材料ul -->
<script type="text/template" id="selectTpl">
    <ul class="menu hidden">
        <div class="search-box-input">
            <input type="text" class="form-control focusedInput J_ingredientSearchInp">
            <span class="fa fa-search search-box" aria-hidden="true"></span>
        </div>
        {@each list as it}
        <li class="J_render-list" data-id="&{it.ingredientId}" data-costCardUnitName="&{it.costCardUnitName}" data-ingredientNumber="&{it.ingredientNumber}" data-code="&{it.code}" data-name="&{it.name}"><a href="javascript:;">[&{it.code}]&nbsp;&{it.ingredientNumber}&nbsp;&{it.name}</a></li>
        {@/each}
    </ul>
</script>

<!-- 审核时,对话框模板 -->
<script type="text/template" id="checkDlg">
    <form class="form-horizontal J_checkForm" action="" method="">
        <div class="form-group">
            <label class="col-sm-3 control-label">审核人</label>
            <div class="col-sm-6">
                <p class="form-control-static">&{auditName}</p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>审核状态</label>
            <div class="col-sm-6" id="check"></div>
        </div>
    </form>
</script>

<!--编辑/添加时,对话框模板-->
<script type="text/template" id="dlg">
    <form class="form-horizontal J_addForm" action="" method="">
        <input type="hidden" name="id" value="&{id}">
        <div class="row">
            <div class="col-sm-4">
                <div class="form-group">
                    <label class="col-sm-5 control-label no-padding-right">单据编号</label>
                    <div class="col-sm-6">
                        <p class="form-control-static J_serialNumber">&{serialNumber}</p>
                    </div>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="form-group">
                    <label class="col-sm-5 control-label no-padding-right">日期</label>
                    <div class="col-sm-6">
                        <p class="form-control-static J_date">&{createdTime}</p>
                    </div>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="form-group money">
                    {@if billType == 1}
                    <label class="col-sm-5 control-label no-padding-right">金额</label>
                    {@/if}
                    <div class="col-sm-6">
                        <p class="form-control-static">{@if billType == 1}￥{@/if}<span class="J_totalMoney">{@if billType == 1}&{money}{@/if}</span></p>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>经手人</label>
            <div class="col-sm-6" id="handler"></div>
        </div>
        {@if billType == 1}
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>存放点</label>
            <div class="col-sm-6" id="depot"></div>
        </div>
        {@/if}
        {# 编辑弹出框的内容}
        {@if isEdit == true}
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>操作人</label>
            <div class="col-sm-6" >
                <p class="form-control-static J_serialNumber">&{createdName}</p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>结算状态</label>
            <div class="col-sm-6"  >
                <p class="form-control-static J_serialNumber">
                    {@if isSettlemented == 0}未结算{@/if}
                    {@if isSettlemented == 1}已结算{@/if}
                </p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>审核状态</label>
            <div class="col-sm-6" id="create">
                <p class="form-control-static J_serialNumber">
                    {@if isAudited == 0}未审核{@/if}
                    {@if isAudited == 1}已通过{@/if}
                    {@if isAudited == 2}未通过{@/if}
                </p>
            </div>
        </div>
        {@/if}
        <div class="form-group">
            <label class="col-sm-3 control-label">单据备注</label>
            <div class="col-sm-6">
                <input class="form-control J_comment" type="text" name="comment" value="&{comment}">
            </div>
        </div>
        {# 入库单据显示的内容}
        {@if billType == 1}
        <div class="form-group">
            <label for="basic" class="col-sm-3 control-label"><span class="requires">*</span>搜索物品</label>
            <div class="col-sm-6" id="basic"></div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right">订货单位</label>
            <div class="col-sm-6 ">
                <input class="form-control w180 J_orderUnitName" type="text" name="" value="&{orderUnitName}" readonly>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right"><span class="requires">*</span>单价</label>
            <div class="col-sm-6 ">
                <input class="form-control w180 J_price" type="text" data-valid-rule="isFloat" data-valid-tip="请输入单价|单价有误，请重新输入" name="" value="&{price}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right"><span class="requires">*</span>数量</label>
            <div class="col-sm-6 ">
                <input class="form-control w180 J_quantity J_orderQuantity" type="text" data-valid-rule="isFloat" data-valid-tip="请输入数量|数量有误，请重新输入" name="" value="">
            </div>
        </div>
        {# 非入库单据显示的内容}
        {@else}
        <div class="form-group">
            <label for="goodsInstore" class="col-sm-3 control-label"><span class="requires">*</span>搜索原材料</label>
            <div class="col-sm-6" id="goodsInstore">
                <div class ="drop clearfix">
                    <div class="J_renderTo render-div clearfix" style="min-height: 32px;"><span class="caret"></span></div>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right">成本卡单位</label>
            <div class="col-sm-6 ">
                <input class="form-control w180 J_costCardUnitName" type="text" name="" value="" readonly>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right"><span class="requires">*</span>数量</label>
            <div class="col-sm-6 ">
                <input class="form-control w180 J_quantity J_costCardQuantity" type="text" data-valid-rule="isFloat" data-valid-tip="请输入数量|数量有误，请重新输入" name="" value="">
            </div>
        </div>
        {@/if}
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right">
                {@if billType == 1}物品备注
                {@else}原材料备注
                {@/if}
            </label>
            <div class="col-sm-6 ">
                <input class="form-control w180 J_itemComment" type="text" name="" value="">
                {@if billType != 1}
                <a disabled="disabled" class="btn btn-success J_addBillIncludeCheck" href="javascript:;"><i class="fa fa-plus"></i></a>
                <a class="btn btn-success J_emptyInStoreBill" href="javascript:;"><i class="fa fa-eraser"></i></a>
                <a disabled="disabled" class="btn btn-success J_delInStoreBill" href="javascript:;"><i class="fa fa-minus"></i></a>
                {@/if}
            </div>
        </div>
        {# 非入库单据无小计功能}
        {@if billType == 1}
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right">小计</label>
            <div class="col-sm-6 ">
                <input class="form-control w180 J_money" readonly="readonly" type="text" placeholder="点击这里,获得小计" name="" value="">
                <a disabled="disabled" class="btn btn-success J_addInStoreBill" href="javascript:;"><i class="fa fa-plus"></i></a>
                <a class="btn btn-success J_emptyInStoreBill" href="javascript:;"><i class="fa fa-eraser"></i></a>
                <a disabled="disabled" class="btn btn-success J_delInStoreBill" href="javascript:;"><i class="fa fa-minus"></i></a>
            </div>
        </div>
        {@/if}
        <div class="table-responsive">
            <table class="table table-hover table-bordered v-center">
                <thead>
                <tr>
                    <th rowspan="2"><input type="checkbox" class="J_selectAll1" rowspan="2"></th>
                    <th rowspan="2">名称</th>
                    {@if billType == 1}
                    <th rowspan="2">物品编号</th>
                    <th colspan="2">订货</th>
                    <th colspan="2">成本卡</th>
                    <th rowspan="2">单价</th>
                    <th rowspan="2">金额</th>
                    <th rowspan="2">物品备注</th>
                    {@else}
                    <th rowspan="2">原材料编号</th>
                    <th colspan="2">成本卡</th>
                    <th colspan="2">库存</th>
                    <th rowspan="2">原材料备注</th>
                    {@/if}
                </tr>
                <tr>
                    <th>数量</th>
                    <th>单位</th>
                    <th>数量</th>
                    <th>单位</th>
                </tr>
                </thead>
                <tbody class="J_billList"></tbody>
            </table>
        </div>
    </form>
</script>

<!--查看时,对话框模板-->
<script type="text/template" id="viewDlg">
    <form class="form-horizontal" action="" method="">
        <input type="hidden" name="id" value="&{id}">
        <div class="row">
            <div class="col-sm-6">
                <div class="form-group">
                    <label class="col-sm-5 control-label">单据编号</label>
                    <div class="col-sm-6">
                        <p class="form-control-static J_serialNumber">&{serialNumber}</p>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label class="col-sm-5 control-label">日期</label>
                    <div class="col-sm-6">
                        <p class="form-control-static J_date">&{createdTime}</p>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label class="col-sm-5 control-label"><span class="requires">*</span>经手人</label>
                    <div class="col-sm-6">
                        <p class="form-control-static">&{handlerName}</p>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label class="col-sm-5 control-label"><span class="requires">*</span>操作人</label>
                    <div class="col-sm-6">
                        <p class="form-control-static">&{createdName}</p>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group">
                    <label class="col-sm-5 control-label"><span class="requires">*</span>审核人</label>
                    <div class="col-sm-6">
                        <p class="form-control-static">&{auditName}</p>
                    </div>
                </div>
            </div>
            <div class="col-sm-6">
                <div class="form-group money">
                    <label class="col-sm-5 control-label">{@if type == 1}金额{@/if}</label>
                    <div class="col-sm-6">
                        <p class="form-control-static">{@if type == 1}￥{@/if}<span class="J_totalMoney">{@if type == 1}&{money}{@/if}</span></p>
                    </div>
                </div>
            </div>
            {@if type == 1}
            <div class="col-sm-6">
                <div class="form-group">
                    <label class="col-sm-5 control-label"><span class="requires">*</span>存放点</label>
                    <div class="col-sm-6">
                        <p class="form-control-static">&{depotName}</p>
                    </div>
                </div>
            </div>
            {@/if}
            <div class="col-sm-12">
                <div class="form-group">
                    <label class="col-sm-3 control-label" style="padding-right: 48px;margin-right: -34px;">单据备注</label>
                    <div class="col-sm-9">
                        <p class="form-control-static">&{comment}</p>
                    </div>
                </div>
            </div>
            <div class="col-sm-12">
                <div class="table-responsive">
                    <table class="table table-hover table-bordered v-center">
                        <thead>
                        <tr>
                            <th rowspan="2">名称</th>
                            {@if type == 1}
                            <th rowspan="2">物品编号</th>
                            <th colspan="2">订货</th>
                            <th colspan="2">成本卡</th>
                            <th rowspan="2">单价</th>
                            <th rowspan="2">金额</th>
                            <th rowspan="2">物品备注</th>
                            {@else}
                            <th rowspan="2">原材料编号</th>
                            <th colspan="2">成本卡</th>
                            <th colspan="2">库存</th>
                            <th rowspan="2">原材料备注</th>
                            {@/if}
                        </tr>
                        <tr>
                            <th>数量</th>
                            <th>单位</th>
                            <th>数量</th>
                            <th>单位</th>
                        </tr>
                        </thead>
                        <tbody class="J_billList"></tbody>
                    </table>
                </div>
            </div>
        </div>
    </form>
</script>

<!--查看时,物品项模板-->
<script type="text/template" id="viewBillTpl">
    <tr>
        {@if billType == 1}
        <td>&{itemName}</td>
        <td>&{itemNumber}</td>
        <td>&{orderQuantity}</td>
        <td>&{orderUnitName}</td>
        <td>&{costCardQuantity}</td>
        <td>&{costCardUnitName}</td>
        <td>&{price}</td>
        <td class="J_countMoney">&{count}</td>
        {@else}
        <td>&{ingredientName}</td>
        <td>&{ingredientNumber}</td>
        <td>&{costCardQuantity}</td>
        <td>&{costCardUnitName}</td>
        <td>&{storageQuantity}</td>
        <td>&{storageUnitName}</td>
        {@/if}
        <td>&{comment}</td>
    </tr>
</script>

<!--物品项模板-->
<script type="text/template" id="billTpl">
    <tr>
        {@if billType == 1}
        <input type="hidden" name="itemId" value="&{itemId}">
        <input type="hidden" name="itemName" value="&{itemName}">
        <input type="hidden" name="itemNumber" value="&{itemNumber}">
        <input type="hidden" name="price" value="&{price}">
        <input type="hidden" name="count" value="&{count}">
        <input type="hidden" name="orderUnitName" value="&{orderUnitName}">
        <input type="hidden" name="orderQuantity" value="&{orderQuantity}">
        <input type="hidden" name="costCardUnitName" value="&{costCardUnitName}">
        <input type="hidden" name="costCardQuantity" value="&{costCardQuantity}">
        {@else}
        <input type="hidden" name="ingredientId" value="&{ingredientId}">
        <input type="hidden" name="ingredientName" value="&{ingredientName}">
        <input type="hidden" name="ingredientNumber" value="&{ingredientNumber}">
        <input type="hidden" name="costCardUnitName" value="&{costCardUnitName}">
        <input type="hidden" name="costCardQuantity" value="&{costCardQuantity}">
        <input type="hidden" name="storageUnitName" value="&{storageUnitName}">
        <input type="hidden" name="storageQuantity" value="&{storageQuantity}">
        {@/if}
        <input type="hidden" name="comment" value="&{comment}">
        <td>
            <input type="checkbox" class="J_check">
        </td>
        {@if billType == 1}
        <td>&{itemName}</td>
        <td>&{itemNumber}</td>
        <td>&{orderQuantity}</td>
        <td>&{orderUnitName}</td>
        <td>&{costCardQuantity}</td>
        <td>&{costCardUnitName}</td>
        <td>&{price}</td>
        <td class="J_countMoney">&{count}</td>
        {@else}
        <td>&{ingredientName}</td>
        <td>&{ingredientNumber}</td>
        <td>&{costCardQuantity}</td>
        <td>&{costCardUnitName}</td>
        <td>&{storageQuantity}</td>
        <td>&{storageUnitName}</td>
        {@/if}
        <td>&{comment}</td>
    </tr>
</script>

<!--刷分页模板-->
<script type="text/template" id="tpl">
    {@each list as it}
    <tr data-store-bill-createdTime="&{it.createdTime}" data-store-bill-handlerPartyId="&{it.handlerPartyId}" data-store-bill-createdPartyId="&{it.createdPartyId}"  data-store-bill-auditPartyId="&{it.auditPartyId}" data-store-bill-depotId="&{it.depotId}" data-store-bill-type="&{it.type}" data-store-bill-itemNumber="&{it.itemNumber}" data-store-bill-id="&{it.id}" data-store-bill-comment="&{it.comment}" data-store-bill-isAudited="&{it.isAudited}" data-store-bill-isSettlemented="&{it.isSettlemented}">
        <input type="hidden" name="id" value="&{it.id}"/>
        <input type="hidden" name="depotId" value="&{it.depotId}"/>
        <input type="hidden" name="depotName" value="&{it.depotName}"/>
        <input type="hidden" name="type" value="&{it.type}"/>
        <input type="hidden" name="money" value="&{it.money}"/>
        <input type="hidden" name="serialNumber" value="&{it.serialNumber}"/>
        <input type="hidden" name="handlerPartyId" value="&{it.handlerPartyId}"/>
        <input type="hidden" name="createdPartyId" value="&{it.createdPartyId}"/>
        <input type="hidden" name="handlerName" value="&{it.handlerName}"/>
        <input type="hidden" name="createdName" value="&{it.createdName}"/>
        <input type="hidden" name="auditPartyId" value="&{it.auditPartyId}"/>
        <input type="hidden" name="auditName" value="&{it.auditName}"/>
        <input type="hidden" name="isAudited" value="&{it.isAudited}"/>
        <input type="hidden" name="isSettlemented" value="&{it.isSettlemented}"/>
        <td class="J_reportItem hidden">
            {@each it.reportItem as item}
            {@if it.type == 1}
            <p data-item-id="&{item.itemId}">
                <input type="hidden" value="&{item.itemName}" name="itemName">
                <input type="hidden" value="&{item.itemNumber}" name="itemNumber">
                <input type="hidden" value="&{item.orderUnitName}" name="orderUnitName">
                <input type="hidden" value="&{item.orderQuantity}"name="orderQuantity">
                <input type="hidden" value="&{item.costCardUnitName}" name="costCardUnitName">
                <input type="hidden" value="&{item.costCardQuantity}" name="costCardQuantity">
                <input type="hidden" value="&{item.price}" name="price">
                <input type="hidden" value="&{item.count}" name="count">
                <input type="hidden" value="&{item.comment}" name="comment">
            </p>
            {@else}
            <p data-item-id="&{item.ingredientId}">
                <input type="hidden" value="&{item.ingredientName}" name="ingredientName">
                <input type="hidden" value="&{item.ingredientNumber}" name="ingredientNumber">
                <input type="hidden" value="&{item.costCardUnitName}" name="costCardUnitName">
                <input type="hidden" value="&{item.costCardQuantity}" name="costCardQuantity">
                <input type="hidden" value="&{item.storageUnitName}"name="storageUnitName">
                <input type="hidden" value="&{item.storageQuantity}" name="storageQuantity">
                <input type="hidden" value="&{item.price}" name="price">
                <input type="hidden" value="&{item.count}" name="count">
                <input type="hidden" value="&{item.comment}" name="comment">
            </p>
            {@/if}
            {@/each}
        </td>
        <td>
            {@if it.type == 1}入库单{@/if}
            {@if it.type == 2}出库单{@/if}
            {@if it.type == 3}盘盈单{@/if}
            {@if it.type == 4}盘亏单{@/if}
        </td>
        <td>&{it.serialNumber}</td>

        <td>&{it.handlerName}</td>
        <td>&{it.createdName}</td>
        <td>&{it.auditName}</td>
        <td>
            {@if it.isSettlemented == 0}未结算{@/if}
            {@if it.isSettlemented == 1}已结算{@/if}
        <td>
            {@if it.isAudited == 0}未审核{@/if}
            {@if it.isAudited == 1}已通过{@/if}
            {@if it.isAudited == 2}未通过{@/if}
        </td>
        <td>
            {@if it.isAudited == 0 || it.isAudited == 2 }
            <a href="javascript:;" class="label-info J_edit"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
            <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
            <a href="javascript:;" class="label-info J_view"><i class="fa fa-search"></i>&nbsp;查看</a>
            <a href="javascript:;" class="label-info J_checkStatus"><i class="fa fa-stop"></i>&nbsp;待审核</a>
            {@/if}
            {@if it.isAudited == 1}
            <a href="javascript:;" class="label-info J_view"><i class="fa fa-search"></i>&nbsp;查看</a>
            {@/if}
        </td>
    </tr>
    {@/each}
</script>

<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('page/store-management/store-bill-management', function(){
            PW.page.StoreManagement.StoreBillManagement({
                renderTo: '.J_pagination',
                juicerRender: '#tpl',
                dataRender: '#J_template',
                url: '/admin/storage/report/ajax/list',
                pageSize: 10,
                configUrl: function(url,page,me,prevPaginationData){
                    //return url;
 					return url + '/' + page;
                },
                date: '${currentDay}'
            });
        });
    })
</script>