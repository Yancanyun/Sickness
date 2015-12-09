package com.emenu.web.controller.admin.storage;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.storage.StorageReportDto;
import com.emenu.common.entity.storage.StorageReport;
import com.emenu.common.entity.storage.StorageReportItem;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
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
    @Module(ModuleEnums.AdminVipInfoList)
    @RequestMapping(value = {"tolist"}, method = RequestMethod.GET)
    public String toList(){
        return "admin/storage/report/list_home";
    }


    /**
     * 分页获取单据信息
     * @param curPage
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "ajax/list/{pageNo}",method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(@PathVariable("curPage") Integer curPage,
                         @RequestParam("pageSize") Integer pageSize) {
        int dataCount = 0;
        try {
            dataCount = storageReportService.count();
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        List<StorageReportDto> storageReportDtoList = Collections.emptyList();
        try {
            storageReportDtoList = storageReportService.listReportDtoByPage(curPage, pageSize);
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
                jsonObject.put("deportName",deportName);
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
