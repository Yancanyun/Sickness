package com.emenu.common.enums.dish;

import java.util.HashMap;
import java.util.Map;

/**
 * AutoOutEnum
 *
 * @author xubaorong
 * @date 2016/5/17.
 */
public enum AutoOutEnums {
    IsAutoOut(1,"自动出库"),
    NotAutoOut(0,"不自动出库")
    ;
    private Integer id;
    private String desc;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    AutoOutEnums(Integer id,String desc){
        this.id = id;
        this.desc = desc;
    }

    private static Map<Integer,AutoOutEnums> map = new HashMap<Integer, AutoOutEnums>();
    static {
        for(AutoOutEnums autoOutEnums:AutoOutEnums.values()){
            map.put(autoOutEnums.getId(),autoOutEnums);
        }
    }
    public static AutoOutEnums valueOf(int id){
        return valueOf(id,null);
    }
    public static AutoOutEnums valueOf(int id,AutoOutEnums defaultValue){
        AutoOutEnums autoOutEnums = map.get(id);
        return autoOutEnums == null? defaultValue:autoOutEnums;
    }
}
