package com.emenu.common.enums.vip;

import java.util.HashMap;
import java.util.Map;

/**
 * VipCardStatusEnums
 *
 * @author: yangch
 * @time: 2015/11/2 15:22
 */
public enum VipCardPermanentlyEffectiveEnums {
    False(0, "否"),
    True(1, "是");

    private Integer id;
    private String status;

    VipCardPermanentlyEffectiveEnums(Integer id, String type) {
        this.id = id;
        this.status = type;
    }

    private static Map<Integer, VipCardPermanentlyEffectiveEnums> map = new HashMap<Integer, VipCardPermanentlyEffectiveEnums>();

    static {
        for (VipCardPermanentlyEffectiveEnums enums : VipCardPermanentlyEffectiveEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static VipCardPermanentlyEffectiveEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static VipCardPermanentlyEffectiveEnums valueOf(int id, VipCardPermanentlyEffectiveEnums defaultValue) {
        VipCardPermanentlyEffectiveEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return status;
    }
}
