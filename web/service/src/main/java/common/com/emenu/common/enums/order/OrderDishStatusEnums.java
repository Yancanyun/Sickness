package com.emenu.common.enums.order;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单菜品状态枚举
 *
 * @author: quanyibo
 * @time: 2016/7/14
 */
public enum OrderDishStatusEnums {
    IsBooked(1, "已下单"),
    IsMake(2, "正在做"),
    IsFinsh(3,"已上菜"),
    IsBack(4,"已退菜"),
    ;

    private Integer id;
    private String status;

    OrderDishStatusEnums(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    private static Map<Integer, OrderDishStatusEnums> map = new HashMap<Integer, OrderDishStatusEnums>();

    static {
        for (OrderDishStatusEnums enums : OrderDishStatusEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static OrderDishStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static OrderDishStatusEnums valueOf(int id, OrderDishStatusEnums defaultValue) {
        OrderDishStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
