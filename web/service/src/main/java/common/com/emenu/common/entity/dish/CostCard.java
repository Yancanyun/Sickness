package com.emenu.common.entity.dish;

import javax.persistence.*;
import java.math.BigDecimal;


/**
 * Costcard成本卡实体
 *
 * @author quanyibo
 * @date 2016/5/17
 */
@Entity
@Table(name="t_cost_card")
public class CostCard {

    //主键
    @Id
    private Integer id;

    //成本卡编号
    @Column(name = "cost_card_number")
    private String costCardNumber;

    //菜品id
    @Column(name = "dish_id")
    private Integer dishId;

    //主料成本
    @Column(name = "main_cost")
    private BigDecimal mainCost;

    //辅料成本
    @Column(name = "assist_cost")
    private BigDecimal assistCost;

    //调料成本
    @Column(name = "delicious_cost")
    private BigDecimal deliciousCost;

    //标准成本即总成本
    @Column(name = "standard_cost")
    private BigDecimal standardCost;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public String getCostCardNumber() {
        return costCardNumber;
    }

    public void setCostCardNumber(String costCardNumber) {
        this.costCardNumber = costCardNumber;
    }
}
