package com.emenu.web.controller.admin.storage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.storage.StorageItemDto;
import com.emenu.common.dto.storage.StorageSupplierDto;
import com.emenu.common.entity.party.group.supplier.Supplier;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

/**
 * AdminSettlementSupplierController
 * 结算中心
 * @author dujuan
 * @date 2015/11/27
 */
@Controller
@Module(ModuleEnums.AdminStorageSettlementSupplier)
@RequestMapping(URLConstants.ADMIN_STORAGE_SETTLEMENT_SUPPLIER_URL)
public class AdminSettlementSupplierController extends AbstractController{

    /**
     * 去结算中心列表页
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminStorageSettlementSupplierList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String tolistSettlementSupplier(Model model,
                                           @RequestParam(value = "eMsg", required = false) String eMsg){
        try {
            //获取供货商
            List<Supplier> supplierList = supplierService.listAll();
            List<StorageSupplierDto> storageSupplierDtoList = storageSettlementService.listSettlementSupplier(0,null,null);
            if (Assert.isNotNull(storageSupplierDtoList)
                    && storageSupplierDtoList.size() > 0){
                Iterator<StorageSupplierDto> iter = storageSupplierDtoList.iterator();
                while(iter.hasNext()){
                    StorageSupplierDto storageSupplierDto = iter.next();
                    if (Assert.isNull(storageSupplierDto.getStorageItemDtoList())
                            || Assert.lessOrEqualZero(storageSupplierDto.getStorageItemDtoList().size())
                            || storageSupplierDto.getTotalMoney().equals(BigDecimal.ZERO)){
                        iter.remove();
                    }
                }
            }
            model.addAttribute("supplierDtoList", storageSupplierDtoList);
            model.addAttribute("supplierList", supplierList);
            model.addAttribute("eMsg", eMsg);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return "admin/storage/settlement/supplier/list_home";
    }

    /**
     * 搜索请求Ajax数据
     * @return
     */
    @Module(ModuleEnums.AdminStorageSettlementSupplierList)
    @RequestMapping(value = "ajax/list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxList(@RequestParam(value = "supplierId", required = false) Integer supplierId,
                               @RequestParam(value = "startDate", required = false) Date startDate,
                               @RequestParam(value = "endDate", required = false) Date endDate) {
        List<StorageSupplierDto> storageSupplierDtoList = Collections.emptyList();
        try {
            storageSupplierDtoList = storageSettlementService.listSettlementSupplier(supplierId,startDate,endDate);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONArray jsonArray = new JSONArray();
        if (Assert.isNotNull(storageSupplierDtoList)
                && storageSupplierDtoList.size() > 0){
            Iterator<StorageSupplierDto> iter = storageSupplierDtoList.iterator();
            while(iter.hasNext()){
                StorageSupplierDto storageSupplierDto = iter.next();
                if (Assert.isNull(storageSupplierDto.getStorageItemDtoList())
                        || Assert.lessOrEqualZero(storageSupplierDto.getStorageItemDtoList().size())
                        || storageSupplierDto.getTotalMoney().equals(BigDecimal.ZERO)){
                    iter.remove();
                }
            }
        }
        if (Assert.isNotNull(storageSupplierDtoList)
                && storageSupplierDtoList.size() > 0) {
            for (StorageSupplierDto storageSupplierDto : storageSupplierDtoList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("supplierName", storageSupplierDto.getSupplierName());
                jsonObject.put("totalMoney", storageSupplierDto.getTotalMoney());
                List<StorageItemDto> storageItemDtoList = storageSupplierDto.getStorageItemDtoList();
                JSONArray childJsonArray = new JSONArray();
                for (StorageItemDto storageItemDto : storageItemDtoList) {
                    JSONObject childJsonObject = new JSONObject();
                    childJsonObject.put("itemName", storageItemDto.getItemName());
                    childJsonObject.put("itemQuantity", storageItemDto.getItemQuantity());
                    childJsonObject.put("itemMoney", storageItemDto.getItemMoney());
                    childJsonObject.put("handlerName", storageItemDto.getHandlerName());
                    childJsonObject.put("createdName", storageItemDto.getCreatedName());
                    childJsonArray.add(childJsonObject);
                }
                jsonObject.put("items", childJsonArray);

                jsonArray.add(jsonObject);
            }
        }
        return sendJsonArray(jsonArray);
    }

    /**
     * 导出EXCEL
     * @return
     */
    @Module(ModuleEnums.AdminStorageSettlementCheckExport)
    @RequestMapping(value = "export", method = RequestMethod.GET)
    public String toExport(@RequestParam(value = "supplierId", required = false) Integer supplierId,
                           @RequestParam(value = "startDate", required = false) Date startDate,
                           @RequestParam(value = "endDate", required = false) Date endDate)throws SSException{
        try{
            storageSettlementService.exportSettlementSupplierToExcel(supplierId,startDate,endDate,getResponse());
            sendErrMsg("导出成功");
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return "admin/storage/settlement/supplier/list_home";
    }
}
