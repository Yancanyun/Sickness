package com.emenu.common.enums.order;

import java.util.HashMap;
import java.util.Map;

/**
 * 消费状态枚举(第一次消费,第二次消费)
 *
 * @author: yangch
 * @time: 2016/7/19 20:08
 */
public enum CheckoutConsumptionTypeEnums {

    IsFirstConsumption(1, "第一次消费"),
    IsSecondConsumption(2, "第二次消费")
    ;

    private Integer id;
    private String status;

    CheckoutConsumptionTypeEnums(Integer id, String type) {
        this.id = id;
        this.status = type;
    }

    private static Map<Integer, CheckoutConsumptionTypeEnums> map = new HashMap<Integer, CheckoutConsumptionTypeEnums>();

    static {
        for (CheckoutConsumptionTypeEnums enums : CheckoutConsumptionTypeEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static CheckoutConsumptionTypeEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static CheckoutConsumptionTypeEnums valueOf(int id, CheckoutConsumptionTypeEnums defaultValue) {
        CheckoutConsumptionTypeEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return status;
    }
}
