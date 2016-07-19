package com.emenu.web.controller.waiter.table;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.table.AreaDto;
import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.meal.MealPeriod;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.meal.MealPeriodIsCurrentEnums;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.enums.waiter.WaiterTableStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

/**
 * WaiterTableController
 *
 * @author: yangch
 * @time: 2015/12/8 10:01
 */
@Controller
@Module(ModuleEnums.WaiterTableList)
@RequestMapping(value = URLConstants.WAITER_TABLE_URL)
public class WaiterTableController extends AbstractController {
    /**
     * Ajax 根据AreaId(可选)、操作状态(开台、清台、换台、并台等)返回餐台数据
     * @param areaId
     * @param status
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject tableList(@RequestParam(value = "areaId", required = false) Integer areaId,
                                @RequestParam(value = "status") Integer status,
                                HttpSession httpSession) {
        try {
            Integer partyId = (Integer)httpSession.getAttribute("partyId");

            if (Assert.isNull(status) || Assert.lessZero(status)) {
                throw SSException.get(EmenuException.OperateStatusIsNotLegal);
            }

            // 获取餐台数据
            JSONArray jsonArray = new JSONArray();
            jsonArray = getTableList(partyId, areaId, status);

            return sendJsonArray(jsonArray);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 获取根据状态、PartyId、AreaId获取餐台数据
     * @param partyId
     * @param areaId
     * @param status
     * @return
     * @throws SSException
     */
    private JSONArray getTableList(Integer partyId, Integer areaId, Integer status) throws SSException {
        JSONArray jsonArray = new JSONArray();

        AreaDto areaDto = new AreaDto();

        // 若未传来AreaId，则返回该服务员的第一个区域的数据
        if (Assert.isNull(areaId)) {
            List<AreaDto> areaDtoList = new ArrayList<AreaDto>();
            WaiterTableStatusEnums waiterTableStatusEnums = WaiterTableStatusEnums.valueOf(status);
            switch (waiterTableStatusEnums) {
                // 全部状态 根据PartyId获取所有AreaDtoList
                case All:
                    areaDtoList = waiterTableService.queryAreaDtoByPartyId(partyId);
                    break;
                // 开台 根据PartyId获取餐台状态为可用的AreaDtoList
                case OpenTable:
                    areaDtoList = waiterTableService.queryAreaDtoByPartyIdAndStatus(partyId, TableStatusEnums.Enabled.getId());
                    break;
                // 清台 根据PartyId获取餐台状态为占用已结账的AreaDtoList
                case CleanTable:
                    areaDtoList = waiterTableService.queryAreaDtoByPartyIdAndStatus(partyId, TableStatusEnums.Checkouted.getId());
                    break;
                // 换台 根据PartyId获取餐台状态为占用未结账的AreaDtoList
                case ChangeTable:
                    areaDtoList = waiterTableService.queryAreaDtoByPartyIdAndStatus(partyId, TableStatusEnums.Uncheckouted.getId());
                    break;
                // 并台 根据PartyId获取餐台状态为可用、占用未结账、已并桌、占用已结账的AreaDtoList
                case MergeTable:
                    List<Integer> statusList = new ArrayList<Integer>();
                    statusList.add(TableStatusEnums.Enabled.getId());
                    statusList.add(TableStatusEnums.Uncheckouted.getId());
                    statusList.add(TableStatusEnums.Merged.getId());
                    statusList.add(TableStatusEnums.Checkouted.getId());
                    areaDtoList = waiterTableService.queryAreaDtoByPartyIdAndStatusList(partyId, statusList);
                    break;
                // 点菜 根据PartyId获取餐台状态为占用已结账、占用未结账、已并桌的AreaDtoList
                case OrderDish:
                    statusList = new ArrayList<Integer>();
                    statusList.add(TableStatusEnums.Checkouted.getId());
                    statusList.add(TableStatusEnums.Uncheckouted.getId());
                    statusList.add(TableStatusEnums.Merged.getId());
                    areaDtoList = waiterTableService.queryAreaDtoByPartyIdAndStatusList(partyId, statusList);
                    break;
                // 查单 根据PartyId获取餐台状态为占用已结账、占用未结账、已并桌的AreaDtoList
                case QueryCheckout:
                    statusList = new ArrayList<Integer>();
                    statusList.add(TableStatusEnums.Checkouted.getId());
                    statusList.add(TableStatusEnums.Uncheckouted.getId());
                    statusList.add(TableStatusEnums.Merged.getId());
                    areaDtoList = waiterTableService.queryAreaDtoByPartyIdAndStatusList(partyId, statusList);
                    break;
                // 查单 根据PartyId获取餐台状态为占用未结账、已并桌的AreaDtoList
                case RetreatDish:
                    statusList = new ArrayList<Integer>();
                    statusList.add(TableStatusEnums.Uncheckouted.getId());
                    statusList.add(TableStatusEnums.Merged.getId());
                    areaDtoList = waiterTableService.queryAreaDtoByPartyIdAndStatusList(partyId, statusList);
                    break;
            }
            // 获取AreaDtoList中的第一个AreaDto
            if (areaDtoList.size() == 0) {
                return jsonArray;
            }
            areaDto = areaDtoList.get(0);
        }

        // 若传来AreaId，则按照AreaId返回数据
        else {
            WaiterTableStatusEnums waiterTableStatusEnums = WaiterTableStatusEnums.valueOf(status);
            List<Integer> statusList = new ArrayList<Integer>();
            switch (waiterTableStatusEnums) {
                // 全部状态 根据PartyId获取所有AreaDto
                case All:
                    areaDto = waiterTableService.queryAreaDtoByPartyIdAndAreaId(partyId, areaId);
                    break;
                // 开台 根据PartyId获取餐台状态为可用的AreaDto
                case OpenTable:
                    statusList = new ArrayList<Integer>();
                    statusList.add(TableStatusEnums.Enabled.getId());
                    areaDto = waiterTableService.queryAreaDtoByPartyIdAndAreaIdAndStatusList(partyId, areaId, statusList);
                    break;
                // 清台 根据PartyId获取餐台状态为占用已结账的AreaDto
                case CleanTable:
                    statusList = new ArrayList<Integer>();
                    statusList.add(TableStatusEnums.Checkouted.getId());
                    areaDto = waiterTableService.queryAreaDtoByPartyIdAndAreaIdAndStatusList(partyId, areaId, statusList);
                    break;
                // 换台 根据PartyId获取餐台状态为占用未结账的AreaDto
                case ChangeTable:
                    statusList = new ArrayList<Integer>();
                    statusList.add(TableStatusEnums.Uncheckouted.getId());
                    areaDto = waiterTableService.queryAreaDtoByPartyIdAndAreaIdAndStatusList(partyId, areaId, statusList);
                    break;
                // 并台 根据PartyId获取餐台状态为可用、占用未结账、已并桌、占用已结账的AreaDto
                case MergeTable:
                    statusList = new ArrayList<Integer>();
                    statusList.add(TableStatusEnums.Enabled.getId());
                    statusList.add(TableStatusEnums.Uncheckouted.getId());
                    statusList.add(TableStatusEnums.Merged.getId());
                    statusList.add(TableStatusEnums.Checkouted.getId());
                    areaDto = waiterTableService.queryAreaDtoByPartyIdAndAreaIdAndStatusList(partyId, areaId, statusList);
                    break;
                // 点菜 根据PartyId获取餐台状态为占用已结账、占用未结账、已并桌的AreaDto
                case OrderDish:
                    statusList = new ArrayList<Integer>();
                    statusList.add(TableStatusEnums.Checkouted.getId());
                    statusList.add(TableStatusEnums.Uncheckouted.getId());
                    statusList.add(TableStatusEnums.Merged.getId());
                    areaDto = waiterTableService.queryAreaDtoByPartyIdAndAreaIdAndStatusList(partyId, areaId, statusList);
                    break;
                // 查单 根据PartyId获取餐台状态为占用已结账、占用未结账、已并桌的AreaDto
                case QueryCheckout:
                    statusList = new ArrayList<Integer>();
                    statusList.add(TableStatusEnums.Checkouted.getId());
                    statusList.add(TableStatusEnums.Uncheckouted.getId());
                    statusList.add(TableStatusEnums.Merged.getId());
                    areaDto = waiterTableService.queryAreaDtoByPartyIdAndAreaIdAndStatusList(partyId, areaId, statusList);
                    break;
                // 退菜 根据PartyId获取餐台状态为占用未结账、已并桌的AreaDto
                case RetreatDish:
                    statusList = new ArrayList<Integer>();
                    statusList.add(TableStatusEnums.Uncheckouted.getId());
                    statusList.add(TableStatusEnums.Merged.getId());
                    areaDto = waiterTableService.queryAreaDtoByPartyIdAndAreaIdAndStatusList(partyId, areaId, statusList);
                    break;
            }
        }

        //获取AreaDto中的Table列表
        List<Table> tableList = areaDto.getTableList();
        if (Assert.isNull(tableList)) {
            return jsonArray;
        }

        // 根据餐段来判断哪些餐台可以显示出来
        for (Table table : tableList) {
            JSONObject jsonObject = new JSONObject();

            MealPeriod nowMealPeriod = mealPeriodService.queryByCurrentPeriod(MealPeriodIsCurrentEnums.Using);
            TableDto tableDto = tableService.queryTableDtoById(table.getId());
            if (tableDto.getMealPeriodList() == null || tableDto.getMealPeriodList().size() == 0) {
                break;
            }
            for (MealPeriod mealPeriod : tableDto.getMealPeriodList()) {
                if (mealPeriod.getId() == nowMealPeriod.getId()) {
                    jsonObject.put("tableId", table.getId());
                    jsonObject.put("tableName", table.getName());
                    jsonObject.put("seatNum", table.getSeatNum());
                    jsonObject.put("currentNum", table.getPersonNum());
                    jsonObject.put("tableStatus", table.getStatus());
                    jsonArray.add(jsonObject);
                    break;
                }
            }
        }

        return jsonArray;
    }
}
