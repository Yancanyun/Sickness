package com.emenu.service.rank.impl;

import com.emenu.common.dto.rank.DishSaleRankDto;
import com.emenu.common.entity.dish.DishTag;
import com.emenu.common.enums.ExcelExportTemplateEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.service.dish.tag.TagService;
import com.emenu.service.rank.DishSaleRankService;
import com.emenu.service.rank.DishTagRankService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.IOUtil;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
/**
 * 营业分析中的菜品大类销售排行
 * DishTagRankServiceImpl
 *
 *
 * @Author guofengrui
 * @Date 2016/7/28.
 */
@Service("dishTagRankService")
public class DishTagRankServiceImpl implements DishTagRankService {
    @Autowired
    private DishSaleRankService dishSaleRankService;

    @Autowired
    private TagService tagService;

    @Override
    public List<DishSaleRankDto> queryDishSaleRankDtoByTimePeriod(Date startTime ,Date endTime) throws SSException{
        // 得到相应的菜品销售排行
        List<DishSaleRankDto> dishSaleRankDtoList = Collections.emptyList();
        // 菜品大类的销售排行
        List<DishSaleRankDto> dishSaleRankDtoList2 = new ArrayList<DishSaleRankDto>();

        try{
            dishSaleRankDtoList = dishSaleRankService.queryDishSaleRankDtoByTimePeriod(startTime, endTime);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetDishSaleRankDtoByTimePeriodFailed,e);
        }
        try{
            // 存菜品大类的数量的map
            Map<Integer,BigDecimal> mapQuality = new HashMap<Integer, BigDecimal>();
            // 存菜品大类的总金额的map
            Map<Integer,BigDecimal> mapMoney = new HashMap<Integer, BigDecimal>();
            for(DishSaleRankDto dishSaleRankDto : dishSaleRankDtoList){
                // 获得菜品大类的销售数量
                if(mapQuality.containsKey(dishSaleRankDto.getTagId())){
                    mapQuality.put(dishSaleRankDto.getTagId(),mapQuality.get(dishSaleRankDto.getTagId()).add(BigDecimal.valueOf(dishSaleRankDto.getNum())));
                }else{
                    mapQuality.put(dishSaleRankDto.getTagId(),BigDecimal.valueOf(dishSaleRankDto.getNum()));
                }
                // 获得菜品大类的销售金额
                if(mapMoney.containsKey(dishSaleRankDto.getTagId())){
                    mapMoney.put(dishSaleRankDto.getTagId(),mapMoney.get(dishSaleRankDto.getTagId()).add(dishSaleRankDto.getConsumeSum()));
                }else{
                    mapMoney.put(dishSaleRankDto.getTagId(),dishSaleRankDto.getConsumeSum());
                }
            }
            // 遍历map
            for (Map.Entry<Integer, BigDecimal> entry : mapMoney.entrySet()) {
                DishSaleRankDto dishSaleRankDto = new DishSaleRankDto();
                dishSaleRankDto.setTagId(entry.getKey());
                dishSaleRankDto.setTagName(tagService.queryById(entry.getKey()).getName());
                dishSaleRankDto.setNum(mapQuality.get(entry.getKey()).intValue());
                dishSaleRankDto.setConsumeSum(entry.getValue());
                dishSaleRankDtoList2.add(dishSaleRankDto);
            }
            return dishSaleRankDtoList2;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetDishTagRankFailed,e);
        }
    }
    @Override
    public List<DishSaleRankDto> listAll() throws SSException{
        // 得到相应的菜品销售排行
        List<DishSaleRankDto> dishSaleRankDtoList = Collections.emptyList();
        // 菜品大类的销售排行
        List<DishSaleRankDto> dishSaleRankDtoList2 = new ArrayList<DishSaleRankDto>();

        try{
            dishSaleRankDtoList = dishSaleRankService.listAll();
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetDishSaleRankDtoByTimePeriodFailed,e);
        }
        try{
            // 存菜品大类的数量的map
            Map<Integer,BigDecimal> mapQuality = new HashMap<Integer, BigDecimal>();
            // 存菜品大类的总金额的map
            Map<Integer,BigDecimal> mapMoney = new HashMap<Integer, BigDecimal>();
            for(DishSaleRankDto dishSaleRankDto : dishSaleRankDtoList){
                // 获得菜品大类的销售数量
                if(mapQuality.containsKey(dishSaleRankDto.getTagId())){
                    mapQuality.put(dishSaleRankDto.getTagId(),mapQuality.get(dishSaleRankDto.getTagId()).add(BigDecimal.valueOf(dishSaleRankDto.getNum())));
                }else{
                    mapQuality.put(dishSaleRankDto.getTagId(),BigDecimal.valueOf(dishSaleRankDto.getNum()));
                }
                // 获得菜品大类的销售金额
                if(mapMoney.containsKey(dishSaleRankDto.getTagId())){
                    mapMoney.put(dishSaleRankDto.getTagId(),mapMoney.get(dishSaleRankDto.getTagId()).add(dishSaleRankDto.getConsumeSum()));
                }else{
                    mapMoney.put(dishSaleRankDto.getTagId(),dishSaleRankDto.getConsumeSum());
                }
            }
            // 遍历map
            for (Map.Entry<Integer, BigDecimal> entry : mapMoney.entrySet()) {
                DishSaleRankDto dishSaleRankDto = new DishSaleRankDto();
                dishSaleRankDto.setTagId(entry.getKey());
                dishSaleRankDto.setTagName(tagService.queryById(entry.getKey()).getName());
                dishSaleRankDto.setNum(mapQuality.get(entry.getKey()).intValue());
                dishSaleRankDto.setConsumeSum(entry.getValue());
                dishSaleRankDtoList2.add(dishSaleRankDto);
            }
            return dishSaleRankDtoList2;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetDishTagAllFailed,e);
        }
    }
    @Override
    public List<DishSaleRankDto> queryDishSaleRankDtoByTimePeriodAndPage(Date startTime
                                                                        ,Date endTime
                                                                        ,Integer pageSize
                                                                        ,Integer pageNumber) throws SSException{
        List<DishSaleRankDto> dishSaleRankDtoList = new ArrayList<DishSaleRankDto>();
        try{
            dishSaleRankDtoList = this.queryDishSaleRankDtoByTimePeriod(startTime, endTime);
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetDishTagRankFailed,e);
        }
        try{
            if(dishSaleRankDtoList.size()<=pageSize){
                dishSaleRankDtoList = dishSaleRankDtoList.subList(0,dishSaleRankDtoList.size());
            }else{
                if(dishSaleRankDtoList.size()%pageSize==0){
                    if(pageNumber == 1){
                        dishSaleRankDtoList = dishSaleRankDtoList.subList(0,pageSize);
                    }else{
                        dishSaleRankDtoList = dishSaleRankDtoList.subList(pageSize * (pageNumber - 1), pageSize * pageNumber );
                    }
                }else{
                    if(pageNumber == 1){
                        dishSaleRankDtoList = dishSaleRankDtoList.subList(0,pageSize);
                    }else if(pageNumber == ((dishSaleRankDtoList.size()/pageSize)+1)){
                        dishSaleRankDtoList = dishSaleRankDtoList.subList(pageSize*(pageNumber - 1),dishSaleRankDtoList.size());
                    }else{
                        dishSaleRankDtoList = dishSaleRankDtoList.subList(pageSize * (pageNumber - 1), pageSize * pageNumber );
                    }
                }
            }
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetPageDishTagRankByTimePeriodAndTagIdFailed,e);
        }
        return dishSaleRankDtoList;
    }


    @Override
    public Integer countByTimePeriod(Date startTime,Date endTime) throws SSException{
        Integer number = 0;
        List<DishSaleRankDto> list = this.queryDishSaleRankDtoByTimePeriod(startTime,endTime);
        if(list.isEmpty()){
            number = 0;
        }else{
            number = list.size();
        }
        return number;
    }

    @Override
    public void exportToExcel(Date startTime,Date endTime,HttpServletResponse response) throws SSException{
        OutputStream os = null;
        try{
            List<DishSaleRankDto> list = this.queryDishSaleRankDtoByTimePeriod(startTime,endTime);
            // 设置输出流
            // 设置excel文件名和sheetName
            String filename = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            filename = ExcelExportTemplateEnums.AdminRankBigTagList.getName() + sdf.format(new Date());
            response.setContentType("application/msexcel");
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(filename.getBytes("gbk"), "ISO8859-1") + ".xls");
            os = response.getOutputStream();
            // 获取模板
            InputStream tplStream = IOUtil.getFileAsStream(ExcelExportTemplateEnums.AdminRankBigTagList.getFilePath());
            Workbook tplWorkBook = Workbook.getWorkbook(tplStream);
            WritableWorkbook outBook = Workbook.createWorkbook(os,tplWorkBook);
            // 获取sheet往sheet里面写数据
            WritableSheet sheet = outBook.getSheet(0) ;
            int row = 2;
            for(DishSaleRankDto dishSaleRankDto : list){
                // 序号
                Label labelNumber = new Label(0, row , String.valueOf(row - 1));
                sheet.addCell(labelNumber);
                // 菜品大类
                Label labelTagName = new Label(1,row,dishSaleRankDto.getTagName());
                sheet.addCell(labelTagName);
                // 销售数量
                Label labelNum = new Label(2,row,String.valueOf(dishSaleRankDto.getNum()));
                sheet.addCell(labelNum);
                // 消费金额
                Label labelConsumeSum = new Label(3,row,dishSaleRankDto.getConsumeSum().toString());
                sheet.addCell(labelConsumeSum);
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
                // 等出前台页面再加上
                response.sendRedirect("/admin/rank/settlement/check?eMsg="+eMsg);
                os.close();
            } catch (IOException e1) {
                LogClerk.errLog.error(e1.getMessage());
            }
            throw SSException.get(EmenuException.BigTagRankExportToExcelFailed, e);
        }
        finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    LogClerk.errLog.error(e);
                    throw SSException.get(EmenuException.BigTagRankExportToExcelFailed, e);
                }
            }
        }
    }
}
