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
     * @return List<Employee>
     */
    public List<Employee> listAll(int partyId) throws Exception;;

    /**
     * 根据partyId查询用户角色
     * @param partyId
     * @return
     * @throws Exception
     */
    public List<Integer> queryRoleByPartyId(@Param("partyId") int partyId) throws Exception;;

    /**
     * 检查注册用户是否重名
     * @param Name
     * @return
     * @throws Exception
     */
    public Employee queryByName(@Param("employeeName") String Name) throws Exception;

    /**
     * 根据partyId更改员工状态
     * @param partyId
     * @param status
     */
    public void updateStatusByPartyId(@Param("partyId") int partyId, @Param("status") int status);

    /**
     * 根据
     * @param  partyId
     * @return
     * @throws Exception
     */
    public List<EmployeeRole> listRoleByPartyId(@Param("partyId") int partyId) throws Exception;

    /**
     * 根据partyId删除员工角色
     * @param  partyId
     * @throws Exception
     */
    public void delRoleByPartyId(@Param("partyId") int partyId) throws Exception;

    /**
     * 删除服务员服务的餐桌
     * @param partyId
     * @throws Exception
     */
    public void delWaiterTableByPartyId(@Param("partyId") int partyId) throws  Exception;

    /**
     * 根据partyId查询员工信息t_party_employee
     * @param partyId
     * @throws Exception
     */
    public Employee queryByPartyId(@Param("partyId") int partyId) throws  Exception;

    /**
     * 根据获取对应角色的partyId
     * @param roles
     * @return
     * @throws Exception
     */
    public List<Integer> listPartIdByRoles(@Param("roles") List<Integer> roles) throws Exception;

    /**
     * 根据员工编号查询员工
     * @param embployeeNumber
     * @return
     * @throws Exception
     */
    public Employee queryByNumber(@Param("number")String embployeeNumber) throws Exception;


}
