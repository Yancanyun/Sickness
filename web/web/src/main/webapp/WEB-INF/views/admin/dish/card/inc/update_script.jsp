<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>

<script type="text/template" id="dialogTpl">
    <form class="form-horizontal J_DialogForm"  data-oper-type="&{data.isEdit}">
        <div class="form-group">
            <label class="col-sm-4 control-label">
                <span class="requires">*</span>原材料
            </label>
            <div class="col-sm-6" id="ingredient" data-result-id="&{data.ingredientId}">
                <div class="drop clearfix"><div class="J_renderTo render-div clearfix"   placeholder="不可重复添加一种原材料" style="min-height: 32px;">&{data.ingredientName}<span class="caret"></span></div></div>
            </div>
            <input type="hidden" class="J_ingredientId" value="&{data.ingredientId}" name="ingredientId"/>
            <input type="hidden" class="J_ingredientName" value="&{data.ingredientName}" name="ingredientName"/>
            <input type="hidden" class="J_ingredientUnit" value="&{data.unit}" name="unit"/>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label">
                <span class="requires">*</span>材料类型
            </label>
            <div class="col-sm-6">
                <select class="form-control" name="itemType">
                    <option value="0" {@if data.itemType == 0}selected{@/if}>主料</option>
                    <option value="1" {@if data.itemType == 1}selected{@/if}>辅料</option>
                    <option value="2" {@if data.itemType == 2}selected{@/if}>调料</option>
                </select>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 margin-right-15 control-label">
                <span class="requires">*</span>毛料用量
            </label>
            <div class="col-sm-6 input-group">
                <input type="text" class="form-control" placeholder="请输入大于0的数字" data-valid-rule="notNull & notNegativeNumber" name="otherCount" value="&{data.otherCount}"/>
                <span class="input-group-addon">&{data.unit}</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 margin-right-15 control-label">
                <span class="requires">*</span>净料用量
            </label>
            <div class="input-group col-sm-6 ">
                <input type="text" class="form-control" placeholder="请输入大于0的数字" data-valid-rule="notNull & notNegativeNumber" name="netCount" value="&{data.netCount}"/>
                <span class="input-group-addon">&{data.unit}</span>
            </div>
        </div>
        <div class="form-group">
            <label class="col-sm-4 control-label">
                <span class="requires">*</span>是否班结时自动出库
            </label>
            <div class="col-sm-6">
                {@if data.isAutoOut ==1}
                <label class="radio-inline">
                    <input type="radio" name="isAutoOut" value="0" />否
                </label>
                <label class="radio-inline">
                    <input type="radio" name="isAutoOut" value="1" checked/>是
                </label>
                {@else}
                <label class="radio-inline">
                    <input type="radio" name="isAutoOut" value="0" checked/>否
                </label>
                <label class="radio-inline">
                    <input type="radio" name="isAutoOut" value="1" />是
                </label>
                {@/if}
            </div>
        </div>
    </form>
</script>
<script type="text/template" id="listTpl">
    <tr data-ingredient-id="&{data.ingredientId}">
        <th>&{data.ingredientName}</th>
        {@if data.itemType == 0}
        <th class="J_itemType" data-itemType="&{data.itemType}">主料</th>
        {@else if data.itemType == 1}
        <th class="J_itemType" data-itemType="&{data.itemType}">辅料</th>
        {@else}
        <th class="J_itemType" data-itemType="&{data.itemType}">调料</th>
        {@/if}
        <th>&{data.unit}</th>
        <th class="J_otherCount">&{data.otherCount}</th>
        <th>&{data.netCount}</th>
        {@if data.isAutoOut == 0}
        <th>否</th>
        {@else}
        <th>是</th>
        {@/if}
        <th>
            <a href="javascript:;" class="label-info J_edit"><i class="fa fa-pencil"></i>&nbsp;编辑</a>
            <a href="javascript:;" class="label-info J_del"><i class="fa fa-times"></i>&nbsp;删除</a>
        </th>
        <input type="hidden" class="J_ingredientInfo" name="ingredientInfo" value="&{data.dataStr}"/>
    </tr>
</script>
<script type="text/template" id="selectTpl">
    <ul class="menu hidden">
        <div class="search-box-input">
            <input type="text" class="form-control focusedInput J_ingredientSearchInp">
            <span class="fa fa-search search-box" aria-hidden="true"></span>
        </div>
        {@each list as it}
        <li class="J_render-list" data-id="&{it.ingredientId}"data-unit="&{it.unit}"><a href="javascript:;">[&{it.code}]&nbsp;&{it.name}</a></li>
        {@/each}
    </ul>
</script>
<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('page/dish-management/cost-card-management-add',function(){
            PW.page.DishManagement.CostCardManagementAdd();
        })
    })
</script>