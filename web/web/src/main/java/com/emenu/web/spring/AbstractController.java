package com.emenu.web.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.dto.rank.DishSaleRankDto;
import com.emenu.common.utils.WebConstants;
import com.emenu.service.bar.BarContrastService;
import com.emenu.service.bar.BarOrderDishNewService;
import com.emenu.service.call.CallCacheService;
import com.emenu.service.cook.CookTableCacheService;
import com.emenu.service.dish.*;
import com.emenu.service.dish.tag.TagFacadeService;
import com.emenu.service.dish.tag.TagService;
import com.emenu.service.meal.MealPeriodService;
import com.emenu.service.order.*;
import com.emenu.service.other.ConstantService;
import com.emenu.service.other.SerialNumService;
import com.emenu.service.page.IndexImgService;
import com.emenu.service.page.KeywordsService;
import com.emenu.service.party.group.PartyService;
import com.emenu.service.party.group.employee.EmployeeService;
import com.emenu.service.party.group.supplier.SupplierService;
import com.emenu.service.party.login.LoginManageService;
import com.emenu.service.party.security.SecurityGroupPermissionService;
import com.emenu.service.party.security.SecurityGroupService;
import com.emenu.service.party.security.SecurityPermissionService;
import com.emenu.service.party.group.vip.VipInfoService;
import com.emenu.service.party.security.SecurityUserService;
import com.emenu.service.printer.DishTagPrinterService;
import com.emenu.service.printer.PrinterService;
import com.emenu.service.rank.DishSaleRankService;
import com.emenu.service.register.RegisterService;
import com.emenu.service.remark.RemarkService;
import com.emenu.service.remark.RemarkTagService;
import com.emenu.service.call.CallWaiterService;
import com.emenu.service.revenue.BillAuditService;
import com.emenu.service.revenue.BackDishCountService;
import com.emenu.service.sms.SmsService;
import com.emenu.service.stock.SpecificationsService;
import com.emenu.service.storage.*;
import com.emenu.service.table.*;
import com.emenu.service.vip.*;
import com.emenu.service.rank.*;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.framework.bean.StaticAutoWire;
import com.pandawork.core.framework.web.spring.controller.Base;
import com.pandawork.wechat.WeChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * 添加抽象父类，进行封装核心的信息。
 *
 * @author Lionel
 * @E-mail lionel@pandawork.net
 * @time 2012-8-14
 */
public class AbstractController extends Base {
    // 默认分页size
    protected final static int DEFAULT_PAGE_SIZE = 10;
    // ajax默认成功代码
    protected final static int AJAX_SUCCESS_CODE = 0;
    // ajax默认失败代码
    protected final static int AJAX_FAILURE_CODE = 1;

    protected final static String DISABLED_STRING = "（已停用）";

    // 系统异常返回页面
    protected final static String ADMIN_SYS_ERR_PAGE = "admin/500";
    // 禁止访问返回页面
    protected final static String ADMIN_FORBIDDEN_PAGE = "admin/403";
    // 无法找到404页面
    protected final static String ADMIN_NOT_FOUND_PAGE = "admin/404";

    // 顾客点餐平台异常返回页面
    protected final static String MOBILE_SYS_ERR_PAGE = "mobile/500";
    // 顾客点餐平台禁止访问返回页面
    protected final static String MOBILE_FORBIDDEN_PAGE = "mobile/403";
    // 顾客点餐平台无法找到404页面
    protected final static String MOBILE_NOT_FOUND_PAGE = "mobile/404";
    // 顾客点餐平台Session过期页面
    protected final static String MOBILE_SESSION_OVERDUE_PAGE = "mobile/session";
    // 顾客点餐平台未开台页面
    protected final static String MOBILE_NOT_OPEN_PAGE = "mobile/notopen";

    // 微信异常返回页面
    protected final static String WECHAT_SYS_ERR_PAGE = "wechat/500";
    // 微信禁止访问返回页面
    protected final static String WECHAT_FORBIDDEN_PAGE = "wechat/403";
    // 微信无法找到404页面
    protected final static String WECHAT_NOT_FOUND_PAGE = "wechat/404";

    //后厨管理端无法找到404页面
    protected final static String COOK_NOT_FOUND_PAGE = "cook/404";

    // 系统异常返回页面
    protected final static String VIP_SYS_ERR_PAGE = "forward:/500.jsp";
    // 禁止访问返回页面
    protected final static String VIP_FORBIDDEN_PAGE = "forward:/403.jsp";
    // 无法找到404页面
    protected final static String VIP_NOT_FOUND_PAGE = "forward:/404.jsp";

    //公共页面异常返回页面
    protected final static String PUB_SYS_ERR_PAGE = "forward:/500.jsp";
    //公共页面无法找到页面
    protected final static String PUB_NOT_FOUND_PAGE = "forward:/404.jsp";
    //公共页面禁止访问返回页
    protected final static String PUB_FORBIDDEN_PAGE = "forward:/403.jsp";

    // 添加成功
    protected final static String NEW_SUCCESS_MSG = "添加成功!";

    // 修改成功
    protected final static String UPDATE_SUCCESS_MSG = "修改成功!";

    /**
     * 当前用户ID
     */
    private ThreadLocal<Integer> uId = new ThreadLocal<Integer>();
    private ThreadLocal<Integer> partyId = new ThreadLocal<Integer>();

    // ${website}
    private ThreadLocal<String> website = new ThreadLocal<String>();


    @Override
    public void init(HttpServletRequest request, HttpServletResponse response) {
        super.init(request, response);
        // 设置uId
        uId.set((Integer) request.getAttribute(WebConstants.WEB_SECURITY_USER_ID));
        // 设置partyId
        partyId.set((Integer) request.getAttribute(WebConstants.WEB_PARTY_ID));
        // 设置website
        website.set((String) request.getAttribute("website"));
    }

    /**
     * 获取用户ID,如果当前用户不登录，默认返回0；
     *
     * @return
     * @author Lionel
     * @E-mail lionel@pandawork.net
     * @time 2012-8-14
     */
    protected Integer getUId() {
        return uId.get() == null ? 0 : uId.get();
    }

    /**
     * 获取当前用户partyId,如果当前用户不登录，默认返回0
     * @return
     * @author Lionel
     * @E-mail lionel@pandawork.net
     * @time 2012-8-26
     * @return
     */
    protected Integer getPartyId() {
        if (partyId.get() == null) {
            return 0;
        }
        return uId.get();
    }


    /**
     * 获取当前网址
     *
     * @return
     */
    protected String getWebsite() {
        return this.website.get();
    }

    /**
     * 发送无数据与的json对象
     *
     * @param code
     * @return
     */
    protected JSONObject sendJsonObject(int code) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        return jsonObject;
    }

    /**
     * 发送json对象
     *
     * @param json
     * @return
     */
    protected JSONObject sendJsonObject(JSON json, int code) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", code);
        if (json != null) {
            jsonObject.put("data", json);
        }
        return jsonObject;
    }

    // 发送ajax错误信息
    protected JSONObject sendErrMsgAndErrCode(SSException e) {
        JSONObject json = new JSONObject();
        json.put("code", AJAX_FAILURE_CODE);
        json.put("errMsg", e.getMessage());
        return json;
    }

    // 发送ajax 编号和信息
    protected JSONObject sendMsgAndCode(int code , String msg) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("errMsg", msg);
        return json;
    }


    // 发送ajax分页信息
    protected JSONObject sendJsonArray(JSONArray jsonArray, int dataCount, int pageSize) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", AJAX_SUCCESS_CODE);
        jsonObject.put("list", jsonArray);
        jsonObject.put("dataCount", dataCount);
        jsonObject.put("pageSize", pageSize);
        return jsonObject;
    }

    // 默认按照默认的pagesize返回数据
    protected JSONObject sendJsonArray(JSONArray jsonArray, int dataCount) {
        return sendJsonArray(jsonArray, dataCount, DEFAULT_PAGE_SIZE);
    }

    protected JSONObject sendJsonArray(JSONArray jsonArray) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("code", AJAX_SUCCESS_CODE);
        jsonObject.put("list", jsonArray);
        return jsonObject;
    }

    @StaticAutoWire
    @Qualifier("loginManageService")
    protected static LoginManageService loginManageService;

    @StaticAutoWire
    @Qualifier("partyService")
    protected static PartyService partyService;

    @StaticAutoWire
    @Qualifier("securityPermissionService")
    protected static SecurityPermissionService securityPermissionService;

    @StaticAutoWire
    @Qualifier("securityGroupService")
    protected static SecurityGroupService securityGroupService;

    @StaticAutoWire
    @Qualifier("securityGroupPermissionService")
    protected static SecurityGroupPermissionService securityGroupPermissionService;

    @StaticAutoWire
    @Qualifier("keywordsService")
    protected static KeywordsService keywordsService;

    @StaticAutoWire
    @Qualifier("indexImgService")
    protected static IndexImgService indexImgService;

    @StaticAutoWire
    @Qualifier("areaService")
    protected static AreaService areaService;

    @StaticAutoWire
    @Qualifier("tableService")
    protected static TableService tableService;

    @StaticAutoWire
    @Qualifier("qrCodeService")
    protected static QrCodeService qrCodeService;

    @StaticAutoWire
    @Qualifier("constantService")
    protected static ConstantService constantService;

    @StaticAutoWire
    @Qualifier("vipInfoService")
    protected static VipInfoService vipInfoService;

    @StaticAutoWire
    @Qualifier("vipOperationService")
    protected static VipOperationService vipOperationService;

    @StaticAutoWire
    @Qualifier("consumptionActivityService")
    protected static ConsumptionActivityService consumptionActivityService;

    //菜品和原料单位
    @StaticAutoWire
    @Qualifier("unitService")
    protected static UnitService unitService;

    @StaticAutoWire
    @Qualifier("employeeService")
    protected static EmployeeService employeeService;

    @StaticAutoWire
    @Qualifier("securityUserService")
    protected static SecurityUserService securityUserService;

    //分类
    @StaticAutoWire
    @Qualifier("tagFacadeService")
    protected static TagFacadeService tagFacadeService;

    @StaticAutoWire
    @Qualifier("tagService")
    protected static TagService tagService;

    @StaticAutoWire
    @Qualifier("waiterTableService")
    protected static WaiterTableService waiterTableService;

    @StaticAutoWire
    @Qualifier("supplierService")
    protected static SupplierService supplierService;

    @StaticAutoWire
    @Qualifier("storageTagService")
    protected static StorageTagService storageTagService;

    @StaticAutoWire
    @Qualifier("mealPeriodService")
    protected static MealPeriodService mealPeriodService;

    @StaticAutoWire
    @Qualifier("tableMealPeriodService")
    protected static TableMealPeriodService tableMealPeriodService;

    //库存存放点
    @StaticAutoWire
    @Qualifier("storageDepotService")
    protected static StorageDepotService storageDepotService;

    @StaticAutoWire
    @Qualifier("remarkService")
    protected static RemarkService remarkService;

    @StaticAutoWire
    @Qualifier("remarkTagService")
    protected static RemarkTagService remarkTagService;

    @StaticAutoWire
    @Qualifier("printerService")
    protected static PrinterService printerService;

    @StaticAutoWire
    @Qualifier("dishTagPrinterService")
    protected static DishTagPrinterService dishTagPrinterService;

    @StaticAutoWire
    @Qualifier("storageItemService")
    protected static StorageItemService storageItemService;

    @StaticAutoWire
    @Qualifier("dishService")
    protected static DishService dishService;

    //套餐管理
    @StaticAutoWire
    @Qualifier("dishPackageService")
    protected static DishPackageService dishPackageService;

    //会员价方案管理
    @StaticAutoWire
    @Qualifier("vipDishPricePlanService")
    protected static VipDishPricePlanService vipDishPricePlanService;

    //会员价管理
    @StaticAutoWire
    @Qualifier("vipDishPriceService")
    protected static VipDishPriceService vipDishPriceService;

    //库存单据
    @StaticAutoWire
    @Qualifier("storageReportService")
    protected static StorageReportService storageReportService;

    //库存单据详情
    @StaticAutoWire
    @Qualifier("storageReportItemService")
    protected static StorageReportItemService storageReportItemService;

    @StaticAutoWire
    @Qualifier("storageReportIngredientService")
    protected StorageReportIngredientService storageReportIngredientService;

    @StaticAutoWire
    @Qualifier("serialNumService")
    protected static SerialNumService serialNumService;

    // 口味
    @StaticAutoWire
    @Qualifier("tasteService")
    protected static TasteService tasteService;

    // 菜品-口味
    @StaticAutoWire
    @Qualifier("dishTasteService")
    protected static DishTasteService dishTasteService;

    // 菜品图片
    @StaticAutoWire
    @Qualifier("dishImgService")
    protected static DishImgService dishImgService;

    //库存盘点和结算中心
    @StaticAutoWire
    @Qualifier("storageSettlementService")
    protected static StorageSettlementService storageSettlementService;

    @StaticAutoWire
    @Qualifier("dishTagService")
    protected static DishTagService dishTagService;

    //多倍积分方案
    @StaticAutoWire
    @Qualifier("multipleIntegralPlanService")
    protected static MultipleIntegralPlanService multipleIntegralPlanService;

    //会员充值方案管理
    @StaticAutoWire
    @Qualifier("vipRechargePlanService")
    protected static VipRechargePlanService vipRechargePlanService;

    //会员等级管理
    @StaticAutoWire
    @Qualifier("vipGradeService")
    protected static VipGradeService vipGradeService;

    //会员卡管理
    @StaticAutoWire
    @Qualifier("vipCardService")
    protected static VipCardService vipCardService;

    //会员账号管理
    @StaticAutoWire
    @Qualifier("vipAccountInfoService")
    protected static VipAccountInfoService vipAccountInfoService;

    //会员积分方案管理
    @StaticAutoWire
    @Qualifier("vipIntegralPlanService")
    protected static VipIntegralPlanService vipIntegralPlanService;

    //成本卡
    @StaticAutoWire
    @Qualifier("costCardService")
    protected static CostCardService costCardService;

    //成本卡原料
    @StaticAutoWire
    @Qualifier("costCardItemService")
    protected  static CostCardItemService costCardItemService;

    //原配料管理
    @StaticAutoWire
    @Qualifier("ingredientService")
    protected static IngredientService ingredientService;

    // 发送短信
    @StaticAutoWire
    @Qualifier("smsService")
    protected static SmsService smsService;

    /**************************营收统计**************************/
    // 账单稽查
    @StaticAutoWire
    @Qualifier("billAuditService")
    protected static BillAuditService billAuditService;

    // 退菜统计service
    @StaticAutoWire
    @Qualifier("backDishCountService")
    protected static BackDishCountService backDishCountService;

    // 营业分析中的菜品销售排行
    @StaticAutoWire
    @Qualifier("dishSaleRankService")
    protected static DishSaleRankService dishSaleRankService;

    // 营业分析中的菜品大类销售排行
    @StaticAutoWire
    @Qualifier("dishTagRankService")
    protected static DishTagRankService dishTagRankService;

    @StaticAutoWire
    @Qualifier("registerService")
    protected static RegisterService registerService;

    /**************************顾客点菜端**************************/
    //呼叫服务后台管理
    @StaticAutoWire
    @Qualifier("callWaiterService")
    protected static CallWaiterService callWaiterService;

    // 点餐缓存
    @StaticAutoWire
    @Qualifier("orderDishCacheService")

    protected static OrderDishCacheService orderDishCacheService;

    //结账单
    @StaticAutoWire
    @Qualifier("checkoutService")
    protected static CheckoutService checkoutService;

    //结账-支付
    @StaticAutoWire
    @Qualifier("checkoutPayService")
    protected static CheckoutPayService checkoutPayService;


    //菜品大类和备注大类关联service
    @StaticAutoWire
    @Qualifier("dishRemarkTagService")
    protected static DishRemarkTagService dishRemarkTagService;


    //订单service
    @StaticAutoWire
    @Qualifier("orderService")
    protected static OrderService orderService;

    // 退菜service
    @StaticAutoWire
    @Qualifier("backDishService")
    protected static BackDishService backDishService;

    //订单菜品service
    @StaticAutoWire
    @Qualifier("orderDishService")
    protected static OrderDishService orderDishService;

    //后厨管理service
    @StaticAutoWire
    @Qualifier("cookTableCacheService")
    protected static CookTableCacheService cookTableCacheService;

    //后厨管理打印菜品service
    @StaticAutoWire
    @Qualifier("orderDishPrintService")
    protected static OrderDishPrintService orderDishPrintService;


    /**************************微信**************************/
    @StaticAutoWire
    @Qualifier("weChatService")
    protected static WeChatService weChatService;

    /**************************吧台**************************/
    // 并台
    @StaticAutoWire
    @Qualifier("tableMergeService")
    protected static TableMergeService tableMergeService;

    // 呼叫服务缓存
    @StaticAutoWire
    @Qualifier("callCacheService")
    protected static CallCacheService callCacheService;

    //吧台端新增消费
    @StaticAutoWire
    @Qualifier("barOrderDishNewService")
    protected static BarOrderDishNewService barOrderDishNewService;

    // 吧台对账
    @StaticAutoWire
    @Qualifier("barContrastService")
    protected static BarContrastService barContrastService;


    /****************新版库存*************************/
    //规格管理
    @StaticAutoWire
    @Qualifier("specificationsService")
    protected static SpecificationsService specificationsService;
}



