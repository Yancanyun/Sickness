package com.emenu.common.enums.printer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * PrinterModelEnums
 * 打印机型号
 *
 * @author Wang Liming
 * @date 2015/11/12 17:04
 */
public enum  PrinterModelEnums {

    Is80(1, "80"),
    Is58(2, "58");

    private Integer id;
    private String description;


    PrinterModelEnums(Integer id, String description){
        this.id = id;
        this.description = description;
    }

    private static Map<Integer, PrinterModelEnums> map = new HashMap<Integer, PrinterModelEnums>();

    static {
        for (PrinterModelEnums enums : PrinterModelEnums.values()){
            map.put(enums.getId(), enums);
        }
    }


    public static PrinterModelEnums valueOf(int id){
        return valueOf(id, null);
    }

    public static PrinterModelEnums valueOf(int id, PrinterModelEnums defaultValue){
        PrinterModelEnums enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public static List<PrinterModelEnums> getModelList(){
        List<PrinterModelEnums> modelList = new ArrayList<PrinterModelEnums>();
        for (PrinterModelEnums enums : PrinterModelEnums.values()){
            modelList.add(enums);
        }
        return modelList;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
