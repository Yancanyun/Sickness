package com.emenu.web.controller.admin.printer;

import com.emenu.common.annotation.Module;
import com.emenu.common.entity.printer.Printer;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @Module(ModuleEnums.AdminBasicInfoPrinterList)
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
}
