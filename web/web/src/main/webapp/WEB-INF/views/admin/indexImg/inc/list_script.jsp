<%@ page contentType="text/html;charset=UTF-8" %>

<script type="text/javascript">
    KISSY.ready(function (S) {
        S.use('page/base-info-management/index-management', function (S) {
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