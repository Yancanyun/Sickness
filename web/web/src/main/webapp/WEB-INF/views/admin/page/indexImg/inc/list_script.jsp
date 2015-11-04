<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/template" id="indexImg">
    <div class="img-container J_imgContainer J_indexImg" data-img-id="&{img.id}">
        <img class="img-responsive" src="&{img.src}" alt="顾客点餐平台首页图片">
        <h5 class="margin-top-15">当前顾客点餐平台首页</h5>
    </div>
</script>
<!-- 备选图片模板-->
<script type="text/template" id="otherImg">
    <div class="img-container J_imgContainer" data-img-id="&{img.id}">
        <img class="img-responsive" src="&{img.src}" alt="顾客点餐平台首页备选图片">
        <a href="javascript:;" class="J_del del"><i class="fa fa-times"></i></a>
        <a class="margin-top-15 btn btn-success J_set" href="javascript:;"><i class="fa fa-check"></i>&nbsp;点击设置为首页</a>
    </div>
</script>
<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('page/base-info-management/index-management', function(S){
            PW.page.BaseInfoManagement.IndexManagement({
                upload: {
                    // 文件接收服务端
                    uploadUrl: '${website}admin/index/img/ajax',
                    // 文件列表
                    fileList: '#fileList',
                    // 选择图片按钮
                    pick: '#filePicker',
                    // 开始上传按钮
                    ctlBtn: '#ctlBtn',
                    fileVal: 'file'
                }
            });
        });
    });
</script>