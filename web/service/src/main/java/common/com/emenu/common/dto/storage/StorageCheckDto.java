package com.emenu.common.dto.storage;

import com.emenu.common.entity.storage.StorageSettlementItem;

import java.math.BigDecimal;

/**
 * StorageCheckDto
 * 库存盘点
 * @author dujuan
 * @date 2015/11/16
 */
public class StorageCheckDto {

    //名称
    private String name;

    // 物品编号
    private String itemNumber;

    // 订货单位
    private String orderUnitName;

    // 库存单位
    private String storageUnitName;

    // 分类名称
    private String tagName;

    // 最近入库单价
    private BigDecimal lastStockInPrice;

    //期初数量
     private BigDecimal beginQuantity;

    //期初金额
    private BigDecimal beginMoney;

    private StorageSettlementItem settlementItem;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(String itemNumber) {
        this.itemNumber = itemNumber;
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

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
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

    public StorageSettlementItem getSettlementItem() {
        return settlementItem;
    }

    public void setSettlementItem(StorageSettlementItem settlementItem) {
        this.settlementItem = settlementItem;
    }
}
