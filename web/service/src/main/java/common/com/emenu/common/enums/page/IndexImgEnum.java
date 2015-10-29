package com.emenu.common.enums.page;

import java.util.HashMap;
import java.util.Map;

/**
 * IndexImgEnum
 *
 * @author Wang LiMing
 * @date 2015/10/23 16:20
 */
public enum IndexImgEnum {

    Using(1,"正在使用"),
    UnUsing(0,"未使用");

    private Integer id;
    private String description;


    IndexImgEnum(Integer id, String description){
        this.id = id;
        this.description = description;
    }

    private static Map<Integer, IndexImgEnum> map = new HashMap<Integer, IndexImgEnum>();

    static {
        for (IndexImgEnum enums : IndexImgEnum.values()){
            map.put(enums.getId(), enums);
        }
    }


    public static IndexImgEnum valueOf(int id){
        return valueOf(id, null);
    }

    public static IndexImgEnum valueOf(int id, IndexImgEnum defaultValue){
        IndexImgEnum enums = map.get(id);
        return enums == null ? defaultValue : enums;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
