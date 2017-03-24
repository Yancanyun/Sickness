package com.emenu.web.controller.admin.stock;

import com.emenu.common.annotation.Module;
import com.emenu.common.entity.stock.StockKitchenItem;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.common.utils.WebConstants;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.emenu.service.stock.StockKitchenItemService;

import java.util.List;
/**
 * StockKitchenItemController
 *
 * @author yaojf
 * @date 2017/3/17 8:28
 */
@Module(ModuleEnums.AdminStock)
@Controller
@RequestMapping(value = URLConstants.ADMIN_STOCK_KITCHEN_ITEM_URL)
public class StockKitchenItemController extends AbstractController{
    @Autowired
    StockKitchenItemService stockKitchenItemService;

    @Module(ModuleEnums.AdminStockKitchenItem)
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toKitchenItemList(Model model){
        try{
            List<StockKitchenItem> kitchenItemList = stockKitchenItemService.queryAllItem();
            model.addAttribute("kitchenItemList",kitchenItemList);
            return "admin/stock/kitchenItem/list_home";
        } catch(SSException e) {
            sendErrMsg(e.getMessage());
            LogClerk.errLog.error(e);
            return WebConstants.sysErrorCode;
        }
    }


}
