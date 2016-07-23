package com.emenu.web.controller.admin.storage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.storage.StorageCheckDto;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.party.group.supplier.Supplier;
import com.emenu.common.entity.storage.StorageDepot;
import com.emenu.common.entity.storage.StorageItem;
import com.emenu.common.entity.storage.StorageReport;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.DateUtils;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * AdminSettlementCheckController
 * 库存盘点
 * @author dujuan
 * @date 2015/11/29
 */
@Controller
@Module(ModuleEnums.AdminStorageSettlementCheck)
@RequestMapping(URLConstants.ADMIN_STORAGE_SETTLEMENT_CHECK_URL)
public class AdminSettlementCheckController extends AbstractController{

    /**
     * 新
     * 去库存盘点列表页
     * @return
     */
    @Module(ModuleEnums.AdminStorageSettlementCheckList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(Model model,
                         @RequestParam(value = "eMsg", required = false) String eMsg){
        try {
            //获取分类列表
            List<Tag> tagList = storageTagService.listAllSmallTag();
            model.addAttribute("tagList", tagList);
            model.addAttribute("currentMonthFirstDay", DateUtils.getCurrentMonthFirstDay());
            model.addAttribute("currentMonthNowDay", DateUtils.formatDate(new Date(),new SimpleDateFormat("yyyy-MM-dd")));
            model.addAttribute("eMsg", eMsg);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return "admin/storage/settlement/check/list_home";
    }

    /**
     * Ajax获取分页数据
     * @param curPage
     * @param pageSize
     * @return
     */
    @Module(ModuleEnums.AdminStorageSettlementCheckList)
    @RequestMapping(value = "ajax/list/{curPage}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxList(@PathVariable("curPage") Integer curPage,
                               @RequestParam("pageSize") Integer pageSize,
                               @RequestParam(value = "startTime", required = false) Date startTime,
                               @RequestParam(value = "endTime", required = false) Date endTime,
                               @RequestParam(value = "keyWord", required = false) String keyword,
                               @RequestParam(value = "tagIds", required = false) List<Integer> tagIds) {
        List<StorageCheckDto> storageCheckDtoList = Collections.emptyList();
        try {
            storageCheckDtoList = storageSettlementService.listSettlementCheck(startTime,endTime,tagIds,keyword,curPage,pageSize);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        int offset = 0;
        if (Assert.isNotNull(curPage)){
            curPage = curPage <= 0 ? 0 : curPage - 1;
            offset = curPage * pageSize;
        }
        int i = 0;
        JSONArray jsonArray = new JSONArray();
        for (StorageCheckDto storageCheckDto : storageCheckDtoList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sequenceNumber",offset+(++i));
            jsonObject.put("ingredientName",storageCheckDto.getIngredientName());
            jsonObject.put("ingredientNumber",storageCheckDto.getIngredientNumber());
            jsonObject.put("tagName", storageCheckDto.getTagName());
            jsonObject.put("orderUnitName", storageCheckDto.getOrderUnitName());
            jsonObject.put("storageUnitName", storageCheckDto.getStorageUnitName());
            jsonObject.put("costCardUnitName",storageCheckDto.getCostCardUnitName());

            String beginQuantityStr = storageCheckDto.getBeginQuantity().setScale(2, BigDecimal.ROUND_HALF_UP).toString()+storageCheckDto.getCostCardUnitName();
            jsonObject.put("beginQuantityStr", beginQuantityStr);

            String stockInQuantityStr = storageCheckDto.getStockInQuantity().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + storageCheckDto.getCostCardUnitName();
            jsonObject.put("stockInQuantityStr", stockInQuantityStr);

            String stockOutQuantityStr = storageCheckDto.getStockOutQuantity().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + storageCheckDto.getCostCardUnitName();
            jsonObject.put("stockOutQuantityStr", stockOutQuantityStr);

            String incomeLossQuantityStr = storageCheckDto.getIncomeLossQuantity().toString() + storageCheckDto.getCostCardUnitName();
            jsonObject.put("incomeLossQuantityStr", incomeLossQuantityStr);

            String totalQuantityStr = storageCheckDto.getTotalQuantity().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + storageCheckDto.getCostCardUnitName();
            jsonObject.put("totalQuantityStr", totalQuantityStr);

            String maxStorageQuantityStr = storageCheckDto.getMaxStorageQuantity().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + storageCheckDto.getCostCardUnitName();
            jsonObject.put("maxStorageQuantityStr", maxStorageQuantityStr);

            String minStorageQuantityStr = storageCheckDto.getMinStorageQuantity().setScale(2, BigDecimal.ROUND_HALF_UP).toString() + storageCheckDto.getCostCardUnitName();
            jsonObject.put("minStorageQuantityStr", minStorageQuantityStr);
            jsonArray.add(jsonObject);
        }

        int dataCount = 0;
        try {
            dataCount = storageSettlementService.countSettlementCheck(tagIds,keyword);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray, dataCount);
    }

    /**
     * 导出Excel
     * @return
     */
    @Module(ModuleEnums.AdminStorageSettlementCheckExport)
    @RequestMapping(value = "export", method = RequestMethod.GET)
    public String toCheckExport(@RequestParam(value = "startDate", required = false) Date startDate,
                              @RequestParam(value = "endDate", required = false) Date endDate,
                              @RequestParam(value = "supplierId", required = false) Integer supplierId,
                              @RequestParam(value = "itemName", required = false) String keyword,
                              @RequestParam(value = "depotIds", required = false) List<Integer> depotIds,
                              @RequestParam(value = "tagIds", required = false) List<Integer> tagIds){
        try{
            storageSettlementService.exportSettlementCheckToExcel(startDate,endDate,supplierId,depotIds,tagIds,keyword,getResponse());
            sendErrMsg("导出成功");
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return "admin/storage/settlement/check/list_home";
    }

    @Module(ModuleEnums.AdminStorageSettlementCheck)
    @RequestMapping(value = "ajax/settlement/check",method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject checkSettlement(){
        try {
            storageSettlementService.newSettlement();
            return sendMsgAndCode(AJAX_SUCCESS_CODE,"盘点成功");
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendMsgAndCode(AJAX_FAILURE_CODE,"盘点失败");
        }
    }
}
