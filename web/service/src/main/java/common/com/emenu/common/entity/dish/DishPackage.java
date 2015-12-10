package com.emenu.common.entity.dish;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Id;
import java.util.Date;

/**
 * DishPackage
 * 套餐
 * @author dujuan
 * @date 2015/12/10
 */
public class DishPackage  extends AbstractEntity{

    private static final long serialVersionUID = 7474697007199812532L;
    //主键ID
    @Id
    private Integer id;

    //菜品ID
    @Column(name = "dish_id")
    private Integer dishId;

    //菜品数量
    @Column(name = "dish_quantity")
    private Integer dishQuantity;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //最后修改时间
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

    public Integer getDishQuantity() {
        return dishQuantity;
    }

    public void setDishQuantity(Integer dishQuantity) {
        this.dishQuantity = dishQuantity;
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
