package com.emenu.common.entity.stock;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by apple on 17/2/27.
 */
@Entity
@Table(name = "t_stock_specification")
public class Specifications extends AbstractEntity{

    @Id
    private Integer id;

    //订货单位id
    @Column(name = "order_unit_id")
    private Integer orderUnitId;

    //订货单位名称
    @Transient
    private String orderUnitName;

    //库存单位id
    @Column(name = "storage_unit_id")
    private Integer storageUnitId;

    //库存单位名称
    @Transient
    private String storageUnitName;

    //成本卡单位id
    @Column(name = "cost_card_unit_id")
    private Integer costCardUnitId;

    //成本卡单位名称
    @Transient
    private String costCardUnitName;

    //订货单位到库存单位转换比例
    @Column(name = "order_to_storage_ratio")
    private BigDecimal orderToStorage;

    //库存单位到成本卡单位转换比例
    @Column(name = "storage_to_cost_card_ratio")
    private BigDecimal storageToCost;

    // 状态 (2-已删除 1-正常使用)
    @Column(name = "status")
    private Integer status;

    //创建时间
    @Column(name = "created_time")
    private Date createdTime;

    //最近修改时间
    @Column(name = "last_modified_time")
    private Date lastModifiedTime;

    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer id) {
        this.id = id;
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

    public String getCostCardUnitName() {
        return costCardUnitName;
    }

    public void setCostCardUnitName(String costCardUnitName) {
        this.costCardUnitName = costCardUnitName;
    }

    public Integer getCostCardUnitId() {
        return costCardUnitId;
    }

    public void setCostCardUnitId(Integer costCardUnitId) {
        this.costCardUnitId = costCardUnitId;
    }

    public BigDecimal getOrderToStorage() {
        return orderToStorage;
    }

    public void setOrderToStorage(BigDecimal orderToStorage) {
        this.orderToStorage = orderToStorage;
    }

    public BigDecimal getStorageToCost() {
        return storageToCost;
    }

    public void setStorageToCost(BigDecimal storageToCost) {
        this.storageToCost = storageToCost;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
