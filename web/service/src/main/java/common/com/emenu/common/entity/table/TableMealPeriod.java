package com.emenu.common.entity.table;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * 餐台-餐段实体类
 *
 * @author: yangch
 * @time: 2015/11/9 13:51
 */
@Entity
@javax.persistence.Table(name = "t_table_meal_period")
public class TableMealPeriod extends AbstractEntity {
    //主键
    @Id
    private Integer id;

    //餐台ID
    @Column(name = "table_id")
    private Integer tableId;

    //餐段ID
    @Column(name = "meal_period_id")
    private Integer mealPeriodId;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(Integer tableId) {
        this.tableId = tableId;
    }

    public Integer getMealPeriodId() {
        return mealPeriodId;
    }

    public void setMealPeriodId(Integer mealPeriodId) {
        this.mealPeriodId = mealPeriodId;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}
