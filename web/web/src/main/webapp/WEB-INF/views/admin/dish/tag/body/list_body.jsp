<%@ page contentType="text/html;charset=UTF-8" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
            <li><a href="#">菜品管理</a></li>
            <li class="active">菜品分类管理</li>
        </ol>
        <h2>菜品管理-菜品分类列表</h2>
    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>搜索</h4>
            </div>
            <div class="panel-body">
                <form class="form-horizontal J_searchForm">
                    <div class="form-group">
                        <label class="col-sm-3 control-label">选择菜品总分类</label>
                        <div class="col-sm-3">
                            <select class="form-control" name="rootId">
                                <option value="0">商品</option>
                                <option value="1">菜品</option>
                                <option value="2">酒水</option>
                                <option value="3">其他</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-sm-6 col-sm-offset-3">
                        <div class="btn-toolbar">
                            <button class="btn-primary btn J_search" type="submit"><i class="fa fa-search"></i>&nbsp;搜索</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>菜品分类列表</h4>
            </div>
            <div class="panel-body">
                <form class="J_operForm">
                    <a class="btn btn-success margin-bottom-15 J_addBigTag" href="javascript:;"><i class="fa fa-plus"></i>&nbsp;添加菜品大类</a>
                    <ul class="classify J_classify">
                        <li class="clearfix" data-big-tag-id="9999" data-root-tag-id="3" data-root-tag-name="菜类" data-big-tag-name="意面大类红酒">
                            <div class="J_inputGroup">
                                <input type="hidden" name="pId" value="3" />
                                <input type="hidden" name="id" value="9999" />
                                <input type="hidden" name="name" value="意面大类红酒" />
                                <input type="hidden" name="weight" value="1" />
                                <input class="J_printAfterConfirmOrder" type="hidden" name="printAfterConfirmOrder" value="1" />
                                <input type="hidden" name="maxPrintNum" value="1" />
                                <input class="J_printId" type="hidden" name="printerId" value="1" />
                                <input type="hidden" name="timeLimit" value="10" />
                                <input type="hidden" name="type" value="1" />
                            </div>
                            <span class="root-tag">[酒水类]</span>
                            <span class="big-tag">意面大类红酒</span>
                            <a class="J_foldToggle" href="javascript:;">展开 <<</a>
                            <a href="javascript:;" class="label-info pull-right oper J_delBigTag"><i class="fa fa-times"></i>&nbsp;删除大类</a>
                            <a href="javascript:;" class="label-info pull-right oper J_editBigTag"><i class="fa fa-pencil"></i>&nbsp;编辑大类</a>
                            <a href="javascript:;" class="label-info pull-right oper J_addSmallTag"><i class="fa fa-plus"></i>&nbsp;添加小类</a>
                            <ul style="display: none;" class="margin-top-20 J_smallClassify" data-big-tag-id="9999" data-big-tag-name="意面大类红酒">
                                <li class="clearfix" data-small-tag-name="name1" data-small-tag-id="xiaolei1">
                                    <input type="hidden" name="pId" value="9999" />
                                    <input type="hidden" name="id" value="xiaolei1" />
                                    <input type="hidden" name="name" value="name1" />
                                    <input type="hidden" name="weight" value="1" />
                                    <input class="J_printAfterConfirmOrderSmall" type="hidden" name="printAfterConfirmOrder" value="1" />
                                    <input type="hidden" name="maxPrintNum" value="1" />
                                    <input class="J_printId" type="hidden" name="printerId" value="1" />
                                    <input type="hidden" name="timeLimit" value="10" />
                                    <input type="hidden" name="type" value="1" />
                                    <span class="small-tag">name1</span>
                                    <a href="javascript:;" class="label-info pull-right oper J_delSmallTag"><i class="fa fa-times"></i>&nbsp;删除小类</a>
                                    <a href="javascript:;" class="label-info pull-right oper J_editSmallTag"><i class="fa fa-pencil"></i>&nbsp;编辑小类</a>
                                </li>
                            </ul>
                        </li>
                        <li class="clearfix" data-big-tag-id="99998888" data-root-tag-id="5" data-root-tag-name="菜类" data-big-tag-name="意面">
                            <div class="J_inputGroup">
                                <input type="hidden" name="pId" value="5" />
                                <input type="hidden" name="id" value="99998888" />
                                <input type="hidden" name="name" value="披萨" />
                                <input type="hidden" name="weight" value="2" />
                                <input class="J_printAfterConfirmOrder" type="hidden" name="printAfterConfirmOrder" value="0" />
                                <input type="hidden" name="maxPrintNum" value="0" />
                                <input class="J_printId" type="hidden" name="printerId" value="0" />
                                <input type="hidden" name="timeLimit" value="20" />
                                <input type="hidden" name="type" value="1" />
                            </div>
                            <span class="root-tag">[套餐]</span>
                            <span class="big-tag">披萨</span>
                            <a class="J_foldToggle" href="javascript:;">展开 <<</a>
                            <a href="javascript:;" class="label-info pull-right oper J_delBigTag"><i class="fa fa-times"></i>&nbsp;删除大类</a>
                            <a href="javascript:;" class="label-info pull-right oper J_editBigTag"><i class="fa fa-pencil"></i>&nbsp;编辑大类</a>
                            <a href="javascript:;" class="label-info pull-right oper J_addSmallTag"><i class="fa fa-plus"></i>&nbsp;添加小类</a>
                            <ul style="display: none;" class="margin-top-20 J_smallClassify" data-big-tag-id="99998888" data-big-tag-name="披萨">
                                <li class="clearfix" data-small-tag-name="name2" data-small-tag-id="xiaolei2">
                                    <input type="hidden" name="pId" value="99998888" />
                                    <input type="hidden" name="id" value="xiaolei2" />
                                    <input type="hidden" name="name" value="name2" />
                                    <input type="hidden" name="weight" value="1" />
                                    <input class="J_printAfterConfirmOrderSmall" type="hidden" name="printAfterConfirmOrder" value="1" />
                                    <input type="hidden" name="maxPrintNum" value="1" />
                                    <input class="J_printId" type="hidden" name="printerId" value="1" />
                                    <input type="hidden" name="timeLimit" value="10" />
                                    <input type="hidden" name="type" value="1" />
                                    <span class="small-tag">name2</span>
                                    <a href="javascript:;" class="label-info pull-right oper J_delSmallTag"><i class="fa fa-times"></i>&nbsp;删除小类</a>
                                    <a href="javascript:;" class="label-info pull-right oper J_editSmallTag"><i class="fa fa-pencil"></i>&nbsp;编辑小类</a>
                                </li>
                                <li class="clearfix" data-small-tag-name="name3" data-small-tag-id="xiaolei3">
                                    <input type="hidden" name="pId" value="99998888" />
                                    <input type="hidden" name="id" value="xiaolei3" />
                                    <input type="hidden" name="name" value="name3" />
                                    <input type="hidden" name="weight" value="1" />
                                    <input class="J_printAfterConfirmOrderSmall" type="hidden" name="printAfterConfirmOrder" value="1" />
                                    <input type="hidden" name="maxPrintNum" value="1" />
                                    <input class="J_printId" type="hidden" name="printerId" value="1" />
                                    <input type="hidden" name="timeLimit" value="10" />
                                    <input type="hidden" name="type" value="1" />
                                    <span class="small-tag">name3</span>
                                    <a href="javascript:;" class="label-info pull-right oper J_delSmallTag"><i class="fa fa-times"></i>&nbsp;删除小类</a>
                                    <a href="javascript:;" class="label-info pull-right oper J_editSmallTag"><i class="fa fa-pencil"></i>&nbsp;编辑小类</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                    <select class="form-control hidden J_bigClassifyHidden" disabled="disabled" name="pId">
                        <option value="2">菜类</option>
                        <option value="3">酒水类</option>
                        <option value="4">商品</option>
                        <option value="5">套餐</option>
                    </select>
                </form>
            </div>
        </div>
    </div>
</div>
