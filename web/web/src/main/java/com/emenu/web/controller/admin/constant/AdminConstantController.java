package com.emenu.web.controller.admin.constant;

import com.emenu.common.annotation.Module;
import com.emenu.common.enums.other.ConstantEnum;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 * AdminConstantController
 *
 * @author: yangch
 * @time: 2016/8/3 17:09
 */
@Module(value = ModuleEnums.AdminConstant)
@Controller
@RequestMapping(value = URLConstants.ADMIN_CONSTANT_URL)
public class AdminConstantController extends AbstractController {
    @Module(value = ModuleEnums.AdminConstantList)
    @RequestMapping(value = {"", "list"} , method = RequestMethod.GET)
    public String toList(Model model) {
        try {
            model.addAttribute("internalNetworkAddress", constantService.queryValueByKey(ConstantEnum.InternalNetworkAddress.getKey()));
            model.addAttribute("printerPrintMaxNum", constantService.queryValueByKey(ConstantEnum.PrinterPrintMaxNum.getKey()));
            model.addAttribute("autoPrintOrderDishStartStatus", constantService.queryValueByKey(ConstantEnum.AutoPrintOrderDishStartStatus.getKey()));
            model.addAttribute("roundingMode", constantService.queryValueByKey(ConstantEnum.RoundingMode.getKey()));

            return "admin/constant/list_home";
        } catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
    }

    @Module(ModuleEnums.AdminConstantUpdate)
    @RequestMapping(value = {"", "list"} , method = RequestMethod.POST)
    public String update(@RequestParam String internalNetworkAddress,
                         @RequestParam String printerPrintMaxNum,
                         @RequestParam String autoPrintOrderDishStartStatus,
                         @RequestParam String roundingMode,
                         RedirectAttributes redirectAttributes) {
        try {
            constantService.updateValueByKey(ConstantEnum.InternalNetworkAddress.getKey(),
                    internalNetworkAddress);
            constantService.updateValueByKey(ConstantEnum.PrinterPrintMaxNum.getKey(),
                    printerPrintMaxNum);
            constantService.updateValueByKey(ConstantEnum.AutoPrintOrderDishStartStatus.getKey(),
                    autoPrintOrderDishStartStatus);
            constantService.updateValueByKey(ConstantEnum.RoundingMode.getKey(),
                    roundingMode);

            String successUrl = "/" + URLConstants.ADMIN_CONSTANT_URL;
            // 返回添加成功信息
            redirectAttributes.addFlashAttribute("successMsg", UPDATE_SUCCESS_MSG);
            return "redirect:" + successUrl;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            String failedUrl = "/" + URLConstants.ADMIN_CONSTANT_URL;
            // 返回添加失败信息
            redirectAttributes.addFlashAttribute("failedMsg", e.getMessage());
            // 返回添加页
            return "redirect:" + failedUrl;
        }
    }
}
