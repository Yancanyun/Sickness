package com.emenu.common.enums.printer;

import java.util.HashMap;
import java.util.Map;

/**
 * PrinterDishEnum
 * 打印机与菜品关联表类型
 * @author dujuan
 * @date 2015/11/14
 */
public enum PrinterDishEnum {

    TagPrinter(0, "菜品分类"),
    DishPrinter(1, "具体菜品");

    private Integer id;
    private String name;

    PrinterDishEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    private static Map<Integer, PrinterDishEnum> map = new HashMap<Integer, PrinterDishEnum>();

    static {
        for(PrinterDishEnum printerDishEnum : PrinterDishEnum.values()) {
            map.put(printerDishEnum.getId(), printerDishEnum);
        }
    }

    public static PrinterDishEnum valueOf(int id) {
        return valueOf(id, null);
    }

    public static PrinterDishEnum valueOf(int id, PrinterDishEnum defaultValue) {
        PrinterDishEnum printerDishEnum = map.get(id);
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
