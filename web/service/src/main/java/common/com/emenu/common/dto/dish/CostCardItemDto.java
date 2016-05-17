package com.emenu.common.dto.dish;

import java.math.BigDecimal;

/**
 * CostCardItemDto
 *
 * @author xubaorong
 * @date 2016/5/16.
 */
public class CostCardItemDto {
    //主键
    private Integer id;

    //原料名称
    private String ingredientName;

    //原料id
    private Integer ingredientId;

    //原料单位
    private String costCardUnit;

    //原料单价
    private BigDecimal averagePrice;

    //净料率
    private BigDecimal netRate;

    //成本卡id
    private Integer costCardId;

    //原料类别,0为主料，1为辅料，2为调料
    private Integer itemType;

    //净料用量
    private BigDecimal netCount;

    //毛料用量
    private BigDecimal totalCount;

    //班结时是否自动出库,1为是，0为否
    private Integer isAutoOut;

    /*******getter and setter**********************/
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getCostCardUnit() {
        return costCardUnit;
    }

    public void setCostCardUnit(String costCardUnit) {
        this.costCardUnit = costCardUnit;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
    }

    public BigDecimal getNetRate() {
        return netRate;
    }

    public void setNetRate(BigDecimal netRate) {
        this.netRate = netRate;
    }

    public Integer getCostCardId() {
        return costCardId;
    }

    public void setCostCardId(Integer costCardId) {
        this.costCardId = costCardId;
    }

    public Integer getItemType() {
        return itemType;
    }

    public void setItemType(Integer itemType) {
        this.itemType = itemType;
    }

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public BigDecimal getNetCount() {
        return netCount;
    }

    public void setNetCount(BigDecimal netCount) {
        this.netCount = netCount;
    }

    public BigDecimal getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(BigDecimal totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getIsAutoOut() {
        return isAutoOut;
    }

    public void setIsAutoOut(Integer isAutoOut) {
        this.isAutoOut = isAutoOut;
    }

    public void  setResultNetRate(){
        if(this.netCount!=null && this.totalCount!=null&&!this.totalCount.equals(BigDecimal.ZERO)) {
            this.netRate =  netCount.divide(totalCount,10, BigDecimal.ROUND_DOWN);
            this.netRate = this.netRate.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }
}
