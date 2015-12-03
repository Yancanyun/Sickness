package com.emenu.web.controller.admin.storage;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.storage.StorageDepot;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

/**
 * 存放点Controller
 *
 * @author xubr
 * @date 2015/11/12.
 */
@Module(ModuleEnums.AdminStorageDepot)
@Controller
@RequestMapping(value = URLConstants.ADMIN_STORAGE_DEPOT_URL)
public class StorageDepotController extends AbstractController{

    /**
     * 去列表页
     *
     * @return
     */
    @Module(value= ModuleEnums.AdminStorageDepotList)
    @RequestMapping(value = {"","list"},method = RequestMethod.GET )
    public String toList(Model model){
        try {
            List<StorageDepot> depotList = storageDepotService.listAll();
            model.addAttribute("depotList", depotList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/storage/depot/list_home";
    }

    /**
     * 去添加页
     *
     * @return
     */
    @Module(ModuleEnums.AdminStorageDepotNew)
    @RequestMapping(value = "tonew",method = RequestMethod.GET)
    public String toNewStorageDepot() {
        return "admin/storage/depot/new_home";
    }

    /**
     * 添加
     *
     * @param storageDepot
     * @param redirectAttributes
     * @return
     */
    @Module(ModuleEnums.AdminStorageDepotNew)
    @RequestMapping(value = "new",method = RequestMethod.POST)
    public String newStorageDepot(StorageDepot storageDepot,RedirectAttributes redirectAttributes) {
        try {
            storageDepotService.newStorageDepot(storageDepot);
            String successUrl = "/" + URLConstants.ADMIN_STORAGE_DEPOT_URL + "/list";
            redirectAttributes.addFlashAttribute("msg", NEW_SUCCESS_MSG);
            return "redirect:" + successUrl;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            String failedUrl = "/" + URLConstants.ADMIN_STORAGE_DEPOT_URL + "/tonew";
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
            return "redirect:" + failedUrl;
        }
    }

    /**
     * 去修改页
     *
     * @return
     */
    @Module(ModuleEnums.AdminStorageDepotUpdate)
    @RequestMapping(value = "toupdate/{id}",method = RequestMethod.GET)
    public String toUpdateStorageDepot(@PathVariable("id") Integer id,
                                       Model model) {
        try {
            StorageDepot storageDepot = storageDepotService.queryById(id);
            model.addAttribute("storageDepot",storageDepot);
        } catch(SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/storage/depot/update_home";
    }

    /**
     * 修改
     *
     * @param storageDepot
     * @return
     */
    @Module(ModuleEnums.AdminBasicInfoIndexImgUpdate)
    @RequestMapping(value= "update",method = RequestMethod.POST)
    public String updateStorageDepot(StorageDepot storageDepot,RedirectAttributes redirectAttributes) {
        try {
            storageDepotService.updateStorageDepot(storageDepot);
            String successUrl = "/" + URLConstants.ADMIN_STORAGE_DEPOT_URL + "/list";
            redirectAttributes.addFlashAttribute("msg", UPDATE_SUCCESS_MSG);
            return "redirect:" + successUrl;
        }  catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            String failedUrl = "/" + URLConstants.ADMIN_STORAGE_DEPOT_URL + "/toupdate";
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
            return "redirect:" + failedUrl;
        }

    }

    /**
     * 删除
     *
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminStorageDepotDelete)
    @RequestMapping(value="ajax/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject ajaxDelStorageDepot(@PathVariable("id")Integer id) {
        try {
            storageDepotService.delById(id);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch(SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
