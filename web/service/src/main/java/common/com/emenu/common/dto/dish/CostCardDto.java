package com.emenu.common.dto.dish;

import com.emenu.common.entity.dish.CostCardItem;
import com.pandawork.core.common.util.Assert;

import java.math.BigDecimal;
import java.util.List;

/**
 * 成本卡Dto
 *
 * @author: zhangteng
 * @time: 2015/12/15 10:57
 **/
public class CostCardDto {

    private Integer id;

    // 菜品ID
    private Integer dishId;

    // 菜品编号
    private String dishNumber;

    // 菜品名称
    private String dishName;

    // 菜品单位
    private String dishUnitName;

    // 菜品售价
    private BigDecimal dishSalePrice;

    // 标准成本
    private BigDecimal standardCost;

    // 调料成本
    private BigDecimal condimentCost;

    // 物品列表
    private List<CostCardItemDto> itemList;

    // 库存成本合计
    private BigDecimal totalStockCost;

    // 毛利率
    private BigDecimal grossProfitRatio;

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

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getDishUnitName() {
        return dishUnitName;
    }

    public void setDishUnitName(String dishUnitName) {
        this.dishUnitName = dishUnitName;
    }

    public BigDecimal getDishSalePrice() {
        return dishSalePrice;
    }

    public void setDishSalePrice(BigDecimal dishSalePrice) {
        this.dishSalePrice = dishSalePrice;
    }

    public BigDecimal getStandardCost() {
        return standardCost;
    }

    public void setStandardCost(BigDecimal standardCost) {
        this.standardCost = standardCost;
    }

    public BigDecimal getCondimentCost() {
        return condimentCost;
    }

    public void setCondimentCost(BigDecimal condimentCost) {
        this.condimentCost = condimentCost;
    }

    public List<CostCardItemDto> getItemList() {
        return itemList;
    }

    public void setItemList(List<CostCardItemDto> itemList) {
        this.itemList = itemList;
    }

    public BigDecimal getTotalStockCost() {
        return totalStockCost;
    }

    public void setTotalStockCost(BigDecimal totalStockCost) {
        this.totalStockCost = totalStockCost;
    }

    public BigDecimal getGrossProfitRatio() {
        return grossProfitRatio;
    }

    public void setGrossProfitRatio(BigDecimal grossProfitRatio) {
        this.grossProfitRatio = grossProfitRatio;
    }

    public void calcTotalCostAndRatio() {
        this.totalStockCost = new BigDecimal("0.00");
        this.grossProfitRatio = new BigDecimal("100.0");
        if (Assert.isNotEmpty(this.itemList)) {
            for (CostCardItemDto itemDto : itemList) {
                this.totalStockCost = this.totalStockCost.add(itemDto.getRawMaterialQuantity());
            }

            // 毛利率=(售价-库存成本合计)/售价
            this.grossProfitRatio = this.dishSalePrice.subtract(this.getTotalStockCost())
                                        .divide(this.dishSalePrice, 10, BigDecimal.ROUND_DOWN);
            this.grossProfitRatio = this.grossProfitRatio.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }
}
