<%@ page contentType="text/html;charset=UTF-8" %>

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
                        <p class="form-control-static J_date">&{date}</p>
                    </div>
                </div>
            </div>
            <div class="col-sm-4">
                <div class="form-group money">
                    <label class="col-sm-5 control-label no-padding-right">金额</label>
                    <div class="col-sm-6">
                        <p class="form-control-static">￥<span class="J_totalMoney">&{money}</span></p>
                    </div>
                </div>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>存放点</label>
            <div class="col-sm-6" id="depot"></div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>经手人</label>
            <div class="col-sm-6" id="handler"></div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label"><span class="requires">*</span>操作人</label>
            <div class="col-sm-6" id="create"></div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label">单据备注</label>
            <div class="col-sm-6">
                <input class="form-control J_comment" type="text" name="" value="">
            </div>
        </div>
        <div class="form-group">
            <label for="basic" class="col-sm-3 control-label"><span class="requires">*</span>搜索物品</label>
            <div class="col-sm-6" id="basic"></div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right"><span class="requires">*</span>单价</label>
            <div class="col-sm-6 ">
                <input class="form-control w180 J_price" type="text" disabled="disabled" data-valid-rule="isFloat" data-valid-tip="请选择物品|没有选择物品，请重新选择" name="price" value="&{price}">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right"><span class="requires">*</span>数量</label>
            <div class="col-sm-6 ">
                <input class="form-control w180 J_quantity" type="text" data-valid-rule="isFloat" data-valid-tip="请输入数量|数量有误，请重新输入" name="" value="">
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-3 control-label no-padding-right">小计</label>
            <div class="col-sm-6 ">
                <input class="form-control w180 J_money" readonly="readonly" type="text" placeholder="点击这里,获得小计" name="" value="">
                <a disabled="disabled" class="btn btn-success J_addInStoreBill" href="javascript:;"><i class="fa fa-plus"></i></a>
                <a class="btn btn-success J_emptyInStoreBill" href="javascript:;"><i class="fa fa-eraser"></i></a>
                <a disabled="disabled" class="btn btn-success J_delInStoreBill" href="javascript:;"><i class="fa fa-minus"></i></a>
            </div>
        </div>
        <div class="table-responsive">
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th><input type="checkbox" class="J_selectAll1"></th>
                    <th>编号</th>
                    <th>名称</th>
                    <th>数量</th>
                    <th>成本价</th>
                    <th>成本金额</th>
                    <th>备注</th>
                </tr>
                </thead>
                <tbody class="J_billList"></tbody>
            </table>
        </div>
    </form>
</script>
<script type="text/template" id="billTpl">
    <tr>
        <input type="hidden" name="itemId" value="&{itemId}">
        <input type="hidden" name="price" value="&{price}">
        <input type="hidden" name="count" value="&{count}">
        <input type="hidden" name="quantity" value="&{quantity}">
        <input type="hidden" name="comment" value="&{comment}">
        <td>
            <input type="checkbox" class="J_check">
        </td>
        <td>&{serialNumber}</td>
        <td>&{name}</td>
        <td>&{quantity}</td>
        <td>&{price}</td>
        <td class="J_countMoney">&{money}</td>
        <td>&{comment}</td>
    </tr>
</script>
<!--刷分页模板-->
<script type="text/template" id="tpl">
    {@each list as it}
    <tr data-store-bill-createdTime="&{it.createdTime}" data-store-bill-handlerPartyId="&{it.handlerPartyId}" data-store-bill-createdPartyId="&{it.createdPartyId}" data-store-bill-type="&{it.type}" data-store-bill-id="&{it.id}" dat-store-bill-status="&{it.status}" data-store-bill-depot-id="&{it.depotId}">
        <input type="hidden" name="id" value="&{it.id}"/>
        <input type="hidden" name="type" value="&{it.type}"/>
        <input type="hidden" name="serialNumber" value="&{it.serialNumber}"/>
        <input type="hidden" name="depotId" value="&{it.depotId}"/>
        <input type="hidden" name="handlerPartyId" value="&{it.handlerPartyId}"/>
        <input type="hidden" name="money" value="&{it.money}"/>
        <input type="hidden" name="createdPartyId" value="&{it.createdPartyId}"/>
        <input type="hidden" name="status" value="&{it.status}"/>
        <td>
            {@if it.type == 1}入库单{@/if}
            {@if it.type == 2}出库单{@/if}
            {@if it.type == 3}盘盈单{@/if}
            {@if it.type == 4}盘亏单{@/if}
        </td>
        <td>&{it.serialNumber}</td>
        <td>&{it.depotName}</td>
        <td>&{it.handlerName}</td>
        <td>&{it.money}</td>
        <td>&{it.createdName}</td>
        <td>
            {@if it.status == 0}
            <a href="javascript:;" class="label-info J_edit"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
            <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
            <a href="javascript:;" class="label-info J_view"><i class="fa fa-search"></i>&nbsp;查看</a>
            {@/if}
            {@if it.status == 1}
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
                url: 'http://static.emenu2.net/mock/admin/store-management-list.json',
                pageSize: 10,
                configUrl: function(url,page,me,prevPaginationData){
                    return url;
//							return url + '/' + page;
                },
                date: '2015-11-11'
            });
        });
    })
</script>
