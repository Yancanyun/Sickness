package com.emenu.common.enums.printer;

import java.util.HashMap;
import java.util.Map;

/**
 * PrinterTypeEnums
 *
 * @author Wang Liming
 * @date 2015/11/12 17:08
 */
public enum PrinterTypeEnums {

    BarPrinter(1, "吧台打印机"),
    BackDishPrinter(2, "退菜打印机"),
    DishTagPrinter(3, "分类打印机");

    private Integer id;
    private String description;


    PrinterTypeEnums(Integer id, String description){
        this.id = id;
        this.description = description;
    }

    private static Map<Integer, PrinterTypeEnums> map = new HashMap<Integer, PrinterTypeEnums>();

    static {
        for (PrinterTypeEnums enums : PrinterTypeEnums.values()){
            map.put(enums.getId(), enums);
        }
    }


    public static PrinterTypeEnums valueOf(int id){
        return valueOf(id, null);
    }

    public static PrinterTypeEnums valueOf(int id, PrinterTypeEnums defaultValue){
        PrinterTypeEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
