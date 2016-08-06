package com.emenu.web.controller.admin.revenue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.revenue.CheckoutDto;
import com.emenu.common.dto.revenue.CheckoutEachItemSumDto;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.DateUtils;
import com.emenu.common.utils.MySortList;
import com.emenu.common.utils.URLConstants;
import com.emenu.common.utils.WebConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * CheckoutController
 *
 * @athor pengpeng
 * @time 2016/8/1  14:56
 */
@Module(value = ModuleEnums.AdminCountSaleRanking)
@Controller
@RequestMapping(value = URLConstants.ADMIN_REVENUE_CHECKOUT_URL)
public class CheckoutController  extends AbstractController{

    @Module(value = ModuleEnums.AdminCountCheckoutList)
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList(Model model){
        try{
            List<Tag> list = tagService.queryLayer2Tag();
            model.addAttribute("tag" ,list);
            // 今天的开始时间
            model.addAttribute("firstToday", DateUtils.getToday());
            // 今天的结束时间
            model.addAttribute("lastToday", DateUtils.getToday());
            // 昨天的开始时间
            model.addAttribute("firstYesterday", DateUtils.getYesterday());
            // 昨天的结束时间
            model.addAttribute("lastYesterday", DateUtils.getYesterday());
            // 上周
            model.addAttribute("lastWeekFirstDay", DateUtils.getLastWeekFirstDay());
            model.addAttribute("lastWeekLastDay", DateUtils.getLastWeekLastDay());
            // 本周
            model.addAttribute("currentWeekFirstDay", DateUtils.getCurrentWeekFirstDay());
            model.addAttribute("currentWeekLastDay", DateUtils.getCurrentWeekLastDay());
            // 上月
            model.addAttribute("lastMonthFirstDay", DateUtils.getLastMonthFirstDay());
            model.addAttribute("lastMonthLastDay", DateUtils.getLastMonthLastDay());
            // 本月
            model.addAttribute("currentMonthFirstDay", DateUtils.getCurrentMonthFirstDay());
            model.addAttribute("currentMonthLastDay", DateUtils.getCurrentMonthLastDay());
            // 本年
            model.addAttribute("currentYearFirstDay", DateUtils.getCurrentYearFirstDay());
            model.addAttribute("currentYearLastDay", DateUtils.getCurrentYearLastDay());
            // 去年
            model.addAttribute("LastYearFirstDay", DateUtils.getLastYearFirstDay());
            model.addAttribute("LastYearLastDay", DateUtils.getLastYearLastDay());
        }catch(SSException e){
            sendErrMsg(e.getMessage());
            LogClerk.errLog.error(e);
            return WebConstants.sysErrorCode;
        }
        return "admin/revenue/checkout/list_home";
    }
    /** 权限*/
    @Module(value = ModuleEnums.AdminCountCheckoutList)
    @RequestMapping(value = "ajax/list/{pageNo}", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxSearch(@PathVariable("pageNo") Integer pageNo,
                           @RequestParam("pageSize") Integer pageSize,
                           @RequestParam("startTime") String startTime,
                           @RequestParam("endTime") String endTime,
                           CheckoutDto checkoutDto) throws SSException {
        Date startDate = DateUtils.getStartTime(startTime);
        Date endDate = DateUtils.getEndTime(endTime);
        // 对页面大小和页码预处理
        pageNo = pageNo == null ? 0 : pageNo;
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        checkoutDto.setPageNo(pageNo);
        checkoutDto.setPageSize(pageSize);
        List<CheckoutDto> checkoutDtoList = Collections.emptyList();
        JSONArray jsonArray = new JSONArray();
        JSONObject jsonMessage = new JSONObject();
        // 数据量
        Integer dataCount = 0;
        try{
            // 得到时间段的账单数
            dataCount = billAuditService.countCheckoutByTimePeriod(startDate,endDate);
            if(dataCount != 0){
                checkoutDtoList = billAuditService.queryCheckoutByTimePeriod(startDate, endDate, checkoutDto);
                // 按时间字段对账单排序
                MySortList<CheckoutDto>  list = new MySortList<CheckoutDto>();
                // false->升序
                list.sortByMethod(checkoutDtoList,"getCheckoutTime",false);
                for(CheckoutDto dto : checkoutDtoList){
                    JSONObject json = new JSONObject();
                    // 结账单号
                    json.put("checkoutPayId",checkoutPayService.queryByCheckoutId(dto.getCheckoutId()).getId());
                    // 餐台名
                    json.put("tableName",dto.getTableName());
                    // 收款人partyId
                    json.put("checkerPartyId",employeeService.queryByPartyId(dto.getCheckerPartyId()).getName());
                    // 结账时间
                    json.put("checkoutTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dto.getCheckoutTime()));
                    // 支付类型
                    json.put("checkoutType",dto.getCheckoutName());
                    // 消费金额
                    json.put("consumptionMoney",new DecimalFormat("0.00").format(dto.getConsumptionMoney()));
                    // 抹零金额
                    json.put("wipeZeroMoney",new DecimalFormat("0.00").format(dto.getWipeZeroMoney()));
                    // 实付金额
                    json.put("shouldPayMoney",new DecimalFormat("0.00").format(dto.getShouldPayMoney()));
                    // 宾客付款
                    json.put("totalPayMoney",new DecimalFormat("0.00").format(dto.getTotalPayMoney()));
                    // 找零金额
                    json.put("changeMoney",new DecimalFormat("0.00").format(dto.getChangeMoney()));
                    // 是否开发票
                    json.put("isInvoiced",dto.getIsInvoiced());
                    jsonArray.add(json);
                }
                jsonMessage.put("list", jsonArray);
                jsonMessage.put("dataCount", dataCount);
            }
            return sendJsonArray(jsonArray, dataCount, pageSize);
        }catch(SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    @Module(value = ModuleEnums.AdminCountCheckoutList)
    @RequestMapping(value = {"ajax/sum/list"}, method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxSearchSum(@RequestParam("startTime") String startTime,
                              @RequestParam("endTime") String endTime) throws SSException {
        Date startDate = DateUtils.getStartTime(startTime);
        Date endDate = DateUtils.getEndTime(endTime);
        Integer dataCount = 0;
        try {
            JSONObject json = new JSONObject();
            JSONObject jsonMessage = new JSONObject();
            // 得到时间段的账单数
            dataCount = billAuditService.countCheckoutByTimePeriod(startDate, endDate);
            if (dataCount != 0) {
                // 总计这块的json
                CheckoutEachItemSumDto checkoutEachItemSumDto = new CheckoutEachItemSumDto();
                checkoutEachItemSumDto = billAuditService.sumCheckoutEachItem(billAuditService.queryCheckoutByTime(startDate, endDate));
                json.put("billSum", checkoutEachItemSumDto.getCheckSum());
                json.put("cashSum", new DecimalFormat("0.00").format(checkoutEachItemSumDto.getCashSum()));
                json.put("vipCardSum", new DecimalFormat("0.00").format(checkoutEachItemSumDto.getVipCardSum()));
                json.put("bankCardSum", new DecimalFormat("0.00").format(checkoutEachItemSumDto.getBankCardSum()));
                json.put("alipaySum", new DecimalFormat("0.00").format(checkoutEachItemSumDto.getAlipaySum()));
                json.put("weChatSum", new DecimalFormat("0.00").format(checkoutEachItemSumDto.getWeChatSum()));
                json.put("consumptionMoneySum", new DecimalFormat("0.00").format(checkoutEachItemSumDto.getConsumptionMoneySum()));
                json.put("wipeZeroMoneySum", new DecimalFormat("0.00").format(checkoutEachItemSumDto.getWipeZeroMoneySum()));
                json.put("totalPayMoneySum", new DecimalFormat("0.00").format(checkoutEachItemSumDto.getTotalPayMoneySum()));
                json.put("changeMoneySum", new DecimalFormat("0.00").format(checkoutEachItemSumDto.getChangeMoneySum()));
                json.put("shouldPayMoneySum", new DecimalFormat("0.00").format(checkoutEachItemSumDto.getShouldPayMoneySum()));
                json.put("invoiceSum", checkoutEachItemSumDto.getInvoiceSum());

            }
            return sendJsonObject(json,AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    @Module(value = ModuleEnums.AdminCountCheckoutList)
    @RequestMapping(value = "exportToExcel",method = RequestMethod.GET)
    @ResponseBody
    public String exportToExcel(@RequestParam("startTime")String startTime,
                                @RequestParam("endTime")String endTime){
        try{
            Date startDate = DateUtils.getStartTime(startTime);
            Date endDate = DateUtils.getEndTime(endTime);
            billAuditService.exportToExcel(startDate,endDate,getResponse());
        }catch(SSException e){
            sendErrMsg(e.getMessage());
            LogClerk.errLog.error(e);
            return WebConstants.sysErrorCode;
        }
        return "admin/revenue/checkout/list_home";
    }
}
