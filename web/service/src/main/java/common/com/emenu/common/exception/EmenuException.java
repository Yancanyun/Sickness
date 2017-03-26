package com.emenu.common.exception;

import com.pandawork.core.common.exception.IBizExceptionMes;

/**
 * 异常处理类
 * @Author: zhangteng
 * @Time: 2015/9/30 17:20.
 */
public enum EmenuException implements IBizExceptionMes {
    SystemException("系统内部异常", 1),
    InternalNetworkAddressError("内网地址配置错误", 2),
    CustomerIsNotInLAN("用户未处于局域网内，禁止访问", 3),

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

    // 库存物品
    StorageItemQueryFailed("查询库存物品失败", 1020),
    StorageItemInsertFailed("添加库存物品失败", 1021),
    StorageItemUpdateFailed("更新库存物品失败", 1022),
    StorageItemDeleteFailed("删除库存物品失败", 1023),
    StorageItemNameNotNull("物品名称不能为空", 1024),
    StorageItemSupplierNotNull("物品的供应商不能为空", 1025),
    StorageItemSupplierPartyIdError("供货商id错误",1026),
    StorageItemUnitNotNull("物品的订货单位、库存单位、成本卡单位不能为空", 1027),
    StorageItemUnitRatioNotNull("物品的单位换算关系不能为空", 1028),
    StorageItemMaxMinQuantity("物品的最大、最小库存量不能为空", 1029),
    StorageItemIdNotNull("物品的ID不能为空", 1030),
    StorageItemIngredientIdNotNull("库存物品原配料ID不能为空",1031),
    StorageItemNotExist("库存物品不存在",1032),
    StorageItemIsNotNull("库存物品不能为空",1033),
    StockOutTypeError("出库方式错误",1034),
    StorageItemTagNotNull("物品所属分类不能为空", 1035),
    StorageItemTagIdError("库存物品分类id错误",1036),
    StorageItemIsExist("库存物品已经存在",1037),

    // 库存结算
    InsertStorageSettlementItemFailed("库存结算保存失败", 3030),
    ListStorageSettlementSupplierFailed("库存结算中心结算失败", 3031),
    ListStorageSettlementCheckFailed("库存盘点失败", 3032),
    CountStorageSettlementCheckFailed("库存盘点统计总数失败", 3033),
    ExportStorageSettlementCheckFailed("库存盘点导出EXCEL失败", 3034),
    ExportStorageSettlementSupplierFailed("库存结算中心导出EXCEL失败", 3035),
    InitSettlementCacheFail("初始化盘点缓存失败",3036),
    UpdateSettlementCacheFail("更新盘点缓存失败",3037),
    SettlementCacheKeyError("缓存key不合法",3038),
    QueryCacheFail("查询原配料缓存失败",3039),
    SettlementCacheQuantityIsNotNull("缓存不能为空",3040),

    // 原配料
    IngredientTagIdIsNotNull("原配料分类id不能为空",3100),
    IngredientNumberIsNotNull("原配料编号不能为空",3101),
    IngredientAssistantCodeIsNotNull("助记码不能为空",3102),
    IngredientOrderUnitIdIsNotNull("订货单位Id不能为空",3103),
    IngredientStorageUnitIdIsNotNull("库存单位id不能为空",3104),
    IngredientCostCardUnitIdIsNotNull("成本卡单位Id不能为空",3105),
    IngredientOrderToStorageRatioIsNotNull("订货单位转换库存单位比例不能为空",3106),
    IngredientStorageToCostCardRatioIsNotNull("库存单位转换成本卡单位比例不能为空",3107),
    IngredientMaxStorageQuantityIsNotNull("最大库存量不能为空",3108),
    IngredientNameIsNotNull("原配料名称不能为空",3109),
    IngredientMinStorageQuantityIsNotNull("最大库存量不能为空",3110),
    IngredientUpdateFailed("更新原配料失败",31101),
    IngredientInserFailed("新增原配料失败",31102),
    IngredientNotExist("原配料不存在",31103),
    IngredientQueryFailed("原配料查询失败",31104),
    IngredientIsExist("原配料已存在",31105),
    IngredientUpdateStatusError("编辑时状态错误",31106),
    IngredientIsNotNull("原配料不能为空",31107),
    AssistantCode("助记码不能为空",31008),

    // 菜品管理模块
    // 分类管理
    NewTagFailed("添加分类失败！", 3001),
    TagIdError("分类的ID为空或者小于等于0！", 3002),
    UpdateTagFailed("修改分类失败！", 3003),
    TagWeightError("分类的权重为空或者小于0！", 3004),
    TagPIdError("分类的父亲Id为空或者小于0或者不存在！", 3005),
    ListTagFailed("分类列表查询失败！", 3006),
    DeleteTagFailed("删除分类失败！", 3007),
    TagNameIsNull("分类名称为空！", 3008),
    TagTypeError("分类的type为空或者小于0或者不存在！", 3009),
    TagChildrenIsNull("该分类下还有子分类，不能删除！", 3010),
    TagFiledIsNull("分类的更新域为空！", 3011),
    TagChildrenIsError("分类的存在子节点，不能删除！", 3012),
    QueryTagParentFailed("根据Id获取上一级分类失败！",3013),
    InitTagCacheFailed("初始化分类缓存失败！", 3014),
    RefreshTagCacheFailed("刷新分类缓存失败！", 3015),
    ListChildrenTagFailed("获取分类儿子节点失败！", 3016),
    ListGrandsonTagFailed("获取分类孙子节点失败！", 3017),
    QueryTagRootFailed("查询分类根节点失败！", 3018),
    ListTagRootFailed("查询分类根节点列表失败！", 3019),
    QueryTagFailed("查询分类失败！", 3020),
    listPathTagFailed("查询分类祖先路径失败！", 3021),
    TagChildrenIsExist("该分类下的子类已存在",3022),

    //菜品分类与备注关联
    DelByDishTagIdFailed("根据菜品分类id删除菜品分类失败",3022),
    NewDishRemarkTagsFailed("新增菜品分类与备注分类失败",3023),
    DishRemarkTags("菜品分类与备注不为空",3024),
    TagNotExist("菜品没有存在",2025),

    //菜品口味
    ListTasteFailed("口味列表查询失败", 3040),
    NewTasteFailed("添加口味失败", 3041),
    QueryTasteFailed("查询口味失败", 3042),
    TasteIdError("口味的ID为空或者小于0", 3043),
    DeleteTasteFailed("删除口味失败", 3044),
    UpdateTasteFailed("查询口味失败", 3045),
    TasteNameError("口味名称为空", 3046),
    TasteNameIsExist("口味名称已存在", 3048),

    // 单位管理
    ListUnitFailed("单位列表查询失败", 3050),
    NewUnitFailed("添加单位失败", 3051),
    QueryUnitFailed("查询单位失败", 3052),
    UnitIdError("单位的ID为空或者小于0", 3053),
    DeleteUnitFailed("删除单位失败", 3054),
    UpdateUnitFailed("更新单位失败", 3055),
    UnitNameError("单位名称为空", 3056),
    UnitTypeError("单位类型为空或者小于0", 3057),
    UnitNameIsExist("单位名称已存在", 3058),

    //套餐
    ListDishPackageFailed("套餐列表查询失败", 3060),
    NewDishPackageFailed("添加套餐失败", 3061),
    QueryDishPackageFailed("查询套餐失败", 3062),
    DishPackageIdError("套餐的ID为空或者小于0", 3063),
    DeleteDishPackageFailed("删除套餐失败", 3064),
    UpdateDishPackageFailed("更新套餐失败", 3065),
    ListChildDishFailed("套餐菜品列表为空", 3066),
    DishPackageIsNull("套餐关联表为空", 3067),
    DishQuantityIsNull("套餐菜品列表中数量为空或者小于0", 3068),
    DishIdIsNotPackage("该ID属于菜品，请前往菜品管理处进行编辑", 3069),
    DishIdIsPackage("该ID属于套餐，请前往套餐管理处进行编辑", 3070),

    // 菜品图片
    DishImgQueryFailed("查询菜品图片失败", 1031),
    DishImgInsertFailed("添加菜品图片失败", 1032),
    DishImgDeleteFailed("删除菜品图片失败", 1033),
    DishImgTypeIllegal("菜品图片类型不合法", 1034),
    DishImgNotNull("菜品图片不能为空", 1035),

    // 菜品
    DishIdNotNull("菜品ID不能为空", 1036),
    DishQueryFailed("菜品查询失败", 1037),
    DishInsertFailed("菜品添加失败", 1038),
    DishUpdateFailed("菜品更新失败", 1039),
    DishDeleteFailed("菜品删除失败", 1040),
    DishStatusIllegal("菜品状态不合法", 1041),
    DishCategoryNotNull("总分类不能为空", 1042),
    DishNameNotNull("菜品名称不能为空", 1043),
    DishUnitNotNull("菜品单位不能为空", 1044),
    DishPriceNotNUll("菜品定价不能为空", 1045),
    DishSaleTypeNotNull("菜品促销方式不能为空", 1046),
    DishDiscountNotNull("菜品折扣不能为空", 1047),
    DishSalePriceNotNull("菜品售价不能为空", 1048),
    DishTagNotNull("小类不能为空", 1049),
    DishCreatedPartyIdNotNull("创建者不能为空", 1054),
    DishLikeFailed("点赞失败", 1055),
    DishDislikeFailed("取消点赞失败", 1055),

    IsFirstTagNotNull("是否是首要分类不能为空", 1055),
    DishTagQueryFiled("菜品-分类查询失败", 1056),
    DishTagInsertFailed("菜品-分类添加失败", 1057),
    DishTagUpdateFailed("菜品-分类更新失败", 1058),
    DishTagDeleteFailed("菜品-分类删除失败", 1059),
    ListByKeywordFailed("根据关键字查询小部分菜品信息失败",1060),
    ListByTagIdAndPageFailed("菜品分类分页查询失败",1060),
    CountByTagIdFailed("获取菜品分类个数失败",1061),

    // 菜品口味
    DishTasteQueryFailed("菜品口味查询失败", 1049),
    DishTasteInsertFailed("菜品口味添加失败", 1050),
    DishTasteUpdateFailed("菜品口味更新失败", 1051),
    DishTasteDeleteFailed("菜品口味删除失败", 1052),
    DishTasteIdError("菜品口味的ID为空或小于等于0", 1053),

    // 菜品-餐段
    DishMealPeriodQueryFailed("菜品-餐段查询失败", 1060),
    DishMealPeriodInsertFailed("菜品-餐段添加失败", 1061),
    DishMealPeriodUpdateFailed("菜品-餐段更新失败", 1062),
    DishMealPeriodDeleteFailed("菜品-餐段删除失败", 1063),

    // 成本卡
    CostCardQueryFailed("成本卡查询失败", 1064),
    CostCardInsertFailed("成本卡添加失败", 1065),
    CostCardUpdateFailed("成本卡更新失败", 1066),
    CostCardDeleteFailed("成本卡删除失败", 1067),
    QueryCostCardFailed("查询成本卡失败",1068),
    QueryCostCardByIdFailed("根据id查询成本卡",1069),
    ListAllCostCardDtoFail("查询所有成本卡的信息失败",1070),

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
    TableIsNotEnabled("餐台不属于\"可用\"状态", 4029),
    TableIsNotUncheckouted("餐台不属于\"占用未结账\"状态", 4030),
    OpenTableFail("开台失败", 4031),
    ChangeTableFail("换台失败", 4032),
    CleanTableFail("清台失败", 4033),
    OperateStatusIsNotLegal("操作餐台不合法",4034),
    MergeTableFail("并台失败", 4035),
    DelMergeTableFail("删除原有并台信息失败", 4036),
    MergeTableNumLessThanTwo("选择的并台餐台数量小于两个", 4037),
    MergeTableStatusError("选择的并台餐台中有处于不可并台状态的餐台", 4038),
    MergeIdError("并台ID不合法", 4039),
    QueryMergeTableFail("查询并台信息失败", 4040),
    TableNotOpen("餐台暂未开台",4041),
    WaiterCanNotServiceThisTable("服务员没有权限操作该餐台",4042),
    //餐台二维码
    DownloadQrCodeFail("下载二维码失败", 4050),
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
    //呼叫服务管理
    InsertCallWaiterFail("添加服务类型失败", 4120),
    UpdateCallWaiterFail("更新服务类型失败", 4121),
    DeleteCallWaiterFail("删除服务类型失败", 4122),
    QueryAllCallWaiterFail("查询服务类型失败", 4123),
    UpdateCallWaiterStatusFail("修改服务类型启用状态失败", 4124),
    AddCallCacheFail("呼叫服务加入缓存失败", 4125),
    QueryCallCacheByWaiterIdFail("查询服务员的服务请求失败", 4126),
    DelTableCallCacheFail("删除呼叫服务缓存失败", 4127),
    CallWaiterNameSameFail("不能添加已存在的服务类型", 4128),
    CallCacheSendTimeLimit("两次相同服务请求间隔不得小于1分钟", 4129),
    CallCacheNotHandle("此请求还未被处理,请稍等哦！", 4130),
    TableNotAvailable("该餐桌已停用或已被删除!", 4131),
    TableNotHaveCallCache("该餐桌不存在服务请求", 4132),
    CountNotResponseTotalMessageFail("查询服务员未应答的服务请求总数失败",4133),
    QueryCallByIdFail("根据服务的Id查询服务请求失败",4134),
    ResponseCallFail("应答服务请求失败",4135),
    DelCallCachesByPartyIdFail("删除服务员已经处理的服务请求失败",4136),

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
    MealPeriodNameNotNull("餐段名称不能为空", 5020),
    MealPeriodStatusIllegal("餐段启用状态非法", 5021),
    InsertMealPeriodFail("添加餐段失败", 5022),
    UpdateMealPeriodFail("修改餐段失败", 5023),
    DeleteMealPeriodFail("删除餐段失败", 5024),
    QueryMealPeriodFail("查询餐段失败", 5025),
    MealPeriodNameExist("餐段名称已存在", 5026),
    MealPeriodInfoIllegal("餐段信息非法", 5027),
    MealPeriodIsUsing("该餐段正在使用", 5028),
    MealPeriodWeightIllegal("餐段排序权重非法", 5029),
    MealPeriodDisabled("餐段已被停用", 5030),
    MealPeriodDeleted("餐段已被删除", 5031),

    //打印机管理
    QueryPrinterFail("查询打印机失败",5040),
    InsertPrinterFail("添加打印机失败",5052),
    UpdatePrinterFail("修改打印机失败",5041),
    DeletePrinterFail("删除打印机失败",5042),
    PrinterInfoIllegal("打印机信息非法",5043),
    PrinterNameNotNull("打印机名称不能为空",5044),
    PrinterBrandIllegal("打印机品牌数据错误",5045),
    PrinterModelIllegal("打印机型号数据错误",5046),
    PrinterIpAddressNotNull("打印机ip地址不能为空",5047),
    PrinterTypeIllegal("打印机类型数据错误",5048),
    PrinterStateIllegal("打印机状态数据错误",5049),
    PrinterNameExist("打印机名称已存在",5050),
    PrinterIpAddressExist("打印机ip地址已存在",5051),
    PrinterIdError("打印机id为空或者小于0",5052),
    PrinterDishTypeError("打印机与菜品关联表类型数据非法",5053),
    NewPrinterDishError("新增打印机与菜品关联失败",5054),
    UpdatePrinterDishError("修改打印机与菜品关联失败",5055),
    DelPrinterDishError("删除打印机与菜品关联失败",5056),
    PrinterIsUsing("打印机正在被使用，请取消所有关联菜品分类后重试",5057),
    PrinterDishExist("关联信息已存在",5058),
    BarPrinterIsNotExistOrIpNotSet("未设置吧台打印机或打印机Ip地址未设置",5059),
    PrinterConnectFaiil("打印机连接失败",5060),
    PrinterNotOpen("存在某一订单菜品关联的打印机未开启或未连接网线,请检查打印机!",5061),

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
    CheckEmployeeNumberFail("检查员工编号重名失败",6013),
    CheckEmployeePhoneFail("检查员工电话重名失败",6014),
    CheckLoginNameFail("检查登录名重名失败",6015),
    OldPasswordWrong("原密码错误", 6016),
    PasswordNotEqual("两遍密码不一致", 6017),
    PasswordWrong("密码错误", 6018),

    // 库存管理
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
    ReportIdError("单据Id不能为空或小于0",7017),
    ListReportFail("获取单据信息失败",7018),
    ListStorageReportItemFail("获取单据详情信息l列表失败",7019),
    QueryReportItemFail("获取单据详情信息失败",7020),
    QueryReportFail("获取单据信息失败",7021),
    UpdateStorageReportFail("修改单据信息失败",7022),
    UpdateStorageReportItemFail("修改单据详情失败",7023),
    DelReportOrItemFail("删除单据或单据详情失败",7024),
    CountReportFail("统计单据失败",7025),
    ReportDtoIsNotNull("ReportDto不能为空",7026),
    ReportItemListIsNotNull("单据详情list不能为空",7027),
    QueryReportDtoFail("查询单据和单据详情失败",7028),
    StorageReportItemListIsNotNull("单据详情list不能为空",7029),
    ExportReportFail("导出库存单据失败",7030),
    IsAuditedIllegal("审核状态不合法",7031),
    ReportIsNotExist("单据不存在",7032),
    ReportStatusIsNull("单据状态不能为空",7033),
    ReportIngredientIsNot("单据原配料不能为空",7034),
    InsertReportIngredientFail("添加单据原配料失败",7035),
    ReportIdOrStatusIdError("单据id或者状态错误",7036),
    ReportSearchDtoError("查询dto错误",7037),
    StorageReportDeatilIsNotNull("单据详情不能为空",7098),
    KeywordError("关键字不合法",7099),
    TimeIsNotNUll("时间不能为空",70100),

    // 存放点管理
    ListDepotPageFailed("分页存放点列表查询失败", 7024),
    ListDepotFailed("获取全部存放点列表失败", 7025),
    NewDepotFailed("增加存放点失败",7026),
    DelDepotFailed("删除存放点失败", 7027),
    UpdateDepotFailed("修改存放点失败", 7028),
    CountDepotFailed("获取存放点总数失败", 7029),
    DepotNameError("存放点名称不为空", 7030),
    QueryDepotByNameFailed("根据名称查找存放点失败", 7031),
    QueryDepotByIdFailed("根据id查找存放点",7032),
    DepotIsNull("存放点为空", 7033),
    DepotNameIsExist("存放点名已经存在", 7034),
    DepotNameIsConflict("存放点名称冲突", 7035),
    CheckDepotNameFailed("检查存放点名称是否重复失败", 7036),
    CheckDepotNameConflictFailed("检查名称是否与其他存放点冲突失败", 7037),



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
    DeleteVipDishPricePlanFail("删除会员价方案失败",8019),
    DeleteVipDishPricePlanNotAllow("当前会员价方案被会员等级使用，不能删除",80191),

    ListVipDishPriceFail("获取会员价列表失败",8020),
    VipDishPriceKeywordNotNull("请输入关键字",8021),
    VipDishPriceNotNull("会员价不能为空",8022),
    InsertVipDishPriceFail("插入会员价失败",8023),
    UpdateVipDishPriceFail("更新会员价失败",8024),
    InputDiscountOrDifferenceNotNull("折扣或差价为空",8025),
    InputDiscountOrDifferenceError("输入折扣或差价不合法",8026),
    VipDishPriceIdError("会员价ID为空或者小于0",8027),
    CountVipByGradeIdFail("计算会员数量失败",8028),
    OpenIdError("微信OpenId不合法", 8029),
    bondWeChatError("绑定微信失败", 8030),
    SendSmsError("发送验证码失败", 8031),
    unbondWeChatError("解绑微信失败", 8032),
    WeChatIsBonded("您的微信已绑定会员，请勿重复绑定", 8033),
    WeChatIsNotBonded("您的微信尚未绑定会员，请先进行绑定再执行该操作", 8034),
    PhoneIsNotExist("该手机号码尚未注册成为会员", 8035),
    PhoneIsBonded("该手机号码已被绑定", 8036),
    PhoneError("手机号码不合法", 8037),
    ValidCodeWrong("验证码不正确", 8038),
    SendTooFrequently("两次获取验证码之间不可少于60秒，请稍后再试", 8039),
    //QueryVipDishPriceFail("获取会员价失败",8021),
    //VipDishPriceNameNotNull("会员价名称不能为空",8022),
    //
    //
    //
    //DeleteVipDishPriceFail("删除会员价失败",8026)

    QueryMultipleIntegralPlanFail("查询多倍积分方案失败",8040),
    UpdateMultipleIntegralPlanFail("修改多倍积分方案失败",8041),
    DeleteMultipleIntegralPlanFail("删除多倍积分方案失败",8042),
    InsertMultipleIntegralPlanFail("新增多倍积分方案失败",8043),
    MultipleIntegralPlanNameNotNull("积分方案名称不能为空",8044),
    MultipleIntegralPlanStatusNotNull("积分方案启用状态不能为空",8045),
    IntegralMultiplesNotNull("积分方案倍数不能为空",8046),
    MultipleIntegralPlanNameExist("积分方案名称已存在",8047),
    MultipleIntegralPlanIdIllegal("Id为空或小于等于0",8048),

    //会员充值方案
    QueryVipRechargePlanFail("查询会员充值方案失败", 8060),
    VipRechargePlanNameExist("会员充值方案名称已存在", 8061),
    InsertVipRechargePlanFail("添加会员充值方案失败", 8062),
    UpdateVipRechargePlanFail("编辑会员充值方案失败", 8063),
    DeleteVipRechargePlanFail("删除会员充值方案失败", 8064),
    VipRechargePlanNameIsNull("会员充值方案名称不能为空", 8065),
    RechargePlanIdError("充值方案id错误", 8066),

    //会员等级方案
    QueryVipGradeFail("查询会员等级方案失败", 8080),
    InsertVipGradeFail("新增会员等级方案失败", 8081),
    UpdateVipGradeFail("修改会员等级方案失败", 8082),
    DeleteVipGradeFail("删除会员等级方案失败", 8083),
    VipGradeNameNotNull("会员等级方案名称不能为空", 8084),
    VipDishPricePlanIdIllegal("会员价方案Id数据非法", 8085),
    MinConsumptionIllegal("最低消费金额数据非法", 8086),
    CreditAmountIllegal("信用额度数据非法", 8087),
    SettlementCycleIllegal("结算周期数据非法", 8088),
    PreReminderAmountIllegal("升级预提醒额度数据非法", 8089),
    VipGradeIdIllegal("会员等级方案id为空或小于等于0", 8090),
    VipGradeIdIsUsing("会员等级方案正在使用", 8091),
    MinConsumptionExist("已存在相同最低消费金额", 8092),
    IntegralStatusIllegal("积分启用状态不合法", 8093),

    //会员积分方案
    VipIntegralPlanTypeNotNull("会员积分方案类型不能为空", 8100),
    VipIntegralPlanValueNotNull("会员积分方案兑换值不能为空", 8101),
    InsertVipIntegralPlanFail("插入会员积分方案失败", 8102),
    UpdateVipIntegralPlanFail("更新会员积分方案失败", 8103),
    DeleteVipIntegralPlanFail("删除会员积分方案失败", 8104),
    VipIntegralPlanIdIllegal("会员积分方案id不合法", 8105),
    ListVipIntegralPlanFail("获取会员积分方案列表失败", 8106),

    //会员账户信息
    ListVipAccountFailed("获取会员账户分页列表失败", 8120),
    VipAccountInfoIsNotNull("会员账户信息不为空", 8121),
    VipAccountInfoPartyIdError("会员账户当事人id错误", 8122),
    NewVipAccountInfoFailed("增加会员账户失败", 8123),
    UpdateVipAccountInfoFailed("修改会员账户失败", 8124),
    DeleteVipAccountInfoFailed("删除会员账户失败", 8125),
    CountAllVipAccountInfoFailed("查询会员账户数失败",8126),
    UpdateVipAccountStateFailed("修改会员账户状态失败",8127),
    VipAccountInfoIdError("会员账户主键id错误",8128),
    VipAccountInfoStatesError("会员账户状态错误",8129),
    UpdateVipAccountInfoStatusFail("修改会员账户状态失败",8130),
    QueryVipAccountFailed("查询会员账户信息失败", 8131),
    QueryVipError("查询会员信息失败",8132),
    VipBalanceNotEnough("会员余额不足，无法结账", 8133),
    CountByKeywordAndGradeFailed("根据keyWord和等级集合查询数据量失败",8134),
    VipAccountInfoStatusError("修改会员账户状态失败，请先去会员管理处启用该账户",8135),


    //会员卡发放
    QueryVipCardFail("查询会员卡失败", 8140),
    InsertVipCardFail("发卡失败", 8141),
    UpdateVipCardFail("修改会员卡失败", 8142),
    DeleteVipCardFail("删除会员卡失败", 8143),
    PermanentlyEffectiveIsNull("有效期不可为空", 8144),
    PermanentlyEffectiveBeforeToday("有效期不可为今天或今天之前", 8145),

    //会员注册
    RejisterVipFail("注册会员失败",80146),
    PhysicalNumberError("物理卡号错误",80147),
    CardNumberError("会员卡号错误",80148),
    VipInfoNotExist("会员不存在",80149),
    VipCardNotExist("会员卡号不存在",80150),

    // 会员消费详情
    QueryConsumptionActivityFail("查询会员消费详情失败", 8180),

    //成本卡原料
    IngredientIdError("原料id错误",8120),
    CostCardIdError("成本卡id错误",8121),
    NetCountError("净料用量不为空且不为0.00",8122),
    TotalCountError("原料用量不为空且不为0.00",8123),
    IsAutoOutIsNotNull("是否自动出库不为空",8124),
    CostCardItemIsNotNUll("成本卡原料不为空",8125),
    NewCostCardItemFailed("添加成本卡原料失败",8126),
    NewCostcardItemsFailed("批量添加成本卡原料失败",8127),
    DelCostCardItemByIdFailed("根据主键id删除成本卡原料失败",8128),
    DelCostCardItemByCostCardIdFailed("根据成本卡id删除其所有成本卡原料失败",8129),
    UpdateCostCardItemFailed("修改成本卡原料失败",8130),
    QueryByCostCardIdFailed("根据成本卡id查询其所有原料失败",8131),
    CostCardItemTypeIsNotNull("成本卡原料类别不为空",8132),
    CostCardItemIdError("成本卡原料主键错误",8133),
    DishCostCardIsExist("该菜品成本卡已经存在",8134),
    CostCardInfoError("成本卡信息不合法",8135),

    // 营收统计
     BillAuditListExportToExcelFailed("账单稽查清单导出Excel表格失败",8903),

    // 营业分析
    GetOrderDishByTimePeriodFailed("根据开始时间和结束时间获取所消费的菜品失败",9001),
    GetDishSaleRankDtoByTimePeriodFailed("根据开始时间和结束时间获取消费菜品并处理放入dto中失败",9002),
    ListAllDishSaleRankDtoFailed("查找全部的菜品销售排行失败",9003),
    GetOrderDishDtoByTimePeriodAndTagIdFailed("根据开始时间和结束时间和菜品大类的Id获取消费菜品并处理放入dto中失败",9004),
    DishSaleRankExportToExcelFailed("菜品销售排行导出Excel表格失败",9005),
    GetPageOrderDishDtoByTimePeriodAndTagIdFailed("根据开始时间和结束时间和菜品大类Id获取分页失败",9006),
    GetCountByTimePeriodAndTagIdFailed("根据开始时间和结束时间和菜品大类Id获取数据条数失败",9007),
    GetDishTagRankFailed("根据开始时间和结束时间获取菜品大类的排行失败",9008),
    GetDishTagAllFailed("获取所有的菜品大类排行失败",9009),
    GetPageDishTagRankByTimePeriodAndTagIdFailed("根据开始时间和结束时间和菜品大类Id获取菜品大类销售排行分页失败",9010),
    BigTagRankExportToExcelFailed("菜品大类销售排行导出Excel表格失败",9011),
    // 营收统计
    GetBackDishDtoByTimePeriodFailed("根据开始时间和结束时间获取退菜Dto失败",9012),
    GetPageBackDishDtoByTimePeriodFailed("根据开始时间和结束时间获取退菜Dto分页失败",9013),
    GetCountBackDishDtoByTimePeriodFailed("根据开始时间和结束时间获取退菜Dto数据总量失败",9014),
    BackDishCountExportToExcelFailed("退项清单导出Excel表格失败",9015),
    // 吧台对账
    NewBarContrastFailed("新增吧台对账记录失败", 9020),
    QueryBarContrastFailed("查询吧台对账记录失败", 9021),



    /****************************顾客点餐端****************************/
    // 点菜
    NewDishError("点菜失败", 10001),
    TableIsLock("该餐台有其他顾客正在下单", 10002),
    DelDishError("删除已点菜品失败", 10003),
    TableIsNotHaveAnyDish("该餐台不存在已点但未下单的菜品", 10004),
    UpdateDishError("编辑已点菜品失败", 10005),
    QueryDishError("查询已点菜品失败", 10006),
    CleanTableCacheError("清空该餐台已点菜品失败", 10007),
    TableLockFail("餐台加锁失败", 10008),
    TableLockRemoveFail("餐台解锁失败", 10009),
    SetCurrentOperateCustomerIpFail("设置正在下单的顾客的Ip失败",10010),
    GetCurrentOperateCustomerIpFail("获取正在下单的顾客的Ip失败",10011),
    ReturnTotalMoneyFail("返回已点未下单的所有菜品总金额失败",10012),
    OrderNotEnoughIngredient("判断缓存菜品原材料是否够用失败",10013),
    UpdateIngredientCacheFail("更新元配料缓存失败",10014),



    //订单菜品模块
    OrderIdError("订单id错误",10030),
    ListOrderDishByOrderIdFailed("根据订单id查询订单菜品列表失败",10031),
    OrderDishIdError("订单菜品id错误",10032),
    QueryOrderDishByIdFailed("根据主键id查询订单菜品错误",10033),
    OrderDishStatusError("订单菜品状态错误",10034),
    UpdateDishStatusFailed("修改订单菜品状态失败",10035),
    OrderServeTypeError("订单服务方式错误",10036),
    UpdateServeTypeFailed("修改订单服务状态失败",10037),
    UpdatePresentedDishFailed("确定菜品是否赠送失败",10038),
    OrderDishIsNotNull("订单菜品不为空",10039),
    UpdateOrderDishFailed("修改订单菜品失败",10040),
    NewOrderDishFailed("添加订单菜品失败",10041),
    OrderdishsIsNotEmpty("订单菜品集合元素不为空",10042),
    NewOrderDishsFailed("批量添加订单菜品失败",10043),

    //退菜
    BackOrderDishNotNull("退菜菜品不能为空",10050),
    BackOrderDishFailed("退菜失败",10051),
    BackOrderNumberError("退菜数量错误",10052),

    //订单模块
    TableIdError("桌号id错误",10044),
    OrderStatusError("订单状态错误",10045),
    ListByTableIdAndStatusFailed("根据桌号和订单状态查询订单列表失败",10046),
    OrderIsNotNull("订单不为空",10047),
    NewOrderFailed("添加一个订单失败",10048),
    UpdateOrderFailed("修改订单失败",10049),
    OrderDishCacheIsNull("订单中不存在任何菜品",10050),
    UpdateOrderDishCacheFail("修改订单菜品失败,存在其他用户正在确认下单",10051),
    QueryCheckOrderDtoFail("查询订单盘点所需要的信息失败",10052),
    OrderNotExist("不存在该订单",10053),
    UpdateOrderIsSettlementFail("修改订单盘点状态失败",10054),
    QueryOrderByTimePeriodFail("查询该时间段内的订单信息失败",10055),
    TimeUnreasonable1("开始时间不能大于等于结束时间",10056),
    TimeUnreasonable2("开始时间不能大于结束时间",10057),
    QueryMaxFlagFail("获取最大的套餐标识失败",10058),
    QuantityCanNotReduce("菜品数量无法再减少1",10059),
    QuantityIsNegative("菜品数量不能为负数",10060),
    ReturnTableOrderTotalMoneyFail("返回餐桌未结账的订单的总金额失败",10061),
    CallDishFailed("催菜失败",10062),
    QueryOrderDishListFailed("查询餐台订单菜品失败",10063),
    QueryBackDishListFailed("查询退菜菜品失败",10063),
    OrderDishCacheNotExist("缓存中不存在该菜品",10064),
    QueryOrderByCheckoutIdFail("根据结账单id查询订单失败",10065),
    QueryOrderByStatusFail("根据订单状态查询订单失败",10066),
    CallDishNotAllow("菜品未到达上菜时限，暂不能催菜",10067),
    QueryBackDishCountDtoFailed("查询退菜得到相应的dto失败",10068),
    QueryBackDishByIdFailed("根据id查询退菜失败",10069),


    //结账单模块
    QueryCheckoutByTableIdFailed("根据桌号查询结账单失败",10070),
    CheckoutIsNotNull("结账单不为空",10071),
    NewCheckoutFailed("添加结账单失败",10072),
    UpdateCheckoutFailed("修改结账单失败",10073),
    CheckoutFailed("结账失败", 10074),
    CheckoutStatusError("结账单状态不合法", 10075),
    CheckoutIsNull("该餐台未消费，不能在该桌进行结账",10076),
    CheckoutPayIsNull("结账-支付信息为空",10077),
    NewCheckoutPayFailed("添加结账-支付信息失败",10078),
    UpdateCheckoutPayFailed("修改结账-支付信息失败",10079),
    QueryCheckoutPayFailed("查询结账-支付信息失败",10080),
    CheckoutIdError("结账单ID不合法",10081),
    PrintCheckoutFail("打印结账单失败",10082),
    DirectToCheckout("该餐台未消费，将直接进行结账", 10083),
    QueryCheckoutByTimePeriodFail("查询该时间段的结账单失败",10084),
    SumCheckoutEachItemFail("对所有结账单的单项金钱求和失败",10085),
    CountCheckoutByTimePeriodFail("统计该时间段里已结账的结账单失败",10086),

    // 新版库存模块

    StockItemInsertFailed("添加库存物品失败", 11000),
    QueryStockItemByIdFailed("根据Id查找库存物品失败", 110001),
    UpdateStockItemFailed("修改库存物品失败", 110002),
    UpdateStockStatusFailed("修改库存物品状态失败", 110002),

    ListStockItemFailed("获取物品列表失败",110003),

    //新版库存-厨房管理
    ListKitchenFailed("查看厨房列表失败",10401),
    AddKitchenFailed("添加厨房失败",10402),
    UpdateKitchenFailed("修改厨房失败",10403),
    DeleteKitchenFailed("删除厨房失败",10404),
    QueryKitchenDetailsFailed("查看厨房明细失败",10405),
    KitchenNameIsExist("厨房名称已存在",10406),
    CheckKitchenNameFailed("检查厨房名称是否已存在失败",10407),
    CheckTypeFailed("判断存放点是否是总库失败",10408),

    //新版库存-单据管理
    InsertDocumentsFail("添加单据失败",110010),
    NewSerialNumberFail("生成单据编号失败",110011),
    DocumentsIsNotNull("单据不能为空",110012),
    DocumentsItemIsNotNull("单据详情不能为空",110013),
    DocumentsHandlerPartyId("操作人id不能为空或者小于0",110014),
    KitchenIdError("存储点ID为空或者小于0",110015),
    DocumentsStatusError("状态不能为空或小于0",110016),
    DocumentsMoneyError("单据金额不能为空或小于0",110017),
    DocumentsTypeError("类型不能为空或小于0",110018),
    DocumentsItemListIsNotNull("单据详情list不能为空",110019),
    DocumentsCreatedPartyIdError("经办人id不能为空或者小于零",110020),
    ListDocumentsFail("加载单据信息失败",110021),
    InsertItemDetailFail("添加物品明细失败",110022),
    ItemDetailIsNotNull("物品详情不能为空",110023),
    DelItemDetailFail("删除物品明细失败",110024),
    QueryDocumentsByDtoFail("根据条件dto查询单据信息失败",110022),
    DocumentsIdError("单据Id不能为空或小于0",110023),
    QueryDocumentsByIdFailed("根据ID查询单据信息失败",110024),
    DelDocumentsByIdFailed("根据ID删除单据信息失败",110025),
    DocumentsIsNotExist("单据不存在",110026),
    DelStockDocumentsItemFailed("根据单据id删除单据详情失败",110027),
    QueryDocumentsItemByDocumentsIdError("根据单据id获取该单据下的详情失败",110027),
    DocumentsIdOrStatusIdError("单据id或单据状态有误",110028),
    QueryItemDetailByIdFailed("根据物品id与存放点id查询物品明细失败",110029),
    ItemIdOrKitchenIdError("物品id或厨房有误",110030),
    updateStockItemDetailFailed("修改物品明细失败",110031),
    StockItemNotEnough("库存剩余不足",110032),
    OutOfStockItem("入库物品数量超过库存上限",110033),
    UpdateItemQuantityFailed("更新库存数量失败",110034),
    UpdateDocumentsFail("修改单据信息失败",110035),
    DocumentsDtoIsNotNull("单据Dto不能为空",110036),
    UpdateStockDocumentsItemFailed("修改单据物品失败",110037),
    StockDocumentsItemIsNotNull("单据物品不能为空",110038),
    DelDocumentsItemByIdFailed("删除单据物品明细失败",110039),
    DocumentsItemIdError("单据明细Id不能为空或小于0",110018),

    //新版库存-规格管理
    AddSpecificationsFail("添加规格失败",111000),
    DeleteSpecificationsFail("删除规格失败",111001),
    UpdateSpecificationsFail("更新规格失败",111002),
    QuerySpecificationsByIdFail("通过id查询规格失败",111003),
    SpecificationsIsNull("规格为空",111004),
    ListAllSpecificationsFail("列举所有规格失败",111005),

    //新版库存-厨房物品列表管理
    AddKitchenItemFail("厨房物品添加失败",112000),
    DelKitchenItemFail("厨房物品删除失败",112001),
    UpdateKitchenItemFail("厨房物品更新失败",112002),
    QueryAllKitchenItemFail("查询所有厨房物品失败",112003),
    QueryKitchenItemFail("查询某个厨房物品失败",112004),

    //新版库存-库存预警
    AddStockWarnFail("添加物品预警失败",113000),
    UpdateStateToResolveFail("修改状态为已解决失败",113001),
    UpdateStateToIgnoreFail("修改状态为已忽略失败",113002),
    QueryAllUntreatedWarnFail("获取所有未处理的预警信息失败",113003),




    /****************************后厨管理端****************************/

    //后厨管理模块
    QueryIsHaveOrderDishFailed("查询餐桌是否有未上的菜品失败",10090),
    InitCookTableCacheFail("初始化餐桌版本号失败",10091),
    ListAllTableVersionFail("获取所有桌子的版本号失败",10092),
    QueryOrderDishTableIdFail("查询订单菜品所对应的餐桌Id失败",10093),
    ListPrintOrderDishDtoFail("获取订单菜品打印详细信息失败",10094),
    PrintOrderDishFail("打印订单菜品失败",10095),
    OrderDishStatusWrong("只有已经打印出来的菜品才可进行划单！",10096),
    OrderDishWipeIsFinsh("该订单菜品已被划单",10097),
    OrderDishNotExist("该订单菜品不存在",10098),
    WipeOrderDishFail("菜品划单失败",10099),
    PrinterIpIsNull("打印机Ip地址未设置",10100),
    ConnectPrinterFail("连接打印机失败",10101),
    CheckOrderDishPrinter("检查订单菜品打印机失败",10102),
    PrintOrderDishAtOnceFail("下单后立即打印菜品失败",10103),

    // 智能排菜
    CreateQueFail("生成智能排菜队列失败",10200),
    OrderDishPrinterIsNotSet("未设置菜品打印机",10201),

    /****************************吧台端****************************/
    QueryTagFail("查询菜品一级分类和对应的二级分类失败",10300),
    QueryDishByTagFail("根据菜品大类查询菜品失败",10301),
    QueryDishByKeyFail("根据关键字查询菜品失败",10302),

    QueryDishTagByNameFail("根据关键字查询分类失败",10303),
    ;



    private String msg;

    private int code;

    EmenuException(String msg, int code) {
        this.msg = msg;
        this.code = code;
    }

    EmenuException(String msg) {
        this.msg = msg;
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
