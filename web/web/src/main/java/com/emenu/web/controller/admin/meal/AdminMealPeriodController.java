package com.emenu.web.controller.admin.meal;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.meal.MealPeriod;
import com.emenu.common.enums.meal.MealPeriodIsCurrentEnums;
import com.emenu.common.enums.meal.MealPeriodStatusEnums;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * MealPeriodController
 *
 * @author Wang Liming
 * @date 2015/11/11 9:31
 */

@Controller
@Module(ModuleEnums.AdminRestaurantMealPeriod)
@RequestMapping(value = URLConstants.ADMIN_MEAL_PERIOD_URL)
public class AdminMealPeriodController extends AbstractController{

    /**
     * 去餐段管理页面
     *
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantMealPeriodList)
    @RequestMapping(value = "", method = RequestMethod.GET)
    public String toMealPeriodPage(Model model){
        try {
            MealPeriod currentMealPeriod = mealPeriodService.queryByCurrentPeriod(MealPeriodIsCurrentEnums.Using);
            List<MealPeriod> mealPeriodList = mealPeriodService.listAll();
            model.addAttribute("currentMealPeriod", currentMealPeriod);
            model.addAttribute("mealPeriodList", mealPeriodList);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return "admin/restaurant/meal/period/list_home";
    }

    /**
     * 新增餐段
     *
     * @param mealPeriod
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantMealPeriodNew)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String newMealPeriod(MealPeriod mealPeriod, HttpServletRequest httpServletRequest,
                                RedirectAttributes redirectAttributes){
        try {
            mealPeriodService.newMealPeriod(mealPeriod);

            String successUrl = "/" + URLConstants.ADMIN_MEAL_PERIOD_URL;
            redirectAttributes.addFlashAttribute("msg", NEW_SUCCESS_MSG);
            return "redirect:" + successUrl;
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            String failedUrl = "/" + URLConstants.ADMIN_MEAL_PERIOD_URL;
            redirectAttributes.addFlashAttribute("msg", e.getMessage());
            return "redirect:" + failedUrl;
        }
    }

    /**
     * ajax修改餐段
     *
     * @param mealPeriod
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantMealPeriodUpdate)
    @RequestMapping(value = "ajax", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject updateMealPeriod(MealPeriod mealPeriod){
        try {
            mealPeriodService.updateMealPeriod(mealPeriod);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax删除餐段
     *
     * @param mealPeriodId
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantMealPeriodDel)
    @RequestMapping(value = "ajax/{mealPeriodId}", method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject delById(@PathVariable("mealPeriodId") Integer mealPeriodId){
        try {
            mealPeriodService.delById(mealPeriodId);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax修改启用状态
     *
     * @param mealPeriodId
     * @param status
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantMealPeriodUpdate)
    @RequestMapping(value = "ajax/status/{mealPeriodId}", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject updateStatusById(@PathVariable("mealPeriodId") Integer mealPeriodId,
                                       @RequestParam("status") Integer status){
        try {
            mealPeriodService.updateStatusById(mealPeriodId, MealPeriodStatusEnums.valueOf(status));
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax修改当前餐段
     *
     * @param mealPeriodId
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantMealPeriodUpdate)
    @RequestMapping(value = "ajax/current/{mealPeriodId}", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject updateCurrentMealPeriod(@PathVariable("mealPeriodId") Integer mealPeriodId){
        try {
            mealPeriodService.updateCurrentMealPeriod(mealPeriodId, MealPeriodIsCurrentEnums.Using);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax获得当前最后排序加1的值
     *
     * @return
     */
    @RequestMapping(value = "ajax/weight", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getLastWeight(){
        try {
            List<MealPeriod> mealPeriodList = mealPeriodService.listAll();
            MealPeriod mealPeriod = mealPeriodList.get(mealPeriodList.size() - 1);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("weight", mealPeriod.getWeight() + 1);
            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
