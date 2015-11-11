package com.emenu.web.controller.admin.meal;

import com.emenu.common.annotation.Module;
import com.emenu.common.entity.meal.MealPeriod;
import com.emenu.common.enums.meal.MealPeriodIsCurrentEnums;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
}
