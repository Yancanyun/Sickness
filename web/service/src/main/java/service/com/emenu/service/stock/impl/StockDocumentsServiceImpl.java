package com.emenu.service.stock.impl;

import com.emenu.common.dto.stock.DocumentsDto;
import com.emenu.common.dto.stock.DocumentsSearchDto;
import com.emenu.common.entity.party.group.employee.Employee;
import com.emenu.common.entity.stock.*;
import com.emenu.common.enums.ExcelExportTemplateEnums;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.enums.stock.StockDocumentsTypeEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.exception.PartyException;
import com.emenu.common.utils.DateUtils;
import com.emenu.common.utils.EntityUtil;
import com.emenu.mapper.stock.StockDocumentsItemMapper;
import com.emenu.mapper.stock.StockDocumentsMapper;
import com.emenu.service.dish.UnitService;
import com.emenu.service.other.SerialNumService;
import com.emenu.service.party.group.employee.EmployeeService;
import com.emenu.service.stock.*;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.common.util.IOUtil;
import com.pandawork.core.framework.dao.CommonDao;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * StockDocumentsServiceImpl
 *
 * @author renhongshuai
 *         Created by admin.
 * @Time 2017/3/7 15:40.
 */
@Service("stockDocumentsService")
public class StockDocumentsServiceImpl implements StockDocumentsService {

    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;

    @Autowired
    private SerialNumService serialNumService;

    @Autowired
    private StockDocumentsItemService stockDocumentsItemService;

    @Autowired
    private StockItemService stockItemService;

    @Autowired
    private StockDocumentsMapper stockDocumentsMapper;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private SpecificationsService specificationsService;

    @Autowired
    private StockKitchenItemService stockKitchenItemService;

    @Autowired
    private StockKitchenService stockKitchenService;

    @Autowired
    private StockDocumentsItemMapper stockDocumentsItemMapper;

    @Autowired
    private UnitService unitService;


    /**
     * 添加单据Dto
     *
     * @param documentsDto
     * @throws SSException
     */

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void newDocumentsDto(DocumentsDto documentsDto) throws SSException {

        try {
            //判断单据类型
            if (Assert.isNull(documentsDto.getStockDocuments().getType()) ||
                    Assert.lessOrEqualZero(documentsDto.getStockDocuments().getType())) {
                throw SSException.get(EmenuException.DocumentsTypeError);
            }
            //判断是否为空
            if (Assert.isNull(documentsDto.getStockDocuments())) {
                throw SSException.get(EmenuException.DocumentsIsNotNull);
            }
            //根据单据类型生成单据编号
            String serialNumber = "";
            switch (documentsDto.getStockDocuments().getType()) {
                case 1:
                    serialNumber = serialNumService.generateSerialNum(SerialNumTemplateEnums.StockInSerialNum);
                    break;
                case 2:
                    serialNumber = serialNumService.generateSerialNum(SerialNumTemplateEnums.StockOutSerialNum);
                    break;
                case 3:
                    serialNumber = serialNumService.generateSerialNum(SerialNumTemplateEnums.StockBackSerialNum);
                    break;
                case 4:
                    serialNumber = serialNumService.generateSerialNum(SerialNumTemplateEnums.IncomeOnSerialNum);
                    break;
                case 5:
                    serialNumber = serialNumService.generateSerialNum(SerialNumTemplateEnums.LossOnSerialNum);
                    break;
                default:
                    throw SSException.get(EmenuException.DocumentsTypeError);
            }
            documentsDto.getStockDocuments().setSerialNumber(serialNumber);
            //若为入库单则计算入库单总计金额
            if (documentsDto.getStockDocuments().getType() == StockDocumentsTypeEnum.StockInDocuments.getId()) {
                //单据明细判空
                if (Assert.isNull(documentsDto.getStockDocumentsItemList()) || Assert.lessOrEqualZero(documentsDto.getStockDocumentsItemList().size())) {
                    throw SSException.get(EmenuException.DocumentsItemListIsNotNull);
                }
                //单据总计金额
                BigDecimal documentsMoney = new BigDecimal("0.00");
                for (StockDocumentsItem stockDocumentsItem : documentsDto.getStockDocumentsItemList()) {
                    documentsMoney = documentsMoney.add(stockDocumentsItem.getPrice());
                }
                documentsDto.getStockDocuments().setMoney(documentsMoney);
            }
            //添加到单据表返回实体
            StockDocuments stockDocuments = this.newDocuments(documentsDto.getStockDocuments());
            //设置单据明细表中的单据Id
            for (StockDocumentsItem stockDocumentsItem : documentsDto.getStockDocumentsItemList()) {
                stockDocumentsItem.setDocumentsId(stockDocuments.getId());
                //添加单据明细表
                stockDocumentsItemService.newDocumentsItem(stockDocumentsItem);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertDocumentsFail, e);
        }
    }

    /**
     * 修改单据Dto
     *
     * @param documentsDto
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateDocumentsDto(DocumentsDto documentsDto) throws SSException {
        try {
            if (Assert.isNull(documentsDto)) {
                throw SSException.get(EmenuException.DocumentsDtoIsNotNull);
            }
            if (Assert.isNull(documentsDto.getStockDocuments())) {
                throw SSException.get(EmenuException.DocumentsIsNotNull);
            }
            if (Assert.lessOrEqualZero(documentsDto.getStockDocumentsItemList().size()) || Assert.isEmpty(documentsDto.getStockDocumentsItemList())) {
                throw SSException.get(EmenuException.DocumentsItemListIsNotNull);
            }
            if (Assert.isNull(documentsDto.getStockDocuments().getType()) || Assert.lessOrEqualZero(documentsDto.getStockDocuments().getType())) {
                throw SSException.get(EmenuException.DocumentsTypeError);
            }
            //获取原始数据
            StockDocuments stockDocumentsNew = documentsDto.getStockDocuments();
            List<StockDocumentsItem> stockDocumentsItemNewList = documentsDto.getStockDocumentsItemList();
            StockDocuments stockDocumentsOld = queryById(documentsDto.getStockDocuments().getId());
            List<StockDocumentsItem> stockDocumentsItemOldList = stockDocumentsItemService.queryByDocumentsId(documentsDto.getStockDocuments().getId());
            //入库单的时候重新计算单据金额
            if (documentsDto.getStockDocuments().getType() == StockDocumentsTypeEnum.StockInDocuments.getId()) {
                BigDecimal moneyNew = new BigDecimal("0.00");
                for (StockDocumentsItem stockDocumentsItem : stockDocumentsItemNewList) {
                    moneyNew = moneyNew.add(stockDocumentsItem.getPrice());
                }
                stockDocumentsNew.setMoney(moneyNew);
            }
            //修改单据
            updateDocuments(stockDocumentsNew);
            if (stockDocumentsItemOldList.size() > 0) {
                if (stockDocumentsItemNewList.size() > 0) {
                    for (StockDocumentsItem stockDocumentsItemOld : stockDocumentsItemOldList) {
                        for (StockDocumentsItem stockDocumentsItemNew : stockDocumentsItemNewList) {
                            if (Assert.isNotNull(stockDocumentsItemNew.getId())
                                    && stockDocumentsItemNew.getItemId().equals(stockDocumentsItemOld.getItemId())
                                    && stockDocumentsItemNew.getSpecificationId().equals(stockDocumentsItemOld.getSpecificationId())) {
                                stockDocumentsItemNew.setId(stockDocumentsOld.getId());
                                stockDocumentsItemService.updateDocumentsItem(stockDocumentsItemNew);
                                stockDocumentsItemNewList.remove(stockDocumentsItemNew);
                                stockDocumentsItemOldList.remove(stockDocumentsItemOld);
                            }
                        }
                    }
                    if (stockDocumentsItemNewList.size() > 0) {
                        for (StockDocumentsItem stockDocumentsItem : stockDocumentsItemNewList) {
                            stockDocumentsItemService.newDocumentsItem(stockDocumentsItem);
                        }
                    }
                    if (stockDocumentsItemOldList.size() > 0) {
                        for (StockDocumentsItem stockDocumentsItem : stockDocumentsItemNewList) {
                            stockDocumentsItemService.delDocumentsItemById(stockDocumentsItem.getId());
                        }
                    }
                } else {
                    stockDocumentsItemService.delByDocumentsId(stockDocumentsOld.getId());
                }
            } else {
                for (StockDocumentsItem stockDocumentsItem : stockDocumentsItemNewList) {
                    stockDocumentsItemService.newDocumentsItem(stockDocumentsItem);
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateDocumentsFail, e);
        }
    }

    /**
     * 修改单据
     *
     * @param stockDocuments
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateDocuments(StockDocuments stockDocuments) throws SSException {
        try {
            if (Assert.isNull(stockDocuments)) {
                throw SSException.get(EmenuException.DocumentsIsNotNull);
            }
            if (Assert.isNull(stockDocuments.getId()) && Assert.lessOrEqualZero(stockDocuments.getId())) {
                throw SSException.get(EmenuException.DocumentsIdError);
            }
            commonDao.update(stockDocuments);
        } catch (Exception e) {
            throw SSException.get(EmenuException.UpdateDocumentsFail);
        }
    }

    /**
     * 修改单据审核状态
     *
     * @param documentsId
     * @param isAudited
     * @return
     * @throws SSException
     */
    @Override
    public boolean updateIsAudited(int documentsId, int isAudited) throws SSException {
        try {
            if (Assert.lessOrEqualZero(documentsId) ||
                    Assert.lessOrEqualZero(isAudited)) {
                throw SSException.get(EmenuException.DocumentsIdOrStatusIdError);
            }
            updateStockItemInfo(documentsId);
            stockDocumentsMapper.updateIsAudited(documentsId, isAudited);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
        return true;
    }

    /**
     * 修改单据结算状态
     *
     * @param documentsId
     * @param isSettled
     * @return
     * @throws SSException
     */
    @Override
    public boolean updateIsSettled(int documentsId, int isSettled) throws SSException {
        try {
            if (Assert.lessOrEqualZero(documentsId) ||
                    Assert.lessOrEqualZero(isSettled)) {
                throw SSException.get(EmenuException.DocumentsIdOrStatusIdError);
            }
            stockDocumentsMapper.updateIsSettled(documentsId, isSettled);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
        return true;
    }

    /**
     * 导出到Excel表格
     *
     * @param stockDocuments
     * @param startTime
     * @param endTime
     * @param deports
     * @param handlerPartyId
     * @param createdPartyId
     * @param response
     * @throws SSException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void exportToExcel(StockDocuments stockDocuments,Date startTime, Date endTime,
                              HttpServletResponse response) throws SSException{
        OutputStream outputStream = null;
        List<DocumentsDto> documentsDtoList = Collections.emptyList();
        try{
            DocumentsSearchDto documentsSearchDto = new DocumentsSearchDto();
            documentsSearchDto.setAuditPartyId(stockDocuments.getAuditPartyId());
            documentsSearchDto.setCreatedPartyId(stockDocuments.getCreatedPartyId());
            documentsSearchDto.setKitchenId(stockDocuments.getKitchenId());
            documentsSearchDto.setEndTime(endTime);
            documentsSearchDto.setHandlerPartyId(stockDocuments.getHandlerPartyId());
            documentsSearchDto.setIsAudited(stockDocuments.getIsAudited());
            documentsSearchDto.setIsSettled(stockDocuments.getIsSettled());
            documentsSearchDto.setStartTime(startTime);
            documentsDtoList = this.listDocumentsDtoBySearchDto(documentsSearchDto);
            for (DocumentsDto documentsDto : documentsDtoList){
                EntityUtil.setNullFieldDefault(documentsDto);
            }
            //设置输出流
            //设置excel文件名和sheetName
            String filename = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            filename = ExcelExportTemplateEnums.AdminStockDocumentsList.getName()+sdf.format(new Date());
            response.setContentType("application/msexcel");
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(filename.getBytes("gbk"), "ISO8859-1") + ".xls");
            outputStream = response.getOutputStream();
            //获取模板
            InputStream tplStream = IOUtil.getFileAsStream(ExcelExportTemplateEnums.AdminStockDocumentsList.getFilePath());
            Workbook tplWorkBook = Workbook.getWorkbook(tplStream);
            WritableWorkbook outBook = Workbook.createWorkbook(outputStream,tplWorkBook);
            //获取sheet往sheet中写入数据
            WritableSheet sheet = outBook.getSheet(0);

            int row = 3;
            int rowchildren = 0;
            int up = 3;
            for(DocumentsDto documentsDto : documentsDtoList){
                WritableCellFormat cellFormat=new WritableCellFormat();
                cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
                cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                cellFormat.setWrap(true);
                //单据类型
                Label labelDocumentsType = new Label(0 , row + rowchildren , StockDocumentsTypeEnum.valueOf(documentsDto.getStockDocuments().getType()).getName());
                sheet.addCell(labelDocumentsType);
                //单据编号
                Label labelSerialNumber = new Label(1 , row + rowchildren , documentsDto.getStockDocuments().getSerialNumber());
                sheet.addCell(labelSerialNumber);
                //存放点(厨房)
                String kitchenName = "";
                if (documentsDto.getStockDocuments().getKitchenId() != 0) {
                    kitchenName = stockKitchenService.queryById(documentsDto.getStockDocuments().getKitchenId()).getName();
                } else {
                    kitchenName = "无";
                }
                Label labelKitchen = new Label(2 , row + rowchildren ,kitchenName);
                sheet.addCell(labelKitchen);
                //经手人
                String handlerName = employeeService.queryByPartyIdWithoutDelete(documentsDto.getStockDocuments().getHandlerPartyId()).getName();
                Label labelHandlerName = new Label(3 , row + rowchildren ,handlerName);
                sheet.addCell(labelHandlerName);
                //操作人
                String createdName = employeeService.queryByPartyIdWithoutDelete(documentsDto.getStockDocuments().getCreatedPartyId()).getName();
                Label labelCreatedName = new Label(4 , row + rowchildren , createdName);
                sheet.addCell(labelCreatedName);
                //审核人
                String auditName = null;
                if(documentsDto.getStockDocuments().getAuditPartyId() != 0){
                    auditName = employeeService.queryByPartyIdWithoutDelete(documentsDto.getStockDocuments().getAuditPartyId()).getName();
                }else{
                    auditName = "无";
                }
                Label labelAuditName = new Label(5 , row + rowchildren , auditName);
                sheet.addCell(labelAuditName);
                //总金额
                Label labelMoney = new Label(6, row + rowchildren, documentsDto.getStockDocuments().getMoney().toString());
                sheet.addCell(labelMoney);
                // 单据备注
                String comment = ("".equals(documentsDto.getStockDocuments().getComment())?"无":documentsDto.getStockDocuments().getComment());
                Label labelReportComment = new Label(7, row + rowchildren, comment);
                sheet.addCell(labelReportComment);
                // 日期
                String createdTime = DateUtils.yearMonthDayFormat(documentsDto.getStockDocuments().getCreatedTime());
                Label labelCreatedTime = new Label(8, row + rowchildren, createdTime);
                sheet.addCell(labelCreatedTime);
                // 物品列表
                List<StockDocumentsItem> stockDocumentsItemList = documentsDto.getStockDocumentsItemList();
                for (StockDocumentsItem stockDocumentsItem : stockDocumentsItemList) {
                        // 物品名称
                        Label labelItemName = new Label(9, row + rowchildren, stockItemService.queryById(stockDocumentsItem.getItemId()).getName());
                        sheet.addCell(labelItemName);
                        // 物品编号
                        String itemNumber = null;
                        if (stockDocumentsItem.getItemId() != null &&
                                !"".equals(stockDocumentsItem.getItemId()) &&
                                (stockItemService.queryById(stockDocumentsItem.getItemId())) != null) {
                            itemNumber = stockItemService.queryById(stockDocumentsItem.getItemId()).getItemNumber();
                        } else {
                            itemNumber = "无";
                        }
                        Label labelItemNumber = new Label(10, row + rowchildren, itemNumber);
                        sheet.addCell(labelItemNumber);

                        // 物品规格
                        Label labelSpecification = new Label(11, row + rowchildren, specificationsService.toSpecificationString(stockDocumentsItem.getSpecificationId()));
                        sheet.addCell(labelSpecification);

                        // 物品数量(库存订货)
                        Label labelQuantity = new Label(12 , row + rowchildren, stockDocumentsItem.getQuantity().toString());
                        sheet.addCell(labelQuantity);

                        // 物品单位(库存订货)
                        String unit = unitService.queryById(stockDocumentsItem.getUnitId()).getName();
                        Label labelUnit = new Label(13 , row + rowchildren, unit);
                        sheet.addCell(labelUnit);

                        // 物品价格
                        Label labelPrice = new Label(14 , row + rowchildren, stockDocumentsItem.getPrice().toString());
                        sheet.addCell(labelPrice);
                        rowchildren++;
                }
                  // 单元格合并函数
                if(stockDocumentsItemList.size()>1){
                    sheet.mergeCells(0, row, 0, row + rowchildren-1);
                    sheet.mergeCells(1, row, 1, row + rowchildren-1);
                    sheet.mergeCells(2, row, 2, row + rowchildren-1);
                    sheet.mergeCells(3, row, 3, row + rowchildren-1);
                    sheet.mergeCells(4, row, 4, row + rowchildren-1);
                    sheet.mergeCells(5, row, 5, row + rowchildren-1);
                    sheet.mergeCells(6, row, 6, row + rowchildren-1);
                    sheet.mergeCells(7, row, 7, row + rowchildren-1);
                    sheet.mergeCells(8, row, 8, row + rowchildren-1);
                }
                if(rowchildren<=1){
                    row ++;
                }else{
                    row = row + rowchildren;
                }
            }
            outBook.write();
            outBook.close();
            tplWorkBook.close();
            tplStream.close();
            outputStream.close();
        }catch(Exception e){
            LogClerk.errLog.error(e);
            response.setContentType("text/html");
            response.setHeader("Content-Type", "text/html");
            response.setHeader("Content-disposition", "");
            response.setCharacterEncoding("UTF-8");
            try {
                String eMsg = "系统内部异常，请联系管理员！";
                eMsg= java.net.URLEncoder.encode(eMsg.toString(),"UTF-8");
                response.sendRedirect("/admin/storage/report?eMsg="+eMsg);
                outputStream.close();
            } catch (IOException e1) {
                LogClerk.errLog.error(e1.getMessage());
            }
            throw SSException.get(EmenuException.ExportReportFail, e);
        }
        finally {
            if(outputStream != null){
                try{
                    outputStream.flush();
                    outputStream.close();
                }catch(Exception e){
                    LogClerk.errLog.error(e);
                    throw SSException.get(EmenuException.ExportReportFail, e);
                }
            }
        }

    }

    /**
     * 导出所有单据
     *
     * @param response
     * @throws SSException
     */
    @Override
    @Transactional(rollbackFor = {Exception.class, SSException.class}, propagation = Propagation.REQUIRED)
    public void exportToExcelAll(HttpServletResponse response) throws SSException{
        OutputStream outputStream = null;
        List<DocumentsDto> documentsDtoList = new ArrayList<DocumentsDto>();
        List<StockDocuments> stockDocumentsList = new ArrayList<StockDocuments>();
        List<StockDocumentsItem> stockDocumentsItemList = new ArrayList<StockDocumentsItem>();
        try{
            stockDocumentsList = listAll();
            //将查询的单据信息赋值给单据Dto
            for (StockDocuments stockDocument : stockDocumentsList) {
                DocumentsDto documentsDto = new DocumentsDto();
                List<StockDocumentsItem> stockDocumentsItems = new ArrayList<StockDocumentsItem>();
                //根据单据id获取单据详情信息
                stockDocumentsItems = stockDocumentsItemService.queryByDocumentsId(stockDocument.getId());
                //数据存入documentsDto
                documentsDto.setStockDocuments(stockDocument);
                documentsDto.setStockDocumentsItemList(stockDocumentsItems);
                documentsDtoList.add(documentsDto);
            }
            //设置输出流
            //设置excel文件名和sheetName
            String filename = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            filename = ExcelExportTemplateEnums.AdminStockDocumentsList.getName()+sdf.format(new Date());
            response.setContentType("application/msexcel");
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(filename.getBytes("gbk"), "ISO8859-1") + ".xls");
            outputStream = response.getOutputStream();
            //获取模板
            InputStream tplStream = IOUtil.getFileAsStream(ExcelExportTemplateEnums.AdminStockDocumentsList.getFilePath());
            Workbook tplWorkBook = Workbook.getWorkbook(tplStream);
            WritableWorkbook outBook = Workbook.createWorkbook(outputStream,tplWorkBook);
            //获取sheet往sheet中写入数据
            WritableSheet sheet = outBook.getSheet(0);

            int row = 3;
            int rowchildren = 0;
            int up = 3;
            for(DocumentsDto documentsDto : documentsDtoList){
                WritableCellFormat cellFormat=new WritableCellFormat();
                cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
                cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                cellFormat.setWrap(true);
                //单据类型
                Label labelDocumentsType = new Label(0 , row + rowchildren , StockDocumentsTypeEnum.valueOf(documentsDto.getStockDocuments().getType()).getName());
                sheet.addCell(labelDocumentsType);
                //单据编号
                Label labelSerialNumber = new Label(1 , row + rowchildren , documentsDto.getStockDocuments().getSerialNumber());
                sheet.addCell(labelSerialNumber);
                //存放点(厨房)
                String kitchenName = "";
                if (documentsDto.getStockDocuments().getKitchenId() != 0) {
                    kitchenName = stockKitchenService.queryById(documentsDto.getStockDocuments().getKitchenId()).getName();
                } else {
                    kitchenName = "无";
                }
                Label labelKitchen = new Label(2 , row + rowchildren ,kitchenName);
                sheet.addCell(labelKitchen);
                //经手人
                String handlerName = employeeService.queryByPartyIdWithoutDelete(documentsDto.getStockDocuments().getHandlerPartyId()).getName();
                Label labelHandlerName = new Label(3 , row + rowchildren ,handlerName);
                sheet.addCell(labelHandlerName);
                //操作人
                String createdName = employeeService.queryByPartyIdWithoutDelete(documentsDto.getStockDocuments().getCreatedPartyId()).getName();
                Label labelCreatedName = new Label(4 , row + rowchildren , createdName);
                sheet.addCell(labelCreatedName);
                //审核人
                String auditName = null;
                if(documentsDto.getStockDocuments().getAuditPartyId() != 0){
                    auditName = employeeService.queryByPartyIdWithoutDelete(documentsDto.getStockDocuments().getAuditPartyId()).getName();
                }else{
                    auditName = "无";
                }
                Label labelAuditName = new Label(5 , row + rowchildren , auditName);
                sheet.addCell(labelAuditName);
                //总金额
                Label labelMoney = new Label(6, row + rowchildren, documentsDto.getStockDocuments().getMoney().toString());
                sheet.addCell(labelMoney);
                // 单据备注
                String comment = ("".equals(documentsDto.getStockDocuments().getComment())?"无":documentsDto.getStockDocuments().getComment());
                Label labelReportComment = new Label(7, row + rowchildren, comment);
                sheet.addCell(labelReportComment);
                // 日期
                String createdTime = DateUtils.yearMonthDayFormat(documentsDto.getStockDocuments().getCreatedTime());
                Label labelCreatedTime = new Label(8, row + rowchildren, createdTime);
                sheet.addCell(labelCreatedTime);
                // 物品列表
                stockDocumentsItemList = documentsDto.getStockDocumentsItemList();
                for (StockDocumentsItem stockDocumentsItem : stockDocumentsItemList) {
                    // 物品名称
                    Label labelItemName = new Label(9, row + rowchildren, stockItemService.queryById(stockDocumentsItem.getItemId()).getName());
                    sheet.addCell(labelItemName);
                    // 物品编号
                    String itemNumber = null;
                    if (stockDocumentsItem.getItemId() != null &&
                            !"".equals(stockDocumentsItem.getItemId()) &&
                            (stockItemService.queryById(stockDocumentsItem.getItemId())) != null) {
                        itemNumber = stockItemService.queryById(stockDocumentsItem.getItemId()).getItemNumber();
                    } else {
                        itemNumber = "无";
                    }
                    Label labelItemNumber = new Label(10, row + rowchildren, itemNumber);
                    sheet.addCell(labelItemNumber);

                    // 物品规格
                    Label labelSpecification = new Label(11, row + rowchildren, specificationsService.toSpecificationString(stockDocumentsItem.getSpecificationId()));
                    sheet.addCell(labelSpecification);

                    // 物品数量(库存订货)
                    Label labelQuantity = new Label(12 , row + rowchildren, stockDocumentsItem.getQuantity().toString());
                    sheet.addCell(labelQuantity);

                    // 物品单位(库存订货)
                    String unit = unitService.queryById(stockDocumentsItem.getUnitId()).getName();
                    Label labelUnit = new Label(13 , row + rowchildren, unit);
                    sheet.addCell(labelUnit);

                    // 物品价格
                    Label labelPrice = new Label(14 , row + rowchildren, stockDocumentsItem.getPrice().toString());
                    sheet.addCell(labelPrice);
                    rowchildren++;
                }
               // 单元格合并函数
                if(stockDocumentsItemList.size()>=1){
                    sheet.mergeCells(0, up , 0, up+stockDocumentsItemList.size()-1 );
                    sheet.mergeCells(1, up, 1, up+stockDocumentsItemList.size()-1);
                    sheet.mergeCells(2, up, 2, up+stockDocumentsItemList.size()-1);
                    sheet.mergeCells(3, up, 3, up+stockDocumentsItemList.size()-1);
                    sheet.mergeCells(4, up, 4, up+stockDocumentsItemList.size()-1);
                    sheet.mergeCells(5, up, 5, up+stockDocumentsItemList.size()-1);
                    sheet.mergeCells(6, up, 6, up+stockDocumentsItemList.size()-1);
                    sheet.mergeCells(7, up, 7, up+stockDocumentsItemList.size()-1);
                    sheet.mergeCells(8, up, 8, up+stockDocumentsItemList.size()-1);
                    up = up+stockDocumentsItemList.size();
                    row++;
                    rowchildren--;
                }else{
                    row++;
                    rowchildren--;
                }
            }
            outBook.write();
            outBook.close();
            tplWorkBook.close();
            tplStream.close();
            outputStream.close();
        }catch(Exception e){
            LogClerk.errLog.error(e);
            response.setContentType("text/html");
            response.setHeader("Content-Type", "text/html");
            response.setHeader("Content-disposition", "");
            response.setCharacterEncoding("UTF-8");
            try {
                String eMsg = "系统内部异常，请联系管理员！";
                eMsg= java.net.URLEncoder.encode(eMsg.toString(),"UTF-8");
                response.sendRedirect("/admin/storage/report?eMsg="+eMsg);
                outputStream.close();
            } catch (IOException e1) {
                LogClerk.errLog.error(e1.getMessage());
            }
            throw SSException.get(EmenuException.ExportReportFail, e);
        }
        finally {
            if(outputStream != null){
                try{
                    outputStream.flush();
                    outputStream.close();
                }catch(Exception e){
                    LogClerk.errLog.error(e);
                    throw SSException.get(EmenuException.ExportReportFail, e);
                }
            }
        }
    }




    /**---------------------------------------------------私有方法---------------------------------------------------**/

    /**
     * 添加单据
     *
     * @param stockDocuments
     * @return
     * @throws SSException
     */

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    private StockDocuments newDocuments(StockDocuments stockDocuments) throws SSException {

        try {
            if (Assert.isNull(stockDocuments)) {
                throw SSException.get(EmenuException.DocumentsIsNotNull);
            }
            //单据判空
            if (checkStockDocumentsBeforeSave(stockDocuments)) {
                return commonDao.insert(stockDocuments);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.InsertDocumentsFail, e);
        }
        return null;
    }

    /**
     * 根据单据执行对数据库的操作
     *
     * @param documentsId
     * @throws SSException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    private void updateStockItemInfo(int documentsId) throws SSException {
        StockDocuments stockDocuments = queryById(documentsId);
        List<StockDocumentsItem> documentsItemList = stockDocumentsItemService.queryByDocumentsId(documentsId);
        //从厨房存放点列表中查找出总库的主键id
        List<StockKitchen> kitchenList = stockKitchenService.listStockKitchen();
        int stockId = 0;
        for (StockKitchen stockKitchen:kitchenList){
            if(stockKitchenService.checkType(stockKitchen.getId())){
                stockId = stockKitchen.getId();
            }
        }
        if (stockDocuments.getType() == StockDocumentsTypeEnum.StockInDocuments.getId()) {
            //入库单
            for (StockDocumentsItem stockDocumentsItem : documentsItemList) {
                //根据入库信息修改库存物品
                StockItem stockItem = stockItemService.queryById(stockDocumentsItem.getItemId());
                List<StockKitchenItem> stockKitchenItems = stockKitchenItemService.queryByItemId(stockDocumentsItem.getItemId(),stockId);
                for(StockKitchenItem stockKitchenItem : stockKitchenItems){
                    if(Assert.isNull(stockKitchenItem) || stockDocumentsItem.getSpecificationId() != stockKitchenItem.getSpecifications()
                            ||stockDocuments.getSupplierId() != stockKitchenItem.getSupplierId()){
                        StockKitchenItem newKitchenItem = new StockKitchenItem();
                        newKitchenItem.setItemId(stockDocumentsItem.getItemId());
                        newKitchenItem.setKitchenId(stockId);
                        newKitchenItem.setSpecifications(stockDocumentsItem.getSpecificationId());
                        newKitchenItem.setSupplierId(stockDocuments.getSupplierId());
                        newKitchenItem.setUnitId(stockDocumentsItem.getUnitId());
                        newKitchenItem.setStorageQuantity(stockDocumentsItem.getQuantity());
                        stockKitchenItemService.newStockKitchenItem(newKitchenItem);
                    }else{
                        //若表中已经存在
                        if(stockDocumentsItem.getSpecificationId() == stockKitchenItem.getSpecifications() && stockDocumentsItem.getUnitId() == stockKitchenItem.getUnitId()
                                &&stockDocuments.getSupplierId() == stockKitchenItem.getSupplierId()){
                            //查询规格
                            Specifications specifications = specificationsService.queryById(stockDocumentsItem.getSpecificationId());
                            //通过规格转换为成本卡单位更新库存
                            BigDecimal itemQuantity = stockKitchenItem.getStorageQuantity().add(toCostCardUnitQuantity(stockDocumentsItem,specifications));
                            if (itemQuantity.floatValue() > stockItem.getUpperQuantity().floatValue()) {
                                throw SSException.get(EmenuException.OutOfStockItem);
                            }
                            stockKitchenItem.setStorageQuantity(itemQuantity);
                            stockKitchenItemService.updateStockKitchenItem(stockKitchenItem);
                        }
                    }
                }
            }
        } else if (stockDocuments.getType() == StockDocumentsTypeEnum.StockOutDocuments.getId()) {
            //领用单
            for (StockDocumentsItem stockDocumentsItem : documentsItemList) {
                //根据出库信息修改库存物品
                StockItem stockItem = stockItemService.queryById(stockDocumentsItem.getItemId());
                //查询规格
                Specifications specifications = specificationsService.queryById(stockDocumentsItem.getSpecificationId());
                //通过规格转换为成本卡单位
                if (stockItem.getStorageQuantity().floatValue() < toCostCardUnitQuantity(stockDocumentsItem, specifications).floatValue()) {
                    throw SSException.get(EmenuException.StockItemNotEnough);
                }
                List<StockKitchenItem> stockKitchenItems = stockKitchenItemService.queryByItemId(stockDocumentsItem.getItemId(),stockId);
                for(StockKitchenItem stockKitchenItem : stockKitchenItems){
                    if(stockKitchenItem.getSpecifications() == stockDocumentsItem.getSpecificationId() &&
                            stockDocumentsItem.getUnitId() == stockDocumentsItem.getUnitId()){
                        //从总库出库
                        BigDecimal itemQuantity = stockKitchenItem.getStorageQuantity().subtract(toCostCardUnitQuantity(stockDocumentsItem, specifications));
                        stockKitchenItem.setStorageQuantity(itemQuantity);
                        stockKitchenItemService.updateStockKitchenItem(stockKitchenItem);
                    }
                }

                //更新领用厨房库存
                List<StockKitchenItem> kitchenItems = stockKitchenItemService.queryByItemId(stockDocumentsItem.getItemId(),stockDocuments.getKitchenId());
                for(StockKitchenItem stockKitchenItem : kitchenItems){
                    if(Assert.isNull(stockKitchenItem) || stockDocumentsItem.getSpecificationId() != stockKitchenItem.getSpecifications()
                            ||stockDocuments.getSupplierId() != stockKitchenItem.getSupplierId()){
                        StockKitchenItem newKitchenItem = new StockKitchenItem();
                        newKitchenItem.setItemId(stockDocumentsItem.getItemId());
                        newKitchenItem.setKitchenId(stockDocuments.getKitchenId());
                        newKitchenItem.setSpecifications(stockDocumentsItem.getSpecificationId());
                        newKitchenItem.setSupplierId(stockDocuments.getSupplierId());
                        newKitchenItem.setUnitId(stockDocumentsItem.getUnitId());
                        newKitchenItem.setStorageQuantity(stockDocumentsItem.getQuantity());
                        stockKitchenItemService.newStockKitchenItem(newKitchenItem);
                    }else{
                        //若表中已经存在
                        if(stockDocumentsItem.getSpecificationId() == stockKitchenItem.getSpecifications() && stockDocumentsItem.getUnitId() == stockKitchenItem.getUnitId()
                                &&stockDocuments.getSupplierId() == stockKitchenItem.getSupplierId()){
                            //查询规格
                            Specifications kitchenSpecifications = specificationsService.queryById(stockDocumentsItem.getSpecificationId());
                            //通过规格转换为成本卡单位更新库存
                            BigDecimal itemQuantity = stockKitchenItem.getStorageQuantity().add(toCostCardUnitQuantity(stockDocumentsItem,kitchenSpecifications));
                            if (itemQuantity.floatValue() > stockItem.getUpperQuantity().floatValue()) {
                                throw SSException.get(EmenuException.OutOfStockItem);
                            }
                            stockKitchenItem.setStorageQuantity(itemQuantity);
                            stockKitchenItemService.updateStockKitchenItem(stockKitchenItem);
                        }
                    }
                }
            }
        } else if (stockDocuments.getType() == StockDocumentsTypeEnum.StockBackDocuments.getId()) {
            //回库单
            for (StockDocumentsItem stockDocumentsItem : documentsItemList) {
                //根据回库信息修改库存物品
                StockItem stockItem = stockItemService.queryById(stockDocumentsItem.getItemId());
                //查询规格
                Specifications specifications = specificationsService.queryById(stockDocumentsItem.getSpecificationId());
                //更新领用厨房库存
                List<StockKitchenItem> stockKitchenItems = stockKitchenItemService.queryByItemId(stockItem.getId(), stockDocuments.getKitchenId());
                for(StockKitchenItem stockKitchenItem : stockKitchenItems){
                    if (stockKitchenItem.getStatus() != 3 && Assert.isNotNull(stockKitchenItem)) {
                        if(stockDocumentsItem.getSpecificationId() == stockKitchenItem.getSpecifications() && stockDocumentsItem.getUnitId() == stockKitchenItem.getUnitId()
                                &&stockDocuments.getSupplierId() == stockKitchenItem.getSupplierId()){
                            //查询规格
                            Specifications kitchenSpecifications = specificationsService.queryById(stockDocumentsItem.getSpecificationId());
                            //通过规格转换为成本卡单位更新库存
                            if (stockDocumentsItem.getQuantity().floatValue() > stockKitchenItem.getStorageQuantity().floatValue()) {
                                //若单据填写的回库量大于厨房剩余量则报错
                                throw SSException.get(EmenuException.UpdateItemQuantityFailed);
                            }
                            BigDecimal itemQuantity = stockKitchenItem.getStorageQuantity().subtract(toCostCardUnitQuantity(stockDocumentsItem,kitchenSpecifications));
                            stockKitchenItem.setStorageQuantity(itemQuantity);
                            stockKitchenItemService.updateStockKitchenItem(stockKitchenItem);
                        }
                    } else {
                        throw SSException.get(EmenuException.UpdateItemQuantityFailed);
                    }
                }
                //根据回库信息更新总库数量
                List<StockKitchenItem> stockItems = stockKitchenItemService.queryByItemId(stockItem.getId(), stockId);
                for(StockKitchenItem stockKitchenItem : stockItems){
                    if (stockKitchenItem.getStatus() != 3 && Assert.isNotNull(stockKitchenItem)) {
                        if(stockDocumentsItem.getSpecificationId() == stockKitchenItem.getSpecifications() && stockDocumentsItem.getUnitId() == stockKitchenItem.getUnitId()
                                &&stockDocuments.getSupplierId() == stockKitchenItem.getSupplierId()){
                            //查询规格
                            Specifications stockSpecifications = specificationsService.queryById(stockDocumentsItem.getSpecificationId());
                            //通过规格转换为成本卡单位更新库存
                            BigDecimal itemQuantity = stockKitchenItem.getStorageQuantity().add(toCostCardUnitQuantity(stockDocumentsItem,stockSpecifications));
                            if (itemQuantity.floatValue() > stockItem.getUpperQuantity().floatValue()) {
                                //若单据填写的回库量大于库存上限则报错
                                throw SSException.get(EmenuException.UpdateItemQuantityFailed);
                            }
                            stockKitchenItem.setStorageQuantity(itemQuantity);
                            stockKitchenItemService.updateStockKitchenItem(stockKitchenItem);
                        }
                    } else {
                        throw SSException.get(EmenuException.UpdateItemQuantityFailed);
                    }
                }
            }
        }else if(stockDocuments.getType() == StockDocumentsTypeEnum.IncomeOnDocuments.getId()){

            //盘盈单
            for (StockDocumentsItem stockDocumentsItem : documentsItemList) {
                StockItem stockItem = stockItemService.queryById(stockDocumentsItem.getItemId());
                List<StockKitchenItem> stockItems = stockKitchenItemService.queryByItemId(stockItem.getId(), stockDocuments.getKitchenId());
                for(StockKitchenItem stockKitchenItem : stockItems){
                    if (stockKitchenItem.getStatus() != 3 && Assert.isNotNull(stockKitchenItem)) {
                        if(stockDocumentsItem.getSpecificationId() == stockKitchenItem.getSpecifications() && stockDocumentsItem.getUnitId() == stockKitchenItem.getUnitId()
                                &&stockDocuments.getSupplierId() == stockKitchenItem.getSupplierId()){
                            //查询规格
                            Specifications stockSpecifications = specificationsService.queryById(stockDocumentsItem.getSpecificationId());
                            //通过规格转换为成本卡单位更新库存
                            BigDecimal itemQuantity = stockKitchenItem.getStorageQuantity().add(toCostCardUnitQuantity(stockDocumentsItem,stockSpecifications));
                            if (itemQuantity.floatValue() > stockItem.getUpperQuantity().floatValue()) {
                                //若单据填写的盘盈量大于库存上限则报错
                                throw SSException.get(EmenuException.UpdateItemQuantityFailed);
                            }
                            stockKitchenItem.setStorageQuantity(itemQuantity);
                            stockKitchenItemService.updateStockKitchenItem(stockKitchenItem);
                        }
                    } else {
                        throw SSException.get(EmenuException.UpdateItemQuantityFailed);
                    }
                }
            }
        }else if(stockDocuments.getType() == StockDocumentsTypeEnum.LossOnDocuments.getId()){
            //盘亏单
            for (StockDocumentsItem stockDocumentsItem : documentsItemList) {
                StockItem stockItem = stockItemService.queryById(stockDocumentsItem.getItemId());
                List<StockKitchenItem> stockItems = stockKitchenItemService.queryByItemId(stockItem.getId(), stockDocuments.getKitchenId());
                for(StockKitchenItem stockKitchenItem : stockItems){
                    if (stockKitchenItem.getStatus() != 3 && Assert.isNotNull(stockKitchenItem)) {
                        if(stockDocumentsItem.getSpecificationId() == stockKitchenItem.getSpecifications() && stockDocumentsItem.getUnitId() == stockKitchenItem.getUnitId()
                                &&stockDocuments.getSupplierId() == stockKitchenItem.getSupplierId()){
                            //查询规格
                            Specifications stockSpecifications = specificationsService.queryById(stockDocumentsItem.getSpecificationId());
                            //通过规格转换为成本卡单位更新库存
                            BigDecimal itemQuantity = stockKitchenItem.getStorageQuantity().subtract(toCostCardUnitQuantity(stockDocumentsItem,stockSpecifications));
                            stockKitchenItem.setStorageQuantity(itemQuantity);
                            stockKitchenItemService.updateStockKitchenItem(stockKitchenItem);
                        }
                    } else {
                        throw SSException.get(EmenuException.UpdateItemQuantityFailed);
                    }
                }
            }
        }
    }

    /**
     * 检查单据信息是否有误
     *
     * @param stockDocuments
     * @return
     * @throws SSException
     */
    private boolean checkStockDocumentsBeforeSave(StockDocuments stockDocuments) throws SSException {

        if (Assert.isNull(stockDocuments)) {
            return false;
        }
        // 操作人
        if (Assert.isNull(stockDocuments.getCreatedPartyId()) || Assert.lessOrEqualZero(stockDocuments.getCreatedPartyId())) {
            throw SSException.get(EmenuException.DocumentsCreatedPartyIdError);
        }
        // 经手人
        if (Assert.isNull(stockDocuments.getHandlerPartyId()) || Assert.lessOrEqualZero(stockDocuments.getHandlerPartyId())) {
            throw SSException.get(EmenuException.DocumentsHandlerPartyId);
        }
        // 单据类型
        if (Assert.isNull(stockDocuments.getType())
                && Assert.lessOrEqualZero(stockDocuments.getType())) {
            throw SSException.get(EmenuException.DocumentsTypeError);
        }
        // 入库单存放点
        if (stockDocuments.getType() == StockDocumentsTypeEnum.StockInDocuments.getId()) {
            if (Assert.isNull(stockDocuments.getKitchenId())
                    || Assert.lessOrEqualZero(stockDocuments.getKitchenId())) {
                throw SSException.get(EmenuException.KitchenIdError);

            }
        }
        // 单据编号
        Assert.isNotNull(stockDocuments.getSerialNumber(), EmenuException.SerialNumberError);
        // 单据金额
        Assert.isNotNull(stockDocuments.getMoney(), EmenuException.DocumentsMoneyError);
        return true;
    }

    /**
     * 将数量按照成本卡单位转化
     *
     * @param stockDocumentsItem
     * @param specifications
     * @return
     * @throws SSException
     */
    private BigDecimal toCostCardUnitQuantity(StockDocumentsItem stockDocumentsItem, Specifications specifications) throws SSException {
        BigDecimal documentsItemQuantity = BigDecimal.ZERO;
        if (stockDocumentsItem.getUnitId() == specifications.getCostCardUnitId()) {
            documentsItemQuantity = stockDocumentsItem.getQuantity();
        } else if (stockDocumentsItem.getUnitId() == specifications.getOrderUnitId()) {
            documentsItemQuantity = stockDocumentsItem.getQuantity().multiply(specifications.getOrderToStorage().multiply(specifications.getStorageToCost()));
        } else if (stockDocumentsItem.getUnitId() == specifications.getStorageUnitId()) {
            documentsItemQuantity = stockDocumentsItem.getQuantity().multiply(specifications.getStorageToCost());
        }
        return documentsItemQuantity;
    }

    /**
     * 将数量按照库存单位转化
     *
     * @param stockDocumentsItem
     * @param specifications
     * @return
     * @throws SSException
     */
    private BigDecimal toStorageUnitQuantity(StockDocumentsItem stockDocumentsItem, Specifications specifications) throws SSException {
        BigDecimal documentsItemQuantity = BigDecimal.ZERO;
        if (stockDocumentsItem.getUnitId() == specifications.getStorageUnitId()) {
            documentsItemQuantity = stockDocumentsItem.getQuantity();
        } else if (stockDocumentsItem.getUnitId() == specifications.getOrderUnitId()) {
            documentsItemQuantity = stockDocumentsItem.getQuantity().multiply(specifications.getOrderToStorage());
        }
        return documentsItemQuantity;
    }


    /**************************************by chenwenyan ***************************************/
    /**
     * 获取单据list
     *
     * @return
     * @throws SSException
     */
    @Override
    public List<StockDocuments> listAll() throws SSException {
        List<StockDocuments> documentsList = Collections.emptyList();
        try {
            documentsList = stockDocumentsMapper.listAll();
            //设置经手人、操作人、审核人名字
            for (StockDocuments stockDocuments : documentsList) {
                setStockDocumentsRelatedName(stockDocuments);
            }
            return documentsList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListDocumentsFail, e);
        }
    }

    /**
     * 根据查询条件获取单据
     *
     * @param documentsSearchDto
     * @return
     * @throws SSException
     */
    @Override
    public List<StockDocuments> listStockDocumentsBySearchDto(DocumentsSearchDto documentsSearchDto) throws SSException {

        List<StockDocuments> documentsList = Collections.emptyList();

        int pageNo = 0;
        int offset = 0;

        try {
            if (Assert.isNotNull(documentsSearchDto.getPageNo())) {
                pageNo = documentsSearchDto.getPageNo() <= 0 ? 0 : documentsSearchDto.getPageNo();
            }
            if (Assert.isNotNull(documentsSearchDto.getOffset())) {
                offset = documentsSearchDto.getOffset() <= 0 ? 0 : documentsSearchDto.getOffset();
            }
            if (documentsSearchDto.getStartTime() != null) {
                documentsSearchDto.getStartTime().setHours(0);
                documentsSearchDto.getStartTime().setMinutes(0);
                documentsSearchDto.getStartTime().setSeconds(0);
            }
            if (documentsSearchDto.getEndTime() != null) {
                documentsSearchDto.getEndTime().setHours(23);
                documentsSearchDto.getEndTime().setMinutes(59);
                documentsSearchDto.getEndTime().setSeconds(59);
            }
            //documentsList = stockDocumentsMapper.listDocumentsBySearchDto(documentsSearchDto);
            //设置经手人、操作人、审核人名字
            for (StockDocuments stockDocument : documentsList) {
                setStockDocumentsRelatedName(stockDocument);
            }
            return documentsList;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryDocumentsByDtoFail, e);
        }
    }


    @Override
    public StockDocuments queryById(int id) throws SSException {
        StockDocuments stockDocuments = new StockDocuments();
        try {
            if (Assert.lessOrEqualZero(id)) {
                throw SSException.get(EmenuException.DocumentsIdError);
            } else {
                stockDocuments = commonDao.queryById(StockDocuments.class, id);
                if (Assert.isNotNull(stockDocuments)) {
                    setStockDocumentsRelatedName(stockDocuments);
                }
                return stockDocuments;
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryDocumentsByIdFailed, e);
        }
    }

    @Override
    public DocumentsDto queryDocumentsDtoById(int id) throws SSException {
        DocumentsDto documentsDto = new DocumentsDto();
        try {
            if (Assert.isNull(id)) {
                throw SSException.get(EmenuException.DocumentsIdError);
            } else {
                StockDocuments stockDocuments = commonDao.queryById(StockDocuments.class, id);
                if (Assert.isNotNull(stockDocuments)) {
                    List<StockDocumentsItem> stockDocumentsItems = stockDocumentsItemMapper.queryByDocumentsId(stockDocuments.getId());
                    documentsDto.setStockDocuments(stockDocuments);
                    documentsDto.setStockDocumentsItemList(stockDocumentsItems);
                    setStockDocumentsRelatedName(stockDocuments);
                }
            }
            return documentsDto;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryDocumentsByIdFailed, e);
        }
    }

    @Override
    public boolean delDocumentsDtoById(int id) throws SSException {
        StockDocuments stockDocuments = new StockDocuments();
        try {
            stockDocuments = queryById(id);
            if (Assert.isNull(stockDocuments)) {
                throw SSException.get(EmenuException.DocumentsIsNotExist);
            }
            //已审核，不能删除
            if (stockDocuments.getIsAudited().equals(1)) {
                return false;
            }
            //未审核，删除单据以及单据详情
            stockDocumentsItemService.delByDocumentsId(id);
            delById(id);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelDocumentsByIdFailed, e);
        }
        return true;
    }

    /**
     * 根据Id删除单据信息
     *
     * @param id
     * @throws SSException
     */
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public boolean delById(int id) throws SSException {
        try {
            if (Assert.lessOrEqualZero(id)) {
                throw SSException.get(EmenuException.DocumentsIdError);
            }
            stockDocumentsMapper.delById(id);
            return true;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DelDocumentsByIdFailed, e);
        }
    }


    /**
     * 根据查询条件获取单据以及单据详情
     *
     * @param documentsSearchDto
     * @return
     * @throws SSException
     */
    @Override
    public List<DocumentsDto> listDocumentsDtoBySearchDto(DocumentsSearchDto documentsSearchDto) throws SSException {
        List<StockDocuments> stockDocuments = Collections.emptyList();
        List<DocumentsDto> documentsDtos = new ArrayList<DocumentsDto>();
        try {
            stockDocuments = stockDocumentsMapper.listBySearchDto(documentsSearchDto);
            if (Assert.isNull(stockDocuments) || Assert.lessOrEqualZero(stockDocuments.size())) {
                return documentsDtos;
            } else {
                for (StockDocuments stockDocument : stockDocuments) {
                    List<StockDocumentsItem> stockDocumentsItems = stockDocumentsItemMapper.queryByDocumentsId(stockDocument.getId());
                    if (Assert.isNotNull(stockDocumentsItems)) {
                        setStockDocumentsRelatedName(stockDocument);
                        DocumentsDto documentsDto = new DocumentsDto();
                        documentsDto.setStockDocuments(stockDocument);
                        documentsDto.setStockDocumentsItemList(stockDocumentsItems);
                        documentsDtos.add(documentsDto);
                    }
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return documentsDtos;
    }

    @Override
    public List<StockDocuments> listDocumentsByTime(Date startTime, Date endTime) throws SSException {
        List<StockDocuments> stockDocuments = new ArrayList<StockDocuments>();
        try {
            stockDocuments = stockDocumentsMapper.listByTime(startTime, endTime);
            for (StockDocuments stockDocument : stockDocuments) {
                setStockDocumentsRelatedName(stockDocument);
            }
            return stockDocuments;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListReportFail, e);
        }
    }

    @Override
    public List<DocumentsDto> listDocumentsDtoByTime(Date startTime, Date endTime) throws SSException {
        List<StockDocuments> stockDocuments = Collections.emptyList();
        List<DocumentsDto> documentsDtos = new ArrayList<DocumentsDto>();
        try {
            stockDocuments = stockDocumentsMapper.listByTime(startTime, endTime);
            if (Assert.isNull(stockDocuments) || Assert.lessOrEqualZero(stockDocuments.size())) {
                return documentsDtos;
            } else {
                for (StockDocuments stockDocument : stockDocuments) {
                    List<StockDocumentsItem> stockDocumentsItems = stockDocumentsItemMapper.queryByDocumentsId(stockDocument.getId());
                    if (Assert.isNotNull(stockDocumentsItems)) {
                        setStockDocumentsRelatedName(stockDocument);
                        DocumentsDto documentsDto = new DocumentsDto();
                        documentsDto.setStockDocuments(stockDocument);
                        documentsDto.setStockDocumentsItemList(stockDocumentsItems);
                        documentsDtos.add(documentsDto);
                    }
                }
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return documentsDtos;
    }

    @Override
    public List<StockDocuments> listDocumentsByTimeAndIsAudited(Date startTime, Date endTime, int isAudited) throws SSException {
        List<StockDocuments> stockDocuments = new ArrayList<StockDocuments>();
        try {
            stockDocuments = stockDocumentsMapper.listByTimeAndIsAudited(startTime, endTime, isAudited);
            if (Assert.isNotNull(stockDocuments) && !Assert.lessOrEqualZero(stockDocuments.size())) {
                for (StockDocuments stockDocument : stockDocuments) {
                    setStockDocumentsRelatedName(stockDocument);
                }
            }
            return stockDocuments;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListReportFail, e);
        }
    }

    @Override
    public List<DocumentsDto> listDocumentsDtoByTimeAndIsAudited(Date startTime, Date endTime, int isAudited) throws SSException {
        List<DocumentsDto> documentsDtos = new ArrayList<DocumentsDto>();
        List<StockDocuments> stockDocuments = Collections.emptyList();
        try {
            stockDocuments = stockDocumentsMapper.listByTimeAndIsAudited(startTime, endTime, isAudited);
            if (Assert.isNotNull(stockDocuments)) {
                for (StockDocuments stockDocument : stockDocuments) {
                    List<StockDocumentsItem> stockDocumentsItems = stockDocumentsItemMapper.queryByDocumentsId(stockDocument.getId());
                    if (Assert.isNotNull(stockDocumentsItems)) {
                        setStockDocumentsRelatedName(stockDocument);
                        DocumentsDto documentsDto = new DocumentsDto();
                        documentsDto.setStockDocuments(stockDocument);
                        documentsDto.setStockDocumentsItemList(stockDocumentsItems);
                        documentsDtos.add(documentsDto);
                    }
                }
            }
            return documentsDtos;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListReportFail, e);
        }
    }


    @Override
    public List<StockDocuments> listDocumentsByTimeAndIsAudited1(Date startTime, Date endTime, int isAudited) throws SSException {
        List<StockDocuments> stockDocuments = new ArrayList<StockDocuments>();
        try {
            stockDocuments = stockDocumentsMapper.listByTimeAndIsAudited1(startTime, endTime, isAudited);
            for (StockDocuments stockDocument : stockDocuments) {
                setStockDocumentsRelatedName(stockDocument);
            }
            return stockDocuments;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListReportFail, e);
        }
    }

    @Override
    public List<DocumentsDto> listDocumentsDtoByTimeAndIsAudited1(Date startTime, Date endTime, int isAudited) throws SSException {
        List<DocumentsDto> documentsDtos = new ArrayList<DocumentsDto>();
        List<StockDocuments> stockDocuments = Collections.emptyList();
        try {
            stockDocuments = stockDocumentsMapper.listByTimeAndIsAudited1(startTime, endTime, isAudited);
            if (Assert.isNotNull(stockDocuments)) {
                for (StockDocuments stockDocument : stockDocuments) {
                    List<StockDocumentsItem> stockDocumentsItems = stockDocumentsItemMapper.queryByDocumentsId(stockDocument.getId());
                    if (Assert.isNotNull(stockDocumentsItems)) {
                        setStockDocumentsRelatedName(stockDocument);
                        DocumentsDto documentsDto = new DocumentsDto();
                        documentsDto.setStockDocuments(stockDocument);
                        documentsDto.setStockDocumentsItemList(stockDocumentsItems);
                        documentsDtos.add(documentsDto);
                    }
                }
            }
            return documentsDtos;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListReportFail, e);
        }
    }

    @Override
    public int countByDocumentsSearchDto(DocumentsSearchDto documentsSearchDto) throws SSException {
        try {
            if (Assert.isNotNull(documentsSearchDto)) {
                return stockDocumentsMapper.countBySearchDto(documentsSearchDto);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListReportFail, e);
        }
        return 0;
    }

    @Override
    public List<StockDocuments> listUnsettleAndAuditedDocumentsByEndTime(Date endTime) throws SSException {
        List<StockDocuments> stockDocuments = new ArrayList<StockDocuments>();
        try {
            if (Assert.isNotNull(endTime)) {
                stockDocuments = stockDocumentsMapper.listUnsettleAndAuditedDocumentsByEndTime(endTime);
                return stockDocuments;
            } else {
                return null;
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListReportFail, e);
        }

    }


    @Override
    public List<DocumentsDto> listDocumentsDtoByPage(int page, int pageSize) throws SSException {
        page = page <= 0 ? 0 : page - 1;
        int offset = page * pageSize;
        List<DocumentsDto> documentsDtos = Collections.emptyList();
        List<StockDocuments> stockDocuments = Collections.emptyList();
        if (Assert.lessZero(offset)) {
            return documentsDtos;
        }
        documentsDtos = new ArrayList<DocumentsDto>();
        try {
            stockDocuments = stockDocumentsMapper.listByPage(offset, pageSize);
            for (StockDocuments stockDocument : stockDocuments) {
                DocumentsDto documentDto = new DocumentsDto();
                List<StockDocumentsItem> stockDocumentsItems = Collections.emptyList();
                //根据单据id获取单据详情信息
                stockDocumentsItems = stockDocumentsItemMapper.queryByDocumentsId(stockDocument.getId());
                setStockDocumentsRelatedName(stockDocument);
                //数据存入documentDto
                documentDto.setStockDocuments(stockDocument);
                documentDto.setStockDocumentsItemList(stockDocumentsItems);

                documentsDtos.add(documentDto);
            }
            return documentsDtos;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListReportFail, e);
        }
    }

    @Override
    public List<DocumentsDto> listDocumentsDtoByCondition(StockDocuments stockDocuments,int page,int pageSize,Date startTime,Date endTime)throws SSException{
        page = page <= 0 ? 0 : page - 1;
        int offset = page * pageSize;

        if (endTime != null) {
            endTime.setHours(23);
            endTime.setMinutes(59);
            endTime.setSeconds(59);
        }
        List<DocumentsDto> documentsDtos = Collections.emptyList();
        List<StockDocuments> stockDocumentsList = Collections.emptyList();
        if(Assert.lessZero(offset)){
            return documentsDtos;
        }
        documentsDtos = new ArrayList<DocumentsDto>();
        try {
            if (Assert.isNull(stockDocuments)){
                throw SSException.get(EmenuException.DocumentsIsNotNull);
            }
            stockDocumentsList = stockDocumentsMapper.listDocumentsByCondition(stockDocuments, offset, pageSize, startTime, endTime);

            for (StockDocuments stockDocument : stockDocumentsList) {
                DocumentsDto documentsDto = new DocumentsDto();
                List<StockDocumentsItem> stockDocumentsItems = new ArrayList<StockDocumentsItem>();
                //根据单据id获取单据详情信息
                stockDocumentsItems = stockDocumentsItemMapper.queryByDocumentsId(stockDocument.getId());
                //数据存入documentsDto
                documentsDto.setStockDocuments(stockDocument);
                documentsDto.setStockDocumentsItemList(stockDocumentsItems);
                documentsDtos.add(documentsDto);
            }
            return documentsDtos;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListDocumentsFail, e);
        }
    }


    @Override
    public List<StockDocuments> listByPage(int offset,int pageSize) throws SSException{
        List<StockDocuments> list = Collections.emptyList();
        try{
            list =  stockDocumentsMapper.listByPage(offset,pageSize);
            if(Assert.isNotNull(list)){
                for(StockDocuments stockDocument: list){
                    setStockDocumentsRelatedName(stockDocument);
                }
            }
            return list;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListDocumentsFail, e);
        }
    }

    @Override
    public int count() throws SSException{
        try{
            return stockDocumentsMapper.count();
        }catch (SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CountDocumentsError,e);
        }
    }


    /******************************** 私有方法 ********************************************/

    /**
     * 设置经手人、操作人、审核人名字
     *
     * @param stockDocuments
     * @throws SSException
     */
    private void setStockDocumentsRelatedName(StockDocuments stockDocuments) throws SSException {
        // 设置经手人、操作人、审核人名字
        int id = stockDocuments.getCreatedPartyId();
//        Employee createdEmployee = employeeService.queryByPartyIdWithoutDelete(stockDocuments.getCreatedPartyId());
        Employee createdEmployee = employeeService.queryByPartyIdWithoutDelete(id);
        if (Assert.isNull(createdEmployee)) {
            throw SSException.get(EmenuException.SystemException);
        }
        stockDocuments.setCreatedName(createdEmployee.getName());

        Employee auditEmployee = employeeService.queryByPartyIdWithoutDelete(stockDocuments.getAuditPartyId());
        if (Assert.isNull(auditEmployee)) {
            stockDocuments.setAuditName("");
        } else {
            stockDocuments.setAuditName(auditEmployee.getName());
        }
        Employee handlerEmployee = employeeService.queryByPartyIdWithoutDelete(stockDocuments.getHandlerPartyId());
        if (Assert.isNull(handlerEmployee)) {
            throw SSException.get(EmenuException.SystemException);
        } else {
            stockDocuments.setHandlerName(handlerEmployee.getName());
        }
    }
}
