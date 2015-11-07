package com.emenu.common.enums.remark;

import java.util.HashMap;
import java.util.Map;

/**
 * RemarkTagStateEnums
 *
 * @author: yangch
 * @time: 2015/11/7 15:40
 */
public enum RemarkStateEnums {
    Enabled(1, "可用"),
    Deleted(2, "已删除");

    private Integer id;
    private String state;

    RemarkStateEnums(Integer id, String type) {
        this.id = id;
        this.state = type;
    }

    private static Map<Integer, RemarkStateEnums> map = new HashMap<Integer, RemarkStateEnums>();

    static {
        for (RemarkStateEnums enums : RemarkStateEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static RemarkStateEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static RemarkStateEnums valueOf(int id, RemarkStateEnums defaultValue) {
        RemarkStateEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return state;
    }
}
