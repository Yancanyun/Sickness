package com.emenu.common.enums.order;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单菜品催菜状态
 *
 * @author: quanyibo
 * @time: 2016/7/15
 */
public enum OrderDishCallStatusEnums {

    IsNotCall(0, "未催菜"),
    IsCall(1, "已催菜"),
    ;

    private Integer id;
    private String status;

    OrderDishCallStatusEnums(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    private static Map<Integer, OrderDishCallStatusEnums> map = new HashMap<Integer, OrderDishCallStatusEnums>();

    static {
        for (OrderDishCallStatusEnums enums : OrderDishCallStatusEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static OrderDishCallStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static OrderDishCallStatusEnums valueOf(int id, OrderDishCallStatusEnums defaultValue) {
        OrderDishCallStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
