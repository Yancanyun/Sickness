package com.emenu.web.controller.admin.stock;

import com.emenu.common.annotation.Module;
import com.emenu.common.entity.stock.StockKitchen;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.common.utils.WebConstants;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;

import java.util.List;


/**
 * AdminStockDocumentsController
 *
 * @author renhongshuai
 * @Time 2017/3/13 11:00.
 */
@Module(ModuleEnums.AdminStock)
@Controller
@RequestMapping(value = URLConstants.ADMIN_STOCK_DOCUMENTS_URL)
public class AdminStockDocumentsController extends AbstractController {

    @Module(ModuleEnums.AdminStockDocumentsList)
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList(Model model){
        try{
            List<StockKitchen> kitchenList = stockKitchenService.listStockKitchen();
            model.addAttribute("kitchenList",kitchenList);

            return "admin/stock/documents/list_home";
        } catch(SSException e) {
            sendErrMsg(e.getMessage());
            LogClerk.errLog.error(e);
            return WebConstants.sysErrorCode;
        }
    }

}
