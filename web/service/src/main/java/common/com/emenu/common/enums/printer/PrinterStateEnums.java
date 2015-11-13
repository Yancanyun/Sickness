package com.emenu.common.enums.printer;

import java.util.HashMap;
import java.util.Map;

/**
 * PrinterStateEnums
 *
 * @author Wang Liming
 * @date 2015/11/12 17:37
 */
public enum PrinterStateEnums {

    Disabled(0, "未启用"),
    Enabled(1, "启用");

    private Integer id;
    private String state;

    PrinterStateEnums(int id, String state) {
        this.id = id;
        this.state = state;
    }

    private static Map<Integer, PrinterStateEnums> map = new HashMap<Integer, PrinterStateEnums>();

    static {
        for (PrinterStateEnums enums : PrinterStateEnums.values()){
            map.put(enums.getId(), enums);
        }
    }


    public static PrinterStateEnums valueOf(int id){
        return valueOf(id, null);
    }

    public static PrinterStateEnums valueOf(int id, PrinterStateEnums defaultValue){
        PrinterStateEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getState() {
        return state;
    }
}
