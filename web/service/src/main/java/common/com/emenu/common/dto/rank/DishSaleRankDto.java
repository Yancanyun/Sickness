package com.emenu.common.dto.rank;

import com.emenu.common.entity.order.OrderDish;

import java.math.BigDecimal;
import java.util.List;

/**
 * @Author guofengrui
 * @Date 2016/7/26.
 */
public class DishSaleRankDto {
    // 主键
    private Integer id;

    // 菜品(套餐)Id
    private Integer dishId;

    // 菜品(套餐)名称
    private String dishName;

    // 菜品大类名称
    private String tagName;

    // 菜品大类Id
    private Integer tagId;

    // 销售数量
    private Integer num;

    // 消费金额
    private BigDecimal consumeSum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String diahName) {
        this.dishName = diahName;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public BigDecimal getConsumeSum() {
        return consumeSum;
    }

    public void setConsumeSum(BigDecimal consumeSum) {
        this.consumeSum = consumeSum;
    }

    public Integer getDishId() {
        return dishId;
    }

    public void setDishId(Integer dishId) {
        this.dishId = dishId;
    }
}
