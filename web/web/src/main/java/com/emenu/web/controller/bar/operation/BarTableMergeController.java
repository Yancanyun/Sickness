package com.emenu.web.controller.bar.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.meal.MealPeriod;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.meal.MealPeriodIsCurrentEnums;
import com.emenu.common.enums.other.ModuleEnums;
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
 * BarTableMergeController
 *
 * @author: yangch
 * @time: 2016/7/14 9:02
 */
@Controller
@Module(ModuleEnums.BarTable)
@RequestMapping(value = URLConstants.BAR_TABLE_MERGE_URL)
public class BarTableMergeController extends AbstractController {
    /**
     * Ajax 获取某区域下可并台的餐台列表
     * @param areaId
     * @return
     */
    @Module(ModuleEnums.BarTableMerge)
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject mergeTableList(@RequestParam("areaId") int areaId) {
        try {
            //将该区域内所有餐台存入tableList
            List<Table> tableList = tableService.listByAreaId(areaId);

            JSONArray jsonArray = new JSONArray();

            if (Assert.isNull(tableList)) {
                return sendJsonArray(jsonArray);
            }

            // 根据餐段来判断哪些餐台可以显示出来
            for (Table table : tableList) {
                JSONObject jsonObject = new JSONObject();

                MealPeriod nowMealPeriod = mealPeriodService.queryByCurrentPeriod(MealPeriodIsCurrentEnums.Using);
                TableDto tableDto = tableService.queryTableDtoById(table.getId());
                if (tableDto.getMealPeriodList() == null || tableDto.getMealPeriodList().size() == 0) {
                    break;
                }
                for (MealPeriod mealPeriod : tableDto.getMealPeriodList()) {
                    if (mealPeriod.getId() == nowMealPeriod.getId()) {
                        jsonObject.put("tableId", table.getId());
                        jsonObject.put("tableName", table.getName());
                        jsonObject.put("status", table.getStatus());
                        jsonArray.add(jsonObject);
                        break;
                    }
                }
            }

            return sendJsonArray(jsonArray);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 执行并台操作
     * @param tableIdList
     * @return
     */
    @Module(ModuleEnums.BarTableMerge)
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject mergeTable(@RequestParam("tableIdList") String tableIdList) {
        try {
            // 把用String按逗号分割成List<Integer>
            List<Integer> tableIdIntList = new ArrayList<Integer>();
            String[] tableIdStrList = tableIdList.split(",");
            for (String tableIdString : tableIdStrList) {
                tableIdIntList.add(Integer.valueOf(tableIdString));
            }

            // 执行并台操作
            tableMergeService.mergeTable(tableIdIntList);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 取消并台操作
     * @param tableId
     * @return
     */
    @Module(ModuleEnums.BarTableMerge)
    @RequestMapping(value = "cancel", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject cancelMergeTable(@RequestParam("tableId") Integer tableId) {
        try {
            // 执行取消并台操作
            tableMergeService.cancelTableMerge(tableId);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
