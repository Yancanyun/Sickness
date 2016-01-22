package com.emenu.web.controller.admin.vip;

import com.emenu.common.dto.vip.VipIntegralDto;
import com.emenu.common.entity.vip.VipIntegralPlan;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.*;

/**
 * @author chenyuting
 * @date 2016/1/21 15:11
 */
@Controller
@RequestMapping(value = URLConstants.ADMIN_VIP_INTEGRAL_URL)
public class AdminVipIntegralPlanController extends AbstractController {

    @RequestMapping(value = "list/{gradeId}", method = RequestMethod.GET)
    public String listAll(@PathVariable("gradeId") Integer gradeId,
                          Model model){
        List<VipIntegralDto> vipIntegralDtoList = Collections.emptyList();
        String gradeName = "";
        try{
            vipIntegralDtoList = vipIntegralPlanService.listDtosGradeId(gradeId);
            gradeName = vipGradeService.queryById(gradeId).getName();
            model.addAttribute("vipIntegralDtoList",vipIntegralDtoList);
            model.addAttribute("gradeName",gradeName);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/vip/integral/list_home";
    }

    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String newPlan(@RequestParam("type") List<Integer> type){
        return "";
    }

}