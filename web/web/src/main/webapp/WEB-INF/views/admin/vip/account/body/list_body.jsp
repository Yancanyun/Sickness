<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
            <div class="row">
                <div class="col-sm-12">
                    <ol class="breadcrumb">
                        <li><a href="${website}admin"><i class="fa fa-home"></i>&nbsp;首页</a></li>
                        <li><a href="">会员管理</a></li>
                        <li class="active">会员账户信息管理</li>
                    </ol>
                    <h2>会员账户信息管理</h2>
                </div>
                <div class="col-sm-12">
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h4>搜索</h4>
                        </div>
                        <div class="panel-body">
                            <form action="" class="form-horizontal J_searchForm">
                                <div class="form-group">
                                    <label class="col-sm-3 control-label">关键字</label>
                                    <div class="col-sm-3">
                                        <input type="text" class="form-control w180 J_key" placeholder="请输入姓名/电话/卡号" name="keyword">
                                    </div>
                                </div>
                                <!-- modify -->
                                <div class="form-group">
                                    <label class="col-sm-3 control-label padding-R-17">会员等级</label>
                                    <div class="checkbox block col-sm-7">
                                        <label>
                                            <input type="checkbox" class="J_selectAll" name="gradeIdList" value="0">全选
                                        </label>
                                        <c:forEach items="${list}" var="vipGrade">
                                            <label>
                                                <input type="checkbox" class="J_selectType" name="gradeIdList"  value="${vipGrade.id}" />${vipGrade.name}
                                            </label>
                                        </c:forEach>
                                    </div>
                                </div>
                                <!-- modify -->
                                <div class="col-sm-6 col-sm-offset-3">
                                    <div class="btn-toolbar">
                                        <button class="btn-primary btn" type="submit"><i class="fa fa-search"></i>&nbsp;搜索</button>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                    <div class="panel panel-info">
                        <div class="panel-heading">
                            <h4>列表</h4>
                        </div>
                        <div class="panel-body">
                            <form class="J_operForm">
                                <div class="table-responsive">
                                    <table class="table table-hover table-bordered">
                                        <thead>
                                        <tr>
                                            <th>会员等级</th>
                                            <th>姓名</th>
                                            <th>电话</th>
                                            <th>卡号</th>
                                            <th class="order pagination-reorder" data-order-key="balance">卡内余额</th>
                                            <th>会员积分</th>
                                            <th class="order pagination-reorder" data-order-key="totalConsumption">总消费额</th>
                                            <th>已挂账金额</th>
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
