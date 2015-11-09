package com.emenu.common.dto.party.group.employee;

import com.emenu.common.entity.party.group.employee.Employee;

import java.util.List;

/**
 * 员工信息Dto
 * @author xiaozl
 * @date 2015-10-23
 */
public class EmployeeDto {

    //用户实体
    private Employee employee;

    //员工登录名
    private String loginName;

    //用户角色标识：1代表后台，2代表吧台，3代表后厨，4代表服务员，5代表顾客
    private List<Integer> role;

    //用户角色名：服务员、吧台、后厨
    private List<String> roleName;

    //启用与禁用
    private String status;

    //如果是服务员，则tables对应的是服务员负责的餐桌；如果不是服务员此项为空,添加用户的时候回用到
    private List<Integer> tables;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public List<Integer> getRole() {
        return role;
    }

    public void setRole(List<Integer> role) {
        this.role = role;
    }

    public List<String> getRoleName() {
        return roleName;
    }

    public void setRoleName(List<String> roleName) {
        this.roleName = roleName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Integer> getTables() {
        return tables;
    }

    public void setTables(List<Integer> tables) {
        this.tables = tables;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
