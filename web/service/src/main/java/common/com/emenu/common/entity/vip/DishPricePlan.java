package com.emenu.common.entity.vip;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 会员价管理方案
 *
 * @author chenyuting
 * @date 2015/11/10 18:42
 */
@Entity
@Table(name = "t_vip_dish_price_plan")
public class DishPricePlan extends AbstractEntity {

    //会员价管理方案id
    @Id
    private Integer id;

    //方案名称
    @Column(name = "name")
    private String name;

    // 方案描述
    private String description;


    // set、get方法
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}