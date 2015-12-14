package com.emenu.web.controller.admin.vip;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.vip.VipRechargePlan;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

/**
 * AdminVipRechargePlanController
 *
 * @author: yangch
 * @time: 2015/12/14 16:08
 */
@Controller
@Module(ModuleEnums.AdminVipRechargePlan)
@RequestMapping(value = URLConstants.ADMIN_VIP_RECHARGE_PLAN_URL)
public class AdminVipRechargePlanController extends AbstractController {
    /**
     * 去会员充值方案管理页
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminVipRechargePlanList)
    @RequestMapping(value = {"", "list"}, method = RequestMethod.GET)
    public String toVipRechargePlanPage(Model model) {
        try {
            List<VipRechargePlan> vipRechargePlanList = vipRechargePlanService.listAll();
            model.addAttribute("vipRechargePlanList", vipRechargePlanList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/vip/recharge/plan/list_home";
    }

    /**
     * Ajax 添加会员充值方案
     * @param vipRechargePlan
     * @return
     */
    @Module(ModuleEnums.AdminVipRechargePlanNew)
    @RequestMapping(value = "ajax", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject ajaxNewVipRechargePlan(VipRechargePlan vipRechargePlan) {
        try {
            vipRechargePlanService.newVipRechargePlan(vipRechargePlan);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", vipRechargePlan.getId());

            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 修改会员充值方案
     * @param id
     * @param name
     * @return
     */
    @Module(ModuleEnums.AdminVipRechargePlanUpdate)
    @RequestMapping(value = "ajax/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject ajaxUpdateVipRechargePlan(@PathVariable("id") Integer id,
                                                @RequestParam("name") String name,
                                                @RequestParam("payAmount") BigDecimal payAmount,
                                                @RequestParam("rechargeAmount") BigDecimal rechargeAmount) {
        try {
            VipRechargePlan vipRechargePlan = new VipRechargePlan();
            vipRechargePlan.setId(id);
            vipRechargePlan.setName(name);
            vipRechargePlan.setPayAmount(payAmount);
            vipRechargePlan.setRechargeAmount(rechargeAmount);
            vipRechargePlanService.updateVipRechargePlan(id, vipRechargePlan);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 删除会员充值方案
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminVipRechargePlanDel)
    @RequestMapping(value = "ajax/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject ajaxDelVipRechargePlan(@PathVariable("id") Integer id) {
        try {
            vipRechargePlanService.delById(id);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 修改会员充值方案状态
     * @param id
     * @param status
     * @return
     */
    @Module(ModuleEnums.AdminVipRechargePlanUpdate)
    @RequestMapping(value = "ajax/status", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject updateStatus(@RequestParam("id") Integer id,
                                   @RequestParam("status") Integer status) {
        try {
            vipRechargePlanService.updateStatus(id, status);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}