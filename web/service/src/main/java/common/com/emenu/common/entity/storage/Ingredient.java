package com.emenu.common.entity.storage;

import com.pandawork.core.common.entity.AbstractEntity;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Ingredient
 * 原配料实体
 * @author xiaozl
 * @date: 2016/5/14
 */
@Entity
@Table(name = "t_ingredient")
public class Ingredient extends AbstractEntity{

    // 主键
    @Id
    private Integer id;

    // 原配料分类id
    @Column(name = "tag_id")
    private Integer tagId;

    // 分类名
    @Transient
    private String tagName;

    // 原配料名称
    private String name;

    // 原配料编号
    @Column(name = "ingredient_number")
    private String ingredientNumber;

    // 原配料助记码
    @Column(name = "assistant_code")
    private String assistantCode;

    // 订货单位id
    @Column(name = "order_unit_id")
    private Integer orderUnitId;

    // 订货单位名称
    @Transient
    private String orderUnitName;

    // 库存单位数量
    @Transient
    private BigDecimal orderQuantity;

    // 库存单位id
    @Column(name = "storage_unit_id")
    private Integer storageUnitId;

    // 库存单位名称
    private String storageUnitName;

    // 成本卡单位id
    @Column(name = "cost_card_unit_id")
    private Integer costCardUnitId;

    // 成本卡单位名称
    @Transient
    private String costCardUnitName;

    // 成卡单位数量
    @Transient
    private BigDecimal costCardQuantity;

    // 订货单位到库存单位转换比例
    @Column(name = "order_to_storage_ratio")
    private BigDecimal orderToStorageRatio;

    // 库存单位到成本卡单位转换比例
    @Column(name = "storage_to_cost_card_ratio")
    private BigDecimal storageToCostCardRatio;

    // 最大库存量：以成本卡单位为单位存储-展示的时候用库存单位
    @Column(name = "max_storage_quantity")
    private BigDecimal maxStorageQuantity;

    private String maxStorageQuantityStr;

    // 最小库存量：以成本卡单位为单位存储-展示的时候用库存规格
    @Column(name = "min_storage_quantity")
    private BigDecimal minStorageQuantity;

    private String minStorageQuantityStr;

    // 实际数量-剩余库存 以成本卡单位为单位存储-展示的时候用库存单位
    @Column(name = "real_quantity")
    private BigDecimal realQuantity;

    private String realQuantityStr;

    // 实际金额：剩余库存
    @Column(name = "real_money")
    private BigDecimal realMoney;

    // 总数量：入库以来累计数量和 以成本卡单位为单位存储-展示的时候用库存单位
    @Column(name = "total_quantity")
    private BigDecimal totalQuantity;

    private String totalQuantityStr;

    // 入库以来累计金额
    @Column(name = "total_money")
    private BigDecimal totalMoney;

    // 平均价格 = 总金额/总数量
    @Column(name = "average_price")
    private BigDecimal averagePrice;

    // 原配料使用状态：1-正常使用,2-已删除
    private Integer status;

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

    public Integer getTagId() {
        return tagId;
    }

    public void setTagId(Integer tagId) {
        this.tagId = tagId;
    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(String tagName) {
        this.tagName = tagName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredientNumber() {
        return ingredientNumber;
    }

    public void setIngredientNumber(String ingredientNumber) {
        this.ingredientNumber = ingredientNumber;
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

    public String getOrderUnitName() {
        return orderUnitName;
    }

    public void setOrderUnitName(String orderUnitName) {
        this.orderUnitName = orderUnitName;
    }

    public BigDecimal getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(BigDecimal orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    public Integer getStorageUnitId() {
        return storageUnitId;
    }

    public void setStorageUnitId(Integer storageUnitId) {
        this.storageUnitId = storageUnitId;
    }

    public String getStorageUnitName() {
        return storageUnitName;
    }

    public void setStorageUnitName(String storageUnitName) {
        this.storageUnitName = storageUnitName;
    }

    public Integer getCostCardUnitId() {
        return costCardUnitId;
    }

    public void setCostCardUnitId(Integer costCardUnitId) {
        this.costCardUnitId = costCardUnitId;
    }

    public String getCostCardUnitName() {
        return costCardUnitName;
    }

    public void setCostCardUnitName(String costCardUnitName) {
        this.costCardUnitName = costCardUnitName;
    }

    public BigDecimal getCostCardQuantity() {
        return costCardQuantity;
    }

    public void setCostCardQuantity(BigDecimal costCardQuantity) {
        this.costCardQuantity = costCardQuantity;
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

    public String getMaxStorageQuantityStr() {
        return maxStorageQuantityStr;
    }

    public void setMaxStorageQuantityStr(String maxStorageQuantityStr) {
        this.maxStorageQuantityStr = maxStorageQuantityStr;
    }

    public BigDecimal getMinStorageQuantity() {
        return minStorageQuantity;
    }

    public void setMinStorageQuantity(BigDecimal minStorageQuantity) {
        this.minStorageQuantity = minStorageQuantity;
    }

    public String getMinStorageQuantityStr() {
        return minStorageQuantityStr;
    }

    public void setMinStorageQuantityStr(String minStorageQuantityStr) {
        this.minStorageQuantityStr = minStorageQuantityStr;
    }

    public BigDecimal getRealQuantity() {
        return realQuantity;
    }

    public void setRealQuantity(BigDecimal realQuantity) {
        this.realQuantity = realQuantity;
    }

    public String getRealQuantityStr() {
        return realQuantityStr;
    }

    public void setRealQuantityStr(String realQuantityStr) {
        this.realQuantityStr = realQuantityStr;
    }

    public BigDecimal getRealMoney() {
        return realMoney;
    }

    public void setRealMoney(BigDecimal realMoney) {
        this.realMoney = realMoney;
    }

    public BigDecimal getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(BigDecimal totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public String getTotalQuantityStr() {
        return totalQuantityStr;
    }

    public void setTotalQuantityStr(String totalQuantityStr) {
        this.totalQuantityStr = totalQuantityStr;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(BigDecimal averagePrice) {
        this.averagePrice = averagePrice;
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
