package com.emenu.web.controller.admin.storage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.party.group.employee.EmployeeDto;
import com.emenu.common.dto.storage.ReportSearchDto;
import com.emenu.common.dto.storage.StorageReportDto;
import com.emenu.common.entity.party.security.SecurityUser;
import com.emenu.common.entity.storage.*;
import com.emenu.common.enums.other.ConstantEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.storage.IngredientStatusEnums;
import com.emenu.common.enums.storage.StorageReportIsAuditedEnum;
import com.emenu.common.enums.storage.StorageReportTypeEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.exception.PartyException;
import com.emenu.common.utils.DateUtils;
import com.emenu.common.utils.URLConstants;
import com.emenu.common.utils.WebConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * 单据controller
 * @author xiaozl
 * @date 2015/11/21
 * @time 10:49
 */

@Module(ModuleEnums.AdminStorage)
@Controller
@RequestMapping(URLConstants.ADMIN_STORAGE_REPORT_URL)
public class AdminStorageReportController extends AbstractController {

    /**
     * 新
     * 去单据管理页
     * @return
     */
    @Module(ModuleEnums.AdminStorageReportList)
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList(Model model){
        try {
            //存放点
            List<StorageDepot> depotList = depotList = storageDepotService.listAll();
            //经手人、操作人、审核人
            List<EmployeeDto> employeeDtoList = employeeDtoList = employeeService.listAll();
            List<StorageItem> itemList = storageItemService.listAll();
            List<Ingredient> ingredientList = ingredientService.listAll();
            model.addAttribute("depotList", depotList);
            model.addAttribute("handlerList", employeeDtoList);
            model.addAttribute("createdList", employeeDtoList);
            model.addAttribute("auditedList", employeeDtoList);
            model.addAttribute("itemList", itemList);
            model.addAttribute("ingredientList", ingredientList);
            model.addAttribute("lastMonthFirstDay", DateUtils.getLastMonthFirstDay());
            model.addAttribute("lastMonthLastDay", DateUtils.getLastMonthLastDay());
            model.addAttribute("currentMonthFirstDay", DateUtils.getCurrentMonthFirstDay());
            model.addAttribute("currentMonthLastDay", DateUtils.getCurrentMonthLastDay());
            model.addAttribute("currentDay", DateUtils.yearMonthDayFormat(DateUtils.now()));
            return "admin/storage/report/list_home";
        } catch (SSException e) {
            sendErrMsg(e.getMessage());
            LogClerk.errLog.error(e);
            return WebConstants.sysErrorCode;
        }
    }

    /**
     *  新
     * 分页获取单据信息
     * @param pageNo
     * @param createdPartyId
     * @param handlerPartyId
     * @param startTime
     * @param endTime
     * @param pageSize
     * @return
     */
    @Module(ModuleEnums.AdminStorageReportList)
    @RequestMapping(value = "ajax/list/{curPage}",method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxSearch(@PathVariable("curPage")Integer pageNo,
                           @RequestParam("createdPartyId")Integer createdPartyId,
                           @RequestParam("handlerPartyId")Integer handlerPartyId,
                           @RequestParam("auditPartyId")Integer auditPartyId,
                           @RequestParam("startTime")Date startTime,
                           @RequestParam("endTime")Date endTime,
                           @RequestParam("pageSize") Integer pageSize,
                           @RequestParam("isAudited") Integer isAudited,
                           @RequestParam("isSettlemented") Integer isSettlemented,
                           @RequestParam("depotId") Integer depotId) {
        ReportSearchDto reportSearchDto = new ReportSearchDto();
        // 对页面大小和页码预处理
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        pageNo = pageNo == null ? 1 : pageNo;

        reportSearchDto.setPageNo(pageNo);
        reportSearchDto.setPageSize(pageSize);
        reportSearchDto.setEndTime(endTime);
        reportSearchDto.setStartTime(startTime);
        reportSearchDto.setCreatedPartyId(createdPartyId);
        reportSearchDto.setHandlerPartyId(handlerPartyId);
        reportSearchDto.setAuditPartyId(auditPartyId);
        reportSearchDto.setIsAudited(isAudited);
        reportSearchDto.setIsSettlemented(isSettlemented);
        reportSearchDto.setDepotId(depotId);

        int dataCount = 0;
        try {
            //总数据数
            dataCount = storageReportService.countByReportSerachDto(reportSearchDto);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        List<StorageReportDto> storageReportDtoList = Collections.emptyList();
        try {
            //分页获取单据和单据详情
            storageReportDtoList = storageReportService.listReportDtoBySerachDto(reportSearchDto);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONArray jsonArray = new JSONArray();
        try{
            for (StorageReportDto storageReportDto : storageReportDtoList) {
                JSONObject jsonObject = new JSONObject();
                // 单据
                jsonObject.put("id", storageReportDto.getStorageReport().getId());
                jsonObject.put("depotId", storageReportDto.getStorageReport().getDepotId());
                jsonObject.put("type", storageReportDto.getStorageReport().getType());
                jsonObject.put("money", storageReportDto.getStorageReport().getMoney());
                jsonObject.put("serialNumber", storageReportDto.getStorageReport().getSerialNumber());
                jsonObject.put("handlerPartyId", storageReportDto.getStorageReport().getHandlerPartyId());
                jsonObject.put("createdPartyId", storageReportDto.getStorageReport().getCreatedPartyId());
                jsonObject.put("handlerName", storageReportDto.getStorageReport().getHandlerName());
                jsonObject.put("createdName", storageReportDto.getStorageReport().getCreatedName());
                jsonObject.put("auditPartyId", storageReportDto.getStorageReport().getAuditPartyId());
                jsonObject.put("comment", storageReportDto.getStorageReport().getComment());
                jsonObject.put("createdTime", DateUtils.formatDate(storageReportDto.getStorageReport().getCreatedTime(),"yyyy-MM-dd"));
                if (Assert.isNull(storageReportDto.getStorageReport().getAuditPartyId())
                        || Assert.lessOrEqualZero(storageReportDto.getStorageReport().getAuditPartyId())){
                    jsonObject.put("auditName","暂无");
                } else {
                    jsonObject.put("auditName", storageReportDto.getStorageReport().getAuditName());
                }
                jsonObject.put("isSettlemented", storageReportDto.getStorageReport().getIsSettlemented());
                jsonObject.put("isAudited", storageReportDto.getStorageReport().getIsAudited());
                JSONArray reportItemJSONArray = new JSONArray();
                if (storageReportDto.getStorageReport().getType() == StorageReportTypeEnum.StockInReport.getId()){
                    String deportName = storageDepotService.queryById(storageReportDto.getStorageReport().getDepotId()).getName();
                    jsonObject.put("depotName", deportName);
                    List<StorageReportItem> storageReportItemList = storageReportDto.getStorageReportItemList();
                    for (StorageReportItem storageReportItem : storageReportItemList){
                        // 存放单据详情
                        JSONObject jsonObjectItem = new JSONObject();
                        jsonObjectItem.put("itemId",storageReportItem.getItemId());
                        jsonObjectItem.put("itemName",storageReportItem.getItemName());
                        jsonObjectItem.put("itemNumber",storageReportItem.getItemNumber());
                        jsonObjectItem.put("orderUnitName",storageReportItem.getOrderUnitName());
                        jsonObjectItem.put("orderQuantity",storageReportItem.getOrderQuantity());
                        jsonObjectItem.put("costCardUnitName",storageReportItem.getCostCardUnitName());
                        jsonObjectItem.put("costCardQuantity",storageReportItem.getCostCardQuantity());
                        jsonObjectItem.put("price",storageReportItem.getPrice());
                        jsonObjectItem.put("count",storageReportItem.getCount());
                        jsonObjectItem.put("comment",storageReportItem.getComment());
                        reportItemJSONArray.add(jsonObjectItem);
                    }

                } else {
                    List<StorageReportIngredient> storageReportIngredientList = storageReportDto.getStorageReportIngredientList();
                    for (StorageReportIngredient storageReportIngredient : storageReportIngredientList){
                        // 存放单据详情
                        JSONObject jsonObjectItem = new JSONObject();
                        jsonObjectItem.put("ingredientName",storageReportIngredient.getIngredientName());
                        jsonObjectItem.put("ingredientNumber",storageReportIngredient.getIngredientNumber());
                        jsonObjectItem.put("costCardUnitName",storageReportIngredient.getCostCardUnitName());
                        jsonObjectItem.put("costCardQuantity",storageReportIngredient.getCostCardQuantity());
                        jsonObjectItem.put("storageUnitName",storageReportIngredient.getCostCardUnitName());
                        jsonObjectItem.put("storageQuantity",storageReportIngredient.getCostCardQuantity());
                        jsonObjectItem.put("comment",storageReportIngredient.getComment());
                        reportItemJSONArray.add(jsonObjectItem);
                    }
                    jsonObject.put("depotName", "");
                }
                jsonObject.put("reportItem",reportItemJSONArray);
                jsonArray.add(jsonObject);
            }
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
//        reportSearchDto
        return sendJsonArray(jsonArray, dataCount, pageSize);
//        return sendJsonArray(jsonArray, dataCount, reportSearchDto.getPageSize());
    }

    /**
     * 新
     * 添加单据
     * @param storageReportDto
     * @param httpSession
     * @return
     */
    @Module(ModuleEnums.AdminStorageReportNew)
    @RequestMapping(value = "ajax/new", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject newStorageReportDto(@RequestBody StorageReportDto storageReportDto, HttpSession httpSession){
        try {
            //生成单据编号
            if (Assert.isNull(storageReportDto)){
                throw SSException.get(EmenuException.ReportIsNotNull);
            }
            SecurityUser securityUser = securityUserService.queryByLoginName((String)httpSession.getAttribute("userName"));
            if (Assert.isNull(securityUser)){
                throw SSException.get(PartyException.UserNotExist);
            }
            storageReportDto.getStorageReport().setCreatedPartyId(securityUser.getPartyId());
            storageReportService.newReportDto(storageReportDto);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 新
     * ajax删除单据和单据详情
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminStorageReportDelete)
    @RequestMapping(value = "ajax/del/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public
    JSONObject delStorageReportDtoByReportId(@PathVariable("id") Integer id){
        try {
            storageReportService.delReportDtoById(id);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }


    /**
     * 新
     * 编辑单据
     * @param storageReportDto
     * @return
     */
    @Module(ModuleEnums.AdminStorageReportUpdate)
    @RequestMapping(value = "ajax/update",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@RequestBody StorageReportDto storageReportDto){
        try{
            storageReportService.updateReportDto(storageReportDto);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 新
     * 计算小计金额 、数量转换
     * @param type
     * @param price
     * @param orderQuantity
     * @param orderUnitName
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminStorageReportNew)
    @RequestMapping(value = "ajax/bill", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject calculateTotalPrice(@RequestParam("type")Integer type,
                                          @RequestParam("price")BigDecimal price,
                                          @RequestParam("orderQuantity")BigDecimal orderQuantity,
                                          @RequestParam("orderUnitName")String orderUnitName,
                                          @RequestParam("id")Integer id){
        JSONObject jsonObject = new JSONObject();
        try {
            StorageItem storageItem = storageItemService.queryById(id);
            if (Assert.isNull(storageItem)
                    || Assert.isNull(storageItem.getCostCardUnitName())
                    || Assert.isNull(storageItem.getStorageToCostCardRatio())
                    || Assert.isNull(storageItem.getStorageToCostCardRatio())){
                return sendJsonObject(jsonObject, AJAX_FAILURE_CODE);
            }
            // 单位名
            jsonObject.put("costCardUnitName", storageItem.getCostCardUnitName());
            BigDecimal costCardQuantity = new BigDecimal("0.00");
            // 数量
            costCardQuantity = costCardQuantity.add(orderQuantity.multiply(storageItem.getOrderToStorageRatio()).multiply(storageItem.getStorageToCostCardRatio()));
            jsonObject.put("costCardQuantity", costCardQuantity);
            // 小计
            BigDecimal money = price.multiply(orderQuantity);
            jsonObject.put("money", money);
            jsonObject.put("itemNumber",storageItem.getItemNumber());
            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendMsgAndCode(AJAX_FAILURE_CODE,"小计失败");
        }
    }

    /**
     * 新
     * 添加单据时获取原配料
     * @param keyword
     * @return
     */
    @Module(ModuleEnums.AdminStorageReportNew)
    @RequestMapping(value = "ajax/getingredient", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getReportIngredient(@RequestParam("keyword")String keyword){
        JSONArray jsonArray = new JSONArray();
        try {
            List<Ingredient> ingredientList = ingredientService.listByKeyword(keyword);
            for (Ingredient ingredient : ingredientList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("ingredientId", ingredient.getId());
                jsonObject.put("name", ingredient.getName());
                jsonObject.put("code", ingredient.getAssistantCode());
                jsonObject.put("ingredientNumber", ingredient.getIngredientNumber());
                jsonObject.put("costCardUnitName",ingredient.getCostCardUnitName());
                jsonArray.add(jsonObject);
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray);
    }

    /**
     * 新
     * 添加单据时获取库存物品
     * @param keyword
     * @return
     */
    @Module(ModuleEnums.AdminStorageReportNew)
    @RequestMapping(value = "ajax/getitem", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getReportItem(@RequestParam("keyword")String keyword){
        JSONObject jsonObject = new JSONObject();
        try {
            StorageItem storageItem = storageItemService.queryByKeyword(keyword);
            jsonObject.put("Item", storageItem);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
    }

    /**
     * 添加是获取单据物品成本卡单位数量
     * @param storageReportItem
     * @return
     */
    @Module(ModuleEnums.AdminStorageReportNew)
    @RequestMapping(value = "ajax/convertitem", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject convertItem(StorageReportItem storageReportItem){
        JSONObject jsonObject = new JSONObject();
        if (Assert.isNull(storageReportItem)){
            sendMsgAndCode(1,"添加失败");
        }
        try {
            StorageItem storageItem = storageItemService.queryById(storageReportItem.getItemId());
            storageReportItem.setCostCardQuantity(storageReportItem.getQuantity().multiply(storageItem.getOrderToStorageRatio()).multiply(storageItem.getStorageToCostCardRatio()));
            jsonObject.put("storageReportItem", storageReportItem);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
    }

    /**
     * 获取库存单位原配料数量
     * @param id
     * @param costCardQuantity
     * @param type
     * @return
     */
    @Module(ModuleEnums.AdminStorageReportNew)
    @RequestMapping(value = "ajax/convertingredient", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject convertIngredient(@RequestParam("id")Integer id,
                                        @RequestParam("costCardQuantity")BigDecimal costCardQuantity,
                                        @RequestParam("type")Integer type){
        JSONObject jsonObject = new JSONObject();
        if (Assert.isNull(costCardQuantity)
                || Assert.isNull(id)
                || Assert.isNull(type)){
            sendMsgAndCode(1,"添加失败");
        }
        try {
            // 是否启用四舍五入
            int roundingMode = Integer.parseInt(constantService.queryValueByKey(ConstantEnum.RoundingMode.getKey()));

            Ingredient ingredient = ingredientService.queryById(id);
            BigDecimal storageQuantity = new BigDecimal("0.00");
            if (roundingMode == 1) {
                storageQuantity = storageQuantity.add(costCardQuantity.divide(ingredient.getStorageToCostCardRatio(), 2, BigDecimal.ROUND_HALF_EVEN));
            }
            if (roundingMode == 1) {
                storageQuantity = storageQuantity.add(costCardQuantity.divide(ingredient.getStorageToCostCardRatio(), 2, BigDecimal.ROUND_DOWN));
            }
            jsonObject.put("storageQuantity", storageQuantity);
            jsonObject.put("storageUnitName", ingredient.getStorageUnitName());
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
    }

    /**
     * 条件导出单据
     * @param startTime
     * @param endTime
     * @param createdPartyId
     * @param depotId
     * @param handlerPartyId
     * @return
     */
    @Module(ModuleEnums.AdminStorageReportExport)
    @RequestMapping(value = "exportbycd", method = RequestMethod.GET)
    public void toReportExport(@RequestParam("createdPartyId")Integer createdPartyId,
                                 @RequestParam("handlerPartyId")Integer handlerPartyId,
                                 @RequestParam("auditPartyId")Integer auditPartyId,
                                 @RequestParam("startTime")Date startTime,
                                 @RequestParam("endTime")Date endTime,
                                 @RequestParam("isAudited") Integer isAudited,
                                 @RequestParam("isSettlemented") Integer isSettlemented,
                                 @RequestParam("depotId") Integer depotId){
        try{
            List<Integer> depots = new ArrayList<Integer>();
            System.out.println(depotId);
            StorageReport report = new StorageReport();
            report.setCreatedPartyId(createdPartyId);
            report.setHandlerPartyId(handlerPartyId);
            report.setIsAudited(isAudited);
            report.setIsSettlemented(isSettlemented);
            report.setAuditPartyId(auditPartyId);
            storageReportService.exportToExcel(report, startTime, endTime, depots, handlerPartyId, createdPartyId, getResponse());
            sendErrMsg("导出成功");
        }catch (Exception e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }

    }

    /**
     * 把所有单据导出
     * @return
     */
    @Module(ModuleEnums.AdminStorageReportExport)
    @RequestMapping(value = "exportall", method = RequestMethod.GET)
    public void toReportExportAll(){
        try{
            storageReportService.exportToExcelAll(getResponse());
            sendErrMsg("导出成功");
        }catch(Exception e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
    }

    /**
     * 修改审核状态
     * @param id
     * @param isAudited
     * @param httpSession
     * @return
     */
    @Module(ModuleEnums.AdminStorageReportUpdate)
    @RequestMapping(value = "ajax/update/isaudited",method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject updateIsAudited(@RequestParam("id")Integer id,
                                      @RequestParam("isAudited")Integer isAudited,
                                      HttpSession httpSession){
        try{
            // 是否启用四舍五入
            int roundingMode = Integer.parseInt(constantService.queryValueByKey(ConstantEnum.RoundingMode.getKey()));

            StorageReport storageReport = storageReportService.queryById(id);
            if (Assert.isNull(storageReport)){
                throw SSException.get(EmenuException.ReportIsNotExist);
            }
            if (Assert.isNull(isAudited)
                    || Assert.lessZero(isAudited)){
                throw SSException.get(EmenuException.IsAuditedIllegal);
            }
            storageReport.setIsAudited(isAudited);
            SecurityUser securityUser = securityUserService.queryByLoginName((String)httpSession.getAttribute("userName"));
            if (Assert.isNull(storageReport)){
                throw SSException.get(PartyException.UserNotExist);
            }
            // 修改库存中物品进货总数量和总金额
            if (StorageReportIsAuditedEnum.Passed.getId().equals(isAudited)) {
                if (storageReport.getType() == StorageReportTypeEnum.StockInReport.getId()) {
                    List<StorageReportItem> storageReportItemList = storageReportItemService.listByReportId(storageReport.getId());
                    Assert.isNotNull(storageReportItemList, EmenuException.StorageItemIsNotNull);
                    for (StorageReportItem reportItem : storageReportItemList) {
                        StorageItem storageItem = storageItemService.queryById(reportItem.getItemId());
                        Assert.isNotNull(storageItem,EmenuException.StorageItemIsNotNull);
                        // 设置最新入库价格总入库数量和金额
                        storageItem.setTotalStockInQuantity(storageItem.getTotalStockInQuantity().add(reportItem.getQuantity()));
                        storageItem.setTotalStockInMoney(storageItem.getTotalStockInMoney().add(reportItem.getCount()));
                        storageItem.setLastStockInPrice(reportItem.getPrice());
                        // 如果是入库单同时修改原配料缓存
                        BigDecimal ingredientCacheQuntity = storageSettlementService.queryCacheForDish(storageItem.getIngredientId());
                        if(ingredientCacheQuntity == null){
                            ingredientCacheQuntity = new BigDecimal("0.00");
                        }
                        if (Assert.isNull(ingredientCacheQuntity)){
                            ingredientCacheQuntity = reportItem.getQuantity().multiply(storageItem.getOrderToStorageRatio()).multiply(storageItem.getStorageToCostCardRatio());
                            storageSettlementService.updateSettlementCache(storageItem.getIngredientId(),ingredientCacheQuntity);
                        } else {
                            ingredientCacheQuntity = ingredientCacheQuntity.add(reportItem.getQuantity().multiply(storageItem.getOrderToStorageRatio()).multiply(storageItem.getStorageToCostCardRatio()));
                            storageSettlementService.updateSettlementCache(storageItem.getIngredientId(),ingredientCacheQuntity);
                        }
                        // 设置原配料入库总金额、入库总数量、均价
                        Ingredient ingredient1 = ingredientService.queryById(storageItem.getIngredientId());
                        ingredient1.setTotalQuantity(ingredient1.getTotalQuantity().add(ingredientCacheQuntity));
                        ingredient1.setTotalMoney(ingredient1.getTotalMoney().add(reportItem.getCount()));
                        if (roundingMode == 1) {
                            ingredient1.setAveragePrice(ingredient1.getTotalMoney().divide(ingredient1.getTotalQuantity(), 2, BigDecimal.ROUND_HALF_EVEN));
                        }
                        if (roundingMode == 0) {
                            ingredient1.setAveragePrice(ingredient1.getTotalMoney().divide(ingredient1.getTotalQuantity(), 2, BigDecimal.ROUND_DOWN));
                        }
                        ingredientService.updateIngredient(ingredient1, IngredientStatusEnums.CanUpdate.getId());
                        storageItemService.updateById(storageItem);
                    }
                }
                if (storageReport.getType() == StorageReportTypeEnum.StockOutReport.getId()){
                    // 其他单据原配料详情
                    List<StorageReportIngredient> storageReportIngredientList = storageReportIngredientService.listByReportId(storageReport.getId());
                    for (StorageReportIngredient storageReportIngredient : storageReportIngredientList){
                        Ingredient ingredient = ingredientService.queryById(storageReportIngredient.getIngredientId());
                        // 如果是入库单同时修改原配料缓存
                        BigDecimal ingredientCacheQuntity = storageSettlementService.queryCache(storageReportIngredient.getIngredientId());
                        if (Assert.isNull(ingredientCacheQuntity)){
                            ingredientCacheQuntity = storageReportIngredient.getQuantity();
                            storageSettlementService.updateSettlementCache(ingredient.getId(),ingredientCacheQuntity);
                        } else {
                            ingredientCacheQuntity = ingredientCacheQuntity.subtract( storageReportIngredient.getQuantity());
                            storageSettlementService.updateSettlementCache(ingredient.getId(),ingredientCacheQuntity);
                        }
                    }

                }
            }
            storageReport.setAuditPartyId(securityUser.getPartyId());
            storageReportService.updateById(storageReport);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
