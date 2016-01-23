<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<div class="row">
    <div class="col-sm-12">
        <ol class="breadcrumb">
            <li>
                <a href="#"><i class="fa fa-home"></i>&nbsp;首页</a>
            </li>
            <li>
                <a href="#">会员管理</a>
            </li>
            <li>
                <a href="#" class="active">会员卡管理</a>
            </li>
        </ol>
        <h2>会员卡管理</h2>
    </div>
    <!-- 搜索面板 -->
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>搜索</h4>
            </div>
            <div class="panel-body">
                <form class="form-horizontal J_searchForm" action="" method="">
                    <div class="form-group">
                        <label class="col-sm-5 control-label">发卡开始日期</label>
                        <div class="col-sm-2">
                            <input type="text" class="col-sm-3 form-control J_date" name="startTime" value=""/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-5 control-label">发卡结束日期</label>
                        <div class="col-sm-2">
                            <input type="text" class="col-sm-3 form-control J_date" name="endTime" value=""/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-5 control-label">关键字</label>
                        <div class="col-sm-2">
                            <input type="text" class="col-sm-3 form-control J_key" placeholder="请输入会员手机号/姓名" name="keyword" value="">
                        </div>
                    </div>
                    <div class="col-sm-6 col-sm-offset-5">
                        <div class="btn-toolbar">
                            <button class="btn btn-primary J_search" type="button"><i class="fa fa-search"></i>&nbsp;搜索</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- 列表展示 -->
    <div class="col-sm-12">
        <div class="panel panel-info">
            <div class="panel-heading">
                <h4>会员卡管理</h4>
            </div>
            <div class="panel-body">
                <form class="J_operForm" method="" action="">
                    <input class="J_card" type="hidden" value="" name="id"/>
                    <input class="J_card" type="hidden" value="" name="name"/>
                    <input class="J_card" type="hidden" value="" name="phone"/>
                    <input class="J_card" type="hidden" value="" name="cardNumber"/>
                    <input class="J_card" type="hidden" value="" name="createdTime"/>
                    <input class="J_card" type="hidden" value="" name="validityTime"/>
                    <input class="J_card" type="hidden" value="" name="permanentlyEffective"/>
                    <input class="J_card" type="hidden" value="" name="operator"/>
                    <input class="J_card" type="hidden" value="" name="status"/>
                    <div class="table-responsive">
                        <table class="table table-hover table-bordered">
                            <thead>
                            <tr>
                                <th>姓名</th>
                                <th>电话</th>
                                <th>卡号</th>
                                <th>发卡日期</th>
                                <th>有效期</th>
                                <th>是否永久有效</th>
                                <th>操作人</th>
                                <th>状态</th>
                                <th>操作</th>
                            </tr>
                            </thead>
                            <tbody id="J_template">

                            </tbody>
                        </table>
                        <div class="J_pagination"></div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>