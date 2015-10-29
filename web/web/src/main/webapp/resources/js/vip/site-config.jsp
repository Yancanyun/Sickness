<%@ page contentType="text/html;charset=UTF-8" %>

var
    site ={
        website: "${website}", //站点地址
        staticWebsite: "${staticWebsite}" // 前端服务器地址
    };

seajs.config({
    // 项目前端域名
    base: site.staticWebsite,
    // 路径配置
    paths: {
        // 公用组件库
        "module": site.staticWebsite + 'js/module',
        // 会员中心组件库
        "vipModule": site.staticWebsite + 'js/admin/vip/module',
        // 页面逻辑层
        "page": site.staticWebsite + "js/admin/vip/page",
        // 数据交互层
        'io': site.staticWebsite +"js/admin/vip/io"
    },
    // 别名配置
    alias: {
        "jquery": site.staticWebsite + "tool/jquery/jquery/1.10.1/jquery.js",
        "bootstrap": site.staticWebsite + "tool/bootstrap/js/bootstrap.js",
        "bootstrapValidator": site.staticWebsite + "tool/bootstrap/js/bootstrapValidator.min.js",
        "jqueryValidate": site.staticWebsite + "tool/jquery/jquery.validate.js",
        "additionalMethods": site.staticWebsite + "tool/jquery/additional-methods.js",
        "validMessage": site.staticWebsite + "tool/jquery/valid-message.js"
    },
    // 文件编码
    charset: 'utf-8'
});


// 配置请求地址
_pw_env = {
    status: 0, //0-前端调试，1-后端调试, 2-后端部署
    website: site.website,
    staticWebsite: site.staticWebsite,
    tag: '',
    //统一错误信息入口
    msg:{
        0: '网络加载错误'
    },
    url: {
        service: {
        // 上传汇款单
        sendRemitId: site.website + 'vip/official/service/order/ajax/logo/update',
        // 取消订单
        sendOrderId: site.website + 'vip/official/service/order/ajax/cancel'
        }
    }
};
extendPW(PW.Env, _pw_env);
// console.log(PW.Env);