package com.emenu.web.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.utils.WebConstants;
import com.emenu.service.call.CallCacheService;
import com.emenu.service.call.CallWaiterService;
import com.emenu.service.dish.*;
import com.emenu.service.dish.tag.TagFacadeService;
import com.emenu.service.meal.MealPeriodService;
import com.emenu.service.order.OrderDishCacheService;
import com.emenu.service.order.OrderDishService;
import com.emenu.service.order.OrderService;
import com.emenu.service.other.ConstantService;
import com.emenu.service.other.SerialNumService;
import com.emenu.service.page.IndexImgService;
import com.emenu.service.page.KeywordsService;
import com.emenu.service.party.group.PartyService;
import com.emenu.service.party.group.employee.EmployeeService;
import com.emenu.service.party.group.supplier.SupplierService;
import com.emenu.service.party.group.vip.VipInfoService;
import com.emenu.service.party.login.LoginManageService;
import com.emenu.service.party.security.SecurityGroupPermissionService;
import com.emenu.service.party.security.SecurityGroupService;
import com.emenu.service.party.security.SecurityPermissionService;
import com.emenu.service.party.security.SecurityUserService;
import com.emenu.service.printer.DishTagPrinterService;
import com.emenu.service.printer.PrinterService;
import com.emenu.service.remark.RemarkService;
import com.emenu.service.remark.RemarkTagService;
import com.emenu.service.storage.*;
import com.emenu.service.table.*;
import com.emenu.service.vip.VipDishPricePlanService;
import com.emenu.service.vip.VipDishPriceService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.framework.bean.StaticAutoWire;
import com.pandawork.core.framework.web.spring.controller.Base;
import org.springframework.beans.factory.annotation.Qualifier;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 服务员和吧台客户端Controller的抽象父类
 *
 * @author: zhangteng
 * @time: 2015/12/8 09:20
 **/
public class AbstractAppBarController extends Base {

    // 默认分页size
    protected final static int DEFAULT_PAGE_SIZE = 10;
    // ajax默认成功代码
    protected final static int AJAX_SUCCESS_CODE = 0;
    // ajax默认失败代码
    protected final static int AJAX_FAILURE_CODE = 1;

    protected final static String DISABLED_STRING = "（已停用）";

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
    @Qualifier("serialNumService")
    protected static SerialNumService serialNumService;

    //菜品口味
    @StaticAutoWire
    @Qualifier("tasteService")
    protected static TasteService tasteService;

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

    //呼叫服务客户端,呼叫服务缓存
    @StaticAutoWire
    @Qualifier("callCacheService")
    protected static CallCacheService callCacheService;

    //呼叫服务后台管理
    @StaticAutoWire
    @Qualifier("callWaiterService")
    protected static CallWaiterService callWaiterService;

    // 并台
    @StaticAutoWire
    @Qualifier("tableMergeService")
    protected static TableMergeService tableMergeService;

    @StaticAutoWire
    @Qualifier("tableMergeCacheService")
    protected static TableMergeCacheService tableMergeCacheService;

    // 点餐缓存
    @StaticAutoWire
    @Qualifier("orderDishCacheService")
    protected static OrderDishCacheService orderDishCacheService;

    // Order
    @StaticAutoWire
    @Qualifier("orderService")
    protected static OrderService orderService;

    // OrderDish
    @StaticAutoWire
    @Qualifier("orderDishService")
    protected static OrderDishService orderDishService;

}
