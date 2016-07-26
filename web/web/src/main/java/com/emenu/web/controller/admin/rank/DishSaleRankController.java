package com.emenu.web.controller.admin.rank;

import com.emenu.common.annotation.Module;
import com.emenu.common.dto.rank.DishSaleRankDto;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

/**
 * DishSaleRankController
 *
 * @author guofengrui
 * @date 2016/7/26.
 */
@Module(value = ModuleEnums.AdminDishSaleRanking)
@Controller
@RequestMapping(value = URLConstants.ADMIN_DISH_SALE_RANKING_URL)
public class DishSaleRankController extends AbstractController {

    @Module(value = ModuleEnums.AdminDishSaleRankingList)
    @RequestMapping(value = {""}, method = RequestMethod.GET)
    public String toList(Model model){
        try{
            List<DishSaleRankDto> dishSaleRankDtoList = dishSaleRankService.listAll();
            model.addAttribute("dishNameList", dishSaleRankDtoList);
            model.addAttribute("tagNameList",dishSaleRankDtoList);
            model.addAttribute("numList",dishSaleRankDtoList);
            model.addAttribute("consumeSumList",dishSaleRankDtoList);
            // 上周

            // 上月
            model.addAttribute("lastMonthFirstDay", DateUtils.getLastMonthFirstDay());
            model.addAttribute("lastMonthLastDay", DateUtils.getLastMonthLastDay());
            // 本月
            model.addAttribute("currentMonthFirstDay", DateUtils.getCurrentMonthFirstDay());
            model.addAttribute("currentMonthLastDay", DateUtils.getCurrentMonthLastDay());
            return "admin/dish/sale/ranking/list_home";
        }catch(SSException e){
            sendErrMsg(e.getMessage());
            LogClerk.errLog.error(e);
            return WebConstants.sysErrorCode;
        }
    }
}
