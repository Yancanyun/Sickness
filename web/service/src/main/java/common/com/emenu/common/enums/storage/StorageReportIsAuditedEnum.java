package com.emenu.common.enums.storage;

import java.util.HashMap;
import java.util.Map;

/**
 * StorageReportIsAuditedEnum
 *
 * @author xiaozl
 * @date: 2016/6/6
 */
public enum StorageReportIsAuditedEnum {

    NotAudited(0 , "未审核"),
    Passed(1 , "已通过"),
    NotPassed(2,"未通过");

    private Integer id;
    private String name;

    StorageReportIsAuditedEnum(Integer id, String name){
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
