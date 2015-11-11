package com.emenu.common.entity.storage;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 库存物品实体
 *
 * @author: zhangteng
 * @time: 2015/11/11 14:02
 **/
@Entity
@Table(name = "t_storage_item")
public class StorageItem extends AbstractEntity {

    // 主键
    @Id
    private Integer id;

    // 供货商ID
    @Column(name = "supplier_party_id")
    private Integer supplierPartyId;

    // 分类ID
    @Column(name = "tag_id")
    private Integer tagId;

    // 名称
    private String name;

    // 物品编号
    @Column(name = "item_number")
    private String itemNumber;

    // 助记码
    @Column(name = "assistant_code")
    private String assistantCode;

    // 订货单位
    @Column(name = "order_unit_id")
    private Integer orderUnitId;

    // 库存单位
    @Column(name = "storage_unit_id")
    private Integer storageUnitId;

    // 成本卡单位
    @Column(name = "cost_card_unit_id")
    private Integer costCardUnitId;

    // 计数单位
    @Column(name = "count_unit_id")
    private Integer countUnitId;

    // 订货单位到库存单位转换率
    @Column(name = "order_to_storage_ratio")
    private BigDecimal orderToStorageRatio;

    // 库存单位到成本卡单位转换率
    @Column(name = "storage_to_cost_card_ratio")
    private BigDecimal storageToCostCardRatio;

    // 最大库存量
    @Column(name = "max_storage_quantity")
    private BigDecimal maxStorageQuantity;

    // 最小库存量
    @Column(name = "min_storage_quantity")
    private BigDecimal minStorageQuantity;

    // 出库方式
    @Column(name = "stock_out_type")
    private Integer stockOutType;

    // 总入库数量
    @Column(name = "total_stock_in_quantity")
    private BigDecimal totalStockInQuantity;

    // 总入库金额
    @Column(name = "total_stock_in_money")
    private BigDecimal totalStockInMoney;

    // 最近入库单价
    @Column(name = "last_stock_in_price")
    private BigDecimal lastStockInPrice;

    // 创建时间
    @Column(name = "created_time")
    private Date createdTime;

    // 最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getSupplierPartyId() {
        return supplierPartyId;
    }

    public void setSupplierPartyId(Integer supplierPartyId) {
        this.supplierPartyId = supplierPartyId;
    }

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

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

    public String getAssistantCode() {
        return assistantCode;
    }

    public void setAssistantCode(String assistantCode) {
        this.assistantCode = assistantCode;
    }

    public Integer getOrderUnitId() {
        return orderUnitId;
    }

    public void setOrderUnitId(Integer orderUnitId) {
        this.orderUnitId = orderUnitId;
    }

    public Integer getStorageUnitId() {
        return storageUnitId;
    }

    public void setStorageUnitId(Integer storageUnitId) {
        this.storageUnitId = storageUnitId;
    }

    public Integer getCostCardUnitId() {
        return costCardUnitId;
    }

    public void setCostCardUnitId(Integer costCardUnitId) {
        this.costCardUnitId = costCardUnitId;
    }

    public Integer getCountUnitId() {
        return countUnitId;
    }

    public void setCountUnitId(Integer countUnitId) {
        this.countUnitId = countUnitId;
    }

    public BigDecimal getOrderToStorageRatio() {
        return orderToStorageRatio;
    }

    public void setOrderToStorageRatio(BigDecimal orderToStorageRatio) {
        this.orderToStorageRatio = orderToStorageRatio;
    }

    public BigDecimal getStorageToCostCardRatio() {
        return storageToCostCardRatio;
    }

    public void setStorageToCostCardRatio(BigDecimal storageToCostCardRatio) {
        this.storageToCostCardRatio = storageToCostCardRatio;
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

    public Integer getStockOutType() {
        return stockOutType;
    }

    public void setStockOutType(Integer stockOutType) {
        this.stockOutType = stockOutType;
    }

    public BigDecimal getTotalStockInQuantity() {
        return totalStockInQuantity;
    }

    public void setTotalStockInQuantity(BigDecimal totalStockInQuantity) {
        this.totalStockInQuantity = totalStockInQuantity;
    }

    public BigDecimal getTotalStockInMoney() {
        return totalStockInMoney;
    }

    public void setTotalStockInMoney(BigDecimal totalStockInMoney) {
        this.totalStockInMoney = totalStockInMoney;
    }

    public BigDecimal getLastStockInPrice() {
        return lastStockInPrice;
    }

    public void setLastStockInPrice(BigDecimal lastStockInPrice) {
        this.lastStockInPrice = lastStockInPrice;
    }

    public Date getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(Date createdTime) {
        this.createdTime = createdTime;
    }

    public Date getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(Date lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}
