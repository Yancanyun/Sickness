package com.emenu.common.dto.vip;

import java.math.BigDecimal;

/**
 * 会员价dto
 *
 * @author chenyuting
 * @date 2015/11/11 10:49
 */
public class VipDishPriceDto {

    //会员价id
    private Integer id;

    //菜品id
    private Integer dishId;

    //菜品编号
    private String dishNumber;

    //助记码
    private String assistantCode;

    //商品名称
    private String dishName;

    //原价
    private BigDecimal price;

    //售价
    private BigDecimal salePrice;

    //会员价
    private BigDecimal vipDishPrice;

    //差价
    private BigDecimal priceDifference;


    //get、set方法


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public String getDishNumber() {
        return dishNumber;
    }

    public void setDishNumber(String dishNumber) {
        this.dishNumber = dishNumber;
    }

    public String getAssistantCode() {
        return assistantCode;
    }

    public void setAssistantCode(String assistantCode) {
        this.assistantCode = assistantCode;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
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