package com.emenu.web.controller.admin.rank;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.rank.DishSaleRankDto;
import com.emenu.common.entity.dish.Tag;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.DateUtils;
import com.emenu.common.utils.URLConstants;
import com.emenu.common.utils.WebConstants;
import com.emenu.service.rank.DishSaleRankService;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * DishSaleRankController
 *
 * @author guofengrui
 * @date 2016/7/26.
 */
@Module(value = ModuleEnums.AdminCountDishSaleRanking)
@Controller
@RequestMapping(value = URLConstants.ADMIN_COUNT_DISH_SALE_RANKING_URL)
public class DishSaleRankController extends AbstractController {

    @Module(value = ModuleEnums.AdminCountDishSaleRankingList)
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList(Model model){
        try{
            List<Tag> list = tagService.queryLayer2Tag();
            model.addAttribute("tagId" ,list);
            model.addAttribute("tagName" ,list);
            // 今天的开始时间
            model.addAttribute("firstToday", DateUtils.getToday().concat(" 00:00:00"));
            // 今天的结束时间
            model.addAttribute("lastToday", DateUtils.getToday().concat(" 23:59:59"));
            // 昨天的开始时间
            model.addAttribute("firstYesterday", DateUtils.getYesterday().concat(" 00:00:00"));
            // 昨天的结束时间
            model.addAttribute("lastYesterday", DateUtils.getYesterday().concat(" 23:59:59"));
            // 上周
            model.addAttribute("lastWeekFirstDay", DateUtils.getLastWeekFirstDay().concat(" 00:00:00"));
            model.addAttribute("lastWeekLastDay", DateUtils.getLastWeekLastDay().concat(" 23:59:59"));
            // 本周
            model.addAttribute("currentWeekFirstDay", DateUtils.getCurrentWeekFirstDay().concat(" 00:00:00"));
            model.addAttribute("currentWeekLastDay", DateUtils.getCurrentWeekLastDay().concat(" 23:59:59"));
            // 上月
            model.addAttribute("lastMonthFirstDay", DateUtils.getLastMonthFirstDay().concat(" 00:00:00"));
            model.addAttribute("lastMonthLastDay", DateUtils.getLastMonthLastDay().concat(" 23:59:59"));
            // 本月
            model.addAttribute("currentMonthFirstDay", DateUtils.getCurrentMonthFirstDay().concat(" 00:00:00"));
            model.addAttribute("currentMonthLastDay", DateUtils.getCurrentMonthLastDay().concat(" 23:59:59"));
            return "admin/dish/sale/ranking/list_home";
        }catch(SSException e){
            sendErrMsg(e.getMessage());
            LogClerk.errLog.error(e);
            return WebConstants.sysErrorCode;
        }
    }

    @Module(value = ModuleEnums.AdminCountDishSaleRankingList)
    @RequestMapping(value = "ajax/list/{pageNumber}",method = RequestMethod.GET)
    @ResponseBody
    public JSON ajaxSearch(@PathVariable("pageNumber")Integer pageNumber,
                           @RequestParam("pageSize") Integer pageSize,
                           @RequestParam("startTime")Date startTime,
                           @RequestParam("endTime")Date endTime,
                           @RequestParam("tagId")Integer tagId){
        // 对页面大小和页码预处理
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        pageNumber = pageNumber == null ? 1 : pageNumber;
        // 数据量
        int dataCount = 0;
        try{
            dataCount = dishSaleRankService.countByTimePeroidAndTagId(startTime,endTime,tagId);
        }catch(SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        List<DishSaleRankDto> dishSaleRankDtoList = Collections.emptyList();
        try{
            dishSaleRankDtoList = dishSaleRankService.queryDishSaleRankDtoByTimePeroidAndTagIdAndPage(startTime, endTime, tagId, pageSize, pageNumber);
        }catch(SSException e){
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
        JSONArray jsonArray = new JSONArray();
            for (DishSaleRankDto dishSaleRankDto :dishSaleRankDtoList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("dishId",dishSaleRankDto.getDishId());
                jsonObject.put("dishName",dishSaleRankDto.getDishName());
                jsonObject.put("tagId",dishSaleRankDto.getTagId());
                jsonObject.put("tagName",dishSaleRankDto.getTagName());
                jsonObject.put("num",dishSaleRankDto.getNum());
                jsonObject.put("consumeSum",dishSaleRankDto.getConsumeSum());
                jsonArray.add(jsonObject);
            }
        return sendJsonArray(jsonArray, dataCount, pageSize);
    }
}
