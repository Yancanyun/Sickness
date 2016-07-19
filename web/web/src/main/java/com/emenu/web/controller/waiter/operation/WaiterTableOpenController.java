package com.emenu.web.controller.waiter.operation;

import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.IgnoreAuthorization;
import com.emenu.common.annotation.IgnoreLogin;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.party.group.employee.EmployeeDto;
import com.emenu.common.entity.table.Table;
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
public class WaiterTableOpenController extends AbstractController {
    /**
     * Ajax 获取要开台的餐台的数据
     * @param tableId
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject toOpenTable(@RequestParam("tableId") Integer tableId,
                                  HttpSession httpSession) {
        try {
            // 根据ID检查餐台是否可开台
            Integer status = tableService.queryStatusById(tableId);
            if (status != TableStatusEnums.Enabled.getId()) {
                throw SSException.get(EmenuException.OpenTableFail);
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
            jsonObject.put("tableName", table.getName());
            jsonObject.put("seatNum", table.getSeatNum());
            jsonObject.put("seatFee", table.getSeatFee());
            jsonObject.put("tableFee", table.getTableFee());

            return sendJsonObject(jsonObject, AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * Ajax 执行开台操作
     * @param tableId
     * @param personNum
     * @param httpSession
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject openTable(@RequestParam("tableId") Integer tableId,
                                @RequestParam("personNum") Integer personNum,
                                HttpSession httpSession) {
        try {
            // 根据ID检查餐台是否可开台
            Integer status = tableService.queryStatusById(tableId);
            if (status != TableStatusEnums.Enabled.getId()) {
                throw SSException.get(EmenuException.OpenTableFail);
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

            tableService.openTable(tableId, personNum);

            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}
