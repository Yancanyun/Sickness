package com.emenu.web.controller.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.call.CallWaiter;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.mapper.call.CallWaiterMapper;
import com.emenu.service.call.CallWaiterService;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 * CallServiceController
 *
 * @author quanyibo
 * @date 2016/5/23
 */

@Controller
@Module(ModuleEnums.AdminRestaurantCallWaiter)
@RequestMapping(value = URLConstants.ADMIN_CALL_WAITER_URL)
public class CallWaiterController extends AbstractController{

    /**
     * 去服务管理首页
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantCallWaiterList)
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String toCallWaiterPage(Model model)
    {
        List<CallWaiter> callWaiter =new ArrayList<CallWaiter>();
        try {
            callWaiter = callWaiterService.queryAllCallWaiter();
            model.addAttribute("callWaiter", callWaiter);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/restaurant/call/call_management_home";
    }

    /**
     * 添加服务类型
     *
     * @param callWaiter
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantCallWaiterNew)
    @RequestMapping(value = "" , method = RequestMethod.POST)
    public String newCallWaiter(CallWaiter callWaiter,RedirectAttributes redirectAttributes)
    {
        try {
            callWaiterService.newCallWaiter(callWaiter);
            String successUrl = "/" + URLConstants.ADMIN_CALL_WAITER_URL;
            redirectAttributes.addFlashAttribute("msg",NEW_SUCCESS_MSG);
            return "redirect:"+successUrl;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            String failedUrl = "/" + URLConstants.ADMIN_MEAL_PERIOD_URL;
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
            return "redirect:"+failedUrl;
        }
    }

    /**
     * ajax删除服务类型
     *
     * @param id
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantCallWaiterDel)
    @RequestMapping(value = "/ajax/del" , method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxDelCallWaiter(@RequestParam("id")Integer id)
    {
        try {
            callWaiterService.delCallWaiter(id);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax修改服务类型
     *
     * @param callWaiter
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantCallWaiterUpdate)
    @RequestMapping(value = "/ajax/update" , method = RequestMethod.GET)
    @ResponseBody
    public JSONObject ajaxUpdateCallWaiter(CallWaiter callWaiter)
    {
        try {
            callWaiterService.updateCallWaiter(callWaiter);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax修改启用状态
     *
     * @param callWaiter
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantCallWaiterUpdate)
    @RequestMapping(value = "/ajax/status/update" , method = RequestMethod.GET)
    @ResponseBody
    public JSONObject updateCallWaiterStatus(@RequestParam("id") Integer id
            ,@RequestParam("status")Integer status)
    {
        try {
            callWaiterService.updateCallWaiterStatus(status,id);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 添加服务类型的时ajax获取添加默认值
     *
     * @param callWaiter
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantCallWaiter)
    @RequestMapping(value = "/ajax/get/tolerant/state" , method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getTolerantState()
    {
        JSONObject jsonObject = new JSONObject();
        List<CallWaiter> callWaiter = new ArrayList<CallWaiter>();
        try {
            callWaiter = callWaiterService.queryAllCallWaiter();
            if(callWaiter.isEmpty())//若没有服务类型则默认值为1
            jsonObject.put("weight",1);
            else//否则为做大的值加1
            jsonObject.put("weight",callWaiter.get(callWaiter.size()-1).getWeight()+1);
            return sendJsonObject(jsonObject,AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
