package com.emenu.common.entity.vip;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * 会员价
 *
 * @author chenyuting
 * @date 2015/11/11 9:19
 */
@Entity
@Table(name = "t_vip_dish_price_plan")
public class DishPrice extends AbstractEntity {

    //会员价id
    @Id
    private Integer id;

    //菜品id
    @Column(name = "dish_id")
    private Integer dishId;

    //会员价方案id
    @Column(name = "plan_id")
    private Integer planId;

    // 会员价
    @Column(name = "vip_price")
    private BigDecimal vipPrice;

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

    public Integer getPlanId() {
        return planId;
    }

    public void setPlanId(Integer planId) {
        this.planId = planId;
    }

    public BigDecimal getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(BigDecimal vipPrice) {
        this.vipPrice = vipPrice;
    }
}