package com.emenu.common.dto.order;

import com.pandawork.core.common.entity.AbstractEntity;

/**
 * 退菜dto
 *
 * @author chenyuting
 * @date 2016/7/20 15:43
 */

public class BackDishDto {

    // 订单菜品id
    private Integer orderDishId;

    private String dishName;

    private String assistantCode;

    private String unitName;

    public Integer getOrderDishId() {
        return orderDishId;
    }

    public void setOrderDishId(Integer orderDishId) {
        this.orderDishId = orderDishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getAssistantCode() {
        return assistantCode;
    }

    public void setAssistantCode(String assistantCode) {
        this.assistantCode = assistantCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }
}