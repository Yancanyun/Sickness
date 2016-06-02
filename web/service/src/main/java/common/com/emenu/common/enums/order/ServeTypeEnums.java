package com.emenu.common.enums.order;

import java.util.HashMap;
import java.util.Map;

/**
 * 上菜方式枚举
 *
 * @author: yangch
 * @time: 2015/11/2 15:22
 */
public enum ServeTypeEnums {
    Instant(1, "即起"),
    Later(2, "叫起");

    private Integer id;
    private String status;

    ServeTypeEnums(Integer id, String type) {
        this.id = id;
        this.status = type;
    }

    private static Map<Integer, ServeTypeEnums> map = new HashMap<Integer, ServeTypeEnums>();

    static {
        for (ServeTypeEnums enums : ServeTypeEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static ServeTypeEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static ServeTypeEnums valueOf(int id, ServeTypeEnums defaultValue) {
        ServeTypeEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return status;
    }
}
