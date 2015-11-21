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
@Table(name = "t_vip_dish_price")
public class VipDishPrice extends AbstractEntity {

    //会员价id
    @Id
    private Integer id;

    //会员价方案id
    @Column(name = "vip_dish_price_plan_id")
    private Integer vipDishPricePlanId;

    //菜品id
    @Column(name = "dish_id")
    private Integer dishId;

    // 会员价
    @Column(name = "vip_dish_price")
    private BigDecimal vipDishPrice;

    //set、get方法
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getVipDishPricePlanId() {
        return vipDishPricePlanId;
    }

    public void setVipDishPricePlanId(Integer vipDishPricePlanId) {
        this.vipDishPricePlanId = vipDishPricePlanId;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public BigDecimal getVipDishPrice() {
        return vipDishPrice;
    }

    public void setVipDishPrice(BigDecimal vipDishPrice) {
        this.vipDishPrice = vipDishPrice;
    }

}