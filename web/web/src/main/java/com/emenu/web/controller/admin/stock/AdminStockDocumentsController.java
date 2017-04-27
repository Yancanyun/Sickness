package com.emenu.web.controller.admin.stock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.party.group.employee.EmployeeDto;
import com.emenu.common.dto.stock.DocumentsDto;
import com.emenu.common.dto.stock.DocumentsSearchDto;
import com.emenu.common.entity.stock.*;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.stock.StockDocumentsTypeEnum;
import com.emenu.common.utils.DateUtils;
import com.emenu.common.utils.URLConstants;
import com.emenu.common.utils.WebConstants;
import com.emenu.service.stock.StockDocumentsService;
import com.emenu.service.stock.StockKitchenService;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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

    @Autowired
    private StockDocumentsService stockDocumentsService;

    @Autowired
    private StockKitchenService stockKitchenService;

    @Module(ModuleEnums.AdminStockDocumentsList)
    @RequestMapping(value = {"","list"}, method = RequestMethod.GET)
    public String toList(Model model){
        try{
            List<StockKitchen> kitchenList = stockKitchenService.listStockKitchen();
            //经手人、操作人、审核人
            List<EmployeeDto> employeeDtoList = employeeService.listAll();

            model.addAttribute("kitchenList",kitchenList);
            model.addAttribute("handlerList", employeeDtoList);
            model.addAttribute("createdList", employeeDtoList);
            model.addAttribute("auditedList", employeeDtoList);
            model.addAttribute("lastMonthFirstDay", DateUtils.getLastMonthFirstDay());
            model.addAttribute("lastMonthLastDay", DateUtils.getLastMonthLastDay());
            model.addAttribute("currentMonthFirstDay", DateUtils.getCurrentMonthFirstDay());
            model.addAttribute("currentMonthLastDay", DateUtils.getCurrentMonthLastDay());
            model.addAttribute("currentDay", DateUtils.yearMonthDayFormat(DateUtils.now()));
            return "admin/stock/documents/list_home";
        } catch(SSException e) {
            sendErrMsg(e.getMessage());
            LogClerk.errLog.error(e);
            return WebConstants.sysErrorCode;
        }
    }

    @Module(value = ModuleEnums.AdminStockDocuments, extModule = ModuleEnums.AdminStockDocumentsList)
    @RequestMapping(value = "ajax/list/{pageNo}",method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxList(@PathVariable("pageNo")Integer pageNo,
                         @RequestParam("pageSize") Integer pageSize) throws SSException{
        pageSize = (pageSize == null || pageSize <= 0) ? DEFAULT_PAGE_SIZE : pageSize;
        int offset = 0;
        if (Assert.isNotNull(pageNo)) {
            pageNo = pageNo <= 0 ? 0 : pageNo - 1;
            offset = pageNo * pageSize;
        }
        List<DocumentsDto> list = Collections.emptyList();
        try{
            list = stockDocumentsService.listDocumentsDtoByPage(offset,pageSize);
        }catch(SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONArray jsonArray = new JSONArray();
        for (DocumentsDto documentsDto: list){
            JSONObject jsonObject = new JSONObject();
            StockDocuments stockDocument = documentsDto.getStockDocuments();
            jsonObject.put("id", stockDocument.getId());
            jsonObject.put("type", stockDocument.getType());
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            jsonObject.put("createdTime",sdf.format(stockDocument.getCreatedTime()));
            jsonObject.put("date",sdf.format(stockDocument.getCreatedTime()));
            jsonObject.put("serialNumber",stockDocument.getSerialNumber());
            jsonObject.put("createdPartyId",stockDocument.getCreatedPartyId());
            jsonObject.put("createdName",stockDocument.getCreatedName());
            jsonObject.put("handlerPartyId",stockDocument.getHandlerPartyId());
            jsonObject.put("handlerName",stockDocument.getHandlerName());
            jsonObject.put("auditPartyId",stockDocument.getAuditName());
            jsonObject.put("auditName",stockDocument.getAuditName());
            jsonObject.put("isAudited",stockDocument.getIsAudited());
            jsonObject.put("isSettlemented",stockDocument.getIsSettled());
            jsonObject.put("money",stockDocument.getMoney());
            jsonObject.put("depotId",stockDocument.getKitchenId());
            jsonObject.put("depotName",(stockKitchenService.queryById(stockDocument.getKitchenId())).getName());
            jsonObject.put("comment",stockDocument.getComment());
            List<StockDocumentsItem> stockDocumentsItemList = documentsDto.getStockDocumentsItemList();
            JSONArray itemList = new JSONArray();
            if(Assert.isNotNull(stockDocumentsItemList)){
                for(StockDocumentsItem stockDocumentsItem: stockDocumentsItemList){
                    JSONObject documentItem = new JSONObject();
                    documentItem.put("itemId",stockDocumentsItem.getId());
                    if(documentsDto.getStockDocuments().getType() == StockDocumentsTypeEnum.StockInDocuments.getId()){
                        try{
                            StockItem item = stockItemService.queryById(stockDocumentsItem.getItemId());
                            documentItem.put("itemName",item.getName());//物品名称
                            documentItem.put("itemNumber",item.getItemNumber());//物品编号
                            Specifications specification = specificationsService.queryById(stockDocumentsItem.getSpecificationId());
                            String orderUnitName = unitService.queryById(specification.getOrderUnitId()).getName();
                            String storageUnitName = unitService.queryById(specification.getStorageUnitId()).getName();
                            String costCardUnitName = unitService.queryById(specification.getCostCardUnitId()).getName();

                            String itemSpecification = "1" + orderUnitName + "*"
                                    + specification.getOrderToStorage() + "*" + storageUnitName
                                    + specification.getStorageToCost() + "*" + costCardUnitName;

                            documentItem.put("itemSpecification",itemSpecification);//规格
                            documentItem.put("orderUnitName",orderUnitName);//订货单位
                            documentItem.put("orderQuantity",stockDocumentsItem.getQuantity());//订货量
                            documentItem.put("storageUnitName",storageUnitName);//库存单位
                            documentItem.put("storageQuantity",item.getStorageQuantity());//库存量
                            documentItem.put("price",stockDocumentsItem.getPrice());//金额
                            documentItem.put("count",stockDocumentsItem.getQuantity());//数量
                            itemList.add(documentItem);
                        }catch (SSException e){
                            LogClerk.errLog.error(e);
                            return sendErrMsgAndErrCode(e);
                        }
                    }
                }
            }
            jsonObject.put("reportItem",itemList);
            jsonArray.add(jsonObject);
        }
        int dataCount = 0;
        try {
            dataCount = stockDocumentsService.count();
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        return sendJsonArray(jsonArray, dataCount);
    }

    /**
     * 条件导出单据
     * @param createdPartyId
     * @param handlerPartyId
     * @param auditPartyId
     * @param startTime
     * @param endTime
     * @param isAudited
     * @param isSettlemented
     * @param depotId
     */
    @Module(ModuleEnums.AdminStockDocumentsExport)
    @RequestMapping(value = "exportbycd", method = RequestMethod.GET)
    public void toReportExport(DocumentsSearchDto searchDto,
                                HttpServletResponse response){
        try{
            System.out.println(searchDto.getKitchenId());
            StockDocuments documents = new StockDocuments();
            documents.setCreatedPartyId(searchDto.getCreatedPartyId());
            documents.setHandlerPartyId(searchDto.getHandlerPartyId());
            documents.setIsAudited(searchDto.getIsAudited());
            documents.setIsSettled(searchDto.getIsSettled());
            documents.setAuditPartyId(searchDto.getAuditPartyId());
            documents.setKitchenId(searchDto.getKitchenId());
            Date startTime = searchDto.getStartTime();
            Date endTime = searchDto.getEndTime();
            stockDocumentsService.exportToExcel(documents, startTime, endTime, response);
            sendErrMsg("导出成功");
        }catch (Exception e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
    }

}
