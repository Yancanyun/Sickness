package com.emenu.web.controller.admin.storage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.party.group.employee.EmployeeDto;
import com.emenu.common.dto.storage.StorageReportDto;
import com.emenu.common.entity.party.group.employee.Employee;
import com.emenu.common.entity.storage.StorageDepot;
import com.emenu.common.entity.storage.StorageReport;
import com.emenu.common.entity.storage.StorageReportItem;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.common.utils.WebConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;


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
            depotList = storageDepotService.listAll();
            handlerList = employeeService.listAll();
            createdList = employeeService.listAll();
            model.addAttribute("depotList", depotList);
            model.addAttribute("handlerList",handlerList);
            model.addAttribute("createdList",createdList);
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
/*    @Module(ModuleEnums.AdminStorageReportList)
    @RequestMapping(value = "ajax/list/{curPage}",method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(@PathVariable("curPage") Integer curPage,
                         @RequestParam Integer pageSize) {


    }*/


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
        if (depotId!=null&&depotId.length>0){
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
            //storageReportDtoList = storageReportService.listReportDtoByPage(curPage, pageSize);
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
                String createdName = employeeService.queryByPartyId(storageReportDto.getStorageReport().getHandlerPartyId()).getName();

                jsonObject.put("storageReport", storageReportDto.getStorageReport());
                jsonObject.put("depotName",deportName);
                jsonObject.put("handlerName",handlerName);
                jsonObject.put("createdName",createdName);
                jsonObject.put("storageReportItemList", storageReportDto.getStorageReportItemList());
                jsonArray.add(jsonObject);
            }
        }catch (SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray, dataCount, pageSize);
    }

    @RequestMapping(value = "new",method = RequestMethod.POST)
    public String newStorageReportDto(@RequestParam("storageReport")StorageReport storageReport,
                                      @RequestParam("storageReportItemList")List<StorageReportItem> storageReportItemList,
                                      RedirectAttributes redirectAttributes){
        StorageReportDto storageReportDto = new StorageReportDto();
        try {
            //生成单据编号
            String serialNumber = serialNumService.generateSerialNum(SerialNumTemplateEnums.StockInSerialNum);
            storageReport.setSerialNumber(serialNumber);
            //数据存入Dto
            storageReportDto.setStorageReport(storageReport);
            storageReportDto.setStorageReportItemList(storageReportItemList);
            storageReportService.newReportDto(storageReportDto);
            String successUrl = "/" + URLConstants.ADMIN_STORAGE_REPORT_URL;
            redirectAttributes.addFlashAttribute("msg","编辑成功");
            return "redirect:" + successUrl;
        } catch (SSException e) {
            sendErrMsg(e.getMessage());
            LogClerk.errLog.error(e);
            //String failedUrl = "/" + URLConstants.ADMIN_STORAGE_REPORT_URL + "/toupdate/"+"{"+String .valueOf(storageReport.ge)+"}";
            //返回添加失败信息
            redirectAttributes.addFlashAttribute("msg", "编辑失败");
            //返回添加页
            //return "redirect:" + failedUrl;
            return null;
        }
    }

    /**
     * ajax删除单据和单据详情
     * @param reportId
     * @return
     */
    @RequestMapping(value = "ajax/del/{reportId}",method = RequestMethod.DELETE)
    @ResponseBody
    public
    JSONObject delStorageReportDtoByReportId(@PathVariable("reportId") Integer reportId){
        try {
            storageReportService.delReportDtoById(reportId);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }


    @RequestMapping(value = "update",method = RequestMethod.POST)
    public String update(@RequestParam("storageReportDto")StorageReportDto storageReportDto,
                         RedirectAttributes redirectAttributes){
        try{
            storageReportService.updateReportDto(storageReportDto);
            String successUrl = "/" + URLConstants.ADMIN_STORAGE_REPORT_URL;
            //返回添加成功信息
            redirectAttributes.addFlashAttribute("msg", "编辑成功");
            //返回列表页
            return "redirect:" + successUrl;
        } catch (SSException e) {
            sendErrMsg(e.getMessage());
            LogClerk.errLog.error(e);
            int reportId = storageReportDto.getStorageReport().getId();
            String failedUrl = "/" + URLConstants.ADMIN_TABLE_URL + "/toupdate/"+"{"+String .valueOf(reportId)+"}";
            //返回添加失败信息
            redirectAttributes.addFlashAttribute("msg", "编辑失败");
            //返回添加页
            return "redirect:" + failedUrl;
        }
    }
}
