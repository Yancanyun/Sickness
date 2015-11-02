package com.emenu.service.party.group.employee.impl;

import com.emenu.common.dto.party.group.employee.EmployeeDto;
import com.emenu.common.entity.party.group.Party;
import com.emenu.common.entity.party.group.employee.Employee;
import com.emenu.common.entity.party.group.employee.EmployeeRole;
import com.emenu.common.entity.party.security.SecurityUser;
import com.emenu.common.entity.table.WaiterTable;
import com.emenu.common.enums.party.PartyTypeEnums;
import com.emenu.common.enums.party.UserStatusEnums;
import com.emenu.common.enums.party.group.employee.EmployeeRoleEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.exception.PartyException;
import com.emenu.common.utils.CommonUtil;
import com.emenu.mapper.party.group.employee.EmployeeMapper;
import com.emenu.service.party.group.PartyService;
import com.emenu.service.party.group.employee.EmployeeService;
import com.emenu.service.party.security.SecurityUserService;
import com.emenu.service.table.WaiterTableService;
import com.pandawork.core.common.exception.SSException;
import com.pandawork.core.common.log.LogClerk;
import com.pandawork.core.common.util.Assert;
import com.pandawork.core.framework.dao.CommonDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author xiaozl
 * @date 2015/10/23
 * @time 10:37
 */
@Service("employeeService")
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeMapper employeeMapper;


    @Autowired
    private WaiterTableService waiterTableService;

    @Autowired
    private PartyService partyService;

    @Autowired
    private SecurityUserService securityUserService;


    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;//core包

    /**
     * 获取所有员工信息
     * @param partyId
     * @return
     * @throws SSException
     */
    @Override
    public List<EmployeeDto> listEmployee(Integer partyId) throws SSException {

        try {
            List<EmployeeDto> employeeDtos = new ArrayList<EmployeeDto>();
            //根据取出启用的员工信息t_party_employee
            List<Employee> employees = employeeMapper.listEmployee(partyId);
            //获取所有启用的员工登录名
            List<SecurityUser> securityUsers = securityUserService.listByPartyId(partyId);

            for(Employee employee : employees) {
                EmployeeDto employeeDto = new EmployeeDto();
                employeeDto.setEmployee(employee);
                List<Integer> role = employeeMapper.queryEmployeeRoleByEmployeeId(employee.getPartyId());//查询每个用户的对应的所有角色
                List<String> roleName = new ArrayList<String>();//根据取出的角色标识，赋予角色名
                //获得用户角色名
                for(int r : role) {
                    roleName.add(EmployeeRoleEnums.getDescriptionById(r));
                }
                //数据存入dto
                employeeDto.setRole(role);
                employeeDto.setRoleName(roleName);
                employeeDto.setStatus(UserStatusEnums.Disabled.getState());
                employeeDtos.add(employeeDto);
            }

            for(EmployeeDto employeeDto : employeeDtos){
                for(SecurityUser securityUser : securityUsers){
                    if(employeeDto.getEmployee().getPartyId()==securityUser.getPartyId()){
                        employeeDto.setLoginName(securityUser.getLoginName());
                    }
                }
            }
            return employeeDtos;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryEmployeeInfoFail, e);
        }


    }

    @Override
    public List<EmployeeDto> listEmployeeByContition(List<Integer> roleList, Integer partyId) throws SSException {
        try {
            List<EmployeeDto> employeeDtos = new ArrayList<EmployeeDto>();
            List<Employee> employees = new ArrayList<Employee>();
            //获取所有启用的员工登录名
            List<SecurityUser> securityUsers = securityUserService.listByPartyId(partyId);
            List<Integer> partyIdList = employeeMapper.listPartIdByContition(roleList);

                for (Integer id : partyIdList){

                    Employee employee = employeeMapper.queryEmployeeByPartyId(id);

                    employees.add(employee);
                }

                for(Employee employee : employees) {
                    EmployeeDto employeeDto = new EmployeeDto();
                    employeeDto.setEmployee(employee);

                    System.out.println("hello" + employee.getPartyId());

                    List<Integer> role = employeeMapper.queryEmployeeRoleByEmployeeId(employee.getPartyId());//查询每个用户的对应的所有角色
                    List<String> roleName = new ArrayList<String>();//根据取出的角色标识，赋予角色名
                    //获得用户角色名
                    for(int r : role) {
                        roleName.add(EmployeeRoleEnums.getDescriptionById(r));
                    }
                    //数据存入dto
                    //数据存入dto
                    employeeDto.setRole(role);
                    employeeDto.setRoleName(roleName);
                    employeeDto.setStatus(UserStatusEnums.Disabled.getState());
                    employeeDtos.add(employeeDto);
                }

                for(EmployeeDto employeeDto : employeeDtos){
                    for(SecurityUser securityUser : securityUsers){
                        if(employeeDto.getEmployee().getPartyId()==securityUser.getPartyId()){
                            employeeDto.setLoginName(securityUser.getLoginName());
                        }
                    }
                }
            return employeeDtos;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryEmployeeInfoFail, e);
        }
    }



    /**
     * 检查注册员工是否用户名重名
     * @param employeeName
     * @return
     * @throws SSException
     */
    @Override
    public boolean checkEmployeeName(String employeeName) throws SSException {

        try {
            if(employeeMapper.checkEmployeeName(employeeName) != null) {
                return true;
            } else {
                return  false;
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }


    }

    /**
     * 插入新注册的员工
     * @param employeeDto
     * @param loginName
     * @param password
     * @return
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public Employee newEmployee(EmployeeDto employeeDto,String loginName,String password) throws SSException
    {
        try {

            List<Integer> role = employeeDto.getRole();
            //新添加运功时新建抽象员工
            Party party = new Party();

            //向t_party_securtiy_user表中查人添加的用户信息
            SecurityUser securityUser = new SecurityUser();

            //设置用户类型：员工
            party.setPartyTypeId(PartyTypeEnums.Employee.getId());

            //向t_party表中查人新建的抽象员工并返回partyId
            Integer partyId = partyService.newParty(party).getId();

            securityUser.setPartyId(partyId);
            securityUser.setLoginName(loginName);
            securityUser.setPassword(password);
            securityUser.setAccountType(1);
            securityUser.setStatus(1);
            securityUser.setUpdatePassword(0);

            securityUserService.newSecurityUser(securityUser);

            //向t_employee表重插入具体员工信息
            Employee employee = employeeDto.getEmployee();

            employee.setStatus(UserStatusEnums.Enabled.getId());
            employee.setPartyId(partyId);

            commonDao.insert(employee);


            for (int r : role) {
                //UserRole类型实体存储要插入角色表中的信息，插入每个用户的所有角色
                EmployeeRole employeeRole = new EmployeeRole();
                employeeRole.setPartyId(partyId);
                employeeRole.setRoleId(r);

                commonDao.insert(employeeRole);

                //TODO 如果是服务员，还需添加到waiter_area表
                List<Integer> tables = employeeDto.getTables();
                if (r == EmployeeRoleEnums.Waiter.getId() && tables != null && tables.size() != 0) {
                    List<WaiterTable> waiterTables = new ArrayList<WaiterTable>();
                    for (Integer tableId : tables) {
                        WaiterTable waiterTable = new WaiterTable();
                        waiterTable.setWaiterId(partyId);
                        waiterTable.setTableId(tableId);
                        waiterTables.add(waiterTable);
                    }
                    waiterTableService.insertWaiterTable(waiterTables);
                }
            }
            return employee;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }


    /**
     * 更新员工信息
     * @param employeeDto
     * @param partyId
     * @param newloginName
     * @param newPwd
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateEmployee(EmployeeDto employeeDto,
                               Integer partyId,
                               String newloginName,
                               String newPwd) throws SSException {
        try {

            //获取员工角色
            List<Integer> role = employeeDto.getRole();

            //获取当前partyId员工注册信息
            SecurityUser securityUser = securityUserService.queryByPartyId(partyId);

            //更新员工登录名和密码
            securityUser.setPassword(newPwd);
            securityUser.setLoginName(newloginName);

           // System.out.println(securityUser.getLoginName()+securityUser.getId());

            //更新t_party_security_user
            securityUserService.updateSecurityUser(securityUser);

            //更新员工姓名、电话等信息t_party_employee
            Employee employee = employeeDto.getEmployee();

            commonDao.update(employee);

            employeeMapper.delEmployeeRoleByPartyId(partyId);
            employeeMapper.delEmployeeWaiterTableByPartyId(partyId);

            for (int r : role) {
                //UserRole类型实体存储要插入角色表中的信息，插入每个用户的所有角色
                EmployeeRole employeeRole = new EmployeeRole();
                employeeRole.setPartyId(partyId);
                employeeRole.setRoleId(r);

                commonDao.insert(employeeRole);

                //TODO 如果是服务员，还需添加到waiter_area表
                List<Integer> tables = employeeDto.getTables();
                if (r == EmployeeRoleEnums.Waiter.getId() && tables != null && tables.size() != 0) {
                    List<WaiterTable> waiterTables = new ArrayList<WaiterTable>();
                    for (Integer tableId : tables) {
                        WaiterTable waiterTable = new WaiterTable();
                        waiterTable.setWaiterId(partyId);
                        waiterTable.setTableId(tableId);
                        waiterTables.add(waiterTable);
                    }
                    waiterTableService.insertWaiterTable(waiterTables);
                }
            }

        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(PartyException.SystemException, e);
        }
    }



    /**
     * 修改密码时检查原密码是否正确
     * @param securityUserId
     * @param oldPwd
     * @return
     * @throws SSException
     */
    @Override
    public boolean checkOldPwd(Integer securityUserId, String oldPwd) throws SSException {
        try {
            String opd = CommonUtil.md5(oldPwd);
            SecurityUser securityUser = commonDao.queryById(SecurityUser.class, securityUserId);
            if(securityUser != null && securityUser.getPassword().equals(opd)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }

    }

    /**
     *
     * @param securityUserId
     * @param oldPwd
     * @param newPwd
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updatePwd(Integer securityUserId, String oldPwd, String newPwd) throws SSException {
        try {
            if(checkOldPwd(securityUserId, CommonUtil.md5(oldPwd))) {
                String npd = CommonUtil.md5(newPwd);
                SecurityUser securityUser = new SecurityUser();
                securityUser.setId(securityUserId);
                securityUser.setPassword(npd);
                commonDao.updateFieldsById(securityUser, "password");
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateUserPwdFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updateEmployeeStatus(Integer partyId, Integer status) throws SSException {
        try {
            //User user = commonDao.queryById(User.class, uid);
            //修改t_party_employee表中员工状态
            employeeMapper.updateEmployeeStatusByPartyId(partyId, status);

            SecurityUser securityUser = securityUserService.queryByPartyId(partyId);
            securityUser.setStatus(status);
            //根据securityUserId修改员工状态t_party_security_user
            securityUserService.updateSecurityUser(securityUser);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.UpdateEmployeeStateFail, e);
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void delEmployeeByPartyId(Integer partyId) throws SSException {
        //查员工信息t_party_employee
        Employee employee=queryEmployeeByPartyId(partyId);
        if(employee!=null&&employee.getStatus().equals(UserStatusEnums.Enabled.getId())){
            throw SSException.get(EmenuException.EmployeeIsActivity,new Exception());
        }
        //假删除，在数据库表中并没有删除，只是把status字段设为“删除”
        try {
            updateEmployeeStatus(partyId, UserStatusEnums.Deleted.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteEmployeeFail, e);
        }
    }

    @Override
    public Employee queryEmployeeByPartyId(Integer partyId) throws SSException {
        Employee employee = null;
        if (Assert.lessOrEqualZero(partyId)) {
            return employee;
        }

        try {
            employee = employeeMapper.queryEmployeeByPartyId(partyId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryEmployeeException, e);
        }

        return employee;
    }


}
