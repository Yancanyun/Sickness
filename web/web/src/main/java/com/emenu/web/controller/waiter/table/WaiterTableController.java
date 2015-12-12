package com.emenu.web.controller.waiter.table;

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
@Module(ModuleEnums.WaiterTableList)
@RequestMapping(value = URLConstants.WAITER_TABLE_LIST_URL)
public class WaiterTableController extends AbstractAppBarController {
    /**
     * Ajax 获取餐台列表首页的数据
     * @param partyId
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject tableList(@RequestParam("partyId") Integer partyId) {
        try {
            //根据PartyId获取所有的AreaDto
            List<AreaDto> areaDtoList = waiterTableService.queryAreaDtoByPartyId(partyId);

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
}
