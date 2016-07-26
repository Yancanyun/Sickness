package com.emenu.common.dto.rank;

import com.emenu.common.entity.order.OrderDish;

import java.util.List;

/**
 * @Author guofengrui
 * @Date 2016/7/26.
 */
public class DishSaleRankDto {
    // 主键
    private Integer id;

    // 所消费菜品集合
    private List<OrderDish> orderDishList;

    // 菜品名称
    private String diahName;

    // 菜品大类名称
    private String tagName;

    // 菜品大类Id
    private Integer tagId;

    // 销售数量
    private Integer num;

    // 消费金额
    private Integer consumeSum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<OrderDish> getOrderDishList() {
        return orderDishList;
    }

    public void setOrderDishList(List<OrderDish> orderDishList) {
        this.orderDishList = orderDishList;
    }

    public String getDiahName() {
        return diahName;
    }

    public void setDiahName(String diahName) {
        this.diahName = diahName;
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

    public Integer getConsumeSum() {
        return consumeSum;
    }

    public void setConsumeSum(Integer consumeSum) {
        this.consumeSum = consumeSum;
    }
}
