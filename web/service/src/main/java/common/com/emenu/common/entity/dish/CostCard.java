package com.emenu.common.entity.dish;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 成本卡实体
 *
 * @author: zhangteng
 * @time: 2015/12/14 18:08
 **/
@Entity
@Table(name = "t_cost_card")
public class CostCard extends AbstractEntity {

    // 主键
    @Id
    private Integer id;

    // 菜品ID
    @Column(name = "dish_id")
    private Integer dish_id;

    // 标准成本
    @Column(name = "standard_cost")
    private BigDecimal standardCost;

    // 调料成本
    @Column(name = "condiment_cost")
    private BigDecimal condimentCost;

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

    public Integer getDish_id() {
        return dish_id;
    }

    public void setDish_id(Integer dish_id) {
        this.dish_id = dish_id;
    }

    public BigDecimal getStandardCost() {
        return standardCost;
    }

    public void setStandardCost(BigDecimal standardCost) {
        this.standardCost = standardCost;
    }

    public BigDecimal getCondimentCost() {
        return condimentCost;
    }

    public void setCondimentCost(BigDecimal condimentCost) {
        this.condimentCost = condimentCost;
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
