package com.emenu.web.controller.admin.revenue;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.revenue.BackDishCountDto;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.DateUtils;
import com.emenu.common.utils.URLConstants;
import com.emenu.common.utils.WebConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * BackDishController
 *
 * @athor guofengrui
 * @time 2016/8/4  09:34*/


@Module(value = ModuleEnums.AdminCountSaleRanking)
@Controller
@RequestMapping(value = URLConstants.ADMIN_REVENUE_BACKDISH_URL)
public class BackDishController extends AbstractController{

    @Module(value = ModuleEnums.AdminCountBackDishList)
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList(Model model){
        try{
            List<Tag> list = tagService.queryLayer2Tag();
            model.addAttribute("list" ,list);
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
        return "admin/revenue/backdish/list_home";
    }

    @Module(value = ModuleEnums.AdminCountBackDishList)
    @RequestMapping(value = "ajax/list/{pageNumber}",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxSearch(@PathVariable("pageNumber")Integer pageNumber,
                                 @RequestParam("pageSize") Integer pageSize,
                                 @RequestParam("startTime")String startTime1,
                                 @RequestParam("endTime")String endTime1,
                                 @RequestParam(value = "tagId" ,required = false) List<Integer> tagIds){
        // 对起始时间和结束时间处理
        Date startTime =  DateUtils.getStartTime(startTime1);
        Date endTime =  DateUtils.getEndTime(endTime1);
        // 对页面大小和页码预处理
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        pageNumber = pageNumber == null ? 1 : pageNumber;
        int dataCount = 0;
        List<BackDishCountDto> backDishCountDtoList = Collections.emptyList();
        try{
            backDishCountDtoList = backDishCountService.queryPageBackDishCountDtoByTimePeriodAndtagIds(pageNumber, pageSize, tagIds, startTime, endTime);
            dataCount = backDishCountService.CountByTimePeriodAndtagIds(tagIds,startTime,endTime);
        }catch(SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONArray jsonArray = new JSONArray();
        for (BackDishCountDto backDishCountDto : backDishCountDtoList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("dishId",backDishCountDto.getOrderId());
            jsonObject.put("day",backDishCountDto.getDay());
            jsonObject.put("orderDishTime",backDishCountDto.getOrderDishTime());
            jsonObject.put("backDishTime", backDishCountDto.getBackDishTime());
            jsonObject.put("intervalTime",backDishCountDto.getIntervalTime());
            String backMan = (backDishCountDto.getBackMan() == null)? "" : backDishCountDto.getBackMan();
            jsonObject.put("backMan",backMan);
            jsonObject.put("table",backDishCountDto.getTableName());
            jsonObject.put("dishName",backDishCountDto.getDishName());
            jsonObject.put("unitPrice",backDishCountDto.getSalePrice());
            jsonObject.put("num",backDishCountDto.getNum());
            jsonObject.put("allPrice",backDishCountDto.getAllPrice());
            jsonObject.put("reason",backDishCountDto.getReason());
            jsonArray.add(jsonObject);
        }
        return sendJsonArray(jsonArray, dataCount, pageSize);
    }

    @Module(value = ModuleEnums.AdminCountBackDishList)
    @RequestMapping(value = "exportToExcel",method = RequestMethod.GET)
    @ResponseBody
    public String exportToExcel(@RequestParam("startTime")String startTime1,
                                @RequestParam("endTime")String endTime1,
                                @RequestParam(value = "tagId" ,required = false) List<Integer> tagIds){
        // 对起始时间和结束时间处理
        Date startTime =  DateUtils.getStartTime(startTime1);
        Date endTime =  DateUtils.getEndTime(endTime1);
        try{
            backDishCountService.exportToExcel(tagIds,startTime,endTime,getResponse());
        }catch(SSException e){
            sendErrMsg(e.getMessage());
            LogClerk.errLog.error(e);
            return WebConstants.sysErrorCode;
        }
        return "admin/revenue/backdish/list_home";
    }
}
