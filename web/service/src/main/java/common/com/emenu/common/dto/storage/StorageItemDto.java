package com.emenu.common.dto.storage;

import java.math.BigDecimal;

/**
 * StorageItemDto
 * 库存物品DTO供StorageSupplierDto使用
 * @author dujuan
 * @date 2015/11/19
 */
public class StorageItemDto {

    //名称
    private String itemName;

    //数量
    private BigDecimal itemQuantity;

    //金额
    private BigDecimal itemMoney;

    //供货商
    private Integer supplierPartyId;

    //经手人名称
    private String handlerName;

    //操作人名称
    private String createdName;

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public BigDecimal getItemQuantity() {
        return itemQuantity;
    }

    public void setItemQuantity(BigDecimal itemQuantity) {
        this.itemQuantity = itemQuantity;
    }

    public BigDecimal getItemMoney() {
        return itemMoney;
    }

    public void setItemMoney(BigDecimal itemMoney) {
        this.itemMoney = itemMoney;
    }

    public Integer getSupplierPartyId() {
        return supplierPartyId;
    }

    public void setSupplierPartyId(Integer supplierPartyId) {
        this.supplierPartyId = supplierPartyId;
    }

    public String getHandlerName() {
        return handlerName;
    }

    public void setHandlerName(String handlerName) {
        this.handlerName = handlerName;
    }

    public String getCreatedName() {
        return createdName;
    }

    public void setCreatedName(String createdName) {
        this.createdName = createdName;
    }
}
