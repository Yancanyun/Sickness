package com.emenu.service.party.group.employee;

import com.emenu.common.dto.party.group.employee.EmployeeDto;
import com.emenu.common.entity.party.group.employee.Employee;
import com.pandawork.core.common.exception.SSException;

import java.util.List;

/**
 * @author xiaozl
 * @date 2015/10/23
 * @time 10:27
 */
public interface EmployeeService {

    /**
     * 获取所有员工信息
     * @param partyId
     * @return
     * @throws SSException
     */
    public List<EmployeeDto> listEmployee(Integer partyId) throws SSException;

    /**
     * 检查注册员工是否用户名重名
     * @param employeeName
     * @return
     * @throws SSException
     */
    public boolean checkEmployeeName(String employeeName) throws SSException;

    /**
     * 添加新员工
     * @param employeeDto
     * @return
     * @throws SSException
     */
    public Employee newEmployee(EmployeeDto employeeDto,String loginName,String password) throws SSException;

    /**
     *
     * @param employeeDto
     * @param partyId
     * @param newloginName
     * @param newPwd
     * @throws SSException
     */
    public void updateEmployee(EmployeeDto employeeDto,Integer partyId, String newloginName,String newPwd) throws SSException;

    /**
     * 修改密码时检查原密码是否正确
     * @param securityUserId
     * @param oldPwd
     * @return
     * @throws SSException
     */
    public boolean checkOldPwd(Integer securityUserId,String oldPwd) throws SSException;

    /**
     * 修改密码
     * @param securityUserId
     * @param oldPwd
     * @param newPwd
     * @throws SSException
     */
    public void updatePwd(Integer securityUserId,String oldPwd,String newPwd) throws SSException;

    /**
     * 更新用户状态
     * @param partyId
     * @param status
     * @throws SSException
     */
    public void updateEmployeeStatus(Integer partyId,Integer status) throws SSException;

    /**
     * 删除员工
     * @param partyId
     * @throws SSException
     */
    public void delEmployeeByPartyId(Integer partyId) throws SSException;

    /**
     * 根据partyId查询员工
     * @param partyId
     * @return
     * @throws SSException
     */
    public Employee queryEmployeeByPartyId(Integer partyId) throws SSException;
}
