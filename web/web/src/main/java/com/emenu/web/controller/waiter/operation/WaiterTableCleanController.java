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
 * WaiterTableCleanController
 *
 * @author: yangch
 * @time: 2015/12/9 14:53
 */
@Controller
@Module(ModuleEnums.WaiterTableClean)
@RequestMapping(value = URLConstants.WAITER_TABLE_CLEAN_URL)
public class WaiterTableCleanController extends AbstractAppBarController {
    /**
     * Ajax 获取清台首页的数据
     * @param partyId
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public JSONObject tableList(@RequestParam("partyId") Integer partyId) {
        try {
            //根据PartyId获取餐桌状态为占用已结账的AreaDto
            List<AreaDto> areaDtoList = waiterTableService.queryAreaDtoByPartyIdAndStatus(partyId, TableStatusEnums.Checkouted.getId());

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
     * Ajax 执行清台操作
     * @param tableId
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    public JSONObject cleanTable(@RequestParam("tableId") Integer tableId) {
        try {
            tableService.cleanTable(tableId);

            return sendJsonArray(null);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
