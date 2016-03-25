package com.emenu.web.controller.admin.vip;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.vip.VipIntegralDto;
import com.emenu.common.dto.vip.VipIntegralTypeDto;
import com.emenu.common.entity.vip.VipGrade;
import com.emenu.common.entity.vip.VipIntegralPlan;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.vip.StatusEnums;
import com.emenu.common.enums.vip.VipIntegralPlanTypeEnums;
import com.emenu.common.enums.vip.grade.IntegralEnableStatusEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.*;

/**
 * @author chenyuting
 * @date 2016/1/21 15:11
 */
@Controller
@RequestMapping(value = URLConstants.ADMIN_VIP_INTEGRAL_URL)
public class AdminVipIntegralPlanController extends AbstractController {

    protected final static String NEW_SUCCESS_MSG = "添加成功";

    /**
     * 列表
     * @param gradeId
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminVipGrade, extModule = ModuleEnums.AdminVipIntegratePlanList)
    @RequestMapping(value = "list/{gradeId}", method = RequestMethod.GET)
    public String listAll(@PathVariable("gradeId") Integer gradeId,
                          Model model){
        List<VipIntegralDto> vipIntegralDtoList = Collections.emptyList();
        String gradeName = "";
        try{
            vipIntegralDtoList = vipIntegralPlanService.listDtosGradeId(gradeId);
            VipGrade vipGrade = vipGradeService.queryById(gradeId);
            model.addAttribute("vipIntegralDtoList",vipIntegralDtoList);
            model.addAttribute("vipGrade",vipGrade);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/vip/integral/list_home";
    }

    /**
     * 插入/修改
     * @param completeInfoIntegral
     * @param integralToMoney
     * @param conCashToIntegral
     * @param conCardToIntegral
     * @param conOnlineToIntegral
     * @param recCashToIntegral
     * @param recCardToIntegral
     * @param recOnlineToIntegral
     * @param gradeId
     * @param redirectAttributes
     * @return
     */
    @Module(value = ModuleEnums.AdminVipGrade, extModule = ModuleEnums.AdminVipIntegratePlanNew)
    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String newPlans(@RequestParam(value = "completeInfoIntegral",required = false) BigDecimal completeInfoIntegral,
                           @RequestParam(value = "integralToMoney",required = false) BigDecimal integralToMoney,
                           @RequestParam(value = "conCashToIntegral", required = false) BigDecimal conCashToIntegral,
                           @RequestParam(value = "conCardToIntegral", required = false) BigDecimal conCardToIntegral,
                           @RequestParam(value = "conOnlineToIntegral", required = false) BigDecimal conOnlineToIntegral,
                           @RequestParam(value = "recCashToIntegral", required = false) BigDecimal recCashToIntegral,
                           @RequestParam(value = "recCardToIntegral", required = false) BigDecimal recCardToIntegral,
                           @RequestParam(value = "recOnlineToIntegral", required = false) BigDecimal recOnlineToIntegral,
                           @RequestParam("gradeId") int gradeId,
                           RedirectAttributes redirectAttributes){
        List<VipIntegralPlan> vipIntegralPlanList = new ArrayList<VipIntegralPlan>();
        try{
            if (completeInfoIntegral != null){
                VipIntegralPlan vipIntegralPlan = new VipIntegralPlan();
                vipIntegralPlan.setType(VipIntegralPlanTypeEnums.CompleteInfoIntegral.getId());
                vipIntegralPlan.setValue(completeInfoIntegral);
                vipIntegralPlanList.add(vipIntegralPlan);
            }
            if (integralToMoney != null){
                VipIntegralPlan vipIntegralPlan = new VipIntegralPlan();
                vipIntegralPlan.setType(VipIntegralPlanTypeEnums.IntegralToMoney.getId());
                vipIntegralPlan.setValue(integralToMoney);
                vipIntegralPlanList.add(vipIntegralPlan);
            }
            if (conCashToIntegral != null){
                VipIntegralPlan vipIntegralPlan = new VipIntegralPlan();
                vipIntegralPlan.setType(VipIntegralPlanTypeEnums.ConCashToIntegral.getId());
                vipIntegralPlan.setValue(conCashToIntegral);
                vipIntegralPlanList.add(vipIntegralPlan);
            }
            if (conCardToIntegral != null){
                VipIntegralPlan vipIntegralPlan = new VipIntegralPlan();
                vipIntegralPlan.setType(VipIntegralPlanTypeEnums.ConCardToIntegral.getId());
                vipIntegralPlan.setValue(conCardToIntegral);
                vipIntegralPlanList.add(vipIntegralPlan);
            }
            if (conOnlineToIntegral != null){
                VipIntegralPlan vipIntegralPlan = new VipIntegralPlan();
                vipIntegralPlan.setType(VipIntegralPlanTypeEnums.ConOnlineToIntegral.getId());
                vipIntegralPlan.setValue(conOnlineToIntegral);
                vipIntegralPlanList.add(vipIntegralPlan);
                }
            if (recCashToIntegral != null){
                VipIntegralPlan vipIntegralPlan = new VipIntegralPlan();
                vipIntegralPlan.setType(VipIntegralPlanTypeEnums.RecCashToIntegral.getId());
                vipIntegralPlan.setValue(recCashToIntegral);
                vipIntegralPlanList.add(vipIntegralPlan);
                }
            if (recCardToIntegral != null){
                VipIntegralPlan vipIntegralPlan = new VipIntegralPlan();
                vipIntegralPlan.setType(VipIntegralPlanTypeEnums.RecCardToIntegral.getId());
                vipIntegralPlan.setValue(recCardToIntegral);
                vipIntegralPlanList.add(vipIntegralPlan);
                }
            if (recOnlineToIntegral != null){
                VipIntegralPlan vipIntegralPlan = new VipIntegralPlan();
                vipIntegralPlan.setType(VipIntegralPlanTypeEnums.RecOnlineToIntegral.getId());
                vipIntegralPlan.setValue(recOnlineToIntegral);
                vipIntegralPlanList.add(vipIntegralPlan);
            }
            vipIntegralPlanService.newPlans(vipIntegralPlanList, gradeId);
            redirectAttributes.addFlashAttribute("msg", NEW_SUCCESS_MSG);
            String redirectUrl = "/" + URLConstants.ADMIN_VIP_INTEGRAL_URL + "/list/" + gradeId;
            return "redirect:" + redirectUrl;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
    }

    /**
     * ajax修改
     * @param gradeId
     * @param status
     * @return
     */
    @Module(value = ModuleEnums.AdminVipGrade, extModule = ModuleEnums.AdminVipIntegratePlanUpdate)
    @RequestMapping(value = "ajax/status", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject updateStatus(@RequestParam("gradeId") int gradeId,
                                   @RequestParam("status") int status){
        try{
            vipIntegralPlanService.updateStatus(gradeId, status);
            vipGradeService.updateIntegralStatus(gradeId, IntegralEnableStatusEnums.valueOf(status));
        }  catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

    /**
     * ajax删除
     * @param id
     * @return
     */
    @Module(value = ModuleEnums.AdminVipGrade, extModule = ModuleEnums.AdminVipIntegratePlanDel)
    @RequestMapping(value = "ajax/del", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject deletePlan(@RequestParam("id") int id){
        try{
            vipIntegralPlanService.deletePlanById(id);
        }  catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
        }
        return sendJsonObject(AJAX_SUCCESS_CODE);
    }

}