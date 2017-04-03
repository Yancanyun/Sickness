package com.emenu.web.controller.admin.stock;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.party.group.employee.EmployeeDto;
import com.emenu.common.dto.stock.DocumentsDto;
import com.emenu.common.entity.stock.StockDocuments;
import com.emenu.common.entity.stock.StockDocumentsItem;
import com.emenu.common.entity.stock.StockItem;
import com.emenu.common.entity.stock.StockKitchen;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.stock.StockDocumentsTypeEnum;
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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
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
    public String toList(HttpServletRequest request, HttpServletResponse response,Model model){
        try{
            List<StockKitchen> kitchenList = stockKitchenService.listStockKitchen();
            //经手人、操作人、审核人
            List<EmployeeDto> employeeDtoList = employeeService.listAll();

            model.addAttribute("kitchenList",kitchenList);
            model.addAttribute("handlerList", employeeDtoList);
            model.addAttribute("createdList", employeeDtoList);
            model.addAttribute("auditedList", employeeDtoList);
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
            jsonObject.put("createdTime",stockDocument.getCreatedTime());
            jsonObject.put("date",stockDocument.getCreatedTime());
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
            jsonObject.put("depotName",(stockKitchenService.queryStockKitchenDetails(stockDocument.getKitchenId())).getName());
            jsonObject.put("comment",stockDocument.getComment());
            List<StockDocumentsItem> stockDocumentsItemList = Collections.emptyList();
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
                            documentItem.put("itemSpecification",stockDocumentsItem.getSpecificationId());//规格
                            documentItem.put("orderUnitName",item.getStorageQuantity());//入库单位
                            documentItem.put("orderQuantity",stockDocumentsItem.getQuantity());//入库量
                            documentItem.put("storageUnitName",stockDocumentsItem.getUnitId());//单位
                            documentItem.put("price",stockDocumentsItem.getPrice());//金额
                            documentItem.put("count",stockDocumentsItem.getQuantity());//数量
                            itemList.add(item);
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

}
