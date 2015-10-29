<%@ page contentType="text/html;charset=UTF-8" %>

(function(){

    var site ={
        website:'${website}', //站点地址
        staticWebsite: '${staticWebsite}', // 前端服务器地址
        puiWebsite: '${staticWebsite}/tool/pui2/'
    }


    _pw_env = {
        status: 0, //0-前端调试，1-后端调试, 2-后端部署
        website: site.website,
        staticWebsite: site.staticWebsite,
        puiWebsite: site.puiWebsite,
        tag: '',
        pkgs:[
            {
                name: 'pio',
                path: site.staticWebsite + 'js/admin/'
            },
            {
                name: 'widget',
                path: site.staticWebsite + 'tool/base-widget/js/'
            },
            {
                name: 'module',
                path: site.staticWebsite + 'js/admin/'
            },
            {
                name: 'page',
                path: site.staticWebsite + 'js/admin/'
            }
        ],
        //对pui各个组件的一个
        modSettings:{
            notifier: {
                top: 100
            },
            dialog:{
                opacity: 0.5,
                position: 'fixed',
                theme: 'white',
                title: '提示信息',
                width: 600,
                top : 100,
                content: '暂无内容！',
                themeUrl: site.staticWebsite + 'tool/base-widget/css/core.css'
            },
            defender:{
                themeUrl: site.staticWebsite + 'tool/base-widget/css/core.css'
            },
            scroll:{
                cursorborderradius: 0,
                cursorcolor: '#3d3d3d'
            },
            pagination: {
                themePackage: {
                    layout: ['first','num','last'],
                    //默认分页样式（定义了五种）
                    themeCss: 'pagination-white',
                    //首页按钮内容显示
                    firstPageTip: '<i class="fa fa-angle-left"></i>',
                    //尾页按钮内容显示
                    lastPageTip: '<i class="fa fa-angle-right"></i>'
                },
                themeUrl: site.staticWebsite + 'tool/base-widget/css/pagination.css'
            },
            tooltip:{
                position: {
                    my: 'tc',
                    at: 'bc' //options: tl,tc,tr, rt,rc,rb, bl,bc,br,lt,lc,lb
                }
            },
            skylo:{
                themeUrl: site.staticWebsite + 'tool/base-widget/css/skylo.css'
            },
            // 文件上传swf路径
            upload: {
                swfUrl: site.staticWebsite + 'tool/webuploader/swf/Uploader.swf'
            }
        },
        //统一错误信息入口
        msg:{
            0: '网络加载错误'
        },
        //地址信息
        url:{
            // 菜品管理
            dishManagement: {
                dishManagement: {
                    // 删除菜品
                    delDish: site.staticWebsite + 'mock/admin/dish.json',
                    // 发送菜品分类联动id
                    sendClassLinkage: site.staticWebsite + 'mock/admin/big-classify.json',
                    // 发送计量单位联动id
                    sendUnitLinkage: site.staticWebsite + 'mock/admin/small-classify.json',
                    // 获取原材料
                    getIngredient: site.staticWebsite + 'mock/admin/ingredient-list.json',
                    // 删除原材料
                    delIngredient: site.staticWebsite + 'mock/admin/ingredient-list.json'
                }
            },
            baseInfoManagement: {
                searchVane: {
                    addOrder: site.staticWebsite + 'mock/admin/add-order.json',
                    delOrder: site.staticWebsite + 'mock/admin/del-order.json'
                }
            },
            // 用户管理（会员管理、员工管理）
            userManagement: {
                // 员工管理
                employeeManagement: {
                    // 发送员工id，获取相应角色
                    sendEmployeeId: site.staticWebsite + 'mock/admin/employee-table.json'
                }
            },
            login: {
                login: {
                    getLogin: site.website + 'admin/ajax/login'
                }
            },
            //权限管理
            authorityManagement: {
                //权限管理
                authorityManagement: {
                    //发送权限id
                    sendAuthorityId: site.website + 'mock/admin/change-authority.json',
                    //保存编辑后的权限
                    saveAuthority: site.website + 'admin/party/security/permission/ajax',
                    //保存新添加的权限
                    saveNewAuthority: site.website + 'admin/party/security/permission/ajax',
                    //删除权限时，发送权限id
                    delAuthority: site.website + 'admin/party/security/permission/ajax'
                },
            },
            // 原配料管理
            ingredientManagement: {
                //原配料单位管理
                ingredientManagement:{
                    //编辑后的原配料单位
                    saveIngredient: site.website + 'admin/dish/unit/ajax/unit',
                    //保存的原配料单位
                    saveNewIngredient: site.website + 'admin/dish/unit/ajax/unit',
                    //删除原配料单位时，发送原配料单位id
                    delIngredient: site.website + 'admin/dish/unit/ajax/unit'
                },
            },
            restManagement:{
                restManagement:{
                    tableAreaId:site.staticWebsite + 'mock/admin/table-area-list.json',
                    changeStatus:site.staticWebsite + 'mock/admin/login.json'
                }
            },
            // 公用模块
            module: {
                // 多选
                mulSelect: {
                    // 搜索
                    search: site.staticWebsite + 'mock/admin/search-item-list.json'
                }
            }
        }
    }
})();