/*-----------------------------------------------------------------------------
 * @Description:    客户手机端--配置url地址 (url-core.js)
 * @Version: 	    V2.0.0
 * @author: 		cuiy(361151713@qq.com)
 * @date			2016.01.07
 * ==NOTES:=============================================
 * v1.0.0(2016.01.07)
 * ---------------------------------------------------------------------------*/
(function(){
    var
        site ={
            website: '${website}', //站点地址
            staticWebsite: '${staticWebsite}', // 前端服务器地址
            puiWebsite: '${staticWebsite}tool/pui2/'
        };


    _pw_apiData = {
        // 菜品分类图片版
        Classify: [
            ['getDishList', site.website + 'mobile/dish/image/ajax/list', 'get', '获取菜品'],
            ['addDish', site.staticWebsite + 'mock/mobile/add-dish.json', 'get', '添加菜品']
        ],
        // 搜索
        Search: [
            ['search', site.staticWebsite + 'mock/mobile/list.json', 'get', '搜索获取菜品列表']
        ],
        //菜品分类文字版
        ClassifyText: [
            ['addDish', site.staticWebsite + 'mock/mobile/add-dish.json', 'get', '添加菜品'],
            ['getDishList', site.website + 'mobile/dish/text/ajax/list/{curPage}', 'get', '获取菜品']
        ],
        //订单
        Order: [
            ['getOrderList', site.staticWebsite + 'mock/mobile/order-list.json', 'get', '获取订单列表'],
            ['deleteOrderingDish', site.staticWebsite + 'mock/mobile/delete-order-dish.json', '删除订单中菜品id'],
            ['sendConfirmOrderInfo', site.staticWebsite + 'mock/mobile/two-return-value.json', 'get', '']
        ],
        //测试
        Test: [
            ['getOrderList', site.staticWebsite + 'mock/mobile/test1.json', 'get', '获取订单列表']
        ],
        //为您推荐
        Recommend: [
            ['sendDishInfo', site.staticWebsite + 'mock/mobile/send-dish-info.json', 'get', '发送用户id和菜品id'],
            ['getRecommendDishList', site.staticWebsite + 'mock/mobile/get-recommend-dish-list.json', 'get', '获取推荐的菜品列表']
        ],
        //今日特价
        Recommend: [
        ['sendDishInfo', site.website + 'mock/mobile/send-dish-info.json', 'get', '发送用户id和菜品id'],
        ['getRecommendDishList', site.website + 'mobile/dish/prefer/ajax/cheap/list', 'get', '获取推荐的菜品列表']
        ],
        //本店特色
        Recommend: [
        ['sendDishInfo', site.staticWebsite + 'mock/mobile/send-dish-info.json', 'get', '发送用户id和菜品id'],
        ['getRecommendDishList', site.website + 'mobile/dish/prefer/ajax/feature/list', 'get', '获取推荐的菜品列表']
        ],
        //销量排行
        Recommend: [
        ['sendDishInfo', site.staticWebsite + 'mock/mobile/send-dish-info.json', 'get', '发送用户id和菜品id'],
        ['getRecommendDishList',site.website + 'mobile/dish/prefer/ajax/rank/list', 'get', '获取推荐的菜品列表']
        ],
        HistoryRecord: [
            ['getDishList', site.staticWebsite + 'mock/mobile/history-dish-list.json', 'get','获取历史记录中的菜品列表'],
            ['sendDishId', site.staticWebsite + 'mock/mobile/send-dish-info.json', 'get', '发送菜品id']
        ]
    };
})();