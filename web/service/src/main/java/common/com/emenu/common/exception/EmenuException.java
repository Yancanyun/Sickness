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
    UploadFileNotNull("上传的文件不能为空!", 1002),
    UploadPathNotNull("上传的文件路径不能为空!", 1003),
    UploadDirCreateFail("上传文件夹创建失败!", 1004),
    UploadFileFail("文件上传失败", 1005),

    //菜品管理模块
    //分类管理
    NewTagFailed("添加分类Tag失败", 3001),
    TagIdError("分类Tag的ID为空或者小于0", 3002),
    UpdateTagFailed("修改分类Tag失败", 3003),
    TagWeightError("分类Tag的权重为空或者小于0", 3004),
    TagPIdError("分类Tag的父亲Id为空或者小于0或者不存在", 3005),
    ListTagFailed("分类Tag列表查询失败", 3006),
    DeleteTagFailed("删除分类Tag失败", 3007),
    TagNameIsNull("分类名称为空", 3008),
    TagTypeError("分类Tag的type为空或者小于0或者不存在", 3009),
    TagChildrenIsNull("分类的子节点为空", 3010),

    //单位管理
    ListUnitFailed("单位列表查询失败", 3020),
    NewUnitFailed("添加单位失败", 3021),
    QueryUnitFailed("查询单位失败", 3022),
    UnitIdError("单位的ID为空或者小于0", 3023),
    DeleteUnitFailed("删除单位失败", 3024),
    UpdateUnitFailed("查询单位失败", 3025),
    UnitNameError("单位名称为空", 3026),
    UnitTypeError("单位类型为空或者小于0", 3027),
    UnitNameIsExist("单位名称已存在", 3028),

    //餐台区域
    QueryAreaFail("查询区域失败", 4001),
    AreaNameExist("区域名称已存在", 4002),
    InsertAreaFail("添加区域失败", 4003),
    UpdateAreaFail("更新区域失败", 4004),
    DeleteAreaFail("删除区域失败", 4005),
    AreaHasTableExist("区域中有餐台存在，不允许删除", 4006),
    AreaNameIsNull("区域名称不能为空", 4007),
    //餐台
    QueryTableFail("查询餐台失败", 4020),
    TableNameExist("餐台名称已存在", 4021),
    InsertTableFail("添加餐台失败", 4022),
    UpdateTableFail("更新餐台失败", 4023),
    DeleteTableFail("删除餐台失败", 4024),
    TableHasUsed("餐台正在被使用", 4025),
    AreaNotExist("区域不存在", 4026),
    TableNameIsNull("餐台名称不能为空", 4027),
    QueryWaiterTableFail("获取服务员-餐台信息失败！", 4028),
    //餐台二维码
    DownloadQrCodeFail("下载二维码失败", 4040),


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
    PartyIdIdError("当事人ID为空或者小于0", 3002),

    //会员基本信息管理模块
    VipInfoPhoneExist("电话号码已经存在", 8001),
    VipInfoKeywordNotNull("请输入关键字",8002),
    PartyIdNotNull("当事人Id不能为空", 8003),
    VipNameNotNUll("用户姓名不能为空", 8004),
    VipPhoneNotNull("电话号码不能为空", 8005),
    VipIdNotNull("用户id不能为空",8006),
    ListVipInfoFail("获取用户信息列表失败！",8007),
    InsertVipInfoFail("添加会员失败！",8008),
    UpdateVipInfoFail("修改会员信息失败",8009),
    VipInfoIdError("会员ID为空或者小于0",8010)

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
