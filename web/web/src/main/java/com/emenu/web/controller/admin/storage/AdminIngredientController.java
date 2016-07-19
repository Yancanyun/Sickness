package com.emenu.web.controller.admin.storage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.storage.ItemAndIngredientSearchDto;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.dish.Unit;
import com.emenu.common.entity.storage.Ingredient;
import com.emenu.common.entity.storage.StorageItem;
import com.emenu.common.enums.ExcelExportTemplateEnums;
import com.emenu.common.enums.dish.UnitEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.enums.storage.IngredientStatusEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.velocity.util.introspection.Uberspect;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * IngredientController
 * 原配料管理
 * @author xiaozl
 * @date: 2016/5/20
 */
@Module(ModuleEnums.AdminStorage)
@Controller
@RequestMapping(value = URLConstants.ADMIN_STORAGE_INGREDIENT_URL)
public class AdminIngredientController extends AbstractController{

    /**
     * 去原配料管理列表
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminStorageIngredient, extModule = ModuleEnums.AdminStorageIngredientList)
    @RequestMapping(value = {"","list"},method = RequestMethod.GET)
    public String toList(Model model){
        try {
            List<Tag> tagList = storageTagService.listAllSmallTag();
            model.addAttribute("tagList",tagList);
        } catch (SSException e) {
                 LogClerk.errLog.error(e);
                 sendErrMsg(e.getMessage());
                 return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/storage/ingredient/list_home";
    }

    /**
     * ajax获取列表
     * @param pageNo
     * @param pageSize

     * @return
     */
    @Module(value = ModuleEnums.AdminStorageIngredient, extModule = ModuleEnums.AdminStorageIngredientList)
    @RequestMapping(value = "ajax/list/{pageNo}",method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(@PathVariable("pageNo")Integer pageNo,
                         @RequestParam("pageSize") Integer pageSize,
                         ItemAndIngredientSearchDto searchDto){
        pageSize = (pageSize == null || pageSize<=0) ? DEFAULT_PAGE_SIZE : pageSize;
        searchDto.setPageNo(pageNo);
        searchDto.setPageSize(pageSize);
        List<Ingredient> ingredientList = Collections.emptyList();
        JSONArray jsonArray = new JSONArray();
        try {
            ingredientList = ingredientService.listBySearchDto(searchDto);
            int offset = 0;
            if (Assert.isNotNull(pageNo)){
                pageNo = pageNo <= 0 ? 0 : pageNo - 1;
                offset = pageNo * pageSize;
            }
            int i = 0;
            for (Ingredient ingredient : ingredientList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", ingredient.getId());
                jsonObject.put("sequenceNumber",offset+(++i));
                jsonObject.put("tagName", ingredient.getTagName());
                jsonObject.put("name", ingredient.getName());
                jsonObject.put("ingredientNumber", ingredient.getIngredientNumber());
                jsonObject.put("assistantCode", ingredient.getAssistantCode());
                jsonObject.put("orderUnitName", ingredient.getOrderUnitName());
                jsonObject.put("orderToStorageRatio", ingredient.getOrderToStorageRatio());
                jsonObject.put("storageUnitName", ingredient.getStorageUnitName());
                jsonObject.put("storageToCostCardRatio", ingredient.getStorageToCostCardRatio());
                jsonObject.put("costCardUnitName", ingredient.getCostCardUnitName());
                // 将数量和单位拼接成string，并将成本卡单位表示的数量转换为库存单位表示
                BigDecimal maxStorageQuantity = ingredient.getMaxStorageQuantity().divide(ingredient.getStorageToCostCardRatio(),2);
                String maxStorageQuantityStr = maxStorageQuantity.toString() + unitService.queryById(ingredient.getStorageUnitId()).getName();
                jsonObject.put("maxStorageQuantityStr", maxStorageQuantityStr);
                // 最小库存
                BigDecimal minStorageQuantity = ingredient.getMinStorageQuantity().divide(ingredient.getStorageToCostCardRatio(),2);
                String minStorageQuantityStr = minStorageQuantity.toString() + unitService.queryById(ingredient.getStorageUnitId()).getName();
                jsonObject.put("minStorageQuantityStr", minStorageQuantityStr);
                // 实际库存
                BigDecimal realQuantity = ingredient.getRealQuantity().divide(ingredient.getStorageToCostCardRatio(),2);
                String realQuantityStr = realQuantity.toString() + unitService.queryById(ingredient.getStorageUnitId()).getName();
                jsonObject.put("realQuantityStr", realQuantityStr);
                jsonObject.put("averagePrice", ingredient.getAveragePrice().toString());
                jsonObject.put("realMoney", ingredient.getRealMoney().toString());
                // 总数量
                BigDecimal totalQuantity = ingredient.getTotalQuantity().divide(ingredient.getStorageToCostCardRatio(),2);
                String totalQuantityStr = totalQuantity.toString() + unitService.queryById(ingredient.getStorageUnitId()).getName();
                jsonObject.put("totalQuantityStr", totalQuantityStr);
                jsonObject.put("totalMoney", ingredient.getTotalMoney().toString());
                jsonArray.add(jsonObject);
            }

        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        int dataCount = 0;
        try {
            dataCount = ingredientService.countBySearchDto(searchDto);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray, dataCount);
    }

    /**
     * 去原配料详情
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminStorageIngredient, extModule = ModuleEnums.AdminStorageIngredientList)
    @RequestMapping(value = "todetails/{id}",method = RequestMethod.GET)
    public String toList(@PathVariable("id")Integer id, Model model){
        try {
            Ingredient ingredient = ingredientService.queryById(id);
            ingredientService.setQuantityFormat(ingredient);
            model.addAttribute("ingredient",ingredient);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/storage/ingredient/details_home";
    }


    /**
     * 去添加页
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminStorageIngredient, extModule = ModuleEnums.AdminStorageIngredientNew)
    @RequestMapping(value = "tonew",method = RequestMethod.GET)
    public String toNew(Model model){
        try {
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
            model.addAttribute("tagList",tagList);
            model.addAttribute("weightUnit", weightUnit);
            model.addAttribute("quantityUnit", quantityUnit);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/storage/ingredient/new_home";
    }

    /**
     * 添加原配料
     * @param ingredient
     * @return
     */
    @Module(value = ModuleEnums.AdminStorageIngredient, extModule = ModuleEnums.AdminStorageIngredientNew)
    @RequestMapping(value = "ajax/new", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject newIngredient(Ingredient ingredient,Model model){
        try {
            Ingredient ingredient1 = ingredientService.newIngredient(ingredient);
            if (Assert.isNull(ingredient1)){
                return sendMsgAndCode(AJAX_FAILURE_CODE,"添加失败");
            } else {
                return sendMsgAndCode(AJAX_SUCCESS_CODE,"添加成功");
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 检查原配料名是否重名
     * @param name
     * @return
     */
    @Module(ModuleEnums.AdminStorageIngredientNew)
    @RequestMapping(value = "ajax/checkname",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject checkIngredientName(@RequestParam("name")String name){
        if (Assert.isNull(name)){
            return sendJsonObject(AJAX_SUCCESS_CODE);
        }
        try {
            if (ingredientService.checkIngredientNameIsExist(name)){
                return sendJsonObject(AJAX_FAILURE_CODE);
            } else {
                return sendJsonObject(AJAX_SUCCESS_CODE);
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 去修改页
     * @param id
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminStorageIngredient,extModule = ModuleEnums.AdminStorageIngredientUpdate)
    @RequestMapping(value = "toupdate/{id}",method = RequestMethod.GET)
    public String toUpdate(@PathVariable("id")Integer id,Model model){
        try {
            List<Tag> tagList = storageTagService.listAllSmallTag();
            List<Unit> unitList = unitService.listAll();
            Ingredient ingredient = ingredientService.queryById(id);
            List<Unit> weightUnit = new ArrayList<Unit>();
            List<Unit> quantityUnit = new ArrayList<Unit>();
            int orderUnitType = 1, storageUnitType = 1, costCardUnitType = 1, countOrderType = 1;
            for (Unit unit : unitList) {
                if (unit.getId().equals(ingredient.getOrderUnitId())) {
                    orderUnitType = unit.getType();
                }
                if (unit.getId().equals(ingredient.getStorageUnitId())) {
                    storageUnitType = unit.getType();
                }
                if (unit.getId().equals(ingredient.getCostCardUnitId())) {
                    costCardUnitType = unit.getType();
                }
                if (UnitEnum.HundredWeight.getId().equals(unit.getType())) {
                    weightUnit.add(unit);
                } else {
                    quantityUnit.add(unit);
                }
            }
            // 变换最大库存表现形式
            ingredient.setMaxStorageQuantity(ingredient.getMaxStorageQuantity().divide(ingredient.getStorageToCostCardRatio()));
            // 最小库存
            ingredient.setMinStorageQuantity(ingredient.getMinStorageQuantity().divide(ingredient.getStorageToCostCardRatio()));
            // 实际库存
            ingredient.setRealQuantity(ingredient.getRealQuantity().divide(ingredient.getStorageToCostCardRatio()));
            // 总库存
            ingredient.setTotalQuantity(ingredient.getTotalQuantity().divide(ingredient.getStorageToCostCardRatio()));
            model.addAttribute("ingredient", ingredient);
            model.addAttribute("orderUnitType", orderUnitType);
            model.addAttribute("storageUnitType", storageUnitType);
            model.addAttribute("storageUnitType", costCardUnitType);
            model.addAttribute("weightUnit", weightUnit);
            model.addAttribute("quantityUnit", quantityUnit);
            model.addAttribute("tagList",tagList);
            model.addAttribute("unit",unitList);
            if (ingredientService.checkIsCanUpdate(id)){
                model.addAttribute("isUpdated",0);
            } else {
                model.addAttribute("isUpdated",1);
            }
            model.addAttribute("isUpdated",1);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/storage/ingredient/update_home";
    }

    /**
     * 当库存单位和成本卡单位转换率改变时，对页面显示的总数量和实际数量进行修改
     * @param id
     * @param storageUnitId
     * @param storageToCostCardRatio
     * @return
     */
    @Module(value = ModuleEnums.AdminStorageIngredientUpdate,extModule = ModuleEnums.AdminStorageIngredientUpdate)
    @RequestMapping(value = "ajax/convert/quantity",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject convertQuantity(@RequestParam("id")Integer id ,
                                      @RequestParam("storageUnitId")Integer storageUnitId ,
                                      @RequestParam("storageToCostCardRatio")BigDecimal storageToCostCardRatio){
        try {
            Ingredient ingredientReal = ingredientService.queryById(id);
            JSONObject jsonObject = new JSONObject();
            if(storageToCostCardRatio.compareTo(BigDecimal.ZERO)==0){
                return sendJsonObject(AJAX_FAILURE_CODE);
            }else {
                jsonObject.put("realQuantity",ingredientReal.getRealQuantity().divide(storageToCostCardRatio));
                jsonObject.put("totalQuantity",ingredientReal.getTotalQuantity().divide(storageToCostCardRatio));
                jsonObject.put("maxStorageQuantity",ingredientReal.getMaxStorageQuantity().divide(storageToCostCardRatio));
                jsonObject.put("minStorageQuantity",ingredientReal.getMinStorageQuantity().divide(storageToCostCardRatio));
            }
            return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 编辑ingredient数量转换
     * @param ingredient
     *
     * @return
     */
    @Module(value = ModuleEnums.AdminStorageIngredientUpdate,extModule = ModuleEnums.AdminStorageIngredientUpdate)
    @RequestMapping(value = "ajax/update",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update(Ingredient ingredient,@RequestParam("isUpdated")Integer isUpdated) {
        try {
            ingredientService.updateIngredient(ingredient);
            return sendMsgAndCode(AJAX_SUCCESS_CODE,"修改成功");
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }

    @Module(value = ModuleEnums.AdminStorageIngredientList,extModule = ModuleEnums.AdminStorageIngredientList)
    @RequestMapping(value = "export",method = RequestMethod.GET)
    public void export(ItemAndIngredientSearchDto searchDto,
                       HttpServletResponse response) {
        //if(searchDto==null)System.out.println("searchDto is null!!!");
        try {
            ingredientService.exportExcel(searchDto,response);
            sendErrMsg("导出成功");
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
    }


    @Module(value = ModuleEnums.AdminStorageIngredient,extModule = ModuleEnums.AdminStorageIngredientDelete)
    @RequestMapping(value = "ajax/del/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject delById(@PathVariable("id")Integer id) {
        try {
//            if (ingredientService.checkIsCanUpdate(id)){
//                ingredientService.updateIngredientStatusById(id, IngredientStatusEnums.Normal.getId());
//                return sendMsgAndCode(AJAX_SUCCESS_CODE,"修改成功");
//            } else {
//                return sendMsgAndCode(AJAX_FAILURE_CODE,"修改失败，当前原配料正在使用");
//            }
            ingredientService.updateIngredientStatusById(id, IngredientStatusEnums.Normal.getId());
            return sendMsgAndCode(AJAX_FAILURE_CODE,"修改失败，当前原配料正在使用");
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }
}
