package com.emenu.web.controller.admin.party.group.employee;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.party.group.employee.EmployeeDto;
import com.emenu.common.dto.table.AreaDto;
import com.emenu.common.entity.party.group.employee.Employee;
import com.emenu.common.entity.party.security.SecurityPermission;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.party.UserStatusEnums;
import com.emenu.common.utils.URLConstants;
import com.emenu.common.utils.WebConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.CommonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author xiaozl
 * @date 2015/10/29
 * @time 18:01
 */
@Controller
@Module(ModuleEnums.AdminUserManagementEmployee)
@RequestMapping(value = URLConstants.EMPLOYEE_MANAGEMENT)
public class EmployeeController  extends AbstractController {


    /**
     * 去员工列表页
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeList)
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String toEmployeePage(Model model){
        //@RequestParam("roleList")List<Integer> roleList,Model model,HttpServletRequest request
            //Integer partyId = (Integer)request.getSession().getAttribute(WebConstants.SESSIONUID);//获取当前登录用户id，admin

        /*    List<TableDto> tableDtoList = new ArrayList<TableDto>();
            if(areaId != null) {
                for(int id : areaId) {
                    if(id>0){
                        tableDtoList.addAll(tableService.queryTableDtoByAreaId(id));
                    }
                }
            } else {
                tableDtoList = tableService.queryAllTable();
            }*/

        try {

            List<EmployeeDto> employeeDtoList = Collections.emptyList();


            employeeDtoList = employeeService.listAll();//向前端返回用户列表数据

            model.addAttribute("employeeDtoList", employeeDtoList);

            return "admin/party/group/employee/list_home";
        } catch (SSException e) {
            sendErrMsg(e.getMessage());
            LogClerk.errLog.error(e);
            return WebConstants.sysErrorCode;
        }



    }

    /**
     * ajax获取员工列表
     * @param roles
     * @param model
     * @param request
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeList)
    @RequestMapping(value = "ajax/list",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject listByRoles(@RequestParam("roles")Integer [] roles,Model model,HttpServletRequest request){

        List<EmployeeDto> employeeDtoList = Collections.emptyList();

        try{
            if(roles == null) {
                employeeDtoList = employeeService.listAll();//向前端返回用户列表数据
            }else {
                List<Integer> roleList = new ArrayList<Integer>();
                for (int i = 0; i <roles.length ; i++) {
                    roleList.add(roles[i]);
                }
                employeeDtoList = employeeService.listByRoles(roleList);

            }

        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());

        }

        JSONArray jsonArray = new JSONArray();
        for (EmployeeDto employeeDto : employeeDtoList) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("loginName", employeeDto.getLoginName());
            jsonObject.put("employeeNumber", employeeDto.getEmployee().getEmployeeNumber());
            jsonObject.put("name", employeeDto.getEmployee().getName());

            List<Integer> roleList = Collections.emptyList();
            roleList = employeeDto.getRole();
            jsonObject.put("roles",roleList);

            jsonObject.put("phone", employeeDto.getEmployee().getPhone());
            jsonObject.put("status", employeeDto.getEmployee().getStatus());
            jsonArray.add(jsonObject);
        }
        return sendJsonArray(jsonArray, 0);
    }


    /**
     * ajax获取服务员-餐桌信息
     * @param partyId
     * @return
     */
    @RequestMapping(value = "ajax/tables/{partyId}",method = RequestMethod.GET)
    @ResponseBody JSONObject getWaiterTable(@PathVariable("partyId") Integer partyId){

        try{

            List<AreaDto> areaDtoList = waiterTableService.queryAreaDtoByPartyId(partyId);
            JSONArray jsonArray = new JSONArray();

            for(AreaDto areaDto : areaDtoList){
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("area", areaDto.getArea().getName());

                List<Table> tableList = new ArrayList<Table>();
                tableList = areaDto.getTableList();

                String tables = "";

                for (Table table : tableList){
                    tables += table.getName()+"、";
                }

                tables = tables.substring(0,tables.length()-1);



                jsonObject.put("tables",tables);
                jsonArray.add(jsonObject);
             }
          /*  try {
                List<AreaDto> areaDtoList = waiterTableService.queryAreaDtoByWaiterId(userId);
                net.sf.json.JSONArray areaList = new net.sf.json.JSONArray();
                for(AreaDto areaDto : areaDtoList) {
                    net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
                    net.sf.json.JSONArray tableList = new net.sf.json.JSONArray();
                    for(Table table : areaDto.getTableList()) {
                        net.sf.json.JSONObject j = new net.sf.json.JSONObject();
                        j.put("name", table.getName());
                        tableList.add(j);
                    }
                    jsonObject.put("name", areaDto.getArea().getName());
                    jsonObject.put("tableList", tableList);
                    areaList.add(jsonObject);
                }
                net.sf.json.JSONObject jsonObject = new net.sf.json.JSONObject();
                jsonObject.put("areaList", areaList);
                return sendJsonObject(jsonObject);
            } catch (SSException e) {
                return sendErrMsgAndErrCode(e);
            }*/

            return sendJsonArray(jsonArray, 0);
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());

        }

        JSONArray jsonArray = new JSONArray();

        return sendJsonArray(jsonArray, 0);

    }

    /**
     * 修改用户状态
     * @param partyId
     * @param status
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeUpdate)
    @RequestMapping(value = "ajax/status/{partyId}")
    @ResponseBody
    public JSONObject updateEmployeeStatus(@PathVariable("partyId")Integer partyId,
                                                         @RequestParam String status){
        try {
            if(status.equals("true")) {
                employeeService.updateStatus(partyId, UserStatusEnums.Enabled.getId());
            } else {
                employeeService.updateStatus(partyId, UserStatusEnums.Disabled.getId());
            }
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * ajax删除员工
     * @param partyId
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeDelete)
    @RequestMapping(value = "ajax/del/{partyId}",method = RequestMethod.DELETE)
    @ResponseBody
    public
    JSONObject delEmployee(@PathVariable("partyId") Integer partyId){
        try {
            employeeService.delByPartyId(partyId);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    @Module(ModuleEnums.AdminUserManagementEmployeeNew)
    @RequestMapping(value = "add",method = RequestMethod.GET)
    public String toNew(){
        return "admin/party/group/employee/new_home";
    }
    /**
     * 添加员工
     * @param roleList
     * @param tableList
     * @param loginName
     * @param password
     * @param employee
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeNew)
    @RequestMapping(value = "new",method = RequestMethod.GET)
    public String newEmployee(@RequestParam("roleList")List<Integer> roleList,
                              @RequestParam(required = false) List<Integer> tableList,
                              @RequestParam("loginName")String loginName,
                              @RequestParam("password")String password,
                              Employee employee){
        try {
        EmployeeDto employeeDto = new EmployeeDto();
        employeeDto.setEmployee(employee);
        employeeDto.setRole(roleList);
        employeeDto.setTables(tableList);
        employeeService.newEmployee(employeeDto, loginName, CommonUtil.md5(password));
        return "redirect:admin/party/group/employee";
        } catch (SSException e) {
            sendErrMsg(e.getMessage());
            LogClerk.errLog.error(e);
            return WebConstants.sysErrorCode;
        }
    }


    @RequestMapping(value = "ajax/chkname",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkLoginName(@RequestParam("loginName")String loginName){
        try {
            securityUserService.checkLoginNameIsExist(loginName);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    @RequestMapping(value = "ajax/chknum",method = RequestMethod.POST)
    @ResponseBody
    public JSONObject checkNumber(@RequestParam("employeeNumber")String employeeNumber){
        try {
            employeeService.checkNumber(employeeNumber);
            return sendJsonObject(AJAX_SUCCESS_CODE);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }


}