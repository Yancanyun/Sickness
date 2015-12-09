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

import java.util.List;

/**
 * WaiterTableChangeController
 *
 * @author: yangch
 * @time: 2015/12/9 13:38
 */
@Controller
@Module(ModuleEnums.WaiterTableChange)
@RequestMapping(value = URLConstants.WAITER_TABLE_CHANGE_URL)
public class WaiterTableChangeController extends AbstractAppBarController {
    /**
     * Ajax 获取换台首页的数据
     * @param partyId
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public JSONObject tableList(@RequestParam("partyId") Integer partyId) {
        try {
            //根据PartyId获取餐桌状态为占用未结账的AreaDto
            List<AreaDto> areaDtoList = waiterTableService.queryAreaDtoByPartyIdAndStatus(partyId, TableStatusEnums.Uncheckouted.getId());

            JSONArray jsonArray = new JSONArray();

            for (AreaDto areaDto : areaDtoList) {
                JSONObject jsonObject = new JSONObject();

                jsonObject.put("areaId", areaDto.getArea().getId());
                jsonObject.put("areaName", areaDto.getArea().getName());

                //获取AreaDto中的Table列表
                List<Table> tables = areaDto.getTableList();

                JSONArray tableList = new JSONArray();

                int t = 0;
                for (Table table : tables) {
                    JSONObject tableJsonObject = new JSONObject();
                    tableJsonObject.put("tableId", tables.get(t).getId());
                    tableJsonObject.put("tableName", tables.get(t).getName());
                    tableJsonObject.put("personNum", tables.get(t).getPersonNum());

                    tableList.add(tableJsonObject);
                    t++;
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
     * Ajax 获取要更换的餐台的数据
     * @param tableId
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public JSONObject toChangeTable(@RequestParam("tableId") Integer tableId) {
        try {
            Table table = tableService.queryById(tableId);

            JSONArray jsonArray = new JSONArray();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tableId", tableId);
            jsonObject.put("name",table.getName());
            jsonObject.put("personNum",table.getPersonNum());
            jsonObject.put("seatNum",table.getSeatNum());
            jsonObject.put("seatFee",table.getSeatFee());
            jsonObject.put("tableFee",table.getTableFee());
            jsonObject.put("minCost",table.getMinCost());
            jsonArray.add(jsonObject);

            return sendJsonArray(jsonArray);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 获取选择可以换至的餐台列表
     * @param partyId
     * @return
     */
    @RequestMapping(value = "enabled_list", method = RequestMethod.GET)
    public JSONObject enabledTableList(@RequestParam("partyId") Integer partyId) {
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

                int t = 0;
                for (Table table : tables) {
                    JSONObject tableJsonObject = new JSONObject();
                    tableJsonObject.put("tableId", tables.get(t).getId());
                    tableJsonObject.put("tableName", tables.get(t).getName());
                    tableJsonObject.put("personNum", tables.get(t).getPersonNum());

                    tableList.add(tableJsonObject);
                    t++;
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
     * Ajax 获取确认更换页的数据
     * @param oldTableId
     * @param newTableId
     * @return
     */
    @RequestMapping(value = "confirm", method = RequestMethod.GET)
    public JSONObject confirmChange(@RequestParam("oldTableId") Integer oldTableId,
                                    @RequestParam("newTableId") Integer newTableId) {
        try {
            Table oldTable = tableService.queryById(oldTableId);
            Table newTable = tableService.queryById(newTableId);

            JSONArray jsonArray = new JSONArray();

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("oldTableId", oldTableId);
            jsonObject.put("newTableId", newTableId);
            jsonObject.put("oldTableName", oldTable.getName());
            jsonObject.put("newTableName", newTable.getName());
            jsonObject.put("personNum", newTable.getPersonNum());
            jsonObject.put("seatNum", newTable.getSeatNum());
            jsonObject.put("seatFee", newTable.getSeatFee());
            jsonObject.put("tableFee", newTable.getTableFee());
            jsonObject.put("minCost", newTable.getMinCost());
            jsonArray.add(jsonObject);

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
