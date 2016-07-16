package com.emenu.web.controller.bar.meal;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.meal.MealPeriod;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.meal.MealPeriodIsCurrentEnums;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * BarMealPeriodController
 *
 * @author: yangch
 * @time: 2016/7/16 9:41
 */
@Controller
@Module(ModuleEnums.BarMealPeriod)
@RequestMapping(value = URLConstants.BAR_MEAL_PERIOD_URL)
public class BarMealPeriodController extends AbstractController {
    /**
     * Ajax 显示餐段列表及当前餐段
     * @return
     */
    @Module(ModuleEnums.BarMealPeriod)
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject listMealPeriod() {
        try {
            JSONObject jsonObject = new JSONObject();

            List<MealPeriod> mealPeriodList = new ArrayList<MealPeriod>();
            mealPeriodList = mealPeriodService.listEnabledMealPeriod();
            MealPeriod nowMealPeriod = mealPeriodService.queryByCurrentPeriod(MealPeriodIsCurrentEnums.Using);

            if (Assert.isNull(mealPeriodList)) {
                throw SSException.get(EmenuException.QueryMealPeriodFail);
            }

            JSONArray jsonArray = new JSONArray();
            for (MealPeriod mealPeriod : mealPeriodList) {
                JSONObject mealPeriodJsonObject = new JSONObject();

                mealPeriodJsonObject.put("mealPeriodId", mealPeriod.getId());
                mealPeriodJsonObject.put("mealPeriodName", mealPeriod.getName());
                if (mealPeriod.getId() == nowMealPeriod.getId()) {
                    mealPeriodJsonObject.put("isNow", 1);
                } else {
                    mealPeriodJsonObject.put("isNow", 0);
                }

                jsonArray.add(mealPeriodJsonObject);
            }

            jsonObject.put("mealPeriodList", jsonArray);

            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 切换餐段
     * @param mealPeriodId
     * @return
     */
    @Module(ModuleEnums.BarMealPeriod)
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject listMealPeriod(@RequestParam Integer mealPeriodId) {
        try {
            mealPeriodService.updateCurrentMealPeriod(mealPeriodId, MealPeriodIsCurrentEnums.Using);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
