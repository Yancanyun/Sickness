package com.emenu.web.controller.waiter.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.table.AreaDto;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * WaiterTableOpenController
 *
 * @author: yangch
 * @time: 2015/12/8 10:01
 */
@Controller
@Module(ModuleEnums.WaiterTableOpen)
@RequestMapping(value = URLConstants.WAITER_TABLE_OPEN_URL)
public class WaiterTableOpenController extends AbstractAppBarController {
    /**
     * Ajax 获取开台首页的数据
     * @param partyId
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject tableList(@RequestParam("partyId") Integer partyId) {
        try {
            //根据PartyId获取餐桌状态为可用的AreaDto
            List<AreaDto> areaDtoList = waiterTableService.queryAreaDtoByPartyIdAndStatus(partyId, TableStatusEnums.Enabled.getId());

            JSONArray jsonArray = new JSONArray();

            for (AreaDto areaDto : areaDtoList) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("areaId", areaDto.getArea().getId());
                jsonObject.put("areaName", areaDto.getArea().getName());

                //获取AreaDto中的Table列表
                List<Table> tables = areaDto.getTableList();

                JSONArray tableList = new JSONArray();

                for (Table table : tables) {
                    JSONObject tableJsonObject = new JSONObject();
                    tableJsonObject.put("tableId", table.getId());
                    tableJsonObject.put("tableName", table.getName());

                    tableList.add(tableJsonObject);
                }

                jsonObject.put("tableList", tableList);
                jsonArray.add(jsonObject);
            }

            return sendJsonArray(jsonArray);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 获取要开台的餐台的数据
     * @param tableId
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject toOpenTable(@RequestParam("tableId") Integer tableId) {
        try {
            Table table = tableService.queryById(tableId);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tableId", tableId);
            jsonObject.put("tableName", table.getName());
            jsonObject.put("seatNum", table.getSeatNum());
            jsonObject.put("seatFee", table.getSeatFee());
            jsonObject.put("tableFee", table.getTableFee());
            jsonObject.put("minCost", table.getMinCost());

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
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject openTable(@RequestParam("tableId") Integer tableId,
                                @RequestParam("personNum") Integer personNum) {
        try {
            tableService.openTable(tableId, personNum);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
