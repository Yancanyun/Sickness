package com.emenu.common.enums.printer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PrinterBrandEnums
 * 打印机品牌
 *
 * @author Wang Liming
 * @date 2015/11/12 16:07
 */
public enum  PrinterBrandEnums {

    GPrinter(1, "佳博(GPrinter)"),
    EpsonOrBeiyang(2, "爱普生(Epson)/北洋(Beiyang)"),
    HardLink(3, "固网"),
    Other(4, "其他");

    private Integer id;
    private String description;


    PrinterBrandEnums(Integer id, String description){
        this.id = id;
        this.description = description;
    }

    private static Map<Integer, PrinterBrandEnums> map = new HashMap<Integer, PrinterBrandEnums>();

    static {
        for (PrinterBrandEnums enums : PrinterBrandEnums.values()){
            map.put(enums.getId(), enums);
        }
    }


    public static PrinterBrandEnums valueOf(int id){
        return valueOf(id, null);
    }

    public static PrinterBrandEnums valueOf(int id, PrinterBrandEnums defaultValue){
        PrinterBrandEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public static List<PrinterBrandEnums> getBrandList(){
        List<PrinterBrandEnums> brandList = new ArrayList<PrinterBrandEnums>();
        for (PrinterBrandEnums enums : PrinterBrandEnums.values()){
            brandList.add(enums);
        }
        return brandList;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
