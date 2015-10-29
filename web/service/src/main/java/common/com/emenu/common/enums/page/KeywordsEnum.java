package com.emenu.common.enums.page;

import java.util.HashMap;
import java.util.Map;

/**
 * KeywordsEnum
 *
 * @author Wang LiMing
 * @date 2015/10/23 11:13
 */
public enum KeywordsEnum {

    Ordering(0,"点餐平台"),
    WaiterSystem(1,"服务员系统");

    private Integer id;
    private String type;

    KeywordsEnum(Integer id, String type){
        this.id = id;
        this.type = type;
    }

    private static Map<Integer, KeywordsEnum> map = new HashMap<Integer, KeywordsEnum>();

    static {
        for (KeywordsEnum enums : KeywordsEnum.values()){
            map.put(enums.getId(), enums);
        }
    }

    public static KeywordsEnum valueOf(int id){
        return valueOf(id, null);
    }

    public static KeywordsEnum valueOf(int id, KeywordsEnum defaultValue){
        KeywordsEnum enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
