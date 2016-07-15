package com.emenu.common.enums.order;


import java.util.HashMap;
import java.util.Map;

/**
 * 订单菜品换桌状态枚举
 *
 * @author: quanyibo
 * @time: 2016/7/15
 */
public enum OrderDishTableChangeStatusEnums {


    IsNotChangeTable(0, "未换桌"),
    IsChangeTable(1, "已换桌"),
    ;

    private Integer id;
    private String status;

    OrderDishTableChangeStatusEnums(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    private static Map<Integer, OrderDishTableChangeStatusEnums> map = new HashMap<Integer, OrderDishTableChangeStatusEnums>();

    static {
        for (OrderDishTableChangeStatusEnums enums : OrderDishTableChangeStatusEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static OrderDishTableChangeStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static OrderDishTableChangeStatusEnums valueOf(int id, OrderDishTableChangeStatusEnums defaultValue) {
        OrderDishTableChangeStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
