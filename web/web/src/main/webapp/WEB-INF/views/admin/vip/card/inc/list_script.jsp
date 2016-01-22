<%@ page contentType="text/html;charset=UTF-8" %>
<script type="text/template" id="tpl">
    {@each list as it}
    <tr vip-card-id="&{it.id}">
        <td class="J_name">&{it.name}</td>
        <td class="J_phone">&{it.phone}</td>
        <td class="J_cardNumber">&{it.cardNumber}</td>
        <td class="J_createdTime">&{it.createdTime}</td>
        <td class="J_validityTime">&{it.validityTime}</td>
        <td class="J_permanentlyEffective">&{it.permanentlyEffective}</td>
        <td class="J_operator">&{it.operator}</td>
        <td class="J_status">&{it.status}</td>
        <td class="J_operation">
            <a class="label-info J_editBtn" href="javascript:;" disabled><i class="fa fa-pencil"></i>&nbsp;编辑</a>
            {@if it.status == "可用"}
            <a class="label-info J_changeBtn" href="javascript:;"><i class="fa fa-minus-circle"></i>&nbsp;挂失</a>
            {@else if it.status == "已挂失"}
            <a class="label-info J_changeBtn" href="javascript:;"><i class="fa fa-dot-circle-o"></i>&nbsp;解挂</a>
            {@else}
            {@/if}
            <a class="label-info J_delBtn" href="javascript:;"><i class="fa fa-times"></i>&nbsp;删除</a>
        </td>
    </tr>
    {@/each}
</script>
<!-- 编辑模板 -->
<script type="text/template" id="editTpl">
    <tr vip-card-id="&{card.id}">
        <input class="J_card" type="hidden" value="" name="id"/>
        <td class="J_name">&{card.name}</td>
        <td class="J_phone">&{card.phone}</td>
        <td class="J_cardNumber">&{card.cardNumber}</td>
        <td class="J_createdTime">&{card.createdTime}</td>
        <td class="J_validityTime">
            <input type="text" class="col-sm-3 form-control J_validityDate" name="validityTime" value="&{card.validityTime}"/>
        </td>
        <td class="J_permanentlyEffective">
            <select class="form-control" name="permanentlyEffective">
                {@if card.permanentlyEffective == "否"}
                <option value ="0">否</option>
                <option value ="1">是</option>
                {@else}
                <option value ="1">是</option>
                <option value ="0">否</option>
                {@/if}
            </select>
        </td>
        <td class="J_operator">&{card.operator}</td>
        <td class="J_status">&{card.status}</td>
        <td class="J_operation">
            <a href="javascript:;" class="label-info J_saveBtn"><i class="fa fa-save"></i>&nbsp;保存</a>
            <a href="javascript:;" class="label-info J_cancelBtn"><i class="fa fa-undo"></i>&nbsp;取消</a>
        </td>
    </tr>
</script>
<!-- 保存模板 -->
<script type="text/template" id="saveTpl">
    <tr vip-card-id="&{card.id}">
        <input class="J_card" type="hidden" value="" name="id"/>
        <td class="J_name">&{card.name}</td>
        <td class="J_phone">&{card.phone}</td>
        <td class="J_cardNumber">&{card.cardNumber}</td>
        <td class="J_createdTime">&{card.createdTime}</td>
        <td class="J_validityTime">&{card.validityTime}</td>
        <td class="J_permanentlyEffective">
            {@if card.permanentlyEffective == "0"}否
            {@else if card.permanentlyEffective == "1"}是
            {@/if}
        </td>
        <td class="J_operator">&{card.operator}</td>
        <td class="J_status">&{card.status}</td>
        <td class="J_operation">
            <a class="label-info J_editBtn" href="javascript:;"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
            {@if card.status == "可用"}
            <a class="label-info J_changeBtn" href="javascript:;"><i class="fa fa-minus-circle"></i>&nbsp;挂失</a>
            {@else if card.status == "已挂失"}
            <a class="label-info J_changeBtn" href="javascript:;"><i class="fa fa-dot-circle-o"></i>&nbsp;解挂</a>
            {@else}
            {@/if}
            <a class="label-info J_delBtn" href="javascript:;"><i class="fa fa-times"></i>&nbsp;删除</a>
        </td>
    </tr>
</script>
<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('page/vip-management/vip-card-management', function(){
            PW.page.vipCardManagement.Core({
                renderTo: '.J_pagination',
                juicerRender: '#tpl',
                dataRender: '#J_template',
                url: '/admin/vip/card/ajax/list',
                pageSize: 10,
                configUrl: function(url,page,me,prevPaginationData){
//                    return url;
                    return url + '/' + page;
                }
            });
        })
    });
</script>