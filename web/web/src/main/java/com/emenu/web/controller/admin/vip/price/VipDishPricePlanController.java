package com.emenu.web.controller.admin.vip.price;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.vip.VipDishPricePlan;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Collections;
import java.util.List;

/**
 * 会员价方案Controller
 *
 * @author chenyuting
 * @date 2015/11/23 15:07
 */
@Controller
@RequestMapping(value = URLConstants.ADMIN_VIP_VIP_DISH_PRICE_PLAN_URL)
public class VipDishPricePlanController extends AbstractController{

    protected final static String NEW_SUCCESS_MSG = "添加成功";
    protected final static String NEW_FAIL_MSG = "添加失败";

    @Module(ModuleEnums.AdminVipVipDishPricePlanList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toList(Model model){
        List<VipDishPricePlan> vipDishPricePlanList = Collections.emptyList();
        try{
            vipDishPricePlanList = vipDishPricePlanService.listAll();
            model.addAttribute("vipDishPricePlanList", vipDishPricePlanList);
        }catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/vip/price/plan_list";
    }

    @Module(ModuleEnums.AdminVipVipDishPricePlanNew)
    @RequestMapping(value = "/new", method = RequestMethod.POST)
    public String newVipDishPricePlan(VipDishPricePlan vipDishPricePlan,
                                      RedirectAttributes redirectAttributes){
        try{
            vipDishPricePlanService.newPlan(vipDishPricePlan);
            redirectAttributes.addFlashAttribute("msg", NEW_SUCCESS_MSG);
            String redirectUrl = "/" + URLConstants.ADMIN_VIP_VIP_DISH_PRICE_PLAN_URL + "/list";
            return "redirect:" + redirectUrl;
        }catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            redirectAttributes.addFlashAttribute("msg", NEW_FAIL_MSG);
            String redirectUrl = "/" + URLConstants.ADMIN_VIP_VIP_DISH_PRICE_PLAN_URL + "/list";
            return "redirect:" + redirectUrl;
        }
    }

    @Module(ModuleEnums.AdminVipVipDishPricePlanUpdate)
    @RequestMapping(value = "/ajax", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject ajaxUpdate(VipDishPricePlan vipDishPricePlan,
                                 RedirectAttributes redirectAttributes){
        try{
            vipDishPricePlanService.updatePlan(vipDishPricePlan);
            redirectAttributes.addAttribute("msg", NEW_SUCCESS_MSG);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            redirectAttributes.addAttribute("msg", NEW_FAIL_MSG);
            return sendErrMsgAndErrCode(e);
        }
    }

    @Module(ModuleEnums.AdminVipVipDishPricePlanDelete)
    @RequestMapping(value = "/ajax/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject ajaxDel(@PathVariable("id") Integer id){
        try{
            vipDishPricePlanService.delPlanById(id);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

}