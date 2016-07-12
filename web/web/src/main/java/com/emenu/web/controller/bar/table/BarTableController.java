package com.emenu.web.controller.bar.table;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.entity.table.Area;
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
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * BarTableController
 *
 * @author: yangch
 * @time: 2015/12/10 16:29
 */
@Controller
@Module(ModuleEnums.BarTable)
@RequestMapping(value = URLConstants.BAR_TABLE_URL)
public class BarTableController extends AbstractAppBarController {
    /**
     * Ajax 获取区域列表
     *
     * @return
     */
    @RequestMapping(value = "arealist", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject areaList() {
        try {
            List<Area> areaList = areaService.listAll();

            JSONArray jsonArray = new JSONArray();

            for (Area area : areaList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("areaId", area.getId());
                jsonObject.put("areaName", area.getName());

                jsonArray.add(jsonObject);
            }

            return sendJsonArray(jsonArray);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 获取某区域下的餐台列表
     *
     * @param areaId
     * @return
     */
    @RequestMapping(value = "tablelist", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject tableList(@RequestParam("areaId") int areaId) {
        try {
            //将该区域内所有餐台存入tableList
            List<Table> tableList = tableService.listByAreaId(areaId);

            JSONArray jsonArray = new JSONArray();

            for (Table table : tableList) {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("tableId", table.getId());
                jsonObject.put("tableName", table.getName());
                jsonObject.put("status", table.getStatus());

                jsonArray.add(jsonObject);
            }

            return sendJsonArray(jsonArray);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }


    /**
     * Ajax 获取某餐台的具体信息
     *
     * @param tableId
     * @return
     */
    @RequestMapping(value = "detail", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject tableDetail(@RequestParam("tableId") int tableId) {
        try {
            Table table = tableService.queryById(tableId);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tableId", table.getId());
            jsonObject.put("tableName", table.getName());
            jsonObject.put("seatNum", table.getSeatNum());
            jsonObject.put("seatFee", table.getSeatFee());
            jsonObject.put("tableFee", table.getTableFee());
            jsonObject.put("minCost", table.getMinCost());
            jsonObject.put("status", table.getStatus());

            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
