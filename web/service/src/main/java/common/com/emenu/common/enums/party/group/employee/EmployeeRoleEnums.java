package com.emenu.common.enums.party.group.employee;

import java.util.HashMap;

/**
 * @author xiaozl
 * @date 2015/10/23
 * @time 11:05
 */
public enum EmployeeRoleEnums {


    Admin(1, "后台"),
    Bar(2, "吧台"),
    Cook(3, "后厨"),
    Waiter(4, "服务员"),
    Guest(5,"顾客");

    private Integer id;
    private String description;

    EmployeeRoleEnums(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    private static HashMap<Integer, String> employeeRoleMap;

    static {
        employeeRoleMap = new HashMap<Integer, String>();

        for(EmployeeRoleEnums employeeRoleEnum : EmployeeRoleEnums.values()) {
            employeeRoleMap.put(employeeRoleEnum.getId(), employeeRoleEnum.getDescription());
        }
    }

    public static String getDescriptionById(Integer id) {
        String desc = employeeRoleMap.get(id);
        if(desc != null && !desc.equals("")) {
            return desc;
        } else {
            return "获取错误";
        }
    }

}

