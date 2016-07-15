package com.emenu.common.enums.dish;

import java.util.HashMap;
import java.util.Map;

/**
 * 套餐状态枚举
 *
 * @author: quanyibo
 * @time: 2016/7/14
 */
public enum PackageStatusEnums {

    IsNotPackage(0, "非套餐"),
    IsPackage(1, "套餐"),
    ;

    private Integer id;
    private String status;

    PackageStatusEnums(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    private static Map<Integer, PackageStatusEnums> map = new HashMap<Integer, PackageStatusEnums>();

    static {
        for (PackageStatusEnums enums : PackageStatusEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static PackageStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static PackageStatusEnums valueOf(int id, PackageStatusEnums defaultValue) {
        PackageStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}
