package com.emenu.web.controller.admin.stock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.dish.Unit;
import com.emenu.common.entity.stock.Specifications;
import com.emenu.common.enums.dish.UnitEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by apple on 17/3/8.
 */
@Module(ModuleEnums.AdminStock)
@Controller
@RequestMapping(value = URLConstants.ADMIN_STOCK_SPECIFICATIONS_URL)
public class AdminStockSpecificationsController extends AbstractController {

    /**
     * 去列表页
     *
     * @return
     */
    @Module(value = ModuleEnums.AdminStockSpecifications, extModule = ModuleEnums.AdminStockSpecificationsList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList() {
        return "admin/stock/specifications/list_home";
    }

    /**
     * ajax刷分页
     *
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Module(value = ModuleEnums.AdminStockSpecifications, extModule = ModuleEnums.AdminStockSpecificationsList)
    @RequestMapping(value = "ajax/list/{pageNo}", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(@PathVariable("pageNo") Integer pageNo,
                         @RequestParam("pageSize") Integer pageSize) {
        pageSize = (pageSize == null || pageSize <= 0) ? DEFAULT_PAGE_SIZE : pageSize;
        int offset = 0;
        if (Assert.isNotNull(pageNo)) {
            pageNo = pageNo <= 0 ? 0 : pageNo - 1;
            offset = pageNo * pageSize;
        }
        List<Specifications> list = Collections.emptyList();
        try {
            list = specificationsService.listByPage(offset, pageSize);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONArray jsonArray = new JSONArray();
        for (Specifications specifications : list) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", specifications.getId());
            jsonObject.put("orderUnitName", specifications.getOrderUnitName());
            jsonObject.put("orderToStorage", specifications.getOrderToStorage());
            jsonObject.put("storageUnitName", specifications.getStorageUnitName());
            jsonObject.put("storageToCost", specifications.getStorageToCost());
            jsonObject.put("costCardUnitName", specifications.getCostCardUnitName());
            jsonArray.add(jsonObject);
        }
        int dataCount = 0;
        try {
            dataCount = specificationsService.count();
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
    @Module(value = ModuleEnums.AdminStockSpecifications, extModule = ModuleEnums.AdminStockSpecificationsNew)
    @RequestMapping(value = "tonew", method = RequestMethod.GET)
    public String toNew(Model model) {
        try {
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
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/stock/specifications/add_home";
    }

    /**
     * 删除规格
     * @param id
     * @return
     */
    @Module(value = ModuleEnums.AdminStockSpecificationDelete)
    @RequestMapping(value = "ajax/del/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JSON DelSpecifications(@PathVariable("id") Integer id) {
        try {
            specificationsService.deleteById(id);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }


    /**
     * 添加规格
     *
     * @param specifications
     * @return
     */
    @Module(value = ModuleEnums.AdminStockSpecifications, extModule = ModuleEnums.AdminStockSpecificationsNew)
    @RequestMapping(value = "ajax/new", method = RequestMethod.POST)
    @ResponseBody
    public String AddSpecifications(Specifications specifications, RedirectAttributes redirectAttributes) {
        try {
            Specifications specifications1 = specifications;
            specificationsService.add(specifications);
            String successUrl = "/" + URLConstants.ADMIN_STOCK_SPECIFICATIONS_URL + "/list";
            redirectAttributes.addFlashAttribute("msg", NEW_SUCCESS_MSG);
            return "redirect:" + successUrl;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            String failedUrl = "/" + URLConstants.ADMIN_STOCK_SPECIFICATIONS_URL + "/tonew";
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
            return "redirect:" + failedUrl;
        }
    }

    /**
     * 去编辑页
     *
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminStockSpecifications, extModule= ModuleEnums.AdminStockSpecificationsUpdate)
    @RequestMapping(value = "toUpdate/{id}", method = RequestMethod.GET)
    public String toUpdate(Model model,@PathVariable("id") Integer id) {
        try {
            List<Unit> unitList = unitService.listAll();
            List<Unit> weightUnit = new ArrayList<Unit>();
            List<Unit> quantityUnit = new ArrayList<Unit>();
            Specifications specifications = specificationsService.queryById(id);
            int orderUnitType = 1, storageUnitType = 1, costCardUnitType = 1;
            for (Unit unit : unitList) {
                if (UnitEnum.HundredWeight.getId().equals(unit.getType())) {
                    weightUnit.add(unit);
                } else {
                    quantityUnit.add(unit);
                }
                if (unit.getId().equals(specifications.getOrderUnitId())) {
                    orderUnitType = unit.getType();
                }
                if (unit.getId().equals(specifications.getStorageUnitId())) {
                    storageUnitType = unit.getType();
                }
                if (unit.getId().equals(specifications.getCostCardUnitId())) {
                    costCardUnitType = unit.getType();
                }
            }
            model.addAttribute("specification",specifications);
            model.addAttribute("orderUnitType", orderUnitType);
            model.addAttribute("storageUnitType", storageUnitType);
            model.addAttribute("costCardUnitType", costCardUnitType);
            model.addAttribute("orderUnitName",specifications.getOrderUnitName());
            model.addAttribute("weightUnit", weightUnit);
            model.addAttribute("quantityUnit", quantityUnit);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/stock/specifications/edit_home";
    }

    /**
     * 修改提交
     * @param specifications
     * @return
     */
    @Module(value = ModuleEnums.AdminStockSpecifications, extModule= ModuleEnums.AdminStockSpecificationsUpdate)
    @RequestMapping(value = "ajax/update", method = RequestMethod.PUT)
    @ResponseBody
    public JSON updateStorageItem(Specifications specifications,@RequestParam("isUpdated")Integer isUpdated) {
        try{
            specificationsService.update(specifications);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }
}
