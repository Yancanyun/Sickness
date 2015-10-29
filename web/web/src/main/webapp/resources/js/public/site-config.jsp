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
        // 公共页面组件库
        "pubModule": site.staticWebsite + 'js/pub/module',
        // 页面逻辑层
        "page": site.staticWebsite + "js/pub/page",
        // 数据交互层
        'io': site.staticWebsite +"js/pub/io"
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
        // 注册
        info:{
            sendInfo: site.website + "ajax/login"
        },
        // 登录
        info:{
            sendInfo: site.website + "ajax/login"
        },
        // 磁铁维护
        maintenance: {
            sendServiceList: site.staticWebsite + "/mock/pub/login.json"
        },
        // 注册
        register:{
            // 查询登录名是否已经存在
            sendAccount: site.website + "ajax/query/exist",
            // 发送邮件
            sendEmail: site.website + "ajax/send/email",
            // 验证邮箱验证码是否正确
            sendForm: site.website + "ajax/register",
            // 检查是否为已经注册过的邮箱
            isValidEmail: site.website + "ajax/query/email/exist"
        },
        // 找回密码
        findPwd: {
            //第一步：发送邮箱和验证码
            sendEmail: site.website + "ajax/find/pwd/email",

            // 第二步：验证码和邮箱
            sendValid: site.website + "ajax/find/pwd/email/valid",

            // 第三步：发送密码和确认密码
            sendPwd: site.website + "ajax/pwd/check"
        },
        // 订单支付页面
        service:{
            //  支付页面，提交订单，请求服务
            sendOrder: site.website + "official/service/ajax/order/submit"
        }
    }
};
extendPW(PW.Env, _pw_env);
// console.log(PW.Env);