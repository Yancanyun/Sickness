package com.emenu.service.party.group.employee.impl;

import com.emenu.common.dto.party.group.employee.EmployeeDto;
import com.emenu.common.entity.party.group.Party;
import com.emenu.common.entity.party.group.employee.Employee;
import com.emenu.common.entity.party.group.employee.EmployeeRole;
import com.emenu.common.entity.party.security.SecurityUser;
import com.emenu.common.entity.party.security.SecurityUserGroup;
import com.emenu.common.entity.table.WaiterTable;
import com.emenu.common.enums.party.EnableEnums;
import com.emenu.common.enums.party.PartyTypeEnums;
import com.emenu.common.enums.party.UserStatusEnums;
import com.emenu.common.enums.party.group.employee.EmployeeRoleEnums;
import com.emenu.common.exception.EmenuException;
import com.emenu.common.exception.PartyException;
import com.emenu.common.utils.CommonUtil;
import com.emenu.mapper.party.group.employee.EmployeeMapper;
import com.emenu.service.party.group.PartyService;
import com.emenu.service.party.group.employee.EmployeeService;
import com.emenu.service.party.security.SecurityUserGroupService;
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
import java.util.Collections;
import java.util.List;

/**
 * 用户管理service接口实现
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
    private SecurityUserGroupService securityUserGroupService;


    @Autowired
    @Qualifier("commonDao")
    private CommonDao commonDao;//core包

    /**
     * 获取所有员工信息
     * @return
     * @throws SSException
     */
    @Override
    public List<EmployeeDto> listAll() throws SSException {
        try {
            List<EmployeeDto> employeeDtos = new ArrayList<EmployeeDto>();
            //根据取出启用的员工信息t_party_employee
            List<Employee> employees = Collections.emptyList();
            employees = employeeMapper.listAll();
           /* //获取所有启用的员工登录名
            List<SecurityUser> securityUsers = Collections.emptyList();
            securityUsers = securityUserService.listByPartyId(partyId);*/
            for (Employee employee : employees) {
                EmployeeDto employeeDto = new EmployeeDto();
                employeeDto.setEmployee(employee);
                List<Integer> roles = Collections.emptyList();
                roles = employeeMapper.queryRoleByPartyId(employee.getPartyId());//查询每个用户的对应的所有角色

                List<String> roleNames = new ArrayList<String>();//根据取出的角色标识，赋予角色名
                    //获得用户角色名
                    for (int r : roles) {
                        roleNames.add(EmployeeRoleEnums.getDescriptionById(r));
                    }
                    //数据存入dto
                employeeDto.setRole(roles);
                employeeDto.setRoleName(roleNames);
                if(employee.getStatus()==1) {
                    employeeDto.setStatus(UserStatusEnums.Enabled.getStatus());
                }
                if(employee.getStatus()==2){
                    employeeDto.setStatus(UserStatusEnums.Disabled.getStatus());
                }
                SecurityUser securityUser = securityUserService.queryByPartyId(employee.getPartyId());
                employeeDto.setLoginName(securityUser.getLoginName());

                employeeDtos.add(employeeDto);
                }
                return employeeDtos;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryEmployeeInfoFail, e);
        }
    }

    @Override
    public List<EmployeeDto> listByRoles(List<Integer> roleList) throws SSException {
        //存放所有员工的所有信息
        List<EmployeeDto> employeeDtos = new ArrayList<EmployeeDto>();
        //存放所有员工t_party_employee中的信息
        List<Employee> employees = new ArrayList<Employee>();
        try {
            //获取具有该角色的所有当事人partId
            List<Integer> partyIdList =Collections.emptyList();
            partyIdList = employeeMapper.listPartIdByRoles(roleList);

            //获取指定partId的员工t_party_employee中的信息，
            for (Integer id : partyIdList) {
                Employee employee = employeeMapper.queryByPartyId(id);
                //不获取删除的员工
                if(employee.getStatus()!=3) {
                    employees.add(employee);
                }
            }

            //根据获取的员工t_party_employee表中的信息获取角色，服务的餐桌等信息
            for (Employee employee : employees) {
                EmployeeDto employeeDto = new EmployeeDto();
                //数据存入Dto
                employeeDto.setEmployee(employee);

                //查询每个用户的对应的所有角色
                List<Integer> roles = Collections.emptyList();
                roles = employeeMapper.queryRoleByPartyId(employee.getPartyId());

                //根据取出的角色标识，赋予角色名
                List<String> roleNames = new ArrayList<String>();
                //roleNames = new ArrayList<String>();
                for (int r : roles) {
                        roleNames.add(EmployeeRoleEnums.getDescriptionById(r));
                }
                //员工信息存入dto
                employeeDto.setRole(roles);
                employeeDto.setRoleName(roleNames);
                //获取员工状态信息
                if(employee.getStatus()==1){
                    employeeDto.setStatus(UserStatusEnums.Enabled.getStatus());
                }else if(employee.getStatus()==2){
                    employeeDto.setStatus(UserStatusEnums.Disabled.getStatus());
                }
                //获取员工登录名
                SecurityUser securityUser = securityUserService.queryByPartyId(employee.getPartyId());
                employeeDto.setLoginName(securityUser.getLoginName());

                employeeDtos.add(employeeDto);
                }
                return employeeDtos;
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryEmployeeInfoFail, e);
        }
    }


    /**
     * 检查注册员工是否姓名重名
     * @param employeeName
     * @return
     * @throws SSException
     */
    @Override
    public boolean checkNameIsExist(String employeeName) throws SSException {
        try {
            if(Assert.isNull(employeeName)){
                throw SSException.get(EmenuException.EmployeeNameNotNull);
            }
            if(employeeMapper.queryByName(employeeName) != null) {
                return true;
            } else {
                return  false;
            }
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }

    @Override
    public boolean checkNumberIsExist(String employeeNumber) throws SSException {
        try{
            if(Assert.isNull(employeeNumber)){
                throw SSException.get(EmenuException.EmployeeNumberNotNull);
            }
            if(employeeMapper.queryByNumber(employeeNumber)!= null) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.SystemException, e);
        }
    }

    @Override
    public boolean checkPhoneIsExist(String phone) throws SSException {
        try{
            if(employeeMapper.queryByPhone(phone) != null) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e) {
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

            // 向SecurityUserGroup中插入信息
            List<Integer> roleIdList = employeeDto.getRole();
            for (Integer roleId : roleIdList) {
                SecurityUserGroup securityUserGroup = new SecurityUserGroup();
                securityUserGroup.setGroupId(roleId);
                securityUserGroup.setUserId(securityUser.getId());

                securityUserGroupService.newSecurityUserGroup(securityUserGroup);
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
    public void update(EmployeeDto employeeDto,
                       int partyId,
                       String newloginName,
                       String newPwd) throws SSException {
        try {
            if(Assert.isNull(employeeDto)){
                return;
            }

            //修改员工登录名密码等信息
            //获取当前partyId员工注册信息
            SecurityUser securityUser = securityUserService.queryByPartyId(partyId);
            Assert.isNotNull(securityUser,PartyException.UserNotExist);
            //密码
            if (Assert.isNotNull(newPwd)){
                securityUser.setPassword(newPwd);
            }
            //更新用户名
            securityUser.setLoginName(newloginName);
            //更新t_party_security_user
            securityUserService.updateSecurityUser(securityUser);
            //更新员工姓名、电话等信息t_party_employee
            Employee employeeNew = employeeDto.getEmployee();
            Employee employeeOld = employeeMapper.queryByPartyId(partyId);
            //员工姓名
            if (Assert.isNotNull(employeeNew.getName())){
                employeeOld.setName(employeeNew.getName());
            } else {
                throw SSException.get(EmenuException.EmployeeNameNotNull);
            }
            //员工编号
            if (Assert.isNotNull(employeeNew.getEmployeeNumber())){
                employeeOld.setEmployeeNumber(employeeNew.getEmployeeNumber());
            } else {
                throw SSException.get(EmenuException.EmployeeNumberNotNull);
            }
            //员工电话
            if (Assert.isNotNull(employeeNew.getPhone())){
                employeeOld.setPhone(employeeNew.getPhone());
            } else {
                throw SSException.get(EmenuException.PhoneError);
            }
            //更新
            commonDao.update(employeeOld);
            //删除了员工角色和员工服务的餐台在添加
            employeeMapper.delRoleByPartyId(partyId);
            employeeMapper.delWaiterTableByPartyId(partyId);
            //获取员工角色
            List<Integer> roles = employeeDto.getRole();
            for (int r : roles) {
                //UserRole类型实体存储要插入角色表中的信息，插入每个用户的所有角色
                EmployeeRole employeeRole = new EmployeeRole();
                employeeRole.setPartyId(partyId);
                employeeRole.setRoleId(r);
                //添加员工角色信息
                commonDao.insert(employeeRole);
                // 如果是服务员，还需添加到waiter_area表，设置服务员服务的餐台
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
            // 向SecurityUserGroup中先删除再插入信息
            securityUserGroupService.delByUserId(securityUser.getId());
            List<Integer> roleIdList = employeeDto.getRole();
            for (Integer roleId : roleIdList) {
                SecurityUserGroup securityUserGroup = new SecurityUserGroup();
                securityUserGroup.setGroupId(roleId);
                securityUserGroup.setUserId(securityUser.getId());
                securityUserGroupService.newSecurityUserGroup(securityUserGroup);
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
    public boolean checkOldPwd(int securityUserId, String oldPwd) throws SSException {
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
     *修改密码
     * @param securityUserId
     * @param oldPwd
     * @param newPwd
     * @throws SSException
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {SSException.class, Exception.class, RuntimeException.class})
    public void updatePwd(int securityUserId, String oldPwd, String newPwd) throws SSException {
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
    public void updateStatus(int partyId, Integer status) throws SSException {
        try {
            //User user = commonDao.queryById(User.class, uid);
            //修改t_party_employee表中员工状态
            employeeMapper.updateStatusByPartyId(partyId, status);

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
    public void delByPartyId(int partyId) throws SSException {
        //假删除，在数据库表中并没有删除，只是把status字段设为“删除”
        try {
            //修改t_employee员工状态
            employeeMapper.updateStatusByPartyId(partyId, UserStatusEnums.Deleted.getId());
            //删除员工和角色关联的信息

            //删除员工服务的餐桌
            employeeMapper.delRoleByPartyId(partyId);
            employeeMapper.delWaiterTableByPartyId(partyId);

            //修改t_party_security_user员工状态
            SecurityUser securityUser = securityUserService.queryByPartyId(partyId);
            securityUser.setStatus(EnableEnums.Disabled.getId());
            securityUserService.updateSecurityUser(securityUser);
            // 从SecurityUserGroup中删除信息
            securityUserGroupService.delByUserId(securityUser.getId());
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.DeleteEmployeeFail, e);
        }
    }

    @Override
    public Employee queryByPartyId(int partyId) throws SSException {
        Employee employee = null;
        if (Assert.lessOrEqualZero(partyId)) {
            return employee;
        }

        try {
            employee = employeeMapper.queryByPartyId(partyId);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryEmployeeException, e);
        }

        return employee;
    }

    public EmployeeDto queryEmployeeDtoByPartyId(int partyId) throws SSException{
        EmployeeDto employeeDto = new EmployeeDto();
        try{
            if (Assert.lessOrEqualZero(partyId)) {
                return null;
            }
            Employee employee = queryByPartyId(partyId);

            employeeDto.setEmployee(employee);
            //获取指定partyId的用户角色
            List<Integer> roles = Collections.emptyList();
            roles = employeeMapper.queryRoleByPartyId(employee.getPartyId());//查询每个用户的对应的所有角色
            List<String> roleNames = new ArrayList<String>();//根据取出的角色标识，赋予角色名

            //获取服务员服务的餐桌
            List<Integer> tables = waiterTableService.queryByPartyId(partyId);
            employeeDto.setTables(tables);
            //获得用户角色名
            for (int r : roles) {
                    roleNames.add(EmployeeRoleEnums.getDescriptionById(r));
            }
            //数据存入dto
            employeeDto.setRole(roles);
            employeeDto.setRoleName(roleNames);
            if(employee.getStatus()==1) {
                employeeDto.setStatus(UserStatusEnums.Enabled.getStatus());
            }
            if(employee.getStatus()==2){
                employeeDto.setStatus(UserStatusEnums.Disabled.getStatus());
            }

            SecurityUser securityUser = securityUserService.queryByPartyId(employee.getPartyId());
            employeeDto.setLoginName(securityUser.getLoginName());

            return employeeDto;
        }catch (Exception e){
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryEmployeeException, e);
        }
    }

    @Override
    public Employee queryByNumber(String employeeNumber) throws SSException {
        try {
              if (Assert.isNull(employeeNumber)){
                  throw SSException.get(EmenuException.EmployeeNumberNotNull);
              }
            return employeeMapper.queryByNumber(employeeNumber);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryEmployeeException, e);
        }
    }

    @Override
    public Employee queryByPhone(String phone) throws SSException {
        try {
            if (Assert.isNull(phone)){
                throw SSException.get(EmenuException.CheckEmployeePhoneFail);
            }
            return employeeMapper.queryByPhone(phone);
        } catch (Exception e) {
            LogClerk.errLog.error(e);
            throw SSException.get(EmenuException.QueryEmployeeException, e);
        }
    }

}
