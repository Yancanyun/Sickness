package com.emenu.common.enums.dish;

import com.lowagie.text.pdf.AcroFields;

import java.util.HashMap;
import java.util.Map;

/**
 * ItemTypeEnums
 *
 * @author xubaorong
 * @date 2016/5/17.
 */
public enum ItemTypeEnums {
    MainIngredient(0,"主料"),
    AssistIngredient(1,"辅料"),
    DiliciousIngredient(2,"调料")
    ;
    private Integer id;
    private String type;
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    ItemTypeEnums(int id,String type){
        this.id = id;
        this.type = type;
    }

    private static Map<Integer,ItemTypeEnums> map = new HashMap<Integer, ItemTypeEnums>();
    static {
        for(ItemTypeEnums itemTypeEnums:ItemTypeEnums.values()){
            map.put(itemTypeEnums.getId(),itemTypeEnums);
        }
    }

    public static ItemTypeEnums valueOf(int id){
        return valueOf(id,null);
    }
    public static ItemTypeEnums valueOf(int id,ItemTypeEnums defaultValue){
        ItemTypeEnums itemTypeEnums = map.get(id);
        return itemTypeEnums == null? defaultValue:itemTypeEnums;
    }
}
