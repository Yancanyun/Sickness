package com.emenu.service.stock.impl;


import com.emenu.common.entity.stock.StockWarn;
import com.emenu.common.enums.ExcelExportTemplateEnums;
import com.emenu.common.enums.stock.StockWarnEnum;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.EntityUtil;
import com.emenu.mapper.stock.StockWarnMapper;
import com.emenu.service.stock.StockWarnService;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * StockWarnServiceImpl
 *
 * @author Flying
 * @date 2017/3/15 15:44
 */
@Service("stockWarnService")
public class StockWarnServiceImpl implements StockWarnService {

    @Autowired
    private CommonDao commonDao;

    @Autowired
    private StockWarnMapper stockWarnMapper;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public StockWarn newWarn(StockWarn stockWarn) throws SSException{
        try{
            return commonDao.insert(stockWarn);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.AddStockWarnFail,e);
        }
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateStateToResolvedByItemId(int itemId) throws SSException{
        try {
            if(Assert.isNull(itemId) || Assert.lessOrEqualZero(itemId)){
                throw SSException.get(EmenuException.UpdateStateToResolveFail);
            }
            stockWarnMapper.updateStateToResolvedByItemId(itemId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateStateToResolveFail,e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateStateToIgnoredByItemId(int itemId) throws SSException{
        try{
            if(Assert.isNull(itemId) || Assert.lessOrEqualZero(itemId)){
                throw SSException.get(EmenuException.UpdateStateToIgnoreFail);
            }
            stockWarnMapper.updateStateToIgnoreByItemId(itemId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateStateToIgnoreFail,e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public List<StockWarn> queryAllUntreatedWarn(int kitchenId) throws SSException{
        List<StockWarn> stockWarnList = Collections.emptyList();
        try {
            if(Assert.isNull(kitchenId) || Assert.lessOrEqualZero(kitchenId)){
                throw SSException.get(EmenuException.QueryAllUntreatedWarnFail);
            }
            stockWarnList = stockWarnMapper.queryAllUntreatedWarnByKitchenId(kitchenId);
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryAllUntreatedWarnFail,e);
        }
        return  stockWarnList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public List<StockWarn> queryAllWarn() throws SSException{
        List<StockWarn> stockWarnList = Collections.emptyList();
        try{
            stockWarnList = stockWarnMapper.queryAllWarn();
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryAllWarnFail,e);
        }
        return stockWarnList;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public int countAllWarn() throws SSException{
        try{
            return stockWarnMapper.countAllWarn();
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CountAllWarnFail,e);
        }

    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public List<StockWarn> listByPage(int offset, int pageSize) throws SSException{
        try{
            if(Assert.isNull(offset)){
                throw SSException.get(EmenuException.WarnOffsetIsNotNull);
            }
            List<StockWarn> stockWarnList =  Collections.emptyList();
            stockWarnList = stockWarnMapper.listByPage(offset,pageSize);
            return stockWarnList;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CountAllWarnFail,e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void exportExcel(HttpServletResponse response) throws SSException{
        OutputStream os = null;
        try{
            //从数据库表中获取数据
            List<StockWarn> stockWarnList = stockWarnMapper.queryAllUntreatedWarn();
            for(StockWarn stockWarn : stockWarnList){
                EntityUtil.setNullFieldDefault(stockWarn);
            }
            //设置输出流
            //设置excel文件名和sheetName
            String fileName = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            fileName = ExcelExportTemplateEnums.AdminStorageList.getName() + sdf.format(new Date());
            response.setContentType("application/msexcel");
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(fileName.getBytes("gbk"), "ISO8859-1") + ".xls");
            os = response.getOutputStream();
            InputStream tplStream = IOUtil.getFileAsStream(ExcelExportTemplateEnums.AdminStorageList.getFilePath());
            Workbook tplWorkBook = Workbook.getWorkbook(tplStream);
            WritableWorkbook outBook = Workbook.createWorkbook(os,tplWorkBook);
            //获取sheet 往sheet中写数据
            WritableSheet sheet = outBook.getSheet(0);
            int row = 3;

            for(StockWarn stockWarn : stockWarnList){
                //单元格居中格式
                WritableCellFormat cellFormat = new WritableCellFormat();
                cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
                cellFormat.setVerticalAlignment(jxl.format.VerticalAlignment.CENTRE);
                cellFormat.setWrap(true);
                //编好
                Label lableId= new Label(0, row, stockWarn.getId()+"");
                lableId.setCellFormat(cellFormat);
                sheet.addCell(lableId);
                //物品名称
                Label labelItemName = new Label(1, row,stockWarn.getItemName());
                labelItemName.setCellFormat(cellFormat);
                sheet.addCell(labelItemName);
                //厨房名称
                Label labelKitchenName = new Label(2, row,stockWarn.getKitchenName());
                labelKitchenName.setCellFormat(cellFormat);
                sheet.addCell(labelKitchenName);
                //预警说明
                Label labelContent = new Label(3, row,stockWarn.getContent());
                labelContent.setCellFormat(cellFormat);
                sheet.addCell(labelContent);
                //预警状态
                Label labelState = new Label(4, row , StockWarnEnum.valueOf(stockWarn.getState()).name());
                labelState.setCellFormat(cellFormat);
                sheet.addCell(labelState);
                //预警时间
                SimpleDateFormat abcd = new SimpleDateFormat("yyyy-MM-dd  HH:mm:ss");
                Label labelTime = new Label(5, row , abcd.format(stockWarn.getTime()));
                labelTime.setCellFormat(cellFormat);
                sheet.addCell(labelTime);

                row++;
            }
            outBook.write();
            outBook.close();
            tplWorkBook.close();
            tplStream.close();
            os.close();

        }catch (Exception e){
            System.out.println();
            LogClerk.errLog.error(e);
            response.setContentType("text/html");
            response.setHeader("Content-Type", "text/html");
            response.setHeader("Content-disposition", "");
            response.setCharacterEncoding("UTF-8");
            try {
                String eMsg = "系统内部异常，请联系管理员！";
                eMsg= java.net.URLEncoder.encode(eMsg.toString(),"UTF-8");
                //response.sendRedirect("/admin/stock/settlement/supplier?eMsg="+eMsg);
                os.close();
            } catch (IOException e1) {
                LogClerk.errLog.error(e1.getMessage());
            }
            throw SSException.get(EmenuException.ExportStorageSettlementCheckFailed, e);
        }finally {
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

}
