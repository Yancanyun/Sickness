package com.emenu.common.dto.order;

/**
 * OrderDishCache
 * 已点但仍未下单的单个菜品的缓存
 *
 * @author: yangch
 * @time: 2016/5/31 17:08
 **/
public class OrderDishCache {
    private Integer id;

    // 菜品ID
    private Integer dishId;

    // 菜品数量
    private Integer quantity;

    // 菜品口味ID
    private Integer tasteId;

    // 菜品上菜方式(1-即起, 2-叫起)
    private Integer serveType;

    // 菜品备注
    private String remark;

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

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getTasteId() {
        return tasteId;
    }

    public void setTasteId(Integer tasteId) {
        this.tasteId = tasteId;
    }

    public Integer getServeType() {
        return serveType;
    }

    public void setServeType(Integer serveType) {
        this.serveType = serveType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
