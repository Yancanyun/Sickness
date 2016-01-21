package com.emenu.web.controller.admin.vip.integral;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.vip.MultipleIntegralPlan;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.vip.MultipleIntegralPlanStatusEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AdminMultipleIntegralPlanController
 *
 * @author Wang Liming
 * @date 2015/12/9 14:45
 */

@Controller
@RequestMapping(value = URLConstants.ADMIN_VIP_MULTIPLE_INTEGRAL_PLAN_URL)
public class AdminMultipleIntegralPlanController extends AbstractController{

    /**
     * 去多分积分方案页
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminVipMultipleIntegralPlanList)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toMultipleIntegralPlanPage(Model model) {
        try {
            List<MultipleIntegralPlan> plans = multipleIntegralPlanService.listAll();
            model.addAttribute("planList", plans);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/vip/multiple/integral/plan/list_home";
    }

    /**
     * ajax新增方案
     *
     * @param multipleIntegralPlan
     * @return
     */
    @Module(ModuleEnums.AdminVipMultipleIntegralPlanNew)
    @RequestMapping(value = "ajax", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject newMultipleIntegralPlan(MultipleIntegralPlan multipleIntegralPlan) {
        try {
            //将Time对应的String设为空，防止其往数据库里插
            multipleIntegralPlan.setStartTimeStr(null);
            multipleIntegralPlan.setEndTimeStr(null);

            MultipleIntegralPlan plan = multipleIntegralPlanService.newMultipleIntegralPlan(multipleIntegralPlan);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", plan.getId());
            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax修改多倍积分方案
     *
     * @param multipleIntegralPlan
     * @return
     */
    @Module(ModuleEnums.AdminVipMultipleIntegralPlanUpdate)
    @RequestMapping(value = "ajax/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject updateMultipleIntegralPlan(MultipleIntegralPlan multipleIntegralPlan) {
        try {
            //将Time对应的String设为空，防止其往数据库里插
            multipleIntegralPlan.setStartTimeStr(null);
            multipleIntegralPlan.setEndTimeStr(null);

            multipleIntegralPlanService.updateById(multipleIntegralPlan);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("id", null);
            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 修改餐台状态
     * @param id
     * @param status
     * @return
     */
    @Module(ModuleEnums.AdminVipMultipleIntegralPlanUpdate)
    @RequestMapping(value = "ajax/status", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject updateStatus(@RequestParam("id") Integer id,
                                   @RequestParam("status") Integer status) {
        try {
            multipleIntegralPlanService.updateStatusById(id, MultipleIntegralPlanStatusEnums.valueOf(status));
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax删除多倍积分方案
     *
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminVipMultipleIntegralPlanDelete)
    @RequestMapping(value = "ajax/{id}", method = RequestMethod.DELETE)
    public JSONObject delById(@PathVariable("id") Integer id) {
        try {
            multipleIntegralPlanService.delById(id);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
