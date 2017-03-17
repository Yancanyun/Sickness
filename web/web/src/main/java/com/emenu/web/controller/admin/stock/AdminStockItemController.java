package com.emenu.web.controller.admin.stock;

import com.emenu.common.annotation.Module;
import com.emenu.common.dto.stock.StockItemSearchDto;
import com.emenu.common.entity.stock.StockItem;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * AdminStockItemController
 * 新版库存-物品管理Controller
 *
 * @author pengpeng
 * @time 2017/3/6 9:37
 */
@Controller
@Module(ModuleEnums.AdminStock)
@RequestMapping(value = URLConstants.ADMIN_STOCK_ITEM_URL)
public class AdminStockItemController extends AbstractController{
    /**
     * 去列表
     * @param model
     * @param searchDto
     * @return
     */
    @Module(value = ModuleEnums.AdminStockItem, extModule = ModuleEnums.AdminStockItemList)
    @RequestMapping(value = {"","list"})
    public String toItemList(Model model, StockItemSearchDto searchDto){
        try {
            List<StockItem> itemList = stockItemService.listItem(searchDto);
            model.addAttribute("itemList",itemList);
            return "admin/stock/item/list_home";
        }
        catch (Exception e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
    }

    /**
     * 去添加页
     * @return
     */
    @Module(value = ModuleEnums.AdminStockItem, extModule = ModuleEnums.AdminStockItemNew)
    @RequestMapping(value = "add_stock_item",method = RequestMethod.GET)
    public String toStockItemAdd(){
        try {
            return "admin/stock/item/add_home";
        }
        catch (Exception e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
    }

    /**
     * 添加item
     * @param stockItem
     * @return
     */
    @Module(value = ModuleEnums.AdminStockItem, extModule = ModuleEnums.AdminStockItemNew)
    @RequestMapping(value = "add_stock_item",method = RequestMethod.POST)
    public String addStockItem(StockItem stockItem){
        try {
            stockItemService.newItem(stockItem);
            return "redirect:list";
        }
        catch (Exception e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
    }

    /**
     * 去编辑页
     * @param id
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminStockItem, extModule = ModuleEnums.AdminStockItemUpdate)
    @RequestMapping(value = "update_stock_item",method = RequestMethod.GET)
    public String toStockItemUpdate(Integer id,Model model){
        try {
            StockItem stockItem = stockItemService.queryById(id);
            model.addAttribute("stockItem",stockItem);
            return "admin/stock/item/edit_home";
        }
        catch (Exception e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
    }

    /**
     * 编辑item
     * @param stockItem
     * @return
     */
    @Module(value = ModuleEnums.AdminStockItem, extModule = ModuleEnums.AdminStockItemUpdate)
    @RequestMapping(value = "update_stock_item",method = RequestMethod.POST)
    public String updateStockItem(StockItem stockItem){
        try {
            stockItemService.updateStockItem(stockItem);
            return "redirect:list";
        }
        catch (Exception e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
    }
}
