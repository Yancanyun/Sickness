package com.emenu.common.entity.storage;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * StorageReportIngredient
 *
 * @author xiaozl
 * @date: 2016/6/14
 */
@Entity
@Table(name = "t_storage_report_ingredient")
public class StorageReportIngredient extends AbstractEntity{

    // 主键
    @Id
    private Integer id;

    @Column(name = "ingredient_id")
    private Integer ingredientId;

    // 原配料名称
    @Transient
    private String ingredientName;

    // 出库、盘盈、盘亏数量 成卡单位数量
    private BigDecimal quantity;

    // 成本卡单位名
    @Transient
    private String costCardUnitName;

    // 库存单位名
    @Transient
    private String storageUnitName;

    // 库存单位数量
    @Transient
    private BigDecimal storageUnitQuantity;

    // 备注
    private String comment;

    // 单据Id
    @Column(name = "report_id")
    private Integer reportId;

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

    public Integer getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(Integer ingredientId) {
        this.ingredientId = ingredientId;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
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

    public String getCostCardUnitName() {
        return costCardUnitName;
    }

    public void setCostCardUnitName(String costCardUnitName) {
        this.costCardUnitName = costCardUnitName;
    }

    public String getStorageUnitName() {
        return storageUnitName;
    }

    public void setStorageUnitName(String storageUnitName) {
        this.storageUnitName = storageUnitName;
    }

    public BigDecimal getStorageUnitQuantity() {
        return storageUnitQuantity;
    }

    public void setStorageUnitQuantity(BigDecimal storageUnitQuantity) {
        this.storageUnitQuantity = storageUnitQuantity;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }
}
