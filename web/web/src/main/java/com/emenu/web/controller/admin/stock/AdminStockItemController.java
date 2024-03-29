package com.emenu.web.controller.admin.stock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.stock.StockItemSearchDto;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.stock.StockItem;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * AdminStockItemController
 * 新版库存-物品管理Controller
 * Update By Renhongshuai
 * @author pengpeng
 * @time 2017/3/6 9:37
 */
@Controller
@Module(ModuleEnums.AdminStock)
@RequestMapping(value = URLConstants.ADMIN_STOCK_ITEM_URL)
public class AdminStockItemController extends AbstractController{

    /**
     * 去列表页
     *
     * @return
     */
    @Module(value = ModuleEnums.AdminStockItemList)
    @RequestMapping(value = {"","list"},method = RequestMethod.GET)
    public String ToList(Model model){
        try{
            List<Tag> tagList = storageTagService.listAllSmallTag();
            model.addAttribute("tagList",tagList);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/stock/item/list_home";
    }


    /**
     * 刷分页及搜索
     * @param pageNo
     * @param pageSize
     * @param searchDto
     * @return
     */
    @Module(value = ModuleEnums.AdminStockItem, extModule = ModuleEnums.AdminStockItemList)
    @RequestMapping(value = "ajax/list/{pageNo}",method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(@PathVariable("pageNo")Integer pageNo,
                         @RequestParam("pageSize") Integer pageSize,
                         StockItemSearchDto searchDto){
        pageSize = (pageSize == null || pageSize <= 0) ? DEFAULT_PAGE_SIZE : pageSize;
        int offset = 0;
        if (Assert.isNotNull(pageNo)) {
            pageNo = pageNo <= 0 ? 0 : pageNo - 1;
            offset = pageNo * pageSize;
        }
        List<StockItem> list = Collections.emptyList();
        try{
            if(Assert.isNull(searchDto)){
                list = stockItemService.listByPage(offset,pageSize);
            }else{
                list = stockItemService.listBySearchDto(searchDto);
            }
        }catch(SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONArray jsonArray = new JSONArray();
        int i = 1 + pageNo * 10;
        for (StockItem item: list){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("sequenceNumber",i++);
            jsonObject.put("id", item.getId());
            jsonObject.put("name",item.getName());
            jsonObject.put("itemNumber",item.getItemNumber());
            jsonObject.put("assistantCode",item.getAssistantCode());
            jsonObject.put("tagName",item.getTagName());
            jsonObject.put("totalStockInQuantityStr",item.getStorageQuantity());
            jsonObject.put("maxStorageQuantity",item.getUpperQuantity());
            jsonObject.put("minStorageQuantity",item.getLowerQuantity());
            jsonObject.put("stockOutType",(item.getStockOutType() == 1)? "自动出库":"手动出库");
            jsonArray.add(jsonObject);
        }
        int dataCount = 0;
        try {
            dataCount = stockItemService.count();
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray, dataCount);
    }

    /**
     * 导出excel
     * @param searchDto
     * @param response
     */
    @Module(value = ModuleEnums.AdminStorageIngredientList,extModule = ModuleEnums.AdminStorageIngredientList)
    @RequestMapping(value = "export",method = RequestMethod.GET)
    public void exportExcel(StockItemSearchDto searchDto, HttpServletResponse response){
        try {
            stockItemService.exportExcelBySearchDto(searchDto,response);
            sendErrMsg("导出成功");
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
    }

    /**
     * 去添加页
     * @return
     */
    @Module(value = ModuleEnums.AdminStockItem, extModule = ModuleEnums.AdminStockItemNew)
    @RequestMapping(value = "add",method = RequestMethod.GET)
    public String toStockItemAdd(Model model){
        try {
            model.addAttribute("tags",tagService.listAll());
            //TODO：成本卡单位列表
            model.addAttribute("costCardUnits",costCardService.listAllCostCardDto());
            model.addAttribute("specifications",specificationsService.listAll());
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
    @RequestMapping(value = "edit/{id}",method = RequestMethod.GET)
    public String toStockItemUpdate(@PathVariable("id")Integer id,Model model){
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
