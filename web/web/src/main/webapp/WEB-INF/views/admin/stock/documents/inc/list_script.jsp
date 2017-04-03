<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%--
  Created by IntelliJ IDEA.
  User: wychen
  Date: 2017/3/29
  Time: 8:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!-- 审核时,对话框模板 -->
<script type="text/template" id="checkDlg">
    <form class="form-horizontal J_checkForm" action="" method="">
        <div class="form-group">
            <label class="col-sm-3 control-label">审核人</label>
            <div class="col-sm-6">
                <p class="form-control-static">${username}</p>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>审核状态</label>
            <div class="col-sm-6" id="check"></div>
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
            <div class="col-sm-6">
                <div class="form-group">
                    <label class="col-sm-5 control-label"><span class="requires">*</span>存放点</label>
                    <div class="col-sm-6">
                        <p class="form-control-static">&{depotName}</p>
                    </div>
                </div>
            </div>
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
                            <th rowspan="2">规格</th>
                            <th colspan="2">订货</th>
                            <th colspan="2">库存</th>
                            <th rowspan="2">单价</th>
                            <th rowspan="2">金额</th>
                            {@else}
                            <th rowspan="2">物品编号</th>
                            <th rowspan="2">规格</th>
                            <th colspan="2">库存</th>
                            <th colspan="2">成本卡</th>
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
        <td>&{itemSpecification}</td>
        <td>&{orderQuantity}</td>
        <td>&{orderUnitName}</td>
        <td>&{storageQuantity}</td>
        <td>&{storageUnitName}</td>
        <td>&{price}</td>
        <td class="J_countMoney">&{count}</td>
        {@else}
        <td>&{itemName}</td>
        <td>&{itemNumber}</td>
        <td>&{itemSpecification}</td>
        <td>&{storageQuantity}</td>
        <td>&{storageUnitName}</td>
        <td>&{costCardQuantity}</td>
        <td>&{costCardUnitName}</td>
        {@/if}
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
                <input type="hidden" value="&{item.itemSpecification}" name="itemSpecification">
                <input type="hidden" value="&{item.orderUnitName}" name="orderUnitName">
                <input type="hidden" value="&{item.orderQuantity}" name="orderQuantity">
                <input type="hidden" value="&{item.storageUnitName}" name="storageUnitName">
                <input type="hidden" value="&{item.storageQuantity}" name="storageQuantity">
                <input type="hidden" value="&{item.price}" name="price">
                <input type="hidden" value="&{item.count}" name="count">
            </p>
            {@else}
            <p data-item-id="&{item.ingredientId}">
                <input type="hidden" value="&{item.itemName}" name="itemName">
                <input type="hidden" value="&{item.itemNumber}" name="itemNumber">
                <input type="hidden" value="&{item.costCardUnitName}" name="costCardUnitName">
                <input type="hidden" value="&{item.costCardQuantity}" name="costCardQuantity">
                <input type="hidden" value="&{item.storageUnitName}" name="storageUnitName">
                <input type="hidden" value="&{item.storageQuantity}" name="storageQuantity">
                <input type="hidden" value="&{item.itemSpecification}" name="itemSpecification">
            </p>
            {@/if}
            {@/each}
        </td>
        <td>
            {@if it.type == 1}入库单{@/if}
            {@if it.type == 2}领用单{@/if}
            {@if it.type == 3}盘点单{@/if}
            {@if it.type == 4}领回单{@/if}
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
        <td>&{it.date}</td>
        <td>
            {@if it.isAudited == 0 || it.isAudited == 2 }
            <a href="#" class="label-info J_edit"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
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
                url: '${website}admin/stock/documents/ajax/list',
                pageSize: 10,
                configUrl: function(url,page,me,prevPaginationData){
//                    return url;
                  return url + '/' + page;
                },
                date: '2015-11-11'
            });
        });
    })
</script>
