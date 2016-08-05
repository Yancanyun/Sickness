package com.emenu.service.revenue.impl;

import com.emenu.common.dto.rank.DishSaleRankDto;
import com.emenu.common.dto.revenue.CheckoutDto;
import com.emenu.common.dto.revenue.CheckoutEachItemSumDto;
import com.emenu.common.entity.order.Checkout;
import com.emenu.common.entity.order.CheckoutPay;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.ExcelExportTemplateEnums;
import com.emenu.common.enums.checkout.CheckoutTypeEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.mapper.order.CheckoutMapper;
import com.emenu.mapper.order.CheckoutPayMapper;
import com.emenu.service.order.CheckoutPayService;
import com.emenu.service.order.CheckoutService;
import com.emenu.service.revenue.BillAuditService;
import com.emenu.service.table.TableService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import jxl.Workbook;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletResponse;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;
import com.pandawork.core.common.util.IOUtil;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import org.springframework.stereotype.Service;

/**
 * @athor pengpeng
 * @time 2016/8/4  10:26
 */
@Service("billAuditService")
public class BillAuditServiceImpl implements BillAuditService{
    @Autowired
    CheckoutMapper checkoutMapper;

    @Autowired
    CheckoutPayMapper checkoutPayMapper;

    @Autowired
    CheckoutPayService checkoutPayService;

    @Autowired
    CheckoutService checkoutService;

    @Autowired
    TableService tableService;

    @Override
    public Integer countCheckoutByTimePeriod(Date startDate, Date endDate) throws SSException{
        List<Checkout> checkoutList = new ArrayList<Checkout>();
        Integer count = 0;
        try{
            checkoutList = checkoutMapper.countCheckoutByTimePeriod(startDate, endDate);
            if(checkoutList != null){
                for(Checkout dto : checkoutList){
                    if(checkoutPayService.queryByCheckoutId(dto.getId()) != null){
                        count++;
                    }
                }
            }
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.CountCheckoutByTimePeriodFail,e);
        }
        return count;
    }

    @Override
    public List<CheckoutDto> queryCheckoutByTime(Date startDate, Date endDate) throws SSException{
        List<CheckoutDto> checkoutDtoList = new ArrayList<CheckoutDto>();
        List<Checkout> checkoutList = new ArrayList<Checkout>();
        try{
            if(Assert.isNotNull(startDate) && Assert.isNotNull(endDate)){
                // 从checkout表中取出该时间段的账单
                checkoutList = checkoutMapper.queryCheckoutByTime(startDate, endDate);
                // 将checkoutList加入到checkoutDtoList里去
                for(Checkout checkout : checkoutList){
                    // 该账单在如果免单或者并台，则在checkoutpay表里不生成
                    if(checkoutPayService.queryByCheckoutId(checkout.getId()) == null){
                        continue;
                    }
                    // 单个账单Dto
                    CheckoutDto checkoutDto = new CheckoutDto();
                    // 账单Id
                    checkoutDto.setCheckoutId(checkout.getId());
                    // 餐台Id
                    checkoutDto.setTableId(checkout.getTableId());
                    // 餐台名
                    checkoutDto.setTableName(tableService.queryById(checkout.getTableId()).getName());
                    // 收款人partyId
                    checkoutDto.setCheckerPartyId(checkout.getCheckerPartyId());
                    // 结账时间
                    checkoutDto.setCheckoutTime(checkout.getCheckoutTime());
                    // 支付类型
                    checkoutDto.setCheckoutType(checkoutPayService.queryByCheckoutId(checkout.getId()).getCheckoutType());
                    // 支付类型名
                    checkoutDto.setCheckoutName(CheckoutTypeEnums.valueOf(checkoutDto.getCheckoutType()).getType());
                    // 消费金额
                    checkoutDto.setConsumptionMoney(checkout.getConsumptionMoney());
                    // 抹零金额
                    checkoutDto.setWipeZeroMoney(checkout.getWipeZeroMoney());
                    // 实付金额
                    checkoutDto.setShouldPayMoney(checkout.getShouldPayMoney());
                    // 宾客付款
                    checkoutDto.setTotalPayMoney(checkout.getTotalPayMoney());
                    // 找零金额
                    checkoutDto.setChangeMoney(checkout.getChangeMoney());
                    // 消费类型
                    checkoutDto.setConsumptionType(checkout.getConsumptionType());
                    // 是否开发票
                    checkoutDto.setIsInvoiced(checkout.getIsInvoiced());
                    // 加入到checkoutDtoList里去
                    checkoutDtoList.add(checkoutDto);
                }
            }
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryCheckoutByTimePeriodFail,e);
        }
        return checkoutDtoList;
    }

    @Override
    public CheckoutEachItemSumDto sumCheckoutEachItem(List<CheckoutDto> checkoutDtoList) throws SSException{
        CheckoutEachItemSumDto checkoutEachItemSumDto = new CheckoutEachItemSumDto();
        try{
            // 有账单
            if(!checkoutDtoList.isEmpty()){
                // 账单总数
                checkoutEachItemSumDto.setCheckSum(checkoutDtoList.size());
                // 求出各项金额总和
                for(CheckoutDto checkoutDto : checkoutDtoList){
                    // 现金支付
                    if(checkoutDto.getCheckoutType() == CheckoutTypeEnums.Cash.getId()){
                        // 现金支付的第一笔(实付金额)
                        if(checkoutEachItemSumDto.getCashSum() == null){
                            checkoutEachItemSumDto.setCashSum(checkoutDto.getShouldPayMoney());
                        }else{
                            checkoutEachItemSumDto.setCashSum(checkoutEachItemSumDto.getCashSum().add(checkoutDto.getShouldPayMoney()));
                        }
                    }
                    // 会员卡支付
                    if(checkoutDto.getCheckoutType() == CheckoutTypeEnums.VipCard.getId()){
                        // 会员卡支付的第一笔(实付金额)
                        if(checkoutEachItemSumDto.getVipCardSum() == null){
                            checkoutEachItemSumDto.setVipCardSum(checkoutDto.getShouldPayMoney());
                        }else{
                            checkoutEachItemSumDto.setVipCardSum(checkoutEachItemSumDto.getVipCardSum().add(checkoutDto.getShouldPayMoney()));
                        }
                    }
                    // 银行卡支付
                    if(checkoutDto.getCheckoutType() == CheckoutTypeEnums.BankCard.getId()){
                        // 银行卡支付的第一笔(实付金额)
                        if(checkoutEachItemSumDto.getBankCardSum() == null){
                            checkoutEachItemSumDto.setBankCardSum(checkoutDto.getShouldPayMoney());
                        }else{
                            checkoutEachItemSumDto.setBankCardSum(checkoutEachItemSumDto.getBankCardSum().add(checkoutDto.getShouldPayMoney()));
                        }
                    }
                    // 支付宝支付
                    if(checkoutDto.getCheckoutType() == CheckoutTypeEnums.Alipay.getId()){
                        // 支付宝支付的第一笔(实付金额)
                        if(checkoutEachItemSumDto.getAlipaySum() == null){
                            checkoutEachItemSumDto.setAlipaySum(checkoutDto.getShouldPayMoney());
                        }else{
                            checkoutEachItemSumDto.setAlipaySum(checkoutEachItemSumDto.getAlipaySum().add(checkoutDto.getShouldPayMoney()));
                        }
                    }
                    // 微信支付
                    if(checkoutDto.getCheckoutType() == CheckoutTypeEnums.WeChat.getId()){
                        // 微信支付的第一笔(实付金额)
                        if(checkoutEachItemSumDto.getWeChatSum() == null){
                            checkoutEachItemSumDto.setWeChatSum(checkoutDto.getShouldPayMoney());
                        }else{
                            checkoutEachItemSumDto.setWeChatSum(checkoutEachItemSumDto.getWeChatSum().add(checkoutDto.getShouldPayMoney()));
                        }
                    }
                    // 消费金额总和
                    if(checkoutEachItemSumDto.getConsumptionMoneySum() == null){
                        checkoutEachItemSumDto.setConsumptionMoneySum(checkoutDto.getConsumptionMoney());
                    }else{
                        checkoutEachItemSumDto.setConsumptionMoneySum(checkoutEachItemSumDto.getConsumptionMoneySum().add(checkoutDto.getConsumptionMoney()));
                    }
                    // 抹零金额总和
                    if(checkoutEachItemSumDto.getWipeZeroMoneySum() == null){
                        checkoutEachItemSumDto.setWipeZeroMoneySum(checkoutDto.getWipeZeroMoney());
                    }else{
                        checkoutEachItemSumDto.setWipeZeroMoneySum(checkoutEachItemSumDto.getWipeZeroMoneySum().add(checkoutDto.getWipeZeroMoney()));
                    }
                    // 实付金额总和
                    if(checkoutEachItemSumDto.getShouldPayMoneySum() == null){
                        checkoutEachItemSumDto.setShouldPayMoneySum(checkoutDto.getShouldPayMoney());
                    }else{
                        checkoutEachItemSumDto.setShouldPayMoneySum(checkoutEachItemSumDto.getShouldPayMoneySum().add(checkoutDto.getShouldPayMoney()));
                    }
                    // 宾客付款总和
                    if(checkoutEachItemSumDto.getTotalPayMoneySum() == null){
                        checkoutEachItemSumDto.setTotalPayMoneySum(checkoutDto.getTotalPayMoney());
                    }else{
                        checkoutEachItemSumDto.setTotalPayMoneySum(checkoutEachItemSumDto.getTotalPayMoneySum().add(checkoutDto.getTotalPayMoney()));
                    }
                    // 找零金额总和
                    if(checkoutEachItemSumDto.getChangeMoneySum() == null){
                        checkoutEachItemSumDto.setChangeMoneySum(checkoutDto.getChangeMoney());
                    }else{
                        checkoutEachItemSumDto.setChangeMoneySum(checkoutEachItemSumDto.getChangeMoneySum().add(checkoutDto.getChangeMoney()));
                    }
                    // 发票总计
                    if(checkoutDto.getIsInvoiced() == 1){
                        if(checkoutEachItemSumDto.getInvoiceSum() == null){
                            checkoutEachItemSumDto.setInvoiceSum(1);
                        }else{
                            checkoutEachItemSumDto.setInvoiceSum(checkoutEachItemSumDto.getInvoiceSum() + 1);
                        }
                    }
                }
            }
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SumCheckoutEachItemFail,e);
        }
        return checkoutEachItemSumDto;
    }

    @Override
    public List<CheckoutDto> queryCheckoutByTimePeriod(Date startDate, Date endDate, CheckoutDto checkoutDto1) throws SSException {
        List<CheckoutDto> checkoutDtoList = new ArrayList<CheckoutDto>();
        List<Checkout> checkoutList = new ArrayList<Checkout>();
        List<CheckoutPay> checkoutPayList = new ArrayList<CheckoutPay>();
        try{
            if(Assert.isNotNull(startDate) && Assert.isNotNull(endDate)){
                Integer pageNo = checkoutDto1.getPageNo() <= 0 ? 0 : checkoutDto1.getPageNo() - 1;
                Integer offset = pageNo * checkoutDto1.getPageSize();
                // 从CheckoutPay表中取出该时间段的账单id号
                checkoutPayList = checkoutPayMapper.queryCheckoutPayByTimePeriod(startDate, endDate, offset, checkoutDto1);
                for(CheckoutPay checkoutPay : checkoutPayList){
                    Checkout checkout = new Checkout();
                    checkout = checkoutMapper.queryById(checkoutPay.getCheckoutId());
                    // 单个账单Dto
                    CheckoutDto checkoutDto = new CheckoutDto();
                    // 账单Id
                    checkoutDto.setCheckoutId(checkout.getId());
                    // 餐台Id
                    checkoutDto.setTableId(checkout.getTableId());
                    // 餐台名
                    checkoutDto.setTableName(tableService.queryById(checkout.getTableId()).getName());
                    // 收款人partyId
                    checkoutDto.setCheckerPartyId(checkout.getCheckerPartyId());
                    // 结账时间
                    checkoutDto.setCheckoutTime(checkout.getCheckoutTime());
                    // 支付类型
                    checkoutDto.setCheckoutType(checkoutPay.getCheckoutType());
                    // 支付类型名
                    checkoutDto.setCheckoutName(CheckoutTypeEnums.valueOf(checkoutDto.getCheckoutType()).getType());
                    // 消费金额
                    checkoutDto.setConsumptionMoney(checkout.getConsumptionMoney());
                    // 抹零金额
                    checkoutDto.setWipeZeroMoney(checkout.getWipeZeroMoney());
                    // 实付金额
                    checkoutDto.setShouldPayMoney(checkout.getShouldPayMoney());
                    // 宾客付款
                    checkoutDto.setTotalPayMoney(checkout.getTotalPayMoney());
                    // 找零金额
                    checkoutDto.setChangeMoney(checkout.getChangeMoney());
                    // 消费类型
                    checkoutDto.setConsumptionType(checkout.getConsumptionType());
                    // 是否开发票
                    checkoutDto.setIsInvoiced(checkout.getIsInvoiced());
                    // 加入到checkoutDtoList里去
                    checkoutDtoList.add(checkoutDto);
                }
            }
        }catch(Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryCheckoutByTimePeriodFail,e);
        }
        return checkoutDtoList;
    }

    @Override
    public void exportToExcel(Date startTime,Date endTime,HttpServletResponse response) throws SSException{
        OutputStream os = null;
        try{
            List<CheckoutDto> checkoutDtoList = this.queryCheckoutByTime(startTime, endTime);
            // 设置输出流
            // 设置excel文件名和sheetName
            String filename = "";
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmm");
            filename = ExcelExportTemplateEnums.AdminBillAuditList.getName() + sdf.format(new Date());
            response.setContentType("application/msexcel");
            response.setHeader("Content-disposition",
                    "attachment; filename=" + new String(filename.getBytes("gbk"), "ISO8859-1") + ".xls");
            os = response.getOutputStream();
            // 获取模板
            InputStream tplStream = IOUtil.getFileAsStream(ExcelExportTemplateEnums.AdminBillAuditList.getFilePath());
            Workbook tplWorkBook = Workbook.getWorkbook(tplStream);
            WritableWorkbook outBook = Workbook.createWorkbook(os,tplWorkBook);
            // 获取sheet往sheet里面写数据
            WritableSheet sheet = outBook.getSheet(0) ;
            int row = 2;
            for(CheckoutDto dto : checkoutDtoList){
                // 序号
                Label labelNumber = new Label(0, row , String.valueOf(row - 1));
                sheet.addCell(labelNumber);
                // 账单编号
                Label labelCheckoutId = new Label(1,row,String.valueOf(dto.getCheckoutId()));
                sheet.addCell(labelCheckoutId);

                // 餐台号
                Label labelTableId = new Label(2,row,String.valueOf(dto.getTableId()));
                sheet.addCell(labelTableId);

                // 餐台名称
                Label labelTableName = new Label(3,row,dto.getTableName());
                sheet.addCell(labelTableName);

                // 收银员
                Label labelCheckerPartyId = new Label(4,row,String.valueOf(dto.getCheckerPartyId()));
                sheet.addCell(labelCheckerPartyId);

                // 结账时间
                SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String time = sdf1.format(dto.getCheckoutTime());
                Label labelCheckoutTime = new Label(5,row,time);
                sheet.addCell(labelCheckoutTime);

                // 支付类型
                Label labelCheckoutType = new Label(6,row,dto.getCheckoutName());
                sheet.addCell(labelCheckoutType);

                // 消费金额
                Label labelConsumptionMoney = new Label(7,row,String.valueOf(dto.getConsumptionMoney()));
                sheet.addCell(labelConsumptionMoney);

                // 抹零金额
                Label labelWipeZeroMoney = new Label(8,row,String.valueOf(dto.getWipeZeroMoney()));
                sheet.addCell(labelWipeZeroMoney);

                // 宾客付款
                Label labelTotalPayMoney = new Label(9,row,String.valueOf(dto.getTotalPayMoney()));
                sheet.addCell(labelTotalPayMoney);

                // 找零金额
                Label labelChangeMoney = new Label(10,row,String.valueOf(dto.getChangeMoney()));
                sheet.addCell(labelChangeMoney);

                // 实付金额
                Label labelShouldPayMoney = new Label(11,row,String.valueOf(dto.getShouldPayMoney()));
                sheet.addCell(labelShouldPayMoney);

                // 发票
                String inVoice = dto.getIsInvoiced().equals(0) ? "未开" : "已开";
                Label labelIsInvoiced = new Label(12,row,inVoice);
                sheet.addCell(labelIsInvoiced);

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
                /** 这下面的地址怎么配？？？*/
                response.sendRedirect("/admin/revenue/settlement/check?eMsg="+eMsg);
                os.close();
            } catch (IOException e1) {
                LogClerk.errLog.error(e1.getMessage());
            }
            throw SSException.get(EmenuException.BillAuditListExportToExcelFailed, e);
        }
        finally {
            if (os != null) {
                try {
                    os.flush();
                    os.close();
                } catch (Exception e) {
                    LogClerk.errLog.error(e);
                    throw SSException.get(EmenuException.BillAuditListExportToExcelFailed, e);
                }
            }
        }
    }
}
