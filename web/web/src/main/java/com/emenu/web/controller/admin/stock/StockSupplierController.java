package com.emenu.web.controller.admin.stock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.party.group.supplier.Supplier;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;

/**
 * SupplierController
 *
 * @author renhongshuai
 * @Time 2017/5/4 9:24.
 */
@Module(ModuleEnums.AdminStock)
@Controller
@RequestMapping(value = URLConstants.ADMIN_STOCK_SUPPLIER_URL)
public class StockSupplierController extends AbstractController{
    /**
     * 去列表页
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminStockSupplierList)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toList(Model model) {
        try {
            List<Supplier> supplierList = supplierService.listAll();
            model.addAttribute("supplierList", supplierList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/stock/supplier/list_home";
    }

    /**
     * ajax添加
     *
     * @param supplier
     * @return
     */
    @Module(ModuleEnums.AdminStockSupplierNew)
    @RequestMapping(value = "ajax", method = RequestMethod.POST)
    @ResponseBody
    public JSON ajaxNew(Supplier supplier) {
        try {
            supplier = supplierService.newSupplier(supplier, getPartyId());
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("id", supplier.getId());
        jsonObject.put("partyId", supplier.getPartyId());
        return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
    }

    /**
     * ajax更新
     *
     * @param supplier
     * @return
     */
    @Module(ModuleEnums.AdminStockSupplierUpdate)
    @RequestMapping(value = "ajax", method = RequestMethod.PUT)
    @ResponseBody
    public JSON ajaxUpdate(Supplier supplier) {
        try {
            supplierService.updateSupplier(supplier);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * ajax删除
     *
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminStockSupplierDelete)
    @RequestMapping(value = "ajax/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSON ajaxDelete(@PathVariable("id") Integer id) {
        try {
            supplierService.delById(id);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }
}
