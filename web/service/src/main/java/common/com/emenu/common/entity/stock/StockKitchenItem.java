package com.emenu.common.entity.stock;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * StockKitchenItem
 *
 * @author yaojf
 * @date 2017/3/6 13:40
 */
@Entity
@Table(name = "t_stock_kitchen_item")
public class StockKitchenItem extends AbstractEntity{
    @Id
    private Integer id;

    //厨房id
    @Column(name = "kitchen_id")
    private Integer kitchenId;

    //物品id
    @Column(name = "item_id")
    private Integer itemId;

    //规格id
    @Column(name = "specifications")
    private Integer specifications;

    //供货商id
    @Column(name = "supplier_id")
    private Integer supplierId;

    //供应商名称
    @Transient
    private String supplierName;

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public Integer getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(Integer supplierId) {
        this.supplierId = supplierId;
    }

    //库存量
    @Column(name = "storage_quantity")
    private BigDecimal storageQuantity;

    //单位
    @Column(name = "unit_id")
    private Integer unitId;

    //物品状态(1-正常使用,2-标缺，3-删除)
    private Integer status;

    //创建时间
    @Column(name="created_time")
    private String createdTime;

    //最近修改时间
    @Column(name = "last_modified_time")
    private String lastModifiedTime;

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getKitchenId() {
        return kitchenId;
    }

    public void setKitchenId(Integer kitchenId) {
        this.kitchenId = kitchenId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }

    public Integer getSpecifications() {
        return specifications;
    }

    public void setSpecifications(Integer specifications) {
        this.specifications = specifications;
    }

    public BigDecimal getStorageQuantity() {
        return storageQuantity;
    }

    public void setStorageQuantity(BigDecimal storageQuantity) {
        this.storageQuantity = storageQuantity;
    }

    public Integer getUnitId() {
        return unitId;
    }

    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(String createdTime) {
        this.createdTime = createdTime;
    }

    public String getLastModifiedTime() {
        return lastModifiedTime;
    }

    public void setLastModifiedTime(String lastModifiedTime) {
        this.lastModifiedTime = lastModifiedTime;
    }
}
