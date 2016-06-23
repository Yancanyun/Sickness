package com.emenu.web.controller.waiter.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.table.AreaDto;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractAppBarController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * WaiterTableChangeController
 *
 * @author: yangch
 * @time: 2015/12/9 13:38
 */
@IgnoreLogin
@IgnoreAuthorization
@Controller
@Module(ModuleEnums.WaiterTableChange)
@RequestMapping(value = URLConstants.WAITER_TABLE_CHANGE_URL)
public class WaiterTableChangeController extends AbstractAppBarController {
    /**
     * Ajax 获取要换台的餐台的数据
     * @param tableId
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject toChangeTable(@RequestParam("tableId") Integer tableId) {
        try {
            // 根据ID检查餐台是否可换台
            Integer status = tableService.queryStatusById(tableId);
            if (status != TableStatusEnums.Uncheckouted.getId()) {
                throw SSException.get(EmenuException.ChangeTableFail);
            }

            Table table = tableService.queryById(tableId);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tableId", tableId);
            jsonObject.put("tableName",table.getName());
            jsonObject.put("personNum",table.getPersonNum());
            jsonObject.put("seatNum",table.getSeatNum());
            jsonObject.put("seatFee",table.getSeatFee());
            jsonObject.put("tableFee",table.getTableFee());

            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 获取确认更换页的数据
     * @param oldTableId
     * @param newTableId
     * @return
     */
    @RequestMapping(value = "confirm", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject toConfirmChange(@RequestParam("oldTableId") Integer oldTableId,
                                      @RequestParam("newTableId") Integer newTableId) {
        try {
            // 根据ID检查新旧餐台是否均处于可换台的状态
            Integer oldStatus = tableService.queryStatusById(oldTableId);
            if (oldStatus != TableStatusEnums.Uncheckouted.getId()) {
                throw SSException.get(EmenuException.ChangeTableFail);
            }
            Integer newStatus = tableService.queryStatusById(newTableId);
            if (newStatus != TableStatusEnums.Enabled.getId()) {
                throw SSException.get(EmenuException.ChangeTableFail);
            }

            Table oldTable = tableService.queryById(oldTableId);
            Table newTable = tableService.queryById(newTableId);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("oldTableId", oldTableId);
            jsonObject.put("newTableId", newTableId);
            jsonObject.put("oldTableName", oldTable.getName());
            jsonObject.put("newTableName", newTable.getName());
            jsonObject.put("personNum", newTable.getPersonNum());
            jsonObject.put("seatNum", newTable.getSeatNum());
            jsonObject.put("seatFee", newTable.getSeatFee());
            jsonObject.put("tableFee", newTable.getTableFee());

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
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject changeTable(@RequestParam("partyId") Integer partyId,
                                  @RequestParam("oldTableId") Integer oldTableId,
                                  @RequestParam("newTableId") Integer newTableId) {
        try {
            // TODO: 根据PartyId记录哪个服务员换的台

            tableService.changeTable(oldTableId, newTableId);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
