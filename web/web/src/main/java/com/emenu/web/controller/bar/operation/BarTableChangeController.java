package com.emenu.web.controller.bar.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractAppBarController;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
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

            JSONArray newTableList = new JSONArray();
            for (Table newTable : tableList) {
                JSONObject childJsonObject = new JSONObject();
                childJsonObject.put("newTableId", newTable.getId());
                childJsonObject.put("newTableName", newTable.getName());
                childJsonObject.put("newSeatNum", newTable.getSeatNum());
                childJsonObject.put("newSeatFee", newTable.getSeatFee());
                childJsonObject.put("newTableFee", newTable.getTableFee());

                newTableList.add(childJsonObject);
            }

            jsonObject.put("newTableList", newTableList);

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
