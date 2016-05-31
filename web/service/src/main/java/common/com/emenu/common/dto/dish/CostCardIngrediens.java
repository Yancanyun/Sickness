package com.emenu.common.dto.dish;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * CostCardIngrediens
 *
 * @author xubaorong
 * @date 2016/5/28.
 */
public class CostCardIngrediens{
    private String ingredientId;
    private String ingredientName;
    private String unit;
    private String itemType;
    private String otherCount;
    private String netCount;
    private String isAutoOut;

    public String getIsAutoOut() {
        return isAutoOut;
    }

    public void setIsAutoOut(String isAutoOut) {
        this.isAutoOut = isAutoOut;
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getOtherCount() {
        return otherCount;
    }

    public void setOtherCount(String otherCount) {
        this.otherCount = otherCount;
    }

    public String getNetCount() {
        return netCount;
    }

    public void setNetCount(String netCount) {
        this.netCount = netCount;
    }
}
