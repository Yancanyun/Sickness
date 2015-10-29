package com.emenu.mapper.party.group.employee;

import com.emenu.common.entity.party.group.employee.Employee;
import com.emenu.common.entity.party.group.employee.EmployeeRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author xiaozl
 * @date 2015/10/23
 * @time 10:41
 */
public interface EmployeeMapper {


    /**
     * 查询所有员工
     *
     * @param
     * @return List<UserDto>
     */
    public List<Employee> listEmployee(Integer partyId) throws Exception;;

    /**
     * 根据partyId查询用户角色
     * @param partyId
     * @return
     * @throws Exception
     */
    public List<Integer> queryEmployeeRoleByEmployeeId(@Param("partyId")Integer partyId) throws Exception;;

    /**
     * 检查注册用户是否重名
     * @param employeeName
     * @return
     * @throws Exception
     */
    public Employee checkEmployeeName(@Param("employeeName") String employeeName) throws Exception;

    /**
     * 根据partyId更改员工状态
     * @param partyId
     * @param status
     */
    public void updateEmployeeStatusByPartyId(@Param("partyId") Integer partyId,@Param("status")Integer status);

    /**
     * 根据
     * @param  partyId
     * @return
     * @throws Exception
     */
    public List<EmployeeRole> listEmployeeRoleByPartyId(@Param("partyId") Integer partyId) throws Exception;

    /**
     * 根据partyId删除员工角色
     * @param  partyId
     * @throws Exception
     */
    public void delEmployeeRoleByPartyId(@Param("partyId") Integer partyId) throws Exception;

    public void delEmployeeWaiterTableByPartyId(@Param("partyId") Integer partyId) throws  Exception;


}
