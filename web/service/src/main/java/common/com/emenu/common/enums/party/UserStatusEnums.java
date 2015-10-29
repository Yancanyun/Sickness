package com.emenu.common.enums.party;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户状态 Enums
 * @author chenyuting
 * @date 2015/10/23  10:51
 */
public enum UserStatusEnums {

    Enabled(1, "启用"),
    Disabled(2, "禁用"),
    Deleted(3, "删除");

    private Integer id;
    private String state;

    UserStatusEnums(Integer id, String state) {
        this.id = id;
        this.state = state;
    }

    private static Map<Integer, UserStatusEnums> map = new HashMap<Integer, UserStatusEnums>();

    static {
        for (UserStatusEnums userStatusEnums : UserStatusEnums.values()) {
            map.put(userStatusEnums.getId(), userStatusEnums);
        }
    }

    public static UserStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static UserStatusEnums valueOf(int id, UserStatusEnums defaultValue) {
        UserStatusEnums userStatusEnums = map.get(id);
        return userStatusEnums == null ? defaultValue : userStatusEnums;
    }

    public Integer getId() {
        return id;
    }

    public String getState() {
        return state;
    }

}
