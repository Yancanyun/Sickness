package com.emenu.web.controller.admin.vip;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.vip.VipGradeDto;
import com.emenu.common.entity.vip.VipDishPricePlan;
import com.emenu.common.entity.vip.VipGrade;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.util.List;

/**
 * AdminVipGradeController
 *
 * @author Wang LM
 * @date 2015/12/16 10:56
 */

@Controller
@RequestMapping(value = URLConstants.ADMIN_VIP_GRADE_URL)
public class AdminVipGradeController extends AbstractController {

    /**
     * 去会员等级管理页面
     *
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminVipGrade , extModule = ModuleEnums.AdminVipGradeList)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toVipGradePage(Model model) {
        try {
            List<VipGradeDto> vipGradeDtoList = vipGradeService.listAllVipGradeDto();
            model.addAttribute("vipGradeDtoList", vipGradeDtoList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/vip/grade/list_home";
    }

    /**
     * 去添加页
     *
     * @return
     */
    @Module(value = ModuleEnums.AdminVipGrade, extModule = ModuleEnums.AdminVipGradeNew)
    @RequestMapping(value = "new", method = RequestMethod.GET)
    public String toNewVipGradePage(Model model) {
        try {
            //返回会员价方案列表
            List<VipDishPricePlan> vipDishPricePlanList = vipDishPricePlanService.listAll();
            model.addAttribute("vipDishPricePlanList", vipDishPricePlanList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/vip/grade/new_home";
    }

    /**
     * 添加会员等级方案
     *
     * @param vipGrade
     * @param httpServletRequest
     * @param redirectAttributes
     * @return
     */
    @Module(value = ModuleEnums.AdminVipGradeNew, extModule = ModuleEnums.AdminVipGradeNew)
    @RequestMapping(value = "new", method = RequestMethod.POST)
    public String newVipGrade(VipGrade vipGrade, HttpServletRequest httpServletRequest,
                              RedirectAttributes redirectAttributes) {
        try {
            vipGradeService.newVipGrade(vipGrade);
            String successUrl = "/" + URLConstants.ADMIN_VIP_GRADE_URL;
            redirectAttributes.addFlashAttribute("msg", NEW_SUCCESS_MSG);
            return "redirect:" + successUrl;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            String failedUrl = "/" + URLConstants.ADMIN_VIP_GRADE_URL + "/new";
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
            return "redirect:" + failedUrl;
        }
    }

    /**
     * 去编辑页
     *
     * @param id
     * @param model
     * @return
     */
    @Module(value = ModuleEnums.AdminVipGrade, extModule = ModuleEnums.AdminVipGradeUpdate)
    @RequestMapping(value = "update/{id}", method = RequestMethod.GET)
    public String toUpdateVipGradePage(@PathVariable("id") int id, Model model) {
        try {
            //返回会员价方案列表
            List<VipDishPricePlan> vipDishPricePlanList = vipDishPricePlanService.listAll();
            model.addAttribute("vipDishPricePlanList", vipDishPricePlanList);

            VipGrade vipGrade = vipGradeService.queryById(id);
            model.addAttribute("vipGrade", vipGrade);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/vip/grade/update_home";
    }

    /**
     * 编辑会员等级方案
     *
     * @param vipGrade
     * @param httpServletRequest
     * @param redirectAttributes
     * @return
     */
    @Module(value = ModuleEnums.AdminVipGradeUpdate, extModule = ModuleEnums.AdminVipGradeUpdate)
    @RequestMapping(value = "update/{id}" , method = RequestMethod.POST)
    public String updateById(@PathVariable Integer id, VipGrade vipGrade,
                             HttpServletRequest httpServletRequest,
                             RedirectAttributes redirectAttributes) {
        try {
            vipGradeService.updateById(vipGrade);
            String successUrl = "/" + URLConstants.ADMIN_VIP_GRADE_URL;
            redirectAttributes.addFlashAttribute("msg", UPDATE_SUCCESS_MSG);
            return "redirect:" + successUrl;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            String failedUrl = "/" + URLConstants.ADMIN_VIP_GRADE_URL + "/update/" + vipGrade.getId();
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
            return "redirect:" + failedUrl;
        }
    }

    /**
     * ajax删除会员等级方案
     *
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminVipGradeDelete)
    @RequestMapping(value = "ajax/{id}", method = RequestMethod.DELETE)
    public JSONObject delById(@PathVariable("id") Integer id) {
        try {
            vipGradeService.delById(id);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax 判断会员最低消费金额是否重复
     * @param id
     * @param minConsumption
     * @return
     */
    @RequestMapping(value = "ajax/min/{id}",method = RequestMethod.POST)
    public JSONObject isMinConsumptionUsed(@PathVariable("id") Integer id,
                                           @RequestParam("minConsumption")BigDecimal minConsumption){
        try{
            VipGrade vipGrade = vipGradeService.countMinConsumptionUsingNumber(minConsumption);
            if (!Assert.isNull(vipGrade)){
                if (vipGrade.getId() != id){
                    throw SSException.get(EmenuException.MinConsumptionExist);
                }
            }
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
