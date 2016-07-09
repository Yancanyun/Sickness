package com.emenu.common.dto.order;

import com.emenu.common.entity.dish.DishImg;
import com.emenu.common.entity.dish.Taste;
import com.emenu.common.entity.dish.Unit;
import com.emenu.service.dish.DishService;

import java.math.BigDecimal;
import java.util.List;

/**
 * MyOrderDto
 * 顾客端我的订单
 *
 * @author: quanyibo
 * @time: 2016/6/2
 **/

public class MyOrderDto {

    //菜品缓存id
    private Integer orderDishCacheId;

    //菜品id
    private Integer dishId;

    //菜品名称
    private String name;

    //菜品小图
    private DishImg smallImg;

    //菜品小图路径
    private String imgPath;

    //菜品定价
    private BigDecimal price;

    //菜品售价
    private BigDecimal salePrice;

    //菜品单位
    private Unit unit;

    //菜品单位名称
    private String unitName;

    //菜品备注
    private String remark;

    //菜品数量,前端页面显示的时候要显示成整数
    private int count;

    // 菜品口味,菜品详情页选择的菜品口味只能选择一个
    private Taste taste;

    public Taste getTaste() {
        return taste;
    }

    public void setTaste(Taste taste) {
        this.taste = taste;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }

    public Integer getOrderDishCacheId() {
        return orderDishCacheId;
    }

    public void setOrderDishCacheId(Integer orderDishCacheId) {
        this.orderDishCacheId = orderDishCacheId;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    public BigDecimal getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(BigDecimal salePrice) {
        this.salePrice = salePrice;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public DishImg getSmallImg() {
        return smallImg;
    }

    public void setSmallImg(DishImg smallImg) {
        this.smallImg = smallImg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgPath() {
        return imgPath;
    }

    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }
}
