package com.emenu.service.revenue.impl;

import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishPackageDto;
import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.dto.revenue.BackDishCountDto;
import com.emenu.common.entity.dish.Dish;
import com.emenu.common.entity.order.BackDish;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.enums.ExcelExportTemplateEnums;
import com.emenu.common.enums.dish.PackageStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.DateUtils;
import com.emenu.mapper.order.BackDishMapper;
import com.emenu.service.dish.DishPackageService;
import com.emenu.service.dish.tag.TagService;
import com.emenu.service.order.BackDishService;
import com.emenu.service.order.OrderDishService;
import com.emenu.service.order.OrderService;
import com.emenu.service.revenue.BackDishCountService;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;


/**
 * @author guofr
 * @time 2016/8/3 10:51
 */
@Service("backDishCountService")
public class BackDishCountServiceImpl implements BackDishCountService {
    @Autowired
    private OrderService orderService;

    @Autowired
    private BackDishService backDishService;

    @Autowired
    private BackDishMapper backDishMapper;

    @Autowired
    private DishPackageService dishPackageService;

    @Autowired
    private OrderDishService orderDishService;

    @Autowired
    private TagService tagService;
    @Override
    public List<BackDishCountDto> queryBackDishCountDtoByTimePeriodAndtagIds (List<Integer> tagIds,Date startTime,Date endTime) throws SSException{
        try {
            List<BackDishCountDto> backDishCountDtoList = backDishMapper.queryBackDishCountDtoByTimePeriod(startTime,endTime);
            List<BackDishCountDto> backDishCountDtoList2 = new ArrayList<BackDishCountDto>();
            // 把是属于套餐且套餐标识计算过的菜品集中移除
            List<BackDishCountDto> backDishCountDtoList3 = new ArrayList<BackDishCountDto>();
            List<Integer> packageFlagList = new ArrayList<Integer>();
            for(BackDishCountDto backDishCountDto : backDishCountDtoList){
                String day = DateUtils.formatDatetime2(backDishCountDto.getBackDish()).toString().substring(0,10);
                String backDishTime = DateUtils.formatDatetime2(backDishCountDto.getBackDish()).toString().substring(11,19);
                String orderDishTime = DateUtils.formatDatetime2(backDishCountDto.getOrderDish()).toString().substring(11, 19);
                backDishCountDto.setDay(day);
                backDishCountDto.setBackDishTime(backDishTime);
                backDishCountDto.setOrderDishTime(orderDishTime);
                String intervalTime = DateUtils.calculateDiffTimeAndFormat2(backDishCountDto.getOrderDish(), backDishCountDto.getBackDish());
                backDishCountDto.setIntervalTime(intervalTime);
                OrderDish orderDish = orderDishService.queryById(backDishCountDto.getOrderDishId());
                // 判断是不是套餐
                if(backDishCountDto.getIsPackage() == PackageStatusEnums.IsPackage.getId()){
                    // 判断存不存在该套餐标识,不存在的话说明没有计算过该套餐
                    if(!packageFlagList.contains(backDishCountDto.getPackageFlag())){
                        DishPackageDto dishPackageDto =dishPackageService.queryDishPackageById(backDishCountDto.getPackageId());
                        Integer packageNum = dishPackageService.queryDishQuantityByPackageIdAndDishId(orderDish.getPackageId(), orderDish.getDishId());
                        int num = BigDecimal.valueOf(backDishCountDto.getNum()).divide(BigDecimal.valueOf(packageNum)).intValue();
                        backDishCountDto.setNum(num);
                        backDishCountDto.setDishName(dishPackageDto.getDishDto().getName());
                        backDishCountDto.setSalePrice(orderDish.getSalePrice());
                        backDishCountDto.setAllPrice(orderDish.getSalePrice().multiply(BigDecimal.valueOf(num)));
                        backDishCountDto.setTagId(6);
                        // 得到相应的套餐数据后把该套餐标识存进List里面
                        packageFlagList.add(backDishCountDto.getPackageFlag());
                    }else{
                        backDishCountDtoList3.add(backDishCountDto);
                    }
                }else{
                    // 普通菜品的单价和总价
                    backDishCountDto.setSalePrice(orderDish.getSalePrice());
                    backDishCountDto.setAllPrice(orderDish.getSalePrice().multiply(BigDecimal.valueOf(backDishCountDto.getNum())));
                    int bigTagId = tagService.queryById(tagService.queryById(backDishCountDto.getTagId()).getpId()).getpId();
                    backDishCountDto.setTagId(bigTagId);
                }

            }
            // 移除
            backDishCountDtoList.removeAll(backDishCountDtoList3);
            // 根据菜品大类Id取数据
            if(tagIds != null){
                for(BackDishCountDto backDishCountDto : backDishCountDtoList){
                    if(tagIds.contains(backDishCountDto.getTagId())){
                        backDishCountDtoList2.add(backDishCountDto);
                    }
                }
            }else{
                backDishCountDtoList2 = backDishCountDtoList;
            }
            return backDishCountDtoList2;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetBackDishDtoByTimePeriodFailed,e);
        }
    }

    @Override
    public int CountByTimePeriodAndtagIds(List<Integer> tagIds,Date startTime,Date endTime) throws SSException{
        try{
            return this.queryBackDishCountDtoByTimePeriodAndtagIds(tagIds,startTime,endTime).size();
        }catch(SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetCountBackDishDtoByTimePeriodFailed,e);
        }
    }

    @Override
    public List<BackDishCountDto> queryPageBackDishCountDtoByTimePeriodAndtagIds (Integer pageNumber, Integer pageSize, List<Integer> tagIds,Date startTime,Date endTime) throws SSException{
        try{
            List<BackDishCountDto> backDishCountDtoList = this.queryBackDishCountDtoByTimePeriodAndtagIds(tagIds,startTime,endTime);
            if(backDishCountDtoList.size()<=pageSize){
                backDishCountDtoList = backDishCountDtoList.subList(0,backDishCountDtoList.size());
            }else{
                if(backDishCountDtoList.size()%pageSize==0){
                    if(pageNumber == 1){
                        backDishCountDtoList = backDishCountDtoList.subList(0,pageSize);
                    }else{
                        backDishCountDtoList = backDishCountDtoList.subList(pageSize * (pageNumber - 1), pageSize * pageNumber );
                    }
                }else{
                    if(pageNumber == 1){
                        backDishCountDtoList = backDishCountDtoList.subList(0,pageSize);
                    }else if(pageNumber == ((backDishCountDtoList.size()/pageSize)+1)){
                        backDishCountDtoList = backDishCountDtoList.subList(pageSize*(pageNumber - 1),backDishCountDtoList.size());
                    }else{
                        backDishCountDtoList = backDishCountDtoList.subList(pageSize * (pageNumber - 1), pageSize * pageNumber );
                    }
                }
            }
            return backDishCountDtoList;
        }catch(SSException e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetPageBackDishDtoByTimePeriodFailed,e);
        }
    }

    @Override
    public void exportToExcel(List<Integer> tagIds,Date startTime,Date endTime,HttpServletResponse response) throws SSException{
        OutputStream os = null;
        try{
            List<BackDishCountDto> list = this.queryBackDishCountDtoByTimePeriodAndtagIds(tagIds,startTime,endTime);
            // 设置输出流
            // 设置excel文件名和sheetName
            String filename = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            filename = ExcelExportTemplateEnums.AdminBackDishCountList.getName() + sdf.format(new Date());
            response.setContentType("application/msexcel");
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(filename.getBytes("gbk"), "ISO8859-1") + ".xls");
            os = response.getOutputStream();
            // 获取模板
            InputStream tplStream = IOUtil.getFileAsStream(ExcelExportTemplateEnums.AdminBackDishCountList.getFilePath());
            Workbook tplWorkBook = Workbook.getWorkbook(tplStream);
            WritableWorkbook outBook = Workbook.createWorkbook(os,tplWorkBook);
            // 获取sheet往sheet里面写数据
            WritableSheet sheet = outBook.getSheet(0) ;
            int row = 2;
            for(BackDishCountDto backDishCountDto : list){
                // 编号
                Label labelNumber = new Label(0, row , String.valueOf(backDishCountDto.getOrderId()));
                sheet.addCell(labelNumber);
                // 日期
                Label labelDay = new Label(1,row,backDishCountDto.getDay());
                sheet.addCell(labelDay);
                // 点菜时间
                Label labelOrderDishTime = new Label(2,row,backDishCountDto.getOrderDishTime());
                sheet.addCell(labelOrderDishTime);
                // 退菜时间
                Label labelBackDishTime = new Label(3,row,backDishCountDto.getBackDishTime());
                sheet.addCell(labelBackDishTime);
                // 间隔时间
                Label labelIntervalTime = new Label(4,row,backDishCountDto.getIntervalTime());
                sheet.addCell(labelIntervalTime);
                // 退菜人
                Label labelBackMan = new Label(5,row,backDishCountDto.getBackMan());
                sheet.addCell(labelBackMan);
                // 餐台
                Label labelTable = new Label(6,row,backDishCountDto.getTableName());
                sheet.addCell(labelTable);
                // 菜品名称
                Label labelDishName = new Label(7,row,backDishCountDto.getDishName());
                sheet.addCell(labelDishName);
                // 菜品单价
                Label labelSalePrice = new Label(8,row,backDishCountDto.getSalePrice().toString());
                sheet.addCell(labelSalePrice);
                // 退菜数量
                Label labelNum = new Label(9,row,backDishCountDto.getNum().toString());
                sheet.addCell(labelNum);
                // 金额
                Label labelAllPrice = new Label(10,row,backDishCountDto.getAllPrice().toString());
                sheet.addCell(labelAllPrice);
                // 原因
                Label labelReason = new Label(11,row,backDishCountDto.getReason());
                sheet.addCell(labelReason);
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
            throw SSException.get(EmenuException.BackDishCountExportToExcelFailed, e);
        }
        finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    LogClerk.errLog.error(e);
                    throw SSException.get(EmenuException.BackDishCountExportToExcelFailed, e);
                }
            }
        }
    }
}


