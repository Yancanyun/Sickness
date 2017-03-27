package com.emenu.web.controller.admin.stock;

import com.emenu.common.annotation.Module;
import com.emenu.common.entity.stock.StockKitchen;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * StockKitchenController
 *
 * @author nigbo
 * @Time 2017/3/8 9:21
 */
@Module(ModuleEnums.AdminStockKitchen)
@Controller
@RequestMapping(URLConstants.ADMIN_STOCK_KITCHEN_URL)
public class StockKitchenController extends AbstractController{
    @Module(ModuleEnums.AdminStockKitchenList)
    @RequestMapping(value = {"","toList"},method = RequestMethod.GET)
    public String toList(Model model){
        try{
            List<StockKitchen> kitchenList = stockKitchenService.listStockKitchen();
            model.addAttribute("kitchenList",kitchenList);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/stock/kitchen/list_home";
    }

    @Module(ModuleEnums.AdminStockKitchenAdd)
    @RequestMapping(value = {"/add"},method = RequestMethod.GET)
    public String toAddPage(Model model){
        return "admin/stock/kitchen/add_home";
    }

    @Module(ModuleEnums.AdminStockKitchenAdd)
    @RequestMapping(value = {"/edit"},method = RequestMethod.GET)
    public String toEditPage(Model model){
        return "admin/stock/kitchen/edit_home";
    }



    /*你好*/

}
