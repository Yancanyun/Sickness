package com.emenu.common.dto.table;

import com.emenu.common.entity.meal.MealPeriod;
import com.emenu.common.entity.table.Table;

import java.util.List;

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
    private String status;
    private List<MealPeriod> mealPeriodList;

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MealPeriod> getMealPeriodList() {
        return mealPeriodList;
    }

    public void setMealPeriodList(List<MealPeriod> mealPeriodList) {
        this.mealPeriodList = mealPeriodList;
    }
}
