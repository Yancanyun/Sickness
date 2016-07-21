package com.emenu.web.controller.waiter.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.party.group.employee.EmployeeDto;
import com.emenu.common.dto.table.AreaDto;
import com.emenu.common.dto.table.TableDto;
import com.emenu.common.entity.meal.MealPeriod;
import com.emenu.common.entity.party.group.employee.Employee;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.meal.MealPeriodIsCurrentEnums;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStatusEnums;
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
 * WaiterTableChangeController
 *
 * @author: yangch
 * @time: 2015/12/9 13:38
 */
@Controller
@Module(ModuleEnums.WaiterTableChange)
@RequestMapping(value = URLConstants.WAITER_TABLE_CHANGE_URL)
public class WaiterTableChangeController extends AbstractController {
    /**
     * Ajax 获取要换台的餐台的数据
     * @param tableId
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject toChangeTable(@RequestParam("tableId") Integer tableId,
                                    HttpSession httpSession) {
        try {
            // 根据ID检查餐台状态是否可换台
            Integer status = tableService.queryStatusById(tableId);
            if (status != null && status != TableStatusEnums.Uncheckouted.getId()) {
                throw SSException.get(EmenuException.ChangeTableFail);
            }

            // 检查服务员是否可服务该餐台
            Integer partyId = (Integer)httpSession.getAttribute("partyId");
            EmployeeDto employeeDto = employeeService.queryEmployeeDtoByPartyId(partyId);
            List<Integer> tableIdList = employeeDto.getTables();
            if (Assert.isNull(tableIdList)) {
                throw SSException.get(EmenuException.WaiterCanNotServiceThisTable);
            }
            Boolean canService = false;
            for (Integer integer : tableIdList) {
                if (tableId == integer) {
                    canService = true;
                    break;
                }
            }
            if (canService == false) {
                throw SSException.get(EmenuException.WaiterCanNotServiceThisTable);
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
     * Ajax 获取可更换至的餐台列表
     * @param tableId
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "list", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject toCanChangeTableList(@RequestParam("tableId") Integer tableId,
                                           @RequestParam(required = false) Integer areaId,
                                           HttpSession httpSession) {
        try {
            // 根据ID检查餐台状态是否可换台
            Integer status = tableService.queryStatusById(tableId);
            if (status != null && status != TableStatusEnums.Uncheckouted.getId()) {
                throw SSException.get(EmenuException.ChangeTableFail);
            }

            // 检查服务员是否可服务该餐台
            Integer partyId = (Integer)httpSession.getAttribute("partyId");
            EmployeeDto employeeDto = employeeService.queryEmployeeDtoByPartyId(partyId);
            List<Integer> tableIdList = employeeDto.getTables();
            if (Assert.isNull(tableIdList)) {
                throw SSException.get(EmenuException.WaiterCanNotServiceThisTable);
            }
            Boolean canService = false;
            for (Integer integer : tableIdList) {
                if (tableId == integer) {
                    canService = true;
                    break;
                }
            }
            if (canService == false) {
                throw SSException.get(EmenuException.WaiterCanNotServiceThisTable);
            }

            Table table = tableService.queryById(tableId);

            JSONObject jsonObject = new JSONObject();

            // 把当前餐台的tableName放到jsonObject里
            jsonObject.put("tableName", table.getName());

            // 查可以换至的餐台列表
            AreaDto areaDto = new AreaDto();

            // 若未传来AreaId，则返回该服务员的第一个区域的数据
            if (Assert.isNull(areaId)) {
                List<AreaDto> areaDtoList = new ArrayList<AreaDto>();
                areaDtoList = waiterTableService.queryAreaDtoByPartyIdAndStatus(partyId, TableStatusEnums.Enabled.getId());

                // 获取AreaDtoList中的第一个AreaDto
                if (areaDtoList.size() == 0) {
                    return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
                }
                areaDto = areaDtoList.get(0);
            }

            // 若传来AreaId，则按照AreaId返回数据
            else {
                List<Integer> statusList = new ArrayList<Integer>();
                statusList.add(TableStatusEnums.Enabled.getId());
                areaDto = waiterTableService.queryAreaDtoByPartyIdAndAreaIdAndStatusList(partyId, areaId, statusList);
            }

            //获取AreaDto中的Table列表
            List<Table> tableList = areaDto.getTableList();
            if (Assert.isNull(tableList)) {
                return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
            }

            // 根据餐段来判断哪些餐台可以显示出来
            JSONArray jsonArray = new JSONArray();
            for (Table t : tableList) {
                JSONObject jO = new JSONObject();

                MealPeriod nowMealPeriod = mealPeriodService.queryByCurrentPeriod(MealPeriodIsCurrentEnums.Using);
                TableDto tableDto = tableService.queryTableDtoById(t.getId());
                if (tableDto.getMealPeriodList() == null || tableDto.getMealPeriodList().size() == 0) {
                    break;
                }
                for (MealPeriod mealPeriod : tableDto.getMealPeriodList()) {
                    if (mealPeriod.getId() == nowMealPeriod.getId()) {
                        jO.put("tableId", t.getId());
                        jO.put("tableName", t.getName());
                        jO.put("seatNum", t.getSeatNum());
                        jO.put("currentNum", t.getPersonNum());
                        jO.put("tableStatus", t.getStatus());
                        jsonArray.add(jO);
                        break;
                    }
                }
            }
            jsonObject.put("tableList", jsonArray);

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
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "confirm", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject toConfirmChange(@RequestParam("oldTableId") Integer oldTableId,
                                      @RequestParam("newTableId") Integer newTableId,
                                      HttpSession httpSession) {
        try {
            // 检查服务员是否可服务这两个餐台
            Integer partyId = (Integer)httpSession.getAttribute("partyId");
            EmployeeDto employeeDto = employeeService.queryEmployeeDtoByPartyId(partyId);
            List<Integer> tableIdList = employeeDto.getTables();
            if (Assert.isNull(tableIdList)) {
                throw SSException.get(EmenuException.WaiterCanNotServiceThisTable);
            }
            Boolean canServiceOld = false;
            Boolean canServiceNew = false;
            for (Integer integer : tableIdList) {
                if (oldTableId == integer) {
                    canServiceOld = true;
                }
                if (newTableId == integer) {
                    canServiceNew = true;
                }
            }
            if (canServiceOld == false || canServiceNew == false) {
                throw SSException.get(EmenuException.WaiterCanNotServiceThisTable);
            }

            // 根据ID检查新旧餐台是否均处于可换台的状态
            Integer oldStatus = tableService.queryStatusById(oldTableId);
            if (oldStatus != null && oldStatus != TableStatusEnums.Uncheckouted.getId()) {
                throw SSException.get(EmenuException.ChangeTableFail);
            }
            Integer newStatus = tableService.queryStatusById(newTableId);
            if (newStatus != null && newStatus != TableStatusEnums.Enabled.getId()) {
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
            TableDto newTableDto = tableService.queryTableDtoById(newTableId);
            jsonObject.put("areaName", newTableDto.getAreaName());

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
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject changeTable(@RequestParam("oldTableId") Integer oldTableId,
                                  @RequestParam("newTableId") Integer newTableId,
                                  HttpSession httpSession) {
        try {
            // 检查服务员是否可服务这两个餐台
            Integer partyId = (Integer)httpSession.getAttribute("partyId");
            EmployeeDto employeeDto = employeeService.queryEmployeeDtoByPartyId(partyId);
            List<Integer> tableIdList = employeeDto.getTables();
            if (Assert.isNull(tableIdList)) {
                throw SSException.get(EmenuException.WaiterCanNotServiceThisTable);
            }
            Boolean canServiceOld = false;
            Boolean canServiceNew = false;
            for (Integer integer : tableIdList) {
                if (oldTableId == integer) {
                    canServiceOld = true;
                }
                if (newTableId == integer) {
                    canServiceNew = true;
                }
            }
            if (canServiceOld == false || canServiceNew == false) {
                throw SSException.get(EmenuException.WaiterCanNotServiceThisTable);
            }

            tableService.changeTable(oldTableId, newTableId);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
