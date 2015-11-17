package com.emenu.common.enums.remark;

import java.util.HashMap;
import java.util.Map;

/**
 * RemarkTagStatusEnums
 *
 * @author: yangch
 * @time: 2015/11/7 15:40
 */
public enum RemarkStatusEnums {
    Enabled(1, "可用"),
    Deleted(2, "已删除");

    private Integer id;
    private String status;

    RemarkStatusEnums(Integer id, String type) {
        this.id = id;
        this.status = type;
    }

    private static Map<Integer, RemarkStatusEnums> map = new HashMap<Integer, RemarkStatusEnums>();

    static {
        for (RemarkStatusEnums enums : RemarkStatusEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static RemarkStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static RemarkStatusEnums valueOf(int id, RemarkStatusEnums defaultValue) {
        RemarkStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return status;
    }
}
