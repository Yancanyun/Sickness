package com.emenu.common.enums.vip;

import java.util.HashMap;
import java.util.Map;

/**
 * StatusEnums
 *
 * @author xubr
 * @date 2016/1/19.
 */
public enum StatusEnums {

    Disabled(0, "停用"),
    Enabled(1, "启用"),
    Deleted(2,"删除");

    private Integer id;
    private String status;

    StatusEnums(int id,String status) {
        this.id = id;
        this.status = status;
    }

    private static Map<Integer, StatusEnums> map = new HashMap<Integer, StatusEnums>();

    static {
        for (StatusEnums statusEnums : StatusEnums.values()) {
            map.put(statusEnums.getId(), statusEnums);
        }
    }

    public static StatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static StatusEnums valueOf(int id, StatusEnums defaultValue) {
        StatusEnums statusEnums = map.get(id);
        return statusEnums == null ? defaultValue : statusEnums;
    }

    public Integer getId() { return id; }

    public void setId(Integer id) { this.id = id; }

    public String getStatus() { return status; }

    public void setStatus(String status) { this.status = status; }
}

