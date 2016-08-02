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
    public JSON ajaxSearch(@PathVariable("pageNumber") Integer pageNumber,
                           @RequestParam("pageSize")  Integer pageSize,
                           @RequestParam("startTime") Date startTime,
                           @RequestParam("endTime") Date endTime,
                           @RequestParam(value = "tagId",required = false) List<Integer> tagIds){
        // 对页面大小和页码预处理
        pageSize = pageSize == null ? DEFAULT_PAGE_SIZE : pageSize;
        pageNumber = pageNumber == null ? 1 : pageNumber;
        List<DishSaleRankDto> dishSaleRankDtoList = Collections.emptyList();
        List<DishSaleRankDto> dishSaleRankDtoList2 = Collections.emptyList();
        // 数据量
        int dataCount = 0;
        if(tagIds != null){
            try{
                for(Integer tagId:tagIds){
                    dataCount +=   dishSaleRankService.countByTimePeroidAndTagId(startTime,endTime,tagId);
                }
            }catch(SSException e){
                LogClerk.errLog.error(e);
                return sendErrMsgAndErrCode(e);
            }
            try{
                for(Integer tagId:tagIds){
                    dishSaleRankDtoList = dishSaleRankService.queryDishSaleRankDtoByTimePeroidAndTagIdAndPage(startTime, endTime, tagId, pageSize, pageNumber);
                    for(DishSaleRankDto dishSaleRankDto : dishSaleRankDtoList){
                        dishSaleRankDtoList2.add(dishSaleRankDto);
                    }
                }
            }catch(SSException e){
                LogClerk.errLog.error(e);
                return sendErrMsgAndErrCode(e);
            }
        }else{
            try{
                dataCount =   dishSaleRankService.countByTimePeroidAndTagId(startTime,endTime,0);
                dishSaleRankDtoList2 = dishSaleRankService.queryDishSaleRankDtoByTimePeroidAndTagIdAndPage(startTime, endTime, 0, pageSize, pageNumber);
            }catch(SSException e){
                LogClerk.errLog.error(e);
                return sendErrMsgAndErrCode(e);
            }

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
}
