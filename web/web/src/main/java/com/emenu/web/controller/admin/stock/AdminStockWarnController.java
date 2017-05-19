package com.emenu.web.controller.admin.stock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.stock.StockWarn;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.List;

/**
 * @author Flying
 * @date 2017/5/19 8:52
 */
@Controller
@Module(ModuleEnums.AdminStock)
@RequestMapping(value = URLConstants.ADMIN_STOCK_WARN_URL)
public class AdminStockWarnController extends AbstractController{
    /**
     * 去预警列表页
     *
     * @return
     */
    @Module(value = ModuleEnums.AdminStockWarnList)
    @RequestMapping(value = {"","list"}, method = RequestMethod.GET)
    public String toList(){
        return "admin/stock/warn/list_home";
    }

    @Module(value = ModuleEnums.AdminStockWarnList)
    @RequestMapping(value = "ajax/list/{pageNo}", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(@PathVariable("pageNo") Integer pageNo,
                         @RequestParam("pageSize") Integer pageSize){
        pageSize = (pageSize == null || pageSize <= 0 ) ? DEFAULT_PAGE_SIZE : pageSize;
        int offset = 0;
        if(Assert.isNotNull(pageNo)){
            pageNo = pageNo <= 0 ? 0 : pageNo - 1;
            offset = pageNo * pageSize;
        }
        List<StockWarn> stockWarnList = Collections.emptyList();
        try{
            stockWarnList = stockWarnService.listByPage(offset,pageSize);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        int dataCount = 0;
        try{
            dataCount = stockWarnService.countAllWarn();
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        JSONArray jsonArray = new JSONArray();
        for(StockWarn stockWarn : stockWarnList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id",stockWarn.getId());
            jsonObject.put("itemId",stockWarn.getItemId());
            jsonObject.put("itemName",stockWarn.getItemName());
            jsonObject.put("kitchenId",stockWarn.getKitchenId());
            jsonObject.put("kitchenName",stockWarn.getKitchenName());
            jsonObject.put("warnContent",stockWarn.getContent());
            jsonObject.put("state",stockWarn.getState());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
            jsonObject.put("time", sdf.format(stockWarn.getTime()));
            jsonArray.add(jsonObject);
        }
        return sendJsonArray(jsonArray,dataCount);
    }

    /**
     * 更改状态为领用
     *
     * @param id
     * @return
     */
    @Module(value = ModuleEnums.AdminStockWarn, extModule = ModuleEnums.AdminStockWarnList)
    @RequestMapping(value = "ajax/solved/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject solvedById(@PathVariable("id") Integer id){
        try{
            stockWarnService.updateStateToResolvedByItemId(id);
            return sendMsgAndCode(AJAX_SUCCESS_CODE,"领用成功");
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 修改预警状态为忽略
     *
     * @param id
     * @return
     */
    @Module(value = ModuleEnums.AdminStockWarn, extModule = ModuleEnums.AdminStockWarnIgnore)
    @RequestMapping(value = "ajax/ignore/{id}", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ignoreById(@PathVariable("id") Integer id){
        try{
            stockWarnService.updateStateToIgnoredByItemId(id);
            return sendMsgAndCode(AJAX_SUCCESS_CODE,"忽略成功");
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 导出excel表格
     *
     * @param response
     */
    @Module(value = ModuleEnums.AdminStockWarn, extModule = ModuleEnums.AdminStockWarnList)
    @RequestMapping(value = "export", method = RequestMethod.GET)
    public void exportExcel(HttpServletResponse response){
        try{
            stockWarnService.exportExcel(response);
            sendErrMsg("导出成功");
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
    }


}