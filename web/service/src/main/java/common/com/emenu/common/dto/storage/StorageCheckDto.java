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
    private String itemName;

    // 物品编号
    private String itemNumber;

    // 分类名称
    private String tagName;

    // 订货单位
    private String orderUnitName;

    // 库存单位
    private String storageUnitName;

    // 最近入库单价
    private BigDecimal lastStockInPrice;

    //期初数量
     private BigDecimal beginQuantity;

    //期初金额
    private BigDecimal beginMoney;

    //入库数量
    private BigDecimal stockInQuantity;

    //入库金额
    private BigDecimal stockInMoney;

    //出库数量
    private BigDecimal stockOutQuantity;

    //出库金额
    private BigDecimal stockOutMoney;

    //盈亏数量
    private BigDecimal incomeLossQuantity;

    //盈亏金额
    private BigDecimal incomeLossMoney;

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

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
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

    public BigDecimal getLastStockInPrice() {
        return lastStockInPrice;
    }

    public void setLastStockInPrice(BigDecimal lastStockInPrice) {
        this.lastStockInPrice = lastStockInPrice;
    }

    public BigDecimal getBeginQuantity() {
        return beginQuantity;
    }

    public void setBeginQuantity(BigDecimal beginQuantity) {
        this.beginQuantity = beginQuantity;
    }

    public BigDecimal getBeginMoney() {
        return beginMoney;
    }

    public void setBeginMoney(BigDecimal beginMoney) {
        this.beginMoney = beginMoney;
    }

    public BigDecimal getStockInQuantity() {
        return stockInQuantity;
    }

    public void setStockInQuantity(BigDecimal stockInQuantity) {
        this.stockInQuantity = stockInQuantity;
    }

    public BigDecimal getStockInMoney() {
        return stockInMoney;
    }

    public void setStockInMoney(BigDecimal stockInMoney) {
        this.stockInMoney = stockInMoney;
    }

    public BigDecimal getStockOutQuantity() {
        return stockOutQuantity;
    }

    public void setStockOutQuantity(BigDecimal stockOutQuantity) {
        this.stockOutQuantity = stockOutQuantity;
    }

    public BigDecimal getStockOutMoney() {
        return stockOutMoney;
    }

    public void setStockOutMoney(BigDecimal stockOutMoney) {
        this.stockOutMoney = stockOutMoney;
    }

    public BigDecimal getIncomeLossQuantity() {
        return incomeLossQuantity;
    }

    public void setIncomeLossQuantity(BigDecimal incomeLossQuantity) {
        this.incomeLossQuantity = incomeLossQuantity;
    }

    public BigDecimal getIncomeLossMoney() {
        return incomeLossMoney;
    }

    public void setIncomeLossMoney(BigDecimal incomeLossMoney) {
        this.incomeLossMoney = incomeLossMoney;
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
