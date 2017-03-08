package com.emenu.common.enums.stock;

import java.util.HashMap;
import java.util.Map;

/**
 * StockDocumentsTypeEnum
 * 库存单据类型枚举
 *
 * @author renhongshuai
 * @Time 2017/3/8 13:34.
 */
public enum StockDocumentsTypeEnum {

    StockInDocuments(1 , "入库单"),
    StockOutDocuments(2 , "领用单"),
    StockBackDocuments(3 , "回库单"),
    IncomeOnDocuments(4 , "盘盈单"),
    LossOnDocuments(5,"盘亏单");

    private Integer id;
    private String name;

    StockDocumentsTypeEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }
    private static Map<Integer, StockDocumentsTypeEnum> map = new HashMap<Integer, StockDocumentsTypeEnum>();
    static {
        for(StockDocumentsTypeEnum storageReportTypeEnum : StockDocumentsTypeEnum.values()) {
            map.put(storageReportTypeEnum.getId(), storageReportTypeEnum);
        }
    }

    public static StockDocumentsTypeEnum valueOf(int id) {
        return valueOf(id, null);
    }

    public static StockDocumentsTypeEnum valueOf(int id, StockDocumentsTypeEnum defaultValue) {
        StockDocumentsTypeEnum stockDocumentsTypeEnum = map.get(id);
        return stockDocumentsTypeEnum == null ? defaultValue : stockDocumentsTypeEnum;
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
