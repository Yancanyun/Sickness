package com.emenu.web.controller.waiter.table;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.table.AreaDto;
import com.emenu.common.entity.table.Area;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.enums.waiter.WaiterTableStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractAppBarController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
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
@IgnoreLogin
@IgnoreAuthorization
@Controller
@Module(ModuleEnums.WaiterTableList)
@RequestMapping(value = URLConstants.WAITER_TABLE_URL)
public class WaiterTableController extends AbstractAppBarController {
    /**
     * Ajax 根据PartyId、AreaId(可选)、操作状态(开台、清台、换台、并台等)返回餐台数据
     * @param partyId
     * @param areaId
     * @param status
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject tableList(@RequestParam("partyId") Integer partyId,
                                @RequestParam(value = "areaId", required = false) Integer areaId,
                                @RequestParam("status") Integer status) {
        try {
            if (Assert.isNull(status) || Assert.lessOrEqualZero(status)) {
                throw SSException.get(EmenuException.OperateStatusIsNotLegal);
            }

            JSONArray jsonArray = new JSONArray();

            // 根据请求状态不同，返回不同的餐台数据
            WaiterTableStatusEnums waiterTableStatusEnums = WaiterTableStatusEnums.valueOf(status);
            switch (waiterTableStatusEnums) {
                // 开台
                case OpenTable: jsonArray = openTable(partyId, areaId); break;
            }

            return sendJsonArray(jsonArray);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 获取可开台的餐桌的数据
     * @param partyId
     * @param areaId
     * @return
     */
    private JSONArray openTable(Integer partyId, Integer areaId) throws SSException {
        JSONArray jsonArray = new JSONArray();

        AreaDto areaDto = new AreaDto();

        // 若未传来AreaId
        if (Assert.isNull(areaId)) {
            // 根据PartyId获取餐桌状态为可用的AreaDtoList
            List<AreaDto> areaDtoList = waiterTableService.queryAreaDtoByPartyIdAndStatus(partyId, TableStatusEnums.Enabled.getId());
            // 获取AreaDtoList中的第一个AreaDto
            if (areaDtoList.size() == 0) {
                return jsonArray;
            }
            areaDto = areaDtoList.get(0);
        }

        // 若传来AreaId
        else {
            // 根据PartyId、AreaId获取获取餐桌状态为可用的AreaDto
            areaDto = waiterTableService.queryAreaDtoByPartyIdAndAreaIdAndStatus(partyId, areaId , TableStatusEnums.Enabled.getId());
        }

        //获取AreaDto中的Table列表
        List<Table> tableList = areaDto.getTableList();
        if (Assert.isNull(tableList)) {
            return jsonArray;
        }
        for (Table table : tableList) {
            JSONObject jsonObject = new JSONObject();

            jsonObject.put("tableId", table.getId());
            jsonObject.put("tableName", table.getName());
            jsonObject.put("seatNum", table.getSeatNum());

            jsonArray.add(jsonObject);
        }

        return jsonArray;
    }
}
