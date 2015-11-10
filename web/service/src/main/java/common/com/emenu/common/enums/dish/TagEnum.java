package com.emenu.common.enums.dish;

import java.util.HashMap;
import java.util.Map;

/**
 * TagEnum
 * 分类类型的枚举
 * @author dujuan
 * @date 2015/11/5
 */
public enum TagEnum {
    DISHES(1 , "菜品"),
    Storage(2 , "原配料")
    ;
    private Integer id;
    private String name;

    TagEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    private static Map<Integer, TagEnum> map = new HashMap<Integer, TagEnum>();

    static {
        for(TagEnum tagEnum : TagEnum.values()) {
            map.put(tagEnum.getId(), tagEnum);
        }
    }

    public static TagEnum valueOf(int id) {
        return valueOf(id, null);
    }

    public static TagEnum valueOf(int id, TagEnum defaultValue) {
        TagEnum tagEnum = map.get(id);
        return tagEnum == null ? defaultValue : tagEnum;
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
