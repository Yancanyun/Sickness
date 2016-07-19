package com.emenu.web.controller.admin.storage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.storage.ItemAndIngredientSearchDto;
import com.emenu.common.dto.storage.StorageItemSearchDto;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.dish.Unit;
import com.emenu.common.entity.party.group.supplier.Supplier;
import com.emenu.common.entity.storage.Ingredient;
import com.emenu.common.entity.storage.StorageItem;
import com.emenu.common.enums.dish.UnitEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 库存物品Controller
 *
 * @author: zhangteng
 * @time: 2015/11/17 19:44
 **/
@Module(ModuleEnums.AdminStorage)
@Controller
@RequestMapping(value = URLConstants.ADMIN_STORAGE_ITEM_URL)
public class AdminStorageItemController extends AbstractController {

    /**
     * 去列表
     *
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminStorageItem, extModule = ModuleEnums.AdminStorageItemList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(Model model) {
        try {
            List<Supplier> supplierList = supplierService.listAll();
            List<Tag> tagList = storageTagService.listAllSmallTag();
            List<Ingredient> ingredientList = ingredientService.listAll();
            model.addAttribute("ingredientList",ingredientList);
            model.addAttribute("supplierList", supplierList);
            model.addAttribute("tagList", tagList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/storage/item/list_home";
    }

    /**
     * 去详情页
     * @param id
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminStorageItem, extModule = ModuleEnums.AdminStorageItemList)
    @RequestMapping(value = "todetails/{id}", method = RequestMethod.GET)
    public String toDetails(@PathVariable("id")Integer id, Model model) {
        try {
            StorageItem storageItem = storageItemService.queryById(id);
            if (Assert.isNotNull(storageItem)){
                storageItemService.setQuantityFormat(storageItem);
            }
            model.addAttribute("storageItem",storageItem);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/storage/item/details_home";
    }

    /**
     * ajax获取列表
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Module(value = ModuleEnums.AdminStorageItem, extModule = ModuleEnums.AdminStorageItemList)
    @RequestMapping(value = "ajax/list/{pageNo}", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(@PathVariable("pageNo") Integer pageNo,
                         @RequestParam("pageSize") Integer pageSize,
                         ItemAndIngredientSearchDto searchDto) {
        pageSize = (pageSize == null || pageSize<=0)   ? DEFAULT_PAGE_SIZE : pageSize;
//        ItemAndIngredientSearchDto searchDto = new ItemAndIngredientSearchDto();
        searchDto.setPageNo(pageNo);
        searchDto.setPageSize(pageSize);
        List<StorageItem> list = Collections.emptyList();
        try {
            list = storageItemService.listBySearchDto(searchDto);

        } catch (SSException e) {
        	LogClerk.errLog.error(e);
        	return sendErrMsgAndErrCode(e);
        }
        JSONArray jsonArray = new JSONArray();
        int offset = 0;
        if (Assert.isNotNull(pageNo)){
            pageNo = pageNo <= 0 ? 0 : pageNo - 1;
            offset = pageNo * pageSize;
        }
        int i = 0;
        for (StorageItem storageItem : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", storageItem.getId());
            jsonObject.put("sequenceNumber",offset+(++i));
            jsonObject.put("name", storageItem.getName());
            jsonObject.put("itemNumber", storageItem.getItemNumber());
            jsonObject.put("assistantCode", storageItem.getAssistantCode());
            jsonObject.put("ingredientName",storageItem.getIngredientName());
            jsonObject.put("tagName", storageItem.getTagName());
            jsonObject.put("supplierName", storageItem.getSupplierName());
            // 各种单位
            jsonObject.put("orderUnitName", storageItem.getOrderUnitName());
            jsonObject.put("orderToStorageRatio", storageItem.getOrderToStorageRatio());
            jsonObject.put("storageUnitName", storageItem.getStorageUnitName());
            jsonObject.put("storageToCostCardRatio", storageItem.getStorageToCostCardRatio());
            jsonObject.put("costCardUnitName", storageItem.getCostCardUnitName());
            jsonObject.put("countUnitName", storageItem.getOrderUnitName());
            // 将数量和单位拼接成string，并将成本卡单位表示的数量转换为库存单位表示

            BigDecimal maxStorageQuantity = storageItem.getMaxStorageQuantity().divide(storageItem.getStorageToCostCardRatio(),2);
            String maxStorageQuantityStr = maxStorageQuantity.toString() + storageItem.getStorageUnitName();
            jsonObject.put("maxStorageQuantityStr", maxStorageQuantityStr);
            // 最小库存
            BigDecimal minStorageQuantity = storageItem.getMinStorageQuantity().divide(storageItem.getStorageToCostCardRatio(),2);
            String minStorageQuantityStr = minStorageQuantity.toString() + storageItem.getStorageUnitName();
            jsonObject.put("minStorageQuantityStr", minStorageQuantityStr);
            jsonObject.put("lastStockInPrice", storageItem.getLastStockInPrice());
            // 总数量
            BigDecimal totalStockInQuantityStr = storageItem.getTotalStockInQuantity().divide(storageItem.getStorageToCostCardRatio(),2);
            String totalQuantityStr = totalStockInQuantityStr.toString() + storageItem.getStorageUnitName();
            jsonObject.put("totalStockInQuantityStr", totalQuantityStr);
            jsonObject.put("totalStockInMoney", storageItem.getTotalStockInMoney());
            jsonObject.put("stockOutType", storageItem.getStockOutTypeStr());

            jsonArray.add(jsonObject);
        }
        int dataCount = 0;
        try {
            dataCount = storageItemService.countBySearchDto(searchDto);
        } catch (SSException e) {
        	LogClerk.errLog.error(e);
        	return sendErrMsgAndErrCode(e);
        }

        return sendJsonArray(jsonArray, dataCount);
    }

    /**
     * 去添加页
     *
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminStorageItem ,extModule = ModuleEnums.AdminStorageItemNew)
    @RequestMapping(value = "tonew", method = RequestMethod.GET)
    public String toNew(Model model) {
        try {
            List<Supplier> supplierList = supplierService.listAll();
            List<Tag> tagList = storageTagService.listAllSmallTag();
            List<Unit> unitList = unitService.listAll();
            List<Ingredient> ingredientList = ingredientService.listAll();
            List<Unit> weightUnit = new ArrayList<Unit>();
            List<Unit> quantityUnit = new ArrayList<Unit>();
            for (Unit unit : unitList) {
                if (UnitEnum.HundredWeight.getId().equals(unit.getType())) {
                    weightUnit.add(unit);
                } else {
                    quantityUnit.add(unit);
                }
            }
            model.addAttribute("weightUnit", weightUnit);
            model.addAttribute("quantityUnit", quantityUnit);
            model.addAttribute("supplierList", supplierList);
            model.addAttribute("tagList", tagList);
            model.addAttribute("ingredientList", ingredientList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }

        return "admin/storage/item/new_home";
    }

    /**
     * 添加提交
     * @param storageItem
     * @return
     */
    @Module(value = ModuleEnums.AdminStorageItem, extModule = ModuleEnums.AdminStorageItemNew)
    @RequestMapping(value = "ajax/new", method = RequestMethod.POST)
    @ResponseBody
    public JSON newStorageItem(StorageItem storageItem) {
        try{
            storageItemService.newStorageItem(storageItem);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 去修改页
     *
     * @param id
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminStorageItem, extModule = ModuleEnums.AdminStorageItemUpdate)
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String toUpdate(@PathVariable("id") Integer id,
                           Model model) {
        try {
            StorageItem storageItem = storageItemService.queryById(id);
            List<Supplier> supplierList = supplierService.listAll();
            List<Tag> tagList = storageTagService.listAllSmallTag();
            List<Unit> unitList = unitService.listAll();
            List<Ingredient> ingredientList = ingredientService.listAll();
            List<Unit> weightUnit = new ArrayList<Unit>();
            List<Unit> quantityUnit = new ArrayList<Unit>();
            int orderUnitType = 1, storageUnitType = 1, costCardUnitType = 1, countOrderType = 1;
            for (Unit unit : unitList) {
                if (unit.getId().equals(storageItem.getOrderUnitId())) {
                    orderUnitType = unit.getType();
                }
                if (unit.getId().equals(storageItem.getStorageUnitId())) {
                    storageUnitType = unit.getType();
                }
                if (unit.getId().equals(storageItem.getCostCardUnitId())) {
                    costCardUnitType = unit.getType();
                }
                if (unit.getId().equals(storageItem.getCountUnitId())) {
                    countOrderType = unit.getType();
                }
                if (UnitEnum.HundredWeight.getId().equals(unit.getType())) {
                    weightUnit.add(unit);
                } else {
                    quantityUnit.add(unit);
                }
            }
            model.addAttribute("storageItem", storageItem);
            model.addAttribute("orderUnitType", orderUnitType);
            model.addAttribute("storageUnitType", storageUnitType);
            model.addAttribute("storageUnitType", costCardUnitType);
            model.addAttribute("storageUnitType", countOrderType);
            model.addAttribute("weightUnit", weightUnit);
            model.addAttribute("quantityUnit", quantityUnit);
            model.addAttribute("supplierList", supplierList);
            model.addAttribute("tagList", tagList);
            model.addAttribute("ingredientList", ingredientList);

        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }

        return "admin/storage/item/update_home";
    }

    /**
     * 修改提交
     * @param storageItem
     * @return
     */
    @Module(value = ModuleEnums.AdminStorageItem, extModule= ModuleEnums.AdminStorageItemUpdate)
    @RequestMapping(value = "ajax/update", method = RequestMethod.PUT)
    @ResponseBody
    public JSON updateStorageItem(StorageItem storageItem) {
        try{
            System.out.println("xiaozl");
            storageItemService.updateStorageItem(storageItem);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 返回成卡单位
     * @param ingredientId
     * @return
     */
    @Module(value = ModuleEnums.AdminStorageItem, extModule= ModuleEnums.AdminStorageItemNew)
    @RequestMapping(value = "ajax/getcostcardunit", method = RequestMethod.GET)
    @ResponseBody
    public JSON unit(@RequestParam("ingredientId")Integer ingredientId) {
        try{
            Ingredient ingredient = ingredientService.queryById(ingredientId);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("costCardUnitName",ingredient.getCostCardUnitName());
            return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 编辑时数量转换
     * @param id
     * @param storageUnitId
     * @param storageToCostCardRatio
     * @return
     */
    @Module(value = ModuleEnums.AdminStorageItem,extModule = ModuleEnums.AdminStorageItemUpdate)
    @RequestMapping(value = "ajax/convert/quantity",method = RequestMethod.GET)
    @ResponseBody
    public JSON convertQuantity(@RequestParam("id")Integer id ,
                                      @RequestParam("storageUnitId")Integer storageUnitId ,
                                      @RequestParam("storageToCostCardRatio")BigDecimal storageToCostCardRatio){
        try {
            StorageItem storageItem = storageItemService.queryById(id);

            JSONObject jsonObject = new JSONObject();
            if(storageToCostCardRatio.compareTo(BigDecimal.ZERO)==0){
                return sendJsonObject(AJAX_FAILURE_CODE);
            }else {
                jsonObject.put("totalQuantity",storageItem.getTotalStockInQuantity().divide(storageToCostCardRatio));
                jsonObject.put("maxStorageQuantity",storageItem.getMaxStorageQuantity().divide(storageToCostCardRatio));
                jsonObject.put("minStorageQuantity",storageItem.getMinStorageQuantity().divide(storageToCostCardRatio));
            }
            return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }



    /**
     * ajax删除
     *
     * @param id
     * @return
     */
    @Module(value = ModuleEnums.AdminStorageItem, extModule = ModuleEnums.AdminStorageItemDelete)
    @RequestMapping(value = "ajax/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSON ajaxDel(@PathVariable("id") Integer id) {
        try {
            storageItemService.delById(id);
        } catch (SSException e) {
        	LogClerk.errLog.error(e);
        	return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * 去换算比例列表页
     *
     * @return
     */
    @Module(value = ModuleEnums.AdminStorageItem, extModule = ModuleEnums.AdminStorageItemUnitConversionList)
    @RequestMapping(value = "unit/conversion/list", method = RequestMethod.GET)
    public String toConversionList(Model model) {
        try {
            List<Unit> unitList = unitService.listAll();

            model.addAttribute("unitList", unitList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/storage/item/unit_conversion_list_home";
    }

    /**
     * ajax获取换算比例列表
     *
     * @param pageNo
     * @param pageSize
     * @param searchDto
     * @return
     */
    @Module(value = ModuleEnums.AdminStorageItemUnitConversion, extModule = ModuleEnums.AdminStorageItemUnitConversionList)
    @RequestMapping(value = "unit/conversion/ajax/list/{pageNo}", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxConversionList(@PathVariable("pageNo") Integer pageNo,
                                   @RequestParam("pageSize") Integer pageSize,
                                   ItemAndIngredientSearchDto searchDto) {
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        searchDto.setPageNo(pageNo);
        searchDto.setPageSize(pageSize);
        List<StorageItem> list = Collections.emptyList();
        try {
            list = storageItemService.listBySearchDto(searchDto);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        JSONArray jsonArray = new JSONArray();
        for (StorageItem storageItem : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", storageItem.getId());
            jsonObject.put("name", storageItem.getName());
            jsonObject.put("orderUnitId", storageItem.getOrderUnitId());
            jsonObject.put("orderUnitName", storageItem.getOrderUnitName());
            jsonObject.put("orderToStorageRatio", storageItem.getOrderToStorageRatio());
            jsonObject.put("storageUnitId", storageItem.getStorageUnitId());
            jsonObject.put("storageUnitName", storageItem.getStorageUnitName());
            jsonObject.put("storageToCostCardRatio", storageItem.getStorageToCostCardRatio());
            jsonObject.put("costCardUnitId", storageItem.getCostCardUnitId());
            jsonObject.put("costCardUnitName", storageItem.getCostCardUnitName());
            jsonObject.put("countUnitId", storageItem.getCountUnitId());
            jsonObject.put("countUnitName", storageItem.getCountUnitName());

            jsonArray.add(jsonObject);
        }

        int dataCount = 0;
        try {
            dataCount = storageItemService.countBySearchDto(searchDto);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        return sendJsonArray(jsonArray, dataCount);
    }

    @Module(value = ModuleEnums.AdminStorageItemUnitConversion, extModule = ModuleEnums.AdminStorageItemUnitConversionUpdate)
    @RequestMapping(value = "unit/conversion/ajax", method = RequestMethod.PUT)
    @ResponseBody
    public JSON updateUnitConversion(StorageItem storageItem) {
        try {
            storageItemService.updateUnit(storageItem);
        } catch (SSException e) {
        	LogClerk.errLog.error(e);
        	return sendErrMsgAndErrCode(e);
        }

        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * 导出excel
     * @param searchDto
     * @param response
     */
    @Module(value = ModuleEnums.AdminStorageIngredientList,extModule = ModuleEnums.AdminStorageIngredientList)
    @RequestMapping(value = "export",method = RequestMethod.GET)
    public void exportExcel(ItemAndIngredientSearchDto searchDto, HttpServletResponse response){
        try {
            storageItemService.exportExcelBySearchDto(searchDto,response);
            sendErrMsg("导出成功");
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }

    }
}
