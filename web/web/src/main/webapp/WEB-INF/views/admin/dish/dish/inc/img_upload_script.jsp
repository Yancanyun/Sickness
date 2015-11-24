<%@ page contentType="text/html;charset=UTF-8"%>

<script type="text/javascript">
    KISSY.ready(function(S){
        S.use('page/dish-management/dish-img-management-add', function(S){
            PW.page.DishManagement.DishImgManagementAdd.BigPic({
                upload: {
                    // 文件接收服务端
                    uploadUrl: '${website}admin/dish/img/upload/ajax',
                    // 文件列表
                    fileList: '#fileList1',
                    // 选择图片按钮
                    pick: '#filePicker1',
                    // 开始上传按钮
                    ctlBtn: '#ctlBtn1',
                    fileVal: 'image'
                }
            });
            PW.page.DishManagement.DishImgManagementAdd.SmallPic({
                upload: {
                    // 文件接收服务端
                    uploadUrl: '${website}admin/dish/img/upload/ajax',
                    // 文件列表
                    fileList: '#fileList2',
                    // 选择图片按钮
                    pick: '#filePicker2',
                    // 开始上传按钮
                    ctlBtn: '#ctlBtn2',
                    fileVal: 'image'
                }
            });
            PW.page.DishManagement.DishImgManagementAdd.Tool();
        });
    });
</script>
