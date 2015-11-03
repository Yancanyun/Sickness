package com.emenu.common.dto.table;

import com.emenu.common.entity.table.Area;
import com.emenu.common.entity.table.Table;

import java.util.List;

/**
 * AreaDto
 *
 * @author: yangch
 * @time: 2015/10/23 9:35
 */
public class     AreaDto {
    private Area area;
    private List<Table> tableList;

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public List<Table> getTableList() {
        return tableList;
    }

    public void setTableList(List<Table> tableList) {
        this.tableList = tableList;
    }
}
