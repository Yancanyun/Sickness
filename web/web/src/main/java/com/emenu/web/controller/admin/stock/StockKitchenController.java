package com.emenu.web.controller.admin.stock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.stock.Specifications;
import com.emenu.common.entity.stock.StockKitchen;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
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
    /*@Module(ModuleEnums.AdminStockKitchenList)
    @RequestMapping(value = {"","list"},method = RequestMethod.GET)
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
    }*/


    /**
     * 去厨房页
     * @return
     */
    @Module(value = ModuleEnums.AdminStockKitchenList)
    @RequestMapping(value = {"","list"},method = RequestMethod.GET)
    public String toList(){
        return "admin/stock/kitchen/list_home";
    }


    /**
     * ajax刷分页
     * @param pageNo
     * @param pageSize
     * @return
     */
    @Module(value = ModuleEnums.AdminStockKitchen, extModule = ModuleEnums.AdminStockKitchenList)
    @RequestMapping(value = "ajax/list/{pageNo}",method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(@PathVariable("pageNo")Integer pageNo,
                         @RequestParam("pageSize") Integer pageSize){
        pageSize = (pageSize == null || pageSize <= 0) ? DEFAULT_PAGE_SIZE : pageSize;
        int offset = 0;
        if (Assert.isNotNull(pageNo)) {
            pageNo = pageNo <= 0 ? 0 : pageNo - 1;
            offset = pageNo * pageSize;
        }
        List<StockKitchen> list = Collections.emptyList();
        try{
            /*list = stockKitchenService.listByPage(offset,pageSize);*/
            list = stockKitchenService.listByPage(offset,pageSize);
        }catch(SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONArray jsonArray = new JSONArray();
        for (StockKitchen  stockKitchen: list){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", stockKitchen.getId());
            jsonObject.put("introduction",stockKitchen.getIntroduction());
            jsonObject.put("depotName",stockKitchen.getName());
            jsonObject.put("responsiblePerson",stockKitchen.getPrincipal());

            jsonArray.add(jsonObject);
        }
        int dataCount = 0;
        try {
            dataCount = stockKitchenService.count();
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray, dataCount);
    }


    @Module(ModuleEnums.AdminStockKitchenAdd)
    @RequestMapping(value = {"/add"},method = RequestMethod.GET)
    public String toAddPage(){
        return "admin/stock/kitchen/add_home";
    }

    @Module(ModuleEnums.AdminStockKitchenAdd)
    @RequestMapping(value = "ajax/add", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxNewChildRemarkTag(@RequestParam("id") Integer id,
                                            @RequestParam("name") String name,
                                            @RequestParam("introduction") String introduction,
                                            @RequestParam("principal") String principal) {
        try {
            StockKitchen stockKitchen = new StockKitchen();
            stockKitchen.setId(id);
            stockKitchen.setName(name);
            stockKitchen.setIntroduction(introduction);
            stockKitchen.setPrincipal(principal);
            //设置状态为可用
            //remarkTag.setStatus(RemarkTagStatusEnums.Enabled.getId());
            //remarkTagService.newRemarkTag(remarkTag);
            stockKitchenService.addStockKitchen(stockKitchen);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", stockKitchen.getId());
            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }


/*    @Module(ModuleEnums.AdminStockKitchenAdd)
    @RequestMapping(value = {"/add"},method = RequestMethod.GET)
    public String toAddPage(Model model){
        return "admin/stock/kitchen/add_home";
    }

    @Module(ModuleEnums.AdminStockKitchenAdd)
    @RequestMapping(value = {"/add"},method = RequestMethod.POST)
    public String AddKitchen(Model model,StockKitchen stockKitchen){
        try{
            stockKitchenService.addStockKitchen(stockKitchen);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "redirect:list";
    }*/


    /**
     * 去厨房编辑页
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminStockKitchenEdit)
    @RequestMapping(value = {"/edit"},method = RequestMethod.GET)
    public String toEditPage(Model model){
        return "admin/stock/kitchen/edit_home";
    }

    /**
     * 编辑
     * @param model
     * @param id
     * @param stockKitchen
     * @return
     */
    @Module(ModuleEnums.AdminStockKitchenEdit)
    @RequestMapping(value = {"/edit{id}"},method = RequestMethod.POST)
    public String EditKitchen(Model model,@PathVariable("id")Integer id,StockKitchen stockKitchen){
        try{
            stockKitchenService.updateStockKitchen(id,stockKitchen);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "redirect:list";
    }
}
