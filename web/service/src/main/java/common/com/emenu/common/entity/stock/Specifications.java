package com.emenu.common.entity.stock;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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

    //库存单位id
    @Column(name = "storage_unit_id")
    private Integer storageUnitId;

    //成本卡单位id
    @Column(name = "cost_card_id")
    private Integer costCardId;

    //订货单位到库存单位转换比例
    @Column(name = "order_to_storage_ratio")
    private BigDecimal orderToStorage;

    //库存单位到成本卡单位转换比例
    @Column(name = "storage_to_cost_card_ratio")
    private BigDecimal storageToCost;

    // 状态
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

    public Integer getCostCardId() {
        return costCardId;
    }

    public void setCostCardId(Integer costCardId) {
        this.costCardId = costCardId;
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
