package com.emenu.web.controller.admin.printer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.printer.Printer;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.printer.PrinterBrandEnums;
import com.emenu.common.enums.printer.PrinterModelEnums;
import com.emenu.common.enums.printer.PrinterTypeEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * AdminPrinterController
 *
 * @author Wang Liming
 * @date 2015/11/16 17:10
 */

@Controller
@Module(ModuleEnums.AdminBasicInfoPrinter)
@RequestMapping(value = URLConstants.ADMIN_PRINTER_URL)
public class AdminPrinterController extends AbstractController {

    /**
     * 去打印机管理页面
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminBasicInfoPrinterList)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toPrinterPage(Model model){
        try {
            List<Printer> printerList = printerService.listAll();
            model.addAttribute("printerList", printerList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/printer/list_home";
    }

    /**
     * 去添加打印机页
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminBasicInfoPrinterNew)
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String toNewPrinter(Model model){
        try {
            List<Tag> availableDishTag = dishTagPrinterService.listAvailableDishTag();
            List<PrinterBrandEnums> brandList = PrinterBrandEnums.getBrandList();
            List<PrinterModelEnums> modelList = PrinterModelEnums.getModelList();
            List<PrinterTypeEnums> typeList = PrinterTypeEnums.getTypeList();
            model.addAttribute("availableDishTag", availableDishTag);
            model.addAttribute("brandList", brandList);
            model.addAttribute("modelList", modelList);
            model.addAttribute("typeList", typeList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/printer/new_home";
    }

    /**
     * 添加打印机
     *
     * @param printer
     * @param dishTagList
     * @param httpServletRequest
     * @param redirectAttributes
     * @return
     */
    @Module(ModuleEnums.AdminBasicInfoPrinterNew)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String newPrinter(Printer printer, @RequestParam(value = "dishTagList", required = false) List<Integer> dishTagList,
                             HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes){
        try {
            Printer printer1 = printerService.newPrinter(printer);

            //如果是分类打印机就绑定选择的菜品分类
            if (printer.getType().equals(PrinterTypeEnums.DishTagPrinter.getId())){
                if (!Assert.isEmpty(dishTagList)) {
                    for (int dishTagId : dishTagList) {
                        dishTagPrinterService.bindDishTag(printer1.getId(), dishTagId);
                    }
                }
            }

            String successUrl = "/" + URLConstants.ADMIN_PRINTER_URL;
            redirectAttributes.addFlashAttribute("msg", NEW_SUCCESS_MSG);
            return "redirect:" + successUrl;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            String failedUrl = "/" + URLConstants.ADMIN_PRINTER_URL + "/new";
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
            return "redirect:" + failedUrl;
        }
    }

    /**
     * 去打印机编辑页
     *
     * @param printerId
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminBasicInfoPrinterUpdate)
    @RequestMapping(value = "update/{printerId}", method = RequestMethod.GET)
    public String toUpdatePrinter(@PathVariable("printerId") int printerId, Model model){
        try {
            Printer printer = printerService.queryById(printerId);
            List<Tag> dishTagList = dishTagPrinterService.listTagById(printerId);
            List<Tag> availableDishTagList = dishTagPrinterService.listAvailableDishTag();
            List<PrinterBrandEnums> brandList = PrinterBrandEnums.getBrandList();
            List<PrinterModelEnums> modelList = PrinterModelEnums.getModelList();
            List<PrinterTypeEnums> typeList = PrinterTypeEnums.getTypeList();
            model.addAttribute("printer", printer);
            model.addAttribute("dishTagList", dishTagList);
            model.addAttribute("availableDishTagList", availableDishTagList);
            model.addAttribute("brandList", brandList);
            model.addAttribute("modelList", modelList);
            model.addAttribute("typeList", typeList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/printer/update_home";
    }

    /**
     * 编辑打印机
     *
     * @param printer
     * @param dishTagList
     * @param httpServletRequest
     * @param redirectAttributes
     * @return
     */
    @Module(ModuleEnums.AdminBasicInfoPrinterUpdate)
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public String updatePrinter(Printer printer, @RequestParam(value = "dishTagList", required = false) List<Integer> dishTagList,
                                HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes){
            try {
                dishTagPrinterService.unBindAllDishTag(printer.getId());
                if (printer.getType().equals(PrinterTypeEnums.DishTagPrinter.getId())) {
                    if (!Assert.isEmpty(dishTagList)){
                        for (int id : dishTagList){
                            dishTagPrinterService.bindDishTag(printer.getId(), id);
                        }
                    }
                }
                printerService.updatePrinter(printer);

                String successUrl = "/" + URLConstants.ADMIN_PRINTER_URL;
                redirectAttributes.addFlashAttribute("msg", NEW_SUCCESS_MSG);
                return "redirect:" + successUrl;
            } catch (SSException e) {
                LogClerk.errLog.error(e);
                sendErrMsg(e.getMessage());
                String failedUrl = "/" + URLConstants.ADMIN_PRINTER_URL + "/update" + printer.getId();
                redirectAttributes.addFlashAttribute("msg", e.getMessage());
                return "redirect:" + failedUrl;
            }
    }

    /**
     * ajax删除打印机
     *
     * @param printerId
     * @return
     */
    @Module(ModuleEnums.AdminBasicInfoPrinterDel)
    @RequestMapping(value = "ajax/{printerId}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject delPrinter(@PathVariable("printerId") int printerId){
        try {
            printerService.delById(printerId);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax检查打印机是否关联菜品分类和菜品
     *
     * @param id
     * @param type
     * @return
     */
    @RequestMapping(value = "ajax/check", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject checkBinding(@RequestParam("id") int id, @RequestParam("type") int type){
            try {
                if (type != PrinterTypeEnums.DishTagPrinter.getId()) {
                    List<Tag> tagList = dishTagPrinterService.listTagById(id);
                    List<String> dishNameList = dishTagPrinterService.listDishNameById(id);
                    JSONArray tagNameArray = new JSONArray();
                    JSONObject dishTagNameList = new JSONObject();
                    if (!Assert.isEmpty(tagList)){
                        for (Tag tag : tagList){
                            tagNameArray.add(tag.getName());
                        }
                    }
                    if (!Assert.isEmpty(dishNameList)){
                        for (String s : dishNameList){
                            tagNameArray.add(s);
                        }
                    }
                    dishTagNameList.put("dishTag", tagNameArray);
                    return sendJsonObject(dishTagNameList, AJAX_SUCCESS_CODE);
                }
                return sendJsonObject(AJAX_SUCCESS_CODE);
            } catch (SSException e) {
                LogClerk.errLog.error(e);
                return sendErrMsgAndErrCode(e);
            }
    }
}
