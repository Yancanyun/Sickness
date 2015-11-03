/*-----------------------------------------------------------------------------
 * @Description:    配置url地址 (url-core.js)
 * @Version: 	    V2.0.0
 * @author: 		cuiy(361151713@qq.com)
 * @date			2015.11.02
 * ==NOTES:=============================================
 * v1.0.0(2015.11.02):
 * 经项目实践,发现目前项目架构不适合调试使用,需要不断修改IO层,这样会对前\后端的开发带来不便,故决定使用此插件来解决问题
 * ---------------------------------------------------------------------------*/
(function () {
    var
        site = {
            website: '${website}', //站点地址
            staticWebsite: '${staticWebsite}', // 前端服务器地址
            puiWebsite: '${staticWebsite}tool/pui2/'
        };


    _pw_apiData = {
        // 用户管理
        UserManagement: [
            // 员工管理--列表
            ['delEmployee', site.staticWebsite + 'mock/admin/employee-list.json', 'get', '删除员工'],
            ['convertEmployeeStatus', site.staticWebsite + 'mock/admin/employee-list.json', 'get', '转换员工的状态,即启用\停用互换'],
            ['sendEmployeeId', site.staticWebsite + 'mock/admin/employee-table.json', 'get', '获取当前员工的所管辖餐台,为气泡所用'],
            ['searchEmployee', site.staticWebsite + 'mock/admin/employee-list.json', 'get', '搜索当前角色下的员工'],
            // 员工管理--员工添加\编辑
            ['sendEmployeePhone', site.staticWebsite + 'mock/admin/hasEmployee.json', 'get', '判断员工的电话是否重复'],
            ['sendEmployeeUserName', site.staticWebsite + 'mock/admin/hasEmployee.json', 'get', '判断员工的用户命是否重复'],
            ['sendEmployeeNo', site.staticWebsite + 'mock/admin/hasEmployee.json', 'get', '判断员工的编号是否重复'],

            // 会员管理
            ['sendVipId', site.staticWebsite + 'mock/admin/change-vip-status.json', 'get', '改变会员状态时，发送会员id'],
            // 会员管理--列表--删除
            ['delVip', site.staticWebsite + 'mock/admin/del-vip.json', 'get', '删除会员时，发送会员id'],
            //会员管理--添加\编辑
            ['hasVip', site.staticWebsite + 'mock/admin/hasVip.json', 'get', '发送新添加会员的手机号，与数据库进行判重']
        ],
        //  后台登录
        Login: [
            ['getLogin', site.website + 'admin/ajax/login', 'post', '后台登录']
        ],
        // 基本信息管理
        BaseInfoManagement: [
            // 搜索风向标
            ['addOrder', site.staticWebsite + 'mock/admin/add-order.json', 'get', '添加风向标关键字'],
            ['delOrder', site.staticWebsite + 'mock/admin/del-order.json', 'get', '删除风向标关键字']

        ],
        // 饭店管理
        RestaurantManagement: [
            //餐台管理--列表
            //table-management-list.html
            ['changeState', site.staticWebsite + 'mock/admin/login.json', 'get', '改变餐台状态（停用、恢复）'],
            ['delOneTable', site.staticWebsite + 'mock/admin/login.json', 'get', '删除单个餐台'],
            ['searchTable', site.staticWebsite + 'mock/admin/table-list.json', 'get', '搜索餐台'],
            ['batchDelete', site.staticWebsite + 'mock/admin/login.json', 'get', '批量删除'],
            //餐台管理--添加\编辑
            //table-management-add.html
            ['sendTableName', site.staticWebsite + 'mock/admin/login.json', 'get', '判断餐台名称是否重复'],

            //餐台区域管理
            ['saveNewArea', site.website + 'admin/restaurant/area/ajax', 'post', '保存新添加区域信息'],
            ['saveEditArea', site.website + 'admin/restaurant/area/ajax', 'put', '保存编辑原有区域信息结果'],
            ['delAreaId', site.website + 'admin/restaurant/area/ajax', 'delete', '删除餐台区域时，发送需删除的id']
        ],
        // 菜品管理
        DishManagement: [
            // 单位管理--列表
            ['saveUnit', site.staticWebsite + 'mock/admin/save-unit.json', 'get', '编辑单位'],
            ['saveNewUnit', site.staticWebsite + 'mock/admin/save-unit.json', 'get', '添加单位'],
            ['delUnit', site.staticWebsite + 'mock/admin/login.json', 'get', '删除单位'],

            //菜品管理--列表
            ['delDish', site.staticWebsite + 'mock/admin/dish.json', 'get', '删除菜品'],
            ['sendClassLinkage', site.staticWebsite + 'mock/admin/big-classify.json', 'get', '发送菜品分类联动id,获取菜品小分类'],
            ['sendUnitLinkage', site.staticWebsite + 'mock/admin/small-classify.json', 'get', '发送计量单位联动id,获取计量单位'],
            ['getIngredient', site.staticWebsite + 'mock/admin/ingredient-list.json', 'get', '获取原材料'],
            ['delIngredient', site.staticWebsite + 'mock/admin/ingredient-list.json', 'get', '删除原材料']
        ],
        // 权限管理
        AuthorityManagement: [
            //权限管理\启用、禁用权限
            ['sendAuthorityId', site.staticWebsite + 'mock/admin/change-authority.json', 'get', '改变权限时，发送当前权限的id'],
            //权限组管理\删除权限组
            ['delAuthorityGroup', site.staticWebsite + 'mock/admin/del-authority-group.json', 'get', '删除权限组时，发送当前权限组的id和当前用户的id'],

            //base-config.html
            ['saveAuthority', site.website + 'admin/party/security/permission/ajax', 'put', '保存编辑后的权限'],
            ['saveNewAuthority', site.website + 'admin/party/security/permission/ajax', 'post', '保存新添加的权限'],
            ['delAuthority', site.website + 'admin/party/security/permission/ajax', 'delete', '删除权限时，发送权限id'],

            //authority-group-list.html
            ['saveAuthorityGroup', site.staticWebsite + 'mock/admin/save-authority.json', 'get', '保存编辑后的权限组'],
            ['saveNewAuthorityGroup', site.staticWebsite + 'mock/admin/save-authority.json', 'get', '保存新添加的权限组'],
            ['deleteAuthorityGroup', site.staticWebsite + 'mock/admin/login.json', 'get', '删除权限时，发送权限组id'],

            //authority-group-config.html
            ['delAuthorityOfGroup', site.staticWebsite + 'mock/admin/login.json', 'get', '权限组配置页面，删除权限']
        ],
        // 公共模块
        Module: [
            ['mulSelectSearch', site.staticWebsite + 'mock/admin/search-item-list.json', 'get', '多选']
        ]
    };
})();