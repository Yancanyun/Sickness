package com.emenu.common.enums.waiter;

import java.util.HashMap;
import java.util.Map;

/**
 * WaiterWaiterTableStatusEnums
 *
 * @author: yangch
 * @time: 2016/6/21 17:15
 */
public enum WaiterTableStatusEnums {
    OpenTable(1, "开台"),
    CleanTable(2, "清台"),
    ChangeTable(3, "换台"),
    MergeTable(4, "并台"),
    OrderDish(5, "点菜"),
    QueryCheckout(6, "查单"),
    RetreatDish(7, "退菜"),
    Confirm(8, "确认");

    private Integer id;
    private String status;

    WaiterTableStatusEnums(Integer id, String type) {
        this.id = id;
        this.status = type;
    }

    private static Map<Integer, WaiterTableStatusEnums> map = new HashMap<Integer, WaiterTableStatusEnums>();

    static {
        for (WaiterTableStatusEnums enums : WaiterTableStatusEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static WaiterTableStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static WaiterTableStatusEnums valueOf(int id, WaiterTableStatusEnums defaultValue) {
        WaiterTableStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return status;
    }
}
