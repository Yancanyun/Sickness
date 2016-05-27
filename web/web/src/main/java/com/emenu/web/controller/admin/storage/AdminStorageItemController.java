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
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
     * ajax获取列表
     *
     * @param pageNo
     * @param pageSize
     * @param searchDto
     * @return
     */
    @Module(value = ModuleEnums.AdminStorageItem, extModule = ModuleEnums.AdminStorageItemList)
    @RequestMapping(value = "ajax/list/{pageNo}", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(@PathVariable("pageNo") Integer pageNo,
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
            BigDecimal maxStorageQuantity = storageItem.getMaxStorageQuantity().divide(storageItem.getStorageToCostCardRatio());
            String maxStorageQuantityStr = maxStorageQuantity.toString() + storageItem.getStorageUnitName();
            jsonObject.put("maxStorageQuantityStr", maxStorageQuantityStr);
            // 最小库存
            BigDecimal minStorageQuantity = storageItem.getMinStorageQuantity().divide(storageItem.getStorageToCostCardRatio());
            String minStorageQuantityStr = minStorageQuantity.toString() + storageItem.getStorageUnitName();
            jsonObject.put("minStorageQuantityStr", minStorageQuantityStr);
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
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String toNew(Model model) {
        try {
            List<Supplier> supplierList = supplierService.listAll();
            List<Tag> tagList = storageTagService.listAllSmallTag();
            List<Unit> unitList = unitService.listAll();

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
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }

        return "admin/storage/item/new_home";
    }

    /**
     * 添加提交
     *
     * @param storageItem
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminStorageItem, extModule = ModuleEnums.AdminStorageItemNew)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String newStorageItem(StorageItem storageItem,
                                 Model model,
                                 RedirectAttributes redirectAttributes) {
        try {
            storageItemService.newStorageItem(storageItem);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return toNew(model);
        }

        redirectAttributes.addFlashAttribute("msg", NEW_SUCCESS_MSG);
        String redirectUrl = "/" + URLConstants.ADMIN_STORAGE_ITEM_URL + "/list";
        return "redirect:" + redirectUrl;
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
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }

        return "admin/storage/item/update_home";
    }

    /**
     * 修改提交
     *
     * @param storageItem
     * @param redirectAttributes
     * @return
     */
    @Module(value = ModuleEnums.AdminStorageItem, extModule= ModuleEnums.AdminStorageItemUpdate)
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public String updateStorageItem(StorageItem storageItem,
                                    RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("code", 0);
        redirectAttributes.addFlashAttribute("msg", UPDATE_SUCCESS_MSG);

        try {
            storageItemService.updateStorageItem(storageItem);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            redirectAttributes.addFlashAttribute("code", 1);
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
        }

        String redirectUrl = "/" + URLConstants.ADMIN_STORAGE_ITEM_URL + "/update/" + storageItem.getId();
        return "redirect:" + redirectUrl;
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
}
