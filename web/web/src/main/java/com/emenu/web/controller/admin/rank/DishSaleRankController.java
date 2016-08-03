package com.emenu.web.controller.admin.rank;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.rank.DishSaleRankDto;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.*;
import com.emenu.service.rank.DishSaleRankService;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Member;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * 营业分析中的菜品销售排行
 *DishSaleRankController
 *
 * @Author guofengrui
 * @Date 2016/7/26.
 */
@Module(value = ModuleEnums.AdminCountDishSaleRankingList)
@Controller
@RequestMapping(value = URLConstants.ADMIN_COUNT_DISH_SALE_RANKING_URL)
public class DishSaleRankController extends AbstractController {

    @Module(value = ModuleEnums.AdminCountDishSaleRankingList)
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
            return "admin/rank/dishsale/list_home";
        }catch(SSException e){
            sendErrMsg(e.getMessage());
            LogClerk.errLog.error(e);
            return WebConstants.sysErrorCode;
        }
    }

    @Module(value = ModuleEnums.AdminCountDishSaleRankingList)
    @RequestMapping(value = "ajax/list/{pageNumber}",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxSearch(@PathVariable("pageNumber")Integer pageNumber,
                           @RequestParam("pageSize") Integer pageSize,
                           @RequestParam("startTime")String startTime1,
                           @RequestParam("endTime")String endTime1,
                           @RequestParam(value = "tagIds" ,required = false) List<Integer> tagIds,
                           @RequestParam(value = "orderBy" ,required = false) String orderBy,
                           @RequestParam(value = "orderType" ,required = false) Integer orderType){
        // 对起始时间和结束时间处理
        Date startTime =  DateUtils.getStartTime(startTime1);
        Date endTime =  DateUtils.getEndTime(endTime1);
        // 对页面大小和页码预处理
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        pageNumber = pageNumber == null ? 1 : pageNumber;
        List<DishSaleRankDto> dishSaleRankDtoList = Collections.emptyList();
        List<DishSaleRankDto> dishSaleRankDtoList2 = new ArrayList<DishSaleRankDto>();
        // 数据量
        int dataCount = 0;
        try{
            if(tagIds != null){
                for(Integer tagId:tagIds){
                    dishSaleRankDtoList = dishSaleRankService.queryDishSaleRankDtoByTimePeriodAndTagIdAndPage(startTime, endTime, tagId, pageSize, pageNumber);
                    dishSaleRankDtoList2.addAll(dishSaleRankDtoList);
                }
                dataCount = dishSaleRankDtoList2.size();
            }else{
                    dataCount = dishSaleRankService.countByTimePeriodAndTagId(startTime,endTime,0);
                    dishSaleRankDtoList2 = dishSaleRankService.queryDishSaleRankDtoByTimePeriodAndTagIdAndPage(startTime, endTime, 0, pageSize, pageNumber);
            }
            // 排序
            if(orderBy!=null && orderType!=null){
                MySortList<DishSaleRankDto> msList = new MySortList<DishSaleRankDto>();
                // 按销售数量
                if("num".equals(orderBy)){
                    // 降序
                    if(orderType == 1){
                        msList.sortByMethod(dishSaleRankDtoList2,"getNum",true);
                    }else{
                        msList.sortByMethod(dishSaleRankDtoList2,"getNum",false);
                    }
                }else{
                    if(orderType == 1){
                        msList.sortByMethod(dishSaleRankDtoList2,"getConsumeSum",true);
                    }else{
                        msList.sortByMethod(dishSaleRankDtoList2,"getConsumeSum",false);
                    }
                }
            }
        } catch(SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }

        JSONArray jsonArray = new JSONArray();
            for (DishSaleRankDto dishSaleRankDto :dishSaleRankDtoList2){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("dishId",dishSaleRankDto.getDishId());
                jsonObject.put("dishName",dishSaleRankDto.getDishName());
                jsonObject.put("tagId",dishSaleRankDto.getTagId());
                jsonObject.put("bigTag",dishSaleRankDto.getTagName());
                jsonObject.put("num", dishSaleRankDto.getNum());
                jsonObject.put("consumeSum",dishSaleRankDto.getConsumeSum());
                jsonArray.add(jsonObject);
            }
        return sendJsonArray(jsonArray, dataCount, pageSize);
    }

    @Module(value = ModuleEnums.AdminCountDishSaleRankingList)
    @RequestMapping(value = "exportToExcel",method = RequestMethod.GET)
    @ResponseBody
    public String exportToExcel(@RequestParam("startTime")String startTime1,
                                @RequestParam("endTime")String endTime1,
                                @RequestParam(value = "tagIds" ,required = false) List<Integer> tagIds){
        try{
            Date startTime = DateUtils.getStartTime(startTime1);
            Date endTime = DateUtils.getEndTime(endTime1);
            dishSaleRankService.exportToExcel(startTime,endTime,tagIds,getResponse());
        }catch(SSException e){
            sendErrMsg(e.getMessage());
            LogClerk.errLog.error(e);
            return WebConstants.sysErrorCode;
        }
        return "admin/rank/dishsale/list_home";
    }


}
