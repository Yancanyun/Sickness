package com.emenu.common.enums.dish;

import java.util.HashMap;
import java.util.Map;

/**
 * 菜品状态枚举
 *
 * @author: zhangteng
 * @time: 2015/11/17 16:50
 **/
public enum DishStatusEnums {

    UnSale(0, "停售"),
    OnSale(1, "销售中"),
    Lack(2, "标缺"),
    Deleted(3, "已删除")
    ;

    private Integer id;

    private String status;

    DishStatusEnums(Integer id, String status) {
        this.id = id;
        this.status = status;
    }

    private static Map<Integer, DishStatusEnums> map = new HashMap<Integer, DishStatusEnums>();

    static {
        for (DishStatusEnums enums : DishStatusEnums.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static DishStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static DishStatusEnums valueOf(int id, DishStatusEnums defaultValue) {
        DishStatusEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getStatus() {
        return status;
    }
}