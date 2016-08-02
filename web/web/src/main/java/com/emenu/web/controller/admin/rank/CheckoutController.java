package com.emenu.web.controller.admin.rank;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.rank.CheckoutDto;
import com.emenu.common.dto.rank.CheckoutEachItemSumDto;
import com.emenu.common.dto.rank.DishSaleRankDto;
import com.emenu.common.dto.storage.ItemAndIngredientSearchDto;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.entity.storage.StorageItem;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.DateUtils;
import com.emenu.common.utils.URLConstants;
import com.emenu.common.utils.WebConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
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
            return "admin/revenue/checkout/list_home";
        }catch(SSException e){
            sendErrMsg(e.getMessage());
            LogClerk.errLog.error(e);
            return WebConstants.sysErrorCode;
        }
    }
    /** 下面这个Module是干什么的 ？？*/
    @Module(value = ModuleEnums.AdminCountSaleRanking, extModule = ModuleEnums.AdminCountCheckoutList)
    @RequestMapping(value = "ajax/list/{pageNumber}", method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxSearch(@PathVariable("pageNo") Integer pageNo,
                           @RequestParam("pageSize") Integer pageSize,
                           @RequestParam("startTime") String startTime,
                           @RequestParam("endTime") String endTime,
                           CheckoutDto checkoutDto) throws SSException {
        /** CheckoutEachItemSumDto checkoutEachItemSumDto = new CheckoutEachItemSumDto();
            各项的总计
         */
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        startTime = startTime + " " + "00:00:00";
        ParsePosition pos1 =new ParsePosition(0);
        Date startDate = sdf.parse(startTime, pos1);
        endTime = endTime + " " + "23:59:59";
        ParsePosition pos2 = new ParsePosition(0);
        Date endDate = sdf.parse(endTime, pos2);

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
            // 得到时间段的账单
            checkoutDtoList = checkoutService.queryCheckoutByTimePeriod(startDate, endDate, checkoutDto);
            /**要是这个时间段没有账单呢？？？？？？？*/
            if(!checkoutDtoList.isEmpty()){
                dataCount = checkoutDtoList.size();
                for(CheckoutDto dto : checkoutDtoList){
                    JSONObject json = new JSONObject();
                    // 结账单号
                    json.put("checkoutId",dto.getCheckoutId());
                    // 餐台号
                    json.put("tableId",dto.getTableId());
                    // 餐台名
                    json.put("tableName",dto.getTableName());
                    // 收款人partyId
                    json.put("checkerPartyId",dto.getCheckerPartyId());
                    // 结账时间
                    json.put("checkoutTime",dto.getCheckoutTime());
                    // 支付类型
                    json.put("checkoutType",dto.getCheckoutType());
                    // 消费金额
                    json.put("consumptionMoney",dto.getConsumptionMoney());
                    // 抹零金额
                    json.put("wipeZeroMoney",dto.getWipeZeroMoney());
                    // 实付金额
                    json.put("shouldPayMoney",dto.getShouldPayMoney());
                    // 宾客付款
                    json.put("totalPayMoney",dto.getTotalPayMoney());
                    // 找零金额
                    json.put("changeMoney",dto.getChangeMoney());
                    // 是否开发票
                    json.put("isInvoiced",dto.getIsInvoiced());
                    jsonArray.add(json);
                }
                jsonMessage.put("list", jsonArray);
                jsonMessage.put("dataCount", dataCount);
            }
        }catch(SSException e){
                LogClerk.errLog.error(e);
                return sendErrMsgAndErrCode(e);
        }
        return jsonMessage;
    }
}
