package com.emenu.common.enums.vip;

import java.util.HashMap;
import java.util.Map;

/**
 * 会员充值消费记录类型枚举
 *
 * @author chenyuting
 * @date 2016/7/25 16:17
 */
public enum ConsumptionActivityTypeEnums {

    Recharge(1, "消费"),
    Consumption(2, "充值");

    private Integer id;
    private String type;

    ConsumptionActivityTypeEnums(int id,String status) {
        this.id = id;
        this.type = type;
    }

    private static Map<Integer, ConsumptionActivityTypeEnums> map = new HashMap<Integer, ConsumptionActivityTypeEnums>();

    static {
        for (ConsumptionActivityTypeEnums consumptionActivityTypeEnums : ConsumptionActivityTypeEnums.values()) {
            map.put(consumptionActivityTypeEnums.getId(), consumptionActivityTypeEnums);
        }
    }

    public static ConsumptionActivityTypeEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static ConsumptionActivityTypeEnums valueOf(int id, ConsumptionActivityTypeEnums defaultValue) {
        ConsumptionActivityTypeEnums consumptionActivityTypeEnums = map.get(id);
        return consumptionActivityTypeEnums == null ? defaultValue : consumptionActivityTypeEnums;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
