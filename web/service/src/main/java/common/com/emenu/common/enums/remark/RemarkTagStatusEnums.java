package com.emenu.common.enums.remark;

import java.util.HashMap;
import java.util.Map;

/**
 * RemarkTagStatusEnums
 *
 * @author: yangch
 * @time: 2015/11/7 13:59
 */
public enum RemarkTagStatusEnums {
    Enabled(1, "可用"),
    Deleted(2, "已删除");

    private Integer id;
    private String status;

    RemarkTagStatusEnums(Integer id, String type) {
        this.id = id;
        this.status = type;
    }

    private static Map<Integer, RemarkTagStatusEnums> map = new HashMap<Integer, RemarkTagStatusEnums>();

    static {
        for (RemarkTagStatusEnums enums : RemarkTagStatusEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static RemarkTagStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static RemarkTagStatusEnums valueOf(int id, RemarkTagStatusEnums defaultValue) {
        RemarkTagStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return status;
    }
}
