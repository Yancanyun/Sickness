/*-----------------------------------------------------------------------------
* @Description: 配置url地址 (url-core.js)
* @Version: V2.0.0
* @author: cuiy(361151713@qq.com)
* @date 2015.11.02
* ==NOTES:=============================================
* v1.0.0(2015.11.02):
* 经项目实践,发现目前项目架构不适合调试使用,需要不断修改IO层,这样会对前\后端的开发带来不便,故决定使用此插件来解决问题
* ---------------------------------------------------------------------------*/
(function () {
    var
    site = {
        website: '${website}', //站点地址
        staticWebsite: '${website}', // 前端服务器地址
        puiWebsite: '${staticWebsite}tool/pui2/'
    };


    _pw_apiData = {
        // 用户管理
        UserManagement: [
            // 员工管理--列表
            ['delEmployee', site.staticWebsite + 'admin/party/group/employee/ajax/del/&{partyId}', 'delete', '删除员工'],
            ['convertEmployeeStatus', site.staticWebsite + 'admin/party/group/employee/ajax/status/', 'put', '转换员工的状态,即启用\停用互换'],
            ['sendEmployeeId', site.staticWebsite + 'admin/party/group/employee/ajax/tables/&{partyId}', 'get', '获取当前员工的所管辖餐台,为气泡所用'],
            ['searchEmployee', site.staticWebsite + 'admin/party/group/employee/ajax/list', 'get', '搜索当前角色下的员工'],
            // 员工管理--员工添加\编辑
            ['sendEmployeePhone', site.staticWebsite + 'admin/party/group/employee/ajax/checkphone', 'get', '判断员工的电话是否重复'],
            ['sendEmployeeUserName', site.staticWebsite + 'admin/party/group/employee/ajax/checkloginname', 'get', '判断员工的用户命是否重复'],
            ['sendEmployeeNo', site.staticWebsite + 'admin/party/group/employee/ajax/checknumber', 'get', '判断员工的编号是否重复'],

            // 会员管理
            ['sendVipId', site.website + 'admin/party/group/vip/ajax/state', 'get', '改变会员状态时，发送会员id'],
            // 会员管理--列表--删除
            ['delVip', site.website + 'admin/party/group/vip/ajax/del', 'put', '删除会员时，发送会员id'],
            //会员管理--添加\编辑
            ['hasVip', site.website + 'admin/party/group/vip/phone/ajax/exist', 'get', '发送新添加会员的手机号，与数据库进行判重']
        ],
        // 后台登录
        Login: [
            ['getLogin', site.website + 'admin/ajax/login', 'post', '后台登录']
        ],
        // 基本信息管理
        BaseInfoManagement: [
            // 搜索风向标
            ['addOrder', site.website + 'admin/keywords/ajax', 'post', '添加风向标关键字'],
            ['delOrder', site.website + 'admin/keywords/ajax/&{id}', 'delete', '删除风向标关键字'],

            // 首页图片管理
            ['delImg', site.website + 'admin/index/img/ajax/&{id}', 'delete', '删除图片'],
            ['setImg', site.website + 'admin/index/img/ajax/&{id}', 'put', '设置图片为首页']
        ],
        // 饭店管理
        RestaurantManagement: [
            //餐台管理--列表
            //table-management-list.html
            ['changeState', site.website + 'admin/restaurant/table/ajax/state', 'put', '改变餐台状态（停用、恢复）'],
            ['delOneTable', site.website + 'admin/restaurant/table/ajax/&{id}', 'delete', '删除单个餐台'],
            ['searchTable', site.website + 'admin/restaurant/table/ajax/list', 'get', '搜索餐台'],
            ['batchDelete', site.website + 'admin/restaurant/table/ajax', 'post', '批量删除'],
            ['editSendId', site.website + 'admin/restaurant/table/ajax/state', 'get', '编辑时发送餐台ID'],
            //餐台管理--添加\编辑
            //table-management-add.html
            ['sendTableName', site.staticWebsite + 'admin/restaurant/table/ajax/exist', 'post', '判断餐台名称是否重复'],

            //餐台区域管理
            ['saveNewArea', site.website + 'admin/restaurant/area/ajax', 'post', '保存新添加区域信息'],
            ['saveEditArea', site.website + 'admin/restaurant/area/ajax/&{id}', 'put', '保存编辑原有区域信息结果'],
            ['delAreaId', site.website + 'admin/restaurant/area/ajax/&{id}', 'delete', '删除餐台区域时，发送需删除的id']
        ],
        // 库存管理
        StoreManagement: [
            // 供货商管理
            ['addSupplier', site.website + 'admin/store/supplier/ajax', 'post','添加供货商'],
            ['editSupplier', site.website + 'admin/store/supplier/ajax', 'put','编辑供货商'],
            ['delSupplier', site.website + 'admin/store/supplier/ajax/&{id}', 'delete','删除供货商']
            ['addSupplier', site.website + 'admin/storage/supplier/ajax', 'post','添加供货商'],
            ['editSupplier', site.website + 'admin/storage/supplier/ajax', 'put','编辑供货商'],
            ['delSupplier', site.website + 'admin/storage/supplier/ajax/&{id}', 'delete','删除供货商'],

            // 库存分类管理
            ['addStoreClassify', site.website + 'admin/storage/tag/ajax', 'post','添加库存分类'],
            ['editStoreClassify', site.website + 'admin/storage/tag/ajax/&{id}', 'put','编辑库存分类'],
            ['delStoreClassify', site.website + 'admin/storage/tag/ajax/&{id}', 'delete','删除库存分类'],

            // 库存物品管理
            ['delStoreItem', site.website + 'mock/admin/login.json', 'get','删除库存物品']
        ],
        // 菜品管理
        DishManagement: [
            // 单位管理--列表
            ['saveUnit', site.staticWebsite + 'admin/dish/unit/ajax', 'put', '编辑单位'],
            ['saveNewUnit', site.staticWebsite + 'admin/dish/unit/ajax', 'post', '添加单位'],
            ['delUnit', site.staticWebsite + 'admin/dish/unit/ajax/&{id}', 'delete', '删除单位'],

            //菜品管理--列表
            ['delDish', site.staticWebsite + 'mock/admin/dish.json', 'get', '删除菜品'],
            ['sendClassLinkage', site.staticWebsite + 'mock/admin/big-classify.json', 'get', '发送菜品分类联动id,获取菜品小分类'],
            ['sendUnitLinkage', site.staticWebsite + 'mock/admin/small-classify.json', 'get', '发送计量单位联动id,获取计量单位'],
            ['getIngredient', site.staticWebsite + 'mock/admin/ingredient-list.json', 'get', '获取原材料'],
            ['delIngredient', site.staticWebsite + 'mock/admin/ingredient-list.json', 'get', '删除原材料']

            //菜品分类管理
            ['addBigClassify', site.website + 'admin/dish/tag/ajax', 'post', '添加菜品大类'],
            ['addSmallClassify', site.website + 'admin/dish/tag/ajax', 'post', '添加菜品小类'],
            ['editBigClassify', site.website + 'admin/dish/tag/ajax', 'put', '编辑菜品大类'],
            ['editSmallClassify', site.website + 'admin/dish/tag/ajax', 'put', '编辑菜品小类'],
            ['delBigClassify', site.website + 'admin/dish/tag/ajax/&{id}', 'delete', '删除菜品大类'],
            ['delSmallClassify', site.website + 'admin/dish/tag/ajax/&{id}', 'delete', '删除菜品小类'],
            ['search', site.website + 'mock/admin/dish-classify-list.json', 'get', '搜索菜品']
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
            ['saveAuthorityGroup', site.staticWebsite + 'admin/party/security/group/ajax', 'put', '保存编辑后的权限组'],
            ['saveNewAuthorityGroup', site.staticWebsite + 'admin/party/security/group/ajax', 'post', '保存新添加的权限组'],
            ['deleteAuthorityGroup', site.staticWebsite + 'admin/party/security/group/ajax/&{id}', 'delete', '删除权限时，发送权限组id'],

            //authority-group-config.html
            ['delAuthorityOfGroup', site.website + 'admin/party/security/group/permission/ajax/&{id}', 'delete', '权限组配置页面，删除权限']
        ],
        // 公共模块
        Module: [
            ['mulSelectSearch', site.staticWebsite + 'mock/admin/search-item-list.json', 'get', '多选']
        ]
    };
})();