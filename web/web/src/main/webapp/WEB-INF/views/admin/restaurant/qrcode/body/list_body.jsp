<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<div class="row">
  <div class="col-sm-12">
    <ol class="breadcrumb">
      <li><a href="#"><i class="fa fa-home"></i>&nbsp;首页</a></li>
      <li><a href="#">饭店管理</a></li>
      <li class="active">餐台二维码</li>
    </ol>
    <h2>餐台二维码</h2>
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>生成二维码</h4>
      </div>
      <div class="panel-body create-code-box">
        <form class="form-horizontal J_webDomain" action="${website}admin/restaurant/qrcode/newAllQrCode" method="POST">
          <div class="form-group">
            <label class="control-label">原始网站域名:</label>
            <div class="col-sm-6 initial-domain">${webDomain}
            </div>
          </div>
          <div class="form-group input-box">
            <div class="col-sm-3">
              <input class="form-control new-text" type="text" name="webDomain" placeholder="请输入网站域名" data-valid-rule="notNull" data-valid-tip="请输入网站域名|输入网站域名不可为空"  value="">
            </div>
            <div class="col-sm-3">
              <div class="btn-toolbar">
                <button class="btn-primary btn" type="submit"><i class="fa fa-qrcode"></i>&nbsp;生成二维码</button>
              </div>
            </div>
          </div>
        </form>
      </div>
    </div>
  </div>
  <div class="col-sm-12">
    <div class="panel panel-info">
      <div class="panel-heading">
        <h4>餐台二维码列表</h4>
      </div>
      <div class="panel-body">
        <a class="btn btn-success margin-bottom-15 J_addBtn" href="${website}admin/restaurant/qrcode/download" target="_blank"><i class="fa fa-download"></i>&nbsp;下载全部二维码</a>
        <c:forEach var="areaDto" items="${areaDtoList}">
        <div class="col-sm-12 code-list-border">
          <div class="row">
            <label class="code-area pull-left text-center">${areaDto.area.name}</label>
            <div class="col-sm-12">
              <div class="row">
                <c:forEach var="table" items="${areaDto.tableList}">
                <div class="col-sm-3">
                  <img class="img-responsive center-block" src="${website}resources${table.qrCodePath}" alt="二维码">
                  <h5 class="margin-bottom-15 text-center">${table.name}</h5>
                </div>
                </c:forEach>
              </div>
            </div>
            <div class="row">
              <div class="col-sm-6 col-sm-offset-3">
                <div class="col-sm-6 col-sm-offset-3 margin-bottom-20">
                  <a href="${website}admin/restaurant/qrcode/download/${areaDto.area.id}" class="btn btn-info J_createBtn" target="_blank">
                    <i class="fa fa-download"></i>&nbsp;下载当前区域二维码
                  </a>
                </div>
              </div>
            </div>
          </div>
        </div>
        </c:forEach>
      </div>
    </div>
  </div>
</div>