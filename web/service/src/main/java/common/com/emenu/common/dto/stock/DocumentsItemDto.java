package com.emenu.common.dto.stock;

import java.math.BigDecimal;

/**
 * 新库存单据明细Dto
 *
 * @author renhongshuai
 *         Created by admin.
 * @Time 2017/3/6 9:57.
 */
public class DocumentsItemDto {

    public Integer getDocumentsItemId() {
        return documentsItemId;
    }

    public void setDocumentsItemId(Integer documentsItemId) {
        this.documentsItemId = documentsItemId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getSpecificationId() {
        return specificationId;
    }

    public void setSpecificationId(Integer specificationId) {
        this.specificationId = specificationId;
    }

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

    public String getOrderUnit() {
        return orderUnit;
    }

    public void setOrderUnit(String orderUnit) {
        this.orderUnit = orderUnit;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getStorageUnit() {
        return storageUnit;
    }

    public void setStorageUnit(String storageUnit) {
        this.storageUnit = storageUnit;
    }

    public BigDecimal getStorageQuantity() {
        return storageQuantity;
    }

    public void setStorageQuantity(BigDecimal storageQuantity) {
        this.storageQuantity = storageQuantity;
    }

    public String getCostCardUnit() {
        return costCardUnit;
    }

    public void setCostCardUnit(String costCardUnit) {
        this.costCardUnit = costCardUnit;
    }

    public BigDecimal getCostCardQuantity() {
        return costCardQuantity;
    }

    public void setCostCardQuantity(BigDecimal costCardQuantity) {
        this.costCardQuantity = costCardQuantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    // 主键
    private Integer documentsItemId;

    // 物品id
    private Integer itemId;

    // 规格id
    private Integer specificationId;

    // 物品名称
    private String itemName;

    // 物品编号
    private String itemNumber;

    // 订货单位
    private String orderUnit;

    // 数量
    private BigDecimal quantity;

    // 库存单位
    private String storageUnit;

    // 库存数量
    private BigDecimal storageQuantity;

    // 成本卡单位
    private String costCardUnit;

    // 成本卡数量
    private BigDecimal costCardQuantity;

    // 单价
    private BigDecimal unitPrice;


}
