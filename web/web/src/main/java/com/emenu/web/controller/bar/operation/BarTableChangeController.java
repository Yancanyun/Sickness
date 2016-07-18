package com.emenu.web.controller.bar.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.meal.MealPeriod;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.meal.MealPeriodIsCurrentEnums;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractAppBarController;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * BarTableChangeController
 *
 * @author: yangch
 * @time: 2015/12/10 16:54
 */
@Controller
@Module(ModuleEnums.BarTable)
@RequestMapping(value = URLConstants.BAR_TABLE_CHANGE_URL)
public class BarTableChangeController extends AbstractController {
    /**
     * Ajax 获取换台对话框中的数据
     * @return
     */
    @Module(ModuleEnums.BarTableChange)
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject toChangeTable(@RequestParam("tableId") Integer tableId) {
        try {
            //获取餐桌状态为可用的Table
            List<Table> tableList = tableService.listByStatus(TableStatusEnums.Enabled);

            //获取当前餐台的信息
            Table table = tableService.queryById(tableId);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("oldTableId", tableId);
            jsonObject.put("oldTableName", table.getName());
            jsonObject.put("oldPersonNum", table.getPersonNum());
            jsonObject.put("oldSeatNum", table.getSeatNum());
            jsonObject.put("oldSeatFee", table.getSeatFee());
            jsonObject.put("oldTableFee", table.getTableFee());

            JSONArray jsonArray = new JSONArray();

            if (Assert.isNull(tableList)) {
                return sendJsonArray(jsonArray);
            }

            for (Table t : tableList) {
                MealPeriod nowMealPeriod = mealPeriodService.queryByCurrentPeriod(MealPeriodIsCurrentEnums.Using);
                TableDto tableDto = tableService.queryTableDtoById(table.getId());
                if (tableDto.getMealPeriodList() == null || tableDto.getMealPeriodList().size() == 0) {
                    break;
                }

                for (MealPeriod mealPeriod : tableDto.getMealPeriodList()) {
                    if (mealPeriod.getId() == nowMealPeriod.getId()) {
                        JSONObject childJsonObject = new JSONObject();
                        childJsonObject.put("newTableId", t.getId());
                        childJsonObject.put("newTableName", t.getName());
                        childJsonObject.put("newSeatNum", t.getSeatNum());
                        childJsonObject.put("newSeatFee", t.getSeatFee());
                        childJsonObject.put("newTableFee", t.getTableFee());
                        jsonArray.add(childJsonObject);
                        break;
                    }
                }
            }

            jsonObject.put("jsonArray", jsonArray);

            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 执行换台操作
     * @param oldTableId
     * @param newTableId
     * @return
     */
    @Module(ModuleEnums.BarTableChange)
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject changeTable(@RequestParam("oldTableId") Integer oldTableId,
                                  @RequestParam("newTableId") Integer newTableId) {
        try {
            tableService.changeTable(oldTableId, newTableId);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
