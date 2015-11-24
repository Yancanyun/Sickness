package com.emenu.web.spring;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.utils.WebConstants;
import com.emenu.service.dish.DishImgService;
import com.emenu.service.dish.DishService;
import com.emenu.service.dish.TasteService;
import com.emenu.service.dish.tag.TagFacadeService;
import com.emenu.service.dish.UnitService;
import com.emenu.service.meal.MealPeriodService;
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

    @StaticAutoWire
    @Qualifier("vipDishPricePlanService")
    protected static VipDishPricePlanService vipDishPricePlanService;

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
 }
