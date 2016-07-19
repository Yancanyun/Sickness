package com.emenu.web.controller.waiter.operation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.party.group.employee.EmployeeDto;
import com.emenu.common.dto.table.AreaDto;
import com.emenu.common.entity.party.group.employee.Employee;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.table.TableStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.web.spring.AbstractAppBarController;
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
