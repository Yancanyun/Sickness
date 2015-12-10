package com.emenu.web.controller.bar.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractAppBarController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * BarTableChangeController
 *
 * @author: yangch
 * @time: 2015/12/10 16:54
 */
@Controller
@Module(ModuleEnums.BarTableChange)
@RequestMapping(value = URLConstants.BAR_TABLE_CHANGE_URL)
public class BarTableChangeController extends AbstractAppBarController {
    /**
     * Ajax 获取换台页的数据
     *
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public JSONObject toChangeTable() {
        try {
            //获取餐桌状态为可用的Table
            List<Table> tableList = tableService.listByStatus(TableStatusEnums.Enabled);

            JSONArray jsonArray = new JSONArray();

            for (Table table : tableList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("tableId", table.getId());
                jsonObject.put("tableName", table.getName());

                jsonArray.add(jsonObject);
            }

            return sendJsonArray(jsonArray);
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
    @RequestMapping(value = "", method = RequestMethod.POST)
    public JSONObject changeTable(@RequestParam("oldTableId") Integer oldTableId,
                                  @RequestParam("newTableId") Integer newTableId) {
        try {
            tableService.changeTable(oldTableId, newTableId);

            return sendJsonArray(null);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
