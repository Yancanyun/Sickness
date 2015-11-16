package com.emenu.common.enums.storage;

/**
 * StorageItemStatusEnums
 *
 * @author: zhangteng
 * @time: 2015/11/16 9:03
 **/
public enum StorageItemStatusEnums {

    Using(1,  "正常使用"),
    Deleted(2, "删除")
    ;

    private Integer id;

    private String description;

    StorageItemStatusEnums(Integer id, String description) {
        this.id = id;
        this.description = description;
    }

    public Integer getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }
}
