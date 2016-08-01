package com.emenu.common.enums.auto;

import java.util.HashMap;
import java.util.Map;

/**
 * AutoPrintStartStatusEnums
 * 智能排菜启用状态
 *
 * @author quanyibo
 * @date 2016/7/28
 */
public enum AutoPrintStartStatusEnums {

    IsNotStart(0, "智能排菜服务停用"),
    IsStart(1, "智能排菜服务启用"),
   ;

    private Integer id;
    private String name;

    AutoPrintStartStatusEnums(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    private static Map<Integer, AutoPrintStartStatusEnums> map = new HashMap<Integer, AutoPrintStartStatusEnums>();

    static {
        for(AutoPrintStartStatusEnums printerDishEnum : AutoPrintStartStatusEnums.values()) {
            map.put(printerDishEnum.getId(), printerDishEnum);
        }
    }

    public static AutoPrintStartStatusEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static AutoPrintStartStatusEnums valueOf(int id,AutoPrintStartStatusEnums defaultValue) {
        AutoPrintStartStatusEnums printerDishEnum = map.get(id);
        return printerDishEnum == null ? defaultValue : printerDishEnum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
