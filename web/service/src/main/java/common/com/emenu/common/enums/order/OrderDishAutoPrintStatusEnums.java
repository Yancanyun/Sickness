package com.emenu.common.enums.order;

import java.util.HashMap;
import java.util.Map;

/**
 * 下单后立即打印
 *
 * @author: quanyibo
 * @time: 2016/8/5
 */
public enum OrderDishAutoPrintStatusEnums {

    NotPrintAtOnce(0, "下单后不立即打印"),
    PrintAtOnce(1, "下单后立即打印"),
    ;

    private Integer id;
    private String status;

    OrderDishAutoPrintStatusEnums(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    private static Map<Integer, OrderDishAutoPrintStatusEnums> map = new HashMap<Integer, OrderDishAutoPrintStatusEnums>();

    static {
        for (OrderDishAutoPrintStatusEnums enums : OrderDishAutoPrintStatusEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static OrderDishAutoPrintStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static OrderDishAutoPrintStatusEnums valueOf(int id, OrderDishAutoPrintStatusEnums defaultValue) {
        OrderDishAutoPrintStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
