package com.emenu.common.dto.storage;

import java.math.BigDecimal;

/**
 * StorageCheckDto
 * 库存盘点
 * @author dujuan
 * @date 2015/11/16
 */
public class StorageCheckDto {

    //名称
    private String ingredientName;

    // 原配料编号
    private String ingredientNumber;

    // 分类名称
    private String tagName;

    // 订货单位
    private String orderUnitName;

    // 库存单位
    private String storageUnitName;

    // 存放点名称
    private String depotName;

    //期初数量
     private BigDecimal beginQuantity;

    //入库数量
    private BigDecimal stockInQuantity;

    //出库数量
    private BigDecimal stockOutQuantity;

    //盈亏数量
    private BigDecimal incomeLossQuantity;

    //结存数量
    private BigDecimal totalQuantity;

    //结存均价
    private BigDecimal totalAveragePrice;

    //结存金额
    private BigDecimal totalMoney;

    //库存报警值上限
    private BigDecimal maxStorageQuantity;

    //库存报警值下限
    private BigDecimal minStorageQuantity;

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getIngredientNumber() {
        return ingredientNumber;
    }

    public void setIngredientNumber(String ingredientNumber) {
        this.ingredientNumber = ingredientNumber;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getOrderUnitName() {
        return orderUnitName;
    }

    public void setOrderUnitName(String orderUnitName) {
        this.orderUnitName = orderUnitName;
    }

    public String getStorageUnitName() {
        return storageUnitName;
    }

    public void setStorageUnitName(String storageUnitName) {
        this.storageUnitName = storageUnitName;
    }

    public String getDepotName() {
        return depotName;
    }

    public void setDepotName(String depotName) {
        this.depotName = depotName;
    }

    public BigDecimal getBeginQuantity() {
        return beginQuantity;
    }

    public void setBeginQuantity(BigDecimal beginQuantity) {
        this.beginQuantity = beginQuantity;
    }

    public BigDecimal getStockInQuantity() {
        return stockInQuantity;
    }

    public void setStockInQuantity(BigDecimal stockInQuantity) {
        this.stockInQuantity = stockInQuantity;
    }

    public BigDecimal getStockOutQuantity() {
        return stockOutQuantity;
    }

    public void setStockOutQuantity(BigDecimal stockOutQuantity) {
        this.stockOutQuantity = stockOutQuantity;
    }

    public BigDecimal getIncomeLossQuantity() {
        return incomeLossQuantity;
    }

    public void setIncomeLossQuantity(BigDecimal incomeLossQuantity) {
        this.incomeLossQuantity = incomeLossQuantity;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public BigDecimal getTotalAveragePrice() {
        return totalAveragePrice;
    }

    public void setTotalAveragePrice(BigDecimal totalAveragePrice) {
        this.totalAveragePrice = totalAveragePrice;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getMaxStorageQuantity() {
        return maxStorageQuantity;
    }

    public void setMaxStorageQuantity(BigDecimal maxStorageQuantity) {
        this.maxStorageQuantity = maxStorageQuantity;
    }

    public BigDecimal getMinStorageQuantity() {
        return minStorageQuantity;
    }

    public void setMinStorageQuantity(BigDecimal minStorageQuantity) {
        this.minStorageQuantity = minStorageQuantity;
    }
}
