package com.emenu.common.enums.storage;

import java.util.HashMap;
import java.util.Map;

/**
 * StorageReportTypeEnum
 * 库存单据类型
 * @author dujuan
 * @date 2015/11/16
 */
public enum StorageReportTypeEnum {
    StockInReport(1 , "入库单"),
    StockOutReport(2 , "出库单"),
    IncomeOnReport(3 , "盘盈单"),
    LossOnReport(4 , "盘亏单");

    private Integer id;
    private String name;

    StorageReportTypeEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    private static Map<Integer, StorageReportTypeEnum> map = new HashMap<Integer, StorageReportTypeEnum>();

    static {
        for(StorageReportTypeEnum storageReportTypeEnum : StorageReportTypeEnum.values()) {
            map.put(storageReportTypeEnum.getId(), storageReportTypeEnum);
        }
    }

    public static StorageReportTypeEnum valueOf(int id) {
        return valueOf(id, null);
    }

    public static StorageReportTypeEnum valueOf(int id, StorageReportTypeEnum defaultValue) {
        StorageReportTypeEnum storageReportTypeEnum = map.get(id);
        return storageReportTypeEnum == null ? defaultValue : storageReportTypeEnum;
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
