package com.emenu.service.stock.impl;

import com.emenu.common.dto.stock.StockItemSearchDto;
import com.emenu.common.entity.stock.StockItem;
import com.emenu.common.enums.ExcelExportTemplateEnums;
import com.emenu.common.enums.other.SerialNumTemplateEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.exception.PartyException;
import com.emenu.common.utils.StringUtils;
import com.emenu.mapper.stock.StockItemMapper;
import com.emenu.service.other.SerialNumService;
import com.emenu.service.party.group.supplier.SupplierService;
import com.emenu.service.stock.StockItemService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.Log;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import sun.invoke.empty.Empty;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * ItemServiceImpl
 *
 * @author pengpeng
 * @time 2017/3/4 9:55
 * updated by renhongshuai
 */
@Service("itemService")
public class StockItemServiceImpl implements StockItemService {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private SerialNumService serialNumService;

    @Autowired
    private StockItemMapper stockItemMapper;

    @Autowired
    private SupplierService supplierService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public StockItem newItem(StockItem stockItem) throws SSException{
        // 设置物品编号和助记码
        try {
            // 物品编号
            String serialNumber = serialNumService.generateSerialNum(SerialNumTemplateEnums.StockItemNum);
            stockItem.setItemNumber(serialNumber);
            // 助记码
            if (Assert.isNull(stockItem.getAssistantCode())
                    || stockItem.getAssistantCode().equals("")){
                String assistantCode = StringUtils.str2Pinyin(stockItem.getName(),"headChar");
                stockItem.setAssistantCode(assistantCode);
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        try {
            return commonDao.insert(stockItem);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.StockItemInsertFailed, e);
        }
    }

    @Override
    public boolean checkIsExist(String name) throws SSException{
        try{
            return stockItemMapper.countByName(name) > 0 ? true : false;
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }

    @Override
    public String listToString(List<Integer> list) throws SSException{
        String string = new String();
        string = "";
        try{
            for(int i = 0; i < list.size(); i++)
            {
                if(i==0)
                    string += list.get(i).toString();
                else
                    string += ',' + list.get(i).toString();
            }
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return string;
    }

    @Override
    public List<Integer> stringTolist(String string) throws SSException{
        List<Integer> list = new ArrayList<Integer>();
        try{
            int num = 0;
            for(int i=0;i<string.length();i++) {
                char a = string.charAt(i);
                if(a!=','){
                    num = num * 10 +  a - '0';
                }else{
                    list.add(num);
                    num = 0;
                }
                if(i==string.length()-1){
                    list.add(num);
                }
            }
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
        return list;
    }

    @Override
    public StockItem queryById(int id) throws SSException{
        StockItem stockItem = new StockItem();
        try{
            stockItem = commonDao.queryById(stockItem.getClass(),id);
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryStockItemByIdFailed, e);
        }
        return stockItem;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateStockItem(StockItem stockItem) throws SSException{
        try{
            commonDao.update(stockItem);
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateStockItemFailed, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateStockItemStatusById(int itemId,int status) throws SSException{
        try{
            if(Assert.isNull(itemId)
                    || Assert.lessOrEqualZero(itemId)
                    || Assert.isNull(status)
                    || Assert.lessOrEqualZero(status)){
                throw SSException.get(EmenuException.UpdateStockStatusFailed);
            }
            stockItemMapper.updateStockItemStatusById(itemId,status);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateStockStatusFailed,e);
        }
    }

    @Override
    public List<StockItem> listItem(StockItemSearchDto searchDto)throws SSException{
        try {
            return stockItemMapper.listBySearchDto(searchDto);
        }
        catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListStockItemFailed,e);
        }
    }

    @Override
    public List<StockItem> listAll()throws SSException{
        try{
            List<StockItem> stockItemList = stockItemMapper.listAll();
            return stockItemList;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListStockItemFailed,e);
        }
    }

    @Override
    public List<StockItem> listByPage(int offset, int pageSize) throws SSException{
        try{
           return stockItemMapper.listByPage(offset,pageSize);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListStockItemFailed,e);
        }
    }

    @Override
    public int count() throws SSException{
        try{
            return stockItemMapper.count();
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CountStockItemFailed,e);
        }
    }

    @Override
    public List<StockItem> listBySearchDto(StockItemSearchDto stockItemSearchDto)throws SSException{
        List<StockItem> stockItemList = Collections.emptyList();
        int pageNo = stockItemSearchDto.getPageNo() <= 0 ? 0 : stockItemSearchDto.getPageNo() - 1;
        if (Assert.isNull(stockItemSearchDto.getPageSize())
                || Assert.lessOrEqualZero(stockItemSearchDto.getPageSize())){
            stockItemSearchDto.setPageSize(10);
        }
        int offset = pageNo * stockItemSearchDto.getPageSize();
        stockItemSearchDto.setOffset(offset);
        try{
            stockItemList = stockItemMapper.listBySearchDto(stockItemSearchDto);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListBySearchFailed,e);
        }
        return stockItemList;
    }

    /**
     * 导出Excel
     * @param searchDto
     * @param response
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void exportExcelBySearchDto(StockItemSearchDto searchDto, HttpServletResponse response) throws SSException{
        // 设置输出流
        OutputStream os = null;
        List<StockItem> stockItemlist =  Collections.emptyList();
        try {
            // 设置excel文件名和sheetName
            stockItemlist = listAll();
            String filename = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            filename = ExcelExportTemplateEnums.AdminStockItemList.getName()+sdf.format(new Date());
            response.setContentType("application/msexcel");
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(filename.getBytes("gbk"), "ISO8859-1") + ".xls");
            os = response.getOutputStream();
            InputStream tplStream = IOUtil.getFileAsStream(ExcelExportTemplateEnums.AdminStockItemList.getFilePath());
            Workbook tplWorkBook = Workbook.getWorkbook(tplStream);
            WritableWorkbook outBook = Workbook.createWorkbook(os, tplWorkBook);
            WritableSheet sheet = outBook.getSheet(0);
            int row=3;

            for(StockItem stockItem :stockItemlist){
                //单元格居中格式
                WritableCellFormat cellFormat=new WritableCellFormat();
                cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
                cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                cellFormat.setWrap(true);
                // 序号
                Label labelNumber = new Label(0,row,String.valueOf(row - 2));
                sheet.addCell(labelNumber);
                // 名称
                Label labelName= new Label(1, row, stockItem.getName());
                sheet.addCell(labelName);
                // 物品编号
                Label labelItemNumber = new Label(2,row,stockItem.getItemNumber());
                sheet.addCell(labelItemNumber);
                // 助记码
                Label labelAssistantCode = new Label(3, row,stockItem.getAssistantCode());
                sheet.addCell(labelAssistantCode);
                //所属类别
                Label labelTag = new Label(4,row,stockItem.getTagName());
                sheet.addCell(labelTag);
                //供货商
                Label labelSupplier = new Label(5,row,stockItem.getSupplierName());
                sheet.addCell(labelSupplier);
                //库存量
                Label labelStorageQuantity = new Label(6,row,stockItem.getStorageQuantity().toString());
                sheet.addCell(labelStorageQuantity);
                //入库上限
                Label labelUpperQuantity = new Label(7,row,stockItem.getUpperQuantity().toString());
                sheet.addCell(labelUpperQuantity);
                //入库下限
                Label labelLowerQuantity = new Label(8,row,stockItem.getLowerQuantity().toString());
                sheet.addCell(labelLowerQuantity);
                // 出库方式
                String stockOutType = ((stockItem.getStockOutType() == 1)?"加权平均":"手动");
                Label labelStockOutType = new Label(9,row,stockOutType);
                sheet.addCell(labelStockOutType);
                row++;
            }
            outBook.write();
            outBook.close();
            tplWorkBook.close();
            tplStream.close();
            os.close();

        } catch (Exception e) {
            LogClerk.errLog.error(e);
            response.setContentType("text/html");
            response.setHeader("Content-Type", "text/html");
            response.setHeader("Content-disposition", "");
            response.setCharacterEncoding("UTF-8");
            try {
                String eMsg = "系统内部异常，请联系管理员！";
                eMsg= java.net.URLEncoder.encode(eMsg.toString(),"UTF-8");
                response.sendRedirect("/admin/storage/settlement/supplier?eMsg="+eMsg);
                os.close();
            } catch (IOException e1) {
                LogClerk.errLog.error(e1.getMessage());
            }
            throw SSException.get(EmenuException.ExportStorageSettlementCheckFailed, e);
        }finally{
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    LogClerk.errLog.error(e);
                    throw SSException.get(EmenuException.ExportStorageSettlementCheckFailed, e);
                }
            }
        }
    }

    public int countByTagId(int id)throws SSException{
        try{
            return stockItemMapper.countByTagId(id);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CountByStockTagIdFailed,e);
        }
    }
}
