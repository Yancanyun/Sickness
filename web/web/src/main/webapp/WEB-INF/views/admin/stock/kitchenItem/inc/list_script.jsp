<%--
  Created by IntelliJ IDEA.
  User: sanqi
  Date: 2017/3/21
  Time: 15:25
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>


<!-- 添加备注时,对话框模板 -->
<script type="text/template" id="addDlg">
    <form class="form-horizontal J_addForm" action="" method="">
        <div class="form-group">
            <label class="col-sm-3 control-label">备注</label>
            <div class="col-sm-9">
                <textarea class="big" name="addRemark" maxlength="20">&{remark}</textarea>
            </div>
        </div>
    </form>
</script>
<!-- 修改备注时,对话框模板 -->
<script type="text/template" id="editDlg">
    <form class="form-horizontal J_editForm" action="" method="">
        <div class="form-group">
            <label class="col-sm-3 control-label">备注</label>
            <div class="col-sm-9">
                <textarea class="big" name="editRemark" maxlength="20">&{remark}</textarea>
            </div>
        </div>
    </form>
</script>

<!-- 刷分页 -->
<script type="text/template" id="tpl">
{@each list as it}
<tr data-depot-id="&{it.id}" data-depot-remark="&{it.remark}">
<td>&{it.itemNumber}</td>
<td>&{it.itemName}</td>
<td>&{it.assistantCode}</td>
<td>&{it.specifications}</td>
<td>&{it.storageQuantity}</td>
<td>
{@if it.status==1}正常{@/if}
{@if it.status==2}标缺{@/if}
</td>
<td>&{it.remark}</td>
<td>
{@if it.remark==""}
<a href="javascript:;" class="label-info J_add"><i class="fa fa-plus"></i>&nbsp;添加备注</a>
{@/if}
{@if it.remark!=""}
<a href="javascript:;" class="label-info J_edit"><i class="fa fa-pencil"></i>&nbsp;修改备注</a>
{@/if}

</td>
</tr>
{@/each}
</script>
<script type="text/javascript">
    KISSY.ready(function(S){
        S.use("page/store-management/depot-item-management-list", function(S){
            PW.page.StoreManagement.DepotItemManagementList({
                renderTo: '.J_pagination',
                juicerRender: '#tpl',
                dataRender: '#J_template',
                url: '/mock/admin/depot-item-management-list.json',
                pageSize: 10,
                configUrl: function(url,page,me,prevPaginationData){
                    return url;
                    // return url + '/' + page;
                }
            });
        });
    });
</script>