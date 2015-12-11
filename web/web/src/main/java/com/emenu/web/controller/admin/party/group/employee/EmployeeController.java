package com.emenu.web.controller.admin.party.group.employee;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.emenu.common.annotation.Module;
import com.emenu.common.dto.party.group.employee.EmployeeDto;
import com.emenu.common.dto.table.AreaDto;
import com.emenu.common.entity.party.group.employee.Employee;
import com.emenu.common.entity.party.security.SecurityGroup;
import com.emenu.common.entity.party.security.SecurityUser;
import com.emenu.common.entity.table.Table;
import com.emenu.common.enums.other.ModuleEnums;
import com.emenu.common.enums.party.UserStatusEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.utils.URLConstants;
import com.emenu.common.utils.WebConstants;
import com.emenu.web.spring.AbstractController;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.common.util.CommonUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

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
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeList)
    @RequestMapping(value = "",method = RequestMethod.GET)
    public String toEmployeePage(Model model){
        try {
            List<EmployeeDto> employeeDtoList = Collections.emptyList();
            employeeDtoList = employeeService.listAll();

            List<SecurityGroup> roleList = securityGroupService.listAll();
            model.addAttribute("employeeDtoList", employeeDtoList);
            model.addAttribute("roleList",roleList);
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
            jsonObject.put("id",employeeDto.getEmployee().getId());
            jsonObject.put("partyId",employeeDto.getEmployee().getPartyId());
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
    @Module(ModuleEnums.AdminUserManagementEmployeeList)
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
    @RequestMapping(value = "ajax/status/",method = RequestMethod.PUT)
    @ResponseBody
    public JSONObject updateEmployeeStatus(@RequestParam("partyId")Integer partyId,
                                           @RequestParam("status")Integer status){
        try {
            if(status==1) {
                employeeService.updateStatus(partyId, UserStatusEnums.Enabled.getId());
            }
            if(status==2) {
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

    /**
     * 去添加员工页
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeNew)
    @RequestMapping(value = "add",method = RequestMethod.GET)
    public String toNew(Model model){
        try{
            List<AreaDto> areaDtoList = waiterTableService.queryAreaDto();
            List<SecurityGroup> roleList = securityGroupService.listAll();
            model.addAttribute("areaDtoList", areaDtoList);
            model.addAttribute("roleList", roleList);
            return "admin/party/group/employee/new_home";
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return WebConstants.sysErrorCode;
        }
    }

    /**
     * 去员工信息编辑页
     * @param partyId
     * @param model
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeUpdate)
    @RequestMapping(value = "toupdate/{partyId}",method = RequestMethod.GET)
    public String toUpdate(@PathVariable("partyId")Integer partyId,Model model){
        try {
            //获取要编辑员工的信息
            EmployeeDto employeeDto = employeeService.queryEmployeeDtoByPartyId(partyId);
            List<Table> tableList = Collections.emptyList();
            //获取所有的餐桌
            tableList = tableService.listAll();
            List<AreaDto> areaDtoList = waiterTableService.queryAreaDto();
            List<SecurityGroup> roleList = securityGroupService.listAll();

            Map<Integer, Integer> roleMap = new HashMap<Integer, Integer>();
            Map<Integer, Integer> tableMap = new HashMap<Integer, Integer>();

            for (Integer roleId : employeeDto.getRole()) {
                roleMap.put(roleId, 1);
            }
            for (Integer tableId: employeeDto.getTables()) {
                tableMap.put(tableId, 1);
            }
            model.addAttribute("areaDtoList", areaDtoList);
            model.addAttribute("employeeDto",employeeDto);
            model.addAttribute("tableList",tableList);
            model.addAttribute("roleMap", roleMap);
            model.addAttribute("tableMap", tableMap);
            model.addAttribute("roleList", roleList);
            return "admin/party/group/employee/update_home";
        }catch (SSException e){
            LogClerk.errLog.error(e);
            sendErrMsg(e.getMessage());
            return WebConstants.sysErrorCode;
        }

    }

    /**
     * 员工信息编辑提交处理
     * @param partyId
     * @param roles
     * @param tables
     * @param loginName
     * @param password
     * @param employee
     * @return
     */
    @Module(ModuleEnums.AdminUserManagementEmployeeUpdate)
    @RequestMapping(value = "update",method = RequestMethod.POST)
    public String updateEmployee(@RequestParam("partyId")Integer partyId,
                                 @RequestParam("roles")Integer [] roles,
                                 @RequestParam(value = "tables",required = false)Integer [] tables,
                                 @RequestParam("loginName")String loginName,
                                 @RequestParam("password")String password,
                                 Employee employee,
                                 RedirectAttributes redirectAttributes) {
        try {
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setEmployee(employee);

            List<Integer> roleList = new ArrayList<Integer>();
            for (int i = 0; i < roles.length; i++) {
                roleList.add(roles[i]);
                System.out.println(i);
            }

            List<Integer> tableList = new ArrayList<Integer>();
            for (int i = 0; i < tables.length; i++) {
                tableList.add(tables[i]);
            }
            employeeDto.setRole(roleList);
            employeeDto.setTables(tableList);
            employeeService.update(employeeDto, partyId, loginName, CommonUtil.md5(password));

            String successUrl = "/" + URLConstants.EMPLOYEE_MANAGEMENT;
            //返回添加成功信息
            redirectAttributes.addFlashAttribute("msg", "编辑成功");
            //返回列表页
            return "redirect:" + successUrl;
        } catch (SSException e) {
            sendErrMsg(e.getMessage());
            LogClerk.errLog.error(e);
            String failedUrl = "/" + URLConstants.ADMIN_TABLE_URL + "/toupdate/"+"{"+String .valueOf(partyId)+"}";
            //返回添加失败信息
            redirectAttributes.addFlashAttribute("msg", "编辑失败");
            //返回添加页
            return "redirect:" + failedUrl;
        }
    }

        /**
         * 添加新员工
         * @param roles
         * @param tables
         * @param loginName
         * @param password
         * @param employee
         * @return
         */
    @Module(ModuleEnums.AdminUserManagementEmployeeNew)
    @RequestMapping(value = "new",method = RequestMethod.POST)
    public String newEmployee(@RequestParam("roles")Integer [] roles,
                              @RequestParam(value = "tables",required = false)Integer [] tables,
                              @RequestParam("loginName")String loginName,
                              @RequestParam("password")String password,
                              Employee employee,
                              RedirectAttributes redirectAttributes){
        try {
            EmployeeDto employeeDto = new EmployeeDto();
            employeeDto.setEmployee(employee);

            List<Integer> roleList = new ArrayList<Integer>();
            for (int i = 0; i <roles.length ; i++) {
                roleList.add(roles[i]);
            }

            if(tables!=null){
                List<Integer> tableList = new ArrayList<Integer>();
                for (int i = 0; i <tables.length ; i++) {
                    tableList.add(tables[i]);
                }
                employeeDto.setTables(tableList);
            }
            employeeDto.setRole(roleList);
            employeeService.newEmployee(employeeDto, loginName, CommonUtil.md5(password));

            String successUrl = "/" + URLConstants.EMPLOYEE_MANAGEMENT;
            //返回添加成功信息
            redirectAttributes.addFlashAttribute("msg", "添加成功");
            //返回列表页
            return "redirect:" + successUrl;
        } catch (SSException e) {
            sendErrMsg(e.getMessage());
            LogClerk.errLog.error(e);

            String failedUrl = "/" + URLConstants.ADMIN_TABLE_URL + "/add";
            //返回添加失败信息
            redirectAttributes.addFlashAttribute("msg", "添加失败");
            //返回添加页
            return "redirect:" + failedUrl;
        }
    }


    /**
     * 添加、修改员工信息时检查登录名是否重复
     * @param loginName
     * @return
     */
    @RequestMapping(value = "ajax/checkloginname",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject checkLoginName(@RequestParam("partyId")Integer partyId,@RequestParam("loginName")String loginName){
        try {
            //添加页重名检查
            if (Assert.isNotNull(partyId)&&Assert.isZero(partyId)){
                if(securityUserService.checkLoginNameIsExist(loginName)){
                    return sendJsonObject(AJAX_FAILURE_CODE);
                }else {
                    return sendJsonObject(AJAX_SUCCESS_CODE);
                }
            }
            //编辑页重名检查
            if (Assert.isNotNull(partyId)&&!Assert.lessOrEqualZero(partyId)){
                SecurityUser securityUser = securityUserService.queryByLoginName(loginName);
                if (partyId == securityUser.getPartyId()){
                    return sendJsonObject(AJAX_SUCCESS_CODE);
                }
                if (partyId != securityUser.getPartyId()){
                    return sendJsonObject(AJAX_FAILURE_CODE);
                }
            }
            throw SSException.get(EmenuException.CheckLoginNameFail);

        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 判断员工编号是否重复
     * @param employeeNumber
     * @return
     */
    @RequestMapping(value = "ajax/checknumber",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject checkNumberIsExist(@RequestParam("partyId")Integer partyId,@RequestParam("employeeNumber")String employeeNumber){
        try {
            if (Assert.isNotNull(partyId)&&Assert.isZero(partyId)){
                if (employeeService.checkNumberIsExist(employeeNumber)){
                    return sendJsonObject(AJAX_FAILURE_CODE);
                }else {
                    return sendJsonObject(AJAX_SUCCESS_CODE);
                }
            }
            if (Assert.isNotNull(partyId)&&!Assert.lessOrEqualZero(partyId)){
                Employee employee = employeeService.queryByNumber(employeeNumber);
                if (partyId == employee.getPartyId()){
                    return sendJsonObject(AJAX_SUCCESS_CODE);
                }
                if (partyId != employee.getPartyId()){
                    return sendJsonObject(AJAX_FAILURE_CODE);
                }
            }
            throw SSException.get(EmenuException.CheckEmployeeNumberFail);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }

    /**
     * 添加和修改员工信息时，检查电话是否重复
     * @param phone
     * @return
     */
    @RequestMapping(value = "ajax/checkphone",method = RequestMethod.GET)
    @ResponseBody
    public JSONObject checkPhoneIsExist(@RequestParam("partyId")Integer partyId,@RequestParam("phone")String phone){
        try {
            if (Assert.isNotNull(partyId)&&Assert.isZero(partyId)){
                if(employeeService.checkPhoneIsExist(phone)){
                    return sendJsonObject(AJAX_FAILURE_CODE);
                }else {
                    return sendJsonObject(AJAX_SUCCESS_CODE);
                }
            }
            if (Assert.isNotNull(partyId)&&!Assert.lessOrEqualZero(partyId)){
                Employee employee = employeeService.queryByPhone(phone);
                if (partyId == employee.getPartyId()){
                    return sendJsonObject(AJAX_SUCCESS_CODE);
                }
                if (partyId != employee.getPartyId()){
                    return sendJsonObject(AJAX_FAILURE_CODE);
                }
            }
            throw SSException.get(EmenuException.CheckEmployeePhoneFail);
        } catch (SSException e) {
            LogClerk.errLog.error(e);
            return sendErrMsgAndErrCode(e);
        }
    }
}