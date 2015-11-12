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
    DishAndGoods(1,"菜品、商品分类"),
    Storage(2,"库存分类"),
    Dishes(3 , "菜品"),
    Goods(4, "商品"),
    Drinks(5, "酒水"),
    Package(6 , "套餐"),
    Others(7, "其他");

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