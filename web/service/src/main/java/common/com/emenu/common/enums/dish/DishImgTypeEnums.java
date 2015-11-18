package com.emenu.common.enums.dish;

import java.util.HashMap;
import java.util.Map;

/**
 * 菜品图片类型枚举
 *
 * @author: zhangteng
 * @time: 2015/11/16 10:29
 **/
public enum DishImgTypeEnums {

    UnSale(0, "停售"),
    OnSale(1, "销售中"),
    Lack(2, "标缺"),
    Deleted(3, "已删除")
    ;

    private Integer id;

    private String type;

    DishImgTypeEnums(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    private static Map<Integer, DishImgTypeEnums> map = new HashMap<Integer, DishImgTypeEnums>();
    static {
        for (DishImgTypeEnums typeEnums : DishImgTypeEnums.values()) {
            map.put(typeEnums.getId(), typeEnums);
        }
    }

    public static DishImgTypeEnums valueOf(int id) {
        return valueOf(id, null);
    }

    public static DishImgTypeEnums valueOf(int id, DishImgTypeEnums defaultValue) {
        DishImgTypeEnums typeEnums = map.get(id);
        return typeEnums == null ? defaultValue : typeEnums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
