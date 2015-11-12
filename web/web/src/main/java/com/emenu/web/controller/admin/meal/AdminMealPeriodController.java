package com.emenu.web.controller.admin.meal;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.meal.MealPeriod;
import com.emenu.common.enums.meal.MealPeriodIsCurrentEnums;
import com.emenu.common.enums.meal.MealPeriodStateEnums;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
        return null;
    }

    /**
     * 新增餐段
     *
     * @param mealPeriod
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantMealPeriodNew)
    @RequestMapping(value = "", method = RequestMethod.POST)
    public String newMealPeriod(MealPeriod mealPeriod){
        try {
            mealPeriodService.newMealPeriod(mealPeriod);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return null;
    }

    /**
     * 修改餐段
     *
     * @param mealPeriod
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantMealPeriodUpdate)
    @RequestMapping(value = "", method = RequestMethod.PUT)
    public String updateMealPeriod(MealPeriod mealPeriod){
        try {
            mealPeriodService.updateMealPeriod(mealPeriod);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return ADMIN_SYS_ERR_PAGE;
        }
        return null;
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
     * @param state
     * @return
     */
    @Module(ModuleEnums.AdminRestaurantMealPeriodUpdate)
    @RequestMapping(value = "ajax/state/{mealPeriodId}", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject updateStateById(@PathVariable("mealPeriodId") Integer mealPeriodId,
                                      @RequestParam("state") Integer state){
        try {
            mealPeriodService.updateStateById(mealPeriodId, MealPeriodStateEnums.valueOf(state));
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    @Module(ModuleEnums.AdminRestaurantMealPeriodUpdate)
    @RequestMapping(value = "ajax/current/{mealPeriodId}", method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject updateCurrentMealPeriod(@PathVariable("mealPeriodId") Integer mealPeriodId){
        try {
            MealPeriod currentMealPeriod = mealPeriodService.queryByCurrentPeriod(MealPeriodIsCurrentEnums.Using);
            if (!Assert.isNull(currentMealPeriod)){
                if (!Assert.isNull(currentMealPeriod.getId())){
                    mealPeriodService.updateCurrentMealPeriod(currentMealPeriod.getId(), MealPeriodIsCurrentEnums.UnUsing);
                }
            }
            mealPeriodService.updateCurrentMealPeriod(mealPeriodId, MealPeriodIsCurrentEnums.Using);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
