package com.emenu.common.enums.order;

import java.util.HashMap;
import java.util.Map;

/**
 * 上菜方式枚举
 *
 * @author: yangch
 * @time: 2015/11/2 15:22
 */
public enum OrderStatusEnums {
    IsBooked(1, "已下单"),
    IsCheckouted(2, "已结账");

    private Integer id;
    private String status;

    OrderStatusEnums(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    private static Map<Integer, OrderStatusEnums> map = new HashMap<Integer, OrderStatusEnums>();

    static {
        for (OrderStatusEnums enums : OrderStatusEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static OrderStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static OrderStatusEnums valueOf(int id, OrderStatusEnums defaultValue) {
        OrderStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
