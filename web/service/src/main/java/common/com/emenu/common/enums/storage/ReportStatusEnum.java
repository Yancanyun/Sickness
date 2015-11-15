package com.emenu.common.enums.storage;

import java.util.HashMap;
import java.util.Map;

/**
 * ReportStatusEnum
 * 单据结算状态
 * @author dujuan
 * @date 2015/11/15
 */
public enum ReportStatusEnum {
    NotSettled(0 , "未结算"),
    Settled(1 , "已结算");

    private Integer id;
    private String name;

    ReportStatusEnum(Integer id, String name){
        this.id = id;
        this.name = name;
    }

    private static Map<Integer, ReportStatusEnum> map = new HashMap<Integer, ReportStatusEnum>();

    static {
        for(ReportStatusEnum reportStatusEnum : ReportStatusEnum.values()) {
            map.put(reportStatusEnum.getId(), reportStatusEnum);
        }
    }

    public static ReportStatusEnum valueOf(int id) {
        return valueOf(id, null);
    }

    public static ReportStatusEnum valueOf(int id, ReportStatusEnum defaultValue) {
        ReportStatusEnum reportStatusEnum = map.get(id);
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
