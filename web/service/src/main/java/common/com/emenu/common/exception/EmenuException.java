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
    SerialNumTemplateNotNull("流水号模板不能为空", 1019),

    // 供货商模块
    SupplierInsertFailed("添加供货商失败", 1006),
    SupplierUpdateFailed("更新供货商失败", 1007),
    SupplierQueryFailed("查询供货商失败", 1008),
    SupplierDeleteFailed("删除供货商失败", 1009),
    SupplierNameNotNull("供货商名称不能为空", 1010),
    SupplierNameExist("供货商名称已存在", 1011),
    SupplierIdNotNull("供货商ID不能为空", 1012),

    // 库存分类
    StorageTagQueryFailed("查询库存分类失败", 1013),
    StorageTagInsertFailed("添加库存分类失败", 1014),
    StorageTagUpdateFailed("更新库存分类失败", 1015),
    StorageTagDeleteFailed("删除库存分类失败", 1016),
    StorageTagNameNotNull("库存分类名称不能为空", 1017),
    StorageTagHasItem("有库存物品属于该分类，请先移除分类下的物品", 1018),

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
    TagFiledIsNull("分类的更新域为空", 3011),

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

    //饭店管理
    //餐台区域
    QueryAreaFail("查询区域失败", 4001),
    AreaNameExist("区域名称已存在", 4002),
    InsertAreaFail("添加区域失败", 4003),
    UpdateAreaFail("编辑区域失败", 4004),
    DeleteAreaFail("删除区域失败", 4005),
    AreaHasTableExist("区域中有餐台存在，不允许删除", 4006),
    AreaNameIsNull("区域名称不能为空", 4007),
    //餐台
    QueryTableFail("查询餐台失败", 4020),
    TableNameExist("餐台名称已存在", 4021),
    InsertTableFail("添加餐台失败", 4022),
    UpdateTableFail("编辑餐台失败", 4023),
    DeleteTableFail("删除餐台失败", 4024),
    TableHasUsed("餐台正在被使用", 4025),
    AreaNotExist("区域不存在", 4026),
    TableNameIsNull("餐台名称不能为空", 4027),
    QueryWaiterTableFail("获取服务员-餐台信息失败", 4028),
    //餐台二维码
    DownloadQrCodeFail("下载二维码失败", 4040),
    //餐台-餐段管理
    QueryTableMealPeriodFail("查询餐台-区域失败", 4060),
    InsertTableMealPeriodFail("添加餐台失败", 4061),
    UpdateTableMealPeriodFail("编辑餐台失败", 4062),
    DeleteTableMealPeriodFail("删除餐台失败", 4063),
    //备注分类管理
    QueryRemarkTagFail("查询备注分类失败", 4080),
    InsertRemarkTagFail("添加备注分类失败", 4081),
    UpdateRemarkTagFail("编辑备注分类失败", 4082),
    DeleteRemarkTagFail("删除备注分类失败", 4083),
    RemarkTagNameExist("备注分类名称已存在", 4084),
    RemarkTagNameIsNull("备注分类名称不能为空", 4085),
    RemarkTagNotExist("备注分类不存在", 4086),
    RemarkTagHasRemarkExist("备注分类中有备注存在，不允许删除", 4087),
    //备注管理
    QueryRemarkFail("查询备注失败", 4100),
    InsertRemarkFail("添加备注失败", 4101),
    UpdateRemarkFail("编辑备注失败", 4102),
    DeleteRemarkFail("删除备注失败", 4103),
    RemarkNameExist("备注名称已存在", 4104),
    RemarkNameIsNull("备注名称不能为空", 4105),

    //基本信息管理
    //搜索风向标
    KeywordsExist("关键字已存在", 5000),
    DeleteKeywordsFail("删除关键字失败", 5001),
    QueryKeywordsFail("获取关键字列表失败", 5002),
    InsertKeywordsFail("添加关键字失败", 5003),
    KeyNotNull("关键字不能为空", 5004),
    KeyTypeNotNull("关键字类型不能为空", 5005),
    KeyTypeWrong("关键字类型非法", 5006),

    //点餐平台首页
    InsertIndexImgFail("添加图片失败", 5010),
    UpdateIndexImgFail("更改首页展示图片失败", 5011),
    DeleteIndexImgFail("删除图片失败", 5012),
    QueryIndexImgFail("获取首页图片失败", 5013),
    ImgPathNotNull("图片路径不能为空",5014),
    ImgStateNotNull("图片使用状态不能为空", 5015),
    ImgStateWrong("图片使用状态异常", 5016),

    //餐段管理
    MealPeriodNameNotNull("餐段名称不能为空",5020),
    MealPeriodStateIllegal("餐段启用状态非法",5021),
    InsertMealPeriodFail("添加餐段失败",5022),
    UpdateMealPeriodFail("修改餐段失败",5023),
    DeleteMealPeriodFail("删除餐段失败",5024),
    QueryMealPeriodFail("查询餐段失败",5025),
    MealPeriodNameExist("餐段名称已存在",5026),
    MealPeriodInfoIllegal("餐段信息非法",5027),
    MealPeriodIsUsing("该餐段正在使用",5028),
    MealPeriodWeightIllegal("餐段排序权重非法",5021),


    //用户管理模块
    QueryEmployeeInfoFail("获取员工信息失败",6001),
    InsertWaiterTableFail("添加服务员-餐台信息失败", 6002),
    PartyNotNull("添加当事人不能为空！",6003),
    UpdateUserPwdFail("修改员工密码失败",6004),
    UpdateEmployeeStateFail("修改员工状态失败",6005),
    QueryEmployeeException("查询员工信息失败",6007),
    EmployeeIsActivity("员工处于激活状态，请先将它转为未激活！",6008),
    DeleteEmployeeFail("删除员工信息失败",6009),
    PartyIdError("当事人ID为空或者小于0", 6010),
    EmployeeNameNotNull("员工姓名不能为空",6011),
    EmployeeNumberNotNull("员工编号不能为空",6012),

    //库存管理
    InsertReportFail("添加单据失败",7001),
    NewSerialNumberFali("生成单据编号失败",7002),
    ReportIsNotNull("单据不能为空",7003),
    ReportItemIsNotNull("单据详情不能为空",7004),
    HandlerPartyId("操作人id不能为空或者小于0",7005),
    DepotIdError("存储点ID为空或者小于0",7006),
    ReportStatusError("状态不能为空或小于0",7007),
    ReportMoneyError("单据金额不能为空或小于0",7008),
    ReportTypeError("类型不能为空或小于0",7009),
    CreatedPartyIdError("订单创建者ID不能为空或小于0",7010),
    SerialNumberError("单据编号不能为空或小于0、生产单据编号失败",7011),
    DishIdError("菜品ID不能为空或小于0",7012),
    PriceError("成本不能为空或小于0",7013),
    QuantityError("数量不能为空或小于0",7014),
    CountError("计数不能为空或小于0",7015),
    InsertReportItemFail("添加单据详情失败",7016),
    ReportIdError("计数不能为空或小于0",7017),
    ListStorageReportFail("获取单据信息失败",7018),
    ListStorageReportItemFail("获取单据详情信息l列表失败",7019),
    QueryStorageReportItemFail("获取单据详情信息失败",7020),


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
    VipInfoIdError("会员ID为空或者小于0",8010),
    SearchSecurityUserIdFail("获取用户登录id失败",8011),
    DeleteVipInfoFail("删除会员失败",8012),

    ListVipDishPricePlanFail("获取会员价方案列表失败",8013),
    QueryVipDishPricePlanFail("获取会员价方案失败",8014),
    VipDishPricePlanNameNotNull("会员价方案名称不能为空",8015),
    InsertVipDishPricePlanFail("插入会员价方案失败",8016),
    VipDishPricePlanIdError("会员价方案ID为空或者小于0",8017),
    UpdateVipDishPricePlanFail("更新会员价方案失败",8018),
    DeleteVipDishPricePlanFail("删除会员价方案失败",8019)

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
