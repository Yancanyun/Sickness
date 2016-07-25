package com.emenu.common.enums.call;

import java.util.HashMap;
import java.util.Map;

/**
 * 呼叫服务缓存状态枚举
 *
 * @author: quanyibo
 * @time: 2016/7/23
 */
public enum CallCacheResponseEnums {
    IsResponse(0, "已应答"),
    IsNotResponse(1, "未应答"),
    ;

    private Integer id;
    private String status;

    CallCacheResponseEnums(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    private static Map<Integer, CallCacheResponseEnums> map = new HashMap<Integer, CallCacheResponseEnums>();

    static {
        for (CallCacheResponseEnums enums : CallCacheResponseEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static CallCacheResponseEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static CallCacheResponseEnums valueOf(int id, CallCacheResponseEnums defaultValue) {
        CallCacheResponseEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
