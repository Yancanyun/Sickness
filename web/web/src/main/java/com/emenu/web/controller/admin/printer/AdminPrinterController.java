package com.emenu.web.controller.admin.printer;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.printer.Printer;
import com.emenu.common.enums.other.ModuleEnums;
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
        return null;
    }

    @Module(ModuleEnums.AdminBasicInfoPrinterNew)
    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String newPrinter(Printer printer, @RequestParam(value = "dishTagList", required = false) List<Integer> dishTagList,
                             HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes){
        try {
            Printer printer1 = printerService.newPrinter(printer);
            if (printer.getType().equals(PrinterTypeEnums.DishTagPrinter.getId())){
                for (int dishTagId : dishTagList){
                    printerService.bindDishTag(printer1.getId(), dishTagId);
                }
            }
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return null;
    }

    @Module(ModuleEnums.AdminBasicInfoPrinterUpdate)
    @RequestMapping(value = "update", method = RequestMethod.PUT)
    public String updatePrinter(Printer printer, @RequestParam(value = "dishTagList", required = false) List<Integer> dishTagList,
                                HttpServletRequest httpServletRequest, RedirectAttributes redirectAttributes){
            try {
                if (!printer.getType().equals(PrinterTypeEnums.DishTagPrinter.getId())) {
                    printerService.unBindAllDishTag(printer.getId());
                }
                printerService.updatePrinter(printer);
            } catch (SSException e) {
                LogClerk.errLog.error(e);
                sendErrMsg(e.getMessage());
                return ADMIN_SYS_ERR_PAGE;
            }
        return null;
    }

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

    @RequestMapping(value = "ajax/check", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject checkBinding(@RequestParam("id") int id, @RequestParam("type") int type){
            try {
                if (type != PrinterTypeEnums.DishTagPrinter.getId()) {
                    List<Tag> list = printerService.listTagById(id);
                    if (!Assert.isEmpty(list)){
                        JSONArray jsonArray = new JSONArray();
                        for (Tag tag : list){
                            jsonArray.add(tag.getName());
                        }
                        JSONObject dishTagNameList = new JSONObject();
                        dishTagNameList.put("dishTag", jsonArray);
                        return sendJsonObject(dishTagNameList, AJAX_SUCCESS_CODE);
                    }
                }
                return sendJsonObject(AJAX_SUCCESS_CODE);
            } catch (SSException e) {
                LogClerk.errLog.error(e);
                return sendErrMsgAndErrCode(e);
            }
    }
}
