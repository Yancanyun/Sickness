package com.emenu.common.enums.storage;

import java.util.HashMap;
import java.util.Map;

/**
 * StorageReportStatusEnum
 * 单据结算状态
 * @author dujuan
 * @date 2015/11/15
 */
public enum StorageReportStatusEnum {
    // 结算状态
    NotSettled(0 , "未结算"),
    Settled(1 , "已结算"),

    // 审核状态
    NotAudited(0,"未审核"),
    PassAudited(1,"审核通过"),
    NotPassAudited(2,"审核未通过");


    private Integer id;
    private String name;

    StorageReportStatusEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    private static Map<Integer, StorageReportStatusEnum> map = new HashMap<Integer, StorageReportStatusEnum>();

    static {
        for(StorageReportStatusEnum reportStatusEnum : StorageReportStatusEnum.values()) {
            map.put(reportStatusEnum.getId(), reportStatusEnum);
        }
    }

    public static StorageReportStatusEnum valueOf(int id) {
        return valueOf(id, null);
    }

    public static StorageReportStatusEnum valueOf(int id, StorageReportStatusEnum defaultValue) {
        StorageReportStatusEnum reportStatusEnum = map.get(id);
        return reportStatusEnum == null ? defaultValue : reportStatusEnum;
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
