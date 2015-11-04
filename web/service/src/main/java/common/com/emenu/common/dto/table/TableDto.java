package com.emenu.common.dto.table;

import com.emenu.common.entity.table.Table;

/**
 * TableDto
 *
 * @author: yangch
 * @time: 2015/10/23 10:10
 */
public class TableDto {
    private Table table;
    private String areaId;
    private String areaName;
    private String state;

    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public String getAreaId() {
        return areaId;
    }

    public void setAreaId(String areaId) {
        this.areaId = areaId;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
