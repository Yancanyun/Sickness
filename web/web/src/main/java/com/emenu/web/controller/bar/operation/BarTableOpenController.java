package com.emenu.web.controller.bar.operation;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * BarTableOpenController
 *
 * @author: yangch
 * @time: 2015/12/10 16:52
 */
@Controller
@Module(ModuleEnums.BarTable)
@RequestMapping(value = URLConstants.BAR_TABLE_OPEN_URL)
public class BarTableOpenController extends AbstractController {
    /**
     * Ajax 获取要开台的餐台的数据
     * @param tableId
     * @return
     */
    @Module(ModuleEnums.BarTableOpen)
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject toOpenTable(@RequestParam("tableId") Integer tableId) {
        try {
            // 根据ID检查餐台是否可开台
            Integer status = tableService.queryStatusById(tableId);
            if (status != TableStatusEnums.Enabled.getId()) {
                throw SSException.get(EmenuException.OpenTableFail);
            }

            Table table = tableService.queryById(tableId);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tableId", tableId);
            jsonObject.put("tableName", table.getName());
            jsonObject.put("seatNum", table.getSeatNum());
            jsonObject.put("seatFee", table.getSeatFee());
            jsonObject.put("tableFee", table.getTableFee());

            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
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
    @Module(ModuleEnums.BarTableOpen)
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject openTable(@RequestParam("tableId") Integer tableId,
                                @RequestParam("personNum") Integer personNum) {
        try {
            // 根据ID检查餐台是否可开台
            Integer status = tableService.queryStatusById(tableId);
            if (status != TableStatusEnums.Enabled.getId()) {
                throw SSException.get(EmenuException.OpenTableFail);
            }

            tableService.openTable(tableId, personNum);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
