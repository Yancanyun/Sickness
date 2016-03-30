package com.emenu.web.controller.admin.storage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.party.group.employee.EmployeeDto;
import com.emenu.common.dto.storage.StorageReportDto;
import com.emenu.common.dto.storage.StorageReportItemDto;
import com.emenu.common.entity.storage.StorageDepot;
import com.emenu.common.entity.storage.StorageItem;
import com.emenu.common.entity.storage.StorageReport;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.utils.DateUtils;
import com.emenu.common.utils.URLConstants;
import com.emenu.common.utils.WebConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;


/**
 * 单据controller
 * @author xiaozl
 * @date 2015/11/21
 * @time 10:49
 */

@Module(ModuleEnums.AdminStorage)
@Controller
@RequestMapping(URLConstants.ADMIN_STORAGE_REPORT_URL)
public class AdminStorageReportController extends AbstractController {
    /**
     * 单据信息list
     * @return
     */
    @Module(ModuleEnums.AdminStorageReportList)
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList(Model model){
        try {
            //存放点
            List<StorageDepot> depotList = Collections.emptyList();
            //经手人
            List<EmployeeDto> handlerList = Collections.emptyList();
            //操作人
            List<EmployeeDto> createdList = Collections.emptyList();
            //库存物品列表
            List<StorageItem> itemList = Collections.emptyList();
            depotList = storageDepotService.listAll();
            handlerList = employeeService.listAll();
            createdList = employeeService.listAll();
            itemList = storageItemService.listAll();
            model.addAttribute("depotList", depotList);
            model.addAttribute("handlerList", handlerList);
            model.addAttribute("createdList", createdList);
            model.addAttribute("itemList", itemList);
            model.addAttribute("lastMonthFirstDay", DateUtils.getLastMonthFirstDay());
            model.addAttribute("lastMonthLastDay", DateUtils.getLastMonthLastDay());
            model.addAttribute("currentMonthFirstDay", DateUtils.getCurrentMonthFirstDay());
            model.addAttribute("currentMonthLastDay", DateUtils.getCurrentMonthLastDay());
            model.addAttribute("currentDay", DateUtils.yearMonthDayFormat(DateUtils.now()));
            return "admin/storage/report/list_home";
        } catch (SSException e) {
            sendErrMsg(e.getMessage());
            LogClerk.errLog.error(e);
            return WebConstants.sysErrorCode;
        }
    }

    /**
     * 分页获取单据信息
     * @param curPage
     * @param pageSize
     * @return
     */
    @Module(ModuleEnums.AdminStorageReportList)
    @RequestMapping(value = "ajax/list/{curPage}",method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxSearch(@PathVariable("curPage") Integer curPage,
                           @RequestParam("pageSize") Integer pageSize,
                           @RequestParam("createdPartyId") Integer createdPartyId,
                           @RequestParam(value = "depotId", required = false) Integer[] depotId,
                           @RequestParam("endTime") Date endTime,
                           @RequestParam("handlerPartyId") Integer handlerPartyId,
                           @RequestParam("startTime") Date startTime) {


        int dataCount = 0;
        StorageReport report = new StorageReport();
        report.setHandlerPartyId(handlerPartyId);
        report.setCreatedPartyId(createdPartyId);
        List<Integer> depots = new ArrayList<Integer>();
        if (depotId != null && depotId.length > 0) {
            for (int i = 0; i < depotId.length; i++) {
                depots.add(depotId[i]);
            }
        }
        try {
            //总数据数
            dataCount = storageReportService.countByContition(report, depots, startTime, endTime);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        List<StorageReportDto> storageReportDtoList = Collections.emptyList();
        try {
            //分页获取单据和单据详情
            storageReportDtoList = storageReportService.listReportDtoByCondition1(report,curPage,pageSize, depots,startTime,endTime);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONArray jsonArray = new JSONArray();
        try{
            for (StorageReportDto storageReportDto : storageReportDtoList) {
                JSONObject jsonObject = new JSONObject();
                String deportName = storageDepotService.queryById(storageReportDto.getStorageReport().getDepotId()).getName();
                String handlerName = employeeService.queryByPartyId(storageReportDto.getStorageReport().getHandlerPartyId()).getName();
                String createdName = employeeService.queryByPartyId(storageReportDto.getStorageReport().getCreatedPartyId()).getName();
                String createdTime = DateUtils.yearMonthDayFormat(storageReportDto.getStorageReport().getCreatedTime());

                jsonObject.put("storageReport", storageReportDto.getStorageReport());
                jsonObject.put("depotName", deportName);
                jsonObject.put("handlerName", handlerName);
                jsonObject.put("createdName", createdName);
                jsonObject.put("createdTime", createdTime);
                List<StorageReportItemDto> storageReportItemDtoList = storageReportDto.getStorageReportItemDtoList();
                jsonObject.put("storageReportItemDtoList", storageReportItemDtoList);
                jsonArray.add(jsonObject);
            }
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray, dataCount, pageSize);
    }

    @Module(ModuleEnums.AdminStorageReportNew)
    @RequestMapping(value = "ajax/new", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject newStorageReportDto(@RequestBody StorageReportDto storageReportDto){
        try {
            //生成单据编号
            String serialNumber = serialNumService.generateSerialNum(SerialNumTemplateEnums.StockInSerialNum);
            storageReportDto.getStorageReport().setSerialNumber(serialNumber);

            storageReportService.newReportDto(storageReportDto);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax删除单据和单据详情
     * @param id
     * @return
     */
    @RequestMapping(value = "ajax/del/{id}",method = RequestMethod.DELETE)
    @ResponseBody
    public
    JSONObject delStorageReportDtoByReportId(@PathVariable("id") Integer id){
        try {
            storageReportService.delReportDtoById(id);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }


    @Module(ModuleEnums.AdminStorageReportUpdate)
    @RequestMapping(value = "ajax/update",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update(@RequestBody StorageReportDto storageReportDto){
        try{
            storageReportService.updateReportDto(storageReportDto);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 计算小计金额
     *
     * @param price
     * @param quantity
     * @param id
     * @return
     */
    @RequestMapping(value = "ajax/bill", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject calculateTotalPrice(@RequestParam("price")BigDecimal price, @RequestParam("quantity")BigDecimal quantity,
                                          @RequestParam("id")int id){
        JSONObject jsonObject = new JSONObject();
        BigDecimal money = price.multiply(quantity);
        jsonObject.put("money", money);
        return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
    }

    /**
     * 导出单据
     * @param startTime
     * @param endTime
     * @param createdPartyId
     * @param depotId
     * @param handlerPartyId
     * @return
     */
    @Module(ModuleEnums.AdminStorageReportExport)
    @RequestMapping(value = "export", method = RequestMethod.GET)
    public String toReportExport(@RequestParam(value = "startTime",required = false) Date startTime,
                                 @RequestParam(value = "endTime",required = false) Date endTime,
                                 @RequestParam("createdPartyId") Integer createdPartyId,
                                 @RequestParam(value = "depotId", required = false) Integer[] depotId,
                                 @RequestParam("handlerPartyId") Integer handlerPartyId){
        try{
            List<Integer> depots = new ArrayList<Integer>();
            if (depotId != null && depotId.length > 0) {
                for (int i = 0; i < depotId.length; i++) {
                    depots.add(depotId[i]);
                }
            }
            StorageReport report = new StorageReport();
            report.setHandlerPartyId(handlerPartyId);
            report.setCreatedPartyId(createdPartyId);
            storageReportService.exportToExcel(report,startTime,endTime,depots,createdPartyId,handlerPartyId,getResponse());
            sendErrMsg("导出成功");
        }catch (Exception e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return null;
    }
}
