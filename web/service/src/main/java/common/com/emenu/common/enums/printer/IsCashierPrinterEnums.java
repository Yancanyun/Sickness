package com.emenu.common.enums.printer;

import java.util.HashMap;
import java.util.Map;

/**
 * IsCashierPrinterEnums
 *
 * @author Wang Liming
 * @date 2015/11/12 17:40
 */
public enum IsCashierPrinterEnums {

    False(0, "否"),
    True(1, "是");

    private Integer id;
    private String description;


    IsCashierPrinterEnums(Integer id, String description){
        this.id = id;
        this.description = description;
    }

    private static Map<Integer, IsCashierPrinterEnums> map = new HashMap<Integer, IsCashierPrinterEnums>();

    static {
        for (IsCashierPrinterEnums enums : IsCashierPrinterEnums.values()){
            map.put(enums.getId(), enums);
        }
    }


    public static IsCashierPrinterEnums valueOf(int id){
        return valueOf(id, null);
    }

    public static IsCashierPrinterEnums valueOf(int id, IsCashierPrinterEnums defaultValue){
        IsCashierPrinterEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
