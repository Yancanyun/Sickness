package com.emenu.service.rank.impl;

import com.emenu.common.dto.dish.DishDto;
import com.emenu.common.dto.dish.DishPackageDto;
import com.emenu.common.dto.order.CheckOrderDto;
import com.emenu.common.dto.order.OrderDishDto;
import com.emenu.common.dto.rank.DishSaleRankDto;
import com.emenu.common.dto.storage.StorageCheckDto;
import com.emenu.common.entity.dish.DishPackage;
import com.emenu.common.entity.order.Order;
import com.emenu.common.entity.order.OrderDish;
import com.emenu.common.enums.ExcelExportTemplateEnums;
import com.emenu.common.enums.dish.PackageStatusEnums;
import com.emenu.common.enums.order.OrderDishPresentedEnums;
import com.emenu.common.enums.order.OrderDishStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.DateUtils;
import com.emenu.service.dish.DishPackageService;
import com.emenu.service.dish.DishService;
import com.emenu.service.dish.tag.TagService;
import com.emenu.service.order.OrderService;
import com.emenu.service.rank.DishSaleRankService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.IOUtil;
import freemarker.template.utility.DateUtil;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.rmi.runtime.Log;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by guofengrui on 2016/7/26.
 */
@Service("dishSaleRankService")
public class DishSaleRankServiceImpl implements DishSaleRankService {
    @Autowired
    private OrderService orderService;

    @Autowired
    private DishPackageService dishPackageService;

    @Autowired
    private DishService dishService;

    @Autowired
    private TagService tagService;

    @Override
    public List<OrderDish> queryOrderDishByTimePeroid(Date startTime ,Date endTime) throws SSException{
        // 得到这个时间段中所下的订单，CheckOrderDto里面包含订单信息和订单菜品信息
        try{
            List<CheckOrderDto> checkOrderDtoList= orderService.queryOrderByTimePeroid2(startTime, endTime);
            List<OrderDish> orderDishList = new ArrayList<OrderDish>();
            if(checkOrderDtoList!=null){
                for(CheckOrderDto checkOrderDto :checkOrderDtoList){
                    // 某个订单的订单菜品的List，中间可能包含赠送的和退菜的
                    List<OrderDish> list = checkOrderDto.getOrderDishs();
                    // 把集合中退菜和赠菜的菜品去除
                    for(OrderDish orderDish :list){
                        if(orderDish.getIsPresentedDish() != OrderDishPresentedEnums.IsPresentedDish.getId()
                                ||orderDish.getIsPresentedDish() != OrderDishStatusEnums.IsBack.getId()){
                            orderDishList.add(orderDish);
                        }
                    }
                }
            }
            return orderDishList;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetOrderDishByTimePeriodFailed,e);
        }
    }
    @Override
    public List<DishSaleRankDto> queryDishSaleRankDtoByTimePeroid(Date startTime ,Date endTime) throws SSException{
        try{
            List<DishSaleRankDto> dishSaleRankDtoList= new ArrayList<DishSaleRankDto>();
            List<OrderDish> orderDishList = this.queryOrderDishByTimePeroid(startTime ,endTime);
            // 存菜品(套餐)的数量的map
            Map<Integer,BigDecimal> mapQuality = new HashMap<Integer, BigDecimal>();
            // 存菜品(套餐)的总金额的map
            Map<Integer,BigDecimal> mapMoney = new HashMap<Integer, BigDecimal>();
            // 存菜品
            // 点的菜品里面包括套餐和普通菜品，要分开讨论
            if(orderDishList != null){
                for(OrderDish orderDish:orderDishList){
                    if(orderDish.getIsPackage() == PackageStatusEnums.IsPackage.getId()){
                        // 套餐的数量
                        if(mapQuality.containsKey(orderDish.getPackageId())){
                            mapQuality.put(orderDish.getPackageId(),mapQuality.get(orderDish.getPackageId()).add(BigDecimal.valueOf(orderDish.getPackageQuantity())));
                        }else{
                            mapQuality.put(orderDish.getPackageId(),BigDecimal.valueOf(orderDish.getPackageQuantity()));
                        }
                        // 会员和非会员的价格
                        if(orderDish.getVipDishPrice()!=null){
                            BigDecimal money = orderDish.getSalePrice().multiply(BigDecimal.valueOf(orderDish.getPackageQuantity())).multiply(orderDish.getDiscount()).multiply(BigDecimal.valueOf(0.1));
                            if(mapMoney.containsKey(orderDish.getPackageId())){
                                mapMoney.put(orderDish.getPackageId(),mapMoney.get(orderDish.getPackageId()).add(money));
                            }else{
                                mapMoney.put(orderDish.getPackageId(),money);
                            }
                        }else{
                            BigDecimal money = orderDish.getVipDishPrice();
                            if(mapMoney.containsKey(orderDish.getPackageId())){
                                mapMoney.put(orderDish.getPackageId(),mapMoney.get(orderDish.getPackageId()).add(money));
                            }else{
                                mapMoney.put(orderDish.getPackageId(),money);
                            }
                        }
                    }else{
                        // 菜品的数量
                        if(mapQuality.containsKey(orderDish.getDishId())){
                            mapQuality.put(orderDish.getDishId(),mapQuality.get(orderDish.getDishId()).add(BigDecimal.valueOf(orderDish.getDishQuantity())));
                        }else{
                            mapQuality.put(orderDish.getDishId(),BigDecimal.valueOf(orderDish.getDishQuantity()));
                        }
                        // 会员和非会员的价格
                        if(orderDish.getVipDishPrice()!=null){
                            BigDecimal money = orderDish.getSalePrice().multiply(BigDecimal.valueOf(orderDish.getDishQuantity())).multiply(orderDish.getDiscount()).multiply(BigDecimal.valueOf(0.1));
                            if(mapMoney.containsKey(orderDish.getPackageId())){
                                mapMoney.put(orderDish.getDishId(),mapMoney.get(orderDish.getDishId()).add(money));
                            }else{
                                mapMoney.put(orderDish.getDishId(),money);
                            }
                        }else{
                            BigDecimal money = orderDish.getVipDishPrice();
                            if(mapMoney.containsKey(orderDish.getPackageId())){
                                mapMoney.put(orderDish.getDishId(),mapMoney.get(orderDish.getDishId()).add(money));
                            }else{
                                mapMoney.put(orderDish.getDishId(),money);
                            }
                        }
                    }
                }
            }
            // 遍历map
            for (Map.Entry<Integer, BigDecimal> entry : mapMoney.entrySet()) {
                DishSaleRankDto dishSaleRankDto = new DishSaleRankDto();
                dishSaleRankDto.setDishId(entry.getKey());
                // 金额保留两位小数
                dishSaleRankDto.setConsumeSum(entry.getValue().setScale(2,BigDecimal.ROUND_HALF_UP));
                dishSaleRankDto.setDishName(dishService.queryById(entry.getKey()).getName());
                // 数量取整
                dishSaleRankDto.setNum(mapQuality.get(entry.getKey()).intValue());
                dishSaleRankDto.setTagId(dishService.queryById(entry.getKey()).getTagId());
                dishSaleRankDto.setTagName(tagService.queryById(dishService.queryById(entry.getKey()).getTagId()).getName());
                dishSaleRankDtoList.add(dishSaleRankDto);
            }
            return dishSaleRankDtoList;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetDishSaleRankDtoByTimePeriodFailed,e);
        }
    }

    @Override
    public List<DishSaleRankDto> listAll() throws SSException{
        try{
            Date startTime = new Date(0,0,0,0,0,0);
            Date endTime = new Date(1000,0,0,0,0,0);
            List<DishSaleRankDto> dishSaleRankDtoList = this.queryDishSaleRankDtoByTimePeroid(startTime,endTime);
            return dishSaleRankDtoList;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.ListAllDishSaleRankDtoFailed,e);
        }
    }

    @Override
    public List<DishSaleRankDto> queryDishSaleRankDtoByTimePeroidAndTagId(Date startTime ,Date endTime,Integer tagId) throws SSException{
        try{
            List<DishSaleRankDto> dishSaleRankDtoList = this.queryDishSaleRankDtoByTimePeroid(startTime,endTime);
            List<DishSaleRankDto> dishSaleRankDtoList2 = new ArrayList<DishSaleRankDto>();
            for(DishSaleRankDto dishSaleRankDto :dishSaleRankDtoList){
                if((tagId).equals(dishSaleRankDto.getTagId())){
                    dishSaleRankDtoList2.add(dishSaleRankDto);
                }
            }
            return dishSaleRankDtoList2;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetOrderDishDtoByTimePeriodAndTagIdFailed,e);
        }
    }

    @Override
    public void exportToExcel(Date startTime ,Date endTime,List<Integer> tagIds ,HttpServletResponse response) throws SSException{
        OutputStream os = null;
        try {
            List<DishSaleRankDto> dishSaleRankDtoList2 = new ArrayList<DishSaleRankDto>();
            List<DishSaleRankDto> dishSaleRankDtoList = Collections.emptyList();
            if(tagIds!=null){
                for(Integer tagId:tagIds){
                    dishSaleRankDtoList = this.queryDishSaleRankDtoByTimePeroidAndTagId(startTime,endTime,tagId);
                    for(DishSaleRankDto dishSaleRankDto : dishSaleRankDtoList){
                        dishSaleRankDtoList2.add(dishSaleRankDto);
                    }
                }
            }else{
                dishSaleRankDtoList2 = this.queryDishSaleRankDtoByTimePeroid(startTime,endTime);
            }

            // 设置输出流
            // 设置excel文件名和sheetName
            String filename = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            filename = ExcelExportTemplateEnums.AdminRankDishSaleRankList.getName() + sdf.format(new Date());
            response.setContentType("application/msexcel");
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(filename.getBytes("gbk"), "ISO8859-1") + ".xls");
            os = response.getOutputStream();
            // 获取模板
            InputStream tplStream = IOUtil.getFileAsStream(ExcelExportTemplateEnums.AdminRankDishSaleRankList.getFilePath());
            Workbook tplWorkBook = Workbook.getWorkbook(tplStream);
            WritableWorkbook outBook = Workbook.createWorkbook(os,tplWorkBook);
            // 获取sheet往sheet里面写数据
            WritableSheet sheet = outBook.getSheet(0) ;
            int row = 2;
            for(DishSaleRankDto dishSaleRankDto : dishSaleRankDtoList2){
                // 序号
                Label labelNumber = new Label(0, row , String.valueOf(row - 1));
                sheet.addCell(labelNumber);
                // 菜品名称
                Label labelDishName = new Label(1,row,dishSaleRankDto.getDishName());
                sheet.addCell(labelDishName);
                // 菜品大类
                Label labelTagName = new Label(2,row,dishSaleRankDto.getTagName());
                sheet.addCell(labelTagName);
                // 销售数量
                Label labelNum = new Label(3,row,String.valueOf(dishSaleRankDto.getNum()));
                sheet.addCell(labelNum);
                // 消费金额
                Label labelConsumeSum = new Label(4,row,dishSaleRankDto.getConsumeSum().toString());
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
            throw SSException.get(EmenuException.ExportToExcelFailed, e);
        }
        finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    LogClerk.errLog.error(e);
                    throw SSException.get(EmenuException.ExportToExcelFailed, e);
                }
            }
        }
    }

    @Override
    public List<DishSaleRankDto> queryDishSaleRankDtoByTimePeroidAndTagIdAndPage(Date startTime
                                                                                ,Date endTime
                                                                                ,Integer tagId
                                                                                ,Integer pageSize
                                                                                ,Integer pageNumber) throws SSException{
        List<DishSaleRankDto> dishSaleRankDtoList = new ArrayList<DishSaleRankDto>();
        try{
            // 判断前台有没有选中菜品大类
            if(tagId != 0){
                dishSaleRankDtoList = this.queryDishSaleRankDtoByTimePeroidAndTagId(startTime,endTime,tagId);
                if(!dishSaleRankDtoList.isEmpty()){
                    if(dishSaleRankDtoList.size()<=pageSize){
                        dishSaleRankDtoList = dishSaleRankDtoList.subList(0,dishSaleRankDtoList.size());
                    }else {
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
                }
            } else {
                dishSaleRankDtoList = this.queryDishSaleRankDtoByTimePeroid(startTime, endTime);
                if(!dishSaleRankDtoList.isEmpty()){
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

                }
            }
            return dishSaleRankDtoList;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetPageOrderDishDtoByTimePeriodAndTagIdFailed, e);
        }
    }

    @Override
    public Integer countByTimePeroidAndTagId(Date startTime,Date endTime,Integer tagId) throws SSException{
        Integer number = 0;
        // 判断前台有没有选中菜品大类
        try{
            if(tagId != null&& tagId != 0 ){
                List<DishSaleRankDto> dishSaleRankDtoList = this.queryDishSaleRankDtoByTimePeroidAndTagId(startTime,endTime,tagId);
                if(dishSaleRankDtoList == null){
                    number = 0;
                }else{
                    number = dishSaleRankDtoList.size();
                }
            }else{
                List<DishSaleRankDto> dishSaleRankDtoList = this.queryDishSaleRankDtoByTimePeroid(startTime,endTime);
                if(dishSaleRankDtoList == null){
                    number = 0;
                }else{
                    number = dishSaleRankDtoList.size();
                }
            }
            return number;
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.GetCountByTimePeriodAndTagIdFailed, e);
        }
    }
}
