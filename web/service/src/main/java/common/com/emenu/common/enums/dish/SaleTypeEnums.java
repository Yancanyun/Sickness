package com.emenu.common.enums.dish;

import java.util.HashMap;
import java.util.Map;

/**
 * 促销方式枚举
 *
 * @author: zhangteng
 * @time: 2015/11/19 20:52
 **/
public enum SaleTypeEnums {

    NoSale(1, "无促销"),
    Discount(2, ""),
    SalePrice(3, "促销价格")
    ;

    private Integer id;

    private String type;

    SaleTypeEnums(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    private static Map<Integer, SaleTypeEnums> map = new HashMap<Integer, SaleTypeEnums>();

    static {
        for (SaleTypeEnums enums : SaleTypeEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static SaleTypeEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static SaleTypeEnums valueOf(int id, SaleTypeEnums defaultValue) {
        SaleTypeEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}