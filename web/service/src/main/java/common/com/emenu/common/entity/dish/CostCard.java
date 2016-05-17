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
 * @author: quanyibo
 * @time: 2016/5/16 8:46
 **/
@Entity
@Table(name = "t_cost_card")
public class CostCard extends AbstractEntity {

    //成本卡自增主键
    @Id
    private Integer id;

    //成本卡编号
    @Column(name = "cost_card_number")
    private  String costCardNumber;

    //成本卡对应菜品id
    @Column(name = "dish_id")
    private  Integer dishId;

    //主料成本
    @Column(name = "main_cost")
    private BigDecimal mainCost;

    //辅料成本
    @Column(name = "assist_cost")
    private BigDecimal assistCost;

    //调料成本
    @Column(name = "delicious_cost")
    private BigDecimal deliciousCost;

    //标准成本
    @Column(name = "standard_cost")
    private  BigDecimal standardCost;

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

    public String getCostCardNumber() {
        return costCardNumber;
    }

    public void setCostCardNumber(String costCardNumber) {
        this.costCardNumber = costCardNumber;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public BigDecimal getAssistCost() {
        return assistCost;
    }

    public void setAssistCost(BigDecimal assistCost) {
        this.assistCost = assistCost;
    }

    public BigDecimal getMainCost() {
        return mainCost;
    }

    public void setMainCost(BigDecimal mainCost) {
        this.mainCost = mainCost;
    }

    public BigDecimal getStandardCost() {
        return standardCost;
    }

    public void setStandardCost(BigDecimal standardCost) {
        this.standardCost = standardCost;
    }

    public BigDecimal getDeliciousCost() {
        return deliciousCost;
    }

    public void setDeliciousCost(BigDecimal deliciousCost) {
        this.deliciousCost = deliciousCost;
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
