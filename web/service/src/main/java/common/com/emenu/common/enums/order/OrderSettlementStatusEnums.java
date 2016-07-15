package com.emenu.common.enums.order;


import java.util.HashMap;
import java.util.Map;

/**
 * 订单盘点状态枚举
 *
 * @author: quanyibo
 * @time: 2016/7/15
 */
public enum OrderSettlementStatusEnums {

    IsNotSettlement(0, "未盘点"),
    IsSettlement(1, "已盘点"),
    ;

    private Integer id;
    private String status;

    OrderSettlementStatusEnums(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    private static Map<Integer, OrderSettlementStatusEnums> map = new HashMap<Integer, OrderSettlementStatusEnums>();

    static {
        for (OrderSettlementStatusEnums enums : OrderSettlementStatusEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static OrderSettlementStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static OrderSettlementStatusEnums valueOf(int id, OrderSettlementStatusEnums defaultValue) {
        OrderSettlementStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
