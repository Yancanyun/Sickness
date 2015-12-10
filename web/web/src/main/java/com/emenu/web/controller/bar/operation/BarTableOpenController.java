package com.emenu.web.controller.bar.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractAppBarController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * BarTableOpenController
 *
 * @author: yangch
 * @time: 2015/12/10 16:52
 */
@Controller
@Module(ModuleEnums.BarTableOpen)
@RequestMapping(value = URLConstants.BAR_TABLE_OPEN_URL)
public class BarTableOpenController extends AbstractAppBarController {
    /**
     * Ajax 获取要开台的餐台的数据
     * @param tableId
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public JSONObject toOpenTable(@RequestParam("tableId") Integer tableId) {
        try {
            Table table = tableService.queryById(tableId);

            JSONArray jsonArray = new JSONArray();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tableId", tableId);
            jsonObject.put("tableName", table.getName());
            jsonObject.put("seatNum", table.getSeatNum());
            jsonObject.put("seatFee", table.getSeatFee());
            jsonObject.put("tableFee", table.getTableFee());
            jsonObject.put("minCost", table.getMinCost());
            jsonArray.add(jsonObject);

            return sendJsonArray(jsonArray);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 执行开台操作
     * @param tableId
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public JSONObject openTable(@RequestParam("tableId") Integer tableId,
                                @RequestParam("personNum") Integer personNum) {
        try {
            tableService.openTable(tableId, personNum);

            return sendJsonArray(null);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
