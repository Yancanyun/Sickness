package com.emenu.common.enums.stock;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Flying
 * @date 2017/5/18 16:59
 */
public enum StockWarnEnum {

    Untreated(0, "未处理"),
    Ignored(1, "已忽略"),
    Resolved(2, "已解决");

    private Integer id;

    private String description;

    StockWarnEnum(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    private static Map<Integer, StockWarnEnum> map = new HashMap<Integer, StockWarnEnum>();

    static {
        for (StockWarnEnum enums : StockWarnEnum.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static StockWarnEnum valueOf(int id) {
        return valueOf(id, null);
    }

    public static StockWarnEnum valueOf(int id, StockWarnEnum defaultValue) {
        StockWarnEnum enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
