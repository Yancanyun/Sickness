package com.emenu.common.dto.party.group.employee;

import com.emenu.common.entity.party.employee.Employee;

import java.util.List;

/**
 * @author xiaozl
 * @time 10:30
 */
public class EmployeeDto {

    private Employee employee;//用户实体

    private List<Integer> role;//用户角色标识：1代表后台，2代表吧台，3代表后厨，4代表服务员，5代表顾客

    private List<String> roleName;//用户角色名：服务员、吧台、后厨

    private String status;//标识加锁和解锁

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
}
