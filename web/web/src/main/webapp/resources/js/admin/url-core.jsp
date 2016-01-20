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
        staticWebsite: '${staticWebsite}', // 前端服务器地址
        puiWebsite: '${staticWebsite}tool/pui2/'
    };


    _pw_apiData = {
        // 用户管理
        UserManagement: [
            // 员工管理--列表
            ['delEmployee', site.website + 'admin/party/group/employee/ajax/del/&{partyId}', 'delete', '删除员工'],
            ['convertEmployeeStatus', site.website + 'admin/party/group/employee/ajax/status/', 'put', '转换员工的状态,即启用\停用互换'],
            ['sendEmployeeId', site.website + 'admin/party/group/employee/ajax/tables/&{partyId}', 'get', '获取当前员工的所管辖餐台,为气泡所用'],
            ['searchEmployee', site.website + 'admin/party/group/employee/ajax/list', 'get', '搜索当前角色下的员工'],
            // 员工管理--员工添加\编辑
            ['sendEmployeePhone', site.website + 'admin/party/group/employee/ajax/checkphone', 'get', '判断员工的电话是否重复'],
            ['sendEmployeeUserName', site.website + 'admin/party/group/employee/ajax/checkloginname', 'get', '判断员工的用户命是否重复'],
            ['sendEmployeeNo', site.website + 'admin/party/group/employee/ajax/checknumber', 'get', '判断员工的编号是否重复']
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
            ['setImg', site.website + 'admin/index/img/ajax/&{id}', 'put', '设置图片为首页'],

            //打印机管理
            ['delPrinter',site.website + 'admin/printer/ajax/&{id}', 'delete', '删除打印机'],
            ['changePrinterType',site.website + 'admin/printer/ajax/check', 'get', '改变打印机类型']
        ],
        // 饭店管理
        RestaurantManagement: [
            //餐台管理--列表
            //table-management-list.html
            ['changeTableStatus', site.website + 'admin/restaurant/table/ajax/status', 'put', '改变餐台状态（停用、恢复）'],
            ['delOneTable', site.website + 'admin/restaurant/table/ajax/&{id}', 'delete', '删除单个餐台'],
            ['searchTable', site.website + 'admin/restaurant/table/ajax/list', 'get', '搜索餐台'],
            ['batchDelete', site.website + 'admin/restaurant/table/ajax', 'post', '批量删除'],
            ['editSendId', site.website + 'admin/restaurant/table/ajax/status', 'get', '编辑时发送餐台ID'],
            //餐台管理--添加\编辑
            //table-management-add.html
            ['sendTableName', site.staticWebsite + 'admin/restaurant/table/ajax/exist', 'post', '判断餐台名称是否重复'],

            //餐台区域管理
            ['saveNewArea', site.website + 'admin/restaurant/area/ajax', 'post', '保存新添加区域信息'],
            ['saveEditArea', site.website + 'admin/restaurant/area/ajax/&{id}', 'put', '保存编辑原有区域信息结果'],
            ['delAreaId', site.website + 'admin/restaurant/area/ajax/&{id}', 'delete', '删除餐台区域时，发送需删除的id'],

            //常用备注管理
            ['chooseRemarkBigTag', site.website + 'admin/restaurant/remark/ajax/list', 'get','选择备注大类渲染对应的列表内容'],
            ['delSmallClassify', site.website + 'admin/restaurant/remark/ajax/remark/tag/&{id}', 'delete','删除备注小类'],
            ['addSmallClassify', site.website + 'admin/restaurant/remark/ajax/remark/tag', 'post','增加备注小类'],
            ['editSmallClassify', site.website + 'admin/restaurant/remark/ajax/remark/tag/&{id}', 'put','编辑备注小类'],
            ['delRemarkContentId', site.website + 'admin/restaurant/remark/ajax/remark/&{id}', 'delete','删除备注内容'],
            ['addRemarkContent', site.website + 'admin/restaurant/remark/ajax/remark', 'post','增加备注内容'],
            ['editRemarkContent', site.website + 'admin/restaurant/remark/ajax/remark/&{id}', 'put','编辑备注内容'],

            //餐段管理
            ['setCurrentPeriod', site.website + 'admin/restaurant/meal/period/ajax/current/&{id}', 'put','设置当前餐段'],
            ['delPeriod', site.website + 'admin/restaurant/meal/period/ajax/&{id}', 'delete', '删除餐段'],
            ['getWeight', site.website + 'admin/restaurant/meal/period/ajax/weight', 'get', '添加餐段时获取默认排序'],
            ['changeStatus', site.website + 'admin/restaurant/meal/period/ajax/status/&{id}', 'put', '改变状态'],
            ['saveEditItem', site.website + 'admin/restaurant/meal/period/ajax', 'put', '保存编辑项']
        ],
        // 库存管理
        StoreManagement: [
            // 供货商管理
            ['addSupplier', site.website + 'admin/storage/supplier/ajax', 'post','添加供货商'],
            ['editSupplier', site.website + 'admin/storage/supplier/ajax', 'put','编辑供货商'],
            ['delSupplier', site.website + 'admin/storage/supplier/ajax/&{id}', 'delete','删除供货商'],

            // 库存分类管理
            ['addStoreClassify', site.website + 'admin/storage/tag/ajax', 'post','添加库存分类'],
            ['editStoreClassify', site.website + 'admin/storage/tag/ajax/&{id}', 'put','编辑库存分类'],
            ['delStoreClassify', site.website + 'admin/storage/tag/ajax/&{id}', 'delete','删除库存分类'],

            // 库存物品管理
            ['delStoreItem', site.website + 'admin/storage/item/ajax/&{id}', 'delete','删除库存物品'],
            ['editStoreItemConversionRatio', site.website + 'admin/storage/item/unit/conversion/ajax', 'put','编辑换算比例'],

            //存放点管理
            ['delDepot', site.staticWebsite + 'admin/storage/depot/ajax/&{id}', 'delete', '删除存放点'],

            //结算中心管理
            ['getSettlementList', site.website + 'admin/storage/settlement/supplier/ajax/list', 'get', '获取结算列表'],

            // 库存单据管理
            ['getMoney', site.website + '/test/ajax/bill', 'get', '获取当前物品小计'],
            ['delStoreBill', site.staticWebsite + 'mock/admin/get-bill.json', 'get', '删除库存单据'],
            ['addStoreBill', site.website + '/test/ajax', 'post', '添加库存单据']
        ],
        // 菜品管理
        DishManagement: [
            // 单位管理--列表
            ['saveUnit', site.staticWebsite + 'admin/dish/unit/ajax', 'put', '编辑单位'],
            ['saveNewUnit', site.staticWebsite + 'admin/dish/unit/ajax', 'post', '添加单位'],
            ['delUnit', site.staticWebsite + 'admin/dish/unit/ajax/&{id}', 'delete', '删除单位'],

            // 菜品管理--列表
            ['delDish', site.website + 'admin/dish/ajax/&{id}', 'delete', '删除菜品'],
            ['changeDishStatus', site.website + 'admin/dish/ajax/status/&{id}', 'put', '编辑菜品状态'],
            ['sendClassLinkage', site.website + 'admin/dish/ajax/tag/children', 'get', '发送菜品分类联动id,获取菜品小分类'],
            ['sendUnitLinkage', site.staticWebsite + 'mock/admin/small-classify.json', 'get', '发送计量单位联动id,获取计量单位'],
            // 菜品管理--编辑菜品图片
            ['delPic', site.website + 'admin/dish/img/&{id}', 'delete', '删除菜品图片'],

            // 菜品分类管理
            ['addClassify', site.staticWebsite + 'admin/dish/tag/ajax', 'post', '添加菜品分类'],
            ['editClassify', site.staticWebsite + 'admin/dish/tag/ajax', 'put', '编辑菜品分类'],
            ['delClassify', site.staticWebsite + 'admin/dish/tag/ajax/&{id}', 'delete', '删除菜品分类'],
            ['search', site.staticWebsite + 'admin/dish/tag/ajax/search', 'get', '搜索菜品'],

            //今日特价
            ['revoteSpecials', site.website + 'admin/dish/feature/ajax/&{id}', 'delete', '撤销今日特价菜品'],
            ['selectDishClassifyOfSpecials', site.website + 'admin/dish/feature/dish/ajax/list', 'get','选择今日特价中的菜品分类'],
            ['sendSelectedDishOfSpecials',site.website + 'admin/dish/feature/ajax', 'post','发送今日特价中的被选中的菜品'],

            //销量排行
            ['revoteSales', site.staticWebsite + 'admin/dish/feature/ajax/&{id}', 'get', '撤销销量排行菜品'],
            ['selectDishClassifyOfSales', site.staticWebsite + 'admin/dish/feature/dish/ajax/list', 'get','选择销量排行中的菜品分类'],
            ['sendSelectedDishOfSales',site.staticWebsite + 'admin/dish/feature/ajax', 'get','发送销量排行中的被选中的菜品'],

            //本店特色
            ['revoteSpecialities', site.website + 'admin/dish/feature/ajax/&{id}', 'delete', '撤销本店特色菜品'],
            ['selectDishClassifyOfSpecialities', site.website + 'admin/dish/feature/dish/ajax/list', 'get','选择本店特色中的菜品分类'],
            ['sendSelectedDishOfSpecialities',site.website + 'admin/dish/feature/ajax', 'post','发送本店特色中的被选中的菜品'],

            // 菜品口味
            ['deleteTaste', site.staticWebsite + 'admin/dish/taste/ajax/&{id}', 'delete', '删除菜品口味'],
            ['saveEditTaste', site.staticWebsite + 'admin/dish/taste/ajax', 'put', '发送编辑的菜品口味'],
            ['saveAddTaste', site.staticWebsite + 'admin/dish/taste/ajax', 'post', '发送新添加的菜品口味']
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
        //会员管理
        VipManagement:[
            // 会员管理
            ['sendVipId', site.website + 'admin/party/group/vip/ajax/status', 'get', '改变会员状态时，发送会员id'],
            // 会员管理--列表--删除
            ['delVip', site.website + 'admin/party/group/vip/ajax/del', 'put', '删除会员时，发送会员id'],
            //会员管理--添加\编辑
            ['hasVip', site.website + 'admin/party/group/vip/phone/ajax/exist', 'get', '发送新添加会员的手机号，与数据库进行判重'],
            //多倍积分方案管理--删除
            ['delExsitedPlan', site.website + 'admin/vip/multiple/integral/plan/ajax/&{id}', 'delete', '删除多倍积分方案时，发送方案id'],
            //多倍积分方案管理--停用/启用
            ['sendModifiedPlan', site.website + 'admin/vip/multiple/integral/plan/ajax/status', 'put', '停用、启用时，发送当前方案的id和status'],
            //多倍积分方案管理--新方案的“保存”
            ['sendAddedPlan', site.website + 'admin/vip/multiple/integral/plan/ajax', 'post', '保存新添加的方案时，发送方案内容，返回该方案的id'],
            //多倍积分方案管理--修改后的方案“保存”
            ['sendEditedPlan', site.website + 'admin/vip/multiple/integral/plan/ajax/&{id}', 'put', '保存修改后的方案时，发送方案内容'],
            //会员价方案
            ['sendEditPlan', site.website + 'admin/vip/price/plan/ajax', 'put', '保存编辑后的方案'],
            ['delPlan', site.website + 'admin/vip/price/plan/ajax/&{id}', 'delete', '删除时发送ID'],
            //会员价
            ['sendSearchInfo', site.website + 'admin/vip/price/ajax/list', 'get','搜索'],
            ['sendEditInfo', site.website + 'admin/vip/price/ajax', 'put', '编辑'],
            ['sendData', site.website + 'admin/vip/price/ajax/generate', 'get', '自动生成发送数据'],
            //充值方案管理
            ['sendEditInfo', site.website + 'admin/vip/recharge/plan/ajax/&{id}', 'put', '保存编辑数据'],
            ['sendAddInfo', site.website + 'admin/vip/recharge/plan/ajax', 'post', '保存新添数据'],
            ['delPlanId', site.website + 'admin/vip/recharge/plan/ajax/&{id}', 'delete', '发送删除数据id'],
            ['changeStatus', site.website + 'admin/vip/recharge/plan/ajax/status', 'put', '改变状态'],
            //会员等级页面
            ['delGrade', site.website + 'admin/vip/grade/ajax/&{id}', 'delete', '删除等级时发送id']
            //会员账户信息管理--停用/启用
            ['changeAccountStatus', site.staticWebsite + 'admin/vip/account/status/ajax','get','点击停用/启用时，发送当前账户的id和status']
        ],
        // 公共模块
        Module: [
            ['mulSelectSearch', site.staticWebsite + 'mock/admin/search-item-list.json', 'get', '多选'],
            ['getAssistantCode', site.website + 'admin/common/tool/str2py/ajax', 'get', '获取名称对应的助记码']
        ]
    };
})();