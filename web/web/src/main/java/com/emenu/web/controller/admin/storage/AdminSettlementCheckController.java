package com.emenu.web.controller.admin.storage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.storage.StorageCheckDto;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.party.group.supplier.Supplier;
import com.emenu.common.entity.storage.StorageDepot;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
     * 去库存盘点列表页
     * @return
     */
    @Module(ModuleEnums.AdminStorageSettlementCheckList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(Model model,
                         @RequestParam(value = "eMsg", required = false) String eMsg){
        try {
            //获取供货商列表
            List<Supplier> supplierList = supplierService.listAll();
            //获取存放点列表
            List<StorageDepot> storageDepotList = storageDepotService.listAll();
            //获取分类列表
            List<Tag> tagList = storageTagService.listAllSmallTag();
            model.addAttribute("supplierList", supplierList);
            model.addAttribute("storageDepotList", storageDepotList);
            model.addAttribute("tagList", tagList);
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
                               @RequestParam(value = "startDate", required = false) Date startDate,
                               @RequestParam(value = "endDate", required = false) Date endDate,
                               @RequestParam(value = "supplierId", required = false) Integer supplierId,
                               @RequestParam(value = "itemName", required = false) String keyword,
                               @RequestParam(value = "depotIds", required = false) List<Integer> depotIds,
                               @RequestParam(value = "tagIds", required = false) List<Integer> tagIds) {
        List<StorageCheckDto> storageCheckDtoList = Collections.emptyList();
        try {
            storageCheckDtoList = storageSettlementService.listSettlementCheck(startDate,endDate,supplierId,depotIds,tagIds,keyword,curPage,pageSize);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        JSONArray jsonArray = new JSONArray();
        for (StorageCheckDto storageCheckDto : storageCheckDtoList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tagName", storageCheckDto.getTagName());
            jsonObject.put("itemNumber", storageCheckDto.getItemNumber());
            jsonObject.put("itemName", storageCheckDto.getItemName());
            jsonObject.put("lastStockInPrice", storageCheckDto.getLastStockInPrice());
            jsonObject.put("orderUnitName", storageCheckDto.getOrderUnitName());
            jsonObject.put("storageUnitName", storageCheckDto.getStorageUnitName());
            jsonObject.put("beginQuantity", storageCheckDto.getBeginQuantity());
            jsonObject.put("beginMoney", storageCheckDto.getBeginMoney());
            jsonObject.put("stockInQuantity", storageCheckDto.getStockInQuantity());
            jsonObject.put("stockInMoney", storageCheckDto.getStockInMoney());
            jsonObject.put("stockOutQuantity", storageCheckDto.getStockOutQuantity());
            jsonObject.put("stockOutMoney", storageCheckDto.getStockOutMoney());
            jsonObject.put("IncomeLossQuantity", storageCheckDto.getIncomeLossQuantity());
            jsonObject.put("IncomeLossMoney", storageCheckDto.getIncomeLossMoney());
            jsonObject.put("totalQuantity", storageCheckDto.getTotalQuantity());
            jsonObject.put("totalAveragePrice", storageCheckDto.getTotalAveragePrice());
            jsonObject.put("totalMoney", storageCheckDto.getTotalMoney());
            jsonObject.put("maxStorageQuantity", storageCheckDto.getMaxStorageQuantity());
            jsonObject.put("minStorageQuantity", storageCheckDto.getMinStorageQuantity());
            jsonArray.add(jsonObject);
        }

        int dataCount = 0;
        try {
            dataCount = storageSettlementService.countSettlementCheck(startDate,endDate,supplierId,depotIds,tagIds,keyword);
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
}
