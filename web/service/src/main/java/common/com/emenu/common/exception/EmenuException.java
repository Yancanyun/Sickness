package com.emenu.common.exception;

import com.pandawork.core.common.exception.IBizExceptionMes;

/**
 * 异常处理类
 * @Author: zhangteng
 * @Time: 2015/9/30 17:20.
 */
public enum EmenuException implements IBizExceptionMes {
    SystemException("系统内部异常", 1),

    ImageCompressedFail("图片压缩失败!", 1001),

    //菜品管理模块
    //分类管理
    NewTagFailed("添加分类Tag失败", 3001),
    TagIdError("分类Tag的ID为空或者小于0", 3002),
    UpdateTagFailed("修改分类Tag失败", 3003),
    TagWeightError("分类Tag的权重为空或者小于0", 3004),
    TagPIdError("分类Tag的父亲Id为空或者小于0或者不存在", 3005),
    ListTagFailed("分类Tag列表查询失败", 3006),
    DeleteTagFailed("删除分类Tag失败", 3007),

//    QueryTagPIdFailed("查询类别失败，该类别不存在父类别",3006),
    //单位管理
    ListUnitFailed("单位列表查询失败", 3010),
    NewUnitFailed("添加单位失败", 3011),
    QueryUnitFailed("查询单位失败", 3012),
    UnitIdError("单位的ID为空或者小于0", 3013),
    DeleteUnitFailed("删除单位失败", 3014),
    UpdateUnitFailed("查询单位失败", 3015),
    UnitNameError("单位名称为空", 3016),

    //餐台区域
    QueryAreaFail("查询区域失败", 4001),
    AreaNameExist("区域名称已存在", 4002),
    InsertAreaFail("添加区域失败", 4003),
    UpdateAreaFail("更新区域失败", 4004),
    DeleteAreaFail("删除区域失败", 4005),
    AreaHasTableExist("区域中有餐台存在，不允许删除", 4006),
    //餐台
    QueryTableFail("查询餐台失败", 4007),
    TableNameExist("餐台名称已存在", 4008),
    InsertTableFail("添加餐台失败", 4009),
    UpdateTableFail("更新餐台失败", 4010),
    DeleteTableFail("删除餐台失败", 4011),
    TableHasUsed("餐台正在被使用", 4012),
    AreaNotExist("区域不存在", 4013),
    //餐台二维码
    DownloadQrCodeFail("下载二维码失败", 4014),


    //搜索风向标
    KeywordsExist("关键字已存在！", 5001),
    DeleteKeywordsFail("删除关键字失败！", 5002),
    QueryKeywordsFail("获取关键字列表失败！", 5003),
    InsertKeywordsFail("添加关键字失败！", 5004),
    KeyNotNull("关键字不能为空！",5009),
    KeyTypeNotNull("关键字类型不能为空！",5010),
    KeyTypeWrong("关键字类型出错！",5011),

    //点餐平台首页
    InsertIndexImgFail("添加图片失败！", 5005),
    UpdateIndexImgFail("更改首页展示图片失败！", 5006),
    DeleteIndexImgFail("删除图片失败！", 5007),
    QueryIndexImgFail("获取首页图片失败！", 5008),
    ImgPathNotNull("图片路径不能为空！",5012),
    ImgStateNotNull("图片使用状态不能为空！", 5013),
    ImgStateWrong("图片使用状态异常！", 5014),


    //用户管理模块
    QueryEmployeeInfoFail("获取员工信息失败",6001),
    InsertWaiterTableFail("添加服务员-餐台信息失败", 6002),
    PartyNotNull("添加当事人不能为空！",6003),
    UpdateUserPwdFail("修改员工密码失败",6004),
    UpdateEmployeeStateFail("修改员工状态失败",6005),
    QueryEmployeeException("查询员工信息失败",6007),
    EmployeeIsActivity("员工处于激活状态，请先将它转为未激活！",6008),
    DeleteEmployeeFail("删除员工信息失败",6009),

    //会员基本信息管理模块
    VipInfoPhoneExist("电话号码已经存在", 8001),

    ;



    private String msg;

    private int code;

    EmenuException(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    @Override
    public String getMes() {
        return msg;
    }

    @Override
    public int getCode() {
        return code;
    }
}
