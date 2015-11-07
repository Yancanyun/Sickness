package com.emenu.common.enums.remark;

import java.util.HashMap;
import java.util.Map;

/**
 * RemarkTagStateEnums
 *
 * @author: yangch
 * @time: 2015/11/7 13:59
 */
public enum RemarkTagStateEnums {
    Enabled(1, "可用"),
    Deleted(2, "已删除");

    private Integer id;
    private String state;

    RemarkTagStateEnums(Integer id, String type) {
        this.id = id;
        this.state = type;
    }

    private static Map<Integer, RemarkTagStateEnums> map = new HashMap<Integer, RemarkTagStateEnums>();

    static {
        for (RemarkTagStateEnums enums : RemarkTagStateEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static RemarkTagStateEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static RemarkTagStateEnums valueOf(int id, RemarkTagStateEnums defaultValue) {
        RemarkTagStateEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return state;
    }
}
