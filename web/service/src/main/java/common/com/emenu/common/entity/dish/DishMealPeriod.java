package com.emenu.common.entity.dish;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * 菜品-餐段实体
 *
 * @author: zhangteng
 * @time: 2015/11/24 14:10
 **/
@Entity
@Table(name = "t_dish_meal_period")
public class DishMealPeriod extends AbstractEntity {

    // 主键
    @Id
    private Integer id;

    // 菜品ID
    @Column(name = "dish_id")
    private Integer dishId;

    // 餐段ID
    @Column(name = "meal_period_id")
    private Integer mealPeriodId;

    // 创建时间
    @Column(name = "created_time")
    private Date createdTime;

    // 最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
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
