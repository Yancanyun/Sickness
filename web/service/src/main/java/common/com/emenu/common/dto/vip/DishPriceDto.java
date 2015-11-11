package com.emenu.common.dto.vip;

import java.math.BigDecimal;

/**
 * @author chenyuting
 * @date 2015/11/11 10:49
 */
public class DishPriceDto {

    //商品编号
    private String number;

    //商品名称
    private String dishName;

    //原价
    private BigDecimal dishPrice;

    //会员价
    private BigDecimal vipDishPrice;

    //差价
    private BigDecimal priceDifference;


    //get、set方法
    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public BigDecimal getDishPrice() {
        return dishPrice;
    }

    public void setDishPrice(BigDecimal dishPrice) {
        this.dishPrice = dishPrice;
    }

    public BigDecimal getVipDishPrice() {
        return vipDishPrice;
    }

    public void setVipDishPrice(BigDecimal vipDishPrice) {
        this.vipDishPrice = vipDishPrice;
    }

    public BigDecimal getPriceDifference() {
        return priceDifference;
    }

    public void setPriceDifference(BigDecimal priceDifference) {
        this.priceDifference = priceDifference;
    }
}