package com.emenu.common.enums.stock;

import java.util.HashMap;
import java.util.Map;

/**
 * StockItemStatusEnum
 *
 * @author yuzhengfei
 * @date 2017/3/17 9:51
 */
public enum  StockItemStatusEnum {

    Nomals(1,"正常使用"),
    Delete(2,"已删除"),
            ;

    private Integer id;

    private String type;

    StockItemStatusEnum(Integer id,String type){
        this.id = id;
        this.type = type;
    }

    private static Map<Integer, StockItemStatusEnum> map = new HashMap<Integer, StockItemStatusEnum>();

    static {
        for (StockItemStatusEnum enums : StockItemStatusEnum.values()) {
            map.put(enums.getId(), enums);
        }
    }

    public static StockItemStatusEnum valueOf(int id) {
        return valueOf(id, null);
    }

    public static StockItemStatusEnum valueOf(int id, StockItemStatusEnum defaultValue) {
        StockItemStatusEnum enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getType() {
        return type;
    }
}
